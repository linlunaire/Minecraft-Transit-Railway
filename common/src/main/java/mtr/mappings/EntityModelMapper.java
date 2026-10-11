package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;

/**
 * The legacy MTR model contract, separate from Minecraft's render-state models.
 * MTR train models render selected parts in material stages and bake their parts
 * after construction. Vanilla Model requires a complete root at construction
 * and its final renderToBuffer method always renders the entire root.
 */
public abstract class EntityModelMapper<T extends Entity> {

	public abstract void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch);

	public abstract void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color);
}
