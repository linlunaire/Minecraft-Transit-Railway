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

open class ModelLondonUnderground1995 private constructor(
    private val is1995: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelLondonUnderground1995?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val window_1: ModelMapper
    private val roof_2_r1: ModelMapper
    private val roof_1_r1: ModelMapper
    private val window_side_pole_r1: ModelMapper
    private val window_top_r1: ModelMapper
    private val window_2: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_2_r2: ModelMapper
    private val window_side_pole_r2: ModelMapper
    private val window_top_r2: ModelMapper
    private val window_exterior: ModelMapper
    private val window_exterior_1: ModelMapper
    private val roof_2_r3: ModelMapper
    private val roof_1_r2: ModelMapper
    private val window_top_r3: ModelMapper
    private val window_middle_r1: ModelMapper
    private val window_exterior_2: ModelMapper
    private val roof_3_r2: ModelMapper
    private val roof_2_r4: ModelMapper
    private val window_top_r4: ModelMapper
    private val window_middle_r2: ModelMapper
    private val door_left: ModelMapper
    private val door_left_part: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_left_middle_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_part: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_right_middle_r1: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_left_exterior_part: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_left_middle_r2: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_right_exterior_part: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_right_middle_r2: ModelMapper
    private val seat: ModelMapper
    private val seat_1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val seat_back_r1: ModelMapper
    private val seat_2: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val seat_back_r2: ModelMapper
    private val seat_wheelchair: ModelMapper
    private val seat_wheelchair_1: ModelMapper
    private val handrail_4_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val seat_back_r3: ModelMapper
    private val seat_wheelchair_2: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_3_r3: ModelMapper
    private val seat_back_r4: ModelMapper
    private val window_light: ModelMapper
    private val light_2_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val window_light_end: ModelMapper
    private val light_3_r1: ModelMapper
    private val light_2_r2: ModelMapper
    private val window_light_head: ModelMapper
    private val light_2_r3: ModelMapper
    private val light_1_r2: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_r1: ModelMapper
    private val end: ModelMapper
    private val end_1: ModelMapper
    private val roof_2_r5: ModelMapper
    private val roof_1_r3: ModelMapper
    private val window_top_r5: ModelMapper
    private val window_middle_r3: ModelMapper
    private val front_box_top_r1: ModelMapper
    private val front_box_r1: ModelMapper
    private val front_side_r1: ModelMapper
    private val end_2: ModelMapper
    private val roof_3_r3: ModelMapper
    private val roof_2_r6: ModelMapper
    private val window_top_r6: ModelMapper
    private val window_middle_r4: ModelMapper
    private val front_box_top_r2: ModelMapper
    private val front_box_r2: ModelMapper
    private val front_side_r2: ModelMapper
    private val end_exterior: ModelMapper
    private val end_exterior_1: ModelMapper
    private val front_side_bottom_r1: ModelMapper
    private val front_side_6_r1: ModelMapper
    private val front_side_5_r1: ModelMapper
    private val front_side_4_r1: ModelMapper
    private val front_side_3_r1: ModelMapper
    private val front_side_2_r1: ModelMapper
    private val end_exterior_2: ModelMapper
    private val front_side_bottom_r2: ModelMapper
    private val front_side_7_r1: ModelMapper
    private val front_side_6_r2: ModelMapper
    private val front_side_5_r2: ModelMapper
    private val front_side_4_r2: ModelMapper
    private val front_side_3_r2: ModelMapper
    private val head: ModelMapper
    private val head_1: ModelMapper
    private val roof_2_r7: ModelMapper
    private val roof_1_r4: ModelMapper
    private val window_top_r7: ModelMapper
    private val window_middle_r5: ModelMapper
    private val head_2: ModelMapper
    private val roof_3_r4: ModelMapper
    private val roof_2_r8: ModelMapper
    private val window_top_r8: ModelMapper
    private val window_middle_r6: ModelMapper
    private val head_exterior: ModelMapper
    private val small_light_r1: ModelMapper
    private val front_side_right_r1: ModelMapper
    private val front_side_left_r1: ModelMapper
    private val head_exterior_1: ModelMapper
    private val front_side_bottom_r3: ModelMapper
    private val front_side_5_r3: ModelMapper
    private val front_side_4_r3: ModelMapper
    private val front_side_3_r3: ModelMapper
    private val front_side_1_r1: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val door_right_middle_r3: ModelMapper
    private val roof_1_r5: ModelMapper
    private val window_top_r9: ModelMapper
    private val window_middle_r7: ModelMapper
    private val head_exterior_2: ModelMapper
    private val front_side_bottom_r4: ModelMapper
    private val front_side_6_r3: ModelMapper
    private val front_side_5_r4: ModelMapper
    private val front_side_4_r4: ModelMapper
    private val front_side_2_r2: ModelMapper
    private val door_right_top_r4: ModelMapper
    private val door_right_middle_r4: ModelMapper
    private val roof_2_r9: ModelMapper
    private val window_top_r10: ModelMapper
    private val window_middle_r8: ModelMapper
    private val logo: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper
    private val headlights: ModelMapper
    private val light_2_r4: ModelMapper
    private val light_1_r3: ModelMapper
    private val tail_lights: ModelMapper
    private val light_3_r2: ModelMapper
    private val light_2_r5: ModelMapper

    constructor(is1995: Boolean) : this(is1995, DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 288
        val textureHeight = 288

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)


        window_1 = ModelMapper(modelDataWrapper)
        window_1.setPos(0f, 0f, 0f)
        window.addChild(window_1)
        window_1.texOffs(88, 0).addBox(-20f, 0f, 0f, 20, 1, 32, 0f, false)
        window_1.texOffs(64, 128).addBox(-20f, -6f, 0f, 3, 6, 36, 0f, false)
        window_1.texOffs(30, 0).addBox(-3f, -35f, 0f, 3, 0, 48, 0f, false)
        window_1.texOffs(0, 112).addBox(-14.369f, -32.0747f, 36f, 2, 1, 12, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(-3f, -35f, 0f)
        window_1.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, -0.5236f)
        roof_2_r1.texOffs(36, 0).addBox(-2f, 0f, 0f, 2, 0, 48, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(-9.771f, -32.5747f, 24f)
        window_1.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, 1.0472f)
        roof_1_r1.texOffs(0, 2).addBox(0f, -3f, -24f, 0, 6, 48, 0f, false)

        window_side_pole_r1 = ModelMapper(modelDataWrapper)
        window_side_pole_r1.setPos(-20f, -12f, 0f)
        window_1.addChild(window_side_pole_r1)
        setRotationAngle(window_side_pole_r1, 0f, 0f, 0.1571f)
        window_side_pole_r1.texOffs(216, 237).addBox(0f, -20f, 26f, 4, 27, 10, 0f, false)
        window_side_pole_r1.texOffs(108, 176).addBox(0f, -16f, 0f, 1, 16, 26, 0f, false)

        window_top_r1 = ModelMapper(modelDataWrapper)
        window_top_r1.setPos(-14.933f, -29.4388f, 0f)
        window_1.addChild(window_top_r1)
        setRotationAngle(window_top_r1, 0f, 0f, 0.8378f)
        window_top_r1.texOffs(0, 147).addBox(-0.5f, -3f, 0f, 1, 6, 36, 0f, false)

        window_2 = ModelMapper(modelDataWrapper)
        window_2.setPos(0f, 0f, 0f)
        window.addChild(window_2)
        window_2.texOffs(88, 0).addBox(0f, 0f, 0f, 20, 1, 32, 0f, true)
        window_2.texOffs(64, 128).addBox(17f, -6f, 0f, 3, 6, 36, 0f, true)
        window_2.texOffs(30, 0).addBox(0f, -35f, 0f, 3, 0, 48, 0f, true)
        window_2.texOffs(0, 112).addBox(12.369f, -32.0747f, 36f, 2, 1, 12, 0f, true)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(3f, -35f, 0f)
        window_2.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 0.5236f)
        roof_3_r1.texOffs(36, 0).addBox(0f, 0f, 0f, 2, 0, 48, 0f, true)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(9.771f, -32.5747f, 24f)
        window_2.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, -1.0472f)
        roof_2_r2.texOffs(0, 2).addBox(0f, -3f, -24f, 0, 6, 48, 0f, true)

        window_side_pole_r2 = ModelMapper(modelDataWrapper)
        window_side_pole_r2.setPos(20f, -12f, 0f)
        window_2.addChild(window_side_pole_r2)
        setRotationAngle(window_side_pole_r2, 0f, 0f, -0.1571f)
        window_side_pole_r2.texOffs(216, 237).addBox(-4f, -20f, 26f, 4, 27, 10, 0f, true)
        window_side_pole_r2.texOffs(108, 176).addBox(-1f, -16f, 0f, 1, 16, 26, 0f, true)

        window_top_r2 = ModelMapper(modelDataWrapper)
        window_top_r2.setPos(14.933f, -29.4388f, 0f)
        window_2.addChild(window_top_r2)
        setRotationAngle(window_top_r2, 0f, 0f, -0.8378f)
        window_top_r2.texOffs(0, 147).addBox(-0.5f, -3f, 0f, 1, 6, 36, 0f, true)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)


        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 0f, 0f)
        window_exterior.addChild(window_exterior_1)
        window_exterior_1.texOffs(42, 176).addBox(-21f, 0f, 0f, 1, 4, 32, 0f, false)
        window_exterior_1.texOffs(100, 41).addBox(-20f, -12f, 0f, 0, 12, 36, 0f, false)
        window_exterior_1.texOffs(22, 0).addBox(-4f, -36f, 0f, 4, 0, 48, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(-4f, -36f, 0f)
        window_exterior_1.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, -0.1745f)
        roof_2_r3.texOffs(12, 0).addBox(-5f, 0f, 0f, 5, 0, 48, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(-11.0223f, -32.7667f, 18f)
        window_exterior_1.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, -0.5236f)
        roof_1_r2.texOffs(0, 0).addBox(-3f, -1f, -18f, 6, 2, 48, 0f, false)

        window_top_r3 = ModelMapper(modelDataWrapper)
        window_top_r3.setPos(-14.933f, -29.4388f, 0f)
        window_exterior_1.addChild(window_top_r3)
        setRotationAngle(window_top_r3, 0f, 0f, 0.8378f)
        window_top_r3.texOffs(110, 134).addBox(-0.5f, -3f, 0f, 0, 6, 36, 0f, false)

        window_middle_r1 = ModelMapper(modelDataWrapper)
        window_middle_r1.setPos(-20f, -12f, 0f)
        window_exterior_1.addChild(window_middle_r1)
        setRotationAngle(window_middle_r1, 0f, 0f, 0.1571f)
        window_middle_r1.texOffs(0, 95).addBox(0f, -16f, 0f, 0, 16, 36, 0f, false)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 0f, 0f)
        window_exterior.addChild(window_exterior_2)
        window_exterior_2.texOffs(42, 176).addBox(20f, 0f, 0f, 1, 4, 32, 0f, true)
        window_exterior_2.texOffs(100, 41).addBox(20f, -12f, 0f, 0, 12, 36, 0f, true)
        window_exterior_2.texOffs(22, 0).addBox(0f, -36f, 0f, 4, 0, 48, 0f, true)

        roof_3_r2 = ModelMapper(modelDataWrapper)
        roof_3_r2.setPos(4f, -36f, 0f)
        window_exterior_2.addChild(roof_3_r2)
        setRotationAngle(roof_3_r2, 0f, 0f, 0.1745f)
        roof_3_r2.texOffs(12, 0).addBox(0f, 0f, 0f, 5, 0, 48, 0f, true)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(11.0223f, -32.7667f, 18f)
        window_exterior_2.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, 0.5236f)
        roof_2_r4.texOffs(0, 0).addBox(-3f, -1f, -18f, 6, 2, 48, 0f, true)

        window_top_r4 = ModelMapper(modelDataWrapper)
        window_top_r4.setPos(14.933f, -29.4388f, 0f)
        window_exterior_2.addChild(window_top_r4)
        setRotationAngle(window_top_r4, 0f, 0f, -0.8378f)
        window_top_r4.texOffs(110, 134).addBox(0.5f, -3f, 0f, 0, 6, 36, 0f, true)

        window_middle_r2 = ModelMapper(modelDataWrapper)
        window_middle_r2.setPos(20f, -12f, 0f)
        window_exterior_2.addChild(window_middle_r2)
        setRotationAngle(window_middle_r2, 0f, 0f, -0.1571f)
        window_middle_r2.texOffs(0, 95).addBox(0f, -16f, 0f, 0, 16, 36, 0f, true)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 24f, 0f)
        door_left.texOffs(0, 189).addBox(-20f, 0f, 0f, 20, 1, 16, 0f, false)

        door_left_part = ModelMapper(modelDataWrapper)
        door_left_part.setPos(0f, 0f, 0f)
        door_left.addChild(door_left_part)
        door_left_part.texOffs(70, 196).addBox(-19.8f, -12f, 0f, 0, 12, 22, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-15.6548f, -29.9327f, 11f)
        door_left_part.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.8378f)
        door_left_top_r1.texOffs(100, 67).addBox(0.5f, -3f, -11f, 0, 6, 22, 0f, false)

        door_left_middle_r1 = ModelMapper(modelDataWrapper)
        door_left_middle_r1.setPos(-20.8f, -12f, 0f)
        door_left_part.addChild(door_left_middle_r1)
        setRotationAngle(door_left_middle_r1, 0f, 0f, 0.1571f)
        door_left_middle_r1.texOffs(136, 162).addBox(1f, -17f, 0f, 0, 17, 22, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 24f, 0f)
        door_right.texOffs(160, 0).addBox(-20f, 0f, -16f, 20, 1, 16, 0f, false)

        door_right_part = ModelMapper(modelDataWrapper)
        door_right_part.setPos(0f, 0f, 0f)
        door_right.addChild(door_right_part)
        door_right_part.texOffs(24, 190).addBox(-19.8f, -12f, -22f, 0, 12, 22, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-15.6548f, -29.9327f, -11f)
        door_right_part.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.8378f)
        door_right_top_r1.texOffs(88, 17).addBox(0.5f, -3f, -11f, 0, 6, 22, 0f, false)

        door_right_middle_r1 = ModelMapper(modelDataWrapper)
        door_right_middle_r1.setPos(-20.8f, -12f, 0f)
        door_right_part.addChild(door_right_middle_r1)
        setRotationAngle(door_right_middle_r1, 0f, 0f, 0.1571f)
        door_right_middle_r1.texOffs(76, 154).addBox(1f, -17f, -22f, 0, 17, 22, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 24f, 0f)
        door_left_exterior.texOffs(238, 86).addBox(-21f, 0f, 0f, 1, 4, 16, 0f, false)

        door_left_exterior_part = ModelMapper(modelDataWrapper)
        door_left_exterior_part.setPos(0f, 0f, 0f)
        door_left_exterior.addChild(door_left_exterior_part)
        door_left_exterior_part.texOffs(220, 169).addBox(-20.8f, -12f, 0f, 1, 12, 22, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-15.6548f, -29.9327f, 11f)
        door_left_exterior_part.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.8378f)
        door_left_top_r2.texOffs(223, 209).addBox(-0.5f, -3f, -11f, 1, 6, 22, 0f, false)

        door_left_middle_r2 = ModelMapper(modelDataWrapper)
        door_left_middle_r2.setPos(-20.8f, -12f, 0f)
        door_left_exterior_part.addChild(door_left_middle_r2)
        setRotationAngle(door_left_middle_r2, 0f, 0f, 0.1571f)
        door_left_middle_r2.texOffs(46, 212).addBox(0f, -17f, 0f, 1, 17, 22, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 24f, 0f)
        door_right_exterior.texOffs(236, 0).addBox(-21f, 0f, -16f, 1, 4, 16, 0f, false)

        door_right_exterior_part = ModelMapper(modelDataWrapper)
        door_right_exterior_part.setPos(0f, 0f, 0f)
        door_right_exterior.addChild(door_right_exterior_part)
        door_right_exterior_part.texOffs(138, 218).addBox(-20.8f, -12f, -22f, 1, 12, 22, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-15.6548f, -29.9327f, -11f)
        door_right_exterior_part.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.8378f)
        door_right_top_r2.texOffs(222, 58).addBox(-0.5f, -3f, -11f, 1, 6, 22, 0f, false)

        door_right_middle_r2 = ModelMapper(modelDataWrapper)
        door_right_middle_r2.setPos(-20.8f, -12f, 0f)
        door_right_exterior_part.addChild(door_right_middle_r2)
        setRotationAngle(door_right_middle_r2, 0f, 0f, 0.1571f)
        door_right_middle_r2.texOffs(0, 206).addBox(0f, -17f, -22f, 1, 17, 22, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)


        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 0f, 0f)
        seat.addChild(seat_1)
        seat_1.texOffs(110, 100).addBox(-17f, -6f, 0f, 10, 6, 32, 0f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-7f, -4f, 0f)
        seat_1.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, -1.5708f, 0f, -0.0349f)
        handrail_3_r1.texOffs(0, 0).addBox(0f, -31.5f, -28f, 0, 32, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(-7f, -4f, 0f)
        seat_1.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, -0.0349f)
        handrail_2_r1.texOffs(0, 0).addBox(0f, -30f, 31.5f, 0, 30, 0, 0.2f, false)
        handrail_2_r1.texOffs(0, 0).addBox(0f, -30f, 13.5f, 0, 30, 0, 0.2f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-14f, -6f, 0f)
        seat_1.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.1745f)
        seat_back_r1.texOffs(164, 144).addBox(-4f, -8f, 0f, 4, 8, 32, 0f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 0f, 0f)
        seat.addChild(seat_2)
        seat_2.texOffs(110, 100).addBox(7f, -6f, 0f, 10, 6, 32, 0f, true)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(7f, -4f, 0f)
        seat_2.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, -1.5708f, 0f, 0.0349f)
        handrail_4_r1.texOffs(0, 0).addBox(0f, -31.5f, -28f, 0, 32, 0, 0.2f, true)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(7f, -4f, 0f)
        seat_2.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, 0.0349f)
        handrail_3_r2.texOffs(0, 0).addBox(0f, -30f, 31.5f, 0, 30, 0, 0.2f, true)
        handrail_3_r2.texOffs(0, 0).addBox(0f, -30f, 13.5f, 0, 30, 0, 0.2f, true)

        seat_back_r2 = ModelMapper(modelDataWrapper)
        seat_back_r2.setPos(14f, -6f, 0f)
        seat_2.addChild(seat_back_r2)
        setRotationAngle(seat_back_r2, 0f, 0f, 0.1745f)
        seat_back_r2.texOffs(164, 144).addBox(0f, -8f, 0f, 4, 8, 32, 0f, true)

        seat_wheelchair = ModelMapper(modelDataWrapper)
        seat_wheelchair.setPos(0f, 24f, 0f)


        seat_wheelchair_1 = ModelMapper(modelDataWrapper)
        seat_wheelchair_1.setPos(-7f, -4f, 0f)
        seat_wheelchair.addChild(seat_wheelchair_1)
        seat_wheelchair_1.texOffs(228, 116).addBox(-10f, -2f, 0f, 10, 6, 14, 0f, false)
        seat_wheelchair_1.texOffs(222, 27).addBox(-12f, -9f, 14f, 5, 13, 18, 0f, false)

        handrail_4_r2 = ModelMapper(modelDataWrapper)
        handrail_4_r2.setPos(0f, 0f, 0f)
        seat_wheelchair_1.addChild(handrail_4_r2)
        setRotationAngle(handrail_4_r2, -1.5708f, 0f, -0.0349f)
        handrail_4_r2.texOffs(0, 0).addBox(0f, -13.5f, -28f, 0, 14, 0, 0.2f, false)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(0f, 0f, 0f)
        seat_wheelchair_1.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, 0f, 0f, -0.0349f)
        handrail_2_r2.texOffs(0, 0).addBox(0f, -30f, 13.5f, 0, 30, 0, 0.2f, false)

        seat_back_r3 = ModelMapper(modelDataWrapper)
        seat_back_r3.setPos(-7f, -2f, 0f)
        seat_wheelchair_1.addChild(seat_back_r3)
        setRotationAngle(seat_back_r3, 0f, 0f, -0.1745f)
        seat_back_r3.texOffs(116, 218).addBox(-4f, -8f, 0f, 4, 8, 14, 0f, false)

        seat_wheelchair_2 = ModelMapper(modelDataWrapper)
        seat_wheelchair_2.setPos(7f, -4f, 0f)
        seat_wheelchair.addChild(seat_wheelchair_2)
        seat_wheelchair_2.texOffs(228, 116).addBox(0f, -2f, 0f, 10, 6, 14, 0f, true)
        seat_wheelchair_2.texOffs(222, 27).addBox(7f, -9f, 14f, 5, 13, 18, 0f, true)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(0f, 0f, 0f)
        seat_wheelchair_2.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, -1.5708f, 0f, 0.0349f)
        handrail_5_r1.texOffs(0, 0).addBox(0f, -13.5f, -28f, 0, 14, 0, 0.2f, true)

        handrail_3_r3 = ModelMapper(modelDataWrapper)
        handrail_3_r3.setPos(0f, 0f, 0f)
        seat_wheelchair_2.addChild(handrail_3_r3)
        setRotationAngle(handrail_3_r3, 0f, 0f, 0.0349f)
        handrail_3_r3.texOffs(0, 0).addBox(0f, -30f, 13.5f, 0, 30, 0, 0.2f, true)

        seat_back_r4 = ModelMapper(modelDataWrapper)
        seat_back_r4.setPos(7f, -2f, 0f)
        seat_wheelchair_2.addChild(seat_back_r4)
        setRotationAngle(seat_back_r4, 0f, 0f, 0.1745f)
        seat_back_r4.texOffs(116, 218).addBox(0f, -8f, 0f, 4, 8, 14, 0f, true)

        window_light = ModelMapper(modelDataWrapper)
        window_light.setPos(0f, 24f, 0f)


        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(6.1463f, -34f, 0f)
        window_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, 0f, 0f, 0.7854f)
        light_2_r1.texOffs(48, 50).addBox(-1f, -1f, 0f, 2, 2, 48, 0f, true)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-6.1463f, -34f, 0f)
        window_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, -0.7854f)
        light_1_r1.texOffs(48, 50).addBox(-1f, -1f, 0f, 2, 2, 48, 0f, false)

        window_light_end = ModelMapper(modelDataWrapper)
        window_light_end.setPos(0f, 24f, 0f)


        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(6.1463f, -34f, 48f)
        window_light_end.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 0f, 0.7854f)
        light_3_r1.texOffs(88, 90).addBox(-1f, -1f, 0f, 2, 2, 8, 0f, true)

        light_2_r2 = ModelMapper(modelDataWrapper)
        light_2_r2.setPos(-6.1463f, -34f, 48f)
        window_light_end.addChild(light_2_r2)
        setRotationAngle(light_2_r2, 0f, 0f, -0.7854f)
        light_2_r2.texOffs(88, 90).addBox(-1f, -1f, 0f, 2, 2, 8, 0f, false)

        window_light_head = ModelMapper(modelDataWrapper)
        window_light_head.setPos(0f, 24f, 0f)


        light_2_r3 = ModelMapper(modelDataWrapper)
        light_2_r3.setPos(6.1463f, -34f, 0f)
        window_light_head.addChild(light_2_r3)
        setRotationAngle(light_2_r3, 0f, 0f, 0.7854f)
        light_2_r3.texOffs(71, 73).addBox(-1f, -1f, 0f, 2, 2, 25, 0f, true)

        light_1_r2 = ModelMapper(modelDataWrapper)
        light_1_r2.setPos(-6.1463f, -34f, 0f)
        window_light_head.addChild(light_1_r2)
        setRotationAngle(light_1_r2, 0f, 0f, -0.7854f)
        light_1_r2.texOffs(71, 73).addBox(-1f, -1f, 0f, 2, 2, 25, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)


        side_panel_r1 = ModelMapper(modelDataWrapper)
        side_panel_r1.setPos(-8f, -6f, 0f)
        side_panel.addChild(side_panel_r1)
        setRotationAngle(side_panel_r1, 0f, 0f, -0.0349f)
        side_panel_r1.texOffs(168, 252).addBox(-8f, -28f, 0f, 8, 28, 0, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(244, 237).addBox(-5f, -36f, 56f, 10, 36, 0, 0f, false)

        end_1 = ModelMapper(modelDataWrapper)
        end_1.setPos(0f, 0f, 0f)
        end.addChild(end_1)
        end_1.texOffs(122, 154).addBox(-20f, 0f, 48f, 20, 1, 9, 0f, false)
        end_1.texOffs(106, 132).addBox(-5.5f, -14f, 50f, 0, 14, 6, 0f, false)
        end_1.texOffs(171, 138).addBox(-20f, -12f, 48f, 1, 12, 6, 0f, false)
        end_1.texOffs(8, 17).addBox(-3f, -35f, 48f, 3, 0, 8, 0f, false)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(-3f, -35f, 49f)
        end_1.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0f, -0.5236f)
        roof_2_r5.texOffs(27, 0).addBox(-2f, 0f, -1f, 2, 0, 8, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(-17.8152f, -28.5077f, 49f)
        end_1.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, 1.0472f)
        roof_1_r3.texOffs(24, 26).addBox(0.5f, -12f, -1f, 0, 6, 8, 0f, false)

        window_top_r5 = ModelMapper(modelDataWrapper)
        window_top_r5.setPos(-14.933f, -29.4388f, 49f)
        end_1.addChild(window_top_r5)
        setRotationAngle(window_top_r5, 0f, 0f, 0.8378f)
        window_top_r5.texOffs(38, 156).addBox(-0.5f, -3f, -1f, 1, 6, 7, 0f, false)

        window_middle_r3 = ModelMapper(modelDataWrapper)
        window_middle_r3.setPos(-20f, -12f, 49f)
        end_1.addChild(window_middle_r3)
        setRotationAngle(window_middle_r3, 0f, 0f, 0.1571f)
        window_middle_r3.texOffs(118, 45).addBox(0f, -16f, -1f, 1, 16, 6, 0f, false)

        front_box_top_r1 = ModelMapper(modelDataWrapper)
        front_box_top_r1.setPos(-5.5f, -14f, 56f)
        end_1.addChild(front_box_top_r1)
        setRotationAngle(front_box_top_r1, 0.1745f, -0.1745f, 0f)
        front_box_top_r1.texOffs(82, 22).addBox(-15f, 0f, -6f, 15, 0, 6, 0f, false)

        front_box_r1 = ModelMapper(modelDataWrapper)
        front_box_r1.setPos(-5.5f, 0f, 56f)
        end_1.addChild(front_box_r1)
        setRotationAngle(front_box_r1, 0f, -0.1745f, 0f)
        front_box_r1.texOffs(0, 98).addBox(-15f, -14f, -5f, 15, 14, 0, 0f, false)

        front_side_r1 = ModelMapper(modelDataWrapper)
        front_side_r1.setPos(-5f, -36f, 56f)
        end_1.addChild(front_side_r1)
        setRotationAngle(front_side_r1, 0f, -0.1745f, 0f)
        front_side_r1.texOffs(0, 56).addBox(-16f, 0f, 0f, 16, 36, 0, 0f, false)

        end_2 = ModelMapper(modelDataWrapper)
        end_2.setPos(0f, 0f, 0f)
        end.addChild(end_2)
        end_2.texOffs(122, 154).addBox(0f, 0f, 48f, 20, 1, 9, 0f, true)
        end_2.texOffs(106, 132).addBox(5.5f, -14f, 50f, 0, 14, 6, 0f, true)
        end_2.texOffs(171, 138).addBox(19f, -12f, 48f, 1, 12, 6, 0f, true)
        end_2.texOffs(8, 17).addBox(0f, -35f, 48f, 3, 0, 8, 0f, true)

        roof_3_r3 = ModelMapper(modelDataWrapper)
        roof_3_r3.setPos(3f, -35f, 49f)
        end_2.addChild(roof_3_r3)
        setRotationAngle(roof_3_r3, 0f, 0f, 0.5236f)
        roof_3_r3.texOffs(27, 0).addBox(0f, 0f, -1f, 2, 0, 8, 0f, true)

        roof_2_r6 = ModelMapper(modelDataWrapper)
        roof_2_r6.setPos(17.8152f, -28.5077f, 49f)
        end_2.addChild(roof_2_r6)
        setRotationAngle(roof_2_r6, 0f, 0f, -1.0472f)
        roof_2_r6.texOffs(24, 26).addBox(-0.5f, -12f, -1f, 0, 6, 8, 0f, true)

        window_top_r6 = ModelMapper(modelDataWrapper)
        window_top_r6.setPos(14.933f, -29.4388f, 49f)
        end_2.addChild(window_top_r6)
        setRotationAngle(window_top_r6, 0f, 0f, -0.8378f)
        window_top_r6.texOffs(38, 156).addBox(-0.5f, -3f, -1f, 1, 6, 7, 0f, true)

        window_middle_r4 = ModelMapper(modelDataWrapper)
        window_middle_r4.setPos(20f, -12f, 49f)
        end_2.addChild(window_middle_r4)
        setRotationAngle(window_middle_r4, 0f, 0f, -0.1571f)
        window_middle_r4.texOffs(118, 45).addBox(-1f, -16f, -1f, 1, 16, 6, 0f, true)

        front_box_top_r2 = ModelMapper(modelDataWrapper)
        front_box_top_r2.setPos(5.5f, -14f, 56f)
        end_2.addChild(front_box_top_r2)
        setRotationAngle(front_box_top_r2, 0.1745f, 0.1745f, 0f)
        front_box_top_r2.texOffs(82, 22).addBox(0f, 0f, -6f, 15, 0, 6, 0f, true)

        front_box_r2 = ModelMapper(modelDataWrapper)
        front_box_r2.setPos(5.5f, 0f, 56f)
        end_2.addChild(front_box_r2)
        setRotationAngle(front_box_r2, 0f, 0.1745f, 0f)
        front_box_r2.texOffs(0, 98).addBox(0f, -14f, -5f, 15, 14, 0, 0f, true)

        front_side_r2 = ModelMapper(modelDataWrapper)
        front_side_r2.setPos(5f, -36f, 56f)
        end_2.addChild(front_side_r2)
        setRotationAngle(front_side_r2, 0f, 0.1745f, 0f)
        front_side_r2.texOffs(0, 56).addBox(0f, 0f, 0f, 16, 36, 0, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(0, 245).addBox(-5f, -36f, 57f, 10, 36, 0, 0f, false)
        end_exterior.texOffs(38, 147).addBox(-5f, 0f, 52f, 10, 4, 5, 0f, false)

        end_exterior_1 = ModelMapper(modelDataWrapper)
        end_exterior_1.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_1)
        end_exterior_1.texOffs(100, 51).addBox(-21f, 0f, 32f, 1, 4, 16, 0f, false)
        end_exterior_1.texOffs(23, 56).addBox(-4f, -36f, 48f, 4, 0, 9, 0f, false)

        front_side_bottom_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_r1.setPos(-5f, -36f, 57f)
        end_exterior_1.addChild(front_side_bottom_r1)
        setRotationAngle(front_side_bottom_r1, 0f, -0.1745f, 0f)
        front_side_bottom_r1.texOffs(160, 17).addBox(-16f, 36f, -5f, 16, 4, 5, 0f, false)
        front_side_bottom_r1.texOffs(62, 56).addBox(-16f, 0f, 0f, 16, 36, 0, 0f, false)

        front_side_6_r1 = ModelMapper(modelDataWrapper)
        front_side_6_r1.setPos(-4f, -36f, 40f)
        end_exterior_1.addChild(front_side_6_r1)
        setRotationAngle(front_side_6_r1, 0f, 0f, -0.1745f)
        front_side_6_r1.texOffs(0, 28).addBox(-5f, 0f, 8f, 5, 0, 9, 0f, false)

        front_side_5_r1 = ModelMapper(modelDataWrapper)
        front_side_5_r1.setPos(-11.0218f, -32.7659f, 58f)
        end_exterior_1.addChild(front_side_5_r1)
        setRotationAngle(front_side_5_r1, 0f, 0f, -0.5236f)
        front_side_5_r1.texOffs(228, 136).addBox(-4f, -1f, -10f, 7, 1, 9, 0f, false)

        front_side_4_r1 = ModelMapper(modelDataWrapper)
        front_side_4_r1.setPos(-15.8157f, -30.1796f, 11f)
        end_exterior_1.addChild(front_side_4_r1)
        setRotationAngle(front_side_4_r1, 0f, 0f, 0.8378f)
        front_side_4_r1.texOffs(110, 144).addBox(-0.5f, -2f, 37f, 1, 5, 8, 0f, false)

        front_side_3_r1 = ModelMapper(modelDataWrapper)
        front_side_3_r1.setPos(-21f, -12f, 48f)
        end_exterior_1.addChild(front_side_3_r1)
        setRotationAngle(front_side_3_r1, 0f, 0.0873f, 0.1571f)
        front_side_3_r1.texOffs(247, 203).addBox(0f, -19f, 0f, 1, 19, 7, 0f, false)

        front_side_2_r1 = ModelMapper(modelDataWrapper)
        front_side_2_r1.setPos(-21f, 4f, 48f)
        end_exterior_1.addChild(front_side_2_r1)
        setRotationAngle(front_side_2_r1, 0f, 0.0873f, 0f)
        front_side_2_r1.texOffs(74, 131).addBox(0f, -16f, 0f, 1, 16, 7, 0f, false)

        end_exterior_2 = ModelMapper(modelDataWrapper)
        end_exterior_2.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_2)
        end_exterior_2.texOffs(100, 51).addBox(20f, 0f, 32f, 1, 4, 16, 0f, true)
        end_exterior_2.texOffs(23, 56).addBox(0f, -36f, 48f, 4, 0, 9, 0f, true)

        front_side_bottom_r2 = ModelMapper(modelDataWrapper)
        front_side_bottom_r2.setPos(5f, -36f, 57f)
        end_exterior_2.addChild(front_side_bottom_r2)
        setRotationAngle(front_side_bottom_r2, 0f, 0.1745f, 0f)
        front_side_bottom_r2.texOffs(160, 17).addBox(0f, 36f, -5f, 16, 4, 5, 0f, true)
        front_side_bottom_r2.texOffs(62, 56).addBox(0f, 0f, 0f, 16, 36, 0, 0f, true)

        front_side_7_r1 = ModelMapper(modelDataWrapper)
        front_side_7_r1.setPos(4f, -36f, 40f)
        end_exterior_2.addChild(front_side_7_r1)
        setRotationAngle(front_side_7_r1, 0f, 0f, 0.1745f)
        front_side_7_r1.texOffs(0, 28).addBox(0f, 0f, 8f, 5, 0, 9, 0f, true)

        front_side_6_r2 = ModelMapper(modelDataWrapper)
        front_side_6_r2.setPos(11.0218f, -32.7659f, 58f)
        end_exterior_2.addChild(front_side_6_r2)
        setRotationAngle(front_side_6_r2, 0f, 0f, 0.5236f)
        front_side_6_r2.texOffs(228, 136).addBox(-3f, -1f, -10f, 7, 1, 9, 0f, true)

        front_side_5_r2 = ModelMapper(modelDataWrapper)
        front_side_5_r2.setPos(15.8157f, -30.1796f, 11f)
        end_exterior_2.addChild(front_side_5_r2)
        setRotationAngle(front_side_5_r2, 0f, 0f, -0.8378f)
        front_side_5_r2.texOffs(110, 144).addBox(-0.5f, -2f, 37f, 1, 5, 8, 0f, true)

        front_side_4_r2 = ModelMapper(modelDataWrapper)
        front_side_4_r2.setPos(21f, -12f, 48f)
        end_exterior_2.addChild(front_side_4_r2)
        setRotationAngle(front_side_4_r2, 0f, -0.0873f, -0.1571f)
        front_side_4_r2.texOffs(247, 203).addBox(-1f, -19f, 0f, 1, 19, 7, 0f, true)

        front_side_3_r2 = ModelMapper(modelDataWrapper)
        front_side_3_r2.setPos(21f, 4f, 48f)
        end_exterior_2.addChild(front_side_3_r2)
        setRotationAngle(front_side_3_r2, 0f, -0.0873f, 0f)
        front_side_3_r2.texOffs(74, 131).addBox(-1f, -16f, 0f, 1, 16, 7, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(162, 95).addBox(-19f, -35f, 25f, 38, 35, 0, 0f, false)

        head_1 = ModelMapper(modelDataWrapper)
        head_1.setPos(0f, 0f, 0f)
        head.addChild(head_1)
        head_1.texOffs(157, 69).addBox(-20f, 0f, 0f, 20, 1, 25, 0f, false)
        head_1.texOffs(189, 200).addBox(-20f, -6f, 0f, 3, 6, 25, 0f, false)
        head_1.texOffs(0, 0).addBox(-3f, -35f, 0f, 3, 0, 25, 0f, false)

        roof_2_r7 = ModelMapper(modelDataWrapper)
        roof_2_r7.setPos(-3f, -35f, 0f)
        head_1.addChild(roof_2_r7)
        setRotationAngle(roof_2_r7, 0f, 0f, -0.5236f)
        roof_2_r7.texOffs(6, 0).addBox(-2f, 0f, 0f, 2, 0, 25, 0f, false)

        roof_1_r4 = ModelMapper(modelDataWrapper)
        roof_1_r4.setPos(-17.8152f, -28.5077f, 0f)
        head_1.addChild(roof_1_r4)
        setRotationAngle(roof_1_r4, 0f, 0f, 1.0472f)
        roof_1_r4.texOffs(88, 8).addBox(0.5f, -12f, 0f, 0, 6, 25, 0f, false)

        window_top_r7 = ModelMapper(modelDataWrapper)
        window_top_r7.setPos(-14.933f, -29.4388f, 0f)
        head_1.addChild(window_top_r7)
        setRotationAngle(window_top_r7, 0f, 0f, 0.8378f)
        window_top_r7.texOffs(47, 131).addBox(-0.5f, -3f, 0f, 1, 6, 25, 0f, false)

        window_middle_r5 = ModelMapper(modelDataWrapper)
        window_middle_r5.setPos(-20f, -12f, 0f)
        head_1.addChild(window_middle_r5)
        setRotationAngle(window_middle_r5, 0f, 0f, 0.1571f)
        window_middle_r5.texOffs(162, 184).addBox(0f, -16f, 0f, 1, 16, 25, 0f, false)

        head_2 = ModelMapper(modelDataWrapper)
        head_2.setPos(0f, 0f, 0f)
        head.addChild(head_2)
        head_2.texOffs(157, 69).addBox(0f, 0f, 0f, 20, 1, 25, 0f, true)
        head_2.texOffs(189, 200).addBox(17f, -6f, 0f, 3, 6, 25, 0f, true)
        head_2.texOffs(0, 0).addBox(0f, -35f, 0f, 3, 0, 25, 0f, true)

        roof_3_r4 = ModelMapper(modelDataWrapper)
        roof_3_r4.setPos(3f, -35f, 0f)
        head_2.addChild(roof_3_r4)
        setRotationAngle(roof_3_r4, 0f, 0f, 0.5236f)
        roof_3_r4.texOffs(6, 0).addBox(0f, 0f, 0f, 2, 0, 25, 0f, true)

        roof_2_r8 = ModelMapper(modelDataWrapper)
        roof_2_r8.setPos(17.8152f, -28.5077f, 0f)
        head_2.addChild(roof_2_r8)
        setRotationAngle(roof_2_r8, 0f, 0f, -1.0472f)
        roof_2_r8.texOffs(88, 8).addBox(-0.5f, -12f, 0f, 0, 6, 25, 0f, true)

        window_top_r8 = ModelMapper(modelDataWrapper)
        window_top_r8.setPos(14.933f, -29.4388f, 0f)
        head_2.addChild(window_top_r8)
        setRotationAngle(window_top_r8, 0f, 0f, -0.8378f)
        window_top_r8.texOffs(47, 131).addBox(-0.5f, -3f, 0f, 1, 6, 25, 0f, true)

        window_middle_r6 = ModelMapper(modelDataWrapper)
        window_middle_r6.setPos(20f, -12f, 0f)
        head_2.addChild(window_middle_r6)
        setRotationAngle(window_middle_r6, 0f, 0f, -0.1571f)
        window_middle_r6.texOffs(162, 184).addBox(-1f, -16f, 0f, 1, 16, 25, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(142, 33).addBox(-20f, -36f, 26f, 40, 36, 0, 0f, false)
        head_exterior.texOffs(20, 245).addBox(-5f, -36f, 57f, 10, 36, 0, 0f, false)
        head_exterior.texOffs(189, 184).addBox(-5f, 0f, 52f, 10, 4, 5, 0f, false)

        small_light_r1 = ModelMapper(modelDataWrapper)
        small_light_r1.setPos(5f, -36f, 57f)
        head_exterior.addChild(small_light_r1)
        setRotationAngle(small_light_r1, 0f, 0.1745f, 0f)
        small_light_r1.texOffs(0, 189).addBox(2f, 30f, 0.05f, 5, 4, 0, 0f, true)

        front_side_right_r1 = ModelMapper(modelDataWrapper)
        front_side_right_r1.setPos(21f, -12f, 40f)
        head_exterior.addChild(front_side_right_r1)
        setRotationAngle(front_side_right_r1, 0f, -0.0873f, -0.1571f)
        front_side_right_r1.texOffs(136, 252).addBox(-1f, -19f, 0f, 1, 19, 15, 0f, true)

        front_side_left_r1 = ModelMapper(modelDataWrapper)
        front_side_left_r1.setPos(-21f, -12f, 40f)
        head_exterior.addChild(front_side_left_r1)
        setRotationAngle(front_side_left_r1, 0f, 0.0873f, 0.1571f)
        front_side_left_r1.texOffs(184, 231).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        head_exterior_1 = ModelMapper(modelDataWrapper)
        head_exterior_1.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_1)
        head_exterior_1.texOffs(100, 33).addBox(-21f, 0f, 0f, 1, 4, 40, 0f, false)
        head_exterior_1.texOffs(0, 100).addBox(-20f, 0f, 25f, 20, 1, 30, 0f, false)
        head_exterior_1.texOffs(70, 80).addBox(-20f, -12f, 0f, 0, 12, 36, 0f, false)
        head_exterior_1.texOffs(0, 0).addBox(-4f, -36f, 0f, 4, 0, 40, 0f, false)
        head_exterior_1.texOffs(92, 218).addBox(-20.8f, -12f, 18f, 1, 12, 22, 0f, false)
        head_exterior_1.texOffs(0, 0).addBox(-4f, -36f, 40f, 4, 0, 17, 0f, false)

        front_side_bottom_r3 = ModelMapper(modelDataWrapper)
        front_side_bottom_r3.setPos(-5f, -36f, 57f)
        head_exterior_1.addChild(front_side_bottom_r3)
        setRotationAngle(front_side_bottom_r3, 0f, -0.1745f, 0f)
        front_side_bottom_r3.texOffs(111, 252).addBox(-15f, 36f, -5f, 15, 4, 5, 0f, false)
        front_side_bottom_r3.texOffs(0, 147).addBox(-15f, 0f, 0f, 15, 36, 0, 0f, false)

        front_side_5_r3 = ModelMapper(modelDataWrapper)
        front_side_5_r3.setPos(-4f, -36f, 40f)
        head_exterior_1.addChild(front_side_5_r3)
        setRotationAngle(front_side_5_r3, 0f, 0f, -0.1745f)
        front_side_5_r3.texOffs(83, 50).addBox(-5f, 0f, 0f, 5, 0, 17, 0f, false)
        front_side_5_r3.texOffs(12, 56).addBox(-5f, 0f, -40f, 5, 0, 40, 0f, false)

        front_side_4_r3 = ModelMapper(modelDataWrapper)
        front_side_4_r3.setPos(-11.0218f, -32.7659f, 58f)
        head_exterior_1.addChild(front_side_4_r3)
        setRotationAngle(front_side_4_r3, 0f, 0f, -0.5236f)
        front_side_4_r3.texOffs(61, 251).addBox(-4f, -1f, -18f, 7, 1, 17, 0f, false)

        front_side_3_r3 = ModelMapper(modelDataWrapper)
        front_side_3_r3.setPos(-15.8157f, -30.1796f, 11f)
        head_exterior_1.addChild(front_side_3_r3)
        setRotationAngle(front_side_3_r3, 0f, 0f, 0.8378f)
        front_side_3_r3.texOffs(93, 253).addBox(-0.5f, -2f, 29f, 1, 5, 16, 0f, false)

        front_side_1_r1 = ModelMapper(modelDataWrapper)
        front_side_1_r1.setPos(-21f, 4f, 40f)
        head_exterior_1.addChild(front_side_1_r1)
        setRotationAngle(front_side_1_r1, 0f, 0.0873f, 0f)
        front_side_1_r1.texOffs(244, 154).addBox(0f, -16f, 0f, 1, 16, 15, 0f, false)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(-15.6548f, -29.9327f, 11f)
        head_exterior_1.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, 0.8378f)
        door_right_top_r3.texOffs(0, 18).addBox(-0.5f, -3f, 7f, 1, 6, 22, 0f, false)

        door_right_middle_r3 = ModelMapper(modelDataWrapper)
        door_right_middle_r3.setPos(-20.8f, -12f, 22f)
        head_exterior_1.addChild(door_right_middle_r3)
        setRotationAngle(door_right_middle_r3, 0f, 0f, 0.1571f)
        door_right_middle_r3.texOffs(204, 130).addBox(0f, -17f, -4f, 1, 17, 22, 0f, false)

        roof_1_r5 = ModelMapper(modelDataWrapper)
        roof_1_r5.setPos(-11.0223f, -32.7667f, 18f)
        head_exterior_1.addChild(roof_1_r5)
        setRotationAngle(roof_1_r5, 0f, 0f, -0.5236f)
        roof_1_r5.texOffs(0, 56).addBox(-3f, -1f, -18f, 6, 2, 40, 0f, false)

        window_top_r9 = ModelMapper(modelDataWrapper)
        window_top_r9.setPos(-14.933f, -29.4388f, 0f)
        head_exterior_1.addChild(window_top_r9)
        setRotationAngle(window_top_r9, 0f, 0f, 0.8378f)
        window_top_r9.texOffs(38, 134).addBox(-0.5f, -3f, 0f, 0, 6, 36, 0f, false)

        window_middle_r7 = ModelMapper(modelDataWrapper)
        window_middle_r7.setPos(-20f, -12f, 0f)
        head_exterior_1.addChild(window_middle_r7)
        setRotationAngle(window_middle_r7, 0f, 0f, 0.1571f)
        window_middle_r7.texOffs(70, 64).addBox(0f, -16f, 0f, 0, 16, 36, 0f, false)

        head_exterior_2 = ModelMapper(modelDataWrapper)
        head_exterior_2.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_2)
        head_exterior_2.texOffs(100, 33).addBox(20f, 0f, 0f, 1, 4, 40, 0f, true)
        head_exterior_2.texOffs(0, 100).addBox(0f, 0f, 25f, 20, 1, 30, 0f, true)
        head_exterior_2.texOffs(70, 80).addBox(20f, -12f, 0f, 0, 12, 36, 0f, true)
        head_exterior_2.texOffs(0, 0).addBox(0f, -36f, 0f, 4, 0, 40, 0f, true)
        head_exterior_2.texOffs(92, 218).addBox(19.8f, -12f, 18f, 1, 12, 22, 0f, true)
        head_exterior_2.texOffs(0, 0).addBox(0f, -36f, 40f, 4, 0, 17, 0f, true)

        front_side_bottom_r4 = ModelMapper(modelDataWrapper)
        front_side_bottom_r4.setPos(5f, -36f, 57f)
        head_exterior_2.addChild(front_side_bottom_r4)
        setRotationAngle(front_side_bottom_r4, 0f, 0.1745f, 0f)
        front_side_bottom_r4.texOffs(111, 252).addBox(0f, 36f, -5f, 15, 4, 5, 0f, true)
        front_side_bottom_r4.texOffs(0, 147).addBox(0f, 0f, 0f, 15, 36, 0, 0f, true)

        front_side_6_r3 = ModelMapper(modelDataWrapper)
        front_side_6_r3.setPos(4f, -36f, 40f)
        head_exterior_2.addChild(front_side_6_r3)
        setRotationAngle(front_side_6_r3, 0f, 0f, 0.1745f)
        front_side_6_r3.texOffs(83, 50).addBox(0f, 0f, 0f, 5, 0, 17, 0f, true)
        front_side_6_r3.texOffs(12, 56).addBox(0f, 0f, -40f, 5, 0, 40, 0f, true)

        front_side_5_r4 = ModelMapper(modelDataWrapper)
        front_side_5_r4.setPos(11.0218f, -32.7659f, 58f)
        head_exterior_2.addChild(front_side_5_r4)
        setRotationAngle(front_side_5_r4, 0f, 0f, 0.5236f)
        front_side_5_r4.texOffs(61, 251).addBox(-3f, -1f, -18f, 7, 1, 17, 0f, true)

        front_side_4_r4 = ModelMapper(modelDataWrapper)
        front_side_4_r4.setPos(15.8157f, -30.1796f, 11f)
        head_exterior_2.addChild(front_side_4_r4)
        setRotationAngle(front_side_4_r4, 0f, 0f, -0.8378f)
        front_side_4_r4.texOffs(93, 253).addBox(-0.5f, -2f, 29f, 1, 5, 16, 0f, true)

        front_side_2_r2 = ModelMapper(modelDataWrapper)
        front_side_2_r2.setPos(21f, 4f, 40f)
        head_exterior_2.addChild(front_side_2_r2)
        setRotationAngle(front_side_2_r2, 0f, -0.0873f, 0f)
        front_side_2_r2.texOffs(244, 154).addBox(-1f, -16f, 0f, 1, 16, 15, 0f, true)

        door_right_top_r4 = ModelMapper(modelDataWrapper)
        door_right_top_r4.setPos(15.6548f, -29.9327f, 11f)
        head_exterior_2.addChild(door_right_top_r4)
        setRotationAngle(door_right_top_r4, 0f, 0f, -0.8378f)
        door_right_top_r4.texOffs(0, 18).addBox(-0.5f, -3f, 7f, 1, 6, 22, 0f, true)

        door_right_middle_r4 = ModelMapper(modelDataWrapper)
        door_right_middle_r4.setPos(20.8f, -12f, 22f)
        head_exterior_2.addChild(door_right_middle_r4)
        setRotationAngle(door_right_middle_r4, 0f, 0f, -0.1571f)
        door_right_middle_r4.texOffs(204, 130).addBox(-1f, -17f, -4f, 1, 17, 22, 0f, true)

        roof_2_r9 = ModelMapper(modelDataWrapper)
        roof_2_r9.setPos(11.0223f, -32.7667f, 18f)
        head_exterior_2.addChild(roof_2_r9)
        setRotationAngle(roof_2_r9, 0f, 0f, 0.5236f)
        roof_2_r9.texOffs(0, 56).addBox(-3f, -1f, -18f, 6, 2, 40, 0f, true)

        window_top_r10 = ModelMapper(modelDataWrapper)
        window_top_r10.setPos(14.933f, -29.4388f, 0f)
        head_exterior_2.addChild(window_top_r10)
        setRotationAngle(window_top_r10, 0f, 0f, -0.8378f)
        window_top_r10.texOffs(38, 134).addBox(0.5f, -3f, 0f, 0, 6, 36, 0f, true)

        window_middle_r8 = ModelMapper(modelDataWrapper)
        window_middle_r8.setPos(20f, -12f, 0f)
        head_exterior_2.addChild(window_middle_r8)
        setRotationAngle(window_middle_r8, 0f, 0f, -0.1571f)
        window_middle_r8.texOffs(70, 64).addBox(0f, -16f, 0f, 0, 16, 36, 0f, true)

        logo = ModelMapper(modelDataWrapper)
        logo.setPos(0f, 24f, 0f)
        logo.texOffs(202, 9).addBox(-20.1f, -12f, -4f, 0, 8, 8, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-14.933f, -29.4388f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.8378f)
        light_r1.texOffs(3, 0).addBox(-1f, -1f, -1f, 1, 1, 2, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-14.933f, -29.4388f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.8378f)
        light_r2.texOffs(3, 3).addBox(-1f, -1f, -1f, 1, 1, 2, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        light_2_r4 = ModelMapper(modelDataWrapper)
        light_2_r4.setPos(5f, -36f, 57f)
        headlights.addChild(light_2_r4)
        setRotationAngle(light_2_r4, 0f, 0.1745f, 0f)
        light_2_r4.texOffs(0, 206).addBox(2f, 26f, 0.1f, 9, 4, 0, 0f, true)

        light_1_r3 = ModelMapper(modelDataWrapper)
        light_1_r3.setPos(-5f, -36f, 57f)
        headlights.addChild(light_1_r3)
        setRotationAngle(light_1_r3, 0f, -0.1745f, 0f)
        light_1_r3.texOffs(0, 206).addBox(-11f, 26f, 0.1f, 9, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        light_3_r2 = ModelMapper(modelDataWrapper)
        light_3_r2.setPos(5f, -36f, 57f)
        tail_lights.addChild(light_3_r2)
        setRotationAngle(light_3_r2, 0f, 0.1745f, 0f)
        light_3_r2.texOffs(0, 210).addBox(2f, 26f, 0.1f, 9, 4, 0, 0f, true)

        light_2_r5 = ModelMapper(modelDataWrapper)
        light_2_r5.setPos(-5f, -36f, 57f)
        tail_lights.addChild(light_2_r5)
        setRotationAngle(light_2_r5, 0f, -0.1745f, 0f)
        light_2_r5.texOffs(0, 210).addBox(-11f, 26f, 0.1f, 9, 4, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior.setModelPart()
        door_left.setModelPart()
        door_left_part.setModelPart(door_left.name)
        door_right.setModelPart()
        door_right_part.setModelPart(door_right.name)
        door_left_exterior.setModelPart()
        door_left_exterior_part.setModelPart(door_left_exterior.name)
        door_right_exterior.setModelPart()
        door_right_exterior_part.setModelPart(door_right_exterior.name)
        seat.setModelPart()
        seat_wheelchair.setModelPart()
        window_light.setModelPart()
        window_light_end.setModelPart()
        window_light_head.setModelPart()
        side_panel.setModelPart()
        end.setModelPart()
        end_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        logo.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ): ModelLondonUnderground1995 {
        return ModelLondonUnderground1995(is1995, doorAnimationType, renderDoorOverlay)
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
        val useHead1 = isEnd1Head && isEnd1
        val useHead2 = isEnd2Head && isEnd2

        when (renderStage!!) {
            RenderStage.LIGHTS -> if (useHead1) {
                renderOnceFlipped(window_light_head, matrices, vertices, light, position.toFloat())
                renderOnce(window_light, matrices, vertices, light, position.toFloat())
            } else if (useHead2) {
                renderOnce(window_light_head, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(window_light, matrices, vertices, light, position.toFloat())
            } else {
                renderMirror(window_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> {
                if (useHead1) {
                    renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
                    renderOnce(window, matrices, vertices, light, position.toFloat())
                } else if (useHead2) {
                    renderOnce(head, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window, matrices, vertices, light, position.toFloat())
                } else {
                    renderMirror(window, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    renderOnceFlipped(
                        if (isEnd1 || isEnd2) seat else seat_wheelchair,
                        matrices,
                        vertices,
                        light,
                        (position + (if (useHead1) 9 else 0)).toFloat()
                    )
                    renderOnce(
                        if (isEnd1 || isEnd2) seat else seat_wheelchair,
                        matrices,
                        vertices,
                        light,
                        (position - (if (useHead2) 9 else 0)).toFloat()
                    )
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isEnd1 || isEnd2) {
                if (!useHead1) {
                    renderMirror(side_panel, matrices, vertices, light, position - 31.5f)
                }
                if (!useHead2) {
                    renderMirror(side_panel, matrices, vertices, light, position + 31.5f)
                }
            }

            RenderStage.EXTERIOR -> {
                if (useHead1) {
                    renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                    renderOnce(window_exterior, matrices, vertices, light, position.toFloat())
                } else if (useHead2) {
                    renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window_exterior, matrices, vertices, light, position.toFloat())
                } else {
                    renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails && position == 0) {
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
        if (isEnd1Head && isIndex(0, position, getDoorPositions()) || isEnd2Head && isIndex(
                -1,
                position,
                getDoorPositions()
            )
        ) {
            return
        }

        val useEnd1 = isIndex(0, position, getDoorPositions())
        val useEnd2 = isIndex(-1, position, getDoorPositions())
        val isDoorLight1 = isIndex(1, position, getDoorPositions())
        val isDoorLight2 = isIndex(-2, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> if (doorOpen && renderDetails) {
                if (isDoorLight1) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 48).toFloat())
                } else if (isDoorLight2) {
                    renderMirror(door_light_on, matrices, vertices, light, (position + 48).toFloat())
                }
            }

            RenderStage.INTERIOR -> {
                if (!useEnd1) {
                    door_right_part.setOffset(0f, 0, -doorRightZ)
                    renderOnce(door_right, matrices, vertices, light, position.toFloat())
                    door_left_part.setOffset(0f, 0, doorLeftZ)
                    renderOnceFlipped(door_left, matrices, vertices, light, position.toFloat())
                }
                if (!useEnd2) {
                    door_left_part.setOffset(0f, 0, doorRightZ)
                    renderOnce(door_left, matrices, vertices, light, position.toFloat())
                    door_right_part.setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(door_right, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                if (!useEnd1) {
                    door_right_exterior_part.setOffset(0f, 0, -doorRightZ)
                    renderOnce(door_right_exterior, matrices, vertices, light, position.toFloat())
                    door_left_exterior_part.setOffset(0f, 0, doorLeftZ)
                    renderOnceFlipped(door_left_exterior, matrices, vertices, light, position.toFloat())
                }
                if (!useEnd2) {
                    door_left_exterior_part.setOffset(0f, 0, doorRightZ)
                    renderOnce(door_left_exterior, matrices, vertices, light, position.toFloat())
                    door_right_exterior_part.setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(door_right_exterior, matrices, vertices, light, position.toFloat())
                }
                if (!doorOpen && renderDetails) {
                    if (isDoorLight1) {
                        renderMirror(door_light_off, matrices, vertices, light, (position - 48).toFloat())
                    } else if (isDoorLight2) {
                        renderMirror(door_light_off, matrices, vertices, light, (position + 48).toFloat())
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
        if (renderStage == RenderStage.ALWAYS_ON_LIGHTS) {
            renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
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
        if (renderStage == RenderStage.ALWAYS_ON_LIGHTS) {
            renderOnce(if (useHeadlights) headlights else tail_lights, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(window_light_end, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
            RenderStage.EXTERIOR -> renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnce(window_light_end, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> renderOnce(end, matrices, vertices, light, position.toFloat())
            RenderStage.EXTERIOR -> renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
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
        return intArrayOf(-96, 0, 96)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-144, -48, 48, 144)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-96, 96)
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
            scrollingTexts.add(ScrollingText(0.46f, 0.09f, 6, false))
        }

        val destinationString: String? =
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false)
        renderFrontDestination(
            matrices, font, immediate,
            0f, -2.08f, getEndPositions()!![0] / 16f - 3.56f, 0f, 0f, -0.01f,
            0f, 0f, 0.66f, 0.08f,
            -0x6700, -0x6700, 1f, ModelTrainBase.getAlternatingString(destinationString), false, car, totalCars
        )

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
                if (is1995) -0x6700 else -0x10000,
                Integer.MAX_VALUE,
                0f,
                false
            )
        )
        scrollingTexts.get(0)!!.setVertexConsumer(vertexConsumers)

        val positions = intArrayOf(-6, 0, 6)
        for (position in positions) {
            for (i in 0..1) {
                matrices!!.pushPose()
                UtilitiesClient.rotateYDegrees(matrices, (if (i == 1) -90 else 90).toFloat())
                UtilitiesClient.rotateXDegrees(matrices, 48f)
                matrices.translate(position - 0.23f, -0.55f, 1.96f)
                scrollingTexts.get(0)!!.scrollText(matrices)
                matrices.popPose()
            }
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    companion object {
        private const val DOOR_MAX = 12
    }
}
