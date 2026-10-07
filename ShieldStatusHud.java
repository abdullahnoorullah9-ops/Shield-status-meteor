package com.shieldstatus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public final class ShieldStatusHud {
    private static final int GREEN = 0xFF55FF55; // alpha is required in 1.21.6+
    private static final int RED = 0xFFFF5555;

    private ShieldStatusHud() {}

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null || client.options.hudHidden) return;

        HitResult hit = client.crosshairTarget;
        if (!(hit instanceof EntityHitResult entityHit)) return;
        if (!(entityHit.getEntity() instanceof PlayerEntity target) || target == client.player) return;

        long left = ShieldTracker.remainingMs(target.getId());
        String text;
        int color;
        if (left > 0) {
            text = String.format("SHIELD DISABLED %.1fs", left / 1000.0);
            color = RED;
        } else if (target.isBlocking()) {
            text = "SHIELD UP";
            color = GREEN;
        } else {
            return;
        }

        TextRenderer font = client.textRenderer;
        int x = context.getScaledWindowWidth() / 2 - font.getWidth(text) / 2;
        int y = context.getScaledWindowHeight() / 2 + 14;
        context.drawText(font, text, x, y, color, true);
    }
}
