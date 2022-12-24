package org.vined.vaddon.commands;

import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.commands.arguments.PlayerListEntryArgumentType;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.Utils;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class PingCommand extends Command {
    public PingCommand() {
        super("ping", "Shows your ping or, if specified, someone elses ping.", "lat", "latency");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            info("Your ping is " + Formatting.AQUA + PlayerUtils.getPing() + "ms");

            return SINGLE_SUCCESS;
        });

        builder.then(argument("player", PlayerListEntryArgumentType.create()).executes(context -> {
            PlayerListEntry entry = PlayerListEntryArgumentType.get(context);
            int latency = Utils.clamp(entry.getLatency(), 0, 9999);
            String name = entry.getProfile().getName();

            info( name + Formatting.GRAY + "'s ping is " + Formatting.AQUA + latency + "ms");

            return SINGLE_SUCCESS;
        }));
    }
}
