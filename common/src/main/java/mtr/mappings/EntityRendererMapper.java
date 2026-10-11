package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public abstract class EntityRendererMapper<T extends Entity> extends EntityRenderer<T, EntityRendererMapper.State> {

	public EntityRendererMapper(Object parameter) {
		super((EntityRendererProvider.Context) parameter);
	}

	@Override
	public final State createRenderState() {
		return new State();
	}

	@Override
	public final void extractRenderState(T entity, State state, float tickDelta) {
		super.extractRenderState(entity, state, tickDelta);
		state.snapshot = RenderSnapshot.EMPTY;
		try (RenderBufferSource buffers = RenderBufferSource.begin(new Vec3(state.x, state.y, state.z))) {
			final PoseStack matrices = new PoseStack();
			// Legacy RenderTrains replaces the entity-local top pose when drawing world geometry.
			matrices.pushPose();
			render(entity, entity.getYRot(), tickDelta, matrices, buffers, state.lightCoords);
			state.snapshot = buffers.snapshot();
		}
	}

	@Override
	public final void submit(State state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState cameraState) {
		state.snapshot.submit(matrices, collector, cameraState);
	}

	public abstract void render(T entity, float yaw, float tickDelta, PoseStack matrices, RenderBufferSource buffers, int light);

	public abstract Identifier getTextureLocation(T entity);

	public static final class State extends EntityRenderState {
		private RenderSnapshot snapshot = RenderSnapshot.EMPTY;
	}
}
