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

open class ModelClass377 private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelClass377?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val light_edge_r1: ModelMapper
    private val roof_1_r1: ModelMapper
    private val window_bottom_r1: ModelMapper
    private val window_light: ModelMapper
    private val light_r1: ModelMapper
    private val door_light: ModelMapper
    private val light_r2: ModelMapper
    private val window_1: ModelMapper
    private val window_top_r1: ModelMapper
    private val window_2: ModelMapper
    private val window_top_r2: ModelMapper
    private val window_3: ModelMapper
    private val window_top_r3: ModelMapper
    private val window_4: ModelMapper
    private val window_top_r4: ModelMapper
    private val window_5: ModelMapper
    private val window_top_r5: ModelMapper
    private val window_6: ModelMapper
    private val window_top_r6: ModelMapper
    private val window_7: ModelMapper
    private val window_top_r7: ModelMapper
    private val window_8: ModelMapper
    private val window_top_r8: ModelMapper
    private val window_exterior_1: ModelMapper
    private val window_exterior_1_1: ModelMapper
    private val window_bottom_r2: ModelMapper
    private val window_top_r9: ModelMapper
    private val window_exterior_1_2: ModelMapper
    private val window_bottom_r3: ModelMapper
    private val window_top_r10: ModelMapper
    private val window_exterior_2: ModelMapper
    private val window_exterior_1_3: ModelMapper
    private val window_bottom_r4: ModelMapper
    private val window_top_r11: ModelMapper
    private val window_exterior_1_4: ModelMapper
    private val window_bottom_r5: ModelMapper
    private val window_top_r12: ModelMapper
    private val window_exterior_3: ModelMapper
    private val window_exterior_1_5: ModelMapper
    private val window_bottom_r6: ModelMapper
    private val window_top_r13: ModelMapper
    private val window_exterior_1_6: ModelMapper
    private val window_bottom_r7: ModelMapper
    private val window_top_r14: ModelMapper
    private val window_exterior_4: ModelMapper
    private val window_exterior_1_7: ModelMapper
    private val window_bottom_r8: ModelMapper
    private val window_top_r15: ModelMapper
    private val window_exterior_1_8: ModelMapper
    private val window_bottom_r9: ModelMapper
    private val window_top_r16: ModelMapper
    private val window_exterior_5: ModelMapper
    private val window_exterior_1_9: ModelMapper
    private val window_bottom_r10: ModelMapper
    private val window_top_r17: ModelMapper
    private val window_exterior_1_10: ModelMapper
    private val window_bottom_r11: ModelMapper
    private val window_top_r18: ModelMapper
    private val window_exterior_6: ModelMapper
    private val window_exterior_1_11: ModelMapper
    private val window_bottom_r12: ModelMapper
    private val window_top_r19: ModelMapper
    private val window_exterior_1_12: ModelMapper
    private val window_bottom_r13: ModelMapper
    private val window_top_r20: ModelMapper
    private val window_exterior_7: ModelMapper
    private val window_exterior_1_13: ModelMapper
    private val window_bottom_r14: ModelMapper
    private val window_top_r21: ModelMapper
    private val window_exterior_1_14: ModelMapper
    private val window_bottom_r15: ModelMapper
    private val window_top_r22: ModelMapper
    private val window_exterior_8: ModelMapper
    private val window_exterior_1_15: ModelMapper
    private val window_bottom_r16: ModelMapper
    private val window_top_r23: ModelMapper
    private val window_exterior_1_16: ModelMapper
    private val window_bottom_r17: ModelMapper
    private val window_top_r24: ModelMapper
    private val window_end_exterior_1: ModelMapper
    private val window_end_exterior_1_1: ModelMapper
    private val window_bottom_r18: ModelMapper
    private val window_top_r25: ModelMapper
    private val window_end_exterior_1_2: ModelMapper
    private val window_bottom_r19: ModelMapper
    private val window_top_r26: ModelMapper
    private val window_end_exterior_2: ModelMapper
    private val window_end_exterior_1_3: ModelMapper
    private val window_bottom_r20: ModelMapper
    private val window_top_r27: ModelMapper
    private val window_end_exterior_1_4: ModelMapper
    private val window_bottom_r21: ModelMapper
    private val window_top_r28: ModelMapper
    private val window_end_exterior_3: ModelMapper
    private val window_end_exterior_1_5: ModelMapper
    private val window_bottom_r22: ModelMapper
    private val window_top_r29: ModelMapper
    private val window_end_exterior_1_6: ModelMapper
    private val window_bottom_r23: ModelMapper
    private val window_top_r30: ModelMapper
    private val window_end_exterior_4: ModelMapper
    private val window_end_exterior_1_7: ModelMapper
    private val window_bottom_r24: ModelMapper
    private val window_top_r31: ModelMapper
    private val window_end_exterior_1_8: ModelMapper
    private val window_bottom_r25: ModelMapper
    private val window_top_r32: ModelMapper
    private val roof_exterior_1: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_2_r1: ModelMapper
    private val roof_1_r2: ModelMapper
    private val roof_exterior_door_edge: ModelMapper
    private val roof_exterior_door_edge_1: ModelMapper
    private val roof_4_r1: ModelMapper
    private val roof_3_r2: ModelMapper
    private val roof_2_r2: ModelMapper
    private val roof_exterior_door_edge_2: ModelMapper
    private val roof_5_r1: ModelMapper
    private val roof_4_r2: ModelMapper
    private val roof_3_r3: ModelMapper
    private val roof_exterior_door: ModelMapper
    private val roof_exterior_door_1: ModelMapper
    private val roof_5_r2: ModelMapper
    private val roof_4_r3: ModelMapper
    private val roof_3_r4: ModelMapper
    private val roof_exterior_door_2: ModelMapper
    private val roof_6_r1: ModelMapper
    private val roof_5_r3: ModelMapper
    private val roof_4_r4: ModelMapper
    private val door: ModelMapper
    private val light_edge_r2: ModelMapper
    private val door_top_3_r1: ModelMapper
    private val door_top_2_r1: ModelMapper
    private val window_top_2_r1: ModelMapper
    private val window_bottom_2_r1: ModelMapper
    private val door_left: ModelMapper
    private val door_top_r1: ModelMapper
    private val door_bottom_r1: ModelMapper
    private val door_right: ModelMapper
    private val door_top_r2: ModelMapper
    private val door_bottom_r2: ModelMapper
    private val door_exterior: ModelMapper
    private val window_top_2_r2: ModelMapper
    private val window_bottom_2_r2: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_top_r3: ModelMapper
    private val door_bottom_r3: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_top_r4: ModelMapper
    private val door_bottom_r4: ModelMapper
    private val door_exterior_end: ModelMapper
    private val door_exterior_end_1: ModelMapper
    private val window_top_2_r3: ModelMapper
    private val window_bottom_2_r3: ModelMapper
    private val door_left_exterior_end_1: ModelMapper
    private val door_top_r5: ModelMapper
    private val door_bottom_r5: ModelMapper
    private val door_right_exterior_end_1: ModelMapper
    private val door_top_r6: ModelMapper
    private val door_bottom_r6: ModelMapper
    private val door_exterior_end_2: ModelMapper
    private val window_top_2_r4: ModelMapper
    private val window_bottom_2_r4: ModelMapper
    private val door_left_exterior_end_2: ModelMapper
    private val door_top_r7: ModelMapper
    private val door_bottom_r7: ModelMapper
    private val door_right_exterior_end_2: ModelMapper
    private val door_top_r8: ModelMapper
    private val door_bottom_r8: ModelMapper
    private val end: ModelMapper
    private val end_1: ModelMapper
    private val end_2: ModelMapper
    private val end_exterior: ModelMapper
    private val end_exterior_1: ModelMapper
    private val window_bottom_r26: ModelMapper
    private val window_top_r33: ModelMapper
    private val end_exterior_2: ModelMapper
    private val window_bottom_r27: ModelMapper
    private val window_top_r34: ModelMapper
    private val roof_end_exterior_1: ModelMapper
    private val roof_3_r5: ModelMapper
    private val roof_2_r3: ModelMapper
    private val roof_1_r3: ModelMapper
    private val roof_end_exterior_2: ModelMapper
    private val roof_4_r5: ModelMapper
    private val roof_3_r6: ModelMapper
    private val roof_2_r4: ModelMapper
    private val head: ModelMapper
    private val head_exterior: ModelMapper
    private val front_r1: ModelMapper
    private val head_exterior_1: ModelMapper
    private val front_bottom_r1: ModelMapper
    private val roof_5_r4: ModelMapper
    private val roof_4_r6: ModelMapper
    private val roof_3_r7: ModelMapper
    private val roof_2_r5: ModelMapper
    private val window_bottom_r28: ModelMapper
    private val window_top_r35: ModelMapper
    private val head_exterior_2: ModelMapper
    private val front_bottom_r2: ModelMapper
    private val roof_6_r2: ModelMapper
    private val roof_5_r5: ModelMapper
    private val roof_4_r7: ModelMapper
    private val roof_3_r8: ModelMapper
    private val window_bottom_r29: ModelMapper
    private val window_top_r36: ModelMapper
    private val seat: ModelMapper
    private val seat_2_r1: ModelMapper
    private val door_light_box: ModelMapper
    private val door_light_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val door_light_r2: ModelMapper
    private val door_light_on: ModelMapper
    private val door_light_r3: ModelMapper
    private val headlights: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_2_r1: ModelMapper

    constructor() : this(DoorAnimationType.PLUG_FAST, true)

    init {
        val textureWidth = 368
        val textureHeight = 368

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(0, 78).addBox(-19f, 0f, -12.5f, 19, 1, 25, 0f, false)
        window.texOffs(208, 221).addBox(-20f, -13f, -12.5f, 0, 6, 25, 0f, false)
        window.texOffs(133, 277).addBox(-18f, -31f, -12.5f, 2, 4, 25, 0f, false)
        window.texOffs(208, 91).addBox(-18f, -32f, -12.5f, 6, 1, 25, 0f, false)
        window.texOffs(183, 117).addBox(-5f, -35f, -12.5f, 5, 0, 25, 0f, false)

        light_edge_r1 = ModelMapper(modelDataWrapper)
        light_edge_r1.setPos(-5f, -35f, 0f)
        window.addChild(light_edge_r1)
        setRotationAngle(light_edge_r1, 0f, 0f, 0.2182f)
        light_edge_r1.texOffs(292, 120).addBox(-5f, 0f, -12.5f, 1, 1, 25, 0f, false)
        light_edge_r1.texOffs(38, 78).addBox(-9f, 0f, -12.5f, 9, 0, 25, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(-18f, -32f, 0f)
        window.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, -0.7854f)
        roof_1_r1.texOffs(181, 173).addBox(0f, 0f, -12.5f, 7, 0, 25, 0f, false)

        window_bottom_r1 = ModelMapper(modelDataWrapper)
        window_bottom_r1.setPos(-21f, -7f, 0f)
        window.addChild(window_bottom_r1)
        setRotationAngle(window_bottom_r1, 0f, 0f, -0.2094f)
        window_bottom_r1.texOffs(208, 206).addBox(1f, 0f, -12.5f, 0, 8, 25, 0f, false)

        window_light = ModelMapper(modelDataWrapper)
        window_light.setPos(0f, 24f, 0f)
        window_light.texOffs(183, 142).addBox(-4f, -35.2f, -12.5f, 4, 0, 25, 0f, false)

        light_r1 = ModelMapper(modelDataWrapper)
        light_r1.setPos(-5f, -35f, 0f)
        window_light.addChild(light_r1)
        setRotationAngle(light_r1, 0f, 0f, 0.2182f)
        light_r1.texOffs(287, 238).addBox(-5.5f, -0.2f, -12.5f, 2, 1, 25, 0f, false)

        door_light = ModelMapper(modelDataWrapper)
        door_light.setPos(0f, 24f, 0f)
        door_light.texOffs(172, 116).addBox(-4f, -35.2f, -14f, 4, 0, 28, 0f, false)

        light_r2 = ModelMapper(modelDataWrapper)
        light_r2.setPos(-5f, -35f, 0f)
        door_light.addChild(light_r2)
        setRotationAngle(light_r2, 0f, 0f, 0.2182f)
        light_r2.texOffs(206, 173).addBox(-5.5f, -0.2f, -14f, 2, 1, 28, 0f, false)

        window_1 = ModelMapper(modelDataWrapper)
        window_1.setPos(0f, 24f, 0f)


        window_top_r1 = ModelMapper(modelDataWrapper)
        window_top_r1.setPos(-21f, -13f, 0f)
        window_1.addChild(window_top_r1)
        setRotationAngle(window_top_r1, 0f, 0f, 0.1396f)
        window_top_r1.texOffs(100, 107).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_2 = ModelMapper(modelDataWrapper)
        window_2.setPos(0f, 24f, 0f)


        window_top_r2 = ModelMapper(modelDataWrapper)
        window_top_r2.setPos(-21f, -13f, 0f)
        window_2.addChild(window_top_r2)
        setRotationAngle(window_top_r2, 0f, 0f, 0.1396f)
        window_top_r2.texOffs(50, 107).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_3 = ModelMapper(modelDataWrapper)
        window_3.setPos(0f, 24f, 0f)


        window_top_r3 = ModelMapper(modelDataWrapper)
        window_top_r3.setPos(-21f, -13f, 0f)
        window_3.addChild(window_top_r3)
        setRotationAngle(window_top_r3, 0f, 0f, 0.1396f)
        window_top_r3.texOffs(100, 86).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_4 = ModelMapper(modelDataWrapper)
        window_4.setPos(0f, 24f, 0f)


        window_top_r4 = ModelMapper(modelDataWrapper)
        window_top_r4.setPos(-21f, -13f, 0f)
        window_4.addChild(window_top_r4)
        setRotationAngle(window_top_r4, 0f, 0f, 0.1396f)
        window_top_r4.texOffs(0, 100).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_5 = ModelMapper(modelDataWrapper)
        window_5.setPos(0f, 24f, 0f)


        window_top_r5 = ModelMapper(modelDataWrapper)
        window_top_r5.setPos(-21f, -13f, 0f)
        window_5.addChild(window_top_r5)
        setRotationAngle(window_top_r5, 0f, 0f, 0.1396f)
        window_top_r5.texOffs(98, 53).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_6 = ModelMapper(modelDataWrapper)
        window_6.setPos(0f, 24f, 0f)


        window_top_r6 = ModelMapper(modelDataWrapper)
        window_top_r6.setPos(-21f, -13f, 0f)
        window_6.addChild(window_top_r6)
        setRotationAngle(window_top_r6, 0f, 0f, 0.1396f)
        window_top_r6.texOffs(88, 3).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_7 = ModelMapper(modelDataWrapper)
        window_7.setPos(0f, 24f, 0f)


        window_top_r7 = ModelMapper(modelDataWrapper)
        window_top_r7.setPos(-21f, -13f, 0f)
        window_7.addChild(window_top_r7)
        setRotationAngle(window_top_r7, 0f, 0f, 0.1396f)
        window_top_r7.texOffs(50, 86).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_8 = ModelMapper(modelDataWrapper)
        window_8.setPos(0f, 24f, 0f)


        window_top_r8 = ModelMapper(modelDataWrapper)
        window_top_r8.setPos(-21f, -13f, 0f)
        window_8.addChild(window_top_r8)
        setRotationAngle(window_top_r8, 0f, 0f, 0.1396f)
        window_top_r8.texOffs(0, 79).addBox(1f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)


        window_exterior_1_1 = ModelMapper(modelDataWrapper)
        window_exterior_1_1.setPos(0f, 0f, 0f)
        window_exterior_1.addChild(window_exterior_1_1)
        window_exterior_1_1.texOffs(286, 89).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r2 = ModelMapper(modelDataWrapper)
        window_bottom_r2.setPos(-21f, -7f, 0f)
        window_exterior_1_1.addChild(window_bottom_r2)
        setRotationAngle(window_bottom_r2, 0f, 0f, -0.2094f)
        window_bottom_r2.texOffs(272, 33).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r9 = ModelMapper(modelDataWrapper)
        window_top_r9.setPos(-21f, -13f, 0f)
        window_exterior_1_1.addChild(window_top_r9)
        setRotationAngle(window_top_r9, 0f, 0f, 0.1396f)
        window_top_r9.texOffs(198, 42).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_2 = ModelMapper(modelDataWrapper)
        window_exterior_1_2.setPos(0f, 0f, 0f)
        window_exterior_1.addChild(window_exterior_1_2)
        window_exterior_1_2.texOffs(286, 83).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r3 = ModelMapper(modelDataWrapper)
        window_bottom_r3.setPos(21f, -7f, 0f)
        window_exterior_1_2.addChild(window_bottom_r3)
        setRotationAngle(window_bottom_r3, 0f, 0f, 0.2094f)
        window_bottom_r3.texOffs(52, 270).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r10 = ModelMapper(modelDataWrapper)
        window_top_r10.setPos(21f, -13f, 0f)
        window_exterior_1_2.addChild(window_top_r10)
        setRotationAngle(window_top_r10, 0f, 0f, -0.1396f)
        window_top_r10.texOffs(100, 191).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)


        window_exterior_1_3 = ModelMapper(modelDataWrapper)
        window_exterior_1_3.setPos(0f, 0f, 0f)
        window_exterior_2.addChild(window_exterior_1_3)
        window_exterior_1_3.texOffs(38, 278).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r4 = ModelMapper(modelDataWrapper)
        window_bottom_r4.setPos(-21f, -7f, 0f)
        window_exterior_1_3.addChild(window_bottom_r4)
        setRotationAngle(window_bottom_r4, 0f, 0f, -0.2094f)
        window_bottom_r4.texOffs(259, 96).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r11 = ModelMapper(modelDataWrapper)
        window_top_r11.setPos(-21f, -13f, 0f)
        window_exterior_1_3.addChild(window_top_r11)
        setRotationAngle(window_top_r11, 0f, 0f, 0.1396f)
        window_top_r11.texOffs(150, 158).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_4 = ModelMapper(modelDataWrapper)
        window_exterior_1_4.setPos(0f, 0f, 0f)
        window_exterior_2.addChild(window_exterior_1_4)
        window_exterior_1_4.texOffs(187, 272).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r5 = ModelMapper(modelDataWrapper)
        window_bottom_r5.setPos(21f, -7f, 0f)
        window_exterior_1_4.addChild(window_bottom_r5)
        setRotationAngle(window_bottom_r5, 0f, 0f, 0.2094f)
        window_bottom_r5.texOffs(253, 0).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r12 = ModelMapper(modelDataWrapper)
        window_top_r12.setPos(21f, -13f, 0f)
        window_exterior_1_4.addChild(window_top_r12)
        setRotationAngle(window_top_r12, 0f, 0f, -0.1396f)
        window_top_r12.texOffs(150, 137).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_3 = ModelMapper(modelDataWrapper)
        window_exterior_3.setPos(0f, 24f, 0f)


        window_exterior_1_5 = ModelMapper(modelDataWrapper)
        window_exterior_1_5.setPos(0f, 0f, 0f)
        window_exterior_3.addChild(window_exterior_1_5)
        window_exterior_1_5.texOffs(272, 53).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r6 = ModelMapper(modelDataWrapper)
        window_bottom_r6.setPos(-21f, -7f, 0f)
        window_exterior_1_5.addChild(window_bottom_r6)
        setRotationAngle(window_bottom_r6, 0f, 0f, -0.2094f)
        window_bottom_r6.texOffs(79, 245).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r13 = ModelMapper(modelDataWrapper)
        window_top_r13.setPos(-21f, -13f, 0f)
        window_exterior_1_5.addChild(window_top_r13)
        setRotationAngle(window_top_r13, 0f, 0f, 0.1396f)
        window_top_r13.texOffs(150, 116).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_6 = ModelMapper(modelDataWrapper)
        window_exterior_1_6.setPos(0f, 0f, 0f)
        window_exterior_3.addChild(window_exterior_1_6)
        window_exterior_1_6.texOffs(272, 47).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r7 = ModelMapper(modelDataWrapper)
        window_bottom_r7.setPos(21f, -7f, 0f)
        window_exterior_1_6.addChild(window_bottom_r7)
        setRotationAngle(window_bottom_r7, 0f, 0f, 0.2094f)
        window_bottom_r7.texOffs(245, 63).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r14 = ModelMapper(modelDataWrapper)
        window_top_r14.setPos(21f, -13f, 0f)
        window_exterior_1_6.addChild(window_top_r14)
        setRotationAngle(window_top_r14, 0f, 0f, -0.1396f)
        window_top_r14.texOffs(150, 95).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_4 = ModelMapper(modelDataWrapper)
        window_exterior_4.setPos(0f, 24f, 0f)


        window_exterior_1_7 = ModelMapper(modelDataWrapper)
        window_exterior_1_7.setPos(0f, 0f, 0f)
        window_exterior_4.addChild(window_exterior_1_7)
        window_exterior_1_7.texOffs(272, 41).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r8 = ModelMapper(modelDataWrapper)
        window_bottom_r8.setPos(-21f, -7f, 0f)
        window_exterior_1_7.addChild(window_bottom_r8)
        setRotationAngle(window_bottom_r8, 0f, 0f, -0.2094f)
        window_bottom_r8.texOffs(152, 244).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r15 = ModelMapper(modelDataWrapper)
        window_top_r15.setPos(-21f, -13f, 0f)
        window_exterior_1_7.addChild(window_top_r15)
        setRotationAngle(window_top_r15, 0f, 0f, 0.1396f)
        window_top_r15.texOffs(150, 74).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_8 = ModelMapper(modelDataWrapper)
        window_exterior_1_8.setPos(0f, 0f, 0f)
        window_exterior_4.addChild(window_exterior_1_8)
        window_exterior_1_8.texOffs(79, 259).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r9 = ModelMapper(modelDataWrapper)
        window_bottom_r9.setPos(21f, -7f, 0f)
        window_exterior_1_8.addChild(window_bottom_r9)
        setRotationAngle(window_bottom_r9, 0f, 0f, 0.2094f)
        window_bottom_r9.texOffs(241, 183).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r16 = ModelMapper(modelDataWrapper)
        window_top_r16.setPos(21f, -13f, 0f)
        window_exterior_1_8.addChild(window_top_r16)
        setRotationAngle(window_top_r16, 0f, 0f, -0.1396f)
        window_top_r16.texOffs(100, 149).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_5 = ModelMapper(modelDataWrapper)
        window_exterior_5.setPos(0f, 24f, 0f)


        window_exterior_1_9 = ModelMapper(modelDataWrapper)
        window_exterior_1_9.setPos(0f, 0f, 0f)
        window_exterior_5.addChild(window_exterior_1_9)
        window_exterior_1_9.texOffs(206, 257).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r10 = ModelMapper(modelDataWrapper)
        window_bottom_r10.setPos(-21f, -7f, 0f)
        window_exterior_1_9.addChild(window_bottom_r10)
        setRotationAngle(window_bottom_r10, 0f, 0f, -0.2094f)
        window_bottom_r10.texOffs(238, 150).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r17 = ModelMapper(modelDataWrapper)
        window_top_r17.setPos(-21f, -13f, 0f)
        window_exterior_1_9.addChild(window_top_r17)
        setRotationAngle(window_top_r17, 0f, 0f, 0.1396f)
        window_top_r17.texOffs(50, 149).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_10 = ModelMapper(modelDataWrapper)
        window_exterior_1_10.setPos(0f, 0f, 0f)
        window_exterior_5.addChild(window_exterior_1_10)
        window_exterior_1_10.texOffs(27, 257).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r11 = ModelMapper(modelDataWrapper)
        window_bottom_r11.setPos(21f, -7f, 0f)
        window_exterior_1_10.addChild(window_bottom_r11)
        setRotationAngle(window_bottom_r11, 0f, 0f, 0.2094f)
        window_bottom_r11.texOffs(52, 237).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r18 = ModelMapper(modelDataWrapper)
        window_top_r18.setPos(21f, -13f, 0f)
        window_exterior_1_10.addChild(window_top_r18)
        setRotationAngle(window_top_r18, 0f, 0f, -0.1396f)
        window_top_r18.texOffs(148, 53).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_6 = ModelMapper(modelDataWrapper)
        window_exterior_6.setPos(0f, 24f, 0f)


        window_exterior_1_11 = ModelMapper(modelDataWrapper)
        window_exterior_1_11.setPos(0f, 0f, 0f)
        window_exterior_6.addChild(window_exterior_1_11)
        window_exterior_1_11.texOffs(79, 253).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r12 = ModelMapper(modelDataWrapper)
        window_bottom_r12.setPos(-21f, -7f, 0f)
        window_exterior_1_11.addChild(window_bottom_r12)
        setRotationAngle(window_bottom_r12, 0f, 0f, -0.2094f)
        window_bottom_r12.texOffs(125, 236).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r19 = ModelMapper(modelDataWrapper)
        window_top_r19.setPos(-21f, -13f, 0f)
        window_exterior_1_11.addChild(window_top_r19)
        setRotationAngle(window_top_r19, 0f, 0f, 0.1396f)
        window_top_r19.texOffs(0, 142).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_12 = ModelMapper(modelDataWrapper)
        window_exterior_1_12.setPos(0f, 0f, 0f)
        window_exterior_6.addChild(window_exterior_1_12)
        window_exterior_1_12.texOffs(206, 251).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r13 = ModelMapper(modelDataWrapper)
        window_bottom_r13.setPos(21f, -7f, 0f)
        window_exterior_1_12.addChild(window_bottom_r13)
        setRotationAngle(window_bottom_r13, 0f, 0f, 0.2094f)
        window_bottom_r13.texOffs(233, 231).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r20 = ModelMapper(modelDataWrapper)
        window_top_r20.setPos(21f, -13f, 0f)
        window_exterior_1_12.addChild(window_top_r20)
        setRotationAngle(window_top_r20, 0f, 0f, -0.1396f)
        window_top_r20.texOffs(138, 0).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_7 = ModelMapper(modelDataWrapper)
        window_exterior_7.setPos(0f, 24f, 0f)


        window_exterior_1_13 = ModelMapper(modelDataWrapper)
        window_exterior_1_13.setPos(0f, 0f, 0f)
        window_exterior_7.addChild(window_exterior_1_13)
        window_exterior_1_13.texOffs(27, 251).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r14 = ModelMapper(modelDataWrapper)
        window_bottom_r14.setPos(-21f, -7f, 0f)
        window_exterior_1_13.addChild(window_bottom_r14)
        setRotationAngle(window_bottom_r14, 0f, 0f, -0.2094f)
        window_bottom_r14.texOffs(232, 117).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r21 = ModelMapper(modelDataWrapper)
        window_top_r21.setPos(-21f, -13f, 0f)
        window_exterior_1_13.addChild(window_top_r21)
        setRotationAngle(window_top_r21, 0f, 0f, 0.1396f)
        window_top_r21.texOffs(100, 128).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_14 = ModelMapper(modelDataWrapper)
        window_exterior_1_14.setPos(0f, 0f, 0f)
        window_exterior_7.addChild(window_exterior_1_14)
        window_exterior_1_14.texOffs(206, 245).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r15 = ModelMapper(modelDataWrapper)
        window_bottom_r15.setPos(21f, -7f, 0f)
        window_exterior_1_14.addChild(window_bottom_r15)
        setRotationAngle(window_bottom_r15, 0f, 0f, 0.2094f)
        window_bottom_r15.texOffs(181, 231).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r22 = ModelMapper(modelDataWrapper)
        window_top_r22.setPos(21f, -13f, 0f)
        window_exterior_1_14.addChild(window_top_r22)
        setRotationAngle(window_top_r22, 0f, 0f, -0.1396f)
        window_top_r22.texOffs(50, 128).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_exterior_8 = ModelMapper(modelDataWrapper)
        window_exterior_8.setPos(0f, 24f, 0f)


        window_exterior_1_15 = ModelMapper(modelDataWrapper)
        window_exterior_1_15.setPos(0f, 0f, 0f)
        window_exterior_8.addChild(window_exterior_1_15)
        window_exterior_1_15.texOffs(27, 245).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r16 = ModelMapper(modelDataWrapper)
        window_bottom_r16.setPos(-21f, -7f, 0f)
        window_exterior_1_15.addChild(window_bottom_r16)
        setRotationAngle(window_bottom_r16, 0f, 0f, -0.2094f)
        window_bottom_r16.texOffs(0, 230).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r23 = ModelMapper(modelDataWrapper)
        window_top_r23.setPos(-21f, -13f, 0f)
        window_exterior_1_15.addChild(window_top_r23)
        setRotationAngle(window_top_r23, 0f, 0f, 0.1396f)
        window_top_r23.texOffs(126, 24).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_exterior_1_16 = ModelMapper(modelDataWrapper)
        window_exterior_1_16.setPos(0f, 0f, 0f)
        window_exterior_8.addChild(window_exterior_1_16)
        window_exterior_1_16.texOffs(206, 239).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r17 = ModelMapper(modelDataWrapper)
        window_bottom_r17.setPos(21f, -7f, 0f)
        window_exterior_1_16.addChild(window_bottom_r17)
        setRotationAngle(window_bottom_r17, 0f, 0f, 0.2094f)
        window_bottom_r17.texOffs(226, 21).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r24 = ModelMapper(modelDataWrapper)
        window_top_r24.setPos(21f, -13f, 0f)
        window_exterior_1_16.addChild(window_top_r24)
        setRotationAngle(window_top_r24, 0f, 0f, -0.1396f)
        window_top_r24.texOffs(0, 121).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_end_exterior_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_1.setPos(0f, 24f, 0f)


        window_end_exterior_1_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_1.setPos(0f, 0f, 0f)
        window_end_exterior_1.addChild(window_end_exterior_1_1)
        window_end_exterior_1_1.texOffs(286, 77).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r18 = ModelMapper(modelDataWrapper)
        window_bottom_r18.setPos(-21f, -7f, 0f)
        window_end_exterior_1_1.addChild(window_bottom_r18)
        setRotationAngle(window_bottom_r18, 0f, 0f, -0.2094f)
        window_bottom_r18.texOffs(106, 269).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r25 = ModelMapper(modelDataWrapper)
        window_top_r25.setPos(-21f, -13f, 0f)
        window_end_exterior_1_1.addChild(window_top_r25)
        setRotationAngle(window_top_r25, 0f, 0f, 0.1396f)
        window_top_r25.texOffs(50, 191).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_end_exterior_1_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_2.setPos(0f, 0f, 0f)
        window_end_exterior_1.addChild(window_end_exterior_1_2)
        window_end_exterior_1_2.texOffs(286, 71).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r19 = ModelMapper(modelDataWrapper)
        window_bottom_r19.setPos(21f, -7f, 0f)
        window_end_exterior_1_2.addChild(window_bottom_r19)
        setRotationAngle(window_bottom_r19, 0f, 0f, 0.2094f)
        window_bottom_r19.texOffs(268, 162).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r26 = ModelMapper(modelDataWrapper)
        window_top_r26.setPos(21f, -13f, 0f)
        window_end_exterior_1_2.addChild(window_top_r26)
        setRotationAngle(window_top_r26, 0f, 0f, -0.1396f)
        window_top_r26.texOffs(188, 0).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_end_exterior_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_2.setPos(0f, 24f, 0f)


        window_end_exterior_1_3 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_3.setPos(0f, 0f, 0f)
        window_end_exterior_2.addChild(window_end_exterior_1_3)
        window_end_exterior_1_3.texOffs(88, 285).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r20 = ModelMapper(modelDataWrapper)
        window_bottom_r20.setPos(-21f, -7f, 0f)
        window_end_exterior_1_3.addChild(window_bottom_r20)
        setRotationAngle(window_bottom_r20, 0f, 0f, -0.2094f)
        window_bottom_r20.texOffs(265, 129).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r27 = ModelMapper(modelDataWrapper)
        window_top_r27.setPos(-21f, -13f, 0f)
        window_end_exterior_1_3.addChild(window_top_r27)
        setRotationAngle(window_top_r27, 0f, 0f, 0.1396f)
        window_top_r27.texOffs(0, 184).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_end_exterior_1_4 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_4.setPos(0f, 0f, 0f)
        window_end_exterior_2.addChild(window_end_exterior_1_4)
        window_end_exterior_1_4.texOffs(188, 284).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r21 = ModelMapper(modelDataWrapper)
        window_bottom_r21.setPos(21f, -7f, 0f)
        window_end_exterior_1_4.addChild(window_bottom_r21)
        setRotationAngle(window_bottom_r21, 0f, 0f, 0.2094f)
        window_bottom_r21.texOffs(231, 264).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r28 = ModelMapper(modelDataWrapper)
        window_top_r28.setPos(21f, -13f, 0f)
        window_end_exterior_1_4.addChild(window_top_r28)
        setRotationAngle(window_top_r28, 0f, 0f, -0.1396f)
        window_top_r28.texOffs(150, 179).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_end_exterior_3 = ModelMapper(modelDataWrapper)
        window_end_exterior_3.setPos(0f, 24f, 0f)


        window_end_exterior_1_5 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_5.setPos(0f, 0f, 0f)
        window_end_exterior_3.addChild(window_end_exterior_1_5)
        window_end_exterior_1_5.texOffs(138, 284).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r22 = ModelMapper(modelDataWrapper)
        window_bottom_r22.setPos(-21f, -7f, 0f)
        window_end_exterior_1_5.addChild(window_bottom_r22)
        setRotationAngle(window_bottom_r22, 0f, 0f, -0.2094f)
        window_bottom_r22.texOffs(179, 264).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r29 = ModelMapper(modelDataWrapper)
        window_top_r29.setPos(-21f, -13f, 0f)
        window_end_exterior_1_5.addChild(window_top_r29)
        setRotationAngle(window_top_r29, 0f, 0f, 0.1396f)
        window_top_r29.texOffs(176, 21).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_end_exterior_1_6 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_6.setPos(0f, 0f, 0f)
        window_end_exterior_3.addChild(window_end_exterior_1_6)
        window_end_exterior_1_6.texOffs(38, 284).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r23 = ModelMapper(modelDataWrapper)
        window_bottom_r23.setPos(21f, -7f, 0f)
        window_end_exterior_1_6.addChild(window_bottom_r23)
        setRotationAngle(window_bottom_r23, 0f, 0f, 0.2094f)
        window_bottom_r23.texOffs(0, 263).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r30 = ModelMapper(modelDataWrapper)
        window_top_r30.setPos(21f, -13f, 0f)
        window_end_exterior_1_6.addChild(window_top_r30)
        setRotationAngle(window_top_r30, 0f, 0f, -0.1396f)
        window_top_r30.texOffs(100, 170).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        window_end_exterior_4 = ModelMapper(modelDataWrapper)
        window_end_exterior_4.setPos(0f, 24f, 0f)


        window_end_exterior_1_7 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_7.setPos(0f, 0f, 0f)
        window_end_exterior_4.addChild(window_end_exterior_1_7)
        window_end_exterior_1_7.texOffs(88, 279).addBox(-21f, -13f, -12.5f, 0, 6, 25, 0f, false)

        window_bottom_r24 = ModelMapper(modelDataWrapper)
        window_bottom_r24.setPos(-21f, -7f, 0f)
        window_end_exterior_1_7.addChild(window_bottom_r24)
        setRotationAngle(window_bottom_r24, 0f, 0f, -0.2094f)
        window_bottom_r24.texOffs(260, 249).addBox(0f, 0f, -12.5f, 1, 8, 25, 0f, false)

        window_top_r31 = ModelMapper(modelDataWrapper)
        window_top_r31.setPos(-21f, -13f, 0f)
        window_end_exterior_1_7.addChild(window_top_r31)
        setRotationAngle(window_top_r31, 0f, 0f, 0.1396f)
        window_top_r31.texOffs(50, 170).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, false)

        window_end_exterior_1_8 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_8.setPos(0f, 0f, 0f)
        window_end_exterior_4.addChild(window_end_exterior_1_8)
        window_end_exterior_1_8.texOffs(187, 278).addBox(21f, -13f, -12.5f, 0, 6, 25, 0f, true)

        window_bottom_r25 = ModelMapper(modelDataWrapper)
        window_bottom_r25.setPos(21f, -7f, 0f)
        window_end_exterior_1_8.addChild(window_bottom_r25)
        setRotationAngle(window_bottom_r25, 0f, 0f, 0.2094f)
        window_bottom_r25.texOffs(260, 216).addBox(-1f, 0f, -12.5f, 1, 8, 25, 0f, true)

        window_top_r32 = ModelMapper(modelDataWrapper)
        window_top_r32.setPos(21f, -13f, 0f)
        window_end_exterior_1_8.addChild(window_top_r32)
        setRotationAngle(window_top_r32, 0f, 0f, -0.1396f)
        window_top_r32.texOffs(0, 163).addBox(0f, -21f, -12.5f, 0, 21, 25, 0f, true)

        roof_exterior_1 = ModelMapper(modelDataWrapper)
        roof_exterior_1.setPos(0f, 24f, 0f)
        roof_exterior_1.texOffs(193, 88).addBox(-3.8369f, -40.0424f, -12.5f, 4, 0, 25, 0f, false)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(-7.2836f, -39.4346f, 0f)
        roof_exterior_1.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 1.3963f)
        roof_3_r1.texOffs(208, 214).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(-13.7612f, -37.077f, 0f)
        roof_exterior_1.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, 1.0472f)
        roof_2_r1.texOffs(27, 219).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(-16.792f, -35.3272f, 0f)
        roof_exterior_1.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, 0.6981f)
        roof_1_r2.texOffs(226, 31).addBox(0f, 0f, -12.5f, 0, 2, 25, 0f, false)

        roof_exterior_door_edge = ModelMapper(modelDataWrapper)
        roof_exterior_door_edge.setPos(0f, 24f, 0f)


        roof_exterior_door_edge_1 = ModelMapper(modelDataWrapper)
        roof_exterior_door_edge_1.setPos(0f, 0f, 0f)
        roof_exterior_door_edge.addChild(roof_exterior_door_edge_1)
        roof_exterior_door_edge_1.texOffs(191, 142).addBox(-3.8369f, -40.0424f, -12.5f, 4, 0, 25, 0f, false)

        roof_4_r1 = ModelMapper(modelDataWrapper)
        roof_4_r1.setPos(-7.2836f, -39.4346f, 0f)
        roof_exterior_door_edge_1.addChild(roof_4_r1)
        setRotationAngle(roof_4_r1, 0f, 0f, 1.3963f)
        roof_4_r1.texOffs(27, 212).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, false)

        roof_3_r2 = ModelMapper(modelDataWrapper)
        roof_3_r2.setPos(-13.7612f, -37.077f, 0f)
        roof_exterior_door_edge_1.addChild(roof_3_r2)
        setRotationAngle(roof_3_r2, 0f, 0f, 1.0472f)
        roof_3_r2.texOffs(79, 212).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, false)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(-16.792f, -35.3272f, 0f)
        roof_exterior_door_edge_1.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, 0.6981f)
        roof_2_r2.texOffs(226, 29).addBox(0f, 0f, -12.5f, 0, 2, 25, 0f, false)

        roof_exterior_door_edge_2 = ModelMapper(modelDataWrapper)
        roof_exterior_door_edge_2.setPos(0f, 0f, 0f)
        roof_exterior_door_edge.addChild(roof_exterior_door_edge_2)
        roof_exterior_door_edge_2.texOffs(185, 88).addBox(-0.1631f, -40.0424f, -12.5f, 4, 0, 25, 0f, true)

        roof_5_r1 = ModelMapper(modelDataWrapper)
        roof_5_r1.setPos(7.2836f, -39.4346f, 0f)
        roof_exterior_door_edge_2.addChild(roof_5_r1)
        setRotationAngle(roof_5_r1, 0f, 0f, -1.3963f)
        roof_5_r1.texOffs(98, 74).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, true)

        roof_4_r2 = ModelMapper(modelDataWrapper)
        roof_4_r2.setPos(13.7612f, -37.077f, 0f)
        roof_exterior_door_edge_2.addChild(roof_4_r2)
        setRotationAngle(roof_4_r2, 0f, 0f, -1.0472f)
        roof_4_r2.texOffs(152, 211).addBox(0f, -3.5f, -12.5f, 0, 7, 25, 0f, true)

        roof_3_r3 = ModelMapper(modelDataWrapper)
        roof_3_r3.setPos(16.792f, -35.3272f, 0f)
        roof_exterior_door_edge_2.addChild(roof_3_r3)
        setRotationAngle(roof_3_r3, 0f, 0f, -0.6981f)
        roof_3_r3.texOffs(27, 226).addBox(0f, 0f, -12.5f, 0, 2, 25, 0f, true)

        roof_exterior_door = ModelMapper(modelDataWrapper)
        roof_exterior_door.setPos(0f, 24f, 0f)


        roof_exterior_door_1 = ModelMapper(modelDataWrapper)
        roof_exterior_door_1.setPos(0f, 0f, 0f)
        roof_exterior_door.addChild(roof_exterior_door_1)
        roof_exterior_door_1.texOffs(172, 144).addBox(-3.8369f, -40.0424f, -14f, 4, 0, 28, 0f, false)

        roof_5_r2 = ModelMapper(modelDataWrapper)
        roof_5_r2.setPos(-7.2836f, -39.4346f, 0f)
        roof_exterior_door_1.addChild(roof_5_r2)
        setRotationAngle(roof_5_r2, 0f, 0f, 1.3963f)
        roof_5_r2.texOffs(126, 42).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, false)

        roof_4_r3 = ModelMapper(modelDataWrapper)
        roof_4_r3.setPos(-13.7612f, -37.077f, 0f)
        roof_exterior_door_1.addChild(roof_4_r3)
        setRotationAngle(roof_4_r3, 0f, 0f, 1.0472f)
        roof_4_r3.texOffs(150, 199).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, false)

        roof_3_r4 = ModelMapper(modelDataWrapper)
        roof_3_r4.setPos(-16.792f, -35.3272f, 0f)
        roof_exterior_door_1.addChild(roof_3_r4)
        setRotationAngle(roof_3_r4, 0f, 0f, 0.6981f)
        roof_3_r4.texOffs(150, 206).addBox(0f, 0f, -14f, 0, 2, 28, 0f, false)

        roof_exterior_door_2 = ModelMapper(modelDataWrapper)
        roof_exterior_door_2.setPos(0f, 0f, 0f)
        roof_exterior_door.addChild(roof_exterior_door_2)
        roof_exterior_door_2.texOffs(246, 297).addBox(-0.1631f, -40.0424f, -14f, 4, 0, 28, 0f, true)

        roof_6_r1 = ModelMapper(modelDataWrapper)
        roof_6_r1.setPos(7.2836f, -39.4346f, 0f)
        roof_exterior_door_2.addChild(roof_6_r1)
        setRotationAngle(roof_6_r1, 0f, 0f, -1.3963f)
        roof_6_r1.texOffs(208, 322).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, true)

        roof_5_r3 = ModelMapper(modelDataWrapper)
        roof_5_r3.setPos(13.7612f, -37.077f, 0f)
        roof_exterior_door_2.addChild(roof_5_r3)
        setRotationAngle(roof_5_r3, 0f, 0f, -1.0472f)
        roof_5_r3.texOffs(208, 313).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, true)

        roof_4_r4 = ModelMapper(modelDataWrapper)
        roof_4_r4.setPos(16.792f, -35.3272f, 0f)
        roof_exterior_door_2.addChild(roof_4_r4)
        setRotationAngle(roof_4_r4, 0f, 0f, -0.6981f)
        roof_4_r4.texOffs(208, 320).addBox(0f, 0f, -14f, 0, 2, 28, 0f, true)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(60, 49).addBox(-19f, 0f, -14f, 19, 1, 28, 0f, false)
        door.texOffs(275, 33).addBox(-20f, -13f, 11f, 1, 6, 1, 0f, false)
        door.texOffs(279, 33).addBox(-20f, -13f, -12f, 1, 6, 1, 0f, false)
        door.texOffs(82, 316).addBox(-20f, -32f, 11.05f, 6, 32, 3, 0f, false)
        door.texOffs(190, 315).addBox(-20f, -32f, -14.05f, 6, 32, 3, 0f, false)
        door.texOffs(60, 78).addBox(-18f, -37f, -14f, 5, 5, 28, 0f, false)
        door.texOffs(66, 315).addBox(-14f, -37f, 14f, 8, 37, 0, 0f, false)
        door.texOffs(258, 297).addBox(-14f, -37f, -14f, 8, 37, 0, 0f, false)
        door.texOffs(172, 88).addBox(-5f, -35f, -14f, 5, 0, 28, 0f, false)

        light_edge_r2 = ModelMapper(modelDataWrapper)
        light_edge_r2.setPos(-5f, -35f, 0f)
        door.addChild(light_edge_r2)
        setRotationAngle(light_edge_r2, 0f, 0f, 0.2182f)
        light_edge_r2.texOffs(206, 202).addBox(-5f, 0f, -14f, 1, 1, 28, 0f, false)
        light_edge_r2.texOffs(60, 0).addBox(-9f, 0f, -14f, 9, 0, 28, 0f, false)

        door_top_3_r1 = ModelMapper(modelDataWrapper)
        door_top_3_r1.setPos(-11.7395f, -35.2093f, 0f)
        door.addChild(door_top_3_r1)
        setRotationAngle(door_top_3_r1, 0f, 0f, -1.3963f)
        door_top_3_r1.texOffs(172, 197).addBox(-1.5f, -2f, -14f, 3, 2, 28, 0f, false)

        door_top_2_r1 = ModelMapper(modelDataWrapper)
        door_top_2_r1.setPos(-13f, -32f, 0f)
        door.addChild(door_top_2_r1)
        setRotationAngle(door_top_2_r1, 0f, 0f, -1.0472f)
        door_top_2_r1.texOffs(200, 144).addBox(0f, -1f, -14f, 2, 1, 28, 0f, false)

        window_top_2_r1 = ModelMapper(modelDataWrapper)
        window_top_2_r1.setPos(-21f, -13f, 12f)
        door.addChild(window_top_2_r1)
        setRotationAngle(window_top_2_r1, 0f, 0f, 0.1396f)
        window_top_2_r1.texOffs(287, 33).addBox(1f, -21f, -24f, 1, 21, 1, 0f, false)
        window_top_2_r1.texOffs(283, 33).addBox(1f, -21f, -1f, 1, 21, 1, 0f, false)

        window_bottom_2_r1 = ModelMapper(modelDataWrapper)
        window_bottom_2_r1.setPos(-21f, -7f, 12f)
        door.addChild(window_bottom_2_r1)
        setRotationAngle(window_bottom_2_r1, 0f, 0f, -0.2094f)
        window_bottom_2_r1.texOffs(271, 33).addBox(1f, 0f, -24f, 1, 8, 1, 0f, false)
        window_bottom_2_r1.texOffs(267, 33).addBox(1f, 0f, -1f, 1, 8, 1, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(222, 7).addBox(-20f, -13f, 0f, 0, 6, 12, 0f, false)

        door_top_r1 = ModelMapper(modelDataWrapper)
        door_top_r1.setPos(-21f, -13f, 12f)
        door_left.addChild(door_top_r1)
        setRotationAngle(door_top_r1, 0f, 0f, 0.1396f)
        door_top_r1.texOffs(206, 190).addBox(1f, -21f, -12f, 0, 21, 12, 0f, false)

        door_bottom_r1 = ModelMapper(modelDataWrapper)
        door_bottom_r1.setPos(-21f, -7f, 12f)
        door_left.addChild(door_bottom_r1)
        setRotationAngle(door_bottom_r1, 0f, 0f, -0.2094f)
        door_bottom_r1.texOffs(0, 226).addBox(1f, 0f, -12f, 0, 8, 12, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(158, 0).addBox(-20f, -13f, -12f, 0, 6, 12, 0f, false)

        door_top_r2 = ModelMapper(modelDataWrapper)
        door_top_r2.setPos(-21f, -13f, 12f)
        door_right.addChild(door_top_r2)
        setRotationAngle(door_top_r2, 0f, 0f, 0.1396f)
        door_top_r2.texOffs(0, 65).addBox(1f, -21f, -24f, 0, 21, 12, 0f, false)

        door_bottom_r2 = ModelMapper(modelDataWrapper)
        door_bottom_r2.setPos(-21f, -7f, 12f)
        door_right.addChild(door_bottom_r2)
        setRotationAngle(door_bottom_r2, 0f, 0f, -0.2094f)
        door_bottom_r2.texOffs(0, 218).addBox(1f, 0f, -24f, 0, 8, 12, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(226, 88).addBox(-21f, -13f, 12f, 1, 6, 2, 0f, false)
        door_exterior.texOffs(190, 67).addBox(-21f, -13f, -14f, 1, 6, 2, 0f, false)
        door_exterior.texOffs(280, 0).addBox(-21f, 0f, -12f, 2, 1, 24, 0f, false)

        window_top_2_r2 = ModelMapper(modelDataWrapper)
        window_top_2_r2.setPos(-21f, -13f, 12f)
        door_exterior.addChild(window_top_2_r2)
        setRotationAngle(window_top_2_r2, 0f, 0f, 0.1396f)
        window_top_2_r2.texOffs(81, 78).addBox(0f, -21f, -26f, 1, 21, 2, 0f, false)
        window_top_2_r2.texOffs(200, 173).addBox(0f, -21f, 0f, 1, 21, 2, 0f, false)

        window_bottom_2_r2 = ModelMapper(modelDataWrapper)
        window_bottom_2_r2.setPos(-21f, -7f, 12f)
        door_exterior.addChild(window_bottom_2_r2)
        setRotationAngle(window_bottom_2_r2, 0f, 0f, -0.2094f)
        window_bottom_2_r2.texOffs(182, 7).addBox(0f, 0f, -26f, 1, 8, 2, 0f, false)
        window_bottom_2_r2.texOffs(188, 7).addBox(0f, 0f, 0f, 1, 8, 2, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(245, 96).addBox(-21f, -13f, 0f, 1, 6, 12, 0f, false)

        door_top_r3 = ModelMapper(modelDataWrapper)
        door_top_r3.setPos(-21f, -13f, 12f)
        door_left_exterior.addChild(door_top_r3)
        setRotationAngle(door_top_r3, 0f, 0f, 0.1396f)
        door_top_r3.texOffs(305, 150).addBox(0f, -21f, -12f, 1, 21, 12, 0f, false)

        door_bottom_r3 = ModelMapper(modelDataWrapper)
        door_bottom_r3.setPos(-21f, -7f, 12f)
        door_left_exterior.addChild(door_bottom_r3)
        setRotationAngle(door_bottom_r3, 0f, 0f, -0.2094f)
        door_bottom_r3.texOffs(117, 237).addBox(0f, 0f, -12f, 1, 8, 12, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(238, 183).addBox(-21f, -13f, -12f, 1, 6, 12, 0f, false)

        door_top_r4 = ModelMapper(modelDataWrapper)
        door_top_r4.setPos(-21f, -13f, 12f)
        door_right_exterior.addChild(door_top_r4)
        setRotationAngle(door_top_r4, 0f, 0f, 0.1396f)
        door_top_r4.texOffs(299, 25).addBox(0f, -21f, -24f, 1, 21, 12, 0f, false)

        door_bottom_r4 = ModelMapper(modelDataWrapper)
        door_bottom_r4.setPos(-21f, -7f, 12f)
        door_right_exterior.addChild(door_bottom_r4)
        setRotationAngle(door_bottom_r4, 0f, 0f, -0.2094f)
        door_bottom_r4.texOffs(232, 150).addBox(0f, 0f, -24f, 1, 8, 12, 0f, false)

        door_exterior_end = ModelMapper(modelDataWrapper)
        door_exterior_end.setPos(0f, 24f, 0f)


        door_exterior_end_1 = ModelMapper(modelDataWrapper)
        door_exterior_end_1.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_exterior_end_1)
        door_exterior_end_1.texOffs(226, 96).addBox(-21f, -13f, 12f, 1, 6, 2, 0f, false)
        door_exterior_end_1.texOffs(226, 104).addBox(-21f, -13f, -14f, 1, 6, 2, 0f, false)
        door_exterior_end_1.texOffs(313, 1).addBox(-21f, 0f, -12f, 2, 1, 24, 0f, false)

        window_top_2_r3 = ModelMapper(modelDataWrapper)
        window_top_2_r3.setPos(-21f, -13f, 12f)
        door_exterior_end_1.addChild(window_top_2_r3)
        setRotationAngle(window_top_2_r3, 0f, 0f, 0.1396f)
        window_top_2_r3.texOffs(298, 0).addBox(0f, -21f, -26f, 1, 21, 2, 0f, false)
        window_top_2_r3.texOffs(292, 0).addBox(0f, -21f, 0f, 1, 21, 2, 0f, false)

        window_bottom_2_r3 = ModelMapper(modelDataWrapper)
        window_bottom_2_r3.setPos(-21f, -7f, 12f)
        door_exterior_end_1.addChild(window_bottom_2_r3)
        setRotationAngle(window_bottom_2_r3, 0f, 0f, -0.2094f)
        window_bottom_2_r3.texOffs(280, 0).addBox(0f, 0f, -26f, 1, 8, 2, 0f, false)
        window_bottom_2_r3.texOffs(286, 0).addBox(0f, 0f, 0f, 1, 8, 2, 0f, false)

        door_left_exterior_end_1 = ModelMapper(modelDataWrapper)
        door_left_exterior_end_1.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_left_exterior_end_1)
        door_left_exterior_end_1.texOffs(339, 46).addBox(-21f, -13f, 0f, 1, 6, 12, 0f, false)

        door_top_r5 = ModelMapper(modelDataWrapper)
        door_top_r5.setPos(-21f, -13f, 12f)
        door_left_exterior_end_1.addChild(door_top_r5)
        setRotationAngle(door_top_r5, 0f, 0f, 0.1396f)
        door_top_r5.texOffs(325, 25).addBox(0f, -21f, -12f, 1, 21, 12, 0f, false)

        door_bottom_r5 = ModelMapper(modelDataWrapper)
        door_bottom_r5.setPos(-21f, -7f, 12f)
        door_left_exterior_end_1.addChild(door_bottom_r5)
        setRotationAngle(door_bottom_r5, 0f, 0f, -0.2094f)
        door_bottom_r5.texOffs(112, 316).addBox(0f, 0f, -12f, 1, 8, 12, 0f, false)

        door_right_exterior_end_1 = ModelMapper(modelDataWrapper)
        door_right_exterior_end_1.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_right_exterior_end_1)
        door_right_exterior_end_1.texOffs(339, 79).addBox(-21f, -13f, -12f, 1, 6, 12, 0f, false)

        door_top_r6 = ModelMapper(modelDataWrapper)
        door_top_r6.setPos(-21f, -13f, 12f)
        door_right_exterior_end_1.addChild(door_top_r6)
        setRotationAngle(door_top_r6, 0f, 0f, 0.1396f)
        door_top_r6.texOffs(325, 58).addBox(0f, -21f, -24f, 1, 21, 12, 0f, false)

        door_bottom_r6 = ModelMapper(modelDataWrapper)
        door_bottom_r6.setPos(-21f, -7f, 12f)
        door_right_exterior_end_1.addChild(door_bottom_r6)
        setRotationAngle(door_bottom_r6, 0f, 0f, -0.2094f)
        door_bottom_r6.texOffs(112, 336).addBox(0f, 0f, -24f, 1, 8, 12, 0f, false)

        door_exterior_end_2 = ModelMapper(modelDataWrapper)
        door_exterior_end_2.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_exterior_end_2)
        door_exterior_end_2.texOffs(226, 96).addBox(20f, -13f, 12f, 1, 6, 2, 0f, true)
        door_exterior_end_2.texOffs(226, 104).addBox(20f, -13f, -14f, 1, 6, 2, 0f, true)
        door_exterior_end_2.texOffs(313, 1).addBox(19f, 0f, -12f, 2, 1, 24, 0f, true)

        window_top_2_r4 = ModelMapper(modelDataWrapper)
        window_top_2_r4.setPos(21f, -13f, 12f)
        door_exterior_end_2.addChild(window_top_2_r4)
        setRotationAngle(window_top_2_r4, 0f, 0f, -0.1396f)
        window_top_2_r4.texOffs(298, 0).addBox(-1f, -21f, -26f, 1, 21, 2, 0f, true)
        window_top_2_r4.texOffs(292, 0).addBox(-1f, -21f, 0f, 1, 21, 2, 0f, true)

        window_bottom_2_r4 = ModelMapper(modelDataWrapper)
        window_bottom_2_r4.setPos(21f, -7f, 12f)
        door_exterior_end_2.addChild(window_bottom_2_r4)
        setRotationAngle(window_bottom_2_r4, 0f, 0f, 0.2094f)
        window_bottom_2_r4.texOffs(280, 0).addBox(-1f, 0f, -26f, 1, 8, 2, 0f, true)
        window_bottom_2_r4.texOffs(286, 0).addBox(-1f, 0f, 0f, 1, 8, 2, 0f, true)

        door_left_exterior_end_2 = ModelMapper(modelDataWrapper)
        door_left_exterior_end_2.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_left_exterior_end_2)
        door_left_exterior_end_2.texOffs(339, 46).addBox(20f, -13f, 0f, 1, 6, 12, 0f, true)

        door_top_r7 = ModelMapper(modelDataWrapper)
        door_top_r7.setPos(21f, -13f, 12f)
        door_left_exterior_end_2.addChild(door_top_r7)
        setRotationAngle(door_top_r7, 0f, 0f, -0.1396f)
        door_top_r7.texOffs(325, 25).addBox(-1f, -21f, -12f, 1, 21, 12, 0f, true)

        door_bottom_r7 = ModelMapper(modelDataWrapper)
        door_bottom_r7.setPos(21f, -7f, 12f)
        door_left_exterior_end_2.addChild(door_bottom_r7)
        setRotationAngle(door_bottom_r7, 0f, 0f, 0.2094f)
        door_bottom_r7.texOffs(112, 316).addBox(-1f, 0f, -12f, 1, 8, 12, 0f, true)

        door_right_exterior_end_2 = ModelMapper(modelDataWrapper)
        door_right_exterior_end_2.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_right_exterior_end_2)
        door_right_exterior_end_2.texOffs(339, 79).addBox(20f, -13f, -12f, 1, 6, 12, 0f, true)

        door_top_r8 = ModelMapper(modelDataWrapper)
        door_top_r8.setPos(21f, -13f, 12f)
        door_right_exterior_end_2.addChild(door_top_r8)
        setRotationAngle(door_top_r8, 0f, 0f, -0.1396f)
        door_top_r8.texOffs(325, 58).addBox(-1f, -21f, -24f, 1, 21, 12, 0f, true)

        door_bottom_r8 = ModelMapper(modelDataWrapper)
        door_bottom_r8.setPos(21f, -7f, 12f)
        door_right_exterior_end_2.addChild(door_bottom_r8)
        setRotationAngle(door_bottom_r8, 0f, 0f, 0.2094f)
        door_bottom_r8.texOffs(112, 336).addBox(-1f, 0f, -24f, 1, 8, 12, 0f, true)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(208, 331).addBox(-7f, -36f, -12.5f, 14, 4, 6, 0f, false)
        end.texOffs(106, 0).addBox(-19f, 0f, -12.5f, 38, 1, 6, 0f, false)

        end_1 = ModelMapper(modelDataWrapper)
        end_1.setPos(0f, 0f, 0f)
        end.addChild(end_1)
        end_1.texOffs(0, 296).addBox(-20f, -37f, -12.5f, 13, 37, 6, 0f, false)

        end_2 = ModelMapper(modelDataWrapper)
        end_2.setPos(0f, 0f, 0f)
        end.addChild(end_2)
        end_2.texOffs(293, 195).addBox(7f, -37f, -12.5f, 13, 37, 6, 0f, true)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)
        end_exterior.texOffs(287, 266).addBox(-7f, -40f, -6.5f, 14, 8, 0, 0f, false)

        end_exterior_1 = ModelMapper(modelDataWrapper)
        end_exterior_1.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_1)
        end_exterior_1.texOffs(246, 150).addBox(-21f, -13f, -12.5f, 1, 6, 6, 0f, false)
        end_exterior_1.texOffs(164, 315).addBox(-20f, -39f, -6.5f, 13, 39, 0, 0f, false)

        window_bottom_r26 = ModelMapper(modelDataWrapper)
        window_bottom_r26.setPos(-21f, -7f, 0f)
        end_exterior_1.addChild(window_bottom_r26)
        setRotationAngle(window_bottom_r26, 0f, 0f, -0.2094f)
        window_bottom_r26.texOffs(106, 251).addBox(0f, 0f, -12.5f, 1, 8, 6, 0f, false)

        window_top_r33 = ModelMapper(modelDataWrapper)
        window_top_r33.setPos(-21f, -13f, 0f)
        end_exterior_1.addChild(window_top_r33)
        setRotationAngle(window_top_r33, 0f, 0f, 0.1396f)
        window_top_r33.texOffs(256, 59).addBox(0f, -21f, -12.5f, 1, 21, 6, 0f, false)

        end_exterior_2 = ModelMapper(modelDataWrapper)
        end_exterior_2.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_2)
        end_exterior_2.texOffs(131, 237).addBox(20f, -13f, -12.5f, 1, 6, 6, 0f, true)
        end_exterior_2.texOffs(138, 315).addBox(7f, -39f, -6.5f, 13, 39, 0, 0f, true)

        window_bottom_r27 = ModelMapper(modelDataWrapper)
        window_bottom_r27.setPos(21f, -7f, 0f)
        end_exterior_2.addChild(window_bottom_r27)
        setRotationAngle(window_bottom_r27, 0f, 0f, 0.2094f)
        window_bottom_r27.texOffs(79, 244).addBox(-1f, 0f, -12.5f, 1, 8, 6, 0f, true)

        window_top_r34 = ModelMapper(modelDataWrapper)
        window_top_r34.setPos(21f, -13f, 0f)
        end_exterior_2.addChild(window_top_r34)
        setRotationAngle(window_top_r34, 0f, 0f, -0.1396f)
        window_top_r34.texOffs(220, 173).addBox(-1f, -21f, -12.5f, 1, 21, 6, 0f, true)

        roof_end_exterior_1 = ModelMapper(modelDataWrapper)
        roof_end_exterior_1.setPos(0f, 0f, 0f)
        end_exterior.addChild(roof_end_exterior_1)
        roof_end_exterior_1.texOffs(27, 230).addBox(-3.8369f, -40.0424f, -12.5f, 4, 1, 6, 0f, false)

        roof_3_r5 = ModelMapper(modelDataWrapper)
        roof_3_r5.setPos(-7.2836f, -39.4346f, 0f)
        roof_end_exterior_1.addChild(roof_3_r5)
        setRotationAngle(roof_3_r5, 0f, 0f, 1.3963f)
        roof_3_r5.texOffs(256, 11).addBox(0f, -3.5f, -12.5f, 1, 7, 6, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(-13.7612f, -37.077f, 0f)
        roof_end_exterior_1.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, 1.0472f)
        roof_2_r3.texOffs(258, 217).addBox(0f, -3.5f, -12.5f, 1, 7, 6, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(-16.792f, -35.3272f, 0f)
        roof_end_exterior_1.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, 0.6981f)
        roof_1_r3.texOffs(248, 80).addBox(0f, 0f, -12.5f, 1, 2, 6, 0f, false)

        roof_end_exterior_2 = ModelMapper(modelDataWrapper)
        roof_end_exterior_2.setPos(0f, 0f, 0f)
        end_exterior.addChild(roof_end_exterior_2)
        roof_end_exterior_2.texOffs(176, 71).addBox(-0.1631f, -40.0424f, -12.5f, 4, 1, 6, 0f, true)

        roof_4_r5 = ModelMapper(modelDataWrapper)
        roof_4_r5.setPos(7.2836f, -39.4346f, 0f)
        roof_end_exterior_2.addChild(roof_4_r5)
        setRotationAngle(roof_4_r5, 0f, 0f, -1.3963f)
        roof_4_r5.texOffs(179, 243).addBox(-1f, -3.5f, -12.5f, 1, 7, 6, 0f, true)

        roof_3_r6 = ModelMapper(modelDataWrapper)
        roof_3_r6.setPos(13.7612f, -37.077f, 0f)
        roof_end_exterior_2.addChild(roof_3_r6)
        setRotationAngle(roof_3_r6, 0f, 0f, -1.0472f)
        roof_3_r6.texOffs(253, 33).addBox(-1f, -3.5f, -12.5f, 1, 7, 6, 0f, true)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(16.792f, -35.3272f, 0f)
        roof_end_exterior_2.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, -0.6981f)
        roof_2_r4.texOffs(0, 246).addBox(-1f, 0f, -12.5f, 1, 2, 6, 0f, true)

        head = ModelMapper(modelDataWrapper)
        head.setPos(0f, 24f, 0f)
        head.texOffs(0, 40).addBox(-22f, -37f, -25.5f, 44, 37, 0, 0f, false)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)
        head_exterior.texOffs(283, 323).addBox(-7f, -34f, 2.5f, 14, 36, 6, 0f, false)
        head_exterior.texOffs(0, 0).addBox(-22f, -40f, -12f, 44, 40, 0, 0f, false)

        front_r1 = ModelMapper(modelDataWrapper)
        front_r1.setPos(0f, 2f, 5.5f)
        head_exterior.addChild(front_r1)
        setRotationAngle(front_r1, 0.0698f, 0f, 0f)
        front_r1.texOffs(283, 282).addBox(-21f, -41f, 0f, 42, 41, 0, 0f, false)

        head_exterior_1 = ModelMapper(modelDataWrapper)
        head_exterior_1.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_1)
        head_exterior_1.texOffs(150, 1).addBox(-21f, -13f, -12.5f, 0, 6, 18, 0f, false)
        head_exterior_1.texOffs(176, 0).addBox(-19f, 0f, -12.5f, 19, 1, 18, 0f, false)

        front_bottom_r1 = ModelMapper(modelDataWrapper)
        front_bottom_r1.setPos(0f, 2f, 5.5f)
        head_exterior_1.addChild(front_bottom_r1)
        setRotationAngle(front_bottom_r1, -0.5236f, 0f, 0f)
        front_bottom_r1.texOffs(114, 7).addBox(-21f, 0f, -1f, 21, 8, 1, 0f, false)

        roof_5_r4 = ModelMapper(modelDataWrapper)
        roof_5_r4.setPos(-1.8369f, -39.258f, -3.5343f)
        head_exterior_1.addChild(roof_5_r4)
        setRotationAngle(roof_5_r4, -0.0873f, 0f, 0f)
        roof_5_r4.texOffs(88, 7).addBox(-2f, 0f, -9f, 4, 0, 18, 0f, false)

        roof_4_r6 = ModelMapper(modelDataWrapper)
        roof_4_r6.setPos(-7.1474f, -38.6622f, -3.5343f)
        head_exterior_1.addChild(roof_4_r6)
        setRotationAngle(roof_4_r6, 0f, 0.0873f, 1.3963f)
        roof_4_r6.texOffs(114, 0).addBox(0f, -3.5f, -9f, 0, 7, 18, 0f, false)

        roof_3_r7 = ModelMapper(modelDataWrapper)
        roof_3_r7.setPos(-13.369f, -36.3977f, -3.5343f)
        head_exterior_1.addChild(roof_3_r7)
        setRotationAngle(roof_3_r7, 0f, 0.0873f, 1.0472f)
        roof_3_r7.texOffs(226, 40).addBox(0f, -3.5f, -9f, 0, 7, 18, 0f, false)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(-17.7981f, -32.9079f, -3.5343f)
        head_exterior_1.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0.0873f, 0.6981f)
        roof_2_r5.texOffs(186, 1).addBox(0f, -2.5f, -9f, 0, 5, 18, 0f, false)

        window_bottom_r28 = ModelMapper(modelDataWrapper)
        window_bottom_r28.setPos(-21f, -7f, 0f)
        head_exterior_1.addChild(window_bottom_r28)
        setRotationAngle(window_bottom_r28, 0f, 0f, -0.2094f)
        window_bottom_r28.texOffs(220, 297).addBox(0f, 0f, -12.5f, 1, 16, 18, 0f, false)

        window_top_r35 = ModelMapper(modelDataWrapper)
        window_top_r35.setPos(-21f, -13f, 0f)
        head_exterior_1.addChild(window_top_r35)
        setRotationAngle(window_top_r35, 0f, 0f, 0.1396f)
        window_top_r35.texOffs(218, 99).addBox(0f, -21f, -12.5f, 0, 21, 18, 0f, false)

        head_exterior_2 = ModelMapper(modelDataWrapper)
        head_exterior_2.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_exterior_2)
        head_exterior_2.texOffs(150, 1).addBox(21f, -13f, -12.5f, 0, 6, 18, 0f, true)
        head_exterior_2.texOffs(176, 0).addBox(0f, 0f, -12.5f, 19, 1, 18, 0f, true)

        front_bottom_r2 = ModelMapper(modelDataWrapper)
        front_bottom_r2.setPos(0f, 2f, 5.5f)
        head_exterior_2.addChild(front_bottom_r2)
        setRotationAngle(front_bottom_r2, -0.5236f, 0f, 0f)
        front_bottom_r2.texOffs(114, 7).addBox(0f, 0f, -1f, 21, 8, 1, 0f, true)

        roof_6_r2 = ModelMapper(modelDataWrapper)
        roof_6_r2.setPos(1.8369f, -39.258f, -3.5343f)
        head_exterior_2.addChild(roof_6_r2)
        setRotationAngle(roof_6_r2, -0.0873f, 0f, 0f)
        roof_6_r2.texOffs(88, 7).addBox(-2f, 0f, -9f, 4, 0, 18, 0f, true)

        roof_5_r5 = ModelMapper(modelDataWrapper)
        roof_5_r5.setPos(7.1474f, -38.6622f, -3.5343f)
        head_exterior_2.addChild(roof_5_r5)
        setRotationAngle(roof_5_r5, 0f, -0.0873f, -1.3963f)
        roof_5_r5.texOffs(114, 0).addBox(0f, -3.5f, -9f, 0, 7, 18, 0f, true)

        roof_4_r7 = ModelMapper(modelDataWrapper)
        roof_4_r7.setPos(13.369f, -36.3977f, -3.5343f)
        head_exterior_2.addChild(roof_4_r7)
        setRotationAngle(roof_4_r7, 0f, -0.0873f, -1.0472f)
        roof_4_r7.texOffs(226, 40).addBox(0f, -3.5f, -9f, 0, 7, 18, 0f, true)

        roof_3_r8 = ModelMapper(modelDataWrapper)
        roof_3_r8.setPos(17.7981f, -32.9079f, -3.5343f)
        head_exterior_2.addChild(roof_3_r8)
        setRotationAngle(roof_3_r8, 0f, -0.0873f, -0.6981f)
        roof_3_r8.texOffs(186, 1).addBox(0f, -2.5f, -9f, 0, 5, 18, 0f, true)

        window_bottom_r29 = ModelMapper(modelDataWrapper)
        window_bottom_r29.setPos(21f, -7f, 0f)
        head_exterior_2.addChild(window_bottom_r29)
        setRotationAngle(window_bottom_r29, 0f, 0f, 0.2094f)
        window_bottom_r29.texOffs(220, 297).addBox(-1f, 0f, -12.5f, 1, 16, 18, 0f, true)

        window_top_r36 = ModelMapper(modelDataWrapper)
        window_top_r36.setPos(21f, -13f, 0f)
        head_exterior_2.addChild(window_top_r36)
        setRotationAngle(window_top_r36, 0f, 0f, -0.1396f)
        window_top_r36.texOffs(218, 99).addBox(0f, -21f, -12.5f, 0, 21, 18, 0f, true)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(232, 9).addBox(-4f, -6f, -4f, 8, 1, 7, 0f, false)
        seat.texOffs(236, 202).addBox(-3.5f, -22.644f, 4.0686f, 7, 5, 1, 0f, false)

        seat_2_r1 = ModelMapper(modelDataWrapper)
        seat_2_r1.setPos(0f, -6f, 2f)
        seat.addChild(seat_2_r1)
        setRotationAngle(seat_2_r1, -0.1745f, 0f, 0f)
        seat_2_r1.texOffs(152, 243).addBox(-4f, -12f, 0f, 8, 12, 1, 0f, false)

        door_light_box = ModelMapper(modelDataWrapper)
        door_light_box.setPos(0f, 24f, 0f)


        door_light_r1 = ModelMapper(modelDataWrapper)
        door_light_r1.setPos(-21f, -13f, 0f)
        door_light_box.addChild(door_light_r1)
        setRotationAngle(door_light_r1, 0f, 0f, 0.1396f)
        door_light_r1.texOffs(232, 0).addBox(-0.5f, -19f, -1f, 1, 2, 2, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        door_light_r2 = ModelMapper(modelDataWrapper)
        door_light_r2.setPos(-21f, -13f, 0f)
        door_light_off.addChild(door_light_r2)
        setRotationAngle(door_light_r2, 0f, 0f, 0.1396f)
        door_light_r2.texOffs(238, 0).addBox(-0.5f, -19f, -1f, 1, 2, 2, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        door_light_r3 = ModelMapper(modelDataWrapper)
        door_light_r3.setPos(-21f, -13f, 0f)
        door_light_on.addChild(door_light_r3)
        setRotationAngle(door_light_r3, 0f, 0f, 0.1396f)
        door_light_r3.texOffs(244, 0).addBox(-0.5f, -19f, -1f, 1, 2, 2, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(0f, 2f, 5.5f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, 0.0698f, 0f, 0f)
        headlight_2_r1.texOffs(250, 0).addBox(12f, -11f, 0.1f, 6, 7, 0, 0f, true)
        headlight_2_r1.texOffs(250, 0).addBox(-18f, -11f, 0.1f, 6, 7, 0, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_2_r1 = ModelMapper(modelDataWrapper)
        tail_light_2_r1.setPos(0f, 2f, 5.5f)
        tail_lights.addChild(tail_light_2_r1)
        setRotationAngle(tail_light_2_r1, 0.0698f, 0f, 0f)
        tail_light_2_r1.texOffs(262, 0).addBox(9f, -9f, 0.1f, 4, 5, 0, 0f, true)
        tail_light_2_r1.texOffs(262, 0).addBox(-13f, -9f, 0.1f, 4, 5, 0, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_light.setModelPart()
        door_light.setModelPart()
        window_1.setModelPart()
        window_2.setModelPart()
        window_3.setModelPart()
        window_4.setModelPart()
        window_5.setModelPart()
        window_6.setModelPart()
        window_7.setModelPart()
        window_8.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        window_exterior_3.setModelPart()
        window_exterior_4.setModelPart()
        window_exterior_5.setModelPart()
        window_exterior_6.setModelPart()
        window_exterior_7.setModelPart()
        window_exterior_8.setModelPart()
        window_end_exterior_1.setModelPart()
        window_end_exterior_2.setModelPart()
        window_end_exterior_3.setModelPart()
        window_end_exterior_4.setModelPart()
        roof_exterior_1.setModelPart()
        roof_exterior_door_edge.setModelPart()
        roof_exterior_door.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        door_exterior_end.setModelPart()
        door_left_exterior_end_1.setModelPart(door_exterior_end.name)
        door_right_exterior_end_1.setModelPart(door_exterior_end.name)
        door_left_exterior_end_2.setModelPart(door_exterior_end.name)
        door_right_exterior_end_2.setModelPart(door_exterior_end.name)
        end.setModelPart()
        end_exterior.setModelPart()
        head.setModelPart()
        head_exterior.setModelPart()
        seat.setModelPart()
        door_light_box.setModelPart()
        door_light_off.setModelPart()
        door_light_on.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelClass377 {
        return ModelClass377(doorAnimationType, renderDoorOverlay)
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
        val windowPositions = this.newWindowPositions

        when (renderStage!!) {
            RenderStage.LIGHTS -> for (i in windowPositions) {
                renderMirror(window_light, matrices, vertices, light, i)
            }

            RenderStage.INTERIOR -> {
                for (i in windowPositions) {
                    renderMirror(window, matrices, vertices, light, i)
                }
                renderOnceFlipped(window_1, matrices, vertices, light, windowPositions[0])
                renderOnceFlipped(window_2, matrices, vertices, light, windowPositions[1])
                renderOnceFlipped(window_3, matrices, vertices, light, windowPositions[2])
                renderOnceFlipped(window_4, matrices, vertices, light, windowPositions[3])
                renderOnceFlipped(window_5, matrices, vertices, light, windowPositions[4])
                renderOnceFlipped(window_6, matrices, vertices, light, windowPositions[5])
                renderOnceFlipped(window_7, matrices, vertices, light, windowPositions[6])
                renderOnceFlipped(window_8, matrices, vertices, light, windowPositions[7])
                renderOnce(window_8, matrices, vertices, light, windowPositions[0])
                renderOnce(window_7, matrices, vertices, light, windowPositions[1])
                renderOnce(window_6, matrices, vertices, light, windowPositions[2])
                renderOnce(window_5, matrices, vertices, light, windowPositions[3])
                renderOnce(window_4, matrices, vertices, light, windowPositions[4])
                renderOnce(window_3, matrices, vertices, light, windowPositions[5])
                renderOnce(window_2, matrices, vertices, light, windowPositions[6])
                renderOnce(window_1, matrices, vertices, light, windowPositions[7])
                if (renderDetails) {
                    for (i in windowPositions) {
                        renderOnceFlipped(seat, matrices, vertices, light, -16f, i - 7)
                        renderOnceFlipped(seat, matrices, vertices, light, -8f, i - 7)
                        renderOnceFlipped(seat, matrices, vertices, light, 8f, i - 7)
                        renderOnceFlipped(seat, matrices, vertices, light, 16f, i - 7)
                        renderOnce(seat, matrices, vertices, light, -16f, i + 7)
                        renderOnce(seat, matrices, vertices, light, -8f, i + 7)
                        renderOnce(seat, matrices, vertices, light, 8f, i + 7)
                        renderOnce(seat, matrices, vertices, light, 16f, i + 7)
                    }
                }
            }

            RenderStage.EXTERIOR -> {
                if (isEnd1Head) {
                    renderOnceFlipped(window_end_exterior_1, matrices, vertices, light, windowPositions[0])
                    renderOnceFlipped(window_end_exterior_2, matrices, vertices, light, windowPositions[1])
                    renderOnceFlipped(window_end_exterior_3, matrices, vertices, light, windowPositions[2])
                    renderOnceFlipped(window_end_exterior_4, matrices, vertices, light, windowPositions[3])
                } else {
                    renderOnceFlipped(window_exterior_1, matrices, vertices, light, windowPositions[0])
                    renderOnceFlipped(window_exterior_2, matrices, vertices, light, windowPositions[1])
                    renderOnceFlipped(window_exterior_3, matrices, vertices, light, windowPositions[2])
                    renderOnceFlipped(window_exterior_4, matrices, vertices, light, windowPositions[3])
                }
                if (isEnd2Head) {
                    renderOnce(window_end_exterior_4, matrices, vertices, light, windowPositions[4])
                    renderOnce(window_end_exterior_3, matrices, vertices, light, windowPositions[5])
                    renderOnce(window_end_exterior_2, matrices, vertices, light, windowPositions[6])
                    renderOnce(window_end_exterior_1, matrices, vertices, light, windowPositions[7])
                } else {
                    renderOnceFlipped(window_exterior_5, matrices, vertices, light, windowPositions[4])
                    renderOnceFlipped(window_exterior_6, matrices, vertices, light, windowPositions[5])
                    renderOnceFlipped(window_exterior_7, matrices, vertices, light, windowPositions[6])
                    renderOnceFlipped(window_exterior_8, matrices, vertices, light, windowPositions[7])
                }
                renderMirror(roof_exterior_1, matrices, vertices, light, windowPositions[0])
                renderOnce(roof_exterior_door_edge, matrices, vertices, light, windowPositions[1])
                renderOnceFlipped(roof_exterior_door_edge, matrices, vertices, light, windowPositions[2])
                renderMirror(roof_exterior_1, matrices, vertices, light, windowPositions[3])
                renderMirror(roof_exterior_1, matrices, vertices, light, windowPositions[4])
                renderOnce(roof_exterior_door_edge, matrices, vertices, light, windowPositions[5])
                renderOnceFlipped(roof_exterior_door_edge, matrices, vertices, light, windowPositions[6])
                renderMirror(roof_exterior_1, matrices, vertices, light, windowPositions[7])
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
        val isFirstDoor = isIndex(0, position, getDoorPositions())
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                if (isFirstDoor && doorOpen) {
                    renderMirror(door_light_on, matrices, vertices, light, 0f)
                }
                renderMirror(door_light, matrices, vertices, light, position.toFloat())
            }

            RenderStage.INTERIOR -> {
                door_left.setOffset(doorRightX, 0, doorRightZ)
                door_right.setOffset(doorRightX, 0, -doorRightZ)
                renderOnce(door, matrices, vertices, light, position.toFloat())
                door_left.setOffset(doorLeftX, 0, doorLeftZ)
                door_right.setOffset(doorLeftX, 0, -doorLeftZ)
                renderOnceFlipped(door, matrices, vertices, light, position.toFloat())
            }

            RenderStage.EXTERIOR -> {
                if (isEnd1Head && isFirstDoor) {
                    door_left_exterior_end_2.setOffset(-doorRightX, 0, doorRightZ)
                    door_right_exterior_end_2.setOffset(-doorRightX, 0, -doorRightZ)
                    door_left_exterior_end_1.setOffset(doorLeftX, 0, doorLeftZ)
                    door_right_exterior_end_1.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior_end, matrices, vertices, light, position.toFloat())
                } else if (isEnd2Head && isIndex(-1, position, getDoorPositions())) {
                    door_left_exterior_end_1.setOffset(doorRightX, 0, doorRightZ)
                    door_right_exterior_end_1.setOffset(doorRightX, 0, -doorRightZ)
                    door_left_exterior_end_2.setOffset(-doorLeftX, 0, doorLeftZ)
                    door_right_exterior_end_2.setOffset(-doorLeftX, 0, -doorLeftZ)
                    renderOnce(door_exterior_end, matrices, vertices, light, position.toFloat())
                } else {
                    door_left_exterior.setOffset(doorRightX, 0, doorRightZ)
                    door_right_exterior.setOffset(doorRightX, 0, -doorRightZ)
                    renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                    door_left_exterior.setOffset(doorLeftX, 0, doorLeftZ)
                    door_right_exterior.setOffset(doorLeftX, 0, -doorLeftZ)
                    renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                }
                renderOnce(roof_exterior_door, matrices, vertices, light, position.toFloat())
                if (isFirstDoor) {
                    renderMirror(door_light_box, matrices, vertices, light, 0f)
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
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                position - 12.5f
            )

            RenderStage.INTERIOR -> renderOnceFlipped(head, matrices, vertices, light, position - 12.5f)
            RenderStage.EXTERIOR -> renderOnceFlipped(head_exterior, matrices, vertices, light, position - 12.5f)
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
                position + 12.5f
            )

            RenderStage.INTERIOR -> renderOnce(head, matrices, vertices, light, position + 12.5f)
            RenderStage.EXTERIOR -> renderOnce(head_exterior, matrices, vertices, light, position + 12.5f)
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
            RenderStage.INTERIOR -> renderOnceFlipped(end, matrices, vertices, light, position - 12.5f)
            RenderStage.EXTERIOR -> renderOnceFlipped(end_exterior, matrices, vertices, light, position - 12.5f)
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
            RenderStage.INTERIOR -> renderOnce(end, matrices, vertices, light, position + 12.5f)
            RenderStage.EXTERIOR -> renderOnce(end_exterior, matrices, vertices, light, position + 12.5f)
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
        return intArrayOf(-64, 64)
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
            scrollingTexts.add(ScrollingText(0.5f, 0.1f, 4, true))
            scrollingTexts.add(ScrollingText(1.08f, 0.06f, 8, true))
        }
        val isEnd1Head = car == 0
        val isEnd2Head = car == totalCars - 1
        val offset = 0.23f
        val positions1 = floatArrayOf(-4 + 26.5f / 16 - offset, 4 + 26.5f / 16 - (if (isEnd2Head) -1 else 1) * offset)
        val positions2 = floatArrayOf(-4 - 26.5f / 16 - offset, 4 - 26.5f / 16 - (if (isEnd2Head) -1 else 1) * offset)

        val destinationString: String? =
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false)
        scrollingTexts.get(0)!!.changeImage(
            if (destinationString!!.isEmpty()) null else ClientData.DATA_CACHE.getPixelatedText(
                destinationString.replace(
                    "|",
                    " "
                ),
                -0x6700,
                Integer.MAX_VALUE,
                0f,
                true
            )
        )
        scrollingTexts.get(0)!!.setVertexConsumer(vertexConsumers)

        for (position in positions1) {
            matrices!!.pushPose()
            matrices.translate(-21f / 16, -13f / 16, position)
            UtilitiesClient.rotateYDegrees(matrices, 90f)
            UtilitiesClient.rotateXDegrees(matrices, -8f)
            matrices.translate(-0.25f, -0.82f, -0.01f)
            scrollingTexts.get(0)!!.scrollText(matrices)
            matrices.popPose()
        }
        for (position in positions2) {
            matrices!!.pushPose()
            matrices.translate(21f / 16, -13f / 16, position)
            UtilitiesClient.rotateYDegrees(matrices, -90f)
            UtilitiesClient.rotateXDegrees(matrices, -8f)
            matrices.translate(-0.25f, -0.82f, -0.01f)
            scrollingTexts.get(0)!!.scrollText(matrices)
            matrices.popPose()
        }

        val nextStationString: String? = ModelTrainBase.getLondonNextStationString(
            thisRoute,
            nextRoute,
            thisStation,
            nextStation,
            lastStation,
            destinationString,
            atPlatform
        )
        scrollingTexts.get(1)!!.changeImage(
            if (nextStationString!!.isEmpty()) null else ClientData.DATA_CACHE.getPixelatedText(
                nextStationString,
                -0x6700,
                Integer.MAX_VALUE,
                0f,
                true
            )
        )
        scrollingTexts.get(1)!!.setVertexConsumer(vertexConsumers)

        for (i in 0..1) {
            matrices!!.pushPose()
            if (i == 1) {
                UtilitiesClient.rotateYDegrees(matrices, 180f)
            }
            matrices.translate(
                -0.54,
                -2.14,
                (getEndPositions()!![1] - (if (i == 1 && isEnd1Head || i == 0 && isEnd2Head) 13 else 0)) / 16f - 0.01
            )
            scrollingTexts.get(1)!!.scrollText(matrices)
            matrices.popPose()
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    private val newWindowPositions: FloatArray
        get() = floatArrayOf(-115.5f, -90.5f, -37.5f, -12.5f, 12.5f, 37.5f, 90.5f, 115.5f)

    companion object {
        private const val DOOR_MAX = 11
    }
}
