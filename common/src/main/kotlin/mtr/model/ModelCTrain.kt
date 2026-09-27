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

open class ModelCTrain protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelCTrain?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val handrail_17_r1: ModelMapper
    private val handrail_16_r1: ModelMapper
    private val handrail_15_r1: ModelMapper
    private val handrail_14_r1: ModelMapper
    private val handrail_13_r1: ModelMapper
    private val handrail_12_r1: ModelMapper
    private val handrail_11_r1: ModelMapper
    private val handrail_10_r1: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val top_handrail_4_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_6_r2: ModelMapper
    private val inner_roof_5_r2: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val inner_roof_1_r1: ModelMapper
    private val roof_exterior: ModelMapper
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
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val outer_roof_1_r3: ModelMapper
    private val roof_light: ModelMapper
    private val roof_light_r1: ModelMapper
    private val roof_end_light: ModelMapper
    private val roof_light_2_r1: ModelMapper
    private val roof_light_1_r1: ModelMapper
    private val roof_head_exterior: ModelMapper
    private val vent_2_r2: ModelMapper
    private val vent_1_r2: ModelMapper
    private val outer_roof_3: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_4: ModelMapper
    private val outer_roof_7_r2: ModelMapper
    private val outer_roof_6_r2: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val front: ModelMapper
    private val side_1: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r6: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val outer_roof_2_r6: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val front_side_bottom_1_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r7: ModelMapper
    private val outer_roof_3_r7: ModelMapper
    private val outer_roof_2_r7: ModelMapper
    private val front_side_bottom_4_r1: ModelMapper
    private val front_side_bottom_3_r1: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val front_panel: ModelMapper
    private val panel_5_r1: ModelMapper
    private val panel_13_r1: ModelMapper
    private val panel_8_r1: ModelMapper
    private val panel_12_r1: ModelMapper
    private val panel_12_r2: ModelMapper
    private val panel_9_r1: ModelMapper
    private val panel_6_r1: ModelMapper
    private val panel_3_r1: ModelMapper
    private val panel_11_r1: ModelMapper
    private val panel_8_r2: ModelMapper
    private val panel_7_r1: ModelMapper
    private val panel_5_r2: ModelMapper
    private val panel_4_r1: ModelMapper
    private val panel_4_r2: ModelMapper
    private val panel_3_r2: ModelMapper
    private val panel_2_r1: ModelMapper
    private val headlights: ModelMapper
    private val headlight_3_r1: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_3_r1: ModelMapper
    private val tail_light_2_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper

    constructor() : this(DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 320
        val textureHeight = 320

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 40).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(168, 0).addBox(-20f, -14f, -22f, 2, 14, 44, 0f, false)
        window.texOffs(0, 160).addBox(-20f, -14f, 22f, 3, 14, 5, 0f, false)
        window.texOffs(22, 160).addBox(-20f, -14f, -27f, 3, 14, 5, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(187, 269).addBox(0f, -19f, -27f, 3, 19, 5, 0f, false)
        upper_wall_2_r1.texOffs(268, 44).addBox(0f, -19f, 22f, 3, 19, 5, 0f, false)
        upper_wall_2_r1.texOffs(0, 163).addBox(0f, -16f, -22f, 2, 16, 44, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(316, 17).addBox(-11.3135f, -18.3633f, -22f, 0, 5, 0, 0.2f, false)
        window_handrails.texOffs(316, 0).addBox(0f, -35f, -9f, 0, 35, 0, 0.2f, false)
        window_handrails.texOffs(316, 0).addBox(0f, -35f, 9f, 0, 35, 0, 0.2f, false)
        window_handrails.texOffs(316, 17).addBox(-11.3135f, -18.3633f, 22f, 0, 5, 0, 0.2f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, 21f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, -3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, -15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 5).addBox(-1f, -32f, -21f, 2, 4, 0, 0f, false)

        handrail_17_r1 = ModelMapper(modelDataWrapper)
        handrail_17_r1.setPos(-12.6434f, -3.3539f, 0f)
        window_handrails.addChild(handrail_17_r1)
        setRotationAngle(handrail_17_r1, 0f, 0f, 0.0873f)
        handrail_17_r1.texOffs(316, 9).addBox(0f, -19.45f, -22f, 0, 4, 0, 0.2f, false)

        handrail_16_r1 = ModelMapper(modelDataWrapper)
        handrail_16_r1.setPos(-12.6434f, -3.3539f, 0f)
        window_handrails.addChild(handrail_16_r1)
        setRotationAngle(handrail_16_r1, 0f, 0f, 0.0873f)
        handrail_16_r1.texOffs(316, 9).addBox(0f, -19.45f, 22f, 0, 4, 0, 0.2f, false)

        handrail_15_r1 = ModelMapper(modelDataWrapper)
        handrail_15_r1.setPos(-10.1335f, -27.6076f, 20.7938f)
        window_handrails.addChild(handrail_15_r1)
        setRotationAngle(handrail_15_r1, 1.0472f, 0f, 0.2618f)
        handrail_15_r1.texOffs(319, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_14_r1 = ModelMapper(modelDataWrapper)
        handrail_14_r1.setPos(-10.362f, -26.7547f, 21.6768f)
        window_handrails.addChild(handrail_14_r1)
        setRotationAngle(handrail_14_r1, 0.5236f, 0f, 0.2618f)
        handrail_14_r1.texOffs(319, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_13_r1 = ModelMapper(modelDataWrapper)
        handrail_13_r1.setPos(-10.3923f, -26.6416f, -22.5259f)
        window_handrails.addChild(handrail_13_r1)
        setRotationAngle(handrail_13_r1, -1.0472f, 0f, 0.2618f)
        handrail_13_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_12_r1 = ModelMapper(modelDataWrapper)
        handrail_12_r1.setPos(-10.362f, -26.7546f, -21.6768f)
        window_handrails.addChild(handrail_12_r1)
        setRotationAngle(handrail_12_r1, -0.5236f, 0f, 0.2618f)
        handrail_12_r1.texOffs(319, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_11_r1 = ModelMapper(modelDataWrapper)
        handrail_11_r1.setPos(-10.253f, -28.0759f, 22f)
        window_handrails.addChild(handrail_11_r1)
        setRotationAngle(handrail_11_r1, 0f, 0f, 0.1309f)
        handrail_11_r1.texOffs(316, 6).addBox(0f, 2f, -44f, 0, 3, 0, 0.2f, false)

        handrail_10_r1 = ModelMapper(modelDataWrapper)
        handrail_10_r1.setPos(-10.253f, -28.0759f, 22f)
        window_handrails.addChild(handrail_10_r1)
        setRotationAngle(handrail_10_r1, 0f, 0f, 0.1309f)
        handrail_10_r1.texOffs(316, 6).addBox(0f, 2f, 0f, 0, 3, 0, 0.2f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(319, 0).addBox(0f, -40f, -31.5f, 0, 80, 0, 0.2f, false)

        top_handrail_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_4_r1.setPos(-9.8084f, -27.8551f, 0.6584f)
        window_handrails.addChild(top_handrail_4_r1)
        setRotationAngle(top_handrail_4_r1, -1.5708f, 0f, 0.2618f)
        top_handrail_4_r1.texOffs(319, 0).addBox(-0.25f, -19.4292f, 0f, 0, 20, 0, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-9.8084f, -27.855f, -0.5168f)
        window_handrails.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.5708f, 0f, 0.2618f)
        top_handrail_3_r1.texOffs(319, 0).addBox(-0.25f, -0.4292f, 0f, 0, 20, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-9.4113f, -31.2686f, -20.0876f)
        window_handrails.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, 0.2618f)
        handrail_5_r1.texOffs(319, 0).addBox(0.25f, -3f, 6.0876f, 0, 6, 0, 0.2f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(-9.4113f, -31.2686f, 20.0876f)
        window_handrails.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0f, 0f, 0.2618f)
        handrail_4_r1.texOffs(319, 0).addBox(0.25f, -3f, -6.0876f, 0, 6, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-10.5822f, -4.8127f, 0f)
        window_handrails.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, -0.0873f)
        handrail_3_r1.texOffs(319, 0).addBox(0f, -8.2f, 22f, 0, 6, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(-10.5822f, -4.8127f, 0f)
        window_handrails.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, 0f, 0f, -0.0873f)
        handrail_1_r1.texOffs(319, 0).addBox(0f, -8.2f, -22f, 0, 6, 0, 0.2f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat)
        seat.texOffs(160, 91).addBox(-18f, -6f, -22f, 7, 1, 44, 0f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        seat.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(174, 136).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(124, 159).addBox(-21f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior.texOffs(106, 91).addBox(-20f, -14f, -26f, 1, 14, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window_exterior.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(0, 89).addBox(0f, -19f, -26f, 1, 19, 52, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(262, 188).addBox(-18f, -34f, 0f, 8, 30, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(150, 211).addBox(-18f, -34f, 0f, 8, 30, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(86, 40).addBox(-16.3335f, -29.4192f, -22f, 1, 0, 44, 0f, false)
        roof_window.texOffs(84, 40).addBox(-1f, -35f, -22f, 1, 0, 44, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(-13.9193f, -30.8334f, 0f)
        roof_window.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, -0.7854f)
        inner_roof_6_r1.texOffs(44, 40).addBox(-2f, 0f, -22f, 4, 0, 44, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-1f, -35f, 0f)
        roof_window.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -0.0873f)
        inner_roof_5_r1.texOffs(52, 40).addBox(-4f, 0f, -22f, 4, 0, 44, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-8.0496f, -34.3781f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.1309f)
        inner_roof_4_r1.texOffs(0, 40).addBox(-1f, 0f, -22f, 2, 0, 44, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-10.7731f, -33.2476f, 0f)
        roof_window.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.5236f)
        inner_roof_3_r1.texOffs(60, 40).addBox(-2f, 0f, -22f, 4, 0, 44, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(0, 89).addBox(-1f, -35f, -18f, 1, 0, 36, 0f, false)

        inner_roof_6_r2 = ModelMapper(modelDataWrapper)
        inner_roof_6_r2.setPos(-14.2729f, -30.4798f, -15.5f)
        roof_door.addChild(inner_roof_6_r2)
        setRotationAngle(inner_roof_6_r2, 0f, 1.5708f, -0.7854f)
        inner_roof_6_r2.texOffs(160, 102).addBox(-2.5f, -4f, -2.5f, 5, 4, 5, 0f, false)

        inner_roof_5_r2 = ModelMapper(modelDataWrapper)
        inner_roof_5_r2.setPos(-14.2729f, -30.4798f, 15.5f)
        roof_door.addChild(inner_roof_5_r2)
        setRotationAngle(inner_roof_5_r2, 0f, 1.5708f, -0.7854f)
        inner_roof_5_r2.texOffs(160, 102).addBox(-2.5f, -4f, -2.5f, 5, 4, 5, 0f, true)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-1f, -35f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.0873f)
        inner_roof_4_r2.texOffs(0, 40).addBox(-4f, 0f, -18f, 4, 0, 36, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-8.0496f, -34.3781f, 0f)
        roof_door.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.1309f)
        inner_roof_3_r2.texOffs(88, 0).addBox(-1f, 0f, -18f, 2, 0, 36, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-10.7731f, -33.2476f, 0f)
        roof_door.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 3.1416f, -0.5236f)
        inner_roof_2_r1.texOffs(80, 0).addBox(-2f, 0f, -18f, 4, 0, 36, 0f, true)

        inner_roof_1_r1 = ModelMapper(modelDataWrapper)
        inner_roof_1_r1.setPos(-18.5f, -32f, 41f)
        roof_door.addChild(inner_roof_1_r1)
        setRotationAngle(inner_roof_1_r1, 0f, 0f, -0.0413f)
        inner_roof_1_r1.texOffs(42, 89).addBox(0f, 0f, -54f, 6, 0, 26, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(0, 89).addBox(-5.7229f, -41.9636f, -20f, 6, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-9.662f, -41.269f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.1745f)
        outer_roof_4_r1.texOffs(72, 40).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-15.3333f, -39.5745f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -0.5236f)
        outer_roof_3_r1.texOffs(14, 89).addBox(-2f, 0f, -20f, 4, 0, 40, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.8145f, -37.2749f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, -1.0472f)
        outer_roof_2_r1.texOffs(22, 89).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(52, 207).addBox(-1f, -22f, -20f, 1, 4, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(190, 188).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(0, 170).addBox(-19.8f, -14f, 0f, 0, 14, 14, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(75, 146).addBox(1f, -19f, 0f, 0, 19, 14, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(0, 26).addBox(-19.8f, -14f, -14f, 0, 14, 14, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 75).addBox(1f, -19f, -14f, 0, 19, 14, 0f, false)

        door_handrail = ModelMapper(modelDataWrapper)
        door_handrail.setPos(0f, 24f, 0f)
        door_handrail.texOffs(316, 0).addBox(0f, -35f, 0f, 0, 35, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(222, 242).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(220, 136).addBox(-20.8f, -14f, 0f, 1, 14, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -14f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(140, 273).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(168, 211).addBox(-20.8f, -14f, -15f, 1, 14, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -14f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(283, 206).addBox(0f, -19f, -15f, 1, 19, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(168, 58).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(0, 233).addBox(9.5f, -35f, -12f, 9, 35, 18, 0f, false)
        end.texOffs(200, 221).addBox(-18.5f, -35f, -12f, 9, 35, 18, 0f, false)
        end.texOffs(54, 251).addBox(-9.5f, -35f, -12f, 19, 3, 18, 0f, false)
        end.texOffs(135, 91).addBox(-20f, -14f, 6f, 3, 14, 5, 0f, false)
        end.texOffs(108, 91).addBox(17f, -14f, 6f, 3, 14, 5, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, -0.1107f)
        upper_wall_2_r2.texOffs(94, 207).addBox(-3f, -19f, 6f, 3, 19, 5, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, 0.1107f)
        upper_wall_1_r1.texOffs(0, 223).addBox(0f, -19f, 6f, 3, 19, 5, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(0, 160).addBox(20f, 0f, -12f, 1, 4, 20, 0f, true)
        end_exterior.texOffs(0, 160).addBox(-21f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(256, 221).addBox(18f, -14f, -12f, 2, 14, 23, 0f, true)
        end_exterior.texOffs(105, 251).addBox(-20f, -14f, -12f, 2, 14, 23, 0f, false)
        end_exterior.texOffs(82, 91).addBox(9.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior.texOffs(82, 91).addBox(-18.5f, -34f, -12f, 9, 34, 0, 0f, true)
        end_exterior.texOffs(168, 79).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(108, 91).addBox(0f, -19f, -12f, 2, 19, 23, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(48, 160).addBox(-2f, -19f, -12f, 2, 19, 23, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(88, 41).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -43f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(58, 157).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -43f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(58, 157).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(252, 79).addBox(-5.7219f, -41.9631f, -12f, 6, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-9.4875f, -40.2837f, -8f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.1745f)
        outer_roof_4_r2.texOffs(36, 223).addBox(-4f, -1f, -4f, 8, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-14.3994f, -38.9579f, -8f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -0.5236f)
        outer_roof_3_r2.texOffs(124, 157).addBox(-2.5f, -1f, -4f, 4, 1, 20, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-16.6984f, -37.2079f, -8f)
        outer_roof_1.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, -1.0472f)
        outer_roof_2_r2.texOffs(260, 129).addBox(-2f, -1f, -4f, 3, 1, 20, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-20f, -14f, 0f)
        outer_roof_1.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, 0.1107f)
        outer_roof_1_r2.texOffs(0, 56).addBox(-1f, -22f, -12f, 1, 4, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(252, 79).addBox(-0.2781f, -41.9631f, -12f, 6, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(9.4875f, -40.2837f, -8f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.1745f)
        outer_roof_4_r3.texOffs(36, 223).addBox(-4f, -1f, -4f, 8, 1, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(15.2654f, -38.4579f, -8f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 0.5236f)
        outer_roof_3_r3.texOffs(124, 157).addBox(-2.5f, -1f, -4f, 4, 1, 20, 0f, true)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(16.6984f, -37.2079f, -8f)
        outer_roof_2.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, 1.0472f)
        outer_roof_2_r3.texOffs(260, 129).addBox(-1f, -1f, -4f, 3, 1, 20, 0f, true)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(20f, -14f, 0f)
        outer_roof_2.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, -0.1107f)
        outer_roof_1_r3.texOffs(0, 56).addBox(0f, -22f, -12f, 1, 4, 20, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)
        setRotationAngle(roof_light, 0f, 3.1416f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(6f, -34.2f, 0f)
        roof_light.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, 0.3927f)
        roof_light_r1.texOffs(54, 91).addBox(-1.5f, -1f, -24f, 3, 1, 48, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        roof_light_2_r1 = ModelMapper(modelDataWrapper)
        roof_light_2_r1.setPos(-6f, -34.2f, 0f)
        roof_end_light.addChild(roof_light_2_r1)
        setRotationAngle(roof_light_2_r1, 0f, 0f, -0.3927f)
        roof_light_2_r1.texOffs(220, 136).addBox(-1.5f, -1f, 6f, 3, 1, 34, 0f, true)

        roof_light_1_r1 = ModelMapper(modelDataWrapper)
        roof_light_1_r1.setPos(6f, -34.2f, 0f)
        roof_end_light.addChild(roof_light_1_r1)
        setRotationAngle(roof_light_1_r1, 0f, 0f, 0.3927f)
        roof_light_1_r1.texOffs(220, 136).addBox(-1.5f, -1f, 6f, 3, 1, 34, 0f, false)

        roof_head_exterior = ModelMapper(modelDataWrapper)
        roof_head_exterior.setPos(0f, 24f, 0f)
        roof_head_exterior.texOffs(88, 41).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r2 = ModelMapper(modelDataWrapper)
        vent_2_r2.setPos(-8f, -43f, 0f)
        roof_head_exterior.addChild(vent_2_r2)
        setRotationAngle(vent_2_r2, 0f, 0f, -0.3491f)
        vent_2_r2.texOffs(58, 157).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r2 = ModelMapper(modelDataWrapper)
        vent_1_r2.setPos(8f, -43f, 0f)
        roof_head_exterior.addChild(vent_1_r2)
        setRotationAngle(vent_1_r2, 0f, 0f, 0.3491f)
        vent_1_r2.texOffs(58, 157).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_3 = ModelMapper(modelDataWrapper)
        outer_roof_3.setPos(0f, 0f, 6f)
        roof_head_exterior.addChild(outer_roof_3)
        outer_roof_3.texOffs(0, 109).addBox(-5.7219f, -41.9631f, -18f, 6, 1, 20, 0f, false)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(-17.3162f, -35.3341f, -22.4193f)
        outer_roof_3.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, -0.0698f, 0.1107f)
        outer_roof_7_r1.texOffs(0, 310).addBox(-1f, -0.5f, -4.5f, 2, 1, 9, 0f, false)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(-20f, -14f, -6f)
        outer_roof_3.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, 0.1107f)
        outer_roof_6_r1.texOffs(83, 126).addBox(-1f, -22f, -12f, 1, 1, 6, 0f, false)
        outer_roof_6_r1.texOffs(14, 184).addBox(-1f, -22f, -6f, 1, 4, 14, 0f, false)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(-9.4875f, -40.2837f, -8f)
        outer_roof_3.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, -0.1745f)
        outer_roof_4_r4.texOffs(94, 211).addBox(-4f, -1f, -10f, 8, 1, 20, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(-14.3994f, -38.9579f, -8f)
        outer_roof_3.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0f, 0f, -0.5236f)
        outer_roof_3_r4.texOffs(54, 115).addBox(-2.5f, -1f, -10f, 4, 1, 20, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(-16.6984f, -37.2079f, -8f)
        outer_roof_3.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -1.0472f)
        outer_roof_2_r4.texOffs(124, 178).addBox(-2f, -1f, -10f, 3, 1, 20, 0f, false)

        outer_roof_4 = ModelMapper(modelDataWrapper)
        outer_roof_4.setPos(0f, 0f, 6f)
        roof_head_exterior.addChild(outer_roof_4)
        outer_roof_4.texOffs(0, 109).addBox(-0.2781f, -41.9631f, -18f, 6, 1, 20, 0f, true)

        outer_roof_7_r2 = ModelMapper(modelDataWrapper)
        outer_roof_7_r2.setPos(17.3162f, -35.3341f, -22.4193f)
        outer_roof_4.addChild(outer_roof_7_r2)
        setRotationAngle(outer_roof_7_r2, 0f, 0.0698f, -0.1107f)
        outer_roof_7_r2.texOffs(0, 310).addBox(-1f, -0.5f, -4.5f, 2, 1, 9, 0f, true)

        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(20f, -14f, -6f)
        outer_roof_4.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, -0.1107f)
        outer_roof_6_r2.texOffs(83, 126).addBox(0f, -22f, -12f, 1, 1, 6, 0f, true)
        outer_roof_6_r2.texOffs(14, 184).addBox(0f, -22f, -6f, 1, 4, 14, 0f, true)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(9.4875f, -40.2837f, -8f)
        outer_roof_4.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, 0.1745f)
        outer_roof_4_r5.texOffs(94, 211).addBox(-4f, -1f, -10f, 8, 1, 20, 0f, true)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(15.2654f, -38.4579f, -8f)
        outer_roof_4.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, 0.5236f)
        outer_roof_3_r5.texOffs(54, 115).addBox(-2.5f, -1f, -10f, 4, 1, 20, 0f, true)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(16.6984f, -37.2079f, -8f)
        outer_roof_4.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, 1.0472f)
        outer_roof_2_r5.texOffs(124, 178).addBox(-1f, -1f, -10f, 3, 1, 20, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(216, 41).addBox(-20f, 0f, 6f, 40, 1, 2, 0f, false)
        head.texOffs(108, 91).addBox(17f, -14f, 6f, 3, 14, 5, 0f, false)
        head.texOffs(135, 91).addBox(-20f, -14f, 6f, 3, 14, 5, 0f, false)
        head.texOffs(128, 0).addBox(-18.5f, -35f, 6f, 37, 35, 0, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(0, 223).addBox(0f, -19f, 6f, 3, 19, 5, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1107f)
        upper_wall_1_r3.texOffs(94, 207).addBox(-3f, -19f, 6f, 3, 19, 5, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(218, 79).addBox(19f, -14f, -21f, 1, 14, 32, 0f, false)
        head_exterior.texOffs(218, 79).addBox(-20f, -14f, -21f, 1, 14, 32, 0f, true)
        head_exterior.texOffs(0, 0).addBox(-20f, 0f, -33f, 40, 4, 36, 0f, false)
        head_exterior.texOffs(174, 157).addBox(20f, 0f, -6f, 1, 4, 15, 0f, false)
        head_exterior.texOffs(174, 157).addBox(-21f, 0f, -6f, 1, 4, 15, 0f, true)
        head_exterior.texOffs(218, 125).addBox(-20f, 0f, 3f, 40, 1, 3, 0f, false)
        head_exterior.texOffs(216, 0).addBox(-18.5f, -41f, 5f, 37, 41, 0, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(-20f, -14f, -15f)
        head_exterior.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, 0.1107f)
        upper_wall_2_r5.texOffs(134, 211).addBox(0f, -21f, -6f, 1, 21, 32, 0f, true)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(20f, -14f, 0f)
        head_exterior.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, -0.1107f)
        upper_wall_1_r4.texOffs(134, 211).addBox(-1f, -21f, -21f, 1, 21, 32, 0f, false)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)


        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)


        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(3.2219f, -40.7466f, -18.8935f)
        side_1.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0.1745f, 0f, 0f)
        outer_roof_5_r1.texOffs(8, 54).addBox(-3.5f, 0f, -7f, 6, 0, 14, 0f, false)

        outer_roof_4_r6 = ModelMapper(modelDataWrapper)
        outer_roof_4_r6.setPos(10.4347f, -39.8969f, -18.8935f)
        side_1.addChild(outer_roof_4_r6)
        setRotationAngle(outer_roof_4_r6, 0.1745f, 0f, 0.1745f)
        outer_roof_4_r6.texOffs(2, 0).addBox(-5f, 0f, -7f, 10, 0, 14, 0f, false)

        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(14.4421f, -39.0318f, -18.94f)
        side_1.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0.1309f, 0f, 0.5236f)
        outer_roof_3_r6.texOffs(14, 40).addBox(-1.5f, 0f, -7f, 4, 0, 14, 0f, false)

        outer_roof_2_r6 = ModelMapper(modelDataWrapper)
        outer_roof_2_r6.setPos(17.7118f, -37.2157f, -12.993f)
        side_1.addChild(outer_roof_2_r6)
        setRotationAngle(outer_roof_2_r6, 0.1178f, 0f, 1.0472f)
        outer_roof_2_r6.texOffs(16, 14).addBox(-1.5f, 0f, -13f, 3, 0, 14, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(23.7436f, -2.2438f, -20f)
        side_1.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, 0f, 0.2618f)
        front_side_bottom_2_r1.texOffs(0, 263).addBox(-2f, 7f, -1f, 0, 3, 26, 0f, true)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(21.153f, 0.1678f, -20.0075f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, 0.1222f, 0.2618f)
        front_side_bottom_1_r1.texOffs(0, 23).addBox(0f, 4f, -11f, 0, 3, 10, 0f, false)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(20f, -14f, -21f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0.1222f, -0.1107f)
        front_side_upper_1_r1.texOffs(0, 44).addBox(0f, -22f, -10f, 0, 22, 10, 0f, true)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(20.9925f, 0f, -21.1219f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, 0.1222f, 0f)
        front_side_lower_1_r1.texOffs(0, 98).addBox(-1f, -14f, -10f, 0, 18, 10, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        front.addChild(side_2)


        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-3.2219f, -40.7466f, -18.8935f)
        side_2.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0.1745f, 0f, 0f)
        outer_roof_5_r2.texOffs(8, 54).addBox(-2.5f, 0f, -7f, 6, 0, 14, 0f, true)

        outer_roof_4_r7 = ModelMapper(modelDataWrapper)
        outer_roof_4_r7.setPos(-10.4347f, -39.8969f, -18.8935f)
        side_2.addChild(outer_roof_4_r7)
        setRotationAngle(outer_roof_4_r7, 0.1745f, 0f, -0.1745f)
        outer_roof_4_r7.texOffs(2, 0).addBox(-5f, 0f, -7f, 10, 0, 14, 0f, true)

        outer_roof_3_r7 = ModelMapper(modelDataWrapper)
        outer_roof_3_r7.setPos(-13.0465f, -36.6147f, -9.4949f)
        side_2.addChild(outer_roof_3_r7)
        setRotationAngle(outer_roof_3_r7, 0.1309f, 0f, -0.5236f)
        outer_roof_3_r7.texOffs(14, 40).addBox(-2.5f, -4f, -16f, 4, 0, 14, 0f, true)

        outer_roof_2_r7 = ModelMapper(modelDataWrapper)
        outer_roof_2_r7.setPos(-18.4243f, -37.627f, -6.0415f)
        side_2.addChild(outer_roof_2_r7)
        setRotationAngle(outer_roof_2_r7, 0.1178f, 0f, -1.0472f)
        outer_roof_2_r7.texOffs(16, 14).addBox(-1.5f, 0f, -20f, 3, 0, 14, 0f, true)

        front_side_bottom_4_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_4_r1.setPos(-19.8799f, -3.2792f, -20f)
        side_2.addChild(front_side_bottom_4_r1)
        setRotationAngle(front_side_bottom_4_r1, 0f, 0f, -0.2618f)
        front_side_bottom_4_r1.texOffs(0, 263).addBox(-2f, 7f, -1f, 0, 3, 26, 0f, false)

        front_side_bottom_3_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_3_r1.setPos(-19.8589f, 4.9974f, -20.0073f)
        side_2.addChild(front_side_bottom_3_r1)
        setRotationAngle(front_side_bottom_3_r1, 0f, -0.1222f, -0.2618f)
        front_side_bottom_3_r1.texOffs(0, 23).addBox(0f, -1f, -11f, 0, 3, 10, 0f, true)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(-20f, -14f, -21f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, -0.1222f, 0.1107f)
        front_side_upper_2_r1.texOffs(0, 44).addBox(0f, -22f, -10f, 0, 22, 10, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(-19.0065f, 0f, -20.878f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, -0.1222f, 0f)
        front_side_lower_2_r1.texOffs(0, 98).addBox(-1f, -14f, -10f, 0, 18, 10, 0f, false)

        front_panel = ModelMapper(modelDataWrapper)
        front_panel.setPos(0f, 0f, 2.75f)
        front.addChild(front_panel)


        panel_5_r1 = ModelMapper(modelDataWrapper)
        panel_5_r1.setPos(24.1146f, -0.1257f, -35.9598f)
        front_panel.addChild(panel_5_r1)
        setRotationAngle(panel_5_r1, 0f, 0f, 0f)
        panel_5_r1.texOffs(160, 124).addBox(-24.5573f, -13.5f, 2f, 8, 10, 0, 0f, true)

        panel_13_r1 = ModelMapper(modelDataWrapper)
        panel_13_r1.setPos(24.1146f, 8.3006f, -43.2568f)
        front_panel.addChild(panel_13_r1)
        setRotationAngle(panel_13_r1, -0.2618f, 0f, 0f)
        panel_13_r1.texOffs(174, 188).addBox(-24.5573f, -50.5f, 2f, 8, 17, 0, 0f, true)

        panel_8_r1 = ModelMapper(modelDataWrapper)
        panel_8_r1.setPos(24.1146f, -0.5024f, -37.7047f)
        front_panel.addChild(panel_8_r1)
        setRotationAngle(panel_8_r1, -0.1309f, 0f, 0f)
        panel_8_r1.texOffs(16, 223).addBox(-24.5573f, -23.5f, 2f, 8, 10, 0, 0f, true)

        panel_12_r1 = ModelMapper(modelDataWrapper)
        panel_12_r1.setPos(-24.1146f, 8.3006f, -43.2568f)
        front_panel.addChild(panel_12_r1)
        setRotationAngle(panel_12_r1, -0.2618f, 0f, 0f)
        panel_12_r1.texOffs(174, 188).addBox(16.5573f, -50.5f, 2f, 8, 17, 0, 0f, false)

        panel_12_r2 = ModelMapper(modelDataWrapper)
        panel_12_r2.setPos(29.1968f, 11.1984f, -41.2713f)
        front_panel.addChild(panel_12_r2)
        setRotationAngle(panel_12_r2, -0.2618f, -0.1309f, 0f)
        panel_12_r2.texOffs(218, 86).addBox(-20.5f, -53.5f, 2f, 13, 17, 0, 0f, true)

        panel_9_r1 = ModelMapper(modelDataWrapper)
        panel_9_r1.setPos(24.4901f, 4.4548f, -36.1661f)
        front_panel.addChild(panel_9_r1)
        setRotationAngle(panel_9_r1, -0.1309f, -0.1309f, 0f)
        panel_9_r1.texOffs(120, 232).addBox(-16.5f, -28.5f, 2f, 13, 10, 0, 0f, true)

        panel_6_r1 = ModelMapper(modelDataWrapper)
        panel_6_r1.setPos(26.1601f, -0.1257f, -33.5279f)
        front_panel.addChild(panel_6_r1)
        setRotationAngle(panel_6_r1, 0f, -0.1309f, 0f)
        panel_6_r1.texOffs(256, 258).addBox(-18.5f, -13.5f, 2f, 13, 10, 0, 0f, true)

        panel_3_r1 = ModelMapper(modelDataWrapper)
        panel_3_r1.setPos(15.127f, 0.2727f, -33.998f)
        front_panel.addChild(panel_3_r1)
        setRotationAngle(panel_3_r1, 0.2618f, -0.1309f, 0f)
        panel_3_r1.texOffs(176, 91).addBox(-7.5f, -3.5f, 2f, 13, 11, 0, 0f, true)

        panel_11_r1 = ModelMapper(modelDataWrapper)
        panel_11_r1.setPos(-29.1968f, 11.1984f, -41.2713f)
        front_panel.addChild(panel_11_r1)
        setRotationAngle(panel_11_r1, -0.2618f, 0.1309f, 0f)
        panel_11_r1.texOffs(218, 86).addBox(7.5f, -53.5f, 2f, 13, 17, 0, 0f, false)

        panel_8_r2 = ModelMapper(modelDataWrapper)
        panel_8_r2.setPos(-24.4901f, 4.4548f, -36.1661f)
        front_panel.addChild(panel_8_r2)
        setRotationAngle(panel_8_r2, -0.1309f, 0.1309f, 0f)
        panel_8_r2.texOffs(120, 232).addBox(3.5f, -28.5f, 2f, 13, 10, 0, 0f, false)

        panel_7_r1 = ModelMapper(modelDataWrapper)
        panel_7_r1.setPos(-24.1146f, -0.5024f, -37.7047f)
        front_panel.addChild(panel_7_r1)
        setRotationAngle(panel_7_r1, -0.1309f, 0f, 0f)
        panel_7_r1.texOffs(16, 223).addBox(16.5573f, -23.5f, 2f, 8, 10, 0, 0f, false)

        panel_5_r2 = ModelMapper(modelDataWrapper)
        panel_5_r2.setPos(-26.1601f, -0.1257f, -33.5279f)
        front_panel.addChild(panel_5_r2)
        setRotationAngle(panel_5_r2, 0f, 0.1309f, 0f)
        panel_5_r2.texOffs(256, 258).addBox(5.5f, -13.5f, 2f, 13, 10, 0, 0f, false)

        panel_4_r1 = ModelMapper(modelDataWrapper)
        panel_4_r1.setPos(-24.1146f, -0.1257f, -35.9598f)
        front_panel.addChild(panel_4_r1)
        setRotationAngle(panel_4_r1, 0f, 0f, 0f)
        panel_4_r1.texOffs(160, 124).addBox(16.5573f, -13.5f, 2f, 8, 10, 0, 0f, false)

        panel_4_r2 = ModelMapper(modelDataWrapper)
        panel_4_r2.setPos(24.1146f, 0.2727f, -34.9857f)
        front_panel.addChild(panel_4_r2)
        setRotationAngle(panel_4_r2, 0.2618f, 0f, 0f)
        panel_4_r2.texOffs(0, 130).addBox(-24.5573f, -3.5f, 2f, 8, 11, 0, 0f, true)

        panel_3_r2 = ModelMapper(modelDataWrapper)
        panel_3_r2.setPos(-24.1146f, 0.2727f, -34.9857f)
        front_panel.addChild(panel_3_r2)
        setRotationAngle(panel_3_r2, 0.2618f, 0f, 0f)
        panel_3_r2.texOffs(0, 130).addBox(16.5573f, -3.5f, 2f, 8, 11, 0, 0f, false)

        panel_2_r1 = ModelMapper(modelDataWrapper)
        panel_2_r1.setPos(-15.127f, 0.2727f, -33.998f)
        front_panel.addChild(panel_2_r1)
        setRotationAngle(panel_2_r1, 0.2618f, 0.1309f, 0f)
        panel_2_r1.texOffs(176, 91).addBox(-5.5f, -3.5f, 2f, 13, 11, 0, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        setRotationAngle(headlights, -0.0175f, 0f, 0f)


        headlight_3_r1 = ModelMapper(modelDataWrapper)
        headlight_3_r1.setPos(9.5402f, -8.0789f, -31.1818f)
        headlights.addChild(headlight_3_r1)
        setRotationAngle(headlight_3_r1, 0f, -0.1309f, 0f)
        headlight_3_r1.texOffs(0, 5).addBox(-2f, -2f, 0f, 4, 4, 0, 0f, true)

        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(-9.5402f, -8.0789f, -31.1818f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, 0f, 0.1309f, 0f)
        headlight_2_r1.texOffs(0, 5).addBox(-2f, -2f, 0f, 4, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        setRotationAngle(tail_lights, -0.0175f, 0f, 0f)


        tail_light_3_r1 = ModelMapper(modelDataWrapper)
        tail_light_3_r1.setPos(11.0273f, -10.5789f, -30.986f)
        tail_lights.addChild(tail_light_3_r1)
        setRotationAngle(tail_light_3_r1, 0f, -0.1309f, 0f)
        tail_light_3_r1.texOffs(0, 0).addBox(-3.5f, -0.5f, 0f, 7, 5, 0, 0f, true)

        tail_light_2_r1 = ModelMapper(modelDataWrapper)
        tail_light_2_r1.setPos(-11.0273f, -10.5789f, -30.986f)
        tail_lights.addChild(tail_light_2_r1)
        setRotationAngle(tail_light_2_r1, 0f, 0.1309f, 0f)
        tail_light_2_r1.texOffs(0, 0).addBox(-3.5f, -0.5f, 0f, 7, 5, 0, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(32, 319).addBox(-1f, -19.5f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(30, 319).addBox(-1f, -19.5f, 0f, 0, 0, 0, 0.4f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrails.setModelPart()
        window_exterior.setModelPart()
        side_panel.setModelPart()
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
        roof_end_exterior.setModelPart()
        roof_light.setModelPart()
        roof_end_light.setModelPart()
        roof_head_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelCTrain {
        return ModelCTrain(doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, position - 22f)
                    renderMirror(side_panel, matrices, vertices, light, position + 22f)
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, position - 22f)
                renderMirror(side_panel_translucent, matrices, vertices, light, position + 22f)
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
                    renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && doorOpen && renderDetails) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 40).toFloat())
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
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
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
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 40).toFloat())
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

            RenderStage.INTERIOR -> renderOnce(head, matrices, vertices, light, position.toFloat())
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

            RenderStage.INTERIOR -> renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
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
            RenderStage.INTERIOR -> renderOnce(end, matrices, vertices, light, position.toFloat())
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
            RenderStage.INTERIOR -> renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
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
        renderFrontDestination(
            matrices,
            font,
            immediate,
            -0.8f,
            0f,
            getEndPositions()!![0] / 16f - 2.22f,
            0f,
            -1.9f,
            -0.01f,
            -15f,
            7.5f,
            0.4f,
            0.14f,
            -0x100,
            -0x10000,
            2f,
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, true),
            true,
            car,
            totalCars
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "回廠|Depot"
    }

    companion object {
        private const val DOOR_MAX = 13
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_c_train_left.png", "door_overlay_c_train_right.png")
    }
}
