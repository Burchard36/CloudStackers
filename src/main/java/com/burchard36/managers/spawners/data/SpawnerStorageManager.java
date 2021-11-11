package com.burchard36.managers.spawners.data;

import com.burchard36.CloudStacker;
import com.burchard36.json.JsonDataFile;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.SpawnerManager;
import com.burchard36.managers.spawners.config.SpawnerConfigs;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;

import java.util.UUID;

public class SpawnerStorageManager implements Manager {

    private final SpawnerManager spawnerManager;
    private final CloudStacker plugin;
    private PluginDataMap spawnersByWorld;

    public SpawnerStorageManager(final SpawnerManager spawnerManager) {
        this.spawnerManager = spawnerManager;
        this.plugin = this.spawnerManager.getPlugin();
    }

    @Override
    public void load() {
        this.plugin.getPluginDataManager().registerPluginMap(SpawnerConfigs.SPAWNER_DATA, new PluginDataMap());
        this.spawnersByWorld = this.plugin.getPluginDataManager().getDataMap(SpawnerConfigs.SPAWNER_DATA);
        this.loadSpawnerData();
    }

    public SpawnerStorage getCachedStorageFile(final World world) {
        return (SpawnerStorage) this.spawnersByWorld.getDataFile(world.getUID().toString());
    }

    private SpawnerStorage getNewSpawnerStorageFile(final World world) {
        return new SpawnerStorage(this.plugin, "/data/" + world.getName(), FileFormat.JSON);
    }

    /**
     * Loads the DataFile into the PluginDataMap ordered by the world UUID
     * @param world World to load in
     */
    private void loadDataFile(final World world) {
        final UUID worldUuid = world.getUID();
        final SpawnerStorage worldStorage = this.getNewSpawnerStorageFile(world);
        this.spawnersByWorld.loadDataFile(worldUuid.toString(), worldStorage);
    }

    /**
     * Loads spawner data to the map ordered by world
     * @param world World to load spawner data from
     */
    private void loadSpawnerData(final World world) {
        final JsonDataFile spawnerDataFile = this.spawnersByWorld.getDataFile(world.getUID().toString());
        final SpawnerStorage spawnerStorageFile = (SpawnerStorage) spawnerDataFile;

        spawnerStorageFile.getSpawnerData().forEach((data) -> {
            final CreatureSpawner dataSpawner = data.getCreatureSpawner();
            if (dataSpawner == null) return; // TODO: Remove errored spawners from storage
            this.spawnerManager.addStackedSpawner(data.getLocation(), data.createStackedSpawner());
        });
    }

    private void saveSpawnerData(final World world) {
        final SpawnerStorage storage = this.getCachedStorageFile(world);
        storage.flushData();
        this.spawnerManager.getStackedSpawners().forEach((location, stackedSpawner) -> {
            if (location.getWorld().getUID() == world.getUID()) {
                storage.addIfNotExists(stackedSpawner.spawnerData);
            }
        });
    }

    private void loadSpawnerData() {
        Bukkit.getWorlds().forEach((world) -> {
            this.loadDataFile(world);
            this.loadSpawnerData(world);
        });
    }

    private void saveSpawnerData() {
        Bukkit.getWorlds().forEach(this::saveSpawnerData);
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {
        this.saveSpawnerData();
        this.spawnersByWorld.saveAll();
        this.spawnersByWorld.getDataMap().clear();
    }
}
