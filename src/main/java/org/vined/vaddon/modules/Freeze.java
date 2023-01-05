package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;

import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.Packet;

import org.vined.vaddon.VAddon;

import java.util.ArrayList;
import java.util.List;


public class Freeze extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> cancelPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("cancel-packets")
        .description("Cancels all C2S packets.")
        .defaultValue(true)
        .build()
    );

    public final List<Packet> c2sPackets = new ArrayList<>();
    public Freeze() {
        super(VAddon.CATEGORY, "freeze", "Freezes everything. (May kick you if enabled for a long time)");
    }

    @Override
    public void onDeactivate() {
        if (!cancelPackets.get()) {
            assert mc.player != null;
            synchronized (c2sPackets) {
                c2sPackets.forEach(mc.player.networkHandler::sendPacket);
            }
        }
        c2sPackets.clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onPacketSend(PacketEvent.Send event) {
        event.cancel();
        if (!cancelPackets.get()) {
            Packet p = event.packet;
            synchronized (c2sPackets) {
                c2sPackets.add(p);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onPacketReceive(PacketEvent.Receive event) {
        event.cancel();
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
}
