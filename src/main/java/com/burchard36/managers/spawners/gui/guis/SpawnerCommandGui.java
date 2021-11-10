package com.burchard36.managers.spawners.gui.guis;

import com.burchard36.CloudStacker;
import com.burchard36.inventory.ClickableItem;
import com.burchard36.inventory.ItemWrapper;
import com.burchard36.inventory.PluginInventory;
import com.burchard36.managers.spawners.SpawnerManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SpawnerCommandGui {

    private final SpawnerManager manager;
    private final PluginInventory inventory;

    public SpawnerCommandGui(final SpawnerManager manager) {
        this.manager = manager;
        this.inventory = new PluginInventory(9, "&b&lSpawners");

        final List<ClickableItem> spawners = new ArrayList<>();
        this.manager.getSpawnerConfig().spawnerSettings.getSpawnerItemList().forEach((spawnerItem) -> {
            final ItemWrapper wrapper = new ItemWrapper(new ItemStack(Material.SPAWNER, 1));
            wrapper.setDisplayName(spawnerItem.getName())
                    .setItemLore(spawnerItem.getLore())
                    .addDataString("spawner_type", spawnerItem.getType().name());
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

        this.inventory.add
    }



}
