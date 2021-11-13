package com.burchard36.managers.mobs.data;

import com.squareup.moshi.Json;
import org.bukkit.entity.EntityType;

public class JsonMobData {

    @Json(name = "mob_type")
    public String mobType;

    public int x;
    public int y;
    public int z;

    @Json(name = "world_name")
    public String worldName;

    @Json(name = "mob_amount")
    public int amount;

    public JsonMobData() {

    }

    public JsonMobData(
            final String mobType,
            final String worldName,
            final int amount,
            final int x,
            final int y,
            final int z) {
        this.mobType = mobType;
        this.worldName = worldName;
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final EntityType getType() {
        return EntityType.valueOf(this.mobType);
    }

}
