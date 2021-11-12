package com.burchard36.managers.spawners.lib;

import com.burchard36.hologram.Hologram;
import com.burchard36.managers.spawners.SpawnerManager;
import com.burchard36.managers.spawners.data.JsonSpawnerData;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;

import java.util.UUID;

public class StackedSpawner {

    private final CreatureSpawner spawner;
    public final JsonSpawnerData spawnerData;
    public SpawnerManager spawnerManager;

    public final UUID mapKey;

    public StackedSpawner(final CreatureSpawner spawner,
                          final JsonSpawnerData spawnerData) {
        this.spawner = spawner;
        this.spawnerData = spawnerData;
        this.mapKey = UUID.randomUUID();
    }

    public final StackedSpawner loadHologram(final SpawnerManager manager) {
        this.spawnerManager = manager;
        final Location location = this.spawner.getLocation();
        location.add(0.5D, -1D, 0.5D);
        final Hologram hologram = new Hologram(location, "&7x&b" + this.spawnerData.spawnerAmount + " &e&o" + this.spawnerData.hologramName.toLowerCase().substring(0, 1).toUpperCase() + this.spawnerData.hologramName.substring(1));
        this.spawnerManager.getPlugin().getHologramManager().addHologram(this.mapKey.toString(), hologram);
        return this;
    }

    public final StackedSpawner reloadHologram() {
        final Hologram hologram = this.spawnerManager.getPlugin().getHologramManager().getHologram(this.mapKey.toString());
        hologram.updateText("&7x&b" + this.spawnerData.spawnerAmount + " &e&o" + this.spawnerData.hologramName.toLowerCase().substring(0, 1).toUpperCase() + this.spawnerData.hologramName.substring(1));
        return this;
    }
}
