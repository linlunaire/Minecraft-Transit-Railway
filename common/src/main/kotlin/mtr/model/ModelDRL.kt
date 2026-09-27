package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelDRL private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelDRL?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_handrails_1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val window_handrails_2: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_5_r1: ModelMapper
    private val upper_wall_3_r1: ModelMapper
    private val floor_4_r1: ModelMapper
    private val floor_3_r1: ModelMapper
    private val roof_window_1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_window_2: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_window_3: ModelMapper
    private val inner_roof_5_r2: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val roof_window_light: ModelMapper
    private val window_light_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val door: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_handrail_1: ModelMapper
    private val handrail_11_r1: ModelMapper
    private val door_handrail_2: ModelMapper
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end: ModelMapper
    private val handrail_2_r3: ModelMapper
    private val inner_roof_7_r1: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val inner_roof_2: ModelMapper
    private val inner_roof_6_r2: ModelMapper
    private val inner_roof_4_r4: ModelMapper
    private val inner_roof_2_r4: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val outer_roof_1_r3: ModelMapper
    private val roof_light: ModelMapper
    private val roof_light_r1: ModelMapper
    private val roof_end_light: ModelMapper
    private val light_5_r1: ModelMapper
    private val light_4_r1: ModelMapper
    private val light_3_r1: ModelMapper
    private val light_2_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val head_exterior: ModelMapper
    private val driver_door_upper_2_r1: ModelMapper
    private val driver_door_upper_1_r1: ModelMapper
    private val front: ModelMapper
    private val bottom_r1: ModelMapper
    private val front_middle_top_r1: ModelMapper
    private val front_panel_2_r1: ModelMapper
    private val front_panel_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val front_side_bottom_1_r1: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_1_r4: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val outer_roof_8_r1: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val nose: ModelMapper
    private val center_nose: ModelMapper
    private val nose_5_r1: ModelMapper
    private val nose_4_r1: ModelMapper
    private val nose_2_r1: ModelMapper
    private val nose_side_1: ModelMapper
    private val nose_18_r1: ModelMapper
    private val nose_17_r1: ModelMapper
    private val nose_16_r1: ModelMapper
    private val nose_15_r1: ModelMapper
    private val nose_14_r1: ModelMapper
    private val nose_13_r1: ModelMapper
    private val nose_12_r1: ModelMapper
    private val nose_11_r1: ModelMapper
    private val nose_10_r1: ModelMapper
    private val nose_9_r1: ModelMapper
    private val nose_8_r1: ModelMapper
    private val nose_7_r1: ModelMapper
    private val nose_6_r1: ModelMapper
    private val nose_5_r2: ModelMapper
    private val nose_4_r2: ModelMapper
    private val nose_3_r1: ModelMapper
    private val nose_2_r2: ModelMapper
    private val nose_side_2: ModelMapper
    private val nose_19_r1: ModelMapper
    private val nose_18_r2: ModelMapper
    private val nose_17_r2: ModelMapper
    private val nose_16_r2: ModelMapper
    private val nose_15_r2: ModelMapper
    private val nose_14_r2: ModelMapper
    private val nose_13_r2: ModelMapper
    private val nose_12_r2: ModelMapper
    private val nose_11_r2: ModelMapper
    private val nose_10_r2: ModelMapper
    private val nose_9_r2: ModelMapper
    private val nose_8_r2: ModelMapper
    private val nose_7_r2: ModelMapper
    private val nose_6_r2: ModelMapper
    private val nose_5_r3: ModelMapper
    private val nose_4_r3: ModelMapper
    private val nose_3_r2: ModelMapper
    private val roof_head_exterior: ModelMapper
    private val vent_3_r1: ModelMapper
    private val vent_2_r2: ModelMapper
    private val outer_roof_3: ModelMapper
    private val outer_roof_6_r2: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val outer_roof_4: ModelMapper
    private val outer_roof_6_r3: ModelMapper
    private val outer_roof_5_r6: ModelMapper
    private val outer_roof_4_r6: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val outer_roof_2_r6: ModelMapper
    private val headlights: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val headlight_4_r1: ModelMapper
    private val headlight_3_r1: ModelMapper
    private val headlight_2_r2: ModelMapper
    private val headlight_1_r1: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_1_r5: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper
    private val side_panel_1: ModelMapper
    private val handrail_r1: ModelMapper
    private val side_panel_2: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val seat_1: ModelMapper
    private val seat_back_2_r1: ModelMapper
    private val seat_2: ModelMapper
    private val seat_back_3_r1: ModelMapper
    private val seat_curve: ModelMapper
    private val seat_panel_2_r1: ModelMapper
    private val statue_box: ModelMapper
    private val statue_box_3_r1: ModelMapper
    private val statue_box_1_r1: ModelMapper
    private val seat_side_1: ModelMapper
    private val seat_back_6_r1: ModelMapper
    private val seat_top_5_r1: ModelMapper
    private val seat_top_7_r1: ModelMapper
    private val seat_back_3_r2: ModelMapper
    private val seat_bottom_4_r1: ModelMapper
    private val seat_bottom_5_r1: ModelMapper
    private val seat_side_2: ModelMapper
    private val seat_top_8_r1: ModelMapper
    private val seat_top_6_r1: ModelMapper
    private val seat_back_5_r1: ModelMapper
    private val seat_bottom_6_r1: ModelMapper
    private val seat_back_4_r1: ModelMapper
    private val seat_bottom_3_r1: ModelMapper
    private val window_edge: ModelMapper
    private val edge_side_1: ModelMapper
    private val window_edge_2_r1: ModelMapper
    private val window_edge_1_r1: ModelMapper
    private val edge_side_2: ModelMapper
    private val window_edge_3_r1: ModelMapper
    private val window_edge_2_r2: ModelMapper
    private val statue_box_translucent: ModelMapper
    private val statue_box_translucent_3_r1: ModelMapper
    private val statue_box_translucent_1_r1: ModelMapper

    constructor() : this(DoorAnimationType.STANDARD_SLOW, true)

    init {
        val textureWidth = 360
        val textureHeight = 360

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 83).addBox(-20f, 0f, -20f, 20, 1, 40, 0f, false)
        window.texOffs(202, 206).addBox(-20f, -14f, -20f, 2, 14, 40, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(134, 12).addBox(0f, -19f, -20f, 2, 19, 40, 0f, false)

        window_handrails_1 = ModelMapper(modelDataWrapper)
        window_handrails_1.setPos(0f, 24f, 0f)
        window_handrails_1.texOffs(359, 3).addBox(0f, -33f, -40f, 0, 33, 0, 0.2f, false)
        window_handrails_1.texOffs(359, 3).addBox(0f, -33f, 20f, 0, 33, 0, 0.2f, false)
        window_handrails_1.texOffs(359, 3).addBox(0f, -33f, 40f, 0, 33, 0, 0.2f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 34f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 26f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 14f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 8f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 2f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 46f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, 52f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -46f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -52f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -58f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -34f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -28f, 3, 4, 0, 0f, false)
        window_handrails_1.texOffs(17, 44).addBox(-1.5f, -32f, -22f, 3, 4, 0, 0f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(0f, 0f, 0f)
        window_handrails_1.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, -1.5708f, 0f, 0f)
        handrail_3_r1.texOffs(352, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(0f, 0f, 40f)
        window_handrails_1.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0f, 0f)
        handrail_2_r1.texOffs(352, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(0f, 0f, -40f)
        window_handrails_1.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, -1.5708f, 0f, 0f)
        handrail_1_r1.texOffs(352, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        window_handrails_2 = ModelMapper(modelDataWrapper)
        window_handrails_2.setPos(0f, 24f, 0f)
        window_handrails_2.texOffs(359, 3).addBox(0f, -33f, -40f, 0, 33, 0, 0.2f, false)
        window_handrails_2.texOffs(359, 3).addBox(0f, -33f, -20f, 0, 33, 0, 0.2f, false)
        window_handrails_2.texOffs(359, 3).addBox(0f, -33f, 40f, 0, 33, 0, 0.2f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 34f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 28f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 22f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -2f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -8f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 46f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 52f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -46f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -52f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, 58f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -34f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -26f, 3, 4, 0, 0f, false)
        window_handrails_2.texOffs(17, 44).addBox(-1.5f, -32f, -14f, 3, 4, 0, 0f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(0f, 0f, 0f)
        window_handrails_2.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, -1.5708f, 0f, 0f)
        handrail_4_r1.texOffs(352, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(0f, 0f, 40f)
        window_handrails_2.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, -1.5708f, 0f, 0f)
        handrail_3_r2.texOffs(352, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(0f, 0f, -40f)
        window_handrails_2.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, -1.5708f, 0f, 0f)
        handrail_2_r2.texOffs(352, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(0, 0).addBox(-20f, -14f, -66f, 1, 14, 132, 0f, false)

        upper_wall_5_r1 = ModelMapper(modelDataWrapper)
        upper_wall_5_r1.setPos(40.6269f, -7.2639f, 65f)
        window_exterior.addChild(upper_wall_5_r1)
        setRotationAngle(upper_wall_5_r1, 0f, -1.5708f, 0.1107f)
        upper_wall_5_r1.texOffs(131, 146).addBox(-5f, -19f, 60f, 6, 19, 1, 0f, false)
        upper_wall_5_r1.texOffs(131, 146).addBox(-131f, -19f, 60f, 6, 19, 1, 0f, true)

        upper_wall_3_r1 = ModelMapper(modelDataWrapper)
        upper_wall_3_r1.setPos(-20f, -14f, 0f)
        window_exterior.addChild(upper_wall_3_r1)
        setRotationAngle(upper_wall_3_r1, 0f, 0f, 0.1107f)
        upper_wall_3_r1.texOffs(120, 198).addBox(0f, -19f, -60f, 1, 19, 40, 0f, false)
        upper_wall_3_r1.texOffs(120, 198).addBox(0f, -19f, -20f, 1, 19, 40, 0f, false)
        upper_wall_3_r1.texOffs(120, 198).addBox(0f, -19f, 20f, 1, 19, 40, 0f, false)

        floor_4_r1 = ModelMapper(modelDataWrapper)
        floor_4_r1.setPos(30f, 0f, 84f)
        window_exterior.addChild(floor_4_r1)
        setRotationAngle(floor_4_r1, 0f, -1.5708f, 0f)
        floor_4_r1.texOffs(0, 67).addBox(-148f, 0f, 50f, 14, 4, 1, 0f, true)
        floor_4_r1.texOffs(0, 67).addBox(-34f, 0f, 50f, 14, 4, 1, 0f, false)

        floor_3_r1 = ModelMapper(modelDataWrapper)
        floor_3_r1.setPos(-20f, 0f, 70f)
        window_exterior.addChild(floor_3_r1)
        setRotationAngle(floor_3_r1, 0f, -1.5708f, 0f)
        floor_3_r1.texOffs(0, 126).addBox(-120f, 0f, 0f, 50, 4, 1, 0f, true)
        floor_3_r1.texOffs(0, 126).addBox(-70f, 0f, 0f, 50, 4, 1, 0f, false)

        roof_window_1 = ModelMapper(modelDataWrapper)
        roof_window_1.setPos(0f, 24f, 0f)
        roof_window_1.texOffs(54, 33).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window_1.texOffs(32, 33).addBox(-10f, -34f, -24f, 7, 0, 48, 0f, false)
        roof_window_1.texOffs(68, 33).addBox(-2f, -33f, -24f, 2, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-2f, -33f, 0f)
        roof_window_1.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, 0.5236f)
        inner_roof_4_r1.texOffs(72, 0).addBox(-2f, 0f, -24f, 2, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13f, -32f, 0f)
        roof_window_1.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.5236f)
        inner_roof_2_r1.texOffs(46, 33).addBox(0f, 0f, -24f, 4, 0, 48, 0f, false)

        roof_window_2 = ModelMapper(modelDataWrapper)
        roof_window_2.setPos(0f, 24f, 0f)
        roof_window_2.texOffs(92, 83).addBox(-16f, -32f, -16f, 3, 0, 32, 0f, false)
        roof_window_2.texOffs(122, 0).addBox(-10f, -34f, -16f, 7, 0, 32, 0f, false)
        roof_window_2.texOffs(136, 0).addBox(-2f, -33f, -16f, 2, 0, 32, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-2f, -33f, 8f)
        roof_window_2.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, 0.5236f)
        inner_roof_5_r1.texOffs(0, 146).addBox(-2f, 0f, -24f, 2, 0, 32, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-13f, -32f, 8f)
        roof_window_2.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.5236f)
        inner_roof_3_r1.texOffs(0, 33).addBox(0f, 0f, -24f, 4, 0, 32, 0f, false)

        roof_window_3 = ModelMapper(modelDataWrapper)
        roof_window_3.setPos(0f, 24f, 0f)
        roof_window_3.texOffs(54, 33).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window_3.texOffs(32, 33).addBox(-10f, -34f, -24f, 7, 0, 48, 0f, false)
        roof_window_3.texOffs(68, 33).addBox(-2f, -33f, -24f, 2, 0, 48, 0f, false)

        inner_roof_5_r2 = ModelMapper(modelDataWrapper)
        inner_roof_5_r2.setPos(-2f, -33f, 0f)
        roof_window_3.addChild(inner_roof_5_r2)
        setRotationAngle(inner_roof_5_r2, 0f, 0f, 0.5236f)
        inner_roof_5_r2.texOffs(72, 0).addBox(-2f, 0f, -24f, 2, 0, 48, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-13f, -32f, 0f)
        roof_window_3.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.5236f)
        inner_roof_3_r2.texOffs(252, 0).addBox(0f, 0f, -24f, 4, 0, 48, 0f, false)

        roof_window_light = ModelMapper(modelDataWrapper)
        roof_window_light.setPos(0f, 24f, 0f)


        window_light_r1 = ModelMapper(modelDataWrapper)
        window_light_r1.setPos(-13f, -32f, 0f)
        roof_window_light.addChild(window_light_r1)
        setRotationAngle(window_light_r1, 0f, 0f, -0.5236f)
        window_light_r1.texOffs(60, 33).addBox(1f, -0.001f, -24f, 2, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(88, 48).addBox(-18f, -32f, -16f, 5, 0, 32, 0f, false)
        roof_door.texOffs(122, 0).addBox(-10f, -34f, -16f, 7, 0, 32, 0f, false)
        roof_door.texOffs(136, 0).addBox(-2f, -33f, -16f, 2, 0, 32, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-2f, -33f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, 0.5236f)
        inner_roof_4_r2.texOffs(0, 146).addBox(-2f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13f, -32f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.5236f)
        inner_roof_2_r2.texOffs(0, 83).addBox(0f, 0f, -16f, 4, 0, 32, 0f, true)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(56, 83).addBox(-6f, -42f, -20f, 6, 0, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(40, 83).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(0, 33).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(68, 83).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 0.1107f)
        outer_roof_2_r1.texOffs(0, 84).addBox(0f, -1f, -20f, 0, 2, 40, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(72, 239).addBox(-1f, -22f, -20f, 1, 4, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(0, 198).addBox(-20f, 0f, -20f, 20, 1, 40, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(125, 198).addBox(-20.8f, -14f, -15f, 1, 14, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 33).addBox(0f, -19f, -15f, 1, 19, 15, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(0, 256).addBox(-20.8f, -14f, 0f, 1, 14, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(0, 83).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        door_handrail_1 = ModelMapper(modelDataWrapper)
        door_handrail_1.setPos(0f, 24f, 0f)
        door_handrail_1.texOffs(352, 0).addBox(0f, -33.75f, 0f, 0, 2, 0, 0.2f, false)
        door_handrail_1.texOffs(359, 4).addBox(0f, -31.5f, -14f, 0, 32, 0, 0.2f, false)
        door_handrail_1.texOffs(359, 4).addBox(0f, -31.5f, 14f, 0, 32, 0, 0.2f, false)

        handrail_11_r1 = ModelMapper(modelDataWrapper)
        handrail_11_r1.setPos(0f, 0f, -30f)
        door_handrail_1.addChild(handrail_11_r1)
        setRotationAngle(handrail_11_r1, -1.5708f, 0f, 0f)
        handrail_11_r1.texOffs(352, 0).addBox(0f, -46f, -31.5f, 0, 32, 0, 0.2f, false)

        door_handrail_2 = ModelMapper(modelDataWrapper)
        door_handrail_2.setPos(0f, 24f, 0f)
        door_handrail_2.texOffs(359, 3).addBox(0f, -33f, 0f, 0, 33, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(0, 256).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(197, 164).addBox(-20.799f, -14f, 0f, 0, 14, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-14.8357f, -13.3373f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(80, 183).addBox(-6f, -19f, 0f, 0, 19, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(178, 23).addBox(-20.799f, -14f, -15f, 0, 14, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.799f, -13.9999f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(0, 183).addBox(0f, -19f, -15f, 0, 19, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(0, 239).addBox(-20f, 0f, -12f, 40, 1, 16, 0f, false)
        end.texOffs(220, 17).addBox(18f, -14f, 7f, 2, 14, 3, 0f, true)
        end.texOffs(220, 17).addBox(-20f, -14f, 7f, 2, 14, 3, 0f, false)
        end.texOffs(247, 127).addBox(9.5f, -34f, -12f, 9, 34, 19, 0f, true)
        end.texOffs(244, 0).addBox(-18.5f, -34f, -12f, 9, 34, 19, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(167, 146).addBox(0f, -19f, 7f, 2, 19, 3, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(167, 146).addBox(-2f, -19f, 7f, 2, 19, 3, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(302, 279).addBox(20f, 0f, -12f, 1, 4, 20, 0f, true)
        end_exterior.texOffs(302, 279).addBox(-21f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(0, 156).addBox(18f, -14f, -12f, 2, 14, 22, 0f, true)
        end_exterior.texOffs(131, 146).addBox(-20f, -14f, -12f, 2, 14, 22, 0f, false)
        end_exterior.texOffs(218, 282).addBox(9.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior.texOffs(218, 282).addBox(-18.5f, -34f, -12f, 9, 34, 0, 0f, true)
        end_exterior.texOffs(80, 230).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(65, 146).addBox(0f, -19f, -12f, 2, 19, 22, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(65, 146).addBox(-2f, -19f, -12f, 2, 19, 22, 0f, true)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        handrail_2_r3 = ModelMapper(modelDataWrapper)
        handrail_2_r3.setPos(0f, 0f, 0f)
        roof_end.addChild(handrail_2_r3)
        setRotationAngle(handrail_2_r3, -1.5708f, 0f, 0f)
        handrail_2_r3.texOffs(352, 0).addBox(0f, -40f, -31.5f, 0, 16, 0, 0.2f, false)

        inner_roof_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_7_r1.setPos(0f, -33f, 16f)
        roof_end.addChild(inner_roof_7_r1)
        setRotationAngle(inner_roof_7_r1, -0.5236f, 0f, 0f)
        inner_roof_7_r1.texOffs(24, 120).addBox(-2f, 0f, -2f, 4, 0, 2, 0f, false)

        inner_roof_1 = ModelMapper(modelDataWrapper)
        inner_roof_1.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_1)
        inner_roof_1.texOffs(76, 83).addBox(-17f, 1f, -12f, 6, 0, 36, 0f, false)
        inner_roof_1.texOffs(82, 0).addBox(-8f, -1f, -28f, 10, 0, 52, 0f, false)
        inner_roof_1.texOffs(4, 0).addBox(0f, 0f, 0f, 2, 0, 24, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(0f, 0f, 0f)
        inner_roof_1.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, -0.5236f, 0f, 0.5236f)
        inner_roof_6_r1.texOffs(15, 33).addBox(-2f, 0f, -2f, 2, 0, 2, 0f, false)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(0f, 0f, -16f)
        inner_roof_1.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, 0.5236f)
        inner_roof_4_r3.texOffs(0, 24).addBox(-2f, 0f, 16f, 2, 0, 24, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-11f, 1f, -16f)
        inner_roof_1.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.5236f)
        inner_roof_2_r3.texOffs(270, 142).addBox(0f, 0f, 4f, 4, 0, 36, 0f, true)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(76, 83).addBox(15f, 1f, -12f, 6, 0, 36, 0f, true)
        inner_roof_2.texOffs(82, 0).addBox(2f, -1f, -28f, 10, 0, 52, 0f, true)
        inner_roof_2.texOffs(4, 0).addBox(2f, 0f, 0f, 2, 0, 24, 0f, true)

        inner_roof_6_r2 = ModelMapper(modelDataWrapper)
        inner_roof_6_r2.setPos(4f, 0f, 0f)
        inner_roof_2.addChild(inner_roof_6_r2)
        setRotationAngle(inner_roof_6_r2, -0.5236f, 0f, -0.5236f)
        inner_roof_6_r2.texOffs(15, 33).addBox(0f, 0f, -2f, 2, 0, 2, 0f, true)

        inner_roof_4_r4 = ModelMapper(modelDataWrapper)
        inner_roof_4_r4.setPos(4f, 0f, -16f)
        inner_roof_2.addChild(inner_roof_4_r4)
        setRotationAngle(inner_roof_4_r4, 0f, 0f, -0.5236f)
        inner_roof_4_r4.texOffs(0, 24).addBox(0f, 0f, 16f, 2, 0, 24, 0f, true)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(15f, 1f, -16f)
        inner_roof_2.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, 0.5236f)
        inner_roof_2_r4.texOffs(88, 0).addBox(-4f, 0f, 4f, 4, 0, 36, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(0, 33).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -43f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(131, 148).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -43f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(131, 148).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(276, 104).addBox(-6f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(34, 256).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(46, 277).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(116, 283).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_1.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, 0.1107f)
        outer_roof_2_r2.texOffs(283, 160).addBox(0f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-20f, -14f, 0f)
        outer_roof_1.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, 0.1107f)
        outer_roof_1_r2.texOffs(176, 281).addBox(-1f, -22f, -12f, 1, 4, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(276, 104).addBox(0f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.1745f)
        outer_roof_5_r3.texOffs(34, 256).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.5236f)
        outer_roof_4_r3.texOffs(46, 277).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.0472f)
        outer_roof_3_r3.texOffs(116, 283).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, true)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(17.587f, -36.3849f, 0f)
        outer_roof_2.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -0.1107f)
        outer_roof_2_r3.texOffs(283, 160).addBox(-1f, -1f, -12f, 1, 2, 20, 0f, true)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(20f, -14f, 0f)
        outer_roof_2.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, -0.1107f)
        outer_roof_1_r3.texOffs(176, 281).addBox(0f, -22f, -12f, 1, 4, 20, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(-2f, -33f, 0f)
        roof_light.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, 0.5236f)
        roof_light_r1.texOffs(64, 33).addBox(-2f, -0.1f, -24f, 2, 0, 48, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        light_5_r1 = ModelMapper(modelDataWrapper)
        light_5_r1.setPos(2f, -33f, 0f)
        roof_end_light.addChild(light_5_r1)
        setRotationAngle(light_5_r1, 0f, 0f, -0.5236f)
        light_5_r1.texOffs(0, 0).addBox(0f, -0.1f, 16f, 2, 0, 24, 0f, false)

        light_4_r1 = ModelMapper(modelDataWrapper)
        light_4_r1.setPos(2f, -33f, 16f)
        roof_end_light.addChild(light_4_r1)
        setRotationAngle(light_4_r1, -0.5236f, 0f, -0.5236f)
        light_4_r1.texOffs(0, 2).addBox(0f, -0.1f, -2f, 2, 0, 2, 0f, true)

        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(0f, -33f, 16f)
        roof_end_light.addChild(light_3_r1)
        setRotationAngle(light_3_r1, -0.5236f, 0f, 0f)
        light_3_r1.texOffs(110, 119).addBox(-2f, -0.1f, -2f, 4, 0, 2, 0f, false)

        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(-2f, -33f, 16f)
        roof_end_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, -0.5236f, 0f, 0.5236f)
        light_2_r1.texOffs(0, 2).addBox(-2f, -0.1f, -2f, 2, 0, 2, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-2f, -33f, 0f)
        roof_end_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, 0.5236f)
        light_1_r1.texOffs(0, 0).addBox(-2f, -0.1f, 16f, 2, 0, 24, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(91, 146).addBox(18f, -14f, 4f, 2, 14, 6, 0f, false)
        head.texOffs(91, 146).addBox(-20f, -14f, 4f, 2, 14, 6, 0f, true)
        head.texOffs(246, 206).addBox(-18f, -34f, 4f, 36, 34, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(24, 211).addBox(0f, -19f, 4f, 2, 19, 6, 0f, true)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1107f)
        upper_wall_1_r3.texOffs(24, 211).addBox(-2f, -19f, 4f, 2, 19, 6, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(134, 103).addBox(-21f, 0f, -18f, 42, 7, 22, 0f, false)
        head_exterior.texOffs(125, 196).addBox(20f, 0f, 4f, 1, 7, 4, 0f, true)
        head_exterior.texOffs(125, 196).addBox(-21f, 0f, 4f, 1, 7, 4, 0f, false)
        head_exterior.texOffs(154, 257).addBox(18f, -14f, -9f, 2, 14, 19, 0f, false)
        head_exterior.texOffs(197, 146).addBox(-20f, -14f, -9f, 2, 14, 19, 0f, true)
        head_exterior.texOffs(90, 256).addBox(18f, -14f, -18f, 1, 14, 9, 0f, false)
        head_exterior.texOffs(90, 256).addBox(-19f, -14f, -18f, 1, 14, 9, 0f, true)
        head_exterior.texOffs(244, 53).addBox(-18f, -34f, 3f, 36, 34, 0, 0f, false)

        driver_door_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_2_r1.setPos(-20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_2_r1)
        setRotationAngle(driver_door_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_upper_2_r1.texOffs(288, 240).addBox(1f, -19f, -18f, 1, 19, 9, 0f, true)
        driver_door_upper_2_r1.texOffs(178, 0).addBox(0f, -19f, -9f, 2, 19, 19, 0f, true)

        driver_door_upper_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_1_r1.setPos(20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_1_r1)
        setRotationAngle(driver_door_upper_1_r1, 0f, 0f, -0.1107f)
        driver_door_upper_1_r1.texOffs(288, 240).addBox(-2f, -19f, -18f, 1, 19, 9, 0f, false)
        driver_door_upper_1_r1.texOffs(178, 0).addBox(-2f, -19f, -9f, 2, 19, 19, 0f, false)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(134, 88).addBox(-19f, 0.2966f, -26.5318f, 38, 5, 0, 0f, false)

        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(0f, 7f, 4f)
        front.addChild(bottom_r1)
        setRotationAngle(bottom_r1, -0.0698f, 0f, 0f)
        bottom_r1.texOffs(0, 0).addBox(-21f, 0f, -33f, 42, 0, 33, 0f, false)

        front_middle_top_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_r1.setPos(0f, -42f, -12f)
        front.addChild(front_middle_top_r1)
        setRotationAngle(front_middle_top_r1, 0.3491f, 0f, 0f)
        front_middle_top_r1.texOffs(161, 0).addBox(-6f, 0f, -11f, 12, 0, 11, 0f, false)

        front_panel_2_r1 = ModelMapper(modelDataWrapper)
        front_panel_2_r1.setPos(0f, 41.314f, -36.3702f)
        front.addChild(front_panel_2_r1)
        setRotationAngle(front_panel_2_r1, -0.1745f, 0f, 0f)
        front_panel_2_r1.texOffs(162, 206).addBox(-19f, -82f, 1f, 38, 28, 0, 0f, false)

        front_panel_1_r1 = ModelMapper(modelDataWrapper)
        front_panel_1_r1.setPos(0f, 0.2966f, -26.5318f)
        front.addChild(front_panel_1_r1)
        setRotationAngle(front_panel_1_r1, -0.0436f, 0f, 0f)
        front_panel_1_r1.texOffs(134, 76).addBox(-19f, -12f, 0f, 38, 12, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)
        side_1.texOffs(22, 30).addBox(19f, -14f, -18f, 1, 14, 0, 0f, false)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(21f, 0f, -13f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, 0.1745f, 0.1745f)
        front_side_bottom_1_r1.texOffs(0, 50).addBox(0f, 0f, -16f, 0, 7, 23, 0f, false)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(6f, -42f, -12f)
        side_1.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0.3491f, 0f, 0.1745f)
        outer_roof_4_r4.texOffs(123, 103).addBox(0f, 0f, -11f, 11, 0, 11, 0f, true)

        outer_roof_1_r4 = ModelMapper(modelDataWrapper)
        outer_roof_1_r4.setPos(20f, -14f, 0f)
        side_1.addChild(outer_roof_1_r4)
        setRotationAngle(outer_roof_1_r4, 0f, 0f, -0.1107f)
        outer_roof_1_r4.texOffs(118, 118).addBox(0f, -22f, -18f, 1, 4, 6, 0f, true)
        outer_roof_1_r4.texOffs(22, 11).addBox(-1f, -19f, -18f, 1, 19, 0, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(17.587f, -36.3849f, 0f)
        side_1.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.1107f)
        outer_roof_2_r4.texOffs(0, 88).addBox(0f, -1f, -18f, 0, 2, 6, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(15.813f, -37.5414f, -17.4163f)
        side_1.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0.1745f, 0f, 0.7418f)
        outer_roof_3_r4.texOffs(6, 83).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, true)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(20f, 0f, -18f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, 0.1745f, 0f)
        front_side_lower_1_r1.texOffs(0, 0).addBox(0f, -14f, -11f, 0, 20, 11, 0f, true)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(20f, -14f, -18f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0.1745f, -0.1107f)
        front_side_upper_1_r1.texOffs(0, 135).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(-21f, 0f, -9f)
        front.addChild(side_2)
        side_2.texOffs(22, 30).addBox(1f, -14f, -9f, 1, 14, 0, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(0f, 0f, -4f)
        side_2.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, -0.1745f, -0.1745f)
        front_side_bottom_2_r1.texOffs(0, 50).addBox(0f, 0f, -16f, 0, 7, 23, 0f, false)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(5.187f, -37.5414f, -8.4163f)
        side_2.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0.1745f, 0f, -0.7418f)
        outer_roof_8_r1.texOffs(6, 83).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, false)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(3.413f, -36.3849f, 9f)
        side_2.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, 0.1107f)
        outer_roof_7_r1.texOffs(0, 88).addBox(0f, -1f, -18f, 0, 2, 6, 0f, false)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(15f, -42f, -3f)
        side_2.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0.3491f, 0f, -0.1745f)
        outer_roof_6_r1.texOffs(123, 103).addBox(-11f, 0f, -11f, 11, 0, 11, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(1f, -14f, 9f)
        side_2.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, 0.1107f)
        outer_roof_5_r4.texOffs(118, 118).addBox(-1f, -22f, -18f, 1, 4, 6, 0f, false)
        outer_roof_5_r4.texOffs(22, 11).addBox(0f, -19f, -18f, 1, 19, 0, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(1f, -14f, -9f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, -0.1745f, 0.1107f)
        front_side_upper_2_r1.texOffs(0, 135).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(1f, 0f, -9f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, -0.1745f, 0f)
        front_side_lower_2_r1.texOffs(0, 0).addBox(0f, -14f, -11f, 0, 20, 11, 0f, false)

        nose = ModelMapper(modelDataWrapper)
        nose.setPos(0f, -1.25f, 0f)
        front.addChild(nose)


        center_nose = ModelMapper(modelDataWrapper)
        center_nose.setPos(0f, 1.25f, 0f)
        nose.addChild(center_nose)
        center_nose.texOffs(156, 93).addBox(-5.5f, -0.25f, -31.25f, 11, 3, 0, 0f, false)
        center_nose.texOffs(131, 93).addBox(-5.5f, -0.7666f, -29.3181f, 11, 0, 3, 0f, false)
        center_nose.texOffs(0, 117).addBox(-5.5f, 3.0089f, -30.2838f, 11, 0, 4, 0f, false)

        nose_5_r1 = ModelMapper(modelDataWrapper)
        nose_5_r1.setPos(0f, 3.4918f, -30.4135f)
        center_nose.addChild(nose_5_r1)
        setRotationAngle(nose_5_r1, -1.8326f, 0f, 0f)
        nose_5_r1.texOffs(0, 10).addBox(-5.5f, 0f, -0.5f, 11, 0, 1, 0f, false)

        nose_4_r1 = ModelMapper(modelDataWrapper)
        nose_4_r1.setPos(0.5f, -5.6845f, -27.9547f)
        center_nose.addChild(nose_4_r1)
        setRotationAngle(nose_4_r1, -0.2618f, 0f, 0f)
        nose_4_r1.texOffs(0, 10).addBox(-6f, 9f, -1f, 11, 0, 1, 0f, false)

        nose_2_r1 = ModelMapper(modelDataWrapper)
        nose_2_r1.setPos(0.5f, -9.3031f, -1.3234f)
        center_nose.addChild(nose_2_r1)
        setRotationAngle(nose_2_r1, 0.2618f, 0f, 0f)
        nose_2_r1.texOffs(0, 8).addBox(-6f, 1f, -31.25f, 11, 0, 2, 0f, false)

        nose_side_1 = ModelMapper(modelDataWrapper)
        nose_side_1.setPos(0f, 0.25f, 0f)
        nose.addChild(nose_side_1)


        nose_18_r1 = ModelMapper(modelDataWrapper)
        nose_18_r1.setPos(-13.2048f, 4.0101f, -25.7633f)
        nose_side_1.addChild(nose_18_r1)
        setRotationAngle(nose_18_r1, 0f, 0.7854f, 0f)
        nose_18_r1.texOffs(30, 80).addBox(-1f, 0f, -0.5f, 3, 0, 1, 0f, false)

        nose_17_r1 = ModelMapper(modelDataWrapper)
        nose_17_r1.setPos(-10.3623f, 4.0101f, -27.9444f)
        nose_side_1.addChild(nose_17_r1)
        setRotationAngle(nose_17_r1, 0f, 0.5236f, 0f)
        nose_17_r1.texOffs(14, 94).addBox(-2f, 0f, -0.5f, 4, 0, 3, 0f, false)

        nose_16_r1 = ModelMapper(modelDataWrapper)
        nose_16_r1.setPos(-10.9576f, -43.4921f, -9.1171f)
        nose_side_1.addChild(nose_16_r1)
        setRotationAngle(nose_16_r1, 0f, 0.2618f, 0f)
        nose_16_r1.texOffs(28, 65).addBox(7f, 47.5f, -19f, 4, 0, 4, 0f, false)

        nose_15_r1 = ModelMapper(modelDataWrapper)
        nose_15_r1.setPos(-0.3159f, -36.8232f, 30.5981f)
        nose_side_1.addChild(nose_15_r1)
        setRotationAngle(nose_15_r1, -1.8326f, 0.2618f, 0f)
        nose_15_r1.texOffs(8, 80).addBox(7f, 47.5f, 55f, 4, 0, 1, 0f, false)

        nose_14_r1 = ModelMapper(modelDataWrapper)
        nose_14_r1.setPos(-10.5481f, 3.5259f, -28.2661f)
        nose_side_1.addChild(nose_14_r1)
        setRotationAngle(nose_14_r1, -1.8326f, 0.5236f, 0f)
        nose_14_r1.texOffs(8, 80).addBox(-2f, 0f, 0.5f, 4, 0, 1, 0f, false)

        nose_13_r1 = ModelMapper(modelDataWrapper)
        nose_13_r1.setPos(-13.297f, 4.4918f, -26.5626f)
        nose_side_1.addChild(nose_13_r1)
        setRotationAngle(nose_13_r1, -1.8326f, 0.7854f, 0f)
        nose_13_r1.texOffs(36, 80).addBox(-1.5f, 0f, -0.5f, 3, 0, 1, 0f, false)

        nose_12_r1 = ModelMapper(modelDataWrapper)
        nose_12_r1.setPos(-40.9283f, -41.8727f, 18.6381f)
        nose_side_1.addChild(nose_12_r1)
        setRotationAngle(nose_12_r1, -0.2618f, 0.7854f, 0f)
        nose_12_r1.texOffs(36, 80).addBox(50f, 47.5f, -1f, 3, 0, 1, 0f, false)

        nose_11_r1 = ModelMapper(modelDataWrapper)
        nose_11_r1.setPos(-27.8485f, -41.8727f, -4.2314f)
        nose_side_1.addChild(nose_11_r1)
        setRotationAngle(nose_11_r1, -0.2618f, 0.5236f, 0f)
        nose_11_r1.texOffs(8, 80).addBox(25f, 47.5f, -1f, 4, 0, 1, 0f, false)

        nose_10_r1 = ModelMapper(modelDataWrapper)
        nose_10_r1.setPos(-12.6933f, -41.8727f, -15.595f)
        nose_side_1.addChild(nose_10_r1)
        setRotationAngle(nose_10_r1, -0.2618f, 0.2618f, 0f)
        nose_10_r1.texOffs(8, 80).addBox(7f, 47.5f, -1f, 4, 0, 1, 0f, false)

        nose_9_r1 = ModelMapper(modelDataWrapper)
        nose_9_r1.setPos(-20.8882f, 1.7334f, -20.1757f)
        nose_side_1.addChild(nose_9_r1)
        setRotationAngle(nose_9_r1, 0f, 0.5236f, 0f)
        nose_9_r1.texOffs(27, 69).addBox(10f, -1.5f, -1f, 5, 0, 3, 0f, false)

        nose_8_r1 = ModelMapper(modelDataWrapper)
        nose_8_r1.setPos(-19.2301f, 1.7334f, -24.5358f)
        nose_side_1.addChild(nose_8_r1)
        setRotationAngle(nose_8_r1, 0f, 0.2618f, 0f)
        nose_8_r1.texOffs(29, 115).addBox(11f, -1.5f, -1f, 4, 0, 3, 0f, false)

        nose_7_r1 = ModelMapper(modelDataWrapper)
        nose_7_r1.setPos(-19.6383f, 1.941f, -26.0595f)
        nose_side_1.addChild(nose_7_r1)
        setRotationAngle(nose_7_r1, 0.2618f, 0.2618f, 0f)
        nose_7_r1.texOffs(24, 118).addBox(11f, -1.5f, -1f, 4, 0, 2, 0f, false)

        nose_6_r1 = ModelMapper(modelDataWrapper)
        nose_6_r1.setPos(-18.2128f, 1.941f, -23.5418f)
        nose_side_1.addChild(nose_6_r1)
        setRotationAngle(nose_6_r1, 0.2618f, 0.5236f, 0f)
        nose_6_r1.texOffs(24, 118).addBox(7f, -1.5f, -1f, 4, 0, 2, 0f, false)

        nose_5_r2 = ModelMapper(modelDataWrapper)
        nose_5_r2.setPos(-10.4559f, 1.941f, -28.6712f)
        nose_side_1.addChild(nose_5_r2)
        setRotationAngle(nose_5_r2, 0.2618f, 0.7854f, 0f)
        nose_5_r2.texOffs(0, 0).addBox(-5f, -1.5f, -1f, 3, 0, 2, 0f, false)

        nose_4_r2 = ModelMapper(modelDataWrapper)
        nose_4_r2.setPos(-10.7065f, 2.25f, -28.9218f)
        nose_side_1.addChild(nose_4_r2)
        setRotationAngle(nose_4_r2, 0f, 0.7854f, 0f)
        nose_4_r2.texOffs(25, 94).addBox(-5f, -1.5f, -1f, 3, 3, 0, 0f, false)

        nose_3_r1 = ModelMapper(modelDataWrapper)
        nose_3_r1.setPos(-18.39f, 2.25f, -23.8487f)
        nose_side_1.addChild(nose_3_r1)
        setRotationAngle(nose_3_r1, 0f, 0.5236f, 0f)
        nose_3_r1.texOffs(124, 43).addBox(7f, -1.5f, -1f, 4, 3, 0, 0f, false)

        nose_2_r2 = ModelMapper(modelDataWrapper)
        nose_2_r2.setPos(0.5544f, 2.25f, -31.837f)
        nose_side_1.addChild(nose_2_r2)
        setRotationAngle(nose_2_r2, 0f, 0.2618f, 0f)
        nose_2_r2.texOffs(124, 40).addBox(-10f, -1.5f, -1f, 4, 3, 0, 0f, false)

        nose_side_2 = ModelMapper(modelDataWrapper)
        nose_side_2.setPos(0f, 0.25f, 0f)
        nose.addChild(nose_side_2)


        nose_19_r1 = ModelMapper(modelDataWrapper)
        nose_19_r1.setPos(12.4983f, 4.0098f, -26.4711f)
        nose_side_2.addChild(nose_19_r1)
        setRotationAngle(nose_19_r1, 0f, -0.7854f, 0f)
        nose_19_r1.texOffs(0, 0).addBox(-1f, 0f, -0.5f, 3, 0, 1, 0f, false)

        nose_18_r2 = ModelMapper(modelDataWrapper)
        nose_18_r2.setPos(9.6127f, 4.0089f, -26.6459f)
        nose_side_2.addChild(nose_18_r2)
        setRotationAngle(nose_18_r2, 0f, -0.5236f, 0f)
        nose_18_r2.texOffs(14, 94).addBox(-2f, 0f, -2f, 4, 0, 3, 0f, false)

        nose_17_r2 = ModelMapper(modelDataWrapper)
        nose_17_r2.setPos(6.6643f, 4.0098f, -27.8677f)
        nose_side_2.addChild(nose_17_r2)
        setRotationAngle(nose_17_r2, 0f, -0.2618f, 0f)
        nose_17_r2.texOffs(28, 65).addBox(-2f, 0f, -2f, 4, 0, 4, 0f, false)

        nose_16_r2 = ModelMapper(modelDataWrapper)
        nose_16_r2.setPos(13.297f, 4.4918f, -26.5626f)
        nose_side_2.addChild(nose_16_r2)
        setRotationAngle(nose_16_r2, -1.8326f, -0.7854f, 0f)
        nose_16_r2.texOffs(36, 80).addBox(-1.5f, 0f, -0.5f, 3, 0, 1, 0f, false)

        nose_15_r2 = ModelMapper(modelDataWrapper)
        nose_15_r2.setPos(10.6775f, 4.4918f, -28.4903f)
        nose_side_2.addChild(nose_15_r2)
        setRotationAngle(nose_15_r2, -1.8326f, -0.5236f, 0f)
        nose_15_r2.texOffs(8, 80).addBox(-2f, 0f, -0.5f, 4, 0, 1, 0f, false)

        nose_14_r2 = ModelMapper(modelDataWrapper)
        nose_14_r2.setPos(7.2154f, 4.4927f, -29.9246f)
        nose_side_2.addChild(nose_14_r2)
        setRotationAngle(nose_14_r2, -1.8326f, -0.2618f, 0f)
        nose_14_r2.texOffs(8, 80).addBox(-2f, 0f, -0.5f, 4, 0, 1, 0f, false)

        nose_13_r2 = ModelMapper(modelDataWrapper)
        nose_13_r2.setPos(10.5228f, -41.8727f, -11.7675f)
        nose_side_2.addChild(nose_13_r2)
        setRotationAngle(nose_13_r2, -0.2618f, -0.7854f, 0f)
        nose_13_r2.texOffs(36, 80).addBox(-10f, 47.5f, -1f, 3, 0, 1, 0f, false)

        nose_12_r2 = ModelMapper(modelDataWrapper)
        nose_12_r2.setPos(12.2601f, -41.8727f, -13.2314f)
        nose_side_2.addChild(nose_12_r2)
        setRotationAngle(nose_12_r2, -0.2618f, -0.5236f, 0f)
        nose_12_r2.texOffs(8, 80).addBox(-11f, 47.5f, -1f, 4, 0, 1, 0f, false)

        nose_11_r2 = ModelMapper(modelDataWrapper)
        nose_11_r2.setPos(12.6934f, -41.8717f, -15.5952f)
        nose_side_2.addChild(nose_11_r2)
        setRotationAngle(nose_11_r2, -0.2618f, -0.2618f, 0f)
        nose_11_r2.texOffs(8, 80).addBox(-11f, 47.5f, -1f, 4, 0, 1, 0f, false)

        nose_10_r2 = ModelMapper(modelDataWrapper)
        nose_10_r2.setPos(16.558f, 0.2334f, -22.6757f)
        nose_side_2.addChild(nose_10_r2)
        setRotationAngle(nose_10_r2, 0f, -0.5236f, 0f)
        nose_10_r2.texOffs(27, 69).addBox(-10f, 0f, -1f, 5, 0, 3, 0f, false)

        nose_9_r2 = ModelMapper(modelDataWrapper)
        nose_9_r2.setPos(6.673f, 0.2334f, -27.9004f)
        nose_side_2.addChild(nose_9_r2)
        setRotationAngle(nose_9_r2, 0f, -0.2618f, 0f)
        nose_9_r2.texOffs(29, 115).addBox(-2f, 0f, -1f, 4, 0, 3, 0f, false)

        nose_8_r2 = ModelMapper(modelDataWrapper)
        nose_8_r2.setPos(19.057f, -45.3893f, -39.3447f)
        nose_side_2.addChild(nose_8_r2)
        setRotationAngle(nose_8_r2, 0.2618f, -0.2618f, 0f)
        nose_8_r2.texOffs(24, 118).addBox(-11f, 47.5f, -1f, 4, 0, 2, 0f, false)

        nose_7_r2 = ModelMapper(modelDataWrapper)
        nose_7_r2.setPos(21.9558f, -45.3893f, -36.0248f)
        nose_side_2.addChild(nose_7_r2)
        setRotationAngle(nose_7_r2, 0.2618f, -0.5236f, 0f)
        nose_7_r2.texOffs(24, 118).addBox(-8f, 47.5f, -1f, 4, 0, 2, 0f, false)

        nose_6_r2 = ModelMapper(modelDataWrapper)
        nose_6_r2.setPos(23.6661f, -45.3893f, -33.3962f)
        nose_side_2.addChild(nose_6_r2)
        setRotationAngle(nose_6_r2, 0.2618f, -0.7854f, 0f)
        nose_6_r2.texOffs(0, 0).addBox(-4f, 47.5f, -1f, 3, 0, 2, 0f, false)

        nose_5_r3 = ModelMapper(modelDataWrapper)
        nose_5_r3.setPos(14.9491f, -46.75f, -24.6792f)
        nose_side_2.addChild(nose_5_r3)
        setRotationAngle(nose_5_r3, 0f, -0.7854f, 0f)
        nose_5_r3.texOffs(25, 94).addBox(-4f, 47.5f, -1f, 3, 3, 0, 0f, true)

        nose_4_r3 = ModelMapper(modelDataWrapper)
        nose_4_r3.setPos(-9.1985f, 2.25f, -12.0641f)
        nose_side_2.addChild(nose_4_r3)
        setRotationAngle(nose_4_r3, 0f, -0.5236f, 0f)
        nose_4_r3.texOffs(124, 43).addBox(7f, -1.5f, -25f, 4, 3, 0, 0f, true)

        nose_3_r2 = ModelMapper(modelDataWrapper)
        nose_3_r2.setPos(5.2412f, 2.25f, -30.2841f)
        nose_side_2.addChild(nose_3_r2)
        setRotationAngle(nose_3_r2, 0f, -0.2618f, 0f)
        nose_3_r2.texOffs(124, 40).addBox(0f, -1.5f, -1f, 4, 3, 0, 0f, false)

        roof_head_exterior = ModelMapper(modelDataWrapper)
        roof_head_exterior.setPos(0f, 24f, 0f)
        roof_head_exterior.texOffs(0, 33).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_3_r1 = ModelMapper(modelDataWrapper)
        vent_3_r1.setPos(-8f, -43f, 0f)
        roof_head_exterior.addChild(vent_3_r1)
        setRotationAngle(vent_3_r1, 0f, 0f, -0.3491f)
        vent_3_r1.texOffs(131, 148).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_2_r2 = ModelMapper(modelDataWrapper)
        vent_2_r2.setPos(8f, -43f, 0f)
        roof_head_exterior.addChild(vent_2_r2)
        setRotationAngle(vent_2_r2, 0f, 0f, 0.3491f)
        vent_2_r2.texOffs(131, 148).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_3 = ModelMapper(modelDataWrapper)
        outer_roof_3.setPos(0f, 0f, 0f)
        roof_head_exterior.addChild(outer_roof_3)
        outer_roof_3.texOffs(196, 260).addBox(-6f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_3.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, -0.1745f)
        outer_roof_6_r2.texOffs(240, 103).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_3.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, -0.5236f)
        outer_roof_5_r5.texOffs(228, 261).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, false)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_3.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, -1.0472f)
        outer_roof_4_r5.texOffs(72, 283).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, false)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_3.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, 0.1107f)
        outer_roof_3_r5.texOffs(190, 76).addBox(0f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(-20f, -14f, 0f)
        outer_roof_3.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, 0.1107f)
        outer_roof_2_r5.texOffs(254, 279).addBox(-1f, -22f, -12f, 1, 4, 20, 0f, false)

        outer_roof_4 = ModelMapper(modelDataWrapper)
        outer_roof_4.setPos(0f, 0f, 0f)
        roof_head_exterior.addChild(outer_roof_4)
        outer_roof_4.texOffs(196, 260).addBox(0f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_6_r3 = ModelMapper(modelDataWrapper)
        outer_roof_6_r3.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_4.addChild(outer_roof_6_r3)
        setRotationAngle(outer_roof_6_r3, 0f, 0f, 0.1745f)
        outer_roof_6_r3.texOffs(240, 103).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_5_r6 = ModelMapper(modelDataWrapper)
        outer_roof_5_r6.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_4.addChild(outer_roof_5_r6)
        setRotationAngle(outer_roof_5_r6, 0f, 0f, 0.5236f)
        outer_roof_5_r6.texOffs(228, 261).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, true)

        outer_roof_4_r6 = ModelMapper(modelDataWrapper)
        outer_roof_4_r6.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_4.addChild(outer_roof_4_r6)
        setRotationAngle(outer_roof_4_r6, 0f, 0f, 1.0472f)
        outer_roof_4_r6.texOffs(72, 283).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, true)

        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(17.587f, -36.3849f, 0f)
        outer_roof_4.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0f, 0f, -0.1107f)
        outer_roof_3_r6.texOffs(190, 76).addBox(-1f, -1f, -12f, 1, 2, 20, 0f, true)

        outer_roof_2_r6 = ModelMapper(modelDataWrapper)
        outer_roof_2_r6.setPos(20f, -14f, 0f)
        outer_roof_4.addChild(outer_roof_2_r6)
        setRotationAngle(outer_roof_2_r6, 0f, 0f, -0.1107f)
        outer_roof_2_r6.texOffs(254, 279).addBox(0f, -22f, -12f, 1, 4, 20, 0f, true)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(-1f, -3.4772f, 1.663f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, -0.0436f, 0f, 0f)
        headlight_2_r1.texOffs(124, 36).addBox(13.75f, -3f, -28.013f, 4, 4, 0, 0f, true)
        headlight_2_r1.texOffs(124, 36).addBox(-15.75f, -3f, -28.013f, 4, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        headlight_4_r1 = ModelMapper(modelDataWrapper)
        headlight_4_r1.setPos(-4.8579f, 1.25f, 0.7838f)
        tail_lights.addChild(headlight_4_r1)
        setRotationAngle(headlight_4_r1, 0f, -0.7854f, 0f)
        headlight_4_r1.texOffs(5, 44).addBox(-8f, -2f, -33.013f, 2, 3, 0, 0f, true)

        headlight_3_r1 = ModelMapper(modelDataWrapper)
        headlight_3_r1.setPos(-11.4729f, 1.25f, -4.1246f)
        tail_lights.addChild(headlight_3_r1)
        setRotationAngle(headlight_3_r1, 0f, -0.5236f, 0f)
        headlight_3_r1.texOffs(9, 44).addBox(5f, -2f, -33.0154f, 4, 3, 0, 0f, true)

        headlight_2_r2 = ModelMapper(modelDataWrapper)
        headlight_2_r2.setPos(7.2409f, 1.75f, -7.4547f)
        tail_lights.addChild(headlight_2_r2)
        setRotationAngle(headlight_2_r2, 0f, 0.5236f, 0f)
        headlight_2_r2.texOffs(9, 44).addBox(-7f, -2.5f, -28.0153f, 4, 3, 0, 0f, false)

        headlight_1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1.setPos(11.929f, 1.75f, -13.3582f)
        tail_lights.addChild(headlight_1_r1)
        setRotationAngle(headlight_1_r1, 0f, 0.7854f, 0f)
        headlight_1_r1.texOffs(5, 44).addBox(-9f, -2.5f, -28.013f, 2, 3, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_1_r5 = ModelMapper(modelDataWrapper)
        outer_roof_1_r5.setPos(-20f, -14f, 0f)
        door_light.addChild(outer_roof_1_r5)
        setRotationAngle(outer_roof_1_r5, 0f, 0f, 0.1107f)
        outer_roof_1_r5.texOffs(0, 0).addBox(-1.1f, -22f, -2f, 0, 4, 4, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(204, 0).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(204, 3).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

        side_panel_1 = ModelMapper(modelDataWrapper)
        side_panel_1.setPos(0f, 24f, 0f)
        side_panel_1.texOffs(218, 38).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        handrail_r1 = ModelMapper(modelDataWrapper)
        handrail_r1.setPos(-11f, -5f, 22f)
        side_panel_1.addChild(handrail_r1)
        setRotationAngle(handrail_r1, 0f, 0f, -0.0436f)
        handrail_r1.texOffs(355, 0).addBox(0f, -28.2f, -22f, 0, 28, 0, 0.2f, false)

        side_panel_2 = ModelMapper(modelDataWrapper)
        side_panel_2.setPos(0f, 24f, 0f)
        side_panel_2.texOffs(0, 83).addBox(-18f, -15f, 0f, 7, 11, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(36, 146).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 24f, 0f)
        seat_1.texOffs(0, 146).addBox(-18f, -6f, -40f, 7, 1, 51, 0f, false)
        seat_1.texOffs(80, 198).addBox(-18f, -6f, 29f, 7, 1, 31, 0f, false)
        seat_1.texOffs(134, 0).addBox(-18f, -5f, -39f, 6, 5, 98, 0f, false)

        seat_back_2_r1 = ModelMapper(modelDataWrapper)
        seat_back_2_r1.setPos(-17f, -6f, 0f)
        seat_1.addChild(seat_back_2_r1)
        setRotationAngle(seat_back_2_r1, 0f, 0f, -0.0524f)
        seat_back_2_r1.texOffs(255, 240).addBox(-1f, -8f, 29f, 1, 8, 31, 0f, false)
        seat_back_2_r1.texOffs(194, 147).addBox(-1f, -8f, -40f, 1, 8, 51, 0f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 24f, 0f)
        seat_2.texOffs(80, 198).addBox(-18f, -6f, -60f, 7, 1, 31, 0f, false)
        seat_2.texOffs(0, 146).addBox(-18f, -6f, -11f, 7, 1, 51, 0f, false)
        seat_2.texOffs(134, 0).addBox(-18f, -5f, -59f, 6, 5, 98, 0f, false)

        seat_back_3_r1 = ModelMapper(modelDataWrapper)
        seat_back_3_r1.setPos(-17f, -6f, 0f)
        seat_2.addChild(seat_back_3_r1)
        setRotationAngle(seat_back_3_r1, 0f, 0f, -0.0524f)
        seat_back_3_r1.texOffs(194, 147).addBox(-1f, -8f, -11f, 1, 8, 51, 0f, false)
        seat_back_3_r1.texOffs(255, 240).addBox(-1f, -8f, -60f, 1, 8, 31, 0f, false)

        seat_curve = ModelMapper(modelDataWrapper)
        seat_curve.setPos(0f, 24f, 0f)
        seat_curve.texOffs(94, 124).addBox(-12.9289f, -5f, -3.6538f, 8, 0, 8, 0f, false)

        seat_panel_2_r1 = ModelMapper(modelDataWrapper)
        seat_panel_2_r1.setPos(-4.9289f, -9.4367f, -0.9289f)
        seat_curve.addChild(seat_panel_2_r1)
        setRotationAngle(seat_panel_2_r1, 0f, -1.5708f, 0f)
        seat_panel_2_r1.texOffs(134, 114).addBox(1.0003f, -4.5633f, -0.0001f, 9, 9, 0, 0f, false)
        seat_panel_2_r1.texOffs(134, 114).addBox(-7.9997f, -4.5633f, -0.0001f, 9, 9, 0, 0f, true)

        statue_box = ModelMapper(modelDataWrapper)
        statue_box.setPos(0f, 0f, 0f)
        seat_curve.addChild(statue_box)
        statue_box.texOffs(145, 142).addBox(-13f, -32f, -2f, 0, 18, 4, 0f, false)

        statue_box_3_r1 = ModelMapper(modelDataWrapper)
        statue_box_3_r1.setPos(-16.3807f, -23f, 2.9059f)
        statue_box.addChild(statue_box_3_r1)
        setRotationAngle(statue_box_3_r1, 0f, 0.2618f, 0f)
        statue_box_3_r1.texOffs(212, 76).addBox(-2.5f, -9f, 0f, 6, 18, 0, 0f, false)

        statue_box_1_r1 = ModelMapper(modelDataWrapper)
        statue_box_1_r1.setPos(-15.4148f, -23f, -2.647f)
        statue_box.addChild(statue_box_1_r1)
        setRotationAngle(statue_box_1_r1, 0f, -0.2618f, 0f)
        statue_box_1_r1.texOffs(212, 76).addBox(-3.5f, -9f, 0f, 6, 18, 0, 0f, false)

        seat_side_1 = ModelMapper(modelDataWrapper)
        seat_side_1.setPos(0f, 0f, 0f)
        seat_curve.addChild(seat_side_1)
        seat_side_1.texOffs(22, 72).addBox(-10.9289f, -13.9357f, -0.9289f, 6, 0, 1, 0f, false)

        seat_back_6_r1 = ModelMapper(modelDataWrapper)
        seat_back_6_r1.setPos(-7.9289f, -9.9684f, -1.6376f)
        seat_side_1.addChild(seat_back_6_r1)
        setRotationAngle(seat_back_6_r1, -0.0524f, 0f, 0f)
        seat_back_6_r1.texOffs(0, 169).addBox(-3f, -4f, -0.5f, 6, 8, 1, 0f, true)

        seat_top_5_r1 = ModelMapper(modelDataWrapper)
        seat_top_5_r1.setPos(-10.2218f, -12.9367f, -5.4645f)
        seat_side_1.addChild(seat_top_5_r1)
        setRotationAngle(seat_top_5_r1, 0f, 0.7854f, 0f)
        seat_top_5_r1.texOffs(18, 24).addBox(-5f, -1f, -8f, 2, 0, 10, 0f, false)

        seat_top_7_r1 = ModelMapper(modelDataWrapper)
        seat_top_7_r1.setPos(-14.9289f, -13.9367f, -3.9289f)
        seat_side_1.addChild(seat_top_7_r1)
        setRotationAngle(seat_top_7_r1, 0f, 1.5708f, 0f)
        seat_top_7_r1.texOffs(0, 0).addBox(-4f, 0f, -4f, 8, 0, 8, 0f, false)

        seat_back_3_r2 = ModelMapper(modelDataWrapper)
        seat_back_3_r2.setPos(-14.1109f, -6f, -5.818f)
        seat_side_1.addChild(seat_back_3_r2)
        setRotationAngle(seat_back_3_r2, 0f, 0.7854f, 0f)
        seat_back_3_r2.texOffs(0, 217).addBox(-0.5f, -8f, -5f, 1, 8, 11, 0f, false)

        seat_bottom_4_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_4_r1.setPos(-12.6967f, -5.5f, -7.2322f)
        seat_side_1.addChild(seat_bottom_4_r1)
        setRotationAngle(seat_bottom_4_r1, 0f, 0.7854f, 0f)
        seat_bottom_4_r1.texOffs(131, 182).addBox(-2.5f, -0.6f, -5f, 6, 1, 10, 0f, false)

        seat_bottom_5_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_5_r1.setPos(-7.9289f, -5.5f, -4.8462f)
        seat_side_1.addChild(seat_bottom_5_r1)
        setRotationAngle(seat_bottom_5_r1, 0f, 1.5708f, 0f)
        seat_bottom_5_r1.texOffs(153, 182).addBox(-3f, -0.5f, -4f, 6, 1, 7, 0f, false)

        seat_side_2 = ModelMapper(modelDataWrapper)
        seat_side_2.setPos(0f, 0f, 0f)
        seat_curve.addChild(seat_side_2)
        seat_side_2.texOffs(22, 72).addBox(-10.9289f, -13.9357f, -0.0711f, 6, 0, 1, 0f, false)

        seat_top_8_r1 = ModelMapper(modelDataWrapper)
        seat_top_8_r1.setPos(-14.9289f, -13.9367f, 4.0711f)
        seat_side_2.addChild(seat_top_8_r1)
        setRotationAngle(seat_top_8_r1, 0f, 1.5708f, 0f)
        seat_top_8_r1.texOffs(0, 0).addBox(-4f, 0f, -4f, 8, 0, 8, 0f, true)

        seat_top_6_r1 = ModelMapper(modelDataWrapper)
        seat_top_6_r1.setPos(-3.1508f, -12.9367f, -1.4645f)
        seat_side_2.addChild(seat_top_6_r1)
        setRotationAngle(seat_top_6_r1, 0f, -0.7854f, 0f)
        seat_top_6_r1.texOffs(18, 24).addBox(-5f, -1f, 8f, 2, 0, 10, 0f, false)

        seat_back_5_r1 = ModelMapper(modelDataWrapper)
        seat_back_5_r1.setPos(-7.9289f, -9.9684f, 1.6376f)
        seat_side_2.addChild(seat_back_5_r1)
        setRotationAngle(seat_back_5_r1, -0.0524f, 3.1416f, 0f)
        seat_back_5_r1.texOffs(0, 169).addBox(-3f, -4f, -0.5f, 6, 8, 1, 0f, false)

        seat_bottom_6_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_6_r1.setPos(-10.4289f, -5.5f, 5.3462f)
        seat_side_2.addChild(seat_bottom_6_r1)
        setRotationAngle(seat_bottom_6_r1, 0f, 1.5708f, 0f)
        seat_bottom_6_r1.texOffs(153, 182).addBox(-3f, -0.5f, -1.5f, 6, 1, 7, 0f, true)

        seat_back_4_r1 = ModelMapper(modelDataWrapper)
        seat_back_4_r1.setPos(-30.0208f, -9f, 21.7279f)
        seat_side_2.addChild(seat_back_4_r1)
        setRotationAngle(seat_back_4_r1, 0f, -0.7854f, 0f)
        seat_back_4_r1.texOffs(0, 217).addBox(-0.5f, -5f, -28.5f, 1, 8, 11, 0f, false)

        seat_bottom_3_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_3_r1.setPos(-12.6967f, -5.5f, 7.2322f)
        seat_side_2.addChild(seat_bottom_3_r1)
        setRotationAngle(seat_bottom_3_r1, 0f, -0.7854f, 0f)
        seat_bottom_3_r1.texOffs(131, 182).addBox(-2.5f, -0.6f, -5f, 6, 1, 10, 0f, false)

        window_edge = ModelMapper(modelDataWrapper)
        window_edge.setPos(0f, 24f, 0f)


        edge_side_1 = ModelMapper(modelDataWrapper)
        edge_side_1.setPos(0f, 0f, 0f)
        window_edge.addChild(edge_side_1)


        window_edge_2_r1 = ModelMapper(modelDataWrapper)
        window_edge_2_r1.setPos(41.6208f, -7.1535f, 64f)
        edge_side_1.addChild(window_edge_2_r1)
        setRotationAngle(window_edge_2_r1, 0f, -1.5708f, 0.1107f)
        window_edge_2_r1.texOffs(65, 146).addBox(-4f, -19f, 60f, 6, 19, 2, 0f, false)

        window_edge_1_r1 = ModelMapper(modelDataWrapper)
        window_edge_1_r1.setPos(-17f, -7f, 65f)
        edge_side_1.addChild(window_edge_1_r1)
        setRotationAngle(window_edge_1_r1, 0f, -1.5708f, 0f)
        window_edge_1_r1.texOffs(154, 32).addBox(-5f, -7f, 1f, 6, 14, 2, 0f, false)

        edge_side_2 = ModelMapper(modelDataWrapper)
        edge_side_2.setPos(0f, 0f, 6f)
        window_edge.addChild(edge_side_2)


        window_edge_3_r1 = ModelMapper(modelDataWrapper)
        window_edge_3_r1.setPos(41.6208f, -7.1535f, -68f)
        edge_side_2.addChild(window_edge_3_r1)
        setRotationAngle(window_edge_3_r1, 0f, -1.5708f, 0.1107f)
        window_edge_3_r1.texOffs(65, 146).addBox(-4f, -19f, 60f, 6, 19, 2, 0f, true)

        window_edge_2_r2 = ModelMapper(modelDataWrapper)
        window_edge_2_r2.setPos(-17f, -7f, -67f)
        edge_side_2.addChild(window_edge_2_r2)
        setRotationAngle(window_edge_2_r2, 0f, -1.5708f, 0f)
        window_edge_2_r2.texOffs(154, 32).addBox(-5f, -7f, 1f, 6, 14, 2, 0f, true)

        statue_box_translucent = ModelMapper(modelDataWrapper)
        statue_box_translucent.setPos(0f, 24f, 0f)
        statue_box_translucent.texOffs(351, 78).addBox(-13f, -32f, -2f, 0, 18, 4, 0f, false)

        statue_box_translucent_3_r1 = ModelMapper(modelDataWrapper)
        statue_box_translucent_3_r1.setPos(-16.3807f, -23f, 2.9059f)
        statue_box_translucent.addChild(statue_box_translucent_3_r1)
        setRotationAngle(statue_box_translucent_3_r1, 0f, 0.2618f, 0f)
        statue_box_translucent_3_r1.texOffs(349, 57).addBox(-1.5f, -9f, 0f, 5, 18, 0, 0f, false)

        statue_box_translucent_1_r1 = ModelMapper(modelDataWrapper)
        statue_box_translucent_1_r1.setPos(-15.4148f, -23f, -2.647f)
        statue_box_translucent.addChild(statue_box_translucent_1_r1)
        setRotationAngle(statue_box_translucent_1_r1, 0f, -0.2618f, 0f)
        statue_box_translucent_1_r1.texOffs(349, 57).addBox(-2.5f, -9f, 0f, 5, 18, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrails_1.setModelPart()
        window_handrails_2.setModelPart()
        window_exterior.setModelPart()
        roof_window_1.setModelPart()
        roof_window_2.setModelPart()
        roof_window_3.setModelPart()
        roof_window_light.setModelPart()
        side_panel_1.setModelPart()
        side_panel_2.setModelPart()
        side_panel_translucent.setModelPart()
        roof_door.setModelPart()
        roof_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_handrail_1.setModelPart()
        door_handrail_2.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        end.setModelPart()
        end_exterior.setModelPart()
        roof_end.setModelPart()
        roof_end_exterior.setModelPart()
        roof_head_exterior.setModelPart()
        roof_light.setModelPart()
        roof_end_light.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        seat_1.setModelPart()
        seat_2.setModelPart()
        window_edge.setModelPart()
        seat_curve.setModelPart()
        statue_box_translucent.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelDRL {
        return ModelDRL(doorAnimationType, renderDoorOverlay)
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
        val isEvenWindow = isEvenWindow(position)
        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                renderMirror(roof_light, matrices, vertices, light, (position + 40).toFloat())
                renderMirror(roof_light, matrices, vertices, light, (position - 40).toFloat())
                renderMirror(roof_window_light, matrices, vertices, light, position.toFloat())
                renderMirror(roof_window_light, matrices, vertices, light, (position + 40).toFloat())
                renderMirror(roof_window_light, matrices, vertices, light, (position - 40).toFloat())
            }

            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                renderMirror(window_edge, matrices, vertices, light, position.toFloat())
                renderMirror(window, matrices, vertices, light, (position + 40).toFloat())
                renderMirror(window, matrices, vertices, light, (position - 40).toFloat())
                if (renderDetails) {
                    renderMirror(roof_window_2, matrices, vertices, light, position.toFloat())
                    renderOnce(roof_window_1, matrices, vertices, light, (position - 40).toFloat())
                    renderOnceFlipped(roof_window_1, matrices, vertices, light, (position + 40).toFloat())
                    renderOnce(roof_window_3, matrices, vertices, light, (position + 40).toFloat())
                    renderOnceFlipped(roof_window_3, matrices, vertices, light, (position - 40).toFloat())
                    renderMirror(
                        side_panel_2,
                        matrices,
                        vertices,
                        light,
                        (position + (if (isEvenWindow) -40 else 40)).toFloat()
                    )
                    renderMirror(
                        side_panel_1,
                        matrices,
                        vertices,
                        light,
                        (position + (if (isEvenWindow) 60 else -60)).toFloat()
                    )
                    renderMirror(
                        seat_curve,
                        matrices,
                        vertices,
                        light,
                        (position + (if (isEvenWindow) 20 else -20)).toFloat()
                    )
                    if (isEvenWindow) {
                        renderOnce(seat_1, matrices, vertices, light, position.toFloat())
                        renderOnceFlipped(seat_2, matrices, vertices, light, position.toFloat())
                        renderOnce(window_handrails_2, matrices, vertices, light, position.toFloat())
                    } else {
                        renderOnceFlipped(seat_1, matrices, vertices, light, position.toFloat())
                        renderOnce(seat_2, matrices, vertices, light, position.toFloat())
                        renderOnce(window_handrails_1, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(
                    side_panel_translucent,
                    matrices,
                    vertices,
                    light,
                    (position + (if (isEvenWindow) 60 else -60)).toFloat()
                )
                renderMirror(
                    statue_box_translucent,
                    matrices,
                    vertices,
                    light,
                    (position + (if (isEvenWindow) 20 else -20)).toFloat()
                )
            }

            RenderStage.EXTERIOR -> {
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position - 40).toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position + 40).toFloat())
                renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
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
        val middleDoor = isIndex(getDoorPositions()!!.size / 2, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0
        val notLastDoor = !isIndex(0, position, getDoorPositions()) && !isIndex(-1, position, getDoorPositions())

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                if (notLastDoor) {
                    renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && doorOpen) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 30).toFloat())
                }
            }

            RenderStage.INTERIOR -> {
                door_left.setOffset(0f, 0, doorRightZ)
                door_right.setOffset(0f, 0, -doorRightZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_left.setOffset(0f, 0, doorLeftZ)
                door_right.setOffset(0f, 0, -doorLeftZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    if (notLastDoor) {
                        renderOnce(roof_door, matrices, vertices, light, position.toFloat())
                        renderOnceFlipped(roof_door, matrices, vertices, light, position.toFloat())
                        renderOnce(door_handrail_1, matrices, vertices, light, position.toFloat())
                    } else {
                        renderOnce(door_handrail_2, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                door_left_exterior.setOffset(0f, 0, doorRightZ)
                door_right_exterior.setOffset(0f, 0, -doorRightZ)
                renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                door_left_exterior.setOffset(0f, 0, doorLeftZ)
                door_right_exterior.setOffset(0f, 0, -doorLeftZ)
                renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (middleDoor && renderDetails) {
                    renderMirror(door_light, matrices, vertices, light, (position - 30).toFloat())
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 30).toFloat())
                    }
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
            RenderStage.LIGHTS -> renderOnce(roof_end_light, matrices, vertices, light, position.toFloat())
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
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_head_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
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
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_head_exterior, matrices, vertices, light, position.toFloat())
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
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderOnce(roof_end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }

    @Override
    override fun getModelDoorOverlay(): ModelDoorOverlay {
        return MODEL_DOOR_OVERLAY
    }

    @Override
    override fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase? {
        return null
    }

    @Override
    override fun getWindowPositions(): IntArray? {
        return intArrayOf(-80, 80)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-160, 0, 160)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-184, 184)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    private fun isEvenWindow(position: Int): Boolean {
        return isIndex(1, position, getWindowPositions()) || isIndex(3, position, getWindowPositions())
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_drl_left.png", "door_overlay_drl_right.png")
    }
}
