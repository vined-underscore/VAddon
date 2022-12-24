package org.vined.vaddon.modules;

import org.vined.vaddon.utils.Utils;
import org.vined.vaddon.VAddon;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.util.Formatting;

public class StayLocked extends Module {
    public double currentX = 0;
    public double currentY = 0;
    public double currentZ = 0;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> showCoords = sgGeneral.add(new BoolSetting.Builder()
        .name("show-coordinates")
        .description("If to show your coordinates when enabling the module.")
        .defaultValue(false)
        .build()
    );

    public StayLocked() {
        super(VAddon.CATEGORY, "stay-locked", "Locks you into your current position.");
    }

    @Override
    public void onActivate() {
        currentX = getX();
        currentY = getY();
        currentZ = getZ();

        if (showCoords.get()) {
            String coords = Utils.round(currentX, 1) + " " + Utils.round(currentY, 1) + " " + Utils.round(currentZ, 1);
            info("You are now locked at " + Formatting.WHITE + coords);
        } else {
            info("You are now locked into position.");
        }
    }

    @Override
    public void onDeactivate() {
        info("You are now unlocked.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        assert mc.player != null;
        mc.player.setPosition(currentX, currentY, currentZ);
        mc.player.setPos(currentX, currentY, currentZ);
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        toggle();
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent event) {
        if (event.screen instanceof DisconnectedScreen) {
            toggle();
        }
    }

    // Taken from the Free-cam module in Meteor
    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event)  {
        if (event.packet instanceof DeathMessageS2CPacket packet) {
            assert mc.world != null;
            Entity entity = mc.world.getEntityById(packet.getEntityId());
            if (entity == mc.player) {
                toggle();
            }
        }
    }

    private double getX() {
        assert mc.player != null;
        return mc.player.getX();
    }

    private double getY() {
        assert mc.player != null;
        return mc.player.getY();
    }

    private double getZ() {
        assert mc.player != null;
        return mc.player.getZ();
    }
}
