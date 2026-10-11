package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.mappings.EntityModelMapper
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.render.MoreRenderLayers
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.Entity

open class ModelCableCarGrip() : EntityModelMapper<Entity?>() {
    private val grip: ModelMapper
    private val texture = Identifier.parse("mtr:textures/entity/cable_car_grip.png")

    init {
        val textureWidth = 48
        val textureHeight = 48

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        grip = ModelMapper(modelDataWrapper)
        grip.setPos(0f, 24f, 0f)
        grip.texOffs(1, 24).addBox(0f, -0.2f, -8f, 0, 0, 16, 0.2f, false)
        grip.texOffs(14, 0).addBox(0f, -1f, -3f, 1, 1, 6, 0f, false)
        grip.texOffs(12, 13).addBox(2f, -1f, -6f, 1, 5, 5, 0f, false)
        grip.texOffs(0, 13).addBox(2f, -1f, 1f, 1, 5, 5, 0f, false)
        grip.texOffs(0, 0).addBox(3f, 0f, -5f, 2, 3, 10, 0f, false)
        grip.texOffs(0, 0).addBox(5f, 0f, -1f, 3, 3, 2, 0f, false)
        grip.texOffs(19, 13).addBox(0f, -2f, -1f, 5, 2, 2, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        grip.setModelPart()
    }

    open fun render(matrices: PoseStack?, vertexConsumers: RenderBufferSource?, light: Int) {
        ModelTrainBase.renderPartOnce(
            grip,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            0f
        )
    }

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
}
