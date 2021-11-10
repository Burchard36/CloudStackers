package com.burchard36.managers.spawners.gui.guis;

import com.burchard36.CloudStacker;
import com.burchard36.inventory.ClickableItem;
import com.burchard36.inventory.ItemWrapper;
import com.burchard36.inventory.PluginInventory;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.SpawnerManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dalton Burchard
 *
 * This class contains 2 class that both handle one thing:
 *
 * SpawnerCommandGui:
 *
 *  - This class actually handles putting items into the GUI, and handling the click events!
 */
public class SpawnerCommandGui implements Manager{

    private final SpawnerManager manager;
    private final PluginInventory inventory;
    //private final SpawnerItemStacks itemStacks;

    public SpawnerCommandGui(final SpawnerManager spawnerManager) {
        this.manager = spawnerManager;
        this.inventory = new PluginInventory(9, "&b&lSpawners");
        //this.itemStacks = new SpawnerItemStacks(this.manager);
    }

    /**
     * Shows the inventory to a Player
     * @param player Player to show the PluginInventory to
     */
    public final void showTo(final Player player) {
        this.inventory.open(player);
    }

    @Override
    public void load() {
        final List<ClickableItem> spawners = new ArrayList<>();
        this.manager.getSpawnerConfig().spawnerSettings.getSpawnerItemList().forEach((spawnerItem) -> {
            final ItemWrapper wrapper = new ItemWrapper(new ItemStack(Material.SPAWNER, 1));
            wrapper.setDisplayName(spawnerItem.getName())
                    .setItemLore(spawnerItem.getLore())
                    .addDataString("spawner_type", spawnerItem.getType().name());
            //this.itemStacks.addItem(spawnerItem.getType(), wrapper);
            spawners.add(new ClickableItem(wrapper)
                    .onClick((clickAction) -> {
                        final Player clicker = (Player) clickAction.getWhoClicked();
                        if (clicker.hasPermission("spawners.give.self")) {

                            final ItemStack clickedItem = clickAction.getCurrentItem();
                            if (clickedItem == null) return;

                            if (clickAction.isShiftClick() && clickAction.isLeftClick()) {
                                clickedItem.setAmount(64);
                            } else if (clickAction.isShiftClick() && clickAction.isRightClick()) {
                                clickedItem.setAmount(32);
                            } else if (clickAction.isRightClick()) {
                                clickedItem.setAmount(16);
                            }

                            clicker.getInventory().addItem(clickedItem);
                        }
                    }));
        });

        this.inventory.addClickableItems(spawners)
                .onClick((guiAction) -> {
                    guiAction.setCancelled(true);
                })
                .register(manager.getPlugin());
    }

    @Override
    public void reload() {
        this.stop();
        this.load();
    }

    @Override
    public void stop() {

    }


    /**
     * @author Dalton Burchard
     *
     * This class handles loading ClickableItems for the SpawnerCommandGui class
     * It will also handle getting the actual ItemStack's when giving a Spawner to a player
     *
    private static class SpawnerItemStacks implements Manager {

        private final SpawnerManager manager;
        private HashMap<EntityType, ItemWrapper> items;

        public SpawnerItemStacks(final SpawnerManager manager) {
            this.manager = manager;
        }

        public final void addItem(final EntityType type, final ItemWrapper wrapper) {
            this.items.putIfAbsent(type, wrapper);
        }

        @Override
        public void load() {
            this.items = new HashMap<>();
        }

        @Override
        public void reload() {

        }

        @Override
        public void stop() {

        }
    }*/

}
