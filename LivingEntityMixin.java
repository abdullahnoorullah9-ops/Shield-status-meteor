package com.shieldstatus.mixin;

import com.shieldstatus.ShieldTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Entity status 30 is what vanilla sends when a player's shield is disabled (axe hit).
    @Inject(method = "handleStatus", at = @At("HEAD"))
    private void shieldstatus$onStatus(byte status, CallbackInfo ci) {
        if (status != 30) return;
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof PlayerEntity player) {
            ShieldTracker.markDisabled(player.getId());
            if (ShieldTracker.DEBUG_LOG) {
                LoggerFactory.getLogger("shieldstatus")
                        .info("Shield disabled detected on {}", player.getName().getString());
            }
        }
    }
}
