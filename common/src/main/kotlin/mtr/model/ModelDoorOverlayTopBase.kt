@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.mappings.EntityModelMapper
import mtr.mappings.RenderBufferSource
import net.minecraft.world.entity.Entity

abstract class ModelDoorOverlayTopBase : EntityModelMapper<Entity?>() {
    override fun setupAnim(
        entity: Entity?,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
    }

    final override fun renderToBuffer(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        light: Int,
        overlay: Int,
        color: Int
    ) {
    }

    abstract fun render(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        light: Int,
        position: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    )
}
