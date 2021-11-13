package com.burchard36.managers.mobs.lib;

import com.burchard36.EntityWrapper;
import com.burchard36.managers.mobs.MobManager;
import com.burchard36.managers.mobs.data.JsonMobData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.UUID;

import static com.burchard36.ApiLib.convert;

public class StackedMob {

    private EntityType entityType;
    public JsonMobData jsonData;
    private Location location;
    private MobManager manager;

    private EntityWrapper wrapper;

    public StackedMob(final Location location,
                      final JsonMobData jsonData) {
        this.location = location;
        this.jsonData = jsonData;
        this.entityType = this.jsonData.getType();
    }

    public StackedMob loadStackedMob(final MobManager manager) {
        this.manager = manager;

        final World world = this.location.getWorld();
        final Entity entity = world.spawnEntity(this.location, this.entityType);
        this.wrapper = new EntityWrapper(entity);

        this.wrapper.setHologram(convert("&7x&b" + this.jsonData.amount + " &e" + entity.getType().name().toLowerCase().substring(0, 1).toUpperCase() +
                entity.getType().name().toLowerCase().substring(1)));
        this.wrapper.setIntegerValue("stacked_amount", this.jsonData.amount);

        return this;
    }

    public void reloadStackedMob() {
        final Entity entity = this.wrapper.getEntity();
        this.wrapper.setHologram(convert("&7x&b" + this.jsonData.amount + " &e" + entity.getType().name().toLowerCase().substring(0, 1).toUpperCase() +
                entity.getType().name().toLowerCase().substring(1)));
        this.wrapper.setIntegerValue("stacked_amount", this.jsonData.amount);
    }

    public final UUID getEntityUuid() {
        return this.wrapper.getEntity().getUniqueId();
    }


}
