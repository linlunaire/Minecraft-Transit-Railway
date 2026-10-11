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

open class ModelRTrain protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelRTrain?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val window_exterior_1: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val window_exterior_2: ModelMapper
    private val upper_wall_r3: ModelMapper
    private val door_exterior_1: ModelMapper
    private val door_left_exterior_1: ModelMapper
    private val door_left_top_r1: ModelMapper
    private val door_right_exterior_1: ModelMapper
    private val door_right_top_r1: ModelMapper
    private val door_exterior_2: ModelMapper
    private val door_right_exterior_2: ModelMapper
    private val door_left_top_r2: ModelMapper
    private val door_left_exterior_2: ModelMapper
    private val door_right_top_r2: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_left_top_r3: ModelMapper
    private val door_right: ModelMapper
    private val door_right_top_r3: ModelMapper
    private val end: ModelMapper
    private val upper_wall_3_r1: ModelMapper
    private val upper_wall_2_r1: ModelMapper
    private val seat_end_1: ModelMapper
    private val seat_end_2: ModelMapper
    private val seat_bottom_3_r1: ModelMapper
    private val seat_back_3_r1: ModelMapper
    private val seat_back_2_r1: ModelMapper
    private val seat_bottom_2_r1: ModelMapper
    private val end_exterior: ModelMapper
    private val upper_wall_2_r2: ModelMapper
    private val upper_wall_1_r1: ModelMapper
    private val seat: ModelMapper
    private val seat_back_2_r2: ModelMapper
    private val door_light: ModelMapper
    private val light_3_r1: ModelMapper
    private val side_panel: ModelMapper
    private val side_panel_translucent: ModelMapper
    private val roof_window_1: ModelMapper
    private val inner_roof_2_r1: ModelMapper
    private val roof_window_2: ModelMapper
    private val inner_roof_3_r1: ModelMapper
    private val roof_light: ModelMapper
    private val roof_light_r1: ModelMapper
    private val roof_door: ModelMapper
    private val inner_roof_3_r2: ModelMapper
    private val roof_end: ModelMapper
    private val side_1: ModelMapper
    private val inner_roof_4_r1: ModelMapper
    private val side_2: ModelMapper
    private val inner_roof_5_r1: ModelMapper
    private val roof_exterior: ModelMapper
    private val outer_roof_4_r1: ModelMapper
    private val outer_roof_3_r1: ModelMapper
    private val outer_roof_2_r1: ModelMapper
    private val outer_roof_1_r1: ModelMapper
    private val roof_end_exterior: ModelMapper
    private val side_3: ModelMapper
    private val outer_roof_8_r1: ModelMapper
    private val outer_roof_10_r1: ModelMapper
    private val outer_roof_11_r1: ModelMapper
    private val outer_roof_9_r1: ModelMapper
    private val side_4: ModelMapper
    private val outer_roof_7_r1: ModelMapper
    private val outer_roof_9_r2: ModelMapper
    private val outer_roof_10_r2: ModelMapper
    private val outer_roof_8_r2: ModelMapper
    private val top_handrail: ModelMapper
    private val pole_bottom_diagonal_2_r1: ModelMapper
    private val pole_bottom_diagonal_1_r1: ModelMapper
    private val pole_top_diagonal_2_r1: ModelMapper
    private val pole_top_diagonal_1_r1: ModelMapper
    private val top_handrail_connector_bottom_3_r1: ModelMapper
    private val top_handrail_bottom_right_r1: ModelMapper
    private val top_handrail_bottom_left_r1: ModelMapper
    private val top_handrail_right_4_r1: ModelMapper
    private val top_handrail_right_3_r1: ModelMapper
    private val top_handrail_left_4_r1: ModelMapper
    private val top_handrail_left_3_r1: ModelMapper
    private val handrail_straps: ModelMapper
    private val handrail_strap_8_r1: ModelMapper
    private val head: ModelMapper
    private val upper_wall_2_r3: ModelMapper
    private val upper_wall_1_r2: ModelMapper
    private val head_exterior: ModelMapper
    private val upper_wall_2_r4: ModelMapper
    private val upper_wall_1_r3: ModelMapper
    private val bottom_r1: ModelMapper
    private val floor_8_r1: ModelMapper
    private val floor_7_r1: ModelMapper
    private val front_side_1: ModelMapper
    private val front_side_lower_3_r1: ModelMapper
    private val front_side_upper_2_r1: ModelMapper
    private val front_side_upper_3_r1: ModelMapper
    private val front_side_upper_1_r1: ModelMapper
    private val front_side_2: ModelMapper
    private val front_side_lower_4_r1: ModelMapper
    private val front_side_upper_4_r1: ModelMapper
    private val front_panel: ModelMapper
    private val panel_6_r1: ModelMapper
    private val panel_5_r1: ModelMapper
    private val panel_4_r1: ModelMapper
    private val panel_4_r2: ModelMapper
    private val panel_3_r1: ModelMapper
    private val panel_3_r2: ModelMapper
    private val panel_2_r1: ModelMapper
    private val panel_2_r2: ModelMapper
    private val panel_1_r1: ModelMapper
    private val nose: ModelMapper
    private val nose_edge: ModelMapper
    private val edge_6_r1: ModelMapper
    private val edge_5_r1: ModelMapper
    private val edge_4_r1: ModelMapper
    private val edge_3_r1: ModelMapper
    private val edge_2_r1: ModelMapper
    private val edge_1_r1: ModelMapper
    private val nose_top: ModelMapper
    private val nose_top_3_r1: ModelMapper
    private val nose_top_2_r1: ModelMapper
    private val nose_top_1_r1: ModelMapper
    private val driver_door: ModelMapper
    private val driver_door_edge_upper_2_r1: ModelMapper
    private val driver_door_edge_roof_3_r1: ModelMapper
    private val roof_vent: ModelMapper
    private val vent_1_r1: ModelMapper
    private val vent_2_r1: ModelMapper
    private val roof_head_exterior: ModelMapper
    private val side_7: ModelMapper
    private val outer_roof_18_r1: ModelMapper
    private val outer_roof_17_r1: ModelMapper
    private val outer_roof_16_r1: ModelMapper
    private val outer_roof_15_r1: ModelMapper
    private val outer_roof_14_r1: ModelMapper
    private val outer_roof_9_r3: ModelMapper
    private val outer_roof_11_r2: ModelMapper
    private val outer_roof_12_r1: ModelMapper
    private val outer_roof_10_r3: ModelMapper
    private val side_8: ModelMapper
    private val outer_roof_19_r1: ModelMapper
    private val outer_roof_18_r2: ModelMapper
    private val outer_roof_17_r2: ModelMapper
    private val outer_roof_16_r2: ModelMapper
    private val outer_roof_15_r2: ModelMapper
    private val outer_roof_10_r4: ModelMapper
    private val outer_roof_12_r2: ModelMapper
    private val outer_roof_13_r1: ModelMapper
    private val outer_roof_11_r3: ModelMapper
    private val headlights: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val headlight_1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_2_r1: ModelMapper
    private val tail_light_1_r1: ModelMapper
    private val roof_head: ModelMapper
    private val side_5: ModelMapper
    private val inner_roof_5_r2: ModelMapper
    private val side_6: ModelMapper
    private val inner_roof_2_r2: ModelMapper
    private val door_light_on: ModelMapper
    private val light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val light_r2: ModelMapper
    private val end_handrail: ModelMapper
    private val pole_top_diagonal_1_r2: ModelMapper
    private val pole_top_diagonal_2_r2: ModelMapper
    private val pole_bottom_diagonal_1_r2: ModelMapper
    private val pole_bottom_diagonal_2_r2: ModelMapper

    constructor() : this(DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 336
        val textureHeight = 336

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 54).addBox(-20f, 0f, -24f, 20, 1, 48, 0f, false)
        window.texOffs(140, 0).addBox(-20f, -14f, -26f, 2, 14, 52, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(-20f, -14f, 0f)
        window.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0f, 0f, 0.1107f)
        upper_wall_r1.texOffs(84, 51).addBox(0f, -19f, -26f, 2, 19, 52, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)
        window_exterior_1.texOffs(144, 80).addBox(-21f, 0f, -24f, 1, 9, 48, 0f, false)
        window_exterior_1.texOffs(140, 14).addBox(-19.999f, -14f, -26f, 0, 14, 52, 0f, false)

        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-34.4103f, -15.6011f, -7.8f)
        window_exterior_1.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.1107f)
        upper_wall_r2.texOffs(104, 124).addBox(14.5f, -18f, -18.2f, 0, 18, 52, 0f, false)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)
        window_exterior_2.texOffs(144, 80).addBox(20f, 0f, -24f, 1, 9, 48, 0f, true)
        window_exterior_2.texOffs(140, 14).addBox(19.999f, -14f, -26f, 0, 14, 52, 0f, true)

        upper_wall_r3 = ModelMapper(modelDataWrapper)
        upper_wall_r3.setPos(34.4103f, -15.6011f, -7.8f)
        window_exterior_2.addChild(upper_wall_r3)
        setRotationAngle(upper_wall_r3, 0f, 0f, -0.1107f)
        upper_wall_r3.texOffs(104, 124).addBox(-14.5f, -18f, -18.2f, 0, 18, 52, 0f, true)

        door_exterior_1 = ModelMapper(modelDataWrapper)
        door_exterior_1.setPos(0f, 24f, 0f)
        door_exterior_1.texOffs(196, 195).addBox(-21f, 0f, -16f, 1, 9, 32, 0f, false)

        door_left_exterior_1 = ModelMapper(modelDataWrapper)
        door_left_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_left_exterior_1)
        door_left_exterior_1.texOffs(283, 1).addBox(-20.8f, -14f, 0f, 1, 14, 15, 0f, false)

        door_left_top_r1 = ModelMapper(modelDataWrapper)
        door_left_top_r1.setPos(-20.8f, -14f, 0f)
        door_left_exterior_1.addChild(door_left_top_r1)
        setRotationAngle(door_left_top_r1, 0f, 0f, 0.1107f)
        door_left_top_r1.texOffs(215, 273).addBox(0f, -19f, 0f, 1, 19, 15, 0f, false)

        door_right_exterior_1 = ModelMapper(modelDataWrapper)
        door_right_exterior_1.setPos(0f, 0f, 0f)
        door_exterior_1.addChild(door_right_exterior_1)
        door_right_exterior_1.texOffs(1, 278).addBox(-20.8f, -14f, -15f, 1, 14, 15, 0f, false)

        door_right_top_r1 = ModelMapper(modelDataWrapper)
        door_right_top_r1.setPos(-20.8f, -14f, 0f)
        door_right_exterior_1.addChild(door_right_top_r1)
        setRotationAngle(door_right_top_r1, 0f, 0f, 0.1107f)
        door_right_top_r1.texOffs(181, 273).addBox(0f, -19f, -15f, 1, 19, 15, 0f, false)

        door_exterior_2 = ModelMapper(modelDataWrapper)
        door_exterior_2.setPos(0f, 24f, 0f)
        door_exterior_2.texOffs(196, 195).addBox(20f, 0f, -16f, 1, 9, 32, 0f, true)

        door_right_exterior_2 = ModelMapper(modelDataWrapper)
        door_right_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_right_exterior_2)
        door_right_exterior_2.texOffs(283, 1).addBox(19.8f, -14f, 0f, 1, 14, 15, 0f, true)

        door_left_top_r2 = ModelMapper(modelDataWrapper)
        door_left_top_r2.setPos(20.8f, -14f, 0f)
        door_right_exterior_2.addChild(door_left_top_r2)
        setRotationAngle(door_left_top_r2, 0f, 0f, -0.1107f)
        door_left_top_r2.texOffs(215, 273).addBox(-1f, -19f, 0f, 1, 19, 15, 0f, true)

        door_left_exterior_2 = ModelMapper(modelDataWrapper)
        door_left_exterior_2.setPos(0f, 0f, 0f)
        door_exterior_2.addChild(door_left_exterior_2)
        door_left_exterior_2.texOffs(1, 278).addBox(19.8f, -14f, -15f, 1, 14, 15, 0f, true)

        door_right_top_r2 = ModelMapper(modelDataWrapper)
        door_right_top_r2.setPos(20.8f, -14f, 0f)
        door_left_exterior_2.addChild(door_right_top_r2)
        setRotationAngle(door_right_top_r2, 0f, 0f, -0.1107f)
        door_right_top_r2.texOffs(181, 273).addBox(-1f, -19f, -15f, 1, 19, 15, 0f, true)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(0, 194).addBox(-20f, 0f, -16f, 20, 1, 32, 0f, false)
        door.texOffs(96, 163).addBox(-4f, -37.25f, -4f, 4, 1, 8, 0f, false)
        door.texOffs(69, 164).addBox(-3f, -37.25f, -3f, 3, 0, 6, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(173, 196).addBox(-19.8f, -14f, 0f, 0, 14, 15, 0f, false)

        door_left_top_r3 = ModelMapper(modelDataWrapper)
        door_left_top_r3.setPos(-20.8f, -14f, 0f)
        door_left.addChild(door_left_top_r3)
        setRotationAngle(door_left_top_r3, 0f, 0f, 0.1107f)
        door_left_top_r3.texOffs(73, 179).addBox(1f, -19f, 0f, 0, 19, 15, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(119, 196).addBox(-19.8f, -14f, -15f, 0, 14, 15, 0f, false)

        door_right_top_r3 = ModelMapper(modelDataWrapper)
        door_right_top_r3.setPos(-20.8f, -14f, 0f)
        door_right.addChild(door_right_top_r3)
        setRotationAngle(door_right_top_r3, 0f, 0f, 0.1107f)
        door_right_top_r3.texOffs(1, 179).addBox(1f, -19f, -15f, 0, 19, 15, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(188, 174).addBox(-20f, 0f, -12f, 40, 1, 20, 0f, false)
        end.texOffs(224, 236).addBox(-20f, -14f, -12f, 2, 14, 22, 0f, false)
        end.texOffs(47, 275).addBox(-19f, -33f, -12f, 9, 33, 5, 0f, false)
        end.texOffs(47, 275).addBox(10f, -33f, -12f, 9, 33, 5, 0f, true)
        end.texOffs(224, 236).addBox(18f, -14f, -12f, 2, 14, 22, 0f, true)
        end.texOffs(144, 164).addBox(-13f, -38f, -12f, 26, 5, 5, 0f, false)

        upper_wall_3_r1 = ModelMapper(modelDataWrapper)
        upper_wall_3_r1.setPos(20f, -14f, 24f)
        end.addChild(upper_wall_3_r1)
        setRotationAngle(upper_wall_3_r1, 0f, 0f, -0.1107f)
        upper_wall_3_r1.texOffs(0, 103).addBox(-2f, -19f, -36f, 2, 19, 22, 0f, true)

        upper_wall_2_r1 = ModelMapper(modelDataWrapper)
        upper_wall_2_r1.setPos(-20f, -14f, 24f)
        end.addChild(upper_wall_2_r1)
        setRotationAngle(upper_wall_2_r1, 0f, 0f, 0.1107f)
        upper_wall_2_r1.texOffs(0, 103).addBox(0f, -19f, -36f, 2, 19, 22, 0f, false)

        seat_end_1 = ModelMapper(modelDataWrapper)
        seat_end_1.setPos(0f, 0f, 24f)
        end.addChild(seat_end_1)


        seat_end_2 = ModelMapper(modelDataWrapper)
        seat_end_2.setPos(0f, 0f, 24f)
        end.addChild(seat_end_2)


        seat_bottom_3_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_3_r1.setPos(0f, 0f, 0f)
        seat_end_2.addChild(seat_bottom_3_r1)
        setRotationAngle(seat_bottom_3_r1, 0f, -3.1416f, 0f)
        seat_bottom_3_r1.texOffs(280, 68).addBox(-18f, -6f, 15f, 7, 1, 16, 0f, true)

        seat_back_3_r1 = ModelMapper(modelDataWrapper)
        seat_back_3_r1.setPos(18f, -6.5f, 0f)
        seat_end_2.addChild(seat_back_3_r1)
        setRotationAngle(seat_back_3_r1, 0f, -3.1416f, 0.1047f)
        seat_back_3_r1.texOffs(68, 235).addBox(0f, -6f, 15f, 1, 4, 16, 0f, true)

        seat_back_2_r1 = ModelMapper(modelDataWrapper)
        seat_back_2_r1.setPos(-18f, -6.5f, 0f)
        seat_end_2.addChild(seat_back_2_r1)
        setRotationAngle(seat_back_2_r1, 0f, 3.1416f, -0.1047f)
        seat_back_2_r1.texOffs(68, 235).addBox(-1f, -6f, 15f, 1, 4, 16, 0f, false)

        seat_bottom_2_r1 = ModelMapper(modelDataWrapper)
        seat_bottom_2_r1.setPos(0f, 0f, 0f)
        seat_end_2.addChild(seat_bottom_2_r1)
        setRotationAngle(seat_bottom_2_r1, 0f, 3.1416f, 0f)
        seat_bottom_2_r1.texOffs(280, 68).addBox(11f, -6f, 15f, 7, 1, 16, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(270, 39).addBox(20f, 0f, -12f, 1, 9, 20, 0f, false)
        end_exterior.texOffs(270, 39).addBox(-21f, 0f, -12f, 1, 9, 20, 0f, true)
        end_exterior.texOffs(176, 236).addBox(18f, -14f, -12f, 2, 14, 22, 0f, false)
        end_exterior.texOffs(0, 0).addBox(10f, -33f, -12f, 8, 33, 0, 0f, false)
        end_exterior.texOffs(0, 0).addBox(-18f, -33f, -12f, 8, 33, 0, 0f, true)
        end_exterior.texOffs(84, 34).addBox(-18f, -45f, -12f, 36, 12, 0, 0f, false)
        end_exterior.texOffs(176, 236).addBox(-20f, -14f, -12f, 2, 14, 22, 0f, true)

        upper_wall_2_r2 = ModelMapper(modelDataWrapper)
        upper_wall_2_r2.setPos(-20f, -14f, 24f)
        end_exterior.addChild(upper_wall_2_r2)
        setRotationAngle(upper_wall_2_r2, 0f, 0f, 0.1107f)
        upper_wall_2_r2.texOffs(90, 235).addBox(0f, -18f, -36f, 2, 18, 22, 0f, true)

        upper_wall_1_r1 = ModelMapper(modelDataWrapper)
        upper_wall_1_r1.setPos(20f, -14f, 24f)
        end_exterior.addChild(upper_wall_1_r1)
        setRotationAngle(upper_wall_1_r1, 0f, 0f, -0.1107f)
        upper_wall_1_r1.texOffs(90, 235).addBox(-2f, -18f, -36f, 2, 18, 22, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(64, 194).addBox(-18f, -6f, -20f, 7, 1, 40, 0f, false)

        seat_back_2_r2 = ModelMapper(modelDataWrapper)
        seat_back_2_r2.setPos(-18f, -6.5f, 0.5f)
        seat.addChild(seat_back_2_r2)
        setRotationAngle(seat_back_2_r2, 0f, 0f, -0.0873f)
        seat_back_2_r2.texOffs(269, 150).addBox(0f, -6f, -0.5f, 1, 4, 20, 0f, false)
        seat_back_2_r2.texOffs(269, 150).addBox(0f, -6f, -20.5f, 1, 4, 20, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)
        door_light.texOffs(30, 45).addBox(-3f, -37.25f, -3f, 3, 1, 0, 0f, false)
        door_light.texOffs(0, 41).addBox(-3f, -37.25f, -3f, 0, 1, 6, 0f, false)

        light_3_r1 = ModelMapper(modelDataWrapper)
        light_3_r1.setPos(-1.5f, -36.75f, 3f)
        door_light.addChild(light_3_r1)
        setRotationAngle(light_3_r1, 0f, 3.1416f, 0f)
        light_3_r1.texOffs(21, 27).addBox(-1.5f, -0.5f, 0f, 3, 1, 0, 0f, false)

        side_panel = ModelMapper(modelDataWrapper)
        side_panel.setPos(0f, 24f, 0f)
        side_panel.texOffs(242, 126).addBox(-18f, -29f, 0f, 7, 24, 0, 0f, false)

        side_panel_translucent = ModelMapper(modelDataWrapper)
        side_panel_translucent.setPos(0f, 23f, 0f)
        side_panel_translucent.texOffs(140, 80).addBox(-18f, -28f, 0f, 6, 22, 0, 0f, false)

        roof_window_1 = ModelMapper(modelDataWrapper)
        roof_window_1.setPos(0f, 24f, 0f)
        roof_window_1.texOffs(122, 0).addBox(-16.0123f, -32.1399f, -26f, 3, 0, 52, 0f, false)
        roof_window_1.texOffs(88, 122).addBox(-10.1444f, -36.2357f, -26f, 2, 0, 52, 0f, false)
        roof_window_1.texOffs(18, 103).addBox(-6f, -37.25f, -26f, 6, 0, 52, 0f, false)

        inner_roof_2_r1 = ModelMapper(modelDataWrapper)
        inner_roof_2_r1.setPos(-13.0123f, -32.1409f, 26f)
        roof_window_1.addChild(inner_roof_2_r1)
        setRotationAngle(inner_roof_2_r1, 0f, 0f, -0.9599f)
        inner_roof_2_r1.texOffs(112, 0).addBox(0f, 0.001f, -52f, 5, 0, 52, 0f, false)

        roof_window_2 = ModelMapper(modelDataWrapper)
        roof_window_2.setPos(0f, 24f, 0f)
        roof_window_2.texOffs(122, 0).addBox(13.0123f, -32.1399f, -26f, 3, 0, 52, 0f, true)
        roof_window_2.texOffs(88, 122).addBox(8.1444f, -36.2357f, -26f, 2, 0, 52, 0f, true)
        roof_window_2.texOffs(18, 103).addBox(0f, -37.25f, -26f, 6, 0, 52, 0f, true)

        inner_roof_3_r1 = ModelMapper(modelDataWrapper)
        inner_roof_3_r1.setPos(13.0123f, -32.1409f, 26f)
        roof_window_2.addChild(inner_roof_3_r1)
        setRotationAngle(inner_roof_3_r1, 0f, 0f, 0.9599f)
        inner_roof_3_r1.texOffs(128, 0).addBox(-5f, 0.001f, -52f, 5, 0, 52, 0f, true)

        roof_light = ModelMapper(modelDataWrapper)
        roof_light.setPos(0f, 24f, 0f)


        roof_light_r1 = ModelMapper(modelDataWrapper)
        roof_light_r1.setPos(-8.1444f, -36.2367f, 26f)
        roof_light.addChild(roof_light_r1)
        setRotationAngle(roof_light_r1, 0f, 0f, -0.4102f)
        roof_light_r1.texOffs(40, 54).addBox(0f, 0.001f, -50f, 3, 0, 48, 0f, false)

        roof_door = ModelMapper(modelDataWrapper)
        roof_door.setPos(0f, 24f, 0f)
        roof_door.texOffs(88, 54).addBox(-18.0123f, -32.1399f, -14f, 5, 0, 28, 0f, false)
        roof_door.texOffs(98, 54).addBox(-10.1444f, -36.2357f, -14f, 2, 0, 28, 0f, false)
        roof_door.texOffs(0, 0).addBox(-6f, -37.25f, -14f, 6, 0, 28, 0f, false)

        inner_roof_3_r2 = ModelMapper(modelDataWrapper)
        inner_roof_3_r2.setPos(-13.0123f, -32.1409f, 26f)
        roof_door.addChild(inner_roof_3_r2)
        setRotationAngle(inner_roof_3_r2, 0f, 0f, -0.9599f)
        inner_roof_3_r2.texOffs(0, 54).addBox(0f, 0.001f, -40f, 5, 0, 28, 0f, false)

        roof_end = ModelMapper(modelDataWrapper)
        roof_end.setPos(0f, 24f, 0f)


        side_1 = ModelMapper(modelDataWrapper)
        side_1.setPos(0f, 0f, 0f)
        roof_end.addChild(side_1)
        side_1.texOffs(0, 17).addBox(-10.1444f, -36.2357f, -7f, 2, 0, 17, 0f, false)
        side_1.texOffs(13, 28).addBox(-16.0123f, -32.1399f, -7f, 3, 0, 17, 0f, false)
        side_1.texOffs(99, 82).addBox(-6f, -37.25f, -7f, 6, 0, 17, 0f, false)

        inner_roof_4_r1 = ModelMapper(modelDataWrapper)
        inner_roof_4_r1.setPos(-13.0123f, -32.1409f, 26f)
        side_1.addChild(inner_roof_4_r1)
        setRotationAngle(inner_roof_4_r1, 0f, 0f, -0.9599f)
        inner_roof_4_r1.texOffs(0, 0).addBox(0f, 0.001f, -33f, 5, 0, 17, 0f, false)

        side_2 = ModelMapper(modelDataWrapper)
        side_2.setPos(0f, 0f, 0f)
        roof_end.addChild(side_2)
        side_2.texOffs(0, 17).addBox(8.1444f, -36.2357f, -7f, 2, 0, 17, 0f, true)
        side_2.texOffs(13, 28).addBox(13.0123f, -32.1399f, -7f, 3, 0, 17, 0f, true)
        side_2.texOffs(99, 82).addBox(0f, -37.25f, -7f, 6, 0, 17, 0f, true)

        inner_roof_5_r1 = ModelMapper(modelDataWrapper)
        inner_roof_5_r1.setPos(10.1444f, -36.2367f, 26f)
        side_2.addChild(inner_roof_5_r1)
        setRotationAngle(inner_roof_5_r1, 0f, 0f, 0.9599f)
        inner_roof_5_r1.texOffs(0, 0).addBox(0f, 0.001f, -33f, 5, 0, 17, 0f, true)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(0, 0).addBox(-5.9859f, -44.6423f, -20f, 6, 0, 40, 0f, false)

        outer_roof_4_r1 = ModelMapper(modelDataWrapper)
        outer_roof_4_r1.setPos(-8.9401f, -44.1214f, -4f)
        roof_exterior.addChild(outer_roof_4_r1)
        setRotationAngle(outer_roof_4_r1, 0f, 0f, -0.1745f)
        outer_roof_4_r1.texOffs(54, 54).addBox(-4f, 0f, -16f, 7, 0, 40, 0f, false)

        outer_roof_3_r1 = ModelMapper(modelDataWrapper)
        outer_roof_3_r1.setPos(-14.178f, -42.6769f, -4f)
        roof_exterior.addChild(outer_roof_3_r1)
        setRotationAngle(outer_roof_3_r1, 0f, 0f, -0.5236f)
        outer_roof_3_r1.texOffs(0, 54).addBox(-2.5f, 0f, -16f, 4, 0, 40, 0f, false)

        outer_roof_2_r1 = ModelMapper(modelDataWrapper)
        outer_roof_2_r1.setPos(-17.3427f, -39.6952f, -4f)
        roof_exterior.addChild(outer_roof_2_r1)
        setRotationAngle(outer_roof_2_r1, 0f, 0f, -1.0472f)
        outer_roof_2_r1.texOffs(68, 54).addBox(-2f, 0f, -16f, 4, 0, 40, 0f, false)

        outer_roof_1_r1 = ModelMapper(modelDataWrapper)
        outer_roof_1_r1.setPos(-20f, -14f, -4f)
        roof_exterior.addChild(outer_roof_1_r1)
        setRotationAngle(outer_roof_1_r1, 0f, 0f, 0.1107f)
        outer_roof_1_r1.texOffs(194, 80).addBox(-1f, -24f, -16f, 1, 6, 40, 0f, false)

        roof_end_exterior = ModelMapper(modelDataWrapper)
        roof_end_exterior.setPos(0f, 24f, 0f)


        side_3 = ModelMapper(modelDataWrapper)
        side_3.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(side_3)
        side_3.texOffs(250, 222).addBox(-5.9859f, -44.6423f, -12f, 6, 1, 22, 0f, true)

        outer_roof_8_r1 = ModelMapper(modelDataWrapper)
        outer_roof_8_r1.setPos(-20.9939f, -14.1104f, -4f)
        side_3.addChild(outer_roof_8_r1)
        setRotationAngle(outer_roof_8_r1, 0f, 0f, 0.1107f)
        outer_roof_8_r1.texOffs(0, 72).addBox(0f, -24f, -8f, 1, 6, 22, 0f, true)

        outer_roof_10_r1 = ModelMapper(modelDataWrapper)
        outer_roof_10_r1.setPos(-15.0444f, -42.1768f, -4f)
        side_3.addChild(outer_roof_10_r1)
        setRotationAngle(outer_roof_10_r1, 0f, 0f, -0.5236f)
        outer_roof_10_r1.texOffs(0, 28).addBox(-1.5f, 0f, -8f, 4, 1, 22, 0f, true)

        outer_roof_11_r1 = ModelMapper(modelDataWrapper)
        outer_roof_11_r1.setPos(-9.9249f, -43.9477f, -4f)
        side_3.addChild(outer_roof_11_r1)
        setRotationAngle(outer_roof_11_r1, 0f, 0f, -0.1745f)
        outer_roof_11_r1.texOffs(246, 16).addBox(-3f, 0f, -8f, 7, 1, 22, 0f, true)

        outer_roof_9_r1 = ModelMapper(modelDataWrapper)
        outer_roof_9_r1.setPos(-17.3427f, -39.6952f, -4f)
        side_3.addChild(outer_roof_9_r1)
        setRotationAngle(outer_roof_9_r1, 0f, 0f, -1.0472f)
        outer_roof_9_r1.texOffs(140, 80).addBox(-2f, 0f, -8f, 4, 1, 22, 0f, true)

        side_4 = ModelMapper(modelDataWrapper)
        side_4.setPos(0f, 0f, 0f)
        roof_end_exterior.addChild(side_4)
        side_4.texOffs(250, 222).addBox(-0.0141f, -44.6423f, -12f, 6, 1, 22, 0f, false)

        outer_roof_7_r1 = ModelMapper(modelDataWrapper)
        outer_roof_7_r1.setPos(20.9939f, -14.1104f, -4f)
        side_4.addChild(outer_roof_7_r1)
        setRotationAngle(outer_roof_7_r1, 0f, 0f, -0.1107f)
        outer_roof_7_r1.texOffs(0, 72).addBox(-1f, -24f, -8f, 1, 6, 22, 0f, false)

        outer_roof_9_r2 = ModelMapper(modelDataWrapper)
        outer_roof_9_r2.setPos(15.0444f, -42.1768f, -4f)
        side_4.addChild(outer_roof_9_r2)
        setRotationAngle(outer_roof_9_r2, 0f, 0f, 0.5236f)
        outer_roof_9_r2.texOffs(0, 28).addBox(-2.5f, 0f, -8f, 4, 1, 22, 0f, false)

        outer_roof_10_r2 = ModelMapper(modelDataWrapper)
        outer_roof_10_r2.setPos(9.9249f, -43.9477f, -4f)
        side_4.addChild(outer_roof_10_r2)
        setRotationAngle(outer_roof_10_r2, 0f, 0f, 0.1745f)
        outer_roof_10_r2.texOffs(246, 16).addBox(-4f, 0f, -8f, 7, 1, 22, 0f, false)

        outer_roof_8_r2 = ModelMapper(modelDataWrapper)
        outer_roof_8_r2.setPos(17.3427f, -39.6952f, -4f)
        side_4.addChild(outer_roof_8_r2)
        setRotationAngle(outer_roof_8_r2, 0f, 0f, 1.0472f)
        outer_roof_8_r2.texOffs(140, 80).addBox(-2f, 0f, -8f, 4, 1, 22, 0f, false)

        top_handrail = ModelMapper(modelDataWrapper)
        top_handrail.setPos(0f, 24f, 0f)
        top_handrail.texOffs(330, 0).addBox(-3f, -33f, 33.166f, 3, 0, 0, 0.2f, false)
        top_handrail.texOffs(330, 0).addBox(-3f, -33f, -33.166f, 3, 0, 0, 0.2f, false)
        top_handrail.texOffs(319, 0).addBox(-5.3453f, -37.249f, -22f, 0, 2, 0, 0.2f, false)
        top_handrail.texOffs(319, 0).addBox(-5.3453f, -37.249f, 0f, 0, 2, 0, 0.2f, false)
        top_handrail.texOffs(319, 0).addBox(-5.3453f, -37.249f, 22f, 0, 2, 0, 0.2f, false)
        top_handrail.texOffs(335, 0).addBox(0f, -38f, 33.166f, 0, 5, 0, 0.2f, false)
        top_handrail.texOffs(335, 0).addBox(0f, -38f, 11f, 0, 9, 0, 0.2f, false)
        top_handrail.texOffs(332, 13).addBox(0f, -23.2645f, 10.437f, 0, 6, 0, 0.2f, false)
        top_handrail.texOffs(332, 13).addBox(0f, -23.2645f, 11.563f, 0, 6, 0, 0.2f, false)
        top_handrail.texOffs(335, 0).addBox(0f, -12f, 11f, 0, 12, 0, 0.2f, false)

        pole_bottom_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_2_r1.setPos(0f, -14.4002f, 11.2819f)
        top_handrail.addChild(pole_bottom_diagonal_2_r1)
        setRotationAngle(pole_bottom_diagonal_2_r1, -0.1047f, 0f, 0f)
        pole_bottom_diagonal_2_r1.texOffs(335, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_1_r1.setPos(0f, -14.4002f, 10.7181f)
        top_handrail.addChild(pole_bottom_diagonal_1_r1)
        setRotationAngle(pole_bottom_diagonal_1_r1, 0.1047f, 0f, 0f)
        pole_bottom_diagonal_1_r1.texOffs(335, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_2_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_2_r1.setPos(0.2f, -28.8f, 10.8f)
        top_handrail.addChild(pole_top_diagonal_2_r1)
        setRotationAngle(pole_top_diagonal_2_r1, 0.1047f, 0f, 0f)
        pole_top_diagonal_2_r1.texOffs(335, 0).addBox(-0.2f, 0.2069f, 0.2f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_1_r1 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_1_r1.setPos(0.2f, -28.8f, 11.2f)
        top_handrail.addChild(pole_top_diagonal_1_r1)
        setRotationAngle(pole_top_diagonal_1_r1, -0.1047f, 0f, 0f)
        pole_top_diagonal_1_r1.texOffs(335, 0).addBox(-0.2f, 0.2069f, -0.2f, 0, 5, 0, 0.2f, false)

        top_handrail_connector_bottom_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_connector_bottom_3_r1.setPos(-6.4333f, -32.9847f, -17.7f)
        top_handrail.addChild(top_handrail_connector_bottom_3_r1)
        setRotationAngle(top_handrail_connector_bottom_3_r1, 0f, 0f, 0.3927f)
        top_handrail_connector_bottom_3_r1.texOffs(319, 0).addBox(0.2f, -2.2f, 39.7f, 0, 2, 0, 0.2f, false)
        top_handrail_connector_bottom_3_r1.texOffs(319, 0).addBox(0.2f, -2.2f, 17.7f, 0, 2, 0, 0.2f, false)
        top_handrail_connector_bottom_3_r1.texOffs(319, 0).addBox(0.2f, -2.2f, -4.3f, 0, 2, 0, 0.2f, false)

        top_handrail_bottom_right_r1 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_right_r1.setPos(-6.9124f, -31f, 7.1125f)
        top_handrail.addChild(top_handrail_bottom_right_r1)
        setRotationAngle(top_handrail_bottom_right_r1, 1.5708f, 0f, 0f)
        top_handrail_bottom_right_r1.texOffs(335, 0).addBox(0.6339f, -37f, 2f, 0, 30, 0, 0.2f, false)

        top_handrail_bottom_left_r1 = ModelMapper(modelDataWrapper)
        top_handrail_bottom_left_r1.setPos(-6.9124f, -31f, 6.8876f)
        top_handrail.addChild(top_handrail_bottom_left_r1)
        setRotationAngle(top_handrail_bottom_left_r1, -1.5708f, 0f, 0f)
        top_handrail_bottom_left_r1.texOffs(335, 0).addBox(0.6339f, -23f, -2f, 0, 30, 0, 0.2f, false)

        top_handrail_right_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_4_r1.setPos(-5.9553f, -31f, -30.5938f)
        top_handrail.addChild(top_handrail_right_4_r1)
        setRotationAngle(top_handrail_right_4_r1, 1.5708f, -0.5236f, 0f)
        top_handrail_right_4_r1.texOffs(319, 0).addBox(0f, -1.5f, 2f, 0, 2, 0, 0.2f, false)

        top_handrail_right_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_right_3_r1.setPos(-4.5723f, -31f, -32.3428f)
        top_handrail.addChild(top_handrail_right_3_r1)
        setRotationAngle(top_handrail_right_3_r1, 1.5708f, -1.0472f, 0f)
        top_handrail_right_3_r1.texOffs(319, 0).addBox(0f, -1.5f, 2f, 0, 2, 0, 0.2f, false)

        top_handrail_left_4_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_4_r1.setPos(-5.9553f, -31f, 30.5938f)
        top_handrail.addChild(top_handrail_left_4_r1)
        setRotationAngle(top_handrail_left_4_r1, -1.5708f, 0.5236f, 0f)
        top_handrail_left_4_r1.texOffs(319, 0).addBox(0f, -1.5f, -2f, 0, 2, 0, 0.2f, false)

        top_handrail_left_3_r1 = ModelMapper(modelDataWrapper)
        top_handrail_left_3_r1.setPos(-4.5723f, -31f, 32.3428f)
        top_handrail.addChild(top_handrail_left_3_r1)
        setRotationAngle(top_handrail_left_3_r1, -1.5708f, 1.0472f, 0f)
        top_handrail_left_3_r1.texOffs(319, 0).addBox(0f, -1.5f, -2f, 0, 2, 0, 0.2f, false)

        handrail_straps = ModelMapper(modelDataWrapper)
        handrail_straps.setPos(0f, 0f, 0f)
        top_handrail.addChild(handrail_straps)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, -20f, 2, 4, 0, 0f, false)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, -11f, 2, 4, 0, 0f, false)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, -2f, 2, 4, 0, 0f, false)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, 2f, 2, 4, 0, 0f, false)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, 11f, 2, 4, 0, 0f, false)
        handrail_straps.texOffs(36, 42).addBox(-7.25f, -34f, 20f, 2, 4, 0, 0f, false)

        handrail_strap_8_r1 = ModelMapper(modelDataWrapper)
        handrail_strap_8_r1.setPos(0f, 0f, 0f)
        handrail_straps.addChild(handrail_strap_8_r1)
        setRotationAngle(handrail_strap_8_r1, 0f, -1.5708f, 0f)
        handrail_strap_8_r1.texOffs(36, 42).addBox(-34.166f, -34f, 3f, 2, 4, 0, 0f, false)
        handrail_strap_8_r1.texOffs(36, 42).addBox(32.166f, -34f, 3f, 2, 4, 0, 0f, false)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(118, 195).addBox(-20f, 0f, -7f, 40, 1, 15, 0f, false)
        head.texOffs(292, 30).addBox(18f, -14f, -4f, 2, 14, 14, 0f, false)
        head.texOffs(292, 30).addBox(-20f, -14f, -4f, 2, 14, 14, 0f, true)
        head.texOffs(196, 0).addBox(-18f, -38f, -4f, 36, 38, 0, 0f, false)

        upper_wall_2_r3 = ModelMapper(modelDataWrapper)
        upper_wall_2_r3.setPos(-20f, -14f, 0f)
        head.addChild(upper_wall_2_r3)
        setRotationAngle(upper_wall_2_r3, 0f, 0f, 0.1107f)
        upper_wall_2_r3.texOffs(75, 275).addBox(0f, -19f, -4f, 2, 19, 14, 0f, true)

        upper_wall_1_r2 = ModelMapper(modelDataWrapper)
        upper_wall_1_r2.setPos(20f, -14f, 0f)
        head.addChild(upper_wall_1_r2)
        setRotationAngle(upper_wall_1_r2, 0f, 0f, -0.1107f)
        upper_wall_1_r2.texOffs(75, 275).addBox(-2f, -19f, -4f, 2, 19, 14, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(230, 195).addBox(20f, 0f, -4f, 1, 9, 12, 0f, false)
        head_exterior.texOffs(144, 137).addBox(-20f, 0f, -25f, 40, 9, 18, 0f, false)
        head_exterior.texOffs(230, 195).addBox(-21f, 0f, -4f, 1, 9, 12, 0f, true)
        head_exterior.texOffs(247, 272).addBox(-18f, -44f, -7f, 36, 44, 0, 0f, false)
        head_exterior.texOffs(82, 122).addBox(18f, -14f, -8f, 2, 14, 18, 0f, false)
        head_exterior.texOffs(82, 122).addBox(-20f, -14f, -8f, 2, 14, 18, 0f, true)

        upper_wall_2_r4 = ModelMapper(modelDataWrapper)
        upper_wall_2_r4.setPos(-20f, -14f, 24f)
        head_exterior.addChild(upper_wall_2_r4)
        setRotationAngle(upper_wall_2_r4, 0f, 0f, 0.1107f)
        upper_wall_2_r4.texOffs(194, 80).addBox(0f, -18f, -32f, 2, 18, 18, 0f, true)

        upper_wall_1_r3 = ModelMapper(modelDataWrapper)
        upper_wall_1_r3.setPos(20f, -14f, 24f)
        head_exterior.addChild(upper_wall_1_r3)
        setRotationAngle(upper_wall_1_r3, 0f, 0f, -0.1107f)
        upper_wall_1_r3.texOffs(194, 80).addBox(-2f, -18f, -32f, 2, 18, 18, 0f, false)

        bottom_r1 = ModelMapper(modelDataWrapper)
        bottom_r1.setPos(-20f, 7.263f, -15.3805f)
        head_exterior.addChild(bottom_r1)
        setRotationAngle(bottom_r1, -0.0873f, 0f, 0f)
        bottom_r1.texOffs(50, 0).addBox(0f, 1f, -25.5f, 40, 0, 34, 0f, false)

        floor_8_r1 = ModelMapper(modelDataWrapper)
        floor_8_r1.setPos(-23.4373f, 3.5f, 4.1584f)
        head_exterior.addChild(floor_8_r1)
        setRotationAngle(floor_8_r1, 0f, -0.3491f, 0f)
        floor_8_r1.texOffs(112, 157).addBox(-0.5f, -3.5f, -12.5f, 1, 9, 4, 0f, true)

        floor_7_r1 = ModelMapper(modelDataWrapper)
        floor_7_r1.setPos(23.4373f, 3.5f, 4.1584f)
        head_exterior.addChild(floor_7_r1)
        setRotationAngle(floor_7_r1, 0f, 0.3491f, 0f)
        floor_7_r1.texOffs(112, 157).addBox(-0.5f, -3.5f, -12.5f, 1, 9, 4, 0f, false)

        front_side_1 = ModelMapper(modelDataWrapper)
        front_side_1.setPos(0f, 0f, 0f)
        head_exterior.addChild(front_side_1)
        front_side_1.texOffs(0, 49).addBox(20f, -14f, -25f, 0, 14, 5, 0f, false)
        front_side_1.texOffs(0, 49).addBox(-20f, -14f, -25f, 0, 14, 5, 0f, true)

        front_side_lower_3_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_3_r1.setPos(20f, 0f, -25f)
        front_side_1.addChild(front_side_lower_3_r1)
        setRotationAngle(front_side_lower_3_r1, 0f, 0.2182f, 0f)
        front_side_lower_3_r1.texOffs(0, 59).addBox(0f, -14f, -9f, 0, 22, 9, 0f, false)

        front_side_upper_2_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_2_r1.setPos(-19.8274f, -13.9808f, -16.0152f)
        front_side_1.addChild(front_side_upper_2_r1)
        setRotationAngle(front_side_upper_2_r1, 0f, 0f, 0.1107f)
        front_side_upper_2_r1.texOffs(152, 75).addBox(-0.1736f, -18f, -8.9848f, 0, 18, 5, 0f, true)

        front_side_upper_3_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_3_r1.setPos(20f, -14f, -25f)
        front_side_1.addChild(front_side_upper_3_r1)
        setRotationAngle(front_side_upper_3_r1, 0f, 0.2182f, -0.1107f)
        front_side_upper_3_r1.texOffs(138, 231).addBox(0f, -24f, -7f, 0, 24, 7, 0f, false)

        front_side_upper_1_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_1_r1.setPos(19.8274f, -13.9808f, -16.0152f)
        front_side_1.addChild(front_side_upper_1_r1)
        setRotationAngle(front_side_upper_1_r1, 0f, 0f, -0.1107f)
        front_side_upper_1_r1.texOffs(152, 75).addBox(0.1736f, -18f, -8.9848f, 0, 18, 5, 0f, false)

        front_side_2 = ModelMapper(modelDataWrapper)
        front_side_2.setPos(0f, 0f, 0f)
        head_exterior.addChild(front_side_2)


        front_side_lower_4_r1 = ModelMapper(modelDataWrapper)
        front_side_lower_4_r1.setPos(-20f, 7f, -24.9988f)
        front_side_2.addChild(front_side_lower_4_r1)
        setRotationAngle(front_side_lower_4_r1, 0f, -0.2182f, 0f)
        front_side_lower_4_r1.texOffs(0, 59).addBox(0.001f, -21f, -9f, 0, 22, 9, 0f, false)

        front_side_upper_4_r1 = ModelMapper(modelDataWrapper)
        front_side_upper_4_r1.setPos(-19.999f, -13.9999f, -24.9988f)
        front_side_2.addChild(front_side_upper_4_r1)
        setRotationAngle(front_side_upper_4_r1, 0f, -0.2182f, 0.1107f)
        front_side_upper_4_r1.texOffs(138, 231).addBox(0f, -24f, -7f, 0, 24, 7, 0f, false)

        front_panel = ModelMapper(modelDataWrapper)
        front_panel.setPos(0f, 0f, 0f)
        head_exterior.addChild(front_panel)


        panel_6_r1 = ModelMapper(modelDataWrapper)
        panel_6_r1.setPos(-7.5573f, -13.881f, -33.7427f)
        front_panel.addChild(panel_6_r1)
        setRotationAngle(panel_6_r1, -0.1309f, 0f, 0f)
        panel_6_r1.texOffs(0, 144).addBox(0f, 0f, 0f, 15, 11, 0, 0f, false)

        panel_5_r1 = ModelMapper(modelDataWrapper)
        panel_5_r1.setPos(-7.2465f, -13.881f, -33.7767f)
        front_panel.addChild(panel_5_r1)
        setRotationAngle(panel_5_r1, -0.1309f, 0.2182f, 0f)
        panel_5_r1.texOffs(72, 213).addBox(-12f, 0f, 0f, 12, 11, 0, 0f, false)

        panel_4_r1 = ModelMapper(modelDataWrapper)
        panel_4_r1.setPos(7.132f, -13.881f, -33.7767f)
        front_panel.addChild(panel_4_r1)
        setRotationAngle(panel_4_r1, -0.1309f, -0.2182f, 0f)
        panel_4_r1.texOffs(172, 225).addBox(0f, 0f, 0f, 12, 11, 0, 0f, false)

        panel_4_r2 = ModelMapper(modelDataWrapper)
        panel_4_r2.setPos(4.7969f, -43.6878f, -23.2439f)
        front_panel.addChild(panel_4_r2)
        setRotationAngle(panel_4_r2, -0.5672f, -0.2182f, 0f)
        panel_4_r2.texOffs(206, 164).addBox(0f, 1f, 0f, 12, 8, 0, 0f, false)

        panel_3_r1 = ModelMapper(modelDataWrapper)
        panel_3_r1.setPos(5.3394f, -44.7906f, -25.6908f)
        front_panel.addChild(panel_3_r1)
        setRotationAngle(panel_3_r1, -0.2618f, -0.2182f, 0f)
        panel_3_r1.texOffs(0, 227).addBox(0f, 9f, 0f, 12, 23, 0, 0f, false)

        panel_3_r2 = ModelMapper(modelDataWrapper)
        panel_3_r2.setPos(-4.9115f, -43.6878f, -23.2439f)
        front_panel.addChild(panel_3_r2)
        setRotationAngle(panel_3_r2, -0.5672f, 0.2182f, 0f)
        panel_3_r2.texOffs(118, 225).addBox(-12f, 1f, 0f, 12, 8, 0, 0f, false)

        panel_2_r1 = ModelMapper(modelDataWrapper)
        panel_2_r1.setPos(-5.4539f, -44.7906f, -25.6908f)
        front_panel.addChild(panel_2_r1)
        setRotationAngle(panel_2_r1, -0.2618f, 0.2182f, 0f)
        panel_2_r1.texOffs(38, 227).addBox(-12f, 9f, 0f, 12, 23, 0, 0f, false)

        panel_2_r2 = ModelMapper(modelDataWrapper)
        panel_2_r2.setPos(-7.5573f, -43.6878f, -22.9541f)
        front_panel.addChild(panel_2_r2)
        setRotationAngle(panel_2_r2, -0.5672f, 0f, 0f)
        panel_2_r2.texOffs(196, 38).addBox(0f, 1f, 0f, 15, 8, 0, 0f, false)

        panel_1_r1 = ModelMapper(modelDataWrapper)
        panel_1_r1.setPos(-7.5573f, -44.7906f, -25.4605f)
        front_panel.addChild(panel_1_r1)
        setRotationAngle(panel_1_r1, -0.2618f, 0f, 0f)
        panel_1_r1.texOffs(291, 146).addBox(0f, 9f, 0f, 15, 23, 0, 0f, false)

        nose = ModelMapper(modelDataWrapper)
        nose.setPos(0f, 0f, 0f)
        head_exterior.addChild(nose)


        nose_edge = ModelMapper(modelDataWrapper)
        nose_edge.setPos(0f, 0f, 0f)
        nose.addChild(nose_edge)


        edge_6_r1 = ModelMapper(modelDataWrapper)
        edge_6_r1.setPos(7.7367f, 6f, -40.4098f)
        nose_edge.addChild(edge_6_r1)
        setRotationAngle(edge_6_r1, 0f, 0f, 0f)
        edge_6_r1.texOffs(82, 122).addBox(-8f, -9f, 0f, 8, 9, 0, 0f, false)

        edge_5_r1 = ModelMapper(modelDataWrapper)
        edge_5_r1.setPos(-7.7358f, 6f, -40.4081f)
        nose_edge.addChild(edge_5_r1)
        setRotationAngle(edge_5_r1, 0f, 0f, 0f)
        edge_5_r1.texOffs(82, 122).addBox(0f, -9f, 0f, 8, 9, 0, 0f, false)

        edge_4_r1 = ModelMapper(modelDataWrapper)
        edge_4_r1.setPos(-15.3656f, 13f, -38.0024f)
        nose_edge.addChild(edge_4_r1)
        setRotationAngle(edge_4_r1, 0f, 0.3054f, 0f)
        edge_4_r1.texOffs(104, 122).addBox(0f, -15.8f, 0f, 8, 9, 0, 0f, true)

        edge_3_r1 = ModelMapper(modelDataWrapper)
        edge_3_r1.setPos(15.3664f, -2f, -38.0042f)
        nose_edge.addChild(edge_3_r1)
        setRotationAngle(edge_3_r1, 0f, -0.3054f, 0f)
        edge_3_r1.texOffs(104, 122).addBox(-8f, -0.8f, 0f, 8, 9, 0, 0f, false)

        edge_2_r1 = ModelMapper(modelDataWrapper)
        edge_2_r1.setPos(18.052f, 17f, -33.7867f)
        nose_edge.addChild(edge_2_r1)
        setRotationAngle(edge_2_r1, 0f, 0.5672f, 0f)
        edge_2_r1.texOffs(30, 139).addBox(0.001f, -20f, -5f, 0, 10, 5, 0f, false)

        edge_1_r1 = ModelMapper(modelDataWrapper)
        edge_1_r1.setPos(-18.0521f, 7f, -33.7855f)
        nose_edge.addChild(edge_1_r1)
        setRotationAngle(edge_1_r1, 0f, -0.5672f, 0f)
        edge_1_r1.texOffs(30, 139).addBox(0.001f, -10f, -5f, 0, 10, 5, 0f, false)

        nose_top = ModelMapper(modelDataWrapper)
        nose_top.setPos(0f, 0f, 0f)
        nose.addChild(nose_top)


        nose_top_3_r1 = ModelMapper(modelDataWrapper)
        nose_top_3_r1.setPos(7.4428f, -2.9742f, -35.178f)
        nose_top.addChild(nose_top_3_r1)
        setRotationAngle(nose_top_3_r1, 0.1745f, 0f, 0f)
        nose_top_3_r1.texOffs(78, 46).addBox(-16f, 0f, -6f, 17, 0, 6, 0f, false)

        nose_top_2_r1 = ModelMapper(modelDataWrapper)
        nose_top_2_r1.setPos(20.2491f, -2.9751f, -32.3648f)
        nose_top.addChild(nose_top_2_r1)
        setRotationAngle(nose_top_2_r1, 0.1745f, -0.2182f, 0f)
        nose_top_2_r1.texOffs(20, 117).addBox(-14f, 0f, -6f, 13, 0, 6, 0f, true)

        nose_top_1_r1 = ModelMapper(modelDataWrapper)
        nose_top_1_r1.setPos(-20.2491f, -2.9751f, -32.3648f)
        nose_top.addChild(nose_top_1_r1)
        setRotationAngle(nose_top_1_r1, 0.1745f, 0.2182f, 0f)
        nose_top_1_r1.texOffs(20, 117).addBox(1f, 0f, -6f, 13, 0, 6, 0f, false)

        driver_door = ModelMapper(modelDataWrapper)
        driver_door.setPos(0f, 0f, 0f)
        head_exterior.addChild(driver_door)
        driver_door.texOffs(285, 246).addBox(18f, -14f, -20f, 1, 14, 12, 0f, false)
        driver_door.texOffs(10, 33).addBox(19f, -14f, -20f, 1, 14, 0, 0f, false)
        driver_door.texOffs(285, 246).addBox(-19f, -14f, -20f, 1, 14, 12, 0f, true)
        driver_door.texOffs(10, 33).addBox(-20f, -14f, -20f, 1, 14, 0, 0f, true)

        driver_door_edge_upper_2_r1 = ModelMapper(modelDataWrapper)
        driver_door_edge_upper_2_r1.setPos(-20f, -14f, -2f)
        driver_door.addChild(driver_door_edge_upper_2_r1)
        setRotationAngle(driver_door_edge_upper_2_r1, 0f, 0f, 0.1107f)
        driver_door_edge_upper_2_r1.texOffs(26, 54).addBox(0f, -18f, -18f, 1, 18, 0, 0f, true)
        driver_door_edge_upper_2_r1.texOffs(11, 17).addBox(0f, -18f, -15f, 1, 0, 10, 0f, true)
        driver_door_edge_upper_2_r1.texOffs(293, 86).addBox(1f, -18f, -18f, 1, 18, 12, 0f, true)
        driver_door_edge_upper_2_r1.texOffs(15, 17).addBox(-1f, -18f, -23f, 2, 0, 8, 0f, true)

        driver_door_edge_roof_3_r1 = ModelMapper(modelDataWrapper)
        driver_door_edge_roof_3_r1.setPos(20f, -14f, 1f)
        driver_door.addChild(driver_door_edge_roof_3_r1)
        setRotationAngle(driver_door_edge_roof_3_r1, 0f, 0f, -0.1107f)
        driver_door_edge_roof_3_r1.texOffs(15, 17).addBox(-1f, -18f, -26f, 2, 0, 8, 0f, false)
        driver_door_edge_roof_3_r1.texOffs(11, 17).addBox(-1f, -18f, -18f, 1, 0, 10, 0f, false)
        driver_door_edge_roof_3_r1.texOffs(26, 54).addBox(-1f, -18f, -21f, 1, 18, 0, 0f, false)
        driver_door_edge_roof_3_r1.texOffs(293, 86).addBox(-2f, -18f, -21f, 1, 18, 12, 0f, false)

        roof_vent = ModelMapper(modelDataWrapper)
        roof_vent.setPos(0f, 24f, 0f)
        roof_vent.texOffs(0, 0).addBox(-8f, -45.25f, -4f, 16, 2, 52, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(8f, -45.25f, 0f)
        roof_vent.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 0.3491f)
        vent_1_r1.texOffs(70, 122).addBox(0f, 0f, -4f, 9, 2, 52, 0f, false)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(-8f, -45.25f, 0f)
        roof_vent.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -0.3491f)
        vent_2_r1.texOffs(0, 103).addBox(-9f, 0f, -4f, 9, 2, 52, 0f, false)

        roof_head_exterior = ModelMapper(modelDataWrapper)
        roof_head_exterior.setPos(0f, 24f, 0f)


        side_7 = ModelMapper(modelDataWrapper)
        side_7.setPos(0f, 0f, 0f)
        roof_head_exterior.addChild(side_7)
        side_7.texOffs(0, 227).addBox(-5.9859f, -44.6423f, -16f, 6, 1, 26, 0f, false)

        outer_roof_18_r1 = ModelMapper(modelDataWrapper)
        outer_roof_18_r1.setPos(-1.9859f, -44.6413f, -15.9999f)
        side_7.addChild(outer_roof_18_r1)
        setRotationAngle(outer_roof_18_r1, 0.2618f, 0f, 0f)
        outer_roof_18_r1.texOffs(31, 40).addBox(-4f, 0f, -9f, 6, 0, 9, 0f, false)

        outer_roof_17_r1 = ModelMapper(modelDataWrapper)
        outer_roof_17_r1.setPos(-8.9399f, -44.1204f, -15.9999f)
        side_7.addChild(outer_roof_17_r1)
        setRotationAngle(outer_roof_17_r1, 0.2618f, 0f, -0.1745f)
        outer_roof_17_r1.texOffs(15, 82).addBox(-4f, 0f, -9f, 7, 0, 9, 0f, false)

        outer_roof_16_r1 = ModelMapper(modelDataWrapper)
        outer_roof_16_r1.setPos(-12.8788f, -43.4259f, -15.9999f)
        side_7.addChild(outer_roof_16_r1)
        setRotationAngle(outer_roof_16_r1, 0.2618f, 0f, -0.5236f)
        outer_roof_16_r1.texOffs(0, 112).addBox(-4f, 0f, -9f, 4, 0, 9, 0f, false)

        outer_roof_15_r1 = ModelMapper(modelDataWrapper)
        outer_roof_15_r1.setPos(-16.566f, -41.5562f, -15.0338f)
        side_7.addChild(outer_roof_15_r1)
        setRotationAngle(outer_roof_15_r1, 0.2618f, 0f, -1.0472f)
        outer_roof_15_r1.texOffs(25, 103).addBox(-8f, 0f, -12f, 8, 0, 11, 0f, false)

        outer_roof_14_r1 = ModelMapper(modelDataWrapper)
        outer_roof_14_r1.setPos(-18.3436f, -37.9636f, -17f)
        side_7.addChild(outer_roof_14_r1)
        setRotationAngle(outer_roof_14_r1, 0f, -0.1309f, 0.1107f)
        outer_roof_14_r1.texOffs(94, 86).addBox(0f, 0f, -8f, 0, 6, 8, 0f, false)

        outer_roof_9_r3 = ModelMapper(modelDataWrapper)
        outer_roof_9_r3.setPos(-20.9939f, -14.1104f, -4f)
        side_7.addChild(outer_roof_9_r3)
        setRotationAngle(outer_roof_9_r3, 0f, 0f, 0.1107f)
        outer_roof_9_r3.texOffs(233, 137).addBox(0f, -24f, -13f, 1, 6, 27, 0f, false)

        outer_roof_11_r2 = ModelMapper(modelDataWrapper)
        outer_roof_11_r2.setPos(-15.0444f, -42.1768f, -4f)
        side_7.addChild(outer_roof_11_r2)
        setRotationAngle(outer_roof_11_r2, 0f, 0f, -0.5236f)
        outer_roof_11_r2.texOffs(230, 195).addBox(-1.5f, 0f, -12f, 4, 1, 26, 0f, false)

        outer_roof_12_r1 = ModelMapper(modelDataWrapper)
        outer_roof_12_r1.setPos(-9.9249f, -43.9477f, -4f)
        side_7.addChild(outer_roof_12_r1)
        setRotationAngle(outer_roof_12_r1, 0f, 0f, -0.1745f)
        outer_roof_12_r1.texOffs(132, 211).addBox(-3f, 0f, -12f, 7, 1, 26, 0f, false)

        outer_roof_10_r3 = ModelMapper(modelDataWrapper)
        outer_roof_10_r3.setPos(-17.3427f, -39.6952f, -4f)
        side_7.addChild(outer_roof_10_r3)
        setRotationAngle(outer_roof_10_r3, 0f, 0f, -1.0472f)
        outer_roof_10_r3.texOffs(236, 54).addBox(-2f, -0.1f, -12f, 4, 1, 26, 0f, false)

        side_8 = ModelMapper(modelDataWrapper)
        side_8.setPos(0f, 0f, 0f)
        roof_head_exterior.addChild(side_8)
        side_8.texOffs(0, 227).addBox(-0.0141f, -44.6423f, -16f, 6, 1, 26, 0f, true)

        outer_roof_19_r1 = ModelMapper(modelDataWrapper)
        outer_roof_19_r1.setPos(1.9859f, -44.6413f, -15.9999f)
        side_8.addChild(outer_roof_19_r1)
        setRotationAngle(outer_roof_19_r1, 0.2618f, 0f, 0f)
        outer_roof_19_r1.texOffs(31, 40).addBox(-2f, 0f, -9f, 6, 0, 9, 0f, true)

        outer_roof_18_r2 = ModelMapper(modelDataWrapper)
        outer_roof_18_r2.setPos(8.9399f, -44.1204f, -15.9999f)
        side_8.addChild(outer_roof_18_r2)
        setRotationAngle(outer_roof_18_r2, 0.2618f, 0f, 0.1745f)
        outer_roof_18_r2.texOffs(15, 82).addBox(-3f, 0f, -9f, 7, 0, 9, 0f, true)

        outer_roof_17_r2 = ModelMapper(modelDataWrapper)
        outer_roof_17_r2.setPos(12.8788f, -43.4259f, -15.9999f)
        side_8.addChild(outer_roof_17_r2)
        setRotationAngle(outer_roof_17_r2, 0.2618f, 0f, 0.5236f)
        outer_roof_17_r2.texOffs(0, 112).addBox(0f, 0f, -9f, 4, 0, 9, 0f, true)

        outer_roof_16_r2 = ModelMapper(modelDataWrapper)
        outer_roof_16_r2.setPos(16.566f, -41.5562f, -15.0338f)
        side_8.addChild(outer_roof_16_r2)
        setRotationAngle(outer_roof_16_r2, 0.2618f, 0f, 1.0472f)
        outer_roof_16_r2.texOffs(25, 103).addBox(0f, 0f, -12f, 8, 0, 11, 0f, true)

        outer_roof_15_r2 = ModelMapper(modelDataWrapper)
        outer_roof_15_r2.setPos(18.3436f, -37.9636f, -17f)
        side_8.addChild(outer_roof_15_r2)
        setRotationAngle(outer_roof_15_r2, 0f, 0.1309f, -0.1107f)
        outer_roof_15_r2.texOffs(94, 86).addBox(0f, 0f, -8f, 0, 6, 8, 0f, true)

        outer_roof_10_r4 = ModelMapper(modelDataWrapper)
        outer_roof_10_r4.setPos(20.9939f, -14.1104f, -4f)
        side_8.addChild(outer_roof_10_r4)
        setRotationAngle(outer_roof_10_r4, 0f, 0f, -0.1107f)
        outer_roof_10_r4.texOffs(233, 137).addBox(-1f, -24f, -13f, 1, 6, 27, 0f, true)

        outer_roof_12_r2 = ModelMapper(modelDataWrapper)
        outer_roof_12_r2.setPos(15.0444f, -42.1768f, -4f)
        side_8.addChild(outer_roof_12_r2)
        setRotationAngle(outer_roof_12_r2, 0f, 0f, 0.5236f)
        outer_roof_12_r2.texOffs(230, 195).addBox(-2.5f, 0f, -12f, 4, 1, 26, 0f, true)

        outer_roof_13_r1 = ModelMapper(modelDataWrapper)
        outer_roof_13_r1.setPos(9.9249f, -43.9477f, -4f)
        side_8.addChild(outer_roof_13_r1)
        setRotationAngle(outer_roof_13_r1, 0f, 0f, 0.1745f)
        outer_roof_13_r1.texOffs(132, 211).addBox(-4f, 0f, -12f, 7, 1, 26, 0f, true)

        outer_roof_11_r3 = ModelMapper(modelDataWrapper)
        outer_roof_11_r3.setPos(17.3427f, -39.6952f, -4f)
        side_8.addChild(outer_roof_11_r3)
        setRotationAngle(outer_roof_11_r3, 0f, 0f, 1.0472f)
        outer_roof_11_r3.texOffs(236, 54).addBox(-2f, -0.1f, -12f, 4, 1, 26, 0f, true)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(7.132f, -13.881f, -33.7767f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, -0.1309f, -0.2182f, 0f)
        headlight_2_r1.texOffs(0, 213).addBox(0.1f, -0.1f, -0.01f, 12, 11, 0, 0f, true)

        headlight_1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1.setPos(-7.2465f, -13.881f, -33.7767f)
        headlights.addChild(headlight_1_r1)
        setRotationAngle(headlight_1_r1, -0.1309f, 0.2182f, 0f)
        headlight_1_r1.texOffs(0, 213).addBox(-12.1f, -0.1f, -0.01f, 12, 11, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_2_r1 = ModelMapper(modelDataWrapper)
        tail_light_2_r1.setPos(7.132f, -13.881f, -33.7767f)
        tail_lights.addChild(tail_light_2_r1)
        setRotationAngle(tail_light_2_r1, -0.1309f, -0.2182f, 0f)
        tail_light_2_r1.texOffs(204, 211).addBox(0f, 0f, -0.01f, 12, 11, 0, 0f, true)

        tail_light_1_r1 = ModelMapper(modelDataWrapper)
        tail_light_1_r1.setPos(-7.2465f, -13.881f, -33.7767f)
        tail_lights.addChild(tail_light_1_r1)
        setRotationAngle(tail_light_1_r1, -0.1309f, 0.2182f, 0f)
        tail_light_1_r1.texOffs(204, 211).addBox(-12f, 0f, -0.01f, 12, 11, 0, 0f, false)

        roof_head = ModelMapper(modelDataWrapper)
        roof_head.setPos(0f, 24f, 0f)


        side_5 = ModelMapper(modelDataWrapper)
        side_5.setPos(0f, 0f, 0f)
        roof_head.addChild(side_5)
        side_5.texOffs(0, 34).addBox(-16.0123f, -32.1399f, -4f, 3, 0, 14, 0f, false)
        side_5.texOffs(22, 28).addBox(-10.1444f, -36.2357f, -4f, 2, 0, 14, 0f, false)
        side_5.texOffs(0, 54).addBox(-6f, -37.25f, -4f, 6, 0, 14, 0f, false)

        inner_roof_5_r2 = ModelMapper(modelDataWrapper)
        inner_roof_5_r2.setPos(-13.0123f, -32.1409f, 26f)
        side_5.addChild(inner_roof_5_r2)
        setRotationAngle(inner_roof_5_r2, 0f, 0f, -0.9599f)
        inner_roof_5_r2.texOffs(12, 103).addBox(0f, 0.001f, -30f, 5, 0, 14, 0f, false)

        side_6 = ModelMapper(modelDataWrapper)
        side_6.setPos(0f, 0f, 0f)
        roof_head.addChild(side_6)
        side_6.texOffs(0, 34).addBox(13.0123f, -32.1399f, -4f, 3, 0, 14, 0f, true)
        side_6.texOffs(22, 28).addBox(8.1444f, -36.2357f, -4f, 2, 0, 14, 0f, true)
        side_6.texOffs(0, 54).addBox(0f, -37.25f, -4f, 6, 0, 14, 0f, true)

        inner_roof_2_r2 = ModelMapper(modelDataWrapper)
        inner_roof_2_r2.setPos(13.0123f, -32.1409f, 26f)
        side_6.addChild(inner_roof_2_r2)
        setRotationAngle(inner_roof_2_r2, 0f, 0f, 0.9599f)
        inner_roof_2_r2.texOffs(12, 103).addBox(-5f, 0.001f, -30f, 5, 0, 14, 0f, true)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-20f, -14f, 0f)
        door_light_on.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.1107f)
        light_r1.texOffs(33, 334).addBox(-1f, -19.25f, 0f, 0, 0, 0, 0.4f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-20f, -14f, 0f)
        door_light_off.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.1107f)
        light_r2.texOffs(29, 334).addBox(-1f, -19.25f, 0f, 0, 0, 0, 0.4f, false)

        end_handrail = ModelMapper(modelDataWrapper)
        end_handrail.setPos(0f, 24f, 0f)
        end_handrail.texOffs(335, 0).addBox(0f, -12f, 0f, 0, 12, 0, 0.2f, false)
        end_handrail.texOffs(332, 13).addBox(0f, -23.2645f, 0.563f, 0, 6, 0, 0.2f, false)
        end_handrail.texOffs(332, 13).addBox(0f, -23.2645f, -0.563f, 0, 6, 0, 0.2f, false)
        end_handrail.texOffs(335, 0).addBox(0f, -38f, 0f, 0, 9, 0, 0.2f, false)

        pole_top_diagonal_1_r2 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_1_r2.setPos(0.2f, -28.8f, 0.2f)
        end_handrail.addChild(pole_top_diagonal_1_r2)
        setRotationAngle(pole_top_diagonal_1_r2, -0.1047f, 0f, 0f)
        pole_top_diagonal_1_r2.texOffs(335, 0).addBox(-0.2f, 0.2069f, -0.2f, 0, 5, 0, 0.2f, false)

        pole_top_diagonal_2_r2 = ModelMapper(modelDataWrapper)
        pole_top_diagonal_2_r2.setPos(0.2f, -28.8f, -0.2f)
        end_handrail.addChild(pole_top_diagonal_2_r2)
        setRotationAngle(pole_top_diagonal_2_r2, 0.1047f, 0f, 0f)
        pole_top_diagonal_2_r2.texOffs(335, 0).addBox(-0.2f, 0.2069f, 0.2f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_1_r2 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_1_r2.setPos(0f, -14.4002f, -0.2819f)
        end_handrail.addChild(pole_bottom_diagonal_1_r2)
        setRotationAngle(pole_bottom_diagonal_1_r2, 0.1047f, 0f, 0f)
        pole_bottom_diagonal_1_r2.texOffs(335, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        pole_bottom_diagonal_2_r2 = ModelMapper(modelDataWrapper)
        pole_bottom_diagonal_2_r2.setPos(0f, -14.4002f, 0.2819f)
        end_handrail.addChild(pole_bottom_diagonal_2_r2)
        setRotationAngle(pole_bottom_diagonal_2_r2, -0.1047f, 0f, 0f)
        pole_bottom_diagonal_2_r2.texOffs(335, 0).addBox(0f, -2.5f, 0f, 0, 5, 0, 0.2f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        door_exterior_1.setModelPart()
        door_left_exterior_1.setModelPart(door_exterior_1.name)
        door_right_exterior_1.setModelPart(door_exterior_1.name)
        door_exterior_2.setModelPart()
        door_left_exterior_2.setModelPart(door_exterior_2.name)
        door_right_exterior_2.setModelPart(door_exterior_2.name)
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        end.setModelPart()
        end_exterior.setModelPart()
        seat.setModelPart()
        door_light.setModelPart()
        side_panel.setModelPart()
        side_panel_translucent.setModelPart()
        roof_window_1.setModelPart()
        roof_window_2.setModelPart()
        roof_light.setModelPart()
        roof_door.setModelPart()
        roof_end.setModelPart()
        roof_exterior.setModelPart()
        roof_end_exterior.setModelPart()
        top_handrail.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        roof_vent.setModelPart()
        roof_head_exterior.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        roof_head.setModelPart()
        door_light_on.setModelPart()
        door_light_off.setModelPart()
        end_handrail.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelRTrain {
        return ModelRTrain(doorAnimationType, renderDoorOverlay)
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
        val isEvenWindow = isEvenWindow(position)
        when (renderStage!!) {
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> {
                if (isEvenWindow) {
                    renderMirror(window, matrices, vertices, light, position.toFloat())
                    renderOnce(roof_window_1, matrices, vertices, light, position.toFloat())
                    renderOnce(roof_window_2, matrices, vertices, light, position.toFloat())
                } else {
                    renderMirror(window, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(roof_window_1, matrices, vertices, light, position.toFloat())
                    renderOnceFlipped(roof_window_2, matrices, vertices, light, position.toFloat())
                }
                if (renderDetails) {
                    renderMirror(top_handrail, matrices, vertices, light, position.toFloat())
                    renderMirror(seat, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position - 20).toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position + 20).toFloat())
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> {
                renderMirror(side_panel_translucent, matrices, vertices, light, (position - 20).toFloat())
                renderMirror(side_panel_translucent, matrices, vertices, light, (position + 20).toFloat())
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
        val notLastDoor = !isIndex(0, position, getDoorPositions()) && !isIndex(-1, position, getDoorPositions())
        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                renderMirror(door_light, matrices, vertices, light, position.toFloat())
                if (notLastDoor) {
                    renderMirror(roof_light, matrices, vertices, light, position.toFloat())
                }
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
                }
            }

            RenderStage.EXTERIOR -> {
                if (isEnd2Head) {
                    door_left_exterior_1.setOffset(0f, 0, doorLeftZ)
                    door_right_exterior_1.setOffset(0f, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_1, matrices, vertices, light, position.toFloat())
                    door_left_exterior_2.setOffset(0f, 0, -doorRightZ)
                    door_right_exterior_2.setOffset(0f, 0, doorRightZ)
                    renderOnceFlipped(door_exterior_2, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior_1.setOffset(0f, 0, doorRightZ)
                    door_right_exterior_1.setOffset(0f, 0, -doorRightZ)
                    renderOnce(door_exterior_1, matrices, vertices, light, position.toFloat())
                    door_left_exterior_2.setOffset(0f, 0, -doorLeftZ)
                    door_right_exterior_2.setOffset(0f, 0, doorLeftZ)
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, (position + 20).toFloat())
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
                    renderOnce(roof_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_head_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_vent, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, (position - 20).toFloat())
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
                    renderOnceFlipped(roof_head, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_head_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_vent, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, (position + 17).toFloat())
            RenderStage.INTERIOR -> {
                renderOnce(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnce(roof_end, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position + 9).toFloat())
                    renderOnce(end_handrail, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(
                side_panel_translucent,
                matrices,
                vertices,
                light,
                (position + 9).toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnce(roof_vent, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderMirror(roof_light, matrices, vertices, light, (position - 17).toFloat())
            RenderStage.INTERIOR -> {
                renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
                if (renderDetails) {
                    renderOnceFlipped(roof_end, matrices, vertices, light, position.toFloat())
                    renderMirror(side_panel, matrices, vertices, light, (position - 9).toFloat())
                    renderOnceFlipped(end_handrail, matrices, vertices, light, position.toFloat())
                }
            }

            RenderStage.INTERIOR_TRANSLUCENT -> renderMirror(
                side_panel_translucent,
                matrices,
                vertices,
                light,
                (position - 9).toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_end_exterior, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(roof_vent, matrices, vertices, light, position.toFloat())
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
            0.79f,
            0f,
            getEndPositions()!![0] / 16f - 2.27f,
            0f,
            -2.25f,
            -0.01f,
            -15f,
            -12.5f,
            0.4f,
            0.14f,
            -0x10000,
            -0x6700,
            1.25f,
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false),
            true,
            car,
            totalCars
        )
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "回廠|Depot"
    }

    private fun isEvenWindow(position: Int): Boolean {
        return isIndex(1, position, getWindowPositions()) || isIndex(3, position, getWindowPositions())
    }

    companion object {
        private const val DOOR_MAX = 14
        private val MODEL_DOOR_OVERLAY =
            ModelDoorOverlay(DOOR_MAX, 6.34f, "door_overlay_r_train_left.png", "door_overlay_r_train_right.png")
        private val MODEL_DOOR_OVERLAY_TOP = ModelDoorOverlayTopSP1900()
    }
}
