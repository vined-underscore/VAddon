package org.vined.vaddon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.arguments.PlayerListEntryArgumentType;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.commands.arguments.PlayerArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class UUIDCommand extends Command {
    public UUIDCommand() {
        super("uuid", "Shows yours or someone elses Minecraft UUID", "id");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            assert mc.player != null;
            String UUID = mc.player.getUuid().toString();

            info(Formatting.WHITE + mc.player.getGameProfile().getName() + "'s " + "UUID: " + Formatting.AQUA + UUID);

            return SINGLE_SUCCESS;
        });

        builder.then(argument("player", PlayerListEntryArgumentType.create()).executes(context -> {
            PlayerListEntry player = PlayerListEntryArgumentType.get(context);
            String UUID = player.getProfile().getId().toString();

            info(Formatting.WHITE + player.getProfile().getName() + "'s " + "UUID: " + Formatting.AQUA + UUID);

            return SINGLE_SUCCESS;
        }));
    }
}
