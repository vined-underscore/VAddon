package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import org.vined.vaddon.VAddon;

public class FidgetSpinner extends Module {

    private final SettingGroup sgModeSpeeds = settings.createGroup("Modes Speed");
    private final SettingGroup sgModes = settings.createGroup("Spin Modes");

    private final Setting<Integer> pitchSpeed = sgModeSpeeds.add(new IntSetting.Builder()
        .name("pitch-speed")
        .description("Pitch mode's speed.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .build()
    );
    private final Setting<Integer> yawSpeed = sgModeSpeeds.add(new IntSetting.Builder()
        .name("yaw-speed")
        .description("Yaw mode's speed.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .build()
    );
    private final Setting<Integer> bodyYawSpeed = sgModeSpeeds.add(new IntSetting.Builder()
        .name("body-speed")
        .description("Body mode's speed.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .build()
    );
    private final Setting<Integer> headYawSpeed = sgModeSpeeds.add(new IntSetting.Builder()
        .name("head-speed")
        .description("Head mode's speed.")
        .defaultValue(1)
        .min(1)
        .sliderMax(100)
        .build()
    );

    private final Setting<Boolean> yawMode = sgModes.add(new BoolSetting.Builder()
        .name("yaw-mode")
        .description("The normal mode.")
        .defaultValue(true)
        .build()
    );
    private final Setting<Boolean> pitchMode = sgModes.add(new BoolSetting.Builder()
        .name("pitch-mode")
        .description("Makes your head go uuuup and dooown.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> bodyYawMode = sgModes.add(new BoolSetting.Builder()
        .name("body-mode")
        .description("Makes your body spiiin. (Client Side)")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> headYawMode = sgModes.add(new BoolSetting.Builder()
        .name("head-mode")
        .description("Makes your head spiiin. (Client Side)")
        .defaultValue(false)
        .build()
    );

    public FidgetSpinner() {
        super(VAddon.CATEGORY, "fidget-spinner", "Makes you a fidget spinner.");
    }

    public float currentYaw = 0;
    public float currentPitch = 0;
    public float currentBodyYaw = 0;
    public float currentHeadYaw = 0;

    @Override
    public void onActivate() {
        currentYaw = getYaw();
        currentPitch = getPitch();
        currentBodyYaw = getBodyYaw();
        currentHeadYaw = getHeadYaw();
        info("You are now a fidget spinner.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        assert mc.player != null;
        if (yawMode.get()) {
            if (currentYaw == 180) {
                currentYaw = -180;
            }

            mc.player.setYaw(currentYaw += yawSpeed.get());
        }
        if (bodyYawMode.get()) {
            if (currentBodyYaw == 180) {
                currentBodyYaw = -180;
            }

            mc.player.setBodyYaw(currentBodyYaw += bodyYawSpeed.get());
        }
        if (headYawMode.get()) {
            if (currentHeadYaw == 180) {
                currentHeadYaw = -180;
            }

            mc.player.setHeadYaw(currentHeadYaw += headYawSpeed.get());
        }
        if (pitchMode.get()) {
            if (currentPitch == 90) {
                currentPitch = -90;
            }

            mc.player.setPitch(currentPitch += pitchSpeed.get());
        }
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

    private float getYaw() {
        assert mc.player != null;
        return mc.player.getYaw();
    }
    private float getPitch() {
        assert mc.player != null;
        return mc.player.getPitch();
    }
    private float getBodyYaw() {
        assert mc.player != null;
        return mc.player.getBodyYaw();
    }
    private float getHeadYaw() {
        assert mc.player != null;
        return mc.player.getHeadYaw();
    }
}
