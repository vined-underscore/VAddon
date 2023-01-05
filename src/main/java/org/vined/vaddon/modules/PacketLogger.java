package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.systems.modules.Module;

import net.minecraft.network.Packet;
import net.minecraft.util.Formatting;

import org.vined.vaddon.VAddon;


public class PacketLogger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> c2sPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("C2S-packets")
        .description("Logs C2S packets (The packets you send to the server)")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> s2cPackets = sgGeneral.add(new BoolSetting.Builder()
        .name("S2C-packets")
        .description("Logs S2C packets (The packets you receive from the server)")
        .defaultValue(false)
        .build()
    );

    public PacketLogger() {
        super(VAddon.CATEGORY, "packet-logger", "Logs every packet you send or receive from the server.");
    }

    @EventHandler
    private void onPacketSent(PacketEvent.Sent event) {
        if (c2sPackets.get()) {
            Packet packet = event.packet;
            Class packetClass = packet.getClass();
            String packetName = packetClass.getCanonicalName();
            info(Formatting.WHITE + packetName);
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (s2cPackets.get()) {
            Packet packet = event.packet;
            Class packetClass = packet.getClass();
            String packetName = packetClass.getCanonicalName();
            info(Formatting.WHITE + packetName);
        }
    }
}
