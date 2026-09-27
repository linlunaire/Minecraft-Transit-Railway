package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelATrain protected constructor(
    @JvmField protected val isAel: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelATrain?>(doorAnimationType, renderDoorOverlay) {
    private val window_tcl: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_tcl_handrails: ModelMapper
    private val handrail_8_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val top_handrail_3_r1: ModelMapper
    private val seat_back_r1: ModelMapper
    private val bench: ModelMapper
    private val window_ael: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_exterior_tcl: ModelMapper
    private val upper_wall_r3: ModelMapper
    private val floor_r1: ModelMapper
    private val window_exterior_ael: ModelMapper
    private val upper_wall_r4: ModelMapper
    private val floor_r2: ModelMapper
    private val window_exterior_end_tcl: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val floor_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val floor_1_r1: ModelMapper
    private val window_exterior_end_ael: ModelMapper
    private val upper_wall_6_r1: ModelMapper
    private val floor_6_r1: ModelMapper
    private val upper_wall_5_r1: ModelMapper
    private val floor_5_r1: ModelMapper
    private val side_panel_tcl: ModelMapper
    private val side_panel_tcl_translucent: ModelMapper
    private val side_panel_ael: ModelMapper
    private val side_panel_ael_translucent: ModelMapper
    private val roof_window_tcl: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_window_ael: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val roof_door_tcl: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_3_r3: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val roof_door_ael: ModelMapper
    private val display_main_r1: ModelMapper
    private val display_6_r1: ModelMapper
    private val display_5_r1: ModelMapper
    private val display_4_r1: ModelMapper
    private val display_3_r1: ModelMapper
    private val display_2_r1: ModelMapper
    private val display_1_r1: ModelMapper
    private val inner_roof_4_r4: ModelMapper
    private val inner_roof_3_r4: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_6_r1: ModelMapper
    private val outer_roof_5_r1: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val door_tcl: ModelMapper
    private val door_left_tcl: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right_tcl: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_tcl_handrail: ModelMapper
    private val door_ael: ModelMapper
    private val door_left_ael: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_right_ael: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door_ael_handrail: ModelMapper
    private val upper_wall_right_r1: ModelMapper
    private val lower_wall_right_r1: ModelMapper
    private val upper_wall_left_r1: ModelMapper
    private val lower_wall_left_r1: ModelMapper
    private val handrail_left_r1: ModelMapper
    private val door_exterior_tcl: ModelMapper
    private val upper_wall_r5: ModelMapper
    private val floor_r3: ModelMapper
    private val door_left_exterior_tcl: ModelMapper
    private val door_left_top_r3: ModelMapper
    private val door_left_base_r1: ModelMapper
    private val door_right_exterior_tcl: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val door_right_base_r1: ModelMapper
    private val door_exterior_ael: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val floor_2_r2: ModelMapper
    private val lower_wall_1_r1: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val floor_1_r2: ModelMapper
    private val door_left_exterior_ael: ModelMapper
    private val door_left_top_r4: ModelMapper
    private val door_left_base_r2: ModelMapper
    private val door_right_exterior_ael: ModelMapper
    private val door_right_top_r4: ModelMapper
    private val door_right_base_r2: ModelMapper
    private val door_exterior_end: ModelMapper
    private val upper_wall_r6: ModelMapper
    private val floor_r4: ModelMapper
    private val door_left_exterior_end: ModelMapper
    private val door_left_top_r5: ModelMapper
    private val door_left_base_r3: ModelMapper
    private val door_right_exterior_end: ModelMapper
    private val door_right_top_r5: ModelMapper
    private val door_right_base_r3: ModelMapper
    private val luggage_rack: ModelMapper
    private val top_3_r1: ModelMapper
    private val top_2_r1: ModelMapper
    private val top_1_r1: ModelMapper
    private val upper_wall_r7: ModelMapper
    private val end_tcl: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val end_ael: ModelMapper
    private val end_exterior_tcl: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r4: ModelMapper
    private val floor_2_r3: ModelMapper
    private val floor_1_r3: ModelMapper
    private val end_exterior_ael: ModelMapper
    private val upper_wall_3_r1: ModelMapper
    private val upper_wall_2_r5: ModelMapper
    private val floor_3_r1: ModelMapper
    private val floor_2_r4: ModelMapper
    private val end_door_ael: ModelMapper
    private val roof_end: ModelMapper
    private val handrail_2_r2: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_4_r5: ModelMapper
    private val inner_roof_3_r5: ModelMapper
    private val inner_roof_2_r4: ModelMapper
    private val inner_roof_2: ModelMapper
    private val inner_roof_4_r6: ModelMapper
    private val inner_roof_3_r6: ModelMapper
    private val inner_roof_2_r5: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_6_r2: ModelMapper
    private val outer_roof_5_r2: ModelMapper
    private val outer_roof_4_r2: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_5_r3: ModelMapper
    private val outer_roof_4_r3: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_6_r3: ModelMapper
    private val roof_light_tcl: ModelMapper
    private val roof_light_r1: ModelMapper
    private val roof_light_door_ael: ModelMapper
    private val roof_light_window_ael: ModelMapper
    private val inner_roof_3_r7: ModelMapper
    private val roof_end_light: ModelMapper
    private val roof_light_2_r1: ModelMapper
    private val roof_light_1_r1: ModelMapper
    private val head_tcl: ModelMapper
    private val upper_wall_2_r6: ModelMapper
    private val upper_wall_1_r5: ModelMapper
    private val head_ael: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r7: ModelMapper
    private val upper_wall_1_r6: ModelMapper
    private val floor_2_r5: ModelMapper
    private val floor_1_r4: ModelMapper
    private val front: ModelMapper
    private val front_bottom_5_r1: ModelMapper
    private val front_bottom_4_r1: ModelMapper
    private val front_bottom_2_r1: ModelMapper
    private val front_bottom_1_r1: ModelMapper
    private val front_panel_3_r1: ModelMapper
    private val front_panel_2_r1: ModelMapper
    private val front_panel_1_r1: ModelMapper
    private val side_1: ModelMapper
    private val front_side_bottom_2_r1: ModelMapper
    private val front_middle_top_r1: ModelMapper
    private val outer_roof_8_r1: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_6_r4: ModelMapper
    private val front_panel_8_r1: ModelMapper
    private val front_panel_7_r1: ModelMapper
    private val front_panel_6_r1: ModelMapper
    private val front_panel_5_r1: ModelMapper
    private val front_panel_4_r1: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_lower_2_r1: ModelMapper
    private val side_2: ModelMapper
    private val front_side_bottom_2_r2: ModelMapper
    private val front_middle_top_r2: ModelMapper
    private val outer_roof_8_r2: ModelMapper
    private val outer_roof_7_r2: ModelMapper
    private val outer_roof_6_r5: ModelMapper
    private val front_panel_8_r2: ModelMapper
    private val front_panel_7_r2: ModelMapper
    private val front_panel_6_r2: ModelMapper
    private val front_panel_5_r2: ModelMapper
    private val front_panel_4_r2: ModelMapper
    private val front_side_upper_2_r2: ModelMapper
    private val front_side_lower_2_r2: ModelMapper
    private val headlights: ModelMapper
    private val headlight_2b_r1: ModelMapper
    private val headlight_2a_r1: ModelMapper
    private val headlight_1a_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_2a_r1: ModelMapper
    private val tail_light_1a_r1: ModelMapper
    private val seat: ModelMapper
    private val top_right_r1: ModelMapper
    private val top_left_r1: ModelMapper
    private val back_right_r1: ModelMapper
    private val back_left_r1: ModelMapper
    private val back_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper

    constructor(isAel: Boolean) : this(isAel, DoorAnimationType.PLUG_FAST, true)

    init {
        val textureWidth = 336
        val textureHeight = 336

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window_tcl = ModelMapper(modelDataWrapper)
        window_tcl.setPos(0f, 24f, 0f)
        window_tcl.texOffs(0, 0).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window_tcl.texOffs(0, 120).addBox(-21f, -14f, -26f, 3, 14, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-21f, -14f, 0f)
        window_tcl.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1396f)
        upper_wall_r1.texOffs(0, 49).addBox(0f, -19f, -26f, 3, 19, 52, 0f, false)

        window_tcl_handrails = ModelMapper(modelDataWrapper)
        window_tcl_handrails.setPos(0f, 24f, 0f)
        window_tcl_handrails.texOffs(142, 76).addBox(-18f, -6f, -22f, 7, 1, 44, 0f, false)
        window_tcl_handrails.texOffs(180, 121).addBox(-18f, -5f, -21f, 5, 5, 42, 0f, false)
        window_tcl_handrails.texOffs(4, 0).addBox(0f, -35f, -22f, 0, 35, 0, 0.2f, false)
        window_tcl_handrails.texOffs(4, 0).addBox(0f, -35f, 22f, 0, 35, 0, 0.2f, false)
        window_tcl_handrails.texOffs(42, 40).addBox(-1f, -32f, -5.5f, 2, 4, 0, 0f, false)
        window_tcl_handrails.texOffs(42, 40).addBox(-1f, -32f, -16.5f, 2, 4, 0, 0f, false)
        window_tcl_handrails.texOffs(42, 40).addBox(-1f, -32f, 5.5f, 2, 4, 0, 0f, false)
        window_tcl_handrails.texOffs(42, 40).addBox(-1f, -32f, 16.5f, 2, 4, 0, 0f, false)
        window_tcl_handrails.texOffs(4, 0).addBox(0f, -35f, 0f, 0, 35, 0, 0.2f, false)

        handrail_8_r1 = ModelMapper(modelDataWrapper)
        handrail_8_r1.setPos(0f, 0f, 0f)
        window_tcl_handrails.addChild(handrail_8_r1)
        setRotationAngle(handrail_8_r1, -1.5708f, 0f, 0f)
        handrail_8_r1.texOffs(0, 0).addBox(0f, -24f, -31.5f, 0, 48, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-11f, -5f, 0f)
        window_tcl_handrails.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, -0.0698f)
        handrail_3_r1.texOffs(8, 0).addBox(0f, -27.2f, 22f, 0, 27, 0, 0.2f, false)
        handrail_3_r1.texOffs(0, 0).addBox(0f, -27.2f, 0f, 0, 4, 0, 0.2f, false)
        handrail_3_r1.texOffs(8, 0).addBox(0f, -27.2f, -22f, 0, 27, 0, 0.2f, false)

        top_handrail_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_3_r1.setPos(-11f, -5f, 0f)
        window_tcl_handrails.addChild(top_handrail_3_r1)
        setRotationAngle(top_handrail_3_r1, -1.5708f, 0f, -0.0698f)
        top_handrail_3_r1.texOffs(0, 0).addBox(0f, -22f, -23f, 0, 44, 0, 0.2f, false)

        seat_back_r1 = ModelMapper(modelDataWrapper)
        seat_back_r1.setPos(-17f, -6f, 0f)
        window_tcl_handrails.addChild(seat_back_r1)
        setRotationAngle(seat_back_r1, 0f, 0f, -0.0524f)
        seat_back_r1.texOffs(116, 175).addBox(-1f, -8f, -22f, 1, 8, 44, 0f, false)

        bench = ModelMapper(modelDataWrapper)
        bench.setPos(0f, 0f, 0f)
        window_tcl_handrails.addChild(bench)


        window_ael = ModelMapper(modelDataWrapper)
        window_ael.setPos(0f, 24f, 0f)
        window_ael.texOffs(22, 22).addBox(-20f, 0f, -13f, 20, 1, 26, 0f, false)
        window_ael.texOffs(28, 146).addBox(-21f, -14f, -13f, 3, 14, 26, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-21f, -14f, 0f)
        window_ael.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1396f)
        upper_wall_r2.texOffs(26, 75).addBox(0f, -19f, -13f, 3, 19, 26, 0f, false)

        window_exterior_tcl = ModelMapper(modelDataWrapper)
        window_exterior_tcl.setPos(0f, 24f, 0f)
        window_exterior_tcl.texOffs(58, 73).addBox(-21f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_r3 = ModelMapper(modelDataWrapper)
        upper_wall_r3.setPos(-21f, -14f, 0f)
        window_exterior_tcl.addChild(upper_wall_r3)
        setRotationAngle(upper_wall_r3, 0f, 0f, 0.1396f)
        upper_wall_r3.texOffs(58, 0).addBox(0f, -23f, -26f, 0, 23, 52, 0f, false)

        floor_r1 = ModelMapper(modelDataWrapper)
        floor_r1.setPos(-21f, 0f, 0f)
        window_exterior_tcl.addChild(floor_r1)
        setRotationAngle(floor_r1, 0f, 0f, -0.1745f)
        floor_r1.texOffs(62, 139).addBox(0f, 0f, -24f, 1, 8, 48, 0f, false)

        window_exterior_ael = ModelMapper(modelDataWrapper)
        window_exterior_ael.setPos(0f, 24f, 0f)
        window_exterior_ael.texOffs(58, 99).addBox(-21f, -14f, -13f, 0, 14, 26, 0f, false)

        upper_wall_r4 = ModelMapper(modelDataWrapper)
        upper_wall_r4.setPos(-21f, -14f, 0f)
        window_exterior_ael.addChild(upper_wall_r4)
        setRotationAngle(upper_wall_r4, 0f, 0f, 0.1396f)
        upper_wall_r4.texOffs(58, 26).addBox(0f, -23f, -13f, 0, 23, 26, 0f, false)

        floor_r2 = ModelMapper(modelDataWrapper)
        floor_r2.setPos(-21f, 0f, 0f)
        window_exterior_ael.addChild(floor_r2)
        setRotationAngle(floor_r2, 0f, 0f, -0.1745f)
        floor_r2.texOffs(84, 161).addBox(0f, 0f, -13f, 1, 8, 26, 0f, false)

        window_exterior_end_tcl = ModelMapper(modelDataWrapper)
        window_exterior_end_tcl.setPos(0f, 24f, 0f)
        window_exterior_end_tcl.texOffs(212, 144).addBox(21f, -14f, -26f, 0, 14, 52, 0f, true)
        window_exterior_end_tcl.texOffs(212, 144).addBox(-21f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-21f, -14f, 0f)
        window_exterior_end_tcl.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1396f)
        upper_wall_2_r1.texOffs(58, 0).addBox(0f, -23f, -26f, 0, 23, 52, 0f, false)

        floor_2_r1 = ModelMapper(modelDataWrapper)
        floor_2_r1.setPos(-21f, 0f, 0f)
        window_exterior_end_tcl.addChild(floor_2_r1)
        setRotationAngle(floor_2_r1, 0f, 0f, -0.1745f)
        floor_2_r1.texOffs(8, 272).addBox(0f, 0f, -24f, 1, 8, 48, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(21f, -14f, 0f)
        window_exterior_end_tcl.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1396f)
        upper_wall_1_r1.texOffs(58, 0).addBox(0f, -23f, -26f, 0, 23, 52, 0f, true)

        floor_1_r1 = ModelMapper(modelDataWrapper)
        floor_1_r1.setPos(21f, 0f, 0f)
        window_exterior_end_tcl.addChild(floor_1_r1)
        setRotationAngle(floor_1_r1, 0f, 0f, 0.1745f)
        floor_1_r1.texOffs(8, 272).addBox(-1f, 0f, -24f, 1, 8, 48, 0f, true)

        window_exterior_end_ael = ModelMapper(modelDataWrapper)
        window_exterior_end_ael.setPos(0f, 24f, 0f)
        window_exterior_end_ael.texOffs(0, 210).addBox(21f, -14f, -2f, 0, 14, 26, 0f, true)
        window_exterior_end_ael.texOffs(0, 210).addBox(-21f, -14f, -2f, 0, 14, 26, 0f, false)
        window_exterior_end_ael.texOffs(0, 224).addBox(21f, -14f, 24f, 0, 14, 26, 0f, true)
        window_exterior_end_ael.texOffs(0, 224).addBox(-21f, -14f, 24f, 0, 14, 26, 0f, false)
        window_exterior_end_ael.texOffs(0, 238).addBox(21f, -14f, 50f, 0, 14, 26, 0f, true)
        window_exterior_end_ael.texOffs(0, 238).addBox(-21f, -14f, 50f, 0, 14, 26, 0f, false)

        upper_wall_6_r1 = ModelMapper(modelDataWrapper)
        upper_wall_6_r1.setPos(-21f, -14f, 0f)
        window_exterior_end_ael.addChild(upper_wall_6_r1)
        setRotationAngle(upper_wall_6_r1, 0f, 0f, 0.1396f)
        upper_wall_6_r1.texOffs(58, 26).addBox(0f, -23f, 50f, 0, 23, 26, 0f, false)
        upper_wall_6_r1.texOffs(58, 26).addBox(0f, -23f, 24f, 0, 23, 26, 0f, false)
        upper_wall_6_r1.texOffs(58, 26).addBox(0f, -23f, -2f, 0, 23, 26, 0f, false)

        floor_6_r1 = ModelMapper(modelDataWrapper)
        floor_6_r1.setPos(-21f, 0f, 0f)
        window_exterior_end_ael.addChild(floor_6_r1)
        setRotationAngle(floor_6_r1, 0f, 0f, -0.1745f)
        floor_6_r1.texOffs(181, 264).addBox(0f, 0f, 50f, 1, 8, 26, 0f, false)
        floor_6_r1.texOffs(181, 256).addBox(0f, 0f, 24f, 1, 8, 26, 0f, false)
        floor_6_r1.texOffs(181, 248).addBox(0f, 0f, -2f, 1, 8, 26, 0f, false)

        upper_wall_5_r1 = ModelMapper(modelDataWrapper)
        upper_wall_5_r1.setPos(21f, -14f, 0f)
        window_exterior_end_ael.addChild(upper_wall_5_r1)
        setRotationAngle(upper_wall_5_r1, 0f, 0f, -0.1396f)
        upper_wall_5_r1.texOffs(58, 26).addBox(0f, -23f, 50f, 0, 23, 26, 0f, true)
        upper_wall_5_r1.texOffs(58, 26).addBox(0f, -23f, 24f, 0, 23, 26, 0f, true)
        upper_wall_5_r1.texOffs(58, 26).addBox(0f, -23f, -2f, 0, 23, 26, 0f, true)

        floor_5_r1 = ModelMapper(modelDataWrapper)
        floor_5_r1.setPos(21f, 0f, 0f)
        window_exterior_end_ael.addChild(floor_5_r1)
        setRotationAngle(floor_5_r1, 0f, 0f, 0.1745f)
        floor_5_r1.texOffs(181, 264).addBox(-1f, 0f, 50f, 1, 8, 26, 0f, true)
        floor_5_r1.texOffs(181, 256).addBox(-1f, 0f, 24f, 1, 8, 26, 0f, true)
        floor_5_r1.texOffs(181, 248).addBox(-1f, 0f, -2f, 1, 8, 26, 0f, true)

        side_panel_tcl = ModelMapper(modelDataWrapper)
        side_panel_tcl.setPos(0f, 24f, 0f)
        side_panel_tcl.texOffs(90, 139).addBox(-18f, -35f, 0f, 7, 30, 0, 0f, false)

        side_panel_tcl_translucent = ModelMapper(modelDataWrapper)
        side_panel_tcl_translucent.setPos(0f, 24f, 0f)
        side_panel_tcl_translucent.texOffs(76, 139).addBox(-18f, -35f, 0f, 7, 30, 0, 0f, false)

        side_panel_ael = ModelMapper(modelDataWrapper)
        side_panel_ael.setPos(0f, 24f, 0f)
        side_panel_ael.texOffs(26, 281).addBox(-18f, -34f, 0f, 12, 34, 0, 0f, false)

        side_panel_ael_translucent = ModelMapper(modelDataWrapper)
        side_panel_ael_translucent.setPos(0f, 24f, 0f)
        side_panel_ael_translucent.texOffs(294, 108).addBox(-18f, -34f, 0f, 12, 34, 0, 0f, false)

        roof_window_tcl = ModelMapper(modelDataWrapper)
        roof_window_tcl.setPos(0f, 24f, 0f)
        roof_window_tcl.texOffs(62, 0).addBox(-16f, -32f, -24f, 3, 0, 48, 0f, false)
        roof_window_tcl.texOffs(52, 0).addBox(-5f, -34.5f, -24f, 5, 0, 48, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-3.5384f, -34.6286f, 0f)
        roof_window_tcl.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.1396f)
        inner_roof_4_r1.texOffs(40, 0).addBox(-6f, 0f, -24f, 6, 0, 48, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-10.4309f, -33.4846f, 0f)
        roof_window_tcl.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -0.3142f)
        inner_roof_3_r1.texOffs(0, 49).addBox(-1f, 0f, -24f, 2, 0, 48, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13f, -32f, 0f)
        roof_window_tcl.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.6283f)
        inner_roof_2_r1.texOffs(68, 0).addBox(0f, 0f, -24f, 2, 0, 48, 0f, false)

        roof_window_ael = ModelMapper(modelDataWrapper)
        roof_window_ael.setPos(0f, 24f, 0f)
        roof_window_ael.texOffs(4, 134).addBox(-16f, -32.3f, -13f, 2, 0, 26, 0f, false)
        roof_window_ael.texOffs(12, 120).addBox(-4f, -35.6679f, -13f, 4, 0, 26, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-5.1202f, -33.4588f, 0f)
        roof_window_ael.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -0.1047f)
        inner_roof_5_r1.texOffs(11, 120).addBox(-2.8798f, -2f, -13f, 5, 0, 26, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-9.0655f, -32.834f, 0f)
        roof_window_ael.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.2094f)
        inner_roof_4_r2.texOffs(10, 120).addBox(-3f, -2f, -13f, 6, 0, 26, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-15f, -32f, 0f)
        roof_window_ael.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.1047f)
        inner_roof_3_r2.texOffs(0, 134).addBox(1f, -2f, -13f, 2, 2, 26, 0f, false)

        roof_door_tcl = ModelMapper(modelDataWrapper)
        roof_door_tcl.setPos(0f, 24f, 0f)
        roof_door_tcl.texOffs(201, 269).addBox(-18f, -33f, -16f, 5, 1, 32, 0f, false)
        roof_door_tcl.texOffs(68, 8).addBox(-5f, -34.5f, -16f, 5, 0, 32, 0f, false)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(-3.5384f, -34.6286f, 0f)
        roof_door_tcl.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, -0.1396f)
        inner_roof_4_r3.texOffs(56, 8).addBox(-6f, 0f, -16f, 6, 0, 32, 0f, false)

        inner_roof_3_r3 = ModelMapper(modelDataWrapper)
        inner_roof_3_r3.setPos(-10.4309f, -33.4846f, 0f)
        roof_door_tcl.addChild(inner_roof_3_r3)
        setRotationAngle(inner_roof_3_r3, 0f, 0f, -0.3142f)
        inner_roof_3_r3.texOffs(126, 0).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-13f, -32f, 0f)
        roof_door_tcl.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.6283f)
        inner_roof_2_r2.texOffs(122, 0).addBox(0f, 0f, -16f, 2, 0, 32, 0f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(0f, 0f, 0f)
        roof_door_tcl.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, -1.5708f, 0f, 0f)
        handrail_2_r1.texOffs(0, 0).addBox(0f, -16f, -31.5f, 0, 32, 0, 0.2f, false)

        roof_door_ael = ModelMapper(modelDataWrapper)
        roof_door_ael.setPos(0f, 24f, 0f)
        roof_door_ael.texOffs(211, 277).addBox(-18f, -33f, -12f, 3, 1, 24, 0f, false)
        roof_door_ael.texOffs(209, 179).addBox(-3.1311f, -33.6679f, -28f, 4, 0, 56, 0f, false)

        display_main_r1 = ModelMapper(modelDataWrapper)
        display_main_r1.setPos(0f, -33.4588f, -28f)
        roof_door_ael.addChild(display_main_r1)
        setRotationAngle(display_main_r1, 0.1745f, 0f, 0f)
        display_main_r1.texOffs(60, 156).addBox(-10f, -2.5412f, -0.1f, 20, 4, 0, 0f, false)

        display_6_r1 = ModelMapper(modelDataWrapper)
        display_6_r1.setPos(-1.1311f, -33.6679f, 28f)
        roof_door_ael.addChild(display_6_r1)
        setRotationAngle(display_6_r1, -0.1745f, 0f, 0f)
        display_6_r1.texOffs(34, 146).addBox(-2.8689f, -3f, 0f, 4, 3, 0, 0f, false)

        display_5_r1 = ModelMapper(modelDataWrapper)
        display_5_r1.setPos(-5.1202f, -33.4588f, 28f)
        roof_door_ael.addChild(display_5_r1)
        setRotationAngle(display_5_r1, -0.1745f, 0f, -0.1047f)
        display_5_r1.texOffs(34, 146).addBox(-2.8798f, -3f, 0f, 5, 3, 0, 0f, false)

        display_4_r1 = ModelMapper(modelDataWrapper)
        display_4_r1.setPos(-9.0655f, -32.834f, 28f)
        roof_door_ael.addChild(display_4_r1)
        setRotationAngle(display_4_r1, -0.1745f, 0f, -0.2094f)
        display_4_r1.texOffs(34, 146).addBox(-3f, -3f, 0f, 6, 3, 0, 0f, false)

        display_3_r1 = ModelMapper(modelDataWrapper)
        display_3_r1.setPos(-1.1311f, -33.6679f, -28f)
        roof_door_ael.addChild(display_3_r1)
        setRotationAngle(display_3_r1, 0.1745f, 0f, 0f)
        display_3_r1.texOffs(34, 146).addBox(-2.8689f, -3f, 0f, 4, 3, 0, 0f, false)

        display_2_r1 = ModelMapper(modelDataWrapper)
        display_2_r1.setPos(-5.1202f, -33.4588f, -28f)
        roof_door_ael.addChild(display_2_r1)
        setRotationAngle(display_2_r1, 0.1745f, 0f, -0.1047f)
        display_2_r1.texOffs(34, 146).addBox(-2.8798f, -3f, 0f, 5, 3, 0, 0f, false)

        display_1_r1 = ModelMapper(modelDataWrapper)
        display_1_r1.setPos(-9.0655f, -32.834f, -28f)
        roof_door_ael.addChild(display_1_r1)
        setRotationAngle(display_1_r1, 0.1745f, 0f, -0.2094f)
        display_1_r1.texOffs(34, 146).addBox(-3f, -3f, 0f, 6, 3, 0, 0f, false)

        inner_roof_4_r4 = ModelMapper(modelDataWrapper)
        inner_roof_4_r4.setPos(-5.1202f, -33.4588f, 0f)
        roof_door_ael.addChild(inner_roof_4_r4)
        setRotationAngle(inner_roof_4_r4, 0f, 0f, -0.1047f)
        inner_roof_4_r4.texOffs(201, 179).addBox(-2f, 0f, -28f, 4, 0, 56, 0f, false)

        inner_roof_3_r4 = ModelMapper(modelDataWrapper)
        inner_roof_3_r4.setPos(-9.0655f, -32.834f, 0f)
        roof_door_ael.addChild(inner_roof_3_r4)
        setRotationAngle(inner_roof_3_r4, 0f, 0f, -0.2094f)
        inner_roof_3_r4.texOffs(193, 179).addBox(-2f, 0f, -28f, 4, 0, 56, 0f, false)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(-15f, -32f, 0f)
        roof_door_ael.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, -0.1047f)
        inner_roof_2_r3.texOffs(225, 195).addBox(0f, 0f, -12f, 4, 0, 24, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)


        outer_roof_6_r1 = ModelMapper(modelDataWrapper)
        outer_roof_6_r1.setPos(-2.339f, -41.5711f, 0f)
        roof_exterior.addChild(outer_roof_6_r1)
        setRotationAngle(outer_roof_6_r1, 0f, 0f, 1.5708f)
        outer_roof_6_r1.texOffs(106, 274).addBox(0f, -3f, -20f, 0, 6, 40, 0f, false)

        outer_roof_5_r1 = ModelMapper(modelDataWrapper)
        outer_roof_5_r1.setPos(-9.7706f, -40.7897f, 0f)
        roof_exterior.addChild(outer_roof_5_r1)
        setRotationAngle(outer_roof_5_r1, 0f, 0f, 1.3963f)
        outer_roof_5_r1.texOffs(106, 280).addBox(0f, -4.5f, -20f, 0, 9, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-15.501f, -39.2584f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, 1.0472f)
        outer_roof_4_r1.texOffs(106, 289).addBox(0f, -1.5f, -20f, 0, 3, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-18.6652f, -37.2758f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, 0.5236f)
        outer_roof_3_r1.texOffs(106, 292).addBox(1f, -2f, -20f, 0, 2, 40, 0f, false)

        door_tcl = ModelMapper(modelDataWrapper)
        door_tcl.setPos(0f, 24f, 0f)
        door_tcl.texOffs(0, 195).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)

        door_left_tcl = ModelMapper(modelDataWrapper)
        door_left_tcl.setPos(0f, 0f, 0f)
        door_tcl.addChild(door_left_tcl)
        door_left_tcl.texOffs(280, 168).addBox(-21f, -14f, 0f, 1, 14, 14, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left_tcl.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1396f)
        door_left_top_r1.texOffs(68, 279).addBox(-0.2f, -19f, 0f, 1, 19, 14, 0f, false)

        door_right_tcl = ModelMapper(modelDataWrapper)
        door_right_tcl.setPos(0f, 0f, 0f)
        door_tcl.addChild(door_right_tcl)
        door_right_tcl.texOffs(56, 233).addBox(-21f, -14f, -14f, 1, 14, 14, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right_tcl.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1396f)
        door_right_top_r1.texOffs(0, 190).addBox(-0.2f, -19f, -14f, 1, 19, 14, 0f, false)

        door_tcl_handrail = ModelMapper(modelDataWrapper)
        door_tcl_handrail.setPos(0f, 24f, 0f)
        door_tcl_handrail.texOffs(4, 0).addBox(0f, -35f, 0f, 0, 35, 0, 0.2f, false)

        door_ael = ModelMapper(modelDataWrapper)
        door_ael.setPos(0f, 24f, 0f)
        door_ael.texOffs(8, 203).addBox(-20f, 0f, -12f, 20, 1, 24, 0f, false)

        door_left_ael = ModelMapper(modelDataWrapper)
        door_left_ael.setPos(0f, 0f, 0f)
        door_ael.addChild(door_left_ael)
        door_left_ael.texOffs(283, 171).addBox(-21f, -14f, 0f, 1, 14, 11, 0f, false)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(-20.8f, -14f, 0f)
        door_left_ael.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, 0.1396f)
        door_left_top_r2.texOffs(71, 282).addBox(-0.2f, -19f, 0f, 1, 19, 11, 0f, false)

        door_right_ael = ModelMapper(modelDataWrapper)
        door_right_ael.setPos(0f, 0f, 0f)
        door_ael.addChild(door_right_ael)
        door_right_ael.texOffs(59, 236).addBox(-21f, -14f, -11f, 1, 14, 11, 0f, false)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(-20.8f, -14f, 0f)
        door_right_ael.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, 0.1396f)
        door_right_top_r2.texOffs(3, 193).addBox(-0.2f, -19f, -11f, 1, 19, 11, 0f, false)

        door_ael_handrail = ModelMapper(modelDataWrapper)
        door_ael_handrail.setPos(0f, 24f, 0f)
        door_ael_handrail.texOffs(119, 195).addBox(-17f, -18f, -12.4f, 5, 1, 1, 0f, false)

        upper_wall_right_r1 = ModelMapper(modelDataWrapper)
        upper_wall_right_r1.setPos(-21f, -14f, -11f)
        door_ael_handrail.addChild(upper_wall_right_r1)
        setRotationAngle(upper_wall_right_r1, 0f, 0.2014f, 0.1396f)
        upper_wall_right_r1.texOffs(212, 196).addBox(0f, -20f, -1f, 5, 20, 1, 0f, false)

        lower_wall_right_r1 = ModelMapper(modelDataWrapper)
        lower_wall_right_r1.setPos(-21f, 0f, -11f)
        door_ael_handrail.addChild(lower_wall_right_r1)
        setRotationAngle(lower_wall_right_r1, 0f, 0.2014f, 0f)
        lower_wall_right_r1.texOffs(184, 196).addBox(0f, -14f, -1f, 5, 14, 1, 0f, false)

        upper_wall_left_r1 = ModelMapper(modelDataWrapper)
        upper_wall_left_r1.setPos(-21f, -14f, 11f)
        door_ael_handrail.addChild(upper_wall_left_r1)
        setRotationAngle(upper_wall_left_r1, 0f, 2.9402f, 0.1396f)
        upper_wall_left_r1.texOffs(212, 196).addBox(-5f, -20f, -1f, 5, 20, 1, 0f, true)

        lower_wall_left_r1 = ModelMapper(modelDataWrapper)
        lower_wall_left_r1.setPos(-21f, 0f, 11f)
        door_ael_handrail.addChild(lower_wall_left_r1)
        setRotationAngle(lower_wall_left_r1, 0f, 2.9402f, 0f)
        lower_wall_left_r1.texOffs(184, 196).addBox(-5f, -14f, -1f, 5, 14, 1, 0f, true)

        handrail_left_r1 = ModelMapper(modelDataWrapper)
        handrail_left_r1.setPos(0f, 0f, 0f)
        door_ael_handrail.addChild(handrail_left_r1)
        setRotationAngle(handrail_left_r1, 0f, 3.1416f, 0f)
        handrail_left_r1.texOffs(119, 195).addBox(12f, -18f, -12.4f, 5, 1, 1, 0f, true)

        door_exterior_tcl = ModelMapper(modelDataWrapper)
        door_exterior_tcl.setPos(0f, 24f, 0f)


        upper_wall_r5 = ModelMapper(modelDataWrapper)
        upper_wall_r5.setPos(-21f, -14f, 0f)
        door_exterior_tcl.addChild(upper_wall_r5)
        setRotationAngle(upper_wall_r5, 0f, 0f, 0.1396f)
        upper_wall_r5.texOffs(72, 196).addBox(0f, -23f, -16f, 1, 4, 32, 0f, false)

        floor_r3 = ModelMapper(modelDataWrapper)
        floor_r3.setPos(-21f, 0f, 0f)
        door_exterior_tcl.addChild(floor_r3)
        setRotationAngle(floor_r3, 0f, 0f, -0.1745f)
        floor_r3.texOffs(56, 232).addBox(0f, 0f, -16f, 1, 8, 32, 0f, false)

        door_left_exterior_tcl = ModelMapper(modelDataWrapper)
        door_left_exterior_tcl.setPos(0f, 0f, 0f)
        door_exterior_tcl.addChild(door_left_exterior_tcl)
        door_left_exterior_tcl.texOffs(0, 287).addBox(-21f, -14f, 0f, 0, 14, 16, 0f, false)

        door_left_top_r3 = ModelMapper(modelDataWrapper)
        door_left_top_r3.setPos(-21f, -14f, 0f)
        door_left_exterior_tcl.addChild(door_left_top_r3)
        setRotationAngle(door_left_top_r3, 0f, 0f, 0.1396f)
        door_left_top_r3.texOffs(0, 265).addBox(0f, -22f, 0f, 0, 22, 16, 0f, false)

        door_left_base_r1 = ModelMapper(modelDataWrapper)
        door_left_base_r1.setPos(-21f, 0f, 0f)
        door_left_exterior_tcl.addChild(door_left_base_r1)
        setRotationAngle(door_left_base_r1, 0f, 0f, -0.1745f)
        door_left_base_r1.texOffs(0, 301).addBox(0f, 0f, 0f, 0, 2, 16, 0f, false)

        door_right_exterior_tcl = ModelMapper(modelDataWrapper)
        door_right_exterior_tcl.setPos(0f, 0f, 0f)
        door_exterior_tcl.addChild(door_right_exterior_tcl)
        door_right_exterior_tcl.texOffs(294, 76).addBox(-21f, -14f, -16f, 0, 14, 16, 0f, false)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(-21f, -14f, 0f)
        door_right_exterior_tcl.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, 0.1396f)
        door_right_top_r3.texOffs(294, 54).addBox(0f, -22f, -16f, 0, 22, 16, 0f, false)

        door_right_base_r1 = ModelMapper(modelDataWrapper)
        door_right_base_r1.setPos(-21f, 0f, 0f)
        door_right_exterior_tcl.addChild(door_right_base_r1)
        setRotationAngle(door_right_base_r1, 0f, 0f, -0.1745f)
        door_right_base_r1.texOffs(294, 90).addBox(0f, 0f, -16f, 0, 2, 16, 0f, false)

        door_exterior_ael = ModelMapper(modelDataWrapper)
        door_exterior_ael.setPos(0f, 24f, 0f)
        door_exterior_ael.texOffs(69, 110).addBox(-21f, -14f, -28f, 0, 14, 15, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-21f, -14f, 0f)
        door_exterior_ael.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1396f)
        upper_wall_2_r2.texOffs(110, 35).addBox(0f, -23f, -28f, 0, 23, 17, 0f, false)
        upper_wall_2_r2.texOffs(78, 202).addBox(0f, -23f, -13f, 1, 4, 26, 0f, false)

        floor_2_r2 = ModelMapper(modelDataWrapper)
        floor_2_r2.setPos(-21f, 0f, 0f)
        door_exterior_ael.addChild(floor_2_r2)
        setRotationAngle(floor_2_r2, 0f, 0f, -0.1745f)
        floor_2_r2.texOffs(95, 172).addBox(0f, 0f, -28f, 1, 8, 15, 0f, false)
        floor_2_r2.texOffs(62, 238).addBox(0f, 0f, -13f, 1, 8, 26, 0f, false)

        lower_wall_1_r1 = ModelMapper(modelDataWrapper)
        lower_wall_1_r1.setPos(0f, 0f, 0f)
        door_exterior_ael.addChild(lower_wall_1_r1)
        setRotationAngle(lower_wall_1_r1, 0f, 3.1416f, 0f)
        lower_wall_1_r1.texOffs(69, 110).addBox(21f, -14f, -28f, 0, 14, 15, 0f, true)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(-21f, -14f, 0f)
        door_exterior_ael.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 3.1416f, 0.1396f)
        upper_wall_1_r2.texOffs(110, 35).addBox(0f, -23f, -28f, 0, 23, 17, 0f, true)

        floor_1_r2 = ModelMapper(modelDataWrapper)
        floor_1_r2.setPos(-21f, 0f, 0f)
        door_exterior_ael.addChild(floor_1_r2)
        setRotationAngle(floor_1_r2, 0f, 3.1416f, -0.1745f)
        floor_1_r2.texOffs(95, 172).addBox(-1f, 0f, -28f, 1, 8, 15, 0f, true)

        door_left_exterior_ael = ModelMapper(modelDataWrapper)
        door_left_exterior_ael.setPos(0f, 0f, 0f)
        door_exterior_ael.addChild(door_left_exterior_ael)
        door_left_exterior_ael.texOffs(0, 290).addBox(-21f, -14f, 0f, 0, 14, 13, 0f, false)

        door_left_top_r4 = ModelMapper(modelDataWrapper)
        door_left_top_r4.setPos(-21f, -14f, 0f)
        door_left_exterior_ael.addChild(door_left_top_r4)
        setRotationAngle(door_left_top_r4, 0f, 0f, 0.1396f)
        door_left_top_r4.texOffs(0, 268).addBox(0f, -22f, 0f, 0, 22, 13, 0f, false)

        door_left_base_r2 = ModelMapper(modelDataWrapper)
        door_left_base_r2.setPos(-21f, 0f, 0f)
        door_left_exterior_ael.addChild(door_left_base_r2)
        setRotationAngle(door_left_base_r2, 0f, 0f, -0.1745f)
        door_left_base_r2.texOffs(0, 304).addBox(0f, 0f, 0f, 0, 2, 13, 0f, false)

        door_right_exterior_ael = ModelMapper(modelDataWrapper)
        door_right_exterior_ael.setPos(0f, 0f, 0f)
        door_exterior_ael.addChild(door_right_exterior_ael)
        door_right_exterior_ael.texOffs(294, 79).addBox(-21f, -14f, -13f, 0, 14, 13, 0f, false)

        door_right_top_r4 = ModelMapper(modelDataWrapper)
        door_right_top_r4.setPos(-21f, -14f, 0f)
        door_right_exterior_ael.addChild(door_right_top_r4)
        setRotationAngle(door_right_top_r4, 0f, 0f, 0.1396f)
        door_right_top_r4.texOffs(294, 57).addBox(0f, -22f, -13f, 0, 22, 13, 0f, false)

        door_right_base_r2 = ModelMapper(modelDataWrapper)
        door_right_base_r2.setPos(-21f, 0f, 0f)
        door_right_exterior_ael.addChild(door_right_base_r2)
        setRotationAngle(door_right_base_r2, 0f, 0f, -0.1745f)
        door_right_base_r2.texOffs(294, 93).addBox(0f, 0f, -13f, 0, 2, 13, 0f, false)

        door_exterior_end = ModelMapper(modelDataWrapper)
        door_exterior_end.setPos(0f, 24f, 0f)


        upper_wall_r6 = ModelMapper(modelDataWrapper)
        upper_wall_r6.setPos(-21f, -14f, 0f)
        door_exterior_end.addChild(upper_wall_r6)
        setRotationAngle(upper_wall_r6, 0f, 0f, 0.1396f)
        upper_wall_r6.texOffs(72, 196).addBox(0f, -23f, -16f, 1, 4, 32, 0f, false)

        floor_r4 = ModelMapper(modelDataWrapper)
        floor_r4.setPos(-21f, 0f, 0f)
        door_exterior_end.addChild(floor_r4)
        setRotationAngle(floor_r4, 0f, 0f, -0.1745f)
        floor_r4.texOffs(266, 294).addBox(0f, 0f, -16f, 1, 8, 32, 0f, false)

        door_left_exterior_end = ModelMapper(modelDataWrapper)
        door_left_exterior_end.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_left_exterior_end)
        door_left_exterior_end.texOffs(162, 109).addBox(-21f, -14f, 0f, 0, 14, 16, 0f, false)

        door_left_top_r5 = ModelMapper(modelDataWrapper)
        door_left_top_r5.setPos(-21f, -14f, 0f)
        door_left_exterior_end.addChild(door_left_top_r5)
        setRotationAngle(door_left_top_r5, 0f, 0f, 0.1396f)
        door_left_top_r5.texOffs(0, 265).addBox(0f, -22f, 0f, 0, 22, 16, 0f, false)

        door_left_base_r3 = ModelMapper(modelDataWrapper)
        door_left_base_r3.setPos(-21f, 0f, 0f)
        door_left_exterior_end.addChild(door_left_base_r3)
        setRotationAngle(door_left_base_r3, 0f, 0f, -0.1745f)
        door_left_base_r3.texOffs(162, 123).addBox(0f, 0f, 0f, 0, 2, 16, 0f, false)

        door_right_exterior_end = ModelMapper(modelDataWrapper)
        door_right_exterior_end.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_right_exterior_end)
        door_right_exterior_end.texOffs(162, 125).addBox(-21f, -14f, -16f, 0, 14, 16, 0f, false)

        door_right_top_r5 = ModelMapper(modelDataWrapper)
        door_right_top_r5.setPos(-21f, -14f, 0f)
        door_right_exterior_end.addChild(door_right_top_r5)
        setRotationAngle(door_right_top_r5, 0f, 0f, 0.1396f)
        door_right_top_r5.texOffs(294, 54).addBox(0f, -22f, -16f, 0, 22, 16, 0f, false)

        door_right_base_r3 = ModelMapper(modelDataWrapper)
        door_right_base_r3.setPos(-21f, 0f, 0f)
        door_right_exterior_end.addChild(door_right_base_r3)
        setRotationAngle(door_right_base_r3, 0f, 0f, -0.1745f)
        door_right_base_r3.texOffs(162, 139).addBox(0f, 0f, -16f, 0, 2, 16, 0f, false)

        luggage_rack = ModelMapper(modelDataWrapper)
        luggage_rack.setPos(0f, 24f, 0f)
        luggage_rack.texOffs(176, 218).addBox(-21f, -14f, -8f, 3, 14, 16, 0f, false)
        luggage_rack.texOffs(60, 139).addBox(-18f, -13f, -8f, 9, 1, 16, 0f, false)
        luggage_rack.texOffs(32, 32).addBox(-20f, 0f, -8f, 20, 1, 16, 0f, false)

        top_3_r1 = ModelMapper(modelDataWrapper)
        top_3_r1.setPos(-9.3615f, -29.3127f, -20f)
        luggage_rack.addChild(top_3_r1)
        setRotationAngle(top_3_r1, 0f, 0f, -0.2618f)
        top_3_r1.texOffs(134, 125).addBox(-3f, -4f, 12f, 3, 7, 16, 0f, false)

        top_2_r1 = ModelMapper(modelDataWrapper)
        top_2_r1.setPos(-10f, -25f, 0f)
        luggage_rack.addChild(top_2_r1)
        setRotationAngle(top_2_r1, 0f, 0f, 0.7854f)
        top_2_r1.texOffs(136, 132).addBox(-1f, -2f, -8f, 1, 2, 16, 0f, false)

        top_1_r1 = ModelMapper(modelDataWrapper)
        top_1_r1.setPos(0f, 0f, 0f)
        luggage_rack.addChild(top_1_r1)
        setRotationAngle(top_1_r1, 0f, 0f, 1.5708f)
        top_1_r1.texOffs(136, 132).addBox(-26f, 10f, -8f, 1, 8, 16, 0f, false)

        upper_wall_r7 = ModelMapper(modelDataWrapper)
        upper_wall_r7.setPos(-21f, -14f, 0f)
        luggage_rack.addChild(upper_wall_r7)
        setRotationAngle(upper_wall_r7, 0f, 0f, 0.1396f)
        upper_wall_r7.texOffs(142, 78).addBox(0f, -19f, -8f, 3, 19, 16, 0f, false)

        end_tcl = ModelMapper(modelDataWrapper)
        end_tcl.setPos(0f, 24f, 0f)
        end_tcl.texOffs(162, 175).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end_tcl.texOffs(110, 195).addBox(18f, -14f, 7f, 3, 14, 3, 0f, true)
        end_tcl.texOffs(110, 195).addBox(-21f, -14f, 7f, 3, 14, 3, 0f, false)
        end_tcl.texOffs(127, 231).addBox(9.5f, -34f, -12f, 9, 34, 19, 0f, false)
        end_tcl.texOffs(0, 228).addBox(-18.5f, -34f, -12f, 9, 34, 19, 0f, false)
        end_tcl.texOffs(79, 293).addBox(-9.5f, -35f, -12f, 19, 2, 19, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-21f, -14f, 0f)
        end_tcl.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1396f)
        upper_wall_2_r3.texOffs(248, 110).addBox(0f, -19f, 7f, 3, 19, 3, 0f, false)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(21f, -14f, 0f)
        end_tcl.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1396f)
        upper_wall_1_r3.texOffs(164, 227).addBox(-3f, -19f, 7f, 3, 19, 3, 0f, true)

        end_ael = ModelMapper(modelDataWrapper)
        end_ael.setPos(0f, 24f, 0f)
        end_ael.texOffs(138, 173).addBox(-7f, 0f, -12f, 14, 1, 10, 0f, false)
        end_ael.texOffs(128, 238).addBox(7f, -36f, -12f, 11, 36, 10, 0f, true)
        end_ael.texOffs(128, 238).addBox(-18f, -36f, -12f, 11, 36, 10, 0f, false)
        end_ael.texOffs(24, 186).addBox(-7f, -36f, -12f, 14, 4, 10, 0f, false)

        end_exterior_tcl = ModelMapper(modelDataWrapper)
        end_exterior_tcl.setPos(0f, 24f, 0f)
        end_exterior_tcl.texOffs(0, 134).addBox(18f, -14f, -12f, 3, 14, 22, 0f, true)
        end_exterior_tcl.texOffs(0, 134).addBox(-21f, -14f, -12f, 3, 14, 22, 0f, false)
        end_exterior_tcl.texOffs(182, 0).addBox(9.5f, -34f, -12f, 9, 34, 0, 0f, true)
        end_exterior_tcl.texOffs(182, 0).addBox(-18.5f, -34f, -12f, 9, 34, 0, 0f, false)
        end_exterior_tcl.texOffs(240, 236).addBox(-18f, -41f, -12f, 36, 8, 0, 0f, false)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-21f, -14f, 0f)
        end_exterior_tcl.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1396f)
        upper_wall_2_r4.texOffs(183, 251).addBox(0f, -23f, -12f, 3, 23, 22, 0f, false)

        upper_wall_1_r4 = ModelMapper(modelDataWrapper)
        upper_wall_1_r4.setPos(21f, -14f, 0f)
        end_exterior_tcl.addChild(upper_wall_1_r4)
        setRotationAngle(upper_wall_1_r4, 0f, 0f, -0.1396f)
        upper_wall_1_r4.texOffs(183, 251).addBox(-3f, -23f, -12f, 3, 23, 22, 0f, true)

        floor_2_r3 = ModelMapper(modelDataWrapper)
        floor_2_r3.setPos(-21f, 0f, 0f)
        end_exterior_tcl.addChild(floor_2_r3)
        setRotationAngle(floor_2_r3, 0f, 0f, -0.1745f)
        floor_2_r3.texOffs(112, 139).addBox(0f, 0f, -12f, 1, 8, 20, 0f, false)

        floor_1_r3 = ModelMapper(modelDataWrapper)
        floor_1_r3.setPos(21f, 0f, 0f)
        end_exterior_tcl.addChild(floor_1_r3)
        setRotationAngle(floor_1_r3, 0f, 0f, 0.1745f)
        floor_1_r3.texOffs(112, 139).addBox(-1f, 0f, -12f, 1, 8, 20, 0f, true)

        end_exterior_ael = ModelMapper(modelDataWrapper)
        end_exterior_ael.setPos(0f, 24f, 0f)
        end_exterior_ael.texOffs(149, 185).addBox(-20f, 0f, -12f, 40, 1, 10, 0f, false)
        end_exterior_ael.texOffs(0, 134).addBox(18f, -14f, -12f, 3, 14, 10, 0f, true)
        end_exterior_ael.texOffs(0, 134).addBox(-21f, -14f, -12f, 3, 14, 10, 0f, false)
        end_exterior_ael.texOffs(180, 2).addBox(7f, -32f, -12f, 11, 32, 0, 0f, true)
        end_exterior_ael.texOffs(180, 2).addBox(-18f, -32f, -12f, 11, 32, 0, 0f, false)
        end_exterior_ael.texOffs(240, 235).addBox(-18f, -41f, -12f, 36, 9, 0, 0f, false)

        upper_wall_3_r1 = ModelMapper(modelDataWrapper)
        upper_wall_3_r1.setPos(-21f, -14f, 0f)
        end_exterior_ael.addChild(upper_wall_3_r1)
        setRotationAngle(upper_wall_3_r1, 0f, 0f, 0.1396f)
        upper_wall_3_r1.texOffs(196, 120).addBox(0f, -23f, -12f, 3, 23, 10, 0f, false)

        upper_wall_2_r5 = ModelMapper(modelDataWrapper)
        upper_wall_2_r5.setPos(21f, -14f, 0f)
        end_exterior_ael.addChild(upper_wall_2_r5)
        setRotationAngle(upper_wall_2_r5, 0f, 0f, -0.1396f)
        upper_wall_2_r5.texOffs(196, 120).addBox(-3f, -23f, -12f, 3, 23, 10, 0f, true)

        floor_3_r1 = ModelMapper(modelDataWrapper)
        floor_3_r1.setPos(-21f, 0f, 0f)
        end_exterior_ael.addChild(floor_3_r1)
        setRotationAngle(floor_3_r1, 0f, 0f, -0.1745f)
        floor_3_r1.texOffs(122, 149).addBox(0f, 0f, -12f, 1, 8, 10, 0f, false)

        floor_2_r4 = ModelMapper(modelDataWrapper)
        floor_2_r4.setPos(21f, 0f, 0f)
        end_exterior_ael.addChild(floor_2_r4)
        setRotationAngle(floor_2_r4, 0f, 0f, 0.1745f)
        floor_2_r4.texOffs(122, 149).addBox(-1f, 0f, -12f, 1, 8, 10, 0f, true)

        end_door_ael = ModelMapper(modelDataWrapper)
        end_door_ael.setPos(0f, 24f, 0f)
        end_door_ael.texOffs(280, 0).addBox(-7f, -32f, -2f, 14, 32, 0, 0f, false)

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
        inner_roof_1.texOffs(197, 265).addBox(-16f, 0f, -12f, 5, 1, 36, 0f, false)
        inner_roof_1.texOffs(64, 0).addBox(-3f, -1.5f, -12f, 5, 0, 36, 0f, false)

        inner_roof_4_r5 = ModelMapper(modelDataWrapper)
        inner_roof_4_r5.setPos(-1.5384f, -1.6286f, -16f)
        inner_roof_1.addChild(inner_roof_4_r5)
        setRotationAngle(inner_roof_4_r5, 0f, 0f, -0.1396f)
        inner_roof_4_r5.texOffs(52, 0).addBox(-6f, 0f, 4f, 6, 0, 36, 0f, false)

        inner_roof_3_r5 = ModelMapper(modelDataWrapper)
        inner_roof_3_r5.setPos(-8.4309f, -0.4846f, -16f)
        inner_roof_1.addChild(inner_roof_3_r5)
        setRotationAngle(inner_roof_3_r5, 0f, 0f, -0.3142f)
        inner_roof_3_r5.texOffs(8, 49).addBox(-1f, 0f, 4f, 2, 0, 36, 0f, false)

        inner_roof_2_r4 = ModelMapper(modelDataWrapper)
        inner_roof_2_r4.setPos(-11f, 1f, -16f)
        inner_roof_1.addChild(inner_roof_2_r4)
        setRotationAngle(inner_roof_2_r4, 0f, 0f, -0.6283f)
        inner_roof_2_r4.texOffs(96, 0).addBox(0f, 0f, 4f, 2, 0, 36, 0f, false)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(-2f, -33f, 16f)
        roof_end.addChild(inner_roof_2)
        inner_roof_2.texOffs(197, 265).addBox(15f, 0f, -12f, 5, 1, 36, 0f, true)
        inner_roof_2.texOffs(64, 0).addBox(2f, -1.5f, -12f, 5, 0, 36, 0f, true)

        inner_roof_4_r6 = ModelMapper(modelDataWrapper)
        inner_roof_4_r6.setPos(5.5384f, -1.6286f, -16f)
        inner_roof_2.addChild(inner_roof_4_r6)
        setRotationAngle(inner_roof_4_r6, 0f, 0f, 0.1396f)
        inner_roof_4_r6.texOffs(52, 0).addBox(0f, 0f, 4f, 6, 0, 36, 0f, true)

        inner_roof_3_r6 = ModelMapper(modelDataWrapper)
        inner_roof_3_r6.setPos(12.4309f, -0.4846f, -16f)
        inner_roof_2.addChild(inner_roof_3_r6)
        setRotationAngle(inner_roof_3_r6, 0f, 0f, 0.3142f)
        inner_roof_3_r6.texOffs(8, 49).addBox(-1f, 0f, 4f, 2, 0, 36, 0f, true)

        inner_roof_2_r5 = ModelMapper(modelDataWrapper)
        inner_roof_2_r5.setPos(15f, 1f, -16f)
        inner_roof_2.addChild(inner_roof_2_r5)
        setRotationAngle(inner_roof_2_r5, 0f, 0f, 0.6283f)
        inner_roof_2_r5.texOffs(96, 0).addBox(-2f, 0f, 4f, 2, 0, 36, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(62, 75).addBox(-8f, -42.5f, 0f, 16, 2, 48, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -42.5f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(88, 1).addBox(-9f, 0f, 0f, 9, 2, 48, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -42.5f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(88, 1).addBox(0f, 0f, 0f, 9, 2, 48, 0f, true)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)


        outer_roof_6_r2 = ModelMapper(modelDataWrapper)
        outer_roof_6_r2.setPos(-2.3377f, -41.071f, 0f)
        outer_roof_1.addChild(outer_roof_6_r2)
        setRotationAngle(outer_roof_6_r2, 0f, 0f, 1.5708f)
        outer_roof_6_r2.texOffs(174, 137).addBox(-0.5f, -3f, -12f, 1, 6, 20, 0f, false)

        outer_roof_5_r2 = ModelMapper(modelDataWrapper)
        outer_roof_5_r2.setPos(-9.6825f, -40.2972f, 0f)
        outer_roof_1.addChild(outer_roof_5_r2)
        setRotationAngle(outer_roof_5_r2, 0f, 0f, 1.3963f)
        outer_roof_5_r2.texOffs(154, 14).addBox(-0.5f, -4.5f, -12f, 1, 9, 20, 0f, false)

        outer_roof_4_r2 = ModelMapper(modelDataWrapper)
        outer_roof_4_r2.setPos(-15.25f, -38.8252f, 0f)
        outer_roof_1.addChild(outer_roof_4_r2)
        setRotationAngle(outer_roof_4_r2, 0f, 0f, 1.0472f)
        outer_roof_4_r2.texOffs(106, 195).addBox(-0.5f, -1.5f, -12f, 1, 3, 20, 0f, false)

        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-16.866f, -37.3922f, 0f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, 0.5236f)
        outer_roof_3_r2.texOffs(162, 196).addBox(-0.5f, -1f, -12f, 1, 2, 20, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)


        outer_roof_5_r3 = ModelMapper(modelDataWrapper)
        outer_roof_5_r3.setPos(9.6825f, -40.2972f, 0f)
        outer_roof_2.addChild(outer_roof_5_r3)
        setRotationAngle(outer_roof_5_r3, 0f, 0f, -1.3963f)
        outer_roof_5_r3.texOffs(154, 14).addBox(-0.5f, -4.5f, -12f, 1, 9, 20, 0f, true)

        outer_roof_4_r3 = ModelMapper(modelDataWrapper)
        outer_roof_4_r3.setPos(15.25f, -38.8252f, 0f)
        outer_roof_2.addChild(outer_roof_4_r3)
        setRotationAngle(outer_roof_4_r3, 0f, 0f, -1.0472f)
        outer_roof_4_r3.texOffs(106, 195).addBox(-0.5f, -1.5f, -12f, 1, 3, 20, 0f, true)

        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(16.866f, -37.3922f, 0f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, -0.5236f)
        outer_roof_3_r3.texOffs(162, 196).addBox(-0.5f, -1f, -12f, 1, 2, 20, 0f, true)

        outer_roof_6_r3 = ModelMapper(modelDataWrapper)
        outer_roof_6_r3.setPos(2.3377f, -41.071f, 0f)
        outer_roof_2.addChild(outer_roof_6_r3)
        setRotationAngle(outer_roof_6_r3, 0f, 0f, -1.5708f)
        outer_roof_6_r3.texOffs(174, 137).addBox(-0.5f, -3f, -12f, 1, 6, 20, 0f, true)

        roof_light_tcl = ModelMapper(modelDataWrapper)
        roof_light_tcl.setPos(0f, 24f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(-7f, -33.5f, 0f)
        roof_light_tcl.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, 0.1745f)
        roof_light_r1.texOffs(154, 0).addBox(-3f, -1f, -24f, 3, 1, 48, 0f, false)

        roof_light_door_ael = ModelMapper(modelDataWrapper)
        roof_light_door_ael.setPos(0f, 24f, 0f)
        roof_light_door_ael.texOffs(126, 78).addBox(-10f, -33.7f, -8f, 10, 0, 16, 0f, false)

        roof_light_window_ael = ModelMapper(modelDataWrapper)
        roof_light_window_ael.setPos(0f, 24f, 0f)


        inner_roof_3_r7 = ModelMapper(modelDataWrapper)
        inner_roof_3_r7.setPos(-15f, -32f, 0f)
        roof_light_window_ael.addChild(inner_roof_3_r7)
        setRotationAngle(inner_roof_3_r7, 0f, 0f, -0.1047f)
        inner_roof_3_r7.texOffs(176, 22).addBox(2.5f, -1.1f, -13f, 2, 1, 26, 0f, false)

        roof_end_light = ModelMapper(modelDataWrapper)
        roof_end_light.setPos(0f, 24f, 0f)


        roof_light_2_r1 = ModelMapper(modelDataWrapper)
        roof_light_2_r1.setPos(7f, -33.5f, 0f)
        roof_end_light.addChild(roof_light_2_r1)
        setRotationAngle(roof_light_2_r1, 0f, 0f, -0.1745f)
        roof_light_2_r1.texOffs(166, 12).addBox(0f, -1f, 4f, 3, 1, 36, 0f, false)

        roof_light_1_r1 = ModelMapper(modelDataWrapper)
        roof_light_1_r1.setPos(-7f, -33.5f, 0f)
        roof_end_light.addChild(roof_light_1_r1)
        setRotationAngle(roof_light_1_r1, 0f, 0f, 0.1745f)
        roof_light_1_r1.texOffs(166, 12).addBox(-3f, -1f, 4f, 3, 1, 36, 0f, false)

        head_tcl = ModelMapper(modelDataWrapper)
        head_tcl.setPos(0f, 24f, 0f)
        head_tcl.texOffs(180, 168).addBox(-20f, 0f, 4f, 40, 1, 4, 0f, false)
        head_tcl.texOffs(90, 75).addBox(18f, -14f, 4f, 3, 14, 6, 0f, true)
        head_tcl.texOffs(90, 75).addBox(-21f, -14f, 4f, 3, 14, 6, 0f, false)
        head_tcl.texOffs(208, 0).addBox(-18f, -41f, 4f, 36, 41, 0, 0f, false)

        upper_wall_2_r6 = ModelMapper(modelDataWrapper)
        upper_wall_2_r6.setPos(-21f, -14f, 0f)
        head_tcl.addChild(upper_wall_2_r6)
        setRotationAngle(upper_wall_2_r6, 0f, 0f, 0.1396f)
        upper_wall_2_r6.texOffs(0, 72).addBox(0f, -19f, 4f, 3, 19, 6, 0f, false)

        upper_wall_1_r5 = ModelMapper(modelDataWrapper)
        upper_wall_1_r5.setPos(21f, -14f, 0f)
        head_tcl.addChild(upper_wall_1_r5)
        setRotationAngle(upper_wall_1_r5, 0f, 0f, -0.1396f)
        upper_wall_1_r5.texOffs(0, 72).addBox(-3f, -19f, 4f, 3, 19, 6, 0f, true)

        head_ael = ModelMapper(modelDataWrapper)
        head_ael.setPos(0f, 24f, 0f)
        head_ael.texOffs(208, 0).addBox(-18f, -41f, -2f, 36, 41, 0, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(158, 306).addBox(-20f, 0f, -24f, 40, 1, 28, 0f, false)
        head_exterior.texOffs(232, 110).addBox(18f, -14f, -24f, 3, 14, 34, 0f, true)
        head_exterior.texOffs(232, 110).addBox(-21f, -14f, -24f, 3, 14, 34, 0f, false)
        head_exterior.texOffs(200, 69).addBox(-18f, -41f, -3f, 36, 41, 0, 0f, false)

        upper_wall_2_r7 = ModelMapper(modelDataWrapper)
        upper_wall_2_r7.setPos(-21f, -14f, 0f)
        head_exterior.addChild(upper_wall_2_r7)
        setRotationAngle(upper_wall_2_r7, 0f, 0f, 0.1396f)
        upper_wall_2_r7.texOffs(250, 237).addBox(0f, -23f, -24f, 3, 23, 34, 0f, false)

        upper_wall_1_r6 = ModelMapper(modelDataWrapper)
        upper_wall_1_r6.setPos(21f, -14f, 0f)
        head_exterior.addChild(upper_wall_1_r6)
        setRotationAngle(upper_wall_1_r6, 0f, 0f, -0.1396f)
        upper_wall_1_r6.texOffs(250, 237).addBox(-3f, -23f, -24f, 3, 23, 34, 0f, true)

        floor_2_r5 = ModelMapper(modelDataWrapper)
        floor_2_r5.setPos(-21f, 0f, 0f)
        head_exterior.addChild(floor_2_r5)
        setRotationAngle(floor_2_r5, 0f, 0f, -0.1745f)
        floor_2_r5.texOffs(90, 252).addBox(0f, 0f, -24f, 1, 8, 32, 0f, false)

        floor_1_r4 = ModelMapper(modelDataWrapper)
        floor_1_r4.setPos(21f, 0f, 0f)
        head_exterior.addChild(floor_1_r4)
        setRotationAngle(floor_1_r4, 0f, 0f, 0.1745f)
        floor_1_r4.texOffs(90, 252).addBox(-1f, 0f, -24f, 1, 8, 32, 0f, true)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)
        front.texOffs(142, 76).addBox(-9f, 0.9884f, -46.7528f, 18, 2, 0, 0f, false)
        front.texOffs(172, 51).addBox(-21f, 0f, -24f, 42, 8, 0, 0f, false)

        front_bottom_5_r1 = ModelMapper(modelDataWrapper)
        front_bottom_5_r1.setPos(0f, 4.4033f, -45.3383f)
        front.addChild(front_bottom_5_r1)
        setRotationAngle(front_bottom_5_r1, 1.4137f, 0f, 0f)
        front_bottom_5_r1.texOffs(256, 42).addBox(-20f, 0f, -0.001f, 40, 22, 0, 0f, false)

        front_bottom_4_r1 = ModelMapper(modelDataWrapper)
        front_bottom_4_r1.setPos(0f, 3.6962f, -46.0454f)
        front.addChild(front_bottom_4_r1)
        setRotationAngle(front_bottom_4_r1, 0.7854f, 0f, 0f)
        front_bottom_4_r1.texOffs(0, 98).addBox(-12f, -1f, 0f, 24, 2, 0, 0f, false)

        front_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_bottom_2_r1.setPos(0f, 0.1009f, -46.292f)
        front.addChild(front_bottom_2_r1)
        setRotationAngle(front_bottom_2_r1, -0.48f, 0f, 0f)
        front_bottom_2_r1.texOffs(58, 120).addBox(-11f, -1f, 0f, 22, 2, 0, 0f, false)

        front_bottom_1_r1 = ModelMapper(modelDataWrapper)
        front_bottom_1_r1.setPos(0f, -4f, -42f)
        front.addChild(front_bottom_1_r1)
        setRotationAngle(front_bottom_1_r1, -0.8727f, 0f, 0f)
        front_bottom_1_r1.texOffs(154, 43).addBox(-12f, 0f, 0f, 24, 5, 0, 0f, false)

        front_panel_3_r1 = ModelMapper(modelDataWrapper)
        front_panel_3_r1.setPos(0f, -32.2269f, -28.3321f)
        front.addChild(front_panel_3_r1)
        setRotationAngle(front_panel_3_r1, -0.6283f, 0f, 0f)
        front_panel_3_r1.texOffs(211, 250).addBox(-12f, -6.5f, 0f, 24, 12, 0, 0f, false)

        front_panel_2_r1 = ModelMapper(modelDataWrapper)
        front_panel_2_r1.setPos(0f, -20.5874f, -35.0728f)
        front.addChild(front_panel_2_r1)
        setRotationAngle(front_panel_2_r1, -0.4538f, 0f, 0f)
        front_panel_2_r1.texOffs(90, 232).addBox(-12f, -8f, 0f, 24, 16, 0, 0f, false)

        front_panel_1_r1 = ModelMapper(modelDataWrapper)
        front_panel_1_r1.setPos(0f, -4f, -42f)
        front.addChild(front_panel_1_r1)
        setRotationAngle(front_panel_1_r1, -0.3491f, 0f, 0f)
        front_panel_1_r1.texOffs(200, 110).addBox(-12f, -10f, 0f, 24, 10, 0, 0f, false)

        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        front.addChild(side_1)


        front_side_bottom_2_r1 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r1.setPos(18.3378f, 3.5923f, -35.3251f)
        side_1.addChild(front_side_bottom_2_r1)
        setRotationAngle(front_side_bottom_2_r1, 0f, 0.1745f, 0.1745f)
        front_side_bottom_2_r1.texOffs(112, 157).addBox(0f, -4f, -6.5f, 0, 8, 18, 0f, true)

        front_middle_top_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_r1.setPos(-0.6613f, -41.5702f, -11.9997f)
        side_1.addChild(front_middle_top_r1)
        setRotationAngle(front_middle_top_r1, 0f, 0.3491f, -1.5708f)
        front_middle_top_r1.texOffs(232, 118).addBox(0f, 0f, -14f, 0, 6, 14, 0f, true)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(16.824f, -34.5499f, -16.9825f)
        side_1.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0f, 0.1745f, -1.0472f)
        outer_roof_8_r1.texOffs(70, 66).addBox(2.5f, -5f, -3.5f, 0, 6, 9, 0f, true)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(17.2991f, -37.6418f, 0f)
        side_1.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, -0.5236f)
        outer_roof_7_r1.texOffs(22, 25).addBox(0f, -1f, -19f, 0, 2, 7, 0f, true)

        outer_roof_6_r4 = ModelMapper(modelDataWrapper)
        outer_roof_6_r4.setPos(11.7869f, -37.8295f, -19.0477f)
        side_1.addChild(outer_roof_6_r4)
        setRotationAngle(outer_roof_6_r4, 0f, 0.3491f, -1.3963f)
        outer_roof_6_r4.texOffs(72, 180).addBox(0f, -7f, -7.5f, 0, 14, 15, 0f, true)

        front_panel_8_r1 = ModelMapper(modelDataWrapper)
        front_panel_8_r1.setPos(8.9994f, 2.9884f, -46.7521f)
        side_1.addChild(front_panel_8_r1)
        setRotationAngle(front_panel_8_r1, 0f, -0.5672f, 0f)
        front_panel_8_r1.texOffs(212, 217).addBox(0f, -5.9884f, -0.001f, 11, 8, 0, 0f, true)

        front_panel_7_r1 = ModelMapper(modelDataWrapper)
        front_panel_7_r1.setPos(12f, -4f, -42f)
        side_1.addChild(front_panel_7_r1)
        setRotationAngle(front_panel_7_r1, -0.7418f, -0.3491f, 0f)
        front_panel_7_r1.texOffs(208, 42).addBox(-4f, 0f, 0f, 11, 5, 0, 0f, true)

        front_panel_6_r1 = ModelMapper(modelDataWrapper)
        front_panel_6_r1.setPos(12f, -4f, -42f)
        side_1.addChild(front_panel_6_r1)
        setRotationAngle(front_panel_6_r1, -0.3491f, -0.3491f, 0f)
        front_panel_6_r1.texOffs(162, 196).addBox(0f, -12f, 0f, 9, 12, 0, 0f, true)

        front_panel_5_r1 = ModelMapper(modelDataWrapper)
        front_panel_5_r1.setPos(12f, -13.397f, -38.5798f)
        side_1.addChild(front_panel_5_r1)
        setRotationAngle(front_panel_5_r1, -0.4538f, -0.3491f, 0f)
        front_panel_5_r1.texOffs(112, 139).addBox(0f, -18f, -0.001f, 10, 18, 0, 0f, true)

        front_panel_4_r1 = ModelMapper(modelDataWrapper)
        front_panel_4_r1.setPos(15.1229f, -32.2269f, -26.988f)
        side_1.addChild(front_panel_4_r1)
        setRotationAngle(front_panel_4_r1, -0.6283f, -0.3491f, 0f)
        front_panel_4_r1.texOffs(0, 228).addBox(-4.5f, -4.5f, 0f, 9, 8, 0, 0f, true)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(21f, -14f, -24f)
        side_1.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, 0.1745f, -0.1396f)
        front_side_upper_2_r1.texOffs(0, 37).addBox(0f, -21f, -12f, 0, 21, 12, 0f, true)

        front_side_lower_2_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r1.setPos(21f, 0f, -24f)
        side_1.addChild(front_side_lower_2_r1)
        setRotationAngle(front_side_lower_2_r1, 0f, 0.1745f, 0f)
        front_side_lower_2_r1.texOffs(0, 102).addBox(0f, -14f, -18f, 0, 14, 18, 0f, true)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(21f, 0f, -9f)
        front.addChild(side_2)


        front_side_bottom_2_r2 = ModelMapper(modelDataWrapper)
        front_side_bottom_2_r2.setPos(-39.3378f, 3.5923f, -26.3251f)
        side_2.addChild(front_side_bottom_2_r2)
        setRotationAngle(front_side_bottom_2_r2, 0f, -0.1745f, -0.1745f)
        front_side_bottom_2_r2.texOffs(112, 157).addBox(0f, -4f, -6.5f, 0, 8, 18, 0f, false)

        front_middle_top_r2 = ModelMapper(modelDataWrapper)
        front_middle_top_r2.setPos(-20.3387f, -41.5702f, -2.9997f)
        side_2.addChild(front_middle_top_r2)
        setRotationAngle(front_middle_top_r2, 0f, -0.3491f, 1.5708f)
        front_middle_top_r2.texOffs(232, 118).addBox(0f, 0f, -14f, 0, 6, 14, 0f, false)

        outer_roof_8_r2 = ModelMapper(modelDataWrapper)
        outer_roof_8_r2.setPos(-37.824f, -34.5499f, -7.9825f)
        side_2.addChild(outer_roof_8_r2)
        setRotationAngle(outer_roof_8_r2, 0f, -0.1745f, 1.0472f)
        outer_roof_8_r2.texOffs(70, 66).addBox(-2.5f, -5f, -3.5f, 0, 6, 9, 0f, false)

        outer_roof_7_r2 = ModelMapper(modelDataWrapper)
        outer_roof_7_r2.setPos(-38.2991f, -37.6418f, 9f)
        side_2.addChild(outer_roof_7_r2)
        setRotationAngle(outer_roof_7_r2, 0f, 0f, 0.5236f)
        outer_roof_7_r2.texOffs(22, 25).addBox(0f, -1f, -19f, 0, 2, 7, 0f, false)

        outer_roof_6_r5 = ModelMapper(modelDataWrapper)
        outer_roof_6_r5.setPos(-32.7869f, -37.8295f, -10.0477f)
        side_2.addChild(outer_roof_6_r5)
        setRotationAngle(outer_roof_6_r5, 0f, -0.3491f, 1.3963f)
        outer_roof_6_r5.texOffs(72, 180).addBox(0f, -7f, -7.5f, 0, 14, 15, 0f, false)

        front_panel_8_r2 = ModelMapper(modelDataWrapper)
        front_panel_8_r2.setPos(-29.9994f, 2.9884f, -37.7521f)
        side_2.addChild(front_panel_8_r2)
        setRotationAngle(front_panel_8_r2, 0f, 0.5672f, 0f)
        front_panel_8_r2.texOffs(212, 217).addBox(-11f, -5.9884f, -0.001f, 11, 8, 0, 0f, false)

        front_panel_7_r2 = ModelMapper(modelDataWrapper)
        front_panel_7_r2.setPos(-33f, -4f, -33f)
        side_2.addChild(front_panel_7_r2)
        setRotationAngle(front_panel_7_r2, -0.7418f, 0.3491f, 0f)
        front_panel_7_r2.texOffs(208, 42).addBox(-7f, 0f, 0f, 11, 5, 0, 0f, false)

        front_panel_6_r2 = ModelMapper(modelDataWrapper)
        front_panel_6_r2.setPos(-33f, -4f, -33f)
        side_2.addChild(front_panel_6_r2)
        setRotationAngle(front_panel_6_r2, -0.3491f, 0.3491f, 0f)
        front_panel_6_r2.texOffs(162, 196).addBox(-9f, -12f, 0f, 9, 12, 0, 0f, false)

        front_panel_5_r2 = ModelMapper(modelDataWrapper)
        front_panel_5_r2.setPos(-33f, -13.397f, -29.5798f)
        side_2.addChild(front_panel_5_r2)
        setRotationAngle(front_panel_5_r2, -0.4538f, 0.3491f, 0f)
        front_panel_5_r2.texOffs(112, 139).addBox(-10f, -18f, -0.001f, 10, 18, 0, 0f, false)

        front_panel_4_r2 = ModelMapper(modelDataWrapper)
        front_panel_4_r2.setPos(-36.1229f, -32.2269f, -17.988f)
        side_2.addChild(front_panel_4_r2)
        setRotationAngle(front_panel_4_r2, -0.6283f, 0.3491f, 0f)
        front_panel_4_r2.texOffs(0, 228).addBox(-4.5f, -4.5f, 0f, 9, 8, 0, 0f, false)

        front_side_upper_2_r2 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r2.setPos(-42f, -14f, -15f)
        side_2.addChild(front_side_upper_2_r2)
        setRotationAngle(front_side_upper_2_r2, 0f, -0.1745f, 0.1396f)
        front_side_upper_2_r2.texOffs(0, 37).addBox(0f, -21f, -12f, 0, 21, 12, 0f, false)

        front_side_lower_2_r2 = ModelMapper(modelDataWrapper)
        front_side_lower_2_r2.setPos(-42f, 0f, -15f)
        side_2.addChild(front_side_lower_2_r2)
        setRotationAngle(front_side_lower_2_r2, 0f, -0.1745f, 0f)
        front_side_lower_2_r2.texOffs(0, 102).addBox(0f, -14f, -18f, 0, 14, 18, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_2b_r1 = ModelMapper(modelDataWrapper)
        headlight_2b_r1.setPos(0f, -4f, -42.1f)
        headlights.addChild(headlight_2b_r1)
        setRotationAngle(headlight_2b_r1, -0.3491f, 0f, 0f)
        headlight_2b_r1.texOffs(20, 4).addBox(8f, -4f, 0f, 4, 4, 0, 0f, true)
        headlight_2b_r1.texOffs(20, 4).addBox(-12f, -4f, 0f, 4, 4, 0, 0f, false)

        headlight_2a_r1 = ModelMapper(modelDataWrapper)
        headlight_2a_r1.setPos(12f, -4f, -42.1f)
        headlights.addChild(headlight_2a_r1)
        setRotationAngle(headlight_2a_r1, -0.3491f, -0.3491f, 0f)
        headlight_2a_r1.texOffs(20, 0).addBox(0f, -4f, 0f, 6, 4, 0, 0f, true)

        headlight_1a_r1 = ModelMapper(modelDataWrapper)
        headlight_1a_r1.setPos(-12f, -4f, -42.1f)
        headlights.addChild(headlight_1a_r1)
        setRotationAngle(headlight_1a_r1, -0.3491f, 0.3491f, 0f)
        headlight_1a_r1.texOffs(20, 0).addBox(-6f, -4f, 0f, 6, 4, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_2a_r1 = ModelMapper(modelDataWrapper)
        tail_light_2a_r1.setPos(12f, -4f, -42f)
        tail_lights.addChild(tail_light_2a_r1)
        setRotationAngle(tail_light_2a_r1, -0.3491f, -0.3491f, 0f)
        tail_light_2a_r1.texOffs(20, 8).addBox(0f, -4f, -0.1f, 6, 4, 0, 0f, true)

        tail_light_1a_r1 = ModelMapper(modelDataWrapper)
        tail_light_1a_r1.setPos(-12f, -4f, -42f)
        tail_lights.addChild(tail_light_1a_r1)
        setRotationAngle(tail_light_1a_r1, -0.3491f, 0.3491f, 0f)
        tail_light_1a_r1.texOffs(20, 8).addBox(-6f, -4f, -0.1f, 6, 4, 0, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(16, 34).addBox(-3f, -5f, -3f, 6, 1, 6, 0f, false)
        seat.texOffs(24, 52).addBox(-1.5f, -16.4f, 4.5f, 3, 2, 1, 0f, false)

        top_right_r1 = ModelMapper(modelDataWrapper)
        top_right_r1.setPos(-1.5f, -16.4f, 4.5f)
        seat.addChild(top_right_r1)
        setRotationAngle(top_right_r1, 0.0017f, 0f, 0.2618f)
        top_right_r1.texOffs(32, 52).addBox(0f, 0f, 0f, 1, 2, 1, 0f, false)

        top_left_r1 = ModelMapper(modelDataWrapper)
        top_left_r1.setPos(1.5f, -16.4f, 4.5f)
        seat.addChild(top_left_r1)
        setRotationAngle(top_left_r1, 0.0017f, 0f, -0.2618f)
        top_left_r1.texOffs(32, 52).addBox(-1f, 0f, 0f, 1, 2, 1, 0f, true)

        back_right_r1 = ModelMapper(modelDataWrapper)
        back_right_r1.setPos(-1.5f, -5f, 2f)
        seat.addChild(back_right_r1)
        setRotationAngle(back_right_r1, -0.2618f, -0.1745f, 0.0873f)
        back_right_r1.texOffs(32, 41).addBox(-1.5f, -9.75f, 0f, 2, 10, 1, 0f, false)

        back_left_r1 = ModelMapper(modelDataWrapper)
        back_left_r1.setPos(1.5f, -5f, 2f)
        seat.addChild(back_left_r1)
        setRotationAngle(back_left_r1, -0.2618f, 0.1745f, -0.0873f)
        back_left_r1.texOffs(32, 41).addBox(-0.5f, -9.75f, 0f, 2, 10, 1, 0f, true)

        back_r1 = ModelMapper(modelDataWrapper)
        back_r1.setPos(3f, -5f, 2f)
        seat.addChild(back_r1)
        setRotationAngle(back_r1, -0.2618f, 0f, 0f)
        back_r1.texOffs(24, 41).addBox(-4.5f, -10f, 0f, 3, 10, 1, 0f, true)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-21f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1396f)
        light_r1.texOffs(20, 15).addBox(0f, -21.5f, -0.5f, 0, 0, 1, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-21f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1396f)
        light_r2.texOffs(23, 15).addBox(0f, -21.5f, -0.5f, 0, 0, 1, 0.4f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window_tcl.setModelPart()
        window_tcl_handrails.setModelPart()
        window_ael.setModelPart()
        window_exterior_tcl.setModelPart()
        window_exterior_ael.setModelPart()
        window_exterior_end_tcl.setModelPart()
        window_exterior_end_ael.setModelPart()
        side_panel_tcl.setModelPart()
        side_panel_tcl_translucent.setModelPart()
        side_panel_ael.setModelPart()
        side_panel_ael_translucent.setModelPart()
        roof_window_tcl.setModelPart()
        roof_window_ael.setModelPart()
        roof_door_tcl.setModelPart()
        roof_door_ael.setModelPart()
        roof_exterior.setModelPart()
        door_tcl.setModelPart()
        door_left_tcl.setModelPart(door_tcl.name)
        door_right_tcl.setModelPart(door_tcl.name)
        door_tcl_handrail.setModelPart()
        door_ael.setModelPart()
        door_left_ael.setModelPart(door_ael.name)
        door_right_ael.setModelPart(door_ael.name)
        door_ael_handrail.setModelPart()
        door_exterior_tcl.setModelPart()
        door_left_exterior_tcl.setModelPart(door_exterior_tcl.name)
        door_right_exterior_tcl.setModelPart(door_exterior_tcl.name)
        door_exterior_ael.setModelPart()
        door_left_exterior_ael.setModelPart(door_exterior_ael.name)
        door_right_exterior_ael.setModelPart(door_exterior_ael.name)
        door_exterior_end.setModelPart()
        door_left_exterior_end.setModelPart(door_exterior_end.name)
        door_right_exterior_end.setModelPart(door_exterior_end.name)
        luggage_rack.setModelPart()
        end_tcl.setModelPart()
        end_ael.setModelPart()
        end_exterior_tcl.setModelPart()
        end_exterior_ael.setModelPart()
        end_door_ael.setModelPart()
        roof_end.setModelPart()
        roof_end_exterior.setModelPart()
        roof_light_tcl.setModelPart()
        roof_light_door_ael.setModelPart()
        roof_light_window_ael.setModelPart()
        roof_end_light.setModelPart()
        head_tcl.setModelPart()
        head_ael.setModelPart()
        head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        seat.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelATrain {
        return ModelATrain(isAel, doorAnimationType, renderDoorOverlay)
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
            RenderStage.LIGHTS -> renderMirror(
                if (isAel) roof_light_window_ael else roof_light_tcl,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderMirror(if (isAel) window_ael else window_tcl, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    if (!isAel) {
                        renderMirror(window_tcl_handrails, matrices, vertices, light, position.toFloat())
                        renderMirror(side_panel_tcl, matrices, vertices, light, position - 22f)
                        renderMirror(side_panel_tcl, matrices, vertices, light, position + 22f)
                    }
                    renderMirror(
                        if (isAel) roof_window_ael else roof_window_tcl,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (!isAel) {
                renderMirror(side_panel_tcl_translucent, matrices, vertices, light, position - 22f)
                renderMirror(side_panel_tcl_translucent, matrices, vertices, light, position + 22f)
            }

            RenderStage.EXTERIOR -> {
                if (isAel) {
                    val isHeadWindows = isEnd1Head && (isIndex(0, position, getWindowPositions()) || isIndex(
                        1,
                        position,
                        getWindowPositions()
                    ) || isIndex(2, position, getWindowPositions()))
                    val isEndWindows = isEnd2Head && (isIndex(-1, position, getWindowPositions()) || isIndex(
                        -2,
                        position,
                        getWindowPositions()
                    ) || isIndex(-3, position, getWindowPositions()))
                    if (!isHeadWindows && !isEndWindows) {
                        renderMirror(window_exterior_ael, matrices, vertices, light, position.toFloat())
                    }
                } else {
                    if (isIndex(0, position, getWindowPositions()) && isEnd1Head) {
                        renderOnceFlipped(window_exterior_end_tcl, matrices, vertices, light, position.toFloat())
                    } else if (isIndex(-1, position, getWindowPositions()) && isEnd2Head) {
                        renderOnce(window_exterior_end_tcl, matrices, vertices, light, position.toFloat())
                    } else {
                        renderMirror(window_exterior_tcl, matrices, vertices, light, position.toFloat())
                    }
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
        val notLastDoor = !isIndex(0, position, getDoorPositions()) && !isIndex(-1, position, getDoorPositions())

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                if (isAel) {
                    renderMirror(roof_light_door_ael, matrices, vertices, light, position.toFloat())
                } else if (notLastDoor) {
                    renderMirror(roof_light_tcl, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && doorOpen && renderDetails) {
                    renderMirror(
                        door_light_on,
                        matrices,
                        vertices,
                        light,
                        (position - (if (isAel) 80 else 40)).toFloat()
                    )
                }
            }

            RenderStage.INTERIOR -> if (isAel) {
                door_left_ael.setOffset(doorRightX, 0, doorRightZ)
                door_right_ael.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door_ael, matrices, vertices, light, position.toFloat())
                door_left_ael.setOffset(doorLeftX, 0, doorLeftZ)
                door_right_ael.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door_ael, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    renderMirror(door_ael_handrail, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_door_ael, matrices, vertices, light, position.toFloat())
                    renderMirror(luggage_rack, matrices, vertices, light, (position - 20).toFloat())
                    renderMirror(luggage_rack, matrices, vertices, light, (position + 20).toFloat())
                    renderMirror(side_panel_ael, matrices, vertices, light, position - 28.1f)
                    renderMirror(side_panel_ael, matrices, vertices, light, position - 11.9f)
                    renderMirror(side_panel_ael, matrices, vertices, light, position + 11.9f)
                    renderMirror(side_panel_ael, matrices, vertices, light, position + 28.1f)

                    run {
                        var z = position + 40
                        while (z <= position + 74) {
                            renderOnce(seat, matrices, vertices, light, 15f, z.toFloat())
                            renderOnce(seat, matrices, vertices, light, 8.5f, z.toFloat())
                            renderOnce(seat, matrices, vertices, light, -8.5f, z.toFloat())
                            renderOnce(seat, matrices, vertices, light, -15f, z.toFloat())
                            z += 17
                        }
                    }
                    var z = position - 74
                    while (z <= position - 40) {
                        renderOnceFlipped(seat, matrices, vertices, light, 15f, z.toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, 8.5f, z.toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, -8.5f, z.toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, -15f, z.toFloat())
                        z += 17
                    }
                }
            } else {
                door_left_tcl.setOffset(doorRightX, 0, doorRightZ)
                door_right_tcl.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door_tcl, matrices, vertices, light, position.toFloat())
                door_left_tcl.setOffset(doorLeftX, 0, doorLeftZ)
                door_right_tcl.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door_tcl, matrices, vertices, light, position.toFloat())

                if (renderDetails) {
                    renderOnce(door_tcl_handrail, matrices, vertices, light, position.toFloat())
                    if (notLastDoor) {
                        renderMirror(roof_door_tcl, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isAel) {
                renderMirror(side_panel_ael_translucent, matrices, vertices, light, position - 28.1f)
                renderMirror(side_panel_ael_translucent, matrices, vertices, light, position - 11.9f)
                renderMirror(side_panel_ael_translucent, matrices, vertices, light, position + 11.9f)
                renderMirror(side_panel_ael_translucent, matrices, vertices, light, position + 28.1f)
            }

            RenderStage.EXTERIOR -> {
                if (isAel) {
                    door_left_exterior_ael.setOffset(doorRightX, 0, doorRightZ)
                    door_right_exterior_ael.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_exterior_ael, matrices, vertices, light, position.toFloat())
                    door_left_exterior_ael.setOffset(doorLeftX, 0, doorLeftZ)
                    door_right_exterior_ael.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_ael, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position - 2).toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position + 2).toFloat())
                } else {
                    val door1End = isIndex(0, position, getDoorPositions()) && isEnd1Head
                    val door2End = isIndex(-1, position, getDoorPositions()) && isEnd2Head

                    if (door1End || door2End) {
                        door_left_exterior_end.setOffset(doorRightX, 0, doorRightZ)
                        door_right_exterior_end.setOffset(doorRightX, 0, -doorRightZ)
                        renderOnce(door_exterior_end, matrices, vertices, light, position.toFloat())
                    } else {
                        door_left_exterior_tcl.setOffset(doorRightX, 0, doorRightZ)
                        door_right_exterior_tcl.setOffset(doorRightX, 0, -doorRightZ)
                        renderOnce(door_exterior_tcl, matrices, vertices, light, position.toFloat())
                    }

                    if (door1End || door2End) {
                        door_left_exterior_end.setOffset(doorLeftX, 0, doorLeftZ)
                        door_right_exterior_end.setOffset(doorLeftX, 0, -doorLeftZ)
                        renderOnceFlipped(door_exterior_end, matrices, vertices, light, position.toFloat())
                    } else {
                        door_left_exterior_tcl.setOffset(doorLeftX, 0, doorLeftZ)
                        door_right_exterior_tcl.setOffset(doorLeftX, 0, -doorLeftZ)
                        renderOnceFlipped(door_exterior_tcl, matrices, vertices, light, position.toFloat())
                    }
                    renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && !doorOpen && renderDetails) {
                    renderMirror(
                        door_light_off,
                        matrices,
                        vertices,
                        light,
                        (position - (if (isAel) 80 else 40)).toFloat()
                    )
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
            RenderStage.LIGHTS -> if (!isAel) {
                renderOnce(roof_end_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnce(if (isAel) head_ael else head_tcl, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    if (isAel) {
                        renderOnceFlipped(seat, matrices, vertices, light, 15f, (position + 13).toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, 8.5f, (position + 13).toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, -15f, (position + 13).toFloat())
                        renderOnceFlipped(seat, matrices, vertices, light, -8.5f, (position + 13).toFloat())
                    } else {
                        renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
                if (isAel) {
                    renderOnce(window_exterior_end_ael, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position + 30).toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position + 70).toFloat())
                }
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
            RenderStage.LIGHTS -> if (!isAel) {
                renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnceFlipped(if (isAel) head_ael else head_tcl, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    if (isAel) {
                        renderOnce(seat, matrices, vertices, light, 15f, (position - 13).toFloat())
                        renderOnce(seat, matrices, vertices, light, 8.5f, (position - 13).toFloat())
                        renderOnce(seat, matrices, vertices, light, -15f, (position - 13).toFloat())
                        renderOnce(seat, matrices, vertices, light, -8.5f, (position - 13).toFloat())
                    } else {
                        renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
                if (isAel) {
                    renderOnceFlipped(window_exterior_end_ael, matrices, vertices, light, position.toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position - 30).toFloat())
                    renderMirror(roof_exterior, matrices, vertices, light, (position - 70).toFloat())
                }
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
            RenderStage.LIGHTS -> if (!isAel) {
                renderOnce(roof_end_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> if (isAel) {
                renderOnce(end_ael, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(end_door_ael, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(seat, matrices, vertices, light, 15f, (position + 13).toFloat())
                    renderOnceFlipped(seat, matrices, vertices, light, 8.5f, (position + 13).toFloat())
                    renderOnceFlipped(seat, matrices, vertices, light, -15f, (position + 13).toFloat())
                    renderOnceFlipped(seat, matrices, vertices, light, -8.5f, (position + 13).toFloat())
                }
            } else {
                renderOnce(end_tcl, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(
                    if (isAel) end_exterior_ael else end_exterior_tcl,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
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
            RenderStage.LIGHTS -> if (!isAel) {
                renderOnceFlipped(roof_end_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> if (isAel) {
                renderOnceFlipped(end_ael, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(end_door_ael, matrices, vertices, light, position.toFloat())
                    renderOnce(seat, matrices, vertices, light, 15f, (position - 13).toFloat())
                    renderOnce(seat, matrices, vertices, light, 8.5f, (position - 13).toFloat())
                    renderOnce(seat, matrices, vertices, light, -15f, (position - 13).toFloat())
                    renderOnce(seat, matrices, vertices, light, -8.5f, (position - 13).toFloat())
                }
            } else {
                renderOnceFlipped(end_tcl, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(
                    if (isAel) end_exterior_ael else end_exterior_tcl,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
            }

            else -> {}
        }
    }

    @Override
    override fun getModelDoorOverlay(): ModelDoorOverlay? {
        return if (isAel) null else MODEL_DOOR_OVERLAY
    }

    @Override
    override fun getModelDoorOverlayTop(): ModelDoorOverlayTopBase? {
        return null
    }

    @Override
    override fun getWindowPositions(): IntArray? {
        return if (isAel) intArrayOf(-173, -147, -121, -39, -13, 13, 39, 121, 147, 173) else intArrayOf(
            -120,
            -40,
            40,
            120
        )
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return if (isAel) intArrayOf(-80, 80) else intArrayOf(-160, -80, 0, 80, 160)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-184, 184)
    }

    @Override
    override fun getDoorMax(): Int {
        return if (isAel) DOOR_MAX_AEL else DOOR_MAX_TCL
    }

    @Override
    override fun getDoorDuration(): Float {
        return 0.5f * (if (isAel) DOOR_MAX_AEL.toFloat() / DOOR_MAX_TCL else 1f)
    }

    companion object {
        private const val DOOR_MAX_TCL = 14
        private const val DOOR_MAX_AEL = 11
        private val MODEL_DOOR_OVERLAY = ModelDoorOverlay(
            DOOR_MAX_TCL,
            8f,
            "door_overlay_a_train_tcl_left.png",
            "door_overlay_a_train_tcl_right.png"
        )
    }
}
