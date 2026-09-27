package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper

open class ModelNgongPing360 private constructor(
    private val isRHT: Boolean,
    doorAnimationType: DoorAnimationType?,
    renderDoorOverlay: Boolean
) : ModelSimpleTrainBase<ModelNgongPing360?>(doorAnimationType, renderDoorOverlay) {
    private val body: ModelMapper
    private val pole_2_r1: ModelMapper
    private val wall_1: ModelMapper
    private val upper_pole_2_r1: ModelMapper
    private val upper_pole_1_r1: ModelMapper
    private val upper_wall_r1: ModelMapper
    private val lower_wall_r1: ModelMapper
    private val wall_2: ModelMapper
    private val upper_wall_r2: ModelMapper
    private val lower_wall_r2: ModelMapper
    private val wall_3: ModelMapper
    private val door_bottom_4_r1: ModelMapper
    private val door_bottom_3_r1: ModelMapper
    private val door_top_r1: ModelMapper
    private val lower_wall_right_r1: ModelMapper
    private val doors: ModelMapper
    private val door_left: ModelMapper
    private val upper_wall_left_r1: ModelMapper
    private val lower_wall_left_r1: ModelMapper
    private val door_right: ModelMapper
    private val upper_wall_right_r1: ModelMapper
    private val lower_wall_right_r2: ModelMapper

    constructor(isRHT: Boolean) : this(isRHT, DoorAnimationType.STANDARD, true)

    init {
        val textureWidth = 192
        val textureHeight = 192

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        body = ModelMapper(modelDataWrapper)
        body.setPos(0f, 24f, 0f)
        body.texOffs(78, 78).addBox(-13f, 0f, -13f, 26, 2, 26, 0f, false)
        body.texOffs(0, 76).addBox(-13f, -34f, -13f, 26, 2, 26, 0f, false)
        body.texOffs(62, 0).addBox(-1f, -44f, -1f, 2, 10, 2, 0f, false)
        body.texOffs(158, 0).addBox(8f, -88f, -1.5f, 3, 36, 3, 0f, false)

        pole_2_r1 = ModelMapper(modelDataWrapper)
        pole_2_r1.setPos(11f, -52f, 0f)
        body.addChild(pole_2_r1)
        setRotationAngle(pole_2_r1, 0f, 0f, 0.7854f)
        pole_2_r1.texOffs(22, 57).addBox(-3f, 0f, -1.5f, 3, 15, 3, -0.1f, false)

        wall_1 = ModelMapper(modelDataWrapper)
        wall_1.setPos(0f, 24f, 0f)
        wall_1.texOffs(126, 67).addBox(-14f, -6f, 10f, 28, 1, 4, 0f, false)

        upper_pole_2_r1 = ModelMapper(modelDataWrapper)
        upper_pole_2_r1.setPos(-13f, -34f, 13f)
        wall_1.addChild(upper_pole_2_r1)
        setRotationAngle(upper_pole_2_r1, 0.2793f, -0.0436f, 0.2793f)
        upper_pole_2_r1.texOffs(0, 0).addBox(0f, -6f, -2f, 2, 6, 2, 0f, false)

        upper_pole_1_r1 = ModelMapper(modelDataWrapper)
        upper_pole_1_r1.setPos(13f, -34f, 13f)
        wall_1.addChild(upper_pole_1_r1)
        setRotationAngle(upper_pole_1_r1, 0.2793f, 0.0436f, -0.2793f)
        upper_pole_1_r1.texOffs(12, 0).addBox(-2f, -6f, -2f, 2, 6, 2, 0f, false)

        upper_wall_r1 = ModelMapper(modelDataWrapper)
        upper_wall_r1.setPos(0f, -34f, 13f)
        wall_1.addChild(upper_wall_r1)
        setRotationAngle(upper_wall_r1, 0.2793f, 0f, 0f)
        upper_wall_r1.texOffs(80, 0).addBox(-19f, 0f, -1f, 38, 19, 1, 0f, false)

        lower_wall_r1 = ModelMapper(modelDataWrapper)
        lower_wall_r1.setPos(0f, 2f, 13f)
        wall_1.addChild(lower_wall_r1)
        setRotationAngle(lower_wall_r1, -0.2793f, 0f, 0f)
        lower_wall_r1.texOffs(80, 20).addBox(-19f, -19f, -1f, 38, 19, 1, 0f, false)

        wall_2 = ModelMapper(modelDataWrapper)
        wall_2.setPos(0f, 24f, 0f)


        upper_wall_r2 = ModelMapper(modelDataWrapper)
        upper_wall_r2.setPos(-13f, -34f, 0f)
        wall_2.addChild(upper_wall_r2)
        setRotationAngle(upper_wall_r2, 0f, 0f, 0.2793f)
        upper_wall_r2.texOffs(0, 0).addBox(0f, 0f, -19f, 1, 19, 38, 0f, false)

        lower_wall_r2 = ModelMapper(modelDataWrapper)
        lower_wall_r2.setPos(-13f, 2f, 0f)
        wall_2.addChild(lower_wall_r2)
        setRotationAngle(lower_wall_r2, 0f, 0f, -0.2793f)
        lower_wall_r2.texOffs(40, 19).addBox(0f, -19f, -19f, 1, 19, 38, 0f, false)

        wall_3 = ModelMapper(modelDataWrapper)
        wall_3.setPos(0f, 24f, 0f)
        wall_3.texOffs(94, 52).addBox(13f, 0f, -12f, 4, 1, 24, 0f, false)
        wall_3.texOffs(80, 40).addBox(17f, 0f, -7f, 2, 1, 14, 0f, false)

        door_bottom_4_r1 = ModelMapper(modelDataWrapper)
        door_bottom_4_r1.setPos(17f, 0f, -12f)
        wall_3.addChild(door_bottom_4_r1)
        setRotationAngle(door_bottom_4_r1, 0f, 0.3491f, 0f)
        door_bottom_4_r1.texOffs(0, 29).addBox(-2f, 0.05f, 0f, 2, 1, 6, 0f, false)

        door_bottom_3_r1 = ModelMapper(modelDataWrapper)
        door_bottom_3_r1.setPos(17f, 0f, 12f)
        wall_3.addChild(door_bottom_3_r1)
        setRotationAngle(door_bottom_3_r1, 0f, -0.3491f, 0f)
        door_bottom_3_r1.texOffs(10, 30).addBox(-2f, 0.05f, -6f, 2, 1, 6, 0f, false)

        door_top_r1 = ModelMapper(modelDataWrapper)
        door_top_r1.setPos(13f, -34f, 0f)
        wall_3.addChild(door_top_r1)
        setRotationAngle(door_top_r1, 0f, 0f, -0.2793f)
        door_top_r1.texOffs(0, 104).addBox(-1.5f, 0f, -11f, 3, 2, 22, 0f, false)
        door_top_r1.texOffs(40, 0).addBox(-1f, 0f, 9f, 1, 19, 10, 0f, false)
        door_top_r1.texOffs(0, 57).addBox(-1f, 0f, -19f, 1, 19, 10, 0f, false)

        lower_wall_right_r1 = ModelMapper(modelDataWrapper)
        lower_wall_right_r1.setPos(13f, 2f, 28f)
        wall_3.addChild(lower_wall_right_r1)
        setRotationAngle(lower_wall_right_r1, 0f, 0f, 0.2793f)
        lower_wall_right_r1.texOffs(0, 0).addBox(-1f, -19f, -19f, 1, 19, 10, 0f, false)
        lower_wall_right_r1.texOffs(50, 104).addBox(-1f, -19f, -47f, 1, 19, 10, 0f, false)

        doors = ModelMapper(modelDataWrapper)
        doors.setPos(0f, 24f, 0f)


        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        doors.addChild(door_left)


        upper_wall_left_r1 = ModelMapper(modelDataWrapper)
        upper_wall_left_r1.setPos(14f, -34f, 9f)
        door_left.addChild(upper_wall_left_r1)
        setRotationAngle(upper_wall_left_r1, 0f, 0f, -0.2793f)
        upper_wall_left_r1.texOffs(94, 106).addBox(-1f, 1f, -19f, 1, 18, 10, 0f, false)

        lower_wall_left_r1 = ModelMapper(modelDataWrapper)
        lower_wall_left_r1.setPos(13f, 2f, 9f)
        door_left.addChild(lower_wall_left_r1)
        setRotationAngle(lower_wall_left_r1, 0f, 0f, 0.2793f)
        lower_wall_left_r1.texOffs(126, 40).addBox(0f, -19f, -19f, 1, 17, 10, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        doors.addChild(door_right)


        upper_wall_right_r1 = ModelMapper(modelDataWrapper)
        upper_wall_right_r1.setPos(14f, -34f, 19f)
        door_right.addChild(upper_wall_right_r1)
        setRotationAngle(upper_wall_right_r1, 0f, 0f, -0.2793f)
        upper_wall_right_r1.texOffs(72, 106).addBox(-1f, 1f, -19f, 1, 18, 10, 0f, false)

        lower_wall_right_r2 = ModelMapper(modelDataWrapper)
        lower_wall_right_r2.setPos(13f, 2f, 19f)
        door_right.addChild(lower_wall_right_r2)
        setRotationAngle(lower_wall_right_r2, 0f, 0f, 0.2793f)
        lower_wall_right_r2.texOffs(116, 106).addBox(0f, -19f, -19f, 1, 17, 10, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        body.setModelPart()
        wall_1.setModelPart()
        wall_2.setModelPart()
        wall_3.setModelPart()
        doors.setModelPart()
        door_left.setModelPart(doors.name)
        door_right.setModelPart(doors.name)
    }

    @Override
    override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelNgongPing360 {
        return ModelNgongPing360(isRHT, doorAnimationType, renderDoorOverlay)
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
        if (renderStage == RenderStage.EXTERIOR) {
            renderMirror(wall_1, matrices, vertices, light, position.toFloat())
            if (isRHT) {
                renderOnceFlipped(body, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(wall_2, matrices, vertices, light, position.toFloat())
                renderOnceFlipped(wall_3, matrices, vertices, light, position.toFloat())
            } else {
                renderOnce(body, matrices, vertices, light, position.toFloat())
                renderOnce(wall_2, matrices, vertices, light, position.toFloat())
                renderOnce(wall_3, matrices, vertices, light, position.toFloat())
            }
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
        if (renderStage == RenderStage.EXTERIOR) {
            door_left.setOffset(0f, 0, if (isRHT) -doorRightZ else -doorLeftZ)
            door_right.setOffset(0f, 0, if (isRHT) doorRightZ else doorLeftZ)
            if (isRHT) {
                renderOnceFlipped(doors, matrices, vertices, light, position.toFloat())
            } else {
                renderOnce(doors, matrices, vertices, light, position.toFloat())
            }
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
        private const val DOOR_MAX = 9
    }
}
