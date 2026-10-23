package dev.timagutmensch.whip.interaction;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
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

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext interactionContext, @Nonnull CooldownHandler cooldownHandler) {
        Ref<EntityStore> target = entityTarget.getEntity(interactionContext, interactionContext.getOwningEntity());
        CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
        if (target == null || !target.isValid() || commandBuffer == null) {
            fail(interactionContext);
            return;
        }

        EffectControllerComponent effects = commandBuffer.getComponent(target, EffectControllerComponent.getComponentType());
        if (effects == null) {
            return;
        }

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
                continue;
            }

            float added = Math.min(bonusSeconds, room);
            effects.addEffect(target, index, definition, added, OverlapBehavior.EXTEND, commandBuffer);
        }
    }

    private static void fail(InteractionContext interactionContext) {
        if (interactionContext.getState() != null) {
            interactionContext.getState().state = InteractionState.Failed;
        }
    }
}
