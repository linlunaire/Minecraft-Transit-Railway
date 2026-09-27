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

open class ModelSP1900 protected constructor(
    @JvmField protected val isC1141A: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelSP1900?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_c1141a_r1: ModelMapper
    private val window_exterior_1: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_exterior_2: ModelMapper
    private val upper_wall_r3: ModelMapper
    private val side_panel_sp1900: ModelMapper
    private val handrail_7_r1: ModelMapper
    private val handrail_6_r1: ModelMapper
    private val handrail_4_r1: ModelMapper
    private val handrail_3_r1: ModelMapper
    private val handrail_2_r1: ModelMapper
    private val side_panel_sp1900_translucent: ModelMapper
    private val side_panel_c1141a: ModelMapper
    private val handrail_5_r1: ModelMapper
    private val handrail_3_r2: ModelMapper
    private val side_panel_c1141a_translucent: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_light_c1141a: ModelMapper
    private val door_exterior_1: ModelMapper
    private val door_right_exterior_1: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_left_exterior_1: ModelMapper
    private val door_right_top_r2_r1: ModelMapper
    private val door_exterior_2: ModelMapper
    private val door_left_exterior_2: ModelMapper
    private val door_left_top_r3: ModelMapper
    private val door_right_exterior_2: ModelMapper
    private val door_right_top_r3_r1: ModelMapper
    private val end: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val seat_end_1: ModelMapper
    private val seat_back_1_c1141a_r1: ModelMapper
    private val seat_end_2: ModelMapper
    private val seat_back_2_c1141a_r1: ModelMapper
    private val seat_bottom_2_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val floor_1_r1: ModelMapper
    private val roof_sp1900: ModelMapper
    private val inner_roof_6_r1: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_c1141a: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r2: ModelMapper
    private val outer_roof_1_r2: ModelMapper
    private val roof_light_sp1900: ModelMapper
    private val light_3_r1: ModelMapper
    private val light_1_r1: ModelMapper
    private val roof_light_c1141a: ModelMapper
    private val roof_end_sp1900: ModelMapper
    private val inner_roof_1: ModelMapper
    private val inner_roof_6_r2: ModelMapper
    private val inner_roof_5_r2: ModelMapper
    private val inner_roof_4_r2: ModelMapper
    private val inner_roof_3_r3: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val inner_roof_2: ModelMapper
    private val inner_roof_6_r3: ModelMapper
    private val inner_roof_5_r3: ModelMapper
    private val inner_roof_4_r3: ModelMapper
    private val inner_roof_3_r4: ModelMapper
    private val inner_roof_2_r3: ModelMapper
    private val roof_end_c1141a: ModelMapper
    private val inner_roof_3: ModelMapper
    private val inner_roof_3_r5: ModelMapper
    private val inner_roof_4: ModelMapper
    private val inner_roof_3_r6: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val vent_2_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val outer_roof_1: ModelMapper
    private val outer_roof_3_r2: ModelMapper
    private val outer_roof_2_r3: ModelMapper
    private val outer_roof_2: ModelMapper
    private val outer_roof_3_r3: ModelMapper
    private val outer_roof_2_r4: ModelMapper
    private val roof_end_light_sp1900: ModelMapper
    private val light_6_r1: ModelMapper
    private val light_4_r1: ModelMapper
    private val light_3_r2: ModelMapper
    private val light_1_r2: ModelMapper
    private val roof_end_light_c1141a: ModelMapper
    private val top_handrail_sp1900: ModelMapper
    private val top_handrail_bottom_right_r1: ModelMapper
    private val top_handrail_bottom_left_r1: ModelMapper
    private val top_handrail_right_3_r1: ModelMapper
    private val top_handrail_right_2_r1: ModelMapper
    private val top_handrail_left_3_r1: ModelMapper
    private val top_handrail_left_2_r1: ModelMapper
    private val handrail_strap_1: ModelMapper
    private val top_handrail_c1141a: ModelMapper
    private val pole_bottom_diagonal_2_r1: ModelMapper
    private val pole_bottom_diagonal_1_r1: ModelMapper
    private val pole_top_diagonal_2_r1: ModelMapper
    private val pole_top_diagonal_1_r1: ModelMapper
    private val top_handrail_connector_bottom_4_r1: ModelMapper
    private val top_handrail_connector_bottom_3_r1: ModelMapper
    private val top_handrail_bottom_right_r2: ModelMapper
    private val top_handrail_bottom_left_r2: ModelMapper
    private val top_handrail_right_4_r1: ModelMapper
    private val top_handrail_right_3_r2: ModelMapper
    private val top_handrail_left_4_r1: ModelMapper
    private val top_handrail_left_3_r2: ModelMapper
    private val handrail_strap_2: ModelMapper
    private val handrail_strap_8_r1: ModelMapper
    private val tv_pole: ModelMapper
    private val tv_right_r1: ModelMapper
    private val tv_left_r1: ModelMapper
    private val pole_5_r1: ModelMapper
    private val pole_4_r1: ModelMapper
    private val pole_2_r1: ModelMapper
    private val pole_1_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val seat_head_1: ModelMapper
    private val seat_back_c1141a_r2: ModelMapper
    private val seat_head_2: ModelMapper
    private val seat_back_c1141a_r3: ModelMapper
    private val seat_bottom_r1: ModelMapper
    private val head_exterior: ModelMapper
    private val outer_roof_1_r3: ModelMapper
    private val outer_roof_2_r5: ModelMapper
    private val driver_door_top_2_r1: ModelMapper
    private val driver_door_top_1_r1: ModelMapper
    private val floor_2_r1: ModelMapper
    private val front: ModelMapper
    private val front_middle_r1_r1: ModelMapper
    private val front_roof_r1_r1: ModelMapper
    private val bottom_r1_r1: ModelMapper
    private val front_bottom_r1_r1: ModelMapper
    private val front_side_1_r1_r1: ModelMapper
    private val front_side_2_r1_r1: ModelMapper
    private val bottom_side_1_r1_r1: ModelMapper
    private val bottom_side_2_r1_r1: ModelMapper
    private val top_side_1_r1_r1: ModelMapper
    private val top_side_2_r1_r1: ModelMapper
    private val roof_side_1_r1_r1: ModelMapper
    private val roof_side_2_r1_r1: ModelMapper
    private val roof_middle_corner_1_r1_r1: ModelMapper
    private val roof_middle_corner_2_r1_r1: ModelMapper
    private val roof_corner_1_r1_r1: ModelMapper
    private val roof_corner_2_r1_r1: ModelMapper
    private val bottom_corner_1_r1_r1: ModelMapper
    private val bottom_corner_2_r1_r1: ModelMapper
    private val top_handrail_head_sp1900: ModelMapper
    private val top_handrail_bottom_left_r3: ModelMapper
    private val top_handrail_bottom_right_r3: ModelMapper
    private val top_handrail_right_3_r3: ModelMapper
    private val top_handrail_right_2_r2: ModelMapper
    private val top_handrail_left_3_r3: ModelMapper
    private val top_handrail_left_2_r2: ModelMapper
    private val handrail_strap_head: ModelMapper
    private val top_handrail_head_c1141a: ModelMapper
    private val pole_bottom_diagonal_3_r1: ModelMapper
    private val pole_bottom_diagonal_2_r2: ModelMapper
    private val pole_top_diagonal_3_r1: ModelMapper
    private val pole_top_diagonal_2_r2: ModelMapper
    private val top_handrail_connector_bottom_5_r1: ModelMapper
    private val top_handrail_connector_bottom_right_4_r1: ModelMapper
    private val top_handrail_connector_bottom_left_4_r1: ModelMapper
    private val top_handrail_connector_bottom_right_3_r1: ModelMapper
    private val top_handrail_connector_bottom_left_3_r1: ModelMapper
    private val top_handrail_bottom_right_r4: ModelMapper
    private val top_handrail_right_5_r1: ModelMapper
    private val top_handrail_right_4_r2: ModelMapper
    private val top_handrail_left_5_r1: ModelMapper
    private val top_handrail_left_4_r2: ModelMapper
    private val handrail_strap_3: ModelMapper
    private val handrail_strap_right_8_r1: ModelMapper
    private val handrail_strap_left_8_r1: ModelMapper
    private val headlights: ModelMapper
    private val headlight_4_r1_r1: ModelMapper
    private val headlight_3_r1_r1: ModelMapper
    private val headlight_1_r1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_4_r1_r1: ModelMapper
    private val tail_light_3_r1_r1: ModelMapper
    private val tail_light_1_r1_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper
    private val bb_main: ModelMapper

    constructor(isC1141A: Boolean) : this(isC1141A, DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 416
        val textureHeight = 416

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(58, 115).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)
        window.texOffs(326, 119).addBox(-20f, -14f, -18f, 2, 14, 36, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(134, 311).addBox(0f, -19f, -18f, 2, 19, 36, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(-9f, 0f, 0f)
        window.addChild(seat)
        seat.texOffs(228, 192).addBox(-9f, -6f, -16f, 7, 1, 32, 0f, false)

        seat_back_c1141a_r1 = ModelMapper(modelDataWrapper)
        seat_back_c1141a_r1.setPos(-9f, -6.5f, 0f)
        seat.addChild(seat_back_c1141a_r1)
        setRotationAngle(seat_back_c1141a_r1, 0f, 0f, -0.0873f)
        seat_back_c1141a_r1.texOffs(254, 71).addBox(0f, -6f, -12f, 1, 4, 24, 0f, false)
        seat_back_c1141a_r1.texOffs(212, 351).addBox(0f, -8f, -16f, 1, 8, 32, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)
        window_exterior_1.texOffs(324, 343).addBox(20f, 0f, -16f, 1, 7, 32, 0f, true)
        window_exterior_1.texOffs(128, 46).addBox(20f, -14f, -18f, 0, 14, 36, 0f, true)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(20f, -14f, 0f)
        window_exterior_1.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, -0.1107f)
        upper_wall_r2.texOffs(58, 112).addBox(0f, -19f, -18f, 0, 19, 36, 0f, true)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)
        window_exterior_2.texOffs(324, 343).addBox(-21f, 0f, -16f, 1, 7, 32, 0f, false)
        window_exterior_2.texOffs(128, 46).addBox(-20f, -14f, -18f, 0, 14, 36, 0f, false)

        upper_wall_r3 = ModelMapper(modelDataWrapper)
        upper_wall_r3.setPos(-20f, -14f, 0f)
        window_exterior_2.addChild(upper_wall_r3)
        setRotationAngle(upper_wall_r3, 0f, 0f, 0.1107f)
        upper_wall_r3.texOffs(58, 112).addBox(0f, -19f, -18f, 0, 19, 36, 0f, false)

        side_panel_sp1900 = ModelMapper(modelDataWrapper)
        side_panel_sp1900.setPos(0f, 24f, 0f)
        side_panel_sp1900.texOffs(38, 188).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)
        side_panel_sp1900.texOffs(12, 18).addBox(-12f, -16f, 0f, 0, 10, 0, 0.2f, false)
        side_panel_sp1900.texOffs(12, 18).addBox(-10.3698f, -26.2455f, 0f, 0, 3, 0, 0.2f, false)

        handrail_7_r1 = ModelMapper(modelDataWrapper)
        handrail_7_r1.setPos(-11.8689f, -31.7477f, 0f)
        side_panel_sp1900.addChild(handrail_7_r1)
        setRotationAngle(handrail_7_r1, 0f, 0f, -0.3491f)
        handrail_7_r1.texOffs(12, 18).addBox(0f, -3f, 0f, 0, 6, 0, 0.2f, false)

        handrail_6_r1 = ModelMapper(modelDataWrapper)
        handrail_6_r1.setPos(-10.5751f, -27.5926f, 0f)
        side_panel_sp1900.addChild(handrail_6_r1)
        setRotationAngle(handrail_6_r1, 0f, 0f, -0.1745f)
        handrail_6_r1.texOffs(12, 18).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_4_r1 = ModelMapper(modelDataWrapper)
        handrail_4_r1.setPos(-10.5751f, -21.8985f, 0f)
        side_panel_sp1900.addChild(handrail_4_r1)
        setRotationAngle(handrail_4_r1, 0f, 0f, 0.1745f)
        handrail_4_r1.texOffs(12, 18).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_3_r1 = ModelMapper(modelDataWrapper)
        handrail_3_r1.setPos(-11.1849f, -19.6228f, 0f)
        side_panel_sp1900.addChild(handrail_3_r1)
        setRotationAngle(handrail_3_r1, 0f, 0f, 0.3491f)
        handrail_3_r1.texOffs(12, 18).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        handrail_2_r1 = ModelMapper(modelDataWrapper)
        handrail_2_r1.setPos(-11.7947f, -17.347f, 0f)
        side_panel_sp1900.addChild(handrail_2_r1)
        setRotationAngle(handrail_2_r1, 0f, 0f, 0.1745f)
        handrail_2_r1.texOffs(12, 18).addBox(0f, -1f, 0f, 0, 2, 0, 0.2f, false)

        side_panel_sp1900_translucent = ModelMapper(modelDataWrapper)
        side_panel_sp1900_translucent.setPos(0f, 24f, 0f)
        side_panel_sp1900_translucent.texOffs(34, 289).addBox(-18f, -34f, 0f, 7, 30, 0, 0f, false)

        side_panel_c1141a = ModelMapper(modelDataWrapper)
        side_panel_c1141a.setPos(0f, 24f, 0f)
        side_panel_c1141a.texOffs(38, 218).addBox(-18f, -29f, 0f, 7, 24, 0, 0f, false)
        side_panel_c1141a.texOffs(8, 4).addBox(-11f, -28f, 0f, 0, 23, 0, 0.2f, false)
        side_panel_c1141a.texOffs(224, 31).addBox(-18.7f, -12.5f, -0.5f, 8, 4, 1, 0f, false)

        handrail_5_r1 = ModelMapper(modelDataWrapper)
        handrail_5_r1.setPos(-14.4899f, -28.9899f, 0f)
        side_panel_c1141a.addChild(handrail_5_r1)
        setRotationAngle(handrail_5_r1, 0f, 0f, 1.5708f)
        handrail_5_r1.texOffs(8, 24).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        handrail_3_r2 = ModelMapper(modelDataWrapper)
        handrail_3_r2.setPos(-10.0929f, -27.4929f, 0.2f)
        side_panel_c1141a.addChild(handrail_3_r2)
        setRotationAngle(handrail_3_r2, 0f, 0f, -0.7854f)
        handrail_3_r2.texOffs(8, 24).addBox(-0.2f, -2.2f, -0.2f, 0, 1, 0, 0.2f, false)

        side_panel_c1141a_translucent = ModelMapper(modelDataWrapper)
        side_panel_c1141a_translucent.setPos(0f, 24f, 0f)
        side_panel_c1141a_translucent.texOffs(34, 295).addBox(-18f, -29f, 0f, 7, 24, 0, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(128, 49).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)
        door.texOffs(128, 66).addBox(-5f, -36f, -5f, 5, 1, 10, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(224, 251).addBox(-20.8f, -14f, 0f, 1, 14, 16, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(236, 122).addBox(0f, -19f, 0f, 1, 19, 16, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(156, 251).addBox(-20.8f, -14f, -16f, 1, 14, 16, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(116, 251).addBox(0f, -19f, -16f, 1, 19, 16, 0f, false)

        door_light_c1141a = ModelMapper(modelDataWrapper)
        door_light_c1141a.setPos(0f, 24f, 0f)
        door_light_c1141a.texOffs(96, 40).addBox(-4f, -36f, -3.5f, 4, 1, 0, 0f, false)
        door_light_c1141a.texOffs(96, 32).addBox(-3.5f, -36f, -4f, 0, 1, 8, 0f, false)
        door_light_c1141a.texOffs(96, 40).addBox(-4f, -36f, 3.5f, 4, 1, 0, 0f, false)

        door_exterior_1 = ModelMapper(modelDataWrapper)
        door_exterior_1.setPos(0f, 24f, 0f)
        door_exterior_1.texOffs(178, 334).addBox(20f, 0f, -16f, 1, 7, 32, 0f, true)

        door_right_exterior_1 = ModelMapper(modelDataWrapper)
        door_right_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_right_exterior_1)
        door_right_exterior_1.texOffs(128, 33).addBox(20.8f, -14f, 0f, 0, 14, 16, 0f, true)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(20.8f, -14f, 0f)
        door_right_exterior_1.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, -0.1107f)
        door_left_top_r2.texOffs(130, 99).addBox(0f, -19f, 0f, 0, 19, 16, 0f, true)

        door_left_exterior_1 = ModelMapper(modelDataWrapper)
        door_left_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_left_exterior_1)
        door_left_exterior_1.texOffs(96, 33).addBox(20.8f, -14f, -16f, 0, 14, 16, 0f, true)

        door_right_top_r2_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r2_r1.setPos(20.8f, -14f, 0f)
        door_left_exterior_1.addChild(door_right_top_r2_r1)
        setRotationAngle(door_right_top_r2_r1, 0f, 0f, -0.1107f)
        door_right_top_r2_r1.texOffs(58, 99).addBox(0f, -19f, -16f, 0, 19, 16, 0f, true)

        door_exterior_2 = ModelMapper(modelDataWrapper)
        door_exterior_2.setPos(0f, 24f, 0f)
        door_exterior_2.texOffs(178, 334).addBox(-21f, 0f, -16f, 1, 7, 32, 0f, false)

        door_left_exterior_2 = ModelMapper(modelDataWrapper)
        door_left_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_left_exterior_2)
        door_left_exterior_2.texOffs(128, 33).addBox(-20.8f, -14f, 0f, 0, 14, 16, 0f, false)

        door_left_top_r3 = ModelMapper(modelDataWrapper)
        door_left_top_r3.setPos(-20.8f, -14f, 0f)
        door_left_exterior_2.addChild(door_left_top_r3)
        setRotationAngle(door_left_top_r3, 0f, 0f, 0.1107f)
        door_left_top_r3.texOffs(130, 99).addBox(0f, -19f, 0f, 0, 19, 16, 0f, false)

        door_right_exterior_2 = ModelMapper(modelDataWrapper)
        door_right_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_right_exterior_2)
        door_right_exterior_2.texOffs(96, 33).addBox(-20.8f, -14f, -16f, 0, 14, 16, 0f, false)

        door_right_top_r3_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r3_r1.setPos(-20.8f, -14f, 0f)
        door_right_exterior_2.addChild(door_right_top_r3_r1)
        setRotationAngle(door_right_top_r3_r1, 0f, 0f, 0.1107f)
        door_right_top_r3_r1.texOffs(58, 99).addBox(0f, -19f, -16f, 0, 19, 16, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(96, 0).addBox(-20f, 0f, -32f, 40, 1, 48, 0f, false)
        end.texOffs(58, 251).addBox(18f, -14f, -36f, 2, 14, 54, 0f, true)
        end.texOffs(228, 124).addBox(-20f, -14f, -36f, 2, 14, 54, 0f, false)
        end.texOffs(0, 188).addBox(11f, -33f, -36f, 7, 33, 12, 0f, false)
        end.texOffs(0, 115).addBox(-18f, -33f, -36f, 7, 33, 12, 0f, false)
        end.texOffs(320, 320).addBox(-18f, -44f, -36f, 36, 11, 12, 0f, false)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 0f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(170, 178).addBox(0f, -19f, -36f, 2, 19, 54, 0f, false)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 0f)
        end.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(194, 49).addBox(-2f, -19f, -36f, 2, 19, 54, 0f, true)

        seat_end_1 = ModelMapper(modelDataWrapper)
        seat_end_1.setPos(0f, 0f, 0f)
        end.addChild(seat_end_1)
        seat_end_1.texOffs(116, 178).addBox(11f, -6f, -24f, 7, 1, 40, 0f, false)

        seat_back_1_c1141a_r1 = ModelMapper(modelDataWrapper)
        seat_back_1_c1141a_r1.setPos(18f, -6.5f, 0f)
        seat_end_1.addChild(seat_back_1_c1141a_r1)
        setRotationAngle(seat_back_1_c1141a_r1, 0f, 0f, 0.0873f)
        seat_back_1_c1141a_r1.texOffs(282, 343).addBox(-1f, -6f, -24f, 1, 4, 40, 0f, false)
        seat_back_1_c1141a_r1.texOffs(194, 122).addBox(-1f, -8f, -24f, 1, 8, 40, 0f, false)

        seat_end_2 = ModelMapper(modelDataWrapper)
        seat_end_2.setPos(0f, 0f, 0f)
        end.addChild(seat_end_2)


        seat_back_2_c1141a_r1 = ModelMapper(modelDataWrapper)
        seat_back_2_c1141a_r1.setPos(-18f, -6.5f, 0f)
        seat_end_2.addChild(seat_back_2_c1141a_r1)
        setRotationAngle(seat_back_2_c1141a_r1, 0f, 3.1416f, -0.1047f)
        seat_back_2_c1141a_r1.texOffs(282, 343).addBox(-1f, -6f, -16f, 1, 4, 40, 0f, false)
        seat_back_2_c1141a_r1.texOffs(194, 122).addBox(-1f, -8f, -16f, 1, 8, 40, 0f, false)

        seat_bottom_2_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_2_r1.setPos(0f, 0f, 0f)
        seat_end_2.addChild(seat_bottom_2_r1)
        setRotationAngle(seat_bottom_2_r1, 0f, 3.1416f, 0f)
        seat_bottom_2_r1.texOffs(116, 178).addBox(11f, -6f, -16f, 7, 1, 40, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(0, 271).addBox(-21f, 0f, -32f, 1, 7, 48, 0f, false)
        end_exterior.texOffs(228, 228).addBox(18f, -14f, -36f, 2, 14, 54, 0f, true)
        end_exterior.texOffs(0, 197).addBox(-20f, -14f, -36f, 2, 14, 54, 0f, false)
        end_exterior.texOffs(162, 115).addBox(11f, -33f, -36f, 7, 33, 0, 0f, false)
        end_exterior.texOffs(38, 115).addBox(-18f, -33f, -36f, 7, 33, 0, 0f, false)
        end_exterior.texOffs(116, 219).addBox(-18f, -44f, -36f, 36, 11, 0, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(20f, -14f, 0f)
        end_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, -0.1107f)
        outer_roof_2_r1.texOffs(170, 251).addBox(0f, -26f, -36f, 1, 8, 52, 0f, true)
        outer_roof_2_r1.texOffs(58, 178).addBox(-2f, -19f, -36f, 2, 19, 54, 0f, true)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, 0f)
        end_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(170, 251).addBox(-1f, -26f, -36f, 1, 8, 52, 0f, false)
        outer_roof_1_r1.texOffs(0, 115).addBox(0f, -19f, -36f, 2, 19, 54, 0f, false)

        floor_1_r1 = ModelMapper(modelDataWrapper)
        floor_1_r1.setPos(0f, 0f, 0f)
        end_exterior.addChild(floor_1_r1)
        setRotationAngle(floor_1_r1, 0f, 3.1416f, 0f)
        floor_1_r1.texOffs(0, 271).addBox(-21f, 0f, -16f, 1, 7, 48, 0f, false)

        roof_sp1900 = ModelMapper(modelDataWrapper)
        roof_sp1900.setPos(0f, 24f, 0f)
        roof_sp1900.texOffs(70, 0).addBox(-18f, -32f, -16f, 3, 0, 32, 0f, false)
        roof_sp1900.texOffs(8, 0).addBox(-4f, -36f, -16f, 4, 0, 32, 0f, false)

        inner_roof_6_r1 = ModelMapper(modelDataWrapper)
        inner_roof_6_r1.setPos(-5.1552f, -35.6379f, 0f)
        roof_sp1900.addChild(inner_roof_6_r1)
        setRotationAngle(inner_roof_6_r1, 0f, 0f, -0.2618f)
        inner_roof_6_r1.texOffs(64, 0).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(-11.3018f, -34.9909f, 0f)
        roof_sp1900.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, -0.2618f)
        inner_roof_5_r1.texOffs(8, 66).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-12.7005f, -34.4822f, 0f)
        roof_sp1900.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.5236f)
        inner_roof_4_r1.texOffs(78, 0).addBox(-0.5f, 0f, -16f, 1, 0, 32, 0f, false)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(-13.6331f, -33.3665f, 0f)
        roof_sp1900.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, -1.0472f)
        inner_roof_3_r1.texOffs(12, 66).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-14.5665f, -32.2501f, 0f)
        roof_sp1900.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.5236f)
        inner_roof_2_r1.texOffs(80, 0).addBox(-0.5f, 0f, -16f, 1, 0, 32, 0f, false)

        roof_c1141a = ModelMapper(modelDataWrapper)
        roof_c1141a.setPos(0f, 24f, 0f)
        roof_c1141a.texOffs(70, 0).addBox(-18f, -32f, -16f, 3, 0, 32, 0f, false)
        roof_c1141a.texOffs(8, 376).addBox(-13f, -36f, -16f, 13, 0, 32, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-15f, -32f, 16f)
        roof_c1141a.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -1.0472f)
        inner_roof_3_r2.texOffs(206, 49).addBox(0f, 0f, -32f, 5, 0, 32, 0f, false)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(98, 0).addBox(-6f, -44f, -16f, 6, 0, 32, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-9.9391f, -43.3054f, 0f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.1745f)
        outer_roof_4_r1.texOffs(82, 0).addBox(-4f, 0f, -16f, 8, 0, 32, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-15.1773f, -41.8608f, 0f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -0.5236f)
        outer_roof_3_r1.texOffs(20, 0).addBox(-1.5f, 0f, -16f, 3, 0, 32, 0f, false)

        outer_roof_2_r2 = ModelMapper(modelDataWrapper)
        outer_roof_2_r2.setPos(-16.9764f, -40.2448f, 0f)
        roof_exterior.addChild(outer_roof_2_r2)
        setRotationAngle(outer_roof_2_r2, 0f, 0f, -1.0472f)
        outer_roof_2_r2.texOffs(26, 6).addBox(-1f, 0f, -16f, 2, 0, 32, 0f, false)

        outer_roof_1_r2 = ModelMapper(modelDataWrapper)
        outer_roof_1_r2.setPos(-20f, -14f, 0f)
        roof_exterior.addChild(outer_roof_1_r2)
        setRotationAngle(outer_roof_1_r2, 0f, 0f, 0.1107f)
        outer_roof_1_r2.texOffs(344, 228).addBox(-1f, -26f, -16f, 1, 8, 32, 0f, false)

        roof_light_sp1900 = ModelMapper(modelDataWrapper)
        roof_light_sp1900.setPos(0f, 24f, 0f)
        roof_light_sp1900.texOffs(16, 8).addBox(-9.4701f, -34.7497f, -16f, 2, 0, 32, 0f, false)

        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(-7.0366f, -34.9988f, 0f)
        roof_light_sp1900.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 0f, -0.5236f)
        light_3_r1.texOffs(30, 22).addBox(-0.5f, 0f, -16f, 1, 0, 32, 0f, false)

        light_1_r1 = ModelMapper(modelDataWrapper)
        light_1_r1.setPos(-9.9036f, -34.9998f, 0f)
        roof_light_sp1900.addChild(light_1_r1)
        setRotationAngle(light_1_r1, 0f, 0f, 0.5236f)
        light_1_r1.texOffs(76, 0).addBox(-0.5f, 0f, -16f, 1, 0, 32, 0f, false)

        roof_light_c1141a = ModelMapper(modelDataWrapper)
        roof_light_c1141a.setPos(0f, 24f, 0f)
        roof_light_c1141a.texOffs(274, 69).addBox(-10f, -36.1f, -16f, 4, 0, 32, 0f, false)

        roof_end_sp1900 = ModelMapper(modelDataWrapper)
        roof_end_sp1900.setPos(0f, 24f, 0f)


        inner_roof_1 = ModelMapper(modelDataWrapper)
        inner_roof_1.setPos(0f, 0f, 0f)
        roof_end_sp1900.addChild(inner_roof_1)
        inner_roof_1.texOffs(62, 0).addBox(-18f, -32f, -24f, 3, 0, 40, 0f, false)
        inner_roof_1.texOffs(0, 0).addBox(-4f, -36f, -24f, 4, 0, 40, 0f, false)

        inner_roof_6_r2 = ModelMapper(modelDataWrapper)
        inner_roof_6_r2.setPos(-5.1551f, -35.6369f, -4f)
        inner_roof_1.addChild(inner_roof_6_r2)
        setRotationAngle(inner_roof_6_r2, 0f, 0f, -0.2618f)
        inner_roof_6_r2.texOffs(56, 0).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, false)

        inner_roof_5_r2 = ModelMapper(modelDataWrapper)
        inner_roof_5_r2.setPos(-11.3023f, -34.9918f, -4f)
        inner_roof_1.addChild(inner_roof_5_r2)
        setRotationAngle(inner_roof_5_r2, 0f, 0f, -0.2618f)
        inner_roof_5_r2.texOffs(0, 66).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        inner_roof_4_r2 = ModelMapper(modelDataWrapper)
        inner_roof_4_r2.setPos(-12.701f, -34.4831f, -4f)
        inner_roof_1.addChild(inner_roof_4_r2)
        setRotationAngle(inner_roof_4_r2, 0f, 0f, -0.5236f)
        inner_roof_4_r2.texOffs(70, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, false)

        inner_roof_3_r3 = ModelMapper(modelDataWrapper)
        inner_roof_3_r3.setPos(-13.6331f, -33.3665f, -4f)
        inner_roof_1.addChild(inner_roof_3_r3)
        setRotationAngle(inner_roof_3_r3, 0f, 0f, -1.0472f)
        inner_roof_3_r3.texOffs(4, 66).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, false)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(-14.5665f, -32.2501f, -4f)
        inner_roof_1.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, -0.5236f)
        inner_roof_2_r2.texOffs(72, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, false)

        inner_roof_2 = ModelMapper(modelDataWrapper)
        inner_roof_2.setPos(0f, 0f, 0f)
        roof_end_sp1900.addChild(inner_roof_2)
        inner_roof_2.texOffs(62, 0).addBox(15f, -32f, -24f, 3, 0, 40, 0f, true)
        inner_roof_2.texOffs(0, 0).addBox(0f, -36f, -24f, 4, 0, 40, 0f, true)

        inner_roof_6_r3 = ModelMapper(modelDataWrapper)
        inner_roof_6_r3.setPos(5.1554f, -35.6388f, -4f)
        inner_roof_2.addChild(inner_roof_6_r3)
        setRotationAngle(inner_roof_6_r3, 0f, 0f, 0.2618f)
        inner_roof_6_r3.texOffs(56, 0).addBox(-1.5f, 0f, -20f, 3, 0, 40, 0f, true)

        inner_roof_5_r3 = ModelMapper(modelDataWrapper)
        inner_roof_5_r3.setPos(11.3023f, -34.9908f, -4f)
        inner_roof_2.addChild(inner_roof_5_r3)
        setRotationAngle(inner_roof_5_r3, 0f, 0f, 0.2618f)
        inner_roof_5_r3.texOffs(0, 66).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, true)

        inner_roof_4_r3 = ModelMapper(modelDataWrapper)
        inner_roof_4_r3.setPos(12.701f, -34.4821f, -4f)
        inner_roof_2.addChild(inner_roof_4_r3)
        setRotationAngle(inner_roof_4_r3, 0f, 0f, 0.5236f)
        inner_roof_4_r3.texOffs(72, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, true)

        inner_roof_3_r4 = ModelMapper(modelDataWrapper)
        inner_roof_3_r4.setPos(13.6336f, -33.3664f, -4f)
        inner_roof_2.addChild(inner_roof_3_r4)
        setRotationAngle(inner_roof_3_r4, 0f, 0f, 1.0472f)
        inner_roof_3_r4.texOffs(4, 66).addBox(-1f, 0f, -20f, 2, 0, 40, 0f, true)

        inner_roof_2_r3 = ModelMapper(modelDataWrapper)
        inner_roof_2_r3.setPos(14.5665f, -32.2491f, -4f)
        inner_roof_2.addChild(inner_roof_2_r3)
        setRotationAngle(inner_roof_2_r3, 0f, 0f, 0.5236f)
        inner_roof_2_r3.texOffs(72, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, true)

        roof_end_c1141a = ModelMapper(modelDataWrapper)
        roof_end_c1141a.setPos(0f, 24f, 0f)


        inner_roof_3 = ModelMapper(modelDataWrapper)
        inner_roof_3.setPos(0f, 0f, 0f)
        roof_end_c1141a.addChild(inner_roof_3)
        inner_roof_3.texOffs(62, 0).addBox(-18f, -32f, -24f, 3, 0, 40, 0f, false)
        inner_roof_3.texOffs(0, 376).addBox(-13f, -36f, -24f, 13, 0, 40, 0f, false)

        inner_roof_3_r5 = ModelMapper(modelDataWrapper)
        inner_roof_3_r5.setPos(-15f, -32f, 0f)
        inner_roof_3.addChild(inner_roof_3_r5)
        setRotationAngle(inner_roof_3_r5, 0f, 0f, -1.0472f)
        inner_roof_3_r5.texOffs(198, 49).addBox(0f, 0f, -24f, 5, 0, 40, 0f, false)

        inner_roof_4 = ModelMapper(modelDataWrapper)
        inner_roof_4.setPos(0f, 0f, 0f)
        roof_end_c1141a.addChild(inner_roof_4)
        inner_roof_4.texOffs(62, 0).addBox(15f, -32f, -24f, 3, 0, 40, 0f, true)
        inner_roof_4.texOffs(0, 376).addBox(0f, -36f, -24f, 13, 0, 40, 0f, true)

        inner_roof_3_r6 = ModelMapper(modelDataWrapper)
        inner_roof_3_r6.setPos(15f, -32f, 0f)
        inner_roof_4.addChild(inner_roof_3_r6)
        setRotationAngle(inner_roof_3_r6, 0f, 0f, 1.0472f)
        inner_roof_3_r6.texOffs(198, 49).addBox(-5f, 0f, -24f, 5, 0, 40, 0f, true)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)
        roof_end_exterior.texOffs(0, 0).addBox(-8f, -45f, -36f, 16, 2, 64, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -45f, 0f)
        roof_end_exterior.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(112, 112).addBox(-9f, 0f, -36f, 9, 2, 64, 0f, true)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -45f, 0f)
        roof_end_exterior.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(112, 112).addBox(0f, 0f, -36f, 9, 2, 64, 0f, false)

        outer_roof_1 = ModelMapper(modelDataWrapper)
        outer_roof_1.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_1)


        outer_roof_3_r2 = ModelMapper(modelDataWrapper)
        outer_roof_3_r2.setPos(-15.1773f, -41.8608f, -10f)
        outer_roof_1.addChild(outer_roof_3_r2)
        setRotationAngle(outer_roof_3_r2, 0f, 0f, -0.5236f)
        outer_roof_3_r2.texOffs(0, 0).addBox(-1.5f, 0f, -26f, 3, 0, 52, 0f, false)

        outer_roof_2_r3 = ModelMapper(modelDataWrapper)
        outer_roof_2_r3.setPos(-16.9764f, -40.2448f, -10f)
        outer_roof_1.addChild(outer_roof_2_r3)
        setRotationAngle(outer_roof_2_r3, 0f, 0f, -1.0472f)
        outer_roof_2_r3.texOffs(6, 6).addBox(-1f, 0f, -26f, 2, 0, 52, 0f, false)

        outer_roof_2 = ModelMapper(modelDataWrapper)
        outer_roof_2.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(outer_roof_2)


        outer_roof_3_r3 = ModelMapper(modelDataWrapper)
        outer_roof_3_r3.setPos(15.1773f, -41.8608f, -10f)
        outer_roof_2.addChild(outer_roof_3_r3)
        setRotationAngle(outer_roof_3_r3, 0f, 0f, 0.5236f)
        outer_roof_3_r3.texOffs(0, 0).addBox(-1.5f, 0f, -26f, 3, 0, 52, 0f, true)

        outer_roof_2_r4 = ModelMapper(modelDataWrapper)
        outer_roof_2_r4.setPos(16.9764f, -40.2448f, -10f)
        outer_roof_2.addChild(outer_roof_2_r4)
        setRotationAngle(outer_roof_2_r4, 0f, 0f, 1.0472f)
        outer_roof_2_r4.texOffs(6, 6).addBox(-1f, 0f, -26f, 2, 0, 52, 0f, true)

        roof_end_light_sp1900 = ModelMapper(modelDataWrapper)
        roof_end_light_sp1900.setPos(0f, 24f, 0f)
        roof_end_light_sp1900.texOffs(8, 8).addBox(-9.4703f, -34.7496f, -24f, 2, 0, 40, 0f, false)
        roof_end_light_sp1900.texOffs(8, 8).addBox(7.4706f, -34.7506f, -24f, 2, 0, 40, 0f, true)

        light_6_r1 = ModelMapper(modelDataWrapper)
        light_6_r1.setPos(7.0371f, -35.0007f, -4f)
        roof_end_light_sp1900.addChild(light_6_r1)
        setRotationAngle(light_6_r1, 0f, 0f, 0.5236f)
        light_6_r1.texOffs(22, 22).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, true)

        light_4_r1 = ModelMapper(modelDataWrapper)
        light_4_r1.setPos(9.9041f, -34.9997f, -4f)
        roof_end_light_sp1900.addChild(light_4_r1)
        setRotationAngle(light_4_r1, 0f, 0f, -0.5236f)
        light_4_r1.texOffs(68, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, true)

        light_3_r2 = ModelMapper(modelDataWrapper)
        light_3_r2.setPos(-7.0368f, -34.9987f, -4f)
        roof_end_light_sp1900.addChild(light_3_r2)
        setRotationAngle(light_3_r2, 0f, 0f, -0.5236f)
        light_3_r2.texOffs(22, 22).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, false)

        light_1_r2 = ModelMapper(modelDataWrapper)
        light_1_r2.setPos(-9.9038f, -34.9997f, -4f)
        roof_end_light_sp1900.addChild(light_1_r2)
        setRotationAngle(light_1_r2, 0f, 0f, 0.5236f)
        light_1_r2.texOffs(68, 0).addBox(-0.5f, 0f, -20f, 1, 0, 40, 0f, false)

        roof_end_light_c1141a = ModelMapper(modelDataWrapper)
        roof_end_light_c1141a.setPos(0f, 24f, 0f)
        roof_end_light_c1141a.texOffs(266, 69).addBox(-10f, -36.1f, -24f, 4, 0, 40, 0f, false)
        roof_end_light_c1141a.texOffs(266, 69).addBox(6f, -36.1f, -24f, 4, 0, 40, 0f, false)

        top_handrail_sp1900 = ModelMapper(modelDataWrapper)
        top_handrail_sp1900.setPos(0f, 24f, 0f)
        top_handrail_sp1900.texOffs(0, 0).addBox(-5f, -36f, 15.8f, 0, 3, 0, 0.2f, false)
        top_handrail_sp1900.texOffs(0, 0).addBox(-5f, -36f, -15.8f, 0, 3, 0, 0.2f, false)

        top_handrail_bottom_right_r1 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_right_r1.setPos(-5f, -31.0876f, -6.8876f)
        top_handrail_sp1900.addChild(top_handrail_bottom_right_r1)
        setRotationAngle(top_handrail_bottom_right_r1, -1.5708f, 0f, 0f)
        top_handrail_bottom_right_r1.texOffs(0, 0).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        top_handrail_bottom_left_r1 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_left_r1.setPos(-5f, -31.0876f, 6.8876f)
        top_handrail_sp1900.addChild(top_handrail_bottom_left_r1)
        setRotationAngle(top_handrail_bottom_left_r1, -1.5708f, 0f, 0f)
        top_handrail_bottom_left_r1.texOffs(0, 0).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        top_handrail_right_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_3_r1.setPos(-5f, -31.4108f, -14.5938f)
        top_handrail_sp1900.addChild(top_handrail_right_3_r1)
        setRotationAngle(top_handrail_right_3_r1, 1.0472f, 0f, 0f)
        top_handrail_right_3_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_right_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_2_r1.setPos(-5f, -32.2938f, -15.4768f)
        top_handrail_sp1900.addChild(top_handrail_right_2_r1)
        setRotationAngle(top_handrail_right_2_r1, 0.5236f, 0f, 0f)
        top_handrail_right_2_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_3_r1.setPos(-5f, -31.4108f, 14.5938f)
        top_handrail_sp1900.addChild(top_handrail_left_3_r1)
        setRotationAngle(top_handrail_left_3_r1, -1.0472f, 0f, 0f)
        top_handrail_left_3_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_2_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_2_r1.setPos(-5f, -32.2938f, 15.4768f)
        top_handrail_sp1900.addChild(top_handrail_left_2_r1)
        setRotationAngle(top_handrail_left_2_r1, -0.5236f, 0f, 0f)
        top_handrail_left_2_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_strap_1 = ModelMapper(modelDataWrapper)
        handrail_strap_1.setPos(0f, 0f, 0f)
        top_handrail_sp1900.addChild(handrail_strap_1)
        handrail_strap_1.texOffs(12, 12).addBox(-6f, -32f, -12f, 2, 4, 0, 0f, false)
        handrail_strap_1.texOffs(12, 12).addBox(-6f, -32f, -6f, 2, 4, 0, 0f, false)
        handrail_strap_1.texOffs(12, 12).addBox(-6f, -32f, 0f, 2, 4, 0, 0f, false)
        handrail_strap_1.texOffs(12, 12).addBox(-6f, -32f, 6f, 2, 4, 0, 0f, false)
        handrail_strap_1.texOffs(12, 12).addBox(-6f, -32f, 12f, 2, 4, 0, 0f, false)

        top_handrail_c1141a = ModelMapper(modelDataWrapper)
        top_handrail_c1141a.setPos(0f, 24f, 0f)
        top_handrail_c1141a.texOffs(0, 50).addBox(-5f, -31f, 15.8f, 5, 0, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 50).addBox(-5f, -31f, -15.8f, 5, 0, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(-4.727f, -36.6045f, -11f, 0, 3, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(-4.727f, -36.6045f, 0f, 0, 3, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(-4.727f, -36.6045f, 11f, 0, 3, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(0f, -36.6046f, 13.6145f, 0, 3, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(0f, -36f, 11f, 0, 7, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(8, 7).addBox(0f, -23.2645f, 10.437f, 0, 6, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(8, 7).addBox(0f, -23.2645f, 11.563f, 0, 6, 0, 0.2f, false)
        top_handrail_c1141a.texOffs(0, 0).addBox(0f, -12f, 11f, 0, 12, 0, 0.2f, false)

        pole_bottom_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_2_r1.setPos(0f, -14.4002f, 11.2819f)
        top_handrail_c1141a.addChild(pole_bottom_diagonal_2_r1)
        setRotationAngle(pole_bottom_diagonal_2_r1, -0.1047f, 0f, 0f)
        pole_bottom_diagonal_2_r1.texOffs(11, 28).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_1_r1.setPos(0f, -14.4002f, 10.7181f)
        top_handrail_c1141a.addChild(pole_bottom_diagonal_1_r1)
        setRotationAngle(pole_bottom_diagonal_1_r1, 0.1047f, 0f, 0f)
        pole_bottom_diagonal_1_r1.texOffs(11, 28).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_2_r1.setPos(0.2f, -28.8f, 10.8f)
        top_handrail_c1141a.addChild(pole_top_diagonal_2_r1)
        setRotationAngle(pole_top_diagonal_2_r1, 0.1047f, 0f, 0f)
        pole_top_diagonal_2_r1.texOffs(11, 11).addBox(-0.2f, 0.2069f, 0.2f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_1_r1.setPos(0.2f, -28.8f, 11.2f)
        top_handrail_c1141a.addChild(pole_top_diagonal_1_r1)
        setRotationAngle(pole_top_diagonal_1_r1, -0.1047f, 0f, 0f)
        pole_top_diagonal_1_r1.texOffs(11, 11).addBox(-0.2f, 0.2069f, -0.2f, 0, 5, 0, 0.2f, false)

        top_handrail_connector_bottom_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_4_r1.setPos(0f, -32.2308f, 14.6605f)
        top_handrail_c1141a.addChild(top_handrail_connector_bottom_4_r1)
        setRotationAngle(top_handrail_connector_bottom_4_r1, 0.6981f, 0f, 0f)
        top_handrail_connector_bottom_4_r1.texOffs(0, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        top_handrail_connector_bottom_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_3_r1.setPos(-5.7729f, -32.2308f, 0f)
        top_handrail_c1141a.addChild(top_handrail_connector_bottom_3_r1)
        setRotationAngle(top_handrail_connector_bottom_3_r1, 0f, 0f, 0.6981f)
        top_handrail_connector_bottom_3_r1.texOffs(0, 0).addBox(0f, -1.5f, 11f, 0, 3, 0, 0.2f, false)
        top_handrail_connector_bottom_3_r1.texOffs(0, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)
        top_handrail_connector_bottom_3_r1.texOffs(0, 0).addBox(0f, -1.5f, -11f, 0, 3, 0, 0.2f, false)

        top_handrail_bottom_right_r2 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_right_r2.setPos(-6.9124f, -31f, -6.8876f)
        top_handrail_c1141a.addChild(top_handrail_bottom_right_r2)
        setRotationAngle(top_handrail_bottom_right_r2, 1.5708f, 0f, 0f)
        top_handrail_bottom_right_r2.texOffs(0, 0).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        top_handrail_bottom_left_r2 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_left_r2.setPos(-6.9124f, -31f, 6.8876f)
        top_handrail_c1141a.addChild(top_handrail_bottom_left_r2)
        setRotationAngle(top_handrail_bottom_left_r2, -1.5708f, 0f, 0f)
        top_handrail_bottom_left_r2.texOffs(0, 0).addBox(0f, -7f, 0f, 0, 14, 0, 0.2f, false)

        top_handrail_right_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_4_r1.setPos(-6.5892f, -31f, -14.5938f)
        top_handrail_c1141a.addChild(top_handrail_right_4_r1)
        setRotationAngle(top_handrail_right_4_r1, 1.5708f, -0.5236f, 0f)
        top_handrail_right_4_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_right_3_r2 = ModelMapper(modelDataWrapper)
        top_handrail_right_3_r2.setPos(-5.7062f, -31f, -15.4768f)
        top_handrail_c1141a.addChild(top_handrail_right_3_r2)
        setRotationAngle(top_handrail_right_3_r2, 1.5708f, -1.0472f, 0f)
        top_handrail_right_3_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_4_r1.setPos(-6.5892f, -31f, 14.5938f)
        top_handrail_c1141a.addChild(top_handrail_left_4_r1)
        setRotationAngle(top_handrail_left_4_r1, -1.5708f, 0.5236f, 0f)
        top_handrail_left_4_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_3_r2 = ModelMapper(modelDataWrapper)
        top_handrail_left_3_r2.setPos(-5.7062f, -31f, 15.4768f)
        top_handrail_c1141a.addChild(top_handrail_left_3_r2)
        setRotationAngle(top_handrail_left_3_r2, -1.5708f, 1.0472f, 0f)
        top_handrail_left_3_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_strap_2 = ModelMapper(modelDataWrapper)
        handrail_strap_2.setPos(0f, 0f, 0f)
        top_handrail_c1141a.addChild(handrail_strap_2)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, -14f, 2, 4, 0, 0f, false)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, -9f, 2, 4, 0, 0f, false)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, -4f, 2, 4, 0, 0f, false)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, 4f, 2, 4, 0, 0f, false)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, 9f, 2, 4, 0, 0f, false)
        handrail_strap_2.texOffs(12, 12).addBox(-8f, -32f, 14f, 2, 4, 0, 0f, false)

        handrail_strap_8_r1 = ModelMapper(modelDataWrapper)
        handrail_strap_8_r1.setPos(0f, 0f, 0f)
        handrail_strap_2.addChild(handrail_strap_8_r1)
        setRotationAngle(handrail_strap_8_r1, 0f, -1.5708f, 0f)
        handrail_strap_8_r1.texOffs(12, 12).addBox(-16.8f, -32f, 3f, 2, 4, 0, 0f, false)
        handrail_strap_8_r1.texOffs(12, 12).addBox(14.8f, -32f, 3f, 2, 4, 0, 0f, false)

        tv_pole = ModelMapper(modelDataWrapper)
        tv_pole.setPos(0f, 24f, 0f)
        tv_pole.texOffs(18, 0).addBox(-4f, -36f, -1f, 8, 6, 2, 0f, false)
        tv_pole.texOffs(4, 0).addBox(0f, -27.5f, 0f, 0, 28, 0, 0.2f, false)
        tv_pole.texOffs(4, 0).addBox(-1f, -27.5f, 0f, 2, 0, 0, 0.2f, false)

        tv_right_r1 = ModelMapper(modelDataWrapper)
        tv_right_r1.setPos(4f, -30f, -1f)
        tv_pole.addChild(tv_right_r1)
        setRotationAngle(tv_right_r1, -0.1047f, 3.1416f, 0f)
        tv_right_r1.texOffs(18, 8).addBox(0f, -7f, -1f, 8, 7, 1, 0f, false)

        tv_left_r1 = ModelMapper(modelDataWrapper)
        tv_left_r1.setPos(4f, -30f, 1f)
        tv_pole.addChild(tv_left_r1)
        setRotationAngle(tv_left_r1, -0.1047f, 0f, 0f)
        tv_left_r1.texOffs(18, 8).addBox(-8f, -7f, -1f, 8, 7, 1, 0f, false)

        pole_5_r1 = ModelMapper(modelDataWrapper)
        pole_5_r1.setPos(-3.3885f, -29.8726f, 0f)
        tv_pole.addChild(pole_5_r1)
        setRotationAngle(pole_5_r1, 0f, 0f, 1.2217f)
        pole_5_r1.texOffs(4, 0).addBox(-1f, 0f, 0f, 2, 0, 0, 0.2f, false)

        pole_4_r1 = ModelMapper(modelDataWrapper)
        pole_4_r1.setPos(-1.2f, -27.3f, 0f)
        tv_pole.addChild(pole_4_r1)
        setRotationAngle(pole_4_r1, 0f, 0f, 0.6109f)
        pole_4_r1.texOffs(4, 0).addBox(-2.2f, -0.2f, 0f, 2, 0, 0, 0.2f, false)

        pole_2_r1 = ModelMapper(modelDataWrapper)
        pole_2_r1.setPos(1.2f, -27.3f, 0f)
        tv_pole.addChild(pole_2_r1)
        setRotationAngle(pole_2_r1, 0f, 0f, -0.6109f)
        pole_2_r1.texOffs(4, 0).addBox(0.2f, -0.2f, 0f, 2, 0, 0, 0.2f, false)

        pole_1_r1 = ModelMapper(modelDataWrapper)
        pole_1_r1.setPos(3.3885f, -29.8726f, 0f)
        tv_pole.addChild(pole_1_r1)
        setRotationAngle(pole_1_r1, 0f, 0f, -1.2217f)
        pole_1_r1.texOffs(4, 0).addBox(-1f, 0f, 0f, 2, 0, 0, 0.2f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(0, 66).addBox(-20f, 0f, -16f, 40, 1, 48, 0f, false)
        head.texOffs(326, 69).addBox(-20f, -14f, -18f, 2, 14, 36, 0f, false)
        head.texOffs(0, 326).addBox(18f, -14f, -18f, 2, 14, 36, 0f, true)
        head.texOffs(326, 192).addBox(-18f, -36f, 8f, 36, 36, 0, 0f, false)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, -0.1107f)
        upper_wall_2_r2.texOffs(240, 296).addBox(-2f, -19f, -18f, 2, 19, 36, 0f, true)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, 0.1107f)
        upper_wall_1_r2.texOffs(304, 260).addBox(0f, -19f, -18f, 2, 19, 36, 0f, false)

        seat_head_1 = ModelMapper(modelDataWrapper)
        seat_head_1.setPos(0f, 0f, 0f)
        head.addChild(seat_head_1)
        seat_head_1.texOffs(0, 34).addBox(11f, -6f, -16f, 7, 1, 24, 0f, false)

        seat_back_c1141a_r2 = ModelMapper(modelDataWrapper)
        seat_back_c1141a_r2.setPos(18f, -6.5f, 0f)
        seat_head_1.addChild(seat_back_c1141a_r2)
        setRotationAngle(seat_back_c1141a_r2, 0f, 0f, 0.0873f)
        seat_back_c1141a_r2.texOffs(254, 71).addBox(-1f, -6f, -16f, 1, 4, 24, 0f, false)
        seat_back_c1141a_r2.texOffs(170, 178).addBox(-1f, -8f, -16f, 1, 8, 24, 0f, false)

        seat_head_2 = ModelMapper(modelDataWrapper)
        seat_head_2.setPos(0f, 0f, 0f)
        head.addChild(seat_head_2)


        seat_back_c1141a_r3 = ModelMapper(modelDataWrapper)
        seat_back_c1141a_r3.setPos(-18f, -6.5f, 0f)
        seat_head_2.addChild(seat_back_c1141a_r3)
        setRotationAngle(seat_back_c1141a_r3, 0f, 3.1416f, -0.1047f)
        seat_back_c1141a_r3.texOffs(254, 71).addBox(-1f, -6f, -8f, 1, 4, 24, 0f, false)
        seat_back_c1141a_r3.texOffs(170, 178).addBox(-1f, -8f, -8f, 1, 8, 24, 0f, false)

        seat_bottom_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_r1.setPos(0f, 0f, 0f)
        seat_head_2.addChild(seat_bottom_r1)
        setRotationAngle(seat_bottom_r1, 0f, 3.1416f, 0f)
        seat_bottom_r1.texOffs(0, 34).addBox(11f, -6f, -8f, 7, 1, 24, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(224, 0).addBox(-21f, 0f, 8f, 42, 7, 24, 0f, false)
        head_exterior.texOffs(58, 188).addBox(-21f, 0f, -16f, 1, 7, 24, 0f, false)
        head_exterior.texOffs(40, 326).addBox(-18f, -36f, 9f, 36, 36, 0, 0f, false)
        head_exterior.texOffs(116, 251).addBox(-20f, -14f, -18f, 2, 14, 36, 0f, false)
        head_exterior.texOffs(116, 251).addBox(18f, -14f, -18f, 2, 14, 36, 0f, true)
        head_exterior.texOffs(200, 49).addBox(-20.8f, -14f, 16f, 1, 14, 16, 0f, false)
        head_exterior.texOffs(350, 22).addBox(19.8f, -14f, 16f, 1, 14, 16, 0f, true)
        head_exterior.texOffs(252, 31).addBox(-18f, -43.9f, 8f, 36, 12, 26, 0f, false)

        outer_roof_1_r3 = ModelMapper(modelDataWrapper)
        outer_roof_1_r3.setPos(-20f, -14f, 0f)
        head_exterior.addChild(outer_roof_1_r3)
        setRotationAngle(outer_roof_1_r3, 0f, 0f, 0.1107f)
        outer_roof_1_r3.texOffs(76, 319).addBox(-1f, -26f, -16f, 1, 8, 49, 0f, false)
        outer_roof_1_r3.texOffs(286, 86).addBox(0f, -19f, -18f, 2, 19, 36, 0f, false)

        outer_roof_2_r5 = ModelMapper(modelDataWrapper)
        outer_roof_2_r5.setPos(20f, -14f, 0f)
        head_exterior.addChild(outer_roof_2_r5)
        setRotationAngle(outer_roof_2_r5, 0f, 0f, -0.1107f)
        outer_roof_2_r5.texOffs(76, 319).addBox(0f, -26f, -16f, 1, 8, 49, 0f, true)
        outer_roof_2_r5.texOffs(286, 192).addBox(-2f, -19f, -18f, 2, 19, 36, 0f, true)

        driver_door_top_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_top_2_r1.setPos(20.8f, -14f, 16f)
        head_exterior.addChild(driver_door_top_2_r1)
        setRotationAngle(driver_door_top_2_r1, 0f, 0f, -0.1107f)
        driver_door_top_2_r1.texOffs(116, 178).addBox(-1f, -19f, 0f, 1, 19, 16, 0f, true)

        driver_door_top_1_r1 = ModelMapper(modelDataWrapper)
        driver_door_top_1_r1.setPos(-20.8f, -14f, 16f)
        head_exterior.addChild(driver_door_top_1_r1)
        setRotationAngle(driver_door_top_1_r1, 0f, 0f, 0.1107f)
        driver_door_top_1_r1.texOffs(194, 122).addBox(0f, -19f, 0f, 1, 19, 16, 0f, false)

        floor_2_r1 = ModelMapper(modelDataWrapper)
        floor_2_r1.setPos(0f, 0f, 0f)
        head_exterior.addChild(floor_2_r1)
        setRotationAngle(floor_2_r1, 0f, 3.1416f, 0f)
        floor_2_r1.texOffs(58, 188).addBox(-21f, 0f, -8f, 1, 7, 24, 0f, false)

        front = ModelMapper(modelDataWrapper)
        front.setPos(0f, 0f, 0f)
        head_exterior.addChild(front)


        front_middle_r1_r1 = ModelMapper(modelDataWrapper)
        front_middle_r1_r1.setPos(0f, 2.5717f, 45.8123f)
        front.addChild(front_middle_r1_r1)
        setRotationAngle(front_middle_r1_r1, 0.3491f, 0f, 0f)
        front_middle_r1_r1.texOffs(0, 66).addBox(-10f, -44.5717f, 8.1877f, 20, 44, 0, 0f, false)

        front_roof_r1_r1 = ModelMapper(modelDataWrapper)
        front_roof_r1_r1.setPos(0f, -44f, 34f)
        front.addChild(front_roof_r1_r1)
        setRotationAngle(front_roof_r1_r1, 1.0472f, 0f, 0f)
        front_roof_r1_r1.texOffs(224, 12).addBox(-6f, 0f, 0f, 12, 6, 0, 0f, false)

        bottom_r1_r1 = ModelMapper(modelDataWrapper)
        bottom_r1_r1.setPos(0f, 7f, 32f)
        front.addChild(bottom_r1_r1)
        setRotationAngle(bottom_r1_r1, -1.3526f, 0f, 0f)
        bottom_r1_r1.texOffs(332, 0).addBox(-21f, -22f, 0f, 42, 22, 0, 0f, false)

        front_bottom_r1_r1 = ModelMapper(modelDataWrapper)
        front_bottom_r1_r1.setPos(0f, -9.1588f, 1.0997f)
        front.addChild(front_bottom_r1_r1)
        setRotationAngle(front_bottom_r1_r1, -0.1745f, 0f, 0f)
        front_bottom_r1_r1.texOffs(0, 59).addBox(-10f, -0.7936f, 52.9026f, 20, 5, 0, 0f, false)

        front_side_1_r1_r1 = ModelMapper(modelDataWrapper)
        front_side_1_r1_r1.setPos(-13.6605f, -21.1866f, 43.5706f)
        front.addChild(front_side_1_r1_r1)
        setRotationAngle(front_side_1_r1_r1, 0.3491f, -0.5236f, -0.0873f)
        front_side_1_r1_r1.texOffs(0, 265).addBox(-6.5f, -22f, 0f, 13, 44, 0, 0f, false)

        front_side_2_r1_r1 = ModelMapper(modelDataWrapper)
        front_side_2_r1_r1.setPos(13.6605f, -21.1866f, 43.5706f)
        front.addChild(front_side_2_r1_r1)
        setRotationAngle(front_side_2_r1_r1, 0.3491f, 0.5236f, 0.0873f)
        front_side_2_r1_r1.texOffs(192, 251).addBox(-6.5f, -22f, 0f, 13, 44, 0, 0f, false)

        bottom_side_1_r1_r1 = ModelMapper(modelDataWrapper)
        bottom_side_1_r1_r1.setPos(-20.8f, -14f, 32f)
        front.addChild(bottom_side_1_r1_r1)
        setRotationAngle(bottom_side_1_r1_r1, 0f, -1.309f, 0f)
        bottom_side_1_r1_r1.texOffs(200, 82).addBox(0f, 0f, 0f, 19, 21, 0, 0f, false)

        bottom_side_2_r1_r1 = ModelMapper(modelDataWrapper)
        bottom_side_2_r1_r1.setPos(20.8f, -14f, 32f)
        front.addChild(bottom_side_2_r1_r1)
        setRotationAngle(bottom_side_2_r1_r1, 0f, 1.309f, 0f)
        bottom_side_2_r1_r1.texOffs(130, 148).addBox(-19f, 0f, 0f, 19, 21, 0, 0f, false)

        top_side_1_r1_r1 = ModelMapper(modelDataWrapper)
        top_side_1_r1_r1.setPos(-20.8f, -14f, 32f)
        front.addChild(top_side_1_r1_r1)
        setRotationAngle(top_side_1_r1_r1, 0f, -1.309f, 0.1107f)
        top_side_1_r1_r1.texOffs(252, 69).addBox(0f, -26f, 0f, 13, 26, 0, 0f, false)

        top_side_2_r1_r1 = ModelMapper(modelDataWrapper)
        top_side_2_r1_r1.setPos(20.8f, -14f, 32f)
        front.addChild(top_side_2_r1_r1)
        setRotationAngle(top_side_2_r1_r1, 0f, 1.309f, -0.1107f)
        top_side_2_r1_r1.texOffs(228, 192).addBox(-13f, -26f, 0f, 13, 26, 0, 0f, false)

        roof_side_1_r1_r1 = ModelMapper(modelDataWrapper)
        roof_side_1_r1_r1.setPos(-6f, -44f, 34f)
        front.addChild(roof_side_1_r1_r1)
        setRotationAngle(roof_side_1_r1_r1, 1.0472f, 0f, -0.1745f)
        roof_side_1_r1_r1.texOffs(224, 6).addBox(-8f, 0f, 0f, 8, 6, 0, 0f, false)

        roof_side_2_r1_r1 = ModelMapper(modelDataWrapper)
        roof_side_2_r1_r1.setPos(6f, -44f, 34f)
        front.addChild(roof_side_2_r1_r1)
        setRotationAngle(roof_side_2_r1_r1, 1.0472f, 0f, 0.1745f)
        roof_side_2_r1_r1.texOffs(224, 0).addBox(0f, 0f, 0f, 8, 6, 0, 0f, false)

        roof_middle_corner_1_r1_r1 = ModelMapper(modelDataWrapper)
        roof_middle_corner_1_r1_r1.setPos(-14.8022f, -41.2114f, 35.299f)
        front.addChild(roof_middle_corner_1_r1_r1)
        setRotationAngle(roof_middle_corner_1_r1_r1, 1.0472f, 0f, -0.5236f)
        roof_middle_corner_1_r1_r1.texOffs(0, 52).addBox(-1.5f, -1.5f, 0f, 3, 3, 0, 0f, false)

        roof_middle_corner_2_r1_r1 = ModelMapper(modelDataWrapper)
        roof_middle_corner_2_r1_r1.setPos(14.8022f, -41.2114f, 35.299f)
        front.addChild(roof_middle_corner_2_r1_r1)
        setRotationAngle(roof_middle_corner_2_r1_r1, 1.0472f, 0f, 0.5236f)
        roof_middle_corner_2_r1_r1.texOffs(0, 55).addBox(-1.5f, -1.5f, 0f, 3, 3, 0, 0f, false)

        roof_corner_1_r1_r1 = ModelMapper(modelDataWrapper)
        roof_corner_1_r1_r1.setPos(-16.7925f, -39.5614f, 34.8655f)
        front.addChild(roof_corner_1_r1_r1)
        setRotationAngle(roof_corner_1_r1_r1, 1.0472f, 0f, -1.0472f)
        roof_corner_1_r1_r1.texOffs(30, 30).addBox(-1.5f, -1f, 0f, 3, 2, 0, 0f, false)

        roof_corner_2_r1_r1 = ModelMapper(modelDataWrapper)
        roof_corner_2_r1_r1.setPos(16.7929f, -39.5622f, 34.866f)
        front.addChild(roof_corner_2_r1_r1)
        setRotationAngle(roof_corner_2_r1_r1, 1.0472f, 0f, 1.0472f)
        roof_corner_2_r1_r1.texOffs(24, 30).addBox(-1.5f, -1f, 0f, 3, 2, 0, 0f, false)

        bottom_corner_1_r1_r1 = ModelMapper(modelDataWrapper)
        bottom_corner_1_r1_r1.setPos(21.913f, -11.9098f, 14.4897f)
        front.addChild(bottom_corner_1_r1_r1)
        setRotationAngle(bottom_corner_1_r1_r1, -0.1745f, -0.5236f, -0.0873f)
        bottom_corner_1_r1_r1.texOffs(114, 37).addBox(-17.951f, -0.4819f, 50.7099f, 9, 5, 0, 0f, false)

        bottom_corner_2_r1_r1 = ModelMapper(modelDataWrapper)
        bottom_corner_2_r1_r1.setPos(-21.913f, -11.9098f, 14.4897f)
        front.addChild(bottom_corner_2_r1_r1)
        setRotationAngle(bottom_corner_2_r1_r1, -0.1745f, 0.5236f, 0.0873f)
        bottom_corner_2_r1_r1.texOffs(114, 32).addBox(8.951f, -0.4819f, 50.7099f, 9, 5, 0, 0f, false)

        top_handrail_head_sp1900 = ModelMapper(modelDataWrapper)
        top_handrail_head_sp1900.setPos(0f, 24f, 0f)
        top_handrail_head_sp1900.texOffs(0, 0).addBox(-5f, -36f, 9.8f, 0, 3, 0, 0.2f, false)
        top_handrail_head_sp1900.texOffs(0, 0).addBox(-5f, -36f, -9.8f, 0, 3, 0, 0.2f, false)

        top_handrail_bottom_left_r3 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_left_r3.setPos(-5f, -31.0876f, 3.8875f)
        top_handrail_head_sp1900.addChild(top_handrail_bottom_left_r3)
        setRotationAngle(top_handrail_bottom_left_r3, -1.5708f, 0f, 0f)
        top_handrail_bottom_left_r3.texOffs(0, 0).addBox(0f, -4f, 0f, 0, 8, 0, 0.2f, false)

        top_handrail_bottom_right_r3 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_right_r3.setPos(-5f, -31.0876f, -3.8876f)
        top_handrail_head_sp1900.addChild(top_handrail_bottom_right_r3)
        setRotationAngle(top_handrail_bottom_right_r3, -1.5708f, 0f, 0f)
        top_handrail_bottom_right_r3.texOffs(0, 0).addBox(0f, -4f, 0f, 0, 8, 0, 0.2f, false)

        top_handrail_right_3_r3 = ModelMapper(modelDataWrapper)
        top_handrail_right_3_r3.setPos(-5f, -31.4108f, -8.5938f)
        top_handrail_head_sp1900.addChild(top_handrail_right_3_r3)
        setRotationAngle(top_handrail_right_3_r3, 1.0472f, 0f, 0f)
        top_handrail_right_3_r3.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_right_2_r2 = ModelMapper(modelDataWrapper)
        top_handrail_right_2_r2.setPos(-4.8f, -32.8f, -10f)
        top_handrail_head_sp1900.addChild(top_handrail_right_2_r2)
        setRotationAngle(top_handrail_right_2_r2, 0.5236f, 0f, 0f)
        top_handrail_right_2_r2.texOffs(0, 0).addBox(-0.2f, 0.2f, 0.2f, 0, 1, 0, 0.2f, false)

        top_handrail_left_3_r3 = ModelMapper(modelDataWrapper)
        top_handrail_left_3_r3.setPos(-5f, -31.4108f, 8.5938f)
        top_handrail_head_sp1900.addChild(top_handrail_left_3_r3)
        setRotationAngle(top_handrail_left_3_r3, -1.0472f, 0f, 0f)
        top_handrail_left_3_r3.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_2_r2 = ModelMapper(modelDataWrapper)
        top_handrail_left_2_r2.setPos(-4.8f, -32.8f, 10f)
        top_handrail_head_sp1900.addChild(top_handrail_left_2_r2)
        setRotationAngle(top_handrail_left_2_r2, -0.5236f, 0f, 0f)
        top_handrail_left_2_r2.texOffs(0, 0).addBox(-0.2f, 0.2f, -0.2f, 0, 1, 0, 0.2f, false)

        handrail_strap_head = ModelMapper(modelDataWrapper)
        handrail_strap_head.setPos(0f, 0f, 0f)
        top_handrail_head_sp1900.addChild(handrail_strap_head)
        handrail_strap_head.texOffs(12, 12).addBox(-6f, -32f, -6f, 2, 4, 0, 0f, false)
        handrail_strap_head.texOffs(12, 12).addBox(-6f, -32f, 0f, 2, 4, 0, 0f, false)
        handrail_strap_head.texOffs(12, 12).addBox(-6f, -32f, 6f, 2, 4, 0, 0f, false)

        top_handrail_head_c1141a = ModelMapper(modelDataWrapper)
        top_handrail_head_c1141a.setPos(0f, 24f, 0f)
        top_handrail_head_c1141a.texOffs(0, 50).addBox(-5f, -31f, 15.8f, 5, 0, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(0, 50).addBox(0f, -31f, 15.8f, 5, 0, 0, 0.2f, true)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(-4.727f, -36.6045f, -3.1124f, 0, 3, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(4.727f, -36.6045f, -3.1124f, 0, 3, 0, 0.2f, true)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(-4.727f, -36.6045f, 11f, 0, 3, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(4.727f, -36.6045f, 11f, 0, 3, 0, 0.2f, true)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(0f, -36.6046f, 13.6145f, 0, 3, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(0f, -36f, 11f, 0, 7, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(8, 7).addBox(0f, -23.2645f, 10.437f, 0, 6, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(8, 7).addBox(0f, -23.2645f, 11.563f, 0, 6, 0, 0.2f, false)
        top_handrail_head_c1141a.texOffs(0, 0).addBox(0f, -12f, 11f, 0, 12, 0, 0.2f, false)

        pole_bottom_diagonal_3_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_3_r1.setPos(0f, -14.4002f, 11.2819f)
        top_handrail_head_c1141a.addChild(pole_bottom_diagonal_3_r1)
        setRotationAngle(pole_bottom_diagonal_3_r1, -0.1047f, 0f, 0f)
        pole_bottom_diagonal_3_r1.texOffs(11, 28).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_2_r2 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_2_r2.setPos(0f, -14.4002f, 10.7181f)
        top_handrail_head_c1141a.addChild(pole_bottom_diagonal_2_r2)
        setRotationAngle(pole_bottom_diagonal_2_r2, 0.1047f, 0f, 0f)
        pole_bottom_diagonal_2_r2.texOffs(11, 28).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_3_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_3_r1.setPos(0.2f, -28.8f, 10.8f)
        top_handrail_head_c1141a.addChild(pole_top_diagonal_3_r1)
        setRotationAngle(pole_top_diagonal_3_r1, 0.1047f, 0f, 0f)
        pole_top_diagonal_3_r1.texOffs(11, 11).addBox(-0.2f, 0.2069f, 0.2f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_2_r2 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_2_r2.setPos(0.2f, -28.8f, 11.2f)
        top_handrail_head_c1141a.addChild(pole_top_diagonal_2_r2)
        setRotationAngle(pole_top_diagonal_2_r2, -0.1047f, 0f, 0f)
        pole_top_diagonal_2_r2.texOffs(11, 11).addBox(-0.2f, 0.2069f, -0.2f, 0, 5, 0, 0.2f, false)

        top_handrail_connector_bottom_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_5_r1.setPos(0f, -32.2308f, 14.6605f)
        top_handrail_head_c1141a.addChild(top_handrail_connector_bottom_5_r1)
        setRotationAngle(top_handrail_connector_bottom_5_r1, 0.6981f, 0f, 0f)
        top_handrail_connector_bottom_5_r1.texOffs(0, 0).addBox(0f, -1.5f, 0f, 0, 3, 0, 0.2f, false)

        top_handrail_connector_bottom_right_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_right_4_r1.setPos(5.7729f, -32.2308f, 0f)
        top_handrail_head_c1141a.addChild(top_handrail_connector_bottom_right_4_r1)
        setRotationAngle(top_handrail_connector_bottom_right_4_r1, 0f, 0f, -0.6981f)
        top_handrail_connector_bottom_right_4_r1.texOffs(0, 0).addBox(0f, -1.5f, 11f, 0, 3, 0, 0.2f, false)

        top_handrail_connector_bottom_left_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_left_4_r1.setPos(-5.7729f, -32.2308f, 0f)
        top_handrail_head_c1141a.addChild(top_handrail_connector_bottom_left_4_r1)
        setRotationAngle(top_handrail_connector_bottom_left_4_r1, 0f, 0f, 0.6981f)
        top_handrail_connector_bottom_left_4_r1.texOffs(0, 0).addBox(0f, -1.5f, 11f, 0, 3, 0, 0.2f, false)

        top_handrail_connector_bottom_right_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_right_3_r1.setPos(5.7729f, -32.2308f, 0f)
        top_handrail_head_c1141a.addChild(top_handrail_connector_bottom_right_3_r1)
        setRotationAngle(top_handrail_connector_bottom_right_3_r1, 0f, 0f, -0.6981f)
        top_handrail_connector_bottom_right_3_r1.texOffs(0, 0).addBox(0f, -1.5f, -3.1124f, 0, 3, 0, 0.2f, false)

        top_handrail_connector_bottom_left_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_left_3_r1.setPos(-5.7729f, -32.2308f, 0f)
        top_handrail_head_c1141a.addChild(top_handrail_connector_bottom_left_3_r1)
        setRotationAngle(top_handrail_connector_bottom_left_3_r1, 0f, 0f, 0.6981f)
        top_handrail_connector_bottom_left_3_r1.texOffs(0, 0).addBox(0f, -1.5f, -3.1124f, 0, 3, 0, 0.2f, false)

        top_handrail_bottom_right_r4 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_right_r4.setPos(6.9124f, -31f, 6.8876f)
        top_handrail_head_c1141a.addChild(top_handrail_bottom_right_r4)
        setRotationAngle(top_handrail_bottom_right_r4, -1.5708f, 0f, 0f)
        top_handrail_bottom_right_r4.texOffs(0, 0).addBox(0f, -7f, 0f, 0, 17, 0, 0.2f, true)
        top_handrail_bottom_right_r4.texOffs(0, 0).addBox(-13.8249f, -7f, 0f, 0, 17, 0, 0.2f, false)

        top_handrail_right_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_5_r1.setPos(6.5892f, -31f, 14.5938f)
        top_handrail_head_c1141a.addChild(top_handrail_right_5_r1)
        setRotationAngle(top_handrail_right_5_r1, -1.5708f, -0.5236f, 0f)
        top_handrail_right_5_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, true)

        top_handrail_right_4_r2 = ModelMapper(modelDataWrapper)
        top_handrail_right_4_r2.setPos(5.7062f, -31f, 15.4768f)
        top_handrail_head_c1141a.addChild(top_handrail_right_4_r2)
        setRotationAngle(top_handrail_right_4_r2, -1.5708f, -1.0472f, 0f)
        top_handrail_right_4_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, true)

        top_handrail_left_5_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_5_r1.setPos(-6.5892f, -31f, 14.5938f)
        top_handrail_head_c1141a.addChild(top_handrail_left_5_r1)
        setRotationAngle(top_handrail_left_5_r1, -1.5708f, 0.5236f, 0f)
        top_handrail_left_5_r1.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        top_handrail_left_4_r2 = ModelMapper(modelDataWrapper)
        top_handrail_left_4_r2.setPos(-5.7062f, -31f, 15.4768f)
        top_handrail_head_c1141a.addChild(top_handrail_left_4_r2)
        setRotationAngle(top_handrail_left_4_r2, -1.5708f, 1.0472f, 0f)
        top_handrail_left_4_r2.texOffs(0, 0).addBox(0f, -0.5f, 0f, 0, 1, 0, 0.2f, false)

        handrail_strap_3 = ModelMapper(modelDataWrapper)
        handrail_strap_3.setPos(0f, 0f, 0f)
        top_handrail_head_c1141a.addChild(handrail_strap_3)
        handrail_strap_3.texOffs(12, 12).addBox(-8f, -32f, -1f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(6f, -32f, -1f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(-8f, -32f, 4f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(6f, -32f, 4f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(-8f, -32f, 9f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(6f, -32f, 9f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(-8f, -32f, 14f, 2, 4, 0, 0f, false)
        handrail_strap_3.texOffs(12, 12).addBox(6f, -32f, 14f, 2, 4, 0, 0f, false)

        handrail_strap_right_8_r1 = ModelMapper(modelDataWrapper)
        handrail_strap_right_8_r1.setPos(0f, 0f, 0f)
        handrail_strap_3.addChild(handrail_strap_right_8_r1)
        setRotationAngle(handrail_strap_right_8_r1, 0f, 1.5708f, 0f)
        handrail_strap_right_8_r1.texOffs(12, 12).addBox(-16.8f, -32f, 3f, 2, 4, 0, 0f, false)

        handrail_strap_left_8_r1 = ModelMapper(modelDataWrapper)
        handrail_strap_left_8_r1.setPos(0f, 0f, 0f)
        handrail_strap_3.addChild(handrail_strap_left_8_r1)
        setRotationAngle(handrail_strap_left_8_r1, 0f, -1.5708f, 0f)
        handrail_strap_left_8_r1.texOffs(12, 12).addBox(14.8f, -32f, 3f, 2, 4, 0, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_4_r1_r1 = ModelMapper(modelDataWrapper)
        headlight_4_r1_r1.setPos(17.943f, 10.9921f, 21.2198f)
        headlights.addChild(headlight_4_r1_r1)
        setRotationAngle(headlight_4_r1_r1, 0.3491f, -0.5236f, -0.0873f)
        headlight_4_r1_r1.texOffs(16, 34).addBox(-12f, -15.5f, 43.4f, 4, 6, 0, 0f, true)

        headlight_3_r1_r1 = ModelMapper(modelDataWrapper)
        headlight_3_r1_r1.setPos(0f, 14.3777f, 10.093f)
        headlights.addChild(headlight_3_r1_r1)
        setRotationAngle(headlight_3_r1_r1, 0.3491f, 0f, 0f)
        headlight_3_r1_r1.texOffs(18, 16).addBox(-12f, -16f, 45.7f, 7, 6, 0, 0f, true)
        headlight_3_r1_r1.texOffs(18, 16).addBox(5f, -16f, 45.7f, 7, 6, 0, 0f, false)

        headlight_1_r1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1_r1.setPos(-17.943f, 10.9921f, 21.2198f)
        headlights.addChild(headlight_1_r1_r1)
        setRotationAngle(headlight_1_r1_r1, 0.3491f, 0.5236f, 0.0873f)
        headlight_1_r1_r1.texOffs(16, 34).addBox(8f, -15.5f, 43.4f, 4, 6, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_4_r1_r1 = ModelMapper(modelDataWrapper)
        tail_light_4_r1_r1.setPos(17.943f, 10.9921f, 21.2198f)
        tail_lights.addChild(tail_light_4_r1_r1)
        setRotationAngle(tail_light_4_r1_r1, 0.3491f, -0.5236f, -0.0873f)
        tail_light_4_r1_r1.texOffs(16, 40).addBox(-12f, -15.5f, 43.4f, 4, 6, 0, 0f, true)

        tail_light_3_r1_r1 = ModelMapper(modelDataWrapper)
        tail_light_3_r1_r1.setPos(0f, 14.3777f, 10.093f)
        tail_lights.addChild(tail_light_3_r1_r1)
        setRotationAngle(tail_light_3_r1_r1, 0.3491f, 0f, 0f)
        tail_light_3_r1_r1.texOffs(18, 22).addBox(-12f, -16f, 45.7f, 7, 6, 0, 0f, true)
        tail_light_3_r1_r1.texOffs(18, 22).addBox(5f, -16f, 45.7f, 7, 6, 0, 0f, false)

        tail_light_1_r1_r1 = ModelMapper(modelDataWrapper)
        tail_light_1_r1_r1.setPos(-17.943f, 10.9921f, 21.2198f)
        tail_lights.addChild(tail_light_1_r1_r1)
        setRotationAngle(tail_light_1_r1_r1, 0.3491f, 0.5236f, 0.0873f)
        tail_light_1_r1_r1.texOffs(16, 40).addBox(8f, -15.5f, 43.4f, 4, 6, 0, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(82, 0).addBox(-1f, -21.5f, 0f, 0, 0, 0, 0.5f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(86, 0).addBox(-1f, -21.5f, 0f, 0, 0, 0, 0.5f, false)

        bb_main = ModelMapper(modelDataWrapper)
        bb_main.setPos(0f, 24f, 0f)
        bb_main.texOffs(4, 0).addBox(0f, -36f, 0f, 0, 36, 0, 0.2f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        side_panel_sp1900.setModelPart()
        side_panel_sp1900_translucent.setModelPart()
        side_panel_c1141a.setModelPart()
        side_panel_c1141a_translucent.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_light_c1141a.setModelPart()
        door_exterior_1.setModelPart()
        door_left_exterior_1.setModelPart(door_exterior_1.name)
        door_right_exterior_1.setModelPart(door_exterior_1.name)
        door_exterior_2.setModelPart()
        door_left_exterior_2.setModelPart(door_exterior_2.name)
        door_right_exterior_2.setModelPart(door_exterior_2.name)
        end.setModelPart()
        end_exterior.setModelPart()
        roof_sp1900.setModelPart()
        roof_c1141a.setModelPart()
        roof_exterior.setModelPart()
        roof_light_sp1900.setModelPart()
        roof_light_c1141a.setModelPart()
        roof_end_sp1900.setModelPart()
        roof_end_c1141a.setModelPart()
        roof_end_exterior.setModelPart()
        roof_end_light_sp1900.setModelPart()
        roof_end_light_c1141a.setModelPart()
        top_handrail_sp1900.setModelPart()
        top_handrail_c1141a.setModelPart()
        tv_pole.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        top_handrail_head_sp1900.setModelPart()
        top_handrail_head_c1141a.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        bb_main.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelSP1900 {
        return ModelSP1900(isC1141A, doorAnimationType, renderDoorOverlay)
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
                if (isC1141A) roof_light_c1141a else roof_light_sp1900,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderMirror(window, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderMirror(
                        if (isC1141A) roof_c1141a else roof_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    renderMirror(
                        if (isC1141A) top_handrail_c1141a else top_handrail_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (isC1141A) {
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position - 12).toFloat())
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position + 12).toFloat())
                    } else {
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position - 15.9f)
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position + 15.9f)
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isC1141A) {
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position - 12).toFloat())
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position + 12).toFloat())
            } else {
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position - 15.9f)
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position + 15.9f)
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
                renderMirror(
                    if (isC1141A) roof_light_c1141a else roof_light_sp1900,
                    matrices,
                    vertices,
                    light,
                    position.toFloat()
                )
                if (isC1141A) {
                    renderMirror(door_light_c1141a, matrices, vertices, light, position.toFloat())
                }
                if (middleDoor && doorOpen && renderDetails) {
                    renderMirror(door_light_on, matrices, vertices, light, (position - 32).toFloat())
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
                    renderMirror(
                        if (isC1141A) roof_c1141a else roof_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (!isC1141A) {
                        if (getDoorPositions()!!.size > 3 && (isIndex(1, position, getDoorPositions()) || isIndex(
                                3,
                                position,
                                getDoorPositions()
                            ))
                        ) {
                            renderOnce(bb_main, matrices, vertices, light, position.toFloat())
                        } else {
                            renderOnce(tv_pole, matrices, vertices, light, position.toFloat())
                        }
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                if (isEnd2Head) {
                    door_left_exterior_2.setOffset(0f, 0, doorLeftZ)
                    door_right_exterior_2.setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_2, matrices, vertices, light, position.toFloat())
                    door_left_exterior_1.setOffset(0f, 0, -doorRightZ)
                    door_right_exterior_1.setOffset(0f, 0, doorRightZ)
                    renderOnceFlipped(door_exterior_1, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior_1.setOffset(0f, 0, -doorLeftZ)
                    door_right_exterior_1.setOffset(0f, 0, doorLeftZ)
                    renderOnce(door_exterior_1, matrices, vertices, light, position.toFloat())
                    door_left_exterior_2.setOffset(0f, 0, doorRightZ)
                    door_right_exterior_2.setOffset(0f, 0, -doorRightZ)
                    renderOnce(door_exterior_2, matrices, vertices, light, position.toFloat())
                }
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                if (middleDoor && !doorOpen && renderDetails) {
                    renderMirror(door_light_off, matrices, vertices, light, (position - 32).toFloat())
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
            RenderStage.LIGHTS -> renderOnce(
                if (isC1141A) roof_end_light_c1141a else roof_end_light_sp1900,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

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
                    renderOnce(
                        if (isC1141A) roof_end_c1141a else roof_end_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (isC1141A) {
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position + 16).toFloat())
                    } else {
                        renderMirror(top_handrail_head_sp1900, matrices, vertices, light, (position + 6).toFloat())
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position + 15.9f)
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isC1141A) {
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position + 16).toFloat())
            } else {
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position + 15.9f)
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, (position + 2).toFloat())
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
            RenderStage.LIGHTS -> renderOnce(
                if (isC1141A) roof_end_light_c1141a else roof_end_light_sp1900,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

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
                    renderOnceFlipped(
                        if (isC1141A) roof_end_c1141a else roof_end_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (isC1141A) {
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position - 16).toFloat())
                    } else {
                        renderMirror(top_handrail_head_sp1900, matrices, vertices, light, (position - 6).toFloat())
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position - 15.9f)
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isC1141A) {
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position - 16).toFloat())
            } else {
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position - 15.9f)
            }

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, (position - 2).toFloat())
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
            RenderStage.LIGHTS -> renderOnce(
                if (isC1141A) roof_end_light_c1141a else roof_end_light_sp1900,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(
                        if (isC1141A) roof_end_c1141a else roof_end_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (isC1141A) {
                        renderOnce(top_handrail_head_c1141a, matrices, vertices, light, position.toFloat())
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position + 16).toFloat())
                    } else {
                        renderMirror(top_handrail_sp1900, matrices, vertices, light, position.toFloat())
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position + 15.9f)
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isC1141A) {
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position + 16).toFloat())
            } else {
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position + 15.9f)
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
            RenderStage.LIGHTS -> renderOnceFlipped(
                if (isC1141A) roof_end_light_c1141a else roof_end_light_sp1900,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.INTERIOR -> {
                renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(
                        if (isC1141A) roof_end_c1141a else roof_end_sp1900,
                        matrices,
                        vertices,
                        light,
                        position.toFloat()
                    )
                    if (isC1141A) {
                        renderOnceFlipped(top_handrail_head_c1141a, matrices, vertices, light, position.toFloat())
                        renderMirror(side_panel_c1141a, matrices, vertices, light, (position - 16).toFloat())
                    } else {
                        renderMirror(top_handrail_sp1900, matrices, vertices, light, position.toFloat())
                        renderMirror(side_panel_sp1900, matrices, vertices, light, position - 15.9f)
                    }
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> if (isC1141A) {
                renderMirror(side_panel_c1141a_translucent, matrices, vertices, light, (position - 16).toFloat())
            } else {
                renderMirror(side_panel_sp1900_translucent, matrices, vertices, light, position - 15.9f)
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
            getEndPositions()!![0] / 16f - 3.34f,
            0f,
            -2.36f,
            -0.01f,
            -20f,
            0f,
            0.76f,
            0.42f,
            if (isC1141A) -0x100 else -0x6700,
            if (isC1141A) -0x10000 else -0x6700,
            3f,
            getDestinationString(
                lastStation,
                customDestination,
                if (isC1141A) TextSpacingType.NORMAL else TextSpacingType.SPACE_CJK,
                true
            ),
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
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_sp1900_left.png", "door_overlay_sp1900_right.png")
        private val MODEL_DOOR_OVERLAY_TOP = ModelDoorOverlayTopSP1900()
    }
}
