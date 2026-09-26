package mtr.screen;
import net.minecraft.client.gui.GuiGraphics;

import mtr.mappings.ButtonMapper;
import net.minecraft.network.chat.Component;

public class WidgetBetterCheckbox extends ButtonMapper {

	private final OnClick onClick;
	private boolean checked;

	public WidgetBetterCheckbox(int x, int y, int width, int height, Component text, OnClick onClick) {
		super(x, y, width, height, text, button -> {
		});
		this.onClick = onClick;
	}

	@Override
	public void onPress() {
		checked = !checked;
		onClick.onClick(checked);
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		super.renderWidget(guiGraphics, mouseX, mouseY, delta);
		guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, (checked ? "[x] " : "[ ] ") + getMessage().getString(), getX() + 4, getY() + (height - 8) / 2, 0xFFFFFFFF);
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean selected() {
		return checked;
	}

	@FunctionalInterface
	public interface OnClick {
		void onClick(boolean checked);
	}
}
