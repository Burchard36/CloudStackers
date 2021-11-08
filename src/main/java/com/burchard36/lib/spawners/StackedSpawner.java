package com.burchard36.lib.spawners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class StackedSpawner {

    private final Location spawnerLocation;

    public StackedSpawner(final Location spawnerLocation,
                          final EntityType spawnerType,
                          final int spawnerLevel) {
        this.spawnerLocation = spawnerLocation;
    }

    public Location getSpawnerLocation() {
        return this.spawnerLocation;
    }
}
