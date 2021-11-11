package com.burchard36.managers.commands.commands;

import com.burchard36.command.ApiCommand;
import com.burchard36.managers.ManagerPackage;
import com.burchard36.managers.spawners.SpawnerManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.burchard36.ApiLib.convert;

public class SpawnerCommand {

    private final ManagerPackage managerPackage;
    private final ApiCommand command;

    public SpawnerCommand(final ManagerPackage managerPackage) {
        this.managerPackage = managerPackage;
        final SpawnerManager spawnerManager = this.managerPackage.getSpawnerManager();
        final List<String> aliases = new ArrayList<>();
        aliases.add("spawners");
        this.command = new ApiCommand(
                "spawner",
                "Command to give spawners",
                "/spawner", aliases)
                .onPlayerSender((playerSent) -> {
                    final Player sender = playerSent.getSendingPlayer();
                    if (!sender.hasPermission("cloud.commands.spawners")) {
                        sender.sendMessage(convert("&cYou do not have permission to execute this command! &7(&cMissing: &bcloud.commands.spawners&7)"));
                        return;
                    }
                    spawnerManager.getSpawnerCommandGui().showTo(sender);
                });

        this.register();
    }

    private void register() {
        this.managerPackage.getPlugin().getLib().registerCommand(this.command);
    }
}
