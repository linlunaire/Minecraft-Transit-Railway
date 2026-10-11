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

open class ModelBogie() : EntityModelMapper<Entity?>() {
    private val bogie: ModelMapper
    private val texture = Identifier.parse("mtr:textures/entity/bogie_1.png")

    init {
        val textureWidth = 186
        val textureHeight = 77

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        bogie = ModelMapper(modelDataWrapper)
        bogie.setPos(0f, 24f, 0f)
        bogie.texOffs(0, 0).addBox(-14f, 1f, -32f, 1, 13, 64, 0f, false)
        bogie.texOffs(0, 0).addBox(-13f, 1.5f, 16f, 1, 14, 14, 0f, false)
        bogie.texOffs(0, 0).addBox(-13f, 1.5f, -30f, 1, 14, 14, 0f, false)
        bogie.texOffs(66, 0).addBox(-13f, 1f, -23.5f, 13, 8, 47, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        bogie.setModelPart()
    }

    open fun render(matrices: PoseStack?, vertexConsumers: RenderBufferSource?, light: Int, position: Int) {
        ModelTrainBase.renderPartMirror(
            bogie,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            position.toFloat()
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
