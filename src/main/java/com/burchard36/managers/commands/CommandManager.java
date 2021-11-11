package com.burchard36.managers.commands;

import com.burchard36.CloudStacker;
import com.burchard36.managers.Manager;
import com.burchard36.managers.ManagerPackage;
import com.burchard36.managers.commands.commands.SpawnerCommand;

public class CommandManager implements Manager {

    private final ManagerPackage managerPackage;

    private SpawnerCommand spawnerCommand;

    public CommandManager(final ManagerPackage managerPackage) {
        this.managerPackage = managerPackage;
    }

    @Override
    public void load() {
        this.spawnerCommand = new SpawnerCommand(this.managerPackage);
    }

    @Override
    public void reload() {

    }

    @Override
    public void stop() {

    }
}
