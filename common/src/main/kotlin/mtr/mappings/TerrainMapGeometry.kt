package mtr.mappings

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.mixin.GuiGraphicsExtractorAccessor
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.core.BlockPos
import net.minecraft.world.level.levelgen.Heightmap
import org.joml.Matrix3x2f
import org.joml.Matrix3x2fc
import java.util.Arrays

/** One immutable terrain mesh, rather than one GUI overlap search per map cell. */
class TerrainMapGeometry private constructor(
    private val viewport: ScreenRectangle,
    private val rectangles: FloatArray,
    private val colors: IntArray,
) {
    fun submit(graphics: GuiGraphicsExtractor?) {
        if (colors.isEmpty()) return
        val pose: Matrix3x2fc = Matrix3x2f(graphics!!.pose())
        val bounds = viewport.transformMaxBounds(pose)
        (graphics as GuiGraphicsExtractorAccessor).`mtr$getGuiRenderState`().addGuiElement(State(this, pose, bounds))
    }

    private class State(
        private val geometry: TerrainMapGeometry,
        private val pose: Matrix3x2fc,
        private val bounds: ScreenRectangle,
    ) : GuiElementRenderState {
        override fun pipeline(): RenderPipeline = RenderPipelines.GUI
        override fun textureSetup(): TextureSetup = TextureSetup.noTexture()
        override fun scissorArea(): ScreenRectangle = bounds
        override fun bounds(): ScreenRectangle = bounds

        override fun buildVertices(vertices: VertexConsumer) {
            for (i in geometry.colors.indices) {
                val offset = i * 4
                val color = geometry.colors[i]
                val x1 = geometry.rectangles[offset]
                val y1 = geometry.rectangles[offset + 1]
                val x2 = geometry.rectangles[offset + 2]
                val y2 = geometry.rectangles[offset + 3]
                vertices.addVertexWith2DPose(pose, x1, y1).setColor(color)
                vertices.addVertexWith2DPose(pose, x1, y2).setColor(color)
                vertices.addVertexWith2DPose(pose, x2, y2).setColor(color)
                vertices.addVertexWith2DPose(pose, x2, y1).setColor(color)
            }
        }
    }

    private class Builder(private val viewport: ScreenRectangle) {
        private var rectangles = FloatArray(1024)
        private var colors = IntArray(256)
        private var size = 0

        fun add(worldX1: Int, worldZ1: Int, worldX2: Int, worldZ2: Int, color: Int, centerX: Double, centerZ: Double, scale: Double) {
            val x1 = Math.max(0.0, (worldX1 - centerX) * scale + viewport.width() / 2.0).toFloat()
            val y1 = Math.max(0.0, (worldZ1 - centerZ) * scale + viewport.height() / 2.0).toFloat()
            val x2 = Math.min(viewport.width().toDouble(), (worldX2 - centerX) * scale + viewport.width() / 2.0).toFloat()
            val y2 = Math.min(viewport.height().toDouble(), (worldZ2 - centerZ) * scale + viewport.height() / 2.0).toFloat()
            if (x1 >= x2 || y1 >= y2) return
            if (size == colors.size) {
                colors = Arrays.copyOf(colors, size * 2)
                rectangles = Arrays.copyOf(rectangles, size * 8)
            }
            val offset = size * 4
            rectangles[offset] = viewport.left() + x1
            rectangles[offset + 1] = viewport.top() + y1
            rectangles[offset + 2] = viewport.left() + x2
            rectangles[offset + 3] = viewport.top() + y2
            colors[size++] = color
        }

        fun build(): TerrainMapGeometry = TerrainMapGeometry(viewport, Arrays.copyOf(rectangles, size * 4), Arrays.copyOf(colors, size))
    }

    companion object {
        @JvmStatic
        fun capture(world: ClientLevel?, x: Int, y: Int, width: Int, height: Int, centerX: Double, centerZ: Double, scale: Double): TerrainMapGeometry {
            val builder = Builder(ScreenRectangle(x, y, Math.max(0, width), Math.max(0, height)))
            if (world == null || width <= 0 || height <= 0) return builder.build()
            val left = Math.floor(centerX - width / (2.0 * scale)).toInt()
            val top = Math.floor(centerZ - height / (2.0 * scale)).toInt()
            val right = Math.floor(centerX + width / (2.0 * scale)).toInt()
            val bottom = Math.floor(centerZ + height / (2.0 * scale)).toInt()
            val increment = if (scale >= 1) 1 else Math.ceil(1 / scale).toInt()
            val pos = BlockPos.MutableBlockPos()
            var worldX = left
            while (worldX <= right) {
                var runStart = top
                var runColor = 0
                var worldZ = top
                while (worldZ <= bottom) {
                    pos.set(worldX, world.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ) - 1, worldZ)
                    val mapColor = world.getBlockState(pos).getMapColor(world, pos).col
                    val color = 0xFF000000.toInt() or ((mapColor and 0xFEFEFE) ushr 1)
                    if (worldZ != top && color != runColor) {
                        builder.add(worldX, runStart, worldX + increment, worldZ, runColor, centerX, centerZ, scale)
                        runStart = worldZ
                    }
                    runColor = color
                    if (worldZ + increment > bottom) {
                        builder.add(worldX, runStart, worldX + increment, worldZ + increment, runColor, centerX, centerZ, scale)
                    }
                    worldZ += increment
                }
                worldX += increment
            }
            return builder.build()
        }
    }
}
