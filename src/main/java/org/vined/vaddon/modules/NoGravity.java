package org.vined.vaddon.modules;

import org.vined.vaddon.VAddon;
import meteordevelopment.meteorclient.systems.modules.Module;

public class NoGravity extends Module {
    public NoGravity() {
        super(VAddon.CATEGORY, "no-gravity", "Replicates suffocating to death!");
    }

    @Override
    public void onActivate() {
        assert mc.player != null;
        mc.player.setNoGravity(true);

        info("You are now in no gravity.");
    }

    @Override
    public void onDeactivate() {
        assert mc.player != null;
        mc.player.setNoGravity(false);

        info("You are now on the Earth.");
    }
}
