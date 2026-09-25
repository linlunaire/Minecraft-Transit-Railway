package mtr.mappings;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;

public interface WidgetMapper extends Renderable {

	@Override
	default void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		render(graphics, mouseX, mouseY, delta);
	}

	void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta);
}
