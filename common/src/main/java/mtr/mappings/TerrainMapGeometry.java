package mtr.mappings;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.mixin.GuiGraphicsExtractorAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fc;

import java.util.Arrays;

/** One immutable terrain mesh, rather than one GUI overlap search per map cell. */
public final class TerrainMapGeometry {

	private final ScreenRectangle viewport;
	private final float[] rectangles;
	private final int[] colors;

	private TerrainMapGeometry(ScreenRectangle viewport, float[] rectangles, int[] colors) {
		this.viewport = viewport;
		this.rectangles = rectangles;
		this.colors = colors;
	}

	public static TerrainMapGeometry capture(ClientLevel world, int x, int y, int width, int height, double centerX, double centerZ, double scale) {
		final Builder builder = new Builder(new ScreenRectangle(x, y, Math.max(0, width), Math.max(0, height)));
		if (world == null || width <= 0 || height <= 0) return builder.build();
		final int left = (int) Math.floor(centerX - width / (2D * scale));
		final int top = (int) Math.floor(centerZ - height / (2D * scale));
		final int right = (int) Math.floor(centerX + width / (2D * scale));
		final int bottom = (int) Math.floor(centerZ + height / (2D * scale));
		final int increment = scale >= 1 ? 1 : (int) Math.ceil(1 / scale);
		final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int worldX = left; worldX <= right; worldX += increment) {
			int runStart = top, runColor = 0;
			for (int worldZ = top; worldZ <= bottom; worldZ += increment) {
				pos.set(worldX, world.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ) - 1, worldZ);
				final int mapColor = world.getBlockState(pos).getMapColor(world, pos).col;
				final int color = 0xFF000000 | (mapColor & 0xFEFEFE) >>> 1;
				if (worldZ != top && color != runColor) {
					builder.add(worldX, runStart, worldX + increment, worldZ, runColor, centerX, centerZ, scale);
					runStart = worldZ;
				}
				runColor = color;
				if (worldZ + increment > bottom) {
					builder.add(worldX, runStart, worldX + increment, worldZ + increment, runColor, centerX, centerZ, scale);
				}
			}
		}
		return builder.build();
	}

	public void submit(GuiGraphicsExtractor graphics) {
		if (colors.length == 0) return;
		final Matrix3x2fc pose = new Matrix3x2f(graphics.pose());
		final ScreenRectangle bounds = viewport.transformMaxBounds(pose);
		((GuiGraphicsExtractorAccessor) graphics).mtr$getGuiRenderState().addGuiElement(new State(this, pose, bounds));
	}

	private record State(TerrainMapGeometry geometry, Matrix3x2fc pose, ScreenRectangle bounds) implements GuiElementRenderState {
		@Override public RenderPipeline pipeline() { return RenderPipelines.GUI; }
		@Override public TextureSetup textureSetup() { return TextureSetup.noTexture(); }
		@Override public ScreenRectangle scissorArea() { return bounds; }

		@Override public void buildVertices(VertexConsumer vertices) {
			for (int i = 0; i < geometry.colors.length; i++) {
				final int offset = i * 4, color = geometry.colors[i];
				final float x1 = geometry.rectangles[offset], y1 = geometry.rectangles[offset + 1];
				final float x2 = geometry.rectangles[offset + 2], y2 = geometry.rectangles[offset + 3];
				vertices.addVertexWith2DPose(pose, x1, y1).setColor(color);
				vertices.addVertexWith2DPose(pose, x1, y2).setColor(color);
				vertices.addVertexWith2DPose(pose, x2, y2).setColor(color);
				vertices.addVertexWith2DPose(pose, x2, y1).setColor(color);
			}
		}
	}

	private static final class Builder {
		private final ScreenRectangle viewport;
		private float[] rectangles = new float[1024];
		private int[] colors = new int[256];
		private int size;

		private Builder(ScreenRectangle viewport) { this.viewport = viewport; }

		private void add(int worldX1, int worldZ1, int worldX2, int worldZ2, int color, double centerX, double centerZ, double scale) {
			final float x1 = (float) Math.max(0, (worldX1 - centerX) * scale + viewport.width() / 2D);
			final float y1 = (float) Math.max(0, (worldZ1 - centerZ) * scale + viewport.height() / 2D);
			final float x2 = (float) Math.min(viewport.width(), (worldX2 - centerX) * scale + viewport.width() / 2D);
			final float y2 = (float) Math.min(viewport.height(), (worldZ2 - centerZ) * scale + viewport.height() / 2D);
			if (x1 >= x2 || y1 >= y2) return;
			if (size == colors.length) {
				colors = Arrays.copyOf(colors, size * 2);
				rectangles = Arrays.copyOf(rectangles, size * 8);
			}
			final int offset = size * 4;
			rectangles[offset] = viewport.left() + x1;
			rectangles[offset + 1] = viewport.top() + y1;
			rectangles[offset + 2] = viewport.left() + x2;
			rectangles[offset + 3] = viewport.top() + y2;
			colors[size++] = color;
		}

		private TerrainMapGeometry build() {
			return new TerrainMapGeometry(viewport, Arrays.copyOf(rectangles, size * 4), Arrays.copyOf(colors, size));
		}
	}
}
