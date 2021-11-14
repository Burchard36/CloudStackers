package com.burchard36.managers.mobs;

import com.burchard36.CloudStacker;
import com.burchard36.managers.Manager;
import com.burchard36.managers.ManagerPackage;
import com.burchard36.managers.mobs.data.MobStorage;
import com.burchard36.managers.mobs.data.MobStorageManager;
import com.burchard36.managers.mobs.events.MobsSpawnEvents;
import com.burchard36.managers.mobs.lib.StackedMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.burchard36.ApiLib.convert;

public class MobManager implements Manager {

    private final ManagerPackage managerPackage;
    private final CloudStacker plugin;
    private MobStorageManager storageManager;
    private MobsSpawnEvents mobsSpawnEvents;
    private BukkitTask mergeTask;

    private HashMap<UUID, StackedMob> stackedMobs;

    public MobManager(final ManagerPackage managerPackage) {
        this.managerPackage = managerPackage;
        this.plugin = this.managerPackage.getPlugin();
    }

    /**
     * Adds a list of StackedMob's to a Location in the HashMap
     * @param entityUuid EntityUUID to use as Key
     * @param stackedMob StackedMob to use
     */
    public final void addStackedMob(final UUID entityUuid, final StackedMob stackedMob) {
        this.stackedMobs.putIfAbsent(entityUuid, stackedMob);
    }

    public final void replaceStackedMob(final UUID oldUuid, final UUID newUuid, final StackedMob data) {
        this.stackedMobs.remove(oldUuid);
        this.stackedMobs.putIfAbsent(newUuid, data);
    }

    /**
     * Gets a list of StackedMob's at a Location
     * @param entityUuid EntityUUID to use as search key
     * @return List of StackedMob's
     */
    public final StackedMob getStackedMob(final UUID entityUuid) {
        return this.stackedMobs.get(entityUuid);
    }

    /**
     * Adds an amount to a StackedMob, however since multiple StackedMobs may exists
     * at one Location, you need to specifiy an EntityType to use as a "secondary" key
     * @param entityUuid EntityUUID of the StackedMob
     * @param amount integer amount to add to stack
     */
    public final void addToStack(final UUID entityUuid, final int amount) {
        final StackedMob mob = this.getStackedMob(entityUuid);
        mob.jsonData.amount += amount;
        mob.reloadHologram();
    }

    public final List<StackedMob> getMobsInRadius(final Location location, final int radius) {
        final List<StackedMob> mobsFound = new ArrayList<>();
        Bukkit.getLogger().info("Getting mobs in radius. . .");
        location.getNearbyEntities(radius, radius, radius).forEach((entity) -> {
            if (entity.getType() == EntityType.PLAYER) return;
            if (entity.getType() == EntityType.DROPPED_ITEM) return;
            if (entity.getType() == EntityType.ARMOR_STAND) return;
            final StackedMob mob = this.getStackedMob(entity.getUniqueId());
            if (mob != null) mobsFound.add(mob);
        });
        return mobsFound;
    }

    @Override
    public void load() {
        this.stackedMobs = new HashMap<>();
        this.storageManager = new MobStorageManager(this);
        this.storageManager.load();
        this.mobsSpawnEvents = new MobsSpawnEvents(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.mobsSpawnEvents, this.plugin);

        this.mergeTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getLogger().info(convert("&aRunning merge task. . ."));
                Bukkit.getWorlds().forEach((world) -> {
                    final MobStorage storage = storageManager.getNewMobStorageFile(world);

                });
            }
        }.runTaskTimerAsynchronously(this.plugin, 0, (20 * 60) * 1); // Ignore warning ikik
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this.mobsSpawnEvents);
        this.mergeTask.cancel();
        this.storageManager.stop();
    }

    public final CloudStacker getPlugin() {
        return this.plugin;
    }
}
