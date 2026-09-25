package mtr.mappings;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

public abstract class ButtonMapper extends Button {

	public ButtonMapper(int x, int y, int width, int height, Component component, OnPress onPress) {
		super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
	}

	@Override
	protected final void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		renderWidget(graphics, mouseX, mouseY, delta);
	}

	public void renderWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		extractDefaultSprite(graphics);
		extractDefaultLabel(graphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
	}

	@Override
	public final void onPress(InputWithModifiers input) {
		onPress();
	}

	public void onPress() {
		onPress.onPress(this);
	}
}
