package com.burchard36.managers.commands.commands;

import com.burchard36.CloudStacker;
import com.burchard36.command.ApiCommand;
import com.burchard36.inventory.PluginInventory;
import com.burchard36.managers.spawners.SpawnerManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnerCommand {

    private final CloudStacker plugin;
    private final ApiCommand command;

    public SpawnerCommand(final CloudStacker plugin) {
        this.plugin = plugin;
        final SpawnerManager spawnerManager = this.plugin.getManagerPackage().getSpawnerManager();
        final List<String> aliases = new ArrayList<>();
        aliases.add("spawners");
        this.command = new ApiCommand(
                "spawner",
                "Command to give spawners",
                "/spawner", aliases)
                .onPlayerSender((playerSent) -> {
                    final PluginInventory pluginInventory = new PluginInventory();
                    final Player sender = playerSent.getSendingPlayer();
                    if (!sender.hasPermission("cloud.commands.spawners")) {
                        sender.sendMessage();
                    }
                    spawnerManager.getSpawnerCommandGui().showTo(sender);
                });

        this.register();
    }

    private void register() {
        this.plugin.getLib().registerCommand(this.command);
    }
}
