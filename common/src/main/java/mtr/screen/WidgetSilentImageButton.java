package mtr.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class WidgetSilentImageButton extends ImageButton {

	private final boolean playSound;
	private final Identifier texture;
	private final int u, v, hoveredVOffset, textureWidth, textureHeight;

	public WidgetSilentImageButton(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, OnPress onPress, boolean playSound) {
		super(x, y, width, height, new WidgetSprites(texture, texture), onPress);
		this.playSound = playSound;
		this.texture = texture;
		this.u = u;
		this.v = v;
		this.hoveredVOffset = hoveredVOffset;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	@Override
	public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		// These are legacy texture-file UVs, not identifiers in Minecraft's GUI atlas.
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, getX(), getY(), u,
				v + (isHoveredOrFocused() ? hoveredVOffset : 0), width, height, textureWidth, textureHeight, ARGB.white(alpha));
	}

	@Override
	public void playDownSound(SoundManager soundManager) {
		if (playSound) {
			super.playDownSound(soundManager);
		}
	}
}
