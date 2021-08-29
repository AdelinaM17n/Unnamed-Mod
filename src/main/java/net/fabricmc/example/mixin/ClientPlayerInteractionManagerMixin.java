package net.fabricmc.example.mixin;

import net.fabricmc.example.config.Configs;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
//import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

	//@Shadow public abstract Boolean interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult);

	@Shadow public abstract float getReachDistance();

	@Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
	public void bypassItemFrame(PlayerEntity playerEntity, Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof ItemFrameEntity) {
			ItemFrameEntity itemFrame = (ItemFrameEntity) entity;
			if (!playerEntity.isSneaking() && (!itemFrame.method_7737().isEmpty()) && Configs.getInstance().bypassItemFrameEntity.value) {
				MinecraftClient client = MinecraftClient.getInstance();
				BlockPos blockToClick = itemFrame.getBlockPos().offset(itemFrame.getHorizontalDirection().getOpposite()); //.getHorizontalFacing().getOpposite());
				Block hit = itemFrame.world.getBlockState(blockToClick).getBlock();
				if (canBeClicked(hit)) {
					HitResult hitResult = (HitResult) playerEntity.rayTrace(getReachDistance(),1); // whatever the ray trace xd
					//ActionResult actionResult = interactBlock(client.player, client.world, hand, hitResult);
					cir.setReturnValue(true);
				}
			}
		}
	}

	public boolean canBeClicked(Block block) {
		return block instanceof CraftingTableBlock || block instanceof BlockWithEntity && !(block instanceof JukeboxBlock);
	}
}