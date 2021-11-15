package com.burchard36.managers.mobs.events;

import com.burchard36.managers.mobs.MobManager;
import com.burchard36.managers.mobs.data.JsonMobData;
import com.burchard36.managers.mobs.lib.StackedMob;
import com.burchard36.managers.spawners.lib.StackedSpawner;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.util.List;

import static com.burchard36.ApiLib.convert;

public class MobsSpawnEvents implements Listener {

    public final MobManager manager;

    public MobsSpawnEvents(final MobManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onEntitySpawn(final SpawnerSpawnEvent event) {
        final CreatureSpawner creatureSpawner = event.getSpawner();
        final Entity spawnedEntity = event.getEntity();
        if (spawnedEntity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER ||
            spawnedEntity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL ||
            spawnedEntity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG ||
            spawnedEntity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.BEEHIVE ||
            spawnedEntity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.DISPENSE_EGG) {

            final StackedSpawner spawner = this.manager.getPlugin().getManagerPackage().
                    getSpawnerManager().getStackedSpawner(creatureSpawner.getLocation());
            int spawnAmount = 1;
            if (spawner != null) {
                // TODO: This will be more advanced promise
                spawnAmount = spawner.spawnerData.spawnerLevel * spawner.spawnerData.spawnerAmount;
            }

            final List<StackedMob> inRadius = this.manager.getMobsInRadius(spawnedEntity.getLocation(), 10);
            if (!inRadius.isEmpty()) {
                for (final StackedMob mob : inRadius) {
                    if (spawnedEntity.getType() == mob.jsonData.getType()) {
                        this.manager.addToStack(mob.getEntityUuid(), spawnAmount);
                    }
                }
            } else {
                final Location mobLoc = spawnedEntity.getLocation();
                final StackedMob mob = new StackedMob(mobLoc, new JsonMobData(
                        spawnedEntity.getType().name(),
                        mobLoc.getWorld().getName(),
                        spawnAmount,
                        mobLoc.getBlockX(),
                        mobLoc.getBlockY(),
                        mobLoc.getBlockZ()
                )).loadStackedMob(this.manager);
                this.manager.addStackedMob(mob.getEntityUuid(), mob);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final Entity killedEntity = event.getEntity();
        final StackedMob possibleStackedMob = this.manager.getStackedMob(killedEntity.getUniqueId());

        if (possibleStackedMob != null) {
            possibleStackedMob.jsonData.amount -= 1;
            possibleStackedMob.reloadStackedMob();
        }
    }

}
