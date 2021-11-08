package com.burchard36.managers.spawners.config.formats.spawner;

import com.squareup.moshi.Json;

public class SpawnerSettings {

    @Json(name = "spawner_mode")
    private String mode;

    public SpawnerSettings() {
        this.mode = SpawnerSpawnMode.VANILLA.name();
    }

    /**
     * Parses the mode variable as a SpawnerSpawnMode
     * @return SpawnerSpawnMode from String mode
     */
    public final SpawnerSpawnMode getSpawnMode() {
        return SpawnerSpawnMode.valueOf(this.mode);
    }

}
