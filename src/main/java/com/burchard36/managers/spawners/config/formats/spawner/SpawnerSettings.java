package com.burchard36.managers.spawners.config.formats.spawner;

import com.squareup.moshi.Json;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class SpawnerSettings {

    @Json(name = "spawner_mode")
    private String mode; // TODO Test this by setting to the SpawnerSpawnMode enum?
    @Json(name = "spawner_item")
    private List<SpawnerItem> spawnerItem;

    public SpawnerSettings() {
        this.mode = SpawnerSpawnMode.VANILLA.name();
        this.spawnerItem = new ArrayList<>();
        this.spawnerItem.add(new SpawnerItem("&d&lEnderman Spawner", new ArrayList<>(), EntityType.ENDERMAN));
        this.spawnerItem.add(new SpawnerItem("&2&lZombie Spawner", new ArrayList<>(), EntityType.ZOMBIE));
        this.spawnerItem.add(new SpawnerItem("&f&lSkeleton Spawner", new ArrayList<>(), EntityType.SKELETON));
    }

    /**
     * Parses the mode variable as a SpawnerSpawnMode
     * @return SpawnerSpawnMode from String mode
     */
    public final SpawnerSpawnMode getSpawnMode() {
        return SpawnerSpawnMode.valueOf(this.mode);
    }

    public final void setSpawnerSpawnMode(final SpawnerSpawnMode mode) {
        this.mode = mode.name();
    }

    public final void addSpawnerItem(final SpawnerItem item) {
        this.spawnerItem.add(item);
    }

    public final List<SpawnerItem> getSpawnerItemList() {
        return this.spawnerItem;
    }

}
