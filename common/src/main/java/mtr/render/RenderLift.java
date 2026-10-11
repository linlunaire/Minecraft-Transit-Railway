package mtr.render;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.data.IGui;
import mtr.entity.EntityLift;
import mtr.mappings.EntityRendererMapper;
import mtr.mappings.RenderBufferSource;
import net.minecraft.resources.Identifier;

// TODO temp code
public class RenderLift extends EntityRendererMapper<EntityLift> implements IGui {

	public RenderLift(Object parameter) {
		super(parameter);
	}

	@Override
	public void render(EntityLift entity, float entityYaw, float tickDelta, PoseStack matrices, RenderBufferSource vertexConsumers, int light) {
	}

	@Override
	public Identifier getTextureLocation(EntityLift entity) {
		return null;
	}
}
