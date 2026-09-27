package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelMPL85 protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelMPL85?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val roof_4_r1: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_2_r1: ModelMapper
    private val wall_top_r1: ModelMapper
    private val wall_bottom_r1: ModelMapper
    private val window_exterior: ModelMapper
    private val roof_3_r2: ModelMapper
    private val roof_2_r2: ModelMapper
    private val roof_1_r1: ModelMapper
    private val wall_top_r2: ModelMapper
    private val wall_bottom_r2: ModelMapper
    private val door: ModelMapper
    private val roof_4_r2: ModelMapper
    private val roof_3_r3: ModelMapper
    private val roof_2_r3: ModelMapper
    private val wall_top_2_r1: ModelMapper
    private val wall_bottom_2_r1: ModelMapper
    private val door_left: ModelMapper
    private val door_top_r1: ModelMapper
    private val door_bottom_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_top_r2: ModelMapper
    private val door_bottom_r2: ModelMapper
    private val door_exterior: ModelMapper
    private val wall_top_3_r1: ModelMapper
    private val wall_bottom_3_r1: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_top_r3: ModelMapper
    private val door_bottom_r3: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_top_r4: ModelMapper
    private val door_bottom_r4: ModelMapper
    private val roof_door_exterior_1: ModelMapper
    private val roof_3_r4: ModelMapper
    private val roof_2_r4: ModelMapper
    private val roof_1_r2: ModelMapper
    private val roof_door_exterior_2: ModelMapper
    private val roof_4_r3: ModelMapper
    private val roof_3_r5: ModelMapper
    private val roof_2_r5: ModelMapper
    private val roof_door_exterior_3: ModelMapper
    private val roof_5_r1: ModelMapper
    private val roof_4_r4: ModelMapper
    private val roof_3_r6: ModelMapper
    private val roof_window_exterior: ModelMapper
    private val roof_6_r1: ModelMapper
    private val roof_5_r2: ModelMapper
    private val roof_4_r5: ModelMapper
    private val seat_1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val seat_2: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val seat_3: ModelMapper
    private val back_upper_diagonal_2_r1: ModelMapper
    private val back_upper_diagonal_1_r1: ModelMapper
    private val seat_diagonal_2_r1: ModelMapper
    private val seat_diagonal_1_r1: ModelMapper
    private val light_window: ModelMapper
    private val light_r1: ModelMapper
    private val light_door: ModelMapper
    private val light_r2: ModelMapper
    private val middle_handrail: ModelMapper
    private val top_handrail_5_r1: ModelMapper
    private val top_handrail_4_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val top_handrail_2_r1: ModelMapper
    private val handrail_16_r1: ModelMapper
    private val handrail_15_r1: ModelMapper
    private val handrail_14_r1: ModelMapper
    private val handrail_13_r1: ModelMapper
    private val handrail_12_r1: ModelMapper
    private val handrail_11_r1: ModelMapper
    private val handrail_10_r1: ModelMapper
    private val handrail_9_r1: ModelMapper
    private val handrail_7_r1: ModelMapper
    private val handrail_6_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r3: ModelMapper
    private val handrail_2_r3: ModelMapper
    private val head: ModelMapper
    private val side_1: ModelMapper
    private val bar_2_r1: ModelMapper
    private val front_11_r1: ModelMapper
    private val front_10_r1: ModelMapper
    private val front_9_r1: ModelMapper
    private val front_8_r1: ModelMapper
    private val front_4_r1: ModelMapper
    private val front_3_r1: ModelMapper
    private val front_2_r1: ModelMapper
    private val roof_8_r1: ModelMapper
    private val roof_7_r1: ModelMapper
    private val roof_4_r6: ModelMapper
    private val roof_3_r7: ModelMapper
    private val roof_2_r6: ModelMapper
    private val wall_top_r3: ModelMapper
    private val wall_bottom_r3: ModelMapper
    private val side_2: ModelMapper
    private val bar_3_r1: ModelMapper
    private val front_12_r1: ModelMapper
    private val front_11_r2: ModelMapper
    private val front_10_r2: ModelMapper
    private val front_9_r2: ModelMapper
    private val front_5_r1: ModelMapper
    private val front_4_r2: ModelMapper
    private val front_3_r2: ModelMapper
    private val roof_9_r1: ModelMapper
    private val roof_8_r2: ModelMapper
    private val roof_5_r3: ModelMapper
    private val roof_4_r7: ModelMapper
    private val roof_3_r8: ModelMapper
    private val wall_top_r4: ModelMapper
    private val wall_bottom_r4: ModelMapper
    private val head_exterior: ModelMapper
    private val side_1_exterior: ModelMapper
    private val roof_8_r3: ModelMapper
    private val roof_7_r2: ModelMapper
    private val roof_6_r2: ModelMapper
    private val roof_5_r4: ModelMapper
    private val roof_3_r9: ModelMapper
    private val roof_2_r7: ModelMapper
    private val roof_1_r3: ModelMapper
    private val front_14_r1: ModelMapper
    private val front_13_r1: ModelMapper
    private val front_12_r2: ModelMapper
    private val front_11_r3: ModelMapper
    private val front_10_r3: ModelMapper
    private val front_9_r3: ModelMapper
    private val front_8_r2: ModelMapper
    private val front_7_r1: ModelMapper
    private val front_6_r1: ModelMapper
    private val front_5_r2: ModelMapper
    private val front_4_r3: ModelMapper
    private val front_3_r3: ModelMapper
    private val wall_top_r5: ModelMapper
    private val wall_bottom_r5: ModelMapper
    private val side_2_exterior: ModelMapper
    private val roof_9_r2: ModelMapper
    private val roof_8_r4: ModelMapper
    private val roof_7_r3: ModelMapper
    private val roof_6_r3: ModelMapper
    private val roof_4_r8: ModelMapper
    private val roof_3_r10: ModelMapper
    private val roof_2_r8: ModelMapper
    private val front_15_r1: ModelMapper
    private val front_14_r2: ModelMapper
    private val front_13_r2: ModelMapper
    private val front_12_r3: ModelMapper
    private val front_11_r4: ModelMapper
    private val front_10_r4: ModelMapper
    private val front_9_r4: ModelMapper
    private val front_8_r3: ModelMapper
    private val front_7_r2: ModelMapper
    private val front_6_r2: ModelMapper
    private val front_5_r3: ModelMapper
    private val front_4_r4: ModelMapper
    private val wall_top_r6: ModelMapper
    private val wall_bottom_r6: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r3: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r4: ModelMapper
    private val headlights: ModelMapper
    private val headlight_4_r1: ModelMapper
    private val headlight_3_r1: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val headlight_1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val headlight_4_r2: ModelMapper
    private val headlight_3_r2: ModelMapper
    private val headlight_2_r2: ModelMapper
    private val headlight_1_r2: ModelMapper

    constructor() : this(DoorAnimationType.PLUG_FAST, true)

    init {
        val textureWidth = 256
        val textureHeight = 256

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(32, 94).addBox(0f, 0f, -14f, 19, 2, 28, 0f, false)
        window.texOffs(130, 132).addBox(20f, -10f, -14f, 1, 5, 28, 0f, false)
        window.texOffs(4, 42).addBox(16.734f, -33.9011f, -14f, 2, 0, 28, 0f, false)
        window.texOffs(126, 0).addBox(0f, -37.3304f, -14f, 7, 0, 28, 0f, false)

        roof_4_r1 = ModelMapper(modelDataWrapper)
        roof_4_r1.setPos(10.0171f, -36.9817f, 0f)
        window.addChild(roof_4_r1)
        setRotationAngle(roof_4_r1, 0f, 0f, 0.0873f)
        roof_4_r1.texOffs(0, 126).addBox(-4f, 0f, -14f, 8, 0, 28, 0f, false)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(14.5019f, -35.7671f, 0f)
        window.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 1.0472f)
        roof_3_r1.texOffs(8, 0).addBox(-1f, 0f, -14f, 2, 0, 28, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(15.8679f, -34.4011f, 0f)
        window.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, 0.5236f)
        roof_2_r1.texOffs(0, 42).addBox(-1f, 0f, -14f, 2, 0, 28, 0f, false)

        wall_top_r1 = ModelMapper(modelDataWrapper)
        wall_top_r1.setPos(21f, -10f, 0f)
        window.addChild(wall_top_r1)
        setRotationAngle(wall_top_r1, 0f, 0f, -0.0873f)
        wall_top_r1.texOffs(124, 12).addBox(-1f, -27f, -14f, 1, 27, 28, 0f, false)

        wall_bottom_r1 = ModelMapper(modelDataWrapper)
        wall_bottom_r1.setPos(21f, -5f, 0f)
        window.addChild(wall_bottom_r1)
        setRotationAngle(wall_bottom_r1, 0f, 0f, 0.1745f)
        wall_bottom_r1.texOffs(44, 126).addBox(-1f, 0f, -14f, 1, 7, 28, 0f, false)

        window_exterior = ModelMapper(modelDataWrapper)
        window_exterior.setPos(0f, 24f, 0f)
        window_exterior.texOffs(0, 133).addBox(21f, -10f, -14f, 0, 5, 28, 0f, false)
        window_exterior.texOffs(30, 126).addBox(-0.0293f, -41.2781f, -14f, 7, 0, 28, 0f, false)

        roof_3_r2 = ModelMapper(modelDataWrapper)
        roof_3_r2.setPos(11.9516f, -40.8423f, 0f)
        window_exterior.addChild(roof_3_r2)
        setRotationAngle(roof_3_r2, 0f, 0f, 0.0873f)
        roof_3_r2.texOffs(108, 125).addBox(-5f, 0f, -14f, 10, 0, 28, 0f, false)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(17.3157f, -40.0862f, 0f)
        window_exterior.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, -0.8727f)
        roof_2_r2.texOffs(74, 124).addBox(0f, -0.5f, -14f, 0, 1, 28, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(18.2163f, -37.8329f, 0f)
        window_exterior.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, -0.2618f)
        roof_1_r1.texOffs(56, 137).addBox(0f, -2f, -14f, 0, 4, 28, 0f, false)

        wall_top_r2 = ModelMapper(modelDataWrapper)
        wall_top_r2.setPos(21f, -10f, 0f)
        window_exterior.addChild(wall_top_r2)
        setRotationAngle(wall_top_r2, 0f, 0f, -0.0873f)
        wall_top_r2.texOffs(80, 97).addBox(0f, -26f, -14f, 0, 26, 28, 0f, false)

        wall_bottom_r2 = ModelMapper(modelDataWrapper)
        wall_bottom_r2.setPos(21f, -5f, 0f)
        window_exterior.addChild(wall_bottom_r2)
        setRotationAngle(wall_bottom_r2, 0f, 0f, 0.1745f)
        wall_bottom_r2.texOffs(102, 125).addBox(0f, 0f, -14f, 0, 7, 28, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(0, 0).addBox(0f, 0f, -20f, 19, 2, 40, 0f, false)
        door.texOffs(162, 165).addBox(20f, -10f, -20f, 1, 5, 4, 0f, false)
        door.texOffs(162, 93).addBox(20f, -10f, 16f, 1, 5, 4, 0f, false)
        door.texOffs(104, 203).addBox(17f, -36f, -20f, 3, 36, 4, 0f, false)
        door.texOffs(0, 192).addBox(17f, -36f, 16f, 3, 36, 4, 0f, false)
        door.texOffs(6, 209).addBox(16.734f, -35.9011f, -20f, 2, 2, 40, 0f, false)
        door.texOffs(56, 42).addBox(0f, -37.3304f, -20f, 7, 0, 40, 0f, false)

        roof_4_r2 = ModelMapper(modelDataWrapper)
        roof_4_r2.setPos(10.0171f, -36.9817f, 0f)
        door.addChild(roof_4_r2)
        setRotationAngle(roof_4_r2, 0f, 0f, 0.0873f)
        roof_4_r2.texOffs(40, 42).addBox(-4f, 0f, -20f, 8, 0, 40, 0f, false)

        roof_3_r3 = ModelMapper(modelDataWrapper)
        roof_3_r3.setPos(14.5019f, -35.7671f, 0f)
        door.addChild(roof_3_r3)
        setRotationAngle(roof_3_r3, 0f, 0f, 1.0472f)
        roof_3_r3.texOffs(8, 82).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(15.8679f, -34.4011f, 0f)
        door.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, 0.5236f)
        roof_2_r3.texOffs(12, 82).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        wall_top_2_r1 = ModelMapper(modelDataWrapper)
        wall_top_2_r1.setPos(21f, -10f, 0f)
        door.addChild(wall_top_2_r1)
        setRotationAngle(wall_top_2_r1, 0f, 0f, -0.0873f)
        wall_top_2_r1.texOffs(14, 204).addBox(-1f, -26f, 16f, 1, 26, 4, 0f, false)
        wall_top_2_r1.texOffs(24, 204).addBox(-1f, -26f, -20f, 1, 26, 4, 0f, false)

        wall_bottom_2_r1 = ModelMapper(modelDataWrapper)
        wall_bottom_2_r1.setPos(21f, -5f, 0f)
        door.addChild(wall_bottom_2_r1)
        setRotationAngle(wall_bottom_2_r1, 0f, 0f, 0.1745f)
        wall_bottom_2_r1.texOffs(114, 160).addBox(-1f, 0f, 16f, 1, 7, 4, 0f, false)
        wall_bottom_2_r1.texOffs(160, 139).addBox(-1f, 0f, -20f, 1, 7, 4, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(188, 52).addBox(20f, -10f, -16f, 1, 5, 16, 0f, false)

        door_top_r1 = ModelMapper(modelDataWrapper)
        door_top_r1.setPos(21f, -10f, 0f)
        door_left.addChild(door_top_r1)
        setRotationAngle(door_top_r1, 0f, 0f, -0.0873f)
        door_top_r1.texOffs(96, 160).addBox(-1f, -26f, -16f, 1, 26, 16, 0f, false)

        door_bottom_r1 = ModelMapper(modelDataWrapper)
        door_bottom_r1.setPos(21f, -5f, 0f)
        door_left.addChild(door_bottom_r1)
        setRotationAngle(door_bottom_r1, 0f, 0f, 0.1745f)
        door_bottom_r1.texOffs(182, 29).addBox(-1f, 0f, -16f, 1, 7, 16, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(36, 188).addBox(20f, -10f, 0f, 1, 5, 16, 0f, false)

        door_top_r2 = ModelMapper(modelDataWrapper)
        door_top_r2.setPos(21f, -10f, 0f)
        door_right.addChild(door_top_r2)
        setRotationAngle(door_top_r2, 0f, 0f, -0.0873f)
        door_top_r2.texOffs(0, 73).addBox(-1f, -26f, 0f, 1, 26, 16, 0f, false)

        door_bottom_r2 = ModelMapper(modelDataWrapper)
        door_bottom_r2.setPos(21f, -5f, 0f)
        door_right.addChild(door_bottom_r2)
        setRotationAngle(door_bottom_r2, 0f, 0f, 0.1745f)
        door_bottom_r2.texOffs(54, 169).addBox(-1f, 0f, 0f, 1, 7, 16, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(20, 126).addBox(21f, -10f, -20f, 0, 5, 4, 0f, false)
        door_exterior.texOffs(32, 112).addBox(21f, -10f, 16f, 0, 5, 4, 0f, false)

        wall_top_3_r1 = ModelMapper(modelDataWrapper)
        wall_top_3_r1.setPos(21f, -10f, 0f)
        door_exterior.addChild(wall_top_3_r1)
        setRotationAngle(wall_top_3_r1, 0f, 0f, -0.0873f)
        wall_top_3_r1.texOffs(70, 202).addBox(0f, -26f, 16f, 0, 26, 4, 0f, false)
        wall_top_3_r1.texOffs(78, 202).addBox(0f, -26f, -20f, 0, 26, 4, 0f, false)

        wall_bottom_3_r1 = ModelMapper(modelDataWrapper)
        wall_bottom_3_r1.setPos(21f, -5f, 0f)
        door_exterior.addChild(wall_bottom_3_r1)
        setRotationAngle(wall_bottom_3_r1, 0f, 0f, 0.1745f)
        wall_bottom_3_r1.texOffs(112, 100).addBox(0f, 0f, 16f, 0, 7, 4, 0f, false)
        wall_bottom_3_r1.texOffs(146, 0).addBox(0f, 0f, -20f, 0, 7, 4, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(154, 18).addBox(21f, -10f, -16f, 0, 5, 16, 0f, false)

        door_top_r3 = ModelMapper(modelDataWrapper)
        door_top_r3.setPos(21f, -10f, 0f)
        door_left_exterior.addChild(door_top_r3)
        setRotationAngle(door_top_r3, 0f, 0f, -0.0873f)
        door_top_r3.texOffs(0, 150).addBox(0f, -26f, -16f, 0, 26, 16, 0f, false)

        door_bottom_r3 = ModelMapper(modelDataWrapper)
        door_bottom_r3.setPos(21f, -5f, 0f)
        door_left_exterior.addChild(door_bottom_r3)
        setRotationAngle(door_bottom_r3, 0f, 0f, 0.1745f)
        door_bottom_r3.texOffs(72, 153).addBox(0f, 0f, -16f, 0, 7, 16, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(154, 13).addBox(21f, -10f, 0f, 0, 5, 16, 0f, false)

        door_top_r4 = ModelMapper(modelDataWrapper)
        door_top_r4.setPos(21f, -10f, 0f)
        door_right_exterior.addChild(door_top_r4)
        setRotationAngle(door_top_r4, 0f, 0f, -0.0873f)
        door_top_r4.texOffs(130, 149).addBox(0f, -26f, 0f, 0, 26, 16, 0f, false)

        door_bottom_r4 = ModelMapper(modelDataWrapper)
        door_bottom_r4.setPos(21f, -5f, 0f)
        door_right_exterior.addChild(door_bottom_r4)
        setRotationAngle(door_bottom_r4, 0f, 0f, 0.1745f)
        door_bottom_r4.texOffs(0, 100).addBox(0f, 0f, 0f, 0, 7, 16, 0f, false)

        roof_door_exterior_1 = ModelMapper(modelDataWrapper)
        roof_door_exterior_1.setPos(0f, 24f, 0f)
        roof_door_exterior_1.texOffs(72, 0).addBox(-0.0293f, -41.2781f, -20f, 7, 0, 40, 0f, false)

        roof_3_r4 = ModelMapper(modelDataWrapper)
        roof_3_r4.setPos(11.9516f, -40.8423f, 0f)
        roof_door_exterior_1.addChild(roof_3_r4)
        setRotationAngle(roof_3_r4, 0f, 0f, 0.0873f)
        roof_3_r4.texOffs(20, 42).addBox(-5f, 0f, -20f, 10, 0, 40, 0f, false)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(17.3157f, -40.0862f, 0f)
        roof_door_exterior_1.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, -0.8727f)
        roof_2_r4.texOffs(0, 85).addBox(0f, -0.5f, -20f, 0, 1, 40, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(18.2163f, -37.8329f, 0f)
        roof_door_exterior_1.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, -0.2618f)
        roof_1_r2.texOffs(60, 50).addBox(0f, -2f, -20f, 0, 4, 40, 0f, false)

        roof_door_exterior_2 = ModelMapper(modelDataWrapper)
        roof_door_exterior_2.setPos(0f, 24f, 0f)
        roof_door_exterior_2.texOffs(70, 42).addBox(-0.0293f, -41.2781f, -20f, 7, 0, 40, 0f, false)

        roof_4_r3 = ModelMapper(modelDataWrapper)
        roof_4_r3.setPos(11.9516f, -40.8423f, 0f)
        roof_door_exterior_2.addChild(roof_4_r3)
        setRotationAngle(roof_4_r3, 0f, 0f, 0.0873f)
        roof_4_r3.texOffs(0, 42).addBox(-5f, 0f, -20f, 10, 0, 40, 0f, false)

        roof_3_r5 = ModelMapper(modelDataWrapper)
        roof_3_r5.setPos(17.3157f, -40.0862f, 0f)
        roof_door_exterior_2.addChild(roof_3_r5)
        setRotationAngle(roof_3_r5, 0f, 0f, -0.8727f)
        roof_3_r5.texOffs(80, 84).addBox(0f, -0.5f, -20f, 0, 1, 40, 0f, false)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(18.2163f, -37.8329f, 0f)
        roof_door_exterior_2.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0f, -0.2618f)
        roof_2_r5.texOffs(60, 46).addBox(0f, -2f, -20f, 0, 4, 40, 0f, false)

        roof_door_exterior_3 = ModelMapper(modelDataWrapper)
        roof_door_exterior_3.setPos(0f, 24f, 0f)
        roof_door_exterior_3.texOffs(58, 0).addBox(-0.0293f, -41.2781f, -20f, 7, 0, 40, 0f, false)

        roof_5_r1 = ModelMapper(modelDataWrapper)
        roof_5_r1.setPos(11.9516f, -40.8423f, 0f)
        roof_door_exterior_3.addChild(roof_5_r1)
        setRotationAngle(roof_5_r1, 0f, 0f, 0.0873f)
        roof_5_r1.texOffs(38, 0).addBox(-5f, 0f, -20f, 10, 0, 40, 0f, false)

        roof_4_r4 = ModelMapper(modelDataWrapper)
        roof_4_r4.setPos(17.3157f, -40.0862f, 0f)
        roof_door_exterior_3.addChild(roof_4_r4)
        setRotationAngle(roof_4_r4, 0f, 0f, -0.8727f)
        roof_4_r4.texOffs(0, 84).addBox(0f, -0.5f, -20f, 0, 1, 40, 0f, false)

        roof_3_r6 = ModelMapper(modelDataWrapper)
        roof_3_r6.setPos(18.2163f, -37.8329f, 0f)
        roof_door_exterior_3.addChild(roof_3_r6)
        setRotationAngle(roof_3_r6, 0f, 0f, -0.2618f)
        roof_3_r6.texOffs(60, 42).addBox(0f, -2f, -20f, 0, 4, 40, 0f, false)

        roof_window_exterior = ModelMapper(modelDataWrapper)
        roof_window_exterior.setPos(0f, 24f, 0f)
        roof_window_exterior.texOffs(16, 126).addBox(-0.0293f, -41.2781f, -14f, 7, 0, 28, 0f, false)

        roof_6_r1 = ModelMapper(modelDataWrapper)
        roof_6_r1.setPos(11.9516f, -40.8423f, 0f)
        roof_window_exterior.addChild(roof_6_r1)
        setRotationAngle(roof_6_r1, 0f, 0f, 0.0873f)
        roof_6_r1.texOffs(98, 0).addBox(-5f, 0f, -14f, 10, 0, 28, 0f, false)

        roof_5_r2 = ModelMapper(modelDataWrapper)
        roof_5_r2.setPos(17.3157f, -40.0862f, 0f)
        roof_window_exterior.addChild(roof_5_r2)
        setRotationAngle(roof_5_r2, 0f, 0f, -0.8727f)
        roof_5_r2.texOffs(74, 123).addBox(0f, -0.5f, -14f, 0, 1, 28, 0f, false)

        roof_4_r5 = ModelMapper(modelDataWrapper)
        roof_4_r5.setPos(18.2163f, -37.8329f, 0f)
        roof_window_exterior.addChild(roof_4_r5)
        setRotationAngle(roof_4_r5, 0f, 0f, -0.2618f)
        roof_4_r5.texOffs(56, 133).addBox(0f, -2f, -14f, 0, 4, 28, 0f, false)

        seat_1 = ModelMapper(modelDataWrapper)
        seat_1.setPos(0f, 24f, 0f)
        seat_1.texOffs(162, 93).addBox(15f, -8f, -11f, 5, 8, 17, 0f, false)
        seat_1.texOffs(160, 139).addBox(11f, -9f, -10f, 9, 1, 16, 0f, false)
        seat_1.texOffs(72, 187).addBox(18f, -12f, -10f, 2, 3, 16, 0f, false)
        seat_1.texOffs(16, 185).addBox(18f, -19f, -10f, 2, 3, 16, 0f, false)
        seat_1.texOffs(194, 126).addBox(11f, -19f, -11f, 9, 11, 1, 0f, false)
        seat_1.texOffs(250, 0).addBox(11.5f, -21f, -10.5f, 0, 6, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(10.0471f, -35.6984f, -10.5f)
        seat_1.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, -0.1309f)
        handrail_3_r1.texOffs(250, 0).addBox(0f, -2f, 0f, 0, 6, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(11.7f, -21.2f, 0f)
        seat_1.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, -0.0873f)
        handrail_2_r1.texOffs(250, 0).addBox(-0.2f, -10.2f, -10.5f, 0, 10, 0, 0.2f, false)

        seat_2 = ModelMapper(modelDataWrapper)
        seat_2.setPos(0f, 24f, 0f)
        seat_2.texOffs(161, 68).addBox(15f, -8f, -6f, 5, 8, 17, 0f, false)
        seat_2.texOffs(160, 122).addBox(11f, -9f, -6f, 9, 1, 16, 0f, false)
        seat_2.texOffs(173, 184).addBox(18f, -12f, -6f, 2, 3, 16, 0f, false)
        seat_2.texOffs(184, 156).addBox(18f, -19f, -6f, 2, 3, 16, 0f, false)
        seat_2.texOffs(144, 220).addBox(11f, -19f, 10f, 9, 11, 1, 0f, false)
        seat_2.texOffs(250, 0).addBox(11.5f, -21f, 10.5f, 0, 6, 0, 0.2f, false)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(10.0471f, -35.6984f, -10.5f)
        seat_2.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, -0.1309f)
        handrail_3_r2.texOffs(250, 0).addBox(0f, -4f, 21f, 0, 8, 0, 0.2f, false)

        handrail_2_r2 = ModelMapper(modelDataWrapper)
        handrail_2_r2.setPos(11.7f, -21.2f, 0f)
        seat_2.addChild(handrail_2_r2)
        setRotationAngle(handrail_2_r2, 0f, 0f, -0.0873f)
        handrail_2_r2.texOffs(250, 0).addBox(-0.2f, -10.2f, 10.5f, 0, 10, 0, 0.2f, false)

        seat_3 = ModelMapper(modelDataWrapper)
        seat_3.setPos(0f, 24f, 0f)
        seat_3.texOffs(118, 218).addBox(15f, -8f, -8f, 5, 8, 16, 0f, false)
        seat_3.texOffs(154, 12).addBox(10f, -9f, -8f, 10, 1, 16, 0f, false)
        seat_3.texOffs(32, 166).addBox(17f, -12f, -8f, 3, 3, 16, 0f, false)
        seat_3.texOffs(162, 165).addBox(17f, -19f, -8f, 3, 3, 16, 0f, false)
        seat_3.texOffs(162, 107).addBox(11f, -12f, 8f, 7, 3, 0, 0f, false)
        seat_3.texOffs(162, 107).addBox(11f, -12f, -8f, 7, 3, 0, 0f, false)

        back_upper_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        back_upper_diagonal_2_r1.setPos(18f, 0f, -8f)
        seat_3.addChild(back_upper_diagonal_2_r1)
        setRotationAngle(back_upper_diagonal_2_r1, 0f, -0.1745f, 0f)
        back_upper_diagonal_2_r1.texOffs(124, 71).addBox(0f, -19f, 0f, 1, 3, 6, 0f, false)
        back_upper_diagonal_2_r1.texOffs(0, 135).addBox(0f, -12f, 0f, 1, 3, 6, 0f, false)

        back_upper_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        back_upper_diagonal_1_r1.setPos(18f, 0f, 8f)
        seat_3.addChild(back_upper_diagonal_1_r1)
        setRotationAngle(back_upper_diagonal_1_r1, 0f, 0.1745f, 0f)
        back_upper_diagonal_1_r1.texOffs(0, 72).addBox(0f, -19f, -6f, 1, 3, 6, 0f, false)
        back_upper_diagonal_1_r1.texOffs(0, 144).addBox(0f, -12f, -6f, 1, 3, 6, 0f, false)

        seat_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        seat_diagonal_2_r1.setPos(11f, 0f, -8f)
        seat_3.addChild(seat_diagonal_2_r1)
        setRotationAngle(seat_diagonal_2_r1, 0f, -0.1745f, 0f)
        seat_diagonal_2_r1.texOffs(152, 87).addBox(0f, -9f, 0f, 1, 1, 6, 0f, false)

        seat_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        seat_diagonal_1_r1.setPos(11f, 0f, 8f)
        seat_3.addChild(seat_diagonal_1_r1)
        setRotationAngle(seat_diagonal_1_r1, 0f, 0.1745f, 0f)
        seat_diagonal_1_r1.texOffs(12, 130).addBox(0f, -9f, -6f, 1, 1, 6, 0f, false)

        light_window = ModelMapper(modelDataWrapper)
        light_window.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(10f, -37f, 0f)
        light_window.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.0873f)
        light_r1.texOffs(6, 209).addBox(-5f, -0.1f, -14f, 6, 0, 28, 0f, false)

        light_door = ModelMapper(modelDataWrapper)
        light_door.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(10f, -37f, 0f)
        light_door.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.0873f)
        light_r2.texOffs(10, 209).addBox(-5f, -0.1f, -20f, 6, 0, 40, 0f, false)

        middle_handrail = ModelMapper(modelDataWrapper)
        middle_handrail.setPos(0f, 24f, 0f)
        middle_handrail.texOffs(250, 0).addBox(0f, -12f, 0f, 0, 12, 0, 0.2f, false)
        middle_handrail.texOffs(250, 0).addBox(0f, -22.0809f, -1.1969f, 0, 1, 0, 0.2f, false)
        middle_handrail.texOffs(250, 0).addBox(0f, -37f, 0f, 0, 5, 0, 0.2f, false)
        middle_handrail.texOffs(183, 219).addBox(0f, -35f, -18f, 0, 0, 36, 0.2f, false)
        middle_handrail.texOffs(36, 52).addBox(-1f, -35.5f, -16f, 2, 4, 0, 0f, false)
        middle_handrail.texOffs(36, 48).addBox(-1f, -35.5f, 16f, 2, 4, 0, 0f, false)

        top_handrail_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_5_r1.setPos(0f, -36.6392f, 19.8392f)
        middle_handrail.addChild(top_handrail_5_r1)
        setRotationAngle(top_handrail_5_r1, 1.0472f, 0f, 0f)
        top_handrail_5_r1.texOffs(217, 253).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        top_handrail_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_4_r1.setPos(0f, -34.8f, 18.2f)
        middle_handrail.addChild(top_handrail_4_r1)
        setRotationAngle(top_handrail_4_r1, 0.5236f, 0f, 0f)
        top_handrail_4_r1.texOffs(218, 254).addBox(0f, -0.2f, 0.2f, 0, 0, 1, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(0f, -36.6392f, -19.8392f)
        middle_handrail.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.0472f, 0f, 0f)
        top_handrail_3_r1.texOffs(217, 253).addBox(0f, 0f, -1f, 0, 0, 2, 0.2f, false)

        top_handrail_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_2_r1.setPos(0f, -34.8f, -18.2f)
        middle_handrail.addChild(top_handrail_2_r1)
        setRotationAngle(top_handrail_2_r1, -0.5236f, 0f, 0f)
        top_handrail_2_r1.texOffs(218, 254).addBox(0f, -0.2f, -1.2f, 0, 0, 1, 0.2f, false)

        handrail_16_r1 = ModelMapper(modelDataWrapper)
        handrail_16_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_16_r1)
        setRotationAngle(handrail_16_r1, -0.1745f, 2.0944f, 0f)
        handrail_16_r1.texOffs(250, 0).addBox(-0.2591f, -10.0003f, -1.9152f, 0, 4, 0, 0.2f, false)

        handrail_15_r1 = ModelMapper(modelDataWrapper)
        handrail_15_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_15_r1)
        setRotationAngle(handrail_15_r1, -0.1745f, -2.0944f, 0f)
        handrail_15_r1.texOffs(250, 0).addBox(0.2591f, -10.0003f, -1.9152f, 0, 4, 0, 0.2f, false)

        handrail_14_r1 = ModelMapper(modelDataWrapper)
        handrail_14_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_14_r1)
        setRotationAngle(handrail_14_r1, -0.1745f, 0f, 0f)
        handrail_14_r1.texOffs(250, 0).addBox(0f, -10.0782f, -1.4732f, 0, 4, 0, 0.2f, false)

        handrail_13_r1 = ModelMapper(modelDataWrapper)
        handrail_13_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_13_r1)
        setRotationAngle(handrail_13_r1, -0.0873f, 2.0944f, 0f)
        handrail_13_r1.texOffs(250, 0).addBox(-0.2591f, -5.7625f, -1.4017f, 0, 5, 0, 0.2f, false)

        handrail_12_r1 = ModelMapper(modelDataWrapper)
        handrail_12_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_12_r1)
        setRotationAngle(handrail_12_r1, -0.0873f, -2.0944f, 0f)
        handrail_12_r1.texOffs(250, 0).addBox(0.2591f, -5.7625f, -1.4017f, 0, 5, 0, 0.2f, false)

        handrail_11_r1 = ModelMapper(modelDataWrapper)
        handrail_11_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_11_r1)
        setRotationAngle(handrail_11_r1, -0.0873f, 0f, 0f)
        handrail_11_r1.texOffs(250, 0).addBox(0f, -5.8017f, -0.9545f, 0, 5, 0, 0.2f, false)

        handrail_10_r1 = ModelMapper(modelDataWrapper)
        handrail_10_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_10_r1)
        setRotationAngle(handrail_10_r1, 0f, 2.0944f, 0f)
        handrail_10_r1.texOffs(250, 0).addBox(-0.2591f, -0.5f, -1.3465f, 0, 1, 0, 0.2f, false)

        handrail_9_r1 = ModelMapper(modelDataWrapper)
        handrail_9_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_9_r1)
        setRotationAngle(handrail_9_r1, 0f, -2.0944f, 0f)
        handrail_9_r1.texOffs(250, 0).addBox(0.2591f, -0.5f, -1.3465f, 0, 1, 0, 0.2f, false)

        handrail_7_r1 = ModelMapper(modelDataWrapper)
        handrail_7_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_7_r1)
        setRotationAngle(handrail_7_r1, 0.0873f, 2.0944f, 0f)
        handrail_7_r1.texOffs(250, 0).addBox(-0.2591f, 0.7625f, -1.4017f, 0, 5, 0, 0.2f, false)

        handrail_6_r1 = ModelMapper(modelDataWrapper)
        handrail_6_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_6_r1)
        setRotationAngle(handrail_6_r1, 0.0873f, -2.0944f, 0f)
        handrail_6_r1.texOffs(250, 0).addBox(0.2591f, 0.7625f, -1.4017f, 0, 5, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0.0873f, 0f, 0f)
        handrail_5_r1.texOffs(250, 0).addBox(0f, 0.8017f, -0.9545f, 0, 5, 0, 0.2f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0.1745f, 2.0944f, 0f)
        handrail_4_r1.texOffs(250, 0).addBox(-0.2591f, 6.0003f, -1.9152f, 0, 4, 0, 0.2f, false)

        handrail_3_r3 = ModelMapper(modelDataWrapper)
        handrail_3_r3.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_3_r3)
        setRotationAngle(handrail_3_r3, 0.1745f, -2.0944f, 0f)
        handrail_3_r3.texOffs(250, 0).addBox(0.2591f, 6.0003f, -1.9152f, 0, 4, 0, 0.2f, false)

        handrail_2_r3 = ModelMapper(modelDataWrapper)
        handrail_2_r3.setPos(0f, -21.5809f, -0.2992f)
        middle_handrail.addChild(handrail_2_r3)
        setRotationAngle(handrail_2_r3, 0.1745f, 0f, 0f)
        handrail_2_r3.texOffs(250, 0).addBox(0f, 6.0782f, -1.4732f, 0, 4, 0, 0.2f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)


        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        head.addChild(side_1)
        side_1.texOffs(98, 94).addBox(0f, 0f, -14f, 19, 2, 26, 0f, false)
        side_1.texOffs(194, 109).addBox(20f, -10f, 0f, 1, 5, 12, 0f, false)
        side_1.texOffs(30, 42).addBox(16.734f, -33.9011f, 6f, 2, 0, 6, 0f, false)
        side_1.texOffs(0, 69).addBox(0f, -37.3304f, 9f, 7, 0, 3, 0f, false)
        side_1.texOffs(124, 67).addBox(0f, -33.9011f, -10f, 19, 0, 16, 0f, false)
        side_1.texOffs(14, 0).addBox(0f, -10f, -14f, 6, 10, 1, 0f, false)
        side_1.texOffs(157, 122).addBox(0f, -20f, -10f, 6, 5, 3, 0f, false)

        bar_2_r1 = ModelMapper(modelDataWrapper)
        bar_2_r1.setPos(6f, 0f, -10f)
        side_1.addChild(bar_2_r1)
        setRotationAngle(bar_2_r1, 0f, -0.3491f, 0f)
        bar_2_r1.texOffs(188, 73).addBox(0f, -19f, 0f, 15, 3, 2, 0f, false)

        front_11_r1 = ModelMapper(modelDataWrapper)
        front_11_r1.setPos(15.9636f, -23.2081f, -8.1364f)
        side_1.addChild(front_11_r1)
        setRotationAngle(front_11_r1, -0.1745f, -0.7854f, 0f)
        front_11_r1.texOffs(190, 0).addBox(-3f, -13.5f, -0.5f, 8, 27, 1, 0f, false)

        front_10_r1 = ModelMapper(modelDataWrapper)
        front_10_r1.setPos(21f, -10f, 0f)
        side_1.addChild(front_10_r1)
        setRotationAngle(front_10_r1, 0f, 0.1745f, -0.0873f)
        front_10_r1.texOffs(153, 184).addBox(-1f, -27f, -9f, 1, 27, 9, 0f, false)

        front_9_r1 = ModelMapper(modelDataWrapper)
        front_9_r1.setPos(21f, 0f, 0f)
        side_1.addChild(front_9_r1)
        setRotationAngle(front_9_r1, 0f, 0.1745f, 0f)
        front_9_r1.texOffs(200, 20).addBox(-1f, -10f, -9f, 1, 5, 9, 0f, false)

        front_8_r1 = ModelMapper(modelDataWrapper)
        front_8_r1.setPos(17.6158f, -7.5f, -9.7886f)
        side_1.addChild(front_8_r1)
        setRotationAngle(front_8_r1, 0f, -0.7854f, 0f)
        front_8_r1.texOffs(160, 130).addBox(-3f, -2.5f, -0.5f, 6, 5, 1, 0f, false)

        front_4_r1 = ModelMapper(modelDataWrapper)
        front_4_r1.setPos(21f, -5f, 0f)
        side_1.addChild(front_4_r1)
        setRotationAngle(front_4_r1, 0f, 0.1745f, 0.1745f)
        front_4_r1.texOffs(194, 139).addBox(-1f, 0f, -10f, 1, 6, 10, 0f, false)

        front_3_r1 = ModelMapper(modelDataWrapper)
        front_3_r1.setPos(17.1096f, -2.5531f, -9.9895f)
        side_1.addChild(front_3_r1)
        setRotationAngle(front_3_r1, 0.0873f, -0.7854f, 0f)
        front_3_r1.texOffs(0, 81).addBox(-3.5f, -2.5f, -0.5f, 7, 6, 1, 0f, false)

        front_2_r1 = ModelMapper(modelDataWrapper)
        front_2_r1.setPos(6f, 0f, -14f)
        side_1.addChild(front_2_r1)
        setRotationAngle(front_2_r1, 0f, -0.1745f, 0f)
        front_2_r1.texOffs(168, 0).addBox(0f, -10f, 0f, 10, 10, 1, 0f, false)

        roof_8_r1 = ModelMapper(modelDataWrapper)
        roof_8_r1.setPos(7.5f, -36.2001f, 8.4821f)
        side_1.addChild(roof_8_r1)
        setRotationAngle(roof_8_r1, 1.0472f, 0f, 0f)
        roof_8_r1.texOffs(157, 156).addBox(-7.5f, 0f, -1.5f, 15, 0, 3, 0f, false)

        roof_7_r1 = ModelMapper(modelDataWrapper)
        roof_7_r1.setPos(8.5f, -34.4011f, 6.866f)
        side_1.addChild(roof_7_r1)
        setRotationAngle(roof_7_r1, 0.5236f, 0f, 0f)
        roof_7_r1.texOffs(124, 122).addBox(-8.5f, 0f, -1f, 17, 0, 2, 0f, false)

        roof_4_r6 = ModelMapper(modelDataWrapper)
        roof_4_r6.setPos(10.0171f, -36.9817f, 10f)
        side_1.addChild(roof_4_r6)
        setRotationAngle(roof_4_r6, 0f, 0f, 0.0873f)
        roof_4_r6.texOffs(120, 67).addBox(-4f, 0f, -2f, 8, 0, 4, 0f, false)

        roof_3_r7 = ModelMapper(modelDataWrapper)
        roof_3_r7.setPos(14.5019f, -35.7671f, 9.5f)
        side_1.addChild(roof_3_r7)
        setRotationAngle(roof_3_r7, 0f, 0f, 1.0472f)
        roof_3_r7.texOffs(19, 48).addBox(-1f, 0f, -2.5f, 2, 0, 5, 0f, false)

        roof_2_r6 = ModelMapper(modelDataWrapper)
        roof_2_r6.setPos(15.8679f, -34.4011f, 9f)
        side_1.addChild(roof_2_r6)
        setRotationAngle(roof_2_r6, 0f, 0f, 0.5236f)
        roof_2_r6.texOffs(18, 42).addBox(-1f, 0f, -3f, 2, 0, 6, 0f, false)

        wall_top_r3 = ModelMapper(modelDataWrapper)
        wall_top_r3.setPos(21f, -10f, 0f)
        side_1.addChild(wall_top_r3)
        setRotationAngle(wall_top_r3, 0f, 0f, -0.0873f)
        wall_top_r3.texOffs(0, 0).addBox(-1f, -27f, 0f, 1, 27, 12, 0f, false)

        wall_bottom_r3 = ModelMapper(modelDataWrapper)
        wall_bottom_r3.setPos(21f, -5f, 0f)
        side_1.addChild(wall_bottom_r3)
        setRotationAngle(wall_bottom_r3, 0f, 0f, 0.1745f)
        wall_bottom_r3.texOffs(193, 175).addBox(-1f, 0f, 0f, 1, 6, 12, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        head.addChild(side_2)
        side_2.texOffs(98, 94).addBox(-19f, 0f, -14f, 19, 2, 26, 0f, true)
        side_2.texOffs(194, 109).addBox(-21f, -10f, 0f, 1, 5, 12, 0f, true)
        side_2.texOffs(30, 42).addBox(-18.734f, -33.9011f, 6f, 2, 0, 6, 0f, true)
        side_2.texOffs(0, 69).addBox(-7f, -37.3304f, 9f, 7, 0, 3, 0f, true)
        side_2.texOffs(124, 67).addBox(-19f, -33.9011f, -10f, 19, 0, 16, 0f, true)
        side_2.texOffs(14, 0).addBox(-6f, -10f, -14f, 6, 10, 1, 0f, true)
        side_2.texOffs(157, 122).addBox(-6f, -20f, -10f, 6, 5, 3, 0f, true)

        bar_3_r1 = ModelMapper(modelDataWrapper)
        bar_3_r1.setPos(-6f, 0f, -10f)
        side_2.addChild(bar_3_r1)
        setRotationAngle(bar_3_r1, 0f, 0.3491f, 0f)
        bar_3_r1.texOffs(188, 73).addBox(-15f, -19f, 0f, 15, 3, 2, 0f, true)

        front_12_r1 = ModelMapper(modelDataWrapper)
        front_12_r1.setPos(-15.9636f, -23.2081f, -8.1364f)
        side_2.addChild(front_12_r1)
        setRotationAngle(front_12_r1, -0.1745f, 0.7854f, 0f)
        front_12_r1.texOffs(190, 0).addBox(-5f, -13.5f, -0.5f, 8, 27, 1, 0f, true)

        front_11_r2 = ModelMapper(modelDataWrapper)
        front_11_r2.setPos(-21f, -10f, 0f)
        side_2.addChild(front_11_r2)
        setRotationAngle(front_11_r2, 0f, -0.1745f, 0.0873f)
        front_11_r2.texOffs(153, 184).addBox(0f, -27f, -9f, 1, 27, 9, 0f, true)

        front_10_r2 = ModelMapper(modelDataWrapper)
        front_10_r2.setPos(-21f, 0f, 0f)
        side_2.addChild(front_10_r2)
        setRotationAngle(front_10_r2, 0f, -0.1745f, 0f)
        front_10_r2.texOffs(200, 20).addBox(0f, -10f, -9f, 1, 5, 9, 0f, true)

        front_9_r2 = ModelMapper(modelDataWrapper)
        front_9_r2.setPos(-17.6158f, -7.5f, -9.7886f)
        side_2.addChild(front_9_r2)
        setRotationAngle(front_9_r2, 0f, 0.7854f, 0f)
        front_9_r2.texOffs(160, 130).addBox(-3f, -2.5f, -0.5f, 6, 5, 1, 0f, true)

        front_5_r1 = ModelMapper(modelDataWrapper)
        front_5_r1.setPos(-21f, -5f, 0f)
        side_2.addChild(front_5_r1)
        setRotationAngle(front_5_r1, 0f, -0.1745f, -0.1745f)
        front_5_r1.texOffs(194, 139).addBox(0f, 0f, -10f, 1, 6, 10, 0f, true)

        front_4_r2 = ModelMapper(modelDataWrapper)
        front_4_r2.setPos(-17.1096f, -2.5531f, -9.9895f)
        side_2.addChild(front_4_r2)
        setRotationAngle(front_4_r2, 0.0873f, 0.7854f, 0f)
        front_4_r2.texOffs(0, 81).addBox(-3.5f, -2.5f, -0.5f, 7, 6, 1, 0f, true)

        front_3_r2 = ModelMapper(modelDataWrapper)
        front_3_r2.setPos(-6f, 0f, -14f)
        side_2.addChild(front_3_r2)
        setRotationAngle(front_3_r2, 0f, 0.1745f, 0f)
        front_3_r2.texOffs(168, 0).addBox(-10f, -10f, 0f, 10, 10, 1, 0f, true)

        roof_9_r1 = ModelMapper(modelDataWrapper)
        roof_9_r1.setPos(-7.5f, -36.2001f, 8.4821f)
        side_2.addChild(roof_9_r1)
        setRotationAngle(roof_9_r1, 1.0472f, 0f, 0f)
        roof_9_r1.texOffs(157, 156).addBox(-7.5f, 0f, -1.5f, 15, 0, 3, 0f, true)

        roof_8_r2 = ModelMapper(modelDataWrapper)
        roof_8_r2.setPos(-8.5f, -34.4011f, 6.866f)
        side_2.addChild(roof_8_r2)
        setRotationAngle(roof_8_r2, 0.5236f, 0f, 0f)
        roof_8_r2.texOffs(124, 122).addBox(-8.5f, 0f, -1f, 17, 0, 2, 0f, true)

        roof_5_r3 = ModelMapper(modelDataWrapper)
        roof_5_r3.setPos(-10.0171f, -36.9817f, 10f)
        side_2.addChild(roof_5_r3)
        setRotationAngle(roof_5_r3, 0f, 0f, -0.0873f)
        roof_5_r3.texOffs(120, 67).addBox(-4f, 0f, -2f, 8, 0, 4, 0f, true)

        roof_4_r7 = ModelMapper(modelDataWrapper)
        roof_4_r7.setPos(-14.5019f, -35.7671f, 9.5f)
        side_2.addChild(roof_4_r7)
        setRotationAngle(roof_4_r7, 0f, 0f, -1.0472f)
        roof_4_r7.texOffs(19, 48).addBox(-1f, 0f, -2.5f, 2, 0, 5, 0f, true)

        roof_3_r8 = ModelMapper(modelDataWrapper)
        roof_3_r8.setPos(-15.8679f, -34.4011f, 9f)
        side_2.addChild(roof_3_r8)
        setRotationAngle(roof_3_r8, 0f, 0f, -0.5236f)
        roof_3_r8.texOffs(18, 42).addBox(-1f, 0f, -3f, 2, 0, 6, 0f, true)

        wall_top_r4 = ModelMapper(modelDataWrapper)
        wall_top_r4.setPos(-21f, -10f, 0f)
        side_2.addChild(wall_top_r4)
        setRotationAngle(wall_top_r4, 0f, 0f, 0.0873f)
        wall_top_r4.texOffs(0, 0).addBox(0f, -27f, 0f, 1, 27, 12, 0f, true)

        wall_bottom_r4 = ModelMapper(modelDataWrapper)
        wall_bottom_r4.setPos(-21f, -5f, 0f)
        side_2.addChild(wall_bottom_r4)
        setRotationAngle(wall_bottom_r4, 0f, 0f, -0.1745f)
        wall_bottom_r4.texOffs(193, 175).addBox(0f, 0f, 0f, 1, 6, 12, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)


        side_1_exterior = ModelMapper(modelDataWrapper)
        side_1_exterior.setPos(0f, 0f, 0f)
        head_exterior.addChild(side_1_exterior)
        side_1_exterior.texOffs(98, 101).addBox(21f, -10f, 0f, 0, 5, 12, 0f, false)
        side_1_exterior.texOffs(112, 94).addBox(0f, -10f, -14f, 6, 10, 0, 0f, false)
        side_1_exterior.texOffs(79, 94).addBox(-0.0293f, -41.2781f, -7f, 7, 0, 19, 0f, false)

        roof_8_r3 = ModelMapper(modelDataWrapper)
        roof_8_r3.setPos(3f, -40.5084f, -7.6415f)
        side_1_exterior.addChild(roof_8_r3)
        setRotationAngle(roof_8_r3, -0.8727f, 0f, 0f)
        roof_8_r3.texOffs(32, 166).addBox(-3f, -1.5f, 0f, 6, 3, 0, 0f, false)

        roof_7_r2 = ModelMapper(modelDataWrapper)
        roof_7_r2.setPos(9.3275f, -40.5084f, -6.9567f)
        side_1_exterior.addChild(roof_7_r2)
        setRotationAngle(roof_7_r2, -0.8727f, -0.1745f, 0f)
        roof_7_r2.texOffs(140, 87).addBox(-4.5f, -1.5f, 0f, 9, 3, 0, 0f, false)

        roof_6_r2 = ModelMapper(modelDataWrapper)
        roof_6_r2.setPos(14.1804f, -40.5084f, -4.9389f)
        side_1_exterior.addChild(roof_6_r2)
        setRotationAngle(roof_6_r2, -0.8727f, -0.7854f, 0f)
        roof_6_r2.texOffs(140, 90).addBox(-4f, -1.5f, 0f, 8, 3, 0, 0f, false)

        roof_5_r4 = ModelMapper(modelDataWrapper)
        roof_5_r4.setPos(17.7514f, -38.226f, -1.9696f)
        side_1_exterior.addChild(roof_5_r4)
        setRotationAngle(roof_5_r4, 0f, 0.1745f, -0.2618f)
        roof_5_r4.texOffs(8, 68).addBox(0f, -2.5f, -2f, 0, 5, 4, 0f, false)

        roof_3_r9 = ModelMapper(modelDataWrapper)
        roof_3_r9.setPos(11.9516f, -40.8423f, 0f)
        side_1_exterior.addChild(roof_3_r9)
        setRotationAngle(roof_3_r9, 0f, 0f, 0.0873f)
        roof_3_r9.texOffs(0, 70).addBox(-5f, 0f, -7f, 10, 0, 19, 0f, false)

        roof_2_r7 = ModelMapper(modelDataWrapper)
        roof_2_r7.setPos(17.3157f, -40.0862f, 0f)
        side_1_exterior.addChild(roof_2_r7)
        setRotationAngle(roof_2_r7, 0f, 0f, -0.8727f)
        roof_2_r7.texOffs(0, 23).addBox(0f, -0.5f, -4f, 0, 1, 16, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(18.2163f, -37.8329f, 0f)
        side_1_exterior.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, -0.2618f)
        roof_1_r3.texOffs(0, 114).addBox(0f, -2f, 0f, 0, 4, 12, 0f, false)

        front_14_r1 = ModelMapper(modelDataWrapper)
        front_14_r1.setPos(0f, -10f, -14f)
        side_1_exterior.addChild(front_14_r1)
        setRotationAngle(front_14_r1, -0.1745f, 0f, 0f)
        front_14_r1.texOffs(118, 203).addBox(0f, -30f, 0f, 6, 30, 0, 0f, false)

        front_13_r1 = ModelMapper(modelDataWrapper)
        front_13_r1.setPos(6f, -10f, -14f)
        side_1_exterior.addChild(front_13_r1)
        setRotationAngle(front_13_r1, -0.1745f, -0.1745f, 0f)
        front_13_r1.texOffs(173, 203).addBox(0f, -30f, 0f, 10, 30, 0, 0f, false)

        front_12_r2 = ModelMapper(modelDataWrapper)
        front_12_r2.setPos(16.6707f, -23.2081f, -7.4293f)
        side_1_exterior.addChild(front_12_r2)
        setRotationAngle(front_12_r2, -0.1745f, -0.7854f, 0f)
        front_12_r2.texOffs(193, 203).addBox(-4f, -16.5f, -0.5f, 8, 30, 0, 0f, false)

        front_11_r3 = ModelMapper(modelDataWrapper)
        front_11_r3.setPos(21f, -10f, 0f)
        side_1_exterior.addChild(front_11_r3)
        setRotationAngle(front_11_r3, 0f, 0.1745f, -0.0873f)
        front_11_r3.texOffs(130, 182).addBox(0f, -27f, -9f, 0, 27, 9, 0f, false)

        front_10_r3 = ModelMapper(modelDataWrapper)
        front_10_r3.setPos(21f, 0f, 0f)
        side_1_exterior.addChild(front_10_r3)
        setRotationAngle(front_10_r3, 0f, 0.1745f, 0f)
        front_10_r3.texOffs(0, 121).addBox(0f, -10f, -9f, 0, 5, 9, 0f, false)

        front_9_r3 = ModelMapper(modelDataWrapper)
        front_9_r3.setPos(17.6158f, -7.5f, -9.7886f)
        side_1_exterior.addChild(front_9_r3)
        setRotationAngle(front_9_r3, 0f, -0.7854f, 0f)
        front_9_r3.texOffs(162, 102).addBox(-3f, -2.5f, -0.5f, 6, 5, 0, 0f, false)

        front_8_r2 = ModelMapper(modelDataWrapper)
        front_8_r2.setPos(15.9306f, 5.3106f, -9.5176f)
        side_1_exterior.addChild(front_8_r2)
        setRotationAngle(front_8_r2, 0.1745f, -0.7854f, 0f)
        front_8_r2.texOffs(0, 0).addBox(-3f, -5.5f, -0.5f, 6, 11, 0, 0f, false)

        front_7_r1 = ModelMapper(modelDataWrapper)
        front_7_r1.setPos(6f, 0f, -14f)
        side_1_exterior.addChild(front_7_r1)
        setRotationAngle(front_7_r1, 0.2618f, -0.1745f, 0f)
        front_7_r1.texOffs(126, 28).addBox(0f, 0f, 0f, 11, 11, 0, 0f, false)

        front_6_r1 = ModelMapper(modelDataWrapper)
        front_6_r1.setPos(0f, 0f, -14f)
        side_1_exterior.addChild(front_6_r1)
        setRotationAngle(front_6_r1, 0.2618f, 0f, 0f)
        front_6_r1.texOffs(26, 28).addBox(0f, 0f, 0f, 6, 11, 0, 0f, false)

        front_5_r2 = ModelMapper(modelDataWrapper)
        front_5_r2.setPos(21f, -5f, 0f)
        side_1_exterior.addChild(front_5_r2)
        setRotationAngle(front_5_r2, 0f, 0.1745f, 0.1745f)
        front_5_r2.texOffs(182, 42).addBox(0f, 0f, -10f, 0, 16, 10, 0f, false)

        front_4_r3 = ModelMapper(modelDataWrapper)
        front_4_r3.setPos(17.1096f, -2.5531f, -9.9895f)
        side_1_exterior.addChild(front_4_r3)
        setRotationAngle(front_4_r3, 0.0873f, -0.7854f, 0f)
        front_4_r3.texOffs(160, 150).addBox(-3.5f, -2.5f, -0.5f, 7, 5, 0, 0f, false)

        front_3_r3 = ModelMapper(modelDataWrapper)
        front_3_r3.setPos(6f, 0f, -14f)
        side_1_exterior.addChild(front_3_r3)
        setRotationAngle(front_3_r3, 0f, -0.1745f, 0f)
        front_3_r3.texOffs(200, 34).addBox(0f, -10f, 0f, 10, 10, 0, 0f, false)

        wall_top_r5 = ModelMapper(modelDataWrapper)
        wall_top_r5.setPos(21f, -10f, 0f)
        side_1_exterior.addChild(wall_top_r5)
        setRotationAngle(wall_top_r5, 0f, 0f, -0.0873f)
        wall_top_r5.texOffs(0, 30).addBox(0f, -27f, 0f, 0, 27, 12, 0f, false)

        wall_bottom_r5 = ModelMapper(modelDataWrapper)
        wall_bottom_r5.setPos(21f, -5f, 0f)
        side_1_exterior.addChild(wall_bottom_r5)
        setRotationAngle(wall_bottom_r5, 0f, 0f, 0.1745f)
        wall_bottom_r5.texOffs(189, 81).addBox(0f, 0f, 0f, 0, 16, 12, 0f, false)

        side_2_exterior = ModelMapper(modelDataWrapper)
        side_2_exterior.setPos(0f, 0f, 0f)
        head_exterior.addChild(side_2_exterior)
        side_2_exterior.texOffs(98, 101).addBox(-21f, -10f, 0f, 0, 5, 12, 0f, true)
        side_2_exterior.texOffs(112, 94).addBox(-6f, -10f, -14f, 6, 10, 0, 0f, true)
        side_2_exterior.texOffs(79, 94).addBox(-6.9707f, -41.2781f, -7f, 7, 0, 19, 0f, true)

        roof_9_r2 = ModelMapper(modelDataWrapper)
        roof_9_r2.setPos(-3f, -40.5084f, -7.6415f)
        side_2_exterior.addChild(roof_9_r2)
        setRotationAngle(roof_9_r2, -0.8727f, 0f, 0f)
        roof_9_r2.texOffs(32, 166).addBox(-3f, -1.5f, 0f, 6, 3, 0, 0f, true)

        roof_8_r4 = ModelMapper(modelDataWrapper)
        roof_8_r4.setPos(-9.3275f, -40.5084f, -6.9567f)
        side_2_exterior.addChild(roof_8_r4)
        setRotationAngle(roof_8_r4, -0.8727f, 0.1745f, 0f)
        roof_8_r4.texOffs(140, 87).addBox(-4.5f, -1.5f, 0f, 9, 3, 0, 0f, true)

        roof_7_r3 = ModelMapper(modelDataWrapper)
        roof_7_r3.setPos(-14.1804f, -40.5084f, -4.9389f)
        side_2_exterior.addChild(roof_7_r3)
        setRotationAngle(roof_7_r3, -0.8727f, 0.7854f, 0f)
        roof_7_r3.texOffs(140, 90).addBox(-4f, -1.5f, 0f, 8, 3, 0, 0f, true)

        roof_6_r3 = ModelMapper(modelDataWrapper)
        roof_6_r3.setPos(-17.7514f, -38.226f, -1.9696f)
        side_2_exterior.addChild(roof_6_r3)
        setRotationAngle(roof_6_r3, 0f, -0.1745f, 0.2618f)
        roof_6_r3.texOffs(8, 68).addBox(0f, -2.5f, -2f, 0, 5, 4, 0f, true)

        roof_4_r8 = ModelMapper(modelDataWrapper)
        roof_4_r8.setPos(-11.9516f, -40.8423f, 0f)
        side_2_exterior.addChild(roof_4_r8)
        setRotationAngle(roof_4_r8, 0f, 0f, -0.0873f)
        roof_4_r8.texOffs(0, 70).addBox(-5f, 0f, -7f, 10, 0, 19, 0f, true)

        roof_3_r10 = ModelMapper(modelDataWrapper)
        roof_3_r10.setPos(-17.3157f, -40.0862f, 0f)
        side_2_exterior.addChild(roof_3_r10)
        setRotationAngle(roof_3_r10, 0f, 0f, 0.8727f)
        roof_3_r10.texOffs(0, 23).addBox(0f, -0.5f, -4f, 0, 1, 16, 0f, true)

        roof_2_r8 = ModelMapper(modelDataWrapper)
        roof_2_r8.setPos(-18.2163f, -37.8329f, 0f)
        side_2_exterior.addChild(roof_2_r8)
        setRotationAngle(roof_2_r8, 0f, 0f, 0.2618f)
        roof_2_r8.texOffs(0, 114).addBox(0f, -2f, 0f, 0, 4, 12, 0f, true)

        front_15_r1 = ModelMapper(modelDataWrapper)
        front_15_r1.setPos(0f, -10f, -14f)
        side_2_exterior.addChild(front_15_r1)
        setRotationAngle(front_15_r1, -0.1745f, 0f, 0f)
        front_15_r1.texOffs(118, 203).addBox(-6f, -30f, 0f, 6, 30, 0, 0f, true)

        front_14_r2 = ModelMapper(modelDataWrapper)
        front_14_r2.setPos(-6f, -10f, -14f)
        side_2_exterior.addChild(front_14_r2)
        setRotationAngle(front_14_r2, -0.1745f, 0.1745f, 0f)
        front_14_r2.texOffs(173, 203).addBox(-10f, -30f, 0f, 10, 30, 0, 0f, true)

        front_13_r2 = ModelMapper(modelDataWrapper)
        front_13_r2.setPos(-16.6707f, -23.2081f, -7.4293f)
        side_2_exterior.addChild(front_13_r2)
        setRotationAngle(front_13_r2, -0.1745f, 0.7854f, 0f)
        front_13_r2.texOffs(193, 203).addBox(-4f, -16.5f, -0.5f, 8, 30, 0, 0f, true)

        front_12_r3 = ModelMapper(modelDataWrapper)
        front_12_r3.setPos(-21f, -10f, 0f)
        side_2_exterior.addChild(front_12_r3)
        setRotationAngle(front_12_r3, 0f, -0.1745f, 0.0873f)
        front_12_r3.texOffs(130, 182).addBox(0f, -27f, -9f, 0, 27, 9, 0f, true)

        front_11_r4 = ModelMapper(modelDataWrapper)
        front_11_r4.setPos(-21f, 0f, 0f)
        side_2_exterior.addChild(front_11_r4)
        setRotationAngle(front_11_r4, 0f, -0.1745f, 0f)
        front_11_r4.texOffs(0, 121).addBox(0f, -10f, -9f, 0, 5, 9, 0f, true)

        front_10_r4 = ModelMapper(modelDataWrapper)
        front_10_r4.setPos(-17.6158f, -7.5f, -9.7886f)
        side_2_exterior.addChild(front_10_r4)
        setRotationAngle(front_10_r4, 0f, 0.7854f, 0f)
        front_10_r4.texOffs(162, 102).addBox(-3f, -2.5f, -0.5f, 6, 5, 0, 0f, true)

        front_9_r4 = ModelMapper(modelDataWrapper)
        front_9_r4.setPos(-15.9306f, 5.3106f, -9.5176f)
        side_2_exterior.addChild(front_9_r4)
        setRotationAngle(front_9_r4, 0.1745f, 0.7854f, 0f)
        front_9_r4.texOffs(0, 0).addBox(-3f, -5.5f, -0.5f, 6, 11, 0, 0f, true)

        front_8_r3 = ModelMapper(modelDataWrapper)
        front_8_r3.setPos(-6f, 0f, -14f)
        side_2_exterior.addChild(front_8_r3)
        setRotationAngle(front_8_r3, 0.2618f, 0.1745f, 0f)
        front_8_r3.texOffs(126, 28).addBox(-11f, 0f, 0f, 11, 11, 0, 0f, true)

        front_7_r2 = ModelMapper(modelDataWrapper)
        front_7_r2.setPos(0f, 0f, -14f)
        side_2_exterior.addChild(front_7_r2)
        setRotationAngle(front_7_r2, 0.2618f, 0f, 0f)
        front_7_r2.texOffs(26, 28).addBox(-6f, 0f, 0f, 6, 11, 0, 0f, true)

        front_6_r2 = ModelMapper(modelDataWrapper)
        front_6_r2.setPos(-21f, -5f, 0f)
        side_2_exterior.addChild(front_6_r2)
        setRotationAngle(front_6_r2, 0f, -0.1745f, -0.1745f)
        front_6_r2.texOffs(182, 42).addBox(0f, 0f, -10f, 0, 16, 10, 0f, true)

        front_5_r3 = ModelMapper(modelDataWrapper)
        front_5_r3.setPos(-17.1096f, -2.5531f, -9.9895f)
        side_2_exterior.addChild(front_5_r3)
        setRotationAngle(front_5_r3, 0.0873f, 0.7854f, 0f)
        front_5_r3.texOffs(160, 150).addBox(-3.5f, -2.5f, -0.5f, 7, 5, 0, 0f, true)

        front_4_r4 = ModelMapper(modelDataWrapper)
        front_4_r4.setPos(-6f, 0f, -14f)
        side_2_exterior.addChild(front_4_r4)
        setRotationAngle(front_4_r4, 0f, 0.1745f, 0f)
        front_4_r4.texOffs(200, 34).addBox(-10f, -10f, 0f, 10, 10, 0, 0f, true)

        wall_top_r6 = ModelMapper(modelDataWrapper)
        wall_top_r6.setPos(-21f, -10f, 0f)
        side_2_exterior.addChild(wall_top_r6)
        setRotationAngle(wall_top_r6, 0f, 0f, 0.0873f)
        wall_top_r6.texOffs(0, 30).addBox(0f, -27f, 0f, 0, 27, 12, 0f, true)

        wall_bottom_r6 = ModelMapper(modelDataWrapper)
        wall_bottom_r6.setPos(-21f, -5f, 0f)
        side_2_exterior.addChild(wall_bottom_r6)
        setRotationAngle(wall_bottom_r6, 0f, 0f, -0.1745f)
        wall_bottom_r6.texOffs(189, 81).addBox(0f, 0f, 0f, 0, 16, 12, 0f, true)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r3 = ModelMapper(modelDataWrapper)
        light_r3.setPos(18.5f, -38f, 0f)
        door_light_on.addChild(light_r3)
        setRotationAngle(light_r3, 0f, 0f, -0.2618f)
        light_r3.texOffs(28, 2).addBox(-1f, -1f, -0.5f, 1, 1, 1, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r4 = ModelMapper(modelDataWrapper)
        light_r4.setPos(18.5f, -38f, 0f)
        door_light_off.addChild(light_r4)
        setRotationAngle(light_r4, 0f, 0f, -0.2618f)
        light_r4.texOffs(28, 0).addBox(-1f, -1f, -0.5f, 1, 1, 1, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_4_r1 = ModelMapper(modelDataWrapper)
        headlight_4_r1.setPos(-6f, 0f, -14f)
        headlights.addChild(headlight_4_r1)
        setRotationAngle(headlight_4_r1, 0.2618f, 0.1745f, 0f)
        headlight_4_r1.texOffs(208, 0).addBox(-11f, 0f, -0.05f, 11, 11, 0, 0f, true)

        headlight_3_r1 = ModelMapper(modelDataWrapper)
        headlight_3_r1.setPos(-15.9306f, 5.3106f, -9.5176f)
        headlights.addChild(headlight_3_r1)
        setRotationAngle(headlight_3_r1, 0.1745f, 0.7854f, 0f)
        headlight_3_r1.texOffs(230, 0).addBox(-3f, -5.5f, -0.55f, 6, 11, 0, 0f, true)

        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(15.9306f, 5.3106f, -9.5176f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, 0.1745f, -0.7854f, 0f)
        headlight_2_r1.texOffs(230, 0).addBox(-3f, -5.5f, -0.55f, 6, 11, 0, 0f, false)

        headlight_1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1.setPos(6f, 0f, -14f)
        headlights.addChild(headlight_1_r1)
        setRotationAngle(headlight_1_r1, 0.2618f, -0.1745f, 0f)
        headlight_1_r1.texOffs(208, 0).addBox(0f, 0f, -0.05f, 11, 11, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        headlight_4_r2 = ModelMapper(modelDataWrapper)
        headlight_4_r2.setPos(-6f, 0f, -14f)
        tail_lights.addChild(headlight_4_r2)
        setRotationAngle(headlight_4_r2, 0.2618f, 0.1745f, 0f)
        headlight_4_r2.texOffs(211, 11).addBox(-11f, 0f, -0.05f, 11, 11, 0, 0f, true)

        headlight_3_r2 = ModelMapper(modelDataWrapper)
        headlight_3_r2.setPos(-15.9306f, 5.3106f, -9.5176f)
        tail_lights.addChild(headlight_3_r2)
        setRotationAngle(headlight_3_r2, 0.1745f, 0.7854f, 0f)
        headlight_3_r2.texOffs(233, 11).addBox(-3f, -5.5f, -0.55f, 6, 11, 0, 0f, true)

        headlight_2_r2 = ModelMapper(modelDataWrapper)
        headlight_2_r2.setPos(15.9306f, 5.3106f, -9.5176f)
        tail_lights.addChild(headlight_2_r2)
        setRotationAngle(headlight_2_r2, 0.1745f, -0.7854f, 0f)
        headlight_2_r2.texOffs(233, 11).addBox(-3f, -5.5f, -0.55f, 6, 11, 0, 0f, false)

        headlight_1_r2 = ModelMapper(modelDataWrapper)
        headlight_1_r2.setPos(6f, 0f, -14f)
        tail_lights.addChild(headlight_1_r2)
        setRotationAngle(headlight_1_r2, 0.2618f, -0.1745f, 0f)
        headlight_1_r2.texOffs(211, 11).addBox(0f, 0f, -0.05f, 11, 11, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        roof_door_exterior_1.setModelPart()
        roof_door_exterior_2.setModelPart()
        roof_door_exterior_3.setModelPart()
        seat_1.setModelPart()
        seat_2.setModelPart()
        seat_3.setModelPart()
        light_window.setModelPart()
        light_door.setModelPart()
        middle_handrail.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelMPL85 {
        return ModelMPL85(doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderMirror(light_window, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> renderMirror(window, matrices, vertices, light, position.toFloat())
            RenderStage.EXTERIOR -> renderMirror(window_exterior, matrices, vertices, light, position.toFloat())
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
        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(light_door, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    if (doorLeftZ > 0) {
                        renderOnce(door_light_on, matrices, vertices, light, position.toFloat())
                    }
                    if (doorRightZ > 0) {
                        renderOnceFlipped(door_light_on, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR -> {
                door_left.setOffset(-doorLeftX, 0, -doorLeftZ)
                door_right.setOffset(-doorLeftX, 0, doorLeftZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_left.setOffset(-doorRightX, 0, -doorRightZ)
                door_right.setOffset(-doorRightX, 0, doorRightZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(seat_1, matrices, vertices, light, (position + 34).toFloat())
                    renderOnceFlipped(seat_2, matrices, vertices, light, (position + 34).toFloat())
                    renderMirror(seat_3, matrices, vertices, light, (position + 48).toFloat())
                    renderOnceFlipped(seat_1, matrices, vertices, light, (position - 34).toFloat())
                    renderOnce(seat_2, matrices, vertices, light, (position - 34).toFloat())
                    renderOnce(middle_handrail, matrices, vertices, light, position.toFloat())
                    if (!isIndex(-1, position, getDoorPositions())) {
                        renderOnce(middle_handrail, matrices, vertices, light, (position + 48).toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                door_left_exterior.setOffset(-doorLeftX, 0, -doorLeftZ)
                door_right_exterior.setOffset(-doorLeftX, 0, doorLeftZ)
                renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                door_left_exterior.setOffset(-doorRightX, 0, -doorRightZ)
                door_right_exterior.setOffset(-doorRightX, 0, doorRightZ)
                renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(
                    if (isIndex(0, position, getDoorPositions())) roof_door_exterior_1 else if (isIndex(
                            1,
                            position,
                            getDoorPositions()
                        )
                    ) roof_door_exterior_2 else roof_door_exterior_3, matrices, vertices, light, position.toFloat()
                )
                if (renderDetails) {
                    if (doorLeftZ == 0f) {
                        renderOnce(door_light_off, matrices, vertices, light, position.toFloat())
                    }
                    if (doorRightZ == 0f) {
                        renderOnceFlipped(door_light_off, matrices, vertices, light, position.toFloat())
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
        renderEndPosition1(matrices, vertices, renderStage, light, position, renderDetails, useHeadlights, true)
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
        renderEndPosition2(matrices, vertices, renderStage, light, position, useHeadlights, true)
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
        renderEndPosition1(matrices, vertices, renderStage, light, position, renderDetails, true, false)
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
        renderEndPosition2(matrices, vertices, renderStage, light, position, true, false)
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
        return intArrayOf(-130, -62, -34, 34, 62, 130)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-96, 0, 96)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-156, 156)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    private fun renderEndPosition1(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        renderDetails: Boolean,
        useHeadlights: Boolean,
        isHead: Boolean
    ) {
        when (renderStage!!) {
            RenderStage.ALWAYS_ON_LIGHTS -> if (isHead) {
                renderOnce(
                    if (useHeadlights) headlights else tail_lights,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
            }

            RenderStage.INTERIOR -> {
                renderOnce(head, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(seat_3, matrices, vertices, light, (position + 12).toFloat())
                }
            }

            RenderStage.EXTERIOR -> renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
            else -> {}
        }
    }

    private fun renderEndPosition2(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        position: Int,
        useHeadlights: Boolean,
        isHead: Boolean
    ) {
        when (renderStage!!) {
            RenderStage.ALWAYS_ON_LIGHTS -> if (isHead) {
                renderOnceFlipped(
                    if (useHeadlights) headlights else tail_lights,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
            }

            RenderStage.INTERIOR -> renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
            RenderStage.EXTERIOR -> renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
            else -> {}
        }
    }

    companion object {
        private const val DOOR_MAX = 16
    }
}
