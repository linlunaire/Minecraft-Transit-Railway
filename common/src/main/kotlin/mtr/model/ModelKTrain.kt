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

open class ModelKTrain protected constructor(
    @JvmField protected val isTcl: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelKTrain?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_handrail: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val top_handrail_6_r1: ModelMapper
    private val top_handrail_5_r1: ModelMapper
    private val top_handrail_4_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val top_handrail_2_r1: ModelMapper
    private val top_handrail_1_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_exterior_end: ModelMapper
    private val upper_wall_r3: ModelMapper
    private val upper_wall_r4: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_handrail: ModelMapper
    private val door_exterior: ModelMapper
    private val upper_wall_r5: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_exterior_end: ModelMapper
    private val upper_wall_r6: ModelMapper
    private val door_left_exterior_end: ModelMapper
    private val door_left_top_r3: ModelMapper
    private val door_right_exterior_end: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val lower_wall_1_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_3_r3: ModelMapper
    private val inner_roof_2: ModelMapper
    private val inner_roof_4_r4: ModelMapper
    private val inner_roof_3_r4: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val roof_light: ModelMapper
    private val roof_light_r1: ModelMapper
    private val roof_end_light: ModelMapper
    private val roof_light_2_r1: ModelMapper
    private val roof_light_1_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val lower_wall_1_r2: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val front: ModelMapper
    private val front_bottom_2_r1: ModelMapper
    private val front_panel_4_r1: ModelMapper
    private val front_panel_3_r1: ModelMapper
    private val front_panel_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_3_r4: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val front_side_bottom_1_r1: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r5: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper
    private val door_light: ModelMapper
    private val outer_roof_3_r6: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper

    constructor(isTcl: Boolean) : this(isTcl, DoorAnimationType.PLUG_SLOW, true)

    init {
        val textureWidth = 320
        val textureHeight = 320

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 42).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-21f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(0, 129).addBox(1f, -19f, -22f, 2, 19, 44, 0f, false)

        window_handrail = ModelMapper(modelDataWrapper)
        window_handrail.setPos(0f, 24f, 0f)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, -21f, 2, 4, 0, 0f, false)
        window_handrail.texOffs(148, 169).addBox(-21f, -14f, 21f, 4, 14, 6, 0f, false)
        window_handrail.texOffs(168, 52).addBox(-21f, -14f, -27f, 4, 14, 6, 0f, false)
        window_handrail.texOffs(8, 0).addBox(0f, -35f, -9f, 0, 35, 0, 0.2f, false)
        window_handrail.texOffs(8, 0).addBox(0f, -35f, 9f, 0, 35, 0, 0.2f, false)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, 21f, 2, 4, 0, 0f, false)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, 15f, 2, 4, 0, 0f, false)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, 3f, 2, 4, 0, 0f, false)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, -3f, 2, 4, 0, 0f, false)
        window_handrail.texOffs(29, 0).addBox(-1f, -32f, -15f, 2, 4, 0, 0f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_handrail.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(0, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        top_handrail_6_r1 = ModelMapper(modelDataWrapper)
        top_handrail_6_r1.setPos(-12.0518f, -29.0895f, 9.5876f)
        window_handrail.addChild(top_handrail_6_r1)
        setRotationAngle(top_handrail_6_r1, 1.5708f, 0f, -0.0436f)
        top_handrail_6_r1.texOffs(0, 0).addBox(0f, -9.5f, 0f, 0, 20, 0, 0.2f, false)

        top_handrail_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_5_r1.setPos(-12.0377f, -28.7666f, 20.7938f)
        window_handrail.addChild(top_handrail_5_r1)
        setRotationAngle(top_handrail_5_r1, 1.0472f, 0f, -0.0436f)
        top_handrail_5_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_4_r1.setPos(-11.9992f, -27.8844f, 21.6768f)
        window_handrail.addChild(top_handrail_4_r1)
        setRotationAngle(top_handrail_4_r1, 0.5236f, 0f, -0.0436f)
        top_handrail_4_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-12.0518f, -29.0895f, -9.5876f)
        window_handrail.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.5708f, 0f, -0.0436f)
        top_handrail_3_r1.texOffs(0, 0).addBox(0f, -9.5f, 0f, 0, 20, 0, 0.2f, false)

        top_handrail_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_2_r1.setPos(-12.0377f, -28.7666f, -20.7938f)
        window_handrail.addChild(top_handrail_2_r1)
        setRotationAngle(top_handrail_2_r1, -1.0472f, 0f, -0.0436f)
        top_handrail_2_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_1_r1 = ModelMapper(modelDataWrapper)
        top_handrail_1_r1.setPos(-11.9992f, -27.8844f, -21.6768f)
        window_handrail.addChild(top_handrail_1_r1)
        setRotationAngle(top_handrail_1_r1, -0.5236f, 0f, -0.0436f)
        top_handrail_1_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-11f, -5f, 0f)
        window_handrail.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, -0.0436f)
        handrail_5_r1.texOffs(0, 0).addBox(0f, -28.2f, -14f, 0, 4, 0, 0.2f, false)
        handrail_5_r1.texOffs(0, 0).addBox(0f, -28.2f, 14f, 0, 4, 0, 0.2f, false)
        handrail_5_r1.texOffs(4, 0).addBox(0f, -22.2f, 22f, 0, 22, 0, 0.2f, false)
        handrail_5_r1.texOffs(4, 0).addBox(0f, -22.2f, -22f, 0, 22, 0, 0.2f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-21f, -14f, 0f)
        window_handrail.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(120, 166).addBox(0f, -19f, -27f, 4, 19, 5, 0f, false)
        upper_wall_2_r1.texOffs(211, 91).addBox(0f, -19f, 22f, 4, 19, 5, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 0f, 0f)
        window_handrail.addChild(seat)
        seat.texOffs(148, 145).addBox(-18f, -6f, -22f, 7, 1, 44, 0f, false)
        seat.texOffs(188, 73).addBox(-18f, -5f, -21f, 5, 5, 42, 0f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        seat.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(168, 0).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(98, 145).addBox(-21f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior.texOffs(0, 63).addBox(-21f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-21f, -14f, 0f)
        window_exterior.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(0, 41).addBox(0f, -22f, -26f, 0, 22, 52, 0f, false)

        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(0, 41).addBox(0f, -22f, -26f, 0, 22, 52, 0f, false)

        window_exterior_end = ModelMapper(modelDataWrapper)
        window_exterior_end.setPos(0f, 24f, 0f)
        window_exterior_end.texOffs(87, 258).addBox(-21f, 0f, -24f, 1, 4, 48, 0f, false)
        window_exterior_end.texOffs(137, 228).addBox(-21f, -14f, -26f, 0, 14, 52, 0f, false)
        window_exterior_end.texOffs(87, 258).addBox(20f, 0f, -24f, 1, 4, 48, 0f, true)
        window_exterior_end.texOffs(137, 228).addBox(21f, -14f, -26f, 0, 14, 52, 0f, true)

        upper_wall_r3 = ModelMapper(modelDataWrapper)
        upper_wall_r3.setPos(21f, -14f, 0f)
        window_exterior_end.addChild(upper_wall_r3)
        setRotationAngle(upper_wall_r3, 0f, 0f, -0.1107f)
        upper_wall_r3.texOffs(137, 206).addBox(0f, -22f, -26f, 0, 22, 52, 0f, true)

        upper_wall_r4 = ModelMapper(modelDataWrapper)
        upper_wall_r4.setPos(-21f, -14f, 0f)
        window_exterior_end.addChild(upper_wall_r4)
        setRotationAngle(upper_wall_r4, 0f, 0f, 0.1107f)
        upper_wall_r4.texOffs(137, 206).addBox(0f, -22f, -26f, 0, 22, 52, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(30, 143).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(293, 163).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(40, 42).addBox(-16f, -32f, -24f, 4, 0, 48, 0f, false)
        roof_window.texOffs(68, 42).addBox(-3f, -34.5f, -24f, 3, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-5.5473f, -34.2615f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.0873f)
        inner_roof_4_r1.texOffs(56, 42).addBox(-3f, 0f, -24f, 6, 0, 48, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-12f, -32f, 0f)
        roof_window.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.5236f)
        inner_roof_3_r1.texOffs(48, 42).addBox(0f, 0f, -24f, 4, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(132, 10).addBox(-18f, -33f, -16f, 6, 1, 32, 0f, false)
        roof_door.texOffs(84, 50).addBox(-3f, -34.5f, -16f, 3, 0, 32, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-5.5473f, -34.2615f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.0873f)
        inner_roof_4_r2.texOffs(72, 50).addBox(-3f, 0f, -16f, 6, 0, 32, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-12f, -32f, 0f)
        roof_door.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.5236f)
        inner_roof_3_r2.texOffs(128, 93).addBox(0f, 0f, -16f, 4, 0, 32, 0f, true)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(0f, 0f, 0f)
        roof_door.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0f, 0f)
        handrail_2_r1.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(82, 43).addBox(-5.728f, -41.8527f, -20f, 6, 0, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.6672f, -41.1581f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(82, 93).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.3385f, -39.4635f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(0, 42).addBox(-2f, 0f, -20f, 4, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-17.8206f, -37.1645f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(98, 93).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(164, 190).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(116, 200).addBox(-21f, -14f, 0f, 1, 14, 14, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-21f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(274, 59).addBox(0f, -19f, 0f, 1, 19, 14, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(0, 192).addBox(-21f, -14f, -14f, 1, 14, 14, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-21f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(178, 0).addBox(0f, -19f, -14f, 1, 19, 14, 0f, false)

        door_handrail = ModelMapper(modelDataWrapper)
        door_handrail.setPos(0f, 24f, 0f)
        door_handrail.texOffs(8, 0).addBox(0f, -35f, 0f, 0, 35, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(114, 161).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        upper_wall_r5 = ModelMapper(modelDataWrapper)
        upper_wall_r5.setPos(-21f, -14f, 0f)
        door_exterior.addChild(upper_wall_r5)
        setRotationAngle(upper_wall_r5, 0f, 0f, 0.1107f)
        upper_wall_r5.texOffs(77, 259).addBox(0f, -22f, -14f, 1, 3, 28, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(0, 135).addBox(-21f, -14f, 0f, 0, 14, 14, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-21f, -14f, 0f)
        door_left_exterior.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(0, 115).addBox(0f, -20f, 0f, 0, 20, 14, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(262, 229).addBox(-21f, -14f, -14f, 0, 14, 14, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-21f, -14f, 0f)
        door_right_exterior.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(262, 209).addBox(0f, -20f, -14f, 0, 20, 14, 0f, false)

        door_exterior_end = ModelMapper(modelDataWrapper)
        door_exterior_end.setPos(0f, 24f, 0f)
        door_exterior_end.texOffs(0, 282).addBox(-21f, 0f, -16f, 1, 4, 32, 0f, false)

        upper_wall_r6 = ModelMapper(modelDataWrapper)
        upper_wall_r6.setPos(-21f, -14f, 0f)
        door_exterior_end.addChild(upper_wall_r6)
        setRotationAngle(upper_wall_r6, 0f, 0f, 0.1107f)
        upper_wall_r6.texOffs(77, 259).addBox(0f, -22f, -14f, 1, 3, 28, 0f, false)

        door_left_exterior_end = ModelMapper(modelDataWrapper)
        door_left_exterior_end.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_left_exterior_end)
        door_left_exterior_end.texOffs(0, 268).addBox(-21f, -14f, 0f, 0, 14, 14, 0f, false)

        door_left_top_r3 = ModelMapper(modelDataWrapper)
        door_left_top_r3.setPos(-21f, -14f, 0f)
        door_left_exterior_end.addChild(door_left_top_r3)
        setRotationAngle(door_left_top_r3, 0f, 0f, 0.1107f)
        door_left_top_r3.texOffs(0, 115).addBox(0f, -20f, 0f, 0, 20, 14, 0f, false)

        door_right_exterior_end = ModelMapper(modelDataWrapper)
        door_right_exterior_end.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_right_exterior_end)
        door_right_exterior_end.texOffs(0, 282).addBox(-21f, -14f, -14f, 0, 14, 14, 0f, false)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(-21f, -14f, 0f)
        door_right_exterior_end.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, 0.1107f)
        door_right_top_r3.texOffs(262, 209).addBox(0f, -20f, -14f, 0, 20, 14, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(168, 52).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(148, 169).addBox(-21f, -14f, 5f, 4, 14, 6, 0f, false)
        end.texOffs(51, 234).addBox(9.5f, -34f, -12f, 8, 34, 19, 0f, true)
        end.texOffs(51, 234).addBox(-17.5f, -34f, -12f, 8, 34, 19, 0f, false)
        end.texOffs(236, 174).addBox(-9.5f, -35f, -12f, 19, 2, 19, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-21f, -14f, 0f)
        end.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(211, 91).addBox(0f, -19f, 6f, 4, 19, 5, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(21f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 3.1416f, -0.1107f)
        upper_wall_1_r1.texOffs(120, 166).addBox(0f, -19f, -11f, 4, 19, 5, 0f, false)

        lower_wall_1_r1 = ModelMapper(modelDataWrapper)
        lower_wall_1_r1.setPos(0f, 0f, 0f)
        end.addChild(lower_wall_1_r1)
        setRotationAngle(lower_wall_1_r1, 0f, 3.1416f, 0f)
        lower_wall_1_r1.texOffs(168, 52).addBox(-21f, -14f, -11f, 4, 14, 6, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(148, 145).addBox(20f, 0f, -12f, 1, 4, 20, 0f, true)
        end_exterior.texOffs(148, 145).addBox(-21f, 0f, -12f, 1, 4, 20, 0f, false)
        end_exterior.texOffs(0, 246).addBox(18f, -14f, -12f, 3, 14, 22, 0f, true)
        end_exterior.texOffs(0, 246).addBox(-21f, -14f, -12f, 3, 14, 22, 0f, false)
        end_exterior.texOffs(248, 120).addBox(9.5f, -34f, -12f, 10, 34, 0, 0f, true)
        end_exterior.texOffs(248, 120).addBox(-19.5f, -34f, -12f, 10, 34, 0, 0f, false)
        end_exterior.texOffs(105, 248).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-21f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(268, 98).addBox(0f, -22f, -12f, 3, 22, 22, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(21f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(268, 98).addBox(-3f, -22f, -12f, 3, 22, 22, 0f, true)

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
        inner_roof_1.texOffs(128, 6).addBox(-16f, 0f, -12f, 6, 1, 36, 0f, false)
        inner_roof_1.texOffs(80, 42).addBox(-1f, -1.5f, -12f, 3, 0, 36, 0f, false)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(-3.5473f, -1.2615f, -16f)
        inner_roof_1.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, -0.0873f)
        inner_roof_4_r3.texOffs(68, 42).addBox(-3f, 0f, 4f, 6, 0, 36, 0f, false)

        inner_roof_3_r3 = ModelMapper(modelDataWrapper)
        inner_roof_3_r3.setPos(-10f, 1f, -16f)
        inner_roof_1.addChild(inner_roof_3_r3)
        setRotationAngle(inner_roof_3_r3, 0f, 0f, -0.5236f)
        inner_roof_3_r3.texOffs(116, 93).addBox(0f, 0f, 4f, 4, 0, 36, 0f, true)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(80, 42).addBox(2f, -1.5f, -12f, 3, 0, 36, 0f, true)
        inner_roof_2.texOffs(128, 6).addBox(14f, 0f, -12f, 6, 1, 36, 0f, true)

        inner_roof_4_r4 = ModelMapper(modelDataWrapper)
        inner_roof_4_r4.setPos(7.5473f, -1.2615f, -16f)
        inner_roof_2.addChild(inner_roof_4_r4)
        setRotationAngle(inner_roof_4_r4, 0f, 0f, 0.0873f)
        inner_roof_4_r4.texOffs(68, 42).addBox(-3f, 0f, 4f, 6, 0, 36, 0f, true)

        inner_roof_3_r4 = ModelMapper(modelDataWrapper)
        inner_roof_3_r4.setPos(14f, 1f, -16f)
        inner_roof_2.addChild(inner_roof_3_r4)
        setRotationAngle(inner_roof_3_r4, 0f, 0f, 0.5236f)
        inner_roof_3_r4.texOffs(108, 93).addBox(-4f, 0f, 4f, 4, 0, 36, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(88, 43).addBox(-8f, -43f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -43f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(56, 93).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -43f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(56, 93).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(188, 120).addBox(-5.7289f, -41.8532f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.4945f, -40.1738f, -8f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(38, 193).addBox(-4f, -1f, -4f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-14.4064f, -38.848f, -8f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(0, 62).addBox(-2.5f, -1f, -4f, 4, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.7054f, -37.098f, -8f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(98, 145).addBox(-2f, -1f, -4f, 3, 1, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(188, 120).addBox(-0.2711f, -41.8532f, -12f, 6, 1, 20, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.4945f, -40.1738f, -8f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.1745f)
        outer_roof_5_r3.texOffs(38, 193).addBox(-4f, -1f, -4f, 8, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(15.2724f, -38.348f, -8f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 0.5236f)
        outer_roof_4_r3.texOffs(0, 62).addBox(-2.5f, -1f, -4f, 4, 1, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.7054f, -37.098f, -8f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 1.0472f)
        outer_roof_3_r3.texOffs(98, 145).addBox(-1f, -1f, -4f, 3, 1, 20, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(-5.8f, -33.8f, 0f)
        roof_light.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, 0.1309f)
        roof_light_r1.texOffs(44, 144).addBox(-3f, -1f, -24f, 3, 1, 48, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        roof_light_2_r1 = ModelMapper(modelDataWrapper)
        roof_light_2_r1.setPos(-5.8f, -33.8f, 0f)
        roof_end_light.addChild(roof_light_2_r1)
        setRotationAngle(roof_light_2_r1, 0f, 0f, 0.1309f)
        roof_light_2_r1.texOffs(56, 156).addBox(-3f, -1f, 4f, 3, 1, 36, 0f, false)

        roof_light_1_r1 = ModelMapper(modelDataWrapper)
        roof_light_1_r1.setPos(5.8f, -33.8f, 0f)
        roof_end_light.addChild(roof_light_1_r1)
        setRotationAngle(roof_light_1_r1, 0f, 0f, -0.1309f)
        roof_light_1_r1.texOffs(56, 156).addBox(0f, -1f, 4f, 3, 1, 36, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(184, 73).addBox(-20f, 0f, 4f, 40, 1, 4, 0f, false)
        head.texOffs(70, 143).addBox(-21f, -14f, 4f, 4, 14, 7, 0f, false)
        head.texOffs(190, 223).addBox(-18f, -35f, 4f, 36, 35, 0, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-21f, -14f, 0f)
        head.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(48, 143).addBox(0f, -19f, 4f, 4, 19, 7, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(21f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 3.1416f, -0.1107f)
        upper_wall_1_r3.texOffs(170, 139).addBox(0f, -19f, -11f, 4, 19, 7, 0f, false)

        lower_wall_1_r2 = ModelMapper(modelDataWrapper)
        lower_wall_1_r2.setPos(0f, 0f, 0f)
        head.addChild(lower_wall_1_r2)
        setRotationAngle(lower_wall_1_r2, 0f, 3.1416f, 0f)
        lower_wall_1_r2.texOffs(158, 200).addBox(-21f, -14f, -11f, 4, 14, 7, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(10, 0).addBox(-21f, 0f, -31f, 42, 7, 35, 0f, false)
        head_exterior.texOffs(176, 0).addBox(20f, 0f, 4f, 1, 4, 4, 0f, true)
        head_exterior.texOffs(176, 0).addBox(-21f, 0f, 4f, 1, 4, 4, 0f, false)
        head_exterior.texOffs(120, 202).addBox(18f, -14f, -22f, 3, 14, 32, 0f, true)
        head_exterior.texOffs(120, 202).addBox(-21f, -14f, -22f, 3, 14, 32, 0f, false)
        head_exterior.texOffs(214, 0).addBox(-18f, -41f, 3f, 36, 41, 0, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(-21f, -14f, 0f)
        head_exterior.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, 0.1107f)
        upper_wall_2_r5.texOffs(0, 192).addBox(0f, -22f, -22f, 3, 22, 32, 0f, false)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(21f, -14f, 0f)
        head_exterior.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, -0.1107f)
        upper_wall_1_r4.texOffs(0, 192).addBox(-3f, -22f, -22f, 3, 22, 32, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(236, 215).addBox(-20f, -8.8978f, -31.2245f, 40, 6, 0, 0f, false)
        front.texOffs(0, 91).addBox(-20f, 0f, -32f, 40, 2, 0, 0f, false)

        front_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_bottom_2_r1.setPos(0f, 2f, -32f)
        front.addChild(front_bottom_2_r1)
        setRotationAngle(front_bottom_2_r1, 0.3491f, 0f, 0f)
        front_bottom_2_r1.texOffs(236, 209).addBox(-20f, 0f, 0f, 40, 6, 0, 0f, false)

        front_panel_4_r1 = ModelMapper(modelDataWrapper)
        front_panel_4_r1.setPos(0f, -29.5396f, -27.5854f)
        front.addChild(front_panel_4_r1)
        setRotationAngle(front_panel_4_r1, -0.2618f, 0f, 0f)
        front_panel_4_r1.texOffs(206, 156).addBox(-20f, -11f, 0f, 40, 18, 0, 0f, false)

        front_panel_3_r1 = ModelMapper(modelDataWrapper)
        front_panel_3_r1.setPos(0f, -15.8379f, -30.3109f)
        front.addChild(front_panel_3_r1)
        setRotationAngle(front_panel_3_r1, -0.1309f, 0f, 0f)
        front_panel_3_r1.texOffs(236, 195).addBox(-20f, -7f, 0f, 40, 14, 0, 0f, false)

        front_panel_1_r1 = ModelMapper(modelDataWrapper)
        front_panel_1_r1.setPos(0f, 0f, -32f)
        front.addChild(front_panel_1_r1)
        setRotationAngle(front_panel_1_r1, -0.2618f, 0f, 0f)
        front_panel_1_r1.texOffs(116, 197).addBox(-20f, -3f, 0f, 40, 3, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)


        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(3.2289f, -40.6367f, -18.8935f)
        side_1.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0.1745f, 0f, 0f)
        outer_roof_5_r4.texOffs(16, 129).addBox(-3.5f, 0f, -7f, 6, 0, 14, 0f, true)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(10.4418f, -39.7879f, -18.8937f)
        side_1.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0.1745f, 0f, 0.1745f)
        outer_roof_4_r4.texOffs(84, 143).addBox(-5f, 0f, -7f, 10, 0, 14, 0f, true)

        outer_roof_3_r4 = ModelMapper(modelDataWrapper)
        outer_roof_3_r4.setPos(13.583f, -39.4219f, -18.94f)
        side_1.addChild(outer_roof_3_r4)
        setRotationAngle(outer_roof_3_r4, 0.1309f, 0f, 0.5236f)
        outer_roof_3_r4.texOffs(114, 0).addBox(-0.5f, 0f, -8f, 6, 0, 15, 0f, true)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(17.5562f, -37.0118f, -18.9933f)
        side_1.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0.0436f, 0f, 1.0472f)
        outer_roof_2_r1.texOffs(129, 0).addBox(-1.5f, 0f, -9f, 4, 0, 16, 0f, true)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(21f, 0f, -22f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, 0.1745f, 0.1745f)
        front_side_bottom_1_r1.texOffs(48, 111).addBox(0f, 0f, -11f, 0, 8, 18, 0f, true)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(21f, 0f, -22f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, 0.1745f, 0f)
        front_side_lower_1_r1.texOffs(74, 182).addBox(0f, -14f, -11f, 0, 14, 11, 0f, true)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(21f, -14f, -22f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0.1745f, -0.1107f)
        front_side_upper_1_r1.texOffs(98, 155).addBox(0f, -23f, -11f, 0, 23, 11, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(-21f, 0f, -9f)
        front.addChild(side_2)


        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(17.7711f, -40.6367f, -9.8935f)
        side_2.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0.1745f, 0f, 0f)
        outer_roof_5_r5.texOffs(16, 129).addBox(-2.5f, 0f, -7f, 6, 0, 14, 0f, false)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(10.5582f, -39.7879f, -9.8937f)
        side_2.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0.1745f, 0f, -0.1745f)
        outer_roof_4_r5.texOffs(84, 143).addBox(-5f, 0f, -7f, 10, 0, 14, 0f, false)

        outer_roof_3_r5 = ModelMapper(modelDataWrapper)
        outer_roof_3_r5.setPos(7.417f, -39.4219f, -9.94f)
        side_2.addChild(outer_roof_3_r5)
        setRotationAngle(outer_roof_3_r5, 0.1309f, 0f, -0.5236f)
        outer_roof_3_r5.texOffs(114, 0).addBox(-5.5f, 0f, -8f, 6, 0, 15, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(3.4438f, -37.0118f, -9.9933f)
        side_2.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0.0436f, 0f, -1.0472f)
        outer_roof_2_r2.texOffs(129, 0).addBox(-2.5f, 0f, -9f, 4, 0, 16, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(0f, 0f, -13f)
        side_2.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, -0.1745f, -0.1745f)
        front_side_bottom_2_r1.texOffs(48, 111).addBox(0f, 0f, -11f, 0, 8, 18, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(0f, -14f, -13f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, -0.1745f, 0.1107f)
        front_side_upper_2_r1.texOffs(98, 155).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(0f, 0f, -13f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, -0.1745f, 0f)
        front_side_lower_2_r1.texOffs(74, 182).addBox(0f, -14f, -11f, 0, 14, 11, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        headlights.texOffs(12, 56).addBox(9.5f, -8.8978f, -31.4f, 4, 4, 0, 0f, true)
        headlights.texOffs(12, 56).addBox(-13.5f, -8.8978f, -31.4f, 4, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        tail_lights.texOffs(20, 56).addBox(13.5f, -8.8978f, -31.4f, 4, 4, 0, 0f, true)
        tail_lights.texOffs(20, 56).addBox(-17.5f, -8.8978f, -31.4f, 4, 4, 0, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)


        outer_roof_3_r6 = ModelMapper(modelDataWrapper)
        outer_roof_3_r6.setPos(-17.8206f, -37.1645f, 0f)
        door_light.addChild(outer_roof_3_r6)
        setRotationAngle(outer_roof_3_r6, 0f, 0f, -1.0472f)
        outer_roof_3_r6.texOffs(25, 4).addBox(-1.5f, -0.1f, -1.5f, 3, 0, 3, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-17.8206f, -37.1645f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, -1.0472f)
        light_r1.texOffs(12, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.3f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-17.8206f, -37.1645f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, -1.0472f)
        light_r2.texOffs(16, 0).addBox(0f, 0f, 0f, 0, 0, 0, 0.3f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrail.setModelPart()
        window_exterior.setModelPart()
        window_exterior_end.setModelPart()
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
        door_exterior_end.setModelPart()
        door_left_exterior_end.setModelPart(door_exterior_end.name)
        door_right_exterior_end.setModelPart(door_exterior_end.name)
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
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelKTrain {
        return ModelKTrain(isTcl, doorAnimationType, renderDoorOverlay)
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
                    renderMirror(window_handrail, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, position - 22.1f)
                    renderMirror(side_panel, matrices, vertices, light, position + 22.1f)
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, position - 22.1f)
                renderMirror(side_panel_translucent, matrices, vertices, light, position + 22.1f)
            }

            RenderStage.EXTERIOR -> {
                if (isTcl && isIndex(0, position, getWindowPositions()) && isEnd1Head) {
                    renderOnceFlipped(window_exterior_end, matrices, vertices, light, position.toFloat())
                } else if (isTcl && isIndex(-1, position, getWindowPositions()) && isEnd2Head) {
                    renderOnce(window_exterior_end, matrices, vertices, light, position.toFloat())
                } else {
                    renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                }
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
        val firstDoor = isIndex(0, position, getDoorPositions())
        val notLastDoor = !firstDoor && !isIndex(-1, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                if (notLastDoor) {
                    renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                }
                if (firstDoor && doorOpen) {
                    renderMirror(door_light_on, matrices, vertices, light, 0f)
                }
            }

            RenderStage.INTERIOR -> {
                door_left.setOffset(doorRightX, 0, doorRightZ)
                door_right.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_left.setOffset(doorLeftX, 0, doorLeftZ)
                door_right.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    renderOnce(door_handrail, matrices, vertices, light, position.toFloat())
                    if (notLastDoor) {
                        renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                val door1End = isIndex(0, position, getDoorPositions()) && isEnd1Head
                val door2End = isIndex(-1, position, getDoorPositions()) && isEnd2Head

                if (isTcl && (door1End || door2End)) {
                    door_left_exterior_end.setOffset(doorRightX, 0, doorRightZ)
                    door_right_exterior_end.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_exterior_end, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior.setOffset(doorRightX, 0, doorRightZ)
                    door_right_exterior.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                }

                if (isTcl && (door1End || door2End)) {
                    door_left_exterior_end.setOffset(doorLeftX, 0, doorLeftZ)
                    door_right_exterior_end.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_end, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior.setOffset(doorLeftX, 0, doorLeftZ)
                    door_right_exterior.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                }

                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (firstDoor && renderDetails) {
                    renderMirror(door_light, matrices, vertices, light, 0f)
                    if (!doorOpen) {
                        renderMirror(door_light_off, matrices, vertices, light, 0f)
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
        if (!isTcl) {
            renderFrontDestination(
                matrices,
                font,
                immediate,
                -0.8f,
                0f,
                getEndPositions()!![0] / 16f - 2.21f,
                0f,
                -1.92f,
                -0.01f,
                -15f,
                0f,
                0.37f,
                0.21f,
                -0x6700,
                -0x10000,
                2f,
                getDestinationString(lastStation, customDestination, TextSpacingType.SPACE_CJK, true),
                true,
                car,
                totalCars
            )
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "回廠|Depot"
    }

    companion object {
        private const val DOOR_MAX = 13
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_k_train_left.png", "door_overlay_k_train_right.png")
    }
}
