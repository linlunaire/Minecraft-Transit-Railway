package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.mappings.EntityModelMapper
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.model.ModelTrainBase.RenderStage
import mtr.render.MoreRenderLayers
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.Entity

open class ModelDoorOverlay(
    doorMax: Int,
    angle: Float,
    pivotY: Int,
    overlayLeftTextureName: String?,
    overlayRightTextureName: String?,
    renderLeft: Boolean,
    renderRight: Boolean
) : EntityModelMapper<Entity?>() {
    private val door_left_overlay_interior: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right_overlay_interior: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_right_bottom_r1: ModelMapper
    private val door_left_overlay_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_overlay_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val wall_1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val wall_2: ModelMapper
    private val upper_wall_2_r1: ModelMapper

    private val doorOverlayTextureLeft: Identifier
    private val doorOverlayTextureRight: Identifier
    private val renderLeft: Boolean
    private val renderRight: Boolean

    constructor(
        doorMax: Int,
        angle: Float,
        overlayLeftTextureName: String?,
        overlayRightTextureName: String?
    ) : this(doorMax, angle, 14, overlayLeftTextureName, overlayRightTextureName, true, true)

    constructor(
        doorMax: Int,
        angle: Float,
        pivotY: Int,
        overlayLeftTextureName: String?,
        overlayRightTextureName: String?
    ) : this(doorMax, angle, pivotY, overlayLeftTextureName, overlayRightTextureName, true, true)

    init {
        val angleRadians = Math.toRadians(angle.toDouble()).toFloat()
        doorOverlayTextureLeft = Identifier.parse("mtr:textures/block/sign/" + overlayLeftTextureName)
        doorOverlayTextureRight = Identifier.parse("mtr:textures/block/sign/" + overlayRightTextureName)
        this.renderLeft = renderLeft
        this.renderRight = renderRight

        val textureWidth = 38
        val textureHeight = 32

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        door_left_overlay_interior = ModelMapper(modelDataWrapper)
        door_left_overlay_interior.setPos(0f, 24f, 0f)
        door_left_overlay_interior.texOffs(3, 3).addBox(-19.7f, (-pivotY).toFloat(), 0f, 0, 13, 16, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-19.7f, (-pivotY).toFloat(), 0f)
        door_left_overlay_interior.addChild(door_left_top_r1)
        ModelTrainBase.rotatePart(door_left_top_r1, 0f, 0f, angleRadians)
        door_left_top_r1.texOffs(3, -16).addBox(0f, -19f, 0f, 0, 19, 16, 0f, false)

        door_right_overlay_interior = ModelMapper(modelDataWrapper)
        door_right_overlay_interior.setPos(0f, 24f, 0f)


        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-19.7f, (-pivotY).toFloat(), 0f)
        door_right_overlay_interior.addChild(door_right_top_r1)
        ModelTrainBase.rotatePart(door_right_top_r1, 0f, 3.1416f, angleRadians)
        door_right_top_r1.texOffs(3, -16).addBox(0f, -19f, 0f, 0, 19, 16, 0f, false)

        door_right_bottom_r1 = ModelMapper(modelDataWrapper)
        door_right_bottom_r1.setPos(0f, 0f, 0f)
        door_right_overlay_interior.addChild(door_right_bottom_r1)
        ModelTrainBase.rotatePart(door_right_bottom_r1, 0f, 3.1416f, 0f)
        door_right_bottom_r1.texOffs(3, 3).addBox(19.7f, (-pivotY).toFloat(), 0f, 0, 13, 16, 0f, false)

        door_left_overlay_exterior = ModelMapper(modelDataWrapper)
        door_left_overlay_exterior.setPos(0f, 24f, 0f)


        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.7f, (-pivotY).toFloat(), 0f)
        door_left_overlay_exterior.addChild(door_left_top_r2)
        ModelTrainBase.rotatePart(door_left_top_r2, 0f, 0f, angleRadians)
        door_left_top_r2.texOffs(3, -16).addBox(0f, -19f, 0f, 0, 19, 16, 0f, false)

        door_right_overlay_exterior = ModelMapper(modelDataWrapper)
        door_right_overlay_exterior.setPos(0f, 24f, 0f)


        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.7f, (-pivotY).toFloat(), 0f)
        door_right_overlay_exterior.addChild(door_right_top_r2)
        ModelTrainBase.rotatePart(door_right_top_r2, 0f, 3.1416f, angleRadians)
        door_right_top_r2.texOffs(3, -16).addBox(0f, -19f, 0f, 0, 19, 16, 0f, false)

        wall_1 = ModelMapper(modelDataWrapper)
        wall_1.setPos(0f, 24f, 0f)
        wall_1.texOffs(32, 19).addBox(-20f, (-pivotY).toFloat(), -doorMax + 0.1f, 3, 13, 0, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(-20f, (-pivotY).toFloat(), 0f)
        wall_1.addChild(upper_wall_1_r1)
        ModelTrainBase.rotatePart(upper_wall_1_r1, 0f, 0f, angleRadians)
        upper_wall_1_r1.texOffs(32, 0).addBox(0f, -19f, -doorMax + 0.1f, 3, 19, 0, 0f, false)

        wall_2 = ModelMapper(modelDataWrapper)
        wall_2.setPos(0f, 24f, 0f)
        wall_2.texOffs(0, 19).addBox(-20f, (-pivotY).toFloat(), doorMax - 0.1f, 3, 13, 0, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, (-pivotY).toFloat(), 0f)
        wall_2.addChild(upper_wall_2_r1)
        ModelTrainBase.rotatePart(upper_wall_2_r1, 0f, 0f, angleRadians)
        upper_wall_2_r1.texOffs(0, 0).addBox(0f, -19f, doorMax - 0.1f, 3, 19, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        door_left_overlay_interior.setModelPart()
        door_right_overlay_interior.setModelPart()
        door_left_overlay_exterior.setModelPart()
        door_right_overlay_exterior.setModelPart()
        wall_1.setModelPart()
        wall_2.setModelPart()
    }

    open fun render(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        lightsOn: Boolean
    ) {
        when (renderStage!!) {
            RenderStage.INTERIOR -> {
                val renderLayerInteriorLeft =
                    if (lightsOn) MoreRenderLayers.getInterior(doorOverlayTextureLeft) else MoreRenderLayers.getExterior(
                        doorOverlayTextureLeft
                    )
                val renderLayerInteriorRight =
                    if (lightsOn) MoreRenderLayers.getInterior(doorOverlayTextureRight) else MoreRenderLayers.getExterior(
                        doorOverlayTextureRight
                    )
                if (renderRight) {
                    ModelTrainBase.renderPartOnce(
                        door_left_overlay_interior,
                        matrices,
                        vertexConsumers!!.getBuffer(renderLayerInteriorRight),
                        light,
                        doorRightX,
                        position + doorRightZ
                    )
                    ModelTrainBase.renderPartOnce(
                        door_right_overlay_interior,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorLeft),
                        light,
                        doorRightX,
                        position - doorRightZ
                    )
                    ModelTrainBase.renderPartOnce(
                        wall_1,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorLeft),
                        light,
                        position.toFloat()
                    )
                    ModelTrainBase.renderPartOnce(
                        wall_2,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorRight),
                        light,
                        position.toFloat()
                    )
                }
                if (renderLeft) {
                    ModelTrainBase.renderPartOnceFlipped(
                        door_left_overlay_interior,
                        matrices,
                        vertexConsumers!!.getBuffer(renderLayerInteriorRight),
                        light,
                        doorLeftX,
                        position - doorLeftZ
                    )
                    ModelTrainBase.renderPartOnceFlipped(
                        door_right_overlay_interior,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorLeft),
                        light,
                        doorLeftX,
                        position + doorLeftZ
                    )
                    ModelTrainBase.renderPartOnceFlipped(
                        wall_1,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorLeft),
                        light,
                        position.toFloat()
                    )
                    ModelTrainBase.renderPartOnceFlipped(
                        wall_2,
                        matrices,
                        vertexConsumers.getBuffer(renderLayerInteriorRight),
                        light,
                        position.toFloat()
                    )
                }
            }

            RenderStage.EXTERIOR -> {
                if (renderRight) {
                    ModelTrainBase.renderPartOnce(
                        door_left_overlay_exterior,
                        matrices,
                        vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(doorOverlayTextureLeft)),
                        light / 4 * 3,
                        doorRightX,
                        position + doorRightZ
                    )
                    ModelTrainBase.renderPartOnce(
                        door_right_overlay_exterior,
                        matrices,
                        vertexConsumers.getBuffer(MoreRenderLayers.getExterior(doorOverlayTextureRight)),
                        light / 4 * 3,
                        doorRightX,
                        position - doorRightZ
                    )
                }
                if (renderLeft) {
                    ModelTrainBase.renderPartOnceFlipped(
                        door_left_overlay_exterior,
                        matrices,
                        vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(doorOverlayTextureLeft)),
                        light / 4 * 3,
                        doorLeftX,
                        position - doorLeftZ
                    )
                    ModelTrainBase.renderPartOnceFlipped(
                        door_right_overlay_exterior,
                        matrices,
                        vertexConsumers.getBuffer(MoreRenderLayers.getExterior(doorOverlayTextureRight)),
                        light / 4 * 3,
                        doorLeftX,
                        position + doorLeftZ
                    )
                }
            }

            else -> {}
        }
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
