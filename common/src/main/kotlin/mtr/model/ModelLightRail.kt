package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.ClientData
import mtr.client.DoorAnimationType
import mtr.client.RouteMapGenerator
import mtr.client.ScrollingText
import mtr.data.Route
import mtr.data.Station
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.mappings.UtilitiesClient
import mtr.render.MoreRenderLayers
import net.minecraft.client.gui.Font

open class ModelLightRail private constructor(
    private val phase: Int,
    private val isRHT: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelLightRail?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val window_exterior: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_right: ModelMapper
    private val door_5: ModelMapper
    private val door_left_5: ModelMapper
    private val door_right_5: ModelMapper
    private val door_handrails: ModelMapper
    private val handrail_6_r1: ModelMapper
    private val door_handrails_4: ModelMapper
    private val handrail_6_r2: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val door_handrails_5: ModelMapper
    private val door_edge_8_r1: ModelMapper
    private val door_edge_7_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_exterior_5: ModelMapper
    private val door_left_exterior_5: ModelMapper
    private val door_right_exterior_5: ModelMapper
    private val door_window: ModelMapper
    private val door_window_handrails: ModelMapper
    private val handrail_6_r3: ModelMapper
    private val door_window_handrails_4: ModelMapper
    private val handrail_12_r1: ModelMapper
    private val handrail_9_r1: ModelMapper
    private val handrail_7_r1: ModelMapper
    private val door_window_handrails_5: ModelMapper
    private val handrail_14_r1: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val handrail_7_r2: ModelMapper
    private val handrail_6_r4: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val door_window_exterior: ModelMapper
    private val roof: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val bumper_2_r1: ModelMapper
    private val bumper_1_r1: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val roof_light: ModelMapper
    private val light_3_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val roof_light_5: ModelMapper
    private val end: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val back_r1: ModelMapper
    private val wall_diagonal_2_r1: ModelMapper
    private val wall_diagonal_1_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val back_r2: ModelMapper
    private val wall_diagonal_2_r2: ModelMapper
    private val wall_diagonal_1_r2: ModelMapper
    private val head: ModelMapper
    private val head_exterior: ModelMapper
    private val wall_diagonal_2_r3: ModelMapper
    private val wall_diagonal_1_r3: ModelMapper
    private val head_exterior_1: ModelMapper
    private val front_r1: ModelMapper
    private val head_exterior_3_5: ModelMapper
    private val front_r2: ModelMapper
    private val head_exterior_4: ModelMapper
    private val front_middle_r1: ModelMapper
    private val front_top_r1: ModelMapper
    private val destination_board_r1: ModelMapper
    private val seat: ModelMapper
    private val back_right_r1: ModelMapper
    private val back_left_r1: ModelMapper
    private val seat_green: ModelMapper
    private val back_right_r2: ModelMapper
    private val seat_purple: ModelMapper
    private val back_right_r3: ModelMapper
    private val vents_top: ModelMapper
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper
    private val side_display: ModelMapper
    private val bottom_r1: ModelMapper

    constructor(phase: Int, isRHT: Boolean) : this(phase, isRHT, getDoorAnimationType(phase), true)

    init {
        val textureWidth = 368
        val textureHeight = 368

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(98, 0).addBox(-20f, 0f, -32f, 20, 1, 64, 0f, false)
        window.texOffs(134, 134).addBox(-20f, -32f, -34f, 2, 32, 68, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(0, 192).addBox(-20f, 0f, -32f, 1, 7, 64, 0f, false)
        window_exterior.texOffs(0, 92).addBox(-20f, -32f, -34f, 0, 32, 68, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(206, 65).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(168, 219).addBox(-20f, -32f, 0f, 0, 32, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(206, 116).addBox(-20f, -32f, -15f, 0, 32, 15, 0f, false)

        door_5 = ModelMapper(modelDataWrapper)
        door_5.setPos(0f, 24f, 0f)
        door_5.texOffs(206, 65).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left_5 = ModelMapper(modelDataWrapper)
        door_left_5.setPos(0f, 0f, 0f)
        door_5.addChild(door_left_5)
        door_left_5.texOffs(167, 218).addBox(-20f, -32f, 0f, 0, 32, 16, 0f, false)

        door_right_5 = ModelMapper(modelDataWrapper)
        door_right_5.setPos(0f, 0f, 0f)
        door_5.addChild(door_right_5)
        door_right_5.texOffs(205, 115).addBox(-20f, -32f, -16f, 0, 32, 16, 0f, false)

        door_handrails = ModelMapper(modelDataWrapper)
        door_handrails.setPos(0f, 24f, 0f)
        door_handrails.texOffs(120, 0).addBox(-18f, -15f, 16f, 5, 13, 0, 0f, false)
        door_handrails.texOffs(120, 0).addBox(-18f, -15f, -16f, 5, 13, 0, 0f, false)
        door_handrails.texOffs(0, 0).addBox(-13f, -36f, 16f, 0, 36, 0, 0.2f, false)
        door_handrails.texOffs(0, 0).addBox(-13f, -36f, -16f, 0, 36, 0, 0.2f, false)
        door_handrails.texOffs(4, 0).addBox(-9f, -36f, 0f, 0, 36, 0, 0.2f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, 20f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, 28f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, 36f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, 44f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, -44f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, -36f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, -28f, 4, 5, 0, 0f, false)
        door_handrails.texOffs(0, 75).addBox(-15f, -32f, -20f, 4, 5, 0, 0f, false)

        handrail_6_r1 = ModelMapper(modelDataWrapper)
        handrail_6_r1.setPos(0f, 0f, 0f)
        door_handrails.addChild(handrail_6_r1)
        setRotationAngle(handrail_6_r1, -1.5708f, 0f, 0f)
        handrail_6_r1.texOffs(8, 0).addBox(-13f, 16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_6_r1.texOffs(8, 0).addBox(-13f, -48f, -32f, 0, 32, 0, 0.2f, false)

        door_handrails_4 = ModelMapper(modelDataWrapper)
        door_handrails_4.setPos(0f, 24f, 0f)
        door_handrails_4.texOffs(4, 6).addBox(-15.8f, -17f, -15f, 0, 4, 0, 0.2f, false)
        door_handrails_4.texOffs(4, 6).addBox(-15.8f, -17f, 15f, 0, 4, 0, 0.2f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -12f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -4f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 4f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 12f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 20f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 28f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 36f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, 44f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -44f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -36f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -28f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(0, 75).addBox(-5f, -32f, -20f, 4, 5, 0, 0f, false)
        door_handrails_4.texOffs(358, 0).addBox(-20f, -32f, 14f, 3, 32, 2, 0f, false)
        door_handrails_4.texOffs(346, 0).addBox(-20f, -32f, -16f, 3, 32, 2, 0f, false)

        handrail_6_r2 = ModelMapper(modelDataWrapper)
        handrail_6_r2.setPos(-15.6f, -17.2f, 14.8f)
        door_handrails_4.addChild(handrail_6_r2)
        setRotationAngle(handrail_6_r2, 0f, 0f, -0.3491f)
        handrail_6_r2.texOffs(4, 6).addBox(-0.2f, -5.2f, 0.2f, 0, 5, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-15.6f, -12.8f, 14.8f)
        door_handrails_4.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, 0.3491f)
        handrail_5_r1.texOffs(4, 6).addBox(-0.2f, 0.2f, 0.2f, 0, 5, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-15.6f, -17.2f, -15.2f)
        door_handrails_4.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, -0.3491f)
        handrail_3_r1.texOffs(4, 6).addBox(-0.2f, -5.2f, 0.2f, 0, 5, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(-15.6f, -12.8f, -15.2f)
        door_handrails_4.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, 0.3491f)
        handrail_2_r1.texOffs(4, 6).addBox(-0.2f, 0.2f, 0.2f, 0, 5, 0, 0.2f, false)

        door_handrails_5 = ModelMapper(modelDataWrapper)
        door_handrails_5.setPos(0f, 24f, 0f)
        door_handrails_5.texOffs(0, 3).addBox(-17.25f, -26f, 14.25f, 0, 19, 0, 0.2f, false)
        door_handrails_5.texOffs(0, 3).addBox(-17.25f, -26f, -14.25f, 0, 19, 0, 0.2f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -12f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -4f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 4f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 12f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 20f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 28f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 36f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, 44f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -44f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -36f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -28f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(0, 75).addBox(-5f, -32.5f, -20f, 4, 5, 0, 0f, false)
        door_handrails_5.texOffs(358, 0).addBox(-20f, -32f, 14f, 3, 32, 2, 0f, false)
        door_handrails_5.texOffs(346, 0).addBox(-20f, -32f, -16f, 3, 32, 2, 0f, false)
        door_handrails_5.texOffs(361, 38).addBox(-19f, -7f, -16f, 2, 0, 2, 0f, false)
        door_handrails_5.texOffs(363, 64).addBox(-18.25f, -26f, -15.25f, 2, 19, 0, 0f, false)
        door_handrails_5.texOffs(361, 38).addBox(-19f, -7f, 13.25f, 2, 0, 2, 0f, false)

        door_edge_8_r1 = ModelMapper(modelDataWrapper)
        door_edge_8_r1.setPos(0f, 0f, 0f)
        door_handrails_5.addChild(door_edge_8_r1)
        setRotationAngle(door_edge_8_r1, 0f, 3.1416f, 0f)
        door_edge_8_r1.texOffs(363, 64).addBox(16.75f, -26f, -15.25f, 2, 19, 0, 0f, false)

        door_edge_7_r1 = ModelMapper(modelDataWrapper)
        door_edge_7_r1.setPos(0f, 0f, 0f)
        door_handrails_5.addChild(door_edge_7_r1)
        setRotationAngle(door_edge_7_r1, 0f, 0f, -3.1416f)
        door_edge_7_r1.texOffs(361, 38).addBox(16f, 26f, 13.25f, 2, 0, 2, 0f, false)
        door_edge_7_r1.texOffs(361, 38).addBox(16f, 26f, -16f, 2, 0, 2, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(278, 29).addBox(-20f, 0f, -16f, 1, 7, 32, 0f, false)
        door_exterior.texOffs(0, 0).addBox(-21f, -34f, -48f, 1, 2, 96, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(234, 311).addBox(-21f, -32f, 0f, 1, 33, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(202, 300).addBox(-21f, -32f, -15f, 1, 33, 15, 0f, false)

        door_exterior_5 = ModelMapper(modelDataWrapper)
        door_exterior_5.setPos(0f, 24f, 0f)
        door_exterior_5.texOffs(278, 29).addBox(-20f, 0f, -16f, 1, 7, 32, 0f, false)
        door_exterior_5.texOffs(0, 0).addBox(-21f, -34f, -48f, 1, 2, 96, 0f, false)

        door_left_exterior_5 = ModelMapper(modelDataWrapper)
        door_left_exterior_5.setPos(0f, 0f, 0f)
        door_exterior_5.addChild(door_left_exterior_5)
        door_left_exterior_5.texOffs(242, 311).addBox(-21f, -32f, 0f, 1, 33, 16, 0f, false)

        door_right_exterior_5 = ModelMapper(modelDataWrapper)
        door_right_exterior_5.setPos(0f, 0f, 0f)
        door_exterior_5.addChild(door_right_exterior_5)
        door_right_exterior_5.texOffs(202, 300).addBox(-21f, -32f, -16f, 1, 33, 16, 0f, false)

        door_window = ModelMapper(modelDataWrapper)
        door_window.setPos(0f, 24f, 0f)
        door_window.texOffs(202, 28).addBox(0f, 0f, -16f, 20, 1, 32, 0f, false)
        door_window.texOffs(0, 98).addBox(18f, -32f, -14f, 2, 32, 28, 0f, true)

        door_window_handrails = ModelMapper(modelDataWrapper)
        door_window_handrails.setPos(0f, 24f, 0f)
        door_window_handrails.texOffs(32, 98).addBox(7f, -15f, 16f, 11, 13, 0, 0f, false)
        door_window_handrails.texOffs(32, 98).addBox(7f, -15f, -16f, 11, 13, 0, 0f, false)
        door_window_handrails.texOffs(0, 0).addBox(7f, -36f, 16f, 0, 36, 0, 0.2f, false)
        door_window_handrails.texOffs(0, 0).addBox(7f, -36f, -16f, 0, 36, 0, 0.2f, false)
        door_window_handrails.texOffs(4, 0).addBox(7f, -36f, 0f, 0, 36, 0, 0.2f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 12f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 4f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -4f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -12f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 20f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 28f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 36f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, 44f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -44f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -36f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -28f, 4, 5, 0, 0f, false)
        door_window_handrails.texOffs(0, 75).addBox(5f, -32f, -20f, 4, 5, 0, 0f, false)

        handrail_6_r3 = ModelMapper(modelDataWrapper)
        handrail_6_r3.setPos(0f, 0f, 0f)
        door_window_handrails.addChild(handrail_6_r3)
        setRotationAngle(handrail_6_r3, -1.5708f, 0f, 0f)
        handrail_6_r3.texOffs(8, 0).addBox(7f, 16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_6_r3.texOffs(8, 0).addBox(7f, -48f, -32f, 0, 32, 0, 0.2f, false)
        handrail_6_r3.texOffs(8, 0).addBox(7f, -16f, -32f, 0, 32, 0, 0.2f, false)

        door_window_handrails_4 = ModelMapper(modelDataWrapper)
        door_window_handrails_4.setPos(0f, 24f, 0f)
        door_window_handrails_4.texOffs(0, 98).addBox(5f, -13f, 16f, 13, 11, 0, 0f, false)
        door_window_handrails_4.texOffs(0, 98).addBox(5f, -13f, -16f, 13, 11, 0, 0f, false)
        door_window_handrails_4.texOffs(0, 9).addBox(5.6f, -27.25f, -16f, 0, 15, 0, 0.2f, false)
        door_window_handrails_4.texOffs(0, 9).addBox(5.6f, -27.25f, 16f, 0, 15, 0, 0.2f, false)
        door_window_handrails_4.texOffs(0, 75).addBox(4.4f, -32f, 8f, 4, 5, 0, 0f, false)
        door_window_handrails_4.texOffs(0, 75).addBox(4.4f, -32f, 0f, 4, 5, 0, 0f, false)
        door_window_handrails_4.texOffs(0, 75).addBox(4.4f, -32f, -8f, 4, 5, 0, 0f, false)

        handrail_12_r1 = ModelMapper(modelDataWrapper)
        handrail_12_r1.setPos(0f, 0f, 0f)
        door_window_handrails_4.addChild(handrail_12_r1)
        setRotationAngle(handrail_12_r1, -1.5708f, 0f, 0f)
        handrail_12_r1.texOffs(8, 0).addBox(-3f, -48f, -32f, 0, 32, 0, 0.2f, false)
        handrail_12_r1.texOffs(8, 0).addBox(-3f, -16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_12_r1.texOffs(8, 0).addBox(-3f, 16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_12_r1.texOffs(8, 0).addBox(6.4f, -16f, -32f, 0, 32, 0, 0.2f, false)

        handrail_9_r1 = ModelMapper(modelDataWrapper)
        handrail_9_r1.setPos(6f, -28.25f, -16f)
        door_window_handrails_4.addChild(handrail_9_r1)
        setRotationAngle(handrail_9_r1, 0f, 0f, 0.1745f)
        handrail_9_r1.texOffs(0, 30).addBox(-0.25f, -8.25f, 32f, 0, 9, 0, 0.2f, false)
        handrail_9_r1.texOffs(0, 30).addBox(-0.25f, -8.25f, 0f, 0, 9, 0, 0.2f, false)

        handrail_7_r1 = ModelMapper(modelDataWrapper)
        handrail_7_r1.setPos(6f, -3.5f, -16f)
        door_window_handrails_4.addChild(handrail_7_r1)
        setRotationAngle(handrail_7_r1, 0f, 0f, -0.2182f)
        handrail_7_r1.texOffs(0, 30).addBox(1.5f, -8.25f, 32f, 0, 12, 0, 0.2f, false)
        handrail_7_r1.texOffs(0, 30).addBox(1.5f, -8.25f, 0f, 0, 12, 0, 0.2f, false)

        door_window_handrails_5 = ModelMapper(modelDataWrapper)
        door_window_handrails_5.setPos(0f, 24f, 0f)
        door_window_handrails_5.texOffs(0, 98).addBox(5f, -12.75f, 16f, 13, 13, 0, 0f, false)
        door_window_handrails_5.texOffs(0, 98).addBox(5f, -12.75f, -16f, 13, 13, 0, 0f, false)
        door_window_handrails_5.texOffs(0, 30).addBox(5.6f, -13f, -16f, 0, 11, 0, 0.2f, false)
        door_window_handrails_5.texOffs(0, 30).addBox(5.6f, -13f, 16f, 0, 11, 0, 0.2f, false)
        door_window_handrails_5.texOffs(0, 30).addBox(9.2f, -36.2738f, -11.5f, 0, 5, 0, 0.2f, false)
        door_window_handrails_5.texOffs(0, 30).addBox(9.2f, -36.2738f, 11.5f, 0, 5, 0, 0.2f, false)
        door_window_handrails_5.texOffs(0, 75).addBox(6.9f, -31.75f, 10f, 4, 5, 0, 0f, false)
        door_window_handrails_5.texOffs(0, 75).addBox(7.15f, -31.75f, 3.5f, 4, 5, 0, 0f, false)
        door_window_handrails_5.texOffs(0, 75).addBox(7.15f, -31.75f, -3.5f, 4, 5, 0, 0f, false)
        door_window_handrails_5.texOffs(0, 75).addBox(7.15f, -31.75f, -10f, 4, 5, 0, 0f, false)
        door_window_handrails_5.texOffs(32, 94).addBox(17.99f, -31.5f, -18f, 0, 18, 4, 0f, false)
        door_window_handrails_5.texOffs(32, 94).addBox(17.99f, -31.5f, 14f, 0, 18, 4, 0f, false)

        handrail_14_r1 = ModelMapper(modelDataWrapper)
        handrail_14_r1.setPos(0f, 0f, 0f)
        door_window_handrails_5.addChild(handrail_14_r1)
        setRotationAngle(handrail_14_r1, -1.5708f, 0f, 0f)
        handrail_14_r1.texOffs(8, 0).addBox(-3f, -48f, -32f, 0, 32, 0, 0.2f, false)
        handrail_14_r1.texOffs(8, 0).addBox(-3f, -16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_14_r1.texOffs(8, 0).addBox(-3f, 16f, -32f, 0, 32, 0, 0.2f, false)
        handrail_14_r1.texOffs(8, 0).addBox(9.15f, -16f, -31.25f, 0, 32, 0, 0.2f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(5.9238f, -16.714f, 16f)
        door_window_handrails_5.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, 0f, 0f, 0.0873f)
        handrail_8_r1.texOffs(0, 9).addBox(0f, -3.5f, 0f, 0, 7, 0, 0.2f, false)

        handrail_7_r2 = ModelMapper(modelDataWrapper)
        handrail_7_r2.setPos(8.5226f, -29.7676f, 16f)
        door_window_handrails_5.addChild(handrail_7_r2)
        setRotationAngle(handrail_7_r2, 0f, 0f, 0.3491f)
        handrail_7_r2.texOffs(0, 3).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        handrail_6_r4 = ModelMapper(modelDataWrapper)
        handrail_6_r4.setPos(7.0994f, -24.3136f, 16f)
        door_window_handrails_5.addChild(handrail_6_r4)
        setRotationAngle(handrail_6_r4, 0f, 0f, 0.2182f)
        handrail_6_r4.texOffs(0, 9).addBox(0f, -4f, 0f, 0, 8, 0, 0.2f, false)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(8.5935f, -30.2738f, -16f)
        door_window_handrails_5.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, 0.3491f)
        handrail_3_r2.texOffs(0, 3).addBox(0.1065f, -1f, 0f, 0, 3, 0, 0.2f, false)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(5.6f, -18.75f, -16f)
        door_window_handrails_5.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, 0f, 0f, 0.0873f)
        handrail_2_r2.texOffs(0, 9).addBox(0.5f, -1.5f, 0f, 0, 7, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(7.0761f, -24.3188f, -16f)
        door_window_handrails_5.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, 0f, 0f, 0.2182f)
        handrail_1_r1.texOffs(0, 9).addBox(0.0239f, -4f, 0f, 0, 8, 0, 0.2f, false)

        door_window_exterior = ModelMapper(modelDataWrapper)
        door_window_exterior.setPos(0f, 24f, 0f)
        door_window_exterior.texOffs(36, 263).addBox(19f, 0f, -16f, 1, 7, 32, 0f, true)
        door_window_exterior.texOffs(98, 0).addBox(20f, -32f, -14f, 0, 32, 28, 0f, true)

        roof = ModelMapper(modelDataWrapper)
        roof.setPos(0f, 24f, 0f)
        roof.texOffs(122, 0).addBox(-20f, -32f, -16f, 3, 0, 32, 0f, false)
        roof.texOffs(36, 36).addBox(-14f, -36f, -16f, 14, 0, 32, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-17f, -32f, 0f)
        roof.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.8727f)
        inner_roof_2_r1.texOffs(109, 98).addBox(0f, 0f, -16f, 6, 0, 32, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(0, 39).addBox(-20f, -36f, -16f, 0, 4, 32, 0f, false)
        roof_exterior.texOffs(242, 242).addBox(-17f, -39f, -16f, 17, 1, 32, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-19f, -37.7329f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -0.5236f)
        outer_roof_3_r1.texOffs(121, 98).addBox(0f, 0.001f, -16f, 3, 0, 32, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-20f, -36f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, -1.0472f)
        outer_roof_2_r1.texOffs(60, 0).addBox(0f, 0f, -16f, 2, 0, 32, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(202, 0).addBox(-17f, -39f, 16f, 34, 1, 27, 0f, false)
        roof_end_exterior.texOffs(266, 138).addBox(-10f, -2f, 43f, 20, 3, 5, 0f, false)

        bumper_2_r1 = ModelMapper(modelDataWrapper)
        bumper_2_r1.setPos(20f, 0f, 16f)
        roof_end_exterior.addChild(bumper_2_r1)
        setRotationAngle(bumper_2_r1, 0f, -0.3491f, 0f)
        bumper_2_r1.texOffs(202, 28).addBox(0f, -2f, 23f, 1, 3, 10, 0f, true)
        bumper_2_r1.texOffs(0, 225).addBox(0f, 0f, 0f, 0, 7, 23, 0f, true)

        bumper_1_r1 = ModelMapper(modelDataWrapper)
        bumper_1_r1.setPos(-20f, 0f, 16f)
        roof_end_exterior.addChild(bumper_1_r1)
        setRotationAngle(bumper_1_r1, 0f, 0.3491f, 0f)
        bumper_1_r1.texOffs(202, 28).addBox(-1f, -2f, 23f, 1, 3, 10, 0f, false)
        bumper_1_r1.texOffs(0, 225).addBox(0f, 0f, 0f, 0, 7, 23, 0f, false)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(20f, -36f, 16f)
        roof_end_exterior.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, -0.2967f, 0f, 1.0472f)
        outer_roof_6_r1.texOffs(69, 65).addBox(-7f, 0f, 0f, 7, 0, 29, 0f, true)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(18.981f, -37.6982f, 16.0159f)
        roof_end_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, -0.1745f, 0f, 0.5236f)
        outer_roof_5_r1.texOffs(70, 0).addBox(-11f, -0.0354f, -0.0226f, 11, 0, 28, 0f, true)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-18.981f, -37.6982f, 16.0159f)
        roof_end_exterior.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, -0.1745f, 0f, -0.5236f)
        outer_roof_3_r2.texOffs(70, 0).addBox(0f, -0.0354f, -0.0226f, 11, 0, 28, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-20f, -36f, 16f)
        roof_end_exterior.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, -0.2967f, 0f, -1.0472f)
        outer_roof_2_r2.texOffs(69, 65).addBox(0f, 0f, 0f, 7, 0, 29, 0f, false)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)
        roof_light.texOffs(122, 32).addBox(-12f, -35.5f, -16f, 2, 0, 32, 0f, false)

        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(-10f, -35.5f, 0f)
        roof_light.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 0f, -1.0472f)
        light_3_r1.texOffs(126, 32).addBox(0f, 0f, -16f, 1, 0, 32, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-12f, -35.5f, 0f)
        roof_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, 1.0472f)
        light_1_r1.texOffs(127, 98).addBox(-1f, 0f, -16f, 1, 0, 32, 0f, false)

        roof_light_5 = ModelMapper(modelDataWrapper)
        roof_light_5.setPos(0f, 24f, 0f)
        roof_light_5.texOffs(122, 32).addBox(-12f, -36.001f, -16f, 2, 0, 32, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(0, 98).addBox(-20f, 0f, -16f, 40, 1, 61, 0f, false)
        end.texOffs(168, 234).addBox(-20f, -32f, -18f, 2, 32, 34, 0f, false)
        end.texOffs(168, 234).addBox(18f, -32f, -18f, 2, 32, 34, 0f, true)
        end.texOffs(278, 68).addBox(-8f, -9f, 44f, 16, 9, 0, 0f, false)
        end.texOffs(21, 25).addBox(-20f, -32f, 16f, 3, 0, 3, 0f, false)
        end.texOffs(9, 0).addBox(-14f, -36f, 16f, 28, 0, 27, 0f, false)
        end.texOffs(21, 25).addBox(17f, -32f, 16f, 3, 0, 3, 0f, true)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(17f, -32f, 0f)
        end.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, 0.8727f)
        inner_roof_4_r1.texOffs(0, 0).addBox(-6f, 0f, 16f, 6, 0, 12, 0f, true)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-17f, -32f, 0f)
        end.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.8727f)
        inner_roof_2_r2.texOffs(0, 0).addBox(0f, 0f, 16f, 6, 0, 12, 0f, false)

        back_r1 = ModelMapper(modelDataWrapper)
        back_r1.setPos(0f, -9f, 46f)
        end.addChild(back_r1)
        setRotationAngle(back_r1, 0.0873f, 0f, 0f)
        back_r1.texOffs(274, 28).addBox(-9f, -30f, -2f, 18, 30, 0, 0f, false)

        wall_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        wall_diagonal_2_r1.setPos(20f, 0f, 16f)
        end.addChild(wall_diagonal_2_r1)
        setRotationAngle(wall_diagonal_2_r1, 0f, -0.3491f, 0f)
        wall_diagonal_2_r1.texOffs(0, 160).addBox(-2f, -36f, 0f, 0, 36, 32, 0f, true)

        wall_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        wall_diagonal_1_r1.setPos(-20f, 0f, 16f)
        end.addChild(wall_diagonal_1_r1)
        setRotationAngle(wall_diagonal_1_r1, 0f, 0.3491f, 0f)
        wall_diagonal_1_r1.texOffs(0, 160).addBox(2f, -36f, 0f, 0, 36, 32, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(36, 263).addBox(-20f, 0f, -16f, 1, 7, 32, 0f, false)
        end_exterior.texOffs(36, 263).addBox(19f, 0f, -16f, 1, 7, 32, 0f, true)
        end_exterior.texOffs(66, 158).addBox(-20f, -32f, -18f, 0, 32, 34, 0f, false)
        end_exterior.texOffs(66, 158).addBox(20f, -32f, -18f, 0, 32, 34, 0f, true)
        end_exterior.texOffs(266, 146).addBox(-10f, -9f, 46f, 20, 9, 0, 0f, false)

        back_r2 = ModelMapper(modelDataWrapper)
        back_r2.setPos(0f, -9f, 46f)
        end_exterior.addChild(back_r2)
        setRotationAngle(back_r2, 0.0873f, 0f, 0f)
        back_r2.texOffs(0, 334).addBox(-11f, -30f, -1f, 22, 30, 1, 0f, false)

        wall_diagonal_2_r2 = ModelMapper(modelDataWrapper)
        wall_diagonal_2_r2.setPos(20f, 0f, 16f)
        end_exterior.addChild(wall_diagonal_2_r2)
        setRotationAngle(wall_diagonal_2_r2, 0f, -0.3491f, 0f)
        wall_diagonal_2_r2.texOffs(136, 128).addBox(0f, -37f, 0f, 0, 37, 32, 0f, true)

        wall_diagonal_1_r2 = ModelMapper(modelDataWrapper)
        wall_diagonal_1_r2.setPos(-20f, 0f, 16f)
        end_exterior.addChild(wall_diagonal_1_r2)
        setRotationAngle(wall_diagonal_1_r2, 0f, 0.3491f, 0f)
        wall_diagonal_1_r2.texOffs(136, 128).addBox(0f, -37f, 0f, 0, 37, 32, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(141, 98).addBox(-20f, 0f, -16f, 40, 1, 32, 0f, false)
        head.texOffs(206, 131).addBox(-20f, -32f, -16f, 2, 32, 34, 0f, false)
        head.texOffs(96, 229).addBox(18f, -32f, -16f, 2, 32, 34, 0f, true)
        head.texOffs(274, 204).addBox(-20f, -36f, -16f, 40, 36, 0, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(98, 65).addBox(-20f, 0f, -46f, 40, 1, 30, 0f, false)
        head_exterior.texOffs(136, 300).addBox(-20f, 0f, -16f, 1, 7, 32, 0f, false)
        head_exterior.texOffs(136, 300).addBox(19f, 0f, -16f, 1, 7, 32, 0f, true)
        head_exterior.texOffs(0, 229).addBox(-20f, -32f, -16f, 0, 32, 34, 0f, false)
        head_exterior.texOffs(206, 200).addBox(20f, -32f, -16f, 0, 32, 34, 0f, true)
        head_exterior.texOffs(240, 275).addBox(-20f, -36f, -16f, 40, 36, 0, 0f, false)
        head_exterior.texOffs(22, 228).addBox(-10f, -14f, -46f, 20, 14, 1, 0f, false)
        head_exterior.texOffs(0, 302).addBox(-18f, -36f, -38f, 36, 0, 22, 0f, false)

        wall_diagonal_2_r3 = ModelMapper(modelDataWrapper)
        wall_diagonal_2_r3.setPos(20f, 0f, -16f)
        head_exterior.addChild(wall_diagonal_2_r3)
        setRotationAngle(wall_diagonal_2_r3, 0f, 0.3491f, 0f)
        wall_diagonal_2_r3.texOffs(288, 279).addBox(-2f, -39f, -32f, 2, 39, 32, 0f, true)

        wall_diagonal_1_r3 = ModelMapper(modelDataWrapper)
        wall_diagonal_1_r3.setPos(-20f, 0f, -16f)
        head_exterior.addChild(wall_diagonal_1_r3)
        setRotationAngle(wall_diagonal_1_r3, 0f, -0.3491f, 0f)
        wall_diagonal_1_r3.texOffs(288, 279).addBox(0f, -39f, -32f, 2, 39, 32, 0f, false)

        head_exterior_1 = ModelMapper(modelDataWrapper)
        head_exterior_1.setPos(0f, 24f, 0f)
        head_exterior_1.texOffs(0, 75).addBox(-10f, -39f, -46f, 20, 7, 9, 0f, false)

        front_r1 = ModelMapper(modelDataWrapper)
        front_r1.setPos(0f, -14f, -46f)
        head_exterior_1.addChild(front_r1)
        setRotationAngle(front_r1, -0.4189f, 0f, 0f)
        front_r1.texOffs(141, 131).addBox(-13f, -20f, 0f, 26, 20, 0, 0f, false)

        head_exterior_3_5 = ModelMapper(modelDataWrapper)
        head_exterior_3_5.setPos(0f, 24f, 0f)
        head_exterior_3_5.texOffs(0, 75).addBox(-10f, -39f, -44f, 20, 7, 9, 0f, false)

        front_r2 = ModelMapper(modelDataWrapper)
        front_r2.setPos(0f, -14f, -46f)
        head_exterior_3_5.addChild(front_r2)
        setRotationAngle(front_r2, -0.1134f, 0f, 0f)
        front_r2.texOffs(141, 131).addBox(-13f, -20f, 0f, 26, 20, 0, 0f, false)

        head_exterior_4 = ModelMapper(modelDataWrapper)
        head_exterior_4.setPos(0f, 24f, 0f)
        head_exterior_4.texOffs(0, 75).addBox(-10f, -38.8073f, -43.2695f, 20, 7, 9, 0f, false)

        front_middle_r1 = ModelMapper(modelDataWrapper)
        front_middle_r1.setPos(0f, -14f, -46f)
        head_exterior_4.addChild(front_middle_r1)
        setRotationAngle(front_middle_r1, -0.0262f, 0f, 0f)
        front_middle_r1.texOffs(141, 143).addBox(-13f, -8f, 0f, 26, 8, 0, 0f, false)

        front_top_r1 = ModelMapper(modelDataWrapper)
        front_top_r1.setPos(0f, -13.9405f, -46.0895f)
        head_exterior_4.addChild(front_top_r1)
        setRotationAngle(front_top_r1, -0.1614f, 0f, 0f)
        front_top_r1.texOffs(141, 133).addBox(-13f, -18f, -1f, 26, 10, 0, 0f, false)

        destination_board_r1 = ModelMapper(modelDataWrapper)
        destination_board_r1.setPos(3f, 0.143f, -0.2353f)
        head_exterior_4.addChild(destination_board_r1)
        setRotationAngle(destination_board_r1, -0.1309f, 0f, 0f)
        destination_board_r1.texOffs(28, 57).addBox(-13f, -33f, -47.75f, 20, 7, 0, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(72, 27).addBox(-3f, -5f, -3f, 6, 1, 6, 0f, false)
        seat.texOffs(18, 21).addBox(-2.5f, -16.5f, 4.5f, 5, 3, 1, 0f, false)

        back_right_r1 = ModelMapper(modelDataWrapper)
        back_right_r1.setPos(-3f, -5f, 2f)
        seat.addChild(back_right_r1)
        setRotationAngle(back_right_r1, -0.2618f, 0f, 0.0873f)
        back_right_r1.texOffs(24, 0).addBox(0f, -10f, 0f, 3, 10, 1, 0f, false)

        back_left_r1 = ModelMapper(modelDataWrapper)
        back_left_r1.setPos(3f, -5f, 2f)
        seat.addChild(back_left_r1)
        setRotationAngle(back_left_r1, -0.2618f, 0f, -0.0873f)
        back_left_r1.texOffs(24, 0).addBox(-3f, -10f, 0f, 3, 10, 1, 0f, true)

        seat_green = ModelMapper(modelDataWrapper)
        seat_green.setPos(0f, 24f, 0f)
        seat_green.texOffs(36, 32).addBox(-3f, -5f, -2.75f, 6, 1, 6, 0f, false)
        seat_green.texOffs(15, 41).addBox(-3f, -16.4216f, 4.008f, 6, 3, 1, 0f, false)
        seat_green.texOffs(45, 45).addBox(-3f, -8f, 2.25f, 6, 3, 1, 0f, false)

        back_right_r2 = ModelMapper(modelDataWrapper)
        back_right_r2.setPos(-3f, -5.1969f, 1.9953f)
        seat_green.addChild(back_right_r2)
        setRotationAngle(back_right_r2, -0.3054f, 0f, 0f)
        back_right_r2.texOffs(34, 43).addBox(0f, -8.75f, -0.6f, 3, 6, 1, 0f, false)
        back_right_r2.texOffs(34, 43).addBox(3f, -8.75f, -0.6f, 3, 6, 1, 0f, true)

        seat_purple = ModelMapper(modelDataWrapper)
        seat_purple.setPos(0f, 24f, 0f)
        seat_purple.texOffs(72, 27).addBox(-3f, -5f, -2.75f, 6, 1, 6, 0f, false)
        seat_purple.texOffs(17, 32).addBox(-3f, -16.4216f, 4.008f, 6, 3, 1, 0f, false)
        seat_purple.texOffs(18, 21).addBox(-3f, -8f, 2.25f, 6, 3, 1, 0f, false)

        back_right_r3 = ModelMapper(modelDataWrapper)
        back_right_r3.setPos(-3f, -5.1969f, 1.9953f)
        seat_purple.addChild(back_right_r3)
        setRotationAngle(back_right_r3, -0.3054f, 0f, 0f)
        back_right_r3.texOffs(24, 0).addBox(0f, -8.75f, -0.6f, 3, 6, 1, 0f, false)
        back_right_r3.texOffs(24, 0).addBox(3f, -8.75f, -0.6f, 3, 6, 1, 0f, true)

        vents_top = ModelMapper(modelDataWrapper)
        vents_top.setPos(0f, 24f, 0f)
        vents_top.texOffs(274, 169).addBox(-15f, -46f, 20f, 15, 7, 28, 0f, false)
        vents_top.texOffs(257, 103).addBox(-15f, -46f, -14f, 15, 7, 28, 0f, false)
        vents_top.texOffs(274, 169).addBox(-15f, -46f, -48f, 15, 7, 28, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        headlights.texOffs(12, 12).addBox(-7f, -6f, -46.1f, 4, 3, 0, 0f, false)
        headlights.texOffs(12, 12).addBox(3f, -6f, -46.1f, 4, 3, 0, 0f, true)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        tail_lights.texOffs(20, 12).addBox(-8f, -7f, 46.1f, 5, 4, 0, 0f, false)
        tail_lights.texOffs(20, 12).addBox(3f, -7f, 46.1f, 5, 4, 0, 0f, true)

        side_display = ModelMapper(modelDataWrapper)
        side_display.setPos(0f, 24f, 0f)
        side_display.texOffs(72, 315).addBox(-19.95f, -32f, -14f, 3, 6, 28, 0f, false)

        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(-16.95f, -29f, 0f)
        side_display.addChild(bottom_r1)
        setRotationAngle(bottom_r1, 0f, 0f, 0.1745f)
        bottom_r1.texOffs(106, 311).addBox(-1f, 0f, -14f, 1, 4, 28, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_5.setModelPart()
        door_left_5.setModelPart(door_5.name)
        door_right_5.setModelPart(door_5.name)
        door_handrails.setModelPart()
        door_handrails_4.setModelPart()
        door_handrails_5.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        door_exterior_5.setModelPart()
        door_left_exterior_5.setModelPart(door_exterior_5.name)
        door_right_exterior_5.setModelPart(door_exterior_5.name)
        door_window.setModelPart()
        door_window_handrails.setModelPart()
        door_window_handrails_4.setModelPart()
        door_window_handrails_5.setModelPart()
        door_window_exterior.setModelPart()
        roof.setModelPart()
        roof_exterior.setModelPart()
        roof_end_exterior.setModelPart()
        roof_light.setModelPart()
        roof_light_5.setModelPart()
        end.setModelPart()
        end_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        head_exterior_1.setModelPart()
        head_exterior_3_5.setModelPart()
        head_exterior_4.setModelPart()
        seat.setModelPart()
        seat_green.setModelPart()
        seat_purple.setModelPart()
        vents_top.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        side_display.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelLightRail {
        return ModelLightRail(phase, isRHT, doorAnimationType, renderDoorOverlay)
    }

    @Override
    override fun renderWindowPositions(
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
    ) {
        when (renderStage!!) {
            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    var flipSeat = false
                    var z = position - 24
                    while (z <= position + 24) {
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            15f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_green else seat_purple else seat,
                            matrices,
                            vertices,
                            light,
                            -8.5f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            -15f,
                            z.toFloat()
                        )
                        flipSeat = !flipSeat
                        z += 16
                    }
                }
            }

            RenderStage.EXTERIOR -> renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
            else -> {}
        }

        for (i in 0..1) {
            val roofPosition = position + i * 32 - 16
            when (renderStage!!) {
                RenderStage.LIGHTS -> renderMirror(
                    if (phase == 5) roof_light_5 else roof_light,
                    matrices,
                    vertices,
                    light,
                    roofPosition.toFloat()
                )

                RenderStage.INTERIOR -> if (renderDetails) {
                    renderMirror(roof, matrices, vertices, light, roofPosition.toFloat())
                }

                RenderStage.EXTERIOR -> renderMirror(roof_exterior, matrices, vertices, light, roofPosition.toFloat())
                else -> {}
            }
        }
    }

    @Override
    override fun renderDoorPositions(
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
    ) {
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(
                if (phase == 5) roof_light_5 else roof_light,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                if (isRHT) {
                    (if (phase == 5) door_left_5 else door_left).setOffset(0f, 0, doorRightZ)
                    (if (phase == 5) door_right_5 else door_right).setOffset(0f, 0, -doorRightZ)
                    renderOnce(if (phase == 5) door_5 else door, matrices, vertices, light, position.toFloat())
                    renderOnce(door_window, matrices, vertices, light, position.toFloat())
                    if (renderDetails) {
                        renderOnce(
                            if (phase >= 4) if (phase == 4 || phase == 6) door_handrails_4 else door_handrails_5 else door_handrails,
                            matrices,
                            vertices,
                            light,
                            position.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (phase == 4 || phase == 6) door_window_handrails_4 else door_window_handrails_5 else door_window_handrails,
                            matrices,
                            vertices,
                            light,
                            position.toFloat()
                        )
                    }
                } else {
                    (if (phase == 5) door_left_5 else door_left).setOffset(0f, 0, doorLeftZ)
                    (if (phase == 5) door_right_5 else door_right).setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(if (phase == 5) door_5 else door, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(door_window, matrices, vertices, light, position.toFloat())
                    if (renderDetails) {
                        renderOnceFlipped(
                            if (phase >= 4) if (phase == 4 || phase == 6) door_handrails_4 else door_handrails_5 else door_handrails,
                            matrices,
                            vertices,
                            light,
                            position.toFloat()
                        )
                        renderOnceFlipped(
                            if (phase >= 4) if (phase == 4 || phase == 6) door_window_handrails_4 else door_window_handrails_5 else door_window_handrails,
                            matrices,
                            vertices,
                            light,
                            position.toFloat()
                        )
                    }
                }
                if (renderDetails) {
                    renderMirror(roof, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                if (isRHT) {
                    (if (phase == 5) door_left_exterior_5 else door_left_exterior).setOffset(0f, 0, doorRightZ)
                    (if (phase == 5) door_right_exterior_5 else door_right_exterior).setOffset(0f, 0, -doorRightZ)
                    renderOnce(
                        if (phase == 5) door_exterior_5 else door_exterior,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    renderOnce(door_window_exterior, matrices, vertices, light, position.toFloat())
                } else {
                    (if (phase == 5) door_left_exterior_5 else door_left_exterior).setOffset(0f, 0, doorLeftZ)
                    (if (phase == 5) door_right_exterior_5 else door_right_exterior).setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(
                        if (phase == 5) door_exterior_5 else door_exterior,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    renderOnceFlipped(door_window_exterior, matrices, vertices, light, position.toFloat())
                }
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (position == 0) {
                    renderMirror(vents_top, matrices, vertices, light, position.toFloat())
                }
            }

            else -> {}
        }
    }

    @Override
    override fun renderHeadPosition1(
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
    ) {
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(
                if (phase == 5) roof_light_5 else roof_light,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(headlights, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    var flipSeat = false
                    var z = position - 8
                    while (z <= position + 8) {
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            15f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_green else seat_purple else seat,
                            matrices,
                            vertices,
                            light,
                            -8.5f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            -15f,
                            z.toFloat()
                        )
                        flipSeat = !flipSeat
                        z += 16
                    }
                    renderMirror(roof, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                when (phase) {
                    1, 2 -> renderOnce(head_exterior_1, matrices, vertices, light, position.toFloat())
                    3, 5, 7 -> renderOnce(head_exterior_3_5, matrices, vertices, light, position.toFloat())
                    4, 6 -> renderOnce(head_exterior_4, matrices, vertices, light, position.toFloat())
                    else -> {}
                }
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }

    @Override
    override fun renderHeadPosition2(
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
    ) {
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(
                if (phase == 5) roof_light_5 else roof_light,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(tail_lights, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    var flipSeat = false
                    var z = position - 8
                    while (z <= position + 8) {
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            15f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_green else seat_purple else seat,
                            matrices,
                            vertices,
                            light,
                            -8.5f,
                            z.toFloat()
                        )
                        renderOnce(
                            if (phase >= 4) if (flipSeat) seat_purple else seat_green else seat,
                            matrices,
                            vertices,
                            light,
                            -15f,
                            z.toFloat()
                        )
                        flipSeat = !flipSeat
                        z += 16
                    }
                    renderMirror(roof, matrices, vertices, light, position.toFloat())
                    if (isRHT) {
                        renderOnce(
                            side_display,
                            matrices,
                            vertices,
                            light,
                            (position - (if (phase <= 3) 64 else if (phase == 5) 0 else 1)).toFloat()
                        )
                    } else {
                        renderOnceFlipped(
                            side_display,
                            matrices,
                            vertices,
                            light,
                            (position - (if (phase <= 3) 64 else if (phase == 5) 0 else 1)).toFloat()
                        )
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }

    @Override
    override fun renderEndPosition1(
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
    ) {
        renderHeadPosition1(
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
            true
        )
    }

    @Override
    override fun renderEndPosition2(
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
    ) {
        renderHeadPosition2(
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
            false
        )
    }

    @Override
    override fun getModelDoorOverlay(): ModelDoorOverlay? {
        when (phase) {
            1, 2 -> return if (isRHT) MODEL_DOOR_OVERLAY_RHT else MODEL_DOOR_OVERLAY
            3, 7 -> return if (isRHT) MODEL_DOOR_OVERLAY_3_RHT else MODEL_DOOR_OVERLAY_3
            4, 6 -> return if (isRHT) MODEL_DOOR_OVERLAY_4_RHT else MODEL_DOOR_OVERLAY_4
            5 -> return if (isRHT) MODEL_DOOR_OVERLAY_5_RHT else MODEL_DOOR_OVERLAY_5
            else -> {}
        }
        return null
    }

    @Override
    override fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase? {
        return null
    }

    @Override
    override fun getWindowPositions(): IntArray? {
        return intArrayOf(-48, 48)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-96, 0, 96)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-128, 128)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    @Override
    override fun renderTextDisplays(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        font: Font?,
        immediate: RenderBufferSource?,
        thisRoute: Route?,
        nextRoute: Route?,
        thisStation: Station?,
        nextStation: Station?,
        lastStation: Station?,
        customDestination: String?,
        car: Int,
        totalCars: Int,
        atPlatform: Boolean,
        scrollingTexts: MutableList<ScrollingText?>?
    ) {
        val routeNumber: String = if (thisRoute == null) "" else thisRoute.lightRailRouteNumber!!
        val frontOffset =
            if (phase == 3 || phase == 5 || phase == 7) 2.75f else if (phase == 4 || phase == 6) 3.02f else 2.87f
        val color = if (phase == 3 || phase == 7) -0x5b01f9 else -0x6700

        renderFrontDestination(
            matrices,
            font,
            immediate,
            0f,
            0f,
            getEndPositions()!![0] / 16f - frontOffset,
            if (routeNumber.isEmpty()) 0f else -0.2f,
            if (phase == 4 || phase == 6) -2.14f else -2.18f,
            -0.01f,
            if (phase == 4 || phase == 6) -7.5f else 0f,
            0f,
            0.56f,
            0.26f,
            color,
            color,
            3f,
            getDestinationString(lastStation, customDestination, TextSpacingType.SPACE_CJK_FLIPPED, true),
            true,
            0,
            2
        )

        if (!routeNumber.isEmpty()) {
            renderFrontDestination(
                matrices,
                font,
                immediate,
                0f,
                0f,
                getEndPositions()!![0] / 16f - frontOffset,
                0.31f,
                if (phase == 4 || phase == 6) -2.14f else -2.15f,
                -0.01f,
                if (phase == 4 || phase == 6) -7.5f else 0f,
                0f,
                0.4f,
                0.26f,
                color,
                color,
                3f,
                routeNumber,
                false,
                0,
                2
            )
            renderFrontDestination(
                matrices,
                font,
                immediate,
                0f,
                0f,
                getEndPositions()!![0] / 16f - 2.92f,
                0f,
                if (phase == 1 || phase == 6) -2.13f else -2.24f,
                -0.01f,
                -5f,
                0f,
                0.38f,
                0.2f,
                color,
                color,
                3f,
                routeNumber,
                false,
                1,
                2
            )
        }

        val sideOffset = (128 - (if (phase <= 3) 64 else if (phase == 5) 0 else 1)) / 16f
        renderFrontDestination(
            matrices,
            font,
            immediate,
            if (isRHT) -1.26f else 1.26f,
            -1.76f,
            sideOffset - 0.3f,
            0f,
            0f,
            0f,
            0f,
            (if (isRHT) 90 else -90).toFloat(),
            0.56f,
            0.26f,
            color,
            color,
            3f,
            getDestinationString(lastStation, customDestination, TextSpacingType.SPACE_CJK_FLIPPED, true),
            true,
            0,
            2
        )
        renderFrontDestination(
            matrices, font, immediate,
            if (isRHT) -1.26f else 1.26f, -1.73f, sideOffset + 0.42f, 0f, 0f, 0f,
            0f, (if (isRHT) 90 else -90).toFloat(), 0.4f, 0.26f,
            color, color, 3f, routeNumber, false, 0, 2
        )
        renderFrontDestination(
            matrices,
            font,
            immediate,
            if (isRHT) -1.05f else 1.05f,
            -1.89f,
            sideOffset,
            0f,
            0f,
            0f,
            0f,
            (if (isRHT) -90 else 90).toFloat(),
            1.2f,
            0.08f,
            color,
            color,
            1f,
            ((if (routeNumber.isEmpty()) "" else routeNumber.toString() + "|") + getDestinationString(
                lastStation,
                customDestination,
                TextSpacingType.SPACE_CJK,
                true
            )).replace("|", "  "),
            false,
            0,
            2
        )

        val station = if (atPlatform) thisStation else nextStation
        if (station != null) {
            val stationString: String? = getDestinationString(station, null, TextSpacingType.SPACE_CJK_LARGE, true)
            val dynamicResource = ClientData.DATA_CACHE.getPixelatedText(stationString, -0x6700, 300, 0f, true)
            val vertexConsumer =
                vertexConsumers!!.getBuffer(MoreRenderLayers.getLight(dynamicResource.resourceLocation, true))
            matrices!!.pushPose()
            UtilitiesClient.rotateYDegrees(matrices, 180f)
            matrices.translate(-0.35f, -2.2f, 8.99f)
            RouteMapGenerator.scrollTextLightRail(
                matrices,
                vertexConsumer,
                // Keep Java regex splitting and trailing-empty handling used by the original model.
                (stationString as java.lang.String).split("\\|").size,
                0.7f,
                0.07f,
                dynamicResource.width,
                dynamicResource.height
            )
            matrices.popPose()
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "不載客|Not in Service"
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_left.png",
            "door_overlay_light_rail_right.png",
            true,
            false
        )
        private val MODEL_DOOR_OVERLAY_RHT = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_left.png",
            "door_overlay_light_rail_right.png",
            false,
            true
        )
        private val MODEL_DOOR_OVERLAY_3 = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_3_left.png",
            "door_overlay_light_rail_3_right.png",
            true,
            false
        )
        private val MODEL_DOOR_OVERLAY_3_RHT = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_3_left.png",
            "door_overlay_light_rail_3_right.png",
            false,
            true
        )
        private val MODEL_DOOR_OVERLAY_4 = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_4_left.png",
            "door_overlay_light_rail_4_right.png",
            true,
            false
        )
        private val MODEL_DOOR_OVERLAY_4_RHT = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_4_left.png",
            "door_overlay_light_rail_4_right.png",
            false,
            true
        )
        private val MODEL_DOOR_OVERLAY_5 = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_5_left.png",
            "door_overlay_light_rail_5_right.png",
            true,
            false
        )
        private val MODEL_DOOR_OVERLAY_5_RHT = ModelDoorOverlay(
            DOOR_MAX,
            0f,
            14,
            "door_overlay_light_rail_5_left.png",
            "door_overlay_light_rail_5_right.png",
            false,
            true
        )

        private fun getDoorAnimationType(phase: Int): DoorAnimationType {
            when (phase) {
                1, 6 -> return DoorAnimationType.BOUNCY_2
                4 -> return DoorAnimationType.STANDARD_SLOW
                else -> return DoorAnimationType.STANDARD
            }
        }
    }
}
