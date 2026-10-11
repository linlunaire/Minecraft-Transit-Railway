package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelCMStock protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelCMStock?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val handrail_12_r1: ModelMapper
    private val handrail_11_r1: ModelMapper
    private val handrail_10_r1: ModelMapper
    private val handrail_9_r1: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val top_handrail_2_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_handrail: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val door_track_r1: ModelMapper
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
    private val handrail_2_r2: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val inner_roof_2: ModelMapper
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
    private val light_2_r1: ModelMapper
    private val roof_end_light: ModelMapper
    private val light_r1: ModelMapper
    private val roof_door_light: ModelMapper
    private val light_1_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val head_exterior: ModelMapper
    private val driver_door_upper_2_r1: ModelMapper
    private val driver_door_upper_1_r1: ModelMapper
    private val front: ModelMapper
    private val bottom_r1: ModelMapper
    private val front_bottom_right_r1: ModelMapper
    private val front_middle_top_r1: ModelMapper
    private val front_panel_r1: ModelMapper
    private val side_1: ModelMapper
    private val front_side_bottom_1_r1: ModelMapper
    private val front_bottom_right_r2: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_1_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val coupler: ModelMapper
    private val headlight_panel_left: ModelMapper
    private val headlight_panel_bottom_r1: ModelMapper
    private val headlight_panel_main_r1: ModelMapper
    private val headlight_panel_right: ModelMapper
    private val headlight_panel_bottom_r2: ModelMapper
    private val headlight_panel_main_r2: ModelMapper
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_1_r5: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r2: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r3: ModelMapper

    constructor() : this(DoorAnimationType.BOUNCY_1, true)

    init {
        val textureWidth = 320
        val textureHeight = 320

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 0).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(136, 109).addBox(-20f, -14f, -26f, 2, 14, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(0, 82).addBox(0f, -19f, -26f, 2, 19, 52, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(317, 0).addBox(-11f, -27.2f, -22f, 0, 22, 0, 0.2f, false)
        window_handrails.texOffs(317, 0).addBox(-11f, -27.2f, 22f, 0, 22, 0, 0.2f, false)
        window_handrails.texOffs(317, 0).addBox(-12.461f, -32.504f, 12.2f, 0, 3, 0, 0.2f, false)
        window_handrails.texOffs(317, 0).addBox(-12.461f, -32.504f, -12.2f, 0, 3, 0, 0.2f, false)
        window_handrails.texOffs(317, 0).addBox(0f, -33f, -9f, 0, 33, 0, 0.2f, false)
        window_handrails.texOffs(317, 0).addBox(0f, -33f, 9f, 0, 33, 0, 0.2f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, 21f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, -3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, -15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-13.575f, -29.725f, 8.525f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-13.575f, -29.725f, 0.475f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-13.575f, -29.725f, -8.525f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(19, 14).addBox(-1f, -32f, -21f, 2, 4, 0, 0f, false)

        handrail_12_r1 = ModelMapper(modelDataWrapper)
        handrail_12_r1.setPos(-14.9052f, -3.8479f, 22f)
        window_handrails.addChild(handrail_12_r1)
        setRotationAngle(handrail_12_r1, 0f, 0f, 1.4835f)
        handrail_12_r1.texOffs(317, 0).addBox(0f, -3f, 0f, 0, 6, 0, 0.2f, false)

        handrail_11_r1 = ModelMapper(modelDataWrapper)
        handrail_11_r1.setPos(-10.8f, -5f, 22.2f)
        window_handrails.addChild(handrail_11_r1)
        setRotationAngle(handrail_11_r1, 0f, 0f, 0.6981f)
        handrail_11_r1.texOffs(317, 0).addBox(-0.2f, 0.2f, -0.2f, 0, 1, 0, 0.2f, false)

        handrail_10_r1 = ModelMapper(modelDataWrapper)
        handrail_10_r1.setPos(-11.0106f, -4.9415f, 0f)
        window_handrails.addChild(handrail_10_r1)
        setRotationAngle(handrail_10_r1, 0f, 0f, 1.4835f)
        handrail_10_r1.texOffs(317, 0).addBox(0.75f, 0.975f, -22f, 0, 6, 0, 0.2f, false)

        handrail_9_r1 = ModelMapper(modelDataWrapper)
        handrail_9_r1.setPos(-10.8f, -5f, -22.2f)
        window_handrails.addChild(handrail_9_r1)
        setRotationAngle(handrail_9_r1, 0f, 0f, 0.6981f)
        handrail_9_r1.texOffs(317, 0).addBox(-0.2f, 0.2f, 0.2f, 0, 1, 0, 0.2f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(317, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-10.8f, -27.4f, 22.2f)
        window_handrails.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, 0f, 0f, -0.6545f)
        top_handrail_3_r1.texOffs(317, 0).addBox(-0.2f, -2.2f, -0.2f, 0, 2, 0, 0.2f, false)
        top_handrail_3_r1.texOffs(317, 0).addBox(-0.2f, -2.2f, -44.2f, 0, 2, 0, 0.2f, false)

        top_handrail_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_2_r1.setPos(-12.461f, -29.104f, 0f)
        window_handrails.addChild(top_handrail_2_r1)
        setRotationAngle(top_handrail_2_r1, -1.5708f, 0f, 0f)
        top_handrail_2_r1.texOffs(317, 0).addBox(0f, -22f, 0f, 0, 44, 0, 0.2f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat)
        seat.texOffs(0, 180).addBox(-18f, -6f, -22f, 7, 1, 44, 0f, false)
        seat.texOffs(60, 189).addBox(-18f, -5f, -21f, 6, 5, 42, 0f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        seat.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(192, 79).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(160, 175).addBox(-21f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior.texOffs(104, 123).addBox(-20f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-20f, -14f, 0f)
        window_exterior.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(0, 109).addBox(0f, -19f, -26f, 0, 19, 52, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(0, 0).addBox(-18f, -28f, 0f, 7, 24, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(68, 0).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window.texOffs(40, 0).addBox(-10f, -34f, -24f, 10, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13f, -32f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.5236f)
        inner_roof_2_r1.texOffs(60, 0).addBox(0f, 0f, -24f, 4, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(108, 111).addBox(-19f, -32f, -16f, 6, 0, 32, 0f, false)
        roof_door.texOffs(56, 7).addBox(-10f, -34f, -16f, 10, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13f, -32f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.5236f)
        inner_roof_2_r2.texOffs(0, 0).addBox(0f, 0f, -16f, 4, 0, 32, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(0, 82).addBox(-6f, -42f, -20f, 6, 0, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(82, 0).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(0, 0).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(98, 0).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 0.1107f)
        outer_roof_2_r1.texOffs(0, 113).addBox(0f, -1f, -20f, 0, 2, 40, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(116, 196).addBox(-0.075f, -22f, -20f, 1, 4, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(16, 16).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(56, 236).addBox(-20.8f, -14f, 0f, 1, 14, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(78, 272).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(0, 9).addBox(-20.8f, -14f, -15f, 1, 14, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 49).addBox(0f, -19f, -15f, 1, 19, 15, 0f, false)

        door_handrail = ModelMapper(modelDataWrapper)
        door_handrail.setPos(0f, 24f, 0f)
        door_handrail.texOffs(317, 0).addBox(0f, -34f, 0f, 0, 34, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(0f, 0f, 0f)
        door_handrail.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0f, 0f)
        handrail_2_r1.texOffs(317, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(56, 236).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        door_track_r1 = ModelMapper(modelDataWrapper)
        door_track_r1.setPos(-20f, -14f, 0f)
        door_exterior.addChild(door_track_r1)
        setRotationAngle(door_track_r1, 0f, 0f, 0.1107f)
        door_track_r1.texOffs(59, 49).addBox(-1f, -22f, -29f, 1, 4, 58, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(210, 194).addBox(-20.8f, -14f, 0f, 0, 14, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -14f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(192, 88).addBox(0f, -19f, 0f, 0, 19, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(104, 146).addBox(-20.8f, -14f, -15f, 0, 14, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -14f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(0, 68).addBox(0f, -19f, -15f, 0, 19, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(192, 131).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(56, 111).addBox(18f, -14f, 7f, 2, 14, 3, 0f, false)
        end.texOffs(30, 82).addBox(-20f, -14f, 7f, 2, 14, 3, 0f, false)
        end.texOffs(225, 156).addBox(9.5f, -34f, -12f, 9, 34, 19, 0f, false)
        end.texOffs(0, 225).addBox(-18.5f, -34f, -12f, 9, 34, 19, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(152, 111).addBox(0f, -19f, 7f, 2, 19, 3, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(185, 50).addBox(-2f, -19f, 7f, 2, 19, 3, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(36, 272).addBox(20f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(270, 213).addBox(-21f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(248, 239).addBox(18f, -14f, -12f, 2, 14, 22, 0f, false)
        end_exterior.texOffs(140, 121).addBox(-20f, -14f, -12f, 2, 14, 22, 0f, false)
        end_exterior.texOffs(0, 278).addBox(9.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior.texOffs(166, 0).addBox(-18.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior.texOffs(205, 40).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(122, 240).addBox(0f, -19f, -12f, 2, 19, 22, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(170, 240).addBox(-2f, -19f, -12f, 2, 19, 22, 0f, false)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(0f, 0f, 0f)
        roof_end.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, -1.5708f, 0f, 0f)
        handrail_2_r2.texOffs(0, 0).addBox(0f, -40f, -31.5f, 0, 16, 0, 0.2f, false)

        inner_roof_1 = ModelMapper(modelDataWrapper)
        inner_roof_1.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_1)
        inner_roof_1.texOffs(103, 50).addBox(-17f, 1f, -12f, 6, 0, 36, 0f, true)
        inner_roof_1.texOffs(67, 49).addBox(-8f, -1f, -28f, 10, 0, 52, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-11f, 1f, -16f)
        inner_roof_1.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.5236f)
        inner_roof_2_r3.texOffs(106, 0).addBox(0f, 0f, 4f, 4, 0, 36, 0f, true)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(103, 50).addBox(15f, 1f, -12f, 6, 0, 36, 0f, false)
        inner_roof_2.texOffs(67, 49).addBox(2f, -1f, -28f, 10, 0, 52, 0f, true)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(15f, 1f, -16f)
        inner_roof_2.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, 0.5236f)
        inner_roof_2_r4.texOffs(106, 0).addBox(-4f, 0f, 4f, 4, 0, 36, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(60, 111).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -43f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(119, 53).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -43f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(139, 0).addBox(0f, 0f, 0f, 9, 2, 48, 0f, false)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(56, 111).addBox(-6f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(130, 20).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(158, 194).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(261, 27).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_1.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, 0.1107f)
        outer_roof_2_r2.texOffs(274, 237).addBox(0f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-20f, -14f, 0f)
        outer_roof_1.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, 0.1107f)
        outer_roof_1_r2.texOffs(261, 189).addBox(-0.075f, -22f, -12f, 1, 4, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(0, 102).addBox(0f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.1745f)
        outer_roof_5_r3.texOffs(56, 82).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.5236f)
        outer_roof_4_r3.texOffs(185, 79).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, false)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.0472f)
        outer_roof_3_r3.texOffs(148, 240).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, false)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(17.587f, -36.3849f, 0f)
        outer_roof_2.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -0.1107f)
        outer_roof_2_r3.texOffs(262, 152).addBox(-1f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(20f, -14f, 0f)
        outer_roof_2.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, -0.1107f)
        outer_roof_1_r3.texOffs(90, 236).addBox(-0.925f, -22f, -12f, 1, 4, 20, 0f, false)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(0f, 0f, 0f)
        roof_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, 0f, -1.5708f, 0f)
        light_2_r1.texOffs(2, 0).addBox(-14.875f, -33.975f, -10f, 2, 0, 20, 0f, false)
        light_2_r1.texOffs(2, 0).addBox(12.125f, -33.975f, -10f, 2, 0, 20, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(0f, 0f, 0f)
        roof_end_light.addChild(light_r1)
        setRotationAngle(light_r1, 0f, -1.5708f, 0f)
        light_r1.texOffs(0, 0).addBox(24f, -33.975f, -13.975f, 2, 0, 24, 0f, false)

        roof_door_light = ModelMapper(modelDataWrapper)
        roof_door_light.setPos(0f, 24f, 0f)


        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(0f, 0f, 0f)
        roof_door_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, -1.5708f, 0f)
        light_1_r1.texOffs(2, 0).addBox(-2f, -33.975f, -10f, 2, 0, 20, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(56, 82).addBox(18f, -14f, 4f, 2, 14, 6, 0f, true)
        head.texOffs(56, 82).addBox(-20f, -14f, 4f, 2, 14, 6, 0f, false)
        head.texOffs(238, 79).addBox(-18f, -34f, 4f, 36, 34, 0, 0f, false)
        head.texOffs(126, 303).addBox(-20f, 0f, 4f, 40, 1, 4, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(151, 50).addBox(0f, -19f, 4f, 2, 19, 6, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1107f)
        upper_wall_1_r3.texOffs(151, 50).addBox(-2f, -19f, 4f, 2, 19, 6, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(185, 50).addBox(-21f, 0f, -18f, 42, 7, 22, 0f, false)
        head_exterior.texOffs(66, 111).addBox(20f, 0f, 4f, 1, 7, 4, 0f, true)
        head_exterior.texOffs(66, 111).addBox(-21f, 0f, 4f, 1, 7, 4, 0f, false)
        head_exterior.texOffs(114, 194).addBox(18f, -14f, -9f, 2, 14, 19, 0f, true)
        head_exterior.texOffs(114, 194).addBox(-20f, -14f, -9f, 2, 14, 19, 0f, false)
        head_exterior.texOffs(142, 77).addBox(18f, -14f, -18f, 1, 14, 9, 0f, false)
        head_exterior.texOffs(142, 77).addBox(-19f, -14f, -18f, 1, 14, 9, 0f, true)
        head_exterior.texOffs(198, 227).addBox(-18f, -34f, 3f, 36, 34, 0, 0f, false)

        driver_door_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_2_r1.setPos(-20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_2_r1)
        setRotationAngle(driver_door_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_upper_2_r1.texOffs(110, 272).addBox(1f, -19f, -18f, 1, 19, 9, 0f, true)
        driver_door_upper_2_r1.texOffs(0, 180).addBox(0f, -19f, -9f, 2, 19, 19, 0f, false)

        driver_door_upper_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_1_r1.setPos(20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_1_r1)
        setRotationAngle(driver_door_upper_1_r1, 0f, 0f, -0.1107f)
        driver_door_upper_1_r1.texOffs(110, 272).addBox(-2f, -19f, -18f, 1, 19, 9, 0f, false)
        driver_door_upper_1_r1.texOffs(0, 180).addBox(-2f, -19f, -9f, 2, 19, 19, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)


        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(0f, 5.4768f, 4.6132f)
        front.addChild(bottom_r1)
        setRotationAngle(bottom_r1, -0.0436f, 0f, 0f)
        bottom_r1.texOffs(0, 49).addBox(-21f, 1.525f, -31.55f, 42, 0, 33, 0f, false)

        front_bottom_right_r1 = ModelMapper(modelDataWrapper)
        front_bottom_right_r1.setPos(0f, 0f, 0f)
        front.addChild(front_bottom_right_r1)
        setRotationAngle(front_bottom_right_r1, 0.3491f, 0f, 0f)
        front_bottom_right_r1.texOffs(92, 93).addBox(7f, -9.625f, -26.3f, 12, 7, 0, 0f, false)

        front_middle_top_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_r1.setPos(0f, -42f, -12f)
        front.addChild(front_middle_top_r1)
        setRotationAngle(front_middle_top_r1, 0.3491f, 0f, 0f)
        front_middle_top_r1.texOffs(0, 123).addBox(-6f, 0f, -10f, 12, 0, 10, 0f, false)

        front_panel_r1 = ModelMapper(modelDataWrapper)
        front_panel_r1.setPos(0f, 0f, -28f)
        front.addChild(front_panel_r1)
        setRotationAngle(front_panel_r1, -0.1745f, 0f, 0f)
        front_panel_r1.texOffs(205, 0).addBox(-19f, -40f, 0f, 38, 40, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)
        side_1.texOffs(19, 0).addBox(19f, -14f, -18f, 1, 14, 0, 0f, false)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(21f, 0f, -13f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, 0.1745f, 0.1745f)
        front_side_bottom_1_r1.texOffs(0, 17).addBox(0f, 0f, -16f, 0, 7, 23, 0f, false)

        front_bottom_right_r2 = ModelMapper(modelDataWrapper)
        front_bottom_right_r2.setPos(0f, 0f, 0f)
        side_1.addChild(front_bottom_right_r2)
        setRotationAngle(front_bottom_right_r2, 0.3491f, 0f, 0f)
        front_bottom_right_r2.texOffs(92, 93).addBox(-19f, -9.625f, -26.3f, 12, 7, 0, 0f, true)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(6f, -42f, -12f)
        side_1.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0.3491f, 0f, 0.1745f)
        outer_roof_4_r4.texOffs(81, 82).addBox(0f, 0f, -11f, 11, 0, 11, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(17.587f, -36.3849f, 0f)
        side_1.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.1107f)
        outer_roof_2_r4.texOffs(0, 32).addBox(0f, -1f, -18f, 0, 2, 6, 0f, false)

        outer_roof_1_r4 = ModelMapper(modelDataWrapper)
        outer_roof_1_r4.setPos(20f, -14f, 0f)
        side_1.addChild(outer_roof_1_r4)
        setRotationAngle(outer_roof_1_r4, 0f, 0f, -0.1107f)
        outer_roof_1_r4.texOffs(37, 225).addBox(0f, -22f, -18f, 1, 4, 14, 0f, true)
        outer_roof_1_r4.texOffs(17, 0).addBox(-1f, -19f, -18f, 1, 19, 0, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(15.813f, -37.5414f, -17.4163f)
        side_1.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0.1745f, 0f, 0.7418f)
        outer_roof_3_r4.texOffs(6, 49).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, false)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(20f, 0f, -18f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, 0.1745f, 0f)
        front_side_lower_1_r1.texOffs(184, 183).addBox(0f, -14f, -11f, 0, 20, 11, 0f, false)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(20f, -14f, -18f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0.1745f, -0.1107f)
        front_side_upper_1_r1.texOffs(166, 100).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(-21f, 0f, -9f)
        front.addChild(side_2)
        side_2.texOffs(17, 0).addBox(1f, -14f, -9f, 1, 14, 0, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(0f, 0f, -4f)
        side_2.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, -0.1745f, -0.1745f)
        front_side_bottom_2_r1.texOffs(0, 17).addBox(0f, 0f, -16f, 0, 7, 23, 0f, false)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(5.187f, -37.5414f, -8.4163f)
        side_2.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0.1745f, 0f, -0.7418f)
        outer_roof_4_r5.texOffs(6, 49).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, true)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(3.413f, -36.3849f, 9f)
        side_2.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, 0.1107f)
        outer_roof_7_r1.texOffs(0, 32).addBox(0f, -1f, -18f, 0, 2, 6, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(15f, -42f, -3f)
        side_2.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0.3491f, 0f, -0.1745f)
        outer_roof_5_r4.texOffs(81, 82).addBox(-11f, 0f, -11f, 11, 0, 11, 0f, true)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(1f, -14f, 9f)
        side_2.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, 0.1107f)
        outer_roof_5_r5.texOffs(37, 225).addBox(-1f, -22f, -18f, 1, 4, 14, 0f, false)
        outer_roof_5_r5.texOffs(17, 0).addBox(0f, -19f, -18f, 1, 19, 0, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(1f, -14f, -9f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, -0.1745f, 0.1107f)
        front_side_upper_2_r1.texOffs(166, 100).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(1f, 0f, -9f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, -0.1745f, 0f)
        front_side_lower_2_r1.texOffs(184, 183).addBox(0f, -14f, -11f, 0, 20, 11, 0f, false)

        coupler = ModelMapper(modelDataWrapper)
        coupler.setPos(0f, 0f, 0f)
        front.addChild(coupler)
        coupler.texOffs(260, 275).addBox(-4f, 0.55f, -29f, 8, 6, 11, 0f, false)

        headlight_panel_left = ModelMapper(modelDataWrapper)
        headlight_panel_left.setPos(0f, 0f, 0f)
        head_exterior.addChild(headlight_panel_left)


        headlight_panel_bottom_r1 = ModelMapper(modelDataWrapper)
        headlight_panel_bottom_r1.setPos(0f, 0f, 0f)
        headlight_panel_left.addChild(headlight_panel_bottom_r1)
        setRotationAngle(headlight_panel_bottom_r1, -1.3963f, 0f, 0f)
        headlight_panel_bottom_r1.texOffs(122, 41).addBox(-17f, 21.475f, 1.1f, 10, 6, 1, 0f, false)

        headlight_panel_main_r1 = ModelMapper(modelDataWrapper)
        headlight_panel_main_r1.setPos(-12f, 3.849f, -27.2491f)
        headlight_panel_left.addChild(headlight_panel_main_r1)
        setRotationAngle(headlight_panel_main_r1, 0.3491f, 0f, 0f)
        headlight_panel_main_r1.texOffs(144, 41).addBox(-5f, -3f, -0.5f, 10, 6, 1, 0f, false)

        headlight_panel_right = ModelMapper(modelDataWrapper)
        headlight_panel_right.setPos(0f, 0f, 0f)
        head_exterior.addChild(headlight_panel_right)


        headlight_panel_bottom_r2 = ModelMapper(modelDataWrapper)
        headlight_panel_bottom_r2.setPos(0f, 0f, 0f)
        headlight_panel_right.addChild(headlight_panel_bottom_r2)
        setRotationAngle(headlight_panel_bottom_r2, -1.3963f, 0f, 0f)
        headlight_panel_bottom_r2.texOffs(122, 41).addBox(7f, 21.475f, 1.1f, 10, 6, 1, 0f, true)

        headlight_panel_main_r2 = ModelMapper(modelDataWrapper)
        headlight_panel_main_r2.setPos(12f, 3.849f, -27.2491f)
        headlight_panel_right.addChild(headlight_panel_main_r2)
        setRotationAngle(headlight_panel_main_r2, 0.3491f, 0f, 0f)
        headlight_panel_main_r2.texOffs(144, 41).addBox(-5f, -3f, -0.5f, 10, 6, 1, 0f, true)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        setRotationAngle(headlights, 0.3491f, 0f, 0f)
        headlights.texOffs(17, 19).addBox(8.5f, -7.2029f, -27.5223f, 2, 2, 0, 0f, true)
        headlights.texOffs(17, 19).addBox(-10.5f, -7.2029f, -27.5223f, 2, 2, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        setRotationAngle(tail_lights, 0.3491f, 0f, 0f)
        tail_lights.texOffs(32, 36).addBox(11.75f, -7.1029f, -27.4723f, 4, 2, 0, 0f, true)
        tail_lights.texOffs(32, 36).addBox(-15.775f, -7.0029f, -27.4723f, 4, 2, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_1_r5 = ModelMapper(modelDataWrapper)
        outer_roof_1_r5.setPos(-20f, -14f, 0f)
        door_light.addChild(outer_roof_1_r5)
        setRotationAngle(outer_roof_1_r5, 0f, 0f, 0.1107f)
        outer_roof_1_r5.texOffs(32, 28).addBox(-1.1f, -22f, -2f, 0, 4, 4, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(0, 50).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r3 = ModelMapper(modelDataWrapper)
        light_r3.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r3)
        setRotationAngle(light_r3, 0f, 0f, 0.1107f)
        light_r3.texOffs(0, 52).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrails.setModelPart()
        window_exterior.setModelPart()
        side_panel_translucent.setModelPart()
        roof_window.setModelPart()
        roof_door.setModelPart()
        roof_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_handrail.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        end.setModelPart()
        end_exterior.setModelPart()
        roof_end.setModelPart()
        roof_end_exterior.setModelPart()
        roof_light.setModelPart()
        roof_end_light.setModelPart()
        roof_door_light.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelCMStock {
        return ModelCMStock(doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderOnce(roof_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, (position - 22).toFloat())
                renderMirror(side_panel_translucent, matrices, vertices, light, (position + 22).toFloat())
            }

            RenderStage.EXTERIOR -> {
                renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
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
                    renderOnce(roof_door_light, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && doorOpen) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 23).toFloat())
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
                    renderOnce(door_handrail, matrices, vertices, light, position.toFloat())
                    if (notLastDoor) {
                        renderMirror(roof_door, matrices, vertices, light, position.toFloat())
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
                    renderMirror(door_light, matrices, vertices, light, (position - 23).toFloat())
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 23).toFloat())
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
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
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
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
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
        return intArrayOf(-120, -40, 40, 120)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-160, -80, 0, 80, 160)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-184, 184)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_cm_stock_left.png", "door_overlay_cm_stock_right.png")
    }
}
