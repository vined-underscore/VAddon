package org.vined.vaddon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.vined.vaddon.utils.Utils;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.commands.arguments.PlayerArgumentType;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class StatsCommand extends Command {
    public StatsCommand() {
        super("stats", "Shows yours or someone elses stats", "ps", "pstats");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            assert mc.player != null;
            String name = mc.player.getGameProfile().getName();
            String ping = PlayerUtils.getPing() + "ms";
            String UUID = mc.player.getUuidAsString();
            double health = Utils.round(mc.player.getHealth(), 0);
            double pitch = Utils.round(mc.player.getPitch(), 0);
            double yaw = Utils.round(mc.player.getYaw(), 0);
            double hunger = Utils.round(mc.player.getHungerManager().getFoodLevel(), 0);

            info("\n");
            info(Formatting.DARK_PURPLE + "--- " + Formatting.WHITE + name + "'s stats are:" + Formatting.DARK_PURPLE + " ---");
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "HP: " + Formatting.AQUA + health);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Hunger: " + Formatting.AQUA + hunger);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Pitch: " + Formatting.AQUA + pitch);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Yaw: " + Formatting.AQUA + yaw);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Ping: " + Formatting.AQUA + ping);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "UUID: " + Formatting.AQUA + UUID);

            return SINGLE_SUCCESS;
        });

        builder.then(argument("player", PlayerArgumentType.create()).executes(context -> {
            PlayerEntity player = PlayerArgumentType.get(context);
            String name = player.getGameProfile().getName();
            String ping = EntityUtils.getPing(player) + "ms";
            String UUID = player.getUuidAsString();
            double health = Utils.round(player.getHealth(), 0);
            double pitch = Utils.round(player.getPitch(), 0);
            double yaw = Utils.round(player.getYaw(), 0);
            double hunger = Utils.round(player.getHungerManager().getFoodLevel(), 0);

            info("\n");
            info(Formatting.DARK_PURPLE + "--- " + Formatting.WHITE + name + "'s stats are:" + Formatting.DARK_PURPLE + " ---");
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "HP: " + Formatting.AQUA + health);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Hunger: " + Formatting.AQUA + hunger);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Pitch: " + Formatting.AQUA + pitch);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Yaw: " + Formatting.AQUA + yaw);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "Ping: " + Formatting.AQUA + ping);
            info(Formatting.DARK_PURPLE + "- " + Formatting.WHITE + "UUID: " + Formatting.AQUA + UUID);

            return SINGLE_SUCCESS;
        }));
    }
}
