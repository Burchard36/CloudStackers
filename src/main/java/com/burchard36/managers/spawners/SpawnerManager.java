package com.burchard36.managers.spawners;

import com.burchard36.CloudStacker;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.lib.spawners.StackedSpawner;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.config.SpawnerConfig;
import com.burchard36.managers.spawners.config.SpawnerConfigs;
import com.burchard36.managers.spawners.data.SpawnerStorageManager;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * @author Dalton Burchard
 * This class is designed to handle:
 *
 * Actual merging of spawners
 * Actual upgrading of spawners
 * Actual spawning of spawners
 *
 * This class DOES NOT!:
 * Merge mobs into a stack
 * Merge items onto the ground
 * Stack blocks into each other
 */
public class SpawnerManager implements Manager {

    private final CloudStacker plugin;
    private SpawnerConfig spawnerConfig;
    private SpawnerStorageManager storageManager;

    private HashMap<Location, StackedSpawner> stackedSpawners;

    public SpawnerManager(final CloudStacker plugin) {
        this.plugin = plugin;
        this.load();
    }

    @Override
    public void load() {
        this.stackedSpawners = new HashMap<>();
        this.storageManager = new SpawnerStorageManager(this);
        this.spawnerConfig = new SpawnerConfig(this.plugin,
                "/configs/spawners/spawner_config.json",
                FileFormat.JSON);

        this.plugin.getPluginDataManager().registerPluginMap(SpawnerConfigs.PLUGIN_MAP, new PluginDataMap());
        this.plugin.getPluginDataManager().loadDataFileToMap(SpawnerConfigs.PLUGIN_MAP, SpawnerConfigs.SPAWNER_CONFIG, this.spawnerConfig);

        this.storageManager.load();
    }

    @Override
    public void reload() {
        this.stop(); // Stop the running instances
        this.load(); // load the objects back into
    }

    @Override
    public void stop() {
        this.spawnerConfig = null;
        this.plugin.getPluginDataManager().clearDataMap(SpawnerConfigs.PLUGIN_MAP);
        this.stackedSpawners.clear();
        this.storageManager.stop();
    }

    /**
     * Gets a stacked spawners from the HashMap
     * @param location Location of where the spawner is
     * @return StackedSpawner spawner instance
     */
    public final StackedSpawner getStackedSpawner(final Location location) {
        return this.stackedSpawners.get(location);
    }

    /**
     * Returns an instance of the CloudStacker plugin instance
     * @return CLoudStacker plugin instance
     */
    public final CloudStacker getPlugin() {
        return this.plugin;
    }
}
