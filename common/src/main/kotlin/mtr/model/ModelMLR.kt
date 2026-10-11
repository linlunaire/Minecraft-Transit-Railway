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

open class ModelMLR protected constructor(
    @JvmField protected var isChristmas: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelMLR?>(doorAnimationType, renderDoorOverlay) {
    private val window_1: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_2: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_handrails: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val seats: ModelMapper
    private val seat_back_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val window_1_tv: ModelMapper
    private val tv_r1: ModelMapper
    private val window_exterior_1: ModelMapper
    private val upper_wall_r3: ModelMapper
    private val window_exterior_2: ModelMapper
    private val upper_wall_r4: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val roof_light: ModelMapper
    private val light_r1: ModelMapper
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
    private val door_handrails: ModelMapper
    private val handrail_8_r2: ModelMapper
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val seat_1: ModelMapper
    private val seat_back_r2: ModelMapper
    private val seat_2: ModelMapper
    private val seat_back_r3: ModelMapper
    private val seat_3: ModelMapper
    private val seat_back_r4: ModelMapper
    private val seat_4: ModelMapper
    private val seat_back_r5: ModelMapper
    private val seat_5: ModelMapper
    private val seat_back_r6: ModelMapper
    private val seat_6: ModelMapper
    private val seat_back_r7: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val handrail_9_r1: ModelMapper
    private val handrail_8_r3: ModelMapper
    private val inner_roof_right_4_r1: ModelMapper
    private val inner_roof_right_5_r1: ModelMapper
    private val inner_roof_right_7_r1: ModelMapper
    private val inner_roof_left_7_r1: ModelMapper
    private val inner_roof_left_5_r1: ModelMapper
    private val inner_roof_left_4_r1: ModelMapper
    private val roof_end_light: ModelMapper
    private val light_2_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val outer_roof_1: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val roof_end_vents: ModelMapper
    private val vent_3_r1: ModelMapper
    private val vent_2_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r5: ModelMapper
    private val head_exterior: ModelMapper
    private val driver_door_upper_2_r1: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val driver_door_upper_1_r1: ModelMapper
    private val upper_wall_1_r6: ModelMapper
    private val front: ModelMapper
    private val front_middle_top_2_r1: ModelMapper
    private val front_middle_top_1_r1: ModelMapper
    private val front_3_r1: ModelMapper
    private val front_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val outer_roof_11_r1: ModelMapper
    private val outer_roof_10_r1: ModelMapper
    private val outer_roof_9_r1: ModelMapper
    private val outer_roof_8_r1: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val front_side_lower_5_r1: ModelMapper
    private val front_side_lower_4_r1: ModelMapper
    private val front_side_lower_3_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val front_side_upper_7_r1: ModelMapper
    private val front_side_upper_6_r1: ModelMapper
    private val front_side_upper_5_r1: ModelMapper
    private val front_side_upper_4_r1: ModelMapper
    private val front_side_upper_3_r1: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val outer_roof_11_r2: ModelMapper
    private val outer_roof_10_r2: ModelMapper
    private val outer_roof_9_r2: ModelMapper
    private val outer_roof_8_r2: ModelMapper
    private val outer_roof_7_r2: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val outer_roof_1_r3: ModelMapper
    private val front_side_lower_6_r1: ModelMapper
    private val front_side_lower_5_r2: ModelMapper
    private val front_side_lower_4_r2: ModelMapper
    private val front_side_lower_3_r2: ModelMapper
    private val front_side_lower_2_r2: ModelMapper
    private val front_side_upper_8_r1: ModelMapper
    private val front_side_upper_7_r2: ModelMapper
    private val front_side_upper_6_r2: ModelMapper
    private val front_side_upper_5_r2: ModelMapper
    private val front_side_upper_4_r2: ModelMapper
    private val front_side_upper_3_r2: ModelMapper
    private val front_side_upper_2_r2: ModelMapper
    private val headlights: ModelMapper
    private val headlight_4_r1: ModelMapper
    private val headlight_3_r1: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val headlight_1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_2_r1: ModelMapper
    private val tail_light_1_r1: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_1_r4: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r2: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r3: ModelMapper
    private val christmas_tree: ModelMapper
    private val present_6_r1: ModelMapper
    private val present_5_r1: ModelMapper
    private val present_2_r1: ModelMapper
    private val christmas_antler: ModelMapper
    private val antler_2_r1: ModelMapper
    private val antler_base_r1: ModelMapper
    private val christmas_light_head: ModelMapper
    private val christmas_light_holder: ModelMapper
    private val c_light_pole_5_r1: ModelMapper
    private val c_light_pole_4_r1: ModelMapper
    private val c_light_pole_3_r1: ModelMapper
    private val c_light_pole_2_r1: ModelMapper
    private val c_light_pole_1_r1: ModelMapper
    private val christmas_light_red: ModelMapper
    private val c_light_9_r1: ModelMapper
    private val c_light_5_r1: ModelMapper
    private val c_light_1_r1: ModelMapper
    private val christmas_light_yellow: ModelMapper
    private val c_light_10_r1: ModelMapper
    private val c_light_6_r1: ModelMapper
    private val c_light_2_r1: ModelMapper
    private val christmas_light_green: ModelMapper
    private val c_light_11_r1: ModelMapper
    private val c_light_7_r1: ModelMapper
    private val c_light_3_r1: ModelMapper
    private val christmas_light_blue: ModelMapper
    private val c_light_12_r1: ModelMapper
    private val c_light_8_r1: ModelMapper
    private val c_light_4_r1: ModelMapper
    private val christmas_light_tree_red: ModelMapper
    private val ct_light_13_r1: ModelMapper
    private val christmas_light_tree_yellow: ModelMapper
    private val ct_light_20_r1: ModelMapper
    private val christmas_light_tree_green: ModelMapper
    private val ct_light_36_r1: ModelMapper
    private val christmas_light_tree_blue: ModelMapper
    private val ct_light_48_r1: ModelMapper

    constructor(isChristmas: Boolean) : this(isChristmas, DoorAnimationType.MLR, true)

    init {
        val textureWidth = 400
        val textureHeight = 400

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window_1 = ModelMapper(modelDataWrapper)
        window_1.setPos(0f, 24f, 0f)
        window_1.texOffs(232, 102).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)
        window_1.texOffs(300, 99).addBox(-20f, -13f, -18f, 2, 13, 36, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -13f, 0f)
        window_1.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(76, 267).addBox(0f, -20f, -18f, 2, 20, 36, 0f, false)

        window_2 = ModelMapper(modelDataWrapper)
        window_2.setPos(0f, 24f, 0f)
        window_2.texOffs(184, 219).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)
        window_2.texOffs(292, 191).addBox(-20f, -13f, -18f, 2, 13, 36, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-20f, -13f, 0f)
        window_2.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(201, 252).addBox(0f, -20f, -18f, 2, 20, 36, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(8, 1).addBox(0f, -33f, 0f, 0, 33, 0, 0.2f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, 9f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, -3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, -9f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 69).addBox(-1f, -32f, -15f, 2, 4, 0, 0f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        seats = ModelMapper(modelDataWrapper)
        seats.setPos(0f, 24f, 0f)
        seats.texOffs(156, 298).addBox(-18f, -6f, -15.5f, 7, 1, 31, 0f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        seats.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(328, 325).addBox(-1f, -8f, -15.5f, 1, 8, 31, 0f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-11f, -5f, 0f)
        seats.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, -0.0436f)
        handrail_3_r1.texOffs(4, 16).addBox(0f, -13.2f, 15f, 0, 13, 0, 0.2f, false)
        handrail_3_r1.texOffs(4, 16).addBox(0f, -13.2f, -15f, 0, 13, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(-12.2986f, -26.5473f, -14f)
        seats.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, -0.0873f)
        handrail_2_r1.texOffs(4, 0).addBox(0f, -8f, -1f, 0, 16, 0, 0.2f, false)
        handrail_2_r1.texOffs(4, 0).addBox(0f, -8f, 29f, 0, 16, 0, 0.2f, false)

        window_1_tv = ModelMapper(modelDataWrapper)
        window_1_tv.setPos(0f, 24f, 0f)
        window_1_tv.texOffs(232, 135).addBox(-17f, -32.5f, -5.5f, 2, 4, 12, 0f, false)

        tv_r1 = ModelMapper(modelDataWrapper)
        tv_r1.setPos(-15f, -28.5f, 0f)
        window_1_tv.addChild(tv_r1)
        setRotationAngle(tv_r1, 0f, 0f, 0.5672f)
        tv_r1.texOffs(20, 155).addBox(-2f, -8f, -5.5f, 2, 8, 12, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)
        window_exterior_1.texOffs(234, 326).addBox(-21f, 0f, -16f, 1, 7, 32, 0f, false)
        window_exterior_1.texOffs(40, 244).addBox(-20f, -13f, -18f, 0, 13, 36, 0f, false)

        upper_wall_r3 = ModelMapper(modelDataWrapper)
        upper_wall_r3.setPos(-20f, -13f, 0f)
        window_exterior_1.addChild(upper_wall_r3)
        setRotationAngle(upper_wall_r3, 0f, 0f, 0.1107f)
        upper_wall_r3.texOffs(164, 216).addBox(0f, -20f, -18f, 0, 20, 36, 0f, false)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)
        window_exterior_2.texOffs(200, 308).addBox(-21f, 0f, -16f, 1, 7, 32, 0f, false)
        window_exterior_2.texOffs(40, 231).addBox(-20f, -13f, -18f, 0, 13, 36, 0f, false)

        upper_wall_r4 = ModelMapper(modelDataWrapper)
        upper_wall_r4.setPos(-20f, -13f, 0f)
        window_exterior_2.addChild(upper_wall_r4)
        setRotationAngle(upper_wall_r4, 0f, 0f, 0.1107f)
        upper_wall_r4.texOffs(68, 13).addBox(0f, -20f, -18f, 0, 20, 36, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(206, 123).addBox(-18f, -34f, 0f, 7, 31, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(304, 103).addBox(-18f, -34f, 0f, 7, 31, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(4, 123).addBox(-16f, -32f, -16f, 2, 0, 32, 0f, false)
        roof_window.texOffs(84, 69).addBox(-10.2292f, -34.8796f, -16f, 5, 0, 32, 0f, false)
        roof_window.texOffs(122, 0).addBox(-2f, -33f, -16f, 2, 0, 32, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-2f, -33f, 0f)
        roof_window.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, 0.5236f)
        inner_roof_5_r1.texOffs(96, 0).addBox(-4f, 0f, -16f, 4, 0, 32, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-11.6147f, -34.3057f, 0f)
        roof_window.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.3927f)
        inner_roof_3_r1.texOffs(104, 0).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-14f, -32f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -1.0472f)
        inner_roof_2_r1.texOffs(0, 123).addBox(0f, 0f, -16f, 2, 0, 32, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(94, 69).addBox(-18f, -32f, -16f, 4, 0, 32, 0f, false)
        roof_door.texOffs(64, 69).addBox(-10.2292f, -34.8796f, -16f, 5, 0, 32, 0f, false)
        roof_door.texOffs(114, 0).addBox(-2f, -33f, -16f, 2, 0, 32, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(-2f, -33f, 0f)
        roof_door.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, 0.5236f)
        inner_roof_6_r1.texOffs(0, 49).addBox(-4f, 0f, -16f, 4, 0, 32, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-11.6147f, -34.3057f, 0f)
        roof_door.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.3927f)
        inner_roof_4_r1.texOffs(102, 69).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-14f, -32f, 0f)
        roof_door.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -1.0472f)
        inner_roof_3_r2.texOffs(118, 0).addBox(0f, 0f, -16f, 2, 0, 32, 0f, false)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-2f, -33f, 0f)
        roof_light.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.5236f)
        light_r1.texOffs(108, 49).addBox(-4f, -0.1f, -16f, 4, 0, 32, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(52, 69).addBox(-6f, -42f, -16f, 6, 0, 32, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(36, 69).addBox(-4f, 0f, -16f, 8, 0, 32, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(74, 69).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(110, 0).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 0.1107f)
        outer_roof_2_r1.texOffs(66, 178).addBox(0f, -1f, -16f, 0, 2, 32, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -13f, 0f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(160, 330).addBox(-1f, -23f, -16f, 1, 4, 32, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(232, 35).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(232, 35).addBox(-20.8f, -13f, 0f, 1, 13, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -13f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(194, 35).addBox(0f, -20f, 0f, 1, 20, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(174, 123).addBox(-20.8f, -13f, -15f, 1, 13, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -13f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 123).addBox(0f, -20f, -15f, 1, 20, 15, 0f, false)

        door_handrails = ModelMapper(modelDataWrapper)
        door_handrails.setPos(0f, 24f, 0f)
        door_handrails.texOffs(8, 0).addBox(0f, -33f, 0f, 0, 33, 0, 0.2f, false)

        handrail_8_r2 = ModelMapper(modelDataWrapper)
        handrail_8_r2.setPos(0f, 0f, 0f)
        door_handrails.addChild(handrail_8_r2)
        setRotationAngle(handrail_8_r2, -1.5708f, 0f, 0f)
        handrail_8_r2.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(72, 323).addBox(-21f, 0f, -16f, 1, 7, 32, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(128, 20).addBox(-20.8f, -13f, 0f, 0, 13, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -13f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(82, 128).addBox(0f, -20f, 0f, 0, 20, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(0, 75).addBox(-20.8f, -13f, -15f, 0, 13, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -13f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(0, 34).addBox(0f, -20f, -15f, 0, 20, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(0, 0).addBox(-20f, 0f, -32f, 40, 1, 48, 0f, false)
        end.texOffs(174, 102).addBox(18f, -13f, -36f, 2, 13, 54, 0f, true)
        end.texOffs(174, 35).addBox(-20f, -13f, -36f, 2, 13, 54, 0f, false)
        end.texOffs(0, 197).addBox(6f, -32f, -36f, 12, 32, 12, 0f, false)
        end.texOffs(66, 212).addBox(-18f, -32f, -36f, 12, 32, 12, 0f, false)
        end.texOffs(242, 14).addBox(-18f, -35f, -36f, 36, 3, 12, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -13f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(116, 49).addBox(0f, -20f, -36f, 2, 20, 54, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -13f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(0, 123).addBox(-2f, -20f, -36f, 2, 20, 54, 0f, true)

        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 0f, 0f)
        end.addChild(seat_1)
        seat_1.texOffs(116, 165).addBox(6f, -6f, -23f, 12, 1, 7, 0f, false)

        seat_back_r2 = ModelMapper(modelDataWrapper)
        seat_back_r2.setPos(0f, -6f, -22f)
        seat_1.addChild(seat_back_r2)
        setRotationAngle(seat_back_r2, 0.0524f, 0f, 0f)
        seat_back_r2.texOffs(0, 241).addBox(6f, -8f, -1f, 12, 8, 1, 0f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 0f, 0f)
        end.addChild(seat_2)
        seat_2.texOffs(58, 164).addBox(6f, -6f, -11f, 12, 1, 7, 0f, false)

        seat_back_r3 = ModelMapper(modelDataWrapper)
        seat_back_r3.setPos(0f, -6f, -10f)
        seat_2.addChild(seat_back_r3)
        setRotationAngle(seat_back_r3, 0.0524f, 0f, 0f)
        seat_back_r3.texOffs(172, 219).addBox(6f, -8f, -1f, 12, 8, 1, 0f, false)

        seat_3 = ModelMapper(modelDataWrapper)
        seat_3.setPos(0f, 0f, 0f)
        end.addChild(seat_3)
        seat_3.texOffs(174, 169).addBox(11f, -6f, 6f, 7, 1, 7, 0f, false)

        seat_back_r4 = ModelMapper(modelDataWrapper)
        seat_back_r4.setPos(0f, -6f, 12f)
        seat_3.addChild(seat_back_r4)
        setRotationAngle(seat_back_r4, -0.0524f, 0f, 0f)
        seat_back_r4.texOffs(0, 80).addBox(11f, -8f, 0f, 7, 8, 1, 0f, false)

        seat_4 = ModelMapper(modelDataWrapper)
        seat_4.setPos(0f, 0f, 0f)
        end.addChild(seat_4)
        seat_4.texOffs(116, 165).addBox(-18f, -6f, -23f, 12, 1, 7, 0f, true)

        seat_back_r5 = ModelMapper(modelDataWrapper)
        seat_back_r5.setPos(0f, -6f, -22f)
        seat_4.addChild(seat_back_r5)
        setRotationAngle(seat_back_r5, 0.0524f, 0f, 0f)
        seat_back_r5.texOffs(0, 241).addBox(-18f, -8f, -1f, 12, 8, 1, 0f, true)

        seat_5 = ModelMapper(modelDataWrapper)
        seat_5.setPos(0f, 0f, 0f)
        end.addChild(seat_5)
        seat_5.texOffs(58, 164).addBox(-18f, -6f, -11f, 12, 1, 7, 0f, true)

        seat_back_r6 = ModelMapper(modelDataWrapper)
        seat_back_r6.setPos(0f, -6f, -10f)
        seat_5.addChild(seat_back_r6)
        setRotationAngle(seat_back_r6, 0.0524f, 0f, 0f)
        seat_back_r6.texOffs(172, 219).addBox(-18f, -8f, -1f, 12, 8, 1, 0f, true)

        seat_6 = ModelMapper(modelDataWrapper)
        seat_6.setPos(0f, 0f, 0f)
        end.addChild(seat_6)
        seat_6.texOffs(174, 169).addBox(-18f, -6f, 6f, 7, 1, 7, 0f, true)

        seat_back_r7 = ModelMapper(modelDataWrapper)
        seat_back_r7.setPos(0f, -6f, 12f)
        seat_6.addChild(seat_back_r7)
        setRotationAngle(seat_back_r7, -0.0524f, 0f, 0f)
        seat_back_r7.texOffs(0, 80).addBox(-18f, -8f, 0f, 7, 8, 1, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(70, 216).addBox(20f, 0f, -28f, 1, 7, 44, 0f, true)
        end_exterior.texOffs(126, 190).addBox(-21f, 0f, -28f, 1, 7, 44, 0f, false)
        end_exterior.texOffs(58, 143).addBox(18f, -13f, -36f, 2, 13, 54, 0f, true)
        end_exterior.texOffs(116, 123).addBox(-20f, -13f, -36f, 2, 13, 54, 0f, false)
        end_exterior.texOffs(0, 260).addBox(6f, -33f, -36f, 12, 33, 0, 0f, true)
        end_exterior.texOffs(0, 260).addBox(-18f, -33f, -36f, 12, 33, 0, 0f, false)
        end_exterior.texOffs(300, 148).addBox(-18f, -41f, -36f, 36, 9, 0, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -13f, 0f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(0, 49).addBox(0f, -20f, -36f, 2, 20, 54, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -13f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(58, 69).addBox(-2f, -20f, -36f, 2, 20, 54, 0f, true)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)
        roof_end.texOffs(134, 49).addBox(-18f, -32f, -24f, 4, 0, 40, 0f, false)
        roof_end.texOffs(18, 49).addBox(-10.2292f, -34.8796f, -24f, 5, 0, 40, 0f, false)
        roof_end.texOffs(146, 35).addBox(-2f, -33f, -24f, 2, 0, 40, 0f, false)
        roof_end.texOffs(10, 49).addBox(0f, -33f, -24f, 2, 0, 40, 0f, true)
        roof_end.texOffs(0, 49).addBox(5.2292f, -34.8796f, -24f, 5, 0, 40, 0f, true)
        roof_end.texOffs(116, 49).addBox(14f, -32f, -24f, 4, 0, 40, 0f, true)
        roof_end.texOffs(0, 0).addBox(0f, -33.4899f, -16.9899f, 0, 1, 0, 0.2f, false)
        roof_end.texOffs(0, 69).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        roof_end.texOffs(0, 69).addBox(-1f, -32f, 9f, 2, 4, 0, 0f, false)
        roof_end.texOffs(0, 69).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(12.2986f, -26.5473f, 0f)
        roof_end.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0f, 0f, 0.0873f)
        handrail_4_r1.texOffs(4, 0).addBox(0f, -8f, 14.25f, 0, 16, 0, 0.2f, true)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(11f, -5f, 0f)
        roof_end.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, 0.0436f)
        handrail_3_r2.texOffs(4, 16).addBox(0f, -13.2f, 14.25f, 0, 13, 0, 0.2f, true)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(-12.2986f, -26.5473f, 0f)
        roof_end.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, 0f, 0f, -0.0873f)
        handrail_2_r2.texOffs(4, 0).addBox(0f, -8f, 14.25f, 0, 16, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(-11f, -5f, 0f)
        roof_end.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, 0f, 0f, -0.0436f)
        handrail_1_r1.texOffs(4, 16).addBox(0f, -13.2f, 14.25f, 0, 13, 0, 0.2f, false)

        handrail_9_r1 = ModelMapper(modelDataWrapper)
        handrail_9_r1.setPos(0f, -31.3f, -16.2f)
        roof_end.addChild(handrail_9_r1)
        setRotationAngle(handrail_9_r1, 0.7854f, 0f, 0f)
        handrail_9_r1.texOffs(0, 0).addBox(0f, -1.2f, 0.2f, 0, 1, 0, 0.2f, false)

        handrail_8_r3 = ModelMapper(modelDataWrapper)
        handrail_8_r3.setPos(0f, 0f, 0f)
        roof_end.addChild(handrail_8_r3)
        setRotationAngle(handrail_8_r3, -1.5708f, 0f, 0f)
        handrail_8_r3.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        inner_roof_right_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_right_4_r1.setPos(14f, -32f, 0f)
        roof_end.addChild(inner_roof_right_4_r1)
        setRotationAngle(inner_roof_right_4_r1, 0f, 0f, 1.0472f)
        inner_roof_right_4_r1.texOffs(142, 35).addBox(-2f, 0f, -24f, 2, 0, 40, 0f, true)

        inner_roof_right_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_right_5_r1.setPos(11.6147f, -34.3057f, 0f)
        roof_end.addChild(inner_roof_right_5_r1)
        setRotationAngle(inner_roof_right_5_r1, 0f, 0f, 0.3927f)
        inner_roof_right_5_r1.texOffs(8, 123).addBox(-1.5f, 0f, -24f, 3, 0, 40, 0f, true)

        inner_roof_right_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_right_7_r1.setPos(2f, -33f, 0f)
        roof_end.addChild(inner_roof_right_7_r1)
        setRotationAngle(inner_roof_right_7_r1, 0f, 0f, -0.5236f)
        inner_roof_right_7_r1.texOffs(108, 49).addBox(0f, 0f, -24f, 4, 0, 40, 0f, true)

        inner_roof_left_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_left_7_r1.setPos(-2f, -33f, 0f)
        roof_end.addChild(inner_roof_left_7_r1)
        setRotationAngle(inner_roof_left_7_r1, 0f, 0f, 0.5236f)
        inner_roof_left_7_r1.texOffs(0, 123).addBox(-4f, 0f, -24f, 4, 0, 40, 0f, false)

        inner_roof_left_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_left_5_r1.setPos(-11.6147f, -34.3057f, 0f)
        roof_end.addChild(inner_roof_left_5_r1)
        setRotationAngle(inner_roof_left_5_r1, 0f, 0f, -0.3927f)
        inner_roof_left_5_r1.texOffs(124, 49).addBox(-1.5f, 0f, -24f, 3, 0, 40, 0f, false)

        inner_roof_left_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_left_4_r1.setPos(-14f, -32f, 0f)
        roof_end.addChild(inner_roof_left_4_r1)
        setRotationAngle(inner_roof_left_4_r1, 0f, 0f, -1.0472f)
        inner_roof_left_4_r1.texOffs(150, 35).addBox(0f, 0f, -24f, 2, 0, 40, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(2f, -33f, 0f)
        roof_end_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, 0f, 0f, -0.5236f)
        light_2_r1.texOffs(100, 49).addBox(0f, -0.1f, -24f, 4, 0, 40, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-2f, -33f, 0f)
        roof_end_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, 0.5236f)
        light_1_r1.texOffs(100, 49).addBox(-4f, -0.1f, -24f, 4, 0, 40, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)


        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(0, 69).addBox(-6f, -42f, -36f, 6, 1, 20, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(-20f, -13f, 0f)
        outer_roof_1.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, 0.1107f)
        upper_wall_1_r3.texOffs(0, 69).addBox(0f, -23f, -36f, 1, 4, 7, 0f, false)
        upper_wall_1_r3.texOffs(182, 70).addBox(-1f, -23f, -29f, 1, 4, 13, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(172, 190).addBox(-4f, -1f, -36f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(0, 20).addBox(-1.5f, -1f, -36f, 3, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(58, 143).addBox(-1f, -1f, -36f, 2, 1, 20, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_1.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, 0.1107f)
        outer_roof_2_r2.texOffs(116, 143).addBox(0f, -1f, -36f, 1, 2, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(0, 69).addBox(0f, -42f, -36f, 6, 1, 20, 0f, true)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(20f, -13f, 0f)
        outer_roof_2.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, -0.1107f)
        upper_wall_1_r4.texOffs(0, 69).addBox(-1f, -23f, -36f, 1, 4, 7, 0f, true)
        upper_wall_1_r4.texOffs(182, 70).addBox(0f, -23f, -29f, 1, 4, 13, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.1745f)
        outer_roof_5_r3.texOffs(172, 190).addBox(-4f, -1f, -36f, 8, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.5236f)
        outer_roof_4_r3.texOffs(0, 20).addBox(-1.5f, -1f, -36f, 3, 1, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.0472f)
        outer_roof_3_r3.texOffs(58, 143).addBox(-1f, -1f, -36f, 2, 1, 20, 0f, true)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(17.587f, -36.3849f, 0f)
        outer_roof_2.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -0.1107f)
        outer_roof_2_r3.texOffs(116, 143).addBox(-1f, -1f, -36f, 1, 2, 20, 0f, true)

        roof_end_vents = ModelMapper(modelDataWrapper)
        roof_end_vents.setPos(0f, 24f, 0f)
        roof_end_vents.texOffs(180, 169).addBox(-8f, -43f, -21f, 16, 2, 48, 0f, false)

        vent_3_r1 = ModelMapper(modelDataWrapper)
        vent_3_r1.setPos(-8f, -43f, 12f)
        roof_end_vents.addChild(vent_3_r1)
        setRotationAngle(vent_3_r1, 0f, 0f, -0.3491f)
        vent_3_r1.texOffs(0, 210).addBox(-9f, 0f, -33f, 9, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(8f, -43f, 12f)
        roof_end_vents.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, 0.3491f)
        vent_2_r1.texOffs(0, 210).addBox(0f, 0f, -33f, 9, 2, 48, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(128, 0).addBox(-20f, 0f, -16f, 40, 1, 34, 0f, false)
        head.texOffs(116, 298).addBox(-20f, -13f, -18f, 2, 13, 36, 0f, false)
        head.texOffs(293, 240).addBox(18f, -13f, -18f, 2, 13, 36, 0f, true)
        head.texOffs(322, 289).addBox(-18f, -36f, 18f, 36, 36, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(20f, -13f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, -0.1107f)
        upper_wall_2_r3.texOffs(0, 260).addBox(-2f, -20f, -18f, 2, 20, 36, 0f, true)

        upper_wall_1_r5 = ModelMapper(modelDataWrapper)
        upper_wall_1_r5.setPos(-20f, -13f, 0f)
        head.addChild(upper_wall_1_r5)
        setRotationAngle(upper_wall_1_r5, 0f, 0f, 0.1107f)
        upper_wall_1_r5.texOffs(260, 135).addBox(0f, -20f, -18f, 2, 20, 36, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(232, 68).addBox(-21f, 0f, 19f, 42, 7, 12, 0f, false)
        head_exterior.texOffs(305, 52).addBox(20f, 0f, -16f, 1, 7, 35, 0f, true)
        head_exterior.texOffs(304, 0).addBox(-21f, 0f, -16f, 1, 7, 35, 0f, false)
        head_exterior.texOffs(281, 289).addBox(18f, -13f, -18f, 2, 13, 37, 0f, true)
        head_exterior.texOffs(256, 219).addBox(20f, -13f, 19f, 1, 13, 12, 0f, true)
        head_exterior.texOffs(240, 276).addBox(-20f, -13f, -18f, 2, 13, 37, 0f, false)
        head_exterior.texOffs(256, 219).addBox(-21f, -13f, 19f, 1, 13, 12, 0f, false)
        head_exterior.texOffs(0, 316).addBox(-18f, -42f, 19f, 36, 42, 0, 0f, false)

        driver_door_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_2_r1.setPos(-21f, -13f, 0f)
        head_exterior.addChild(driver_door_upper_2_r1)
        setRotationAngle(driver_door_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_upper_2_r1.texOffs(232, 102).addBox(0f, -20f, 19f, 1, 20, 12, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-20f, -13f, 0f)
        head_exterior.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(123, 241).addBox(0f, -20f, -18f, 2, 20, 37, 0f, false)

        driver_door_upper_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_1_r1.setPos(21f, -13f, 0f)
        head_exterior.addChild(driver_door_upper_1_r1)
        setRotationAngle(driver_door_upper_1_r1, 0f, 0f, -0.1107f)
        driver_door_upper_1_r1.texOffs(232, 102).addBox(-1f, -20f, 19f, 1, 20, 12, 0f, true)

        upper_wall_1_r6 = ModelMapper(modelDataWrapper)
        upper_wall_1_r6.setPos(20f, -13f, 0f)
        head_exterior.addChild(upper_wall_1_r6)
        setRotationAngle(upper_wall_1_r6, 0f, 0f, -0.1107f)
        upper_wall_1_r6.texOffs(251, 219).addBox(-2f, -20f, -18f, 2, 20, 37, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(116, 143).addBox(-3f, -10f, 45f, 6, 5, 0, 0f, false)
        front.texOffs(228, 0).addBox(-21f, 0f, 31f, 42, 0, 14, 0f, false)

        front_middle_top_2_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_2_r1.setPos(0f, -36.1042f, 37.8711f)
        front.addChild(front_middle_top_2_r1)
        setRotationAngle(front_middle_top_2_r1, -0.7854f, 0f, 0f)
        front_middle_top_2_r1.texOffs(149, 35).addBox(-6f, 0f, -3.5f, 12, 0, 8, 0f, false)

        front_middle_top_1_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_1_r1.setPos(0f, -42f, 26f)
        front.addChild(front_middle_top_1_r1)
        setRotationAngle(front_middle_top_1_r1, -0.3491f, 0f, 0f)
        front_middle_top_1_r1.texOffs(20, 90).addBox(-6f, 0f, 0f, 12, 0, 10, 0f, false)

        front_3_r1 = ModelMapper(modelDataWrapper)
        front_3_r1.setPos(0f, -5f, 45f)
        front.addChild(front_3_r1)
        setRotationAngle(front_3_r1, -0.0873f, 0f, 0f)
        front_3_r1.texOffs(58, 143).addBox(-3f, 0f, 0f, 6, 12, 0, 0f, false)

        front_1_r1 = ModelMapper(modelDataWrapper)
        front_1_r1.setPos(0f, -10f, 45f)
        front.addChild(front_1_r1)
        setRotationAngle(front_1_r1, 0.1745f, 0f, 0f)
        front_1_r1.texOffs(260, 191).addBox(-3f, -24f, 0f, 6, 24, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)
        side_1.texOffs(201, 277).addBox(0f, -42f, 16f, 6, 1, 10, 0f, false)

        outer_roof_11_r1 = ModelMapper(modelDataWrapper)
        outer_roof_11_r1.setPos(18.7914f, -32.8777f, 31f)
        side_1.addChild(outer_roof_11_r1)
        setRotationAngle(outer_roof_11_r1, -1.0472f, 0.9948f, 0f)
        outer_roof_11_r1.texOffs(106, 267).addBox(-9.0009f, 0.0014f, -6f, 11, 0, 10, 0f, false)

        outer_roof_10_r1 = ModelMapper(modelDataWrapper)
        outer_roof_10_r1.setPos(5.4063f, -38.6327f, 35.3973f)
        side_1.addChild(outer_roof_10_r1)
        setRotationAngle(outer_roof_10_r1, -0.8378f, 0.0873f, 0.2618f)
        outer_roof_10_r1.texOffs(66, 293).addBox(-3f, 0.001f, -2f, 14, 0, 10, 0f, false)

        outer_roof_9_r1 = ModelMapper(modelDataWrapper)
        outer_roof_9_r1.setPos(6f, -42f, 26f)
        side_1.addChild(outer_roof_9_r1)
        setRotationAngle(outer_roof_9_r1, -0.3491f, 0f, 0.1745f)
        outer_roof_9_r1.texOffs(0, 0).addBox(0f, 0f, 0f, 8, 0, 10, 0f, false)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(18.682f, -33.8719f, 31f)
        side_1.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0f, 0f, 1.1345f)
        outer_roof_8_r1.texOffs(145, 89).addBox(-6f, 0.001f, -5f, 6, 0, 5, 0f, false)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(13.8776f, -40.6102f, 26f)
        side_1.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, -0.3491f, 0f, 0.5236f)
        outer_roof_7_r1.texOffs(130, 89).addBox(0f, 0f, 0f, 5, 0, 10, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(9.9394f, -41.3064f, 0f)
        side_1.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, 0.1745f)
        outer_roof_5_r4.texOffs(116, 249).addBox(-4f, 0f, 16f, 8, 1, 10, 0f, false)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(15.1778f, -39.8628f, 0f)
        side_1.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, 0.5236f)
        outer_roof_4_r4.texOffs(156, 309).addBox(-1.5f, 0f, 16f, 3, 1, 10, 0f, false)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(16.9769f, -38.2468f, 0f)
        side_1.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0f, 0f, 1.0472f)
        outer_roof_3_r4.texOffs(156, 298).addBox(-1f, 0f, 16f, 2, 1, 10, 0f, false)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(17.5872f, -36.3872f, 0f)
        side_1.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.1107f)
        outer_roof_2_r4.texOffs(138, 143).addBox(-1f, -1f, 16f, 1, 2, 10, 0f, true)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(20f, -13f, 5f)
        side_1.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, -0.1107f)
        outer_roof_1_r2.texOffs(245, 136).addBox(-1f, -23f, 11f, 2, 4, 15, 0f, true)

        front_side_lower_5_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_5_r1.setPos(8.7065f, -8.5f, 43.6502f)
        side_1.addChild(front_side_lower_5_r1)
        setRotationAngle(front_side_lower_5_r1, 0f, 0.2182f, 0f)
        front_side_lower_5_r1.texOffs(116, 241).addBox(-7f, -3.5f, 0f, 14, 7, 0, 0f, false)

        front_side_lower_4_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_4_r1.setPos(17.6615f, -8.5f, 40.0141f)
        side_1.addChild(front_side_lower_4_r1)
        setRotationAngle(front_side_lower_4_r1, 0f, 0.7854f, 0f)
        front_side_lower_4_r1.texOffs(26, 32).addBox(-3f, -3.5f, 0f, 6, 7, 0, 0f, false)

        front_side_lower_3_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_3_r1.setPos(1.8727f, -5f, 45.1663f)
        side_1.addChild(front_side_lower_3_r1)
        setRotationAngle(front_side_lower_3_r1, -0.0873f, 0.2182f, 0f)
        front_side_lower_3_r1.texOffs(197, 70).addBox(0f, 0f, -0.001f, 15, 12, 0, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(15.5408f, -5f, 42.1361f)
        side_1.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, -0.0436f, 0.7854f, 0f)
        front_side_lower_2_r1.texOffs(0, 123).addBox(0f, 0f, -0.001f, 7, 12, 0, 0f, false)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(21f, 7f, 31f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, -0.1745f, 0f)
        front_side_lower_1_r1.texOffs(116, 203).addBox(0f, -20f, 0f, 0, 20, 7, 0f, false)

        front_side_upper_7_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_7_r1.setPos(15.2976f, -12.6432f, 41.454f)
        side_1.addChild(front_side_upper_7_r1)
        setRotationAngle(front_side_upper_7_r1, 0.4102f, 0.2059f, -0.0436f)
        front_side_upper_7_r1.texOffs(58, 172).addBox(-13f, -1f, -0.001f, 13, 4, 0, 0f, false)

        front_side_upper_6_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_6_r1.setPos(19.1f, -12.95f, 37.7f)
        side_1.addChild(front_side_upper_6_r1)
        setRotationAngle(front_side_upper_6_r1, 0.3491f, 0.7679f, -0.0436f)
        front_side_upper_6_r1.texOffs(28, 43).addBox(-6.35f, -0.85f, 0f, 7, 3, 0, 0f, false)

        front_side_upper_5_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_5_r1.setPos(20.4813f, -12.9424f, 33.9542f)
        side_1.addChild(front_side_upper_5_r1)
        setRotationAngle(front_side_upper_5_r1, 0.4363f, 1.2217f, 0f)
        front_side_upper_5_r1.texOffs(30, 100).addBox(-4.9998f, -0.9997f, -0.0001f, 5, 3, 0, 0f, false)

        front_side_upper_4_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_4_r1.setPos(3f, -10f, 45f)
        side_1.addChild(front_side_upper_4_r1)
        setRotationAngle(front_side_upper_4_r1, 0.1745f, 0.2443f, 0f)
        front_side_upper_4_r1.texOffs(76, 323).addBox(0f, -24f, 0f, 14, 24, 0, 0f, false)

        front_side_upper_3_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_3_r1.setPos(19.1695f, -12.1451f, 37.781f)
        side_1.addChild(front_side_upper_3_r1)
        setRotationAngle(front_side_upper_3_r1, 0.1309f, 0.7679f, 0f)
        front_side_upper_3_r1.texOffs(276, 191).addBox(-5.9989f, -21.9999f, 0.0149f, 7, 23, 0, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(19.156f, -22.9499f, 34.5398f)
        side_1.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0.0873f, -0.3491f, -0.1107f)
        front_side_upper_2_r1.texOffs(136, 206).addBox(0f, -9f, -1.5f, 0, 20, 4, 0f, false)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(21f, -13f, 31f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, -0.1745f, -0.1107f)
        front_side_upper_1_r1.texOffs(130, 207).addBox(0f, -20f, 0f, 0, 20, 3, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        front.addChild(side_2)
        side_2.texOffs(201, 277).addBox(-6f, -42f, 16f, 6, 1, 10, 0f, true)

        outer_roof_11_r2 = ModelMapper(modelDataWrapper)
        outer_roof_11_r2.setPos(-18.7914f, -32.8777f, 31f)
        side_2.addChild(outer_roof_11_r2)
        setRotationAngle(outer_roof_11_r2, -1.0472f, -0.9948f, 0f)
        outer_roof_11_r2.texOffs(106, 267).addBox(-1.9991f, 0.0014f, -6f, 11, 0, 10, 0f, true)

        outer_roof_10_r2 = ModelMapper(modelDataWrapper)
        outer_roof_10_r2.setPos(-5.4063f, -38.6327f, 35.3973f)
        side_2.addChild(outer_roof_10_r2)
        setRotationAngle(outer_roof_10_r2, -0.8378f, -0.0873f, -0.2618f)
        outer_roof_10_r2.texOffs(66, 293).addBox(-11f, 0.001f, -2f, 14, 0, 10, 0f, true)

        outer_roof_9_r2 = ModelMapper(modelDataWrapper)
        outer_roof_9_r2.setPos(-6f, -42f, 26f)
        side_2.addChild(outer_roof_9_r2)
        setRotationAngle(outer_roof_9_r2, -0.3491f, 0f, -0.1745f)
        outer_roof_9_r2.texOffs(0, 0).addBox(-8f, 0f, 0f, 8, 0, 10, 0f, true)

        outer_roof_8_r2 = ModelMapper(modelDataWrapper)
        outer_roof_8_r2.setPos(-18.682f, -33.8719f, 31f)
        side_2.addChild(outer_roof_8_r2)
        setRotationAngle(outer_roof_8_r2, 0f, 0f, -1.1345f)
        outer_roof_8_r2.texOffs(145, 89).addBox(0f, 0.001f, -5f, 6, 0, 5, 0f, true)

        outer_roof_7_r2 = ModelMapper(modelDataWrapper)
        outer_roof_7_r2.setPos(-13.8776f, -40.6102f, 26f)
        side_2.addChild(outer_roof_7_r2)
        setRotationAngle(outer_roof_7_r2, -0.3491f, 0f, -0.5236f)
        outer_roof_7_r2.texOffs(130, 89).addBox(-5f, 0f, 0f, 5, 0, 10, 0f, true)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(-9.9394f, -41.3064f, 0f)
        side_2.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, -0.1745f)
        outer_roof_5_r5.texOffs(116, 249).addBox(-4f, 0f, 16f, 8, 1, 10, 0f, true)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(-15.1778f, -39.8628f, 0f)
        side_2.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, -0.5236f)
        outer_roof_4_r5.texOffs(156, 309).addBox(-1.5f, 0f, 16f, 3, 1, 10, 0f, true)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(-16.9769f, -38.2468f, 0f)
        side_2.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0f, 0f, -1.0472f)
        outer_roof_3_r5.texOffs(156, 298).addBox(-1f, 0f, 16f, 2, 1, 10, 0f, true)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(-17.5872f, -36.3872f, 0f)
        side_2.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, 0.1107f)
        outer_roof_2_r5.texOffs(138, 143).addBox(0f, -1f, 16f, 1, 2, 10, 0f, false)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(-20f, -13f, 5f)
        side_2.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, 0.1107f)
        outer_roof_1_r3.texOffs(245, 136).addBox(-1f, -23f, 11f, 2, 4, 15, 0f, false)

        front_side_lower_6_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_6_r1.setPos(-8.7065f, -8.5f, 43.6502f)
        side_2.addChild(front_side_lower_6_r1)
        setRotationAngle(front_side_lower_6_r1, 0f, -0.2182f, 0f)
        front_side_lower_6_r1.texOffs(116, 241).addBox(-7f, -3.5f, 0f, 14, 7, 0, 0f, true)

        front_side_lower_5_r2 = ModelMapper(modelDataWrapper)
        front_side_lower_5_r2.setPos(-17.6615f, -8.5f, 40.0141f)
        side_2.addChild(front_side_lower_5_r2)
        setRotationAngle(front_side_lower_5_r2, 0f, -0.7854f, 0f)
        front_side_lower_5_r2.texOffs(26, 32).addBox(-3f, -3.5f, 0f, 6, 7, 0, 0f, true)

        front_side_lower_4_r2 = ModelMapper(modelDataWrapper)
        front_side_lower_4_r2.setPos(-1.8727f, -5f, 45.1663f)
        side_2.addChild(front_side_lower_4_r2)
        setRotationAngle(front_side_lower_4_r2, -0.0873f, -0.2182f, 0f)
        front_side_lower_4_r2.texOffs(197, 70).addBox(-15f, 0f, -0.001f, 15, 12, 0, 0f, true)

        front_side_lower_3_r2 = ModelMapper(modelDataWrapper)
        front_side_lower_3_r2.setPos(-15.5408f, -5f, 42.1361f)
        side_2.addChild(front_side_lower_3_r2)
        setRotationAngle(front_side_lower_3_r2, -0.0436f, -0.7854f, 0f)
        front_side_lower_3_r2.texOffs(0, 123).addBox(-7f, 0f, -0.001f, 7, 12, 0, 0f, true)

        front_side_lower_2_r2 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r2.setPos(-21f, 7f, 31f)
        side_2.addChild(front_side_lower_2_r2)
        setRotationAngle(front_side_lower_2_r2, 0f, 0.1745f, 0f)
        front_side_lower_2_r2.texOffs(116, 203).addBox(0f, -20f, 0f, 0, 20, 7, 0f, true)

        front_side_upper_8_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_8_r1.setPos(-15.2976f, -12.6432f, 41.454f)
        side_2.addChild(front_side_upper_8_r1)
        setRotationAngle(front_side_upper_8_r1, 0.4102f, -0.2059f, 0.0436f)
        front_side_upper_8_r1.texOffs(58, 172).addBox(0f, -1f, -0.001f, 13, 4, 0, 0f, true)

        front_side_upper_7_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_7_r2.setPos(-19.1f, -12.95f, 37.7f)
        side_2.addChild(front_side_upper_7_r2)
        setRotationAngle(front_side_upper_7_r2, 0.3491f, -0.7679f, 0.0436f)
        front_side_upper_7_r2.texOffs(28, 43).addBox(-0.65f, -0.85f, 0f, 7, 3, 0, 0f, true)

        front_side_upper_6_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_6_r2.setPos(-20.4813f, -12.9424f, 33.9542f)
        side_2.addChild(front_side_upper_6_r2)
        setRotationAngle(front_side_upper_6_r2, 0.4363f, -1.2217f, 0f)
        front_side_upper_6_r2.texOffs(30, 100).addBox(-0.0002f, -0.9997f, -0.0001f, 5, 3, 0, 0f, true)

        front_side_upper_5_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_5_r2.setPos(-3f, -10f, 45f)
        side_2.addChild(front_side_upper_5_r2)
        setRotationAngle(front_side_upper_5_r2, 0.1745f, -0.2443f, 0f)
        front_side_upper_5_r2.texOffs(76, 323).addBox(-14f, -24f, 0f, 14, 24, 0, 0f, true)

        front_side_upper_4_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_4_r2.setPos(-19.1695f, -12.1451f, 37.781f)
        side_2.addChild(front_side_upper_4_r2)
        setRotationAngle(front_side_upper_4_r2, 0.1309f, -0.7679f, 0f)
        front_side_upper_4_r2.texOffs(276, 191).addBox(-1.0011f, -21.9999f, 0.0149f, 7, 23, 0, 0f, true)

        front_side_upper_3_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_3_r2.setPos(-19.156f, -22.9499f, 34.5398f)
        side_2.addChild(front_side_upper_3_r2)
        setRotationAngle(front_side_upper_3_r2, 0.0873f, 0.3491f, 0.1107f)
        front_side_upper_3_r2.texOffs(136, 205).addBox(0f, -10f, -1.5f, 0, 21, 4, 0f, true)

        front_side_upper_2_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r2.setPos(-21f, -13f, 31f)
        side_2.addChild(front_side_upper_2_r2)
        setRotationAngle(front_side_upper_2_r2, 0f, 0.1745f, 0.1107f)
        front_side_upper_2_r2.texOffs(130, 207).addBox(0f, -20f, 0f, 0, 20, 3, 0f, true)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_4_r1 = ModelMapper(modelDataWrapper)
        headlight_4_r1.setPos(-14.0978f, -7f, 42.5574f)
        headlights.addChild(headlight_4_r1)
        setRotationAngle(headlight_4_r1, 0f, -0.2182f, 0f)
        headlight_4_r1.texOffs(39, 0).addBox(-1.5f, -2f, 0f, 3, 4, 0, 0f, true)

        headlight_3_r1 = ModelMapper(modelDataWrapper)
        headlight_3_r1.setPos(-16.976f, -7f, 40.8188f)
        headlights.addChild(headlight_3_r1)
        setRotationAngle(headlight_3_r1, 0f, -0.7854f, 0f)
        headlight_3_r1.texOffs(31, 0).addBox(-2f, -2f, 0f, 4, 4, 0, 0f, true)

        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(14.0978f, -7f, 42.5574f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, 0f, 0.2182f, 0f)
        headlight_2_r1.texOffs(39, 0).addBox(-1.5f, -2f, 0f, 3, 4, 0, 0f, false)

        headlight_1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1.setPos(16.976f, -7f, 40.8188f)
        headlights.addChild(headlight_1_r1)
        setRotationAngle(headlight_1_r1, 0f, 0.7854f, 0f)
        headlight_1_r1.texOffs(31, 0).addBox(-2f, -2f, 0f, 4, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_2_r1 = ModelMapper(modelDataWrapper)
        tail_light_2_r1.setPos(-14.0978f, -7f, 42.5574f)
        tail_lights.addChild(tail_light_2_r1)
        setRotationAngle(tail_light_2_r1, 0f, -0.2182f, 0f)
        tail_light_2_r1.texOffs(31, 4).addBox(0.5f, -2f, 0f, 5, 4, 0, 0f, true)

        tail_light_1_r1 = ModelMapper(modelDataWrapper)
        tail_light_1_r1.setPos(14.0978f, -7f, 42.5574f)
        tail_lights.addChild(tail_light_1_r1)
        setRotationAngle(tail_light_1_r1, 0f, 0.2182f, 0f)
        tail_light_1_r1.texOffs(31, 4).addBox(-5.5f, -2f, 0f, 5, 4, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_1_r4 = ModelMapper(modelDataWrapper)
        outer_roof_1_r4.setPos(-20f, -13f, 0f)
        door_light.addChild(outer_roof_1_r4)
        setRotationAngle(outer_roof_1_r4, 0f, 0f, 0.1107f)
        outer_roof_1_r4.texOffs(58, 85).addBox(-1.1f, -23f, -2f, 0, 4, 4, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -13f, 0f)
        door_light_on.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(60, 94).addBox(-1f, -21f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r3 = ModelMapper(modelDataWrapper)
        light_r3.setPos(-20f, -13f, 0f)
        door_light_off.addChild(light_r3)
        setRotationAngle(light_r3, 0f, 0f, 0.1107f)
        light_r3.texOffs(60, 96).addBox(-1f, -21f, 0f, 0, 0, 0, 0.4f, false)

        christmas_tree = ModelMapper(modelDataWrapper)
        christmas_tree.setPos(0f, 24f, 0f)
        christmas_tree.texOffs(0, 379).addBox(-1.5f, -17.9f, -1.5f, 3, 18, 3, 0f, false)
        christmas_tree.texOffs(12, 380).addBox(-8f, -10f, -8f, 16, 4, 16, 0f, false)
        christmas_tree.texOffs(20, 384).addBox(-6f, -14f, -6f, 12, 4, 12, 0f, false)
        christmas_tree.texOffs(28, 388).addBox(-4f, -18f, -4f, 8, 4, 8, 0f, false)
        christmas_tree.texOffs(36, 392).addBox(-2f, -22f, -2f, 4, 4, 4, 0f, false)
        christmas_tree.texOffs(40, 396).addBox(-1f, -24f, -1f, 2, 2, 2, 0f, false)

        present_6_r1 = ModelMapper(modelDataWrapper)
        present_6_r1.setPos(0f, 0f, 0f)
        christmas_tree.addChild(present_6_r1)
        setRotationAngle(present_6_r1, 0f, -0.0873f, 0f)
        present_6_r1.texOffs(72, 390).addBox(-2f, -3f, 3.75f, 3, 3, 3, 0f, false)
        present_6_r1.texOffs(60, 390).addBox(-5f, -3f, -0.25f, 3, 3, 3, 0f, false)
        present_6_r1.texOffs(60, 384).addBox(-2f, -3f, -5.25f, 3, 3, 3, 0f, false)

        present_5_r1 = ModelMapper(modelDataWrapper)
        present_5_r1.setPos(0f, 0f, 0f)
        christmas_tree.addChild(present_5_r1)
        setRotationAngle(present_5_r1, 0f, -0.3927f, 0f)
        present_5_r1.texOffs(60, 384).addBox(2.5f, -3f, -0.5f, 3, 3, 3, 0f, false)
        present_5_r1.texOffs(72, 390).addBox(-6.5f, -3f, -3.5f, 3, 3, 3, 0f, false)

        present_2_r1 = ModelMapper(modelDataWrapper)
        present_2_r1.setPos(0f, 0f, 0f)
        christmas_tree.addChild(present_2_r1)
        setRotationAngle(present_2_r1, 0f, 0.0873f, 0f)
        present_2_r1.texOffs(72, 384).addBox(1.75f, -3f, -3.5f, 3, 3, 3, 0f, false)

        christmas_antler = ModelMapper(modelDataWrapper)
        christmas_antler.setPos(0f, 24f, 0f)


        antler_2_r1 = ModelMapper(modelDataWrapper)
        antler_2_r1.setPos(-10f, -7f, 0f)
        christmas_antler.addChild(antler_2_r1)
        setRotationAngle(antler_2_r1, 0f, 0f, 1.1781f)
        antler_2_r1.texOffs(22, 383).addBox(-18f, -42.75f, 0f, 1, 4, 1, 0f, false)
        antler_2_r1.texOffs(22, 383).addBox(-20f, -44.75f, 0f, 1, 4, 1, 0f, false)

        antler_base_r1 = ModelMapper(modelDataWrapper)
        antler_base_r1.setPos(-10f, -7f, 0f)
        christmas_antler.addChild(antler_base_r1)
        setRotationAngle(antler_base_r1, 0f, 0f, 0.5236f)
        antler_base_r1.texOffs(22, 383).addBox(9f, -50f, 0f, 1, 12, 1, 0f, false)

        christmas_light_head = ModelMapper(modelDataWrapper)
        christmas_light_head.setPos(0f, 24f, 0f)
        christmas_light_head.texOffs(12, 392).addBox(-2f, -28f, 0f, 4, 4, 0, 0f, false)
        christmas_light_head.texOffs(12, 387).addBox(-1.5f, -8.25f, 45f, 3, 3, 2, 0f, false)

        christmas_light_holder = ModelMapper(modelDataWrapper)
        christmas_light_holder.setPos(0f, 24f, 0f)


        c_light_pole_5_r1 = ModelMapper(modelDataWrapper)
        c_light_pole_5_r1.setPos(0f, -31.8f, 5.2f)
        christmas_light_holder.addChild(c_light_pole_5_r1)
        setRotationAngle(c_light_pole_5_r1, 1.7453f, 0f, 0f)
        c_light_pole_5_r1.texOffs(85, 385).addBox(-12f, 0.2f, 0.2f, 0, 5, 0, 0.2f, false)

        c_light_pole_4_r1 = ModelMapper(modelDataWrapper)
        c_light_pole_4_r1.setPos(0f, -33.8491f, 12.9867f)
        christmas_light_holder.addChild(c_light_pole_4_r1)
        setRotationAngle(c_light_pole_4_r1, 1.9199f, 0f, 0f)
        c_light_pole_4_r1.texOffs(85, 385).addBox(-12f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        c_light_pole_3_r1 = ModelMapper(modelDataWrapper)
        c_light_pole_3_r1.setPos(0f, -32f, 0f)
        christmas_light_holder.addChild(c_light_pole_3_r1)
        setRotationAngle(c_light_pole_3_r1, -1.5708f, 0f, 0f)
        c_light_pole_3_r1.texOffs(85, 385).addBox(-12f, -5f, 0f, 0, 10, 0, 0.2f, false)

        c_light_pole_2_r1 = ModelMapper(modelDataWrapper)
        c_light_pole_2_r1.setPos(0f, -31.8f, -5.2f)
        christmas_light_holder.addChild(c_light_pole_2_r1)
        setRotationAngle(c_light_pole_2_r1, -1.7453f, 0f, 0f)
        c_light_pole_2_r1.texOffs(85, 385).addBox(-12f, 0.2f, -0.2f, 0, 5, 0, 0.2f, false)

        c_light_pole_1_r1 = ModelMapper(modelDataWrapper)
        c_light_pole_1_r1.setPos(0f, -33.8491f, -12.9867f)
        christmas_light_holder.addChild(c_light_pole_1_r1)
        setRotationAngle(c_light_pole_1_r1, -1.9199f, 0f, 0f)
        c_light_pole_1_r1.texOffs(85, 385).addBox(-12f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        christmas_light_red = ModelMapper(modelDataWrapper)
        christmas_light_red.setPos(0f, 24f, 0f)


        c_light_9_r1 = ModelMapper(modelDataWrapper)
        c_light_9_r1.setPos(0f, -31.8f, 5.2f)
        christmas_light_red.addChild(c_light_9_r1)
        setRotationAngle(c_light_9_r1, 1.7453f, 0f, 0f)
        c_light_9_r1.texOffs(61, 385).addBox(-12f, 1.05f, -0.2f, 0, 0, 0, 0.2f, false)

        c_light_5_r1 = ModelMapper(modelDataWrapper)
        c_light_5_r1.setPos(0f, -32f, 0f)
        christmas_light_red.addChild(c_light_5_r1)
        setRotationAngle(c_light_5_r1, -1.5708f, 0f, 0f)
        c_light_5_r1.texOffs(61, 385).addBox(-12f, 3.75f, 0.4f, 0, 0, 0, 0.2f, false)

        c_light_1_r1 = ModelMapper(modelDataWrapper)
        c_light_1_r1.setPos(0f, -33.5402f, -10.625f)
        christmas_light_red.addChild(c_light_1_r1)
        setRotationAngle(c_light_1_r1, -1.9199f, 0f, 0f)
        c_light_1_r1.texOffs(61, 385).addBox(-12f, 2.8249f, 0.9175f, 0, 0, 0, 0.2f, false)

        christmas_light_yellow = ModelMapper(modelDataWrapper)
        christmas_light_yellow.setPos(0f, 24f, 0f)


        c_light_10_r1 = ModelMapper(modelDataWrapper)
        c_light_10_r1.setPos(0f, -31.8f, 5.2f)
        christmas_light_yellow.addChild(c_light_10_r1)
        setRotationAngle(c_light_10_r1, 1.7453f, 0f, 0f)
        c_light_10_r1.texOffs(73, 385).addBox(-12f, 3.55f, -0.2f, 0, 0, 0, 0.2f, false)

        c_light_6_r1 = ModelMapper(modelDataWrapper)
        c_light_6_r1.setPos(0f, -32f, 0f)
        christmas_light_yellow.addChild(c_light_6_r1)
        setRotationAngle(c_light_6_r1, -1.5708f, 0f, 0f)
        c_light_6_r1.texOffs(73, 385).addBox(-12f, 1.25f, 0.4f, 0, 0, 0, 0.2f, false)

        c_light_2_r1 = ModelMapper(modelDataWrapper)
        c_light_2_r1.setPos(0f, -33.5402f, -10.625f)
        christmas_light_yellow.addChild(c_light_2_r1)
        setRotationAngle(c_light_2_r1, -1.9199f, 0f, 0f)
        c_light_2_r1.texOffs(73, 385).addBox(-12f, 0.3249f, 0.9175f, 0, 0, 0, 0.2f, false)

        christmas_light_green = ModelMapper(modelDataWrapper)
        christmas_light_green.setPos(0f, 24f, 0f)


        c_light_11_r1 = ModelMapper(modelDataWrapper)
        c_light_11_r1.setPos(0f, -33.5402f, 10.625f)
        christmas_light_green.addChild(c_light_11_r1)
        setRotationAngle(c_light_11_r1, 1.9199f, 0f, 0f)
        c_light_11_r1.texOffs(61, 391).addBox(-12f, 0.3249f, -0.9175f, 0, 0, 0, 0.2f, false)

        c_light_7_r1 = ModelMapper(modelDataWrapper)
        c_light_7_r1.setPos(0f, -32f, 0f)
        christmas_light_green.addChild(c_light_7_r1)
        setRotationAngle(c_light_7_r1, -1.5708f, 0f, 0f)
        c_light_7_r1.texOffs(61, 391).addBox(-12f, -1.25f, 0.4f, 0, 0, 0, 0.2f, false)

        c_light_3_r1 = ModelMapper(modelDataWrapper)
        c_light_3_r1.setPos(0f, -31.8f, -5.2f)
        christmas_light_green.addChild(c_light_3_r1)
        setRotationAngle(c_light_3_r1, -1.7453f, 0f, 0f)
        c_light_3_r1.texOffs(61, 391).addBox(-12f, 3.55f, 0.2f, 0, 0, 0, 0.2f, false)

        christmas_light_blue = ModelMapper(modelDataWrapper)
        christmas_light_blue.setPos(0f, 24f, 0f)


        c_light_12_r1 = ModelMapper(modelDataWrapper)
        c_light_12_r1.setPos(0f, -33.5402f, 10.625f)
        christmas_light_blue.addChild(c_light_12_r1)
        setRotationAngle(c_light_12_r1, 1.9199f, 0f, 0f)
        c_light_12_r1.texOffs(73, 391).addBox(-12f, 2.8249f, -0.9175f, 0, 0, 0, 0.2f, false)

        c_light_8_r1 = ModelMapper(modelDataWrapper)
        c_light_8_r1.setPos(0f, -32f, 0f)
        christmas_light_blue.addChild(c_light_8_r1)
        setRotationAngle(c_light_8_r1, -1.5708f, 0f, 0f)
        c_light_8_r1.texOffs(73, 391).addBox(-12f, -3.75f, 0.4f, 0, 0, 0, 0.2f, false)

        c_light_4_r1 = ModelMapper(modelDataWrapper)
        c_light_4_r1.setPos(0f, -31.8f, -5.2f)
        christmas_light_blue.addChild(c_light_4_r1)
        setRotationAngle(c_light_4_r1, -1.7453f, 0f, 0f)
        c_light_4_r1.texOffs(73, 391).addBox(-12f, 1.05f, 0.2f, 0, 0, 0, 0.2f, false)

        christmas_light_tree_red = ModelMapper(modelDataWrapper)
        christmas_light_tree_red.setPos(0f, 24f, 0f)
        christmas_light_tree_red.texOffs(70, 385).addBox(1f, -21.2888f, -2.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(0f, -17.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(-1f, -13.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(-5f, -8.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(-3f, -6.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(6f, -7.2888f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_red.texOffs(70, 385).addBox(-0.2f, -0.2f, 0.2f, 0, 0, 0, 0.2f, false)

        ct_light_13_r1 = ModelMapper(modelDataWrapper)
        ct_light_13_r1.setPos(0f, 0f, 0f)
        christmas_light_tree_red.addChild(ct_light_13_r1)
        setRotationAngle(ct_light_13_r1, 0f, -1.5708f, 0f)
        ct_light_13_r1.texOffs(70, 385).addBox(6f, -7.2888f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(-3f, -6.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(-5f, -8.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(-1f, -13.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(0f, -17.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(1f, -21.2888f, -2.177f, 0, 0, 0, 0.2f, false)
        ct_light_13_r1.texOffs(70, 385).addBox(-0.2f, -0.2f, 0.2f, 0, 0, 0, 0.2f, false)

        christmas_light_tree_yellow = ModelMapper(modelDataWrapper)
        christmas_light_tree_yellow.setPos(0f, 24f, 0f)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(-1f, -18.2888f, -2.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(2f, -15.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(1f, -11.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(-3f, -12.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(-2f, -9.5388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_yellow.texOffs(82, 385).addBox(-6f, -7.0388f, -8.177f, 0, 0, 0, 0.2f, false)

        ct_light_20_r1 = ModelMapper(modelDataWrapper)
        ct_light_20_r1.setPos(0f, 0f, 0f)
        christmas_light_tree_yellow.addChild(ct_light_20_r1)
        setRotationAngle(ct_light_20_r1, 0f, -1.5708f, 0f)
        ct_light_20_r1.texOffs(82, 385).addBox(-6f, -7.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_20_r1.texOffs(82, 385).addBox(-2f, -9.5388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_20_r1.texOffs(82, 385).addBox(-3f, -12.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_20_r1.texOffs(82, 385).addBox(1f, -11.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_20_r1.texOffs(82, 385).addBox(2f, -15.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        ct_light_20_r1.texOffs(82, 385).addBox(-1f, -18.2888f, -2.177f, 0, 0, 0, 0.2f, false)

        christmas_light_tree_green = ModelMapper(modelDataWrapper)
        christmas_light_tree_green.setPos(0f, 24f, 0f)
        christmas_light_tree_green.texOffs(70, 391).addBox(1.75f, -19.2888f, -2.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_green.texOffs(70, 391).addBox(-5f, -10.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_green.texOffs(70, 391).addBox(0f, -7.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_green.texOffs(70, 391).addBox(7.5f, -9.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_green.texOffs(70, 391).addBox(4f, -14.2888f, -4.177f, 0, 0, 0, 0.2f, false)

        ct_light_36_r1 = ModelMapper(modelDataWrapper)
        ct_light_36_r1.setPos(0f, 0f, 0f)
        christmas_light_tree_green.addChild(ct_light_36_r1)
        setRotationAngle(ct_light_36_r1, 0f, -1.5708f, 0f)
        ct_light_36_r1.texOffs(70, 391).addBox(4f, -14.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        ct_light_36_r1.texOffs(70, 391).addBox(7.5f, -9.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_36_r1.texOffs(70, 391).addBox(0f, -7.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_36_r1.texOffs(70, 391).addBox(-5f, -10.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_36_r1.texOffs(70, 391).addBox(1.75f, -19.2888f, -2.177f, 0, 0, 0, 0.2f, false)

        christmas_light_tree_blue = ModelMapper(modelDataWrapper)
        christmas_light_tree_blue.setPos(0f, 24f, 0f)
        christmas_light_tree_blue.texOffs(82, 391).addBox(4f, -12.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_blue.texOffs(82, 391).addBox(3f, -8.5388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_blue.texOffs(82, 391).addBox(-7f, -9.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_blue.texOffs(82, 391).addBox(-2f, -11.0388f, -6.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_blue.texOffs(82, 391).addBox(-3f, -15.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        christmas_light_tree_blue.texOffs(82, 391).addBox(-1f, -21.7888f, -2.177f, 0, 0, 0, 0.2f, false)

        ct_light_48_r1 = ModelMapper(modelDataWrapper)
        ct_light_48_r1.setPos(0f, 0f, 0f)
        christmas_light_tree_blue.addChild(ct_light_48_r1)
        setRotationAngle(ct_light_48_r1, 0f, -1.5708f, 0f)
        ct_light_48_r1.texOffs(82, 391).addBox(4f, -12.2888f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_48_r1.texOffs(82, 391).addBox(-1f, -21.7888f, -2.177f, 0, 0, 0, 0.2f, false)
        ct_light_48_r1.texOffs(82, 391).addBox(-3f, -15.2888f, -4.177f, 0, 0, 0, 0.2f, false)
        ct_light_48_r1.texOffs(82, 391).addBox(-2f, -11.0388f, -6.177f, 0, 0, 0, 0.2f, false)
        ct_light_48_r1.texOffs(82, 391).addBox(-7f, -9.0388f, -8.177f, 0, 0, 0, 0.2f, false)
        ct_light_48_r1.texOffs(82, 391).addBox(3f, -8.5388f, -8.177f, 0, 0, 0, 0.2f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window_1.setModelPart()
        window_2.setModelPart()
        window_handrails.setModelPart()
        seats.setModelPart()
        window_1_tv.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        side_panel.setModelPart()
        side_panel_translucent.setModelPart()
        roof_window.setModelPart()
        roof_door.setModelPart()
        roof_light.setModelPart()
        roof_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_handrails.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        end.setModelPart()
        end_exterior.setModelPart()
        roof_end.setModelPart()
        roof_end_light.setModelPart()
        roof_end_exterior.setModelPart()
        roof_end_vents.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        christmas_tree.setModelPart()
        christmas_antler.setModelPart()
        christmas_light_head.setModelPart()
        christmas_light_holder.setModelPart()
        christmas_light_red.setModelPart()
        christmas_light_yellow.setModelPart()
        christmas_light_green.setModelPart()
        christmas_light_blue.setModelPart()
        christmas_light_tree_red.setModelPart()
        christmas_light_tree_yellow.setModelPart()
        christmas_light_tree_green.setModelPart()
        christmas_light_tree_blue.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelMLR {
        return ModelMLR(isChristmas, doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                if (isEvenWindow) {
                    renderOnceFlipped(window_1_tv, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window_1, matrices, vertices, light, position.toFloat())
                    renderOnce(window_2, matrices, vertices, light, position.toFloat())
                } else {
                    renderOnce(window_1_tv, matrices, vertices, light, position.toFloat())
                    renderOnce(window_1, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window_2, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(seats, matrices, vertices, light, position + (if (isEvenWindow) -0.75f else 0.75f))
                    renderMirror(
                        side_panel,
                        matrices,
                        vertices,
                        light,
                        position - (if (isEvenWindow) 15.75f else 14.25f)
                    )
                    renderMirror(
                        side_panel,
                        matrices,
                        vertices,
                        light,
                        position + (if (isEvenWindow) 14.25f else 15.75f)
                    )
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(
                    side_panel_translucent,
                    matrices,
                    vertices,
                    light,
                    position - (if (isEvenWindow) 15.75f else 14.25f)
                )
                renderMirror(
                    side_panel_translucent,
                    matrices,
                    vertices,
                    light,
                    position + (if (isEvenWindow) 14.25f else 15.75f)
                )
            }

            RenderStage.EXTERIOR -> {
                if (isEvenWindow) {
                    renderOnceFlipped(window_exterior_1, matrices, vertices, light, position.toFloat())
                    renderOnce(window_exterior_2, matrices, vertices, light, position.toFloat())
                } else {
                    renderOnce(window_exterior_1, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window_exterior_2, matrices, vertices, light, position.toFloat())
                }
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
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
                renderMirror(roof_light, matrices, vertices, light, position.toFloat())
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
                    renderOnce(door_handrails, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
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
                    renderMirror(door_light, matrices, vertices, light, (position - 22).toFloat())
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 22).toFloat())
                    }
                }
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
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
            RenderStage.ALWAYS_ON_LIGHTS -> {
                renderOnceFlipped(
                    if (useHeadlights) headlights else tail_lights,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
                if (renderDetails && isChristmas) {
                    renderOnceFlipped(christmas_light_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR -> {
                renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
                        renderOnce(christmas_tree, matrices, vertices, light, position.toFloat())
                        renderMirror(christmas_antler, matrices, vertices, light, (position - 30).toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(side_panel, matrices, vertices, light, position + 14.25f)
            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_vents, matrices, vertices, light, (position + 2).toFloat())
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_tree_red,
                christmas_light_tree_yellow,
                christmas_light_tree_green,
                christmas_light_tree_blue,
                light,
                position
            )
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
            RenderStage.ALWAYS_ON_LIGHTS -> {
                renderOnce(
                    if (useHeadlights) headlights else tail_lights,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
                if (renderDetails && isChristmas) {
                    renderOnce(christmas_light_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR -> {
                renderOnce(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
                        renderOnce(christmas_tree, matrices, vertices, light, position.toFloat())
                        renderMirror(christmas_antler, matrices, vertices, light, (position + 30).toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(side_panel, matrices, vertices, light, position - 14.25f)
            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_vents, matrices, vertices, light, (position - 2).toFloat())
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_tree_red,
                christmas_light_tree_yellow,
                christmas_light_tree_green,
                christmas_light_tree_blue,
                light,
                position
            )
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
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(side_panel, matrices, vertices, light, position + 14.25f)
            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_vents, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
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
                    if (isChristmas) {
                        renderMirror(christmas_light_holder, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(side_panel, matrices, vertices, light, position - 14.25f)
            RenderStage.EXTERIOR -> {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_vents, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }

        if (renderDetails && isChristmas) {
            renderChristmasLights(
                matrices,
                vertices,
                renderStage,
                christmas_light_red,
                christmas_light_yellow,
                christmas_light_green,
                christmas_light_blue,
                light,
                position
            )
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
        return intArrayOf(-96, -32, 32, 96)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-128, -64, 0, 64, 128)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-160, 160)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    private fun isEvenWindow(position: Int): Boolean {
        return isIndex(1, position, getWindowPositions()) || isIndex(3, position, getWindowPositions())
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
            0f,
            0f,
            getEndPositions()!![0] / 16f - 2.92f,
            0f,
            -1.81f,
            -0.01f,
            -10f,
            0f,
            0.5f,
            0.26f,
            -0x10000,
            -0x6700,
            2f,
            getDestinationString(lastStation, customDestination, TextSpacingType.MLR_SPACING, true),
            false,
            car,
            totalCars
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "East Rail"
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, 13, "door_overlay_mlr_left.png", "door_overlay_mlr_right.png")
        private val MODEL_DOOR_OVERLAY_TOP = ModelDoorOverlayTopMLR("mtr:textures/block/sign/door_overlay_mlr_top.png")
        private val CHRISTMAS_LIGHT_STAGES = arrayOf<BooleanArray>(
            booleanArrayOf(true, false, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, false, true, false),
            booleanArrayOf(false, false, false, true),
            booleanArrayOf(true, false, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, false, true, false),
            booleanArrayOf(false, false, false, true),

            booleanArrayOf(true, true, false, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, true, true),
            booleanArrayOf(true, false, false, true),
            booleanArrayOf(true, true, false, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, false, true, true),
            booleanArrayOf(true, false, false, true),

            booleanArrayOf(true, false, true, false),
            booleanArrayOf(false, true, false, true),
            booleanArrayOf(true, false, true, false),
            booleanArrayOf(false, true, false, true),
            booleanArrayOf(true, false, true, false),
            booleanArrayOf(false, true, false, true),
            booleanArrayOf(true, false, true, false),
            booleanArrayOf(false, true, false, true),

            booleanArrayOf(true, false, false, false),
            booleanArrayOf(true, true, false, false),
            booleanArrayOf(true, true, true, false),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, true, false),
            booleanArrayOf(false, true, true, true),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(false, false, true, false),
            booleanArrayOf(false, false, true, true),
            booleanArrayOf(true, false, true, true),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(false, false, false, true),
            booleanArrayOf(true, false, false, true),
            booleanArrayOf(true, true, false, true),
            booleanArrayOf(true, true, true, true),

            booleanArrayOf(false, false, false, false),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(false, false, false, false),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(true, true, true, true),
            booleanArrayOf(true, true, true, true),
        )

        private fun renderChristmasLights(
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            renderStage: RenderStage?,
            lightRed: ModelMapper,
            lightYellow: ModelMapper,
            lightGreen: ModelMapper,
            lightBlue: ModelMapper,
            light: Int,
            position: Int
        ) {
            if (renderStage == RenderStage.INTERIOR || renderStage == RenderStage.ALWAYS_ON_LIGHTS) {
                val lights: BooleanArray =
                    CHRISTMAS_LIGHT_STAGES[((System.currentTimeMillis() / 500) % CHRISTMAS_LIGHT_STAGES.size).toInt()]
                if (renderStage == RenderStage.ALWAYS_ON_LIGHTS == lights[0]) {
                    renderMirror(lightRed, matrices, vertices, light / 2, position.toFloat())
                }
                if (renderStage == RenderStage.ALWAYS_ON_LIGHTS == lights[1]) {
                    renderMirror(lightYellow, matrices, vertices, light / 2, position.toFloat())
                }
                if (renderStage == RenderStage.ALWAYS_ON_LIGHTS == lights[2]) {
                    renderMirror(lightGreen, matrices, vertices, light / 2, position.toFloat())
                }
                if (renderStage == RenderStage.ALWAYS_ON_LIGHTS == lights[3]) {
                    renderMirror(lightBlue, matrices, vertices, light / 2, position.toFloat())
                }
            }
        }
    }
}
