package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.util.Formatting;
import org.vined.vaddon.VAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;
import org.vined.vaddon.utils.Utils;

public class StayLocked extends Module {
    public double currentX = 0;
    public double currentY = 0;
    public double currentZ = 0;

    public StayLocked() {
        super(VAddon.CATEGORY, "stay-locked", "Locks you into your current position.");
    }

    @Override
    public void onActivate() {
        currentX = getX();
        currentY = getY();
        currentZ = getZ();
        String coords = Utils.round(currentX, 1) + " " + Utils.round(currentY, 1) + " " + Utils.round(currentZ, 1);
        info("You are now locked at " + Formatting.WHITE + coords);
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
