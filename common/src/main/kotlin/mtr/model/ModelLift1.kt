package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.client.DoorAnimationType
import mtr.data.Lift
import mtr.mappings.ModelDataWrapper
import mtr.mappings.ModelMapper
import net.minecraft.client.renderer.texture.OverlayTexture

open class ModelLift1(
    height: Int,
    private val width: Int,
    private val depth: Int,
    private val isDoubleSided: Boolean
) : ModelTrainBase(DoorAnimationType.CONSTANT, false) {
    private val main: ModelMapper
    private val main_ceiling: ModelMapper
    private val main_edge: ModelMapper
    private val main_edge_wall: ModelMapper
    private val main_edge_ceiling: ModelMapper
    private val main_corner: ModelMapper
    private val handrail_bottom_r1: ModelMapper
    private val main_corner_wall: ModelMapper
    private val wall_r1: ModelMapper
    private val main_corner_ceiling: ModelMapper
    private val main_exterior: ModelMapper
    private val main_exterior_ceiling: ModelMapper
    private val main_exterior_edge: ModelMapper
    private val main_exterior_edge_ceiling: ModelMapper
    private val main_exterior_corner: ModelMapper
    private val main_exterior_corner_wall: ModelMapper
    private val main_exterior_corner_ceiling: ModelMapper
    private val main_light: ModelMapper
    private val door: ModelMapper
    private val door_left: ModelMapper
    private val door_right: ModelMapper
    private val door_wall: ModelMapper
    private val door_ceiling: ModelMapper
    private val door_exterior: ModelMapper
    private val door_left_exterior: ModelMapper
    private val door_right_exterior: ModelMapper
    private val door_wall_exterior: ModelMapper
    private val door_ceiling_exterior: ModelMapper
    private val wall_patch: ModelMapper
    private val wall_r2: ModelMapper
    private val wall_r3: ModelMapper
    private val wall_patch_wall: ModelMapper
    private val wall_r4: ModelMapper
    private val wall_r5: ModelMapper

    private val heightCount: Int
    private val heightOffset: Int

    init {
        heightCount = height - 4
        heightOffset = -heightCount * 8

        val textureWidth = 128
        val textureHeight = 128

        val modelDataWrapper = ModelDataWrapper(this, textureWidth, textureHeight)

        main = ModelMapper(modelDataWrapper)
        main.setPos(0f, 24f, 0f)
        main.texOffs(0, 34).addBox(-8f, 0f, -8f, 16, 0, 16, 0f, false)

        main_ceiling = ModelMapper(modelDataWrapper)
        main_ceiling.setPos(0f, 24f, 0f)
        main_ceiling.texOffs(79, 44).addBox(-8f, -32f, -8f, 16, 0, 16, 0f, false)

        main_edge = ModelMapper(modelDataWrapper)
        main_edge.setPos(0f, 24f, 0f)
        main_edge.texOffs(18, 44).addBox(-4f, 0f, -8f, 8, 0, 6, 0f, false)
        main_edge.texOffs(76, 33).addBox(-4f, -32f, -3f, 8, 32, 1, 0f, false)
        main_edge.texOffs(28, 52).addBox(-4f, -13f, -4f, 8, 1, 1, 0f, false)
        main_edge.texOffs(26, 50).addBox(-4f, -2f, -4f, 8, 1, 1, 0f, false)

        main_edge_wall = ModelMapper(modelDataWrapper)
        main_edge_wall.setPos(0f, 24f, 0f)
        main_edge_wall.texOffs(76, 33).addBox(-4f, -40f, -3f, 8, 8, 1, 0f, false)

        main_edge_ceiling = ModelMapper(modelDataWrapper)
        main_edge_ceiling.setPos(0f, 24f, 0f)
        main_edge_ceiling.texOffs(97, 44).addBox(-4f, -32f, -8f, 8, 0, 6, 0f, false)

        main_corner = ModelMapper(modelDataWrapper)
        main_corner.setPos(0f, 24f, 0f)
        main_corner.texOffs(20, 44).addBox(2f, 0f, -8f, 6, 0, 6, 0f, false)
        main_corner.texOffs(112, 62).addBox(3f, -32f, -3f, 5, 32, 1, 0f, false)
        main_corner.texOffs(29, 52).addBox(5f, -13f, -4f, 3, 1, 1, 0f, false)
        main_corner.texOffs(27, 50).addBox(5f, -2f, -4f, 3, 1, 1, 0f, false)
        main_corner.texOffs(104, 68).addBox(2f, -32f, -3f, 1, 32, 1, 0f, false)

        handrail_bottom_r1 = ModelMapper(modelDataWrapper)
        handrail_bottom_r1.setPos(0f, 0f, 0f)
        main_corner.addChild(handrail_bottom_r1)
        setRotationAngle(handrail_bottom_r1, 0f, -1.5708f, 0f)
        handrail_bottom_r1.texOffs(36, 50).addBox(-8f, -2f, -4f, 3, 1, 1, 0f, false)
        handrail_bottom_r1.texOffs(38, 52).addBox(-8f, -13f, -4f, 3, 1, 1, 0f, false)
        handrail_bottom_r1.texOffs(112, 62).addBox(-8f, -32f, -3f, 5, 32, 1, 0f, false)

        main_corner_wall = ModelMapper(modelDataWrapper)
        main_corner_wall.setPos(0f, 24f, 0f)
        main_corner_wall.texOffs(112, 62).addBox(3f, -40f, -3f, 5, 8, 1, 0f, false)
        main_corner_wall.texOffs(104, 68).addBox(2f, -40f, -3f, 1, 8, 1, 0f, false)

        wall_r1 = ModelMapper(modelDataWrapper)
        wall_r1.setPos(0f, 0f, 0f)
        main_corner_wall.addChild(wall_r1)
        setRotationAngle(wall_r1, 0f, -1.5708f, 0f)
        wall_r1.texOffs(112, 62).addBox(-8f, -40f, -3f, 5, 8, 1, 0f, false)

        main_corner_ceiling = ModelMapper(modelDataWrapper)
        main_corner_ceiling.setPos(0f, 24f, 0f)
        main_corner_ceiling.texOffs(99, 44).addBox(2f, -32f, -8f, 6, 0, 6, 0f, false)

        main_exterior = ModelMapper(modelDataWrapper)
        main_exterior.setPos(0f, 24f, 0f)
        main_exterior.texOffs(0, 17).addBox(-8f, 0f, -8f, 16, 1, 16, 0f, false)

        main_exterior_ceiling = ModelMapper(modelDataWrapper)
        main_exterior_ceiling.setPos(0f, 24f, 0f)
        main_exterior_ceiling.texOffs(0, 0).addBox(-8f, -33f, -8f, 16, 1, 16, 0f, false)

        main_exterior_edge = ModelMapper(modelDataWrapper)
        main_exterior_edge.setPos(0f, 24f, 0f)
        main_exterior_edge.texOffs(18, 27).addBox(-4f, 0f, -8f, 8, 1, 6, 0f, false)

        main_exterior_edge_ceiling = ModelMapper(modelDataWrapper)
        main_exterior_edge_ceiling.setPos(0f, 24f, 0f)
        main_exterior_edge_ceiling.texOffs(18, 10).addBox(-4f, -33f, -8f, 8, 1, 6, 0f, false)

        main_exterior_corner = ModelMapper(modelDataWrapper)
        main_exterior_corner.setPos(0f, 24f, 0f)
        main_exterior_corner.texOffs(20, 27).addBox(2f, 0f, -8f, 6, 1, 6, 0f, false)

        main_exterior_corner_wall = ModelMapper(modelDataWrapper)
        main_exterior_corner_wall.setPos(0f, 24f, 0f)
        main_exterior_corner_wall.texOffs(108, 68).addBox(2f, -40f, -3f, 1, 8, 1, 0f, false)

        main_exterior_corner_ceiling = ModelMapper(modelDataWrapper)
        main_exterior_corner_ceiling.setPos(0f, 24f, 0f)
        main_exterior_corner_ceiling.texOffs(20, 10).addBox(2f, -33f, -8f, 6, 1, 6, 0f, false)

        main_light = ModelMapper(modelDataWrapper)
        main_light.setPos(0f, 24f, 0f)
        main_light.texOffs(79, 28).addBox(-8f, -32.5f, -8f, 16, 0, 16, 0f, false)

        door = ModelMapper(modelDataWrapper)
        door.setPos(0f, 24f, 0f)
        door.texOffs(90, 66).addBox(-16f, -32f, 4f, 4, 32, 3, 0f, false)
        door.texOffs(14, 84).addBox(12f, -32f, 4f, 4, 32, 3, 0f, false)
        door.texOffs(20, 101).addBox(-16f, 0f, 0f, 32, 0, 8, 0f, false)

        door_left = ModelMapper(modelDataWrapper)
        door_left.setPos(0f, 0f, 0f)
        door.addChild(door_left)
        door_left.texOffs(52, 68).addBox(-12f, -32f, 6f, 12, 32, 0, 0f, false)

        door_right = ModelMapper(modelDataWrapper)
        door_right.setPos(0f, 0f, 0f)
        door.addChild(door_right)
        door_right.texOffs(28, 68).addBox(0f, -32f, 6f, 12, 32, 0, 0f, false)

        door_wall = ModelMapper(modelDataWrapper)
        door_wall.setPos(0f, 24f, 0f)
        door_wall.texOffs(48, 0).addBox(-16f, -40f, 4f, 32, 8, 3, 0f, false)

        door_ceiling = ModelMapper(modelDataWrapper)
        door_ceiling.setPos(0f, 24f, 0f)
        door_ceiling.texOffs(20, 101).addBox(-16f, -32f, 0f, 32, 0, 8, 0f, false)

        door_exterior = ModelMapper(modelDataWrapper)
        door_exterior.setPos(0f, 24f, 0f)
        door_exterior.texOffs(0, 84).addBox(-16f, -32f, 4f, 4, 32, 3, 0f, false)
        door_exterior.texOffs(76, 66).addBox(12f, -32f, 4f, 4, 32, 3, 0f, false)
        door_exterior.texOffs(28, 109).addBox(-16f, 0f, 0f, 32, 1, 8, 0f, false)

        door_left_exterior = ModelMapper(modelDataWrapper)
        door_left_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_left_exterior)
        door_left_exterior.texOffs(0, 50).addBox(-12f, -32f, 6f, 12, 32, 2, 0f, false)

        door_right_exterior = ModelMapper(modelDataWrapper)
        door_right_exterior.setPos(0f, 0f, 0f)
        door_exterior.addChild(door_right_exterior)
        door_right_exterior.texOffs(48, 34).addBox(0f, -32f, 6f, 12, 32, 2, 0f, false)

        door_wall_exterior = ModelMapper(modelDataWrapper)
        door_wall_exterior.setPos(0f, 24f, 0f)
        door_wall_exterior.texOffs(48, 17).addBox(-16f, -40f, 5f, 32, 8, 3, 0f, false)

        door_ceiling_exterior = ModelMapper(modelDataWrapper)
        door_ceiling_exterior.setPos(0f, 24f, 0f)
        door_ceiling_exterior.texOffs(0, 119).addBox(-16f, -33f, 0f, 32, 1, 8, 0f, false)

        wall_patch = ModelMapper(modelDataWrapper)
        wall_patch.setPos(0f, 24f, 0f)


        wall_r2 = ModelMapper(modelDataWrapper)
        wall_r2.setPos(0f, 0f, 0f)
        wall_patch.addChild(wall_r2)
        setRotationAngle(wall_r2, 0f, -1.5708f, 0f)
        wall_r2.texOffs(108, 95).addBox(0f, -32f, 13f, 4, 32, 1, 0f, false)
        wall_r2.texOffs(30, 50).addBox(0f, -2f, 12f, 4, 1, 1, 0f, false)
        wall_r2.texOffs(32, 52).addBox(0f, -13f, 12f, 4, 1, 1, 0f, false)

        wall_r3 = ModelMapper(modelDataWrapper)
        wall_r3.setPos(0f, 0f, 0f)
        wall_patch.addChild(wall_r3)
        setRotationAngle(wall_r3, 0f, 1.5708f, 0f)
        wall_r3.texOffs(108, 95).addBox(-4f, -32f, 13f, 4, 32, 1, 0f, false)
        wall_r3.texOffs(30, 50).addBox(-4f, -2f, 12f, 4, 1, 1, 0f, false)
        wall_r3.texOffs(32, 52).addBox(-4f, -13f, 12f, 4, 1, 1, 0f, false)

        wall_patch_wall = ModelMapper(modelDataWrapper)
        wall_patch_wall.setPos(0f, 24f, 0f)


        wall_r4 = ModelMapper(modelDataWrapper)
        wall_r4.setPos(0f, 0f, 0f)
        wall_patch_wall.addChild(wall_r4)
        setRotationAngle(wall_r4, 0f, -1.5708f, 0f)
        wall_r4.texOffs(108, 95).addBox(0f, -40f, 13f, 4, 8, 1, 0f, false)

        wall_r5 = ModelMapper(modelDataWrapper)
        wall_r5.setPos(0f, 0f, 0f)
        wall_patch_wall.addChild(wall_r5)
        setRotationAngle(wall_r5, 0f, 1.5708f, 0f)
        wall_r5.texOffs(108, 95).addBox(-4f, -40f, 13f, 4, 8, 1, 0f, false)

        modelDataWrapper.setModelPart(textureWidth, textureHeight)
        main.setModelPart()
        main_ceiling.setModelPart()
        main_edge.setModelPart()
        main_edge_wall.setModelPart()
        main_edge_ceiling.setModelPart()
        main_corner.setModelPart()
        main_corner_wall.setModelPart()
        main_corner_ceiling.setModelPart()
        main_exterior.setModelPart()
        main_exterior_ceiling.setModelPart()
        main_exterior_edge.setModelPart()
        main_exterior_edge_ceiling.setModelPart()
        main_exterior_corner.setModelPart()
        main_exterior_corner_wall.setModelPart()
        main_exterior_corner_ceiling.setModelPart()
        main_light.setModelPart()
        door.setModelPart()
        door_left.setModelPart(door.name)
        door_right.setModelPart(door.name)
        door_wall.setModelPart()
        door_ceiling.setModelPart()
        door_exterior.setModelPart()
        door_left_exterior.setModelPart(door_exterior.name)
        door_right_exterior.setModelPart(door_exterior.name)
        door_wall_exterior.setModelPart()
        door_ceiling_exterior.setModelPart()
        wall_patch.setModelPart()
        wall_patch_wall.setModelPart()
    }

    protected override fun render(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        currentCar: Int,
        trainCars: Int,
        head1IsFront: Boolean,
        renderDetails: Boolean
    ) {
        for (i in 0..width) {
            for (j in 0..depth) {
                val x = (i - width / 2f) * 16
                val z = (j - depth / 2f) * 16

                val edge1X = i == 0
                val edge2X = i == width
                val edge1Z = j == 0
                val edge2Z = j == depth

                when (renderStage!!) {
                    RenderStage.LIGHTS -> if (!edge1X && !edge2X && !edge1Z && !edge2Z) {
                        renderOnce(main_light, matrices, vertices, light, x, heightOffset.toFloat(), z)
                    }

                    RenderStage.INTERIOR, RenderStage.EXTERIOR -> {
                        val mainPiece = if (renderStage == RenderStage.INTERIOR) main else main_exterior
                        val mainCeilingPiece =
                            if (renderStage == RenderStage.INTERIOR) main_ceiling else main_exterior_ceiling
                        val mainEdgePiece = if (renderStage == RenderStage.INTERIOR) main_edge else main_exterior_edge
                        val mainEdgeCeilingPiece =
                            if (renderStage == RenderStage.INTERIOR) main_edge_ceiling else main_exterior_edge_ceiling
                        val mainCornerPiece =
                            if (renderStage == RenderStage.INTERIOR) main_corner else main_exterior_corner
                        val mainCornerWallPiece =
                            if (renderStage == RenderStage.INTERIOR) main_corner_wall else main_exterior_corner_wall
                        val mainCornerCeilingPiece =
                            if (renderStage == RenderStage.INTERIOR) main_corner_ceiling else main_exterior_corner_ceiling

                        if (!edge1X && !edge2X && !edge1Z && !edge2Z) {
                            renderOnce(mainPiece, matrices, vertices, light, x, z)
                            renderOnce(mainCeilingPiece, matrices, vertices, light, x, heightOffset.toFloat(), z)
                        }

                        if (edge1X && !edge2X && !edge1Z && !edge2Z) {
                            mainEdgePiece.render(
                                matrices,
                                vertices,
                                x,
                                z - 4,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            mainEdgePiece.render(
                                matrices,
                                vertices,
                                x,
                                z + 4,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(main_edge_wall, matrices, vertices, x, z - 4, -Math.PI.toFloat() / 2, light)
                                renderWall(main_edge_wall, matrices, vertices, x, z + 4, -Math.PI.toFloat() / 2, light)
                            }
                            mainEdgeCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z - 4,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            mainEdgeCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z + 4,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                        if (!edge1X && edge2X && !edge1Z && !edge2Z) {
                            mainEdgePiece.render(
                                matrices,
                                vertices,
                                x,
                                z - 4,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            mainEdgePiece.render(
                                matrices,
                                vertices,
                                x,
                                z + 4,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(main_edge_wall, matrices, vertices, x, z - 4, Math.PI.toFloat() / 2, light)
                                renderWall(main_edge_wall, matrices, vertices, x, z + 4, Math.PI.toFloat() / 2, light)
                            }
                            mainEdgeCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z - 4,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            mainEdgeCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z + 4,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                        if (!edge1X && !edge2X && !edge1Z && edge2Z && !isDoubleSided) {
                            renderOnce(mainEdgePiece, matrices, vertices, light, x - 4, z)
                            renderOnce(mainEdgePiece, matrices, vertices, light, x + 4, z)
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWallOnce(main_edge_wall, matrices, vertices, x - 4, z, light)
                                renderWallOnce(main_edge_wall, matrices, vertices, x + 4, z, light)
                            }
                            renderOnce(
                                mainEdgeCeilingPiece,
                                matrices,
                                vertices,
                                light,
                                x - 4,
                                heightOffset.toFloat(),
                                z
                            )
                            renderOnce(
                                mainEdgeCeilingPiece,
                                matrices,
                                vertices,
                                light,
                                x + 4,
                                heightOffset.toFloat(),
                                z
                            )
                        }

                        if (edge1X && !edge2X && !edge1Z && edge2Z && (width > 2 || !isDoubleSided)) {
                            mainCornerPiece.render(matrices, vertices, x, z, 0f, light, OverlayTexture.NO_OVERLAY)
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(mainCornerWallPiece, matrices, vertices, x, z, 0f, light)
                            }
                            mainCornerCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z,
                                0f,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                        if (!edge1X && edge2X && !edge1Z && edge2Z && (width > 2 || !isDoubleSided)) {
                            mainCornerPiece.render(
                                matrices,
                                vertices,
                                x,
                                z,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(mainCornerWallPiece, matrices, vertices, x, z, Math.PI.toFloat() / 2, light)
                            }
                            mainCornerCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z,
                                Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                        if (edge1X && !edge2X && edge1Z && !edge2Z && width > 2) {
                            mainCornerPiece.render(
                                matrices,
                                vertices,
                                x,
                                z,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(mainCornerWallPiece, matrices, vertices, x, z, -Math.PI.toFloat() / 2, light)
                            }
                            mainCornerCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z,
                                -Math.PI.toFloat() / 2,
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                        if (!edge1X && edge2X && edge1Z && !edge2Z && width > 2) {
                            mainCornerPiece.render(
                                matrices,
                                vertices,
                                x,
                                z,
                                Math.PI.toFloat(),
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                            // TODO edge exterior pieces
                            if (renderStage == RenderStage.INTERIOR) {
                                renderWall(mainCornerWallPiece, matrices, vertices, x, z, Math.PI.toFloat(), light)
                            }
                            mainCornerCeilingPiece.render(
                                matrices,
                                vertices,
                                x,
                                heightOffset.toFloat(),
                                z,
                                Math.PI.toFloat(),
                                light,
                                OverlayTexture.NO_OVERLAY
                            )
                        }
                    }

                    else -> {}
                }
            }
        }

        if (renderStage == RenderStage.INTERIOR || renderStage == RenderStage.EXTERIOR) {
            val doorLeftPiece = if (renderStage == RenderStage.INTERIOR) door_left else door_left_exterior
            val doorRightPiece = if (renderStage == RenderStage.INTERIOR) door_right else door_right_exterior
            val doorPiece = if (renderStage == RenderStage.INTERIOR) door else door_exterior
            val doorWallPiece = if (renderStage == RenderStage.INTERIOR) door_wall else door_wall_exterior
            val doorCeilingPiece = if (renderStage == RenderStage.INTERIOR) door_ceiling else door_ceiling_exterior
            val mainEdgePiece = if (renderStage == RenderStage.INTERIOR) main_edge else main_exterior_edge
            val mainEdgeCeilingPiece =
                if (renderStage == RenderStage.INTERIOR) main_edge_ceiling else main_exterior_edge_ceiling

            doorLeftPiece.setOffset(-doorLeftZ, 0, 0f)
            doorRightPiece.setOffset(doorLeftZ, 0, 0f)
            renderOnceFlipped(doorPiece, matrices, vertices, light, (8 - depth * 8).toFloat())
            renderWallOnceFlipped(doorWallPiece, matrices, vertices, 0f, (8 - depth * 8).toFloat(), light)
            renderOnceFlipped(
                doorCeilingPiece,
                matrices,
                vertices,
                light,
                0f,
                heightOffset.toFloat(),
                (8 - depth * 8).toFloat()
            )

            if (isDoubleSided) {
                doorLeftPiece.setOffset(-doorRightZ, 0, 0f)
                doorRightPiece.setOffset(doorRightZ, 0, 0f)
                renderOnce(doorPiece, matrices, vertices, light, (-8 + depth * 8).toFloat())
                renderWallOnce(doorWallPiece, matrices, vertices, 0f, (-8 + depth * 8).toFloat(), light)
                renderOnce(
                    doorCeilingPiece,
                    matrices,
                    vertices,
                    light,
                    0f,
                    heightOffset.toFloat(),
                    (-8 + depth * 8).toFloat()
                )
            }

            if (renderStage == RenderStage.INTERIOR && width == 2) {
                renderOnceFlipped(wall_patch, matrices, vertices, light, (8 - depth * 8).toFloat())
                renderWallOnceFlipped(wall_patch_wall, matrices, vertices, 0f, (8 - depth * 8).toFloat(), light)
                if (isDoubleSided) {
                    renderOnce(wall_patch, matrices, vertices, light, (-8 + depth * 8).toFloat())
                    renderWallOnce(wall_patch_wall, matrices, vertices, 0f, (-8 + depth * 8).toFloat(), light)
                }
            }

            for (i in 1..<width - 2) {
                renderOnceFlipped(
                    mainEdgePiece,
                    matrices,
                    vertices,
                    light,
                    (i * 8 - width * 8 + 4).toFloat(),
                    (-depth * 8).toFloat()
                )
                renderOnceFlipped(
                    mainEdgePiece,
                    matrices,
                    vertices,
                    light,
                    (-i * 8 + width * 8 - 4).toFloat(),
                    (-depth * 8).toFloat()
                )
                // TODO edge exterior pieces
                if (renderStage == RenderStage.INTERIOR) {
                    renderWallOnceFlipped(
                        main_edge_wall,
                        matrices,
                        vertices,
                        (i * 8 - width * 8 + 4).toFloat(),
                        (-depth * 8).toFloat(),
                        light
                    )
                    renderWallOnceFlipped(
                        main_edge_wall,
                        matrices,
                        vertices,
                        (-i * 8 + width * 8 - 4).toFloat(),
                        (-depth * 8).toFloat(),
                        light
                    )
                }
                renderOnceFlipped(
                    mainEdgeCeilingPiece,
                    matrices,
                    vertices,
                    light,
                    (i * 8 - width * 8 + 4).toFloat(),
                    heightOffset.toFloat(),
                    (-depth * 8).toFloat()
                )
                renderOnceFlipped(
                    mainEdgeCeilingPiece,
                    matrices,
                    vertices,
                    light,
                    (-i * 8 + width * 8 - 4).toFloat(),
                    heightOffset.toFloat(),
                    (-depth * 8).toFloat()
                )
                if (isDoubleSided) {
                    renderOnce(
                        mainEdgePiece,
                        matrices,
                        vertices,
                        light,
                        (i * 8 - width * 8 + 4).toFloat(),
                        (depth * 8).toFloat()
                    )
                    renderOnce(
                        mainEdgePiece,
                        matrices,
                        vertices,
                        light,
                        (-i * 8 + width * 8 - 4).toFloat(),
                        (depth * 8).toFloat()
                    )
                    // TODO edge exterior pieces
                    if (renderStage == RenderStage.INTERIOR) {
                        renderWallOnce(
                            main_edge_wall,
                            matrices,
                            vertices,
                            (i * 8 - width * 8 + 4).toFloat(),
                            (depth * 8).toFloat(),
                            light
                        )
                        renderWallOnce(
                            main_edge_wall,
                            matrices,
                            vertices,
                            (-i * 8 + width * 8 - 4).toFloat(),
                            (depth * 8).toFloat(),
                            light
                        )
                    }
                    renderOnce(
                        mainEdgeCeilingPiece,
                        matrices,
                        vertices,
                        light,
                        (i * 8 - width * 8 + 4).toFloat(),
                        heightOffset.toFloat(),
                        (depth * 8).toFloat()
                    )
                    renderOnce(
                        mainEdgeCeilingPiece,
                        matrices,
                        vertices,
                        light,
                        (-i * 8 + width * 8 - 4).toFloat(),
                        heightOffset.toFloat(),
                        (depth * 8).toFloat()
                    )
                }
            }
        }
    }

    protected override fun getDoorMax(): Int {
        return Lift.DOOR_MAX / 4
    }

    private fun renderWall(
        model: ModelMapper,
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        x: Float,
        z: Float,
        rotateY: Float,
        light: Int
    ) {
        for (i in 0..<heightCount) {
            model.render(matrices, vertices, x, (-i * 8).toFloat(), z, rotateY, light, OverlayTexture.NO_OVERLAY)
        }
    }

    private fun renderWallOnce(
        model: ModelMapper,
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        x: Float,
        z: Float,
        light: Int
    ) {
        for (i in 0..<heightCount) {
            renderOnce(model, matrices, vertices, light, x, (-i * 8).toFloat(), z)
        }
    }

    private fun renderWallOnceFlipped(
        model: ModelMapper,
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        x: Float,
        z: Float,
        light: Int
    ) {
        for (i in 0..<heightCount) {
            renderOnceFlipped(model, matrices, vertices, light, x, (-i * 8).toFloat(), z)
        }
    }
}
