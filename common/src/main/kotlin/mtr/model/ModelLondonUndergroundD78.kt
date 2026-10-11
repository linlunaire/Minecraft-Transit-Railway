package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.ClientData
import mtr.client.DoorAnimationType
import mtr.client.ScrollingText
import mtr.data.Route
import mtr.data.Station
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import mtr.mappings.RenderBufferSource
import mtr.mappings.UtilitiesClient
import net.minecraft.client.gui.Font

open class ModelLondonUndergroundD78 protected constructor(
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelLondonUndergroundD78?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val window_1: ModelMapper
    private val roof_side_r1: ModelMapper
    private val window_bottom_r1: ModelMapper
    private val window_3: ModelMapper
    private val roof_side_r2: ModelMapper
    private val window_bottom_r2: ModelMapper
    private val window_2_partial: ModelMapper
    private val window_13: ModelMapper
    private val window_bottom_r3: ModelMapper
    private val window_4: ModelMapper
    private val window_bottom_r4: ModelMapper
    private val window_exterior: ModelMapper
    private val window_2: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_2_r1: ModelMapper
    private val roof_1_r1: ModelMapper
    private val window_bottom_r5: ModelMapper
    private val window_6: ModelMapper
    private val roof_4_r1: ModelMapper
    private val roof_3_r2: ModelMapper
    private val roof_2_r2: ModelMapper
    private val window_bottom_r6: ModelMapper
    private val window_exterior_2_partial: ModelMapper
    private val window_15: ModelMapper
    private val window_bottom_r7: ModelMapper
    private val window_8: ModelMapper
    private val window_bottom_r8: ModelMapper
    private val logo: ModelMapper
    private val logo_r1: ModelMapper
    private val door: ModelMapper
    private val roof_side_r3: ModelMapper
    private val door_top_r1: ModelMapper
    private val door_sliding_1: ModelMapper
    private val door_sliding_1_part: ModelMapper
    private val door_top_r2: ModelMapper
    private val door_sliding_2: ModelMapper
    private val door_sliding_2_part: ModelMapper
    private val door_top_r3: ModelMapper
    private val door_handrails: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val roof_3_r3: ModelMapper
    private val roof_2_r3: ModelMapper
    private val roof_1_r2: ModelMapper
    private val door_top_r4: ModelMapper
    private val door_sliding_exterior_1: ModelMapper
    private val door_sliding_exterior_1_part: ModelMapper
    private val door_top_r5: ModelMapper
    private val door_sliding_exterior_2: ModelMapper
    private val door_sliding_exterior_2_part: ModelMapper
    private val door_top_r6: ModelMapper
    private val side_seats: ModelMapper
    private val seat_1: ModelMapper
    private val handrail_5_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val seat_back_r1: ModelMapper
    private val seat_2: ModelMapper
    private val handrail_6_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val seat_back_r2: ModelMapper
    private val middle_seats: ModelMapper
    private val seat_3: ModelMapper
    private val handrail_2_r3: ModelMapper
    private val handrail_5_r3: ModelMapper
    private val seat_back_2_r1: ModelMapper
    private val seat_back_1_r1: ModelMapper
    private val seat_4: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val handrail_6_r2: ModelMapper
    private val seat_back_3_r1: ModelMapper
    private val seat_back_2_r2: ModelMapper
    private val side_panel: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val light_window: ModelMapper
    private val cube_r1: ModelMapper
    private val cube_r2: ModelMapper
    private val light_door: ModelMapper
    private val cube_r3: ModelMapper
    private val light_end: ModelMapper
    private val cube_r4: ModelMapper
    private val cube_r5: ModelMapper
    private val light_head: ModelMapper
    private val cube_r6: ModelMapper
    private val cube_r7: ModelMapper
    private val side_seats_end: ModelMapper
    private val seat_5: ModelMapper
    private val handrail_6_r3: ModelMapper
    private val handrail_3_r3: ModelMapper
    private val seat_back_r3: ModelMapper
    private val seat_6: ModelMapper
    private val handrail_7_r1: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val seat_back_r4: ModelMapper
    private val side_seats_head: ModelMapper
    private val seat_7: ModelMapper
    private val handrail_7_r2: ModelMapper
    private val handrail_4_r2: ModelMapper
    private val seat_back_r5: ModelMapper
    private val seat_8: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val handrail_5_r4: ModelMapper
    private val seat_back_r6: ModelMapper
    private val end: ModelMapper
    private val window_5: ModelMapper
    private val roof_side_r4: ModelMapper
    private val window_bottom_r9: ModelMapper
    private val window_10: ModelMapper
    private val roof_side_r5: ModelMapper
    private val window_bottom_r10: ModelMapper
    private val end_exterior: ModelMapper
    private val window_7: ModelMapper
    private val roof_3_r4: ModelMapper
    private val roof_2_r4: ModelMapper
    private val roof_1_r3: ModelMapper
    private val back_wall_r1: ModelMapper
    private val window_bottom_r11: ModelMapper
    private val window_12: ModelMapper
    private val roof_4_r2: ModelMapper
    private val roof_3_r5: ModelMapper
    private val roof_2_r5: ModelMapper
    private val back_wall_r2: ModelMapper
    private val window_bottom_r12: ModelMapper
    private val head: ModelMapper
    private val window_11: ModelMapper
    private val roof_side_r6: ModelMapper
    private val window_bottom_r13: ModelMapper
    private val window_14: ModelMapper
    private val roof_side_r7: ModelMapper
    private val window_bottom_r14: ModelMapper
    private val head_exterior: ModelMapper
    private val window_9: ModelMapper
    private val door_top_r7: ModelMapper
    private val roof_4_r3: ModelMapper
    private val roof_3_r6: ModelMapper
    private val roof_2_r6: ModelMapper
    private val back_wall_r3: ModelMapper
    private val window_16: ModelMapper
    private val door_top_r8: ModelMapper
    private val roof_5_r1: ModelMapper
    private val roof_4_r4: ModelMapper
    private val roof_3_r7: ModelMapper
    private val back_wall_r4: ModelMapper
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper

    constructor() : this(DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 272
        val textureHeight = 272

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)


        window_1 = ModelMapper(modelDataWrapper)
        window_1.setPos(0f, 0f, 0f)
        window.addChild(window_1)
        window_1.texOffs(88, 0).addBox(-21f, 0f, 0f, 21, 1, 23, 0f, false)
        window_1.texOffs(28, 0).addBox(-13f, -39f, 0f, 13, 0, 23, 0f, false)

        roof_side_r1 = ModelMapper(modelDataWrapper)
        roof_side_r1.setPos(-15.5823f, -35.5941f, 11.5f)
        window_1.addChild(roof_side_r1)
        setRotationAngle(roof_side_r1, 0f, 0f, 0.4363f)
        roof_side_r1.texOffs(188, 80).addBox(0f, -3f, -11.5f, 0, 6, 23, 0f, false)

        window_bottom_r1 = ModelMapper(modelDataWrapper)
        window_bottom_r1.setPos(-21f, 0f, 0f)
        window_1.addChild(window_bottom_r1)
        setRotationAngle(window_bottom_r1, 0f, 0f, 0.0349f)
        window_bottom_r1.texOffs(99, 93).addBox(0f, -33f, 0f, 3, 33, 23, 0f, false)

        window_3 = ModelMapper(modelDataWrapper)
        window_3.setPos(0f, 0f, 0f)
        window.addChild(window_3)
        window_3.texOffs(88, 0).addBox(0f, 0f, 0f, 21, 1, 23, 0f, true)
        window_3.texOffs(28, 0).addBox(0f, -39f, 0f, 13, 0, 23, 0f, true)

        roof_side_r2 = ModelMapper(modelDataWrapper)
        roof_side_r2.setPos(15.5823f, -35.5941f, 11.5f)
        window_3.addChild(roof_side_r2)
        setRotationAngle(roof_side_r2, 0f, 0f, -0.4363f)
        roof_side_r2.texOffs(188, 80).addBox(0f, -3f, -11.5f, 0, 6, 23, 0f, true)

        window_bottom_r2 = ModelMapper(modelDataWrapper)
        window_bottom_r2.setPos(21f, 0f, 0f)
        window_3.addChild(window_bottom_r2)
        setRotationAngle(window_bottom_r2, 0f, 0f, -0.0349f)
        window_bottom_r2.texOffs(99, 93).addBox(-3f, -33f, 0f, 3, 33, 23, 0f, true)

        window_2_partial = ModelMapper(modelDataWrapper)
        window_2_partial.setPos(0f, 24f, 0f)


        window_13 = ModelMapper(modelDataWrapper)
        window_13.setPos(0f, 0f, 0f)
        window_2_partial.addChild(window_13)


        window_bottom_r3 = ModelMapper(modelDataWrapper)
        window_bottom_r3.setPos(-21f, 0f, 0f)
        window_13.addChild(window_bottom_r3)
        setRotationAngle(window_bottom_r3, 0f, 0f, 0.0349f)
        window_bottom_r3.texOffs(0, 126).addBox(3f, -33f, 0f, 0, 33, 23, 0f, false)

        window_4 = ModelMapper(modelDataWrapper)
        window_4.setPos(0f, 0f, 0f)
        window_2_partial.addChild(window_4)


        window_bottom_r4 = ModelMapper(modelDataWrapper)
        window_bottom_r4.setPos(21f, 0f, 0f)
        window_4.addChild(window_bottom_r4)
        setRotationAngle(window_bottom_r4, 0f, 0f, -0.0349f)
        window_bottom_r4.texOffs(0, 126).addBox(-3f, -33f, 0f, 0, 33, 23, 0f, true)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)


        window_2 = ModelMapper(modelDataWrapper)
        window_2.setPos(0f, 0f, 0f)
        window_exterior.addChild(window_2)
        window_2.texOffs(203, 204).addBox(-21f, 0f, 0f, 1, 1, 23, 0f, false)
        window_2.texOffs(189, 27).addBox(-20f, 1f, 0f, 1, 4, 23, 0f, false)
        window_2.texOffs(0, 0).addBox(-3.8669f, -42.7901f, 0f, 4, 0, 23, 0f, false)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(-8.7907f, -41.9219f, 0f)
        window_2.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 1.3963f)
        roof_3_r1.texOffs(150, 55).addBox(0f, -5f, 0f, 0, 7, 23, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(-14.2241f, -39.5747f, 0f)
        window_2.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, 1.0472f)
        roof_2_r1.texOffs(72, 87).addBox(0f, -4f, 0f, 0, 6, 23, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(-18.8485f, -35.1277f, 0f)
        window_2.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, 0.6981f)
        roof_1_r1.texOffs(150, 62).addBox(0f, -4.5f, 0f, 0, 6, 23, 0f, false)

        window_bottom_r5 = ModelMapper(modelDataWrapper)
        window_bottom_r5.setPos(-21f, 0f, 0f)
        window_2.addChild(window_bottom_r5)
        setRotationAngle(window_bottom_r5, 0f, 0f, 0.0349f)
        window_bottom_r5.texOffs(128, 126).addBox(0f, -34f, 0f, 1, 34, 23, 0f, false)

        window_6 = ModelMapper(modelDataWrapper)
        window_6.setPos(0f, 0f, 0f)
        window_exterior.addChild(window_6)
        window_6.texOffs(203, 204).addBox(20f, 0f, 0f, 1, 1, 23, 0f, true)
        window_6.texOffs(189, 27).addBox(19f, 1f, 0f, 1, 4, 23, 0f, true)
        window_6.texOffs(0, 0).addBox(-0.1331f, -42.7901f, 0f, 4, 0, 23, 0f, true)

        roof_4_r1 = ModelMapper(modelDataWrapper)
        roof_4_r1.setPos(8.7907f, -41.9219f, 0f)
        window_6.addChild(roof_4_r1)
        setRotationAngle(roof_4_r1, 0f, 0f, -1.3963f)
        roof_4_r1.texOffs(150, 55).addBox(0f, -5f, 0f, 0, 7, 23, 0f, true)

        roof_3_r2 = ModelMapper(modelDataWrapper)
        roof_3_r2.setPos(14.2241f, -39.5747f, 0f)
        window_6.addChild(roof_3_r2)
        setRotationAngle(roof_3_r2, 0f, 0f, -1.0472f)
        roof_3_r2.texOffs(72, 87).addBox(0f, -4f, 0f, 0, 6, 23, 0f, true)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(18.8485f, -35.1277f, 0f)
        window_6.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, -0.6981f)
        roof_2_r2.texOffs(150, 62).addBox(0f, -4.5f, 0f, 0, 6, 23, 0f, true)

        window_bottom_r6 = ModelMapper(modelDataWrapper)
        window_bottom_r6.setPos(21f, 0f, 0f)
        window_6.addChild(window_bottom_r6)
        setRotationAngle(window_bottom_r6, 0f, 0f, -0.0349f)
        window_bottom_r6.texOffs(128, 126).addBox(-1f, -34f, 0f, 1, 34, 23, 0f, true)

        window_exterior_2_partial = ModelMapper(modelDataWrapper)
        window_exterior_2_partial.setPos(0f, 24f, 0f)


        window_15 = ModelMapper(modelDataWrapper)
        window_15.setPos(0f, 0f, 0f)
        window_exterior_2_partial.addChild(window_15)


        window_bottom_r7 = ModelMapper(modelDataWrapper)
        window_bottom_r7.setPos(-21f, 0f, 0f)
        window_15.addChild(window_bottom_r7)
        setRotationAngle(window_bottom_r7, 0f, 0f, 0.0349f)
        window_bottom_r7.texOffs(88, 1).addBox(0f, -34f, 0f, 0, 34, 23, 0f, false)

        window_8 = ModelMapper(modelDataWrapper)
        window_8.setPos(0f, 0f, 0f)
        window_exterior_2_partial.addChild(window_8)


        window_bottom_r8 = ModelMapper(modelDataWrapper)
        window_bottom_r8.setPos(21f, 0f, 0f)
        window_8.addChild(window_bottom_r8)
        setRotationAngle(window_bottom_r8, 0f, 0f, -0.0349f)
        window_bottom_r8.texOffs(88, 1).addBox(0f, -34f, 0f, 0, 34, 23, 0f, true)

        logo = ModelMapper(modelDataWrapper)
        logo.setPos(0f, 24f, 0f)


        logo_r1 = ModelMapper(modelDataWrapper)
        logo_r1.setPos(-19f, 0f, 0f)
        logo.addChild(logo_r1)
        setRotationAngle(logo_r1, 0f, 0f, 0.0349f)
        logo_r1.texOffs(238, 15).addBox(-2.1f, -15f, -4f, 0, 8, 8, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(150, 59).addBox(-21f, 0f, -9f, 21, 1, 18, 0f, false)
        door.texOffs(59, 0).addBox(-13f, -39f, -9f, 13, 0, 18, 0f, false)

        roof_side_r3 = ModelMapper(modelDataWrapper)
        roof_side_r3.setPos(-15.5823f, -35.5941f, 0f)
        door.addChild(roof_side_r3)
        setRotationAngle(roof_side_r3, 0f, 0f, 0.4363f)
        roof_side_r3.texOffs(128, 41).addBox(0f, -3f, -9f, 0, 6, 18, 0f, false)

        door_top_r1 = ModelMapper(modelDataWrapper)
        door_top_r1.setPos(-21f, 0f, 0f)
        door.addChild(door_top_r1)
        setRotationAngle(door_top_r1, 0f, 0f, 0.0349f)
        door_top_r1.texOffs(168, 242).addBox(1f, -33.5f, -9f, 2, 2, 18, 0f, false)

        door_sliding_1 = ModelMapper(modelDataWrapper)
        door_sliding_1.setPos(0f, 24f, 0f)


        door_sliding_1_part = ModelMapper(modelDataWrapper)
        door_sliding_1_part.setPos(0f, 0f, 0f)
        door_sliding_1.addChild(door_sliding_1_part)


        door_top_r2 = ModelMapper(modelDataWrapper)
        door_top_r2.setPos(-21f, 0f, 0f)
        door_sliding_1_part.addChild(door_top_r2)
        setRotationAngle(door_top_r2, 0f, 0f, 0.0349f)
        door_top_r2.texOffs(158, 165).addBox(0.5f, -32f, -9f, 1, 32, 18, 0f, false)

        door_sliding_2 = ModelMapper(modelDataWrapper)
        door_sliding_2.setPos(0f, 24f, 0f)


        door_sliding_2_part = ModelMapper(modelDataWrapper)
        door_sliding_2_part.setPos(0f, 0f, 0f)
        door_sliding_2.addChild(door_sliding_2_part)


        door_top_r3 = ModelMapper(modelDataWrapper)
        door_top_r3.setPos(-21f, 0f, 0f)
        door_sliding_2_part.addChild(door_top_r3)
        setRotationAngle(door_top_r3, 0f, 0f, 0.0349f)
        door_top_r3.texOffs(28, 165).addBox(0.5f, -32f, -9f, 1, 32, 18, 0f, false)

        door_handrails = ModelMapper(modelDataWrapper)
        door_handrails.setPos(0f, 24f, 0f)


        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(-8f, -9f, 0f)
        door_handrails.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, -0.0349f)
        handrail_2_r1.texOffs(268, 216).addBox(0f, -30f, 0f, 0, 5, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-8f, -9f, 0f)
        door_handrails.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, -1.5708f, 0f, -0.0349f)
        handrail_5_r1.texOffs(268, 216).addBox(0f, -14f, -25f, 0, 28, 0, 0.2f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(128, 93).addBox(-21f, 0f, -9f, 1, 1, 18, 0f, false)
        door_exterior.texOffs(223, 96).addBox(-20f, 1f, -9f, 1, 4, 18, 0f, false)
        door_exterior.texOffs(13, 0).addBox(-3.8669f, -42.7901f, -9f, 4, 0, 18, 0f, false)

        roof_3_r3 = ModelMapper(modelDataWrapper)
        roof_3_r3.setPos(-8.7907f, -41.9219f, 0f)
        door_exterior.addChild(roof_3_r3)
        setRotationAngle(roof_3_r3, 0f, 0f, 1.3963f)
        roof_3_r3.texOffs(178, 151).addBox(0f, -5f, -9f, 0, 7, 18, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(-14.2241f, -39.5747f, 0f)
        door_exterior.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, 1.0472f)
        roof_2_r3.texOffs(148, 81).addBox(0f, -4f, -9f, 0, 6, 18, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(-18.8485f, -35.1277f, 0f)
        door_exterior.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, 0.6981f)
        roof_1_r2.texOffs(148, 87).addBox(0f, -4.5f, -9f, 0, 6, 18, 0f, false)

        door_top_r4 = ModelMapper(modelDataWrapper)
        door_top_r4.setPos(-21f, 0f, 0f)
        door_exterior.addChild(door_top_r4)
        setRotationAngle(door_top_r4, 0f, 0f, 0.0349f)
        door_top_r4.texOffs(218, 29).addBox(0f, -34f, -9f, 1, 2, 18, 0f, false)

        door_sliding_exterior_1 = ModelMapper(modelDataWrapper)
        door_sliding_exterior_1.setPos(0f, 24f, 0f)


        door_sliding_exterior_1_part = ModelMapper(modelDataWrapper)
        door_sliding_exterior_1_part.setPos(0f, 0f, 0f)
        door_sliding_exterior_1.addChild(door_sliding_exterior_1_part)


        door_top_r5 = ModelMapper(modelDataWrapper)
        door_top_r5.setPos(-21f, 0f, 0f)
        door_sliding_exterior_1_part.addChild(door_top_r5)
        setRotationAngle(door_top_r5, 0f, 0f, 0.0349f)
        door_top_r5.texOffs(176, 0).addBox(0.5f, -32f, -9f, 0, 32, 18, 0f, false)

        door_sliding_exterior_2 = ModelMapper(modelDataWrapper)
        door_sliding_exterior_2.setPos(0f, 24f, 0f)


        door_sliding_exterior_2_part = ModelMapper(modelDataWrapper)
        door_sliding_exterior_2_part.setPos(0f, 0f, 0f)
        door_sliding_exterior_2.addChild(door_sliding_exterior_2_part)


        door_top_r6 = ModelMapper(modelDataWrapper)
        door_top_r6.setPos(-21f, 0f, 0f)
        door_sliding_exterior_2_part.addChild(door_top_r6)
        setRotationAngle(door_top_r6, 0f, 0f, 0.0349f)
        door_top_r6.texOffs(153, 94).addBox(0.5f, -32f, -9f, 0, 32, 18, 0f, false)

        side_seats = ModelMapper(modelDataWrapper)
        side_seats.setPos(0f, 24f, 0f)


        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 0f, -29f)
        side_seats.addChild(seat_1)
        seat_1.texOffs(46, 196).addBox(-15f, -7f, 29f, 6, 2, 20, 0f, false)
        seat_1.texOffs(82, 73).addBox(-11f, -5f, 29f, 0, 5, 20, 0f, false)

        handrail_5_r2 = ModelMapper(modelDataWrapper)
        handrail_5_r2.setPos(-9f, -9f, 29f)
        seat_1.addChild(handrail_5_r2)
        setRotationAngle(handrail_5_r2, -1.5708f, 0f, -0.0349f)
        handrail_5_r2.texOffs(268, 216).addBox(0f, -20f, -24f, 0, 20, 0, 0.2f, false)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(-9f, -9f, 29f)
        seat_1.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, 0f, 0f, -0.0349f)
        handrail_2_r2.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-15f, -7f, 29f)
        seat_1.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.1745f)
        seat_back_r1.texOffs(205, 137).addBox(-2f, -8f, 0f, 2, 8, 20, 0f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 0f, -29f)
        side_seats.addChild(seat_2)
        seat_2.texOffs(46, 196).addBox(9f, -7f, 29f, 6, 2, 20, 0f, true)
        seat_2.texOffs(82, 73).addBox(11f, -5f, 29f, 0, 5, 20, 0f, true)

        handrail_6_r1 = ModelMapper(modelDataWrapper)
        handrail_6_r1.setPos(9f, -9f, 29f)
        seat_2.addChild(handrail_6_r1)
        setRotationAngle(handrail_6_r1, -1.5708f, 0f, 0.0349f)
        handrail_6_r1.texOffs(268, 216).addBox(0f, -20f, -24f, 0, 20, 0, 0.2f, true)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(9f, -9f, 29f)
        seat_2.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, 0.0349f)
        handrail_3_r1.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, true)

        seat_back_r2 = ModelMapper(modelDataWrapper)
        seat_back_r2.setPos(15f, -7f, 29f)
        seat_2.addChild(seat_back_r2)
        setRotationAngle(seat_back_r2, 0f, 0f, 0.1745f)
        seat_back_r2.texOffs(205, 137).addBox(0f, -8f, 0f, 2, 8, 20, 0f, true)

        middle_seats = ModelMapper(modelDataWrapper)
        middle_seats.setPos(0f, 24f, 0f)


        seat_3 = ModelMapper(modelDataWrapper)
        seat_3.setPos(0f, 0f, 0f)
        middle_seats.addChild(seat_3)
        seat_3.texOffs(214, 184).addBox(-15f, -7f, 12f, 6, 2, 8, 0f, false)
        seat_3.texOffs(236, 10).addBox(-15f, -5f, 12f, 4, 5, 8, 0f, false)
        seat_3.texOffs(226, 0).addBox(-18f, -7f, 4f, 14, 2, 8, 0f, false)
        seat_3.texOffs(210, 228).addBox(-18f, -5f, 6f, 12, 5, 6, 0f, false)
        seat_3.texOffs(122, 234).addBox(-18f, -15f, 11f, 14, 8, 1, 0f, false)

        handrail_2_r3 = ModelMapper(modelDataWrapper)
        handrail_2_r3.setPos(-9f, -9f, 0f)
        seat_3.addChild(handrail_2_r3)
        setRotationAngle(handrail_2_r3, 0f, 0f, -0.0349f)
        handrail_2_r3.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, false)

        handrail_5_r3 = ModelMapper(modelDataWrapper)
        handrail_5_r3.setPos(-9f, -9f, 0f)
        seat_3.addChild(handrail_5_r3)
        setRotationAngle(handrail_5_r3, -1.5708f, 0f, -0.0349f)
        handrail_5_r3.texOffs(268, 216).addBox(0f, -20f, -24f, 0, 20, 0, 0.2f, false)

        seat_back_2_r1 = ModelMapper(modelDataWrapper)
        seat_back_2_r1.setPos(-11f, -7f, 10f)
        seat_3.addChild(seat_back_2_r1)
        setRotationAngle(seat_back_2_r1, -0.1745f, 0f, 0f)
        seat_back_2_r1.texOffs(235, 72).addBox(-7f, -7f, 0f, 14, 7, 1, 0f, false)

        seat_back_1_r1 = ModelMapper(modelDataWrapper)
        seat_back_1_r1.setPos(-15f, -7f, 0f)
        seat_3.addChild(seat_back_1_r1)
        setRotationAngle(seat_back_1_r1, 0f, 0f, -0.1745f)
        seat_back_1_r1.texOffs(0, 7).addBox(-2f, -8f, 12f, 2, 8, 8, 0f, false)

        seat_4 = ModelMapper(modelDataWrapper)
        seat_4.setPos(0f, 0f, 0f)
        middle_seats.addChild(seat_4)
        seat_4.texOffs(214, 184).addBox(9f, -7f, 12f, 6, 2, 8, 0f, true)
        seat_4.texOffs(236, 10).addBox(11f, -5f, 12f, 4, 5, 8, 0f, true)
        seat_4.texOffs(226, 0).addBox(4f, -7f, 4f, 14, 2, 8, 0f, true)
        seat_4.texOffs(210, 228).addBox(6f, -5f, 6f, 12, 5, 6, 0f, true)
        seat_4.texOffs(122, 234).addBox(4f, -15f, 11f, 14, 8, 1, 0f, true)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(9f, -9f, 0f)
        seat_4.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, 0.0349f)
        handrail_3_r2.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, true)

        handrail_6_r2 = ModelMapper(modelDataWrapper)
        handrail_6_r2.setPos(9f, -9f, 0f)
        seat_4.addChild(handrail_6_r2)
        setRotationAngle(handrail_6_r2, -1.5708f, 0f, 0.0349f)
        handrail_6_r2.texOffs(268, 216).addBox(0f, -20f, -24f, 0, 20, 0, 0.2f, true)

        seat_back_3_r1 = ModelMapper(modelDataWrapper)
        seat_back_3_r1.setPos(11f, -7f, 10f)
        seat_4.addChild(seat_back_3_r1)
        setRotationAngle(seat_back_3_r1, -0.1745f, 0f, 0f)
        seat_back_3_r1.texOffs(235, 72).addBox(-7f, -7f, 0f, 14, 7, 1, 0f, true)

        seat_back_2_r2 = ModelMapper(modelDataWrapper)
        seat_back_2_r2.setPos(15f, -7f, 0f)
        seat_4.addChild(seat_back_2_r2)
        setRotationAngle(seat_back_2_r2, 0f, 0f, 0.1745f)
        seat_back_2_r2.texOffs(0, 7).addBox(0f, -8f, 12f, 2, 8, 8, 0f, true)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(76, 116).addBox(-18f, -30f, 0f, 9, 30, 0, 0f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(-9f, -9f, 0f)
        side_panel.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, 0f, 0f, -0.0349f)
        handrail_1_r1.texOffs(268, 216).addBox(0f, -30f, 0f, 0, 30, 0, 0.2f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(0, 245).addBox(-18f, -30f, 0f, 9, 20, 0, 0f, false)

        light_window = ModelMapper(modelDataWrapper)
        light_window.setPos(0f, 24f, 0f)


        cube_r1 = ModelMapper(modelDataWrapper)
        cube_r1.setPos(-0.9792f, -0.1101f, 0f)
        light_window.addChild(cube_r1)
        setRotationAngle(cube_r1, 0f, 0f, -0.7854f)
        cube_r1.texOffs(188, 78).addBox(17f, -37f, 0f, 2, 1, 23, 0f, true)

        cube_r2 = ModelMapper(modelDataWrapper)
        cube_r2.setPos(0.9792f, -0.1101f, 0f)
        light_window.addChild(cube_r2)
        setRotationAngle(cube_r2, 0f, 0f, 0.7854f)
        cube_r2.texOffs(188, 78).addBox(-19f, -37f, 0f, 2, 1, 23, 0f, false)

        light_door = ModelMapper(modelDataWrapper)
        light_door.setPos(0f, 24f, 0f)


        cube_r3 = ModelMapper(modelDataWrapper)
        cube_r3.setPos(-0.9792f, -0.1101f, 0f)
        light_door.addChild(cube_r3)
        setRotationAngle(cube_r3, 0f, 0f, -0.7854f)
        cube_r3.texOffs(190, 244).addBox(17f, -37f, -9f, 2, 1, 18, 0f, true)

        light_end = ModelMapper(modelDataWrapper)
        light_end.setPos(0f, 24f, 0f)


        cube_r4 = ModelMapper(modelDataWrapper)
        cube_r4.setPos(0.9792f, -0.1101f, 0f)
        light_end.addChild(cube_r4)
        setRotationAngle(cube_r4, 0f, 0f, 0.7854f)
        cube_r4.texOffs(243, 84).addBox(-19f, -37f, 0f, 2, 1, 12, 0f, true)

        cube_r5 = ModelMapper(modelDataWrapper)
        cube_r5.setPos(-0.9792f, -0.1101f, 0f)
        light_end.addChild(cube_r5)
        setRotationAngle(cube_r5, 0f, 0f, -0.7854f)
        cube_r5.texOffs(243, 84).addBox(17f, -37f, 0f, 2, 1, 12, 0f, false)

        light_head = ModelMapper(modelDataWrapper)
        light_head.setPos(0f, 24f, 0f)


        cube_r6 = ModelMapper(modelDataWrapper)
        cube_r6.setPos(0.9792f, -0.1101f, 5f)
        light_head.addChild(cube_r6)
        setRotationAngle(cube_r6, 0f, 0f, 0.7854f)
        cube_r6.texOffs(212, 239).addBox(-19f, -37f, -5f, 2, 1, 19, 0f, true)

        cube_r7 = ModelMapper(modelDataWrapper)
        cube_r7.setPos(-0.9792f, -0.1101f, 5f)
        light_head.addChild(cube_r7)
        setRotationAngle(cube_r7, 0f, 0f, -0.7854f)
        cube_r7.texOffs(212, 239).addBox(17f, -37f, -5f, 2, 1, 19, 0f, false)

        side_seats_end = ModelMapper(modelDataWrapper)
        side_seats_end.setPos(0f, 24f, 0f)


        seat_5 = ModelMapper(modelDataWrapper)
        seat_5.setPos(0f, 0f, -29f)
        side_seats_end.addChild(seat_5)
        seat_5.texOffs(186, 228).addBox(-15f, -7f, 29f, 6, 2, 12, 0f, false)
        seat_5.texOffs(82, 72).addBox(-11f, -5f, 29f, 0, 5, 12, 0f, false)

        handrail_6_r3 = ModelMapper(modelDataWrapper)
        handrail_6_r3.setPos(-9f, -9f, 29f)
        seat_5.addChild(handrail_6_r3)
        setRotationAngle(handrail_6_r3, -1.5708f, 0f, -0.0349f)
        handrail_6_r3.texOffs(268, 216).addBox(0f, -7f, -24f, 0, 7, 0, 0.2f, false)

        handrail_3_r3 = ModelMapper(modelDataWrapper)
        handrail_3_r3.setPos(-9f, -9f, 29f)
        seat_5.addChild(handrail_3_r3)
        setRotationAngle(handrail_3_r3, 0f, 0f, -0.0349f)
        handrail_3_r3.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, false)

        seat_back_r3 = ModelMapper(modelDataWrapper)
        seat_back_r3.setPos(-15f, -7f, 29f)
        seat_5.addChild(seat_back_r3)
        setRotationAngle(seat_back_r3, 0f, 0f, -0.1745f)
        seat_back_r3.texOffs(228, 202).addBox(-2f, -8f, 0f, 2, 8, 12, 0f, false)

        seat_6 = ModelMapper(modelDataWrapper)
        seat_6.setPos(0f, 0f, -29f)
        side_seats_end.addChild(seat_6)
        seat_6.texOffs(186, 228).addBox(9f, -7f, 29f, 6, 2, 12, 0f, true)
        seat_6.texOffs(82, 72).addBox(11f, -5f, 29f, 0, 5, 12, 0f, true)

        handrail_7_r1 = ModelMapper(modelDataWrapper)
        handrail_7_r1.setPos(9f, -9f, 29f)
        seat_6.addChild(handrail_7_r1)
        setRotationAngle(handrail_7_r1, -1.5708f, 0f, 0.0349f)
        handrail_7_r1.texOffs(268, 216).addBox(0f, -7f, -24f, 0, 7, 0, 0.2f, true)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(9f, -9f, 29f)
        seat_6.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0f, 0f, 0.0349f)
        handrail_4_r1.texOffs(268, 216).addBox(0f, -30f, 7f, 0, 6, 0, 0.2f, true)

        seat_back_r4 = ModelMapper(modelDataWrapper)
        seat_back_r4.setPos(15f, -7f, 29f)
        seat_6.addChild(seat_back_r4)
        setRotationAngle(seat_back_r4, 0f, 0f, 0.1745f)
        seat_back_r4.texOffs(228, 202).addBox(0f, -8f, 0f, 2, 8, 12, 0f, true)

        side_seats_head = ModelMapper(modelDataWrapper)
        side_seats_head.setPos(0f, 24f, 0f)


        seat_7 = ModelMapper(modelDataWrapper)
        seat_7.setPos(0f, 0f, -29f)
        side_seats_head.addChild(seat_7)
        seat_7.texOffs(210, 54).addBox(-15f, -7f, 32f, 6, 2, 16, 0f, false)
        seat_7.texOffs(134, 8).addBox(-11f, -5f, 32f, 0, 5, 16, 0f, false)

        handrail_7_r2 = ModelMapper(modelDataWrapper)
        handrail_7_r2.setPos(-9f, -9f, 29f)
        seat_7.addChild(handrail_7_r2)
        setRotationAngle(handrail_7_r2, -1.5708f, 0f, -0.0349f)
        handrail_7_r2.texOffs(268, 216).addBox(0f, -10f, -24f, 0, 7, 0, 0.2f, false)

        handrail_4_r2 = ModelMapper(modelDataWrapper)
        handrail_4_r2.setPos(-9f, -9f, 29f)
        seat_7.addChild(handrail_4_r2)
        setRotationAngle(handrail_4_r2, 0f, 0f, -0.0349f)
        handrail_4_r2.texOffs(268, 216).addBox(0f, -30f, 10f, 0, 6, 0, 0.2f, false)

        seat_back_r5 = ModelMapper(modelDataWrapper)
        seat_back_r5.setPos(-15f, -7f, 29f)
        seat_7.addChild(seat_back_r5)
        setRotationAngle(seat_back_r5, 0f, 0f, -0.1745f)
        seat_back_r5.texOffs(215, 72).addBox(-2f, -8f, 3f, 2, 8, 16, 0f, false)

        seat_8 = ModelMapper(modelDataWrapper)
        seat_8.setPos(0f, 0f, -29f)
        side_seats_head.addChild(seat_8)
        seat_8.texOffs(210, 54).addBox(9f, -7f, 32f, 6, 2, 16, 0f, true)
        seat_8.texOffs(134, 8).addBox(11f, -5f, 32f, 0, 5, 16, 0f, true)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(9f, -9f, 29f)
        seat_8.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0.0349f)
        handrail_8_r1.texOffs(268, 216).addBox(0f, -10f, -24f, 0, 7, 0, 0.2f, true)

        handrail_5_r4 = ModelMapper(modelDataWrapper)
        handrail_5_r4.setPos(9f, -9f, 29f)
        seat_8.addChild(handrail_5_r4)
        setRotationAngle(handrail_5_r4, 0f, 0f, 0.0349f)
        handrail_5_r4.texOffs(268, 216).addBox(0f, -30f, 10f, 0, 6, 0, 0.2f, true)

        seat_back_r6 = ModelMapper(modelDataWrapper)
        seat_back_r6.setPos(15f, -7f, 29f)
        seat_8.addChild(seat_back_r6)
        setRotationAngle(seat_back_r6, 0f, 0f, 0.1745f)
        seat_back_r6.texOffs(215, 72).addBox(0f, -8f, 3f, 2, 8, 16, 0f, true)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(72, 218).addBox(-6f, -34f, 14f, 12, 34, 0, 0f, false)

        window_5 = ModelMapper(modelDataWrapper)
        window_5.setPos(0f, 0f, 0f)
        end.addChild(window_5)
        window_5.texOffs(153, 0).addBox(-21f, 0f, 0f, 21, 1, 14, 0f, false)
        window_5.texOffs(158, 215).addBox(-18f, -39f, 12f, 12, 39, 2, 0f, false)
        window_5.texOffs(128, 93).addBox(-6f, -39f, 12f, 6, 6, 2, 0f, false)
        window_5.texOffs(128, 101).addBox(-8f, -39f, 11f, 8, 5, 1, 0f, false)
        window_5.texOffs(70, 66).addBox(-13f, -39f, 0f, 13, 0, 12, 0f, false)

        roof_side_r4 = ModelMapper(modelDataWrapper)
        roof_side_r4.setPos(-15.5823f, -35.5941f, 11.5f)
        window_5.addChild(roof_side_r4)
        setRotationAngle(roof_side_r4, 0f, 0f, 0.4363f)
        roof_side_r4.texOffs(82, 66).addBox(0f, -3f, -11.5f, 0, 6, 12, 0f, false)

        window_bottom_r9 = ModelMapper(modelDataWrapper)
        window_bottom_r9.setPos(-21f, 0f, 0f)
        window_5.addChild(window_bottom_r9)
        setRotationAngle(window_bottom_r9, 0f, 0f, 0.0349f)
        window_bottom_r9.texOffs(196, 182).addBox(0f, -33f, 0f, 3, 33, 12, 0f, false)

        window_10 = ModelMapper(modelDataWrapper)
        window_10.setPos(0f, 0f, 0f)
        end.addChild(window_10)
        window_10.texOffs(153, 0).addBox(0f, 0f, 0f, 21, 1, 14, 0f, true)
        window_10.texOffs(158, 215).addBox(6f, -39f, 12f, 12, 39, 2, 0f, true)
        window_10.texOffs(128, 93).addBox(0f, -39f, 12f, 6, 6, 2, 0f, true)
        window_10.texOffs(128, 101).addBox(0f, -39f, 11f, 8, 5, 1, 0f, true)
        window_10.texOffs(70, 66).addBox(0f, -39f, 0f, 13, 0, 12, 0f, true)

        roof_side_r5 = ModelMapper(modelDataWrapper)
        roof_side_r5.setPos(15.5823f, -35.5941f, 11.5f)
        window_10.addChild(roof_side_r5)
        setRotationAngle(roof_side_r5, 0f, 0f, -0.4363f)
        roof_side_r5.texOffs(82, 66).addBox(0f, -3f, -11.5f, 0, 6, 12, 0f, true)

        window_bottom_r10 = ModelMapper(modelDataWrapper)
        window_bottom_r10.setPos(21f, 0f, 0f)
        window_10.addChild(window_bottom_r10)
        setRotationAngle(window_bottom_r10, 0f, 0f, -0.0349f)
        window_bottom_r10.texOffs(196, 182).addBox(-3f, -33f, 0f, 3, 33, 12, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(28, 218).addBox(-6f, -43f, 15f, 12, 43, 0, 0f, false)

        window_7 = ModelMapper(modelDataWrapper)
        window_7.setPos(0f, 0f, 0f)
        end_exterior.addChild(window_7)
        window_7.texOffs(0, 29).addBox(-21f, 0f, 0f, 1, 1, 12, 0f, false)
        window_7.texOffs(0, 229).addBox(-20f, 1f, 0f, 1, 4, 12, 0f, false)
        window_7.texOffs(82, 89).addBox(-19f, 1f, 11f, 12, 2, 1, 0f, false)
        window_7.texOffs(229, 144).addBox(-7f, 0f, 11f, 14, 4, 4, 0f, false)
        window_7.texOffs(0, 0).addBox(-3.8669f, -42.7901f, 0f, 4, 0, 15, 0f, false)

        roof_3_r4 = ModelMapper(modelDataWrapper)
        roof_3_r4.setPos(-8.7907f, -41.9219f, 11.5f)
        window_7.addChild(roof_3_r4)
        setRotationAngle(roof_3_r4, 0f, 0f, 1.3963f)
        roof_3_r4.texOffs(134, 14).addBox(0f, -5f, -11.5f, 0, 7, 15, 0f, false)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(-14.2241f, -39.5747f, 11.5f)
        window_7.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, 1.0472f)
        roof_2_r4.texOffs(122, 213).addBox(0f, -4f, -11.5f, 0, 6, 15, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(-18.8485f, -35.1277f, 11.5f)
        window_7.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, 0.6981f)
        roof_1_r3.texOffs(99, 135).addBox(0f, -4.5f, -11.5f, 0, 6, 14, 0f, false)

        back_wall_r1 = ModelMapper(modelDataWrapper)
        back_wall_r1.setPos(-6f, 0f, 15f)
        window_7.addChild(back_wall_r1)
        setRotationAngle(back_wall_r1, 0f, -0.1745f, 0f)
        back_wall_r1.texOffs(189, 112).addBox(-16f, -43f, -1f, 16, 44, 1, 0f, false)

        window_bottom_r11 = ModelMapper(modelDataWrapper)
        window_bottom_r11.setPos(-21f, 0f, 0f)
        window_7.addChild(window_bottom_r11)
        setRotationAngle(window_bottom_r11, 0f, 0f, 0.0349f)
        window_bottom_r11.texOffs(0, 182).addBox(0f, -34f, 0f, 1, 34, 13, 0f, false)

        window_12 = ModelMapper(modelDataWrapper)
        window_12.setPos(0f, 0f, 0f)
        end_exterior.addChild(window_12)
        window_12.texOffs(0, 29).addBox(20f, 0f, 0f, 1, 1, 12, 0f, true)
        window_12.texOffs(0, 229).addBox(19f, 1f, 0f, 1, 4, 12, 0f, true)
        window_12.texOffs(82, 89).addBox(7f, 1f, 11f, 12, 2, 1, 0f, true)
        window_12.texOffs(229, 144).addBox(-7f, 0f, 11f, 14, 4, 4, 0f, true)
        window_12.texOffs(0, 0).addBox(-0.1331f, -42.7901f, 0f, 4, 0, 15, 0f, true)

        roof_4_r2 = ModelMapper(modelDataWrapper)
        roof_4_r2.setPos(8.7907f, -41.9219f, 11.5f)
        window_12.addChild(roof_4_r2)
        setRotationAngle(roof_4_r2, 0f, 0f, -1.3963f)
        roof_4_r2.texOffs(134, 14).addBox(0f, -5f, -11.5f, 0, 7, 15, 0f, true)

        roof_3_r5 = ModelMapper(modelDataWrapper)
        roof_3_r5.setPos(14.2241f, -39.5747f, 11.5f)
        window_12.addChild(roof_3_r5)
        setRotationAngle(roof_3_r5, 0f, 0f, -1.0472f)
        roof_3_r5.texOffs(122, 213).addBox(0f, -4f, -11.5f, 0, 6, 15, 0f, true)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(18.8485f, -35.1277f, 11.5f)
        window_12.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0f, -0.6981f)
        roof_2_r5.texOffs(99, 135).addBox(0f, -4.5f, -11.5f, 0, 6, 14, 0f, true)

        back_wall_r2 = ModelMapper(modelDataWrapper)
        back_wall_r2.setPos(6f, 0f, 15f)
        window_12.addChild(back_wall_r2)
        setRotationAngle(back_wall_r2, 0f, 0.1745f, 0f)
        back_wall_r2.texOffs(189, 112).addBox(0f, -43f, -1f, 16, 44, 1, 0f, true)

        window_bottom_r12 = ModelMapper(modelDataWrapper)
        window_bottom_r12.setPos(21f, 0f, 0f)
        window_12.addChild(window_bottom_r12)
        setRotationAngle(window_bottom_r12, 0f, 0f, -0.0349f)
        window_bottom_r12.texOffs(0, 182).addBox(-1f, -34f, 0f, 1, 34, 13, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(0, 110).addBox(-18f, -39f, 19f, 36, 39, 0, 0f, false)

        window_11 = ModelMapper(modelDataWrapper)
        window_11.setPos(0f, 0f, -29f)
        head.addChild(window_11)
        window_11.texOffs(115, 39).addBox(-21f, 0f, 29f, 21, 1, 19, 0f, false)
        window_11.texOffs(128, 101).addBox(-8f, -39f, 47f, 8, 5, 1, 0f, false)
        window_11.texOffs(32, 23).addBox(-13f, -39f, 29f, 13, 0, 19, 0f, false)

        roof_side_r6 = ModelMapper(modelDataWrapper)
        roof_side_r6.setPos(-15.5823f, -35.5941f, 40.5f)
        window_11.addChild(roof_side_r6)
        setRotationAngle(roof_side_r6, 0f, 0f, 0.4363f)
        roof_side_r6.texOffs(82, 79).addBox(0f, -3f, -11.5f, 0, 6, 19, 0f, false)

        window_bottom_r13 = ModelMapper(modelDataWrapper)
        window_bottom_r13.setPos(-21f, 0f, 29f)
        window_11.addChild(window_bottom_r13)
        setRotationAngle(window_bottom_r13, 0f, 0f, 0.0349f)
        window_bottom_r13.texOffs(80, 164).addBox(0f, -33f, 0f, 3, 33, 19, 0f, false)

        window_14 = ModelMapper(modelDataWrapper)
        window_14.setPos(0f, 0f, -29f)
        head.addChild(window_14)
        window_14.texOffs(115, 39).addBox(0f, 0f, 29f, 21, 1, 19, 0f, true)
        window_14.texOffs(128, 101).addBox(0f, -39f, 47f, 8, 5, 1, 0f, true)
        window_14.texOffs(32, 23).addBox(0f, -39f, 29f, 13, 0, 19, 0f, true)

        roof_side_r7 = ModelMapper(modelDataWrapper)
        roof_side_r7.setPos(15.5823f, -35.5941f, 40.5f)
        window_14.addChild(roof_side_r7)
        setRotationAngle(roof_side_r7, 0f, 0f, -0.4363f)
        roof_side_r7.texOffs(82, 79).addBox(0f, -3f, -11.5f, 0, 6, 19, 0f, true)

        window_bottom_r14 = ModelMapper(modelDataWrapper)
        window_bottom_r14.setPos(21f, 0f, 29f)
        window_14.addChild(window_bottom_r14)
        setRotationAngle(window_bottom_r14, 0f, 0f, -0.0349f)
        window_bottom_r14.texOffs(80, 164).addBox(-3f, -33f, 0f, 3, 33, 19, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(98, 216).addBox(-6f, -43f, 43f, 12, 43, 0, 0f, false)
        head_exterior.texOffs(0, 26).addBox(-16f, 1f, 40f, 6, 2, 1, 0f, false)
        head_exterior.texOffs(0, 23).addBox(10f, 1f, 40f, 6, 2, 1, 0f, false)
        head_exterior.texOffs(0, 66).addBox(-20f, -43f, 20f, 40, 43, 0, 0f, false)

        window_9 = ModelMapper(modelDataWrapper)
        window_9.setPos(0f, 0f, 0f)
        head_exterior.addChild(window_9)
        window_9.texOffs(82, 66).addBox(-21f, 0f, 16f, 21, 1, 26, 0f, false)
        window_9.texOffs(40, 69).addBox(-21f, 0f, 0f, 1, 1, 40, 0f, false)
        window_9.texOffs(46, 22).addBox(-20f, 1f, 0f, 1, 4, 40, 0f, false)
        window_9.texOffs(77, 18).addBox(-19f, 1f, 39f, 12, 2, 1, 0f, false)
        window_9.texOffs(229, 136).addBox(-7f, 0f, 39f, 14, 4, 4, 0f, false)
        window_9.texOffs(0, 0).addBox(-3.8669f, -42.7901f, 0f, 4, 0, 43, 0f, false)

        door_top_r7 = ModelMapper(modelDataWrapper)
        door_top_r7.setPos(-21f, 0f, 32f)
        window_9.addChild(door_top_r7)
        setRotationAngle(door_top_r7, 0f, 0f, 0.0349f)
        door_top_r7.texOffs(105, 155).addBox(0f, -34f, -9f, 1, 2, 10, 0f, false)
        door_top_r7.texOffs(214, 5).addBox(0.5f, -32f, -9f, 1, 32, 10, 0f, false)
        door_top_r7.texOffs(52, 218).addBox(0f, -34f, 1f, 2, 34, 8, 0f, false)
        door_top_r7.texOffs(49, 126).addBox(0f, -34f, -32f, 2, 34, 23, 0f, false)

        roof_4_r3 = ModelMapper(modelDataWrapper)
        roof_4_r3.setPos(-8.7907f, -41.9219f, 0f)
        window_9.addChild(roof_4_r3)
        setRotationAngle(roof_4_r3, 0f, 0f, 1.3963f)
        roof_4_r3.texOffs(0, 0).addBox(0f, -5f, 0f, 0, 7, 43, 0f, false)

        roof_3_r6 = ModelMapper(modelDataWrapper)
        roof_3_r6.setPos(-14.2241f, -39.5747f, 0f)
        window_9.addChild(roof_3_r6)
        setRotationAngle(roof_3_r6, 0f, 0f, 1.0472f)
        roof_3_r6.texOffs(0, 7).addBox(0f, -4f, 0f, 0, 6, 43, 0f, false)

        roof_2_r6 = ModelMapper(modelDataWrapper)
        roof_2_r6.setPos(-18.8485f, -35.1277f, 0f)
        window_9.addChild(roof_2_r6)
        setRotationAngle(roof_2_r6, 0f, 0f, 0.6981f)
        roof_2_r6.texOffs(0, 14).addBox(0f, -4.5f, 0f, 0, 6, 42, 0f, false)

        back_wall_r3 = ModelMapper(modelDataWrapper)
        back_wall_r3.setPos(-6f, 0f, 43f)
        window_9.addChild(back_wall_r3)
        setRotationAngle(back_wall_r3, 0f, -0.1745f, 0f)
        back_wall_r3.texOffs(124, 183).addBox(-16f, -43f, -1f, 16, 44, 1, 0f, false)

        window_16 = ModelMapper(modelDataWrapper)
        window_16.setPos(0f, 0f, 0f)
        head_exterior.addChild(window_16)
        window_16.texOffs(82, 66).addBox(0f, 0f, 16f, 21, 1, 26, 0f, true)
        window_16.texOffs(40, 69).addBox(20f, 0f, 0f, 1, 1, 40, 0f, true)
        window_16.texOffs(46, 22).addBox(19f, 1f, 0f, 1, 4, 40, 0f, true)
        window_16.texOffs(77, 18).addBox(7f, 1f, 39f, 12, 2, 1, 0f, true)
        window_16.texOffs(229, 136).addBox(-7f, 0f, 39f, 14, 4, 4, 0f, true)
        window_16.texOffs(0, 0).addBox(-0.1331f, -42.7901f, 0f, 4, 0, 43, 0f, true)

        door_top_r8 = ModelMapper(modelDataWrapper)
        door_top_r8.setPos(21f, 0f, 32f)
        window_16.addChild(door_top_r8)
        setRotationAngle(door_top_r8, 0f, 0f, -0.0349f)
        door_top_r8.texOffs(105, 155).addBox(-1f, -34f, -9f, 1, 2, 10, 0f, true)
        door_top_r8.texOffs(214, 5).addBox(-1.5f, -32f, -9f, 1, 32, 10, 0f, true)
        door_top_r8.texOffs(52, 218).addBox(-2f, -34f, 1f, 2, 34, 8, 0f, true)
        door_top_r8.texOffs(49, 126).addBox(-2f, -34f, -32f, 2, 34, 23, 0f, true)

        roof_5_r1 = ModelMapper(modelDataWrapper)
        roof_5_r1.setPos(8.7907f, -41.9219f, 0f)
        window_16.addChild(roof_5_r1)
        setRotationAngle(roof_5_r1, 0f, 0f, -1.3963f)
        roof_5_r1.texOffs(0, 0).addBox(0f, -5f, 0f, 0, 7, 43, 0f, true)

        roof_4_r4 = ModelMapper(modelDataWrapper)
        roof_4_r4.setPos(14.2241f, -39.5747f, 0f)
        window_16.addChild(roof_4_r4)
        setRotationAngle(roof_4_r4, 0f, 0f, -1.0472f)
        roof_4_r4.texOffs(0, 7).addBox(0f, -4f, 0f, 0, 6, 43, 0f, true)

        roof_3_r7 = ModelMapper(modelDataWrapper)
        roof_3_r7.setPos(18.8485f, -35.1277f, 0f)
        window_16.addChild(roof_3_r7)
        setRotationAngle(roof_3_r7, 0f, 0f, -0.6981f)
        roof_3_r7.texOffs(0, 14).addBox(0f, -4.5f, 0f, 0, 6, 42, 0f, true)

        back_wall_r4 = ModelMapper(modelDataWrapper)
        back_wall_r4.setPos(6f, 0f, 43f)
        window_16.addChild(back_wall_r4)
        setRotationAngle(back_wall_r4, 0f, 0.1745f, 0f)
        back_wall_r4.texOffs(124, 183).addBox(0f, -43f, -1f, 16, 44, 1, 0f, true)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        headlights.texOffs(14, 23).addBox(-14f, 1f, 41.1f, 2, 2, 0, 0f, false)
        headlights.texOffs(14, 23).addBox(12f, 1f, 41.1f, 2, 2, 0, 0f, true)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        tail_lights.texOffs(14, 25).addBox(-16f, 1f, 41.1f, 2, 2, 0, 0f, false)
        tail_lights.texOffs(14, 25).addBox(-12f, 1f, 41.1f, 2, 2, 0, 0f, false)
        tail_lights.texOffs(14, 25).addBox(14f, 1f, 41.1f, 2, 2, 0, 0f, true)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_2_partial.setModelPart()
        window_exterior.setModelPart()
        window_exterior_2_partial.setModelPart()
        logo.setModelPart()
        door.setModelPart()
        door_sliding_1.setModelPart()
        door_sliding_1_part.setModelPart(door_sliding_1.name)
        door_sliding_2.setModelPart()
        door_sliding_2_part.setModelPart(door_sliding_2.name)
        door_exterior.setModelPart()
        door_sliding_exterior_1.setModelPart()
        door_sliding_exterior_1_part.setModelPart(door_sliding_exterior_1.name)
        door_sliding_exterior_2.setModelPart()
        door_sliding_exterior_2_part.setModelPart(door_sliding_exterior_2.name)
        side_seats.setModelPart()
        middle_seats.setModelPart()
        side_panel.setModelPart()
        side_panel_translucent.setModelPart()
        light_window.setModelPart()
        light_door.setModelPart()
        light_end.setModelPart()
        light_head.setModelPart()
        side_seats_end.setModelPart()
        side_seats_head.setModelPart()
        end.setModelPart()
        end_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ): ModelLondonUndergroundD78 {
        return ModelLondonUndergroundD78(doorAnimationType, renderDoorOverlay)
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
        val isEnd1 = isIndex(0, position, getWindowPositions())
        val isEnd2 = isIndex(-1, position, getWindowPositions())

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderOnceFlipped(light_window, matrices, vertices, light, position.toFloat())
                renderOnce(light_window, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(
                        if (isEnd1 || isEnd2) side_seats else middle_seats,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (!isEnd1 && !isEnd2) {
                        renderMirror(window_2_partial, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                if (renderDetails && !isEnd1 && !isEnd2) {
                    renderMirror(window_exterior_2_partial, matrices, vertices, light, position.toFloat())
                    renderMirror(logo, matrices, vertices, light, position.toFloat())
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
        val doorPositions = getDoorPositions()
        var isOdd = false
        for (i in doorPositions.indices) {
            if (isIndex(i, position, doorPositions)) {
                isOdd = (i % 2) == 1
                break
            }
        }

        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(light_door, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderMirror(door, matrices, vertices, light, position.toFloat())

                (if (isOdd) door_sliding_1_part else door_sliding_2_part).setOffset(
                    0f,
                    0,
                    (if (isOdd) -1 else 1) * doorRightZ
                )
                renderOnce(if (isOdd) door_sliding_1 else door_sliding_2, matrices, vertices, light, position.toFloat())
                (if (isOdd) door_sliding_2_part else door_sliding_1_part).setOffset(
                    0f,
                    0,
                    (if (isOdd) 1 else -1) * doorLeftZ
                )
                renderOnceFlipped(
                    if (isOdd) door_sliding_2 else door_sliding_1,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )

                if (renderDetails) {
                    renderMirror(side_panel, matrices, vertices, light, position - 11.9f)
                    renderMirror(side_panel, matrices, vertices, light, position + 11.9f)
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (renderDetails) {
                renderMirror(side_panel_translucent, matrices, vertices, light, position - 11.9f)
                renderMirror(side_panel_translucent, matrices, vertices, light, position + 11.9f)
            }

            RenderStage.EXTERIOR -> {
                renderMirror(door_exterior, matrices, vertices, light, position.toFloat())

                (if (isOdd) door_sliding_exterior_1_part else door_sliding_exterior_2_part).setOffset(
                    0f,
                    0,
                    (if (isOdd) -1 else 1) * doorRightZ
                )
                renderOnce(
                    if (isOdd) door_sliding_exterior_1 else door_sliding_exterior_2,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
                (if (isOdd) door_sliding_exterior_2_part else door_sliding_exterior_1_part).setOffset(
                    0f,
                    0,
                    (if (isOdd) 1 else -1) * doorLeftZ
                )
                renderOnceFlipped(
                    if (isOdd) door_sliding_exterior_2 else door_sliding_exterior_1,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
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
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                (position + 23).toFloat()
            )

            RenderStage.LIGHTS -> renderOnceFlipped(light_head, matrices, vertices, light, (position + 23).toFloat())
            RenderStage.INTERIOR -> {
                renderOnceFlipped(head, matrices, vertices, light, (position + 23).toFloat())
                if (renderDetails) {
                    renderOnceFlipped(side_seats_head, matrices, vertices, light, (position + 23).toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnceFlipped(
                head_exterior,
                matrices,
                vertices,
                light,
                (position + 23).toFloat()
            )

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
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                (position - 23).toFloat()
            )

            RenderStage.LIGHTS -> renderOnce(light_head, matrices, vertices, light, (position - 23).toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(head, matrices, vertices, light, (position - 23).toFloat())
                if (renderDetails) {
                    renderOnce(side_seats_head, matrices, vertices, light, (position - 23).toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnce(head_exterior, matrices, vertices, light, (position - 23).toFloat())
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
            RenderStage.LIGHTS -> {
                renderOnce(light_window, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(light_end, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> {
                renderOnce(window, matrices, vertices, light, position.toFloat())
                renderOnce(window_2_partial, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(side_seats, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(side_seats_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(window_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(window_exterior_2_partial, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> {
                renderOnceFlipped(light_window, matrices, vertices, light, position.toFloat())
                renderOnce(light_end, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> {
                renderOnceFlipped(window, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(window_2_partial, matrices, vertices, light, position.toFloat())
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(side_seats, matrices, vertices, light, position.toFloat())
                    renderOnce(side_seats_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(window_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(window_exterior_2_partial, matrices, vertices, light, position.toFloat())
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
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
        return intArrayOf(-64, 0, 64)
    }

    @Override
    override fun getDoorPositions(): IntArray {
        return intArrayOf(-96, -32, 32, 96)
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
        if (scrollingTexts!!.isEmpty()) {
            scrollingTexts.add(ScrollingText(0.72f, 0.08f, 8, true))
        }

        val destinationString: String? =
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false)
        renderFrontDestination(
            matrices, font, immediate,
            0f, -2.18f, getEndPositions()!![0] / 16f - 1.25f, 0f, 0f, -0.01f,
            0f, 0f, 0.8f, 0.14f,
            -0x6700, -0x6700, 1f, ModelTrainBase.getAlternatingString(destinationString), false, car, totalCars
        )

        val isEnd1Head = car == 0
        val isEnd2Head = car == totalCars - 1
        val nextStationString: String? = ModelTrainBase.getLondonNextStationString(
            thisRoute,
            nextRoute,
            thisStation,
            nextStation,
            lastStation,
            destinationString,
            atPlatform
        )
        scrollingTexts.get(0)!!.changeImage(
            if (nextStationString!!.isEmpty()) null else ClientData.DATA_CACHE.getPixelatedText(
                nextStationString,
                -0x6700,
                Integer.MAX_VALUE,
                0f,
                true
            )
        )
        scrollingTexts.get(0)!!.setVertexConsumer(vertexConsumers)

        for (i in 0..1) {
            matrices!!.pushPose()
            if (i == 1) {
                UtilitiesClient.rotateYDegrees(matrices, 180f)
            }
            matrices.translate(
                -0.36,
                -2.32,
                (getEndPositions()!![1] + 11 - (if (i == 1 && isEnd1Head || i == 0 && isEnd2Head) 16 else 0)) / 16f - 0.01
            )
            scrollingTexts.get(0)!!.scrollText(matrices)
            matrices.popPose()
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    companion object {
        private const val DOOR_MAX = 17
    }
}
