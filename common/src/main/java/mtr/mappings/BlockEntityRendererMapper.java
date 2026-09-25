package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

public abstract class BlockEntityRendererMapper<T extends BlockEntityMapper> implements BlockEntityRenderer<T, BlockEntityRendererMapper.State> {

	public BlockEntityRendererMapper(BlockEntityRenderDispatcher dispatcher) {
	}

	@Override
	public final State createRenderState() {
		return new State();
	}

	@Override
	public final void extractRenderState(T entity, State state, float tickDelta, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(entity, state, tickDelta, cameraPosition, breakProgress);
		state.snapshot = RenderSnapshot.EMPTY;
		try (RenderBufferSource buffers = RenderBufferSource.begin(Vec3.atLowerCornerOf(entity.getBlockPos()))) {
			render(entity, tickDelta, new PoseStack(), buffers, state.lightCoords, OverlayTexture.NO_OVERLAY);
			state.snapshot = buffers.snapshot();
		}
	}

	@Override
	public final void submit(State state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState cameraState) {
		state.snapshot.submit(matrices, collector, cameraState);
	}

	public abstract void render(T entity, float tickDelta, PoseStack matrices, RenderBufferSource buffers, int light, int overlay);

	public static final class State extends BlockEntityRenderState {
		private RenderSnapshot snapshot = RenderSnapshot.EMPTY;
	}
}
