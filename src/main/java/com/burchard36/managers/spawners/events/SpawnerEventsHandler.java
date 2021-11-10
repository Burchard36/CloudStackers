package com.burchard36.managers.spawners.events;

import com.burchard36.CloudStacker;
import com.burchard36.lib.spawners.StackedSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerEventsHandler implements Listener {

    public SpawnerEventsHandler(final CloudStacker plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent placeEvent) {
        if (!(placeEvent.getBlock() instanceof CreatureSpawner)) return;
        final CreatureSpawner spawner = (CreatureSpawner) placeEvent.getBlock();

        final EntityType spawnerType = spawner.getSpawnedType();
        final Location spawnerLocation = placeEvent.getBlock().getLocation();

        final StackedSpawner stackedSpawner = new StackedSpawner(spawnerLocation, spawnerType, 1);

        final Player player = placeEvent.getPlayer();
        final ItemStack itemInHand = placeEvent.getItemInHand();
        int amount = 1;
        if (player.isSneaking()) {

        } else {

        }
    }

}
