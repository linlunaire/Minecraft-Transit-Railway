package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.render.MoreRenderLayers
import net.minecraft.resources.Identifier

open class ModelDoorOverlayTopMLR(texture: String) : ModelDoorOverlayTopBase() {
    private val left: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val right: ModelMapper
    private val outer_roof_2_r1: ModelMapper

    private val texture: Identifier

    init {
        this.texture = Identifier.parse(texture)

        val textureWidth = 24
        val textureHeight = 3

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        left = ModelMapper(modelDataWrapper)
        left.setPos(0f, 24f, 0f)


        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20.7f, -13f, 0f)
        left.addChild(outer_roof_1_r1)
        ModelTrainBase.rotatePart(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(0, -12).addBox(-0.2f, -19f, 0f, 0, 3, 12, 0f, false)

        right = ModelMapper(modelDataWrapper)
        right.setPos(0f, 24f, 0f)


        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-20.7f, -13f, 0f)
        right.addChild(outer_roof_2_r1)
        ModelTrainBase.rotatePart(outer_roof_2_r1, 0f, 3.1416f, 0.1107f)
        outer_roof_2_r1.texOffs(0, -12).addBox(0.2f, -19f, 0f, 0, 3, 12, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        left.setModelPart()
        right.setModelPart()
    }

    @Override
    override fun render(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        light: Int,
        position: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    ) {
        ModelTrainBase.renderPartOnce(
            left,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            doorRightX,
            position + doorRightZ
        )
        ModelTrainBase.renderPartOnce(
            right,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            doorRightX,
            position - doorRightZ
        )
        ModelTrainBase.renderPartOnceFlipped(
            left,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            doorLeftX,
            position - doorLeftZ
        )
        ModelTrainBase.renderPartOnceFlipped(
            right,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
            light,
            doorLeftX,
            position + doorLeftZ
        )
    }
}
