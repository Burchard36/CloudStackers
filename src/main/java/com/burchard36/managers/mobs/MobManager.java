package com.burchard36.managers.mobs;

import com.burchard36.CloudStacker;
import com.burchard36.managers.Manager;
import com.burchard36.managers.ManagerPackage;
import com.burchard36.managers.mobs.events.MobsSpawnEvents;
import com.burchard36.managers.mobs.lib.StackedMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MobManager implements Manager {

    private final ManagerPackage managerPackage;
    private final CloudStacker plugin;
    private MobsSpawnEvents mobsSpawnEvents;

    /* Since multiple Entities may exist at one Location, we need to
     * Have a map of Location's that contain a List of StackedMobs!
     */
    private HashMap<UUID, List<StackedMob>> stackedMobs;

    public MobManager(final ManagerPackage managerPackage) {
        this.managerPackage = managerPackage;
        this.plugin = this.managerPackage.getPlugin();
    }

    /**
     * Adds a singular StackedMob to a Location in the HashMap
     * @param entityUuid EntityUUID to use as Key
     * @param stackedMob StackedMob to add to Cache
     */
    public final void addStackedMob(final UUID entityUuid, final StackedMob stackedMob) {
        List<StackedMob> search = this.getStackedMobs(entityUuid);
        if (search == null) search = new ArrayList<>();
        search.add(stackedMob.loadStackedMob(this));
        this.stackedMobs.put(entityUuid, search);
    }

    /**
     * Adds a list of StackedMob's to a Location in the HashMap
     * @param entityUuid EntityUUID to use as Key
     * @param stackedMobs List of StackedMob's to use
     */
    public final void addStackedMobs(final UUID entityUuid, final List<StackedMob> stackedMobs) {
        List<StackedMob> search = this.getStackedMobs(entityUuid);
        if (search == null) search = new ArrayList<>();
        search.addAll(stackedMobs);
        this.stackedMobs.put(entityUuid, search);
    }

    /**
     * Gets a list of StackedMob's at a Location
     * @param entityUuid EntityUUID to use as search key
     * @return List of StackedMob's
     */
    public final List<StackedMob> getStackedMobs(final UUID entityUuid) {
        return this.stackedMobs.get(entityUuid);
    }

    /**
     * Adds an amount to a StackedMob, however since multiple StackedMobs may exists
     * at one Location, you need to specifiy an EntityType to use as a "secondary" key
     * @param entityUuid EntityUUID of the StackedMob
     * @param type Type of Entity to use
     * @param amount integer amount to add to stack
     */
    public final void addToStack(final UUID entityUuid, final EntityType type, final int amount) {
        final boolean[] added = {false}; // TODO: Temporary, may be bugged? WHo knowsssss, maybe we see one day, maybe wont, oooo im a mysterious bug that may cause you hours of struggle one day oooo
        this.getStackedMobs(entityUuid).forEach((mob) -> {
            if (mob.jsonData.getType() == type && !added[0]) {
                mob.jsonData.amount += amount;
                added[0] = true;
            }
        });
    }

    public final List<StackedMob> getMobsInRadius(final Location location, final int radius) {
        final List<StackedMob> mobsFound = new ArrayList<>();
        location.getNearbyEntities(radius, radius, radius).forEach((entity) -> {
            final List<StackedMob> mobs = this.getStackedMobs(entity.getUniqueId());
            if (mobs != null) mobsFound.addAll(mobs);
        });
        return mobsFound;
    }

    @Override
    public void load() {
        this.stackedMobs = new HashMap<>();
        this.mobsSpawnEvents = new MobsSpawnEvents(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.mobsSpawnEvents, this.plugin);
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(this.mobsSpawnEvents);
    }

    public final CloudStacker getPlugin() {
        return this.plugin;
    }
}
