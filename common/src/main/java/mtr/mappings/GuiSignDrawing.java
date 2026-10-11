package mtr.mappings;

import mtr.block.BlockRailwaySign;
import mtr.client.CustomResources;
import mtr.data.IGui;
import mtr.render.RenderRailwaySign;
import mtr.render.RenderTrains;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/** The existing sign preview branch, submitted in GUI space instead of a world PoseStack. */
public final class GuiSignDrawing {

	private GuiSignDrawing() {
	}

	public static void drawSign(GuiGraphicsExtractor graphics, Font font, BlockPos pos, String signId, float x, float y, float size, float maxWidthLeft, float maxWidthRight, RenderRailwaySign.DrawTexture drawTexture) {
		if (RenderTrains.shouldNotRender(pos, RenderTrains.maxTrainRenderDistance, Direction.UP)) {
			return;
		}
		final CustomResources.CustomSign sign = RenderRailwaySign.getSign(signId);
		if (sign == null) {
			return;
		}
		final float signSize = (sign.small ? BlockRailwaySign.SMALL_SIGN_PERCENTAGE : 1) * size;
		final float margin = (size - signSize) / 2;
		drawTexture.drawTexture(sign.textureId, x + margin, y + margin, signSize, sign.flipTexture);
		if (sign.hasCustomText() && !RenderTrains.shouldNotRender(pos, RenderTrains.maxTrainRenderDistance / 2, null)) {
			final boolean placeholder = signId.equals(BlockRailwaySign.SignType.EXIT_LETTER.toString()) || signId.equals(BlockRailwaySign.SignType.EXIT_LETTER_FLIPPED.toString()) || signId.equals(BlockRailwaySign.SignType.LINE.toString()) || signId.equals(BlockRailwaySign.SignType.LINE_FLIPPED.toString());
			final float fixedMargin = size * (1 - BlockRailwaySign.SMALL_SIGN_PERCENTAGE) / 2;
			final float maxWidth = Math.max(0, (sign.flipCustomText ? maxWidthLeft : maxWidthRight) * size - fixedMargin * (sign.small ? 1 : 2));
			final float start = sign.flipCustomText ? x - (sign.small ? 0 : fixedMargin) : x + size + (sign.small ? 0 : fixedMargin);
			GuiDrawing.drawStringWithFont(graphics, font, placeholder ? "..." : sign.customText, sign.flipCustomText ? IGui.HorizontalAlignment.RIGHT : IGui.HorizontalAlignment.LEFT, IGui.VerticalAlignment.TOP, start, y + fixedMargin, maxWidth, size - fixedMargin * 2, 0.01F, IGui.ARGB_WHITE, false);
		}
	}
}
