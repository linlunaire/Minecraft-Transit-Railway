package mtr.mappings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Keeps shared screens on MTR's render entry point while 26.2 extracts GUI state. */
public abstract class ScreenMapper extends Screen {

	private boolean renderingLegacyScreen;

	protected ScreenMapper(Component title) {
		super(title);
	}

	public <T extends AbstractWidget> void addDrawableChild(T child) {
		addRenderableWidget(child);
	}

	@Override
	public final void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		renderingLegacyScreen = true;
		try {
			render(graphics, mouseX, mouseY, delta);
		} finally {
			renderingLegacyScreen = false;
		}
	}

	public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);
	}

	public void renderBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractBackground(graphics, mouseX, mouseY, delta);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		// 26.2 requests a background before extracting content. Legacy screens own
		// that decision inside render(), including custom and intentionally absent
		// backgrounds. Keep explicit addon calls there, but avoid a second blur.
		if (renderingLegacyScreen) {
			super.extractBackground(graphics, mouseX, mouseY, delta);
		}
	}

	@Override
	public final void resize(int width, int height) {
		resize(minecraft, width, height);
	}

	public void resize(Minecraft client, int width, int height) {
		super.resize(width, height);
	}
}
