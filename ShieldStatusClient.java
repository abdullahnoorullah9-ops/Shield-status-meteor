package com.shieldstatus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;

public class ShieldStatusClient implements ClientModInitializer {
    public static final String MOD_ID = "shieldstatus";

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CROSSHAIR,
                Identifier.of(MOD_ID, "shield_status"),
                ShieldStatusHud::render
        );
    }
}
