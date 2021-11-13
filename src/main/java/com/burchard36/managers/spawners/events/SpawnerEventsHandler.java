package com.burchard36.managers.spawners.events;

import com.burchard36.CloudStacker;
import com.burchard36.inventory.ItemWrapper;
import com.burchard36.managers.spawners.SpawnerManager;
import com.burchard36.managers.spawners.data.JsonSpawnerData;
import com.burchard36.managers.spawners.lib.StackedSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.burchard36.CloudStacker.getNearbyBlocks;

public class SpawnerEventsHandler implements Listener {

    private final SpawnerManager manager;
    private final CloudStacker plugin;

    public SpawnerEventsHandler(final SpawnerManager manager) {
        this.manager = manager;
        this.plugin = this.manager.getPlugin();
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent placeEvent) {
        if (!(placeEvent.getBlock().getState() instanceof CreatureSpawner)) return;
        final Block placedBlocked = placeEvent.getBlockPlaced();
        final CreatureSpawner spawner = (CreatureSpawner) placeEvent.getBlock().getState();
        final ItemStack itemInHand = placeEvent.getItemInHand();
        final ItemWrapper wrapper = new ItemWrapper(itemInHand);
        final String entityString = wrapper.getStringDataValue("entity_type");
        if (entityString == null) return;

        final EntityType inHandType = EntityType.valueOf(entityString);
        /* We need to wait a tick in order for the spawner data to be edited */
        boolean wasMerged = false;

        for (final Block block : getNearbyBlocks(placedBlocked.getLocation(), 3)) {
            if (block.getType() == Material.SPAWNER) {
                final CreatureSpawner possibleAnchor = (CreatureSpawner) block.getState();
                if (possibleAnchor.getSpawnedType() == inHandType) {
                    manager.getStackedSpawner(possibleAnchor.getLocation()).spawnerData.spawnerAmount += 1;
                    manager.getStackedSpawner(possibleAnchor.getLocation()).reloadHologram();
                    wasMerged = true;
                }
            }
        }

        if (wasMerged) {
            placedBlocked.setType(Material.AIR);
            return;
        }


        spawner.setSpawnedType(inHandType);
        spawner.update(true);

        final Location loc = spawner.getLocation();
        manager.addStackedSpawner(loc,
                new StackedSpawner(spawner,
                        new JsonSpawnerData(
                                loc.getBlockX(),
                                loc.getBlockY(),
                                loc.getBlockZ(),
                                1,
                                1,
                                loc.getWorld().getName(),
                                inHandType.name().toLowerCase().substring(0, 1).toUpperCase() + entityString.substring(1)
                        )).loadHologram(manager));

    }
}
