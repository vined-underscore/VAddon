// Code taken from https://github.com/HoseanRC/ifpeek-meteor-addon/blob/main/src/main/java/org/hoseanrc/ifpeek/commands/IfpeekCommand.java

package org.vined.vaddon.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ItemFramePeekCommand extends Command {
    private static final ItemStack[] ITEMS = new ItemStack[27];
    public ItemFramePeekCommand() {
        super("ipeek", "Shows the contents of a shulker inside an item frame. (Made by HoseanRC)", "itemframepeek");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(this::run);
    }

    private int run(CommandContext<CommandSource> context) {
        if (mc.targetedEntity == null || !(mc.targetedEntity instanceof ItemFrameEntity itemframe)) {
            info("You have to point at an item frame.");
            return SINGLE_SUCCESS;
        }

        ItemStack shulker = itemframe.getHeldItemStack();
        if (shulker == ItemStack.EMPTY) {
            info("There is no item in the item frame.");
            return SINGLE_SUCCESS;
        }

        if (!Utils.isShulker(shulker.getItem())) {
            info("The item isn't a shulker.");
            return SINGLE_SUCCESS;
        }

        if (!Utils.hasItems(shulker)) {
            info("The shulker is empty or the server isn't loading the contents of the shulker.");
            return SINGLE_SUCCESS;
        }

        Utils.openContainer(shulker, ITEMS, true);
        return SINGLE_SUCCESS;
    }
}
