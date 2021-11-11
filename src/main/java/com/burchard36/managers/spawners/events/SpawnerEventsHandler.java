package com.burchard36.managers.spawners.events;

import com.burchard36.CloudStacker;
import com.burchard36.inventory.ItemWrapper;
import com.burchard36.lib.spawners.StackedSpawner;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnerEventsHandler implements Listener {

    private final CloudStacker plugin;

    public SpawnerEventsHandler(final CloudStacker plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent placeEvent) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!(placeEvent.getBlock().getState() instanceof CreatureSpawner)) return;
                final CreatureSpawner spawner = (CreatureSpawner) placeEvent.getBlock().getState();
                final ItemStack itemInHand = placeEvent.getItemInHand();
                final ItemWrapper wrapper = new ItemWrapper(itemInHand);
                final String entityString = wrapper.getStringDataValue("entity_type");
                if (entityString == null) return;

                final EntityType inHandType = EntityType.valueOf(entityString);
                /* We need to wait a tick in order for the spawner data to be edited */

                spawner.setSpawnedType(inHandType);
                spawner.update(true);
            }
        }.runTaskLater(this.plugin, 1);
    }

}
