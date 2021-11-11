package com.burchard36.managers.spawners.lib;

import com.burchard36.managers.spawners.data.JsonSpawnerData;
import org.bukkit.block.CreatureSpawner;

public class StackedSpawner {

    private final CreatureSpawner spawner;
    public final JsonSpawnerData spawnerData;

    public StackedSpawner(final CreatureSpawner spawner,
                          final JsonSpawnerData spawnerData) {
        this.spawner = spawner;
        this.spawnerData = spawnerData;
    }
}
