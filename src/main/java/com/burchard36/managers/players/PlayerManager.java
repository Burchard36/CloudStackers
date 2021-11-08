package com.burchard36.managers.players;

import com.burchard36.CloudStacker;
import com.burchard36.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager implements Manager, Listener {

    private final CloudStacker plugin;
    private HashMap<UUID, StackerPlayer> stackerPlayers;

    public PlayerManager(final CloudStacker plugin) {
        this.plugin = plugin;
        this.load();
    }

    @Override
    public void load() {
        this.stackerPlayers = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, this.plugin);

        Bukkit.getOnlinePlayers().forEach((player) -> {
            final UUID uuid = player.getUniqueId();
            if (this.stackerPlayers.get(uuid) == null) this.addStackerPlayer(player);
        });
    }

    @Override
    public void reload() {
        this.stop(); // Stop any running instances/clear maps
        this.load(); // Load those instances and maps back in
    }

    @Override
    public void stop() {
        this.stackerPlayers.clear();
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent joinEvent) {
        final UUID joiningUuid = joinEvent.getPlayer().getUniqueId();
        if (this.getStackerPlayer(joiningUuid) == null) this.addStackerPlayer(joiningUuid);
    }

    /**
     * Adds a stacker player to cache
     * This will not clear out an already existing player in the map
     * @param player Player to add to StackerMap
     */
    public final void addStackerPlayer(final Player player) {
        this.addStackerPlayer(player.getUniqueId());
    }

    /**
     * Adds a stacker player to cache
     * This will not clear out an already existing player in the map
     * @param uuid UUID of Player to add to StackerMap
     */
    public final void addStackerPlayer(final UUID uuid) {
        this.stackerPlayers.putIfAbsent(uuid, new StackerPlayer(uuid));
    }

    /**
     * Gets a stacker player from the map using the Player Object
     * @param uuid UUID of StackerPlayer to get
     * @return StackerPlayer from map
     */
    public final StackerPlayer getStackerPlayer(final UUID uuid) {
        return this.stackerPlayers.get(uuid);
    }

    /**
     * Gets a stacker player from the map using the Player Object
     * @param player Player to get from map
     * @return StackerPlayer from map
     */
    public final StackerPlayer getStackerPlayer(final Player player) {
        return this.getStackerPlayer(player.getUniqueId());
    }
}
