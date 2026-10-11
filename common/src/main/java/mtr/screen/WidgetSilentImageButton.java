package mtr.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;

public class WidgetSilentImageButton extends ImageButton {

	private final boolean playSound;
	private final ResourceLocation texture;
	private final int u, v, hoveredVOffset, textureWidth, textureHeight;

	public WidgetSilentImageButton(int x, int y, int width, int height, int u, int v, int hoveredVOffset, ResourceLocation texture, int textureWidth, int textureHeight, OnPress onPress, boolean playSound) {
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
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		// These constructor arguments describe a texture file and UV slices,
		// not sprite identifiers in Minecraft's GUI atlas.
		graphics.blit(texture, getX(), getY(), u, v + (isHoveredOrFocused() ? hoveredVOffset : 0),
				width, height, textureWidth, textureHeight);
	}

	@Override
	public void playDownSound(SoundManager soundManager) {
		if (playSound) {
			super.playDownSound(soundManager);
		}
	}
}
