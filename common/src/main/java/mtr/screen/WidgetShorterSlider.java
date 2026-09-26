package mtr.screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import mtr.data.IGui;
import mtr.mappings.Text;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import java.util.function.Consumer;
import java.util.function.Function;

public class WidgetShorterSlider extends AbstractSliderButton implements IGui {

	private final int maxValue;
	private final int markerFrequency;
	private final int markerDisplayedRatio;
	private final Function<Integer, String> setMessage;
	private final Consumer<Integer> shiftClickAction;

	private static final int SLIDER_WIDTH = HANDLE_WIDTH;
	private static final int TICK_HEIGHT = SQUARE_SIZE / 2;
	private static final Identifier SLIDER = Identifier.withDefaultNamespace("widget/slider");
	private static final Identifier SLIDER_HIGHLIGHTED = Identifier.withDefaultNamespace("widget/slider_highlighted");
	private static final Identifier HANDLE = Identifier.withDefaultNamespace("widget/slider_handle");
	private static final Identifier HANDLE_HIGHLIGHTED = Identifier.withDefaultNamespace("widget/slider_handle_highlighted");

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
	public void onClick(net.minecraft.client.input.MouseButtonEvent mc26Event, boolean doubleClick) {
		super.onClick(mc26Event, doubleClick);
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
	protected void onDrag(net.minecraft.client.input.MouseButtonEvent mc26Event, double f, double g) {
		super.onDrag(mc26Event, f, g);
		checkShiftClick();
	}

	@Override
	protected void applyValue() {
	}

	@Override
	public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
		render(guiGraphics);
	}

	public void renderButton(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
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

	private void render(GuiGraphicsExtractor guiGraphics) {
		final Minecraft client = Minecraft.getInstance();

		guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, isActive() && isFocused() && !canChangeValue ? SLIDER_HIGHLIGHTED : SLIDER, getX(), getY(), width, height, ARGB.white(alpha));
		final int xOffset = (int) (value * (width - SLIDER_WIDTH));
		guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, isActive() && (isHovered || canChangeValue) ? HANDLE_HIGHLIGHTED : HANDLE, getX() + xOffset, getY(), SLIDER_WIDTH, height, ARGB.white(alpha));

		guiGraphics.text(client.font, getMessage().getString(), UtilitiesClient.getWidgetX(this) + width + TEXT_PADDING, UtilitiesClient.getWidgetY(this) + (height - TEXT_HEIGHT) / 2, ARGB_WHITE);

		if (markerFrequency > 0) {
			for (int i = 1; i <= maxValue / markerFrequency; i++) {
				final int xOffset1 = (width - SLIDER_WIDTH) * i * markerFrequency / maxValue;
				final int tickX = getX() + xOffset1 + SLIDER_WIDTH / 2 - 1;
				guiGraphics.fill(tickX, getY() + height, tickX + 2, getY() + height + TICK_HEIGHT, ARGB_LIGHT_GRAY);
				guiGraphics.centeredText(client.font, String.valueOf(i * markerFrequency / markerDisplayedRatio), UtilitiesClient.getWidgetX(this) + xOffset1 + SLIDER_WIDTH / 2, UtilitiesClient.getWidgetY(this) + height + TICK_HEIGHT + 2, ARGB_WHITE);
			}
		}
	}

	private void checkShiftClick() {
		if (shiftClickAction != null && net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
			shiftClickAction.accept(getIntValue());
		}
	}
}
