package mtr.mappings;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class ScreenMapper extends Screen {

	private boolean renderingWidgets;

	protected ScreenMapper(Component title) {
		super(title);
	}

	public <T extends AbstractWidget> void addDrawableChild(T child) {
		addRenderableWidget(child);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		final boolean previousRenderingWidgets = renderingWidgets;
		renderingWidgets = true;
		try {
			super.render(graphics, mouseX, mouseY, delta);
		} finally {
			renderingWidgets = previousRenderingWidgets;
		}
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		// Legacy MTR screens draw their background explicitly, or intentionally
		// omit it for world previews. 1.21.1's Screen.render would blur them again.
		if (!renderingWidgets) {
			super.renderBackground(graphics, mouseX, mouseY, delta);
		}
	}
}
