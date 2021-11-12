package com.burchard36.managers.spawners;

import com.burchard36.CloudStacker;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.config.SpawnerConfig;
import com.burchard36.managers.spawners.config.SpawnerConfigs;
import com.burchard36.managers.spawners.data.SpawnerStorageManager;
import com.burchard36.managers.spawners.events.SpawnerEventsHandler;
import com.burchard36.managers.spawners.gui.guis.SpawnerCommandGui;
import com.burchard36.managers.spawners.lib.StackedSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.HandlerList;

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
    private SpawnerEventsHandler spawnerEventsHandler;
    private SpawnerCommandGui spawnerCommandGui;

    private HashMap<Location, StackedSpawner> stackedSpawners;

    public SpawnerManager(final CloudStacker plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        this.spawnerEventsHandler = new SpawnerEventsHandler(this);
        this.stackedSpawners = new HashMap<>();
        this.storageManager = new SpawnerStorageManager(this);
        this.spawnerCommandGui = new SpawnerCommandGui(this);
        this.spawnerConfig = new SpawnerConfig(this.plugin,
                "/configs/spawners/spawner_config.json",
                FileFormat.JSON);

        this.plugin.getPluginDataManager().registerPluginMap(SpawnerConfigs.PLUGIN_MAP, new PluginDataMap());
        this.plugin.getPluginDataManager().loadDataFileToMap(SpawnerConfigs.PLUGIN_MAP, SpawnerConfigs.SPAWNER_CONFIG, this.spawnerConfig);

        this.storageManager.load();
        this.spawnerCommandGui.load();
    }

    @Override
    public void reload() {
        this.stop(); // Stop the running instances
        this.load(); // load the objects back into
    }

    @Override
    public void stop() {
        /* Unregister events */
        HandlerList.unregisterAll(this.spawnerEventsHandler);

        /* Unregister configs */
        this.plugin.getPluginDataManager().clearDataMap(SpawnerConfigs.PLUGIN_MAP);

        /* Clear storage managers */
        this.storageManager.stop();

        /* Clear Gui's attached to this package */
        this.spawnerCommandGui.stop();
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
     * Adds a StackedSpawner to the HashMap
     * @param location Location of where StackedSpawner is
     * @param spawner StackedSpawner instance
     */
    public final void addStackedSpawner(final Location location, final StackedSpawner spawner) {
        this.stackedSpawners.putIfAbsent(location, spawner);
        Bukkit.getLogger().info("Added stacked spawner!");
    }

    /**
     * Returns the map containing all the Stacked Spawners
     * @return HashMap of StackedSpawners ordered by Location
     */
    public final HashMap<Location, StackedSpawner> getStackedSpawners() {
        return this.stackedSpawners;
    }

    public static CreatureSpawner getCreatureSpawner(final Block block) {
        if (block.getState() instanceof CreatureSpawner) {
            return (CreatureSpawner) block.getState();
        } else return null;
    }

    public static CreatureSpawner getCreatureSpawner(final Location location) {
        return getCreatureSpawner(location.getBlock());
    }

    /**
     * Returns an instance of the CloudStacker plugin instance
     * @return CLoudStacker plugin instance
     */
    public final CloudStacker getPlugin() {
        return this.plugin;
    }

    /**
     * Grabs a instance of the SpawnerConfig
     * @return instance of SpawnerConfig
     */
    public final SpawnerConfig getSpawnerConfig() {
        return this.spawnerConfig;
    }

    /**
     * Returns the GUI that handles showing spawners in /spawners
     * @return instance of SpawnerCommandGui
     */
    public final SpawnerCommandGui getSpawnerCommandGui() {
        return this.spawnerCommandGui;
    }
}
