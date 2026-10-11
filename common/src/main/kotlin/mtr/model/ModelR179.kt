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

open class ModelR179 protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelR179?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val wall_1_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val seat: ModelMapper
    private val seat_bottom_r1: ModelMapper
    private val seat_back_3_r1: ModelMapper
    private val handrail_window: ModelMapper
    private val handrail_top_1_r1: ModelMapper
    private val handrail_mid: ModelMapper
    private val handrail_middle_4_r1: ModelMapper
    private val handrail_middle_3_r1: ModelMapper
    private val handrail_middle_2_r1: ModelMapper
    private val handrail_turn_1_r1: ModelMapper
    private val headrail_right: ModelMapper
    private val handrail_right_3_r1: ModelMapper
    private val handrail_right_1_r1: ModelMapper
    private val handrail_turn_4_r1: ModelMapper
    private val handrail_turn_3_r1: ModelMapper
    private val handrail_turn_2_r1: ModelMapper
    private val handrail_turn_1_r2: ModelMapper
    private val headrail_left: ModelMapper
    private val handrail_left_3_r1: ModelMapper
    private val handrail_left_1_r1: ModelMapper
    private val handrail_turn_4_r2: ModelMapper
    private val handrail_turn_3_r2: ModelMapper
    private val handrail_turn_2_r2: ModelMapper
    private val handrail_turn_1_r3: ModelMapper
    private val headrail_up: ModelMapper
    private val handrail_up_11_r1: ModelMapper
    private val handrail_up_10_r1: ModelMapper
    private val window_display: ModelMapper
    private val display_window_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val side_panel: ModelMapper
    private val door: ModelMapper
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
    private val door_side_top_1_r1: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val end_handrails: ModelMapper
    private val end_mid_roof_3_r1: ModelMapper
    private val end_mid_roof_4_r1: ModelMapper
    private val end_side_1: ModelMapper
    private val seat_bottom_r2: ModelMapper
    private val seat_back_4_r1: ModelMapper
    private val end_side_2: ModelMapper
    private val seat_bottom_r3: ModelMapper
    private val seat_back_3_r2: ModelMapper
    private val handrail_end_1: ModelMapper
    private val handrail_top_6_r1: ModelMapper
    private val handrail_top_5_r1: ModelMapper
    private val handrail_top_4_r1: ModelMapper
    private val handrail_right_4_r1: ModelMapper
    private val handrail_right_2_r1: ModelMapper
    private val handrail_turn_5_r1: ModelMapper
    private val handrail_turn_4_r3: ModelMapper
    private val handrail_turn_3_r3: ModelMapper
    private val handrail_turn_2_r3: ModelMapper
    private val handrail_end_2: ModelMapper
    private val handrail_top_5_r2: ModelMapper
    private val handrail_top_4_r2: ModelMapper
    private val handrail_top_3_r1: ModelMapper
    private val handrail_right_3_r2: ModelMapper
    private val handrail_right_1_r2: ModelMapper
    private val handrail_turn_4_r4: ModelMapper
    private val handrail_turn_3_r4: ModelMapper
    private val handrail_turn_2_r4: ModelMapper
    private val handrail_turn_1_r4: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val end_bottom_out: ModelMapper
    private val buttom_panel_right_4_r1: ModelMapper
    private val buttom_panel_right_3_r1: ModelMapper
    private val buttom_panel_right_2_r1: ModelMapper
    private val buttom_panel_left_4_r1: ModelMapper
    private val buttom_panel_left_3_r1: ModelMapper
    private val buttom_panel_left_2_r1: ModelMapper
    private val end_back: ModelMapper
    private val front_right_panel_4_r1: ModelMapper
    private val front_right_panel_3_r1: ModelMapper
    private val front_right_panel_2_r1: ModelMapper
    private val front_right_panel_3_r2: ModelMapper
    private val front_right_panel_2_r2: ModelMapper
    private val front_right_panel_1_r1: ModelMapper
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
    private val head: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val head_bottom_out: ModelMapper
    private val buttom_panel_right_5_r1: ModelMapper
    private val buttom_panel_right_4_r2: ModelMapper
    private val buttom_panel_right_3_r2: ModelMapper
    private val buttom_panel_left_5_r1: ModelMapper
    private val buttom_panel_left_4_r2: ModelMapper
    private val buttom_panel_left_3_r2: ModelMapper
    private val head_back: ModelMapper
    private val front_right_panel_5_r1: ModelMapper
    private val front_right_panel_4_r2: ModelMapper
    private val front_right_panel_3_r3: ModelMapper
    private val front_right_panel_4_r3: ModelMapper
    private val front_right_panel_3_r4: ModelMapper
    private val front_right_panel_2_r3: ModelMapper
    private val roof_head_exterior: ModelMapper
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
    private val roof_window: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val roof_end: ModelMapper
    private val inner_roof_9_r1: ModelMapper
    private val inner_roof_7_r1: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val roof_exterior_window: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val roof_exterior_door: ModelMapper
    private val outer_roof_6_r4: ModelMapper
    private val outer_roof_5_r6: ModelMapper
    private val outer_roof_4_r6: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val roof_head: ModelMapper
    private val inner_roof_9_r2: ModelMapper
    private val inner_roof_7_r2: ModelMapper
    private val inner_roof_4_r4: ModelMapper
    private val inner_roof_2_r4: ModelMapper
    private val roof_window_light: ModelMapper
    private val roof_door_light: ModelMapper
    private val roof_end_light: ModelMapper
    private val roof_head_light: ModelMapper
    private val handrail_door_type_1: ModelMapper
    private val handrail_door_type_2: ModelMapper
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
    private val roof_handle: ModelMapper
    private val roof_handrail_curve_19_r1: ModelMapper
    private val roof_handrail_curve_22_r1: ModelMapper
    private val roof_handrail_curve_23_r1: ModelMapper
    private val roof_handrail_curve_18_r1: ModelMapper
    private val roof_handrail_curve_21_r1: ModelMapper
    private val roof_handrail_curve_22_r2: ModelMapper
    private val roof_handrail_curve_18_r2: ModelMapper
    private val roof_handrail_curve_21_r2: ModelMapper
    private val roof_handrail_curve_22_r3: ModelMapper
    private val roof_handrail_curve_17_r1: ModelMapper
    private val roof_handrail_curve_20_r1: ModelMapper
    private val roof_handrail_curve_21_r3: ModelMapper
    private val roof_handrail_2_r1: ModelMapper
    private val headlights: ModelMapper
    private val front_right_panel_6_r1: ModelMapper
    private val front_right_panel_5_r2: ModelMapper
    private val front_right_panel_4_r4: ModelMapper
    private val front_right_panel_3_r5: ModelMapper
    private val tail_lights: ModelMapper
    private val front_right_panel_5_r3: ModelMapper
    private val front_right_panel_4_r5: ModelMapper
    private val front_right_panel_3_r6: ModelMapper
    private val front_right_panel_2_r4: ModelMapper
    private val door_light: ModelMapper
    private val light_plate_1_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper

    constructor() : this(DoorAnimationType.R179, true)

    init {
        val textureWidth = 368
        val textureHeight = 368

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(120, 0).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(0, 78).addBox(-21.5f, -13f, -28.5f, 2, 13, 57, 0f, false)

        wall_1_r1 = ModelMapper(modelDataWrapper)
        wall_1_r1.setPos(-21.5f, -13f, 0f)
        window.addChild(wall_1_r1)
        setRotationAngle(wall_1_r1, 0f, 0f, 0.1047f)
        wall_1_r1.texOffs(0, 0).addBox(0f, -21f, -28.5f, 2, 21, 57, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)


        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat)
        seat.texOffs(124, 155).addBox(-19.9f, -10.75f, -26.5f, 2, 5, 53, 0f, false)
        seat.texOffs(58, 171).addBox(-19.55f, -6f, -26f, 3, 4, 52, 0f, false)

        seat_bottom_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_r1.setPos(0f, -1.75f, 0f)
        seat.addChild(seat_bottom_r1)
        setRotationAngle(seat_bottom_r1, 0f, 0f, -0.0873f)
        seat_bottom_r1.texOffs(120, 101).addBox(-19.9f, -6f, -26.5f, 9, 1, 53, 0f, false)

        seat_back_3_r1 = ModelMapper(modelDataWrapper)
        seat_back_3_r1.setPos(-17.9f, -10.75f, 0f)
        seat.addChild(seat_back_3_r1)
        setRotationAngle(seat_back_3_r1, 0f, 0f, -0.1309f)
        seat_back_3_r1.texOffs(0, 148).addBox(-2f, -5f, -26.5f, 2, 5, 53, 0f, false)

        handrail_window = ModelMapper(modelDataWrapper)
        handrail_window.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_window)


        handrail_top_1_r1 = ModelMapper(modelDataWrapper)
        handrail_top_1_r1.setPos(-12.8f, -31.75f, 0f)
        handrail_window.addChild(handrail_top_1_r1)
        setRotationAngle(handrail_top_1_r1, -1.5708f, 0f, 0f)
        handrail_top_1_r1.texOffs(0, 0).addBox(0f, -24.5f, 0f, 0, 49, 0, 0.2f, false)

        handrail_mid = ModelMapper(modelDataWrapper)
        handrail_mid.setPos(-39.95f, -9.875f, 38.1f)
        handrail_window.addChild(handrail_mid)
        handrail_mid.texOffs(0, 0).addBox(23.5101f, 5.4649f, -38.1f, 5, 0, 0, 0.2f, false)

        handrail_middle_4_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_4_r1.setPos(27.8083f, -20.505f, -38.1f)
        handrail_mid.addChild(handrail_middle_4_r1)
        setRotationAngle(handrail_middle_4_r1, 0f, 0f, 1.1606f)
        handrail_middle_4_r1.texOffs(0, 0).addBox(-1.5f, 0f, 0f, 3, 0, 0, 0.2f, false)

        handrail_middle_3_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_3_r1.setPos(29.7f, -13.725f, -38.1f)
        handrail_mid.addChild(handrail_middle_3_r1)
        setRotationAngle(handrail_middle_3_r1, 0f, 0f, 1.3788f)
        handrail_middle_3_r1.texOffs(0, 0).addBox(-5.2f, 0.2f, 0f, 5, 0, 0, 0.2f, false)

        handrail_middle_2_r1 = ModelMapper(modelDataWrapper)
        handrail_middle_2_r1.setPos(29.5f, -4.525f, -38.1f)
        handrail_mid.addChild(handrail_middle_2_r1)
        setRotationAngle(handrail_middle_2_r1, 0f, 0f, 1.5708f)
        handrail_middle_2_r1.texOffs(0, 0).addBox(-9f, 0f, 0f, 18, 0, 0, 0.2f, false)

        handrail_turn_1_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r1.setPos(29.7f, 4.675f, -38.3f)
        handrail_mid.addChild(handrail_turn_1_r1)
        setRotationAngle(handrail_turn_1_r1, 0f, -1.5708f, 2.3562f)
        handrail_turn_1_r1.texOffs(0, 0).addBox(0.2f, 0.2f, -1.2f, 0, 0, 1, 0.2f, false)

        headrail_right = ModelMapper(modelDataWrapper)
        headrail_right.setPos(-39.95f, -9.875f, 37.1f)
        handrail_window.addChild(headrail_right)


        handrail_right_3_r1 = ModelMapper(modelDataWrapper)
        handrail_right_3_r1.setPos(23.3f, -0.225f, -12.2f)
        headrail_right.addChild(handrail_right_3_r1)
        setRotationAngle(handrail_right_3_r1, 0f, 0f, 1.5708f)
        handrail_right_3_r1.texOffs(0, 0).addBox(4f, -5f, 1.6f, 0, 0, 0, 0.2f, false)
        handrail_right_3_r1.texOffs(0, 0).addBox(-15f, -5f, 2f, 19, 0, 0, 0.2f, false)

        handrail_right_1_r1 = ModelMapper(modelDataWrapper)
        handrail_right_1_r1.setPos(28.06f, -17.5269f, -10.2f)
        headrail_right.addChild(handrail_right_1_r1)
        setRotationAngle(handrail_right_1_r1, 0f, 0f, 1.4573f)
        handrail_right_1_r1.texOffs(0, 0).addBox(-1f, 0f, 0f, 3, 0, 0, 0.2f, false)

        handrail_turn_4_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_4_r1.setPos(27.9f, -18.7556f, -10.237f)
        headrail_right.addChild(handrail_turn_4_r1)
        setRotationAngle(handrail_turn_4_r1, -1.3099f, 0.0441f, -0.1412f)
        handrail_turn_4_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_3_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_3_r1.setPos(27.65f, -19.8179f, -10.7262f)
        headrail_right.addChild(handrail_turn_3_r1)
        setRotationAngle(handrail_turn_3_r1, -1.1436f, 0.0916f, -0.1983f)
        handrail_turn_3_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_turn_2_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_2_r1.setPos(27.375f, -20.9556f, -11.312f)
        headrail_right.addChild(handrail_turn_2_r1)
        setRotationAngle(handrail_turn_2_r1, -0.7494f, 0.1284f, -0.1186f)
        handrail_turn_2_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_1_r2 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r2.setPos(27.25f, -21.4912f, -11.9782f)
        headrail_right.addChild(handrail_turn_1_r2)
        setRotationAngle(handrail_turn_1_r2, -0.6584f, 0.1103f, -0.0706f)
        handrail_turn_1_r2.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        headrail_left = ModelMapper(modelDataWrapper)
        headrail_left.setPos(-39.95f, -9.875f, 39.1f)
        handrail_window.addChild(headrail_left)


        handrail_left_3_r1 = ModelMapper(modelDataWrapper)
        handrail_left_3_r1.setPos(23.3f, -0.225f, -64f)
        headrail_left.addChild(handrail_left_3_r1)
        setRotationAngle(handrail_left_3_r1, 0f, 0f, 1.5708f)
        handrail_left_3_r1.texOffs(0, 0).addBox(4f, -5f, -1.6f, 0, 0, 0, 0.2f, false)
        handrail_left_3_r1.texOffs(0, 0).addBox(-15f, -5f, -2f, 19, 0, 0, 0.2f, false)

        handrail_left_1_r1 = ModelMapper(modelDataWrapper)
        handrail_left_1_r1.setPos(28.06f, -17.5269f, -66f)
        headrail_left.addChild(handrail_left_1_r1)
        setRotationAngle(handrail_left_1_r1, 0f, 0f, 1.4573f)
        handrail_left_1_r1.texOffs(0, 0).addBox(-1f, 0f, 0f, 3, 0, 0, 0.2f, false)

        handrail_turn_4_r2 = ModelMapper(modelDataWrapper)
        handrail_turn_4_r2.setPos(27.9f, -18.7556f, -65.963f)
        headrail_left.addChild(handrail_turn_4_r2)
        setRotationAngle(handrail_turn_4_r2, 1.3099f, -0.0441f, -0.1412f)
        handrail_turn_4_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_3_r2 = ModelMapper(modelDataWrapper)
        handrail_turn_3_r2.setPos(27.65f, -19.8179f, -65.4738f)
        headrail_left.addChild(handrail_turn_3_r2)
        setRotationAngle(handrail_turn_3_r2, 1.1436f, -0.0916f, -0.1983f)
        handrail_turn_3_r2.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_turn_2_r2 = ModelMapper(modelDataWrapper)
        handrail_turn_2_r2.setPos(27.375f, -20.9556f, -64.888f)
        headrail_left.addChild(handrail_turn_2_r2)
        setRotationAngle(handrail_turn_2_r2, 0.7494f, -0.1284f, -0.1186f)
        handrail_turn_2_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_1_r3 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r3.setPos(27.25f, -21.4912f, -64.2218f)
        headrail_left.addChild(handrail_turn_1_r3)
        setRotationAngle(handrail_turn_1_r3, 0.6584f, -0.1103f, -0.0706f)
        handrail_turn_1_r3.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        headrail_up = ModelMapper(modelDataWrapper)
        headrail_up.setPos(-40.95f, -12.875f, 38.1f)
        handrail_window.addChild(headrail_up)
        headrail_up.texOffs(0, 0).addBox(30.35f, -22.375f, -15.8f, 0, 1, 0, 0.2f, false)
        headrail_up.texOffs(0, 0).addBox(30.35f, -22.375f, -60.4f, 0, 1, 0, 0.2f, false)
        headrail_up.texOffs(0, 0).addBox(30.35f, -22.375f, -32.4f, 0, 1, 0, 0.2f, false)
        headrail_up.texOffs(0, 0).addBox(30.35f, -22.375f, -43.8f, 0, 1, 0, 0.2f, false)

        handrail_up_11_r1 = ModelMapper(modelDataWrapper)
        handrail_up_11_r1.setPos(37.479f, -8.7545f, -46.8f)
        headrail_up.addChild(handrail_up_11_r1)
        setRotationAngle(handrail_up_11_r1, 0f, 0f, 0.6109f)
        handrail_up_11_r1.texOffs(0, 0).addBox(-13f, -6f, 3f, 0, 1, 0, 0.2f, false)
        handrail_up_11_r1.texOffs(0, 0).addBox(-13f, -6f, 14.4f, 0, 1, 0, 0.2f, false)
        handrail_up_11_r1.texOffs(0, 0).addBox(-13f, -6f, -13.6f, 0, 1, 0, 0.2f, false)
        handrail_up_11_r1.texOffs(0, 0).addBox(-13f, -6f, 31f, 0, 1, 0, 0.2f, false)

        handrail_up_10_r1 = ModelMapper(modelDataWrapper)
        handrail_up_10_r1.setPos(34.65f, -6.875f, -46.8f)
        headrail_up.addChild(handrail_up_10_r1)
        setRotationAngle(handrail_up_10_r1, 0f, 0f, 0.7854f)
        handrail_up_10_r1.texOffs(0, 0).addBox(-13f, -6f, 3f, 0, 2, 0, 0.2f, false)
        handrail_up_10_r1.texOffs(0, 0).addBox(-13f, -6f, 14.4f, 0, 2, 0, 0.2f, false)
        handrail_up_10_r1.texOffs(0, 0).addBox(-13f, -6f, -13.6f, 0, 2, 0, 0.2f, false)
        handrail_up_10_r1.texOffs(0, 0).addBox(-13f, -6f, 31f, 0, 2, 0, 0.2f, false)

        window_display = ModelMapper(modelDataWrapper)
        window_display.setPos(0f, 24f, 0f)


        display_window_r1 = ModelMapper(modelDataWrapper)
        display_window_r1.setPos(-21.5f, -13f, 0f)
        window_display.addChild(display_window_r1)
        setRotationAngle(display_window_r1, 0f, 0f, 0.1047f)
        display_window_r1.texOffs(116, 171).addBox(0.1f, -19.5f, -14f, 2, 6, 28, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(181, 155).addBox(-21.5f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior.texOffs(61, 101).addBox(-21.5f, -13f, -28.5f, 1, 13, 57, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-21.5f, -13f, 0f)
        window_exterior.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1047f)
        upper_wall_r1.texOffs(61, 21).addBox(0f, -23f, -28.5f, 1, 23, 57, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(302, 137).addBox(-20f, -32f, 0f, 9, 29, 0, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(202, 207).addBox(-21f, 0f, -16f, 21, 1, 32, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(116, 171).addBox(-21f, -13f, 0f, 1, 13, 12, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-21f, -13f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1047f)
        door_right_top_r1.texOffs(232, 149).addBox(0f, -21f, 0f, 1, 21, 12, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(148, 171).addBox(-21f, -13f, -12f, 1, 13, 12, 0f, false)

        door_side_top_r1 = ModelMapper(modelDataWrapper)
        door_side_top_r1.setPos(-21f, -13f, 0f)
        door_left.addChild(door_side_top_r1)
        setRotationAngle(door_side_top_r1, 0f, 0f, 0.1047f)
        door_side_top_r1.texOffs(52, 294).addBox(0f, -21f, -12f, 1, 21, 12, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)


        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(191, 123).addBox(-21f, -13f, 0f, 0, 13, 12, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-21f, -13f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1047f)
        door_right_top_r2.texOffs(84, 159).addBox(0f, -21f, 0f, 0, 21, 12, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(181, 178).addBox(-21f, -13f, -12f, 0, 13, 12, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-21f, -13f, 0f)
        door_left_exterior.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1047f)
        door_left_top_r1.texOffs(0, 136).addBox(0f, -21f, -12f, 0, 21, 12, 0f, false)

        door_sides = ModelMapper(modelDataWrapper)
        door_sides.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_sides)
        door_sides.texOffs(245, 240).addBox(-21.5f, 0f, -17f, 1, 4, 34, 0f, false)
        door_sides.texOffs(0, 338).addBox(-22f, 0f, -11f, 1, 1, 22, 0f, false)
        door_sides.texOffs(235, 149).addBox(-20f, -33f, -11.5f, 2, 0, 23, 0f, false)

        door_side_top_1_r1 = ModelMapper(modelDataWrapper)
        door_side_top_1_r1.setPos(-21.5f, -13f, 0f)
        door_sides.addChild(door_side_top_1_r1)
        setRotationAngle(door_side_top_1_r1, 0f, 0f, 0.1047f)
        door_side_top_1_r1.texOffs(2, 30).addBox(0f, -23f, -11.5f, 0, 3, 23, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(208, 22).addBox(-20f, 0f, -10f, 40, 1, 18, 0f, false)
        end.texOffs(191, 287).addBox(-7f, -33f, -10f, 14, 33, 0, 0f, false)
        end.texOffs(0, 255).addBox(7f, -33f, -10f, 13, 33, 1, 0f, true)
        end.texOffs(33, 255).addBox(-20f, -33f, -10f, 13, 33, 1, 0f, false)
        end.texOffs(191, 100).addBox(19.5f, -13f, -9.5f, 2, 13, 22, 0f, true)
        end.texOffs(191, 100).addBox(-21.5f, -13f, -9.5f, 2, 13, 22, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-21.5f, -13f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1047f)
        upper_wall_2_r1.texOffs(0, 87).addBox(0f, -21f, -9.5f, 2, 21, 22, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(21.5f, -13f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1047f)
        upper_wall_1_r1.texOffs(0, 87).addBox(-2f, -21f, -9.5f, 2, 21, 22, 0f, true)

        end_handrails = ModelMapper(modelDataWrapper)
        end_handrails.setPos(0f, 24f, 0f)
        end_handrails.texOffs(120, 69).addBox(-17f, -37.875f, -8f, 34, 3, 3, 0f, false)
        end_handrails.texOffs(120, 64).addBox(-18f, -35.875f, -10f, 36, 3, 2, 0f, false)

        end_mid_roof_3_r1 = ModelMapper(modelDataWrapper)
        end_mid_roof_3_r1.setPos(16.05f, -32.95f, 0f)
        end_handrails.addChild(end_mid_roof_3_r1)
        setRotationAngle(end_mid_roof_3_r1, 0f, 0f, 0.2967f)
        end_mid_roof_3_r1.texOffs(0, 130).addBox(-8.05f, -2f, -8f, 8, 2, 3, 0f, true)

        end_mid_roof_4_r1 = ModelMapper(modelDataWrapper)
        end_mid_roof_4_r1.setPos(-16.05f, -32.95f, 0f)
        end_handrails.addChild(end_mid_roof_4_r1)
        setRotationAngle(end_mid_roof_4_r1, 0f, 0f, -0.2967f)
        end_mid_roof_4_r1.texOffs(0, 130).addBox(0.05f, -2f, -8f, 8, 2, 3, 0f, false)

        end_side_1 = ModelMapper(modelDataWrapper)
        end_side_1.setPos(0f, 0f, 0f)
        end_handrails.addChild(end_side_1)
        end_side_1.texOffs(0, 206).addBox(17.9f, -10.75f, -9.5f, 2, 5, 20, 0f, true)
        end_side_1.texOffs(50, 227).addBox(16.55f, -6f, -9f, 3, 4, 19, 0f, true)

        seat_bottom_r2 = ModelMapper(modelDataWrapper)
        seat_bottom_r2.setPos(0f, -1.75f, 16f)
        end_side_1.addChild(seat_bottom_r2)
        setRotationAngle(seat_bottom_r2, 0f, 0f, 0.0873f)
        seat_bottom_r2.texOffs(172, 213).addBox(10.9f, -6f, -25.5f, 9, 1, 20, 0f, true)

        seat_back_4_r1 = ModelMapper(modelDataWrapper)
        seat_back_4_r1.setPos(17.9f, -10.75f, 16f)
        end_side_1.addChild(seat_back_4_r1)
        setRotationAngle(seat_back_4_r1, 0f, 0f, 0.1309f)
        seat_back_4_r1.texOffs(177, 64).addBox(0f, -5f, -25.5f, 2, 5, 20, 0f, true)

        end_side_2 = ModelMapper(modelDataWrapper)
        end_side_2.setPos(0f, 0f, 0f)
        end_handrails.addChild(end_side_2)
        end_side_2.texOffs(0, 206).addBox(-19.9f, -10.75f, -9.5f, 2, 5, 20, 0f, false)
        end_side_2.texOffs(50, 227).addBox(-19.55f, -6f, -9f, 3, 4, 19, 0f, false)

        seat_bottom_r3 = ModelMapper(modelDataWrapper)
        seat_bottom_r3.setPos(0f, -1.75f, 16f)
        end_side_2.addChild(seat_bottom_r3)
        setRotationAngle(seat_bottom_r3, 0f, 0f, -0.0873f)
        seat_bottom_r3.texOffs(172, 213).addBox(-19.9f, -6f, -25.5f, 9, 1, 20, 0f, false)

        seat_back_3_r2 = ModelMapper(modelDataWrapper)
        seat_back_3_r2.setPos(-17.9f, -10.75f, 16f)
        end_side_2.addChild(seat_back_3_r2)
        setRotationAngle(seat_back_3_r2, 0f, 0f, -0.1309f)
        seat_back_3_r2.texOffs(177, 64).addBox(-2f, -5f, -25.5f, 2, 5, 20, 0f, false)

        handrail_end_1 = ModelMapper(modelDataWrapper)
        handrail_end_1.setPos(-1f, 4f, -12f)
        end_handrails.addChild(handrail_end_1)
        handrail_end_1.texOffs(0, 0).addBox(13.8f, -35.75f, 11.5f, 0, 0, 9, 0.2f, false)

        handrail_top_6_r1 = ModelMapper(modelDataWrapper)
        handrail_top_6_r1.setPos(11.7943f, -38.3087f, 9.5766f)
        handrail_end_1.addChild(handrail_top_6_r1)
        setRotationAngle(handrail_top_6_r1, 0f, 0.7854f, 0f)
        handrail_top_6_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_top_5_r1 = ModelMapper(modelDataWrapper)
        handrail_top_5_r1.setPos(12.1924f, -37.3779f, 9.807f)
        handrail_end_1.addChild(handrail_top_5_r1)
        setRotationAngle(handrail_top_5_r1, -2.7699f, 0.6484f, -0.6107f)
        handrail_top_5_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_top_4_r1 = ModelMapper(modelDataWrapper)
        handrail_top_4_r1.setPos(13.2474f, -36.3462f, 10.7346f)
        handrail_end_1.addChild(handrail_top_4_r1)
        setRotationAngle(handrail_top_4_r1, -0.7247f, 0.5197f, -0.2271f)
        handrail_top_4_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_right_4_r1 = ModelMapper(modelDataWrapper)
        handrail_right_4_r1.setPos(17.65f, -14.1f, 20.9f)
        handrail_end_1.addChild(handrail_right_4_r1)
        setRotationAngle(handrail_right_4_r1, 0f, 0f, -1.5708f)
        handrail_right_4_r1.texOffs(0, 0).addBox(-4f, -5f, 1.6f, 0, 0, 0, 0.2f, false)
        handrail_right_4_r1.texOffs(0, 0).addBox(-4f, -5f, 2f, 19, 0, 0, 0.2f, false)

        handrail_right_2_r1 = ModelMapper(modelDataWrapper)
        handrail_right_2_r1.setPos(12.89f, -31.4019f, 22.9f)
        handrail_end_1.addChild(handrail_right_2_r1)
        setRotationAngle(handrail_right_2_r1, 0f, 0f, -1.4573f)
        handrail_right_2_r1.texOffs(0, 0).addBox(-2f, 0f, 0f, 3, 0, 0, 0.2f, false)

        handrail_turn_5_r1 = ModelMapper(modelDataWrapper)
        handrail_turn_5_r1.setPos(13.05f, -32.6306f, 22.863f)
        handrail_end_1.addChild(handrail_turn_5_r1)
        setRotationAngle(handrail_turn_5_r1, -1.3099f, -0.0441f, 0.1412f)
        handrail_turn_5_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_4_r3 = ModelMapper(modelDataWrapper)
        handrail_turn_4_r3.setPos(13.3f, -33.6929f, 22.3738f)
        handrail_end_1.addChild(handrail_turn_4_r3)
        setRotationAngle(handrail_turn_4_r3, -1.1436f, -0.0916f, 0.1983f)
        handrail_turn_4_r3.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_turn_3_r3 = ModelMapper(modelDataWrapper)
        handrail_turn_3_r3.setPos(13.575f, -34.8306f, 21.788f)
        handrail_end_1.addChild(handrail_turn_3_r3)
        setRotationAngle(handrail_turn_3_r3, -0.7494f, -0.1284f, 0.1186f)
        handrail_turn_3_r3.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_2_r3 = ModelMapper(modelDataWrapper)
        handrail_turn_2_r3.setPos(13.7f, -35.3662f, 21.1218f)
        handrail_end_1.addChild(handrail_turn_2_r3)
        setRotationAngle(handrail_turn_2_r3, -0.6584f, -0.1103f, 0.0706f)
        handrail_turn_2_r3.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        handrail_end_2 = ModelMapper(modelDataWrapper)
        handrail_end_2.setPos(1f, 4f, -12f)
        end_handrails.addChild(handrail_end_2)
        handrail_end_2.texOffs(0, 0).addBox(-13.8f, -35.75f, 11.5f, 0, 0, 9, 0.2f, false)

        handrail_top_5_r2 = ModelMapper(modelDataWrapper)
        handrail_top_5_r2.setPos(-11.7943f, -38.3087f, 9.5766f)
        handrail_end_2.addChild(handrail_top_5_r2)
        setRotationAngle(handrail_top_5_r2, 0f, -0.7854f, 0f)
        handrail_top_5_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_top_4_r2 = ModelMapper(modelDataWrapper)
        handrail_top_4_r2.setPos(-12.1924f, -37.3779f, 9.807f)
        handrail_end_2.addChild(handrail_top_4_r2)
        setRotationAngle(handrail_top_4_r2, -2.7699f, -0.6484f, 0.6107f)
        handrail_top_4_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_top_3_r1 = ModelMapper(modelDataWrapper)
        handrail_top_3_r1.setPos(-13.2474f, -36.3462f, 10.7346f)
        handrail_end_2.addChild(handrail_top_3_r1)
        setRotationAngle(handrail_top_3_r1, -0.7247f, -0.5197f, 0.2271f)
        handrail_top_3_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_right_3_r2 = ModelMapper(modelDataWrapper)
        handrail_right_3_r2.setPos(-17.65f, -14.1f, 20.9f)
        handrail_end_2.addChild(handrail_right_3_r2)
        setRotationAngle(handrail_right_3_r2, 0f, 0f, 1.5708f)
        handrail_right_3_r2.texOffs(0, 0).addBox(4f, -5f, 1.6f, 0, 0, 0, 0.2f, false)
        handrail_right_3_r2.texOffs(0, 0).addBox(-15f, -5f, 2f, 19, 0, 0, 0.2f, false)

        handrail_right_1_r2 = ModelMapper(modelDataWrapper)
        handrail_right_1_r2.setPos(-12.89f, -31.4019f, 22.9f)
        handrail_end_2.addChild(handrail_right_1_r2)
        setRotationAngle(handrail_right_1_r2, 0f, 0f, 1.4573f)
        handrail_right_1_r2.texOffs(0, 0).addBox(-1f, 0f, 0f, 3, 0, 0, 0.2f, false)

        handrail_turn_4_r4 = ModelMapper(modelDataWrapper)
        handrail_turn_4_r4.setPos(-13.05f, -32.6306f, 22.863f)
        handrail_end_2.addChild(handrail_turn_4_r4)
        setRotationAngle(handrail_turn_4_r4, -1.3099f, 0.0441f, -0.1412f)
        handrail_turn_4_r4.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_3_r4 = ModelMapper(modelDataWrapper)
        handrail_turn_3_r4.setPos(-13.3f, -33.6929f, 22.3738f)
        handrail_end_2.addChild(handrail_turn_3_r4)
        setRotationAngle(handrail_turn_3_r4, -1.1436f, 0.0916f, -0.1983f)
        handrail_turn_3_r4.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        handrail_turn_2_r4 = ModelMapper(modelDataWrapper)
        handrail_turn_2_r4.setPos(-13.575f, -34.8306f, 21.788f)
        handrail_end_2.addChild(handrail_turn_2_r4)
        setRotationAngle(handrail_turn_2_r4, -0.7494f, 0.1284f, -0.1186f)
        handrail_turn_2_r4.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_turn_1_r4 = ModelMapper(modelDataWrapper)
        handrail_turn_1_r4.setPos(-13.7f, -35.3662f, 21.1218f)
        handrail_end_2.addChild(handrail_turn_1_r4)
        setRotationAngle(handrail_turn_1_r4, -0.6584f, 0.1103f, -0.0706f)
        handrail_turn_1_r4.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(276, 208).addBox(20.5f, 0f, -12f, 1, 4, 20, 0f, true)
        end_exterior.texOffs(120, 109).addBox(19.5f, -13f, -10.5f, 2, 13, 23, 0f, true)
        end_exterior.texOffs(276, 208).addBox(-21.5f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(222, 278).addBox(-21.5f, -13f, -10.5f, 2, 13, 23, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-21.5f, -13f, 0f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1047f)
        upper_wall_2_r2.texOffs(113, 271).addBox(0f, -23f, -11.5f, 2, 23, 24, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(21.5f, -13f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1047f)
        upper_wall_1_r2.texOffs(0, 148).addBox(-2f, -23f, -11.5f, 2, 23, 24, 0f, true)

        end_bottom_out = ModelMapper(modelDataWrapper)
        end_bottom_out.setPos(0f, 0.1f, -21f)
        end_exterior.addChild(end_bottom_out)
        end_bottom_out.texOffs(120, 56).addBox(-19.5f, -0.1f, 5f, 39, 3, 5, 0f, false)

        buttom_panel_right_4_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_right_4_r1.setPos(-12.7131f, 1.4f, 5.8639f)
        end_bottom_out.addChild(buttom_panel_right_4_r1)
        setRotationAngle(buttom_panel_right_4_r1, 0f, 0.1745f, 0f)
        buttom_panel_right_4_r1.texOffs(32, 130).addBox(-1.5f, -1.5f, -0.5f, 4, 3, 1, 0f, false)

        buttom_panel_right_3_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_right_3_r1.setPos(-15.9855f, 1.4f, 6.7858f)
        end_bottom_out.addChild(buttom_panel_right_3_r1)
        setRotationAngle(buttom_panel_right_3_r1, 0f, 0.3491f, 0f)
        buttom_panel_right_3_r1.texOffs(134, 145).addBox(-2f, -1.5f, -0.5f, 4, 3, 1, 0f, false)

        buttom_panel_right_2_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_right_2_r1.setPos(-21.5f, -0.1f, 9f)
        end_bottom_out.addChild(buttom_panel_right_2_r1)
        setRotationAngle(buttom_panel_right_2_r1, 0f, 0.5236f, 0f)
        buttom_panel_right_2_r1.texOffs(120, 145).addBox(0f, 0f, 0f, 4, 3, 3, 0f, false)

        buttom_panel_left_4_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_left_4_r1.setPos(12.7131f, 1.4f, 5.8639f)
        end_bottom_out.addChild(buttom_panel_left_4_r1)
        setRotationAngle(buttom_panel_left_4_r1, 0f, -0.1745f, 0f)
        buttom_panel_left_4_r1.texOffs(32, 130).addBox(-2.5f, -1.5f, -0.5f, 4, 3, 1, 0f, true)

        buttom_panel_left_3_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_left_3_r1.setPos(15.9855f, 1.4f, 6.7858f)
        end_bottom_out.addChild(buttom_panel_left_3_r1)
        setRotationAngle(buttom_panel_left_3_r1, 0f, -0.3491f, 0f)
        buttom_panel_left_3_r1.texOffs(134, 145).addBox(-2f, -1.5f, -0.5f, 4, 3, 1, 0f, true)

        buttom_panel_left_2_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_left_2_r1.setPos(21.5f, -0.1f, 9f)
        end_bottom_out.addChild(buttom_panel_left_2_r1)
        setRotationAngle(buttom_panel_left_2_r1, 0f, -0.5236f, 0f)
        buttom_panel_left_2_r1.texOffs(120, 145).addBox(-4f, 0f, 0f, 4, 3, 3, 0f, true)

        end_back = ModelMapper(modelDataWrapper)
        end_back.setPos(0f, 0f, -21f)
        end_exterior.addChild(end_back)
        end_back.texOffs(308, 302).addBox(-8f, -33f, 6f, 1, 33, 5, 0f, false)
        end_back.texOffs(296, 302).addBox(7f, -33f, 6f, 1, 33, 5, 0f, false)
        end_back.texOffs(0, 231).addBox(-8f, -42f, 6f, 16, 9, 6, 0f, false)
        end_back.texOffs(249, 278).addBox(-7f, -25f, 6f, 14, 19, 0, 0f, false)
        end_back.texOffs(281, 240).addBox(-7f, -34f, 10f, 14, 34, 0, 0f, false)

        front_right_panel_4_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r1.setPos(9.4774f, -21f, 6.2595f)
        end_back.addChild(front_right_panel_4_r1)
        setRotationAngle(front_right_panel_4_r1, 0f, -0.1745f, 0f)
        front_right_panel_4_r1.texOffs(311, 41).addBox(-1.5f, -21f, 0f, 3, 42, 0, 0f, false)

        front_right_panel_3_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r1.setPos(13.7738f, -20.5f, 7.5461f)
        end_back.addChild(front_right_panel_3_r1)
        setRotationAngle(front_right_panel_3_r1, 0f, -0.3491f, 0f)
        front_right_panel_3_r1.texOffs(165, 295).addBox(-3f, -20.5f, 0f, 6, 41, 0, 0f, false)

        front_right_panel_2_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_2_r1.setPos(19.1907f, -20f, 10.0731f)
        end_back.addChild(front_right_panel_2_r1)
        setRotationAngle(front_right_panel_2_r1, 0f, -0.5236f, 0f)
        front_right_panel_2_r1.texOffs(24, 298).addBox(-3f, -20f, 0f, 6, 40, 0, 0f, false)

        front_right_panel_3_r2 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r2.setPos(-9.4774f, -21f, 6.2595f)
        end_back.addChild(front_right_panel_3_r2)
        setRotationAngle(front_right_panel_3_r2, 0f, 0.1745f, 0f)
        front_right_panel_3_r2.texOffs(219, 314).addBox(-1.5f, -21f, 0f, 3, 42, 0, 0f, false)

        front_right_panel_2_r2 = ModelMapper(modelDataWrapper)
        front_right_panel_2_r2.setPos(-13.7738f, -20.5f, 7.5461f)
        end_back.addChild(front_right_panel_2_r2)
        setRotationAngle(front_right_panel_2_r2, 0f, 0.3491f, 0f)
        front_right_panel_2_r2.texOffs(177, 295).addBox(-3f, -20.5f, 0f, 6, 41, 0, 0f, false)

        front_right_panel_1_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_1_r1.setPos(-19.1907f, -20f, 10.0731f)
        end_back.addChild(front_right_panel_1_r1)
        setRotationAngle(front_right_panel_1_r1, 0f, 0.5236f, 0f)
        front_right_panel_1_r1.texOffs(36, 298).addBox(-3f, -20f, 0f, 6, 40, 0, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 0f, 0f)
        end_exterior.addChild(roof_end_exterior)
        roof_end_exterior.texOffs(137, 0).addBox(-4f, -41.375f, -15f, 4, 0, 23, 0f, false)
        roof_end_exterior.texOffs(137, 0).addBox(0f, -41.375f, -15f, 4, 0, 23, 0f, true)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(4f, -41.375f, 0f)
        roof_end_exterior.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, 0.0873f)
        outer_roof_6_r1.texOffs(125, 0).addBox(0f, 0f, -15f, 6, 0, 23, 0f, true)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(12.392f, -40.205f, 0f)
        roof_end_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, 0.2618f)
        outer_roof_5_r1.texOffs(125, 23).addBox(-2.5f, 0f, -15f, 5, 0, 23, 0f, true)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(16.2163f, -39.0449f, 0f)
        roof_end_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, 0.3491f)
        outer_roof_4_r1.texOffs(113, 101).addBox(-1.5f, 0f, -15f, 3, 0, 23, 0f, true)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(18.2687f, -37.7659f, 0f)
        roof_end_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, 0.8727f)
        outer_roof_3_r1.texOffs(3, 78).addBox(-1f, 0f, -15f, 2, 0, 23, 0f, true)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(18.6114f, -35.9228f, 0f)
        roof_end_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 1.3788f)
        outer_roof_2_r1.texOffs(57, 171).addBox(-1f, -0.5f, -15f, 2, 1, 23, 0f, true)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-4f, -41.375f, 0f)
        roof_end_exterior.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.0873f)
        outer_roof_5_r2.texOffs(125, 0).addBox(-6f, 0f, -15f, 6, 0, 23, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-12.392f, -40.205f, 0f)
        roof_end_exterior.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.2618f)
        outer_roof_4_r2.texOffs(125, 23).addBox(-2.5f, 0f, -15f, 5, 0, 23, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.2163f, -39.0449f, 0f)
        roof_end_exterior.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -0.3491f)
        outer_roof_3_r2.texOffs(113, 101).addBox(-1.5f, 0f, -15f, 3, 0, 23, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-18.2687f, -37.7659f, 0f)
        roof_end_exterior.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, -0.8727f)
        outer_roof_2_r2.texOffs(3, 78).addBox(-1f, 0f, -15f, 2, 0, 23, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-18.6114f, -35.9228f, 0f)
        roof_end_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, -1.3788f)
        outer_roof_1_r1.texOffs(57, 171).addBox(-1f, -0.5f, -15f, 2, 1, 23, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(120, 49).addBox(-20f, 0f, 4f, 40, 1, 4, 0f, false)
        head.texOffs(201, 56).addBox(19.5f, -13f, 3.5f, 2, 13, 9, 0f, true)
        head.texOffs(201, 56).addBox(-21.5f, -13f, 3.5f, 2, 13, 9, 0f, false)
        head.texOffs(243, 100).addBox(-19.5f, -37f, 4f, 39, 37, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-21.5f, -13f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1047f)
        upper_wall_2_r3.texOffs(0, 78).addBox(0f, -21f, 3.5f, 2, 21, 9, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(21.5f, -13f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1047f)
        upper_wall_1_r3.texOffs(0, 78).addBox(-2f, -21f, 3.5f, 2, 21, 9, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(0, 263).addBox(-21.5f, 0f, -23f, 1, 4, 31, 0f, false)
        head_exterior.texOffs(231, 149).addBox(-21.5f, -13f, -21.5f, 2, 13, 34, 0f, false)
        head_exterior.texOffs(0, 263).addBox(20.5f, 0f, -23f, 1, 4, 31, 0f, true)
        head_exterior.texOffs(173, 240).addBox(19.5f, -13f, -21.5f, 2, 13, 34, 0f, true)
        head_exterior.texOffs(22, 336).addBox(-20f, 0f, -22f, 40, 1, 26, 0f, false)
        head_exterior.texOffs(229, 49).addBox(-19.5f, -42f, 3f, 39, 42, 0, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(21.5f, -13f, -3f)
        head_exterior.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, -0.1047f)
        upper_wall_2_r4.texOffs(63, 236).addBox(-2f, -23f, -19.5f, 2, 23, 35, 0f, true)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(-21.5f, -13f, -3f)
        head_exterior.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, 0.1047f)
        upper_wall_1_r4.texOffs(133, 213).addBox(0f, -23f, -19.5f, 2, 23, 35, 0f, false)

        head_bottom_out = ModelMapper(modelDataWrapper)
        head_bottom_out.setPos(0f, 0.1f, -21f)
        head_exterior.addChild(head_bottom_out)
        head_bottom_out.texOffs(120, 56).addBox(-19.5f, -0.1f, -6f, 39, 3, 5, 0f, false)

        buttom_panel_right_5_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_right_5_r1.setPos(-12.7131f, 1.4f, -5.1361f)
        head_bottom_out.addChild(buttom_panel_right_5_r1)
        setRotationAngle(buttom_panel_right_5_r1, 0f, 0.1745f, 0f)
        buttom_panel_right_5_r1.texOffs(32, 130).addBox(-1.5f, -1.5f, -0.5f, 4, 3, 1, 0f, false)

        buttom_panel_right_4_r2 = ModelMapper(modelDataWrapper)
        buttom_panel_right_4_r2.setPos(-15.9855f, 1.4f, -4.2142f)
        head_bottom_out.addChild(buttom_panel_right_4_r2)
        setRotationAngle(buttom_panel_right_4_r2, 0f, 0.3491f, 0f)
        buttom_panel_right_4_r2.texOffs(134, 145).addBox(-2f, -1.5f, -0.5f, 4, 3, 1, 0f, false)

        buttom_panel_right_3_r2 = ModelMapper(modelDataWrapper)
        buttom_panel_right_3_r2.setPos(-21.5f, -0.1f, -2f)
        head_bottom_out.addChild(buttom_panel_right_3_r2)
        setRotationAngle(buttom_panel_right_3_r2, 0f, 0.5236f, 0f)
        buttom_panel_right_3_r2.texOffs(120, 145).addBox(0f, 0f, 0f, 4, 3, 3, 0f, false)

        buttom_panel_left_5_r1 = ModelMapper(modelDataWrapper)
        buttom_panel_left_5_r1.setPos(12.7131f, 1.4f, -5.1361f)
        head_bottom_out.addChild(buttom_panel_left_5_r1)
        setRotationAngle(buttom_panel_left_5_r1, 0f, -0.1745f, 0f)
        buttom_panel_left_5_r1.texOffs(32, 130).addBox(-2.5f, -1.5f, -0.5f, 4, 3, 1, 0f, true)

        buttom_panel_left_4_r2 = ModelMapper(modelDataWrapper)
        buttom_panel_left_4_r2.setPos(15.9855f, 1.4f, -4.2142f)
        head_bottom_out.addChild(buttom_panel_left_4_r2)
        setRotationAngle(buttom_panel_left_4_r2, 0f, -0.3491f, 0f)
        buttom_panel_left_4_r2.texOffs(32, 130).addBox(-2f, -1.5f, -0.5f, 4, 3, 1, 0f, true)

        buttom_panel_left_3_r2 = ModelMapper(modelDataWrapper)
        buttom_panel_left_3_r2.setPos(21.5f, -0.1f, -2f)
        head_bottom_out.addChild(buttom_panel_left_3_r2)
        setRotationAngle(buttom_panel_left_3_r2, 0f, -0.5236f, 0f)
        buttom_panel_left_3_r2.texOffs(120, 145).addBox(-4f, 0f, 0f, 4, 3, 3, 0f, true)

        head_back = ModelMapper(modelDataWrapper)
        head_back.setPos(0f, 0f, -21f)
        head_exterior.addChild(head_back)
        head_back.texOffs(284, 302).addBox(-8f, -33f, -5f, 1, 33, 5, 0f, false)
        head_back.texOffs(272, 302).addBox(7f, -33f, -5f, 1, 33, 5, 0f, false)
        head_back.texOffs(50, 206).addBox(-8f, -42f, -5f, 16, 9, 6, 0f, false)
        head_back.texOffs(269, 149).addBox(-7f, -25f, -5f, 14, 19, 0, 0f, false)
        head_back.texOffs(102, 227).addBox(-7f, -33f, -0.1f, 14, 34, 0, 0f, false)

        front_right_panel_5_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_5_r1.setPos(9.4774f, -21f, -4.7405f)
        head_back.addChild(front_right_panel_5_r1)
        setRotationAngle(front_right_panel_5_r1, 0f, -0.1745f, 0f)
        front_right_panel_5_r1.texOffs(102, 294).addBox(-1.5f, -21f, 0f, 3, 42, 0, 0f, false)

        front_right_panel_4_r2 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r2.setPos(13.7738f, -20.5f, -3.4539f)
        head_back.addChild(front_right_panel_4_r2)
        setRotationAngle(front_right_panel_4_r2, 0f, -0.3491f, 0f)
        front_right_panel_4_r2.texOffs(78, 294).addBox(-3f, -20.5f, 0f, 6, 41, 0, 0f, false)

        front_right_panel_3_r3 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r3.setPos(19.1907f, -20f, -0.9269f)
        head_back.addChild(front_right_panel_3_r3)
        setRotationAngle(front_right_panel_3_r3, 0f, -0.5236f, 0f)
        front_right_panel_3_r3.texOffs(0, 298).addBox(-3f, -20f, 0f, 6, 40, 0, 0f, false)

        front_right_panel_4_r3 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r3.setPos(-9.4774f, -21f, -4.7405f)
        head_back.addChild(front_right_panel_4_r3)
        setRotationAngle(front_right_panel_4_r3, 0f, 0.1745f, 0f)
        front_right_panel_4_r3.texOffs(309, 232).addBox(-1.5f, -21f, 0f, 3, 42, 0, 0f, false)

        front_right_panel_3_r4 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r4.setPos(-13.7738f, -20.5f, -3.4539f)
        head_back.addChild(front_right_panel_3_r4)
        setRotationAngle(front_right_panel_3_r4, 0f, 0.3491f, 0f)
        front_right_panel_3_r4.texOffs(90, 294).addBox(-3f, -20.5f, 0f, 6, 41, 0, 0f, false)

        front_right_panel_2_r3 = ModelMapper(modelDataWrapper)
        front_right_panel_2_r3.setPos(-19.1907f, -20f, -0.9269f)
        head_back.addChild(front_right_panel_2_r3)
        setRotationAngle(front_right_panel_2_r3, 0f, 0.5236f, 0f)
        front_right_panel_2_r3.texOffs(12, 298).addBox(-3f, -20f, 0f, 6, 40, 0, 0f, false)

        roof_head_exterior = ModelMapper(modelDataWrapper)
        roof_head_exterior.setPos(0f, 0f, 0f)
        head_exterior.addChild(roof_head_exterior)
        roof_head_exterior.texOffs(65, 101).addBox(-4f, -41.375f, -26f, 4, 0, 34, 0f, false)
        roof_head_exterior.texOffs(65, 101).addBox(0f, -41.375f, -26f, 4, 0, 34, 0f, true)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(4f, -41.375f, -3f)
        roof_head_exterior.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, 0.0873f)
        outer_roof_7_r1.texOffs(27, 101).addBox(0f, 0f, -23f, 6, 0, 34, 0f, true)

        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(12.392f, -40.205f, -3f)
        roof_head_exterior.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, 0.2618f)
        outer_roof_6_r2.texOffs(39, 101).addBox(-2.5f, 0f, -23f, 5, 0, 34, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(16.2163f, -39.0449f, -3f)
        roof_head_exterior.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.3491f)
        outer_roof_5_r3.texOffs(53, 101).addBox(-1.5f, 0f, -23f, 3, 0, 34, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(18.2687f, -37.7659f, -3f)
        roof_head_exterior.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.8727f)
        outer_roof_4_r3.texOffs(49, 101).addBox(-1f, 0f, -23f, 2, 0, 34, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(18.6114f, -35.9228f, -3f)
        roof_head_exterior.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.3788f)
        outer_roof_3_r3.texOffs(273, 57).addBox(-1f, -0.5f, -23f, 2, 1, 34, 0f, true)

        outer_roof_6_r3 = ModelMapper(modelDataWrapper)
        outer_roof_6_r3.setPos(-4f, -41.375f, -3f)
        roof_head_exterior.addChild(outer_roof_6_r3)
        setRotationAngle(outer_roof_6_r3, 0f, 0f, -0.0873f)
        outer_roof_6_r3.texOffs(27, 101).addBox(-6f, 0f, -23f, 6, 0, 34, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(-12.392f, -40.205f, -3f)
        roof_head_exterior.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, -0.2618f)
        outer_roof_5_r4.texOffs(39, 101).addBox(-2.5f, 0f, -23f, 5, 0, 34, 0f, false)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(-16.2163f, -39.0449f, -3f)
        roof_head_exterior.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, -0.3491f)
        outer_roof_4_r4.texOffs(53, 101).addBox(-1.5f, 0f, -23f, 3, 0, 34, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(-18.2687f, -37.7659f, -3f)
        roof_head_exterior.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0f, 0f, -0.8727f)
        outer_roof_3_r4.texOffs(49, 101).addBox(-1f, 0f, -23f, 2, 0, 34, 0f, false)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(-18.6114f, -35.9228f, -3f)
        roof_head_exterior.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -1.3788f)
        outer_roof_2_r3.texOffs(273, 57).addBox(-1f, -0.5f, -23f, 2, 1, 34, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(78, 0).addBox(-18f, -33f, -24f, 2, 0, 48, 0f, false)
        roof_window.texOffs(0, 206).addBox(-11f, -36f, -24f, 1, 1, 48, 0f, false)
        roof_window.texOffs(13, 0).addBox(-9f, -37f, -24f, 9, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-10f, -35f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.829f)
        inner_roof_4_r1.texOffs(0, 78).addBox(0f, 0f, -24f, 3, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-16f, -33f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.9163f)
        inner_roof_2_r1.texOffs(43, 0).addBox(0f, 0f, -24f, 6, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(12, 0).addBox(-18f, -33f, -16f, 2, 0, 32, 0f, false)
        roof_door.texOffs(211, 240).addBox(-11f, -36f, -16f, 1, 1, 32, 0f, false)
        roof_door.texOffs(98, 0).addBox(-9f, -37f, -16f, 9, 0, 32, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-10f, -35f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.829f)
        inner_roof_4_r2.texOffs(61, 101).addBox(0f, 0f, -16f, 3, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-16f, -33f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.9163f)
        inner_roof_2_r2.texOffs(0, 4).addBox(0f, 0f, -16f, 6, 0, 32, 0f, false)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)
        roof_end.texOffs(41, 148).addBox(-18f, -33f, -8f, 2, 0, 16, 0f, false)
        roof_end.texOffs(130, 227).addBox(-11f, -36f, -8f, 1, 1, 16, 0f, false)
        roof_end.texOffs(114, 32).addBox(-9f, -37f, -8f, 9, 0, 16, 0f, false)
        roof_end.texOffs(114, 32).addBox(0f, -37f, -8f, 9, 0, 16, 0f, true)
        roof_end.texOffs(130, 227).addBox(10f, -36f, -8f, 1, 1, 16, 0f, true)
        roof_end.texOffs(41, 148).addBox(16f, -33f, -8f, 2, 0, 16, 0f, true)

        inner_roof_9_r1 = ModelMapper(modelDataWrapper)
        inner_roof_9_r1.setPos(16f, -33f, 0f)
        roof_end.addChild(inner_roof_9_r1)
        setRotationAngle(inner_roof_9_r1, 0f, 0f, 0.9163f)
        inner_roof_9_r1.texOffs(41, 171).addBox(-6f, 0f, -8f, 6, 0, 16, 0f, true)

        inner_roof_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_7_r1.setPos(10f, -35f, 0f)
        roof_end.addChild(inner_roof_7_r1)
        setRotationAngle(inner_roof_7_r1, 0f, 0f, 0.829f)
        inner_roof_7_r1.texOffs(0, 16).addBox(-3f, 0f, -8f, 3, 0, 16, 0f, true)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(-10f, -35f, 0f)
        roof_end.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, -0.829f)
        inner_roof_4_r3.texOffs(0, 16).addBox(0f, 0f, -8f, 3, 0, 16, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-16f, -33f, 0f)
        roof_end.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.9163f)
        inner_roof_2_r3.texOffs(41, 171).addBox(0f, 0f, -8f, 6, 0, 16, 0f, false)

        roof_exterior_window = ModelMapper(modelDataWrapper)
        roof_exterior_window.setPos(0f, 24f, 0f)
        roof_exterior_window.texOffs(0, 0).addBox(-4f, -41.375f, -24f, 4, 0, 48, 0f, false)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(-4f, -41.375f, 0f)
        roof_exterior_window.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, -0.0873f)
        outer_roof_5_r5.texOffs(31, 0).addBox(-6f, 0f, -24f, 6, 0, 48, 0f, false)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(-12.392f, -40.205f, 0f)
        roof_exterior_window.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, -0.2618f)
        outer_roof_4_r5.texOffs(55, 0).addBox(-2.5f, 0f, -24f, 5, 0, 48, 0f, false)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(-16.2163f, -39.0449f, 0f)
        roof_exterior_window.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, -0.3491f)
        outer_roof_3_r5.texOffs(72, 0).addBox(-1.5f, 0f, -24f, 3, 0, 48, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(-18.2687f, -37.7659f, 0f)
        roof_exterior_window.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.8727f)
        outer_roof_2_r4.texOffs(65, 0).addBox(-1f, 0f, -24f, 2, 0, 48, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-18.6114f, -35.9228f, 0f)
        roof_exterior_window.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, -1.3788f)
        outer_roof_1_r2.texOffs(191, 100).addBox(-1f, -0.5f, -24f, 2, 1, 48, 0f, false)

        roof_exterior_door = ModelMapper(modelDataWrapper)
        roof_exterior_door.setPos(0f, 24f, 0f)
        roof_exterior_door.texOffs(16, 0).addBox(-4f, -41.375f, -16f, 4, 0, 32, 0f, false)

        outer_roof_6_r4 = ModelMapper(modelDataWrapper)
        outer_roof_6_r4.setPos(-4f, -41.375f, 0f)
        roof_exterior_door.addChild(outer_roof_6_r4)
        setRotationAngle(outer_roof_6_r4, 0f, 0f, -0.0873f)
        outer_roof_6_r4.texOffs(47, 0).addBox(-6f, 0f, -16f, 6, 0, 32, 0f, false)

        outer_roof_5_r6 = ModelMapper(modelDataWrapper)
        outer_roof_5_r6.setPos(-12.392f, -40.205f, 0f)
        roof_exterior_door.addChild(outer_roof_5_r6)
        setRotationAngle(outer_roof_5_r6, 0f, 0f, -0.2618f)
        outer_roof_5_r6.texOffs(71, 0).addBox(-2.5f, 0f, -16f, 5, 0, 32, 0f, false)

        outer_roof_4_r6 = ModelMapper(modelDataWrapper)
        outer_roof_4_r6.setPos(-16.2163f, -39.0449f, 0f)
        roof_exterior_door.addChild(outer_roof_4_r6)
        setRotationAngle(outer_roof_4_r6, 0f, 0f, -0.3491f)
        outer_roof_4_r6.texOffs(88, 0).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(-18.2687f, -37.7659f, 0f)
        roof_exterior_door.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0f, 0f, -0.8727f)
        outer_roof_3_r6.texOffs(81, 0).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(-18.6114f, -35.9228f, 0f)
        roof_exterior_door.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, -1.3788f)
        outer_roof_2_r5.texOffs(207, 116).addBox(-1f, -0.5f, -16f, 2, 1, 32, 0f, false)

        roof_head = ModelMapper(modelDataWrapper)
        roof_head.setPos(0f, 24f, 0f)
        roof_head.texOffs(6, 16).addBox(-18f, -33f, 2f, 2, 0, 6, 0f, false)
        roof_head.texOffs(8, 26).addBox(-11f, -36f, 2f, 1, 1, 6, 0f, false)
        roof_head.texOffs(55, 50).addBox(-9f, -37f, 2f, 9, 0, 6, 0f, false)
        roof_head.texOffs(55, 50).addBox(0f, -37f, 2f, 9, 0, 6, 0f, true)
        roof_head.texOffs(8, 26).addBox(10f, -36f, 2f, 1, 1, 6, 0f, true)
        roof_head.texOffs(6, 16).addBox(16f, -33f, 2f, 2, 0, 6, 0f, true)

        inner_roof_9_r2 = ModelMapper(modelDataWrapper)
        inner_roof_9_r2.setPos(16f, -33f, 0f)
        roof_head.addChild(inner_roof_9_r2)
        setRotationAngle(inner_roof_9_r2, 0f, 0f, 0.9163f)
        inner_roof_9_r2.texOffs(91, 50).addBox(-6f, 0f, 2f, 6, 0, 6, 0f, true)

        inner_roof_7_r2 = ModelMapper(modelDataWrapper)
        inner_roof_7_r2.setPos(10f, -35f, 0f)
        roof_head.addChild(inner_roof_7_r2)
        setRotationAngle(inner_roof_7_r2, 0f, 0f, 0.829f)
        inner_roof_7_r2.texOffs(0, 16).addBox(-3f, 0f, 2f, 3, 0, 6, 0f, true)

        inner_roof_4_r4 = ModelMapper(modelDataWrapper)
        inner_roof_4_r4.setPos(-10f, -35f, 0f)
        roof_head.addChild(inner_roof_4_r4)
        setRotationAngle(inner_roof_4_r4, 0f, 0f, -0.829f)
        inner_roof_4_r4.texOffs(0, 16).addBox(0f, 0f, 2f, 3, 0, 6, 0f, false)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(-16f, -33f, 0f)
        roof_head.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, -0.9163f)
        inner_roof_2_r4.texOffs(91, 50).addBox(0f, 0f, 2f, 6, 0, 6, 0f, false)

        roof_window_light = ModelMapper(modelDataWrapper)
        roof_window_light.setPos(0f, 24f, 0f)
        roof_window_light.texOffs(177, 49).addBox(-12.4f, -38.5f, -24f, 2, 3, 48, 0f, false)

        roof_door_light = ModelMapper(modelDataWrapper)
        roof_door_light.setPos(0f, 24f, 0f)
        roof_door_light.texOffs(193, 65).addBox(-12.4f, -38.5f, -16f, 2, 3, 32, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)
        roof_end_light.texOffs(206, 97).addBox(-9f, -34.75f, -8.1f, 18, 0, 3, 0f, false)
        roof_end_light.texOffs(212, 84).addBox(-12.4f, -38.5f, -5f, 2, 3, 13, 0f, false)
        roof_end_light.texOffs(212, 84).addBox(10.4f, -38.5f, -5f, 2, 3, 13, 0f, true)

        roof_head_light = ModelMapper(modelDataWrapper)
        roof_head_light.setPos(0f, 24f, 0f)
        roof_head_light.texOffs(219, 91).addBox(-12.4f, -38.5f, 2f, 2, 3, 6, 0f, false)
        roof_head_light.texOffs(219, 91).addBox(10.4f, -38.5f, 2f, 2, 3, 6, 0f, true)

        handrail_door_type_1 = ModelMapper(modelDataWrapper)
        handrail_door_type_1.setPos(0f, 24f, 0f)
        handrail_door_type_1.texOffs(0, 0).addBox(0f, -37f, 0f, 0, 37, 0, 0.2f, false)

        handrail_door_type_2 = ModelMapper(modelDataWrapper)
        handrail_door_type_2.setPos(0f, 24f, 0f)
        handrail_door_type_2.texOffs(0, 0).addBox(0f, -14.25f, 0f, 0, 15, 0, 0.2f, false)
        handrail_door_type_2.texOffs(0, 0).addBox(0f, -37f, 0f, 0, 6, 0, 0.2f, false)

        handrail_curve = ModelMapper(modelDataWrapper)
        handrail_curve.setPos(0.175f, 3.25f, 15.1f)
        handrail_door_type_2.addChild(handrail_curve)
        handrail_curve.texOffs(0, 0).addBox(-0.175f, -34.1585f, -16.6f, 0, 0, 3, 0.2f, false)
        handrail_curve.texOffs(0, 0).addBox(-0.175f, -17.5f, -16.6f, 0, 0, 3, 0.2f, false)

        handrail_curve_12_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_12_r1.setPos(-0.175f, -17.7828f, -17.1464f)
        handrail_curve.addChild(handrail_curve_12_r1)
        setRotationAngle(handrail_curve_12_r1, -0.7854f, 0f, 0f)
        handrail_curve_12_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_12_r1, -0.7854f, 0f, 0f)
        handrail_curve_12_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_10_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_10_r1.setPos(-0.175f, -18.056f, -17.356f)
        handrail_curve.addChild(handrail_curve_10_r1)
        setRotationAngle(handrail_curve_10_r1, -1.0472f, 0f, 0f)
        handrail_curve_10_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_10_r1, -1.0472f, 0f, 0f)
        handrail_curve_10_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_9_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_9_r1.setPos(-0.175f, -17.5732f, -16.8732f)
        handrail_curve.addChild(handrail_curve_9_r1)
        setRotationAngle(handrail_curve_9_r1, -0.5236f, 0f, 0f)
        handrail_curve_9_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_9_r1, -0.5236f, 0f, 0f)
        handrail_curve_9_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_7_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_7_r1.setPos(-0.175f, -25.8043f, -17.4293f)
        handrail_curve.addChild(handrail_curve_7_r1)
        setRotationAngle(handrail_curve_7_r1, 0f, 0f, -1.5708f)
        handrail_curve_7_r1.texOffs(0, 0).addBox(-7.5f, 0f, 0f, 15, 0, 0, 0.2f, false)
        handrail_curve_7_r1.texOffs(0, 0).addBox(-7.5f, 0f, 4.6585f, 15, 0, 0, 0.2f, false)

        handrail_curve_6_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_6_r1.setPos(-0.175f, -33.6025f, -17.356f)
        handrail_curve.addChild(handrail_curve_6_r1)
        setRotationAngle(handrail_curve_6_r1, 1.0472f, 0f, 0f)
        handrail_curve_6_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_6_r1, 1.0472f, 0f, 0f)
        handrail_curve_6_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_5_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_5_r1.setPos(-0.175f, -33.8757f, -17.1464f)
        handrail_curve.addChild(handrail_curve_5_r1)
        setRotationAngle(handrail_curve_5_r1, 0.7854f, 0f, 0f)
        handrail_curve_5_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_5_r1, 0.7854f, 0f, 0f)
        handrail_curve_5_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_3_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_3_r1.setPos(-0.175f, -34.0853f, -16.8732f)
        handrail_curve.addChild(handrail_curve_3_r1)
        setRotationAngle(handrail_curve_3_r1, 0.5236f, 0f, 0f)
        handrail_curve_3_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_3_r1, 0.5236f, 0f, 0f)
        handrail_curve_3_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_11_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_11_r1.setPos(-0.175f, -17.7828f, -13.0536f)
        handrail_curve.addChild(handrail_curve_11_r1)
        setRotationAngle(handrail_curve_11_r1, 0.7854f, 0f, 0f)
        handrail_curve_11_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_11_r1, 0.7854f, 0f, 0f)
        handrail_curve_11_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_9_r2 = ModelMapper(modelDataWrapper)
        handrail_curve_9_r2.setPos(-0.175f, -18.056f, -12.844f)
        handrail_curve.addChild(handrail_curve_9_r2)
        setRotationAngle(handrail_curve_9_r2, 1.0472f, 0f, 0f)
        handrail_curve_9_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_9_r2, 1.0472f, 0f, 0f)
        handrail_curve_9_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_8_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_8_r1.setPos(-0.175f, -17.5732f, -13.3268f)
        handrail_curve.addChild(handrail_curve_8_r1)
        setRotationAngle(handrail_curve_8_r1, 0.5236f, 0f, 0f)
        handrail_curve_8_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_8_r1, 0.5236f, 0f, 0f)
        handrail_curve_8_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_5_r2 = ModelMapper(modelDataWrapper)
        handrail_curve_5_r2.setPos(-0.175f, -33.6025f, -12.844f)
        handrail_curve.addChild(handrail_curve_5_r2)
        setRotationAngle(handrail_curve_5_r2, -1.0472f, 0f, 0f)
        handrail_curve_5_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_5_r2, -1.0472f, 0f, 0f)
        handrail_curve_5_r2.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_4_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_4_r1.setPos(-0.175f, -33.8757f, -13.0536f)
        handrail_curve.addChild(handrail_curve_4_r1)
        setRotationAngle(handrail_curve_4_r1, -0.7854f, 0f, 0f)
        handrail_curve_4_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        setRotationAngle(handrail_curve_4_r1, -0.7854f, 0f, 0f)
        handrail_curve_4_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        handrail_curve_2_r1 = ModelMapper(modelDataWrapper)
        handrail_curve_2_r1.setPos(-0.175f, -34.0853f, -13.3268f)
        handrail_curve.addChild(handrail_curve_2_r1)
        setRotationAngle(handrail_curve_2_r1, -0.5236f, 0f, 0f)
        handrail_curve_2_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.2f, false)

        roof_handle = ModelMapper(modelDataWrapper)
        roof_handle.setPos(0f, 24f, 0f)
        roof_handle.texOffs(0, 0).addBox(-4f, -37.2f, -24f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(-4f, -37.2f, -8f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(-4f, -37.2f, 8f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(-4f, -37.2f, 24f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(4f, -37.2f, -24f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(4f, -37.2f, -8f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(4f, -37.2f, 8f, 0, 2, 0, 0.2f, false)
        roof_handle.texOffs(0, 0).addBox(4f, -37.2f, 24f, 0, 2, 0, 0.2f, false)

        roof_handrail_curve_19_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_19_r1.setPos(0.2215f, -35f, -31.2785f)
        roof_handle.addChild(roof_handrail_curve_19_r1)
        setRotationAngle(roof_handrail_curve_19_r1, 0f, 1.5708f, 0f)
        roof_handrail_curve_19_r1.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        roof_handrail_curve_22_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_22_r1.setPos(1.8608f, -35f, -30.7053f)
        roof_handle.addChild(roof_handrail_curve_22_r1)
        setRotationAngle(roof_handrail_curve_22_r1, 0f, 1.0472f, 0f)
        roof_handrail_curve_22_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_23_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_23_r1.setPos(3.4268f, -35f, -29.1392f)
        roof_handle.addChild(roof_handrail_curve_23_r1)
        setRotationAngle(roof_handrail_curve_23_r1, 0f, 0.5236f, 0f)
        roof_handrail_curve_23_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_18_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_18_r1.setPos(-0.2215f, -35f, -31.2785f)
        roof_handle.addChild(roof_handrail_curve_18_r1)
        setRotationAngle(roof_handrail_curve_18_r1, 0f, -1.5708f, 0f)
        roof_handrail_curve_18_r1.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        roof_handrail_curve_21_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_21_r1.setPos(-1.8608f, -35f, -30.7053f)
        roof_handle.addChild(roof_handrail_curve_21_r1)
        setRotationAngle(roof_handrail_curve_21_r1, 0f, -1.0472f, 0f)
        roof_handrail_curve_21_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_22_r2 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_22_r2.setPos(-3.4268f, -35f, -29.1392f)
        roof_handle.addChild(roof_handrail_curve_22_r2)
        setRotationAngle(roof_handrail_curve_22_r2, 0f, -0.5236f, 0f)
        roof_handrail_curve_22_r2.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_18_r2 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_18_r2.setPos(0.2215f, -35f, 31.2785f)
        roof_handle.addChild(roof_handrail_curve_18_r2)
        setRotationAngle(roof_handrail_curve_18_r2, 0f, -1.5708f, 0f)
        roof_handrail_curve_18_r2.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        roof_handrail_curve_21_r2 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_21_r2.setPos(1.8608f, -35f, 30.7053f)
        roof_handle.addChild(roof_handrail_curve_21_r2)
        setRotationAngle(roof_handrail_curve_21_r2, 0f, -1.0472f, 0f)
        roof_handrail_curve_21_r2.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_22_r3 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_22_r3.setPos(3.4268f, -35f, 29.1392f)
        roof_handle.addChild(roof_handrail_curve_22_r3)
        setRotationAngle(roof_handrail_curve_22_r3, 0f, -0.5236f, 0f)
        roof_handrail_curve_22_r3.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_17_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_17_r1.setPos(-0.2215f, -35f, 31.2785f)
        roof_handle.addChild(roof_handrail_curve_17_r1)
        setRotationAngle(roof_handrail_curve_17_r1, 0f, 1.5708f, 0f)
        roof_handrail_curve_17_r1.texOffs(0, 0).addBox(0f, 0f, -0.5f, 0, 0, 1, 0.2f, false)

        roof_handrail_curve_20_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_20_r1.setPos(-1.8608f, -35f, 30.7053f)
        roof_handle.addChild(roof_handrail_curve_20_r1)
        setRotationAngle(roof_handrail_curve_20_r1, 0f, 1.0472f, 0f)
        roof_handrail_curve_20_r1.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_curve_21_r3 = ModelMapper(modelDataWrapper)
        roof_handrail_curve_21_r3.setPos(-3.4268f, -35f, 29.1392f)
        roof_handle.addChild(roof_handrail_curve_21_r3)
        setRotationAngle(roof_handrail_curve_21_r3, 0f, 0.5236f, 0f)
        roof_handrail_curve_21_r3.texOffs(0, 0).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        roof_handrail_2_r1 = ModelMapper(modelDataWrapper)
        roof_handrail_2_r1.setPos(4f, -35f, 0f)
        roof_handle.addChild(roof_handrail_2_r1)
        setRotationAngle(roof_handrail_2_r1, -1.5708f, 0f, 0f)
        roof_handrail_2_r1.texOffs(0, 0).addBox(0f, -28f, 0f, 0, 56, 0, 0.2f, false)
        roof_handrail_2_r1.texOffs(0, 0).addBox(-8f, -28f, 0f, 0, 56, 0, 0.2f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        front_right_panel_6_r1 = ModelMapper(modelDataWrapper)
        front_right_panel_6_r1.setPos(9.4774f, -21f, -25.7655f)
        headlights.addChild(front_right_panel_6_r1)
        setRotationAngle(front_right_panel_6_r1, 0f, -0.1745f, 0f)
        front_right_panel_6_r1.texOffs(18, 33).addBox(-1.5f, 10.75f, 0f, 3, 6, 0, 0f, true)

        front_right_panel_5_r2 = ModelMapper(modelDataWrapper)
        front_right_panel_5_r2.setPos(13.7738f, -20.5f, -24.4789f)
        headlights.addChild(front_right_panel_5_r2)
        setRotationAngle(front_right_panel_5_r2, 0f, -0.3491f, 0f)
        front_right_panel_5_r2.texOffs(6, 33).addBox(-3f, 10.25f, 0f, 6, 6, 0, 0f, true)

        front_right_panel_4_r4 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r4.setPos(-9.4774f, -21f, -25.7655f)
        headlights.addChild(front_right_panel_4_r4)
        setRotationAngle(front_right_panel_4_r4, 0f, 0.1745f, 0f)
        front_right_panel_4_r4.texOffs(18, 33).addBox(-1.5f, 10.75f, 0f, 3, 6, 0, 0f, false)

        front_right_panel_3_r5 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r5.setPos(-13.7738f, -20.5f, -24.4789f)
        headlights.addChild(front_right_panel_3_r5)
        setRotationAngle(front_right_panel_3_r5, 0f, 0.3491f, 0f)
        front_right_panel_3_r5.texOffs(6, 33).addBox(-3f, 10.25f, 0f, 6, 6, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        front_right_panel_5_r3 = ModelMapper(modelDataWrapper)
        front_right_panel_5_r3.setPos(13.7738f, -20.5f, -24.4789f)
        tail_lights.addChild(front_right_panel_5_r3)
        setRotationAngle(front_right_panel_5_r3, 0f, -0.3491f, 0f)
        front_right_panel_5_r3.texOffs(18, 39).addBox(-3f, 9.5f, 0f, 6, 6, 0, 0f, true)

        front_right_panel_4_r5 = ModelMapper(modelDataWrapper)
        front_right_panel_4_r5.setPos(19.1907f, -20f, -21.9519f)
        tail_lights.addChild(front_right_panel_4_r5)
        setRotationAngle(front_right_panel_4_r5, 0f, -0.5236f, 0f)
        front_right_panel_4_r5.texOffs(6, 39).addBox(-3f, 9f, 0f, 6, 6, 0, 0f, true)

        front_right_panel_3_r6 = ModelMapper(modelDataWrapper)
        front_right_panel_3_r6.setPos(-13.7738f, -20.5f, -24.4789f)
        tail_lights.addChild(front_right_panel_3_r6)
        setRotationAngle(front_right_panel_3_r6, 0f, 0.3491f, 0f)
        front_right_panel_3_r6.texOffs(18, 39).addBox(-3f, 9.5f, 0f, 6, 6, 0, 0f, false)

        front_right_panel_2_r4 = ModelMapper(modelDataWrapper)
        front_right_panel_2_r4.setPos(-19.1907f, -20f, -21.9519f)
        tail_lights.addChild(front_right_panel_2_r4)
        setRotationAngle(front_right_panel_2_r4, 0f, 0.5236f, 0f)
        front_right_panel_2_r4.texOffs(6, 39).addBox(-3f, 9f, 0f, 6, 6, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        light_plate_1_r1 = ModelMapper(modelDataWrapper)
        light_plate_1_r1.setPos(-20.4294f, -31.1645f, 0f)
        door_light.addChild(light_plate_1_r1)
        setRotationAngle(light_plate_1_r1, 0f, 0f, 1.5708f)
        light_plate_1_r1.texOffs(0, 45).addBox(-1.5f, -1.1f, -1f, 1, 1, 1, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20.4294f, -31.1645f, -1.025f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 1.5708f)
        light_r1.texOffs(48, 12).addBox(-1.5f, -1.1f, 0f, 1, 1, 0, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20.4294f, -31.1645f, -1.025f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 1.5708f)
        light_r2.texOffs(48, 10).addBox(-1.5f, -1.1f, 0.01f, 1, 1, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrails.setModelPart()
        window_display.setModelPart()
        window_exterior.setModelPart()
        side_panel.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        end.setModelPart()
        end_handrails.setModelPart()
        end_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        roof_window.setModelPart()
        roof_door.setModelPart()
        roof_end.setModelPart()
        roof_exterior_window.setModelPart()
        roof_exterior_door.setModelPart()
        roof_head.setModelPart()
        roof_window_light.setModelPart()
        roof_door_light.setModelPart()
        roof_end_light.setModelPart()
        roof_head_light.setModelPart()
        handrail_door_type_1.setModelPart()
        handrail_door_type_2.setModelPart()
        roof_handle.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelR179 {
        return ModelR179(doorAnimationType, renderDoorOverlay)
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
        val frontWindow = isIndex(0, position, getWindowPositions())
        val endWindow = isIndex(-1, position, getWindowPositions())

        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(roof_window_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                    if (frontWindow) {
                        renderOnceFlipped(window_display, matrices, vertices, light, position.toFloat())
                    }
                    if (!frontWindow && !endWindow) {
                        renderOnce(roof_handle, matrices, vertices, light, position.toFloat())
                    }
                    if (endWindow) {
                        renderOnce(window_display, matrices, vertices, light, position.toFloat())
                    }
                }
                renderMirror(side_panel, matrices, vertices, light, (position - 27).toFloat())
                renderMirror(side_panel, matrices, vertices, light, (position + 27).toFloat())
            }

            RenderStage.EXTERIOR -> {
                renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_window, matrices, vertices, light, position.toFloat())
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
        val firstDoor = isIndex(0, position, getDoorPositions())
        val lastDoor = isIndex(-1, position, getDoorPositions())

        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(roof_door_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                door_right.setOffset(doorRightX, 0, doorRightZ)
                door_left.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_right.setOffset(doorLeftX, 0, doorLeftZ)
                door_left.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                    if (getDoorPositions()!!.size > 2 && !firstDoor && !lastDoor) {
                        renderOnce(handrail_door_type_2, matrices, vertices, light, position.toFloat())
                    } else {
                        renderOnce(handrail_door_type_1, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                door_right_exterior.setOffset(doorRightX, 0, doorRightZ)
                door_left_exterior.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                door_right_exterior.setOffset(doorLeftX, 0, doorLeftZ)
                door_left_exterior.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnce(roof_head_light, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(roof_head_light, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnce(roof_end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                    renderOnce(end_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position + 11).toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(end_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position - 11).toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
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
            -21.5f / 16,
            -13f / 16,
            -getWindowPositions()!![0] / 16f,
            0.12f,
            -0.98f,
            -0.01f,
            -6f,
            90f,
            1.16f,
            0.24f,
            -0x6700,
            -0x6700,
            2f,
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, true),
            true,
            0,
            1
        )
        renderFrontDestination(
            matrices, font, immediate,
            -21.5f / 16, -13f / 16, -getWindowPositions()!![0] / 16f, -0.6f, -0.95f, -0.01f,
            -6f, 90f, 0.22f, 0.24f,
            -0x6700, -0x6700, 2f, routeNumber, false, 0, 1
        )
        renderFrontDestination(
            matrices, font, immediate,
            0f, -2.28f, getEndPositions()!![0] / 16f - 1.62f, 0f, 0f, -0.01f,
            0f, 0f, 0.5f, 0.36f,
            -0x10000, -0x10000, 2f, routeNumber, false, car, totalCars
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    companion object {
        private const val DOOR_MAX = 12
    }
}
