package com.burchard36.managers.spawners.data;

import com.burchard36.managers.spawners.SpawnerManager;
import com.burchard36.managers.spawners.lib.StackedSpawner;
import com.squareup.moshi.Json;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;

import java.util.logging.Level;

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
    @Json(name = "hologram_name")
    public String hologramName;

    public JsonSpawnerData() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.spawnerLevel = 1;
        this.spawnerAmount = 1;
        this.worldName = "";
        this.hologramName = "";
    }

    public JsonSpawnerData(final int x,
                           final int y,
                           final int z,
                           final int spawnerLevel,
                           final int spawnerAmount,
                           final String worldName,
                           final String hologramName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.spawnerLevel = spawnerLevel;
        this.spawnerAmount = spawnerAmount;
        this.worldName = worldName;
        this.hologramName = hologramName;
    }

    public final World getWorld() {
        return Bukkit.getWorld(this.worldName);
    }

    public final Location getLocation() {
        return new Location(this.getWorld(), this.x, this.y, this.z);
    }

    public final CreatureSpawner getCreatureSpawner() {
        final CreatureSpawner locationSpawner = SpawnerManager.getCreatureSpawner(this.getLocation());
        if (locationSpawner == null) {
            Bukkit.getLogger().log(Level.SEVERE,
                    "Cannot load spawner at in world: " + this.worldName + " at location X: " + this.x + " Y: " + this.y + " Z: " + this.z);
            return null;
        }

        return locationSpawner;
    }

    public final StackedSpawner createStackedSpawner() {
        return new StackedSpawner(this.getCreatureSpawner(), this);
    }

}
