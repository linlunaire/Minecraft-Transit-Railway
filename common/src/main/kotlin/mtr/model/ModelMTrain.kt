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

open class ModelMTrain protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelMTrain?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val top_handrail_6_r1: ModelMapper
    private val top_handrail_5_r1: ModelMapper
    private val top_handrail_4_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val top_handrail_2_r1: ModelMapper
    private val top_handrail_1_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_door: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val inner_roof_4_r2: ModelMapper
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
    private val handrail_2_r2: ModelMapper
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
    private val front_panel_r1: ModelMapper
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
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_1_r5: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper

    constructor() : this(DoorAnimationType.BOUNCY_1, true)

    init {
        val textureWidth = 320
        val textureHeight = 320

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 0).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(117, 0).addBox(-20f, -14f, -26f, 2, 14, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(0, 82).addBox(0f, -19f, -26f, 2, 19, 52, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, -21f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(8, 0).addBox(0f, -33f, -9f, 0, 33, 0, 0.2f, false)
        window_handrails.texOffs(8, 0).addBox(0f, -33f, 9f, 0, 33, 0, 0.2f, false)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, 21f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, -3f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(28, 28).addBox(-1f, -32f, -15f, 2, 4, 0, 0f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(0, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        top_handrail_6_r1 = ModelMapper(modelDataWrapper)
        top_handrail_6_r1.setPos(-12.0518f, -29.0895f, 9.5876f)
        window_handrails.addChild(top_handrail_6_r1)
        setRotationAngle(top_handrail_6_r1, 1.5708f, 0f, -0.0436f)
        top_handrail_6_r1.texOffs(0, 0).addBox(0f, -9.5f, 0f, 0, 20, 0, 0.2f, false)

        top_handrail_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_5_r1.setPos(-12.0377f, -28.7666f, 20.7938f)
        window_handrails.addChild(top_handrail_5_r1)
        setRotationAngle(top_handrail_5_r1, 1.0472f, 0f, -0.0436f)
        top_handrail_5_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_4_r1.setPos(-11.9992f, -27.8844f, 21.6768f)
        window_handrails.addChild(top_handrail_4_r1)
        setRotationAngle(top_handrail_4_r1, 0.5236f, 0f, -0.0436f)
        top_handrail_4_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-12.0518f, -29.0895f, -9.5876f)
        window_handrails.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.5708f, 0f, -0.0436f)
        top_handrail_3_r1.texOffs(0, 0).addBox(0f, -9.5f, 0f, 0, 20, 0, 0.2f, false)

        top_handrail_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_2_r1.setPos(-12.0377f, -28.7666f, -20.7938f)
        window_handrails.addChild(top_handrail_2_r1)
        setRotationAngle(top_handrail_2_r1, -1.0472f, 0f, -0.0436f)
        top_handrail_2_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_1_r1 = ModelMapper(modelDataWrapper)
        top_handrail_1_r1.setPos(-11.9992f, -27.8844f, -21.6768f)
        window_handrails.addChild(top_handrail_1_r1)
        setRotationAngle(top_handrail_1_r1, -0.5236f, 0f, -0.0436f)
        top_handrail_1_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-11f, -5f, 0f)
        window_handrails.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, -0.0436f)
        handrail_5_r1.texOffs(0, 0).addBox(0f, -28.2f, -14f, 0, 4, 0, 0.2f, false)
        handrail_5_r1.texOffs(0, 0).addBox(0f, -28.2f, 14f, 0, 4, 0, 0.2f, false)
        handrail_5_r1.texOffs(4, 3).addBox(0f, -22.2f, 22f, 0, 22, 0, 0.2f, false)
        handrail_5_r1.texOffs(4, 1).addBox(0f, -24f, 0f, 0, 24, 0, 0.2f, false)
        handrail_5_r1.texOffs(4, 3).addBox(0f, -22.2f, -22f, 0, 22, 0, 0.2f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrails.addChild(seat)
        seat.texOffs(0, 172).addBox(-18f, -6f, -22f, 7, 1, 44, 0f, false)
        seat.texOffs(106, 170).addBox(-18f, -5f, -21f, 6, 5, 42, 0f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        seat.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(173, 0).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(56, 170).addBox(-21f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior.texOffs(104, 104).addBox(-20f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-20f, -14f, 0f)
        window_exterior.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(0, 101).addBox(0f, -19f, -26f, 0, 19, 52, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(153, 283).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(285, 273).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(38, 82).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window.texOffs(76, 0).addBox(-10f, -34f, -24f, 7, 0, 48, 0f, false)
        roof_window.texOffs(48, 82).addBox(-2f, -33f, -24f, 2, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-2f, -33f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, 0.5236f)
        inner_roof_4_r1.texOffs(44, 82).addBox(-2f, 0f, -24f, 2, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13f, -32f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.5236f)
        inner_roof_2_r1.texOffs(90, 0).addBox(0f, 0f, -24f, 4, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(0, 172).addBox(-19f, -32f, -16f, 6, 0, 32, 0f, false)
        roof_door.texOffs(84, 117).addBox(-10f, -34f, -16f, 7, 0, 32, 0f, false)
        roof_door.texOffs(12, 82).addBox(-2f, -33f, -16f, 2, 0, 32, 0f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(0f, 0f, 0f)
        roof_door.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0f, 0f)
        handrail_2_r1.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-2f, -33f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, 0.5236f)
        inner_roof_4_r2.texOffs(128, 0).addBox(-2f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13f, -32f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.5236f)
        inner_roof_2_r2.texOffs(0, 0).addBox(0f, 0f, -16f, 4, 0, 32, 0f, true)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(133, 0).addBox(-6f, -42f, -20f, 6, 0, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.9394f, -41.3064f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(60, 82).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.1778f, -39.8628f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(0, 0).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-16.9769f, -38.2468f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(0, 82).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.5872f, -36.3872f, 0f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, 0.1107f)
        outer_roof_2_r1.texOffs(0, 182).addBox(0f, -1f, -20f, 0, 2, 40, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(162, 210).addBox(-1f, -22f, -20f, 1, 4, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(185, 95).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(286, 49).addBox(-20.8f, -14f, 0f, 1, 14, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(185, 0).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(34, 264).addBox(-20.8f, -14f, -15f, 1, 14, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 58).addBox(0f, -19f, -15f, 1, 19, 15, 0f, false)

        door_handrail = ModelMapper(modelDataWrapper)
        door_handrail.setPos(0f, 24f, 0f)
        door_handrail.texOffs(8, 0).addBox(0f, -33f, 0f, 0, 33, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(0, 224).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(190, 239).addBox(-20.8f, -14f, 0f, 0, 14, 15, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -14f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(0, 209).addBox(0f, -19f, 0f, 0, 19, 15, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(34, 226).addBox(-20.8f, -14f, -15f, 0, 14, 15, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -14f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(185, 80).addBox(0f, -19f, -15f, 0, 19, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(185, 128).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(0, 92).addBox(18f, -14f, 7f, 2, 14, 3, 0f, true)
        end.texOffs(0, 92).addBox(-20f, -14f, 7f, 2, 14, 3, 0f, false)
        end.texOffs(229, 250).addBox(9.5f, -34f, -12f, 9, 34, 19, 0f, true)
        end.texOffs(226, 149).addBox(-18.5f, -34f, -12f, 9, 34, 19, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(22, 92).addBox(0f, -19f, 7f, 2, 19, 3, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(22, 92).addBox(-2f, -19f, 7f, 2, 19, 3, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(0, 192).addBox(20f, 0f, -12f, 1, 4, 20, 0f, true)
        end_exterior.texOffs(0, 192).addBox(-21f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(44, 279).addBox(18f, -14f, -12f, 2, 14, 22, 0f, true)
        end_exterior.texOffs(44, 279).addBox(-20f, -14f, -12f, 2, 14, 22, 0f, false)
        end_exterior.texOffs(88, 0).addBox(9.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior.texOffs(88, 0).addBox(-18.5f, -34f, -12f, 9, 34, 0, 0f, true)
        end_exterior.texOffs(225, 57).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(97, 264).addBox(0f, -19f, -12f, 2, 19, 22, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(97, 264).addBox(-2f, -19f, -12f, 2, 19, 22, 0f, true)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(0f, 0f, 0f)
        roof_end.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, -1.5708f, 0f, 0f)
        handrail_2_r2.texOffs(0, 0).addBox(0f, -40f, -31.5f, 0, 16, 0, 0.2f, false)

        inner_roof_7_r1 = ModelMapper(modelDataWrapper)
        inner_roof_7_r1.setPos(0f, -33f, 16f)
        roof_end.addChild(inner_roof_7_r1)
        setRotationAngle(inner_roof_7_r1, -0.5236f, 0f, 0f)
        inner_roof_7_r1.texOffs(6, 49).addBox(-2f, 0f, -2f, 4, 0, 2, 0f, false)

        inner_roof_1 = ModelMapper(modelDataWrapper)
        inner_roof_1.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_1)
        inner_roof_1.texOffs(32, 82).addBox(-17f, 1f, -12f, 6, 0, 36, 0f, false)
        inner_roof_1.texOffs(81, 66).addBox(-8f, -1f, -28f, 10, 0, 52, 0f, false)
        inner_roof_1.texOffs(0, 49).addBox(0f, 0f, 0f, 2, 0, 24, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(0f, 0f, 0f)
        inner_roof_1.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, -0.5236f, 0f, 0.5236f)
        inner_roof_6_r1.texOffs(6, 51).addBox(-2f, 0f, -2f, 2, 0, 2, 0f, false)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(0f, 0f, -16f)
        inner_roof_1.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, 0.5236f)
        inner_roof_4_r3.texOffs(4, 49).addBox(-2f, 0f, 16f, 2, 0, 24, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-11f, 1f, -16f)
        inner_roof_1.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.5236f)
        inner_roof_2_r3.texOffs(78, 0).addBox(0f, 0f, 4f, 4, 0, 36, 0f, true)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(32, 82).addBox(15f, 1f, -12f, 6, 0, 36, 0f, true)
        inner_roof_2.texOffs(81, 66).addBox(2f, -1f, -28f, 10, 0, 52, 0f, true)
        inner_roof_2.texOffs(0, 49).addBox(2f, 0f, 0f, 2, 0, 24, 0f, true)

        inner_roof_6_r2 = ModelMapper(modelDataWrapper)
        inner_roof_6_r2.setPos(4f, 0f, 0f)
        inner_roof_2.addChild(inner_roof_6_r2)
        setRotationAngle(inner_roof_6_r2, -0.5236f, 0f, -0.5236f)
        inner_roof_6_r2.texOffs(6, 51).addBox(0f, 0f, -2f, 2, 0, 2, 0f, true)

        inner_roof_4_r4 = ModelMapper(modelDataWrapper)
        inner_roof_4_r4.setPos(4f, 0f, -16f)
        inner_roof_2.addChild(inner_roof_4_r4)
        setRotationAngle(inner_roof_4_r4, 0f, 0f, -0.5236f)
        inner_roof_4_r4.texOffs(4, 49).addBox(0f, 0f, 16f, 2, 0, 24, 0f, true)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(15f, 1f, -16f)
        inner_roof_2.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, 0.5236f)
        inner_roof_2_r4.texOffs(70, 0).addBox(-4f, 0f, 4f, 4, 0, 36, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(105, 105).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -43f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(160, 160).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -43f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(160, 160).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(147, 293).addBox(-6f, -42f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.7656f, -40.3206f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(262, 182).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-14.6775f, -38.9948f, 0f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(259, 64).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.1105f, -37.7448f, 0f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(0, 289).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-17.587f, -36.3849f, 0f)
        outer_roof_1.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, 0.1107f)
        outer_roof_2_r2.texOffs(110, 129).addBox(0f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-20f, -14f, 0f)
        outer_roof_1.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, 0.1107f)
        outer_roof_1_r2.texOffs(185, 268).addBox(-1f, -22f, -12f, 1, 4, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(147, 293).addBox(0f, -42f, -12f, 6, 1, 20, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.7656f, -40.3206f, 0f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.1745f)
        outer_roof_5_r3.texOffs(262, 182).addBox(-4f, -1f, -12f, 8, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(14.6775f, -38.9948f, 0f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.5236f)
        outer_roof_4_r3.texOffs(259, 64).addBox(-1.5f, -1f, -12f, 3, 1, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.1105f, -37.7448f, 0f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.0472f)
        outer_roof_3_r3.texOffs(0, 289).addBox(-1f, -1f, -12f, 2, 1, 20, 0f, true)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(17.587f, -36.3849f, 0f)
        outer_roof_2.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -0.1107f)
        outer_roof_2_r3.texOffs(110, 129).addBox(-1f, -1f, -12f, 1, 2, 20, 0f, true)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(20f, -14f, 0f)
        outer_roof_2.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, -0.1107f)
        outer_roof_1_r3.texOffs(185, 268).addBox(0f, -22f, -12f, 1, 4, 20, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(-2f, -33f, 0f)
        roof_light.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, 0.5236f)
        roof_light_r1.texOffs(0, 82).addBox(-2f, -0.1f, -24f, 2, 0, 48, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        light_5_r1 = ModelMapper(modelDataWrapper)
        light_5_r1.setPos(2f, -33f, 0f)
        roof_end_light.addChild(light_5_r1)
        setRotationAngle(light_5_r1, 0f, 0f, -0.5236f)
        light_5_r1.texOffs(24, 82).addBox(0f, -0.1f, 16f, 2, 0, 24, 0f, false)

        light_4_r1 = ModelMapper(modelDataWrapper)
        light_4_r1.setPos(2f, -33f, 16f)
        roof_end_light.addChild(light_4_r1)
        setRotationAngle(light_4_r1, -0.5236f, 0f, -0.5236f)
        light_4_r1.texOffs(10, 51).addBox(0f, -0.1f, -2f, 2, 0, 2, 0f, true)

        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(0f, -33f, 16f)
        roof_end_light.addChild(light_3_r1)
        setRotationAngle(light_3_r1, -0.5236f, 0f, 0f)
        light_3_r1.texOffs(14, 49).addBox(-2f, -0.1f, -2f, 4, 0, 2, 0f, false)

        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(-2f, -33f, 16f)
        roof_end_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, -0.5236f, 0f, 0.5236f)
        light_2_r1.texOffs(10, 51).addBox(-2f, -0.1f, -2f, 2, 0, 2, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-2f, -33f, 0f)
        roof_end_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, 0.5236f)
        light_1_r1.texOffs(24, 82).addBox(-2f, -0.1f, 16f, 2, 0, 24, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(219, 35).addBox(-20f, 0f, 4f, 40, 1, 4, 0f, false)
        head.texOffs(185, 128).addBox(18f, -14f, 4f, 2, 14, 6, 0f, true)
        head.texOffs(185, 128).addBox(-20f, -14f, 4f, 2, 14, 6, 0f, false)
        head.texOffs(80, 222).addBox(-18f, -34f, 4f, 36, 34, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(132, 118).addBox(0f, -19f, 4f, 2, 19, 6, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1107f)
        upper_wall_1_r3.texOffs(132, 118).addBox(-2f, -19f, 4f, 2, 19, 6, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(153, 66).addBox(-21f, 0f, -18f, 42, 7, 22, 0f, false)
        head_exterior.texOffs(153, 77).addBox(20f, 0f, 4f, 1, 7, 4, 0f, true)
        head_exterior.texOffs(153, 77).addBox(-21f, 0f, 4f, 1, 7, 4, 0f, false)
        head_exterior.texOffs(51, 241).addBox(18f, -14f, -9f, 2, 14, 19, 0f, true)
        head_exterior.texOffs(51, 241).addBox(-20f, -14f, -9f, 2, 14, 19, 0f, false)
        head_exterior.texOffs(276, 86).addBox(18f, -14f, -18f, 1, 14, 9, 0f, true)
        head_exterior.texOffs(276, 86).addBox(-19f, -14f, -18f, 1, 14, 9, 0f, false)
        head_exterior.texOffs(219, 0).addBox(-18f, -34f, 3f, 36, 34, 0, 0f, false)

        driver_door_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_2_r1.setPos(-20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_2_r1)
        setRotationAngle(driver_door_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_upper_2_r1.texOffs(0, 172).addBox(1f, -19f, -18f, 1, 19, 9, 0f, false)
        driver_door_upper_2_r1.texOffs(160, 170).addBox(0f, -19f, -9f, 2, 19, 19, 0f, false)

        driver_door_upper_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_upper_1_r1.setPos(20f, -14f, 0f)
        head_exterior.addChild(driver_door_upper_1_r1)
        setRotationAngle(driver_door_upper_1_r1, 0f, 0f, -0.1107f)
        driver_door_upper_1_r1.texOffs(0, 172).addBox(-2f, -19f, -18f, 1, 19, 9, 0f, true)
        driver_door_upper_1_r1.texOffs(160, 170).addBox(-2f, -19f, -9f, 2, 19, 19, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(225, 52).addBox(-19f, 0f, -28f, 38, 5, 0, 0f, false)

        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(0f, 7f, 4f)
        front.addChild(bottom_r1)
        setRotationAngle(bottom_r1, -0.0698f, 0f, 0f)
        bottom_r1.texOffs(0, 49).addBox(-21f, 0f, -33f, 42, 0, 33, 0f, false)

        front_middle_top_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_r1.setPos(0f, -42f, -12f)
        front.addChild(front_middle_top_r1)
        setRotationAngle(front_middle_top_r1, 0.3491f, 0f, 0f)
        front_middle_top_r1.texOffs(143, 95).addBox(-6f, 0f, -10f, 12, 0, 10, 0f, false)

        front_panel_r1 = ModelMapper(modelDataWrapper)
        front_panel_r1.setPos(0f, 0f, -28f)
        front.addChild(front_panel_r1)
        setRotationAngle(front_panel_r1, -0.1745f, 0f, 0f)
        front_panel_r1.texOffs(204, 210).addBox(-19f, -40f, 0f, 38, 40, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)
        side_1.texOffs(22, 0).addBox(19f, -14f, -18f, 1, 14, 0, 0f, true)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(21f, 0f, -13f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, 0.1745f, 0.1745f)
        front_side_bottom_1_r1.texOffs(263, 17).addBox(0f, 0f, -16f, 0, 7, 23, 0f, true)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(6f, -42f, -12f)
        side_1.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0.3491f, 0f, 0.1745f)
        outer_roof_4_r4.texOffs(142, 66).addBox(0f, 0f, -11f, 11, 0, 11, 0f, true)

        outer_roof_1_r4 = ModelMapper(modelDataWrapper)
        outer_roof_1_r4.setPos(20f, -14f, 0f)
        side_1.addChild(outer_roof_1_r4)
        setRotationAngle(outer_roof_1_r4, 0f, 0f, -0.1107f)
        outer_roof_1_r4.texOffs(0, 49).addBox(0f, -22f, -18f, 1, 4, 6, 0f, true)
        outer_roof_1_r4.texOffs(46, 0).addBox(-1f, -19f, -18f, 1, 19, 0, 0f, true)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(17.587f, -36.3849f, 0f)
        side_1.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, -0.1107f)
        outer_roof_2_r4.texOffs(24, 26).addBox(0f, -1f, -18f, 0, 2, 6, 0f, true)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(15.813f, -37.5414f, -17.4163f)
        side_1.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0.1745f, 0f, 0.7418f)
        outer_roof_3_r4.texOffs(89, 122).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, true)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(20f, 0f, -18f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, 0.1745f, 0f)
        front_side_lower_1_r1.texOffs(207, 257).addBox(0f, -14f, -11f, 0, 20, 11, 0f, true)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(20f, -14f, -18f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0.1745f, -0.1107f)
        front_side_upper_1_r1.texOffs(93, 245).addBox(0f, -23f, -11f, 0, 23, 11, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(-21f, 0f, -9f)
        front.addChild(side_2)
        side_2.texOffs(22, 0).addBox(1f, -14f, -9f, 1, 14, 0, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(0f, 0f, -4f)
        side_2.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, -0.1745f, -0.1745f)
        front_side_bottom_2_r1.texOffs(263, 17).addBox(0f, 0f, -16f, 0, 7, 23, 0f, false)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(5.187f, -37.5414f, -8.4163f)
        side_2.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0.1745f, 0f, -0.7418f)
        outer_roof_8_r1.texOffs(89, 122).addBox(-3.5f, 0f, -5.5f, 7, 0, 11, 0f, false)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(3.413f, -36.3849f, 9f)
        side_2.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, 0.1107f)
        outer_roof_7_r1.texOffs(24, 26).addBox(0f, -1f, -18f, 0, 2, 6, 0f, false)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(15f, -42f, -3f)
        side_2.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0.3491f, 0f, -0.1745f)
        outer_roof_6_r1.texOffs(142, 66).addBox(-11f, 0f, -11f, 11, 0, 11, 0f, false)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(1f, -14f, 9f)
        side_2.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, 0.1107f)
        outer_roof_5_r4.texOffs(0, 49).addBox(-1f, -22f, -18f, 1, 4, 6, 0f, false)
        outer_roof_5_r4.texOffs(46, 0).addBox(0f, -19f, -18f, 1, 19, 0, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(1f, -14f, -9f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, -0.1745f, 0.1107f)
        front_side_upper_2_r1.texOffs(93, 245).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(1f, 0f, -9f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, -0.1745f, 0f)
        front_side_lower_2_r1.texOffs(207, 257).addBox(0f, -14f, -11f, 0, 20, 11, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        headlights.texOffs(102, 36).addBox(8f, -1f, -28.1f, 4, 4, 0, 0f, true)
        headlights.texOffs(102, 36).addBox(-12f, -1f, -28.1f, 4, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        tail_lights.texOffs(110, 36).addBox(10f, -1f, -28.1f, 4, 4, 0, 0f, true)
        tail_lights.texOffs(110, 36).addBox(-14f, -1f, -28.1f, 4, 4, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_1_r5 = ModelMapper(modelDataWrapper)
        outer_roof_1_r5.setPos(-20f, -14f, 0f)
        door_light.addChild(outer_roof_1_r5)
        setRotationAngle(outer_roof_1_r5, 0f, 0f, 0.1107f)
        outer_roof_1_r5.texOffs(24, 34).addBox(-1.1f, -22f, -2f, 0, 4, 4, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(22, 39).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(22, 41).addBox(-1f, -20f, 0f, 0, 0, 0, 0.4f, false)

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
        roof_end.setModelPart()
        roof_end_exterior.setModelPart()
        roof_light.setModelPart()
        roof_end_light.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelMTrain {
        return ModelMTrain(doorAnimationType, renderDoorOverlay)
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
            getEndPositions()!![0] / 16f - 1.75f,
            0f,
            -1.97f,
            -0.01f,
            -10f,
            0f,
            0.37f,
            0.21f,
            -0x6700,
            -0x10000,
            3f,
            getDestinationString(lastStation, customDestination, TextSpacingType.SPACE_CJK, true),
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
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_m_train_left.png", "door_overlay_m_train_right.png")
    }
}
