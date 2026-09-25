package mtr.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.MTRClient;
import mtr.mappings.LevelRenderStateExtension;
import mtr.mappings.RenderBufferSource;
import mtr.mappings.RenderSnapshot;
import mtr.render.RenderTrains;
import mtr.screen.ResourcePackCreatorScreen;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public abstract class LevelExtractionMixin {
	@Shadow @Final private LevelRenderState levelRenderState;

	@Inject(method = "extract", at = @At("TAIL"))
	private void mtr$extract(DeltaTracker deltaTracker, Camera camera, float partialTick, CallbackInfo callback) {
		final LevelRenderStateExtension target = (LevelRenderStateExtension) levelRenderState;
		target.mtr$setSnapshot(RenderSnapshot.EMPTY);
		final CameraRenderState cameraState = levelRenderState.cameraRenderState;
		try (RenderBufferSource buffers = RenderBufferSource.begin(cameraState.pos)) {
			final PoseStack world = new PoseStack();
			world.translate(-cameraState.pos.x, -cameraState.pos.y, -cameraState.pos.z);
			RenderTrains.render(null, 0, world, buffers);
			// The editor's model is camera-facing. Cancel the scene view rotation once;
			// its own yaw/roll/scale remain in ResourcePackCreatorScreen.render.
			final PoseStack preview = new PoseStack();
			preview.mulPose(new Matrix4f(cameraState.viewRotationMatrix).invert());
			ResourcePackCreatorScreen.render(preview);
			target.mtr$setSnapshot(buffers.snapshot());
		}
		// Simulation/cache changes belong to extraction, never to a delayed submit callback.
		MTRClient.incrementGameTick();
	}
}
