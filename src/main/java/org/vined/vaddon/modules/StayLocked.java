package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import org.vined.vaddon.VAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;

import java.util.Objects;

public class StayLocked extends Module {
    public StayLocked() {
        super(VAddon.CATEGORY, "stay-locked", "Locks you into your current position.");
    }

    public double currentX = 0;
    public double currentY = 0;
    public double currentZ = 0;

    @Override
    public void onActivate() {
        currentX = getX();
        currentY = getY();
        currentZ = getZ();

        info("You are now locked where you are.");
    }

    @Override
    public void onDeactivate() {
        info("You are now free.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        assert mc.player != null;
        mc.player.setPosition(currentX, currentY, currentZ);
        Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(currentX, currentY, currentZ, mc.player.isOnGround()));
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

    // Taken from the Freecam module in Meteor
    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event)  {
        if (event.packet instanceof DeathMessageS2CPacket packet) {
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
