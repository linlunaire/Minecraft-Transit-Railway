package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.client.ScrollingText
import mtr.data.Route
import mtr.data.Station
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import net.minecraft.client.gui.Font

open class ModelR211 protected constructor(
    @JvmField protected val openGangway: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelR211?>(doorAnimationType, renderDoorOverlay) {
    private val window_exterior: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val headrail_right: ModelMapper
    private val handrail_turn_1_r1: ModelMapper
    private val handrail_middle_4_r1: ModelMapper
    private val handrail_middle_3_r1: ModelMapper
    private val handrail_middle_2_r1: ModelMapper
    private val handrail_turn_1_r2: ModelMapper
    private val headrail_left: ModelMapper
    private val handrail_turn_1_r3: ModelMapper
    private val handrail_middle_4_r2: ModelMapper
    private val handrail_middle_3_r2: ModelMapper
    private val handrail_middle_2_r2: ModelMapper
    private val handrail_turn_1_r4: ModelMapper
    private val handrail_mid: ModelMapper
    private val handrail_middle_4_r3: ModelMapper
    private val handrail_middle_3_r3: ModelMapper
    private val handrail_middle_2_r3: ModelMapper
    private val handrail_turn_1_r5: ModelMapper
    private val headrail_up: ModelMapper
    private val handrail_up_3_r1: ModelMapper
    private val handrail_top_1_r1: ModelMapper
    private val handrail_top_2_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_bottom_r1: ModelMapper
    private val seat_back_3_r1: ModelMapper
    private val window_exterior_end: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val window: ModelMapper
    private val wall_1_r1: ModelMapper
    private val side_panel: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val end_bottom_out: ModelMapper
    private val buttom_panel_right_2_r1: ModelMapper
    private val buttom_panel_left_2_r1: ModelMapper
    private val end_back: ModelMapper
    private val front_right_panel_4_r1: ModelMapper
    private val front_right_panel_3_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val end_gangway: ModelMapper
    private val end_gangway_exterior: ModelMapper
    private val upper_wall_3_r1: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val roof_end_gangway_exterior: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_6_r2: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_6_r3: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val door: ModelMapper
    private val door_edge_4_r1: ModelMapper
    private val door_edge_3_r1: ModelMapper
    private val door_edge_2_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_left: ModelMapper
    private val door_side_top_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_sides: ModelMapper
    private val door_side_top_2_r1: ModelMapper
    private val door_side_top_1_r1: ModelMapper
    private val door_end_exterior: ModelMapper
    private val door_right_exterior_end: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val door_left_exterior_end: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_sides_end: ModelMapper
    private val door_side_top_3_r1: ModelMapper
    private val door_side_top_2_r2: ModelMapper
    private val handrail_door: ModelMapper
    private val handrail_curve: ModelMapper
    private val handrail_curve_12_r1: ModelMapper
    private val handrail_curve_10_r1: ModelMapper
    private val handrail_curve_9_r1: ModelMapper
    private val handrail_curve_7_r1: ModelMapper
    private val handrail_curve_6_r1: ModelMapper
    private val handrail_curve_5_r1: ModelMapper
    private val handrail_curve_3_r1: ModelMapper
    private val handrail_curve_11_r1: ModelMapper
    private val handrail_curve_9_r2: ModelMapper
    private val handrail_curve_8_r1: ModelMapper
    private val handrail_curve_5_r2: ModelMapper
    private val handrail_curve_4_r1: ModelMapper
    private val handrail_curve_2_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r6: ModelMapper
    private val upper_wall_1_r5: ModelMapper
    private val bumper: ModelMapper
    private val bumper_2_r1: ModelMapper
    private val bumper_1_r1: ModelMapper
    private val head_back: ModelMapper
    private val front_right_panel_3_r2: ModelMapper
    private val front_right_panel_2_r1: ModelMapper
    private val roof_head_exterior: ModelMapper
    private val outer_roof_7_r2: ModelMapper
    private val outer_roof_6_r4: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_6_r5: ModelMapper
    private val outer_roof_5_r6: ModelMapper
    private val outer_roof_4_r6: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_5_r7: ModelMapper
    private val outer_roof_4_r7: ModelMapper
    private val outer_roof_3_r7: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_9_r1: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_end: ModelMapper
    private val side_1: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val side_2: ModelMapper
    private val inner_roof_7_r1: ModelMapper
    private val inner_roof_6_r2: ModelMapper
    private val mid_roof: ModelMapper
    private val roof_end_gangway: ModelMapper
    private val mid_roof_gangway: ModelMapper
    private val roof_light: ModelMapper
    private val destination_display_end_interior: ModelMapper
    private val display_6_r1: ModelMapper
    private val display_5_r1: ModelMapper
    private val display_4_r1: ModelMapper
    private val display_3_r1: ModelMapper
    private val roof_head: ModelMapper
    private val inner_roof_6_r3: ModelMapper
    private val inner_roof_3_r3: ModelMapper
    private val destination_display: ModelMapper
    private val display_7_r1: ModelMapper
    private val display_6_r2: ModelMapper
    private val headlights: ModelMapper
    private val headlights_2_r1: ModelMapper
    private val headlights_1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_lights_2_r1: ModelMapper
    private val tail_lights_1_r1: ModelMapper
    private val door_light_interior_off: ModelMapper
    private val light_2_r1: ModelMapper
    private val door_light_interior_on: ModelMapper
    private val light_3_r1: ModelMapper

    constructor(openGangway: Boolean) : this(openGangway, DoorAnimationType.R211, true)

    init {
        val textureWidth = 360
        val textureHeight = 360

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(0, 212).addBox(-21.5f, 0f, -24f, 1, 3, 48, 0f, false)
        window_exterior.texOffs(112, 0).addBox(-21.5f, -13f, -27f, 1, 13, 54, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-21.5f, -13f, 0f)
        window_exterior.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1047f)
        upper_wall_r1.texOffs(56, 23).addBox(0f, -23f, -27f, 1, 23, 54, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)


        headrail_right = ModelMapper(modelDataWrapper)
        headrail_right.setPos(0f, 0f, 0f)
        window_handrails.addChild(headrail_right)
        headrail_right.texOffs(343, 0).addBox(-19.5f, -4.5f, 24f, 8, 0, 0, 0.2f, false)

        handrail_turn_1_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r1.setPos(-10.9858f, -31.518f, 23.5636f)
        headrail_right.addChild(handrail_turn_1_r1)
        setRotationAngle(handrail_turn_1_r1, -0.7854f, 0f, -0.1745f)
        handrail_turn_1_r1.texOffs(343, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        handrail_middle_4_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_4_r1.setPos(-10.1951f, -27.0336f, 24f)
        headrail_right.addChild(handrail_middle_4_r1)
        setRotationAngle(handrail_middle_4_r1, 0f, 0f, -0.1745f)
        handrail_middle_4_r1.texOffs(359, 27).addBox(0f, -4f, 0f, 0, 8, 0, 0.2f, false)

        handrail_middle_3_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_3_r1.setPos(-9.3199f, -21.2212f, 24f)
        headrail_right.addChild(handrail_middle_3_r1)
        setRotationAngle(handrail_middle_3_r1, 0f, 0f, -0.0873f)
        handrail_middle_3_r1.texOffs(342, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        handrail_middle_2_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_2_r1.setPos(-9.7993f, -12.3899f, 24f)
        headrail_right.addChild(handrail_middle_2_r1)
        setRotationAngle(handrail_middle_2_r1, 0f, 0f, 0.0873f)
        handrail_middle_2_r1.texOffs(359, 30).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        handrail_turn_1_r2 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r2.setPos(-11.3f, -4.3f, 0f)
        headrail_right.addChild(handrail_turn_1_r2)
        setRotationAngle(handrail_turn_1_r2, 0f, 0f, 0.8727f)
        handrail_turn_1_r2.texOffs(343, 0).addBox(-0.2f, -1.2f, 24f, 0, 1, 0, 0.2f, false)

        headrail_left = ModelMapper(modelDataWrapper)
        headrail_left.setPos(0f, 0f, 0f)
        window_handrails.addChild(headrail_left)
        headrail_left.texOffs(343, 0).addBox(-19.5f, -4.5f, -24f, 8, 0, 0, 0.2f, false)

        handrail_turn_1_r3 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r3.setPos(-10.9858f, -31.518f, -23.5636f)
        headrail_left.addChild(handrail_turn_1_r3)
        setRotationAngle(handrail_turn_1_r3, 0.7854f, 0f, -0.1745f)
        handrail_turn_1_r3.texOffs(343, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        handrail_middle_4_r2 = ModelMapper(modelDataWrapper)
        handrail_middle_4_r2.setPos(-10.1951f, -27.0336f, -24f)
        headrail_left.addChild(handrail_middle_4_r2)
        setRotationAngle(handrail_middle_4_r2, 0f, 0f, -0.1745f)
        handrail_middle_4_r2.texOffs(359, 27).addBox(0f, -4f, 0f, 0, 8, 0, 0.2f, false)

        handrail_middle_3_r2 = ModelMapper(modelDataWrapper)
        handrail_middle_3_r2.setPos(-9.3199f, -21.2212f, -24f)
        headrail_left.addChild(handrail_middle_3_r2)
        setRotationAngle(handrail_middle_3_r2, 0f, 0f, -0.0873f)
        handrail_middle_3_r2.texOffs(342, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        handrail_middle_2_r2 = ModelMapper(modelDataWrapper)
        handrail_middle_2_r2.setPos(-9.7993f, -12.3899f, -24f)
        headrail_left.addChild(handrail_middle_2_r2)
        setRotationAngle(handrail_middle_2_r2, 0f, 0f, 0.0873f)
        handrail_middle_2_r2.texOffs(359, 30).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        handrail_turn_1_r4 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r4.setPos(-11.3f, -4.3f, 0f)
        headrail_left.addChild(handrail_turn_1_r4)
        setRotationAngle(handrail_turn_1_r4, 0f, 0f, 0.8727f)
        handrail_turn_1_r4.texOffs(343, 0).addBox(-0.2f, -1.2f, -24f, 0, 1, 0, 0.2f, false)

        handrail_mid = ModelMapper(modelDataWrapper)
        handrail_mid.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_mid)
        handrail_mid.texOffs(343, 0).addBox(-16.5f, -4.5f, -3.5f, 5, 0, 0, 0.2f, false)

        handrail_middle_4_r3 = ModelMapper(modelDataWrapper)
        handrail_middle_4_r3.setPos(-10.5424f, -29.0032f, -3.5f)
        handrail_mid.addChild(handrail_middle_4_r3)
        setRotationAngle(handrail_middle_4_r3, 0f, 0f, -0.1745f)
        handrail_middle_4_r3.texOffs(359, 27).addBox(0f, -3f, 0f, 0, 9, 0, 0.2f, false)

        handrail_middle_3_r3 = ModelMapper(modelDataWrapper)
        handrail_middle_3_r3.setPos(-9.3199f, -21.2212f, -3.5f)
        handrail_mid.addChild(handrail_middle_3_r3)
        setRotationAngle(handrail_middle_3_r3, 0f, 0f, -0.0873f)
        handrail_middle_3_r3.texOffs(342, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        handrail_middle_2_r3 = ModelMapper(modelDataWrapper)
        handrail_middle_2_r3.setPos(-9.7993f, -12.3899f, -3.5f)
        handrail_mid.addChild(handrail_middle_2_r3)
        setRotationAngle(handrail_middle_2_r3, 0f, 0f, 0.0873f)
        handrail_middle_2_r3.texOffs(359, 30).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        handrail_turn_1_r5 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r5.setPos(-11.3f, -4.3f, 0f)
        handrail_mid.addChild(handrail_turn_1_r5)
        setRotationAngle(handrail_turn_1_r5, 0f, 0f, 0.8727f)
        handrail_turn_1_r5.texOffs(343, 0).addBox(-0.2f, -1.2f, -3.5f, 0, 1, 0, 0.2f, false)

        headrail_up = ModelMapper(modelDataWrapper)
        headrail_up.setPos(0f, 0f, 0f)
        window_handrails.addChild(headrail_up)
        headrail_up.texOffs(343, 0).addBox(-10.6132f, -35.3491f, 0f, 0, 1, 0, 0.2f, false)
        headrail_up.texOffs(343, 0).addBox(-10.6132f, -35.3491f, -20f, 0, 1, 0, 0.2f, false)
        headrail_up.texOffs(343, 0).addBox(-10.6132f, -35.3491f, 20f, 0, 1, 0, 0.2f, false)

        handrail_up_3_r1 = ModelMapper(modelDataWrapper)
        handrail_up_3_r1.setPos(-10.8185f, -33.002f, 0f)
        headrail_up.addChild(handrail_up_3_r1)
        setRotationAngle(handrail_up_3_r1, 0f, 0f, 0.1745f)
        handrail_up_3_r1.texOffs(343, 0).addBox(0f, -1f, 20f, 0, 2, 0, 0.2f, false)
        handrail_up_3_r1.texOffs(343, 0).addBox(0f, -1f, -20f, 0, 2, 0, 0.2f, false)
        handrail_up_3_r1.texOffs(343, 0).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_top_1_r1 = ModelMapper(modelDataWrapper)
        handrail_top_1_r1.setPos(-11.0616f, -31.9478f, -11.5101f)
        headrail_up.addChild(handrail_top_1_r1)
        setRotationAngle(handrail_top_1_r1, -1.5708f, 0f, -0.1745f)
        handrail_top_1_r1.texOffs(359, 30).addBox(0f, -11.5f, 0f, 0, 23, 0, 0.2f, false)

        handrail_top_2_r1 = ModelMapper(modelDataWrapper)
        handrail_top_2_r1.setPos(-11.0616f, -31.9478f, 11.5101f)
        headrail_up.addChild(handrail_top_2_r1)
        setRotationAngle(handrail_top_2_r1, -1.5708f, 0f, -0.1745f)
        handrail_top_2_r1.texOffs(359, 30).addBox(0f, -11.5f, 0f, 0, 23, 0, 0.2f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat)
        seat.texOffs(122, 168).addBox(-19.9f, -10.75f, -24f, 2, 5, 48, 0f, false)
        seat.texOffs(175, 174).addBox(-19.55f, -6f, -23.5f, 3, 4, 47, 0f, false)

        seat_bottom_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_r1.setPos(0f, -1.75f, 2.5f)
        seat.addChild(seat_bottom_r1)
        setRotationAngle(seat_bottom_r1, 0f, 0f, -0.0873f)
        seat_bottom_r1.texOffs(56, 167).addBox(-19.9f, -6f, -26.5f, 9, 1, 48, 0f, false)

        seat_back_3_r1 = ModelMapper(modelDataWrapper)
        seat_back_3_r1.setPos(-17.9f, -10.75f, 2.5f)
        seat.addChild(seat_back_3_r1)
        setRotationAngle(seat_back_3_r1, 0f, 0f, -0.1309f)
        seat_back_3_r1.texOffs(168, 0).addBox(-2f, -5f, -26.5f, 2, 5, 48, 0f, false)

        window_exterior_end = ModelMapper(modelDataWrapper)
        window_exterior_end.setPos(0f, 24f, 0f)
        window_exterior_end.texOffs(192, 53).addBox(-21.5f, 0f, -24f, 1, 3, 48, 0f, false)
        window_exterior_end.texOffs(192, 53).addBox(20.5f, 0f, -24f, 1, 3, 48, 0f, true)
        window_exterior_end.texOffs(82, 100).addBox(-21.5f, -13f, -27f, 1, 13, 54, 0f, false)
        window_exterior_end.texOffs(82, 100).addBox(20.5f, -13f, -27f, 1, 13, 54, 0f, true)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-9.3302f, -9.7596f, 0f)
        window_exterior_end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, -0.1047f)
        upper_wall_2_r1.texOffs(0, 0).addBox(30f, -23f, -27f, 1, 23, 54, 0f, true)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(-21.5f, -13f, 0f)
        window_exterior_end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, 0.1047f)
        upper_wall_1_r1.texOffs(0, 0).addBox(0f, -23f, -27f, 1, 23, 54, 0f, false)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 100).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(0, 149).addBox(-21.5f, -13f, -25f, 2, 13, 50, 0f, false)

        wall_1_r1 = ModelMapper(modelDataWrapper)
        wall_1_r1.setPos(-21.5f, -13f, 0f)
        window.addChild(wall_1_r1)
        setRotationAngle(wall_1_r1, 0f, 0f, 0.1047f)
        wall_1_r1.texOffs(138, 67).addBox(0f, -21f, -25f, 2, 21, 50, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(0, 314).addBox(-20f, -32f, 0f, 11, 29, 0, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(149, 225).addBox(-20.5f, 0f, -9f, 41, 1, 17, 0f, false)
        end.texOffs(285, 296).addBox(-7f, -33f, -9f, 14, 33, 0, 0f, false)
        end.texOffs(114, 294).addBox(7f, -33f, -9f, 13, 33, 1, 0f, true)
        end.texOffs(114, 294).addBox(-20f, -33f, -9f, 13, 33, 1, 0f, false)
        end.texOffs(48, 40).addBox(19.5f, -13f, 8f, 2, 13, 1, 0f, false)
        end.texOffs(48, 40).addBox(-21.5f, -13f, 8f, 2, 13, 1, 0f, true)
        end.texOffs(122, 167).addBox(-20.5f, -34f, -8f, 3, 21, 16, 0f, false)
        end.texOffs(122, 167).addBox(17.5f, -34f, -8f, 3, 21, 16, 0f, true)
        end.texOffs(168, 0).addBox(-19.5f, -13f, -8f, 5, 13, 16, 0f, false)
        end.texOffs(168, 0).addBox(14.5f, -13f, -8f, 5, 13, 16, 0f, true)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-21.5f, -13f, 0f)
        end.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1047f)
        upper_wall_2_r2.texOffs(14, 90).addBox(0f, -21f, 8f, 2, 21, 1, 0f, true)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(21.5f, -13f, 0f)
        end.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1047f)
        upper_wall_1_r2.texOffs(14, 90).addBox(-2f, -21f, 8f, 2, 21, 1, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(147, 81).addBox(20.5f, 0f, -11f, 1, 3, 19, 0f, false)
        end_exterior.texOffs(0, 18).addBox(19.5f, -13f, -11f, 2, 13, 22, 0f, false)
        end_exterior.texOffs(147, 81).addBox(-21.5f, 0f, -11f, 1, 3, 19, 0f, true)
        end_exterior.texOffs(0, 18).addBox(-21.5f, -13f, -11f, 2, 13, 22, 0f, true)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-21.5f, -13f, 0f)
        end_exterior.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1047f)
        upper_wall_2_r3.texOffs(0, 95).addBox(0f, -23f, -11f, 2, 23, 22, 0f, true)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(21.5f, -13f, 0f)
        end_exterior.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1047f)
        upper_wall_1_r3.texOffs(0, 95).addBox(-2f, -23f, -11f, 2, 23, 22, 0f, false)

        end_bottom_out = ModelMapper(modelDataWrapper)
        end_bottom_out.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_bottom_out)
        end_bottom_out.texOffs(54, 191).addBox(-0.057f, 0f, -14.3646f, 9, 3, 5, 0f, false)
        end_bottom_out.texOffs(54, 191).addBox(-8.943f, 0f, -14.3646f, 9, 3, 5, 0f, true)

        buttom_panel_right_2_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_right_2_r1.setPos(-21.5f, 0f, -11f)
        end_bottom_out.addChild(buttom_panel_right_2_r1)
        setRotationAngle(buttom_panel_right_2_r1, 0f, 0.2618f, 0f)
        buttom_panel_right_2_r1.texOffs(0, 140).addBox(0f, 0f, 0f, 13, 3, 3, 0f, true)

        buttom_panel_left_2_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_left_2_r1.setPos(21.5f, 0f, -11f)
        end_bottom_out.addChild(buttom_panel_left_2_r1)
        setRotationAngle(buttom_panel_left_2_r1, 0f, -0.2618f, 0f)
        buttom_panel_left_2_r1.texOffs(0, 140).addBox(-13f, 0f, 0f, 13, 3, 3, 0f, false)

        end_back = ModelMapper(modelDataWrapper)
        end_back.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_back)
        end_back.texOffs(158, 0).addBox(-8f, -33f, -13f, 1, 33, 3, 0f, false)
        end_back.texOffs(158, 0).addBox(7f, -33f, -13f, 1, 33, 3, 0f, true)
        end_back.texOffs(138, 103).addBox(-8f, -42f, -13f, 16, 9, 4, 0f, false)
        end_back.texOffs(86, 291).addBox(-7f, -33f, -11f, 14, 33, 0, 0f, false)

        front_right_panel_4_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r1.setPos(8f, -42f, -13f)
        end_back.addChild(front_right_panel_4_r1)
        setRotationAngle(front_right_panel_4_r1, 0f, -0.192f, 0f)
        front_right_panel_4_r1.texOffs(58, 291).addBox(0f, 0f, 0f, 14, 42, 0, 0f, true)

        front_right_panel_3_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r1.setPos(-8f, -42f, -13f)
        end_back.addChild(front_right_panel_3_r1)
        setRotationAngle(front_right_panel_3_r1, 0f, 0.192f, 0f)
        front_right_panel_3_r1.texOffs(58, 291).addBox(-14f, 0f, 0f, 14, 42, 0, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 0f, 0f)
        end_exterior.addChild(roof_end_exterior)
        roof_end_exterior.texOffs(105, 100).addBox(-4f, -41.375f, -15f, 4, 0, 23, 0f, false)
        roof_end_exterior.texOffs(105, 100).addBox(0f, -41.375f, -15f, 4, 0, 23, 0f, true)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(4f, -41.375f, 0f)
        roof_end_exterior.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, 0.0873f)
        outer_roof_6_r1.texOffs(119, 0).addBox(0f, 0f, -15f, 6, 0, 23, 0f, true)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(12.392f, -40.205f, 0f)
        roof_end_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, 0.2618f)
        outer_roof_5_r1.texOffs(23, 77).addBox(-2.5f, 0f, -15f, 5, 0, 23, 0f, true)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(16.2163f, -39.0449f, 0f)
        roof_end_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, 0.3491f)
        outer_roof_4_r1.texOffs(105, 123).addBox(-1.5f, 0f, -15f, 3, 0, 23, 0f, true)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(18.2687f, -37.7659f, 0f)
        roof_end_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, 0.8727f)
        outer_roof_3_r1.texOffs(131, 0).addBox(-1f, 0f, -15f, 2, 0, 23, 0f, true)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(18.6114f, -35.9228f, 0f)
        roof_end_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 1.3788f)
        outer_roof_2_r1.texOffs(166, 285).addBox(-1f, -0.5f, -15f, 2, 1, 23, 0f, true)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-4f, -41.375f, 0f)
        roof_end_exterior.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.0873f)
        outer_roof_5_r2.texOffs(119, 0).addBox(-6f, 0f, -15f, 6, 0, 23, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-12.392f, -40.205f, 0f)
        roof_end_exterior.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.2618f)
        outer_roof_4_r2.texOffs(23, 77).addBox(-2.5f, 0f, -15f, 5, 0, 23, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.2163f, -39.0449f, 0f)
        roof_end_exterior.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -0.3491f)
        outer_roof_3_r2.texOffs(105, 123).addBox(-1.5f, 0f, -15f, 3, 0, 23, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-18.2687f, -37.7659f, 0f)
        roof_end_exterior.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, -0.8727f)
        outer_roof_2_r2.texOffs(131, 0).addBox(-1f, 0f, -15f, 2, 0, 23, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-18.6114f, -35.9228f, 0f)
        roof_end_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, -1.3788f)
        outer_roof_1_r1.texOffs(166, 285).addBox(-1f, -0.5f, -15f, 2, 1, 23, 0f, false)

        end_gangway = ModelMapper(modelDataWrapper)
        end_gangway.setPos(0f, 24f, 0f)
        end_gangway.texOffs(50, 221).addBox(-20.5f, 0f, -9f, 41, 1, 17, 0f, false)
        end_gangway.texOffs(0, 263).addBox(8.5f, -32.875f, -9f, 11, 33, 18, 0f, false)
        end_gangway.texOffs(131, 243).addBox(-19.5f, -32.875f, -9f, 11, 33, 18, 0f, false)

        end_gangway_exterior = ModelMapper(modelDataWrapper)
        end_gangway_exterior.setPos(0f, 24f, 0f)
        end_gangway_exterior.texOffs(303, 211).addBox(20.5f, 0f, -9f, 1, 3, 17, 0f, false)
        end_gangway_exterior.texOffs(54, 149).addBox(19.5f, -13f, -9f, 2, 13, 20, 0f, false)
        end_gangway_exterior.texOffs(303, 211).addBox(-21.5f, 0f, -9f, 1, 3, 17, 0f, true)
        end_gangway_exterior.texOffs(54, 149).addBox(-21.5f, -13f, -9f, 2, 13, 20, 0f, true)
        end_gangway_exterior.texOffs(231, 301).addBox(8.5f, -33f, -9f, 12, 33, 0, 0f, false)
        end_gangway_exterior.texOffs(231, 301).addBox(-20.5f, -33f, -9f, 12, 33, 0, 0f, true)
        end_gangway_exterior.texOffs(192, 104).addBox(-20f, -41.875f, -9f, 20, 9, 0, 0f, false)
        end_gangway_exterior.texOffs(192, 104).addBox(0f, -41.875f, -9f, 20, 9, 0, 0f, true)

        upper_wall_3_r1 = ModelMapper(modelDataWrapper)
        upper_wall_3_r1.setPos(-21.5f, -13f, 0f)
        end_gangway_exterior.addChild(upper_wall_3_r1)
        setRotationAngle(upper_wall_3_r1, 0f, 0f, 0.1047f)
        upper_wall_3_r1.texOffs(0, 149).addBox(0f, -23f, -9f, 2, 23, 20, 0f, true)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(21.5f, -13f, 0f)
        end_gangway_exterior.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, -0.1047f)
        upper_wall_2_r4.texOffs(0, 149).addBox(-2f, -23f, -9f, 2, 23, 20, 0f, false)

        roof_end_gangway_exterior = ModelMapper(modelDataWrapper)
        roof_end_gangway_exterior.setPos(0f, 0f, 0f)
        end_gangway_exterior.addChild(roof_end_gangway_exterior)
        roof_end_gangway_exterior.texOffs(50, 239).addBox(-4f, -41.375f, -9f, 4, 1, 17, 0f, false)
        roof_end_gangway_exterior.texOffs(50, 239).addBox(0f, -41.375f, -9f, 4, 1, 17, 0f, true)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(4f, -41.375f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, 0.0873f)
        outer_roof_7_r1.texOffs(88, 129).addBox(0f, 0f, -9f, 6, 1, 17, 0f, true)

        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(12.392f, -40.205f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, 0.2618f)
        outer_roof_6_r2.texOffs(192, 67).addBox(-2.5f, 0f, -9f, 5, 1, 17, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(16.2163f, -39.0449f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.3491f)
        outer_roof_5_r3.texOffs(294, 0).addBox(-1.5f, 0f, -9f, 3, 1, 17, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(18.2687f, -37.7659f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.8727f)
        outer_roof_4_r3.texOffs(301, 130).addBox(-1f, 0f, -9f, 2, 1, 17, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(18.6114f, -35.9228f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.3788f)
        outer_roof_3_r3.texOffs(303, 193).addBox(-1f, -0.5f, -9f, 2, 1, 17, 0f, true)

        outer_roof_6_r3 = ModelMapper(modelDataWrapper)
        outer_roof_6_r3.setPos(-4f, -41.375f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_6_r3)
        setRotationAngle(outer_roof_6_r3, 0f, 0f, -0.0873f)
        outer_roof_6_r3.texOffs(88, 129).addBox(-6f, 0f, -9f, 6, 1, 17, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(-12.392f, -40.205f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, -0.2618f)
        outer_roof_5_r4.texOffs(192, 67).addBox(-2.5f, 0f, -9f, 5, 1, 17, 0f, false)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(-16.2163f, -39.0449f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, -0.3491f)
        outer_roof_4_r4.texOffs(294, 0).addBox(-1.5f, 0f, -9f, 3, 1, 17, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(-18.2687f, -37.7659f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0f, 0f, -0.8727f)
        outer_roof_3_r4.texOffs(301, 130).addBox(-1f, 0f, -9f, 2, 1, 17, 0f, false)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(-18.6114f, -35.9228f, 0f)
        roof_end_gangway_exterior.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -1.3788f)
        outer_roof_2_r3.texOffs(303, 193).addBox(-1f, -0.5f, -9f, 2, 1, 17, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(220, 0).addBox(-21f, 0f, -16f, 21, 1, 32, 0f, false)

        door_edge_4_r1 = ModelMapper(modelDataWrapper)
        door_edge_4_r1.setPos(-21.5f, -13f, 15f)
        door.addChild(door_edge_4_r1)
        setRotationAngle(door_edge_4_r1, 0f, 1.5708f, 0.1047f)
        door_edge_4_r1.texOffs(0, 90).addBox(0f, -21f, 0.5f, 2, 21, 2, 0f, false)
        door_edge_4_r1.texOffs(0, 90).addBox(28f, -21f, 0.5f, 2, 21, 2, 0f, true)

        door_edge_3_r1 = ModelMapper(modelDataWrapper)
        door_edge_3_r1.setPos(-20f, -6.5f, 14f)
        door.addChild(door_edge_3_r1)
        setRotationAngle(door_edge_3_r1, 0f, 1.5708f, 0f)
        door_edge_3_r1.texOffs(96, 126).addBox(-1f, -6.5f, -1f, 2, 13, 2, 0f, false)

        door_edge_2_r1 = ModelMapper(modelDataWrapper)
        door_edge_2_r1.setPos(-20f, -6.5f, -14f)
        door.addChild(door_edge_2_r1)
        setRotationAngle(door_edge_2_r1, 0f, 1.5708f, 0f)
        door_edge_2_r1.texOffs(96, 126).addBox(-1f, -6.5f, -1f, 2, 13, 2, 0f, true)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(269, 212).addBox(-21f, -13f, 0f, 1, 13, 13, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-21f, -13f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1047f)
        door_right_top_r1.texOffs(203, 296).addBox(0f, -20f, 0f, 1, 20, 13, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(220, 0).addBox(-21f, -13f, -13f, 1, 13, 13, 0f, false)

        door_side_top_r1 = ModelMapper(modelDataWrapper)
        door_side_top_r1.setPos(-21f, -13f, -2f)
        door_left.addChild(door_side_top_r1)
        setRotationAngle(door_side_top_r1, 0f, 0f, 0.1047f)
        door_side_top_r1.texOffs(142, 296).addBox(0f, -20f, -11f, 1, 20, 13, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)


        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(164, 125).addBox(-21f, -13f, 0f, 0, 13, 13, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-21f, -13f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1047f)
        door_right_top_r2.texOffs(222, 230).addBox(0f, -20f, 0f, 0, 20, 13, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(144, 155).addBox(-21f, -13f, -13f, 0, 13, 13, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-21f, -13f, 0f)
        door_left_exterior.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1047f)
        door_left_top_r1.texOffs(189, 230).addBox(0f, -20f, -13f, 0, 20, 13, 0f, false)

        door_sides = ModelMapper(modelDataWrapper)
        door_sides.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_sides)
        door_sides.texOffs(269, 210).addBox(-21.5f, 0f, -16f, 1, 3, 32, 0f, false)
        door_sides.texOffs(282, 266).addBox(-22f, 0f, -13f, 1, 1, 26, 0f, false)

        door_side_top_2_r1 = ModelMapper(modelDataWrapper)
        door_side_top_2_r1.setPos(-18.9095f, -32.8894f, 0f)
        door_sides.addChild(door_side_top_2_r1)
        setRotationAngle(door_side_top_2_r1, 0f, 0f, 0.1047f)
        door_side_top_2_r1.texOffs(98, 100).addBox(-1f, 0f, -13f, 2, 0, 26, 0f, false)

        door_side_top_1_r1 = ModelMapper(modelDataWrapper)
        door_side_top_1_r1.setPos(-21.5f, -13f, 0f)
        door_sides.addChild(door_side_top_1_r1)
        setRotationAngle(door_side_top_1_r1, 0f, 0f, 0.1047f)
        door_side_top_1_r1.texOffs(112, 17).addBox(0f, -23f, -13f, 0, 3, 26, 0f, false)

        door_end_exterior = ModelMapper(modelDataWrapper)
        door_end_exterior.setPos(0f, 24f, 0f)


        door_right_exterior_end = ModelMapper(modelDataWrapper)
        door_right_exterior_end.setPos(0f, 0f, 0f)
        door_end_exterior.addChild(door_right_exterior_end)
        door_right_exterior_end.texOffs(138, 125).addBox(-21f, -13f, 0f, 0, 13, 13, 0f, false)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(-21f, -13f, 0f)
        door_right_exterior_end.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, 0.1047f)
        door_right_top_r3.texOffs(102, 226).addBox(0f, -20f, 0f, 0, 20, 13, 0f, false)

        door_left_exterior_end = ModelMapper(modelDataWrapper)
        door_left_exterior_end.setPos(0f, 0f, 0f)
        door_end_exterior.addChild(door_left_exterior_end)
        door_left_exterior_end.texOffs(0, 64).addBox(-21f, -13f, -13f, 0, 13, 13, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-21f, -13f, 0f)
        door_left_exterior_end.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1047f)
        door_left_top_r2.texOffs(24, 136).addBox(0f, -20f, -13f, 0, 20, 13, 0f, false)

        door_sides_end = ModelMapper(modelDataWrapper)
        door_sides_end.setPos(0f, 0f, 0f)
        door_end_exterior.addChild(door_sides_end)
        door_sides_end.texOffs(219, 266).addBox(-21.5f, 0f, -16f, 1, 3, 32, 0f, false)
        door_sides_end.texOffs(282, 266).addBox(-22f, 0f, -13f, 1, 1, 26, 0f, false)

        door_side_top_3_r1 = ModelMapper(modelDataWrapper)
        door_side_top_3_r1.setPos(-18.9094f, -32.8894f, 0f)
        door_sides_end.addChild(door_side_top_3_r1)
        setRotationAngle(door_side_top_3_r1, 0f, 0f, 0.1047f)
        door_side_top_3_r1.texOffs(98, 100).addBox(-1f, 0f, -13f, 2, 0, 26, 0f, false)

        door_side_top_2_r2 = ModelMapper(modelDataWrapper)
        door_side_top_2_r2.setPos(-21.5f, -13f, 0f)
        door_sides_end.addChild(door_side_top_2_r2)
        setRotationAngle(door_side_top_2_r2, 0f, 0f, 0.1047f)
        door_side_top_2_r2.texOffs(112, 14).addBox(0f, -23f, -13f, 0, 3, 26, 0f, false)

        handrail_door = ModelMapper(modelDataWrapper)
        handrail_door.setPos(0f, 24f, 0f)
        handrail_door.texOffs(359, 77).addBox(0f, -14.25f, 0f, 0, 15, 0, 0.2f, false)
        handrail_door.texOffs(359, 29).addBox(0f, -37f, 0f, 0, 6, 0, 0.2f, false)

        handrail_curve = ModelMapper(modelDataWrapper)
        handrail_curve.setPos(0f, 0f, 0f)
        handrail_door.addChild(handrail_curve)
        handrail_curve.texOffs(330, 0).addBox(0f, -30.9085f, -0.5f, 0, 0, 1, 0.2f, false)
        handrail_curve.texOffs(330, 0).addBox(0f, -14.25f, -0.5f, 0, 0, 1, 0.2f, false)

        handrail_curve_12_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_12_r1.setPos(0f, -14.5328f, -1.0464f)
        handrail_curve.addChild(handrail_curve_12_r1)
        setRotationAngle(handrail_curve_12_r1, -0.7854f, 0f, 0f)
        handrail_curve_12_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_10_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_10_r1.setPos(0f, -14.806f, -1.256f)
        handrail_curve.addChild(handrail_curve_10_r1)
        setRotationAngle(handrail_curve_10_r1, -1.0472f, 0f, 0f)
        handrail_curve_10_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_9_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_9_r1.setPos(0f, -14.3232f, -0.7732f)
        handrail_curve.addChild(handrail_curve_9_r1)
        setRotationAngle(handrail_curve_9_r1, -0.5236f, 0f, 0f)
        handrail_curve_9_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_7_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_7_r1.setPos(0f, -22.5793f, -1.3293f)
        handrail_curve.addChild(handrail_curve_7_r1)
        setRotationAngle(handrail_curve_7_r1, 0f, 0f, -1.5708f)
        handrail_curve_7_r1.texOffs(328, 0).addBox(-7.5f, 0f, 0f, 15, 0, 0, 0.2f, false)
        handrail_curve_7_r1.texOffs(328, 0).addBox(-7.5f, 0f, 2.6585f, 15, 0, 0, 0.2f, false)

        handrail_curve_6_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_6_r1.setPos(0f, -30.3525f, -1.256f)
        handrail_curve.addChild(handrail_curve_6_r1)
        setRotationAngle(handrail_curve_6_r1, 1.0472f, 0f, 0f)
        handrail_curve_6_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_5_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_5_r1.setPos(0f, -30.6257f, -1.0464f)
        handrail_curve.addChild(handrail_curve_5_r1)
        setRotationAngle(handrail_curve_5_r1, 0.7854f, 0f, 0f)
        handrail_curve_5_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_3_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_3_r1.setPos(0f, -30.8353f, -0.7732f)
        handrail_curve.addChild(handrail_curve_3_r1)
        setRotationAngle(handrail_curve_3_r1, 0.5236f, 0f, 0f)
        handrail_curve_3_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_11_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_11_r1.setPos(0f, -14.5328f, 1.0464f)
        handrail_curve.addChild(handrail_curve_11_r1)
        setRotationAngle(handrail_curve_11_r1, 0.7854f, 0f, 0f)
        handrail_curve_11_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_9_r2 = ModelMapper(modelDataWrapper)
        handrail_curve_9_r2.setPos(0f, -14.806f, 1.256f)
        handrail_curve.addChild(handrail_curve_9_r2)
        setRotationAngle(handrail_curve_9_r2, 1.0472f, 0f, 0f)
        handrail_curve_9_r2.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_8_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_8_r1.setPos(0f, -14.3232f, 0.7732f)
        handrail_curve.addChild(handrail_curve_8_r1)
        setRotationAngle(handrail_curve_8_r1, 0.5236f, 0f, 0f)
        handrail_curve_8_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_5_r2 = ModelMapper(modelDataWrapper)
        handrail_curve_5_r2.setPos(0f, -30.3525f, 1.256f)
        handrail_curve.addChild(handrail_curve_5_r2)
        setRotationAngle(handrail_curve_5_r2, -1.0472f, 0f, 0f)
        handrail_curve_5_r2.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_4_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_4_r1.setPos(0f, -30.6257f, 1.0464f)
        handrail_curve.addChild(handrail_curve_4_r1)
        setRotationAngle(handrail_curve_4_r1, -0.7854f, 0f, 0f)
        handrail_curve_4_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_2_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_2_r1.setPos(0f, -30.8353f, 0.7732f)
        handrail_curve.addChild(handrail_curve_2_r1)
        setRotationAngle(handrail_curve_2_r1, -0.5236f, 0f, 0f)
        handrail_curve_2_r1.texOffs(330, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(117, 126).addBox(-20.5f, -13f, 7f, 1, 13, 2, 0f, false)
        head.texOffs(117, 126).addBox(19.5f, -13f, 7f, 1, 13, 2, 0f, true)
        head.texOffs(192, 113).addBox(-21f, 0f, 7f, 42, 1, 1, 0f, false)
        head.texOffs(228, 174).addBox(-20f, -36f, 7f, 40, 36, 0, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(21.5f, -13f, 0f)
        head.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, -0.1047f)
        upper_wall_2_r5.texOffs(8, 90).addBox(-2f, -21f, 7f, 1, 21, 2, 0f, true)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(-21.5f, -13f, 0f)
        head.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, 0.1047f)
        upper_wall_1_r4.texOffs(8, 90).addBox(1f, -21f, 7f, 1, 21, 2, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(272, 130).addBox(-21.5f, 0f, -19f, 1, 3, 27, 0f, false)
        head_exterior.texOffs(189, 243).addBox(-21.5f, -13f, -18f, 2, 13, 29, 0f, false)
        head_exterior.texOffs(272, 130).addBox(20.5f, 0f, -19f, 1, 3, 27, 0f, true)
        head_exterior.texOffs(189, 243).addBox(19.5f, -13f, -18f, 2, 13, 29, 0f, true)
        head_exterior.texOffs(163, 138).addBox(-20f, 0f, -22f, 40, 1, 29, 0f, false)
        head_exterior.texOffs(242, 53).addBox(-19.5f, -42f, 7f, 39, 42, 0, 0f, false)

        upper_wall_2_r6 = ModelMapper(modelDataWrapper)
        upper_wall_2_r6.setPos(21.5f, -13f, 1f)
        head_exterior.addChild(upper_wall_2_r6)
        setRotationAngle(upper_wall_2_r6, 0f, 0f, -0.1047f)
        upper_wall_2_r6.texOffs(69, 239).addBox(-2f, -23f, -19f, 2, 23, 29, 0f, true)

        upper_wall_1_r5 = ModelMapper(modelDataWrapper)
        upper_wall_1_r5.setPos(-21.5f, -13f, 1f)
        head_exterior.addChild(upper_wall_1_r5)
        setRotationAngle(upper_wall_1_r5, 0f, 0f, 0.1047f)
        upper_wall_1_r5.texOffs(69, 239).addBox(0f, -23f, -19f, 2, 23, 29, 0f, false)

        bumper = ModelMapper(modelDataWrapper)
        bumper.setPos(0f, 0.1f, -17f)
        head_exterior.addChild(bumper)
        bumper.texOffs(54, 155).addBox(-4.5855f, -0.1f, -8.1564f, 5, 3, 3, 0f, false)
        bumper.texOffs(54, 155).addBox(-0.4145f, -0.1f, -8.1564f, 5, 3, 3, 0f, true)

        bumper_2_r1 = ModelMapper(modelDataWrapper)
        bumper_2_r1.setPos(-21.5f, -0.1f, -2f)
        bumper.addChild(bumper_2_r1)
        setRotationAngle(bumper_2_r1, 0f, 0.3491f, 0f)
        bumper_2_r1.texOffs(112, 46).addBox(0f, 0f, 0f, 18, 3, 3, 0f, true)

        bumper_1_r1 = ModelMapper(modelDataWrapper)
        bumper_1_r1.setPos(21.5f, -0.1f, -2f)
        bumper.addChild(bumper_1_r1)
        setRotationAngle(bumper_1_r1, 0f, -0.3491f, 0f)
        bumper_1_r1.texOffs(112, 46).addBox(-18f, 0f, 0f, 18, 3, 3, 0f, false)

        head_back = ModelMapper(modelDataWrapper)
        head_back.setPos(0f, 0f, -17f)
        head_exterior.addChild(head_back)
        head_back.texOffs(52, 0).addBox(-8f, -33f, -5f, 1, 33, 0, 0f, false)
        head_back.texOffs(32, 0).addBox(7f, -33f, -5f, 1, 33, 0, 0f, false)
        head_back.texOffs(54, 182).addBox(-8f, -42f, -5f, 16, 9, 0, 0f, false)
        head_back.texOffs(294, 95).addBox(-7f, -33f, -5f, 14, 34, 0, 0f, false)

        front_right_panel_3_r2 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r2.setPos(8f, -42f, -5f)
        head_back.addChild(front_right_panel_3_r2)
        setRotationAngle(front_right_panel_3_r2, 0f, -0.3491f, 0f)
        front_right_panel_3_r2.texOffs(174, 168).addBox(0f, 0f, 0f, 15, 42, 0, 0f, false)

        front_right_panel_2_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_2_r1.setPos(-8f, -42f, -5f)
        head_back.addChild(front_right_panel_2_r1)
        setRotationAngle(front_right_panel_2_r1, 0f, 0.3491f, 0f)
        front_right_panel_2_r1.texOffs(0, 212).addBox(-15f, 0f, 0f, 15, 42, 0, 0f, false)

        roof_head_exterior = ModelMapper(modelDataWrapper)
        roof_head_exterior.setPos(0f, 0f, 4f)
        head_exterior.addChild(roof_head_exterior)
        roof_head_exterior.texOffs(54, 100).addBox(-4f, -41.375f, -26f, 4, 0, 34, 0f, false)
        roof_head_exterior.texOffs(54, 100).addBox(0f, -41.375f, -26f, 4, 0, 34, 0f, true)

        outer_roof_7_r2 = ModelMapper(modelDataWrapper)
        outer_roof_7_r2.setPos(4f, -41.375f, -3f)
        roof_head_exterior.addChild(outer_roof_7_r2)
        setRotationAngle(outer_roof_7_r2, 0f, 0f, 0.0873f)
        outer_roof_7_r2.texOffs(86, 0).addBox(0f, 0f, -23f, 6, 0, 34, 0f, false)

        outer_roof_6_r4 = ModelMapper(modelDataWrapper)
        outer_roof_6_r4.setPos(12.392f, -40.205f, -3f)
        roof_head_exterior.addChild(outer_roof_6_r4)
        setRotationAngle(outer_roof_6_r4, 0f, 0f, 0.2618f)
        outer_roof_6_r4.texOffs(98, 0).addBox(-2.5f, 0f, -23f, 5, 0, 34, 0f, true)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(16.2163f, -39.0449f, -3f)
        roof_head_exterior.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, 0.3491f)
        outer_roof_5_r5.texOffs(0, 0).addBox(-1.5f, 0f, -23f, 3, 0, 34, 0f, true)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(18.2687f, -37.7659f, -3f)
        roof_head_exterior.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, 0.8727f)
        outer_roof_4_r5.texOffs(0, 77).addBox(-1f, 0f, -23f, 2, 0, 34, 0f, true)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(18.6114f, -35.9228f, -3f)
        roof_head_exterior.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, 1.3788f)
        outer_roof_3_r5.texOffs(256, 95).addBox(-1f, -0.5f, -23f, 2, 1, 34, 0f, true)

        outer_roof_6_r5 = ModelMapper(modelDataWrapper)
        outer_roof_6_r5.setPos(-4f, -41.375f, -3f)
        roof_head_exterior.addChild(outer_roof_6_r5)
        setRotationAngle(outer_roof_6_r5, 0f, 0f, -0.0873f)
        outer_roof_6_r5.texOffs(86, 0).addBox(-6f, 0f, -23f, 6, 0, 34, 0f, true)

        outer_roof_5_r6 = ModelMapper(modelDataWrapper)
        outer_roof_5_r6.setPos(-12.392f, -40.205f, -3f)
        roof_head_exterior.addChild(outer_roof_5_r6)
        setRotationAngle(outer_roof_5_r6, 0f, 0f, -0.2618f)
        outer_roof_5_r6.texOffs(98, 0).addBox(-2.5f, 0f, -23f, 5, 0, 34, 0f, false)

        outer_roof_4_r6 = ModelMapper(modelDataWrapper)
        outer_roof_4_r6.setPos(-16.2163f, -39.0449f, -3f)
        roof_head_exterior.addChild(outer_roof_4_r6)
        setRotationAngle(outer_roof_4_r6, 0f, 0f, -0.3491f)
        outer_roof_4_r6.texOffs(0, 0).addBox(-1.5f, 0f, -23f, 3, 0, 34, 0f, false)

        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(-18.2687f, -37.7659f, -3f)
        roof_head_exterior.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0f, 0f, -0.8727f)
        outer_roof_3_r6.texOffs(0, 77).addBox(-1f, 0f, -23f, 2, 0, 34, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(-18.6114f, -35.9228f, -3f)
        roof_head_exterior.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -1.3788f)
        outer_roof_2_r4.texOffs(256, 95).addBox(-1f, -0.5f, -23f, 2, 1, 34, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(72, 0).addBox(-4f, -41.375f, -20f, 4, 0, 40, 0f, false)

        outer_roof_5_r7 = ModelMapper(modelDataWrapper)
        outer_roof_5_r7.setPos(-4f, -41.375f, 4f)
        roof_exterior.addChild(outer_roof_5_r7)
        setRotationAngle(outer_roof_5_r7, 0f, 0f, -0.0873f)
        outer_roof_5_r7.texOffs(0, 0).addBox(-6f, 0f, -24f, 6, 0, 40, 0f, false)

        outer_roof_4_r7 = ModelMapper(modelDataWrapper)
        outer_roof_4_r7.setPos(-12.392f, -40.205f, 4f)
        roof_exterior.addChild(outer_roof_4_r7)
        setRotationAngle(outer_roof_4_r7, 0f, 0f, -0.2618f)
        outer_roof_4_r7.texOffs(56, 0).addBox(-2.5f, 0f, -24f, 5, 0, 40, 0f, false)

        outer_roof_3_r7 = ModelMapper(modelDataWrapper)
        outer_roof_3_r7.setPos(-16.2163f, -39.0449f, 4f)
        roof_exterior.addChild(outer_roof_3_r7)
        setRotationAngle(outer_roof_3_r7, 0f, 0f, -0.3491f)
        outer_roof_3_r7.texOffs(0, 77).addBox(-1.5f, 0f, -24f, 3, 0, 40, 0f, false)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(-18.2687f, -37.7659f, 4f)
        roof_exterior.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, -0.8727f)
        outer_roof_2_r5.texOffs(66, 0).addBox(-1f, 0f, -24f, 2, 0, 40, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-18.6114f, -35.9228f, 4f)
        roof_exterior.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, -1.3788f)
        outer_roof_1_r2.texOffs(225, 225).addBox(-1f, -0.5f, -24f, 2, 1, 40, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(94, 100).addBox(-17.9149f, -32.7849f, -13f, 2, 0, 26, 0f, false)
        roof_door.texOffs(4, 77).addBox(-11.3838f, -34.8979f, -13f, 2, 0, 26, 0f, false)
        roof_door.texOffs(70, 100).addBox(-6.6649f, -36.1658f, -13f, 7, 0, 26, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-9.3834f, -34.898f, 3f)
        roof_door.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.4363f)
        inner_roof_4_r1.texOffs(0, 0).addBox(0f, 0f, -16f, 3, 0, 26, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-15.9149f, -32.7849f, 3f)
        roof_door.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.4363f)
        inner_roof_2_r1.texOffs(84, 100).addBox(0f, 0f, -16f, 5, 0, 26, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(38, 0).addBox(-17.9149f, -32.7849f, -27f, 2, 0, 54, 0f, false)
        roof_window.texOffs(34, 0).addBox(-11.3838f, -34.8979f, -27f, 2, 0, 54, 0f, false)
        roof_window.texOffs(2, 0).addBox(-6.6649f, -36.1658f, -27f, 7, 0, 54, 0f, false)

        inner_roof_9_r1 = ModelMapper(modelDataWrapper)
        inner_roof_9_r1.setPos(-23.3588f, 10.4067f, -26.999f)
        roof_window.addChild(inner_roof_9_r1)
        setRotationAngle(inner_roof_9_r1, 0f, -1.5708f, -0.4363f)
        inner_roof_9_r1.texOffs(26, 33).addBox(54f, -37f, -30f, 0, 1, 5, 0f, true)
        inner_roof_9_r1.texOffs(26, 33).addBox(0f, -37f, -30f, 0, 1, 5, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(-9.3834f, -34.898f, 3f)
        roof_window.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, -0.4363f)
        inner_roof_6_r1.texOffs(22, 0).addBox(0f, 0f, -30f, 3, 0, 54, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-11.2897f, -34.4754f, 0f)
        roof_window.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.2182f)
        inner_roof_4_r2.texOffs(16, 0).addBox(-2.9063f, -0.4226f, -27f, 3, 0, 54, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-15.8212f, -32.3623f, 3f)
        roof_window.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.6109f)
        inner_roof_3_r1.texOffs(28, 0).addBox(0.0937f, -0.4226f, -30f, 3, 0, 54, 0f, false)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        roof_end.addChild(side_1)
        side_1.texOffs(168, 29).addBox(-17.9149f, -33.7849f, -7f, 4, 1, 18, 0f, true)
        side_1.texOffs(10, 10).addBox(-11.3838f, -34.8979f, 3f, 2, 0, 8, 0f, true)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-9.3834f, -34.898f, 3f)
        side_1.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -0.4363f)
        inner_roof_5_r1.texOffs(8, 0).addBox(0f, 0f, -2f, 3, 0, 10, 0f, true)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-14.6848f, -32.1477f, 3f)
        side_1.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.6912f)
        inner_roof_3_r2.texOffs(78, 183).addBox(1f, -1f, 0f, 4, 1, 8, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        roof_end.addChild(side_2)
        side_2.texOffs(168, 29).addBox(13.9149f, -33.7849f, -7f, 4, 1, 18, 0f, false)
        side_2.texOffs(10, 10).addBox(9.3838f, -34.8979f, 3f, 2, 0, 8, 0f, false)

        inner_roof_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_7_r1.setPos(13.9156f, -32.7856f, 11f)
        side_2.addChild(inner_roof_7_r1)
        setRotationAngle(inner_roof_7_r1, 0f, 0f, 0.6912f)
        inner_roof_7_r1.texOffs(78, 183).addBox(-4f, -0.999f, -8f, 4, 1, 8, 0f, false)

        inner_roof_6_r2 = ModelMapper(modelDataWrapper)
        inner_roof_6_r2.setPos(6.6644f, -36.1659f, 3f)
        side_2.addChild(inner_roof_6_r2)
        setRotationAngle(inner_roof_6_r2, 0f, 0f, 0.4363f)
        inner_roof_6_r2.texOffs(8, 0).addBox(0f, 0f, -2f, 3, 0, 10, 0f, false)

        mid_roof = ModelMapper(modelDataWrapper)
        mid_roof.setPos(0f, 0f, 0f)
        roof_end.addChild(mid_roof)
        mid_roof.texOffs(112, 67).addBox(-18f, -36.7849f, -9f, 36, 4, 2, 0f, false)
        mid_roof.texOffs(54, 149).addBox(7.9149f, -36.7849f, -7f, 6, 4, 2, 0f, false)
        mid_roof.texOffs(54, 149).addBox(-13.9149f, -36.7849f, -7f, 6, 4, 2, 0f, true)

        roof_end_gangway = ModelMapper(modelDataWrapper)
        roof_end_gangway.setPos(0f, 24f, 0f)
        roof_end_gangway.texOffs(96, 40).addBox(-17.9149f, -33.7849f, 9f, 4, 1, 2, 0f, false)
        roof_end_gangway.texOffs(96, 40).addBox(13.9149f, -33.7849f, 9f, 4, 1, 2, 0f, true)

        mid_roof_gangway = ModelMapper(modelDataWrapper)
        mid_roof_gangway.setPos(0f, 0f, 0f)
        roof_end_gangway.addChild(mid_roof_gangway)
        mid_roof_gangway.texOffs(268, 33).addBox(-8.5f, -34.875f, -9f, 17, 2, 16, 0f, false)
        mid_roof_gangway.texOffs(32, 140).addBox(8.5f, -36.875f, 7f, 6, 4, 2, 0f, false)
        mid_roof_gangway.texOffs(32, 140).addBox(-14.5f, -36.875f, 7f, 6, 4, 2, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)
        roof_light.texOffs(0, 77).addBox(-11.3838f, -34.9f, -13f, 2, 0, 26, 0f, false)

        destination_display_end_interior = ModelMapper(modelDataWrapper)
        destination_display_end_interior.setPos(0f, 24f, 0f)
        destination_display_end_interior.texOffs(269, 245).addBox(-0.5032f, -36.4362f, -7f, 9, 2, 18, 0f, false)
        destination_display_end_interior.texOffs(269, 245).addBox(-8.4964f, -36.4363f, -7f, 9, 2, 18, 0f, true)

        display_6_r1 = ModelMapper(modelDataWrapper)
        display_6_r1.setPos(-24.0533f, -3.2172f, 42.001f)
        destination_display_end_interior.addChild(display_6_r1)
        setRotationAngle(display_6_r1, 0f, 0f, 0.48f)
        display_6_r1.texOffs(0, 149).addBox(-1.6162f, -35.875f, -39f, 1, 1, 8, 0f, true)

        display_5_r1 = ModelMapper(modelDataWrapper)
        display_5_r1.setPos(4.8028f, -41.5322f, 9.501f)
        destination_display_end_interior.addChild(display_5_r1)
        setRotationAngle(display_5_r1, 0f, 0f, -0.48f)
        display_5_r1.texOffs(0, 149).addBox(0f, 7f, -6.5f, 1, 1, 8, 0f, false)

        display_4_r1 = ModelMapper(modelDataWrapper)
        display_4_r1.setPos(13.7899f, -31.7933f, 21f)
        destination_display_end_interior.addChild(display_4_r1)
        setRotationAngle(display_4_r1, 0f, 0f, 0.295f)
        display_4_r1.texOffs(134, 28).addBox(-6.1257f, -2.9925f, -26f, 6, 2, 8, 0f, false)

        display_3_r1 = ModelMapper(modelDataWrapper)
        display_3_r1.setPos(-7.7652f, -33.617f, 21f)
        destination_display_end_interior.addChild(display_3_r1)
        setRotationAngle(display_3_r1, 0f, 0f, -0.295f)
        display_3_r1.texOffs(134, 28).addBox(-6.1257f, -2.9925f, -26f, 6, 2, 8, 0f, true)

        roof_head = ModelMapper(modelDataWrapper)
        roof_head.setPos(0f, 24f, 0f)
        roof_head.texOffs(14, 18).addBox(-17.9149f, -32.7849f, 7f, 2, 0, 4, 0f, false)
        roof_head.texOffs(18, 10).addBox(-11.3838f, -34.8979f, 7f, 2, 0, 4, 0f, false)
        roof_head.texOffs(18, 10).addBox(9.3838f, -34.8979f, 7f, 2, 0, 4, 0f, true)
        roof_head.texOffs(14, 18).addBox(15.9149f, -32.7849f, 7f, 2, 0, 4, 0f, true)

        inner_roof_6_r3 = ModelMapper(modelDataWrapper)
        inner_roof_6_r3.setPos(11.3834f, -34.898f, 19f)
        roof_head.addChild(inner_roof_6_r3)
        setRotationAngle(inner_roof_6_r3, 0f, 0f, 0.4363f)
        inner_roof_6_r3.texOffs(22, 34).addBox(0f, 0f, -12f, 5, 0, 4, 0f, true)

        inner_roof_3_r3 = ModelMapper(modelDataWrapper)
        inner_roof_3_r3.setPos(-15.9149f, -32.7849f, 27f)
        roof_head.addChild(inner_roof_3_r3)
        setRotationAngle(inner_roof_3_r3, 0f, 0f, -0.4363f)
        inner_roof_3_r3.texOffs(22, 34).addBox(0f, 0f, -20f, 5, 0, 4, 0f, false)

        destination_display = ModelMapper(modelDataWrapper)
        destination_display.setPos(0f, 0f, 4f)
        roof_head.addChild(destination_display)
        destination_display.texOffs(269, 245).addBox(-0.5032f, -36.4362f, 3f, 9, 2, 18, 0f, false)
        destination_display.texOffs(269, 245).addBox(-8.4964f, -36.4363f, 3f, 9, 2, 18, 0f, true)

        display_7_r1 = ModelMapper(modelDataWrapper)
        display_7_r1.setPos(-24.0533f, -3.2172f, 52.001f)
        destination_display.addChild(display_7_r1)
        setRotationAngle(display_7_r1, 0f, 0f, 0.48f)
        display_7_r1.texOffs(110, 241).addBox(-1.6162f, -35.875f, -49f, 1, 1, 18, 0f, true)

        display_6_r2 = ModelMapper(modelDataWrapper)
        display_6_r2.setPos(4.8028f, -41.5322f, 19.501f)
        destination_display.addChild(display_6_r2)
        setRotationAngle(display_6_r2, 0f, 0f, -0.48f)
        display_6_r2.texOffs(110, 241).addBox(0f, 7f, -16.5f, 1, 1, 18, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlights_2_r1 = ModelMapper(modelDataWrapper)
        headlights_2_r1.setPos(8f, -42f, -22f)
        headlights.addChild(headlights_2_r1)
        setRotationAngle(headlights_2_r1, 0f, -0.3491f, 0f)
        headlights_2_r1.texOffs(0, 29).addBox(3f, 29f, -0.1f, 10, 10, 0, 0f, true)

        headlights_1_r1 = ModelMapper(modelDataWrapper)
        headlights_1_r1.setPos(-8f, -42f, -22f)
        headlights.addChild(headlights_1_r1)
        setRotationAngle(headlights_1_r1, 0f, 0.3491f, 0f)
        headlights_1_r1.texOffs(0, 29).addBox(-13f, 29f, -0.1f, 10, 10, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_lights_2_r1 = ModelMapper(modelDataWrapper)
        tail_lights_2_r1.setPos(8f, -42f, -22f)
        tail_lights.addChild(tail_lights_2_r1)
        setRotationAngle(tail_lights_2_r1, 0f, -0.3491f, 0f)
        tail_lights_2_r1.texOffs(96, 43).addBox(9f, 28f, -0.05f, 5, 5, 0, 0f, true)

        tail_lights_1_r1 = ModelMapper(modelDataWrapper)
        tail_lights_1_r1.setPos(-8f, -42f, -22f)
        tail_lights.addChild(tail_lights_1_r1)
        setRotationAngle(tail_lights_1_r1, 0f, 0.3491f, 0f)
        tail_lights_1_r1.texOffs(96, 43).addBox(-14f, 28f, -0.05f, 5, 5, 0, 0f, false)

        door_light_interior_off = ModelMapper(modelDataWrapper)
        door_light_interior_off.setPos(0f, 24f, 0f)


        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(-9.6762f, -32.7244f, 13.2f)
        door_light_interior_off.addChild(light_2_r1)
        setRotationAngle(light_2_r1, 0f, 0f, 0.1047f)
        light_2_r1.texOffs(351, 10).addBox(-7f, 1.75f, -27.2f, 0, 0, 0, 0.3f, false)
        light_2_r1.texOffs(351, 10).addBox(-7f, 1.75f, 0.8f, 0, 0, 0, 0.3f, false)

        door_light_interior_on = ModelMapper(modelDataWrapper)
        door_light_interior_on.setPos(0f, 24f, 0f)


        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(-9.6762f, -32.7244f, 13.2f)
        door_light_interior_on.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 0f, 0.1047f)
        light_3_r1.texOffs(351, 15).addBox(-7f, 1.75f, -27.2f, 0, 0, 0, 0.3f, false)
        light_3_r1.texOffs(351, 15).addBox(-7f, 1.75f, 0.8f, 0, 0, 0, 0.3f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window_exterior.setModelPart()
        window_handrails.setModelPart()
        window_exterior_end.setModelPart()
        window.setModelPart()
        side_panel.setModelPart()
        end.setModelPart()
        end_exterior.setModelPart()
        end_gangway.setModelPart()
        end_gangway_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        door_end_exterior.setModelPart()
        door_left_exterior_end.setModelPart(door_end_exterior.name)
        door_right_exterior_end.setModelPart(door_end_exterior.name)
        handrail_door.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        roof_exterior.setModelPart()
        roof_door.setModelPart()
        roof_window.setModelPart()
        roof_end.setModelPart()
        roof_end_gangway.setModelPart()
        roof_light.setModelPart()
        destination_display_end_interior.setModelPart()
        roof_head.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light_interior_off.setModelPart()
        door_light_interior_on.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelR211 {
        return ModelR211(openGangway, doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> {
                renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                renderMirror(roof_light, matrices, vertices, light, (position - 15).toFloat())
                renderMirror(roof_light, matrices, vertices, light, (position + 15).toFloat())
            }

            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, position + 24.1f)
                    renderMirror(side_panel, matrices, vertices, light, position - 24.1f)
                }
            }

            RenderStage.EXTERIOR -> {
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (isIndex(0, position, getWindowPositions()) && isEnd1Head) {
                    renderOnceFlipped(window_exterior_end, matrices, vertices, light, position.toFloat())
                } else if (isIndex(-1, position, getWindowPositions()) && isEnd2Head) {
                    renderOnce(window_exterior_end, matrices, vertices, light, position.toFloat())
                } else {
                    renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                }
            }

            else -> {}
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
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                if (doorOpen) {
                    renderMirror(door_light_interior_on, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR -> {
                door_right.setOffset(doorRightX, 0, doorRightZ)
                door_left.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_right.setOffset(doorLeftX, 0, doorLeftZ)
                door_left.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())
                if (!doorOpen) {
                    renderMirror(door_light_interior_off, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    renderOnce(handrail_door, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                val door1End = isIndex(0, position, getDoorPositions()) && isEnd1Head
                val door2End = isIndex(-1, position, getDoorPositions()) && isEnd2Head

                if (door1End || door2End) {
                    door_right_exterior_end.setOffset(doorRightX, 0, doorRightZ)
                    door_left_exterior_end.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_end_exterior, matrices, vertices, light, position.toFloat())
                } else {
                    door_right_exterior.setOffset(doorRightX, 0, doorRightZ)
                    door_left_exterior.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                }

                if (door1End || door2End) {
                    door_right_exterior_end.setOffset(doorLeftX, 0, doorLeftZ)
                    door_left_exterior_end.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_end_exterior, matrices, vertices, light, position.toFloat())
                } else {
                    door_right_exterior.setOffset(doorLeftX, 0, doorLeftZ)
                    door_left_exterior.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                }
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> {
                renderOnce(roof_light, matrices, vertices, light, (position + 20).toFloat())
                renderOnceFlipped(roof_light, matrices, vertices, light, (position + 20).toFloat())
            }

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnce(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> {
                renderOnce(roof_light, matrices, vertices, light, (position - 20).toFloat())
                renderOnceFlipped(roof_light, matrices, vertices, light, (position - 20).toFloat())
            }

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
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
        when (renderStage!!) {
            RenderStage.LIGHTS -> if (!openGangway) {
                renderOnce(roof_light, matrices, vertices, light, (position + 16).toFloat())
                renderOnceFlipped(roof_light, matrices, vertices, light, (position + 16).toFloat())
            }

            RenderStage.INTERIOR -> {
                if (openGangway) {
                    renderOnce(end_gangway, matrices, vertices, light, position.toFloat())
                } else {
                    renderOnce(end, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    if (openGangway) {
                        renderOnce(roof_end_gangway, matrices, vertices, light, position.toFloat())
                        renderOnce(
                            destination_display_end_interior,
                            matrices,
                            vertices,
                            light,
                            (position + 8).toFloat()
                        )
                    } else {
                        renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                        renderOnce(destination_display_end_interior, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> if (openGangway) {
                renderOnce(end_gangway_exterior, matrices, vertices, light, position.toFloat())
            } else {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
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
        when (renderStage!!) {
            RenderStage.LIGHTS -> if (!openGangway) {
                renderOnce(roof_light, matrices, vertices, light, (position - 16).toFloat())
                renderOnceFlipped(roof_light, matrices, vertices, light, (position - 16).toFloat())
            }

            RenderStage.INTERIOR -> {
                if (openGangway) {
                    renderOnceFlipped(end_gangway, matrices, vertices, light, position.toFloat())
                } else {
                    renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    if (openGangway) {
                        renderOnceFlipped(roof_end_gangway, matrices, vertices, light, position.toFloat())
                        renderOnceFlipped(
                            destination_display_end_interior,
                            matrices,
                            vertices,
                            light,
                            (position - 8).toFloat()
                        )
                    } else {
                        renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                        renderOnceFlipped(
                            destination_display_end_interior,
                            matrices,
                            vertices,
                            light,
                            position.toFloat()
                        )
                    }
                }
            }

            RenderStage.EXTERIOR -> if (openGangway) {
                renderOnceFlipped(end_gangway_exterior, matrices, vertices, light, position.toFloat())
            } else {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }

    @Override
    override fun getModelDoorOverlay(): ModelDoorOverlay? {
        return null
    }

    @Override
    override fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase? {
        return null
    }

    @Override
    override fun getWindowPositions(): IntArray? {
        return intArrayOf(-80, 0, 80)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-120, -40, 40, 120)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-144, 144)
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
        val routeNumber: String? = if (thisRoute == null) "" else thisRoute.lightRailRouteNumber
        renderFrontDestination(
            matrices,
            font,
            immediate,
            0f,
            -2.26f,
            getEndPositions()!![0] / 16f - 1.37f,
            0f,
            0f,
            -0.01f,
            0f,
            0f,
            0.44f,
            0.12f,
            mtr.data.IGui.ARGB_WHITE,
            mtr.data.IGui.ARGB_WHITE,
            1f,
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, true),
            true,
            car,
            totalCars
        )
        renderFrontDestination(
            matrices, font, immediate,
            0.5f, 0f, getEndPositions()!![0] / 16f - 1.37f, 0.35f, -1.57f, -0.01f,
            0f, -20f, 0.4f, 0.36f,
            mtr.data.IGui.ARGB_WHITE, mtr.data.IGui.ARGB_WHITE, 1f, routeNumber, false, car, totalCars
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    companion object {
        private const val DOOR_MAX = 13
    }
}
