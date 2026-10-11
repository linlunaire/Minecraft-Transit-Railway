package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelA320 protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelA320?>(doorAnimationType, renderDoorOverlay) {
    private val exterior: ModelMapper
    private val main: ModelMapper
    private val right_roof_r1: ModelMapper
    private val left_roof_r1: ModelMapper
    private val right_top_wall_r1: ModelMapper
    private val left_top_wall_r1: ModelMapper
    private val right_bottom_wall_r1: ModelMapper
    private val left_bottom_wall_r1: ModelMapper
    private val right_floor_r1: ModelMapper
    private val left_floor_r1: ModelMapper
    private val front: ModelMapper
    private val top_right_back_r1: ModelMapper
    private val top_left_back_r1: ModelMapper
    private val bottom_right_r1: ModelMapper
    private val bottom_left_r1: ModelMapper
    private val bottom_front_right_r1: ModelMapper
    private val bottom_front_left_r1: ModelMapper
    private val windscreen_lower_right_r1: ModelMapper
    private val windscreen_lower_left_r1: ModelMapper
    private val windscreen_right_r1: ModelMapper
    private val windscreen_left_r1: ModelMapper
    private val right_wall_front_r1: ModelMapper
    private val left_wall_front_r1: ModelMapper
    private val right_wall_top_r1: ModelMapper
    private val left_wall_top_r1: ModelMapper
    private val right_wall_lower_r1: ModelMapper
    private val left_wall_lower_r1: ModelMapper
    private val right_wall_r1: ModelMapper
    private val left_wall_r1: ModelMapper
    private val top_back_r1: ModelMapper
    private val top_front_r1: ModelMapper
    private val bottom_r1: ModelMapper
    private val bottom_front_r1: ModelMapper
    private val windscreen_main_r1: ModelMapper
    private val windscreen_lower_r1: ModelMapper
    private val front_lower_r1: ModelMapper
    private val front_upper_r1: ModelMapper
    private val tail: ModelMapper
    private val top_r1: ModelMapper
    private val bottom_back_r1: ModelMapper
    private val bottom_r2: ModelMapper
    private val bottom_right_r2: ModelMapper
    private val bottom_left_r2: ModelMapper
    private val side_right_r1: ModelMapper
    private val side_left_r1: ModelMapper
    private val right_roof_top_r1: ModelMapper
    private val left_roof_top_r1: ModelMapper
    private val right_wall_top_r2: ModelMapper
    private val left_wall_top_r2: ModelMapper
    private val right_wall_back_r1: ModelMapper
    private val left_wall_back_r1: ModelMapper
    private val right_wall_r2: ModelMapper
    private val left_wall_r2: ModelMapper
    private val left_wing: ModelMapper
    private val edge_wing_bottom_r1: ModelMapper
    private val edge_wing_middle_r1: ModelMapper
    private val outer_bottom_cover_r1: ModelMapper
    private val outer_top_cover_r1: ModelMapper
    private val middle_top_cover_r1: ModelMapper
    private val back_flat_bottom_edge_r1: ModelMapper
    private val back_flat_top_edge_r1: ModelMapper
    private val back_outer_bottom_edge_r1: ModelMapper
    private val back_outer_top_edge_r1: ModelMapper
    private val back_middle_bottom_edge_r1: ModelMapper
    private val back_middle_top_edge_r1: ModelMapper
    private val front_outer_top_edge_r1: ModelMapper
    private val front_outer_top_edge_r2: ModelMapper
    private val front_middle_bottom_edge_r1: ModelMapper
    private val front_middle_top_edge_r1: ModelMapper
    private val right_wing: ModelMapper
    private val edge_wing_bottom_r2: ModelMapper
    private val edge_wing_middle_r2: ModelMapper
    private val outer_bottom_cover_r2: ModelMapper
    private val outer_top_cover_r2: ModelMapper
    private val middle_top_cover_r2: ModelMapper
    private val back_flat_bottom_edge_r2: ModelMapper
    private val back_flat_top_edge_r2: ModelMapper
    private val back_outer_bottom_edge_r2: ModelMapper
    private val back_outer_top_edge_r2: ModelMapper
    private val back_middle_bottom_edge_r2: ModelMapper
    private val back_middle_top_edge_r2: ModelMapper
    private val front_outer_top_edge_r3: ModelMapper
    private val front_outer_top_edge_r4: ModelMapper
    private val front_middle_bottom_edge_r2: ModelMapper
    private val front_middle_top_edge_r2: ModelMapper
    private val back_top_wing: ModelMapper
    private val back_r1: ModelMapper
    private val front_bottom_r1: ModelMapper
    private val front_top_r1: ModelMapper
    private val back_left_wing: ModelMapper
    private val back_r2: ModelMapper
    private val side_r1: ModelMapper
    private val front_r1: ModelMapper
    private val back_right_wing: ModelMapper
    private val back_r3: ModelMapper
    private val side_r2: ModelMapper
    private val front_r2: ModelMapper
    private val bottom: ModelMapper
    private val back_top_right_r1: ModelMapper
    private val back_top_left_r1: ModelMapper
    private val back_side_right_r1: ModelMapper
    private val back_side_left_r1: ModelMapper
    private val back_bottom_right_r1: ModelMapper
    private val back_bottom_left_r1: ModelMapper
    private val front_top_right_r1: ModelMapper
    private val front_top_left_r1: ModelMapper
    private val front_side_right_r1: ModelMapper
    private val front_side_left_r1: ModelMapper
    private val front_bottom_right_r1: ModelMapper
    private val front_bottom_left_r1: ModelMapper
    private val side_right_r2: ModelMapper
    private val side_left_r2: ModelMapper
    private val bottom_right_r3: ModelMapper
    private val bottom_left_r3: ModelMapper
    private val back_side_r1: ModelMapper
    private val back_bottom_r1: ModelMapper
    private val front_side_r1: ModelMapper
    private val front_bottom_r2: ModelMapper
    private val left_engine: ModelMapper
    private val tail_right_roof_r1: ModelMapper
    private val tail_left_roof_r1: ModelMapper
    private val tail_roof_r1: ModelMapper
    private val tail_right_wall_bottom_r1: ModelMapper
    private val tail_right_wall_top_r1: ModelMapper
    private val tail_right_wall_r1: ModelMapper
    private val tail_left_wall_bottom_r1: ModelMapper
    private val tail_left_wall_top_r1: ModelMapper
    private val tail_left_wall_r1: ModelMapper
    private val tail_right_floor_r1: ModelMapper
    private val tail_left_floor_r1: ModelMapper
    private val tail_floor_r1: ModelMapper
    private val back_right_roof_r1: ModelMapper
    private val back_left_roof_r1: ModelMapper
    private val back_roof_r1: ModelMapper
    private val back_right_wall_bottom_r1: ModelMapper
    private val back_right_wall_top_r1: ModelMapper
    private val back_right_wall_r1: ModelMapper
    private val back_left_wall_bottom_r1: ModelMapper
    private val back_left_wall_top_r1: ModelMapper
    private val back_left_wall_r1: ModelMapper
    private val back_right_floor_r1: ModelMapper
    private val back_left_floor_r1: ModelMapper
    private val back_floor_r1: ModelMapper
    private val front_right_roof_r1: ModelMapper
    private val front_left_roof_r1: ModelMapper
    private val front_roof_r1: ModelMapper
    private val front_right_wall_bottom_r1: ModelMapper
    private val front_right_wall_top_r1: ModelMapper
    private val front_right_wall_r1: ModelMapper
    private val front_left_wall_bottom_r1: ModelMapper
    private val front_left_wall_top_r1: ModelMapper
    private val front_left_wall_r1: ModelMapper
    private val front_right_floor_r1: ModelMapper
    private val front_left_floor_r1: ModelMapper
    private val front_floor_r1: ModelMapper
    private val right_roof_r2: ModelMapper
    private val left_roof_r2: ModelMapper
    private val right_wall_bottom_r1: ModelMapper
    private val right_wall_top_r3: ModelMapper
    private val left_wall_bottom_r1: ModelMapper
    private val left_wall_top_r3: ModelMapper
    private val right_floor_r2: ModelMapper
    private val left_floor_r2: ModelMapper
    private val support_back_r1: ModelMapper
    private val support_top_r1: ModelMapper
    private val right_engine: ModelMapper
    private val tail_right_roof_r2: ModelMapper
    private val tail_left_roof_r2: ModelMapper
    private val tail_roof_r2: ModelMapper
    private val tail_right_wall_bottom_r2: ModelMapper
    private val tail_right_wall_top_r2: ModelMapper
    private val tail_right_wall_r2: ModelMapper
    private val tail_left_wall_bottom_r2: ModelMapper
    private val tail_left_wall_top_r2: ModelMapper
    private val tail_left_wall_r2: ModelMapper
    private val tail_right_floor_r2: ModelMapper
    private val tail_left_floor_r2: ModelMapper
    private val tail_floor_r2: ModelMapper
    private val back_right_roof_r2: ModelMapper
    private val back_left_roof_r2: ModelMapper
    private val back_roof_r2: ModelMapper
    private val back_right_wall_bottom_r2: ModelMapper
    private val back_right_wall_top_r2: ModelMapper
    private val back_right_wall_r2: ModelMapper
    private val back_left_wall_bottom_r2: ModelMapper
    private val back_left_wall_top_r2: ModelMapper
    private val back_left_wall_r2: ModelMapper
    private val back_right_floor_r2: ModelMapper
    private val back_left_floor_r2: ModelMapper
    private val back_floor_r2: ModelMapper
    private val front_right_roof_r2: ModelMapper
    private val front_left_roof_r2: ModelMapper
    private val front_roof_r2: ModelMapper
    private val front_right_wall_bottom_r2: ModelMapper
    private val front_right_wall_top_r2: ModelMapper
    private val front_right_wall_r2: ModelMapper
    private val front_left_wall_bottom_r2: ModelMapper
    private val front_left_wall_top_r2: ModelMapper
    private val front_left_wall_r2: ModelMapper
    private val front_right_floor_r2: ModelMapper
    private val front_left_floor_r2: ModelMapper
    private val front_floor_r2: ModelMapper
    private val right_roof_r3: ModelMapper
    private val left_roof_r3: ModelMapper
    private val right_wall_bottom_r2: ModelMapper
    private val right_wall_top_r4: ModelMapper
    private val left_wall_bottom_r2: ModelMapper
    private val left_wall_top_r4: ModelMapper
    private val right_floor_r3: ModelMapper
    private val left_floor_r3: ModelMapper
    private val support_back_r2: ModelMapper
    private val support_top_r2: ModelMapper
    private val window_interior: ModelMapper
    private val roof_side_r1: ModelMapper
    private val luggage_rack_top_r1: ModelMapper
    private val luggage_rack_front_r1: ModelMapper
    private val luggage_rack_bottom_front_r1: ModelMapper
    private val right_top_wall_r2: ModelMapper
    private val window_interior_wall: ModelMapper
    private val right_upper_wall_r1: ModelMapper
    private val right_lower_wall_r1: ModelMapper
    private val window_interior_light: ModelMapper
    private val light_r1: ModelMapper
    private val window_interior_blank: ModelMapper
    private val roof_side_r2: ModelMapper
    private val luggage_rack_top_r2: ModelMapper
    private val luggage_rack_front_r2: ModelMapper
    private val luggage_rack_bottom_front_r2: ModelMapper
    private val right_top_wall_r3: ModelMapper
    private val window_interior_blank_wall: ModelMapper
    private val right_upper_wall_r2: ModelMapper
    private val right_lower_wall_r2: ModelMapper
    private val window_interior_blank_light: ModelMapper
    private val light_r2: ModelMapper
    private val door_left_exterior: ModelMapper
    private val right_wall_front_r2: ModelMapper
    private val right_top_wall_front_r1: ModelMapper
    private val right_top_wall_r4: ModelMapper
    private val top_back_r2: ModelMapper
    private val door_left_interior: ModelMapper
    private val right_wall_front_r3: ModelMapper
    private val right_top_wall_front_r2: ModelMapper
    private val right_top_wall_r5: ModelMapper
    private val top_back_r3: ModelMapper
    private val door_right_exterior: ModelMapper
    private val right_wall_front_r4: ModelMapper
    private val right_top_wall_front_r3: ModelMapper
    private val right_top_wall_r6: ModelMapper
    private val top_back_r4: ModelMapper
    private val door_right_interior: ModelMapper
    private val right_wall_front_r5: ModelMapper
    private val right_top_wall_front_r4: ModelMapper
    private val right_top_wall_r7: ModelMapper
    private val top_back_r5: ModelMapper
    private val head_interior: ModelMapper
    private val right_door_top_r1: ModelMapper
    private val left_door_top_r1: ModelMapper
    private val roof_front_r1: ModelMapper
    private val right_roof_side_r1: ModelMapper
    private val left_roof_side_r1: ModelMapper
    private val right_upper_wall_r3: ModelMapper
    private val left_upper_wall_r1: ModelMapper
    private val right_wall_r3: ModelMapper
    private val left_wall_r3: ModelMapper
    private val right_lower_wall_r3: ModelMapper
    private val left_lower_wall_r1: ModelMapper
    private val end_interior: ModelMapper
    private val right_luggage_rack_top_r1: ModelMapper
    private val left_luggage_rack_top_r1: ModelMapper
    private val right_luggage_rack_front_r1: ModelMapper
    private val left_luggage_rack_front_r1: ModelMapper
    private val right_luggage_rack_bottom_front_r1: ModelMapper
    private val left_luggage_rack_bottom_front_r1: ModelMapper
    private val right_top_wall_r8: ModelMapper
    private val left_top_wall_r2: ModelMapper
    private val right_roof_side_r2: ModelMapper
    private val left_roof_side_r2: ModelMapper
    private val right_upper_wall_r4: ModelMapper
    private val left_upper_wall_r2: ModelMapper
    private val right_wall_r4: ModelMapper
    private val left_wall_r4: ModelMapper
    private val right_lower_wall_r4: ModelMapper
    private val left_lower_wall_r2: ModelMapper
    private val end_light: ModelMapper
    private val left_light_r1: ModelMapper
    private val right_light_r1: ModelMapper
    private val emergency_exit: ModelMapper
    private val right_upper_wall_r5: ModelMapper
    private val right_lower_wall_r5: ModelMapper
    private val left_upper_wall_r3: ModelMapper
    private val left_lower_wall_r3: ModelMapper
    private val seat_nice: ModelMapper
    private val back_bottom_right_r2: ModelMapper
    private val seat_normal: ModelMapper
    private val back_bottom_right_r3: ModelMapper

    constructor() : this(DoorAnimationType.PLUG_SLOW, true)

    init {
        val textureWidth = 1024
        val textureHeight = 1024

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        exterior = ModelMapper(modelDataWrapper)
        exterior.setPos(0f, 24f, 0f)


        main = ModelMapper(modelDataWrapper)
        main.setPos(0f, 0f, 0f)
        exterior.addChild(main)
        main.texOffs(0, 0).addBox(-9f, -33f, -191f, 18, 0, 367, 0f, false)
        main.texOffs(43, 0).addBox(-9f, 33f, -251f, 18, 0, 360, 0f, false)
        main.texOffs(0, 20).addBox(32f, -10f, -225f, 0, 20, 380, 0f, false)
        main.texOffs(0, 0).addBox(-32f, -10f, -225f, 0, 20, 380, 0f, false)

        right_roof_r1 = ModelMapper(modelDataWrapper)
        right_roof_r1.setPos(-9f, -33f, 0f)
        main.addChild(right_roof_r1)
        setRotationAngle(right_roof_r1, 0f, 0f, -0.5236f)
        right_roof_r1.texOffs(161, 0).addBox(-17f, 0f, -191f, 17, 0, 346, 0f, false)

        left_roof_r1 = ModelMapper(modelDataWrapper)
        left_roof_r1.setPos(9f, -33f, 0f)
        main.addChild(left_roof_r1)
        setRotationAngle(left_roof_r1, 0f, 0f, 0.5236f)
        left_roof_r1.texOffs(195, 0).addBox(0f, 0f, -191f, 17, 0, 346, 0f, false)

        right_top_wall_r1 = ModelMapper(modelDataWrapper)
        right_top_wall_r1.setPos(-32f, -10f, 0f)
        main.addChild(right_top_wall_r1)
        setRotationAngle(right_top_wall_r1, 0f, 0f, 0.5236f)
        right_top_wall_r1.texOffs(0, 40).addBox(0f, -17f, -225f, 0, 17, 380, 0f, false)

        left_top_wall_r1 = ModelMapper(modelDataWrapper)
        left_top_wall_r1.setPos(32f, -10f, 0f)
        main.addChild(left_top_wall_r1)
        setRotationAngle(left_top_wall_r1, 0f, 0f, -0.5236f)
        left_top_wall_r1.texOffs(0, 57).addBox(0f, -17f, -225f, 0, 17, 380, 0f, false)

        right_bottom_wall_r1 = ModelMapper(modelDataWrapper)
        right_bottom_wall_r1.setPos(-32f, 10f, 0f)
        main.addChild(right_bottom_wall_r1)
        setRotationAngle(right_bottom_wall_r1, 0f, 0f, -0.5236f)
        right_bottom_wall_r1.texOffs(0, 111).addBox(0f, 0f, -251f, 0, 17, 360, 0f, false)

        left_bottom_wall_r1 = ModelMapper(modelDataWrapper)
        left_bottom_wall_r1.setPos(32f, 10f, 0f)
        main.addChild(left_bottom_wall_r1)
        setRotationAngle(left_bottom_wall_r1, 0f, 0f, 0.5236f)
        left_bottom_wall_r1.texOffs(0, 94).addBox(0f, 0f, -251f, 0, 17, 360, 0f, false)

        right_floor_r1 = ModelMapper(modelDataWrapper)
        right_floor_r1.setPos(-9f, 33f, 0f)
        main.addChild(right_floor_r1)
        setRotationAngle(right_floor_r1, 0f, 0f, 0.5236f)
        right_floor_r1.texOffs(113, 0).addBox(-17f, 0f, -251f, 17, 0, 360, 0f, false)

        left_floor_r1 = ModelMapper(modelDataWrapper)
        left_floor_r1.setPos(9f, 33f, 0f)
        main.addChild(left_floor_r1)
        setRotationAngle(left_floor_r1, 0f, 0f, -0.5236f)
        left_floor_r1.texOffs(79, 0).addBox(0f, 0f, -251f, 17, 0, 360, 0f, false)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        exterior.addChild(front)
        front.texOffs(52, 110).addBox(-3f, 8f, -301f, 6, 6, 0, 0f, false)
        front.texOffs(124, 897).addBox(17f, -24f, -229f, 15, 32, 0, 0f, false)
        front.texOffs(124, 897).addBox(-32f, -24f, -229f, 15, 32, 0, 0f, true)
        front.texOffs(154, 900).addBox(18f, -25f, -213f, 14, 33, 0, 0f, false)
        front.texOffs(154, 900).addBox(-32f, -25f, -213f, 14, 33, 0, 0f, true)

        top_right_back_r1 = ModelMapper(modelDataWrapper)
        top_right_back_r1.setPos(-9f, -33f, -191f)
        front.addChild(top_right_back_r1)
        setRotationAngle(top_right_back_r1, 0.0873f, 0f, -0.5236f)
        top_right_back_r1.texOffs(0, 0).addBox(-22f, 0f, -64f, 22, 0, 64, 0f, false)

        top_left_back_r1 = ModelMapper(modelDataWrapper)
        top_left_back_r1.setPos(9f, -33f, -191f)
        front.addChild(top_left_back_r1)
        setRotationAngle(top_left_back_r1, 0.0873f, 0f, 0.5236f)
        top_left_back_r1.texOffs(0, 64).addBox(0f, 0f, -64f, 22, 0, 64, 0f, false)

        bottom_right_r1 = ModelMapper(modelDataWrapper)
        bottom_right_r1.setPos(-9f, 33f, -251f)
        front.addChild(bottom_right_r1)
        setRotationAngle(bottom_right_r1, -0.1396f, 0f, 0.5236f)
        bottom_right_r1.texOffs(72, 581).addBox(-17f, 0f, -22f, 17, 0, 22, 0f, false)

        bottom_left_r1 = ModelMapper(modelDataWrapper)
        bottom_left_r1.setPos(9f, 33f, -251f)
        front.addChild(bottom_left_r1)
        setRotationAngle(bottom_left_r1, -0.1396f, 0f, -0.5236f)
        bottom_left_r1.texOffs(305, 606).addBox(0f, 0f, -22f, 17, 0, 22, 0f, false)

        bottom_front_right_r1 = ModelMapper(modelDataWrapper)
        bottom_front_right_r1.setPos(-3f, 29f, -280f)
        front.addChild(bottom_front_right_r1)
        setRotationAngle(bottom_front_right_r1, -0.4363f, 0f, 0.5236f)
        bottom_front_right_r1.texOffs(78, 109).addBox(-18f, 0f, -21f, 18, 0, 30, 0f, false)

        bottom_front_left_r1 = ModelMapper(modelDataWrapper)
        bottom_front_left_r1.setPos(3f, 29f, -280f)
        front.addChild(bottom_front_left_r1)
        setRotationAngle(bottom_front_left_r1, -0.4363f, 0f, -0.5236f)
        bottom_front_left_r1.texOffs(201, 109).addBox(0f, 0f, -21f, 18, 0, 30, 0f, false)

        windscreen_lower_right_r1 = ModelMapper(modelDataWrapper)
        windscreen_lower_right_r1.setPos(-9f, -7f, -280f)
        front.addChild(windscreen_lower_right_r1)
        setRotationAngle(windscreen_lower_right_r1, -0.6109f, 1.0472f, 0f)
        windscreen_lower_right_r1.texOffs(758, 593).addBox(-39f, 0f, 0f, 56, 10, 0, 0f, false)

        windscreen_lower_left_r1 = ModelMapper(modelDataWrapper)
        windscreen_lower_left_r1.setPos(9f, -7f, -280f)
        front.addChild(windscreen_lower_left_r1)
        setRotationAngle(windscreen_lower_left_r1, -0.6109f, -1.0472f, 0f)
        windscreen_lower_left_r1.texOffs(760, 375).addBox(-17f, 0f, 0f, 56, 10, 0, 0f, false)

        windscreen_right_r1 = ModelMapper(modelDataWrapper)
        windscreen_right_r1.setPos(-9f, -7f, -280f)
        front.addChild(windscreen_right_r1)
        setRotationAngle(windscreen_right_r1, -0.5236f, 1.0472f, 0f)
        windscreen_right_r1.texOffs(68, 766).addBox(-42f, -21f, 0f, 42, 21, 0, 0f, false)

        windscreen_left_r1 = ModelMapper(modelDataWrapper)
        windscreen_left_r1.setPos(9f, -7f, -280f)
        front.addChild(windscreen_left_r1)
        setRotationAngle(windscreen_left_r1, -0.5236f, -1.0472f, 0f)
        windscreen_left_r1.texOffs(767, 754).addBox(0f, -21f, 0f, 42, 21, 0, 0f, false)

        right_wall_front_r1 = ModelMapper(modelDataWrapper)
        right_wall_front_r1.setPos(-3f, -1f, -301f)
        front.addChild(right_wall_front_r1)
        setRotationAngle(right_wall_front_r1, 0f, -0.5236f, 0f)
        right_wall_front_r1.texOffs(0, 511).addBox(0f, -2f, 0f, 0, 27, 45, 0f, false)

        left_wall_front_r1 = ModelMapper(modelDataWrapper)
        left_wall_front_r1.setPos(3f, -1f, -301f)
        front.addChild(left_wall_front_r1)
        setRotationAngle(left_wall_front_r1, 0f, 0.5236f, 0f)
        left_wall_front_r1.texOffs(0, 538).addBox(0f, -2f, 0f, 0, 27, 45, 0f, false)

        right_wall_top_r1 = ModelMapper(modelDataWrapper)
        right_wall_top_r1.setPos(-32f, -10f, -225f)
        front.addChild(right_wall_top_r1)
        setRotationAngle(right_wall_top_r1, 0f, -0.1745f, 0.5236f)
        right_wall_top_r1.texOffs(577, 714).addBox(0f, -13f, -19f, 0, 13, 19, 0f, false)

        left_wall_top_r1 = ModelMapper(modelDataWrapper)
        left_wall_top_r1.setPos(32f, -10f, -225f)
        front.addChild(left_wall_top_r1)
        setRotationAngle(left_wall_top_r1, 0f, 0.1745f, -0.5236f)
        left_wall_top_r1.texOffs(577, 727).addBox(0f, -13f, -19f, 0, 13, 19, 0f, false)

        right_wall_lower_r1 = ModelMapper(modelDataWrapper)
        right_wall_lower_r1.setPos(-32f, 10f, -251f)
        front.addChild(right_wall_lower_r1)
        setRotationAngle(right_wall_lower_r1, 0f, -0.1745f, -0.5236f)
        right_wall_lower_r1.texOffs(220, 680).addBox(0f, 9f, -22f, 0, 9, 22, 0f, false)

        left_wall_lower_r1 = ModelMapper(modelDataWrapper)
        left_wall_lower_r1.setPos(32f, 10f, -251f)
        front.addChild(left_wall_lower_r1)
        setRotationAngle(left_wall_lower_r1, 0f, 0.1745f, 0.5236f)
        left_wall_lower_r1.texOffs(627, 721).addBox(0f, 9f, -22f, 0, 9, 22, 0f, false)

        right_wall_r1 = ModelMapper(modelDataWrapper)
        right_wall_r1.setPos(-32f, 0f, -225f)
        front.addChild(right_wall_r1)
        setRotationAngle(right_wall_r1, 0f, -0.1745f, 0f)
        right_wall_r1.texOffs(0, 137).addBox(0f, -10f, -38f, 0, 28, 38, 0f, false)

        left_wall_r1 = ModelMapper(modelDataWrapper)
        left_wall_r1.setPos(32f, 0f, -225f)
        front.addChild(left_wall_r1)
        setRotationAngle(left_wall_r1, 0f, 0.1745f, 0f)
        left_wall_r1.texOffs(0, 165).addBox(0f, -10f, -38f, 0, 28, 38, 0f, false)

        top_back_r1 = ModelMapper(modelDataWrapper)
        top_back_r1.setPos(0f, -33f, -191f)
        front.addChild(top_back_r1)
        setRotationAngle(top_back_r1, 0.0873f, 0f, 0f)
        top_back_r1.texOffs(128, 685).addBox(-9f, 0f, -56f, 18, 0, 56, 0f, false)

        top_front_r1 = ModelMapper(modelDataWrapper)
        top_front_r1.setPos(0f, -24.5002f, -254.5527f)
        front.addChild(top_front_r1)
        setRotationAngle(top_front_r1, 0.3491f, 0f, 0f)
        top_front_r1.texOffs(556, 109).addBox(-12f, -0.5f, -9.5f, 24, 0, 19, 0f, false)

        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(0f, 33f, -251f)
        front.addChild(bottom_r1)
        setRotationAngle(bottom_r1, -0.1396f, 0f, 0f)
        bottom_r1.texOffs(545, 79).addBox(-9f, 0f, -30f, 18, 0, 30, 0f, false)

        bottom_front_r1 = ModelMapper(modelDataWrapper)
        bottom_front_r1.setPos(0f, 29f, -280f)
        front.addChild(bottom_front_r1)
        setRotationAngle(bottom_front_r1, -0.4363f, 0f, 0f)
        bottom_front_r1.texOffs(114, 86).addBox(-3f, 0f, -19f, 6, 0, 19, 0f, false)

        windscreen_main_r1 = ModelMapper(modelDataWrapper)
        windscreen_main_r1.setPos(0f, -7f, -280f)
        front.addChild(windscreen_main_r1)
        setRotationAngle(windscreen_main_r1, 0.733f, 0f, 0f)
        windscreen_main_r1.texOffs(304, 317).addBox(-9f, 0f, 0f, 18, 0, 22, 0f, false)

        windscreen_lower_r1 = ModelMapper(modelDataWrapper)
        windscreen_lower_r1.setPos(0f, -7f, -280f)
        front.addChild(windscreen_lower_r1)
        setRotationAngle(windscreen_lower_r1, 0.4363f, 0f, 0f)
        windscreen_lower_r1.texOffs(558, 714).addBox(-9f, 0f, -19f, 18, 0, 19, 0f, false)

        front_lower_r1 = ModelMapper(modelDataWrapper)
        front_lower_r1.setPos(0f, 14f, -301f)
        front.addChild(front_lower_r1)
        setRotationAngle(front_lower_r1, -1.0472f, 0f, 0f)
        front_lower_r1.texOffs(127, 307).addBox(-5f, 0f, 0f, 10, 0, 9, 0f, false)

        front_upper_r1 = ModelMapper(modelDataWrapper)
        front_upper_r1.setPos(0f, 8f, -301f)
        front.addChild(front_upper_r1)
        setRotationAngle(front_upper_r1, 1.0472f, 0f, 0f)
        front_upper_r1.texOffs(327, 258).addBox(-6f, 0f, 0f, 12, 0, 9, 0f, false)

        tail = ModelMapper(modelDataWrapper)
        tail.setPos(0f, 0f, 0f)
        exterior.addChild(tail)
        tail.texOffs(100, 256).addBox(-3f, -22f, 301f, 6, 11, 0, 0f, false)

        top_r1 = ModelMapper(modelDataWrapper)
        top_r1.setPos(0f, -22f, 301f)
        tail.addChild(top_r1)
        setRotationAngle(top_r1, -0.0873f, 0f, 0f)
        top_r1.texOffs(155, 109).addBox(-6f, 0f, -127f, 12, 0, 127, 0f, false)

        bottom_back_r1 = ModelMapper(modelDataWrapper)
        bottom_back_r1.setPos(0f, -11f, 301f)
        tail.addChild(bottom_back_r1)
        setRotationAngle(bottom_back_r1, 0.2618f, 0f, 0f)
        bottom_back_r1.texOffs(0, 175).addBox(-11f, 0f, -115f, 22, 0, 115, 0f, false)

        bottom_r2 = ModelMapper(modelDataWrapper)
        bottom_r2.setPos(0f, 33f, 109f)
        tail.addChild(bottom_r2)
        setRotationAngle(bottom_r2, 0.1745f, 0f, 0f)
        bottom_r2.texOffs(223, 109).addBox(-11f, 0f, 0f, 22, 0, 83, 0f, false)

        bottom_right_r2 = ModelMapper(modelDataWrapper)
        bottom_right_r2.setPos(-9f, 33f, 109f)
        tail.addChild(bottom_right_r2)
        setRotationAngle(bottom_right_r2, 0.1396f, 0f, 0.5236f)
        bottom_right_r2.texOffs(497, 165).addBox(-17f, 0f, 0f, 17, 0, 78, 0f, false)

        bottom_left_r2 = ModelMapper(modelDataWrapper)
        bottom_left_r2.setPos(9f, 33f, 109f)
        tail.addChild(bottom_left_r2)
        setRotationAngle(bottom_left_r2, 0.1396f, 0f, -0.5236f)
        bottom_left_r2.texOffs(531, 165).addBox(0f, 0f, 0f, 17, 0, 78, 0f, false)

        side_right_r1 = ModelMapper(modelDataWrapper)
        side_right_r1.setPos(-32f, 10f, 109f)
        tail.addChild(side_right_r1)
        setRotationAngle(side_right_r1, 0f, 0.1745f, -0.5236f)
        side_right_r1.texOffs(0, 297).addBox(0f, -15f, 0f, 0, 34, 191, 0f, false)

        side_left_r1 = ModelMapper(modelDataWrapper)
        side_left_r1.setPos(32f, 10f, 109f)
        tail.addChild(side_left_r1)
        setRotationAngle(side_left_r1, 0f, -0.1745f, 0.5236f)
        side_left_r1.texOffs(0, 331).addBox(0f, -15f, 0f, 0, 34, 191, 0f, false)

        right_roof_top_r1 = ModelMapper(modelDataWrapper)
        right_roof_top_r1.setPos(-9f, -33f, 155f)
        tail.addChild(right_roof_top_r1)
        setRotationAngle(right_roof_top_r1, -0.0873f, 0f, -0.5236f)
        right_roof_top_r1.texOffs(0, 0).addBox(-17f, 0f, 0f, 21, 0, 147, 0f, false)

        left_roof_top_r1 = ModelMapper(modelDataWrapper)
        left_roof_top_r1.setPos(9f, -33f, 155f)
        tail.addChild(left_roof_top_r1)
        setRotationAngle(left_roof_top_r1, -0.0873f, 0f, 0.5236f)
        left_roof_top_r1.texOffs(42, 0).addBox(-4f, 0f, 0f, 21, 0, 147, 0f, false)

        right_wall_top_r2 = ModelMapper(modelDataWrapper)
        right_wall_top_r2.setPos(-32f, -10f, 155f)
        tail.addChild(right_wall_top_r2)
        setRotationAngle(right_wall_top_r2, 0f, 0.0873f, 0.5236f)
        right_wall_top_r2.texOffs(0, 214).addBox(0f, -17f, 0f, 0, 17, 76, 0f, false)

        left_wall_top_r2 = ModelMapper(modelDataWrapper)
        left_wall_top_r2.setPos(32f, -10f, 155f)
        tail.addChild(left_wall_top_r2)
        setRotationAngle(left_wall_top_r2, 0f, -0.0873f, -0.5236f)
        left_wall_top_r2.texOffs(184, 592).addBox(0f, -17f, 0f, 0, 17, 76, 0f, false)

        right_wall_back_r1 = ModelMapper(modelDataWrapper)
        right_wall_back_r1.setPos(-3f, 0f, 301f)
        tail.addChild(right_wall_back_r1)
        setRotationAngle(right_wall_back_r1, 0f, 0.5236f, 0f)
        right_wall_back_r1.texOffs(342, 329).addBox(0f, -22f, -10f, 0, 13, 10, 0f, false)

        left_wall_back_r1 = ModelMapper(modelDataWrapper)
        left_wall_back_r1.setPos(3f, 0f, 301f)
        tail.addChild(left_wall_back_r1)
        setRotationAngle(left_wall_back_r1, 0f, -0.5236f, 0f)
        left_wall_back_r1.texOffs(865, 205).addBox(0f, -22f, -10f, 0, 13, 10, 0f, false)

        right_wall_r2 = ModelMapper(modelDataWrapper)
        right_wall_r2.setPos(-32f, 0f, 155f)
        tail.addChild(right_wall_r2)
        setRotationAngle(right_wall_r2, 0f, 0.1745f, 0f)
        right_wall_r2.texOffs(0, 6).addBox(0f, -20f, 0f, 0, 14, 141, 0f, false)

        left_wall_r2 = ModelMapper(modelDataWrapper)
        left_wall_r2.setPos(32f, 0f, 155f)
        tail.addChild(left_wall_r2)
        setRotationAngle(left_wall_r2, 0f, -0.1745f, 0f)
        left_wall_r2.texOffs(0, 20).addBox(0f, -20f, 0f, 0, 14, 141, 0f, false)

        left_wing = ModelMapper(modelDataWrapper)
        left_wing.setPos(0f, 0f, 0f)
        exterior.addChild(left_wing)
        left_wing.texOffs(410, 678).addBox(271f, -58f, 31f, 0, 24, 36, 0f, false)
        left_wing.texOffs(633, 725).addBox(76f, 8f, -42f, 5, 7, 56, 0f, false)
        left_wing.texOffs(511, 721).addBox(131f, 3f, -32f, 5, 7, 56, 0f, false)
        left_wing.texOffs(758, 502).addBox(189f, -3f, -6f, 5, 7, 50, 0f, false)

        edge_wing_bottom_r1 = ModelMapper(modelDataWrapper)
        edge_wing_bottom_r1.setPos(255f, -18f, 39f)
        left_wing.addChild(edge_wing_bottom_r1)
        setRotationAngle(edge_wing_bottom_r1, 0f, 0f, 1.0472f)
        edge_wing_bottom_r1.texOffs(0, 574).addBox(0f, -12f, -24f, 0, 24, 36, 0f, false)

        edge_wing_middle_r1 = ModelMapper(modelDataWrapper)
        edge_wing_middle_r1.setPos(271f, -34f, 39f)
        left_wing.addChild(edge_wing_middle_r1)
        setRotationAngle(edge_wing_middle_r1, 0f, 0f, 0.5236f)
        edge_wing_middle_r1.texOffs(0, 598).addBox(0f, 0f, -18f, 0, 12, 36, 0f, false)

        outer_bottom_cover_r1 = ModelMapper(modelDataWrapper)
        outer_bottom_cover_r1.setPos(255f, -14f, 35f)
        left_wing.addChild(outer_bottom_cover_r1)
        setRotationAngle(outer_bottom_cover_r1, 0f, -0.4712f, -0.1745f)
        outer_bottom_cover_r1.texOffs(557, 147).addBox(-135f, 0f, 0f, 125, 0, 18, 0f, false)

        outer_top_cover_r1 = ModelMapper(modelDataWrapper)
        outer_top_cover_r1.setPos(255f, -18f, 35f)
        left_wing.addChild(outer_top_cover_r1)
        setRotationAngle(outer_top_cover_r1, 0f, -0.4712f, -0.1745f)
        outer_top_cover_r1.texOffs(685, 644).addBox(-72f, 0f, 0f, 79, 0, 13, 0f, false)

        middle_top_cover_r1 = ModelMapper(modelDataWrapper)
        middle_top_cover_r1.setPos(32f, 7f, -80f)
        left_wing.addChild(middle_top_cover_r1)
        setRotationAngle(middle_top_cover_r1, 0f, -0.4712f, -0.0873f)
        middle_top_cover_r1.texOffs(511, 0).addBox(0f, 0f, 0f, 189, 0, 64, 0f, false)

        back_flat_bottom_edge_r1 = ModelMapper(modelDataWrapper)
        back_flat_bottom_edge_r1.setPos(32f, 13f, -9f)
        left_wing.addChild(back_flat_bottom_edge_r1)
        setRotationAngle(back_flat_bottom_edge_r1, 0.2618f, 0f, -0.0873f)
        back_flat_bottom_edge_r1.texOffs(575, 79).addBox(-9f, -1f, -37f, 85, 1, 49, 0f, false)

        back_flat_top_edge_r1 = ModelMapper(modelDataWrapper)
        back_flat_top_edge_r1.setPos(32f, 7f, -9f)
        left_wing.addChild(back_flat_top_edge_r1)
        setRotationAngle(back_flat_top_edge_r1, -0.0873f, 0f, -0.0873f)
        back_flat_top_edge_r1.texOffs(720, 474).addBox(-1f, 0f, 0f, 74, 1, 12, 0f, false)

        back_outer_bottom_edge_r1 = ModelMapper(modelDataWrapper)
        back_outer_bottom_edge_r1.setPos(255f, -14f, 35f)
        left_wing.addChild(back_outer_bottom_edge_r1)
        setRotationAngle(back_outer_bottom_edge_r1, 0.0873f, -0.2793f, -0.1745f)
        back_outer_bottom_edge_r1.texOffs(652, 697).addBox(-88f, -1f, 0f, 85, 1, 12, 0f, false)

        back_outer_top_edge_r1 = ModelMapper(modelDataWrapper)
        back_outer_top_edge_r1.setPos(255f, -18f, 35f)
        left_wing.addChild(back_outer_top_edge_r1)
        setRotationAngle(back_outer_top_edge_r1, -0.1745f, -0.2793f, -0.1745f)
        back_outer_top_edge_r1.texOffs(725, 281).addBox(-66f, 0f, 0f, 66, 1, 12, 0f, false)

        back_middle_bottom_edge_r1 = ModelMapper(modelDataWrapper)
        back_middle_bottom_edge_r1.setPos(32f, 13f, -30f)
        left_wing.addChild(back_middle_bottom_edge_r1)
        setRotationAngle(back_middle_bottom_edge_r1, 0.2618f, -0.2793f, -0.0873f)
        back_middle_bottom_edge_r1.texOffs(94, 606).addBox(73f, -1f, -17f, 102, 1, 29, 0f, false)

        back_middle_top_edge_r1 = ModelMapper(modelDataWrapper)
        back_middle_top_edge_r1.setPos(32f, 7f, -30f)
        left_wing.addChild(back_middle_top_edge_r1)
        setRotationAngle(back_middle_top_edge_r1, -0.0873f, -0.2793f, -0.0873f)
        back_middle_top_edge_r1.texOffs(507, 347).addBox(-10f, 0f, 0f, 194, 1, 12, 0f, false)

        front_outer_top_edge_r1 = ModelMapper(modelDataWrapper)
        front_outer_top_edge_r1.setPos(255f, -14f, 35f)
        left_wing.addChild(front_outer_top_edge_r1)
        setRotationAngle(front_outer_top_edge_r1, -0.0873f, -0.4712f, -0.1745f)
        front_outer_top_edge_r1.texOffs(460, 684).addBox(-98f, -1f, -14f, 88, 1, 14, 0f, false)

        front_outer_top_edge_r2 = ModelMapper(modelDataWrapper)
        front_outer_top_edge_r2.setPos(255f, -18f, 35f)
        left_wing.addChild(front_outer_top_edge_r2)
        setRotationAngle(front_outer_top_edge_r2, 0.0873f, -0.4712f, -0.1745f)
        front_outer_top_edge_r2.texOffs(614, 710).addBox(-77f, 0f, -14f, 77, 1, 14, 0f, false)

        front_middle_bottom_edge_r1 = ModelMapper(modelDataWrapper)
        front_middle_bottom_edge_r1.setPos(32f, 13f, -80f)
        left_wing.addChild(front_middle_bottom_edge_r1)
        setRotationAngle(front_middle_bottom_edge_r1, -0.2618f, -0.4712f, -0.0873f)
        front_middle_bottom_edge_r1.texOffs(331, 552).addBox(-7f, -1f, -14f, 188, 1, 51, 0f, false)

        front_middle_top_edge_r1 = ModelMapper(modelDataWrapper)
        front_middle_top_edge_r1.setPos(32f, 7f, -80f)
        left_wing.addChild(front_middle_top_edge_r1)
        setRotationAngle(front_middle_top_edge_r1, 0.0873f, -0.4712f, -0.0873f)
        front_middle_top_edge_r1.texOffs(575, 64).addBox(-8f, 0f, -14f, 191, 1, 14, 0f, false)

        right_wing = ModelMapper(modelDataWrapper)
        right_wing.setPos(0f, 0f, 0f)
        exterior.addChild(right_wing)
        right_wing.texOffs(287, 237).addBox(-271f, -58f, 31f, 0, 24, 36, 0f, false)
        right_wing.texOffs(720, 398).addBox(-81f, 8f, -42f, 5, 7, 56, 0f, false)
        right_wing.texOffs(445, 714).addBox(-136f, 3f, -32f, 5, 7, 56, 0f, false)
        right_wing.texOffs(124, 749).addBox(-194f, -3f, -6f, 5, 7, 50, 0f, false)

        edge_wing_bottom_r2 = ModelMapper(modelDataWrapper)
        edge_wing_bottom_r2.setPos(-255f, -18f, 39f)
        right_wing.addChild(edge_wing_bottom_r2)
        setRotationAngle(edge_wing_bottom_r2, 0f, 0f, -1.0472f)
        edge_wing_bottom_r2.texOffs(0, 195).addBox(0f, -12f, -24f, 0, 24, 36, 0f, false)

        edge_wing_middle_r2 = ModelMapper(modelDataWrapper)
        edge_wing_middle_r2.setPos(-271f, -34f, 39f)
        right_wing.addChild(edge_wing_middle_r2)
        setRotationAngle(edge_wing_middle_r2, 0f, 0f, -0.5236f)
        edge_wing_middle_r2.texOffs(287, 268).addBox(0f, 0f, -18f, 0, 12, 36, 0f, false)

        outer_bottom_cover_r2 = ModelMapper(modelDataWrapper)
        outer_bottom_cover_r2.setPos(-255f, -14f, 35f)
        right_wing.addChild(outer_bottom_cover_r2)
        setRotationAngle(outer_bottom_cover_r2, 0f, 0.4712f, 0.1745f)
        outer_bottom_cover_r2.texOffs(557, 129).addBox(10f, 0f, 0f, 125, 0, 18, 0f, false)

        outer_top_cover_r2 = ModelMapper(modelDataWrapper)
        outer_top_cover_r2.setPos(-255f, -18f, 35f)
        right_wing.addChild(outer_top_cover_r2)
        setRotationAngle(outer_top_cover_r2, 0f, 0.4712f, 0.1745f)
        outer_top_cover_r2.texOffs(685, 631).addBox(-7f, 0f, 0f, 79, 0, 13, 0f, false)

        middle_top_cover_r2 = ModelMapper(modelDataWrapper)
        middle_top_cover_r2.setPos(-32f, 7f, -80f)
        right_wing.addChild(middle_top_cover_r2)
        setRotationAngle(middle_top_cover_r2, 0f, 0.4712f, 0.0873f)
        middle_top_cover_r2.texOffs(318, 488).addBox(-189f, 0f, 0f, 189, 0, 64, 0f, false)

        back_flat_bottom_edge_r2 = ModelMapper(modelDataWrapper)
        back_flat_bottom_edge_r2.setPos(-32f, 13f, -9f)
        right_wing.addChild(back_flat_bottom_edge_r2)
        setRotationAngle(back_flat_bottom_edge_r2, 0.2618f, 0f, 0.0873f)
        back_flat_bottom_edge_r2.texOffs(94, 556).addBox(-76f, -1f, -37f, 85, 1, 49, 0f, false)

        back_flat_top_edge_r2 = ModelMapper(modelDataWrapper)
        back_flat_top_edge_r2.setPos(-32f, 7f, -9f)
        right_wing.addChild(back_flat_top_edge_r2)
        setRotationAngle(back_flat_top_edge_r2, -0.0873f, 0f, 0.0873f)
        back_flat_top_edge_r2.texOffs(720, 461).addBox(-73f, 0f, 0f, 74, 1, 12, 0f, false)

        back_outer_bottom_edge_r2 = ModelMapper(modelDataWrapper)
        back_outer_bottom_edge_r2.setPos(-255f, -14f, 35f)
        right_wing.addChild(back_outer_bottom_edge_r2)
        setRotationAngle(back_outer_bottom_edge_r2, 0.0873f, 0.2793f, 0.1745f)
        back_outer_bottom_edge_r2.texOffs(650, 684).addBox(3f, -1f, 0f, 85, 1, 12, 0f, false)

        back_outer_top_edge_r2 = ModelMapper(modelDataWrapper)
        back_outer_top_edge_r2.setPos(-255f, -18f, 35f)
        right_wing.addChild(back_outer_top_edge_r2)
        setRotationAngle(back_outer_top_edge_r2, -0.1745f, 0.2793f, 0.1745f)
        back_outer_top_edge_r2.texOffs(206, 353).addBox(0f, 0f, 0f, 66, 1, 12, 0f, false)

        back_middle_bottom_edge_r2 = ModelMapper(modelDataWrapper)
        back_middle_bottom_edge_r2.setPos(-32f, 13f, -30f)
        right_wing.addChild(back_middle_bottom_edge_r2)
        setRotationAngle(back_middle_bottom_edge_r2, 0.2618f, 0.2793f, 0.0873f)
        back_middle_bottom_edge_r2.texOffs(374, 604).addBox(-175f, -1f, -17f, 102, 1, 29, 0f, false)

        back_middle_top_edge_r2 = ModelMapper(modelDataWrapper)
        back_middle_top_edge_r2.setPos(-32f, 7f, -30f)
        right_wing.addChild(back_middle_top_edge_r2)
        setRotationAngle(back_middle_top_edge_r2, -0.0873f, 0.2793f, 0.0873f)
        back_middle_top_edge_r2.texOffs(0, 367).addBox(-184f, 0f, 0f, 194, 1, 12, 0f, false)

        front_outer_top_edge_r3 = ModelMapper(modelDataWrapper)
        front_outer_top_edge_r3.setPos(-255f, -14f, 35f)
        right_wing.addChild(front_outer_top_edge_r3)
        setRotationAngle(front_outer_top_edge_r3, -0.0873f, 0.4712f, 0.1745f)
        front_outer_top_edge_r3.texOffs(673, 225).addBox(10f, -1f, -14f, 88, 1, 14, 0f, false)

        front_outer_top_edge_r4 = ModelMapper(modelDataWrapper)
        front_outer_top_edge_r4.setPos(-255f, -18f, 35f)
        right_wing.addChild(front_outer_top_edge_r4)
        setRotationAngle(front_outer_top_edge_r4, 0.0873f, 0.4712f, 0.1745f)
        front_outer_top_edge_r4.texOffs(446, 699).addBox(0f, 0f, -14f, 77, 1, 14, 0f, false)

        front_middle_bottom_edge_r2 = ModelMapper(modelDataWrapper)
        front_middle_bottom_edge_r2.setPos(-32f, 13f, -80f)
        right_wing.addChild(front_middle_bottom_edge_r2)
        setRotationAngle(front_middle_bottom_edge_r2, -0.2618f, 0.4712f, 0.0873f)
        front_middle_bottom_edge_r2.texOffs(524, 295).addBox(-181f, -1f, -14f, 188, 1, 51, 0f, false)

        front_middle_top_edge_r2 = ModelMapper(modelDataWrapper)
        front_middle_top_edge_r2.setPos(-32f, 7f, -80f)
        right_wing.addChild(front_middle_top_edge_r2)
        setRotationAngle(front_middle_top_edge_r2, 0.0873f, 0.4712f, 0.0873f)
        front_middle_top_edge_r2.texOffs(400, 360).addBox(-183f, 0f, -14f, 191, 1, 14, 0f, false)

        back_top_wing = ModelMapper(modelDataWrapper)
        back_top_wing.setPos(0f, 0f, 0f)
        exterior.addChild(back_top_wing)
        back_top_wing.texOffs(0, 556).addBox(-1f, -128f, 194f, 2, 103, 90, -0.1f, false)

        back_r1 = ModelMapper(modelDataWrapper)
        back_r1.setPos(0f, -128f, 285f)
        back_top_wing.addChild(back_r1)
        setRotationAngle(back_r1, -0.2269f, 0f, 0f)
        back_r1.texOffs(28, 0).addBox(-1f, 0f, -1f, 2, 106, 1, 0f, false)

        front_bottom_r1 = ModelMapper(modelDataWrapper)
        front_bottom_r1.setPos(0f, -33f, 162f)
        back_top_wing.addChild(front_bottom_r1)
        setRotationAngle(front_bottom_r1, -1.1345f, 0f, 0f)
        front_bottom_r1.texOffs(0, 749).addBox(-1f, -30f, 0f, 2, 30, 16, 0.1f, false)

        front_top_r1 = ModelMapper(modelDataWrapper)
        front_top_r1.setPos(0f, -128f, 258f)
        back_top_wing.addChild(front_top_r1)
        setRotationAngle(front_top_r1, -0.6981f, 0f, 0f)
        front_top_r1.texOffs(0, 0).addBox(-1f, 0f, 0f, 2, 113, 12, 0f, false)

        back_left_wing = ModelMapper(modelDataWrapper)
        back_left_wing.setPos(0f, 0f, 0f)
        exterior.addChild(back_left_wing)


        back_r2 = ModelMapper(modelDataWrapper)
        back_r2.setPos(0f, -15f, 264f)
        back_left_wing.addChild(back_r2)
        setRotationAngle(back_r2, 0f, -0.2269f, -0.0873f)
        back_r2.texOffs(673, 165).addBox(11f, -1f, -25f, 93, 2, 25, 0f, false)

        side_r1 = ModelMapper(modelDataWrapper)
        side_r1.setPos(0f, -15f, 0f)
        back_left_wing.addChild(side_r1)
        setRotationAngle(side_r1, 0f, 0f, -0.0873f)
        side_r1.texOffs(511, 743).addBox(99f, -1f, 264f, 2, 2, 24, 0.1f, false)

        front_r1 = ModelMapper(modelDataWrapper)
        front_r1.setPos(0f, -15f, 204f)
        back_left_wing.addChild(front_r1)
        setRotationAngle(front_r1, 0f, -0.5411f, -0.0873f)
        front_r1.texOffs(374, 657).addBox(25f, -1f, 0f, 94, 2, 25, -0.1f, false)

        back_right_wing = ModelMapper(modelDataWrapper)
        back_right_wing.setPos(0f, 0f, 0f)
        exterior.addChild(back_right_wing)


        back_r3 = ModelMapper(modelDataWrapper)
        back_r3.setPos(0f, -15f, 264f)
        back_right_wing.addChild(back_r3)
        setRotationAngle(back_r3, 0f, 0.2269f, 0.0873f)
        back_r3.texOffs(612, 657).addBox(-104f, -1f, -25f, 93, 2, 25, 0f, false)

        side_r2 = ModelMapper(modelDataWrapper)
        side_r2.setPos(0f, -15f, 0f)
        back_right_wing.addChild(side_r2)
        setRotationAngle(side_r2, 0f, 0f, 0.0873f)
        side_r2.texOffs(10, 101).addBox(-101f, -1f, 264f, 2, 2, 24, 0.1f, false)

        front_r2 = ModelMapper(modelDataWrapper)
        front_r2.setPos(0f, -15f, 204f)
        back_right_wing.addChild(front_r2)
        setRotationAngle(front_r2, 0f, 0.5411f, 0.0873f)
        front_r2.texOffs(607, 604).addBox(-119f, -1f, 0f, 94, 2, 25, -0.1f, false)

        bottom = ModelMapper(modelDataWrapper)
        bottom.setPos(0f, 0f, 0f)
        exterior.addChild(bottom)
        bottom.texOffs(122, 0).addBox(-24f, 36f, -107f, 48, 0, 109, 0f, false)
        bottom.texOffs(218, 341).addBox(-31f, 13f, -131f, 62, 12, 0, 0f, false)
        bottom.texOffs(0, 284).addBox(-24f, 24f, 51f, 48, 6, 0, 0f, false)
        bottom.texOffs(0, 226).addBox(32f, 10f, -107f, 0, 18, 109, 0f, false)
        bottom.texOffs(0, 208).addBox(-32f, 10f, -107f, 0, 18, 109, 0f, false)

        back_top_right_r1 = ModelMapper(modelDataWrapper)
        back_top_right_r1.setPos(-32f, 28f, 2f)
        bottom.addChild(back_top_right_r1)
        setRotationAngle(back_top_right_r1, 0f, 0.1745f, 0f)
        back_top_right_r1.texOffs(0, 78).addBox(0f, -18f, 0f, 0, 18, 50, 0f, false)

        back_top_left_r1 = ModelMapper(modelDataWrapper)
        back_top_left_r1.setPos(32f, 28f, 2f)
        bottom.addChild(back_top_left_r1)
        setRotationAngle(back_top_left_r1, 0f, -0.1745f, 0f)
        back_top_left_r1.texOffs(0, 206).addBox(0f, -18f, 0f, 0, 18, 50, 0f, false)

        back_side_right_r1 = ModelMapper(modelDataWrapper)
        back_side_right_r1.setPos(-32f, 28f, 2f)
        bottom.addChild(back_side_right_r1)
        setRotationAngle(back_side_right_r1, 0f, 0.1745f, -0.5236f)
        back_side_right_r1.texOffs(159, 172).addBox(0f, 0f, 0f, 0, 10, 50, 0f, false)

        back_side_left_r1 = ModelMapper(modelDataWrapper)
        back_side_left_r1.setPos(32f, 28f, 2f)
        bottom.addChild(back_side_left_r1)
        setRotationAngle(back_side_left_r1, 0f, -0.1745f, 0.5236f)
        back_side_left_r1.texOffs(0, 224).addBox(0f, 0f, 0f, 0, 10, 50, 0f, false)

        back_bottom_right_r1 = ModelMapper(modelDataWrapper)
        back_bottom_right_r1.setPos(-24f, 36f, 2f)
        bottom.addChild(back_bottom_right_r1)
        setRotationAngle(back_bottom_right_r1, 0.0873f, 0f, 0.5236f)
        back_bottom_right_r1.texOffs(22, 80).addBox(-6f, 0f, 0f, 6, 0, 30, 0f, false)

        back_bottom_left_r1 = ModelMapper(modelDataWrapper)
        back_bottom_left_r1.setPos(24f, 36f, 2f)
        bottom.addChild(back_bottom_left_r1)
        setRotationAngle(back_bottom_left_r1, 0.0873f, 0f, -0.5236f)
        back_bottom_left_r1.texOffs(103, 0).addBox(0f, 0f, 0f, 6, 0, 30, 0f, false)

        front_top_right_r1 = ModelMapper(modelDataWrapper)
        front_top_right_r1.setPos(-32f, 28f, -107f)
        bottom.addChild(front_top_right_r1)
        setRotationAngle(front_top_right_r1, 0f, -0.0873f, 0f)
        front_top_right_r1.texOffs(587, 632).addBox(0f, -18f, -25f, 0, 18, 25, 0f, false)

        front_top_left_r1 = ModelMapper(modelDataWrapper)
        front_top_left_r1.setPos(32f, 28f, -107f)
        bottom.addChild(front_top_left_r1)
        setRotationAngle(front_top_left_r1, 0f, 0.0873f, 0f)
        front_top_left_r1.texOffs(627, 700).addBox(0f, -18f, -25f, 0, 18, 25, 0f, false)

        front_side_right_r1 = ModelMapper(modelDataWrapper)
        front_side_right_r1.setPos(-32f, 28f, -107f)
        bottom.addChild(front_side_right_r1)
        setRotationAngle(front_side_right_r1, 0f, -0.1745f, -0.5236f)
        front_side_right_r1.texOffs(673, 215).addBox(0f, -4f, -25f, 0, 10, 25, 0f, false)

        front_side_left_r1 = ModelMapper(modelDataWrapper)
        front_side_left_r1.setPos(32f, 28f, -107f)
        bottom.addChild(front_side_left_r1)
        setRotationAngle(front_side_left_r1, 0f, 0.1745f, 0.5236f)
        front_side_left_r1.texOffs(614, 674).addBox(0f, -4f, -25f, 0, 10, 25, 0f, false)

        front_bottom_right_r1 = ModelMapper(modelDataWrapper)
        front_bottom_right_r1.setPos(-24f, 36f, -107f)
        bottom.addChild(front_bottom_right_r1)
        setRotationAngle(front_bottom_right_r1, -0.2618f, 0f, 0.5236f)
        front_bottom_right_r1.texOffs(234, 203).addBox(-9f, 0f, -25f, 9, 0, 25, 0f, false)

        front_bottom_left_r1 = ModelMapper(modelDataWrapper)
        front_bottom_left_r1.setPos(24f, 36f, -107f)
        bottom.addChild(front_bottom_left_r1)
        setRotationAngle(front_bottom_left_r1, -0.2618f, 0f, -0.5236f)
        front_bottom_left_r1.texOffs(262, 236).addBox(0f, 0f, -25f, 9, 0, 25, 0f, false)

        side_right_r2 = ModelMapper(modelDataWrapper)
        side_right_r2.setPos(-32f, 28f, 0f)
        bottom.addChild(side_right_r2)
        setRotationAngle(side_right_r2, 0f, 0f, -0.5236f)
        side_right_r2.texOffs(0, 244).addBox(0f, 0f, -107f, 0, 6, 109, 0f, false)

        side_left_r2 = ModelMapper(modelDataWrapper)
        side_left_r2.setPos(32f, 28f, 0f)
        bottom.addChild(side_left_r2)
        setRotationAngle(side_left_r2, 0f, 0f, 0.5236f)
        side_left_r2.texOffs(0, 250).addBox(0f, 0f, -107f, 0, 6, 109, 0f, false)

        bottom_right_r3 = ModelMapper(modelDataWrapper)
        bottom_right_r3.setPos(-24f, 36f, 0f)
        bottom.addChild(bottom_right_r3)
        setRotationAngle(bottom_right_r3, 0f, 0f, 0.5236f)
        bottom_right_r3.texOffs(0, 0).addBox(-6f, 0f, -107f, 6, 0, 109, 0f, false)

        bottom_left_r3 = ModelMapper(modelDataWrapper)
        bottom_left_r3.setPos(24f, 36f, 0f)
        bottom.addChild(bottom_left_r3)
        setRotationAngle(bottom_left_r3, 0f, 0f, -0.5236f)
        bottom_left_r3.texOffs(12, 0).addBox(0f, 0f, -107f, 6, 0, 109, 0f, false)

        back_side_r1 = ModelMapper(modelDataWrapper)
        back_side_r1.setPos(0f, 30f, 51f)
        bottom.addChild(back_side_r1)
        setRotationAngle(back_side_r1, 0.6981f, 0f, 0f)
        back_side_r1.texOffs(397, 375).addBox(-21f, 0f, -3f, 42, 0, 3, 0f, false)

        back_bottom_r1 = ModelMapper(modelDataWrapper)
        back_bottom_r1.setPos(0f, 36f, 2f)
        bottom.addChild(back_bottom_r1)
        setRotationAngle(back_bottom_r1, 0.0873f, 0f, 0f)
        back_bottom_r1.texOffs(112, 175).addBox(-24f, 0f, 0f, 48, 0, 47, 0f, false)

        front_side_r1 = ModelMapper(modelDataWrapper)
        front_side_r1.setPos(31f, 25f, -131f)
        bottom.addChild(front_side_r1)
        setRotationAngle(front_side_r1, 0.5236f, 0f, 0f)
        front_side_r1.texOffs(159, 232).addBox(-60f, 0f, 0f, 58, 4, 0, 0f, false)

        front_bottom_r2 = ModelMapper(modelDataWrapper)
        front_bottom_r2.setPos(24f, 36f, -107f)
        bottom.addChild(front_bottom_r2)
        setRotationAngle(front_bottom_r2, -0.3491f, 0f, 0f)
        front_bottom_r2.texOffs(194, 317).addBox(-51f, 0f, -24f, 54, 0, 24, 0f, false)

        left_engine = ModelMapper(modelDataWrapper)
        left_engine.setPos(0f, 0f, 0f)
        exterior.addChild(left_engine)
        left_engine.texOffs(315, 712).addBox(77f, 17f, -99f, 30, 30, 35, 0f, false)
        left_engine.texOffs(276, 604).addBox(89f, 6f, -106f, 6, 22, 86, 0f, false)
        left_engine.texOffs(618, 823).addBox(87f, 47f, -99f, 10, 3, 16, 0f, false)
        left_engine.texOffs(861, 868).addBox(107f, 27f, -99f, 3, 10, 16, 0f, false)
        left_engine.texOffs(248, 874).addBox(74f, 27f, -99f, 3, 10, 16, 0f, false)
        left_engine.texOffs(682, 823).addBox(87f, 14f, -99f, 10, 3, 16, 0f, false)

        tail_right_roof_r1 = ModelMapper(modelDataWrapper)
        tail_right_roof_r1.setPos(89f, 22f, -64f)
        left_engine.addChild(tail_right_roof_r1)
        setRotationAngle(tail_right_roof_r1, -0.2094f, 0f, -0.5236f)
        tail_right_roof_r1.texOffs(636, 791).addBox(-6f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_left_roof_r1 = ModelMapper(modelDataWrapper)
        tail_left_roof_r1.setPos(95f, 22f, -64f)
        left_engine.addChild(tail_left_roof_r1)
        setRotationAngle(tail_left_roof_r1, -0.2094f, 0f, 0.5236f)
        tail_left_roof_r1.texOffs(0, 808).addBox(0f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_roof_r1 = ModelMapper(modelDataWrapper)
        tail_roof_r1.setPos(0f, 22f, -64f)
        left_engine.addChild(tail_roof_r1)
        setRotationAngle(tail_roof_r1, -0.2094f, 0f, 0f)
        tail_roof_r1.texOffs(95, 806).addBox(89f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_right_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        tail_right_wall_bottom_r1.setPos(82f, 35f, -64f)
        left_engine.addChild(tail_right_wall_bottom_r1)
        setRotationAngle(tail_right_wall_bottom_r1, 0f, 0.2094f, -0.5236f)
        tail_right_wall_bottom_r1.texOffs(711, 823).addBox(0f, 0f, 0f, 3, 6, 29, 0f, false)

        tail_right_wall_top_r1 = ModelMapper(modelDataWrapper)
        tail_right_wall_top_r1.setPos(82f, 29f, -64f)
        left_engine.addChild(tail_right_wall_top_r1)
        setRotationAngle(tail_right_wall_top_r1, 0f, 0.2094f, 0.5236f)
        tail_right_wall_top_r1.texOffs(647, 823).addBox(0f, -6f, 0f, 3, 6, 29, 0f, false)

        tail_right_wall_r1 = ModelMapper(modelDataWrapper)
        tail_right_wall_r1.setPos(82f, 0f, -64f)
        left_engine.addChild(tail_right_wall_r1)
        setRotationAngle(tail_right_wall_r1, 0f, 0.2094f, 0f)
        tail_right_wall_r1.texOffs(395, 822).addBox(0f, 29f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        tail_left_wall_bottom_r1.setPos(102f, 35f, -64f)
        left_engine.addChild(tail_left_wall_bottom_r1)
        setRotationAngle(tail_left_wall_bottom_r1, 0f, -0.2094f, 0.5236f)
        tail_left_wall_bottom_r1.texOffs(583, 820).addBox(-3f, 0f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_top_r1 = ModelMapper(modelDataWrapper)
        tail_left_wall_top_r1.setPos(102f, 29f, -64f)
        left_engine.addChild(tail_left_wall_top_r1)
        setRotationAngle(tail_left_wall_top_r1, 0f, -0.2094f, -0.5236f)
        tail_left_wall_top_r1.texOffs(818, 487).addBox(-3f, -6f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_r1 = ModelMapper(modelDataWrapper)
        tail_left_wall_r1.setPos(102f, 0f, -64f)
        left_engine.addChild(tail_left_wall_r1)
        setRotationAngle(tail_left_wall_r1, 0f, -0.2094f, 0f)
        tail_left_wall_r1.texOffs(817, 810).addBox(-3f, 29f, 0f, 3, 6, 29, 0f, false)

        tail_right_floor_r1 = ModelMapper(modelDataWrapper)
        tail_right_floor_r1.setPos(89f, 42f, -64f)
        left_engine.addChild(tail_right_floor_r1)
        setRotationAngle(tail_right_floor_r1, 0.2094f, 0f, 0.5236f)
        tail_right_floor_r1.texOffs(794, 79).addBox(-6f, -3f, 0f, 6, 3, 29, 0f, false)

        tail_left_floor_r1 = ModelMapper(modelDataWrapper)
        tail_left_floor_r1.setPos(95f, 42f, -64f)
        left_engine.addChild(tail_left_floor_r1)
        setRotationAngle(tail_left_floor_r1, 0.2094f, 0f, -0.5236f)
        tail_left_floor_r1.texOffs(776, 791).addBox(0f, -3f, 0f, 6, 3, 29, 0f, false)

        tail_floor_r1 = ModelMapper(modelDataWrapper)
        tail_floor_r1.setPos(0f, 42f, -64f)
        left_engine.addChild(tail_floor_r1)
        setRotationAngle(tail_floor_r1, 0.2094f, 0f, 0f)
        tail_floor_r1.texOffs(706, 791).addBox(89f, -3f, 0f, 6, 3, 29, 0f, false)

        back_right_roof_r1 = ModelMapper(modelDataWrapper)
        back_right_roof_r1.setPos(87f, 14f, -83f)
        left_engine.addChild(back_right_roof_r1)
        setRotationAngle(back_right_roof_r1, -0.1745f, 0f, -0.5236f)
        back_right_roof_r1.texOffs(835, 79).addBox(-10f, 0f, 0f, 10, 3, 20, 0f, false)

        back_left_roof_r1 = ModelMapper(modelDataWrapper)
        back_left_roof_r1.setPos(97f, 14f, -83f)
        left_engine.addChild(back_left_roof_r1)
        setRotationAngle(back_left_roof_r1, -0.1745f, 0f, 0.5236f)
        back_left_roof_r1.texOffs(786, 845).addBox(0f, 0f, 0f, 10, 3, 20, 0f, false)

        back_roof_r1 = ModelMapper(modelDataWrapper)
        back_roof_r1.setPos(0f, 14f, -83f)
        left_engine.addChild(back_roof_r1)
        setRotationAngle(back_roof_r1, -0.1745f, 0f, 0f)
        back_roof_r1.texOffs(125, 844).addBox(87f, 0f, 0f, 10, 3, 20, 0f, false)

        back_right_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        back_right_wall_bottom_r1.setPos(74f, 37f, -83f)
        left_engine.addChild(back_right_wall_bottom_r1)
        setRotationAngle(back_right_wall_bottom_r1, 0f, 0.1745f, -0.5236f)
        back_right_wall_bottom_r1.texOffs(861, 790).addBox(0f, 0f, 0f, 3, 10, 20, 0f, false)

        back_right_wall_top_r1 = ModelMapper(modelDataWrapper)
        back_right_wall_top_r1.setPos(74f, 27f, -83f)
        left_engine.addChild(back_right_wall_top_r1)
        setRotationAngle(back_right_wall_top_r1, 0f, 0.1745f, 0.5236f)
        back_right_wall_top_r1.texOffs(80, 861).addBox(0f, -10f, 0f, 3, 10, 20, 0f, false)

        back_right_wall_r1 = ModelMapper(modelDataWrapper)
        back_right_wall_r1.setPos(74f, 0f, -83f)
        left_engine.addChild(back_right_wall_r1)
        setRotationAngle(back_right_wall_r1, 0f, 0.1745f, 0f)
        back_right_wall_r1.texOffs(860, 583).addBox(0f, 27f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        back_left_wall_bottom_r1.setPos(110f, 37f, -83f)
        left_engine.addChild(back_left_wall_bottom_r1)
        setRotationAngle(back_left_wall_bottom_r1, 0f, -0.1745f, 0.5236f)
        back_left_wall_bottom_r1.texOffs(331, 860).addBox(-3f, 0f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_top_r1 = ModelMapper(modelDataWrapper)
        back_left_wall_top_r1.setPos(110f, 27f, -83f)
        left_engine.addChild(back_left_wall_top_r1)
        setRotationAngle(back_left_wall_top_r1, 0f, -0.1745f, -0.5236f)
        back_left_wall_top_r1.texOffs(859, 719).addBox(-3f, -10f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_r1 = ModelMapper(modelDataWrapper)
        back_left_wall_r1.setPos(110f, 0f, -83f)
        left_engine.addChild(back_left_wall_r1)
        setRotationAngle(back_left_wall_r1, 0f, -0.1745f, 0f)
        back_left_wall_r1.texOffs(723, 858).addBox(-3f, 27f, 0f, 3, 10, 20, 0f, false)

        back_right_floor_r1 = ModelMapper(modelDataWrapper)
        back_right_floor_r1.setPos(87f, 50f, -83f)
        left_engine.addChild(back_right_floor_r1)
        setRotationAngle(back_right_floor_r1, 0.1745f, 0f, 0.5236f)
        back_right_floor_r1.texOffs(0, 840).addBox(-10f, -3f, 0f, 10, 3, 20, 0f, false)

        back_left_floor_r1 = ModelMapper(modelDataWrapper)
        back_left_floor_r1.setPos(97f, 50f, -83f)
        left_engine.addChild(back_left_floor_r1)
        setRotationAngle(back_left_floor_r1, 0.1745f, 0f, -0.5236f)
        back_left_floor_r1.texOffs(85, 838).addBox(0f, -3f, 0f, 10, 3, 20, 0f, false)

        back_floor_r1 = ModelMapper(modelDataWrapper)
        back_floor_r1.setPos(0f, 50f, -83f)
        left_engine.addChild(back_floor_r1)
        setRotationAngle(back_floor_r1, 0.1745f, 0f, 0f)
        back_floor_r1.texOffs(340, 837).addBox(87f, -3f, 0f, 10, 3, 20, 0f, false)

        front_right_roof_r1 = ModelMapper(modelDataWrapper)
        front_right_roof_r1.setPos(87f, 14f, -99f)
        left_engine.addChild(front_right_roof_r1)
        setRotationAngle(front_right_roof_r1, 0.1047f, 0f, -0.5236f)
        front_right_roof_r1.texOffs(499, 851).addBox(-10f, 0f, -20f, 10, 3, 20, 0f, false)

        front_left_roof_r1 = ModelMapper(modelDataWrapper)
        front_left_roof_r1.setPos(97f, 14f, -99f)
        left_engine.addChild(front_left_roof_r1)
        setRotationAngle(front_left_roof_r1, 0.1047f, 0f, 0.5236f)
        front_left_roof_r1.texOffs(439, 851).addBox(0f, 0f, -20f, 10, 3, 20, 0f, false)

        front_roof_r1 = ModelMapper(modelDataWrapper)
        front_roof_r1.setPos(0f, 14f, -99f)
        left_engine.addChild(front_roof_r1)
        setRotationAngle(front_roof_r1, 0.1047f, 0f, 0f)
        front_roof_r1.texOffs(225, 851).addBox(87f, 0f, -20f, 10, 3, 20, 0f, false)

        front_right_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        front_right_wall_bottom_r1.setPos(74f, 37f, -99f)
        left_engine.addChild(front_right_wall_bottom_r1)
        setRotationAngle(front_right_wall_bottom_r1, 0f, -0.1047f, -0.5236f)
        front_right_wall_bottom_r1.texOffs(867, 365).addBox(0f, 0f, -20f, 3, 10, 20, 0f, false)

        front_right_wall_top_r1 = ModelMapper(modelDataWrapper)
        front_right_wall_top_r1.setPos(74f, 27f, -99f)
        left_engine.addChild(front_right_wall_top_r1)
        setRotationAngle(front_right_wall_top_r1, 0f, -0.1047f, 0.5236f)
        front_right_wall_top_r1.texOffs(126, 867).addBox(0f, -10f, -20f, 3, 10, 20, 0f, false)

        front_right_wall_r1 = ModelMapper(modelDataWrapper)
        front_right_wall_r1.setPos(74f, 0f, -99f)
        left_engine.addChild(front_right_wall_r1)
        setRotationAngle(front_right_wall_r1, 0f, -0.1047f, 0f)
        front_right_wall_r1.texOffs(873, 208).addBox(0f, 27f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        front_left_wall_bottom_r1.setPos(110f, 37f, -99f)
        left_engine.addChild(front_left_wall_bottom_r1)
        setRotationAngle(front_left_wall_bottom_r1, 0f, 0.1047f, 0.5236f)
        front_left_wall_bottom_r1.texOffs(865, 114).addBox(-3f, 0f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_top_r1 = ModelMapper(modelDataWrapper)
        front_left_wall_top_r1.setPos(110f, 27f, -99f)
        left_engine.addChild(front_left_wall_top_r1)
        setRotationAngle(front_left_wall_top_r1, 0f, 0.1047f, -0.5236f)
        front_left_wall_top_r1.texOffs(0, 863).addBox(-3f, -10f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_r1 = ModelMapper(modelDataWrapper)
        front_left_wall_r1.setPos(110f, 0f, -99f)
        left_engine.addChild(front_left_wall_r1)
        setRotationAngle(front_left_wall_r1, 0f, 0.1047f, 0f)
        front_left_wall_r1.texOffs(862, 502).addBox(-3f, 27f, -20f, 3, 10, 20, 0f, false)

        front_right_floor_r1 = ModelMapper(modelDataWrapper)
        front_right_floor_r1.setPos(87f, 50f, -99f)
        left_engine.addChild(front_right_floor_r1)
        setRotationAngle(front_right_floor_r1, -0.1047f, 0f, 0.5236f)
        front_right_floor_r1.texOffs(165, 851).addBox(-10f, -3f, -20f, 10, 3, 20, 0f, false)

        front_left_floor_r1 = ModelMapper(modelDataWrapper)
        front_left_floor_r1.setPos(97f, 50f, -99f)
        left_engine.addChild(front_left_floor_r1)
        setRotationAngle(front_left_floor_r1, -0.1047f, 0f, -0.5236f)
        front_left_floor_r1.texOffs(846, 845).addBox(0f, -3f, -20f, 10, 3, 20, 0f, false)

        front_floor_r1 = ModelMapper(modelDataWrapper)
        front_floor_r1.setPos(0f, 50f, -99f)
        left_engine.addChild(front_floor_r1)
        setRotationAngle(front_floor_r1, -0.1047f, 0f, 0f)
        front_floor_r1.texOffs(40, 846).addBox(87f, -3f, -20f, 10, 3, 20, 0f, false)

        right_roof_r2 = ModelMapper(modelDataWrapper)
        right_roof_r2.setPos(87f, 14f, 0f)
        left_engine.addChild(right_roof_r2)
        setRotationAngle(right_roof_r2, 0f, 0f, -0.5236f)
        right_roof_r2.texOffs(852, 820).addBox(-10f, 0f, -99f, 10, 3, 16, 0f, false)

        left_roof_r2 = ModelMapper(modelDataWrapper)
        left_roof_r2.setPos(97f, 14f, 0f)
        left_engine.addChild(left_roof_r2)
        setRotationAngle(left_roof_r2, 0f, 0f, 0.5236f)
        left_roof_r2.texOffs(430, 819).addBox(0f, 0f, -99f, 10, 3, 16, 0f, false)

        right_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        right_wall_bottom_r1.setPos(74f, 37f, 0f)
        left_engine.addChild(right_wall_bottom_r1)
        setRotationAngle(right_wall_bottom_r1, 0f, 0f, -0.5236f)
        right_wall_bottom_r1.texOffs(172, 874).addBox(0f, 0f, -99f, 3, 10, 16, 0f, false)

        right_wall_top_r3 = ModelMapper(modelDataWrapper)
        right_wall_top_r3.setPos(74f, 27f, 0f)
        left_engine.addChild(right_wall_top_r3)
        setRotationAngle(right_wall_top_r3, 0f, 0f, 0.5236f)
        right_wall_top_r3.texOffs(210, 874).addBox(0f, -10f, -99f, 3, 10, 16, 0f, false)

        left_wall_bottom_r1 = ModelMapper(modelDataWrapper)
        left_wall_bottom_r1.setPos(110f, 37f, 0f)
        left_engine.addChild(left_wall_bottom_r1)
        setRotationAngle(left_wall_bottom_r1, 0f, 0f, 0.5236f)
        left_wall_bottom_r1.texOffs(823, 868).addBox(-3f, 0f, -99f, 3, 10, 16, 0f, false)

        left_wall_top_r3 = ModelMapper(modelDataWrapper)
        left_wall_top_r3.setPos(110f, 27f, 0f)
        left_engine.addChild(left_wall_top_r3)
        setRotationAngle(left_wall_top_r3, 0f, 0f, -0.5236f)
        left_wall_top_r3.texOffs(410, 871).addBox(-3f, -10f, -99f, 3, 10, 16, 0f, false)

        right_floor_r2 = ModelMapper(modelDataWrapper)
        right_floor_r2.setPos(87f, 50f, 0f)
        left_engine.addChild(right_floor_r2)
        setRotationAngle(right_floor_r2, 0f, 0f, 0.5236f)
        right_floor_r2.texOffs(842, 440).addBox(-10f, -3f, -99f, 10, 3, 16, 0f, false)

        left_floor_r2 = ModelMapper(modelDataWrapper)
        left_floor_r2.setPos(97f, 50f, 0f)
        left_engine.addChild(left_floor_r2)
        setRotationAngle(left_floor_r2, 0f, 0f, -0.5236f)
        left_floor_r2.texOffs(554, 816).addBox(0f, -3f, -99f, 10, 3, 16, 0f, false)

        support_back_r1 = ModelMapper(modelDataWrapper)
        support_back_r1.setPos(0f, 28f, -20f)
        left_engine.addChild(support_back_r1)
        setRotationAngle(support_back_r1, 0.8203f, 0f, 0f)
        support_back_r1.texOffs(196, 777).addBox(89f, -13f, 0f, 6, 13, 38, 0.1f, false)

        support_top_r1 = ModelMapper(modelDataWrapper)
        support_top_r1.setPos(0f, 11f, -106f)
        left_engine.addChild(support_top_r1)
        setRotationAngle(support_top_r1, 0.1222f, 0f, 0f)
        support_top_r1.texOffs(0, 749).addBox(89f, 0f, 0f, 6, 3, 56, 0.1f, false)

        right_engine = ModelMapper(modelDataWrapper)
        right_engine.setPos(0f, 0f, 0f)
        exterior.addChild(right_engine)
        right_engine.texOffs(185, 712).addBox(-107f, 17f, -99f, 30, 30, 35, 0f, false)
        right_engine.texOffs(575, 165).addBox(-95f, 6f, -106f, 6, 22, 86, 0f, false)
        right_engine.texOffs(490, 816).addBox(-97f, 47f, -99f, 10, 3, 16, 0f, false)
        right_engine.texOffs(785, 868).addBox(-110f, 27f, -99f, 3, 10, 16, 0f, false)
        right_engine.texOffs(76, 808).addBox(-77f, 27f, -99f, 3, 10, 16, 0f, false)
        right_engine.texOffs(525, 784).addBox(-97f, 14f, -99f, 10, 3, 16, 0f, false)

        tail_right_roof_r2 = ModelMapper(modelDataWrapper)
        tail_right_roof_r2.setPos(-89f, 22f, -64f)
        right_engine.addChild(tail_right_roof_r2)
        setRotationAngle(tail_right_roof_r2, -0.2094f, 0f, 0.5236f)
        tail_right_roof_r2.texOffs(280, 712).addBox(0f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_left_roof_r2 = ModelMapper(modelDataWrapper)
        tail_left_roof_r2.setPos(-95f, 22f, -64f)
        right_engine.addChild(tail_left_roof_r2)
        setRotationAngle(tail_left_roof_r2, -0.2094f, 0f, -0.5236f)
        tail_left_roof_r2.texOffs(484, 784).addBox(-6f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_roof_r2 = ModelMapper(modelDataWrapper)
        tail_roof_r2.setPos(0f, 22f, -64f)
        right_engine.addChild(tail_roof_r2)
        setRotationAngle(tail_roof_r2, -0.2094f, 0f, 0f)
        tail_roof_r2.texOffs(554, 784).addBox(-95f, 0f, 0f, 6, 3, 29, 0f, false)

        tail_right_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        tail_right_wall_bottom_r2.setPos(-82f, 35f, -64f)
        right_engine.addChild(tail_right_wall_bottom_r2)
        setRotationAngle(tail_right_wall_bottom_r2, 0f, -0.2094f, 0.5236f)
        tail_right_wall_bottom_r2.texOffs(136, 809).addBox(-3f, 0f, 0f, 3, 6, 29, 0f, false)

        tail_right_wall_top_r2 = ModelMapper(modelDataWrapper)
        tail_right_wall_top_r2.setPos(-82f, 29f, -64f)
        right_engine.addChild(tail_right_wall_top_r2)
        setRotationAngle(tail_right_wall_top_r2, 0f, -0.2094f, -0.5236f)
        tail_right_wall_top_r2.texOffs(41, 811).addBox(-3f, -6f, 0f, 3, 6, 29, 0f, false)

        tail_right_wall_r2 = ModelMapper(modelDataWrapper)
        tail_right_wall_r2.setPos(-82f, 0f, -64f)
        right_engine.addChild(tail_right_wall_r2)
        setRotationAngle(tail_right_wall_r2, 0f, -0.2094f, 0f)
        tail_right_wall_r2.texOffs(455, 816).addBox(-3f, 29f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        tail_left_wall_bottom_r2.setPos(-102f, 35f, -64f)
        right_engine.addChild(tail_left_wall_bottom_r2)
        setRotationAngle(tail_left_wall_bottom_r2, 0f, 0.2094f, -0.5236f)
        tail_left_wall_bottom_r2.texOffs(519, 816).addBox(0f, 0f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_top_r2 = ModelMapper(modelDataWrapper)
        tail_left_wall_top_r2.setPos(-102f, 29f, -64f)
        right_engine.addChild(tail_left_wall_top_r2)
        setRotationAngle(tail_left_wall_top_r2, 0f, 0.2094f, 0.5236f)
        tail_left_wall_top_r2.texOffs(817, 684).addBox(0f, -6f, 0f, 3, 6, 29, 0f, false)

        tail_left_wall_r2 = ModelMapper(modelDataWrapper)
        tail_left_wall_r2.setPos(-102f, 0f, -64f)
        right_engine.addChild(tail_left_wall_r2)
        setRotationAngle(tail_left_wall_r2, 0f, 0.2094f, 0f)
        tail_left_wall_r2.texOffs(817, 775).addBox(0f, 29f, 0f, 3, 6, 29, 0f, false)

        tail_right_floor_r2 = ModelMapper(modelDataWrapper)
        tail_right_floor_r2.setPos(-89f, 42f, -64f)
        right_engine.addChild(tail_right_floor_r2)
        setRotationAngle(tail_right_floor_r2, 0.2094f, 0f, -0.5236f)
        tail_right_floor_r2.texOffs(786, 385).addBox(0f, -3f, 0f, 6, 3, 29, 0f, false)

        tail_left_floor_r2 = ModelMapper(modelDataWrapper)
        tail_left_floor_r2.setPos(-95f, 42f, -64f)
        right_engine.addChild(tail_left_floor_r2)
        setRotationAngle(tail_left_floor_r2, 0.2094f, 0f, 0.5236f)
        tail_left_floor_r2.texOffs(786, 417).addBox(-6f, -3f, 0f, 6, 3, 29, 0f, false)

        tail_floor_r2 = ModelMapper(modelDataWrapper)
        tail_floor_r2.setPos(0f, 42f, -64f)
        right_engine.addChild(tail_floor_r2)
        setRotationAngle(tail_floor_r2, 0.2094f, 0f, 0f)
        tail_floor_r2.texOffs(595, 788).addBox(-95f, -3f, 0f, 6, 3, 29, 0f, false)

        back_right_roof_r2 = ModelMapper(modelDataWrapper)
        back_right_roof_r2.setPos(-87f, 14f, -83f)
        right_engine.addChild(back_right_roof_r2)
        setRotationAngle(back_right_roof_r2, -0.1745f, 0f, 0.5236f)
        back_right_roof_r2.texOffs(818, 522).addBox(0f, 0f, 0f, 10, 3, 20, 0f, false)

        back_left_roof_r2 = ModelMapper(modelDataWrapper)
        back_left_roof_r2.setPos(-97f, 14f, -83f)
        right_engine.addChild(back_left_roof_r2)
        setRotationAngle(back_left_roof_r2, -0.1745f, 0f, -0.5236f)
        back_left_roof_r2.texOffs(820, 603).addBox(-10f, 0f, 0f, 10, 3, 20, 0f, false)

        back_roof_r2 = ModelMapper(modelDataWrapper)
        back_roof_r2.setPos(0f, 14f, -83f)
        right_engine.addChild(back_roof_r2)
        setRotationAngle(back_roof_r2, -0.1745f, 0f, 0f)
        back_roof_r2.texOffs(823, 657).addBox(-97f, 0f, 0f, 10, 3, 20, 0f, false)

        back_right_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        back_right_wall_bottom_r2.setPos(-74f, 37f, -83f)
        right_engine.addChild(back_right_wall_bottom_r2)
        setRotationAngle(back_right_wall_bottom_r2, 0f, -0.1745f, 0.5236f)
        back_right_wall_bottom_r2.texOffs(760, 487).addBox(-3f, 0f, 0f, 3, 10, 20, 0f, false)

        back_right_wall_top_r2 = ModelMapper(modelDataWrapper)
        back_right_wall_top_r2.setPos(-74f, 27f, -83f)
        right_engine.addChild(back_right_wall_top_r2)
        setRotationAngle(back_right_wall_top_r2, 0f, -0.1745f, -0.5236f)
        back_right_wall_top_r2.texOffs(760, 517).addBox(-3f, -10f, 0f, 3, 10, 20, 0f, false)

        back_right_wall_r2 = ModelMapper(modelDataWrapper)
        back_right_wall_r2.setPos(-74f, 0f, -83f)
        right_engine.addChild(back_right_wall_r2)
        setRotationAngle(back_right_wall_r2, 0f, -0.1745f, 0f)
        back_right_wall_r2.texOffs(755, 848).addBox(-3f, 27f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        back_left_wall_bottom_r2.setPos(-110f, 37f, -83f)
        right_engine.addChild(back_left_wall_bottom_r2)
        setRotationAngle(back_left_wall_bottom_r2, 0f, 0.1745f, -0.5236f)
        back_left_wall_bottom_r2.texOffs(285, 852).addBox(0f, 0f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_top_r2 = ModelMapper(modelDataWrapper)
        back_left_wall_top_r2.setPos(-110f, 27f, -83f)
        right_engine.addChild(back_left_wall_top_r2)
        setRotationAngle(back_left_wall_top_r2, 0f, 0.1745f, 0.5236f)
        back_left_wall_top_r2.texOffs(852, 680).addBox(0f, -10f, 0f, 3, 10, 20, 0f, false)

        back_left_wall_r2 = ModelMapper(modelDataWrapper)
        back_left_wall_r2.setPos(-110f, 0f, -83f)
        right_engine.addChild(back_left_wall_r2)
        setRotationAngle(back_left_wall_r2, 0f, 0.1745f, 0f)
        back_left_wall_r2.texOffs(852, 754).addBox(0f, 27f, 0f, 3, 10, 20, 0f, false)

        back_right_floor_r2 = ModelMapper(modelDataWrapper)
        back_right_floor_r2.setPos(-87f, 50f, -83f)
        right_engine.addChild(back_right_floor_r2)
        setRotationAngle(back_right_floor_r2, 0.1745f, 0f, -0.5236f)
        back_right_floor_r2.texOffs(746, 823).addBox(0f, -3f, 0f, 10, 3, 20, 0f, false)

        back_left_floor_r2 = ModelMapper(modelDataWrapper)
        back_left_floor_r2.setPos(-97f, 50f, -83f)
        right_engine.addChild(back_left_floor_r2)
        setRotationAngle(back_left_floor_r2, 0.1745f, 0f, 0.5236f)
        back_left_floor_r2.texOffs(825, 111).addBox(-10f, -3f, 0f, 10, 3, 20, 0f, false)

        back_floor_r2 = ModelMapper(modelDataWrapper)
        back_floor_r2.setPos(0f, 50f, -83f)
        right_engine.addChild(back_floor_r2)
        setRotationAngle(back_floor_r2, 0.1745f, 0f, 0f)
        back_floor_r2.texOffs(825, 134).addBox(-97f, -3f, 0f, 10, 3, 20, 0f, false)

        front_right_roof_r2 = ModelMapper(modelDataWrapper)
        front_right_roof_r2.setPos(-87f, 14f, -99f)
        right_engine.addChild(front_right_roof_r2)
        setRotationAngle(front_right_roof_r2, 0.1047f, 0f, 0.5236f)
        front_right_roof_r2.texOffs(827, 385).addBox(0f, 0f, -20f, 10, 3, 20, 0f, false)

        front_left_roof_r2 = ModelMapper(modelDataWrapper)
        front_left_roof_r2.setPos(-97f, 14f, -99f)
        right_engine.addChild(front_left_roof_r2)
        setRotationAngle(front_left_roof_r2, 0.1047f, 0f, -0.5236f)
        front_left_roof_r2.texOffs(827, 417).addBox(-10f, 0f, -20f, 10, 3, 20, 0f, false)

        front_roof_r2 = ModelMapper(modelDataWrapper)
        front_roof_r2.setPos(0f, 14f, -99f)
        right_engine.addChild(front_roof_r2)
        setRotationAngle(front_roof_r2, 0.1047f, 0f, 0f)
        front_roof_r2.texOffs(180, 828).addBox(-97f, 0f, -20f, 10, 3, 20, 0f, false)

        front_right_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        front_right_wall_bottom_r2.setPos(-74f, 37f, -99f)
        right_engine.addChild(front_right_wall_bottom_r2)
        setRotationAngle(front_right_wall_bottom_r2, 0f, 0.1047f, 0.5236f)
        front_right_wall_bottom_r2.texOffs(539, 854).addBox(-3f, 0f, -20f, 3, 10, 20, 0f, false)

        front_right_wall_top_r2 = ModelMapper(modelDataWrapper)
        front_right_wall_top_r2.setPos(-74f, 27f, -99f)
        right_engine.addChild(front_right_wall_top_r2)
        setRotationAngle(front_right_wall_top_r2, 0f, 0.1047f, -0.5236f)
        front_right_wall_top_r2.texOffs(585, 855).addBox(-3f, -10f, -20f, 3, 10, 20, 0f, false)

        front_right_wall_r2 = ModelMapper(modelDataWrapper)
        front_right_wall_r2.setPos(-74f, 0f, -99f)
        right_engine.addChild(front_right_wall_r2)
        setRotationAngle(front_right_wall_r2, 0f, 0.1047f, 0f)
        front_right_wall_r2.texOffs(856, 626).addBox(-3f, 27f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        front_left_wall_bottom_r2.setPos(-110f, 37f, -99f)
        right_engine.addChild(front_left_wall_bottom_r2)
        setRotationAngle(front_left_wall_bottom_r2, 0f, -0.1047f, -0.5236f)
        front_left_wall_bottom_r2.texOffs(380, 857).addBox(0f, 0f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_top_r2 = ModelMapper(modelDataWrapper)
        front_left_wall_top_r2.setPos(-110f, 27f, -99f)
        right_engine.addChild(front_left_wall_top_r2)
        setRotationAngle(front_left_wall_top_r2, 0f, -0.1047f, 0.5236f)
        front_left_wall_top_r2.texOffs(631, 858).addBox(0f, -10f, -20f, 3, 10, 20, 0f, false)

        front_left_wall_r2 = ModelMapper(modelDataWrapper)
        front_left_wall_r2.setPos(-110f, 0f, -99f)
        right_engine.addChild(front_left_wall_r2)
        setRotationAngle(front_left_wall_r2, 0f, -0.1047f, 0f)
        front_left_wall_r2.texOffs(677, 858).addBox(0f, 27f, -20f, 3, 10, 20, 0f, false)

        front_right_floor_r2 = ModelMapper(modelDataWrapper)
        front_right_floor_r2.setPos(-87f, 50f, -99f)
        right_engine.addChild(front_right_floor_r2)
        setRotationAngle(front_right_floor_r2, -0.1047f, 0f, -0.5236f)
        front_right_floor_r2.texOffs(240, 828).addBox(0f, -3f, -20f, 10, 3, 20, 0f, false)

        front_left_floor_r2 = ModelMapper(modelDataWrapper)
        front_left_floor_r2.setPos(-97f, 50f, -99f)
        right_engine.addChild(front_left_floor_r2)
        setRotationAngle(front_left_floor_r2, -0.1047f, 0f, 0.5236f)
        front_left_floor_r2.texOffs(300, 829).addBox(-10f, -3f, -20f, 10, 3, 20, 0f, false)

        front_floor_r2 = ModelMapper(modelDataWrapper)
        front_floor_r2.setPos(0f, 50f, -99f)
        right_engine.addChild(front_floor_r2)
        setRotationAngle(front_floor_r2, -0.1047f, 0f, 0f)
        front_floor_r2.texOffs(833, 192).addBox(-97f, -3f, -20f, 10, 3, 20, 0f, false)

        right_roof_r3 = ModelMapper(modelDataWrapper)
        right_roof_r3.setPos(-87f, 14f, 0f)
        right_engine.addChild(right_roof_r3)
        setRotationAngle(right_roof_r3, 0f, 0f, 0.5236f)
        right_roof_r3.texOffs(445, 750).addBox(0f, 0f, -99f, 10, 3, 16, 0f, false)

        left_roof_r3 = ModelMapper(modelDataWrapper)
        left_roof_r3.setPos(-97f, 14f, 0f)
        right_engine.addChild(left_roof_r3)
        setRotationAngle(left_roof_r3, 0f, 0f, -0.5236f)
        left_roof_r3.texOffs(699, 754).addBox(-10f, 0f, -99f, 10, 3, 16, 0f, false)

        right_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        right_wall_bottom_r2.setPos(-74f, 37f, 0f)
        right_engine.addChild(right_wall_bottom_r2)
        setRotationAngle(right_wall_bottom_r2, 0f, 0f, 0.5236f)
        right_wall_bottom_r2.texOffs(136, 771).addBox(-3f, 0f, -99f, 3, 10, 16, 0f, false)

        right_wall_top_r4 = ModelMapper(modelDataWrapper)
        right_wall_top_r4.setPos(-74f, 27f, 0f)
        right_engine.addChild(right_wall_top_r4)
        setRotationAngle(right_wall_top_r4, 0f, 0f, -0.5236f)
        right_wall_top_r4.texOffs(246, 777).addBox(-3f, -10f, -99f, 3, 10, 16, 0f, false)

        left_wall_bottom_r2 = ModelMapper(modelDataWrapper)
        left_wall_bottom_r2.setPos(-110f, 37f, 0f)
        right_engine.addChild(left_wall_bottom_r2)
        setRotationAngle(left_wall_bottom_r2, 0f, 0f, -0.5236f)
        left_wall_bottom_r2.texOffs(867, 408).addBox(0f, 0f, -99f, 3, 10, 16, 0f, false)

        left_wall_top_r4 = ModelMapper(modelDataWrapper)
        left_wall_top_r4.setPos(-110f, 27f, 0f)
        right_engine.addChild(left_wall_top_r4)
        setRotationAngle(left_wall_top_r4, 0f, 0f, 0.5236f)
        left_wall_top_r4.texOffs(868, 532).addBox(0f, -10f, -99f, 3, 10, 16, 0f, false)

        right_floor_r3 = ModelMapper(modelDataWrapper)
        right_floor_r3.setPos(-87f, 50f, 0f)
        right_engine.addChild(right_floor_r3)
        setRotationAngle(right_floor_r3, 0f, 0f, -0.5236f)
        right_floor_r3.texOffs(677, 791).addBox(0f, -3f, -99f, 10, 3, 16, 0f, false)

        left_floor_r3 = ModelMapper(modelDataWrapper)
        left_floor_r3.setPos(-97f, 50f, 0f)
        right_engine.addChild(left_floor_r3)
        setRotationAngle(left_floor_r3, 0f, 0f, 0.5236f)
        left_floor_r3.texOffs(747, 791).addBox(-10f, -3f, -99f, 10, 3, 16, 0f, false)

        support_back_r2 = ModelMapper(modelDataWrapper)
        support_back_r2.setPos(0f, 28f, -20f)
        right_engine.addChild(support_back_r2)
        setRotationAngle(support_back_r2, 0.8203f, 0f, 0f)
        support_back_r2.texOffs(577, 725).addBox(-95f, -13f, 0f, 6, 13, 38, 0.1f, false)

        support_top_r2 = ModelMapper(modelDataWrapper)
        support_top_r2.setPos(0f, 11f, -106f)
        right_engine.addChild(support_top_r2)
        setRotationAngle(support_top_r2, 0.1222f, 0f, 0f)
        support_top_r2.texOffs(699, 732).addBox(-95f, 0f, 0f, 6, 3, 56, 0.1f, false)

        window_interior = ModelMapper(modelDataWrapper)
        window_interior.setPos(0f, 24f, 0f)
        window_interior.texOffs(68, 749).addBox(-32f, 8f, -8f, 32, 1, 16, 0f, false)
        window_interior.texOffs(251, 109).addBox(-21f, -17f, -8f, 7, 0, 16, 0f, false)
        window_interior.texOffs(334, 109).addBox(-8f, -26f, -8f, 8, 0, 16, 0f, false)

        roof_side_r1 = ModelMapper(modelDataWrapper)
        roof_side_r1.setPos(-8f, -26f, 0f)
        window_interior.addChild(roof_side_r1)
        setRotationAngle(roof_side_r1, 0f, 0f, -0.1745f)
        roof_side_r1.texOffs(320, 242).addBox(-8f, 0f, -8f, 8, 0, 16, 0f, false)

        luggage_rack_top_r1 = ModelMapper(modelDataWrapper)
        luggage_rack_top_r1.setPos(-13f, -24f, 0f)
        window_interior.addChild(luggage_rack_top_r1)
        setRotationAngle(luggage_rack_top_r1, 0f, 0f, 0.3491f)
        luggage_rack_top_r1.texOffs(18, 0).addBox(-3f, 0f, -8f, 3, 0, 16, 0f, false)

        luggage_rack_front_r1 = ModelMapper(modelDataWrapper)
        luggage_rack_front_r1.setPos(-12f, -18f, -8f)
        window_interior.addChild(luggage_rack_front_r1)
        setRotationAngle(luggage_rack_front_r1, 0f, 0f, -0.1745f)
        luggage_rack_front_r1.texOffs(327, 612).addBox(0f, -7f, 0f, 0, 7, 16, 0f, false)

        luggage_rack_bottom_front_r1 = ModelMapper(modelDataWrapper)
        luggage_rack_bottom_front_r1.setPos(-14f, -17f, 0f)
        window_interior.addChild(luggage_rack_bottom_front_r1)
        setRotationAngle(luggage_rack_bottom_front_r1, 0f, 0f, -0.5236f)
        luggage_rack_bottom_front_r1.texOffs(18, 16).addBox(0f, 0f, -8f, 3, 0, 16, 0f, false)

        right_top_wall_r2 = ModelMapper(modelDataWrapper)
        right_top_wall_r2.setPos(-22f, -18f, 0f)
        window_interior.addChild(right_top_wall_r2)
        setRotationAngle(right_top_wall_r2, 0f, 0f, -0.384f)
        right_top_wall_r2.texOffs(255, 175).addBox(-5f, 0f, -8f, 5, 0, 16, 0f, false)

        window_interior_wall = ModelMapper(modelDataWrapper)
        window_interior_wall.setPos(0f, 24f, 0f)
        window_interior_wall.texOffs(673, 176).addBox(-30f, -10f, -8f, 0, 12, 16, 0f, false)

        right_upper_wall_r1 = ModelMapper(modelDataWrapper)
        right_upper_wall_r1.setPos(-32f, -10f, 0f)
        window_interior_wall.addChild(right_upper_wall_r1)
        setRotationAngle(right_upper_wall_r1, 0f, 0f, 0.5236f)
        right_upper_wall_r1.texOffs(673, 188).addBox(2f, -9f, -8f, 0, 9, 16, 0f, false)

        right_lower_wall_r1 = ModelMapper(modelDataWrapper)
        right_lower_wall_r1.setPos(-30f, 2f, 0f)
        window_interior_wall.addChild(right_lower_wall_r1)
        setRotationAngle(right_lower_wall_r1, 0f, 0f, -0.2967f)
        right_lower_wall_r1.texOffs(625, 227).addBox(0f, 0f, -8f, 0, 7, 16, 0f, false)

        window_interior_light = ModelMapper(modelDataWrapper)
        window_interior_light.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-21f, -17f, 0f)
        window_interior_light.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.5236f)
        light_r1.texOffs(18, 32).addBox(-2f, 0f, -8f, 2, 0, 16, 0f, false)

        window_interior_blank = ModelMapper(modelDataWrapper)
        window_interior_blank.setPos(0f, 24f, 0f)
        window_interior_blank.texOffs(94, 636).addBox(-32f, 8f, -4f, 32, 1, 8, 0f, false)
        window_interior_blank.texOffs(124, 139).addBox(-21f, -17f, -4f, 7, 0, 8, 0f, false)
        window_interior_blank.texOffs(279, 261).addBox(-8f, -26f, -4f, 8, 0, 8, 0f, false)

        roof_side_r2 = ModelMapper(modelDataWrapper)
        roof_side_r2.setPos(-8f, -26f, 0f)
        window_interior_blank.addChild(roof_side_r2)
        setRotationAngle(roof_side_r2, 0f, 0f, -0.1745f)
        roof_side_r2.texOffs(342, 125).addBox(-8f, 0f, -4f, 8, 0, 8, 0f, false)

        luggage_rack_top_r2 = ModelMapper(modelDataWrapper)
        luggage_rack_top_r2.setPos(-13f, -24f, 0f)
        window_interior_blank.addChild(luggage_rack_top_r2)
        setRotationAngle(luggage_rack_top_r2, 0f, 0f, 0.3491f)
        luggage_rack_top_r2.texOffs(26, 48).addBox(-3f, 0f, -4f, 3, 0, 8, 0f, false)

        luggage_rack_front_r2 = ModelMapper(modelDataWrapper)
        luggage_rack_front_r2.setPos(-12f, -18f, 0f)
        window_interior_blank.addChild(luggage_rack_front_r2)
        setRotationAngle(luggage_rack_front_r2, 0f, 0f, -0.1745f)
        luggage_rack_front_r2.texOffs(350, 146).addBox(0f, -7f, -4f, 0, 7, 8, 0f, false)

        luggage_rack_bottom_front_r2 = ModelMapper(modelDataWrapper)
        luggage_rack_bottom_front_r2.setPos(-14f, -17f, 0f)
        window_interior_blank.addChild(luggage_rack_bottom_front_r2)
        setRotationAngle(luggage_rack_bottom_front_r2, 0f, 0f, -0.5236f)
        luggage_rack_bottom_front_r2.texOffs(26, 56).addBox(0f, 0f, -4f, 3, 0, 8, 0f, false)

        right_top_wall_r3 = ModelMapper(modelDataWrapper)
        right_top_wall_r3.setPos(-22f, -18f, 0f)
        window_interior_blank.addChild(right_top_wall_r3)
        setRotationAngle(right_top_wall_r3, 0f, 0f, -0.384f)
        right_top_wall_r3.texOffs(8, 0).addBox(-5f, 0f, -4f, 5, 0, 8, 0f, false)

        window_interior_blank_wall = ModelMapper(modelDataWrapper)
        window_interior_blank_wall.setPos(0f, 24f, 0f)
        window_interior_blank_wall.texOffs(350, 125).addBox(-30f, -10f, -4f, 0, 12, 8, 0f, false)

        right_upper_wall_r2 = ModelMapper(modelDataWrapper)
        right_upper_wall_r2.setPos(-32f, -10f, 0f)
        window_interior_blank_wall.addChild(right_upper_wall_r2)
        setRotationAngle(right_upper_wall_r2, 0f, 0f, 0.5236f)
        right_upper_wall_r2.texOffs(350, 137).addBox(2f, -9f, -4f, 0, 9, 8, 0f, false)

        right_lower_wall_r2 = ModelMapper(modelDataWrapper)
        right_lower_wall_r2.setPos(-30f, 2f, 0f)
        window_interior_blank_wall.addChild(right_lower_wall_r2)
        setRotationAngle(right_lower_wall_r2, 0f, 0f, -0.2967f)
        right_lower_wall_r2.texOffs(350, 153).addBox(0f, 0f, -4f, 0, 7, 8, 0f, false)

        window_interior_blank_light = ModelMapper(modelDataWrapper)
        window_interior_blank_light.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-21f, -17f, 0f)
        window_interior_blank_light.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.5236f)
        light_r2.texOffs(0, 0).addBox(-2f, 0f, -4f, 2, 0, 8, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 24f, 0f)
        door_left_exterior.texOffs(41, 796).addBox(32f, -10f, -225f, 0, 18, 12, 0f, false)

        right_wall_front_r2 = ModelMapper(modelDataWrapper)
        right_wall_front_r2.setPos(32f, 0f, -225f)
        door_left_exterior.addChild(right_wall_front_r2)
        setRotationAngle(right_wall_front_r2, 0f, 0.1745f, 0f)
        right_wall_front_r2.texOffs(0, 859).addBox(0f, -10f, -4f, 0, 18, 4, 0f, false)

        right_top_wall_front_r1 = ModelMapper(modelDataWrapper)
        right_top_wall_front_r1.setPos(32f, -10f, -225f)
        door_left_exterior.addChild(right_top_wall_front_r1)
        setRotationAngle(right_top_wall_front_r1, 0f, 0.1745f, -0.5236f)
        right_top_wall_front_r1.texOffs(0, 836).addBox(0f, -13f, -4f, 0, 13, 4, 0f, false)

        right_top_wall_r4 = ModelMapper(modelDataWrapper)
        right_top_wall_r4.setPos(32f, -10f, 0f)
        door_left_exterior.addChild(right_top_wall_r4)
        setRotationAngle(right_top_wall_r4, 0f, 0f, -0.5236f)
        right_top_wall_r4.texOffs(0, 812).addBox(0f, -13f, -225f, 0, 13, 12, 0f, false)

        top_back_r2 = ModelMapper(modelDataWrapper)
        top_back_r2.setPos(9f, -33f, -191f)
        door_left_exterior.addChild(top_back_r2)
        setRotationAngle(top_back_r2, 0.0873f, 0f, 0.5236f)
        top_back_r2.texOffs(-16, 808).addBox(13f, 0f, -38f, 9, 0, 16, 0f, false)

        door_left_interior = ModelMapper(modelDataWrapper)
        door_left_interior.setPos(0f, 24f, 0f)
        door_left_interior.texOffs(94, 907).addBox(29f, -10f, -225f, 3, 18, 12, 0f, false)

        right_wall_front_r3 = ModelMapper(modelDataWrapper)
        right_wall_front_r3.setPos(32f, 0f, -225f)
        door_left_interior.addChild(right_wall_front_r3)
        setRotationAngle(right_wall_front_r3, 0f, 0.1745f, 0f)
        right_wall_front_r3.texOffs(0, 910).addBox(-3f, -10f, -4f, 3, 18, 4, 0f, false)

        right_top_wall_front_r2 = ModelMapper(modelDataWrapper)
        right_top_wall_front_r2.setPos(32f, -10f, -225f)
        door_left_interior.addChild(right_top_wall_front_r2)
        setRotationAngle(right_top_wall_front_r2, 0f, 0.1745f, -0.5236f)
        right_top_wall_front_r2.texOffs(14, 915).addBox(-3f, -13f, -4f, 3, 13, 4, 0f, false)

        right_top_wall_r5 = ModelMapper(modelDataWrapper)
        right_top_wall_r5.setPos(32f, -10f, 0f)
        door_left_interior.addChild(right_top_wall_r5)
        setRotationAngle(right_top_wall_r5, 0f, 0f, -0.5236f)
        right_top_wall_r5.texOffs(16, 923).addBox(-3f, -13f, -225f, 3, 13, 12, 0f, false)

        top_back_r3 = ModelMapper(modelDataWrapper)
        top_back_r3.setPos(9f, -33f, -191f)
        door_left_interior.addChild(top_back_r3)
        setRotationAngle(top_back_r3, 0.0873f, 0f, 0.5236f)
        top_back_r3.texOffs(42, 907).addBox(13f, 0f, -38f, 9, 3, 16, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 24f, 0f)
        door_right_exterior.texOffs(136, 794).addBox(-32f, -10f, -225f, 0, 18, 12, 0f, false)

        right_wall_front_r4 = ModelMapper(modelDataWrapper)
        right_wall_front_r4.setPos(-32f, 0f, -225f)
        door_right_exterior.addChild(right_wall_front_r4)
        setRotationAngle(right_wall_front_r4, 0f, -0.1745f, 0f)
        right_wall_front_r4.texOffs(8, 859).addBox(0f, -10f, -4f, 0, 18, 4, 0f, false)

        right_top_wall_front_r3 = ModelMapper(modelDataWrapper)
        right_top_wall_front_r3.setPos(-32f, -10f, -225f)
        door_right_exterior.addChild(right_top_wall_front_r3)
        setRotationAngle(right_top_wall_front_r3, 0f, -0.1745f, 0.5236f)
        right_top_wall_front_r3.texOffs(8, 836).addBox(0f, -13f, -4f, 0, 13, 4, 0f, false)

        right_top_wall_r6 = ModelMapper(modelDataWrapper)
        right_top_wall_r6.setPos(-32f, -10f, 0f)
        door_right_exterior.addChild(right_top_wall_r6)
        setRotationAngle(right_top_wall_r6, 0f, 0f, 0.5236f)
        right_top_wall_r6.texOffs(26, 857).addBox(0f, -13f, -225f, 0, 13, 12, 0f, false)

        top_back_r4 = ModelMapper(modelDataWrapper)
        top_back_r4.setPos(-9f, -33f, -191f)
        door_right_exterior.addChild(top_back_r4)
        setRotationAngle(top_back_r4, 0.0873f, 0f, -0.5236f)
        top_back_r4.texOffs(82, 808).addBox(-22f, 0f, -38f, 9, 0, 16, 0f, false)

        door_right_interior = ModelMapper(modelDataWrapper)
        door_right_interior.setPos(0f, 24f, 0f)
        door_right_interior.texOffs(28, 893).addBox(-32f, -10f, -225f, 3, 18, 12, 0f, false)

        right_wall_front_r5 = ModelMapper(modelDataWrapper)
        right_wall_front_r5.setPos(-32f, 0f, -225f)
        door_right_interior.addChild(right_wall_front_r5)
        setRotationAngle(right_wall_front_r5, 0f, -0.1745f, 0f)
        right_wall_front_r5.texOffs(14, 893).addBox(0f, -10f, -4f, 3, 18, 4, 0f, false)

        right_top_wall_front_r4 = ModelMapper(modelDataWrapper)
        right_top_wall_front_r4.setPos(-32f, -10f, -225f)
        door_right_interior.addChild(right_top_wall_front_r4)
        setRotationAngle(right_top_wall_front_r4, 0f, -0.1745f, 0.5236f)
        right_top_wall_front_r4.texOffs(0, 893).addBox(0f, -13f, -4f, 3, 13, 4, 0f, false)

        right_top_wall_r7 = ModelMapper(modelDataWrapper)
        right_top_wall_r7.setPos(-32f, -10f, 0f)
        door_right_interior.addChild(right_top_wall_r7)
        setRotationAngle(right_top_wall_r7, 0f, 0f, 0.5236f)
        right_top_wall_r7.texOffs(76, 894).addBox(0f, -13f, -225f, 3, 13, 12, 0f, false)

        top_back_r5 = ModelMapper(modelDataWrapper)
        top_back_r5.setPos(-9f, -33f, -191f)
        door_right_interior.addChild(top_back_r5)
        setRotationAngle(top_back_r5, 0.0873f, 0f, -0.5236f)
        top_back_r5.texOffs(46, 875).addBox(-22f, 0f, -38f, 9, 3, 16, 0f, false)

        head_interior = ModelMapper(modelDataWrapper)
        head_interior.setPos(0f, 24f, 0f)
        head_interior.texOffs(673, 192).addBox(-32f, 8f, -230f, 64, 1, 32, 0f, false)
        head_interior.texOffs(759, 240).addBox(-30f, -26f, -230f, 60, 34, 0, 0f, false)
        head_interior.texOffs(424, 777).addBox(8f, -26f, -206f, 22, 34, 8, 0f, false)
        head_interior.texOffs(313, 556).addBox(-30f, -26f, -206f, 22, 34, 8, 0f, false)
        head_interior.texOffs(92, 139).addBox(-8f, -26f, -206f, 16, 0, 8, 0f, false)

        right_door_top_r1 = ModelMapper(modelDataWrapper)
        right_door_top_r1.setPos(-9f, -33f, -191f)
        head_interior.addChild(right_door_top_r1)
        setRotationAngle(right_door_top_r1, 0.0873f, 0f, -0.5236f)
        right_door_top_r1.texOffs(46, 879).addBox(-13f, 0f, -39f, 0, 2, 17, 0f, true)

        left_door_top_r1 = ModelMapper(modelDataWrapper)
        left_door_top_r1.setPos(9f, -33f, -191f)
        head_interior.addChild(left_door_top_r1)
        setRotationAngle(left_door_top_r1, 0.0873f, 0f, 0.5236f)
        left_door_top_r1.texOffs(46, 877).addBox(13f, 0f, -39f, 0, 2, 17, 0f, false)

        roof_front_r1 = ModelMapper(modelDataWrapper)
        roof_front_r1.setPos(0f, -26f, -206f)
        head_interior.addChild(roof_front_r1)
        setRotationAngle(roof_front_r1, 0.0873f, 0f, 0f)
        roof_front_r1.texOffs(69, 556).addBox(-8f, 0f, -25f, 16, 0, 25, 0f, false)

        right_roof_side_r1 = ModelMapper(modelDataWrapper)
        right_roof_side_r1.setPos(-8f, -26f, -206f)
        head_interior.addChild(right_roof_side_r1)
        setRotationAngle(right_roof_side_r1, 0.0873f, 0f, -0.1745f)
        right_roof_side_r1.texOffs(311, 192).addBox(-15f, 0f, -25f, 15, 0, 25, 0f, false)

        left_roof_side_r1 = ModelMapper(modelDataWrapper)
        left_roof_side_r1.setPos(8f, -26f, -206f)
        head_interior.addChild(left_roof_side_r1)
        setRotationAngle(left_roof_side_r1, 0.0873f, 0f, 0.1745f)
        left_roof_side_r1.texOffs(311, 217).addBox(0f, 0f, -25f, 15, 0, 25, 0f, false)

        right_upper_wall_r3 = ModelMapper(modelDataWrapper)
        right_upper_wall_r3.setPos(-32f, -10f, -206f)
        head_interior.addChild(right_upper_wall_r3)
        setRotationAngle(right_upper_wall_r3, 0f, -0.0436f, 0.5236f)
        right_upper_wall_r3.texOffs(699, 700).addBox(2f, -17f, -25f, 0, 17, 25, 0f, false)

        left_upper_wall_r1 = ModelMapper(modelDataWrapper)
        left_upper_wall_r1.setPos(32f, -10f, -206f)
        head_interior.addChild(left_upper_wall_r1)
        setRotationAngle(left_upper_wall_r1, 0f, 0.0436f, -0.5236f)
        left_upper_wall_r1.texOffs(511, 701).addBox(-2f, -17f, -25f, 0, 17, 25, 0f, false)

        right_wall_r3 = ModelMapper(modelDataWrapper)
        right_wall_r3.setPos(-30f, 2f, -206f)
        head_interior.addChild(right_wall_r3)
        setRotationAngle(right_wall_r3, 0f, -0.0436f, 0f)
        right_wall_r3.texOffs(445, 713).addBox(0f, -12f, -25f, 0, 12, 25, 0f, false)

        left_wall_r3 = ModelMapper(modelDataWrapper)
        left_wall_r3.setPos(30f, 2f, -206f)
        head_interior.addChild(left_wall_r3)
        setRotationAngle(left_wall_r3, 0f, 0.0436f, 0f)
        left_wall_r3.texOffs(699, 717).addBox(0f, -12f, -25f, 0, 12, 25, 0f, false)

        right_lower_wall_r3 = ModelMapper(modelDataWrapper)
        right_lower_wall_r3.setPos(-30f, 2f, -206f)
        head_interior.addChild(right_lower_wall_r3)
        setRotationAngle(right_lower_wall_r3, 0f, -0.0436f, -0.2967f)
        right_lower_wall_r3.texOffs(231, 114).addBox(0f, 0f, -25f, 0, 7, 25, 0f, false)

        left_lower_wall_r1 = ModelMapper(modelDataWrapper)
        left_lower_wall_r1.setPos(30f, 2f, -206f)
        head_interior.addChild(left_lower_wall_r1)
        setRotationAngle(left_lower_wall_r1, 0f, 0.0436f, 0.2967f)
        left_lower_wall_r1.texOffs(575, 218).addBox(0f, 0f, -25f, 0, 7, 25, 0f, false)

        end_interior = ModelMapper(modelDataWrapper)
        end_interior.setPos(0f, 24f, 0f)
        end_interior.texOffs(79, 236).addBox(-32f, 8f, 106f, 64, 1, 80, 0f, false)
        end_interior.texOffs(758, 559).addBox(-30f, -26f, 186f, 60, 34, 0, 0f, false)
        end_interior.texOffs(354, 777).addBox(8f, -26f, 146f, 17, 34, 18, 0f, false)
        end_interior.texOffs(284, 777).addBox(-25f, -26f, 146f, 17, 34, 18, 0f, false)
        end_interior.texOffs(0, 175).addBox(-8f, -26f, 106f, 16, 0, 81, 0f, false)
        end_interior.texOffs(0, 0).addBox(14f, -17f, 106f, 7, 0, 40, 0f, false)
        end_interior.texOffs(0, 40).addBox(-21f, -17f, 106f, 7, 0, 40, 0f, false)

        right_luggage_rack_top_r1 = ModelMapper(modelDataWrapper)
        right_luggage_rack_top_r1.setPos(-13f, -24f, 0f)
        end_interior.addChild(right_luggage_rack_top_r1)
        setRotationAngle(right_luggage_rack_top_r1, 0f, 0f, 0.3491f)
        right_luggage_rack_top_r1.texOffs(0, 80).addBox(-3f, 0f, 106f, 3, 0, 40, 0f, false)

        left_luggage_rack_top_r1 = ModelMapper(modelDataWrapper)
        left_luggage_rack_top_r1.setPos(13f, -24f, 0f)
        end_interior.addChild(left_luggage_rack_top_r1)
        setRotationAngle(left_luggage_rack_top_r1, 0f, 0f, -0.3491f)
        left_luggage_rack_top_r1.texOffs(14, 40).addBox(0f, 0f, 106f, 3, 0, 40, 0f, false)

        right_luggage_rack_front_r1 = ModelMapper(modelDataWrapper)
        right_luggage_rack_front_r1.setPos(-12f, -18f, 0f)
        end_interior.addChild(right_luggage_rack_front_r1)
        setRotationAngle(right_luggage_rack_front_r1, 0f, 0f, -0.1745f)
        right_luggage_rack_front_r1.texOffs(287, 257).addBox(0f, -7f, 106f, 0, 7, 40, 0f, false)

        left_luggage_rack_front_r1 = ModelMapper(modelDataWrapper)
        left_luggage_rack_front_r1.setPos(12f, -18f, 0f)
        end_interior.addChild(left_luggage_rack_front_r1)
        setRotationAngle(left_luggage_rack_front_r1, 0f, 0f, 0.1745f)
        left_luggage_rack_front_r1.texOffs(0, 267).addBox(0f, -7f, 106f, 0, 7, 40, 0f, false)

        right_luggage_rack_bottom_front_r1 = ModelMapper(modelDataWrapper)
        right_luggage_rack_bottom_front_r1.setPos(-14f, -17f, 0f)
        end_interior.addChild(right_luggage_rack_bottom_front_r1)
        setRotationAngle(right_luggage_rack_bottom_front_r1, 0f, 0f, -0.5236f)
        right_luggage_rack_bottom_front_r1.texOffs(6, 80).addBox(0f, 0f, 106f, 3, 0, 40, 0f, false)

        left_luggage_rack_bottom_front_r1 = ModelMapper(modelDataWrapper)
        left_luggage_rack_bottom_front_r1.setPos(14f, -17f, 0f)
        end_interior.addChild(left_luggage_rack_bottom_front_r1)
        setRotationAngle(left_luggage_rack_bottom_front_r1, 0f, 0f, 0.5236f)
        left_luggage_rack_bottom_front_r1.texOffs(14, 0).addBox(-3f, 0f, 106f, 3, 0, 40, 0f, false)

        right_top_wall_r8 = ModelMapper(modelDataWrapper)
        right_top_wall_r8.setPos(-22f, -18f, 0f)
        end_interior.addChild(right_top_wall_r8)
        setRotationAngle(right_top_wall_r8, 0f, 0f, -0.384f)
        right_top_wall_r8.texOffs(105, 58).addBox(-5f, 0f, 106f, 5, 0, 28, 0f, false)

        left_top_wall_r2 = ModelMapper(modelDataWrapper)
        left_top_wall_r2.setPos(22f, -18f, 0f)
        end_interior.addChild(left_top_wall_r2)
        setRotationAngle(left_top_wall_r2, 0f, 0f, 0.384f)
        left_top_wall_r2.texOffs(105, 30).addBox(0f, 0f, 106f, 5, 0, 28, 0f, false)

        right_roof_side_r2 = ModelMapper(modelDataWrapper)
        right_roof_side_r2.setPos(-8f, -26f, 0f)
        end_interior.addChild(right_roof_side_r2)
        setRotationAngle(right_roof_side_r2, 0f, 0f, -0.1745f)
        right_roof_side_r2.texOffs(225, 192).addBox(-15f, 0f, 106f, 15, 0, 81, 0f, false)

        left_roof_side_r2 = ModelMapper(modelDataWrapper)
        left_roof_side_r2.setPos(8f, -26f, 0f)
        end_interior.addChild(left_roof_side_r2)
        setRotationAngle(left_roof_side_r2, 0f, 0f, 0.1745f)
        left_roof_side_r2.texOffs(246, 0).addBox(0f, 0f, 106f, 15, 0, 81, 0f, false)

        right_upper_wall_r4 = ModelMapper(modelDataWrapper)
        right_upper_wall_r4.setPos(-32f, -10f, 106f)
        end_interior.addChild(right_upper_wall_r4)
        setRotationAngle(right_upper_wall_r4, 0f, 0.0873f, 0.5236f)
        right_upper_wall_r4.texOffs(374, 553).addBox(2f, -23f, 0f, 0, 23, 81, 0f, false)

        left_upper_wall_r2 = ModelMapper(modelDataWrapper)
        left_upper_wall_r2.setPos(32f, -10f, 106f)
        end_interior.addChild(left_upper_wall_r2)
        setRotationAngle(left_upper_wall_r2, 0f, -0.0873f, -0.5236f)
        left_upper_wall_r2.texOffs(536, 553).addBox(-2f, -23f, 0f, 0, 23, 81, 0f, false)

        right_wall_r4 = ModelMapper(modelDataWrapper)
        right_wall_r4.setPos(-30f, 2f, 106f)
        end_interior.addChild(right_wall_r4)
        setRotationAngle(right_wall_r4, 0f, 0.1309f, 0f)
        right_wall_r4.texOffs(184, 555).addBox(0f, -16f, 0f, 0, 16, 81, 0f, false)

        left_wall_r4 = ModelMapper(modelDataWrapper)
        left_wall_r4.setPos(30f, 2f, 106f)
        end_interior.addChild(left_wall_r4)
        setRotationAngle(left_wall_r4, 0f, -0.1309f, 0f)
        left_wall_r4.texOffs(184, 571).addBox(0f, -16f, 0f, 0, 16, 81, 0f, false)

        right_lower_wall_r4 = ModelMapper(modelDataWrapper)
        right_lower_wall_r4.setPos(-30f, 2f, 106f)
        end_interior.addChild(right_lower_wall_r4)
        setRotationAngle(right_lower_wall_r4, 0f, 0.1309f, -0.2967f)
        right_lower_wall_r4.texOffs(575, 192).addBox(0f, 0f, 0f, 0, 10, 81, 0f, false)

        left_lower_wall_r2 = ModelMapper(modelDataWrapper)
        left_lower_wall_r2.setPos(30f, 2f, 106f)
        end_interior.addChild(left_lower_wall_r2)
        setRotationAngle(left_lower_wall_r2, 0f, -0.1309f, 0.2967f)
        left_lower_wall_r2.texOffs(575, 202).addBox(0f, 0f, 0f, 0, 10, 81, 0f, false)

        end_light = ModelMapper(modelDataWrapper)
        end_light.setPos(0f, 24f, 0f)


        left_light_r1 = ModelMapper(modelDataWrapper)
        left_light_r1.setPos(21f, -17f, 0f)
        end_light.addChild(left_light_r1)
        setRotationAngle(left_light_r1, 0f, 0f, -0.5236f)
        left_light_r1.texOffs(20, 0).addBox(0f, 0f, 106f, 2, 0, 40, 0f, false)

        right_light_r1 = ModelMapper(modelDataWrapper)
        right_light_r1.setPos(-21f, -17f, 0f)
        end_light.addChild(right_light_r1)
        setRotationAngle(right_light_r1, 0f, 0f, 0.5236f)
        right_light_r1.texOffs(20, 40).addBox(-2f, 0f, 106f, 2, 0, 40, 0f, false)

        emergency_exit = ModelMapper(modelDataWrapper)
        emergency_exit.setPos(0f, 24f, 0f)
        emergency_exit.texOffs(68, 763).addBox(30f, -10f, -12f, 0, 12, 24, 0f, false)
        emergency_exit.texOffs(511, 690).addBox(-30f, -10f, -12f, 0, 12, 24, 0f, false)

        right_upper_wall_r5 = ModelMapper(modelDataWrapper)
        right_upper_wall_r5.setPos(-32f, -10f, 0f)
        emergency_exit.addChild(right_upper_wall_r5)
        setRotationAngle(right_upper_wall_r5, 0f, 0f, 0.5236f)
        right_upper_wall_r5.texOffs(220, 661).addBox(2f, -9f, -12f, 0, 9, 24, 0f, false)

        right_lower_wall_r5 = ModelMapper(modelDataWrapper)
        right_lower_wall_r5.setPos(-30f, 2f, 0f)
        emergency_exit.addChild(right_lower_wall_r5)
        setRotationAngle(right_lower_wall_r5, 0f, 0f, -0.2967f)
        right_lower_wall_r5.texOffs(80, 283).addBox(0f, 0f, -12f, 0, 7, 24, 0f, false)

        left_upper_wall_r3 = ModelMapper(modelDataWrapper)
        left_upper_wall_r3.setPos(32f, -10f, 0f)
        emergency_exit.addChild(left_upper_wall_r3)
        setRotationAngle(left_upper_wall_r3, 0f, 0f, -0.5236f)
        left_upper_wall_r3.texOffs(184, 753).addBox(-2f, -9f, -12f, 0, 9, 24, 0f, false)

        left_lower_wall_r3 = ModelMapper(modelDataWrapper)
        left_lower_wall_r3.setPos(30f, 2f, 0f)
        emergency_exit.addChild(left_lower_wall_r3)
        setRotationAngle(left_lower_wall_r3, 0f, 0f, 0.2967f)
        left_lower_wall_r3.texOffs(0, 771).addBox(0f, 0f, -12f, 0, 7, 24, 0f, false)

        seat_nice = ModelMapper(modelDataWrapper)
        seat_nice.setPos(0f, 24f, 0f)
        seat_nice.texOffs(864, 102).addBox(-12f, 1f, -4f, 24, 2, 6, 0f, false)
        seat_nice.texOffs(873, 157).addBox(4.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_nice.texOffs(889, 157).addBox(-3.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_nice.texOffs(905, 157).addBox(-11.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_nice.texOffs(47, 119).addBox(11.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_nice.texOffs(47, 119).addBox(3.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_nice.texOffs(47, 119).addBox(-4.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_nice.texOffs(47, 119).addBox(-12.5f, -2f, -3f, 1, 1, 5, 0f, false)

        back_bottom_right_r2 = ModelMapper(modelDataWrapper)
        back_bottom_right_r2.setPos(0f, 3f, 2f)
        seat_nice.addChild(back_bottom_right_r2)
        setRotationAngle(back_bottom_right_r2, -0.1745f, 0f, 0f)
        back_bottom_right_r2.texOffs(907, 87).addBox(-11.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)
        back_bottom_right_r2.texOffs(891, 87).addBox(-3.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)
        back_bottom_right_r2.texOffs(875, 87).addBox(4.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)

        seat_normal = ModelMapper(modelDataWrapper)
        seat_normal.setPos(0f, 24f, 0f)
        seat_normal.texOffs(875, 79).addBox(-12f, 1f, -4f, 24, 2, 6, 0f, false)
        seat_normal.texOffs(825, 157).addBox(4.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_normal.texOffs(841, 157).addBox(-3.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_normal.texOffs(857, 157).addBox(-11.5f, -11f, 2.5f, 7, 6, 1, 0f, false)
        seat_normal.texOffs(0, 3).addBox(11.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_normal.texOffs(0, 3).addBox(3.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_normal.texOffs(0, 3).addBox(-4.5f, -2f, -3f, 1, 1, 5, 0f, false)
        seat_normal.texOffs(0, 3).addBox(-12.5f, -2f, -3f, 1, 1, 5, 0f, false)

        back_bottom_right_r3 = ModelMapper(modelDataWrapper)
        back_bottom_right_r3.setPos(0f, 3f, 2f)
        seat_normal.addChild(back_bottom_right_r3)
        setRotationAngle(back_bottom_right_r3, -0.1745f, 0f, 0f)
        back_bottom_right_r3.texOffs(826, 111).addBox(-11.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)
        back_bottom_right_r3.texOffs(810, 111).addBox(-3.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)
        back_bottom_right_r3.texOffs(794, 111).addBox(4.5f, -11.2f, -1.2f, 7, 11, 1, 0.2f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        exterior.setModelPart()
        window_interior.setModelPart()
        window_interior_wall.setModelPart()
        window_interior_light.setModelPart()
        window_interior_blank.setModelPart()
        window_interior_blank_wall.setModelPart()
        window_interior_blank_light.setModelPart()
        door_left_exterior.setModelPart()
        door_left_interior.setModelPart()
        door_right_exterior.setModelPart()
        door_right_interior.setModelPart()
        head_interior.setModelPart()
        end_interior.setModelPart()
        end_light.setModelPart()
        emergency_exit.setModelPart()
        seat_nice.setModelPart()
        seat_normal.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelA320 {
        return ModelA320(doorAnimationType, renderDoorOverlay)
    }

    @Override
    override fun baseTransform(matrices: PoseStack?) {
        matrices!!.translate(0.0, -3.5, 2.375)
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
            RenderStage.LIGHTS -> {
                renderOnce(end_light, matrices, vertices, light, position.toFloat())
                renderMirror(window_interior_blank_light, matrices, vertices, light, (position - 194).toFloat())

                run {
                    var i = 0
                    while (i < 7) {
                        renderMirror(
                            window_interior_light,
                            matrices,
                            vertices,
                            light,
                            (position + i * 16 - 182).toFloat()
                        )
                        i++
                    }
                }

                renderMirror(window_interior_light, matrices, vertices, light, (position - 70).toFloat())
                renderMirror(window_interior_blank_light, matrices, vertices, light, (position - 58).toFloat())

                var i = 0
                while (i < 10) {
                    renderMirror(window_interior_light, matrices, vertices, light, (position + i * 16 - 46).toFloat())
                    i++
                }
            }

            RenderStage.INTERIOR -> {
                renderOnce(head_interior, matrices, vertices, light, position.toFloat())
                renderOnce(end_interior, matrices, vertices, light, position.toFloat())

                renderMirror(window_interior_blank, matrices, vertices, light, (position - 194).toFloat())
                renderMirror(window_interior_blank_wall, matrices, vertices, light, (position - 194).toFloat())

                run {
                    var i = 0
                    while (i < 7) {
                        renderMirror(window_interior, matrices, vertices, light, (position + i * 16 - 182).toFloat())
                        renderMirror(
                            window_interior_wall,
                            matrices,
                            vertices,
                            light,
                            (position + i * 16 - 182).toFloat()
                        )
                        i++
                    }
                }

                renderOnce(emergency_exit, matrices, vertices, light, (position - 66).toFloat())
                renderMirror(window_interior, matrices, vertices, light, (position - 70).toFloat())
                renderMirror(window_interior_blank, matrices, vertices, light, (position - 58).toFloat())

                var i = 0
                while (i < 10) {
                    renderMirror(window_interior, matrices, vertices, light, (position + i * 16 - 46).toFloat())
                    renderMirror(window_interior_wall, matrices, vertices, light, (position + i * 16 - 46).toFloat())
                    i++
                }

                renderOnce(door_left_interior, matrices, vertices, light, -doorLeftX * 6, position + doorLeftZ)
                renderOnce(door_right_interior, matrices, vertices, light, doorRightX * 6, position + doorRightZ)

                if (renderDetails) {
                    run {
                        var i = 0
                        while (i < 12) {
                            renderOnce(seat_nice, matrices, vertices, light, -16f, (i * 12 - 189).toFloat())
                            renderOnce(seat_nice, matrices, vertices, light, 16f, (i * 12 - 189).toFloat())
                            i++
                        }
                    }
                    var i = 0
                    while (i < 18) {
                        renderOnce(seat_normal, matrices, vertices, light, -16f, (i * 11 - 47).toFloat())
                        renderOnce(seat_normal, matrices, vertices, light, 16f, (i * 11 - 47).toFloat())
                        i++
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(exterior, matrices, vertices, light, position.toFloat())
                renderOnce(door_left_exterior, matrices, vertices, light, -doorLeftX * 6, position + doorLeftZ)
                renderOnce(door_right_exterior, matrices, vertices, light, doorRightX * 6, position + doorRightZ)
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
        return intArrayOf(0)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(0, 0)
    }

    @Override
    override fun getDoorMax(): Int {
        return DOOR_MAX
    }

    companion object {
        private const val DOOR_MAX = 16
    }
}
