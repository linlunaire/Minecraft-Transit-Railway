package mtr.mixin;

import mtr.client.ClientData;
import mtr.mappings.PassengerRenderState;
import mtr.render.RenderTrains;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public abstract class PlayerRendererOffsetMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void mtr$capturePassenger(Avatar entity, AvatarRenderState state, float partialTick, CallbackInfo callback) {
		((PassengerRenderState) state).mtr$setRidingTrain(ClientData.isRiding(entity.getUUID()));
	}

	@Inject(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"), cancellable = true)
	private void mtr$passengerOffset(AvatarRenderState state, CallbackInfoReturnable<Vec3> callback) {
		if (((PassengerRenderState) state).mtr$isRidingTrain()) {
			callback.setReturnValue(new Vec3(0, -RenderTrains.PLAYER_RENDER_OFFSET, 0));
		}
	}
}
