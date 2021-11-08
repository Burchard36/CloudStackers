package com.burchard36;

import com.burchard36.json.PluginDataManager;
import com.burchard36.managers.ManagerPackage;
import org.bukkit.plugin.java.JavaPlugin;

public final class CloudStacker extends JavaPlugin implements Api {

    private ApiLib lib;
    private ManagerPackage managerPackage;

    @Override
    public void onEnable() {
        this.lib = new ApiLib().initializeApi(this);
        this.managerPackage = new ManagerPackage(this);
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
}