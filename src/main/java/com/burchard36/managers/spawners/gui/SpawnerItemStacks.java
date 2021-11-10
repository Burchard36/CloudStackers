package com.burchard36.managers.spawners.gui;

import com.burchard36.inventory.ClickableItem;
import com.burchard36.managers.Manager;
import com.burchard36.managers.spawners.SpawnerManager;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnerItemStacks implements Manager {

    private final SpawnerManager manager;
    private HashMap<EntityType, ClickableItem> items;

    public SpawnerItemStacks(final SpawnerManager manager) {
        this.manager = manager;
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
}
