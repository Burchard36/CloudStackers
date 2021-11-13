package com.burchard36.managers.mobs.data;

import com.burchard36.CloudStacker;
import com.burchard36.json.JsonDataFile;
import com.burchard36.json.PluginDataMap;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.managers.Manager;
import com.burchard36.managers.mobs.MobManager;
import com.burchard36.managers.mobs.config.MobConfigs;
import com.burchard36.managers.mobs.lib.StackedMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;
import java.util.logging.Level;

public class MobStorageManager implements Manager {

    public final MobManager manager;
    public final CloudStacker plugin;
    public PluginDataMap mobsByWorld;

    public MobStorageManager(final MobManager manager) {
        this.manager = manager;
        this.plugin = this.manager.getPlugin();
    }

    @Override
    public void load() {
        this.plugin.getPluginDataManager().registerPluginMap(MobConfigs.DATA_FILES, new PluginDataMap());
        this.mobsByWorld = this.plugin.getPluginDataManager().getDataMap(MobConfigs.DATA_FILES);

        this.loadMobData();
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {

    }

    private MobStorage getNewMobStorageFile(final World world) {
        return new MobStorage(this.plugin, "/data/mobs/" + world.getName(), FileFormat.JSON);
    }

    private void loadMobData() {
        Bukkit.getWorlds().forEach((world) -> {
            this.loadDataFile(world);
            this.loadMobData(world);
        });
    }

    private void loadDataFile(final World world) {
        final UUID uuid = world.getUID();
        final MobStorage mobStorage = this.getNewMobStorageFile(world);
        this.mobsByWorld.loadDataFile(uuid.toString(), mobStorage);
    }

    private void loadMobData(final World world) {
        final JsonDataFile dataFile = this.mobsByWorld.getDataFile(world.getUID().toString());
        final MobStorage mobStorage = (MobStorage) dataFile;

        mobStorage.getMobDataList().forEach((jsonData) -> {

            final World mobWorld = Bukkit.getWorld(jsonData.worldName);
            if (mobWorld == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Cannot spawn mob type: " + jsonData.mobType + " Because world: " + jsonData.worldName + " does not exists (Or isnt loaded?)");
                return;
            }
            final StackedMob stackedMob = new StackedMob(new Location(mobWorld, jsonData.x, jsonData.y, jsonData.z), jsonData);
            this.manager.addStackedMob(stackedMob.getEntityUuid(), stackedMob);
        });
    }
}
