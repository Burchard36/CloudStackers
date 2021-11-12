package com.burchard36;

import com.burchard36.hologram.HologramManager;
import com.burchard36.json.PluginDataManager;
import com.burchard36.managers.Manager;
import com.burchard36.managers.ManagerPackage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ServiceLoader;

public final class CloudStacker extends JavaPlugin implements Api {

    private ApiLib lib;
    private ManagerPackage managerPackage;
    private HologramManager hologramManager;

    @Override
    public void onEnable() {
        this.lib = new ApiLib().initializeApi(this);
        this.managerPackage = new ManagerPackage(this);
        this.hologramManager = this.lib.getHologramManager();
    }

    @Override
    public void onDisable() {
        this.managerPackage.unloadAll();
    }

    /**
     * For this plugins own use, Do not use this method if your using
     * CloudStacker's api, use getApi() instead
     * @return ApiLib instance
     */
    public ApiLib getLib() {
        return this.lib;
    }

    @Override
    public PluginDataManager getPluginDataManager() {
        return this.lib.getPluginDataManager();
    }

    /**
     * Gets a instance of ManagerPackage, this has getters for all public managers to be used either externally
     * or internally, these are basically just API's
     * @return instance of ManagerPackage
     */
    public ManagerPackage getManagerPackage() {
        return this.managerPackage;
    }

    public HologramManager getHologramManager() {
        return this.hologramManager;
    }
}
