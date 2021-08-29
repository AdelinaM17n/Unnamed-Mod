package net.fabricmc.example.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRenderMixin {

    @Inject(
            method = "renderFog",
            at = @At("TAIL"),
            cancellable = true
    )
    public void renderFog(int i, float tickDelta, CallbackInfo ci){
        GlStateManager.disableFog();

    }
}
