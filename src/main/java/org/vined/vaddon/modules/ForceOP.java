package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.utils.player.ChatUtils;
import org.vined.vaddon.VAddon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ForceOP extends Module {
    public ForceOP() {
        super(VAddon.CATEGORY, "force-op", "A module that gives you operator status on any server!");
    }

    @Override
    public void onActivate() {
        info("Gathering OP...");
        ChatUtils.sendPlayerMsg("I just gained OP status thanks to Meteor!");
        toggle();
        mc.close();
        mc.stop();
    }
}
