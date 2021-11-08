package com.burchard36.managers.players;

import java.util.UUID;

public class StackerPlayer {

    private boolean spawnerStackingMode;

    public StackerPlayer(final UUID uuid) {
        this.spawnerStackingMode = true;
    }

    /**
     * @return boolean based on if player has theyre Spawner stacking mode enabled
     */
    public boolean spawnerStackingModeEnabled() {
        return this.spawnerStackingMode;
    }

    /**
     * Sets the boolean that decides wether when a player places a Spawner it gets stacked to one block or not
     * @param mode boolean on wether to enable this mode or not
     */
    public void setSpawnerStackingMode(final boolean mode) {
        this.spawnerStackingMode = mode;
    }
}
