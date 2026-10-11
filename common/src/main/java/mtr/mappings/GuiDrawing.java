package mtr.mappings;

import mtr.MTR;
import mtr.client.Config;
import mtr.data.IGui;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

/** GUI-only equivalents of the immediate-mode helpers; every draw queues actual GUI state. */
public final class GuiDrawing {

	private GuiDrawing() {
	}

	public static void drawRectangle(GuiGraphicsExtractor graphics, double x1, double y1, double x2, double y2, int color) {
		if ((color >>> 24) == 0 || x1 == x2 || y1 == y2) {
			return;
		}
		// A transformed unit rectangle retains the original fractional map coordinates.
		graphics.pose().pushMatrix();
		try {
			graphics.pose().translate((float) x1, (float) y1);
			graphics.pose().scale((float) (x2 - x1), (float) (y2 - y1));
			graphics.fill(0, 0, 1, 1, color);
		} finally {
			graphics.pose().popMatrix();
		}
	}

	public static void drawStringWithFont(GuiGraphicsExtractor graphics, Font font, String text, float x, float y, int light) {
		drawStringWithFont(graphics, font, text, IGui.HorizontalAlignment.CENTER, IGui.VerticalAlignment.CENTER, x, y, -1, -1, 1, IGui.ARGB_WHITE, true);
	}

	public static void drawStringWithFont(GuiGraphicsExtractor graphics, Font font, String text, IGui.HorizontalAlignment horizontalAlignment, IGui.VerticalAlignment verticalAlignment, float x, float y, float maxWidth, float maxHeight, float scale, int color, boolean shadow) {
		final Style style = Config.useMTRFont() ? Style.EMPTY.withFont(new FontDescription.Resource(Identifier.fromNamespaceAndPath(MTR.MOD_ID, "mtr"))) : Style.EMPTY;
		while (text.contains("||")) {
			text = text.replace("||", "|");
		}
		final List<FormattedCharSequence> lines = new ArrayList<>();
		final List<Boolean> cjkLines = new ArrayList<>();
		int totalHeight = 0, totalWidth = 0;
		for (final String line : text.split("\\|")) {
			final boolean cjk = IGui.isCjk(line);
			final FormattedCharSequence orderedText = Text.literal(line).setStyle(style).getVisualOrderText();
			lines.add(orderedText);
			cjkLines.add(cjk);
			totalHeight += IGui.LINE_HEIGHT * (cjk ? 2 : 1);
			totalWidth = Math.max(totalWidth, font.width(orderedText) * (cjk ? 2 : 1));
		}
		if (lines.isEmpty() || maxWidth == 0 || maxHeight == 0) {
			return;
		}
		if (maxHeight >= 0 && totalHeight / scale > maxHeight) {
			scale = totalHeight / maxHeight;
		}
		final float scaleX = maxWidth >= 0 && totalWidth > maxWidth * scale ? totalWidth / maxWidth : scale;
		graphics.pose().pushMatrix();
		try {
			graphics.pose().scale(1 / scaleX, 1 / scale);
			float offset = verticalAlignment.getOffset(y * scale, totalHeight);
			for (int i = 0; i < lines.size(); i++) {
				final float extraScale = cjkLines.get(i) ? 2 : 1;
				final float xOffset = horizontalAlignment.getOffset(horizontalAlignment.getOffset(x * scaleX, totalWidth), font.width(lines.get(i)) * extraScale - totalWidth);
				graphics.pose().pushMatrix();
				try {
					graphics.pose().scale(extraScale, extraScale);
					graphics.pose().translate(xOffset / extraScale, offset / extraScale);
					graphics.text(font, lines.get(i), 0, 0, color, shadow);
				} finally {
					graphics.pose().popMatrix();
				}
				offset += IGui.LINE_HEIGHT * extraScale;
			}
		} finally {
			graphics.pose().popMatrix();
		}
	}
}
