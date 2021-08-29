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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

	@Shadow public abstract ActionResult interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult);

	@Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
	public void bypassItemFrame(PlayerEntity playerEntity, Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof ItemFrameEntity) {
			ItemFrameEntity itemFrame = (ItemFrameEntity) entity;
			if (!player.isSneaking() && (!itemFrame.getHeldItemStack().isEmpty()) && Configs.getInstance().bypassItemFrameEntity.value) {
				MinecraftClient client = MinecraftClient.getInstance();
				BlockPos blockToClick = itemFrame.getBlockPos().offset(itemFrame.getHorizontalFacing().getOpposite());
				Block hit = itemFrame.getEntityWorld().getBlockState(blockToClick).getBlock();
				if (canBeClicked(hit)) {
					BlockHitResult hitResult = new BlockHitResult(new Vec3d(blockToClick.getX(), blockToClick.getY(), blockToClick.getZ()), itemFrame.getHorizontalFacing().getOpposite(), blockToClick, false); // whatever the ray trace xd
					ActionResult actionResult = interactBlock(client.player, client.world, hand, hitResult);
					cir.setReturnValue(actionResult);
				}
			}
		}
	}

	public boolean canBeClicked(Block block) {
		return block instanceof CraftingTableBlock || block instanceof NoteBlock || block instanceof BlockWithEntity && !(block instanceof JukeboxBlock || block instanceof BeehiveBlock || block instanceof EndGatewayBlock);
	}
}