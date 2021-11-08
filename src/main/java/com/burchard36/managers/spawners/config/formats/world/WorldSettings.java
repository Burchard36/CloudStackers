package com.burchard36.managers.spawners.config.formats.world;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class WorldSettings {
    @Json(name = "enabled_worlds")
    public List<String> enabledWorlds;

    public WorldSettings() {
        this.enabledWorlds = new ArrayList<>();
    }
}
