package org.vined.vaddon.modules;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.vined.vaddon.VAddon;

public class MultiSpinner extends Module {

    private final SettingGroup sgModeSpeeds = settings.createGroup("Mode Speed");
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
        .description("Makes everyones head go uuuup and dooown.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> bodyYawMode = sgModes.add(new BoolSetting.Builder()
        .name("body-mode")
        .description("Broken for some reason")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> headYawMode = sgModes.add(new BoolSetting.Builder()
        .name("head-mode")
        .description("Makes everyones head spiiin.")
        .defaultValue(false)
        .build()
    );

    public MultiSpinner() {
        super(VAddon.CATEGORY, "multi-spinner", "Makes everyone spin. (Obviously client side)");
    }

    @Override
    public void onActivate() {
        info("Everyone is now a fidget spinner.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        assert mc.world != null;
        for (Entity entity : mc.world.getEntities()) {
            float currentYaw = entity.getYaw();
            float currentPitch = entity.getPitch();
            float currentBodyYaw = entity.getBodyYaw();
            float currentHeadYaw = entity.getHeadYaw();
            EntityType<?> type = entity.getType();

            if (yawMode.get()) {
                if (currentYaw == 180) {
                    currentYaw = -180;
                }

                entity.setYaw(currentYaw + yawSpeed.get());
            }
            if (bodyYawMode.get()) {
                if (currentBodyYaw == 180) {
                    currentBodyYaw = -180;
                }

                entity.setBodyYaw(currentBodyYaw + bodyYawSpeed.get());
            }
            if (headYawMode.get()) {
                if (currentHeadYaw == 180) {
                    currentHeadYaw = -180;
                }

                entity.setHeadYaw(currentHeadYaw + headYawSpeed.get());
            }
            if (pitchMode.get()) {
                if (currentPitch == 90) {
                    currentPitch = -90;
                }

                entity.setPitch(currentPitch + pitchSpeed.get());
            }
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
}
