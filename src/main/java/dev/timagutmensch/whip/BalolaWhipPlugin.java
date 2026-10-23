package dev.timagutmensch.whip;

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.timagutmensch.whip.interaction.ProlongPositiveEffectsInteraction;

import javax.annotation.Nonnull;

public class BalolaWhipPlugin extends JavaPlugin {

    public BalolaWhipPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCodecRegistry(Interaction.CODEC)
                .register("ProlongPositiveEffects", ProlongPositiveEffectsInteraction.class, ProlongPositiveEffectsInteraction.CODEC);
    }
}
