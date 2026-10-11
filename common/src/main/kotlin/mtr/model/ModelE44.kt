package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelE44 protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelE44?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_handrails: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val seat_7: ModelMapper
    private val seat_back_r1: ModelMapper
    private val seat_bottom_r1: ModelMapper
    private val seat_8: ModelMapper
    private val seat_back_r2: ModelMapper
    private val door: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_handrails: ModelMapper
    private val handrail_1_r2: ModelMapper
    private val side_panel: ModelMapper
    private val handrail_11_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val inner_roof_1_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val roof_window_light: ModelMapper
    private val roof_door_light: ModelMapper
    private val roof_exterior_window: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val roof_exterior_door: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val seat_1: ModelMapper
    private val seat_back_r3: ModelMapper
    private val seat_bottom_r2: ModelMapper
    private val seat_2: ModelMapper
    private val seat_back_r4: ModelMapper
    private val seat_bottom_r3: ModelMapper
    private val seat_3: ModelMapper
    private val seat_back_r5: ModelMapper
    private val seat_4: ModelMapper
    private val seat_back_r6: ModelMapper
    private val seat_bottom_r4: ModelMapper
    private val seat_5: ModelMapper
    private val seat_back_r7: ModelMapper
    private val seat_bottom_r5: ModelMapper
    private val seat_6: ModelMapper
    private val seat_back_r8: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_2_r4: ModelMapper
    private val inner_roof_1_r2: ModelMapper
    private val roof_end_light: ModelMapper
    private val roof_end_handrails: ModelMapper
    private val handrail_11_r2: ModelMapper
    private val handrail_10_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val outer_roof_1: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val roof_end_vents: ModelMapper
    private val vent_3_r1: ModelMapper
    private val vent_2_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val seat_9: ModelMapper
    private val seat_back_r9: ModelMapper
    private val seat_10: ModelMapper
    private val seat_back_r10: ModelMapper
    private val seat_11: ModelMapper
    private val seat_back_r11: ModelMapper
    private val seat_12: ModelMapper
    private val seat_back_r12: ModelMapper
    private val head_exterior: ModelMapper
    private val driver_door_upper_2_r1: ModelMapper
    private val upper_wall_2_r6: ModelMapper
    private val driver_door_upper_1_r1: ModelMapper
    private val upper_wall_1_r5: ModelMapper
    private val front: ModelMapper
    private val head_roof_r1: ModelMapper
    private val head_top_r1: ModelMapper
    private val head_bottom_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val outer_head_4_r1: ModelMapper
    private val outer_head_2_r1: ModelMapper
    private val outer_head_1_r1: ModelMapper
    private val outer_roof_1_r3: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_11_r1: ModelMapper
    private val outer_head_6_r1: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_head_5_r1: ModelMapper
    private val side_2: ModelMapper
    private val outer_head_6_r2: ModelMapper
    private val outer_head_5_r2: ModelMapper
    private val outer_head_4_r2: ModelMapper
    private val outer_head_2_r2: ModelMapper
    private val outer_head_1_r2: ModelMapper
    private val outer_roof_11_r2: ModelMapper
    private val outer_roof_5_r6: ModelMapper
    private val outer_roof_4_r6: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val outer_roof_2_r6: ModelMapper
    private val outer_roof_1_r4: ModelMapper
    private val emergency_door: ModelMapper
    private val pipe: ModelMapper
    private val valve_8_r1: ModelMapper
    private val valve_7_r1: ModelMapper
    private val valve_6_r1: ModelMapper
    private val valve_5_r1: ModelMapper
    private val valve_4_r1: ModelMapper
    private val valve_3_r1: ModelMapper
    private val valve_2_r1: ModelMapper
    private val valve_1_r1: ModelMapper
    private val headlights: ModelMapper
    private val outer_head_5_r3: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_r1: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_1_r5: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r2: ModelMapper

    constructor() : this(DoorAnimationType.BOUNCY_1, true)

    init {
        val textureWidth = 336
        val textureHeight = 336

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(191, 39).addBox(-20f, 0f, -15f, 20, 1, 30, 0f, false)
        window.texOffs(36, 271).addBox(-20f, -13f, -15f, 2, 13, 30, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -13f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(245, 247).addBox(0f, -20f, -15f, 2, 20, 30, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(100, 271).addBox(-21f, 0f, -15f, 1, 2, 30, 0f, false)
        window_exterior.texOffs(94, 221).addBox(-20f, -13f, -15f, 0, 13, 30, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-20f, -13f, 0f)
        window_exterior.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(214, 217).addBox(0f, -20f, -15f, 0, 20, 30, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(4, 41).addBox(-9f, -31f, 7f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(4, 41).addBox(-9f, -31f, 0f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(4, 41).addBox(-9f, -31f, -7f, 2, 4, 0, 0f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(-7.8f, -30.7f, 16.2f)
        window_handrails.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0f, 0f, -0.0873f)
        handrail_4_r1.texOffs(0, 0).addBox(-0.2f, 0.2f, -1.2f, 0, 18, 0, 0.2f, false)
        handrail_4_r1.texOffs(0, 0).addBox(-0.2f, 0.2f, -31.2f, 0, 18, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, -1.5708f, 0f, 0f)
        handrail_1_r1.texOffs(0, 0).addBox(-8f, -15f, -30.5f, 0, 30, 0, 0.2f, false)

        seat_7 = ModelMapper(modelDataWrapper)
        seat_7.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat_7)


        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(0f, -6f, -13.5f)
        seat_7.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, -0.0524f, 3.1416f, 0f)
        seat_back_r1.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_bottom_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_r1.setPos(0f, 0f, 8f)
        seat_7.addChild(seat_bottom_r1)
        setRotationAngle(seat_bottom_r1, 0f, 3.1416f, 0f)
        seat_bottom_r1.texOffs(0, 85).addBox(6f, -6f, 15.5f, 12, 1, 7, 0f, false)

        seat_8 = ModelMapper(modelDataWrapper)
        seat_8.setPos(-24f, 0f, 2f)
        window_handrails.addChild(seat_8)
        seat_8.texOffs(0, 85).addBox(6f, -6f, 5.5f, 12, 1, 7, 0f, false)

        seat_back_r2 = ModelMapper(modelDataWrapper)
        seat_back_r2.setPos(0f, -6f, 11.5f)
        seat_8.addChild(seat_back_r2)
        setRotationAngle(seat_back_r2, -0.0524f, 0f, 0f)
        seat_back_r2.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(125, 0).addBox(-20f, 0f, -19f, 20, 1, 38, 0f, false)
        door.texOffs(154, 246).addBox(-20f, -13f, -19f, 2, 13, 5, 0f, false)
        door.texOffs(239, 15).addBox(-20f, -13f, 14f, 2, 13, 5, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -13f, 0f)
        door.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(196, 240).addBox(0f, -20f, 14f, 2, 20, 5, 0f, false)
        upper_wall_2_r1.texOffs(114, 271).addBox(0f, -20f, -19f, 2, 20, 5, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(47, 187).addBox(-20.8f, -13f, 0f, 1, 13, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -13f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(132, 91).addBox(0f, -20f, 0f, 1, 20, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(0, 187).addBox(-20.8f, -13f, -15f, 1, 13, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -13f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 96).addBox(0f, -20f, -15f, 1, 20, 15, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(132, 231).addBox(-21f, 0f, -19f, 1, 2, 38, 0f, false)
        door_exterior.texOffs(0, 91).addBox(-20f, -13f, -19f, 0, 13, 5, 0f, false)
        door_exterior.texOffs(0, 182).addBox(-20f, -13f, 14f, 0, 13, 5, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -13f, 0f)
        door_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(32, 197).addBox(0f, -20f, 14f, 0, 20, 5, 0f, false)
        upper_wall_2_r2.texOffs(164, 99).addBox(0f, -20f, -19f, 0, 20, 5, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(149, 76).addBox(-20.8f, -13f, 0f, 0, 13, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -13f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(166, 167).addBox(0f, -20f, 0f, 0, 20, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(0, 116).addBox(-20.8f, -13f, -15f, 0, 13, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -13f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(77, 81).addBox(0f, -20f, -15f, 0, 20, 15, 0f, false)

        door_handrails = ModelMapper(modelDataWrapper)
        door_handrails.setPos(0f, 24f, 0f)


        handrail_1_r2 = ModelMapper(modelDataWrapper)
        handrail_1_r2.setPos(0f, 0f, 0f)
        door_handrails.addChild(handrail_1_r2)
        setRotationAngle(handrail_1_r2, -1.5708f, 0f, 0f)
        handrail_1_r2.texOffs(0, 0).addBox(-8f, -19f, -30.5f, 0, 38, 0, 0.2f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(287, 164).addBox(-18f, -15f, 0f, 13, 15, 0, 0f, false)

        handrail_11_r1 = ModelMapper(modelDataWrapper)
        handrail_11_r1.setPos(0f, 0f, 0f)
        side_panel.addChild(handrail_11_r1)
        setRotationAngle(handrail_11_r1, 0f, 0f, 0f)
        handrail_11_r1.texOffs(0, 0).addBox(-6.3963f, -12.17f, 0f, 0, 13, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-7.8f, -30.7f, 0f)
        side_panel.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, -0.0873f)
        handrail_5_r1.texOffs(0, 0).addBox(-0.2f, 0.2f, 0f, 0, 18, 0, 0.2f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(287, 148).addBox(-18f, -31f, 0f, 13, 16, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(50, 46).addBox(-9.775f, -32.575f, -15f, 7, 0, 30, 0f, false)
        roof_window.texOffs(0, 0).addBox(-3f, -33.575f, -15f, 3, 0, 30, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-2.775f, -33.076f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, 1.5708f)
        inner_roof_4_r1.texOffs(6, 0).addBox(-0.5f, 0f, -15f, 1, 0, 30, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-9.775f, -32.575f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, 1.309f)
        inner_roof_2_r1.texOffs(0, 46).addBox(-2f, 0f, -15f, 2, 0, 30, 0f, false)

        inner_roof_1_r1 = ModelMapper(modelDataWrapper)
        inner_roof_1_r1.setPos(0f, 0f, 0f)
        roof_window.addChild(inner_roof_1_r1)
        setRotationAngle(inner_roof_1_r1, 0f, 0f, -0.3491f)
        inner_roof_1_r1.texOffs(64, 46).addBox(-4.75f, -35f, -15f, 7, 0, 30, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(215, 208).addBox(-18f, -32f, -19f, 3, 1, 38, 0f, false)
        roof_door.texOffs(103, 0).addBox(-9.775f, -32.575f, -19f, 7, 0, 38, 0f, false)
        roof_door.texOffs(166, 169).addBox(-3f, -33.575f, -19f, 3, 0, 38, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(-2.775f, -33.076f, 0f)
        roof_door.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, 1.5708f)
        inner_roof_6_r1.texOffs(8, 96).addBox(-0.5f, 0f, -19f, 1, 0, 38, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-9.775f, -32.575f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, 1.309f)
        inner_roof_4_r2.texOffs(0, 96).addBox(-2f, 0f, -19f, 2, 0, 38, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(0f, 0f, 0f)
        roof_door.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.3491f)
        inner_roof_3_r1.texOffs(0, 46).addBox(0.25f, -35f, -19f, 2, 0, 38, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13.863f, -32.0306f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.5236f)
        inner_roof_2_r2.texOffs(204, 169).addBox(-1.5f, -0.675f, -19f, 5, 1, 38, 0f, false)

        roof_window_light = ModelMapper(modelDataWrapper)
        roof_window_light.setPos(0f, 24f, 0f)
        roof_window_light.texOffs(37, 102).addBox(-2.5f, -33.1f, -15f, 5, 0, 30, 0f, false)

        roof_door_light = ModelMapper(modelDataWrapper)
        roof_door_light.setPos(0f, 24f, 0f)
        roof_door_light.texOffs(29, 98).addBox(-2.5f, -33.1f, -19f, 5, 0, 38, 0f, false)

        roof_exterior_window = ModelMapper(modelDataWrapper)
        roof_exterior_window.setPos(0f, 24f, 0f)
        roof_exterior_window.texOffs(173, 0).addBox(-6f, -42f, -15f, 6, 0, 30, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior_window.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(95, 0).addBox(-4f, 0f, -15f, 8, 0, 30, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior_window.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(125, 0).addBox(-1.5f, 0f, -15f, 3, 0, 30, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior_window.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(8, 0).addBox(-1f, 0f, -15f, 2, 0, 30, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior_window.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 0.1107f)
        outer_roof_2_r1.texOffs(133, 9).addBox(0f, -1f, -15f, 0, 2, 30, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -13f, 0f)
        roof_exterior_window.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(62, 237).addBox(-1f, -23f, -15f, 1, 4, 30, 0f, false)

        roof_exterior_door = ModelMapper(modelDataWrapper)
        roof_exterior_door.setPos(0f, 24f, 0f)
        roof_exterior_door.texOffs(165, 0).addBox(-6f, -42f, -19f, 6, 0, 38, 0f, false)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior_door.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, -0.1745f)
        outer_roof_6_r1.texOffs(87, 0).addBox(-4f, 0f, -19f, 8, 0, 38, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior_door.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.5236f)
        outer_roof_5_r2.texOffs(117, 0).addBox(-1.5f, 0f, -19f, 3, 0, 38, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior_door.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -1.0472f)
        outer_roof_4_r2.texOffs(0, 0).addBox(-1f, 0f, -19f, 2, 0, 38, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior_door.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, 0.1107f)
        outer_roof_3_r2.texOffs(125, 1).addBox(0f, -1f, -19f, 0, 2, 38, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-20f, -13f, 0f)
        roof_exterior_door.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, 0.1107f)
        outer_roof_2_r2.texOffs(54, 229).addBox(-1f, -23f, -19f, 1, 4, 38, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(0, 0).addBox(-20f, 0f, -32f, 40, 1, 45, 0f, false)
        end.texOffs(102, 120).addBox(-18f, -13f, -36f, 0, 13, 49, 0f, false)
        end.texOffs(132, 29).addBox(18f, -13f, -36f, 0, 13, 49, 0f, true)
        end.texOffs(150, 291).addBox(6f, -32f, -36f, 12, 32, 12, 0f, true)
        end.texOffs(198, 291).addBox(-18f, -32f, -36f, 12, 32, 12, 0f, false)
        end.texOffs(215, 0).addBox(-18f, -35f, -36f, 36, 3, 12, 0f, false)
        end.texOffs(0, 291).addBox(-6f, -32f, -24f, 12, 32, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -13f, 0f)
        end.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(102, 98).addBox(2f, -20f, -36f, 0, 20, 49, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -13f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(0, 118).addBox(-2f, -20f, -36f, 0, 20, 49, 0f, true)

        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 0f, 0f)
        end.addChild(seat_1)


        seat_back_r3 = ModelMapper(modelDataWrapper)
        seat_back_r3.setPos(0f, -6f, -22f)
        seat_1.addChild(seat_back_r3)
        setRotationAngle(seat_back_r3, -0.0524f, 3.1416f, 0f)
        seat_back_r3.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_bottom_r2 = ModelMapper(modelDataWrapper)
        seat_bottom_r2.setPos(0f, 0f, 0f)
        seat_1.addChild(seat_bottom_r2)
        setRotationAngle(seat_bottom_r2, 0f, 3.1416f, 0f)
        seat_bottom_r2.texOffs(0, 85).addBox(-18f, -6f, 16f, 12, 1, 7, 0f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 0f, 0f)
        end.addChild(seat_2)


        seat_back_r4 = ModelMapper(modelDataWrapper)
        seat_back_r4.setPos(0f, -6f, -10f)
        seat_2.addChild(seat_back_r4)
        setRotationAngle(seat_back_r4, -0.0524f, 3.1416f, 0f)
        seat_back_r4.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_bottom_r3 = ModelMapper(modelDataWrapper)
        seat_bottom_r3.setPos(0f, 0f, 0f)
        seat_2.addChild(seat_bottom_r3)
        setRotationAngle(seat_bottom_r3, 0f, 3.1416f, 0f)
        seat_bottom_r3.texOffs(0, 85).addBox(-18f, -6f, 4f, 12, 1, 7, 0f, false)

        seat_3 = ModelMapper(modelDataWrapper)
        seat_3.setPos(0f, 0f, 0f)
        end.addChild(seat_3)
        seat_3.texOffs(0, 85).addBox(6f, -6f, 5f, 12, 1, 7, 0f, false)

        seat_back_r5 = ModelMapper(modelDataWrapper)
        seat_back_r5.setPos(0f, -6f, 11f)
        seat_3.addChild(seat_back_r5)
        setRotationAngle(seat_back_r5, -0.0524f, 0f, 0f)
        seat_back_r5.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_4 = ModelMapper(modelDataWrapper)
        seat_4.setPos(0f, 0f, 0f)
        end.addChild(seat_4)


        seat_back_r6 = ModelMapper(modelDataWrapper)
        seat_back_r6.setPos(0f, -6f, -22f)
        seat_4.addChild(seat_back_r6)
        setRotationAngle(seat_back_r6, -0.0524f, 3.1416f, 0f)
        seat_back_r6.texOffs(53, 138).addBox(-18f, -8f, 0f, 12, 8, 1, 0f, true)

        seat_bottom_r4 = ModelMapper(modelDataWrapper)
        seat_bottom_r4.setPos(0f, 0f, 0f)
        seat_4.addChild(seat_bottom_r4)
        setRotationAngle(seat_bottom_r4, 0f, 3.1416f, 0f)
        seat_bottom_r4.texOffs(0, 85).addBox(6f, -6f, 16f, 12, 1, 7, 0f, true)

        seat_5 = ModelMapper(modelDataWrapper)
        seat_5.setPos(0f, 0f, 0f)
        end.addChild(seat_5)


        seat_back_r7 = ModelMapper(modelDataWrapper)
        seat_back_r7.setPos(0f, -6f, -10f)
        seat_5.addChild(seat_back_r7)
        setRotationAngle(seat_back_r7, -0.0524f, 3.1416f, 0f)
        seat_back_r7.texOffs(53, 138).addBox(-18f, -8f, 0f, 12, 8, 1, 0f, true)

        seat_bottom_r5 = ModelMapper(modelDataWrapper)
        seat_bottom_r5.setPos(0f, 0f, 0f)
        seat_5.addChild(seat_bottom_r5)
        setRotationAngle(seat_bottom_r5, 0f, 3.1416f, 0f)
        seat_bottom_r5.texOffs(0, 85).addBox(6f, -6f, 4f, 12, 1, 7, 0f, true)

        seat_6 = ModelMapper(modelDataWrapper)
        seat_6.setPos(0f, 0f, 0f)
        end.addChild(seat_6)
        seat_6.texOffs(0, 85).addBox(-18f, -6f, 5f, 12, 1, 7, 0f, true)

        seat_back_r8 = ModelMapper(modelDataWrapper)
        seat_back_r8.setPos(0f, -6f, 11f)
        seat_6.addChild(seat_back_r8)
        setRotationAngle(seat_back_r8, -0.0524f, 0f, 0f)
        seat_back_r8.texOffs(53, 138).addBox(-18f, -8f, 0f, 12, 8, 1, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(0, 187).addBox(20f, 0f, -32f, 1, 2, 45, 0f, true)
        end_exterior.texOffs(0, 187).addBox(-21f, 0f, -32f, 1, 2, 45, 0f, false)
        end_exterior.texOffs(151, 169).addBox(18f, -13f, -36f, 2, 13, 49, 0f, true)
        end_exterior.texOffs(49, 167).addBox(-20f, -13f, -36f, 2, 13, 49, 0f, false)
        end_exterior.texOffs(59, 234).addBox(6f, -33f, -36f, 12, 33, 0, 0f, true)
        end_exterior.texOffs(35, 234).addBox(-18f, -33f, -36f, 12, 33, 0, 0f, false)
        end_exterior.texOffs(172, 231).addBox(-18f, -41f, -36f, 36, 9, 0, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-20f, -13f, 0f)
        end_exterior.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(79, 78).addBox(0f, -20f, -36f, 2, 20, 49, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -13f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(0, 98).addBox(-2f, -20f, -36f, 2, 20, 49, 0f, true)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)
        roof_end.texOffs(16, 96).addBox(-9.775f, -32.575f, -24f, 7, 0, 37, 0f, false)
        roof_end.texOffs(5, 46).addBox(-3f, -33.575f, -24f, 3, 0, 37, 0f, false)
        roof_end.texOffs(16, 96).addBox(2.775f, -32.575f, -24f, 7, 0, 37, 0f, true)
        roof_end.texOffs(5, 46).addBox(0f, -33.575f, -24f, 3, 0, 37, 0f, true)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(2.775f, -33.076f, 0f)
        roof_end.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -1.5708f)
        inner_roof_5_r1.texOffs(5, 0).addBox(-0.5f, 0f, -24f, 1, 0, 37, 0f, true)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(9.775f, -32.575f, 0f)
        roof_end.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -1.309f)
        inner_roof_3_r2.texOffs(5, 96).addBox(0f, 0f, -24f, 2, 0, 37, 0f, true)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(0f, 0f, 0f)
        roof_end.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, 0.3491f)
        inner_roof_2_r3.texOffs(144, 91).addBox(-2.25f, -35f, -24f, 7, 0, 37, 0f, true)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(-2.775f, -33.076f, 0f)
        roof_end.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, 1.5708f)
        inner_roof_4_r3.texOffs(5, 0).addBox(-0.5f, 0f, -24f, 1, 0, 37, 0f, false)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(-9.775f, -32.575f, 0f)
        roof_end.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, 1.309f)
        inner_roof_2_r4.texOffs(5, 96).addBox(-2f, 0f, -24f, 2, 0, 37, 0f, false)

        inner_roof_1_r2 = ModelMapper(modelDataWrapper)
        inner_roof_1_r2.setPos(0f, 0f, 0f)
        roof_end.addChild(inner_roof_1_r2)
        setRotationAngle(inner_roof_1_r2, 0f, 0f, -0.3491f)
        inner_roof_1_r2.texOffs(144, 91).addBox(-4.75f, -35f, -24f, 7, 0, 37, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)
        roof_end_light.texOffs(25, 96).addBox(-2.5f, -33.1f, -24f, 5, 0, 42, 0f, false)

        roof_end_handrails = ModelMapper(modelDataWrapper)
        roof_end_handrails.setPos(0f, 24f, 0f)
        roof_end_handrails.texOffs(4, 41).addBox(-9f, -31f, 7f, 2, 4, 0, 0f, false)
        roof_end_handrails.texOffs(4, 41).addBox(-9f, -31f, 0f, 2, 4, 0, 0f, false)
        roof_end_handrails.texOffs(4, 41).addBox(-9f, -31f, -7f, 2, 4, 0, 0f, false)
        roof_end_handrails.texOffs(0, 0).addBox(-8f, -33.4899f, -16.9899f, 0, 2, 0, 0.2f, false)
        roof_end_handrails.texOffs(4, 41).addBox(7f, -31f, 7f, 2, 4, 0, 0f, true)
        roof_end_handrails.texOffs(4, 41).addBox(7f, -31f, 0f, 2, 4, 0, 0f, true)
        roof_end_handrails.texOffs(4, 41).addBox(7f, -31f, -7f, 2, 4, 0, 0f, true)
        roof_end_handrails.texOffs(0, 0).addBox(8f, -33.4899f, -16.9899f, 0, 2, 0, 0.2f, true)

        handrail_11_r2 = ModelMapper(modelDataWrapper)
        handrail_11_r2.setPos(10f, -30.9364f, -16.5536f)
        roof_end_handrails.addChild(handrail_11_r2)
        setRotationAngle(handrail_11_r2, 0.7854f, 0f, 0f)
        handrail_11_r2.texOffs(0, 0).addBox(-2f, -0.5f, 0f, 0, 1, 0, 0.2f, true)
        handrail_11_r2.texOffs(0, 0).addBox(-18f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_10_r1 = ModelMapper(modelDataWrapper)
        handrail_10_r1.setPos(0f, 0f, 0f)
        roof_end_handrails.addChild(handrail_10_r1)
        setRotationAngle(handrail_10_r1, -1.5708f, 0f, 0f)
        handrail_10_r1.texOffs(0, 0).addBox(8f, -16f, -30.5f, 0, 32, 0, 0.2f, true)
        handrail_10_r1.texOffs(0, 0).addBox(-8f, -16f, -30.5f, 0, 32, 0, 0.2f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)


        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(93, 79).addBox(-6f, -42f, -36f, 6, 1, 11, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(-20f, -13f, 0f)
        outer_roof_1.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, 0.1107f)
        upper_wall_1_r3.texOffs(181, 133).addBox(0f, -23f, -36f, 1, 4, 7, 0f, false)
        upper_wall_1_r3.texOffs(34, 134).addBox(-1f, -23f, -29f, 1, 4, 4, 0f, false)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_1.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, -0.1745f)
        outer_roof_5_r3.texOffs(155, 204).addBox(-4f, -1f, -36f, 8, 1, 11, 0f, false)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_1.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, -0.5236f)
        outer_roof_4_r3.texOffs(17, 31).addBox(-1.5f, -1f, -36f, 3, 1, 11, 0f, false)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_1.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, -1.0472f)
        outer_roof_3_r3.texOffs(19, 133).addBox(-1f, -1f, -36f, 2, 1, 11, 0f, false)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_1.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, 0.1107f)
        outer_roof_2_r3.texOffs(215, 15).addBox(0f, -1f, -36f, 1, 2, 11, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(93, 79).addBox(0f, -42f, -36f, 6, 1, 11, 0f, true)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_2.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, 0.1745f)
        outer_roof_5_r4.texOffs(155, 204).addBox(-4f, -1f, -36f, 8, 1, 11, 0f, true)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_2.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, 0.5236f)
        outer_roof_4_r4.texOffs(17, 31).addBox(-1.5f, -1f, -36f, 3, 1, 11, 0f, true)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_2.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0f, 0f, 1.0472f)
        outer_roof_3_r4.texOffs(19, 133).addBox(-1f, -1f, -36f, 2, 1, 11, 0f, true)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(17.587f, -36.3849f, 0f)
        outer_roof_2.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.1107f)
        outer_roof_2_r4.texOffs(215, 15).addBox(-1f, -1f, -36f, 1, 2, 11, 0f, true)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(20f, -13f, 0f)
        outer_roof_2.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, -0.1107f)
        outer_roof_1_r2.texOffs(34, 134).addBox(0f, -23f, -29f, 1, 4, 4, 0f, true)
        outer_roof_1_r2.texOffs(181, 133).addBox(-1f, -23f, -36f, 1, 4, 7, 0f, true)

        roof_end_vents = ModelMapper(modelDataWrapper)
        roof_end_vents.setPos(0f, 24f, 0f)
        roof_end_vents.texOffs(0, 46).addBox(-8f, -43f, -21f, 16, 2, 48, 0f, false)

        vent_3_r1 = ModelMapper(modelDataWrapper)
        vent_3_r1.setPos(-8f, -43f, 12f)
        roof_end_vents.addChild(vent_3_r1)
        setRotationAngle(vent_3_r1, 0f, 0f, -0.3491f)
        vent_3_r1.texOffs(152, 119).addBox(-9f, 0f, -33f, 9, 2, 48, 0f, true)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(8f, -43f, 12f)
        roof_end_vents.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, 0.3491f)
        vent_2_r1.texOffs(152, 119).addBox(0f, 0f, -33f, 9, 2, 48, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(80, 46).addBox(-20f, 0f, -13f, 40, 1, 31, 0f, false)
        head.texOffs(259, 192).addBox(-20f, -13f, -13f, 2, 13, 31, 0f, false)
        head.texOffs(252, 148).addBox(18f, -13f, -13f, 2, 13, 31, 0f, true)
        head.texOffs(261, 15).addBox(-18f, -36f, 18f, 36, 36, 0, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(20f, -13f, 0f)
        head.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, -0.1107f)
        upper_wall_2_r5.texOffs(0, 240).addBox(-2f, -20f, -13f, 2, 20, 31, 0f, true)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(-20f, -13f, 0f)
        head.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, 0.1107f)
        upper_wall_1_r4.texOffs(179, 240).addBox(0f, -20f, -13f, 2, 20, 31, 0f, false)

        seat_9 = ModelMapper(modelDataWrapper)
        seat_9.setPos(0f, 0f, 2f)
        head.addChild(seat_9)
        seat_9.texOffs(0, 85).addBox(-18f, -6f, 8f, 12, 1, 7, 0f, true)

        seat_back_r9 = ModelMapper(modelDataWrapper)
        seat_back_r9.setPos(0f, -6f, 14f)
        seat_9.addChild(seat_back_r9)
        setRotationAngle(seat_back_r9, -0.0524f, 0f, 0f)
        seat_back_r9.texOffs(53, 138).addBox(-18f, -8f, 0f, 12, 8, 1, 0f, true)

        seat_10 = ModelMapper(modelDataWrapper)
        seat_10.setPos(0f, 0f, -2f)
        head.addChild(seat_10)
        setRotationAngle(seat_10, 0f, 3.1416f, 0f)
        seat_10.texOffs(0, 85).addBox(6f, -6f, 3f, 12, 1, 7, 0f, false)

        seat_back_r10 = ModelMapper(modelDataWrapper)
        seat_back_r10.setPos(0f, -6f, 9f)
        seat_10.addChild(seat_back_r10)
        setRotationAngle(seat_back_r10, -0.0524f, 0f, 0f)
        seat_back_r10.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_11 = ModelMapper(modelDataWrapper)
        seat_11.setPos(24f, 0f, -2f)
        head.addChild(seat_11)
        setRotationAngle(seat_11, 0f, 3.1416f, 0f)
        seat_11.texOffs(0, 85).addBox(6f, -6f, 3f, 12, 1, 7, 0f, false)

        seat_back_r11 = ModelMapper(modelDataWrapper)
        seat_back_r11.setPos(0f, -6f, 9f)
        seat_11.addChild(seat_back_r11)
        setRotationAngle(seat_back_r11, -0.0524f, 0f, 0f)
        seat_back_r11.texOffs(53, 138).addBox(6f, -8f, 0f, 12, 8, 1, 0f, false)

        seat_12 = ModelMapper(modelDataWrapper)
        seat_12.setPos(0f, 0f, 2f)
        head.addChild(seat_12)
        seat_12.texOffs(0, 85).addBox(6f, -6f, 8f, 12, 1, 7, 0f, true)

        seat_back_r12 = ModelMapper(modelDataWrapper)
        seat_back_r12.setPos(24f, -6f, 14f)
        seat_12.addChild(seat_back_r12)
        setRotationAngle(seat_back_r12, -0.0524f, 0f, 0f)
        seat_back_r12.texOffs(53, 138).addBox(-18f, -8f, 0f, 12, 8, 1, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(195, 91).addBox(-21f, 0f, 18f, 42, 2, 13, 0f, false)
        head_exterior.texOffs(18, 58).addBox(21f, 2f, 22f, 0, 5, 6, 0f, true)
        head_exterior.texOffs(18, 58).addBox(-21f, 2f, 22f, 0, 5, 6, 0f, false)
        head_exterior.texOffs(218, 116).addBox(20f, -13f, -13f, 0, 13, 32, 0f, true)
        head_exterior.texOffs(88, 271).addBox(20f, -13f, 19f, 1, 13, 12, 0f, true)
        head_exterior.texOffs(102, 170).addBox(-20f, -13f, -13f, 0, 13, 32, 0f, false)
        head_exterior.texOffs(88, 271).addBox(-21f, -13f, 19f, 1, 13, 12, 0f, false)
        head_exterior.texOffs(271, 51).addBox(20f, 0f, -13f, 1, 2, 31, 0f, false)
        head_exterior.texOffs(271, 51).addBox(-21f, 0f, -13f, 1, 2, 31, 0f, false)
        head_exterior.texOffs(218, 106).addBox(-20f, -42f, 19f, 40, 42, 0, 0f, false)

        driver_door_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_2_r1.setPos(-21f, -13f, 0f)
        head_exterior.addChild(driver_door_upper_2_r1)
        setRotationAngle(driver_door_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_upper_2_r1.texOffs(0, 234).addBox(0f, -20f, 19f, 1, 20, 12, 0f, false)

        upper_wall_2_r6 = ModelMapper(modelDataWrapper)
        upper_wall_2_r6.setPos(-20f, -13f, 0f)
        head_exterior.addChild(upper_wall_2_r6)
        setRotationAngle(upper_wall_2_r6, 0f, 0f, 0.1107f)
        upper_wall_2_r6.texOffs(102, 150).addBox(0f, -20f, -13f, 0, 20, 32, 0f, false)

        driver_door_upper_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_1_r1.setPos(21f, -13f, 0f)
        head_exterior.addChild(driver_door_upper_1_r1)
        setRotationAngle(driver_door_upper_1_r1, 0f, 0f, -0.1107f)
        driver_door_upper_1_r1.texOffs(0, 234).addBox(-1f, -20f, 19f, 1, 20, 12, 0f, true)

        upper_wall_1_r5 = ModelMapper(modelDataWrapper)
        upper_wall_1_r5.setPos(20f, -13f, 0f)
        head_exterior.addChild(upper_wall_1_r5)
        setRotationAngle(upper_wall_1_r5, 0f, 0f, -0.1107f)
        upper_wall_1_r5.texOffs(94, 199).addBox(0f, -20f, -13f, 0, 20, 32, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(214, 70).addBox(-20f, 2f, 31f, 40, 0, 8, 0f, false)

        head_roof_r1 = ModelMapper(modelDataWrapper)
        head_roof_r1.setPos(0f, -41.2432f, 30.9176f)
        front.addChild(head_roof_r1)
        setRotationAngle(head_roof_r1, -0.1309f, 0f, 0f)
        head_roof_r1.texOffs(73, 116).addBox(-6f, -0.5f, -2f, 12, 0, 4, 0f, false)

        head_top_r1 = ModelMapper(modelDataWrapper)
        head_top_r1.setPos(0f, -35.7151f, 31.9514f)
        front.addChild(head_top_r1)
        setRotationAngle(head_top_r1, 0.1745f, 0f, 0f)
        head_top_r1.texOffs(0, 215).addBox(-6f, -5.5f, -2f, 12, 11, 4, 0f, false)

        head_bottom_1_r1 = ModelMapper(modelDataWrapper)
        head_bottom_1_r1.setPos(0f, 2f, 39f)
        front.addChild(head_bottom_1_r1)
        setRotationAngle(head_bottom_1_r1, -0.3054f, 0f, 0f)
        head_bottom_1_r1.texOffs(230, 84).addBox(-20f, -4f, 0f, 40, 4, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)
        side_1.texOffs(76, 90).addBox(0f, -42f, 25f, 6, 0, 4, 0f, true)

        outer_head_4_r1 = ModelMapper(modelDataWrapper)
        outer_head_4_r1.setPos(13f, -22.275f, 36.3511f)
        side_1.addChild(outer_head_4_r1)
        setRotationAngle(outer_head_4_r1, 0.1745f, 0f, 0f)
        outer_head_4_r1.texOffs(297, 236).addBox(-7f, -19.5f, 0f, 14, 41, 0, 0f, false)

        outer_head_2_r1 = ModelMapper(modelDataWrapper)
        outer_head_2_r1.setPos(0f, 0f, 0f)
        side_1.addChild(outer_head_2_r1)
        setRotationAngle(outer_head_2_r1, 0f, -0.0873f, -0.1107f)
        outer_head_2_r1.texOffs(79, 178).addBox(24f, -35.5f, 28.75f, 0, 25, 9, 0f, false)

        outer_head_1_r1 = ModelMapper(modelDataWrapper)
        outer_head_1_r1.setPos(0f, 0f, 0f)
        side_1.addChild(outer_head_1_r1)
        setRotationAngle(outer_head_1_r1, 0f, -0.0873f, 0f)
        outer_head_1_r1.texOffs(17, 177).addBox(22.7f, -13f, 29f, 0, 15, 10, 0f, false)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(20f, -13f, 5f)
        side_1.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, -0.1107f)
        outer_roof_1_r3.texOffs(195, 106).addBox(-1f, -23f, 20f, 2, 4, 6, 0f, true)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(16.9769f, -38.2468f, 23.5f)
        side_1.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, 1.0472f)
        outer_roof_3_r5.texOffs(28, 36).addBox(-1f, 0f, 1.5f, 2, 0, 6, 0f, true)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(17.0902f, -36.332f, 23.5f)
        side_1.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, -0.1107f)
        outer_roof_2_r5.texOffs(132, 91).addBox(-0.5f, -1f, 1.5f, 1, 2, 6, 0f, true)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(15.1778f, -39.8628f, 0f)
        side_1.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, 0.5236f)
        outer_roof_4_r5.texOffs(112, 83).addBox(-1.5f, 0f, 25f, 3, 0, 4, 0f, true)

        outer_roof_11_r1 = ModelMapper(modelDataWrapper)
        outer_roof_11_r1.setPos(16.4774f, -39.1136f, 29.0001f)
        side_1.addChild(outer_roof_11_r1)
        setRotationAngle(outer_roof_11_r1, -0.2182f, -0.829f, 1.0472f)
        outer_roof_11_r1.texOffs(24, 80).addBox(0f, 0.001f, 0f, 5, 0, 4, 0f, true)

        outer_head_6_r1 = ModelMapper(modelDataWrapper)
        outer_head_6_r1.setPos(14.7995f, -39.2074f, 30.9176f)
        side_1.addChild(outer_head_6_r1)
        setRotationAngle(outer_head_6_r1, -0.1309f, 0f, 0.5236f)
        outer_head_6_r1.texOffs(111, 78).addBox(-1.5f, -0.5f, -2f, 3, 0, 5, 0f, true)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(9.9394f, -41.3064f, 0f)
        side_1.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, 0.1745f)
        outer_roof_5_r5.texOffs(121, 41).addBox(-4f, 0f, 25f, 8, 0, 4, 0f, true)

        outer_head_5_r1 = ModelMapper(modelDataWrapper)
        outer_head_5_r1.setPos(9.808f, -40.5611f, 30.9176f)
        side_1.addChild(outer_head_5_r1)
        setRotationAngle(outer_head_5_r1, -0.1309f, 0f, 0.1745f)
        outer_head_5_r1.texOffs(72, 120).addBox(-4f, -0.5f, -2f, 8, 0, 5, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        front.addChild(side_2)
        side_2.texOffs(76, 90).addBox(-6f, -42f, 25f, 6, 0, 4, 0f, false)

        outer_head_6_r2 = ModelMapper(modelDataWrapper)
        outer_head_6_r2.setPos(-14.7995f, -39.2074f, 30.9176f)
        side_2.addChild(outer_head_6_r2)
        setRotationAngle(outer_head_6_r2, -0.1309f, 0f, -0.5236f)
        outer_head_6_r2.texOffs(111, 78).addBox(-1.5f, -0.5f, -2f, 3, 0, 5, 0f, false)

        outer_head_5_r2 = ModelMapper(modelDataWrapper)
        outer_head_5_r2.setPos(-9.808f, -40.5611f, 30.9176f)
        side_2.addChild(outer_head_5_r2)
        setRotationAngle(outer_head_5_r2, -0.1309f, 0f, -0.1745f)
        outer_head_5_r2.texOffs(72, 120).addBox(-4f, -0.5f, -2f, 8, 0, 5, 0f, false)

        outer_head_4_r2 = ModelMapper(modelDataWrapper)
        outer_head_4_r2.setPos(-13f, -22.275f, 36.3511f)
        side_2.addChild(outer_head_4_r2)
        setRotationAngle(outer_head_4_r2, 0.1745f, 0f, 0f)
        outer_head_4_r2.texOffs(298, 106).addBox(-7f, -19.5f, 0f, 14, 41, 0, 0f, false)

        outer_head_2_r2 = ModelMapper(modelDataWrapper)
        outer_head_2_r2.setPos(0f, 0f, 0f)
        side_2.addChild(outer_head_2_r2)
        setRotationAngle(outer_head_2_r2, 0f, 0.0873f, 0.1107f)
        outer_head_2_r2.texOffs(79, 178).addBox(-24f, -35.5f, 28.75f, 0, 25, 9, 0f, false)

        outer_head_1_r2 = ModelMapper(modelDataWrapper)
        outer_head_1_r2.setPos(0f, 0f, 0f)
        side_2.addChild(outer_head_1_r2)
        setRotationAngle(outer_head_1_r2, 0f, 0.0873f, 0f)
        outer_head_1_r2.texOffs(17, 86).addBox(-22.7f, -13f, 29f, 0, 15, 10, 0f, false)

        outer_roof_11_r2 = ModelMapper(modelDataWrapper)
        outer_roof_11_r2.setPos(-16.4774f, -39.1136f, 29.0001f)
        side_2.addChild(outer_roof_11_r2)
        setRotationAngle(outer_roof_11_r2, -0.2182f, 0.829f, -1.0472f)
        outer_roof_11_r2.texOffs(24, 80).addBox(-5f, 0.001f, 0f, 5, 0, 4, 0f, false)

        outer_roof_5_r6 = ModelMapper(modelDataWrapper)
        outer_roof_5_r6.setPos(-9.9394f, -41.3064f, 0f)
        side_2.addChild(outer_roof_5_r6)
        setRotationAngle(outer_roof_5_r6, 0f, 0f, -0.1745f)
        outer_roof_5_r6.texOffs(121, 41).addBox(-4f, 0f, 25f, 8, 0, 4, 0f, false)

        outer_roof_4_r6 = ModelMapper(modelDataWrapper)
        outer_roof_4_r6.setPos(-15.1778f, -39.8628f, 0f)
        side_2.addChild(outer_roof_4_r6)
        setRotationAngle(outer_roof_4_r6, 0f, 0f, -0.5236f)
        outer_roof_4_r6.texOffs(112, 83).addBox(-1.5f, 0f, 25f, 3, 0, 4, 0f, false)

        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(-16.9769f, -38.2468f, 23.5f)
        side_2.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0f, 0f, -1.0472f)
        outer_roof_3_r6.texOffs(28, 36).addBox(-1f, 0f, 1.5f, 2, 0, 6, 0f, false)

        outer_roof_2_r6 = ModelMapper(modelDataWrapper)
        outer_roof_2_r6.setPos(-17.0902f, -36.332f, 23.5f)
        side_2.addChild(outer_roof_2_r6)
        setRotationAngle(outer_roof_2_r6, 0f, 0f, 0.1107f)
        outer_roof_2_r6.texOffs(132, 91).addBox(-0.5f, -1f, 1.5f, 1, 2, 6, 0f, false)

        outer_roof_1_r4 = ModelMapper(modelDataWrapper)
        outer_roof_1_r4.setPos(-20f, -13f, 5f)
        side_2.addChild(outer_roof_1_r4)
        setRotationAngle(outer_roof_1_r4, 0f, 0f, 0.1107f)
        outer_roof_1_r4.texOffs(195, 106).addBox(-1f, -23f, 20f, 2, 4, 6, 0f, false)

        emergency_door = ModelMapper(modelDataWrapper)
        emergency_door.setPos(0f, 0f, 0f)
        front.addChild(emergency_door)
        emergency_door.texOffs(294, 192).addBox(-6f, -31f, 34f, 12, 30, 0, 0f, false)
        emergency_door.texOffs(100, 293).addBox(6f, -31f, 31f, 0, 30, 10, 0f, false)
        emergency_door.texOffs(120, 293).addBox(-6f, -31f, 31f, 0, 30, 10, 0f, false)
        emergency_door.texOffs(68, 78).addBox(-6f, -1f, 30f, 12, 0, 12, 0f, false)

        pipe = ModelMapper(modelDataWrapper)
        pipe.setPos(0f, -3.05f, -0.5f)
        head_exterior.addChild(pipe)


        valve_8_r1 = ModelMapper(modelDataWrapper)
        valve_8_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_8_r1)
        setRotationAngle(valve_8_r1, 0.1745f, 0f, 0f)
        valve_8_r1.texOffs(34, 46).addBox(-10.175f, 2.125f, 39.55f, 1, 8, 1, 0f, false)
        valve_8_r1.texOffs(0, 85).addBox(-10.775f, 9.3f, 39.6f, 2, 3, 1, 0f, false)
        valve_8_r1.texOffs(32, 116).addBox(-10.775f, -0.7f, 39.6f, 2, 3, 1, 0f, false)
        valve_8_r1.texOffs(32, 120).addBox(8.675f, -0.7f, 39.6f, 2, 3, 1, 0f, false)
        valve_8_r1.texOffs(93, 120).addBox(15.325f, -0.7f, 39.6f, 2, 3, 1, 0f, false)

        valve_7_r1 = ModelMapper(modelDataWrapper)
        valve_7_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_7_r1)
        setRotationAngle(valve_7_r1, 0f, 0f, -0.1745f)
        valve_7_r1.texOffs(0, 39).addBox(8.65f, 2.375f, 40.35f, 1, 5, 1, 0f, false)

        valve_6_r1 = ModelMapper(modelDataWrapper)
        valve_6_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_6_r1)
        setRotationAngle(valve_6_r1, 0f, 0f, -1.309f)
        valve_6_r1.texOffs(38, 38).addBox(-3f, 10.975f, 40.35f, 1, 3, 1, 0f, false)

        valve_5_r1 = ModelMapper(modelDataWrapper)
        valve_5_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_5_r1)
        setRotationAngle(valve_5_r1, 0f, 0f, 1.5708f)
        valve_5_r1.texOffs(16, 39).addBox(5.375f, -13.325f, 40.3f, 1, 1, 1, 0f, false)

        valve_4_r1 = ModelMapper(modelDataWrapper)
        valve_4_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_4_r1)
        setRotationAngle(valve_4_r1, 0f, 0f, 1.309f)
        valve_4_r1.texOffs(37, 84).addBox(8.55f, -13.85f, 40.3f, 1, 3, 1, 0f, false)

        valve_3_r1 = ModelMapper(modelDataWrapper)
        valve_3_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_3_r1)
        setRotationAngle(valve_3_r1, 0f, 0f, 0.2618f)
        valve_3_r1.texOffs(34, 69).addBox(15.8f, -3.625f, 40.3f, 1, 5, 1, 0f, false)

        valve_2_r1 = ModelMapper(modelDataWrapper)
        valve_2_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_2_r1)
        setRotationAngle(valve_2_r1, 0.1745f, 0f, 0.0436f)
        valve_2_r1.texOffs(34, 55).addBox(8.95f, 1.775f, 39.65f, 1, 6, 1, 0f, false)

        valve_1_r1 = ModelMapper(modelDataWrapper)
        valve_1_r1.setPos(0f, 0f, 0f)
        pipe.addChild(valve_1_r1)
        setRotationAngle(valve_1_r1, 0.1745f, 0f, -0.0436f)
        valve_1_r1.texOffs(34, 62).addBox(16.1f, 2.8f, 39.4f, 1, 6, 1, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        outer_head_5_r3 = ModelMapper(modelDataWrapper)
        outer_head_5_r3.setPos(-13f, -22.275f, 36.4511f)
        headlights.addChild(outer_head_5_r3)
        setRotationAngle(outer_head_5_r3, 0.1745f, 0f, 0f)
        outer_head_5_r3.texOffs(18, 46).addBox(-3f, 15.5f, 0f, 6, 5, 0, 0f, true)
        outer_head_5_r3.texOffs(18, 46).addBox(23f, 15.5f, 0f, 6, 5, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_r1 = ModelMapper(modelDataWrapper)
        tail_light_r1.setPos(4f, -40.0015f, 33.2254f)
        tail_lights.addChild(tail_light_r1)
        setRotationAngle(tail_light_r1, 0.1745f, 0f, 0f)
        tail_light_r1.texOffs(18, 51).addBox(-4f, -2f, 0.1f, 6, 5, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_1_r5 = ModelMapper(modelDataWrapper)
        outer_roof_1_r5.setPos(-20f, -13f, 0f)
        door_light.addChild(outer_roof_1_r5)
        setRotationAngle(outer_roof_1_r5, 0f, 0f, 0.1107f)
        outer_roof_1_r5.texOffs(18, 52).addBox(-1.1f, -23f, -2f, 0, 4, 4, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -13f, 0f)
        door_light_off.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(19, 62).addBox(-1f, -21f, 0f, 0, 0, 0, 0.4f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -13f, 0f)
        door_light_on.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(21, 62).addBox(-1f, -21f, 0f, 0, 0, 0, 0.4f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior.setModelPart()
        window_handrails.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        door_handrails.setModelPart()
        side_panel.setModelPart()
        side_panel_translucent.setModelPart()
        roof_window.setModelPart()
        roof_door.setModelPart()
        roof_window_light.setModelPart()
        roof_door_light.setModelPart()
        roof_exterior_window.setModelPart()
        roof_exterior_door.setModelPart()
        end.setModelPart()
        end_exterior.setModelPart()
        roof_end.setModelPart()
        roof_end_light.setModelPart()
        roof_end_handrails.setModelPart()
        roof_end_exterior.setModelPart()
        roof_end_vents.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light.setModelPart()
        door_light_off.setModelPart()
        door_light_on.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelE44 {
        return ModelE44(doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderMirror(roof_window_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(window, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(window, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(window_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(window_exterior, matrices, vertices, light, position.toFloat())
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
        val middleDoor = isIndex(getDoorPositions()!!.size / 2, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(roof_door_light, matrices, vertices, light, position.toFloat())
                if (middleDoor && doorOpen) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 22).toFloat())
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
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                    renderMirror(door_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position - 19).toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position + 19).toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                door_left_exterior.setOffset(0f, 0, doorRightZ)
                door_right_exterior.setOffset(0f, 0, -doorRightZ)
                renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                door_left_exterior.setOffset(0f, 0, doorLeftZ)
                door_right_exterior.setOffset(0f, 0, -doorLeftZ)
                renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, position.toFloat())

                if (middleDoor && renderDetails) {
                    renderMirror(door_light, matrices, vertices, light, (position - 22).toFloat())
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 22).toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, (position - 19).toFloat())
                renderMirror(side_panel_translucent, matrices, vertices, light, (position + 19).toFloat())
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
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, (position - 6).toFloat())
                renderOnceFlipped(roof_end_vents, matrices, vertices, light, (position + 2).toFloat())
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
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, (position + 6).toFloat())
                renderOnce(roof_end_vents, matrices, vertices, light, (position - 2).toFloat())
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
                    renderOnce(roof_end_handrails, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, (position - 6).toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_vents, matrices, vertices, light, position.toFloat())
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
                    renderOnceFlipped(roof_end_handrails, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior_door, matrices, vertices, light, (position + 6).toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_vents, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }


    @Override
    override fun getModelDoorOverlay(): ModelDoorOverlay {
        return MODEL_DOOR_OVERLAY
    }

    @Override
    override fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase {
        return MODEL_DOOR_OVERLAY_TOP
    }

    @Override
    override fun getWindowPositions(): IntArray? {
        return intArrayOf(-94, -64, -34, 34, 64, 94)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-128, 0, 128)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-160, 160)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, 13, "door_overlay_e44_left.png", "door_overlay_e44_right.png")
        private val MODEL_DOOR_OVERLAY_TOP = ModelDoorOverlayTopMLR("mtr:textures/block/sign/door_overlay_e44_top.png")
    }
}
