package net.fabricmc.example.mixin;

import net.fabricmc.example.config.Configs;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.world.ClientWorld;
//import net.minecraft.particle.DefaultParticleType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpellParticle.SpellFactory.class)
public class SpellParticleMixin {

    @Inject(method = "createParticle", at = @At("HEAD"), cancellable = true)
    private void removePotionParticleOnEntity(int id, World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] arr, CallbackInfoReturnable<Particle> cir) {
        if (Configs.getInstance().disablePotionEffectParticles.value) {
            cir.setReturnValue(null);
        }
    }

}
