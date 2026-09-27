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

open class ModelBR423 private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelBR423?>(doorAnimationType, renderDoorOverlay) {
    private val window_1_interior: ModelMapper
    private val curve_top_r1: ModelMapper
    private val curve_middle_r1: ModelMapper
    private val curve_bottom_r1: ModelMapper
    private val window_2_interior: ModelMapper
    private val curve_top_r2: ModelMapper
    private val curve_middle_r2: ModelMapper
    private val curve_bottom_r2: ModelMapper
    private val window_3_interior: ModelMapper
    private val curve_top_r3: ModelMapper
    private val curve_middle_r3: ModelMapper
    private val curve_bottom_r3: ModelMapper
    private val window_1_exterior: ModelMapper
    private val window_2_exterior: ModelMapper
    private val window_3_exterior: ModelMapper
    private val door_interior: ModelMapper
    private val curve_top_r4: ModelMapper
    private val curve_middle_r4: ModelMapper
    private val right_curve_2_r1: ModelMapper
    private val right_curve_1_r1: ModelMapper
    private val door_left_interior: ModelMapper
    private val door_right_interior: ModelMapper
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_right_exterior: ModelMapper
    private val end_interior: ModelMapper
    private val end_exterior: ModelMapper
    private val end_exterior_left: ModelMapper
    private val roof_2_r1: ModelMapper
    private val roof_1_r1: ModelMapper
    private val end_exterior_right: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_2_r2: ModelMapper
    private val roof_window_interior: ModelMapper
    private val roof_2_r3: ModelMapper
    private val roof_door_interior: ModelMapper
    private val roof_2_r4: ModelMapper
    private val roof_window_exterior_1: ModelMapper
    private val roof_2_r5: ModelMapper
    private val roof_1_r2: ModelMapper
    private val roof_window_exterior_2: ModelMapper
    private val roof_2_r6: ModelMapper
    private val roof_1_r3: ModelMapper
    private val roof_door_exterior: ModelMapper
    private val roof_2_r7: ModelMapper
    private val roof_1_r4: ModelMapper
    private val vent: ModelMapper
    private val light_window: ModelMapper
    private val light_door: ModelMapper
    private val light_head: ModelMapper
    private val roof_right_r1: ModelMapper
    private val roof_left_r1: ModelMapper
    private val head_exterior: ModelMapper
    private val front_5_r1: ModelMapper
    private val front_4_r1: ModelMapper
    private val front_3_r1: ModelMapper
    private val front_1_r1: ModelMapper
    private val head_exterior_left: ModelMapper
    private val side_roof_2_r1: ModelMapper
    private val side_roof_1_r1: ModelMapper
    private val side_4_r1: ModelMapper
    private val side_3_r1: ModelMapper
    private val side_2_r1: ModelMapper
    private val side_1_r1: ModelMapper
    private val roof_2_r8: ModelMapper
    private val roof_1_r5: ModelMapper
    private val head_exterior_right: ModelMapper
    private val side_roof_2_r2: ModelMapper
    private val side_roof_1_r2: ModelMapper
    private val side_4_r2: ModelMapper
    private val side_3_r2: ModelMapper
    private val side_2_r2: ModelMapper
    private val side_1_r2: ModelMapper
    private val roof_2_r9: ModelMapper
    private val roof_1_r6: ModelMapper
    private val head_interior: ModelMapper
    private val head_interior_left: ModelMapper
    private val roof_2_r10: ModelMapper
    private val head_interior_right: ModelMapper
    private val roof_2_r11: ModelMapper
    private val seat: ModelMapper
    private val seat_2_r1: ModelMapper
    private val headlights: ModelMapper
    private val side_2_r3: ModelMapper
    private val side_1_r3: ModelMapper
    private val front_5_r2: ModelMapper
    private val tail_lights: ModelMapper
    private val side_2_r4: ModelMapper
    private val side_1_r4: ModelMapper

    constructor() : this(DoorAnimationType.PLUG_FAST, true)

    init {
        val textureWidth = 288
        val textureHeight = 288

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window_1_interior = ModelMapper(modelDataWrapper)
        window_1_interior.setPos(0f, 24f, 0f)
        window_1_interior.texOffs(105, 36).addBox(0f, 0f, -13.5f, 20, 1, 27, 0f, false)
        window_1_interior.texOffs(138, 150).addBox(20f, -33f, -13.5f, 0, 33, 27, 0f, false)

        curve_top_r1 = ModelMapper(modelDataWrapper)
        curve_top_r1.setPos(13f, -37f, 0f)
        window_1_interior.addChild(curve_top_r1)
        setRotationAngle(curve_top_r1, 0f, 0f, 0.0873f)
        curve_top_r1.texOffs(0, 27).addBox(-3f, 0f, -13.5f, 6, 0, 27, 0f, false)

        curve_middle_r1 = ModelMapper(modelDataWrapper)
        curve_middle_r1.setPos(19f, -35f, 0f)
        window_1_interior.addChild(curve_middle_r1)
        setRotationAngle(curve_middle_r1, 0f, 0f, -1.0472f)
        curve_middle_r1.texOffs(158, 73).addBox(0f, -4f, -13.5f, 0, 4, 27, 0f, false)

        curve_bottom_r1 = ModelMapper(modelDataWrapper)
        curve_bottom_r1.setPos(20f, -33f, 0f)
        window_1_interior.addChild(curve_bottom_r1)
        setRotationAngle(curve_bottom_r1, 0f, 0f, -0.5236f)
        curve_bottom_r1.texOffs(168, 81).addBox(0f, -3f, -13.5f, 0, 3, 27, 0f, false)

        window_2_interior = ModelMapper(modelDataWrapper)
        window_2_interior.setPos(0f, 24f, 0f)
        window_2_interior.texOffs(91, 77).addBox(0f, 0f, -13.5f, 20, 1, 27, 0f, false)
        window_2_interior.texOffs(138, 117).addBox(20f, -33f, -13.5f, 0, 33, 27, 0f, false)

        curve_top_r2 = ModelMapper(modelDataWrapper)
        curve_top_r2.setPos(13f, -37f, 0f)
        window_2_interior.addChild(curve_top_r2)
        setRotationAngle(curve_top_r2, 0f, 0f, 0.0873f)
        curve_top_r2.texOffs(0, 0).addBox(-3f, 0f, -13.5f, 6, 0, 27, 0f, false)

        curve_middle_r2 = ModelMapper(modelDataWrapper)
        curve_middle_r2.setPos(19f, -35f, 0f)
        window_2_interior.addChild(curve_middle_r2)
        setRotationAngle(curve_middle_r2, 0f, 0f, -1.0472f)
        curve_middle_r2.texOffs(158, 69).addBox(0f, -4f, -13.5f, 0, 4, 27, 0f, false)

        curve_bottom_r2 = ModelMapper(modelDataWrapper)
        curve_bottom_r2.setPos(20f, -33f, 0f)
        window_2_interior.addChild(curve_bottom_r2)
        setRotationAngle(curve_bottom_r2, 0f, 0f, -0.5236f)
        curve_bottom_r2.texOffs(168, 78).addBox(0f, -3f, -13.5f, 0, 3, 27, 0f, false)

        window_3_interior = ModelMapper(modelDataWrapper)
        window_3_interior.setPos(0f, 24f, 0f)
        window_3_interior.texOffs(194, 260).addBox(0f, 0f, -13.5f, 20, 1, 27, 0f, false)
        window_3_interior.texOffs(166, 213).addBox(20f, -33f, -13.5f, 0, 33, 27, 0f, false)

        curve_top_r3 = ModelMapper(modelDataWrapper)
        curve_top_r3.setPos(13f, -37f, 0f)
        window_3_interior.addChild(curve_top_r3)
        setRotationAngle(curve_top_r3, 0f, 0f, 0.0873f)
        curve_top_r3.texOffs(243, 0).addBox(-3f, 0f, -13.5f, 6, 0, 27, 0f, false)

        curve_middle_r3 = ModelMapper(modelDataWrapper)
        curve_middle_r3.setPos(19f, -35f, 0f)
        window_3_interior.addChild(curve_middle_r3)
        setRotationAngle(curve_middle_r3, 0f, 0f, -1.0472f)
        curve_middle_r3.texOffs(208, 104).addBox(0f, -4f, -13.5f, 0, 4, 27, 0f, false)

        curve_bottom_r3 = ModelMapper(modelDataWrapper)
        curve_bottom_r3.setPos(20f, -33f, 0f)
        window_3_interior.addChild(curve_bottom_r3)
        setRotationAngle(curve_bottom_r3, 0f, 0f, -0.5236f)
        curve_bottom_r3.texOffs(208, 101).addBox(0f, -3f, -13.5f, 0, 3, 27, 0f, false)

        window_1_exterior = ModelMapper(modelDataWrapper)
        window_1_exterior.setPos(0f, 24f, 0f)
        window_1_exterior.texOffs(192, 173).addBox(20f, 0f, -13.5f, 1, 4, 27, 0f, false)
        window_1_exterior.texOffs(0, 120).addBox(21f, -36f, -13.5f, 0, 36, 27, 0f, false)

        window_2_exterior = ModelMapper(modelDataWrapper)
        window_2_exterior.setPos(0f, 24f, 0f)
        window_2_exterior.texOffs(192, 37).addBox(20f, 0f, -13.5f, 1, 4, 27, 0f, false)
        window_2_exterior.texOffs(84, 116).addBox(21f, -36f, -13.5f, 0, 36, 27, 0f, false)

        window_3_exterior = ModelMapper(modelDataWrapper)
        window_3_exterior.setPos(0f, 24f, 0f)
        window_3_exterior.texOffs(224, 142).addBox(20f, 0f, -13.5f, 1, 4, 27, 0f, false)
        window_3_exterior.texOffs(192, 177).addBox(21f, -36f, -13.5f, 0, 36, 27, 0f, false)

        door_interior = ModelMapper(modelDataWrapper)
        door_interior.setPos(0f, 24f, 0f)
        door_interior.texOffs(142, 117).addBox(0f, 0f, -13f, 20, 1, 26, 0f, false)
        door_interior.texOffs(273, 27).addBox(16f, -32f, -13f, 4, 32, 2, 0f, false)
        door_interior.texOffs(206, 17).addBox(16f, -32f, 11f, 4, 32, 2, 0f, false)
        door_interior.texOffs(40, 231).addBox(6f, -38f, -12.9f, 10, 38, 0, 0f, false)
        door_interior.texOffs(40, 231).addBox(6f, -38f, 12.9f, 10, 38, 0, 0f, false)
        door_interior.texOffs(220, 229).addBox(16f, -37f, -13f, 4, 5, 26, 0f, false)

        curve_top_r4 = ModelMapper(modelDataWrapper)
        curve_top_r4.setPos(12f, -34f, 0f)
        door_interior.addChild(curve_top_r4)
        setRotationAngle(curve_top_r4, 0f, 0f, -0.5236f)
        curve_top_r4.texOffs(8, 201).addBox(0f, -4f, -13f, 1, 4, 26, 0f, false)

        curve_middle_r4 = ModelMapper(modelDataWrapper)
        curve_middle_r4.setPos(17f, -32f, 0f)
        door_interior.addChild(curve_middle_r4)
        setRotationAngle(curve_middle_r4, 0f, 0f, 0.3927f)
        curve_middle_r4.texOffs(82, 36).addBox(-6f, 0f, -13f, 6, 0, 26, 0f, false)

        right_curve_2_r1 = ModelMapper(modelDataWrapper)
        right_curve_2_r1.setPos(12f, -34f, 0f)
        door_interior.addChild(right_curve_2_r1)
        setRotationAngle(right_curve_2_r1, 0f, 0f, -0.7854f)
        right_curve_2_r1.texOffs(0, 0).addBox(0f, 0f, 11f, 2, 4, 2, 0f, false)
        right_curve_2_r1.texOffs(0, 6).addBox(0f, 0f, -13f, 2, 4, 2, 0f, false)

        right_curve_1_r1 = ModelMapper(modelDataWrapper)
        right_curve_1_r1.setPos(16f, -29f, 0f)
        door_interior.addChild(right_curve_1_r1)
        setRotationAngle(right_curve_1_r1, 0f, 0f, -0.5236f)
        right_curve_1_r1.texOffs(105, 90).addBox(0f, -3f, 11f, 3, 3, 2, 0f, false)
        right_curve_1_r1.texOffs(212, 96).addBox(0f, -3f, -13f, 3, 3, 2, 0f, false)

        door_left_interior = ModelMapper(modelDataWrapper)
        door_left_interior.setPos(0f, 24f, 0f)
        door_left_interior.texOffs(248, 182).addBox(20f, -35f, -12f, 1, 35, 12, 0f, false)

        door_right_interior = ModelMapper(modelDataWrapper)
        door_right_interior.setPos(0f, 24f, 0f)
        door_right_interior.texOffs(0, 0).addBox(20f, -35f, 0f, 1, 35, 12, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(208, 90).addBox(20f, 0f, -13f, 1, 4, 26, 0f, false)
        door_exterior.texOffs(262, 0).addBox(20f, -35f, -13f, 1, 35, 1, 0f, false)
        door_exterior.texOffs(266, 0).addBox(20f, -35f, 12f, 1, 35, 1, 0f, false)
        door_exterior.texOffs(60, 228).addBox(20f, -36f, -13f, 1, 1, 26, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 24f, 0f)
        door_left_exterior.texOffs(62, 207).addBox(21f, -35f, -12f, 0, 35, 12, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 24f, 0f)
        door_right_exterior.texOffs(88, 203).addBox(21f, -35f, 0f, 0, 35, 12, 0f, false)

        end_interior = ModelMapper(modelDataWrapper)
        end_interior.setPos(0f, 24f, 0f)
        end_interior.texOffs(88, 33).addBox(-20f, 0f, 0f, 40, 1, 2, 0f, false)
        end_interior.texOffs(20, 231).addBox(12f, -35f, 0f, 8, 35, 2, 0f, false)
        end_interior.texOffs(0, 231).addBox(-20f, -35f, 0f, 8, 35, 2, 0f, true)
        end_interior.texOffs(165, 144).addBox(12f, -31f, -27f, 8, 0, 27, 0f, false)
        end_interior.texOffs(181, 144).addBox(-20f, -31f, -27f, 8, 0, 27, 0f, true)
        end_interior.texOffs(156, 0).addBox(-20f, -39f, 0f, 40, 4, 2, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(156, 7).addBox(-21f, -43f, 2f, 42, 8, 0, 0f, false)
        end_exterior.texOffs(154, 15).addBox(-17f, -43f, 0f, 34, 0, 2, 0f, false)

        end_exterior_left = ModelMapper(modelDataWrapper)
        end_exterior_left.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_left)
        end_exterior_left.texOffs(164, 17).addBox(20f, 0f, 0f, 1, 4, 2, 0f, false)
        end_exterior_left.texOffs(266, 104).addBox(21f, -36f, 0f, 0, 36, 2, 0f, false)
        end_exterior_left.texOffs(268, 61).addBox(12f, -35f, 2f, 9, 35, 0, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(17f, -43f, 0f)
        end_exterior_left.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, -0.6981f)
        roof_2_r1.texOffs(78, 147).addBox(-1f, 0f, 0f, 1, 4, 2, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(21f, -36f, 0f)
        end_exterior_left.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, -0.3491f)
        roof_1_r1.texOffs(18, 5).addBox(-1f, -5f, 0f, 1, 5, 2, 0f, false)

        end_exterior_right = ModelMapper(modelDataWrapper)
        end_exterior_right.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_right)
        end_exterior_right.texOffs(164, 23).addBox(-21f, 0f, 0f, 1, 4, 2, 0f, true)
        end_exterior_right.texOffs(262, 104).addBox(-21f, -36f, 0f, 0, 36, 2, 0f, true)
        end_exterior_right.texOffs(0, 63).addBox(-21f, -35f, 2f, 9, 35, 0, 0f, true)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(-17f, -43f, 0f)
        end_exterior_right.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 0.6981f)
        roof_3_r1.texOffs(78, 153).addBox(0f, 0f, 0f, 1, 4, 2, 0f, true)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(-21f, -36f, 0f)
        end_exterior_right.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, 0.3491f)
        roof_2_r2.texOffs(14, 0).addBox(0f, -5f, 0f, 1, 5, 2, 0f, true)

        roof_window_interior = ModelMapper(modelDataWrapper)
        roof_window_interior.setPos(0f, 24f, 0f)
        roof_window_interior.texOffs(12, 0).addBox(0f, -38f, -13.5f, 6, 0, 27, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(6f, -38f, 0f)
        roof_window_interior.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, 0.1745f)
        roof_2_r3.texOffs(12, 27).addBox(0f, 0f, -13.5f, 5, 0, 27, 0f, false)

        roof_door_interior = ModelMapper(modelDataWrapper)
        roof_door_interior.setPos(0f, 24f, 0f)
        roof_door_interior.texOffs(79, 64).addBox(0f, -38f, -13f, 6, 0, 26, 0f, false)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(6f, -38f, 0f)
        roof_door_interior.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, 0.1745f)
        roof_2_r4.texOffs(94, 36).addBox(0f, 0f, -13f, 5, 0, 26, 0f, false)

        roof_window_exterior_1 = ModelMapper(modelDataWrapper)
        roof_window_exterior_1.setPos(0f, 24f, 0f)
        roof_window_exterior_1.texOffs(27, 0).addBox(0f, -43f, -13.5f, 17, 0, 27, 0f, false)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(17f, -43f, 0f)
        roof_window_exterior_1.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0f, -0.6981f)
        roof_2_r5.texOffs(0, 32).addBox(0f, 0f, -13.5f, 0, 4, 27, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(21f, -36f, 0f)
        roof_window_exterior_1.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, -0.3491f)
        roof_1_r2.texOffs(0, 27).addBox(0f, -5f, -13.5f, 0, 5, 27, 0f, false)

        roof_window_exterior_2 = ModelMapper(modelDataWrapper)
        roof_window_exterior_2.setPos(0f, 24f, 0f)
        roof_window_exterior_2.texOffs(27, 27).addBox(0f, -43f, -13.5f, 17, 0, 27, 0f, false)

        roof_2_r6 = ModelMapper(modelDataWrapper)
        roof_2_r6.setPos(17f, -43f, 0f)
        roof_window_exterior_2.addChild(roof_2_r6)
        setRotationAngle(roof_2_r6, 0f, 0f, -0.6981f)
        roof_2_r6.texOffs(54, 32).addBox(0f, 0f, -13.5f, 0, 4, 27, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(21f, -36f, 0f)
        roof_window_exterior_2.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, -0.3491f)
        roof_1_r3.texOffs(54, 27).addBox(0f, -5f, -13.5f, 0, 5, 27, 0f, false)

        roof_door_exterior = ModelMapper(modelDataWrapper)
        roof_door_exterior.setPos(0f, 24f, 0f)
        roof_door_exterior.texOffs(132, 64).addBox(0f, -43f, -13f, 17, 0, 26, 0f, false)

        roof_2_r7 = ModelMapper(modelDataWrapper)
        roof_2_r7.setPos(17f, -43f, 0f)
        roof_door_exterior.addChild(roof_2_r7)
        setRotationAngle(roof_2_r7, 0f, 0f, -0.6981f)
        roof_2_r7.texOffs(36, 189).addBox(0f, 0f, -13f, 0, 4, 26, 0f, false)

        roof_1_r4 = ModelMapper(modelDataWrapper)
        roof_1_r4.setPos(21f, -36f, 0f)
        roof_door_exterior.addChild(roof_1_r4)
        setRotationAngle(roof_1_r4, 0f, 0f, -0.3491f)
        roof_1_r4.texOffs(168, 85).addBox(0f, -5f, -13f, 0, 5, 26, 0f, false)

        vent = ModelMapper(modelDataWrapper)
        vent.setPos(0f, 24f, 0f)
        vent.texOffs(74, 237).addBox(-13f, -49f, -40f, 26, 6, 40, 0f, false)

        light_window = ModelMapper(modelDataWrapper)
        light_window.setPos(0f, 24f, 0f)
        light_window.texOffs(217, 0).addBox(2f, -38.2f, -13.5f, 4, 0, 27, 0f, false)

        light_door = ModelMapper(modelDataWrapper)
        light_door.setPos(0f, 24f, 0f)
        light_door.texOffs(218, 0).addBox(2f, -38.2f, -13f, 4, 0, 26, 0f, false)

        light_head = ModelMapper(modelDataWrapper)
        light_head.setPos(0f, 24f, 0f)
        light_head.texOffs(127, 210).addBox(-6f, -38.2f, -13.5f, 12, 0, 27, 0f, false)

        roof_right_r1 = ModelMapper(modelDataWrapper)
        roof_right_r1.setPos(-6f, -38.2f, 5f)
        light_head.addChild(roof_right_r1)
        setRotationAngle(roof_right_r1, 0f, 0f, -0.1745f)
        roof_right_r1.texOffs(151, 210).addBox(-5f, 0f, -18.5f, 5, 0, 27, 0f, true)

        roof_left_r1 = ModelMapper(modelDataWrapper)
        roof_left_r1.setPos(6f, -38.2f, 5f)
        light_head.addChild(roof_left_r1)
        setRotationAngle(roof_left_r1, 0f, 0f, 0.1745f)
        roof_left_r1.texOffs(117, 210).addBox(0f, 0f, -18.5f, 5, 0, 27, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(-22, 63).addBox(-21f, 0f, -40f, 42, 0, 40, 0f, false)
        head_exterior.texOffs(221, 173).addBox(-12f, -11f, -40f, 24, 9, 0, 0f, false)
        head_exterior.texOffs(68, 0).addBox(-17f, -43f, -20f, 34, 0, 20, 0f, false)
        head_exterior.texOffs(0, 104).addBox(-21f, -43f, -1f, 42, 43, 0, 0f, false)

        front_5_r1 = ModelMapper(modelDataWrapper)
        front_5_r1.setPos(0f, -43f, -20f)
        head_exterior.addChild(front_5_r1)
        setRotationAngle(front_5_r1, 0.5236f, 0f, 0f)
        front_5_r1.texOffs(80, 20).addBox(-18f, 0f, -8f, 36, 0, 8, 0f, false)

        front_4_r1 = ModelMapper(modelDataWrapper)
        front_4_r1.setPos(0f, -43f, -20f)
        head_exterior.addChild(front_4_r1)
        setRotationAngle(front_4_r1, 0.9599f, 0f, 0f)
        front_4_r1.texOffs(-19, 269).addBox(-17f, -3f, -25f, 34, 0, 19, 0f, false)

        front_3_r1 = ModelMapper(modelDataWrapper)
        front_3_r1.setPos(0f, -11f, -40f)
        head_exterior.addChild(front_3_r1)
        setRotationAngle(front_3_r1, -0.2618f, 0f, 0f)
        front_3_r1.texOffs(192, 68).addBox(-14f, -16f, 0f, 28, 16, 0, 0f, false)

        front_1_r1 = ModelMapper(modelDataWrapper)
        front_1_r1.setPos(0f, -2f, -40f)
        head_exterior.addChild(front_1_r1)
        setRotationAngle(front_1_r1, 0.0873f, 0f, 0f)
        front_1_r1.texOffs(221, 55).addBox(-13f, 0f, 0f, 26, 6, 0, 0f, false)

        head_exterior_left = ModelMapper(modelDataWrapper)
        head_exterior_left.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_left)
        head_exterior_left.texOffs(221, 31).addBox(20f, 0f, -20f, 1, 4, 20, 0f, false)
        head_exterior_left.texOffs(94, 159).addBox(21f, -36f, -20f, 0, 36, 20, 0f, false)

        side_roof_2_r1 = ModelMapper(modelDataWrapper)
        side_roof_2_r1.setPos(17f, -43f, -20f)
        head_exterior_left.addChild(side_roof_2_r1)
        setRotationAngle(side_roof_2_r1, 0f, 0.2618f, -0.6981f)
        side_roof_2_r1.texOffs(0, 178).addBox(0f, 0f, -5f, 0, 4, 5, 0f, false)

        side_roof_1_r1 = ModelMapper(modelDataWrapper)
        side_roof_1_r1.setPos(21f, -36f, -20f)
        head_exterior_left.addChild(side_roof_1_r1)
        setRotationAngle(side_roof_1_r1, 0f, 0.2618f, -0.3491f)
        side_roof_1_r1.texOffs(0, 41).addBox(0f, -5f, -6f, 0, 7, 6, 0f, false)

        side_4_r1 = ModelMapper(modelDataWrapper)
        side_4_r1.setPos(17f, -43f, -20f)
        head_exterior_left.addChild(side_4_r1)
        setRotationAngle(side_4_r1, 1.1781f, -0.6981f, 0f)
        side_4_r1.texOffs(204, 182).addBox(-13f, -2.5f, -20f, 12, 0, 17, 0f, false)

        side_3_r1 = ModelMapper(modelDataWrapper)
        side_3_r1.setPos(12f, -11f, -40f)
        head_exterior_left.addChild(side_3_r1)
        setRotationAngle(side_3_r1, -0.1309f, -0.7854f, 0f)
        side_3_r1.texOffs(224, 144).addBox(0f, -18f, 0f, 13, 18, 0, 0f, false)

        side_2_r1 = ModelMapper(modelDataWrapper)
        side_2_r1.setPos(12f, 0f, -40f)
        head_exterior_left.addChild(side_2_r1)
        setRotationAngle(side_2_r1, 0f, -0.7854f, 0f)
        side_2_r1.texOffs(224, 15).addBox(0f, -11f, 0f, 10, 15, 0, 0f, false)

        side_1_r1 = ModelMapper(modelDataWrapper)
        side_1_r1.setPos(21f, 0f, -20f)
        head_exterior_left.addChild(side_1_r1)
        setRotationAngle(side_1_r1, 0f, 0.1745f, 0f)
        side_1_r1.texOffs(172, 7).addBox(0f, -36f, -14f, 0, 40, 14, 0f, false)

        roof_2_r8 = ModelMapper(modelDataWrapper)
        roof_2_r8.setPos(17f, -43f, 0f)
        head_exterior_left.addChild(roof_2_r8)
        setRotationAngle(roof_2_r8, 0f, 0f, -0.6981f)
        roof_2_r8.texOffs(208, 104).addBox(0f, 0f, -20f, 0, 4, 20, 0f, false)

        roof_1_r5 = ModelMapper(modelDataWrapper)
        roof_1_r5.setPos(21f, -36f, 0f)
        head_exterior_left.addChild(roof_1_r5)
        setRotationAngle(roof_1_r5, 0f, 0f, -0.3491f)
        roof_1_r5.texOffs(117, 44).addBox(0f, -5f, -20f, 0, 5, 20, 0f, false)

        head_exterior_right = ModelMapper(modelDataWrapper)
        head_exterior_right.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_right)
        head_exterior_right.texOffs(242, 82).addBox(-21f, 0f, -20f, 1, 4, 20, 0f, true)
        head_exterior_right.texOffs(54, 159).addBox(-21f, -36f, -20f, 0, 36, 20, 0f, true)

        side_roof_2_r2 = ModelMapper(modelDataWrapper)
        side_roof_2_r2.setPos(-17f, -43f, -20f)
        head_exterior_right.addChild(side_roof_2_r2)
        setRotationAngle(side_roof_2_r2, 0f, -0.2618f, 0.6981f)
        side_roof_2_r2.texOffs(10, 178).addBox(0f, 0f, -5f, 0, 4, 5, 0f, true)

        side_roof_1_r2 = ModelMapper(modelDataWrapper)
        side_roof_1_r2.setPos(-21f, -36f, -20f)
        head_exterior_right.addChild(side_roof_1_r2)
        setRotationAngle(side_roof_1_r2, 0f, -0.2618f, 0.3491f)
        side_roof_1_r2.texOffs(12, 41).addBox(0f, -5f, -6f, 0, 7, 6, 0f, true)

        side_4_r2 = ModelMapper(modelDataWrapper)
        side_4_r2.setPos(-17f, -43f, -20f)
        head_exterior_right.addChild(side_4_r2)
        setRotationAngle(side_4_r2, 1.1781f, 0.6981f, 0f)
        side_4_r2.texOffs(37, 147).addBox(1f, -2.5f, -20f, 12, 0, 17, 0f, true)

        side_3_r2 = ModelMapper(modelDataWrapper)
        side_3_r2.setPos(-12f, -11f, -40f)
        head_exterior_right.addChild(side_3_r2)
        setRotationAngle(side_3_r2, -0.1309f, 0.7854f, 0f)
        side_3_r2.texOffs(236, 84).addBox(-13f, -18f, 0f, 13, 18, 0, 0f, true)

        side_2_r2 = ModelMapper(modelDataWrapper)
        side_2_r2.setPos(-12f, 0f, -40f)
        head_exterior_right.addChild(side_2_r2)
        setRotationAngle(side_2_r2, 0f, 0.7854f, 0f)
        side_2_r2.texOffs(248, 61).addBox(-10f, -11f, 0f, 10, 15, 0, 0f, true)

        side_1_r2 = ModelMapper(modelDataWrapper)
        side_1_r2.setPos(-21f, 0f, -20f)
        head_exterior_right.addChild(side_1_r2)
        setRotationAngle(side_1_r2, 0f, -0.1745f, 0f)
        side_1_r2.texOffs(0, 173).addBox(0f, -36f, -14f, 0, 40, 14, 0f, true)

        roof_2_r9 = ModelMapper(modelDataWrapper)
        roof_2_r9.setPos(-17f, -43f, 0f)
        head_exterior_right.addChild(roof_2_r9)
        setRotationAngle(roof_2_r9, 0f, 0f, 0.6981f)
        roof_2_r9.texOffs(208, 100).addBox(0f, 0f, -20f, 0, 4, 20, 0f, true)

        roof_1_r6 = ModelMapper(modelDataWrapper)
        roof_1_r6.setPos(-21f, -36f, 0f)
        head_exterior_right.addChild(roof_1_r6)
        setRotationAngle(roof_1_r6, 0f, 0f, 0.3491f)
        roof_1_r6.texOffs(117, 49).addBox(0f, -5f, -20f, 0, 5, 20, 0f, true)

        head_interior = ModelMapper(modelDataWrapper)
        head_interior.setPos(0f, 24f, 0f)
        head_interior.texOffs(84, 105).addBox(-21f, -38f, -13.5f, 42, 38, 0, 0f, false)
        head_interior.texOffs(165, 173).addBox(-6f, -38f, -13.5f, 12, 0, 27, 0f, false)

        head_interior_left = ModelMapper(modelDataWrapper)
        head_interior_left.setPos(0f, 0f, 0f)
        head_interior.addChild(head_interior_left)


        roof_2_r10 = ModelMapper(modelDataWrapper)
        roof_2_r10.setPos(6f, -38f, 5f)
        head_interior_left.addChild(roof_2_r10)
        setRotationAngle(roof_2_r10, 0f, 0f, 0.1745f)
        roof_2_r10.texOffs(9, 183).addBox(0f, 0f, -18.5f, 5, 0, 27, 0f, false)

        head_interior_right = ModelMapper(modelDataWrapper)
        head_interior_right.setPos(0f, 0f, 0f)
        head_interior.addChild(head_interior_right)


        roof_2_r11 = ModelMapper(modelDataWrapper)
        roof_2_r11.setPos(-6f, -38f, 5f)
        head_interior_right.addChild(roof_2_r11)
        setRotationAngle(roof_2_r11, 0f, 0f, -0.1745f)
        roof_2_r11.texOffs(107, 210).addBox(-5f, 0f, -18.5f, 5, 0, 27, 0f, true)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(54, 166).addBox(-4f, -6f, -4f, 8, 1, 7, 0f, false)
        seat.texOffs(199, 61).addBox(-3.5f, -19.644f, 4.0686f, 7, 2, 1, 0f, false)

        seat_2_r1 = ModelMapper(modelDataWrapper)
        seat_2_r1.setPos(0f, -6f, 2f)
        seat.addChild(seat_2_r1)
        setRotationAngle(seat_2_r1, -0.1745f, 0f, 0f)
        seat_2_r1.texOffs(88, 36).addBox(-4f, -12f, 0f, 8, 12, 1, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        side_2_r3 = ModelMapper(modelDataWrapper)
        side_2_r3.setPos(-12f, 0f, -40f)
        headlights.addChild(side_2_r3)
        setRotationAngle(side_2_r3, 0f, 0.7854f, 0f)
        side_2_r3.texOffs(221, 34).addBox(-8.1f, -11f, -0.1f, 7, 4, 0, 0f, true)

        side_1_r3 = ModelMapper(modelDataWrapper)
        side_1_r3.setPos(12f, 0f, -40f)
        headlights.addChild(side_1_r3)
        setRotationAngle(side_1_r3, 0f, -0.7854f, 0f)
        side_1_r3.texOffs(221, 30).addBox(1.1f, -11f, -0.1f, 7, 4, 0, 0f, false)

        front_5_r2 = ModelMapper(modelDataWrapper)
        front_5_r2.setPos(0f, -43f, -20f)
        headlights.addChild(front_5_r2)
        setRotationAngle(front_5_r2, 0.5236f, 0f, 0f)
        front_5_r2.texOffs(216, 43).addBox(-2f, -0.2f, -7f, 4, 0, 5, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        side_2_r4 = ModelMapper(modelDataWrapper)
        side_2_r4.setPos(-12f, 0f, -40f)
        tail_lights.addChild(side_2_r4)
        setRotationAngle(side_2_r4, 0f, 0.7854f, 0f)
        side_2_r4.texOffs(243, 34).addBox(-8.1f, -11f, -0.1f, 7, 4, 0, 0f, true)

        side_1_r4 = ModelMapper(modelDataWrapper)
        side_1_r4.setPos(12f, 0f, -40f)
        tail_lights.addChild(side_1_r4)
        setRotationAngle(side_1_r4, 0f, -0.7854f, 0f)
        side_1_r4.texOffs(243, 30).addBox(1.1f, -11f, -0.1f, 7, 4, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window_1_interior.setModelPart()
        window_2_interior.setModelPart()
        window_3_interior.setModelPart()
        window_1_exterior.setModelPart()
        window_2_exterior.setModelPart()
        window_3_exterior.setModelPart()
        door_interior.setModelPart()
        door_left_interior.setModelPart()
        door_right_interior.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart()
        door_right_exterior.setModelPart()
        end_interior.setModelPart()
        end_exterior.setModelPart()
        roof_window_interior.setModelPart()
        roof_door_interior.setModelPart()
        roof_window_exterior_1.setModelPart()
        roof_window_exterior_2.setModelPart()
        roof_door_exterior.setModelPart()
        vent.setModelPart()
        light_window.setModelPart()
        light_door.setModelPart()
        light_head.setModelPart()
        head_exterior.setModelPart()
        head_interior.setModelPart()
        seat.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelBR423 {
        return ModelBR423(doorAnimationType, renderDoorOverlay)
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
        val windowPositions = floatArrayOf(-106.5f, -53.5f, -26.5f, 26.5f, 53.5f, 106.5f)

        for (i in windowPositions.indices) {
            val windowPosition = windowPositions[i]
            val isEnd1 = isEnd1Head && i == 0
            val isEnd2 = isEnd2Head && i == windowPositions.size - 1

            when (renderStage!!) {
                RenderStage.LIGHTS -> if (isEnd1) {
                    renderOnce(light_head, matrices, vertices, light, windowPosition)
                } else if (isEnd2) {
                    renderOnceFlipped(light_head, matrices, vertices, light, windowPosition)
                } else {
                    renderMirror(light_window, matrices, vertices, light, windowPosition)
                }

                RenderStage.INTERIOR -> {
                    if (i == 2) {
                        renderOnce(window_3_interior, matrices, vertices, light, windowPosition)
                        renderOnceFlipped(window_1_interior, matrices, vertices, light, windowPosition)
                    } else if (i == 3) {
                        renderOnce(window_1_interior, matrices, vertices, light, windowPosition)
                        renderOnceFlipped(window_3_interior, matrices, vertices, light, windowPosition)
                    } else {
                        renderMirror(
                            if (isEnd1 || isEnd2 || i == 1 || i == 4) window_2_interior else window_1_interior,
                            matrices,
                            vertices,
                            light,
                            windowPosition
                        )
                    }
                    if (isEnd1) {
                        renderOnce(head_interior, matrices, vertices, light, windowPosition)
                    } else if (isEnd2) {
                        renderOnceFlipped(head_interior, matrices, vertices, light, windowPosition)
                    } else {
                        renderMirror(roof_window_interior, matrices, vertices, light, windowPosition)
                    }
                    if (renderDetails) {
                        renderOnceFlipped(seat, matrices, vertices, light, -16f, windowPosition - 7)
                        renderOnceFlipped(seat, matrices, vertices, light, 16f, windowPosition - 7)
                        renderOnce(seat, matrices, vertices, light, -16f, windowPosition + 7)
                        renderOnce(seat, matrices, vertices, light, 16f, windowPosition + 7)
                        if (i != 1 && i != 3) {
                            renderOnceFlipped(seat, matrices, vertices, light, -8f, windowPosition - 7)
                            renderOnceFlipped(seat, matrices, vertices, light, 8f, windowPosition - 7)
                        }
                        if (i != 2 && i != 4) {
                            renderOnce(seat, matrices, vertices, light, -8f, windowPosition + 7)
                            renderOnce(seat, matrices, vertices, light, 8f, windowPosition + 7)
                        }
                    }
                }

                RenderStage.EXTERIOR -> {
                    if (i == 2) {
                        renderOnce(window_3_exterior, matrices, vertices, light, windowPosition)
                        renderOnceFlipped(window_1_exterior, matrices, vertices, light, windowPosition)
                    } else if (i == 3) {
                        renderOnce(window_1_exterior, matrices, vertices, light, windowPosition)
                        renderOnceFlipped(window_3_exterior, matrices, vertices, light, windowPosition)
                    } else {
                        renderMirror(
                            if (isEnd1 || isEnd2 || i == 1 || i == 4) window_2_exterior else window_1_exterior,
                            matrices,
                            vertices,
                            light,
                            windowPosition
                        )
                    }
                    renderOnce(
                        if (i % 2 == 0) roof_window_exterior_2 else roof_window_exterior_1,
                        matrices,
                        vertices,
                        light,
                        windowPosition
                    )
                    renderOnceFlipped(
                        if (i % 2 == 0) roof_window_exterior_1 else roof_window_exterior_2,
                        matrices,
                        vertices,
                        light,
                        windowPosition
                    )
                }

                else -> {}
            }
        }

        if (renderStage == RenderStage.EXTERIOR) {
            renderMirror(vent, matrices, vertices, light, position.toFloat())
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
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(light_door, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                renderMirror(door_interior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_door_interior, matrices, vertices, light, position.toFloat())
                renderOnce(door_left_interior, matrices, vertices, light, -doorLeftX, position - doorLeftZ)
                renderOnce(door_right_interior, matrices, vertices, light, -doorLeftX, position + doorLeftZ)
                renderOnceFlipped(door_left_interior, matrices, vertices, light, -doorRightX, position + doorRightZ)
                renderOnceFlipped(door_right_interior, matrices, vertices, light, -doorRightX, position - doorRightZ)
            }

            RenderStage.EXTERIOR -> {
                renderMirror(door_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_door_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(door_left_exterior, matrices, vertices, light, -doorLeftX, position - doorLeftZ)
                renderOnce(door_right_exterior, matrices, vertices, light, -doorLeftX, position + doorLeftZ)
                renderOnceFlipped(door_left_exterior, matrices, vertices, light, -doorRightX, position + doorRightZ)
                renderOnceFlipped(door_right_exterior, matrices, vertices, light, -doorRightX, position - doorRightZ)
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
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

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
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

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
            RenderStage.INTERIOR -> renderOnceFlipped(end_interior, matrices, vertices, light, position.toFloat())
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
            RenderStage.INTERIOR -> renderOnce(end_interior, matrices, vertices, light, position.toFloat())
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
        return intArrayOf(0)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-80, 0, 80)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-120, 120)
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
        val routeNumber: String = if (thisRoute == null) "" else thisRoute.lightRailRouteNumber!!
        val noRoute = routeNumber.isEmpty()
        val destinationString: String? =
            getAlternatingString(getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false))
        val center = 26.5f / 16
        val widthBig = 28f / 16
        val widthSmall = 19f / 16
        val routeWidthSmall = 0.2f
        val routeWidthBig = 0.3f
        val margin = 0.03f

        renderFrontDestination(
            matrices, font, immediate,
            0f, -43f / 16, -8.75f, if (noRoute) 0f else (routeWidthBig + margin) / 2, 0.66f, -0.01f - 3f / 16,
            -35f, 0f, widthBig - (if (noRoute) margin * 2 else margin * 3 + routeWidthBig), 0.16f,
            mtr.data.IGui.ARGB_WHITE, mtr.data.IGui.ARGB_WHITE, 1f, destinationString, false, car, totalCars
        )
        renderFrontDestination(
            matrices, font, immediate,
            0f, -43f / 16, -8.75f, -widthBig / 2 + margin + routeWidthBig / 2, 0.66f, -0.01f - 3f / 16,
            -35f, 0f, routeWidthBig, 0.16f,
            mtr.data.IGui.ARGB_WHITE, mtr.data.IGui.ARGB_WHITE, 1f, routeNumber, false, car, totalCars
        )

        renderFrontDestination(
            matrices, font, immediate,
            -1.31f, -1.68f, center + (if (noRoute) 0f else (-routeWidthBig - margin) / 2), 0f, 0f, -0.01f,
            0f, 90f, widthSmall - (if (noRoute) margin * 2 else margin * 3 + routeWidthSmall), 0.1f,
            mtr.data.IGui.ARGB_WHITE, mtr.data.IGui.ARGB_WHITE, 1f, destinationString, false, 0, 1
        )
        renderFrontDestination(
            matrices, font, immediate,
            -1.31f, -1.68f, center + widthSmall / 2 - margin - routeWidthSmall / 2, 0f, 0f, -0.01f,
            0f, 90f, routeWidthSmall, 0.1f,
            mtr.data.IGui.ARGB_WHITE, mtr.data.IGui.ARGB_WHITE, 1f, routeNumber, false, 0, 1
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Nicht Einsteigen"
    }

    companion object {
        private const val DOOR_MAX = 12
    }
}
