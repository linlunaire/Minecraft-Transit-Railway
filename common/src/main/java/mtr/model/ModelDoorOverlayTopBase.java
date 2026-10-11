package mtr.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.mappings.EntityModelMapper;
import mtr.mappings.RenderBufferSource;
import net.minecraft.world.entity.Entity;

public abstract class ModelDoorOverlayTopBase extends EntityModelMapper<Entity> {

	@Override
	public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public final void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
	}

	public abstract void render(PoseStack matrices, RenderBufferSource vertexConsumers, int light, int position, float doorLeftX, float doorRightX, float doorLeftZ, float doorRightZ);
}
