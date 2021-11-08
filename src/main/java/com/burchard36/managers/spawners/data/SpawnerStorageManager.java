package com.burchard36.managers.spawners.data;

import com.burchard36.CloudStacker;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.SpawnerManager;
import com.burchard36.managers.spawners.config.SpawnerConfigs;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
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

        Bukkit.getWorlds().forEach((world) -> {
            final UUID worldUuid = world.getUID();
            final SpawnerStorage worldStorage =
                    new SpawnerStorage(plugin, "/data/" + world.getName(), FileFormat.JSON);
            this.spawnersByWorld.loadDataFile(worldUuid.toString(), worldStorage);
        });

        final World world = Bukkit.getWorld("world");
        final SpawnerStorage worldStorage = (SpawnerStorage) this.spawnersByWorld.getDataFile(world.getUID().toString());
        final List<JsonSpawnerData> dataList = worldStorage.getSpawnerData();
        Bukkit.getLogger().info("Total data in list: " + dataList.size());
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {

    }
}
