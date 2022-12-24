package org.vined.vaddon.commands;

import meteordevelopment.meteorclient.utils.world.TickRate;
import meteordevelopment.meteorclient.systems.commands.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;


import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class TPSCommand extends Command {
    public TPSCommand() {
        super("tps", "Shows the servers TPS");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            // This is literally taken from the server command from meteor
            float tps = TickRate.INSTANCE.getTickRate();
            Formatting color;
            if (tps > 17.0f) {
                color = Formatting.GREEN;
            } else if (tps > 12.0f) {
                color = Formatting.YELLOW;
            } else {
                color = Formatting.RED;
            }

            info("The current server TPS is %s%.2f(default).", color, tps);
            return SINGLE_SUCCESS;
        });
    }
}
