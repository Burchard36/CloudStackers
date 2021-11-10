package com.burchard36.managers.spawners.config.formats.spawner;

import com.squareup.moshi.Json;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class SpawnerItem {

    @Json(name = "item_name")
    private String itemName;
    @Json(name = "item_lore")
    private List<String> itemLore;
    @Json(name = "entity_type")
    private String entityType;

    public SpawnerItem() {
        this.itemName = "&b&l{spawner_name}";
        final List<String> lore = new ArrayList<>();
        lore.add("&f &r");
        lore.add("&7&oSpawns a &6{spawner_name}");
        this.itemLore = lore;
        this.entityType = EntityType.ZOMBIE.name();
    }

    public SpawnerItem(final String name, final List<String> lore, final EntityType type) {
        this.itemName = name;
        this.itemLore = lore;
        this.entityType = type.name();
    }

    public final String getName() {
        return this.itemName;
    }

    public final List<String> getLore() {
        return this.itemLore;
    }

    public final EntityType getType() {
        return EntityType.valueOf(this.entityType);
    }

    public final void setItemName(final String name) {
        this.itemName = name;
    }

    public final void setItemLore(final List<String> lore) {
        this.itemLore = lore;
    }

    public final void setEntityType(final EntityType type) {
        this.entityType = type.name();
    }

}
