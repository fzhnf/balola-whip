package dev.timagutmensch.whip.interaction;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

/**
 * Adds {@code BonusSeconds} to the remaining duration of every positive (non-debuff, finite) effect
 * already present on the target. It never applies an effect that is not already there, and never
 * pushes an effect further than {@code MaxBonusSeconds} past its own base duration.
 */
public class ProlongPositiveEffectsInteraction extends SimpleInstantInteraction {

    public static final BuilderCodec<ProlongPositiveEffectsInteraction> CODEC = BuilderCodec.builder(
            ProlongPositiveEffectsInteraction.class, ProlongPositiveEffectsInteraction::new, SimpleInstantInteraction.CODEC
    )
            .append(
                    new KeyedCodec<>("Entity", InteractionTarget.CODEC),
                    (interaction, value, info) -> interaction.entityTarget = value,
                    (interaction, info) -> interaction.entityTarget
            )
            .add()
            .append(
                    new KeyedCodec<>("BonusSeconds", Codec.FLOAT),
                    (interaction, value, info) -> interaction.bonusSeconds = value,
                    (interaction, info) -> interaction.bonusSeconds
            )
            .add()
            .append(
                    new KeyedCodec<>("MaxBonusSeconds", Codec.FLOAT),
                    (interaction, value, info) -> interaction.maxBonusSeconds = value,
                    (interaction, info) -> interaction.maxBonusSeconds
            )
            .add()
            .build();

    private InteractionTarget entityTarget = InteractionTarget.TARGET;
    private float bonusSeconds = 3.0f;
    private float maxBonusSeconds = 240.0f;

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        Ref<EntityStore> target = entityTarget.getEntity(interactionContext, interactionContext.getOwningEntity());
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        if (target == null || !target.isValid() || commandBuffer == null) {
            LOGGER.atInfo().log("BalolaWhip: no valid target (target=%s, buffer=%s)", target, commandBuffer);
            fail(interactionContext);
            return;
        }

        EffectControllerComponent effects = commandBuffer.getComponent(target, EffectControllerComponent.getComponentType());
        if (effects == null) {
            LOGGER.atInfo().log("BalolaWhip: target %s has no EffectControllerComponent", target);
            return;
        }

        ItemStack heldItem = interactionContext.getHeldItem();
        if (heldItem != null) {
            LOGGER.atInfo().log("BalolaWhip: whip durability %.2f / %.2f", heldItem.getDurability(), heldItem.getMaxDurability());
        }

        EntityStatMap stats = commandBuffer.getComponent(target, EntityStatMap.getComponentType());
        if (stats != null) {
            EntityStatValue health = stats.get(DefaultEntityStatTypes.getHealth());
            if (health != null) {
                LOGGER.atInfo().log("BalolaWhip: target health %.2f", health.get());
            }
        }

        int extended = 0;
        for (int index : effects.getActiveEffectIndexes()) {
            ActiveEntityEffect active = effects.getActiveEffects().get(index);
            if (active == null || active.isInfinite() || active.isDebuff()) {
                continue;
            }

            EntityEffect definition = EntityEffect.getAssetMap().getAsset(index);
            if (definition == null || definition.getDuration() <= 0.0f) {
                continue;
            }

            float cap = definition.getDuration() + maxBonusSeconds;
            float room = cap - active.getRemainingDuration();
            if (room <= 0.0f) {
                LOGGER.atInfo().log("BalolaWhip: %s already at cap (%.1fs of %.1fs)", definition.getId(), active.getRemainingDuration(), cap);
                continue;
            }

            float added = Math.min(bonusSeconds, room);
            effects.addEffect(target, index, definition, added, OverlapBehavior.EXTEND, commandBuffer);
            extended++;
            LOGGER.atInfo().log("BalolaWhip: extended %s by %.2fs (now %.1fs)", definition.getId(), added, active.getRemainingDuration());
        }

        if (extended == 0) {
            LOGGER.atInfo().log("BalolaWhip: ran on target %s, nothing extendable", target);
        }
    }

    private static void fail(InteractionContext interactionContext) {
        if (interactionContext.getState() != null) {
            interactionContext.getState().state = InteractionState.Failed;
        }
    }
}
