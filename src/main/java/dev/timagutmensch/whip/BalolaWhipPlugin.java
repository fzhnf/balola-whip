package dev.timagutmensch.whip;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

// Asset-only mod: the whip is item JSON plus the Balola_Whipped effect asset, no interaction code.
public class BalolaWhipPlugin extends JavaPlugin {

    public BalolaWhipPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }
}
