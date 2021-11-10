package com.burchard36.managers;

import com.burchard36.CloudStacker;
import com.burchard36.managers.commands.CommandManager;
import com.burchard36.managers.items.ItemManager;
import com.burchard36.managers.mobs.MobManager;
import com.burchard36.managers.players.PlayerManager;
import com.burchard36.managers.spawners.SpawnerManager;

/**
 * @author Dalton Burchard
 * This class is designed to me used for packaging all of the "Managers" in one file to
 * easier manage & access them during runtime! It allows easy reloadability to be added
 * to the plugin and is completely modular!
 */
public class ManagerPackage {

    private final SpawnerManager spawnerManager;
    private final MobManager mobManager;
    private final ItemManager itemManager;
    private final PlayerManager playerManager;
    private final CommandManager commandManager;

    public ManagerPackage(final CloudStacker plugin) {
        this.spawnerManager = new SpawnerManager(plugin);
        this.mobManager = new MobManager();
        this.itemManager = new ItemManager();
        this.playerManager = new PlayerManager(plugin);
        this.commandManager = new CommandManager(plugin);

        this.loadAll();
    }

    /**
     * Reloads all managers, may cause a slight lag!
     */
    public final void reloadAll() {
        this.unloadAll();
        this.loadAll();
    }

    /**
     * Stops all Managers, typically used when stopping the server
     */
    public final void unloadAll() {
        this.spawnerManager.stop();
        this.playerManager.stop();
        this.commandManager.stop();
    }

    /**
     * Starts-up all the managers
     */
    public final void loadAll() {
        this.spawnerManager.load();
        this.playerManager.load();
        this.commandManager.load();
    }

    /**
     * Gets the SpawnerManager instance
     * @return SpawnerManager instance
     */
    public final SpawnerManager getSpawnerManager() {
        return this.spawnerManager;
    }

    /**
     * Gets the MobManager instance
     * @return MobManager instance
     */
    public final MobManager getMobManager() {
        return this.mobManager;
    }

    /**
     * Gets the ItemManager instance
     * @return ItemManager instance
     */
    public final ItemManager getItemManager() {
        return this.itemManager;
    }

    /**
     * Gets the PlayerManager instance
     * @return PlayerManager instance
     */
    public final PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    /**
     * Gets the CommandManager instance
     * @return CommandManager instance
     */
    public final CommandManager getCommandManager() {
        return this.commandManager;
    }
}
