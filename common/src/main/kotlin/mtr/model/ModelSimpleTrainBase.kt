@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.client.IDrawing
import mtr.data.IGui
import mtr.data.IGui.HorizontalAlignment
import mtr.data.IGui.VerticalAlignment
import mtr.mappings.RenderBufferSource
import mtr.mappings.UtilitiesClient
import net.minecraft.client.gui.Font

abstract class ModelSimpleTrainBase<T>(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelTrainBase(doorAnimationType, renderDoorOverlay) {
    protected final override fun render(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        car: Int,
        totalCars: Int,
        head1IsFront: Boolean,
        renderDetails: Boolean
    ) {
        val isEnd1Head = car == 0
        val isEnd2Head = car == totalCars - 1

        for (position in getWindowPositions()!!) {
            renderWindowPositions(
                matrices,
                vertices,
                renderStage,
                light,
                position,
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                isEnd1Head,
                isEnd2Head
            )
        }
        for (position in getDoorPositions()!!) {
            renderDoorPositions(
                matrices,
                vertices,
                renderStage,
                light,
                position,
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                isEnd1Head,
                isEnd2Head
            )
        }

        if (isEnd1Head) {
            renderHeadPosition1(
                matrices,
                vertices,
                renderStage,
                light,
                getEndPositions()!![0],
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                head1IsFront
            )
        } else {
            renderEndPosition1(
                matrices,
                vertices,
                renderStage,
                light,
                getEndPositions()!![0],
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ
            )
        }

        if (isEnd2Head) {
            renderHeadPosition2(
                matrices,
                vertices,
                renderStage,
                light,
                getEndPositions()!![1],
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                !head1IsFront
            )
        } else {
            renderEndPosition2(
                matrices,
                vertices,
                renderStage,
                light,
                getEndPositions()!![1],
                renderDetails,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ
            )
        }
    }

    protected override fun renderExtraDetails(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        light: Int,
        lightOnInteriorLevel: Int,
        lightsOn: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    ) {
        for (position in getDoorPositions()!!) {
            val modelDoorOverlay = if (renderDoorOverlay) getModelDoorOverlay() else null
            if (modelDoorOverlay != null) {
                modelDoorOverlay.render(
                    matrices,
                    vertexConsumers,
                    RenderStage.INTERIOR,
                    lightOnInteriorLevel,
                    position,
                    doorLeftX,
                    doorRightX,
                    doorLeftZ,
                    doorRightZ,
                    lightsOn
                )
                modelDoorOverlay.render(
                    matrices,
                    vertexConsumers,
                    RenderStage.EXTERIOR,
                    light,
                    position,
                    doorLeftX,
                    doorRightX,
                    doorLeftZ,
                    doorRightZ,
                    lightsOn
                )
            }
            val modelDoorOverlayTop = if (renderDoorOverlay) getModelDoorOverlayTop() else null
            if (modelDoorOverlayTop != null) {
                modelDoorOverlayTop.render(
                    matrices,
                    vertexConsumers,
                    light,
                    position,
                    doorLeftX,
                    doorRightX,
                    doorLeftZ,
                    doorRightZ
                )
            }
        }
    }

    protected open fun renderFrontDestination(
        matrices: PoseStack?,
        font: Font?,
        immediate: RenderBufferSource?,
        x1: Float,
        y1: Float,
        z1: Float,
        x2: Float,
        y2: Float,
        z2: Float,
        rotationX: Float,
        rotationY: Float,
        maxWidth: Float,
        maxHeight: Float,
        colorCjk: Int,
        color: Int,
        fontSizeRatio: Float,
        text: String?,
        padOneLine: Boolean,
        car: Int,
        totalCars: Int
    ) {
        val isEnd1Head = car == 0
        val isEnd2Head = car == totalCars - 1

        for (i in 0..1) {
            if (i == 0 && isEnd1Head || i == 1 && isEnd2Head) {
                matrices!!.pushPose()
                if (i == 1) {
                    UtilitiesClient.rotateYDegrees(matrices, 180f)
                }
                matrices.translate(x1, y1, z1)
                if (rotationY != 0f) {
                    UtilitiesClient.rotateYDegrees(matrices, rotationY)
                }
                if (rotationX != 0f) {
                    UtilitiesClient.rotateXDegrees(matrices, rotationX)
                }
                matrices.translate(x2, y2, z2)
                IDrawing.drawStringWithFont(
                    matrices,
                    font,
                    immediate,
                    text,
                    HorizontalAlignment.CENTER,
                    VerticalAlignment.CENTER,
                    HorizontalAlignment.CENTER,
                    0f,
                    0f,
                    maxWidth,
                    (if (padOneLine && !text!!.contains("|")) if (IGui.isCjk(text)) fontSizeRatio / (fontSizeRatio + 1) else 0.5f else 1f) * maxHeight,
                    1f,
                    colorCjk,
                    color,
                    fontSizeRatio,
                    false,
                    IGui.MAX_LIGHT_GLOWING,
                    null
                )
                matrices.popPose()
            }
        }
    }

    abstract fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): T?

    protected abstract fun renderWindowPositions(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        isEnd1Head: Boolean,
        isEnd2Head: Boolean
    )

    protected abstract fun renderDoorPositions(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        isEnd1Head: Boolean,
        isEnd2Head: Boolean
    )

    protected abstract fun renderHeadPosition1(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        useHeadlights: Boolean
    )

    protected abstract fun renderHeadPosition2(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        useHeadlights: Boolean
    )

    protected abstract fun renderEndPosition1(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    )

    protected abstract fun renderEndPosition2(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    )

    protected abstract fun getModelDoorOverlay(): ModelDoorOverlay?

    protected abstract fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase?

    protected abstract fun getWindowPositions(): IntArray?

    protected abstract fun getDoorPositions(): IntArray?

    protected abstract fun getEndPositions(): IntArray?
}
