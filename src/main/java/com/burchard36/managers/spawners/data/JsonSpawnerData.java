package com.burchard36.managers.spawners.data;

import com.squareup.moshi.Json;

public class JsonSpawnerData {

    public int x;
    public int y;
    public int z;
    @Json(name = "spawner_level")
    public int spawnerLevel;
    @Json(name = "spawner_amount")
    public int spawnerAmount;
    @Json(name = "world_name")
    public String worldName;

    public JsonSpawnerData() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.spawnerLevel = 1;
        this.spawnerAmount = 1;
        this.worldName = "";
    }

}
