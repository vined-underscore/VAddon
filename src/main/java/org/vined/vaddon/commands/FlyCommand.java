package org.vined.vaddon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FlyCommand extends Command {
    public FlyCommand() {
        super("fly", "Makes you float to the clouds!");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            assert mc.player != null;
            PlayerAbilities abilities = mc.player.getAbilities();
            boolean isFlying = abilities.flying;
            if (isFlying) {
                abilities.flying = false;
                abilities.allowFlying = false;
                info("Set flying to " + Formatting.RED + "false");
            } else {
                abilities.flying = true;
                abilities.allowFlying = true;
                info("Set flying to " + Formatting.GREEN + "true");
            }
            return SINGLE_SUCCESS;
        });
    }
}
