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

open class ModelClass802 protected constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    ModelSimpleTrainBase<ModelClass802?>(doorAnimationType, renderDoorOverlay) {
    private val window: ModelMapper
    private val roof_3_r1: ModelMapper
    private val roof_1_r1: ModelMapper
    private val window_bottom_r1: ModelMapper
    private val window_top_r1: ModelMapper
    private val window_light: ModelMapper
    private val window_exterior_1: ModelMapper
    private val roof_2_r1: ModelMapper
    private val roof_1_r2: ModelMapper
    private val window_bottom_r2: ModelMapper
    private val window_top_r2: ModelMapper
    private val window_exterior_2: ModelMapper
    private val roof_2_r2: ModelMapper
    private val roof_1_r3: ModelMapper
    private val window_bottom_r3: ModelMapper
    private val window_top_r3: ModelMapper
    private val window_exterior_3: ModelMapper
    private val roof_2_r3: ModelMapper
    private val roof_1_r4: ModelMapper
    private val window_bottom_r4: ModelMapper
    private val window_top_r4: ModelMapper
    private val window_exterior_4: ModelMapper
    private val roof_2_r4: ModelMapper
    private val roof_1_r5: ModelMapper
    private val window_bottom_r5: ModelMapper
    private val window_top_r5: ModelMapper
    private val window_exterior_5: ModelMapper
    private val roof_2_r5: ModelMapper
    private val roof_1_r6: ModelMapper
    private val window_bottom_r6: ModelMapper
    private val window_top_r6: ModelMapper
    private val window_exterior_6: ModelMapper
    private val roof_2_r6: ModelMapper
    private val roof_1_r7: ModelMapper
    private val window_bottom_r7: ModelMapper
    private val window_top_r7: ModelMapper
    private val window_exterior_7: ModelMapper
    private val roof_2_r7: ModelMapper
    private val roof_1_r8: ModelMapper
    private val window_bottom_r8: ModelMapper
    private val window_top_r8: ModelMapper
    private val window_exterior_8: ModelMapper
    private val roof_2_r8: ModelMapper
    private val roof_1_r9: ModelMapper
    private val window_bottom_r9: ModelMapper
    private val window_top_r9: ModelMapper
    private val window_exterior_9: ModelMapper
    private val roof_2_r9: ModelMapper
    private val roof_1_r10: ModelMapper
    private val window_bottom_r10: ModelMapper
    private val window_top_r10: ModelMapper
    private val window_end_exterior_1: ModelMapper
    private val window_end_exterior_1_1: ModelMapper
    private val roof_2_r10: ModelMapper
    private val roof_1_r11: ModelMapper
    private val window_bottom_r11: ModelMapper
    private val window_top_r11: ModelMapper
    private val window_end_exterior_1_2: ModelMapper
    private val roof_3_r2: ModelMapper
    private val roof_2_r11: ModelMapper
    private val window_bottom_r12: ModelMapper
    private val window_top_r12: ModelMapper
    private val window_end_exterior_2: ModelMapper
    private val window_end_exterior_2_1: ModelMapper
    private val roof_3_r3: ModelMapper
    private val roof_2_r12: ModelMapper
    private val window_bottom_r13: ModelMapper
    private val window_top_r13: ModelMapper
    private val window_end_exterior_2_2: ModelMapper
    private val roof_4_r1: ModelMapper
    private val roof_3_r4: ModelMapper
    private val window_bottom_r14: ModelMapper
    private val window_top_r14: ModelMapper
    private val window_end_exterior_3: ModelMapper
    private val window_end_exterior_3_1: ModelMapper
    private val roof_4_r2: ModelMapper
    private val roof_3_r5: ModelMapper
    private val window_bottom_r15: ModelMapper
    private val window_top_r15: ModelMapper
    private val window_end_exterior_3_2: ModelMapper
    private val roof_5_r1: ModelMapper
    private val roof_4_r3: ModelMapper
    private val window_bottom_r16: ModelMapper
    private val window_top_r16: ModelMapper
    private val window_end_exterior_4: ModelMapper
    private val window_end_exterior_4_1: ModelMapper
    private val roof_4_r4: ModelMapper
    private val roof_3_r6: ModelMapper
    private val window_bottom_r17: ModelMapper
    private val window_top_r17: ModelMapper
    private val window_end_exterior_4_2: ModelMapper
    private val roof_5_r2: ModelMapper
    private val roof_4_r5: ModelMapper
    private val window_bottom_r18: ModelMapper
    private val window_top_r18: ModelMapper
    private val window_end_exterior_5: ModelMapper
    private val window_end_exterior_5_1: ModelMapper
    private val roof_5_r3: ModelMapper
    private val roof_4_r6: ModelMapper
    private val window_bottom_r19: ModelMapper
    private val window_top_r19: ModelMapper
    private val window_end_exterior_5_2: ModelMapper
    private val roof_6_r1: ModelMapper
    private val roof_5_r4: ModelMapper
    private val window_bottom_r20: ModelMapper
    private val window_top_r20: ModelMapper
    private val window_end_exterior_6: ModelMapper
    private val window_end_exterior_6_1: ModelMapper
    private val roof_6_r2: ModelMapper
    private val roof_5_r5: ModelMapper
    private val window_bottom_r21: ModelMapper
    private val window_top_r21: ModelMapper
    private val window_end_exterior_6_2: ModelMapper
    private val roof_7_r1: ModelMapper
    private val roof_6_r3: ModelMapper
    private val window_bottom_r22: ModelMapper
    private val window_top_r22: ModelMapper
    private val door: ModelMapper
    private val door_1: ModelMapper
    private val window_bottom_2_r1: ModelMapper
    private val window_top_2_r1: ModelMapper
    private val door_2: ModelMapper
    private val window_bottom_3_r1: ModelMapper
    private val window_top_3_r1: ModelMapper
    private val door_exterior: ModelMapper
    private val door_exterior_1: ModelMapper
    private val window_bottom_1_r1: ModelMapper
    private val window_top_1_r1: ModelMapper
    private val door_exterior_2: ModelMapper
    private val window_bottom_2_r2: ModelMapper
    private val window_top_2_r2: ModelMapper
    private val door_exterior_end: ModelMapper
    private val door_exterior_end_1: ModelMapper
    private val window_bottom_2_r3: ModelMapper
    private val window_top_2_r3: ModelMapper
    private val door_exterior_end_2: ModelMapper
    private val window_bottom_3_r2: ModelMapper
    private val window_top_3_r2: ModelMapper
    private val roof_exterior: ModelMapper
    private val end: ModelMapper
    private val end_pillar_4_r1: ModelMapper
    private val end_pillar_3_r1: ModelMapper
    private val end_side_1: ModelMapper
    private val roof_3_r7: ModelMapper
    private val roof_1_r12: ModelMapper
    private val window_bottom_r23: ModelMapper
    private val window_top_r23: ModelMapper
    private val end_side_2: ModelMapper
    private val roof_4_r7: ModelMapper
    private val roof_2_r13: ModelMapper
    private val window_bottom_r24: ModelMapper
    private val window_top_r24: ModelMapper
    private val end_light: ModelMapper
    private val end_translucent: ModelMapper
    private val end_exterior: ModelMapper
    private val end_exterior_side_1: ModelMapper
    private val roof_4_r8: ModelMapper
    private val roof_3_r8: ModelMapper
    private val door_top_r1: ModelMapper
    private val window_bottom_2_r4: ModelMapper
    private val end_exterior_side_2: ModelMapper
    private val roof_5_r6: ModelMapper
    private val roof_4_r9: ModelMapper
    private val door_top_r2: ModelMapper
    private val window_bottom_3_r3: ModelMapper
    private val roof_vent: ModelMapper
    private val roof_vent_side_1: ModelMapper
    private val vent_4_r1: ModelMapper
    private val vent_3_r1: ModelMapper
    private val vent_1_r1: ModelMapper
    private val roof_vent_side_2: ModelMapper
    private val vent_5_r1: ModelMapper
    private val vent_4_r2: ModelMapper
    private val vent_2_r1: ModelMapper
    private val head_exterior: ModelMapper
    private val head_side_1: ModelMapper
    private val roof_4_r10: ModelMapper
    private val roof_3_r9: ModelMapper
    private val window_top_1_r2: ModelMapper
    private val window_bottom_1_r2: ModelMapper
    private val end_r1: ModelMapper
    private val roof_11_r1: ModelMapper
    private val roof_12_r1: ModelMapper
    private val roof_11_r2: ModelMapper
    private val roof_10_r1: ModelMapper
    private val roof_10_r2: ModelMapper
    private val roof_11_r3: ModelMapper
    private val roof_10_r3: ModelMapper
    private val roof_9_r1: ModelMapper
    private val roof_7_r2: ModelMapper
    private val roof_4_r11: ModelMapper
    private val roof_4_r12: ModelMapper
    private val roof_3_r10: ModelMapper
    private val roof_2_r14: ModelMapper
    private val roof_3_r11: ModelMapper
    private val roof_2_r15: ModelMapper
    private val roof_1_r13: ModelMapper
    private val head_side_2: ModelMapper
    private val roof_5_r7: ModelMapper
    private val roof_4_r13: ModelMapper
    private val window_top_2_r4: ModelMapper
    private val window_bottom_2_r5: ModelMapper
    private val end_r2: ModelMapper
    private val roof_12_r2: ModelMapper
    private val roof_13_r1: ModelMapper
    private val roof_12_r3: ModelMapper
    private val roof_11_r4: ModelMapper
    private val roof_11_r5: ModelMapper
    private val roof_12_r4: ModelMapper
    private val roof_11_r6: ModelMapper
    private val roof_10_r4: ModelMapper
    private val roof_8_r1: ModelMapper
    private val roof_5_r8: ModelMapper
    private val roof_5_r9: ModelMapper
    private val roof_4_r14: ModelMapper
    private val roof_3_r12: ModelMapper
    private val roof_4_r15: ModelMapper
    private val roof_3_r13: ModelMapper
    private val roof_2_r16: ModelMapper
    private val middle: ModelMapper
    private val roof_8_r2: ModelMapper
    private val roof_6_r4: ModelMapper
    private val roof_5_r10: ModelMapper
    private val roof_9_r2: ModelMapper
    private val bottom_middle: ModelMapper
    private val bottom_end: ModelMapper
    private val seat: ModelMapper
    private val seat_2_r1: ModelMapper
    private val headlights: ModelMapper
    private val headlight_2_r1: ModelMapper
    private val headlight_1_r1: ModelMapper
    private val tail_lights: ModelMapper
    private val tail_light_2_r1: ModelMapper
    private val tail_light_1_r1: ModelMapper
    private val door_light_off: ModelMapper
    private val door_light_off_r1: ModelMapper
    private val door_light_on: ModelMapper
    private val door_light_on_r1: ModelMapper

    constructor() : this(DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 432
        val textureHeight = 432

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        window = ModelMapper(modelDataWrapper)
        window.setPos(0f, 24f, 0f)
        window.texOffs(230, 139).addBox(-19f, 0f, -15f, 19, 1, 30, 0f, false)
        window.texOffs(82, 201).addBox(-20f, -13f, -15f, 0, 6, 30, 0f, false)
        window.texOffs(30, 53).addBox(-15f, -36f, -15f, 6, 0, 30, 0f, false)
        window.texOffs(0, 0).addBox(-7f, -35f, -15f, 7, 0, 30, 0f, false)
        window.texOffs(237, 36).addBox(-18f, -31f, -15f, 6, 1, 30, 0f, false)

        roof_3_r1 = ModelMapper(modelDataWrapper)
        roof_3_r1.setPos(-7f, -35f, 0f)
        window.addChild(roof_3_r1)
        setRotationAngle(roof_3_r1, 0f, 0f, 0.3491f)
        roof_3_r1.texOffs(14, 0).addBox(-3f, 0f, -15f, 3, 0, 30, 0f, false)

        roof_1_r1 = ModelMapper(modelDataWrapper)
        roof_1_r1.setPos(-15f, -36f, 0f)
        window.addChild(roof_1_r1)
        setRotationAngle(roof_1_r1, 0f, 0f, -0.7854f)
        roof_1_r1.texOffs(49, 0).addBox(-3f, 0f, -15f, 3, 0, 30, 0f, false)

        window_bottom_r1 = ModelMapper(modelDataWrapper)
        window_bottom_r1.setPos(-21f, -7f, 0f)
        window.addChild(window_bottom_r1)
        setRotationAngle(window_bottom_r1, 0f, 0f, -0.2094f)
        window_bottom_r1.texOffs(0, 120).addBox(1f, 0f, -15f, 0, 8, 30, 0f, false)

        window_top_r1 = ModelMapper(modelDataWrapper)
        window_top_r1.setPos(-21f, -13f, 0f)
        window.addChild(window_top_r1)
        setRotationAngle(window_top_r1, 0f, 0f, 0.1396f)
        window_top_r1.texOffs(227, 205).addBox(1f, -22f, -15f, 0, 22, 30, 0f, false)

        window_light = ModelMapper(modelDataWrapper)
        window_light.setPos(0f, 24f, 0f)
        window_light.texOffs(283, 44).addBox(-7f, -35.1f, -15f, 4, 0, 30, 0f, false)

        window_exterior_1 = ModelMapper(modelDataWrapper)
        window_exterior_1.setPos(0f, 24f, 0f)
        window_exterior_1.texOffs(32, 251).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r1 = ModelMapper(modelDataWrapper)
        roof_2_r1.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_1.addChild(roof_2_r1)
        setRotationAngle(roof_2_r1, 0f, 0f, 1.0472f)
        roof_2_r1.texOffs(32, 356).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r2 = ModelMapper(modelDataWrapper)
        roof_1_r2.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_1.addChild(roof_1_r2)
        setRotationAngle(roof_1_r2, 0f, 0f, 0.6981f)
        roof_1_r2.texOffs(299, 311).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r2 = ModelMapper(modelDataWrapper)
        window_bottom_r2.setPos(-21f, -7f, 0f)
        window_exterior_1.addChild(window_bottom_r2)
        setRotationAngle(window_bottom_r2, 0f, 0f, -0.2094f)
        window_bottom_r2.texOffs(203, 313).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r2 = ModelMapper(modelDataWrapper)
        window_top_r2.setPos(-21f, -13f, 0f)
        window_exterior_1.addChild(window_top_r2)
        setRotationAngle(window_top_r2, 0f, 0f, 0.1396f)
        window_top_r2.texOffs(82, 229).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_2 = ModelMapper(modelDataWrapper)
        window_exterior_2.setPos(0f, 24f, 0f)
        window_exterior_2.texOffs(160, 198).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r2 = ModelMapper(modelDataWrapper)
        roof_2_r2.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_2.addChild(roof_2_r2)
        setRotationAngle(roof_2_r2, 0f, 0f, 1.0472f)
        roof_2_r2.texOffs(331, 355).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r3 = ModelMapper(modelDataWrapper)
        roof_1_r3.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_2.addChild(roof_1_r3)
        setRotationAngle(roof_1_r3, 0f, 0f, 0.6981f)
        roof_1_r3.texOffs(299, 307).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r3 = ModelMapper(modelDataWrapper)
        window_bottom_r3.setPos(-21f, -7f, 0f)
        window_exterior_2.addChild(window_bottom_r3)
        setRotationAngle(window_bottom_r3, 0f, 0f, -0.2094f)
        window_bottom_r3.texOffs(313, 54).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r3 = ModelMapper(modelDataWrapper)
        window_top_r3.setPos(-21f, -13f, 0f)
        window_exterior_2.addChild(window_top_r3)
        setRotationAngle(window_top_r3, 0f, 0f, 0.1396f)
        window_top_r3.texOffs(160, 164).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_3 = ModelMapper(modelDataWrapper)
        window_exterior_3.setPos(0f, 24f, 0f)
        window_exterior_3.texOffs(160, 192).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r3 = ModelMapper(modelDataWrapper)
        roof_2_r3.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_3.addChild(roof_2_r3)
        setRotationAngle(roof_2_r3, 0f, 0f, 1.0472f)
        roof_2_r3.texOffs(221, 354).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r4 = ModelMapper(modelDataWrapper)
        roof_1_r4.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_3.addChild(roof_1_r4)
        setRotationAngle(roof_1_r4, 0f, 0f, 0.6981f)
        roof_1_r4.texOffs(299, 303).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r4 = ModelMapper(modelDataWrapper)
        window_bottom_r4.setPos(-21f, -7f, 0f)
        window_exterior_3.addChild(window_bottom_r4)
        setRotationAngle(window_bottom_r4, 0f, 0f, -0.2094f)
        window_bottom_r4.texOffs(0, 312).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r4 = ModelMapper(modelDataWrapper)
        window_top_r4.setPos(-21f, -13f, 0f)
        window_exterior_3.addChild(window_top_r4)
        setRotationAngle(window_top_r4, 0f, 0f, 0.1396f)
        window_top_r4.texOffs(98, 164).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_4 = ModelMapper(modelDataWrapper)
        window_exterior_4.setPos(0f, 24f, 0f)
        window_exterior_4.texOffs(98, 192).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r4 = ModelMapper(modelDataWrapper)
        roof_2_r4.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_4.addChild(roof_2_r4)
        setRotationAngle(roof_2_r4, 0f, 0f, 1.0472f)
        roof_2_r4.texOffs(299, 352).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r5 = ModelMapper(modelDataWrapper)
        roof_1_r5.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_4.addChild(roof_1_r5)
        setRotationAngle(roof_1_r5, 0f, 0f, 0.6981f)
        roof_1_r5.texOffs(299, 299).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r5 = ModelMapper(modelDataWrapper)
        window_bottom_r5.setPos(-21f, -7f, 0f)
        window_exterior_4.addChild(window_bottom_r5)
        setRotationAngle(window_bottom_r5, 0f, 0f, -0.2094f)
        window_bottom_r5.texOffs(94, 310).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r5 = ModelMapper(modelDataWrapper)
        window_top_r5.setPos(-21f, -13f, 0f)
        window_exterior_4.addChild(window_top_r5)
        setRotationAngle(window_top_r5, 0f, 0f, 0.1396f)
        window_top_r5.texOffs(161, 0).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_5 = ModelMapper(modelDataWrapper)
        window_exterior_5.setPos(0f, 24f, 0f)
        window_exterior_5.texOffs(0, 192).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r5 = ModelMapper(modelDataWrapper)
        roof_2_r5.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_5.addChild(roof_2_r5)
        setRotationAngle(roof_2_r5, 0f, 0f, 1.0472f)
        roof_2_r5.texOffs(189, 351).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r6 = ModelMapper(modelDataWrapper)
        roof_1_r6.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_5.addChild(roof_1_r6)
        setRotationAngle(roof_1_r6, 0f, 0f, 0.6981f)
        roof_1_r6.texOffs(297, 256).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r6 = ModelMapper(modelDataWrapper)
        window_bottom_r6.setPos(-21f, -7f, 0f)
        window_exterior_5.addChild(window_bottom_r6)
        setRotationAngle(window_bottom_r6, 0f, 0f, -0.2094f)
        window_bottom_r6.texOffs(298, 147).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r6 = ModelMapper(modelDataWrapper)
        window_top_r6.setPos(-21f, -13f, 0f)
        window_exterior_5.addChild(window_top_r6)
        setRotationAngle(window_top_r6, 0f, 0f, 0.1396f)
        window_top_r6.texOffs(160, 142).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_6 = ModelMapper(modelDataWrapper)
        window_exterior_6.setPos(0f, 24f, 0f)
        window_exterior_6.texOffs(160, 186).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r6 = ModelMapper(modelDataWrapper)
        roof_2_r6.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_6.addChild(roof_2_r6)
        setRotationAngle(roof_2_r6, 0f, 0f, 1.0472f)
        roof_2_r6.texOffs(0, 350).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r7 = ModelMapper(modelDataWrapper)
        roof_1_r7.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_6.addChild(roof_1_r7)
        setRotationAngle(roof_1_r7, 0f, 0f, 0.6981f)
        roof_1_r7.texOffs(158, 290).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r7 = ModelMapper(modelDataWrapper)
        window_bottom_r7.setPos(-21f, -7f, 0f)
        window_exterior_6.addChild(window_bottom_r7)
        setRotationAngle(window_bottom_r7, 0f, 0f, -0.2094f)
        window_bottom_r7.texOffs(298, 109).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r7 = ModelMapper(modelDataWrapper)
        window_top_r7.setPos(-21f, -13f, 0f)
        window_exterior_6.addChild(window_top_r7)
        setRotationAngle(window_top_r7, 0f, 0f, 0.1396f)
        window_top_r7.texOffs(158, 100).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_7 = ModelMapper(modelDataWrapper)
        window_exterior_7.setPos(0f, 24f, 0f)
        window_exterior_7.texOffs(98, 186).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r7 = ModelMapper(modelDataWrapper)
        roof_2_r7.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_7.addChild(roof_2_r7)
        setRotationAngle(roof_2_r7, 0f, 0f, 1.0472f)
        roof_2_r7.texOffs(92, 348).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r8 = ModelMapper(modelDataWrapper)
        roof_1_r8.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_7.addChild(roof_1_r8)
        setRotationAngle(roof_1_r8, 0f, 0f, 0.6981f)
        roof_1_r8.texOffs(158, 286).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r8 = ModelMapper(modelDataWrapper)
        window_bottom_r8.setPos(-21f, -7f, 0f)
        window_exterior_7.addChild(window_bottom_r8)
        setRotationAngle(window_bottom_r8, 0f, 0f, -0.2094f)
        window_bottom_r8.texOffs(297, 291).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r8 = ModelMapper(modelDataWrapper)
        window_top_r8.setPos(-21f, -13f, 0f)
        window_exterior_7.addChild(window_top_r8)
        setRotationAngle(window_top_r8, 0f, 0f, 0.1396f)
        window_top_r8.texOffs(158, 78).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_8 = ModelMapper(modelDataWrapper)
        window_exterior_8.setPos(0f, 24f, 0f)
        window_exterior_8.texOffs(0, 186).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r8 = ModelMapper(modelDataWrapper)
        roof_2_r8.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_8.addChild(roof_2_r8)
        setRotationAngle(roof_2_r8, 0f, 0f, 1.0472f)
        roof_2_r8.texOffs(345, 33).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r9 = ModelMapper(modelDataWrapper)
        roof_1_r9.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_8.addChild(roof_1_r9)
        setRotationAngle(roof_1_r9, 0f, 0f, 0.6981f)
        roof_1_r9.texOffs(281, 70).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r9 = ModelMapper(modelDataWrapper)
        window_bottom_r9.setPos(-21f, -7f, 0f)
        window_exterior_8.addChild(window_bottom_r9)
        setRotationAngle(window_bottom_r9, 0f, 0f, -0.2094f)
        window_bottom_r9.texOffs(296, 245).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r9 = ModelMapper(modelDataWrapper)
        window_top_r9.setPos(-21f, -13f, 0f)
        window_exterior_8.addChild(window_top_r9)
        setRotationAngle(window_top_r9, 0f, 0f, 0.1396f)
        window_top_r9.texOffs(158, 56).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_exterior_9 = ModelMapper(modelDataWrapper)
        window_exterior_9.setPos(0f, 24f, 0f)
        window_exterior_9.texOffs(0, 180).addBox(-21f, -13f, -15f, 0, 6, 30, 0f, false)

        roof_2_r9 = ModelMapper(modelDataWrapper)
        roof_2_r9.setPos(-10.1709f, -40.8501f, 0f)
        window_exterior_9.addChild(roof_2_r9)
        setRotationAngle(roof_2_r9, 0f, 0f, 1.0472f)
        roof_2_r9.texOffs(330, 125).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r10 = ModelMapper(modelDataWrapper)
        roof_1_r10.setPos(-17.9382f, -34.7859f, 0f)
        window_exterior_9.addChild(roof_1_r10)
        setRotationAngle(roof_1_r10, 0f, 0f, 0.6981f)
        roof_1_r10.texOffs(281, 66).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r10 = ModelMapper(modelDataWrapper)
        window_bottom_r10.setPos(-21f, -7f, 0f)
        window_exterior_9.addChild(window_bottom_r10)
        setRotationAngle(window_bottom_r10, 0f, 0f, -0.2094f)
        window_bottom_r10.texOffs(296, 205).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r10 = ModelMapper(modelDataWrapper)
        window_top_r10.setPos(-21f, -13f, 0f)
        window_exterior_9.addChild(window_top_r10)
        setRotationAngle(window_top_r10, 0f, 0f, 0.1396f)
        window_top_r10.texOffs(0, 158).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_1.setPos(0f, 24f, 0f)


        window_end_exterior_1_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_1.addChild(window_end_exterior_1_1)
        window_end_exterior_1_1.texOffs(161, 46).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_2_r10 = ModelMapper(modelDataWrapper)
        roof_2_r10.setPos(0f, 0f, 0f)
        window_end_exterior_1_1.addChild(roof_2_r10)
        setRotationAngle(roof_2_r10, 0f, 0f, 1.0472f)
        roof_2_r10.texOffs(330, 92).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_1_r11 = ModelMapper(modelDataWrapper)
        roof_1_r11.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_1_1.addChild(roof_1_r11)
        setRotationAngle(roof_1_r11, 0f, 0f, 0.6981f)
        roof_1_r11.texOffs(281, 62).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r11 = ModelMapper(modelDataWrapper)
        window_bottom_r11.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_1_1.addChild(window_bottom_r11)
        setRotationAngle(window_bottom_r11, 0f, 0f, -0.2094f)
        window_bottom_r11.texOffs(287, 6).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r11 = ModelMapper(modelDataWrapper)
        window_top_r11.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_1_1.addChild(window_top_r11)
        setRotationAngle(window_top_r11, 0f, 0f, 0.1396f)
        window_top_r11.texOffs(98, 142).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_1_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_1_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_1.addChild(window_end_exterior_1_2)
        window_end_exterior_1_2.texOffs(161, 46).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_3_r2 = ModelMapper(modelDataWrapper)
        roof_3_r2.setPos(0f, 0f, 0f)
        window_end_exterior_1_2.addChild(roof_3_r2)
        setRotationAngle(roof_3_r2, 0f, 0f, -1.0472f)
        roof_3_r2.texOffs(330, 92).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_2_r11 = ModelMapper(modelDataWrapper)
        roof_2_r11.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_1_2.addChild(roof_2_r11)
        setRotationAngle(roof_2_r11, 0f, 0f, -0.6981f)
        roof_2_r11.texOffs(281, 62).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r12 = ModelMapper(modelDataWrapper)
        window_bottom_r12.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_1_2.addChild(window_bottom_r12)
        setRotationAngle(window_bottom_r12, 0f, 0f, 0.2094f)
        window_bottom_r12.texOffs(287, 6).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r12 = ModelMapper(modelDataWrapper)
        window_top_r12.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_1_2.addChild(window_top_r12)
        setRotationAngle(window_top_r12, 0f, 0f, -0.1396f)
        window_top_r12.texOffs(98, 142).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        window_end_exterior_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_2.setPos(0f, 24f, 0f)


        window_end_exterior_2_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_2_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_2.addChild(window_end_exterior_2_1)
        window_end_exterior_2_1.texOffs(161, 34).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_3_r3 = ModelMapper(modelDataWrapper)
        roof_3_r3.setPos(0f, 0f, 0f)
        window_end_exterior_2_1.addChild(roof_3_r3)
        setRotationAngle(roof_3_r3, 0f, 0f, 1.0472f)
        roof_3_r3.texOffs(329, 319).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_2_r12 = ModelMapper(modelDataWrapper)
        roof_2_r12.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_2_1.addChild(roof_2_r12)
        setRotationAngle(roof_2_r12, 0f, 0f, 0.6981f)
        roof_2_r12.texOffs(278, 167).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r13 = ModelMapper(modelDataWrapper)
        window_bottom_r13.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_2_1.addChild(window_bottom_r13)
        setRotationAngle(window_bottom_r13, 0f, 0f, -0.2094f)
        window_bottom_r13.texOffs(265, 283).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r13 = ModelMapper(modelDataWrapper)
        window_top_r13.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_2_1.addChild(window_top_r13)
        setRotationAngle(window_top_r13, 0f, 0f, 0.1396f)
        window_top_r13.texOffs(0, 136).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_2_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_2_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_2.addChild(window_end_exterior_2_2)
        window_end_exterior_2_2.texOffs(161, 34).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_4_r1 = ModelMapper(modelDataWrapper)
        roof_4_r1.setPos(0f, 0f, 0f)
        window_end_exterior_2_2.addChild(roof_4_r1)
        setRotationAngle(roof_4_r1, 0f, 0f, -1.0472f)
        roof_4_r1.texOffs(329, 319).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_3_r4 = ModelMapper(modelDataWrapper)
        roof_3_r4.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_2_2.addChild(roof_3_r4)
        setRotationAngle(roof_3_r4, 0f, 0f, -0.6981f)
        roof_3_r4.texOffs(278, 167).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r14 = ModelMapper(modelDataWrapper)
        window_bottom_r14.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_2_2.addChild(window_bottom_r14)
        setRotationAngle(window_bottom_r14, 0f, 0f, 0.2094f)
        window_bottom_r14.texOffs(265, 283).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r14 = ModelMapper(modelDataWrapper)
        window_top_r14.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_2_2.addChild(window_top_r14)
        setRotationAngle(window_top_r14, 0f, 0f, -0.1396f)
        window_top_r14.texOffs(0, 136).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        window_end_exterior_3 = ModelMapper(modelDataWrapper)
        window_end_exterior_3.setPos(0f, 24f, 0f)


        window_end_exterior_3_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_3_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_3.addChild(window_end_exterior_3_1)
        window_end_exterior_3_1.texOffs(161, 28).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_4_r2 = ModelMapper(modelDataWrapper)
        roof_4_r2.setPos(0f, 0f, 0f)
        window_end_exterior_3_1.addChild(roof_4_r2)
        setRotationAngle(roof_4_r2, 0f, 0f, 1.0472f)
        roof_4_r2.texOffs(329, 286).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_3_r5 = ModelMapper(modelDataWrapper)
        roof_3_r5.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_3_1.addChild(roof_3_r5)
        setRotationAngle(roof_3_r5, 0f, 0f, 0.6981f)
        roof_3_r5.texOffs(278, 163).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r15 = ModelMapper(modelDataWrapper)
        window_bottom_r15.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_3_1.addChild(window_bottom_r15)
        setRotationAngle(window_bottom_r15, 0f, 0f, -0.2094f)
        window_bottom_r15.texOffs(62, 281).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r15 = ModelMapper(modelDataWrapper)
        window_top_r15.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_3_1.addChild(window_top_r15)
        setRotationAngle(window_top_r15, 0f, 0f, 0.1396f)
        window_top_r15.texOffs(74, 100).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_3_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_3_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_3.addChild(window_end_exterior_3_2)
        window_end_exterior_3_2.texOffs(161, 28).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_5_r1 = ModelMapper(modelDataWrapper)
        roof_5_r1.setPos(0f, 0f, 0f)
        window_end_exterior_3_2.addChild(roof_5_r1)
        setRotationAngle(roof_5_r1, 0f, 0f, -1.0472f)
        roof_5_r1.texOffs(329, 286).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_4_r3 = ModelMapper(modelDataWrapper)
        roof_4_r3.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_3_2.addChild(roof_4_r3)
        setRotationAngle(roof_4_r3, 0f, 0f, -0.6981f)
        roof_4_r3.texOffs(278, 163).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r16 = ModelMapper(modelDataWrapper)
        window_bottom_r16.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_3_2.addChild(window_bottom_r16)
        setRotationAngle(window_bottom_r16, 0f, 0f, 0.2094f)
        window_bottom_r16.texOffs(62, 281).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r16 = ModelMapper(modelDataWrapper)
        window_top_r16.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_3_2.addChild(window_top_r16)
        setRotationAngle(window_top_r16, 0f, 0f, -0.1396f)
        window_top_r16.texOffs(74, 100).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        window_end_exterior_4 = ModelMapper(modelDataWrapper)
        window_end_exterior_4.setPos(0f, 24f, 0f)


        window_end_exterior_4_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_4_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_4.addChild(window_end_exterior_4_1)
        window_end_exterior_4_1.texOffs(161, 22).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_4_r4 = ModelMapper(modelDataWrapper)
        roof_4_r4.setPos(0f, 0f, 0f)
        window_end_exterior_4_1.addChild(roof_4_r4)
        setRotationAngle(roof_4_r4, 0f, 0f, 1.0472f)
        roof_4_r4.texOffs(267, 329).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_3_r6 = ModelMapper(modelDataWrapper)
        roof_3_r6.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_4_1.addChild(roof_3_r6)
        setRotationAngle(roof_3_r6, 0f, 0f, 0.6981f)
        roof_3_r6.texOffs(278, 159).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r17 = ModelMapper(modelDataWrapper)
        window_bottom_r17.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_4_1.addChild(window_bottom_r17)
        setRotationAngle(window_bottom_r17, 0f, 0f, -0.2094f)
        window_bottom_r17.texOffs(281, 46).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r17 = ModelMapper(modelDataWrapper)
        window_top_r17.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_4_1.addChild(window_top_r17)
        setRotationAngle(window_top_r17, 0f, 0f, 0.1396f)
        window_top_r17.texOffs(0, 86).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_4_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_4_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_4.addChild(window_end_exterior_4_2)
        window_end_exterior_4_2.texOffs(161, 22).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_5_r2 = ModelMapper(modelDataWrapper)
        roof_5_r2.setPos(0f, 0f, 0f)
        window_end_exterior_4_2.addChild(roof_5_r2)
        setRotationAngle(roof_5_r2, 0f, 0f, -1.0472f)
        roof_5_r2.texOffs(267, 329).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_4_r5 = ModelMapper(modelDataWrapper)
        roof_4_r5.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_4_2.addChild(roof_4_r5)
        setRotationAngle(roof_4_r5, 0f, 0f, -0.6981f)
        roof_4_r5.texOffs(278, 159).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r18 = ModelMapper(modelDataWrapper)
        window_bottom_r18.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_4_2.addChild(window_bottom_r18)
        setRotationAngle(window_bottom_r18, 0f, 0f, 0.2094f)
        window_bottom_r18.texOffs(281, 46).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r18 = ModelMapper(modelDataWrapper)
        window_top_r18.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_4_2.addChild(window_top_r18)
        setRotationAngle(window_top_r18, 0f, 0f, -0.1396f)
        window_top_r18.texOffs(0, 86).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        window_end_exterior_5 = ModelMapper(modelDataWrapper)
        window_end_exterior_5.setPos(0f, 24f, 0f)


        window_end_exterior_5_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_5_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_5.addChild(window_end_exterior_5_1)
        window_end_exterior_5_1.texOffs(74, 122).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_5_r3 = ModelMapper(modelDataWrapper)
        roof_5_r3.setPos(0f, 0f, 0f)
        window_end_exterior_5_1.addChild(roof_5_r3)
        setRotationAngle(roof_5_r3, 0f, 0f, 1.0472f)
        roof_5_r3.texOffs(328, 253).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_4_r6 = ModelMapper(modelDataWrapper)
        roof_4_r6.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_5_1.addChild(roof_4_r6)
        setRotationAngle(roof_4_r6, 0f, 0f, 0.6981f)
        roof_4_r6.texOffs(278, 155).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r19 = ModelMapper(modelDataWrapper)
        window_bottom_r19.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_5_1.addChild(window_bottom_r19)
        setRotationAngle(window_bottom_r19, 0f, 0f, -0.2094f)
        window_bottom_r19.texOffs(233, 275).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r19 = ModelMapper(modelDataWrapper)
        window_top_r19.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_5_1.addChild(window_top_r19)
        setRotationAngle(window_top_r19, 0f, 0f, 0.1396f)
        window_top_r19.texOffs(74, 78).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_5_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_5_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_5.addChild(window_end_exterior_5_2)
        window_end_exterior_5_2.texOffs(74, 122).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_6_r1 = ModelMapper(modelDataWrapper)
        roof_6_r1.setPos(0f, 0f, 0f)
        window_end_exterior_5_2.addChild(roof_6_r1)
        setRotationAngle(roof_6_r1, 0f, 0f, -1.0472f)
        roof_6_r1.texOffs(328, 253).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_5_r4 = ModelMapper(modelDataWrapper)
        roof_5_r4.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_5_2.addChild(roof_5_r4)
        setRotationAngle(roof_5_r4, 0f, 0f, -0.6981f)
        roof_5_r4.texOffs(278, 155).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r20 = ModelMapper(modelDataWrapper)
        window_bottom_r20.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_5_2.addChild(window_bottom_r20)
        setRotationAngle(window_bottom_r20, 0f, 0f, 0.2094f)
        window_bottom_r20.texOffs(233, 275).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r20 = ModelMapper(modelDataWrapper)
        window_top_r20.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_5_2.addChild(window_top_r20)
        setRotationAngle(window_top_r20, 0f, 0f, -0.1396f)
        window_top_r20.texOffs(74, 78).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        window_end_exterior_6 = ModelMapper(modelDataWrapper)
        window_end_exterior_6.setPos(0f, 24f, 0f)


        window_end_exterior_6_1 = ModelMapper(modelDataWrapper)
        window_end_exterior_6_1.setPos(-10.1709f, -40.8501f, 0f)
        window_end_exterior_6.addChild(window_end_exterior_6_1)
        window_end_exterior_6_1.texOffs(0, 49).addBox(-10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, false)

        roof_6_r2 = ModelMapper(modelDataWrapper)
        roof_6_r2.setPos(0f, 0f, 0f)
        window_end_exterior_6_1.addChild(roof_6_r2)
        setRotationAngle(roof_6_r2, 0f, 0f, 1.0472f)
        roof_6_r2.texOffs(328, 218).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)

        roof_5_r5 = ModelMapper(modelDataWrapper)
        roof_5_r5.setPos(-7.7673f, 6.0642f, 0f)
        window_end_exterior_6_1.addChild(roof_5_r5)
        setRotationAngle(roof_5_r5, 0f, 0f, 0.6981f)
        roof_5_r5.texOffs(32, 269).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)

        window_bottom_r21 = ModelMapper(modelDataWrapper)
        window_bottom_r21.setPos(-10.8291f, 33.8501f, 0f)
        window_end_exterior_6_1.addChild(window_bottom_r21)
        setRotationAngle(window_bottom_r21, 0f, 0f, -0.2094f)
        window_bottom_r21.texOffs(0, 274).addBox(0f, 0f, -15f, 1, 8, 30, 0f, false)

        window_top_r21 = ModelMapper(modelDataWrapper)
        window_top_r21.setPos(-10.8291f, 27.8501f, 0f)
        window_end_exterior_6_1.addChild(window_top_r21)
        setRotationAngle(window_top_r21, 0f, 0f, 0.1396f)
        window_top_r21.texOffs(74, 56).addBox(0f, -22f, -15f, 0, 22, 30, 0f, false)

        window_end_exterior_6_2 = ModelMapper(modelDataWrapper)
        window_end_exterior_6_2.setPos(10.1709f, -40.8501f, 0f)
        window_end_exterior_6.addChild(window_end_exterior_6_2)
        window_end_exterior_6_2.texOffs(0, 49).addBox(10.8291f, 27.8501f, -15f, 0, 6, 30, 0f, true)

        roof_7_r1 = ModelMapper(modelDataWrapper)
        roof_7_r1.setPos(0f, 0f, 0f)
        window_end_exterior_6_2.addChild(roof_7_r1)
        setRotationAngle(roof_7_r1, 0f, 0f, -1.0472f)
        roof_7_r1.texOffs(328, 218).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)

        roof_6_r3 = ModelMapper(modelDataWrapper)
        roof_6_r3.setPos(7.7673f, 6.0642f, 0f)
        window_end_exterior_6_2.addChild(roof_6_r3)
        setRotationAngle(roof_6_r3, 0f, 0f, -0.6981f)
        roof_6_r3.texOffs(32, 269).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)

        window_bottom_r22 = ModelMapper(modelDataWrapper)
        window_bottom_r22.setPos(10.8291f, 33.8501f, 0f)
        window_end_exterior_6_2.addChild(window_bottom_r22)
        setRotationAngle(window_bottom_r22, 0f, 0f, 0.2094f)
        window_bottom_r22.texOffs(0, 274).addBox(-1f, 0f, -15f, 1, 8, 30, 0f, true)

        window_top_r22 = ModelMapper(modelDataWrapper)
        window_top_r22.setPos(10.8291f, 27.8501f, 0f)
        window_end_exterior_6_2.addChild(window_top_r22)
        setRotationAngle(window_top_r22, 0f, 0f, -0.1396f)
        window_top_r22.texOffs(74, 56).addBox(0f, -22f, -15f, 0, 22, 30, 0f, true)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)


        door_1 = ModelMapper(modelDataWrapper)
        door_1.setPos(0f, 0f, 0f)
        door.addChild(door_1)
        door_1.texOffs(230, 86).addBox(-20f, -13f, 3f, 1, 6, 13, 0f, false)

        window_bottom_2_r1 = ModelMapper(modelDataWrapper)
        window_bottom_2_r1.setPos(-20f, -7f, 0f)
        door_1.addChild(window_bottom_2_r1)
        setRotationAngle(window_bottom_2_r1, 0f, 0f, -0.2094f)
        window_bottom_2_r1.texOffs(199, 0).addBox(0f, 0f, 3f, 1, 8, 13, 0f, false)

        window_top_2_r1 = ModelMapper(modelDataWrapper)
        window_top_2_r1.setPos(-20f, -13f, 0f)
        door_1.addChild(window_top_2_r1)
        setRotationAngle(window_top_2_r1, 0f, 0f, 0.1396f)
        window_top_2_r1.texOffs(390, 235).addBox(0f, -21f, 3f, 1, 21, 13, 0f, false)

        door_2 = ModelMapper(modelDataWrapper)
        door_2.setPos(0f, 0f, 0f)
        door.addChild(door_2)
        door_2.texOffs(230, 86).addBox(19f, -13f, 3f, 1, 6, 13, 0f, true)

        window_bottom_3_r1 = ModelMapper(modelDataWrapper)
        window_bottom_3_r1.setPos(20f, -7f, 0f)
        door_2.addChild(window_bottom_3_r1)
        setRotationAngle(window_bottom_3_r1, 0f, 0f, 0.2094f)
        window_bottom_3_r1.texOffs(199, 0).addBox(-1f, 0f, 3f, 1, 8, 13, 0f, true)

        window_top_3_r1 = ModelMapper(modelDataWrapper)
        window_top_3_r1.setPos(20f, -13f, 0f)
        door_2.addChild(window_top_3_r1)
        setRotationAngle(window_top_3_r1, 0f, 0f, -0.1396f)
        window_top_3_r1.texOffs(390, 235).addBox(-1f, -21f, 3f, 1, 21, 13, 0f, true)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)


        door_exterior_1 = ModelMapper(modelDataWrapper)
        door_exterior_1.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_exterior_1)
        door_exterior_1.texOffs(221, 63).addBox(-20f, -13f, 3f, 0, 6, 13, 0f, false)

        window_bottom_1_r1 = ModelMapper(modelDataWrapper)
        window_bottom_1_r1.setPos(-20f, -7f, 0f)
        door_exterior_1.addChild(window_bottom_1_r1)
        setRotationAngle(window_bottom_1_r1, 0f, 0f, -0.2094f)
        window_bottom_1_r1.texOffs(199, 8).addBox(0f, 0f, 3f, 0, 8, 13, 0f, false)

        window_top_1_r1 = ModelMapper(modelDataWrapper)
        window_top_1_r1.setPos(-20f, -13f, 0f)
        door_exterior_1.addChild(window_top_1_r1)
        setRotationAngle(window_top_1_r1, 0f, 0f, 0.1396f)
        window_top_1_r1.texOffs(227, 159).addBox(0f, -21f, 3f, 0, 21, 13, 0f, false)

        door_exterior_2 = ModelMapper(modelDataWrapper)
        door_exterior_2.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_exterior_2)
        door_exterior_2.texOffs(221, 63).addBox(20f, -13f, 3f, 0, 6, 13, 0f, true)

        window_bottom_2_r2 = ModelMapper(modelDataWrapper)
        window_bottom_2_r2.setPos(20f, -7f, 0f)
        door_exterior_2.addChild(window_bottom_2_r2)
        setRotationAngle(window_bottom_2_r2, 0f, 0f, 0.2094f)
        window_bottom_2_r2.texOffs(199, 8).addBox(0f, 0f, 3f, 0, 8, 13, 0f, true)

        window_top_2_r2 = ModelMapper(modelDataWrapper)
        window_top_2_r2.setPos(20f, -13f, 0f)
        door_exterior_2.addChild(window_top_2_r2)
        setRotationAngle(window_top_2_r2, 0f, 0f, -0.1396f)
        window_top_2_r2.texOffs(227, 159).addBox(0f, -21f, 3f, 0, 21, 13, 0f, true)

        door_exterior_end = ModelMapper(modelDataWrapper)
        door_exterior_end.setPos(0f, 24f, 0f)


        door_exterior_end_1 = ModelMapper(modelDataWrapper)
        door_exterior_end_1.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_exterior_end_1)
        door_exterior_end_1.texOffs(0, 11).addBox(-20f, -13f, 3f, 0, 6, 13, 0f, false)

        window_bottom_2_r3 = ModelMapper(modelDataWrapper)
        window_bottom_2_r3.setPos(-20f, -7f, 0f)
        door_exterior_end_1.addChild(window_bottom_2_r3)
        setRotationAngle(window_bottom_2_r3, 0f, 0f, -0.2094f)
        window_bottom_2_r3.texOffs(0, 94).addBox(0f, 0f, 3f, 0, 8, 13, 0f, false)

        window_top_2_r3 = ModelMapper(modelDataWrapper)
        window_top_2_r3.setPos(-20f, -13f, 0f)
        door_exterior_end_1.addChild(window_top_2_r3)
        setRotationAngle(window_top_2_r3, 0f, 0f, 0.1396f)
        window_top_2_r3.texOffs(0, 73).addBox(0f, -21f, 3f, 0, 21, 13, 0f, false)

        door_exterior_end_2 = ModelMapper(modelDataWrapper)
        door_exterior_end_2.setPos(0f, 0f, 0f)
        door_exterior_end.addChild(door_exterior_end_2)
        door_exterior_end_2.texOffs(0, 11).addBox(20f, -13f, 3f, 0, 6, 13, 0f, true)

        window_bottom_3_r2 = ModelMapper(modelDataWrapper)
        window_bottom_3_r2.setPos(20f, -7f, 0f)
        door_exterior_end_2.addChild(window_bottom_3_r2)
        setRotationAngle(window_bottom_3_r2, 0f, 0f, 0.2094f)
        window_bottom_3_r2.texOffs(0, 94).addBox(0f, 0f, 3f, 0, 8, 13, 0f, true)

        window_top_3_r2 = ModelMapper(modelDataWrapper)
        window_top_3_r2.setPos(20f, -13f, 0f)
        door_exterior_end_2.addChild(window_top_3_r2)
        setRotationAngle(window_top_3_r2, 0f, 0f, -0.1396f)
        window_top_3_r2.texOffs(0, 73).addBox(0f, -21f, 3f, 0, 21, 13, 0f, true)

        roof_exterior = ModelMapper(modelDataWrapper)
        roof_exterior.setPos(0f, 24f, 0f)
        roof_exterior.texOffs(0, 86).addBox(-13f, -39f, -15f, 13, 0, 30, 0f, false)
        roof_exterior.texOffs(360, 193).addBox(-19f, 0f, -15f, 1, 2, 30, 0f, false)

        end = ModelMapper(modelDataWrapper)
        end.setPos(0f, 24f, 0f)
        end.texOffs(230, 76).addBox(9.5f, -34f, 16.1f, 11, 34, 29, 0f, false)
        end.texOffs(310, 104).addBox(11f, -34f, 2.9f, 9, 34, 0, 0f, false)
        end.texOffs(227, 172).addBox(-20.5f, -34f, 16.1f, 11, 34, 29, 0f, false)
        end.texOffs(307, 201).addBox(-20f, -34f, 2.9f, 9, 34, 0, 0f, false)
        end.texOffs(237, 0).addBox(-20f, -36f, -8f, 40, 36, 0, 0f, false)
        end.texOffs(381, 0).addBox(-10f, -34f, 45f, 20, 34, 0, 0f, false)

        end_pillar_4_r1 = ModelMapper(modelDataWrapper)
        end_pillar_4_r1.setPos(-11f, 0f, 2.9f)
        end.addChild(end_pillar_4_r1)
        setRotationAngle(end_pillar_4_r1, 0f, 1.2217f, 0f)
        end_pillar_4_r1.texOffs(94, 381).addBox(0f, -34f, 0f, 12, 34, 0, 0f, false)

        end_pillar_3_r1 = ModelMapper(modelDataWrapper)
        end_pillar_3_r1.setPos(11f, 0f, 2.9f)
        end.addChild(end_pillar_3_r1)
        setRotationAngle(end_pillar_3_r1, 0f, -1.2217f, 0f)
        end_pillar_3_r1.texOffs(0, 383).addBox(-12f, -34f, 0f, 12, 34, 0, 0f, false)

        end_side_1 = ModelMapper(modelDataWrapper)
        end_side_1.setPos(0f, 0f, 0f)
        end.addChild(end_side_1)
        end_side_1.texOffs(0, 0).addBox(-13f, -34f, -8f, 13, 0, 53, 0f, false)
        end_side_1.texOffs(0, 170).addBox(-19f, 0f, -15f, 19, 1, 60, 0f, false)
        end_side_1.texOffs(189, 324).addBox(-17f, -34f, 3f, 4, 1, 13, 0f, false)
        end_side_1.texOffs(214, 0).addBox(-20f, -13f, -15f, 0, 6, 7, 0f, false)
        end_side_1.texOffs(9, 7).addBox(-15f, -36f, -15f, 6, 0, 7, 0f, false)
        end_side_1.texOffs(9, 0).addBox(-7f, -35f, -15f, 7, 0, 7, 0f, false)
        end_side_1.texOffs(227, 193).addBox(-18f, -31f, -15f, 6, 1, 7, 0f, false)

        roof_3_r7 = ModelMapper(modelDataWrapper)
        roof_3_r7.setPos(-7f, -35f, 0f)
        end_side_1.addChild(roof_3_r7)
        setRotationAngle(roof_3_r7, 0f, 0f, 0.3491f)
        roof_3_r7.texOffs(0, 0).addBox(-3f, 0f, -15f, 3, 0, 7, 0f, false)

        roof_1_r12 = ModelMapper(modelDataWrapper)
        roof_1_r12.setPos(-15f, -36f, 0f)
        end_side_1.addChild(roof_1_r12)
        setRotationAngle(roof_1_r12, 0f, 0f, -0.7854f)
        roof_1_r12.texOffs(0, 7).addBox(-3f, 0f, -15f, 3, 0, 7, 0f, false)

        window_bottom_r23 = ModelMapper(modelDataWrapper)
        window_bottom_r23.setPos(-21f, -7f, 0f)
        end_side_1.addChild(window_bottom_r23)
        setRotationAngle(window_bottom_r23, 0f, 0f, -0.2094f)
        window_bottom_r23.texOffs(56, 101).addBox(1f, 0f, -15f, 0, 8, 7, 0f, false)

        window_top_r23 = ModelMapper(modelDataWrapper)
        window_top_r23.setPos(-21f, -13f, 0f)
        end_side_1.addChild(window_top_r23)
        setRotationAngle(window_top_r23, 0f, 0f, 0.1396f)
        window_top_r23.texOffs(56, 79).addBox(1f, -22f, -15f, 0, 22, 7, 0f, false)
        window_top_r23.texOffs(237, 36).addBox(1f, -22f, 3f, 2, 1, 13, 0f, false)

        end_side_2 = ModelMapper(modelDataWrapper)
        end_side_2.setPos(0f, 0f, 0f)
        end.addChild(end_side_2)
        end_side_2.texOffs(0, 0).addBox(0f, -34f, -8f, 13, 0, 53, 0f, true)
        end_side_2.texOffs(0, 170).addBox(0f, 0f, -15f, 19, 1, 60, 0f, true)
        end_side_2.texOffs(189, 324).addBox(13f, -34f, 3f, 4, 1, 13, 0f, true)
        end_side_2.texOffs(214, 0).addBox(20f, -13f, -15f, 0, 6, 7, 0f, true)
        end_side_2.texOffs(9, 7).addBox(9f, -36f, -15f, 6, 0, 7, 0f, true)
        end_side_2.texOffs(9, 0).addBox(0f, -35f, -15f, 7, 0, 7, 0f, true)
        end_side_2.texOffs(227, 193).addBox(12f, -31f, -15f, 6, 1, 7, 0f, true)

        roof_4_r7 = ModelMapper(modelDataWrapper)
        roof_4_r7.setPos(7f, -35f, 0f)
        end_side_2.addChild(roof_4_r7)
        setRotationAngle(roof_4_r7, 0f, 0f, -0.3491f)
        roof_4_r7.texOffs(0, 0).addBox(0f, 0f, -15f, 3, 0, 7, 0f, true)

        roof_2_r13 = ModelMapper(modelDataWrapper)
        roof_2_r13.setPos(15f, -36f, 0f)
        end_side_2.addChild(roof_2_r13)
        setRotationAngle(roof_2_r13, 0f, 0f, 0.7854f)
        roof_2_r13.texOffs(0, 7).addBox(0f, 0f, -15f, 3, 0, 7, 0f, true)

        window_bottom_r24 = ModelMapper(modelDataWrapper)
        window_bottom_r24.setPos(21f, -7f, 0f)
        end_side_2.addChild(window_bottom_r24)
        setRotationAngle(window_bottom_r24, 0f, 0f, 0.2094f)
        window_bottom_r24.texOffs(56, 101).addBox(-1f, 0f, -15f, 0, 8, 7, 0f, true)

        window_top_r24 = ModelMapper(modelDataWrapper)
        window_top_r24.setPos(21f, -13f, 0f)
        end_side_2.addChild(window_top_r24)
        setRotationAngle(window_top_r24, 0f, 0f, -0.1396f)
        window_top_r24.texOffs(56, 79).addBox(-1f, -22f, -15f, 0, 22, 7, 0f, true)
        window_top_r24.texOffs(237, 36).addBox(-3f, -22f, 3f, 2, 1, 13, 0f, true)

        end_light = ModelMapper(modelDataWrapper)
        end_light.setPos(0f, 24f, 0f)
        end_light.texOffs(317, 44).addBox(-7f, -35.1f, -15f, 14, 0, 4, 0f, false)

        end_translucent = ModelMapper(modelDataWrapper)
        end_translucent.setPos(0f, 24f, 0f)
        end_translucent.texOffs(331, 388).addBox(-7f, -34f, -8f, 14, 34, 0, 0f, false)
        end_translucent.texOffs(359, 388).addBox(-7f, -34f, 45f, 14, 34, 0, 0f, false)

        end_exterior = ModelMapper(modelDataWrapper)
        end_exterior.setPos(0f, 24f, 0f)


        end_exterior_side_1 = ModelMapper(modelDataWrapper)
        end_exterior_side_1.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_side_1)
        end_exterior_side_1.texOffs(362, 125).addBox(-21f, -13f, -15f, 1, 6, 18, 0f, false)
        end_exterior_side_1.texOffs(360, 158).addBox(-21f, -13f, 16f, 1, 6, 29, 0f, false)
        end_exterior_side_1.texOffs(325, 188).addBox(-21f, 0f, 3f, 2, 1, 13, 0f, false)
        end_exterior_side_1.texOffs(303, 385).addBox(-20.5f, -34f, 45f, 11, 34, 0, 0f, false)
        end_exterior_side_1.texOffs(32, 274).addBox(-18f, -39f, 45f, 18, 5, 0, 0f, false)

        roof_4_r8 = ModelMapper(modelDataWrapper)
        roof_4_r8.setPos(-10.1709f, -40.8501f, 0f)
        end_exterior_side_1.addChild(roof_4_r8)
        setRotationAngle(roof_4_r8, 0f, 0f, 1.0472f)
        roof_4_r8.texOffs(235, 321).addBox(0f, 3f, -15f, 1, 3, 30, 0f, false)
        roof_4_r8.texOffs(319, 0).addBox(0f, 3f, 15f, 1, 3, 30, 0f, false)

        roof_3_r8 = ModelMapper(modelDataWrapper)
        roof_3_r8.setPos(-17.9382f, -34.7859f, 0f)
        end_exterior_side_1.addChild(roof_3_r8)
        setRotationAngle(roof_3_r8, 0f, 0f, 0.6981f)
        roof_3_r8.texOffs(204, 241).addBox(0f, -4f, -15f, 0, 4, 30, 0f, false)
        roof_3_r8.texOffs(0, 387).addBox(0f, -4f, 15f, 1, 4, 30, 0f, false)

        door_top_r1 = ModelMapper(modelDataWrapper)
        door_top_r1.setPos(-21f, -13f, 0f)
        end_exterior_side_1.addChild(door_top_r1)
        setRotationAngle(door_top_r1, 0f, 0f, 0.1396f)
        door_top_r1.texOffs(265, 275).addBox(0f, -22f, 3f, 1, 1, 13, 0f, false)
        door_top_r1.texOffs(173, 265).addBox(0f, -22f, 16f, 1, 22, 29, 0f, false)
        door_top_r1.texOffs(265, 369).addBox(0f, -22f, -15f, 1, 22, 18, 0f, false)

        window_bottom_2_r4 = ModelMapper(modelDataWrapper)
        window_bottom_2_r4.setPos(-21f, -7f, 0f)
        end_exterior_side_1.addChild(window_bottom_2_r4)
        setRotationAngle(window_bottom_2_r4, 0f, 0f, -0.2094f)
        window_bottom_2_r4.texOffs(127, 319).addBox(0f, 0f, 16f, 1, 8, 29, 0f, false)
        window_bottom_2_r4.texOffs(360, 251).addBox(0f, 0f, -15f, 1, 8, 18, 0f, false)

        end_exterior_side_2 = ModelMapper(modelDataWrapper)
        end_exterior_side_2.setPos(0f, 0f, 0f)
        end_exterior.addChild(end_exterior_side_2)
        end_exterior_side_2.texOffs(362, 125).addBox(20f, -13f, -15f, 1, 6, 18, 0f, true)
        end_exterior_side_2.texOffs(360, 158).addBox(20f, -13f, 16f, 1, 6, 29, 0f, true)
        end_exterior_side_2.texOffs(325, 188).addBox(19f, 0f, 3f, 2, 1, 13, 0f, true)
        end_exterior_side_2.texOffs(303, 385).addBox(9.5f, -34f, 45f, 11, 34, 0, 0f, true)
        end_exterior_side_2.texOffs(32, 274).addBox(0f, -39f, 45f, 18, 5, 0, 0f, true)

        roof_5_r6 = ModelMapper(modelDataWrapper)
        roof_5_r6.setPos(10.1709f, -40.8501f, 0f)
        end_exterior_side_2.addChild(roof_5_r6)
        setRotationAngle(roof_5_r6, 0f, 0f, -1.0472f)
        roof_5_r6.texOffs(235, 321).addBox(-1f, 3f, -15f, 1, 3, 30, 0f, true)
        roof_5_r6.texOffs(319, 0).addBox(-1f, 3f, 15f, 1, 3, 30, 0f, true)

        roof_4_r9 = ModelMapper(modelDataWrapper)
        roof_4_r9.setPos(17.9382f, -34.7859f, 0f)
        end_exterior_side_2.addChild(roof_4_r9)
        setRotationAngle(roof_4_r9, 0f, 0f, -0.6981f)
        roof_4_r9.texOffs(204, 241).addBox(0f, -4f, -15f, 0, 4, 30, 0f, true)
        roof_4_r9.texOffs(0, 387).addBox(-1f, -4f, 15f, 1, 4, 30, 0f, true)

        door_top_r2 = ModelMapper(modelDataWrapper)
        door_top_r2.setPos(21f, -13f, 0f)
        end_exterior_side_2.addChild(door_top_r2)
        setRotationAngle(door_top_r2, 0f, 0f, -0.1396f)
        door_top_r2.texOffs(265, 275).addBox(-1f, -22f, 3f, 1, 1, 13, 0f, true)
        door_top_r2.texOffs(173, 265).addBox(-1f, -22f, 16f, 1, 22, 29, 0f, true)
        door_top_r2.texOffs(265, 369).addBox(-1f, -22f, -15f, 1, 22, 18, 0f, true)

        window_bottom_3_r3 = ModelMapper(modelDataWrapper)
        window_bottom_3_r3.setPos(21f, -7f, 0f)
        end_exterior_side_2.addChild(window_bottom_3_r3)
        setRotationAngle(window_bottom_3_r3, 0f, 0f, 0.2094f)
        window_bottom_3_r3.texOffs(127, 319).addBox(-1f, 0f, 16f, 1, 8, 29, 0f, true)
        window_bottom_3_r3.texOffs(360, 251).addBox(-1f, 0f, -15f, 1, 8, 18, 0f, true)

        roof_vent = ModelMapper(modelDataWrapper)
        roof_vent.setPos(0f, 24f, 0f)


        roof_vent_side_1 = ModelMapper(modelDataWrapper)
        roof_vent_side_1.setPos(0f, -42f, 10f)
        roof_vent.addChild(roof_vent_side_1)
        roof_vent_side_1.texOffs(76, 96).addBox(-6f, 0f, -70f, 6, 4, 70, 0f, false)

        vent_4_r1 = ModelMapper(modelDataWrapper)
        vent_4_r1.setPos(0f, 0f, 0f)
        roof_vent_side_1.addChild(vent_4_r1)
        setRotationAngle(vent_4_r1, -0.6981f, 0f, 0f)
        vent_4_r1.texOffs(67, 53).addBox(-6f, 0f, 0f, 6, 0, 5, 0f, false)

        vent_3_r1 = ModelMapper(modelDataWrapper)
        vent_3_r1.setPos(-6f, 0f, 0f)
        roof_vent_side_1.addChild(vent_3_r1)
        setRotationAngle(vent_3_r1, 0f, 0.6981f, 1.1345f)
        vent_3_r1.texOffs(72, 58).addBox(0f, 0f, 0f, 1, 6, 5, 0f, false)

        vent_1_r1 = ModelMapper(modelDataWrapper)
        vent_1_r1.setPos(-6f, 0f, -10f)
        roof_vent_side_1.addChild(vent_1_r1)
        setRotationAngle(vent_1_r1, 0f, 0f, 1.1345f)
        vent_1_r1.texOffs(161, 0).addBox(0f, 0f, -60f, 3, 6, 70, 0f, false)

        roof_vent_side_2 = ModelMapper(modelDataWrapper)
        roof_vent_side_2.setPos(0f, -42f, 10f)
        roof_vent.addChild(roof_vent_side_2)
        roof_vent_side_2.texOffs(76, 96).addBox(0f, 0f, -70f, 6, 4, 70, 0f, true)

        vent_5_r1 = ModelMapper(modelDataWrapper)
        vent_5_r1.setPos(0f, 0f, 0f)
        roof_vent_side_2.addChild(vent_5_r1)
        setRotationAngle(vent_5_r1, -0.6981f, 0f, 0f)
        vent_5_r1.texOffs(67, 53).addBox(0f, 0f, 0f, 6, 0, 5, 0f, true)

        vent_4_r2 = ModelMapper(modelDataWrapper)
        vent_4_r2.setPos(6f, 0f, 0f)
        roof_vent_side_2.addChild(vent_4_r2)
        setRotationAngle(vent_4_r2, 0f, -0.6981f, -1.1345f)
        vent_4_r2.texOffs(72, 58).addBox(-1f, 0f, 0f, 1, 6, 5, 0f, true)

        vent_2_r1 = ModelMapper(modelDataWrapper)
        vent_2_r1.setPos(6f, 0f, -10f)
        roof_vent_side_2.addChild(vent_2_r1)
        setRotationAngle(vent_2_r1, 0f, 0f, -1.1345f)
        vent_2_r1.texOffs(161, 0).addBox(-3f, 0f, -60f, 3, 6, 70, 0f, true)

        head_exterior = ModelMapper(modelDataWrapper)
        head_exterior.setPos(0f, 24f, 0f)


        head_side_1 = ModelMapper(modelDataWrapper)
        head_side_1.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_side_1)
        head_side_1.texOffs(155, 97).addBox(-19f, 0f, -15f, 1, 2, 73, 0f, false)
        head_side_1.texOffs(158, 192).addBox(-21f, -13f, -15f, 1, 6, 67, 0f, false)
        head_side_1.texOffs(279, 44).addBox(-21f, 0f, -57f, 2, 1, 13, 0f, false)
        head_side_1.texOffs(125, 356).addBox(-21f, -13f, -44f, 1, 6, 29, 0f, false)
        head_side_1.texOffs(361, 286).addBox(-21f, -13f, -75f, 1, 6, 18, 0f, false)

        roof_4_r10 = ModelMapper(modelDataWrapper)
        roof_4_r10.setPos(-10.1709f, -40.8501f, 0f)
        head_side_1.addChild(roof_4_r10)
        setRotationAngle(roof_4_r10, 0f, 0f, 1.0472f)
        roof_4_r10.texOffs(157, 326).addBox(0f, 3f, -75f, 1, 3, 30, 0f, false)
        roof_4_r10.texOffs(328, 185).addBox(0f, 3f, -45f, 1, 3, 30, 0f, false)

        roof_3_r9 = ModelMapper(modelDataWrapper)
        roof_3_r9.setPos(-17.9382f, -34.7859f, 0f)
        head_side_1.addChild(roof_3_r9)
        setRotationAngle(roof_3_r9, 0f, 0f, 0.6981f)
        roof_3_r9.texOffs(32, 261).addBox(0f, -4f, -75f, 0, 4, 30, 0f, false)
        roof_3_r9.texOffs(32, 265).addBox(0f, -4f, -45f, 0, 4, 30, 0f, false)

        window_top_1_r2 = ModelMapper(modelDataWrapper)
        window_top_1_r2.setPos(-21f, -13f, 0f)
        head_side_1.addChild(window_top_1_r2)
        setRotationAngle(window_top_1_r2, 0f, 0f, 0.1396f)
        window_top_1_r2.texOffs(362, 74).addBox(0f, -22f, -75f, 1, 22, 18, 0f, false)
        window_top_1_r2.texOffs(113, 259).addBox(0f, -22f, -44f, 1, 22, 29, 0f, false)
        window_top_1_r2.texOffs(237, 50).addBox(0f, -22f, -57f, 1, 1, 13, 0f, false)
        window_top_1_r2.texOffs(93, 172).addBox(0f, -22f, -15f, 1, 22, 65, 0f, false)

        window_bottom_1_r2 = ModelMapper(modelDataWrapper)
        window_bottom_1_r2.setPos(-21f, -7f, 0f)
        head_side_1.addChild(window_bottom_1_r2)
        setRotationAngle(window_bottom_1_r2, 0f, 0f, -0.2094f)
        window_bottom_1_r2.texOffs(161, 0).addBox(0f, 0f, -75f, 1, 8, 18, 0f, false)
        window_bottom_1_r2.texOffs(62, 319).addBox(0f, 0f, -44f, 1, 8, 29, 0f, false)
        window_bottom_1_r2.texOffs(0, 86).addBox(0f, 0f, -15f, 1, 8, 72, 0f, false)

        end_r1 = ModelMapper(modelDataWrapper)
        end_r1.setPos(-19f, 2f, 33f)
        head_side_1.addChild(end_r1)
        setRotationAngle(end_r1, 0f, 0f, -0.2618f)
        end_r1.texOffs(204, 256).addBox(0f, 0f, 0f, 0, 11, 26, 0f, false)

        roof_11_r1 = ModelMapper(modelDataWrapper)
        roof_11_r1.setPos(-16.8732f, -11.8177f, 56.7312f)
        head_side_1.addChild(roof_11_r1)
        setRotationAngle(roof_11_r1, 0.1745f, -1.0472f, 0f)
        roof_11_r1.texOffs(361, 328).addBox(-9.5f, -3f, 0f, 19, 15, 0, 0f, false)

        roof_12_r1 = ModelMapper(modelDataWrapper)
        roof_12_r1.setPos(-15.5128f, 10.8301f, 58.2552f)
        head_side_1.addChild(roof_12_r1)
        setRotationAngle(roof_12_r1, 0f, -1.0472f, 0f)
        roof_12_r1.texOffs(161, 26).addBox(-3.5f, -1.5f, 0f, 7, 3, 0, 0f, false)

        roof_11_r2 = ModelMapper(modelDataWrapper)
        roof_11_r2.setPos(-15.5954f, 7.1646f, 60.6123f)
        head_side_1.addChild(roof_11_r2)
        setRotationAngle(roof_11_r2, -0.5236f, -1.0472f, 0f)
        roof_11_r2.texOffs(134, 91).addBox(-5.5f, -2.5f, 0f, 11, 5, 0, 0f, false)

        roof_10_r1 = ModelMapper(modelDataWrapper)
        roof_10_r1.setPos(-16.9278f, 3f, 60.8042f)
        head_side_1.addChild(roof_10_r1)
        setRotationAngle(roof_10_r1, 0f, -1.0472f, 0f)
        roof_10_r1.texOffs(134, 86).addBox(-6f, -3f, 0f, 12, 5, 0, 0f, false)

        roof_10_r2 = ModelMapper(modelDataWrapper)
        roof_10_r2.setPos(-7f, 0f, 70f)
        head_side_1.addChild(roof_10_r2)
        setRotationAngle(roof_10_r2, 0.2618f, -0.5236f, 0f)
        roof_10_r2.texOffs(218, 86).addBox(-9f, -11f, 0f, 9, 11, 0, 0f, false)

        roof_11_r3 = ModelMapper(modelDataWrapper)
        roof_11_r3.setPos(-10.0747f, 10.7433f, 63.3255f)
        head_side_1.addChild(roof_11_r3)
        setRotationAngle(roof_11_r3, 0f, -0.5236f, 0f)
        roof_11_r3.texOffs(28, 50).addBox(-5f, -1.5f, 0f, 10, 3, 0, 0f, false)

        roof_10_r3 = ModelMapper(modelDataWrapper)
        roof_10_r3.setPos(-7f, 5f, 70f)
        head_side_1.addChild(roof_10_r3)
        setRotationAngle(roof_10_r3, -0.7854f, -0.5236f, 0f)
        roof_10_r3.texOffs(279, 58).addBox(-11f, 0f, 0f, 11, 6, 0, 0f, false)

        roof_9_r1 = ModelMapper(modelDataWrapper)
        roof_9_r1.setPos(-7f, 0f, 70f)
        head_side_1.addChild(roof_9_r1)
        setRotationAngle(roof_9_r1, 0f, -0.5236f, 0f)
        roof_9_r1.texOffs(161, 13).addBox(-8f, 0f, 0f, 8, 5, 0, 0f, false)

        roof_7_r2 = ModelMapper(modelDataWrapper)
        roof_7_r2.setPos(-18.5824f, -19.5437f, 43.232f)
        head_side_1.addChild(roof_7_r2)
        setRotationAngle(roof_7_r2, -0.2182f, 0.5236f, 1.0472f)
        roof_7_r2.texOffs(118, 360).addBox(0f, -8f, -28.5f, 0, 11, 57, 0f, false)

        roof_4_r11 = ModelMapper(modelDataWrapper)
        roof_4_r11.setPos(-2f, -42f, -9f)
        head_side_1.addChild(roof_4_r11)
        setRotationAngle(roof_4_r11, 0f, 0f, 1.5708f)
        roof_4_r11.texOffs(265, 289).addBox(0f, -2f, -6f, 1, 4, 12, 0f, false)

        roof_4_r12 = ModelMapper(modelDataWrapper)
        roof_4_r12.setPos(-8.1369f, -39.4953f, 9.8171f)
        head_side_1.addChild(roof_4_r12)
        setRotationAngle(roof_4_r12, -0.1309f, 0.1309f, 1.3963f)
        roof_4_r12.texOffs(32, 292).addBox(0f, -3.5f, -13.5f, 0, 7, 27, 0f, false)

        roof_3_r10 = ModelMapper(modelDataWrapper)
        roof_3_r10.setPos(-13.6527f, -37.467f, 10.5898f)
        head_side_1.addChild(roof_3_r10)
        setRotationAngle(roof_3_r10, -0.0873f, 0.0873f, 1.0472f)
        roof_3_r10.texOffs(204, 247).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, false)

        roof_2_r14 = ModelMapper(modelDataWrapper)
        roof_2_r14.setPos(-17.2178f, -34.6364f, 11.8407f)
        head_side_1.addChild(roof_2_r14)
        setRotationAngle(roof_2_r14, -0.0436f, 0.0436f, 0.6981f)
        roof_2_r14.texOffs(204, 235).addBox(0f, -3f, -15f, 0, 6, 30, 0f, false)

        roof_3_r11 = ModelMapper(modelDataWrapper)
        roof_3_r11.setPos(-6.6372f, -40.9654f, -9f)
        head_side_1.addChild(roof_3_r11)
        setRotationAngle(roof_3_r11, 0f, 0f, 1.3963f)
        roof_3_r11.texOffs(230, 139).addBox(-0.5f, -3.5f, -6f, 1, 7, 12, 0f, false)

        roof_2_r15 = ModelMapper(modelDataWrapper)
        roof_2_r15.setPos(-12.519f, -38.9171f, -9f)
        head_side_1.addChild(roof_2_r15)
        setRotationAngle(roof_2_r15, 0f, 0f, 1.0472f)
        roof_2_r15.texOffs(0, 274).addBox(-0.5f, -3f, -6f, 1, 6, 12, 0f, false)

        roof_1_r13 = ModelMapper(modelDataWrapper)
        roof_1_r13.setPos(-16.2696f, -35.9966f, -9f)
        head_side_1.addChild(roof_1_r13)
        setRotationAngle(roof_1_r13, 0f, 0f, 0.6981f)
        roof_1_r13.texOffs(296, 243).addBox(-0.5f, -2f, -6f, 1, 4, 12, 0f, false)

        head_side_2 = ModelMapper(modelDataWrapper)
        head_side_2.setPos(0f, 0f, 0f)
        head_exterior.addChild(head_side_2)
        head_side_2.texOffs(155, 97).addBox(18f, 0f, -15f, 1, 2, 73, 0f, true)
        head_side_2.texOffs(158, 192).addBox(20f, -13f, -15f, 1, 6, 67, 0f, true)
        head_side_2.texOffs(279, 44).addBox(19f, 0f, -57f, 2, 1, 13, 0f, true)
        head_side_2.texOffs(125, 356).addBox(20f, -13f, -44f, 1, 6, 29, 0f, true)
        head_side_2.texOffs(361, 286).addBox(20f, -13f, -75f, 1, 6, 18, 0f, true)

        roof_5_r7 = ModelMapper(modelDataWrapper)
        roof_5_r7.setPos(10.1709f, -40.8501f, 0f)
        head_side_2.addChild(roof_5_r7)
        setRotationAngle(roof_5_r7, 0f, 0f, -1.0472f)
        roof_5_r7.texOffs(157, 326).addBox(-1f, 3f, -75f, 1, 3, 30, 0f, true)
        roof_5_r7.texOffs(328, 185).addBox(-1f, 3f, -45f, 1, 3, 30, 0f, true)

        roof_4_r13 = ModelMapper(modelDataWrapper)
        roof_4_r13.setPos(17.9382f, -34.7859f, 0f)
        head_side_2.addChild(roof_4_r13)
        setRotationAngle(roof_4_r13, 0f, 0f, -0.6981f)
        roof_4_r13.texOffs(32, 261).addBox(0f, -4f, -75f, 0, 4, 30, 0f, true)
        roof_4_r13.texOffs(32, 265).addBox(0f, -4f, -45f, 0, 4, 30, 0f, true)

        window_top_2_r4 = ModelMapper(modelDataWrapper)
        window_top_2_r4.setPos(21f, -13f, 0f)
        head_side_2.addChild(window_top_2_r4)
        setRotationAngle(window_top_2_r4, 0f, 0f, -0.1396f)
        window_top_2_r4.texOffs(362, 74).addBox(-1f, -22f, -75f, 1, 22, 18, 0f, true)
        window_top_2_r4.texOffs(113, 259).addBox(-1f, -22f, -44f, 1, 22, 29, 0f, true)
        window_top_2_r4.texOffs(237, 50).addBox(-1f, -22f, -57f, 1, 1, 13, 0f, true)
        window_top_2_r4.texOffs(93, 172).addBox(-1f, -22f, -15f, 1, 22, 65, 0f, true)

        window_bottom_2_r5 = ModelMapper(modelDataWrapper)
        window_bottom_2_r5.setPos(21f, -7f, 0f)
        head_side_2.addChild(window_bottom_2_r5)
        setRotationAngle(window_bottom_2_r5, 0f, 0f, 0.2094f)
        window_bottom_2_r5.texOffs(161, 0).addBox(-1f, 0f, -75f, 1, 8, 18, 0f, true)
        window_bottom_2_r5.texOffs(62, 319).addBox(-1f, 0f, -44f, 1, 8, 29, 0f, true)
        window_bottom_2_r5.texOffs(0, 86).addBox(-1f, 0f, -15f, 1, 8, 72, 0f, true)

        end_r2 = ModelMapper(modelDataWrapper)
        end_r2.setPos(19f, 2f, 33f)
        head_side_2.addChild(end_r2)
        setRotationAngle(end_r2, 0f, 0f, 0.2618f)
        end_r2.texOffs(204, 256).addBox(0f, 0f, 0f, 0, 11, 26, 0f, true)

        roof_12_r2 = ModelMapper(modelDataWrapper)
        roof_12_r2.setPos(16.8732f, -11.8177f, 56.7312f)
        head_side_2.addChild(roof_12_r2)
        setRotationAngle(roof_12_r2, 0.1745f, 1.0472f, 0f)
        roof_12_r2.texOffs(361, 328).addBox(-9.5f, -3f, 0f, 19, 15, 0, 0f, true)

        roof_13_r1 = ModelMapper(modelDataWrapper)
        roof_13_r1.setPos(15.5128f, 10.8301f, 58.2552f)
        head_side_2.addChild(roof_13_r1)
        setRotationAngle(roof_13_r1, 0f, 1.0472f, 0f)
        roof_13_r1.texOffs(161, 26).addBox(-3.5f, -1.5f, 0f, 7, 3, 0, 0f, true)

        roof_12_r3 = ModelMapper(modelDataWrapper)
        roof_12_r3.setPos(15.5954f, 7.1646f, 60.6123f)
        head_side_2.addChild(roof_12_r3)
        setRotationAngle(roof_12_r3, -0.5236f, 1.0472f, 0f)
        roof_12_r3.texOffs(134, 91).addBox(-5.5f, -2.5f, 0f, 11, 5, 0, 0f, true)

        roof_11_r4 = ModelMapper(modelDataWrapper)
        roof_11_r4.setPos(16.9278f, 3f, 60.8042f)
        head_side_2.addChild(roof_11_r4)
        setRotationAngle(roof_11_r4, 0f, 1.0472f, 0f)
        roof_11_r4.texOffs(134, 86).addBox(-6f, -3f, 0f, 12, 5, 0, 0f, true)

        roof_11_r5 = ModelMapper(modelDataWrapper)
        roof_11_r5.setPos(7f, 0f, 70f)
        head_side_2.addChild(roof_11_r5)
        setRotationAngle(roof_11_r5, 0.2618f, 0.5236f, 0f)
        roof_11_r5.texOffs(218, 86).addBox(0f, -11f, 0f, 9, 11, 0, 0f, true)

        roof_12_r4 = ModelMapper(modelDataWrapper)
        roof_12_r4.setPos(10.0747f, 10.7433f, 63.3255f)
        head_side_2.addChild(roof_12_r4)
        setRotationAngle(roof_12_r4, 0f, 0.5236f, 0f)
        roof_12_r4.texOffs(28, 50).addBox(-5f, -1.5f, 0f, 10, 3, 0, 0f, true)

        roof_11_r6 = ModelMapper(modelDataWrapper)
        roof_11_r6.setPos(7f, 5f, 70f)
        head_side_2.addChild(roof_11_r6)
        setRotationAngle(roof_11_r6, -0.7854f, 0.5236f, 0f)
        roof_11_r6.texOffs(279, 58).addBox(0f, 0f, 0f, 11, 6, 0, 0f, true)

        roof_10_r4 = ModelMapper(modelDataWrapper)
        roof_10_r4.setPos(7f, 0f, 70f)
        head_side_2.addChild(roof_10_r4)
        setRotationAngle(roof_10_r4, 0f, 0.5236f, 0f)
        roof_10_r4.texOffs(161, 13).addBox(0f, 0f, 0f, 8, 5, 0, 0f, true)

        roof_8_r1 = ModelMapper(modelDataWrapper)
        roof_8_r1.setPos(18.5824f, -19.5437f, 43.232f)
        head_side_2.addChild(roof_8_r1)
        setRotationAngle(roof_8_r1, -0.2182f, -0.5236f, -1.0472f)
        roof_8_r1.texOffs(118, 360).addBox(0f, -8f, -28.5f, 0, 11, 57, 0f, true)

        roof_5_r8 = ModelMapper(modelDataWrapper)
        roof_5_r8.setPos(2f, -42f, -9f)
        head_side_2.addChild(roof_5_r8)
        setRotationAngle(roof_5_r8, 0f, 0f, -1.5708f)
        roof_5_r8.texOffs(265, 289).addBox(-1f, -2f, -6f, 1, 4, 12, 0f, true)

        roof_5_r9 = ModelMapper(modelDataWrapper)
        roof_5_r9.setPos(8.1369f, -39.4953f, 9.8171f)
        head_side_2.addChild(roof_5_r9)
        setRotationAngle(roof_5_r9, -0.1309f, -0.1309f, -1.3963f)
        roof_5_r9.texOffs(32, 292).addBox(0f, -3.5f, -13.5f, 0, 7, 27, 0f, true)

        roof_4_r14 = ModelMapper(modelDataWrapper)
        roof_4_r14.setPos(13.6527f, -37.467f, 10.5898f)
        head_side_2.addChild(roof_4_r14)
        setRotationAngle(roof_4_r14, -0.0873f, -0.0873f, -1.0472f)
        roof_4_r14.texOffs(204, 247).addBox(0f, -3.5f, -14f, 0, 7, 28, 0f, true)

        roof_3_r12 = ModelMapper(modelDataWrapper)
        roof_3_r12.setPos(17.2178f, -34.6364f, 11.8407f)
        head_side_2.addChild(roof_3_r12)
        setRotationAngle(roof_3_r12, -0.0436f, -0.0436f, -0.6981f)
        roof_3_r12.texOffs(204, 235).addBox(0f, -3f, -15f, 0, 6, 30, 0f, true)

        roof_4_r15 = ModelMapper(modelDataWrapper)
        roof_4_r15.setPos(6.6372f, -40.9654f, -9f)
        head_side_2.addChild(roof_4_r15)
        setRotationAngle(roof_4_r15, 0f, 0f, -1.3963f)
        roof_4_r15.texOffs(230, 139).addBox(-0.5f, -3.5f, -6f, 1, 7, 12, 0f, true)

        roof_3_r13 = ModelMapper(modelDataWrapper)
        roof_3_r13.setPos(12.519f, -38.9171f, -9f)
        head_side_2.addChild(roof_3_r13)
        setRotationAngle(roof_3_r13, 0f, 0f, -1.0472f)
        roof_3_r13.texOffs(0, 274).addBox(-0.5f, -3f, -6f, 1, 6, 12, 0f, true)

        roof_2_r16 = ModelMapper(modelDataWrapper)
        roof_2_r16.setPos(16.2696f, -35.9966f, -9f)
        head_side_2.addChild(roof_2_r16)
        setRotationAngle(roof_2_r16, 0f, 0f, -0.6981f)
        roof_2_r16.texOffs(296, 243).addBox(-0.5f, -2f, -6f, 1, 4, 12, 0f, true)

        middle = ModelMapper(modelDataWrapper)
        middle.setPos(0f, 0f, 0f)
        head_exterior.addChild(middle)
        middle.texOffs(181, 8).addBox(-7f, 0f, 70f, 14, 5, 0, 0f, false)
        middle.texOffs(0, 50).addBox(-7f, 9.2433f, 65.7571f, 14, 3, 0, 0f, false)
        middle.texOffs(0, 0).addBox(-19f, 0f, -15f, 38, 1, 85, 0f, false)
        middle.texOffs(0, 231).addBox(-20f, -42f, -14.5f, 40, 42, 1, 0f, false)

        roof_8_r2 = ModelMapper(modelDataWrapper)
        roof_8_r2.setPos(0f, 0f, 70f)
        middle.addChild(roof_8_r2)
        setRotationAngle(roof_8_r2, 0.3491f, 0f, 0f)
        roof_8_r2.texOffs(32, 326).addBox(-8f, -9f, 0f, 16, 9, 0, 0f, false)

        roof_6_r4 = ModelMapper(modelDataWrapper)
        roof_6_r4.setPos(-10f, -37.8324f, 20.6354f)
        middle.addChild(roof_6_r4)
        setRotationAngle(roof_6_r4, 0f, -0.5672f, -1.5708f)
        roof_6_r4.texOffs(118, 336).addBox(0f, -3f, 0f, 0, 26, 55, 0f, false)

        roof_5_r10 = ModelMapper(modelDataWrapper)
        roof_5_r10.setPos(0f, -42f, -3f)
        middle.addChild(roof_5_r10)
        setRotationAngle(roof_5_r10, 0f, -0.1745f, -1.5708f)
        roof_5_r10.texOffs(144, 241).addBox(0f, -10f, 0f, 0, 20, 24, 0f, false)

        roof_9_r2 = ModelMapper(modelDataWrapper)
        roof_9_r2.setPos(0f, 5f, 70f)
        middle.addChild(roof_9_r2)
        setRotationAngle(roof_9_r2, -0.7854f, 0f, 0f)
        roof_9_r2.texOffs(230, 158).addBox(-7f, 0f, 0f, 14, 6, 0, 0f, false)

        bottom_middle = ModelMapper(modelDataWrapper)
        bottom_middle.setPos(0f, 24f, 0f)
        bottom_middle.texOffs(264, 235).addBox(-17f, 2f, -15f, 1, 10, 30, 0f, false)

        bottom_end = ModelMapper(modelDataWrapper)
        bottom_end.setPos(0f, 24f, 0f)
        bottom_end.texOffs(0, 0).addBox(-17f, 2f, -7f, 1, 10, 14, 0f, false)

        seat = ModelMapper(modelDataWrapper)
        seat.setPos(0f, 24f, 0f)
        seat.texOffs(181, 0).addBox(-4f, -6f, -4f, 8, 1, 7, 0f, false)
        seat.texOffs(214, 0).addBox(-3.5f, -22.644f, 4.0686f, 7, 5, 1, 0f, false)

        seat_2_r1 = ModelMapper(modelDataWrapper)
        seat_2_r1.setPos(0f, -6f, 2f)
        seat.addChild(seat_2_r1)
        setRotationAngle(seat_2_r1, -0.1745f, 0f, 0f)
        seat_2_r1.texOffs(161, 0).addBox(-4f, -12f, 0f, 8, 12, 1, 0f, false)

        headlights = ModelMapper(modelDataWrapper)
        headlights.setPos(0f, 24f, 0f)


        headlight_2_r1 = ModelMapper(modelDataWrapper)
        headlight_2_r1.setPos(18.5824f, -19.5437f, 43.232f)
        headlights.addChild(headlight_2_r1)
        setRotationAngle(headlight_2_r1, -0.2182f, -0.5236f, -1.0472f)
        headlight_2_r1.texOffs(345, 53).addBox(0.1f, -8f, 10.5f, 0, 6, 13, 0f, true)

        headlight_1_r1 = ModelMapper(modelDataWrapper)
        headlight_1_r1.setPos(-18.5824f, -19.5437f, 43.232f)
        headlights.addChild(headlight_1_r1)
        setRotationAngle(headlight_1_r1, -0.2182f, 0.5236f, 1.0472f)
        headlight_1_r1.texOffs(345, 53).addBox(-0.1f, -8f, 10.5f, 0, 6, 13, 0f, false)

        tail_lights = ModelMapper(modelDataWrapper)
        tail_lights.setPos(0f, 24f, 0f)


        tail_light_2_r1 = ModelMapper(modelDataWrapper)
        tail_light_2_r1.setPos(18.5824f, -19.5437f, 43.232f)
        tail_lights.addChild(tail_light_2_r1)
        setRotationAngle(tail_light_2_r1, -0.2182f, -0.5236f, -1.0472f)
        tail_light_2_r1.texOffs(345, 59).addBox(0.1f, -8f, 10.5f, 0, 6, 13, 0f, true)

        tail_light_1_r1 = ModelMapper(modelDataWrapper)
        tail_light_1_r1.setPos(-18.5824f, -19.5437f, 43.232f)
        tail_lights.addChild(tail_light_1_r1)
        setRotationAngle(tail_light_1_r1, -0.2182f, 0.5236f, 1.0472f)
        tail_light_1_r1.texOffs(345, 59).addBox(-0.1f, -8f, 10.5f, 0, 6, 13, 0f, false)

        door_light_off = ModelMapper(modelDataWrapper)
        door_light_off.setPos(0f, 24f, 0f)


        door_light_off_r1 = ModelMapper(modelDataWrapper)
        door_light_off_r1.setPos(-21f, -13f, 0f)
        door_light_off.addChild(door_light_off_r1)
        setRotationAngle(door_light_off_r1, 0f, 0f, 0.1396f)
        door_light_off_r1.texOffs(0, 0).addBox(-0.5f, -21.5f, -0.5f, 1, 1, 1, 0f, false)

        door_light_on = ModelMapper(modelDataWrapper)
        door_light_on.setPos(0f, 24f, 0f)


        door_light_on_r1 = ModelMapper(modelDataWrapper)
        door_light_on_r1.setPos(-21f, -13f, 0f)
        door_light_on.addChild(door_light_on_r1)
        setRotationAngle(door_light_on_r1, 0f, 0f, 0.1396f)
        door_light_on_r1.texOffs(0, 2).addBox(-0.5f, -21.5f, -0.5f, 1, 1, 1, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        window.setModelPart()
        window_light.setModelPart()
        window_exterior_1.setModelPart()
        window_exterior_2.setModelPart()
        window_exterior_3.setModelPart()
        window_exterior_4.setModelPart()
        window_exterior_5.setModelPart()
        window_exterior_6.setModelPart()
        window_exterior_7.setModelPart()
        window_exterior_8.setModelPart()
        window_exterior_9.setModelPart()
        window_end_exterior_1.setModelPart()
        window_end_exterior_2.setModelPart()
        window_end_exterior_3.setModelPart()
        window_end_exterior_4.setModelPart()
        window_end_exterior_5.setModelPart()
        window_end_exterior_6.setModelPart()
        door.setModelPart()
        door_1.setModelPart(door.name)
        door_2.setModelPart(door.name)
        door_exterior.setModelPart()
        door_exterior_1.setModelPart(door_exterior.name)
        door_exterior_2.setModelPart(door_exterior.name)
        door_exterior_end.setModelPart()
        door_exterior_end_1.setModelPart(door_exterior_end.name)
        door_exterior_end_2.setModelPart(door_exterior_end.name)
        roof_exterior.setModelPart()
        end.setModelPart()
        end_light.setModelPart()
        end_translucent.setModelPart()
        end_exterior.setModelPart()
        roof_vent.setModelPart()
        head_exterior.setModelPart()
        bottom_middle.setModelPart()
        bottom_end.setModelPart()
        seat.setModelPart()
        headlights.setModelPart()
        tail_lights.setModelPart()
        door_light_off.setModelPart()
        door_light_on.setModelPart()
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelClass802 {
        return ModelClass802(doorAnimationType, renderDoorOverlay)
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
        val windowParts = if (isEnd1Head || isEnd2Head) windowEndParts() else windowParts()
        val loopStart = this.bogiePositions[0] + (if (isEnd1Head) 94 else 4)
        val loopEnd = this.bogiePositions[1] - (if (isEnd2Head) 94 else 4)

        when (renderStage!!) {
            RenderStage.LIGHTS -> {
                var i = loopStart
                while (i <= loopEnd) {
                    renderMirror(window_light, matrices, vertices, light, i.toFloat())
                    i += 30
                }
            }

            RenderStage.INTERIOR -> {
                var j = 0
                var i = loopStart
                while (i <= loopEnd) {
                    renderMirror(window, matrices, vertices, light, i.toFloat())
                    if (renderDetails) {
                        if (j % 4 < 2) {
                            renderOnce(seat, matrices, vertices, light, -16f, (i - 5).toFloat())
                            renderOnce(seat, matrices, vertices, light, -16f, (i + 10).toFloat())
                            renderOnce(seat, matrices, vertices, light, -8f, (i - 5).toFloat())
                            renderOnce(seat, matrices, vertices, light, -8f, (i + 10).toFloat())
                            renderOnce(seat, matrices, vertices, light, 8f, (i - 5).toFloat())
                            renderOnce(seat, matrices, vertices, light, 8f, (i + 10).toFloat())
                            renderOnce(seat, matrices, vertices, light, 16f, (i - 5).toFloat())
                            renderOnce(seat, matrices, vertices, light, 16f, (i + 10).toFloat())
                        } else {
                            renderOnceFlipped(seat, matrices, vertices, light, -16f, (i - 10).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, -16f, (i + 5).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, -8f, (i - 10).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, -8f, (i + 5).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, 8f, (i - 10).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, 8f, (i + 5).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, 16f, (i - 10).toFloat())
                            renderOnceFlipped(seat, matrices, vertices, light, 16f, (i + 5).toFloat())
                        }
                    }
                    j++
                    i += 30
                }
            }

            RenderStage.EXTERIOR -> {
                var k = 0
                run {
                    var i = loopStart
                    while (i <= loopEnd) {
                        if (!isEnd2Head) {
                            renderOnce(windowParts[windowParts.size - k - 1], matrices, vertices, light, i.toFloat())
                        }
                        if (!isEnd1Head || isEnd2Head) {
                            renderOnceFlipped(windowParts[k], matrices, vertices, light, i.toFloat())
                        }
                        renderMirror(roof_exterior, matrices, vertices, light, i.toFloat())
                        k++
                        i += 30
                    }
                }
                val bogiePositions = this.bogiePositions
                renderMirror(bottom_end, matrices, vertices, light, (bogiePositions[0] + 42).toFloat())
                renderMirror(bottom_end, matrices, vertices, light, (bogiePositions[1] - 42).toFloat())
                var i = bogiePositions[0] + 64
                while (i <= bogiePositions[1] - 64) {
                    renderMirror(bottom_middle, matrices, vertices, light, i.toFloat())
                    i += 30
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
        val firstDoor = isIndex(0, position, getDoorPositions())
        val doorOffset = (if (isEnd1Head && firstDoor) 90 else 0) + (if (isEnd2Head && !firstDoor) -90 else 0)
        val doorOpen = doorLeftZ > 0 || doorRightZ > 0

        when (renderStage!!) {
            RenderStage.LIGHTS -> if (firstDoor && doorOpen) {
                renderMirror(door_light_on, matrices, vertices, light, 0f)
            }

            RenderStage.INTERIOR -> if (firstDoor) {
                door_1.setOffset(0f, 0, doorLeftZ)
                door_2.setOffset(0f, 0, doorRightZ)
                renderOnceFlipped(door, matrices, vertices, light, (position + doorOffset).toFloat())
            } else {
                door_1.setOffset(0f, 0, doorRightZ)
                door_2.setOffset(0f, 0, doorLeftZ)
                renderOnce(door, matrices, vertices, light, (position + doorOffset).toFloat())
            }

            RenderStage.EXTERIOR -> {
                if (isEnd1Head && firstDoor || isEnd2Head && !firstDoor) {
                    if (firstDoor) {
                        door_exterior_end_1.setOffset(0f, 0, doorLeftZ)
                        door_exterior_end_2.setOffset(0f, 0, doorRightZ)
                        renderOnceFlipped(
                            door_exterior_end,
                            matrices,
                            vertices,
                            light,
                            (position + doorOffset).toFloat()
                        )
                    } else {
                        door_exterior_end_1.setOffset(0f, 0, doorRightZ)
                        door_exterior_end_2.setOffset(0f, 0, doorLeftZ)
                        renderOnce(door_exterior_end, matrices, vertices, light, (position + doorOffset).toFloat())
                    }
                } else {
                    if (firstDoor) {
                        door_exterior_1.setOffset(0f, 0, doorLeftZ)
                        door_exterior_2.setOffset(0f, 0, doorRightZ)
                        renderOnceFlipped(door_exterior, matrices, vertices, light, position.toFloat())
                    } else {
                        door_exterior_1.setOffset(0f, 0, doorRightZ)
                        door_exterior_2.setOffset(0f, 0, doorLeftZ)
                        renderOnce(door_exterior, matrices, vertices, light, position.toFloat())
                    }
                }
                if (firstDoor && !doorOpen) {
                    renderMirror(door_light_off, matrices, vertices, light, 0f)
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
            RenderStage.LIGHTS -> renderOnceFlipped(end_light, matrices, vertices, light, (position + 90).toFloat())
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnceFlipped(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                (position + 30).toFloat()
            )

            RenderStage.INTERIOR -> renderOnceFlipped(end, matrices, vertices, light, (position + 90).toFloat())
            RenderStage.INTERIOR_TRANSLUCENT -> renderOnceFlipped(
                end_translucent,
                matrices,
                vertices,
                light,
                (position + 90).toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(head_exterior, matrices, vertices, light, (position + 30).toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position + 60).toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position + 90).toFloat())
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
            RenderStage.LIGHTS -> renderOnce(end_light, matrices, vertices, light, (position - 90).toFloat())
            RenderStage.ALWAYS_ON_LIGHTS -> renderOnce(
                if (useHeadlights) headlights else tail_lights,
                matrices,
                vertices,
                light,
                (position - 30).toFloat()
            )

            RenderStage.INTERIOR -> renderOnce(end, matrices, vertices, light, (position - 90).toFloat())
            RenderStage.INTERIOR_TRANSLUCENT -> renderOnce(
                end_translucent,
                matrices,
                vertices,
                light,
                (position - 90).toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnce(head_exterior, matrices, vertices, light, (position - 30).toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position - 60).toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position - 90).toFloat())
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
            RenderStage.LIGHTS -> renderOnceFlipped(end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> renderOnceFlipped(end, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR_TRANSLUCENT -> renderOnceFlipped(
                end_translucent,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnceFlipped(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position - 30).toFloat())
                renderOnceFlipped(roof_vent, matrices, vertices, light, position.toFloat())
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
            RenderStage.LIGHTS -> renderOnce(end_light, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR -> renderOnce(end, matrices, vertices, light, position.toFloat())
            RenderStage.INTERIOR_TRANSLUCENT -> renderOnce(
                end_translucent,
                matrices,
                vertices,
                light,
                position.toFloat()
            )

            RenderStage.EXTERIOR -> {
                renderOnce(end_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, position.toFloat())
                renderMirror(roof_exterior, matrices, vertices, light, (position + 30).toFloat())
                renderOnce(roof_vent, matrices, vertices, light, position.toFloat())
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
        return intArrayOf(0)
    }

    @Override
    override fun getDoorPositions(): IntArray? {
        return intArrayOf(-150, 150)
    }

    @Override
    override fun getEndPositions(): IntArray? {
        return intArrayOf(-150, 150)
    }

    protected open val bogiePositions: IntArray
        get() = intArrayOf(-124, 124)

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
            scrollingTexts.add(ScrollingText(0.46f, 0.16f, 4, false))
            scrollingTexts.add(ScrollingText(0.7f, 0.06f, 8, true))
        }
        val isEnd1Head = car == 0
        val isEnd2Head = car == totalCars - 1
        val renderFirstDestination = renderFirstDestination(isEnd1Head, isEnd2Head)
        val renderSecondDestination = renderSecondDestination(isEnd1Head, isEnd2Head)
        val destinationString: String? =
            getDestinationString(lastStation, customDestination, TextSpacingType.NORMAL, false)

        if (renderFirstDestination || renderSecondDestination) {
            scrollingTexts.get(0)!!.changeImage(
                if (destinationString!!.isEmpty()) null else ClientData.DATA_CACHE.getPixelatedText(
                    destinationString.replace(
                        "|",
                        " "
                    ),
                    -0x6700,
                    Integer.MAX_VALUE,
                    0f,
                    false
                )
            )
            scrollingTexts.get(0)!!.setVertexConsumer(vertexConsumers)

            for (i in 0..1) {
                if (i == 0 && renderFirstDestination || i == 1 && renderSecondDestination) {
                    for (j in 0..1) {
                        val flip = if (j == 1) -1 else 1
                        matrices!!.pushPose()
                        matrices.translate(
                            -21f / 16 * flip,
                            -13f / 16,
                            getEndPositions()!![i] / 16f + (if (i == 0) 1 else -1) * (1.28f + (if (i == 0 && isEnd1Head || i == 1 && isEnd2Head) 5.63f else 0f))
                        )
                        UtilitiesClient.rotateYDegrees(matrices, (90 * flip).toFloat())
                        UtilitiesClient.rotateXDegrees(matrices, -8f)
                        matrices.translate(-0.23f, -1.38f, -0.01f)
                        scrollingTexts.get(0)!!.scrollText(matrices)
                        matrices.popPose()
                    }
                }
            }
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
                -0.35f,
                -2.19f,
                (getEndPositions()!![1] - (if (i == 1 && isEnd1Head || i == 0 && isEnd2Head) 90 else 0)) / 16f - 0.51f
            )
            scrollingTexts.get(1)!!.scrollText(matrices)
            matrices.popPose()
        }
    }

    @Override
    override fun defaultDestinationString(): String? {
        return "Not in Service"
    }

    protected open fun renderFirstDestination(isEnd1Head: Boolean, isEnd2Head: Boolean): Boolean {
        return true
    }

    protected open fun renderSecondDestination(isEnd1Head: Boolean, isEnd2Head: Boolean): Boolean {
        return !isEnd1Head || !isEnd2Head
    }

    protected open fun windowParts(): Array<ModelMapper?> {
        return arrayOf<ModelMapper?>(
            window_exterior_1,
            window_exterior_2,
            window_exterior_3,
            window_exterior_4,
            window_exterior_5,
            window_exterior_6,
            window_exterior_7,
            window_exterior_8,
            window_exterior_9
        )
    }

    protected open fun windowEndParts(): Array<ModelMapper?> {
        return arrayOf<ModelMapper?>(
            window_end_exterior_6,
            window_end_exterior_5,
            window_end_exterior_4,
            window_end_exterior_3,
            window_end_exterior_2,
            window_end_exterior_1
        )
    }

    protected fun windowPartsMini(): Array<ModelMapper?> {
        return arrayOf<ModelMapper?>(
            window_exterior_1,
            window_exterior_2,
            window_exterior_3,
            window_exterior_7,
            window_exterior_8,
            window_exterior_9
        )
    }

    protected fun windowEndPartsMini(): Array<ModelMapper?> {
        return arrayOf<ModelMapper?>(window_end_exterior_3, window_end_exterior_2, window_end_exterior_1)
    }

    companion object {
        private const val DOOR_MAX = 13
    }
}
