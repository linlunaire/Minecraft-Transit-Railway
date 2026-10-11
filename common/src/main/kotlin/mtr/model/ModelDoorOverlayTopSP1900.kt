package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.render.MoreRenderLayers
import net.minecraft.resources.Identifier

open class ModelDoorOverlayTopSP1900() : ModelDoorOverlayTopBase() {
    private val bb_main: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper

    init {
        val textureWidth = 24
        val textureHeight = 3

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        bb_main = ModelMapper(modelDataWrapper)
        bb_main.setPos(0f, 24f, 0f)


        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-20f, -14f, 0f)
        bb_main.addChild(outer_roof_2_r1)
        ModelTrainBase.rotatePart(outer_roof_2_r1, 0f, 3.1416f, 0.1107f)
        outer_roof_2_r1.texOffs(0, -12).addBox(1.1f, -21f, 0f, 0, 3, 12, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        bb_main.addChild(outer_roof_1_r1)
        ModelTrainBase.rotatePart(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(0, -12).addBox(-1.1f, -21f, 0f, 0, 3, 12, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        bb_main.setModelPart()
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
        ModelTrainBase.renderPartMirror(
            bb_main,
            matrices,
            vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(TEXTURE_ID)),
            light,
            position.toFloat()
        )
    }

    companion object {
        private val TEXTURE_ID = Identifier.parse("mtr:textures/block/sign/door_overlay_sp1900_top.png")
    }
}
