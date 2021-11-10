package com.burchard36.managers.commands;

import com.burchard36.CloudStacker;
import com.burchard36.managers.Manager;
import com.burchard36.managers.commands.commands.SpawnerCommand;

public class CommandManager implements Manager {

    private final CloudStacker plugin;

    private SpawnerCommand spawnerCommand;

    public CommandManager(final CloudStacker plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        this.spawnerCommand = new SpawnerCommand(this.plugin);
    }

    @Override
    public void reload() {

    }

    @Override
    public void stop() {

    }
}
