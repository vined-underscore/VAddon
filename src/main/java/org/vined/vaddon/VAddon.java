package org.vined.vaddon;

import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;

import org.slf4j.Logger;
import org.vined.vaddon.commands.*;
import org.vined.vaddon.modules.*;

import com.mojang.logging.LogUtils;

public class VAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("VAddon");

    @Override
    public void onInitialize() {
        LOG.info("Initializing VAddon");

        // Commands
        Commands.get().add(new PingCommand());
        Commands.get().add(new ItemFramePeekCommand());
        Commands.get().add(new StatsCommand());
        Commands.get().add(new UUIDCommand());
        Commands.get().add(new TPSCommand());
        Commands.get().add(new FlyCommand());

        // Modules
        Modules.get().add(new ForceOP());
        Modules.get().add(new StayLocked());
        Modules.get().add(new FidgetSpinner());
        Modules.get().add(new MultiSpinner());
        Modules.get().add(new NoGravity());
    }


    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "org.vined.vaddon";
    }
}
