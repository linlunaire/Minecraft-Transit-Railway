package mtr.screen;
import net.minecraft.client.gui.GuiGraphics;

import mtr.data.IGui;
import mtr.mappings.Text;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;
import java.util.function.Function;

public class WidgetShorterSlider extends AbstractSliderButton implements IGui {

	private final int maxValue;
	private final int markerFrequency;
	private final int markerDisplayedRatio;
	private final Function<Integer, String> setMessage;
	private final Consumer<Integer> shiftClickAction;

	private static final int SLIDER_WIDTH = 6;
	private static final int TICK_HEIGHT = SQUARE_SIZE / 2;
	private static final ResourceLocation SLIDER_SPRITE = ResourceLocation.withDefaultNamespace("widget/slider");
	private static final ResourceLocation SLIDER_HANDLE_SPRITE = ResourceLocation.withDefaultNamespace("widget/slider_handle");
	private static final ResourceLocation SLIDER_HANDLE_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("widget/slider_handle_highlighted");

	public WidgetShorterSlider(int x, int width, int maxValue, int markerFrequency, int markerDisplayedRatio, Function<Integer, String> setMessage, Consumer<Integer> shiftClickAction) {
		super(x, 0, width, 0, Text.literal(""), 0);
		this.maxValue = maxValue;
		this.setMessage = setMessage;
		this.shiftClickAction = shiftClickAction;
		this.markerFrequency = markerFrequency;
		this.markerDisplayedRatio = markerDisplayedRatio;
	}

	public WidgetShorterSlider(int x, int width, int maxValue, Function<Integer, String> setMessage, Consumer<Integer> shiftClickAction) {
		this(x, width, maxValue, 0, 0, setMessage, shiftClickAction);
	}

	@Override
	public void onClick(double d, double e) {
		super.onClick(d, e);
		checkShiftClick();
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(Math.min(width, 380));
	}

	@Override
	protected void updateMessage() {
		setMessage(Text.literal(setMessage.apply(getIntValue())));
	}

	@Override
	protected void onDrag(double d, double e, double f, double g) {
		super.onDrag(d, e, f, g);
		checkShiftClick();
	}

	@Override
	protected void applyValue() {
	}

	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		render(guiGraphics);
	}

	public void renderButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		render(guiGraphics);
	}

	public void setValue(int valueInt) {
		value = (double) valueInt / maxValue;
		updateMessage();
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getIntValue() {
		return (int) Math.round(value * maxValue);
	}

	private void render(GuiGraphics guiGraphics) {
		final Minecraft client = Minecraft.getInstance();
		final int x = UtilitiesClient.getWidgetX(this);
		final int y = UtilitiesClient.getWidgetY(this);
		guiGraphics.blitSprite(SLIDER_SPRITE, x, y, width, height);

		final int xOffset = (width - SLIDER_WIDTH) * getIntValue() / maxValue;
		guiGraphics.blitSprite(UtilitiesClient.isHovered(this) ? SLIDER_HANDLE_HIGHLIGHTED_SPRITE : SLIDER_HANDLE_SPRITE, x + xOffset, y, SLIDER_WIDTH, height);

		guiGraphics.drawString(client.font, getMessage().getString(), x + width + TEXT_PADDING, y + (height - TEXT_HEIGHT) / 2, ARGB_WHITE);

		if (markerFrequency > 0) {
			for (int i = 1; i <= maxValue / markerFrequency; i++) {
				final int xOffset1 = (width - SLIDER_WIDTH) * i * markerFrequency / maxValue;
				final int tickX = x + xOffset1 + SLIDER_WIDTH / 3;
				guiGraphics.fill(tickX, y + height, tickX + 2, y + height + TICK_HEIGHT, ARGB_LIGHT_GRAY);
				guiGraphics.drawCenteredString(client.font, String.valueOf(i * markerFrequency / markerDisplayedRatio), x + xOffset1 + SLIDER_WIDTH / 2, y + height + TICK_HEIGHT + 2, ARGB_WHITE);
			}
		}
	}

	private void checkShiftClick() {
		if (shiftClickAction != null && Screen.hasShiftDown()) {
			shiftClickAction.accept(getIntValue());
		}
	}
}
