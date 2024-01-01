package com.dangerlevelsystem.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class InsomniaProfile {
    private boolean amplifyExperienceByLevel;
    private boolean increaseRareDropByLevel;
    private boolean vanillaPhantomSpawn;

    private boolean resetLevelAtDeath;

    private boolean fullMoonEventEnabled;

    public InsomniaProfile(boolean amplifyExperienceByLevel, boolean increaseRareDropByLevel, boolean vanillaPhantomSpawn, boolean resetLevelAtDeath, boolean fullMoonEventEnabled) {
        this.amplifyExperienceByLevel = amplifyExperienceByLevel;
        this.increaseRareDropByLevel = increaseRareDropByLevel;
        this.vanillaPhantomSpawn = vanillaPhantomSpawn;
        this.resetLevelAtDeath = resetLevelAtDeath;
        this.fullMoonEventEnabled = fullMoonEventEnabled;
    }
}
