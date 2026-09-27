package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelSTrain protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelSTrain?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val window_handrails: ModelMapper
    private val top_handrail_5_r1: ModelMapper
    private val top_handrail_4_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val top_handrail_2_r1: ModelMapper
    private val top_handrail_1_r1: ModelMapper
    private val seat_back_r1: ModelMapper
    private val window_exterior_1: ModelMapper
    private val door_leaf_r1: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_exterior_2: ModelMapper
    private val door_leaf_r2: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_5_r2: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_2_r2: ModelMapper
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
    private val pole_bottom_diagonal_3_r1: ModelMapper
    private val pole_bottom_diagonal_2_r1: ModelMapper
    private val pole_bottom_diagonal_1_r1: ModelMapper
    private val pole_middle_3_r1: ModelMapper
    private val pole_middle_2_r1: ModelMapper
    private val pole_middle_1_r1: ModelMapper
    private val pole_top_diagonal_3_r1: ModelMapper
    private val pole_top_diagonal_2_r1: ModelMapper
    private val pole_top_diagonal_1_r1: ModelMapper
    private val door_exterior_1: ModelMapper
    private val door_leaf_r3: ModelMapper
    private val door_left_exterior_1: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_exterior_1: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_exterior_2: ModelMapper
    private val door_leaf_r4: ModelMapper
    private val door_left_exterior_2: ModelMapper
    private val door_left_top_r3: ModelMapper
    private val door_right_exterior_2: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val lower_wall_1_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val door_leaf_2_r1: ModelMapper
    private val door_leaf_1_r1: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val roof_end: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_5_r3: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val inner_roof_2: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_5_r4: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val roof_window_light: ModelMapper
    private val light_2_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val roof_door_light: ModelMapper
    private val light_3_r1: ModelMapper
    private val light_2_r2: ModelMapper
    private val roof_end_light: ModelMapper
    private val light_3_r2: ModelMapper
    private val light_2_r3: ModelMapper
    private val light_3_r3: ModelMapper
    private val light_2_r4: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val lower_wall_1_r2: ModelMapper
    private val ceiling: ModelMapper
    private val panel_9_r1: ModelMapper
    private val panel_8_r1: ModelMapper
    private val panel_7_r1: ModelMapper
    private val panel_6_r1: ModelMapper
    private val panel_4_r1: ModelMapper
    private val panel_3_r1: ModelMapper
    private val panel_2_r1: ModelMapper
    private val panel_1_r1: ModelMapper
    private val main_r1: ModelMapper
    private val emergency_door: ModelMapper
    private val upper_r1: ModelMapper
    private val left_c_panel: ModelMapper
    private val panel_r1: ModelMapper
    private val base_r1: ModelMapper
    private val right_c_panel: ModelMapper
    private val panel_r2: ModelMapper
    private val base_r2: ModelMapper
    private val handrail: ModelMapper
    private val wall: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_1_r1: ModelMapper
    private val ceiling2: ModelMapper
    private val handrail_7_r1: ModelMapper
    private val handrail_4_r2: ModelMapper
    private val handrail_5_r2: ModelMapper
    private val handrail_6_r1: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val ceiling3: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val handrail_5_r3: ModelMapper
    private val handrail_6_r2: ModelMapper
    private val handrail_7_r2: ModelMapper
    private val handrail_4_r3: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val door_leaf_4_r1: ModelMapper
    private val door_leaf_1_r2: ModelMapper
    private val door_leaf_5_r1: ModelMapper
    private val door_leaf_2_r2: ModelMapper
    private val front: ModelMapper
    private val front_panel_4_r1: ModelMapper
    private val front_panel_3_r1: ModelMapper
    private val front_panel_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val front_side_bottom_3_r1: ModelMapper
    private val front_side_bottom_1_r1: ModelMapper
    private val front_side_lower_1_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val side_2: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val front_side_bottom_4_r1: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val roof: ModelMapper
    private val outer_roof_6_r2: ModelMapper
    private val outer_roof_5_r4: ModelMapper
    private val outer_roof_4_r4: ModelMapper
    private val outer_roof_5_r5: ModelMapper
    private val outer_roof_4_r5: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val vent_top_r1: ModelMapper
    private val vent_2_r2: ModelMapper
    private val vent_1_r2: ModelMapper
    private val outer_roof_6_r3: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_7_r2: ModelMapper
    private val outer_roof_8_r1: ModelMapper
    private val outer_roof_5_r6: ModelMapper
    private val outer_roof_6_r4: ModelMapper
    private val outer_roof_6_r5: ModelMapper
    private val outer_roof_7_r3: ModelMapper
    private val headlights: ModelMapper
    private val tail_lights: ModelMapper
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
        window.texOffs(0, 0).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(80, 43).addBox(-18f, -5f, -21f, 0, 5, 42, 0f, false)
        window.texOffs(124, 204).addBox(-20f, -14f, 21f, 3, 14, 6, 0f, false)
        window.texOffs(130, 164).addBox(-20f, -14f, -27f, 3, 14, 6, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(72, 196).addBox(0f, -19f, -27f, 3, 19, 6, 0f, false)
        upper_wall_2_r1.texOffs(245, 205).addBox(0f, -19f, 21f, 3, 19, 6, 0f, false)
        upper_wall_2_r1.texOffs(106, 141).addBox(0f, -19f, -22f, 2, 19, 44, 0f, false)

        window_handrails = ModelMapper(modelDataWrapper)
        window_handrails.setPos(0f, 24f, 0f)
        window_handrails.texOffs(156, 162).addBox(-18f, -6f, -21f, 7, 1, 42, 0f, false)
        window_handrails.texOffs(16, 55).addBox(-18f, -11f, -22f, 7, 6, 1, 0f, false)
        window_handrails.texOffs(16, 55).addBox(-18f, -11f, 21f, 7, 6, 1, 0f, false)
        window_handrails.texOffs(319, 1).addBox(0f, -35.2f, -13f, 0, 35, 0, 0.2f, false)
        window_handrails.texOffs(319, 1).addBox(0f, -35.2f, 13f, 0, 35, 0, 0.2f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, 18f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, 12f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, 6f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, -6f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, -12f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(0, 0).addBox(-8f, -33f, -18f, 2, 4, 0, 0f, false)
        window_handrails.texOffs(319, 0).addBox(-7f, -34.5f, -10f, 0, 2, 0, 0.2f, true)
        window_handrails.texOffs(319, 0).addBox(-7f, -34.5f, 10f, 0, 2, 0, 0.2f, true)
        window_handrails.texOffs(319, 2).addBox(-11f, -34.2f, -22f, 0, 29, 0, 0.2f, false)
        window_handrails.texOffs(319, 0).addBox(-11f, -34.2f, 22f, 0, 29, 0, 0.2f, false)

        top_handrail_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_5_r1.setPos(0f, 0f, 0f)
        window_handrails.addChild(top_handrail_5_r1)
        setRotationAngle(top_handrail_5_r1, 1.5708f, 0f, 0f)
        top_handrail_5_r1.texOffs(319, 22).addBox(-7f, -20f, 32.5f, 0, 40, 0, 0.2f, false)

        top_handrail_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_4_r1.setPos(-7f, -33.7062f, 21.5892f)
        window_handrails.addChild(top_handrail_4_r1)
        setRotationAngle(top_handrail_4_r1, -0.5236f, 0f, 0f)
        top_handrail_4_r1.texOffs(319, 0).addBox(0f, -1.5f, 0f, 0, 2, 0, 0.2f, true)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-6.8f, -32.3f, 20.2f)
        window_handrails.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.0472f, 0f, 0f)
        top_handrail_3_r1.texOffs(319, 0).addBox(-0.2f, -1.2f, -0.2f, 0, 1, 0, 0.2f, true)

        top_handrail_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_2_r1.setPos(-6.8f, -32.3f, -20.2f)
        window_handrails.addChild(top_handrail_2_r1)
        setRotationAngle(top_handrail_2_r1, 1.0472f, 0f, 0f)
        top_handrail_2_r1.texOffs(319, 0).addBox(-0.2f, -1.2f, 0.2f, 0, 1, 0, 0.2f, true)

        top_handrail_1_r1 = ModelMapper(modelDataWrapper)
        top_handrail_1_r1.setPos(-7f, -34.1392f, -21.8392f)
        window_handrails.addChild(top_handrail_1_r1)
        setRotationAngle(top_handrail_1_r1, 0.5236f, 0f, 0f)
        top_handrail_1_r1.texOffs(319, 0).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, true)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        window_handrails.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(146, 85).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)
        window_exterior_1.texOffs(0, 69).addBox(-20f, -14f, -26f, 0, 18, 52, 0f, false)

        door_leaf_r1 = ModelMapper(modelDataWrapper)
        door_leaf_r1.setPos(-21f, -14f, 0f)
        window_exterior_1.addChild(door_leaf_r1)
        setRotationAngle(door_leaf_r1, 0f, 0f, 0.1107f)
        door_leaf_r1.texOffs(0, 139).addBox(0f, -23f, -26f, 1, 5, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window_exterior_1.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(0, 47).addBox(0f, -22f, -26f, 0, 22, 52, 0f, false)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)
        window_exterior_2.texOffs(0, 69).addBox(20f, -14f, -26f, 0, 18, 52, 0f, true)

        door_leaf_r2 = ModelMapper(modelDataWrapper)
        door_leaf_r2.setPos(21f, -14f, 0f)
        window_exterior_2.addChild(door_leaf_r2)
        setRotationAngle(door_leaf_r2, 0f, 0f, -0.1107f)
        door_leaf_r2.texOffs(0, 139).addBox(-1f, -23f, -26f, 1, 5, 52, 0f, true)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(20f, -14f, 0f)
        window_exterior_2.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, -0.1107f)
        upper_wall_r2.texOffs(0, 47).addBox(0f, -22f, -26f, 0, 22, 52, 0f, true)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 24f, 0f)
        side_panel_translucent.texOffs(152, 90).addBox(-18f, -25f, 0f, 7, 15, 0, 0f, false)

        roof_window = ModelMapper(modelDataWrapper)
        roof_window.setPos(0f, 24f, 0f)
        roof_window.texOffs(60, 0).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window.texOffs(55, 0).addBox(-11.0724f, -34.2978f, -24f, 1, 0, 48, 0f, false)
        roof_window.texOffs(40, 0).addBox(-4f, -34.5f, -24f, 4, 0, 48, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-3f, -34.5f, 0f)
        roof_window.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -0.0873f)
        inner_roof_5_r1.texOffs(54, 0).addBox(-3f, 0f, -24f, 3, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-6.9734f, -34.0649f, 0f)
        roof_window.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.1745f)
        inner_roof_4_r1.texOffs(66, 0).addBox(-1f, 0f, -24f, 2, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13f, -32f, 0f)
        roof_window.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.8727f)
        inner_roof_2_r1.texOffs(60, 0).addBox(0f, 0f, -24f, 3, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(204, 0).addBox(-18f, -33f, -16f, 5, 1, 32, 0f, false)
        roof_door.texOffs(80, 16).addBox(-11.0724f, -34.2978f, -16f, 1, 0, 32, 0f, false)
        roof_door.texOffs(56, 0).addBox(-4f, -34.5f, -16f, 4, 0, 32, 0f, false)

        inner_roof_5_r2 = ModelMapper(modelDataWrapper)
        inner_roof_5_r2.setPos(-3f, -34.5f, 0f)
        roof_door.addChild(inner_roof_5_r2)
        setRotationAngle(inner_roof_5_r2, 0f, 0f, -0.0873f)
        inner_roof_5_r2.texOffs(70, 0).addBox(-3f, 0f, -16f, 3, 0, 32, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-6.9733f, -34.0649f, 0f)
        roof_door.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.1745f)
        inner_roof_4_r2.texOffs(82, 0).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13f, -32f, 0f)
        roof_door.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.8727f)
        inner_roof_2_r2.texOffs(114, 90).addBox(0f, 0f, -16f, 3, 0, 32, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(64, 99).addBox(-6f, -41f, -20f, 6, 0, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-6f, -41f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, -0.1745f)
        outer_roof_5_r1.texOffs(98, 0).addBox(-8f, 0f, -20f, 8, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.6102f, -38.6109f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.5236f)
        outer_roof_4_r1.texOffs(0, 0).addBox(-2f, 0f, -20f, 4, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-17.8419f, -36.7453f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -1.0472f)
        outer_roof_3_r1.texOffs(0, 49).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(178, 50).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(94, 265).addBox(-21f, -14f, 0f, 1, 14, 14, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-21f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(192, 83).addBox(0f, -19f, 0f, 1, 19, 14, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(204, 0).addBox(-21f, -14f, -14f, 1, 14, 14, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-21f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(0, 49).addBox(0f, -19f, -14f, 1, 19, 14, 0f, false)

        door_handrail = ModelMapper(modelDataWrapper)
        door_handrail.setPos(0f, 24f, 0f)
        door_handrail.texOffs(319, 0).addBox(0f, -34.6f, 0f, 0, 5, 0, 0.2f, false)
        door_handrail.texOffs(319, 24).addBox(0f, -11.2f, 0f, 0, 11, 0, 0.2f, false)

        pole_bottom_diagonal_3_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_3_r1.setPos(-0.1712f, -12.5316f, 0.1712f)
        door_handrail.addChild(pole_bottom_diagonal_3_r1)
        setRotationAngle(pole_bottom_diagonal_3_r1, -0.096f, -0.7854f, 0f)
        pole_bottom_diagonal_3_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_2_r1.setPos(0.2339f, -12.5316f, 0.0627f)
        door_handrail.addChild(pole_bottom_diagonal_2_r1)
        setRotationAngle(pole_bottom_diagonal_2_r1, -0.096f, 1.309f, 0f)
        pole_bottom_diagonal_2_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_1_r1.setPos(-0.0627f, -12.5316f, -0.2339f)
        door_handrail.addChild(pole_bottom_diagonal_1_r1)
        setRotationAngle(pole_bottom_diagonal_1_r1, -0.096f, -2.8798f, 0f)
        pole_bottom_diagonal_1_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_middle_3_r1 = ModelMapper(modelDataWrapper)
        pole_middle_3_r1.setPos(0f, 0f, 0f)
        door_handrail.addChild(pole_middle_3_r1)
        setRotationAngle(pole_middle_3_r1, 0f, 1.309f, 0f)
        pole_middle_3_r1.texOffs(319, 11).addBox(0f, -25.4f, 0.5f, 0, 10, 0, 0.2f, false)

        pole_middle_2_r1 = ModelMapper(modelDataWrapper)
        pole_middle_2_r1.setPos(0f, 0f, 0f)
        door_handrail.addChild(pole_middle_2_r1)
        setRotationAngle(pole_middle_2_r1, 0f, -2.8798f, 0f)
        pole_middle_2_r1.texOffs(319, 11).addBox(0f, -25.4f, 0.5f, 0, 10, 0, 0.2f, false)

        pole_middle_1_r1 = ModelMapper(modelDataWrapper)
        pole_middle_1_r1.setPos(0f, 0f, 0f)
        door_handrail.addChild(pole_middle_1_r1)
        setRotationAngle(pole_middle_1_r1, 0f, -0.7854f, 0f)
        pole_middle_1_r1.texOffs(319, 11).addBox(0f, -25.4f, 0.5f, 0, 10, 0, 0.2f, false)

        pole_top_diagonal_3_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_3_r1.setPos(-0.1712f, -28.2684f, 0.1712f)
        door_handrail.addChild(pole_top_diagonal_3_r1)
        setRotationAngle(pole_top_diagonal_3_r1, 0.096f, -0.7854f, 0f)
        pole_top_diagonal_3_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_2_r1.setPos(0.2339f, -28.2684f, 0.0627f)
        door_handrail.addChild(pole_top_diagonal_2_r1)
        setRotationAngle(pole_top_diagonal_2_r1, 0.096f, 1.309f, 0f)
        pole_top_diagonal_2_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_1_r1.setPos(-0.0627f, -28.2684f, -0.2339f)
        door_handrail.addChild(pole_top_diagonal_1_r1)
        setRotationAngle(pole_top_diagonal_1_r1, 0.096f, -2.8798f, 0f)
        pole_top_diagonal_1_r1.texOffs(319, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        door_exterior_1 = ModelMapper(modelDataWrapper)
        door_exterior_1.setPos(0f, 24f, 0f)
        door_exterior_1.texOffs(100, 18).addBox(-21f, 0f, -18f, 1, 4, 36, 0f, false)

        door_leaf_r3 = ModelMapper(modelDataWrapper)
        door_leaf_r3.setPos(-21f, -14f, 0f)
        door_exterior_1.addChild(door_leaf_r3)
        setRotationAngle(door_leaf_r3, 0f, 0f, 0.1107f)
        door_leaf_r3.texOffs(0, 243).addBox(0f, -23f, -14f, 1, 5, 28, 0f, false)

        door_left_exterior_1 = ModelMapper(modelDataWrapper)
        door_left_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_left_exterior_1)
        door_left_exterior_1.texOffs(159, 191).addBox(-21f, -14f, 0f, 0, 14, 14, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-21f, -14f, 0f)
        door_left_exterior_1.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1107f)
        door_left_top_r2.texOffs(0, 182).addBox(0f, -18f, 0f, 0, 18, 14, 0f, false)

        door_right_exterior_1 = ModelMapper(modelDataWrapper)
        door_right_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_right_exterior_1)
        door_right_exterior_1.texOffs(96, 190).addBox(-21f, -14f, -14f, 0, 14, 14, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-21f, -14f, 0f)
        door_right_exterior_1.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1107f)
        door_right_top_r2.texOffs(178, 44).addBox(0f, -18f, -14f, 0, 18, 14, 0f, false)

        door_exterior_2 = ModelMapper(modelDataWrapper)
        door_exterior_2.setPos(0f, 24f, 0f)
        door_exterior_2.texOffs(100, 18).addBox(20f, 0f, -18f, 1, 4, 36, 0f, true)

        door_leaf_r4 = ModelMapper(modelDataWrapper)
        door_leaf_r4.setPos(21f, -14f, 0f)
        door_exterior_2.addChild(door_leaf_r4)
        setRotationAngle(door_leaf_r4, 0f, 0f, -0.1107f)
        door_leaf_r4.texOffs(0, 243).addBox(-1f, -23f, -14f, 1, 5, 28, 0f, true)

        door_left_exterior_2 = ModelMapper(modelDataWrapper)
        door_left_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_left_exterior_2)
        door_left_exterior_2.texOffs(159, 191).addBox(21f, -14f, 0f, 0, 14, 14, 0f, true)

        door_left_top_r3 = ModelMapper(modelDataWrapper)
        door_left_top_r3.setPos(21f, -14f, 0f)
        door_left_exterior_2.addChild(door_left_top_r3)
        setRotationAngle(door_left_top_r3, 0f, 0f, -0.1107f)
        door_left_top_r3.texOffs(0, 182).addBox(0f, -18f, 0f, 0, 18, 14, 0f, true)

        door_right_exterior_2 = ModelMapper(modelDataWrapper)
        door_right_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_right_exterior_2)
        door_right_exterior_2.texOffs(96, 190).addBox(21f, -14f, -14f, 0, 14, 14, 0f, true)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(21f, -14f, 0f)
        door_right_exterior_2.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, -0.1107f)
        door_right_top_r3.texOffs(178, 44).addBox(0f, -18f, -14f, 0, 18, 14, 0f, true)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(154, 141).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(28, 139).addBox(-20f, -14f, 5f, 3, 14, 6, 0f, false)
        end.texOffs(178, 205).addBox(9.5f, -35f, -12f, 8, 35, 19, 0f, false)
        end.texOffs(124, 205).addBox(-17.5f, -35f, -12f, 8, 35, 19, 0f, false)
        end.texOffs(217, 118).addBox(-9.5f, -35f, -12f, 19, 3, 19, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(54, 139).addBox(0f, -19f, 6f, 3, 19, 5, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 3.1416f, -0.1107f)
        upper_wall_1_r1.texOffs(236, 83).addBox(0f, -19f, -11f, 3, 19, 5, 0f, false)

        lower_wall_1_r1 = ModelMapper(modelDataWrapper)
        lower_wall_1_r1.setPos(0f, 0f, 0f)
        end.addChild(lower_wall_1_r1)
        setRotationAngle(lower_wall_1_r1, 0f, 3.1416f, 0f)
        lower_wall_1_r1.texOffs(154, 141).addBox(-20f, -14f, -11f, 3, 14, 6, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(156, 259).addBox(17f, -14f, -12f, 3, 18, 22, 0f, true)
        end_exterior.texOffs(156, 259).addBox(-20f, -14f, -12f, 3, 18, 22, 0f, false)
        end_exterior.texOffs(168, 85).addBox(9.5f, -34f, -12f, 10, 34, 0, 0f, false)
        end_exterior.texOffs(168, 85).addBox(-19.5f, -34f, -12f, 10, 34, 0, 0f, true)
        end_exterior.texOffs(246, 11).addBox(-18f, -41f, -12f, 36, 7, 0, 0f, false)

        door_leaf_2_r1 = ModelMapper(modelDataWrapper)
        door_leaf_2_r1.setPos(21f, -14f, 0f)
        end_exterior.addChild(door_leaf_2_r1)
        setRotationAngle(door_leaf_2_r1, 0f, 3.1416f, -0.1107f)
        door_leaf_2_r1.texOffs(236, 83).addBox(0f, -23f, -16f, 1, 5, 28, 0f, false)

        door_leaf_1_r1 = ModelMapper(modelDataWrapper)
        door_leaf_1_r1.setPos(-21f, -14f, 0f)
        end_exterior.addChild(door_leaf_1_r1)
        setRotationAngle(door_leaf_1_r1, 0f, 0f, 0.1107f)
        door_leaf_1_r1.texOffs(238, 204).addBox(0f, -23f, -12f, 1, 5, 28, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        end_exterior.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(0, 139).addBox(0f, -22f, -12f, 3, 22, 22, 0f, false)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        end_exterior.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(0, 139).addBox(-3f, -22f, -12f, 3, 22, 22, 0f, true)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        inner_roof_1 = ModelMapper(modelDataWrapper)
        inner_roof_1.setPos(-2f, -33f, 38f)
        roof_end.addChild(inner_roof_1)
        inner_roof_1.texOffs(225, 116).addBox(-16f, 0f, -31f, 5, 1, 1, 0f, false)
        inner_roof_1.texOffs(79, 82).addBox(-9.0724f, -1.2978f, -31f, 1, 0, 1, 0f, false)
        inner_roof_1.texOffs(115, 99).addBox(-2f, -1.5f, -31f, 4, 0, 1, 0f, false)

        inner_roof_5_r3 = ModelMapper(modelDataWrapper)
        inner_roof_5_r3.setPos(-1f, -1.5f, -38f)
        inner_roof_1.addChild(inner_roof_5_r3)
        setRotationAngle(inner_roof_5_r3, 0f, 0f, -0.0873f)
        inner_roof_5_r3.texOffs(33, 0).addBox(-3f, 0f, 7f, 3, 0, 1, 0f, false)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(-4.9733f, -1.0649f, -38f)
        inner_roof_1.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, -0.1745f)
        inner_roof_4_r3.texOffs(123, 99).addBox(-1f, 0f, 7f, 2, 0, 1, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-11f, 1f, -38f)
        inner_roof_1.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.8727f)
        inner_roof_2_r3.texOffs(33, 49).addBox(0f, 0f, 7f, 3, 0, 1, 0f, false)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(2f, -33f, 38f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(225, 116).addBox(11f, 0f, -31f, 5, 1, 1, 0f, true)
        inner_roof_2.texOffs(79, 82).addBox(8.0724f, -1.2978f, -31f, 1, 0, 1, 0f, true)
        inner_roof_2.texOffs(115, 99).addBox(-2f, -1.5f, -31f, 4, 0, 1, 0f, true)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(1f, -1.5f, -38f)
        inner_roof_2.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, 0.0873f)
        inner_roof_6_r1.texOffs(33, 0).addBox(0f, 0f, 7f, 3, 0, 1, 0f, true)

        inner_roof_5_r4 = ModelMapper(modelDataWrapper)
        inner_roof_5_r4.setPos(4.9733f, -1.0649f, -38f)
        inner_roof_2.addChild(inner_roof_5_r4)
        setRotationAngle(inner_roof_5_r4, 0f, 0f, 0.1745f)
        inner_roof_5_r4.texOffs(123, 99).addBox(-1f, 0f, 7f, 2, 0, 1, 0f, true)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(11f, 1f, -38f)
        inner_roof_2.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, 0.8727f)
        inner_roof_3_r1.texOffs(33, 49).addBox(-3f, 0f, 7f, 3, 0, 1, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(0, 49).addBox(-8f, -42f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -42f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(80, 91).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -42f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(138, 0).addBox(0f, 0f, 0f, 9, 2, 48, 0f, false)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)
        outer_roof_1.texOffs(247, 162).addBox(-6f, -41f, -12f, 6, 1, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-6f, -41f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, -0.1745f)
        outer_roof_5_r2.texOffs(87, 243).addBox(-8f, 0f, -12f, 8, 1, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-15.3605f, -38.1778f, -2f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, -0.5236f)
        outer_roof_4_r2.texOffs(31, 243).addBox(-2f, -0.5f, -10f, 4, 1, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-17.4096f, -36.4948f, -2f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -1.0472f)
        outer_roof_3_r2.texOffs(246, 253).addBox(-1f, -0.5f, -10f, 2, 1, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)
        outer_roof_2.texOffs(247, 162).addBox(0f, -41f, -12f, 6, 1, 20, 0f, true)

        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(6f, -41f, 0f)
        outer_roof_2.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, 0.1745f)
        outer_roof_6_r1.texOffs(87, 243).addBox(0f, 0f, -12f, 8, 1, 20, 0f, true)

        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(15.3605f, -38.1778f, -2f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, 0.5236f)
        outer_roof_5_r3.texOffs(31, 243).addBox(-2f, -0.5f, -10f, 4, 1, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(17.4096f, -36.4948f, -2f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, 1.0472f)
        outer_roof_4_r3.texOffs(246, 253).addBox(-1f, -0.5f, -10f, 2, 1, 20, 0f, true)

        roof_window_light = ModelMapper(modelDataWrapper)
        roof_window_light.setPos(0f, 24f, 0f)


        light_2_r1 = ModelMapper(modelDataWrapper)
        light_2_r1.setPos(-10.1225f, -34.1864f, 0f)
        roof_window_light.addChild(light_2_r1)
        setRotationAngle(light_2_r1, 0f, 0f, 1.2217f)
        light_2_r1.texOffs(80, 0).addBox(-0.5f, 0f, -24f, 1, 0, 48, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-8.9544f, -33.8041f, 0f)
        roof_window_light.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, -0.0873f)
        light_1_r1.texOffs(74, 0).addBox(-1f, 0f, -24f, 2, 0, 48, 0f, false)

        roof_door_light = ModelMapper(modelDataWrapper)
        roof_door_light.setPos(0f, 24f, 0f)


        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(-10.1225f, -34.1864f, 0f)
        roof_door_light.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 0f, 1.2217f)
        light_3_r1.texOffs(96, 8).addBox(-0.5f, 0f, -16f, 1, 0, 32, 0f, false)

        light_2_r2 = ModelMapper(modelDataWrapper)
        light_2_r2.setPos(-8.9544f, -33.8041f, 0f)
        roof_door_light.addChild(light_2_r2)
        setRotationAngle(light_2_r2, 0f, 0f, -0.0873f)
        light_2_r2.texOffs(90, 8).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        light_3_r2 = ModelMapper(modelDataWrapper)
        light_3_r2.setPos(10.1225f, -34.1864f, 0f)
        roof_end_light.addChild(light_3_r2)
        setRotationAngle(light_3_r2, 0f, 0f, -1.2217f)
        light_3_r2.texOffs(127, 35).addBox(-0.5f, 0f, 7f, 1, 0, 1, 0f, true)

        light_2_r3 = ModelMapper(modelDataWrapper)
        light_2_r3.setPos(8.9544f, -33.8041f, 0f)
        roof_end_light.addChild(light_2_r3)
        setRotationAngle(light_2_r3, 0f, 0f, 0.0873f)
        light_2_r3.texOffs(121, 35).addBox(-1f, 0f, 7f, 2, 0, 1, 0f, true)

        light_3_r3 = ModelMapper(modelDataWrapper)
        light_3_r3.setPos(-10.1225f, -34.1864f, 0f)
        roof_end_light.addChild(light_3_r3)
        setRotationAngle(light_3_r3, 0f, 0f, 1.2217f)
        light_3_r3.texOffs(127, 35).addBox(-0.5f, 0f, 7f, 1, 0, 1, 0f, false)

        light_2_r4 = ModelMapper(modelDataWrapper)
        light_2_r4.setPos(-8.9544f, -33.8041f, 0f)
        roof_end_light.addChild(light_2_r4)
        setRotationAngle(light_2_r4, 0f, 0f, -0.0873f)
        light_2_r4.texOffs(121, 35).addBox(-1f, 0f, 7f, 2, 0, 1, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(80, 58).addBox(-18f, 0f, -18f, 36, 1, 26, 0f, false)
        head.texOffs(212, 162).addBox(-20f, -14f, -17f, 3, 14, 28, 0f, false)
        head.texOffs(266, 83).addBox(-17f, -14f, -2f, 4, 14, 13, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(0, 196).addBox(0f, -19f, -17f, 3, 19, 28, 0f, false)
        upper_wall_2_r4.texOffs(0, 0).addBox(3f, -21f, -2f, 4, 21, 13, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 3.1416f, -0.1107f)
        upper_wall_1_r3.texOffs(62, 196).addBox(0f, -19f, -11f, 3, 19, 28, 0f, false)

        lower_wall_1_r2 = ModelMapper(modelDataWrapper)
        lower_wall_1_r2.setPos(0f, 0f, 0f)
        head.addChild(lower_wall_1_r2)
        setRotationAngle(lower_wall_1_r2, 0f, 3.1416f, 0f)
        lower_wall_1_r2.texOffs(204, 231).addBox(-20f, -14f, -11f, 3, 14, 28, 0f, false)

        ceiling = ModelMapper(modelDataWrapper)
        ceiling.setPos(0f, 0f, 0f)
        head.addChild(ceiling)
        ceiling.texOffs(0, 183).addBox(-15.165f, -34.3f, -15f, 8, 4, 4, 0f, true)
        ceiling.texOffs(208, 273).addBox(-7.5f, -32f, -17f, 2, 32, 2, 0f, true)
        ceiling.texOffs(208, 273).addBox(5.5f, -32f, -17f, 2, 32, 2, 0f, true)

        panel_9_r1 = ModelMapper(modelDataWrapper)
        panel_9_r1.setPos(7.165f, -30.3f, -11f)
        ceiling.addChild(panel_9_r1)
        setRotationAngle(panel_9_r1, 0f, 0f, 0.6109f)
        panel_9_r1.texOffs(0, 49).addBox(-2f, -4f, -4f, 2, 4, 4, 0f, true)

        panel_8_r1 = ModelMapper(modelDataWrapper)
        panel_8_r1.setPos(22.165f, 0.7f, 0f)
        ceiling.addChild(panel_8_r1)
        setRotationAngle(panel_8_r1, 0f, 0f, 0f)
        panel_8_r1.texOffs(0, 183).addBox(-15f, -35f, -15f, 8, 4, 4, 0f, true)

        panel_7_r1 = ModelMapper(modelDataWrapper)
        panel_7_r1.setPos(0f, 0.7f, 2f)
        ceiling.addChild(panel_7_r1)
        setRotationAngle(panel_7_r1, 0f, 0f, 0f)
        panel_7_r1.texOffs(80, 90).addBox(-7f, -34f, -17f, 14, 2, 4, 0f, true)

        panel_6_r1 = ModelMapper(modelDataWrapper)
        panel_6_r1.setPos(-7.165f, -30.3f, 0f)
        ceiling.addChild(panel_6_r1)
        setRotationAngle(panel_6_r1, 0f, 0f, -0.6109f)
        panel_6_r1.texOffs(0, 49).addBox(0f, -4f, -15f, 2, 4, 4, 0f, true)

        panel_4_r1 = ModelMapper(modelDataWrapper)
        panel_4_r1.setPos(-13.5f, -31f, 0f)
        ceiling.addChild(panel_4_r1)
        setRotationAngle(panel_4_r1, 0f, 0f, -0.7418f)
        panel_4_r1.texOffs(0, 214).addBox(0f, -1f, -11f, 3, 1, 9, 0f, false)

        panel_3_r1 = ModelMapper(modelDataWrapper)
        panel_3_r1.setPos(-1.0805f, 1.0256f, 2f)
        ceiling.addChild(panel_3_r1)
        setRotationAngle(panel_3_r1, 0f, 0f, 0f)
        panel_3_r1.texOffs(250, 42).addBox(-14.4195f, -33.0256f, -13f, 2, 1, 9, 0f, false)

        panel_2_r1 = ModelMapper(modelDataWrapper)
        panel_2_r1.setPos(13.5f, -31f, 0f)
        ceiling.addChild(panel_2_r1)
        setRotationAngle(panel_2_r1, 0f, 0f, 0.7418f)
        panel_2_r1.texOffs(250, 42).addBox(-3f, -1f, -11f, 3, 1, 22, 0f, false)

        panel_1_r1 = ModelMapper(modelDataWrapper)
        panel_1_r1.setPos(0.1555f, 1.0256f, -2f)
        ceiling.addChild(panel_1_r1)
        setRotationAngle(panel_1_r1, 0f, 0f, 0f)
        panel_1_r1.texOffs(138, 18).addBox(13.3445f, -33.0256f, -9f, 2, 1, 22, 0f, false)

        main_r1 = ModelMapper(modelDataWrapper)
        main_r1.setPos(0f, 1f, -2f)
        ceiling.addChild(main_r1)
        setRotationAngle(main_r1, 0f, 0f, 0f)
        main_r1.texOffs(54, 141).addBox(-12f, -35f, -9f, 24, 1, 22, 0f, true)

        emergency_door = ModelMapper(modelDataWrapper)
        emergency_door.setPos(0f, 0f, 0f)
        head.addChild(emergency_door)
        emergency_door.texOffs(238, 246).addBox(-5.5f, -11f, -18f, 11, 11, 1, 0f, false)

        upper_r1 = ModelMapper(modelDataWrapper)
        upper_r1.setPos(0f, -11f, -17f)
        emergency_door.addChild(upper_r1)
        setRotationAngle(upper_r1, -0.0873f, 0f, 0f)
        upper_r1.texOffs(212, 162).addBox(-5.5f, -22f, -1f, 11, 22, 1, 0f, false)

        left_c_panel = ModelMapper(modelDataWrapper)
        left_c_panel.setPos(0f, 0f, 0f)
        head.addChild(left_c_panel)


        panel_r1 = ModelMapper(modelDataWrapper)
        panel_r1.setPos(0f, 0f, 0f)
        left_c_panel.addChild(panel_r1)
        setRotationAngle(panel_r1, -2.5744f, 0f, 3.1416f)
        panel_r1.texOffs(246, 18).addBox(-17f, -4.15f, 15.65f, 10, 3, 6, 0f, false)

        base_r1 = ModelMapper(modelDataWrapper)
        base_r1.setPos(0f, 0f, 0f)
        left_c_panel.addChild(base_r1)
        setRotationAngle(base_r1, 0f, 3.1416f, 0f)
        base_r1.texOffs(146, 259).addBox(-17f, -12f, 11f, 10, 12, 6, 0f, false)

        right_c_panel = ModelMapper(modelDataWrapper)
        right_c_panel.setPos(0f, 0f, 0f)
        head.addChild(right_c_panel)


        panel_r2 = ModelMapper(modelDataWrapper)
        panel_r2.setPos(0f, 0f, 0f)
        right_c_panel.addChild(panel_r2)
        setRotationAngle(panel_r2, -2.5744f, 0f, -3.1416f)
        panel_r2.texOffs(192, 118).addBox(7f, -4.15f, 15.65f, 10, 3, 6, 0f, false)

        base_r2 = ModelMapper(modelDataWrapper)
        base_r2.setPos(0f, 0f, 0f)
        right_c_panel.addChild(base_r2)
        setRotationAngle(base_r2, 0f, -3.1416f, 0f)
        base_r2.texOffs(213, 205).addBox(7f, -12f, 11f, 10, 12, 6, 0f, false)

        handrail = ModelMapper(modelDataWrapper)
        handrail.setPos(0f, 0f, 0f)
        head.addChild(handrail)


        wall = ModelMapper(modelDataWrapper)
        wall.setPos(0f, 0f, 2f)
        handrail.addChild(wall)


        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(14.8f, -11.2f, -7.2f)
        wall.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 1.5708f, -0.4363f, 0f)
        handrail_4_r1.texOffs(319, 0).addBox(0.2f, -1.2f, -0.2f, 0, 1, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(16.5638f, -11f, 2.7947f)
        wall.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, -1.5708f, 1.1345f, 0f)
        handrail_3_r1.texOffs(319, 0).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(14.8f, -11.2f, 1.2f)
        wall.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0.4363f, 0f)
        handrail_2_r1.texOffs(319, 0).addBox(0.2f, -1.2f, 0.2f, 0, 1, 0, 0.2f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(16.5638f, -11f, -8.7947f)
        wall.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 1.5708f, -1.1345f, 0f)
        handrail_5_r1.texOffs(319, 0).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_1_r1 = ModelMapper(modelDataWrapper)
        handrail_1_r1.setPos(0f, 0f, -2f)
        wall.addChild(handrail_1_r1)
        setRotationAngle(handrail_1_r1, -1.5708f, 0f, 0f)
        handrail_1_r1.texOffs(319, 11).addBox(15f, -3f, -11f, 0, 8, 0, 0.2f, false)

        ceiling2 = ModelMapper(modelDataWrapper)
        ceiling2.setPos(1f, 0f, 2f)
        handrail.addChild(ceiling2)
        ceiling2.texOffs(0, 0).addBox(-9f, -31.5f, 0f, 2, 4, 0, 0f, false)
        ceiling2.texOffs(0, 0).addBox(-9f, -31.5f, -6f, 2, 4, 0, 0f, false)

        handrail_7_r1 = ModelMapper(modelDataWrapper)
        handrail_7_r1.setPos(-19f, -16.0275f, -4.0905f)
        ceiling2.addChild(handrail_7_r1)
        setRotationAngle(handrail_7_r1, 1.5708f, 1.1345f, 1.5708f)
        handrail_7_r1.texOffs(319, 0).addBox(-2.725f, -17.975f, 11f, 0, 2, 0, 0.2f, false)

        handrail_4_r2 = ModelMapper(modelDataWrapper)
        handrail_4_r2.setPos(-7.8f, -30.8f, 1.2f)
        ceiling2.addChild(handrail_4_r2)
        setRotationAngle(handrail_4_r2, -1.5708f, -0.4363f, 1.5708f)
        handrail_4_r2.texOffs(319, 0).addBox(-0.2f, -1.2f, 0.2f, 0, 1, 0, 0.2f, false)

        handrail_5_r2 = ModelMapper(modelDataWrapper)
        handrail_5_r2.setPos(-19f, -16.0275f, -1.9095f)
        ceiling2.addChild(handrail_5_r2)
        setRotationAngle(handrail_5_r2, -1.5708f, -1.1345f, 1.5708f)
        handrail_5_r2.texOffs(319, 0).addBox(-2.725f, -17.975f, -11f, 0, 2, 0, 0.2f, false)

        handrail_6_r1 = ModelMapper(modelDataWrapper)
        handrail_6_r1.setPos(-7.8f, -30.8f, -7.2f)
        ceiling2.addChild(handrail_6_r1)
        setRotationAngle(handrail_6_r1, 1.5708f, 0.4363f, 1.5708f)
        handrail_6_r1.texOffs(319, 0).addBox(-0.2f, -1.2f, -0.2f, 0, 1, 0, 0.2f, false)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(-1f, 0f, -2f)
        ceiling2.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, -1.5708f, 0f, 0f)
        handrail_3_r2.texOffs(319, 0).addBox(-7f, -3f, -31f, 0, 8, 0, 0.2f, false)

        ceiling3 = ModelMapper(modelDataWrapper)
        ceiling3.setPos(-1f, 0f, 2f)
        handrail.addChild(ceiling3)
        ceiling3.texOffs(0, 0).addBox(7f, -31.5f, 0f, 2, 4, 0, 0f, true)
        ceiling3.texOffs(0, 0).addBox(7f, -31.5f, -6f, 2, 4, 0, 0f, true)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(19f, -16.0275f, -4.0905f)
        ceiling3.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, 1.5708f, -1.1345f, -1.5708f)
        handrail_8_r1.texOffs(319, 0).addBox(2.725f, -17.975f, 11f, 0, 2, 0, 0.2f, true)

        handrail_5_r3 = ModelMapper(modelDataWrapper)
        handrail_5_r3.setPos(7.8f, -30.8f, 1.2f)
        ceiling3.addChild(handrail_5_r3)
        setRotationAngle(handrail_5_r3, -1.5708f, 0.4363f, -1.5708f)
        handrail_5_r3.texOffs(319, 0).addBox(0.2f, -1.2f, 0.2f, 0, 1, 0, 0.2f, true)

        handrail_6_r2 = ModelMapper(modelDataWrapper)
        handrail_6_r2.setPos(19f, -16.0275f, -1.9095f)
        ceiling3.addChild(handrail_6_r2)
        setRotationAngle(handrail_6_r2, -1.5708f, 1.1345f, -1.5708f)
        handrail_6_r2.texOffs(319, 0).addBox(2.725f, -17.975f, -11f, 0, 2, 0, 0.2f, true)

        handrail_7_r2 = ModelMapper(modelDataWrapper)
        handrail_7_r2.setPos(7.8f, -30.8f, -7.2f)
        ceiling3.addChild(handrail_7_r2)
        setRotationAngle(handrail_7_r2, 1.5708f, -0.4363f, -1.5708f)
        handrail_7_r2.texOffs(319, 0).addBox(0.2f, -1.2f, -0.2f, 0, 1, 0, 0.2f, true)

        handrail_4_r3 = ModelMapper(modelDataWrapper)
        handrail_4_r3.setPos(1f, 0f, -2f)
        ceiling3.addChild(handrail_4_r3)
        setRotationAngle(handrail_4_r3, -1.5708f, 0f, 0f)
        handrail_4_r3.texOffs(319, 0).addBox(7f, -3f, -31f, 0, 8, 0, 0.2f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(0, 14).addBox(20f, -14f, -10f, 0, 14, 20, 0f, false)
        head_exterior.texOffs(0, 14).addBox(-20f, -14f, -10f, 0, 14, 20, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(-20f, -14f, 0f)
        head_exterior.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, 0.1107f)
        upper_wall_2_r5.texOffs(154, 142).addBox(0f, -22f, -10f, 0, 22, 20, 0f, false)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(20f, -14f, 0f)
        head_exterior.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, -0.1107f)
        upper_wall_1_r4.texOffs(154, 142).addBox(0f, -22f, -10f, 0, 22, 20, 0f, false)

        door_leaf_4_r1 = ModelMapper(modelDataWrapper)
        door_leaf_4_r1.setPos(21f, -14f, -10f)
        head_exterior.addChild(door_leaf_4_r1)
        setRotationAngle(door_leaf_4_r1, 0f, 0.3316f, -0.1107f)
        door_leaf_4_r1.texOffs(0, 82).addBox(-1f, -23f, -5f, 1, 5, 5, 0f, true)

        door_leaf_1_r2 = ModelMapper(modelDataWrapper)
        door_leaf_1_r2.setPos(21f, -14f, 0f)
        head_exterior.addChild(door_leaf_1_r2)
        setRotationAngle(door_leaf_1_r2, 0f, 3.1416f, -0.1107f)
        door_leaf_1_r2.texOffs(146, 102).addBox(0f, -23f, -10f, 1, 5, 20, 0f, true)

        door_leaf_5_r1 = ModelMapper(modelDataWrapper)
        door_leaf_5_r1.setPos(-21f, -14f, -10f)
        head_exterior.addChild(door_leaf_5_r1)
        setRotationAngle(door_leaf_5_r1, 0f, -0.3316f, 0.1107f)
        door_leaf_5_r1.texOffs(0, 82).addBox(0f, -23f, -5f, 1, 5, 5, 0f, false)

        door_leaf_2_r2 = ModelMapper(modelDataWrapper)
        door_leaf_2_r2.setPos(-21f, -14f, 0f)
        head_exterior.addChild(door_leaf_2_r2)
        setRotationAngle(door_leaf_2_r2, 0f, -3.1416f, 0.1107f)
        door_leaf_2_r2.texOffs(146, 102).addBox(-1f, -23f, -10f, 1, 5, 20, 0f, false)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(238, 237).addBox(-19f, -10f, -19.5f, 38, 9, 0, 0f, false)

        front_panel_4_r1 = ModelMapper(modelDataWrapper)
        front_panel_4_r1.setPos(0f, -35.2365f, -16.3334f)
        front.addChild(front_panel_4_r1)
        setRotationAngle(front_panel_4_r1, -0.2618f, 0f, 0f)
        front_panel_4_r1.texOffs(246, 0).addBox(-17.5f, -5.5f, 0f, 35, 11, 0, 0f, false)

        front_panel_3_r1 = ModelMapper(modelDataWrapper)
        front_panel_3_r1.setPos(0f, -10f, -19.5f)
        front.addChild(front_panel_3_r1)
        setRotationAngle(front_panel_3_r1, -0.0873f, 0f, 0f)
        front_panel_3_r1.texOffs(54, 164).addBox(-19f, -20f, 0f, 38, 20, 0, 0f, false)

        front_panel_1_r1 = ModelMapper(modelDataWrapper)
        front_panel_1_r1.setPos(0f, -1f, -19.5f)
        front.addChild(front_panel_1_r1)
        setRotationAngle(front_panel_1_r1, 0.3054f, 0f, 0f)
        front_panel_1_r1.texOffs(204, 33).addBox(-19f, 0f, 0f, 38, 9, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)


        front_side_bottom_3_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_3_r1.setPos(20f, 0f, -10f)
        side_1.addChild(front_side_bottom_3_r1)
        setRotationAngle(front_side_bottom_3_r1, 0f, 0f, 0.1745f)
        front_side_bottom_3_r1.texOffs(178, 60).addBox(0f, 0f, 0f, 0, 8, 16, 0f, true)

        front_side_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_1_r1.setPos(-20f, 0f, -10f)
        side_1.addChild(front_side_bottom_1_r1)
        setRotationAngle(front_side_bottom_1_r1, 0f, -0.1309f, -0.1745f)
        front_side_bottom_1_r1.texOffs(124, 136).addBox(0f, 0f, -13f, 0, 8, 13, 0f, false)

        front_side_lower_1_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_1_r1.setPos(-20f, 0f, -10f)
        side_1.addChild(front_side_lower_1_r1)
        setRotationAngle(front_side_lower_1_r1, 0f, -0.1309f, 0f)
        front_side_lower_1_r1.texOffs(0, 128).addBox(0f, -14f, -11f, 0, 14, 11, 0f, false)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(-20f, -14f, -10f)
        side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, -0.1309f, 0.1107f)
        front_side_upper_1_r1.texOffs(82, 47).addBox(0f, -23f, -11f, 0, 23, 11, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(-21f, 0f, 9f)
        front.addChild(side_2)


        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(41f, -14f, -19f)
        side_2.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, 0.1309f, -0.1107f)
        front_side_upper_2_r1.texOffs(82, 47).addBox(0f, -23f, -11f, 0, 23, 11, 0f, true)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(41f, 0f, -19f)
        side_2.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, 0.1309f, 0f)
        front_side_lower_2_r1.texOffs(0, 128).addBox(0f, -14f, -11f, 0, 14, 11, 0f, true)

        front_side_bottom_4_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_4_r1.setPos(1f, 0f, -19f)
        side_2.addChild(front_side_bottom_4_r1)
        setRotationAngle(front_side_bottom_4_r1, 0f, 0f, -0.1745f)
        front_side_bottom_4_r1.texOffs(178, 60).addBox(0f, 0f, 0f, 0, 8, 16, 0f, false)

        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(41f, 0f, -19f)
        side_2.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, 0.1309f, 0.1745f)
        front_side_bottom_2_r1.texOffs(124, 136).addBox(0f, 0f, -13f, 0, 8, 13, 0f, true)

        roof = ModelMapper(modelDataWrapper)
        roof.setPos(-16.7054f, -37.098f, 5f)
        head_exterior.addChild(roof)
        roof.texOffs(246, 162).addBox(10.7054f, -3.902f, -16f, 6, 1, 21, 0f, false)
        roof.texOffs(246, 162).addBox(16.7054f, -3.902f, -16f, 6, 1, 21, 0f, true)

        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(22.7054f, -3.902f, -5f)
        roof.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, 0.1745f)
        outer_roof_6_r2.texOffs(86, 243).addBox(0f, 0f, -11f, 8, 1, 21, 0f, true)

        outer_roof_5_r4 = ModelMapper(modelDataWrapper)
        outer_roof_5_r4.setPos(32.0659f, -1.0798f, -5.5f)
        roof.addChild(outer_roof_5_r4)
        setRotationAngle(outer_roof_5_r4, 0f, 0f, 0.5236f)
        outer_roof_5_r4.texOffs(30, 243).addBox(-2f, -0.5f, -10.5f, 4, 1, 21, 0f, true)

        outer_roof_4_r4 = ModelMapper(modelDataWrapper)
        outer_roof_4_r4.setPos(34.115f, 0.6032f, -5.5f)
        roof.addChild(outer_roof_4_r4)
        setRotationAngle(outer_roof_4_r4, 0f, 0f, 1.0472f)
        outer_roof_4_r4.texOffs(245, 274).addBox(-1f, -0.5f, -10.5f, 2, 1, 21, 0f, true)

        outer_roof_5_r5 = ModelMapper(modelDataWrapper)
        outer_roof_5_r5.setPos(10.7054f, -3.902f, -5f)
        roof.addChild(outer_roof_5_r5)
        setRotationAngle(outer_roof_5_r5, 0f, 0f, -0.1745f)
        outer_roof_5_r5.texOffs(86, 243).addBox(-8f, 0f, -11f, 8, 1, 21, 0f, false)

        outer_roof_4_r5 = ModelMapper(modelDataWrapper)
        outer_roof_4_r5.setPos(1.3449f, -1.0798f, -5.5f)
        roof.addChild(outer_roof_4_r5)
        setRotationAngle(outer_roof_4_r5, 0f, 0f, -0.5236f)
        outer_roof_4_r5.texOffs(30, 243).addBox(-2f, -0.5f, -10.5f, 4, 1, 21, 0f, false)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(-0.7041f, 0.6032f, -5.5f)
        roof.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, -1.0472f)
        outer_roof_3_r3.texOffs(245, 274).addBox(-1f, -0.5f, -10.5f, 2, 1, 21, 0f, false)

        vent_top_r1 = ModelMapper(modelDataWrapper)
        vent_top_r1.setPos(16.7054f, 37.098f, 48f)
        roof.addChild(vent_top_r1)
        setRotationAngle(vent_top_r1, -3.1416f, 0f, 3.1416f)
        vent_top_r1.texOffs(0, 49).addBox(-8f, -42f, 0f, 16, 2, 48, 0f, false)

        vent_2_r2 = ModelMapper(modelDataWrapper)
        vent_2_r2.setPos(24.7054f, -4.902f, 48f)
        roof.addChild(vent_2_r2)
        setRotationAngle(vent_2_r2, -3.1416f, 0f, -2.7925f)
        vent_2_r2.texOffs(80, 91).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r2 = ModelMapper(modelDataWrapper)
        vent_1_r2.setPos(8.7054f, -4.902f, 48f)
        roof.addChild(vent_1_r2)
        setRotationAngle(vent_1_r2, 3.1416f, 0f, 2.7925f)
        vent_1_r2.texOffs(138, 0).addBox(0f, 0f, 0f, 9, 2, 48, 0f, false)

        outer_roof_6_r3 = ModelMapper(modelDataWrapper)
        outer_roof_6_r3.setPos(34.1586f, 1.7327f, -18.8187f)
        roof.addChild(outer_roof_6_r3)
        setRotationAngle(outer_roof_6_r3, 2.7925f, 0f, -2.0944f)
        outer_roof_6_r3.texOffs(33, 40).addBox(-1f, 0f, -3f, 4, 0, 7, 0f, true)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(31.9919f, -0.9516f, -18.4146f)
        roof.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 2.8798f, 0f, -2.618f)
        outer_roof_7_r1.texOffs(-1, 5).addBox(-2f, 0f, -2.5f, 4, 0, 6, 0f, false)

        outer_roof_7_r2 = ModelMapper(modelDataWrapper)
        outer_roof_7_r2.setPos(22.7054f, -3.902f, -16f)
        roof.addChild(outer_roof_7_r2)
        setRotationAngle(outer_roof_7_r2, 0.3054f, 0f, 0.1745f)
        outer_roof_7_r2.texOffs(10, 49).addBox(0f, 0f, -6f, 9, 0, 6, 0f, true)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(16.7054f, 79.2472f, 0.0461f)
        roof.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0.3054f, 0f, 0f)
        outer_roof_8_r1.texOffs(16, 0).addBox(0f, -84.125f, 4.7f, 6, 0, 5, 0f, true)

        outer_roof_5_r6 = ModelMapper(modelDataWrapper)
        outer_roof_5_r6.setPos(-0.7477f, 1.7327f, -18.8187f)
        roof.addChild(outer_roof_5_r6)
        setRotationAngle(outer_roof_5_r6, 2.7925f, 0f, 2.0944f)
        outer_roof_5_r6.texOffs(33, 40).addBox(-3f, 0f, -3f, 4, 0, 7, 0f, false)

        outer_roof_6_r4 = ModelMapper(modelDataWrapper)
        outer_roof_6_r4.setPos(1.4189f, -0.9516f, -18.4146f)
        roof.addChild(outer_roof_6_r4)
        setRotationAngle(outer_roof_6_r4, 2.8798f, 0f, 2.618f)
        outer_roof_6_r4.texOffs(-1, 5).addBox(-2f, 0f, -2.5f, 4, 0, 6, 0f, true)

        outer_roof_6_r5 = ModelMapper(modelDataWrapper)
        outer_roof_6_r5.setPos(10.7054f, -3.902f, -16f)
        roof.addChild(outer_roof_6_r5)
        setRotationAngle(outer_roof_6_r5, 0.3054f, 0f, -0.1745f)
        outer_roof_6_r5.texOffs(10, 49).addBox(-9f, 0f, -6f, 9, 0, 6, 0f, false)

        outer_roof_7_r3 = ModelMapper(modelDataWrapper)
        outer_roof_7_r3.setPos(16.7054f, 79.2472f, 0.0461f)
        roof.addChild(outer_roof_7_r3)
        setRotationAngle(outer_roof_7_r3, 0.3054f, 0f, 0f)
        outer_roof_7_r3.texOffs(16, 0).addBox(-6f, -84.125f, 4.7f, 6, 0, 5, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)
        headlights.texOffs(0, 57).addBox(7.75f, -11f, -19.6f, 7, 4, 0, 0f, false)
        headlights.texOffs(0, 57).addBox(-14.75f, -11f, -19.6f, 7, 4, 0, 0f, true)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)
        tail_lights.texOffs(0, 243).addBox(7.75f, -15f, -19.6f, 14, 13, 0, 0f, false)
        tail_lights.texOffs(0, 243).addBox(-21.75f, -15f, -19.6f, 14, 13, 0, 0f, true)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-21f, 0f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(6, 3).addBox(-1.5f, -33.5f, 0f, 0, 0, 0, 0.3f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-21f, 0f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(6, 0).addBox(-1.5f, -33.5f, 0f, 0, 0, 0, 0.3f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_handrails.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        side_panel_translucent.setModelPart()
        roof_window.setModelPart()
        roof_door.setModelPart()
        roof_end.setModelPart()
        roof_exterior.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_handrail.setModelPart()
        door_exterior_1.setModelPart()
        door_left_exterior_1.setModelPart(door_exterior_1.name)
        door_right_exterior_1.setModelPart(door_exterior_1.name)
        door_exterior_2.setModelPart()
        door_left_exterior_2.setModelPart(door_exterior_2.name)
        door_right_exterior_2.setModelPart(door_exterior_2.name)
        end.setModelPart()
        end_exterior.setModelPart()
        roof_end_exterior.setModelPart()
        roof_window_light.setModelPart()
        roof_door_light.setModelPart()
        roof_end_light.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelSTrain {
        return ModelSTrain(doorAnimationType, renderDoorOverlay)
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
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(roof_window, matrices, vertices, light, position.toFloat())
                    renderMirror(window_handrails, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, position - 21.5f)
                renderMirror(side_panel_translucent, matrices, vertices, light, position + 21.5f)
            }

            RenderStage.EXTERIOR -> {
                if (isEnd2Head) {
                    renderOnceFlipped(window_exterior_1, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(window_exterior_2, matrices, vertices, light, position.toFloat())
                } else {
                    renderOnce(window_exterior_1, matrices, vertices, light, position.toFloat())
                    renderOnce(window_exterior_2, matrices, vertices, light, position.toFloat())
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
        val middleDoor = isIndex(getDoorPositions()!!.size / 2, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(roof_door_light, matrices, vertices, light, position.toFloat())
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
                    renderMirror(roof_door, matrices, vertices, light, position.toFloat())
                    renderOnce(door_handrail, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                if (isEnd2Head) {
                    door_left_exterior_1.setOffset(0f, 0, doorLeftZ)
                    door_right_exterior_1.setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_1, matrices, vertices, light, position.toFloat())
                    door_left_exterior_2.setOffset(0f, 0, doorRightZ)
                    door_right_exterior_2.setOffset(0f, 0, -doorRightZ)
                    renderOnceFlipped(door_exterior_2, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior_1.setOffset(0f, 0, doorRightZ)
                    door_right_exterior_1.setOffset(0f, 0, -doorRightZ)
                    renderOnce(door_exterior_1, matrices, vertices, light, position.toFloat())
                    door_left_exterior_2.setOffset(0f, 0, doorLeftZ)
                    door_right_exterior_2.setOffset(0f, 0, -doorLeftZ)
                    renderOnce(door_exterior_2, matrices, vertices, light, position.toFloat())
                }
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (middleDoor && !doorOpen && renderDetails) {
                    renderMirror(door_light_off, matrices, vertices, light, (position - 40).toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> renderOnceFlipped(head, matrices, vertices, light, position.toFloat())
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
        private const val DOOR_MAX = 13
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_c_train_left.png", "door_overlay_c_train_right.png")
    }
}
