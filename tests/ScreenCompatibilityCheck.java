package mtr.mappings;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/** Runs the actual 26.2 Screen lifecycle and blur state without creating a window. */
public final class ScreenCompatibilityCheck {

	private static final Unsafe UNSAFE;
	static {
		try {
			final Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			UNSAFE = (Unsafe) field.get(null);
		} catch (ReflectiveOperationException exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		final Minecraft minecraft = allocate(Minecraft.class);
		final Gui gui = allocate(Gui.class);
		set(Minecraft.class, minecraft, "gui", gui);
		set(Gui.class, gui, "hud", allocate(Hud.class));
		final GuiGraphicsExtractor graphics = allocate(GuiGraphicsExtractor.class);
		final GuiRenderState state = new GuiRenderState();
		set(GuiGraphicsExtractor.class, graphics, "guiRenderState", state);
		set(GuiGraphicsExtractor.class, graphics, "minecraft", minecraft);
		final Fixture screen = allocate(Fixture.class);
		set(Screen.class, screen, "minecraft", minecraft);
		for (int frame = 1; frame <= 3; frame++) {
			screen.extractRenderStateWithTooltipAndSubtitles(graphics, 10, 20, 0.5F);
			require(screen.backgrounds == frame && screen.blurs == frame && screen.panoramas == frame && screen.contents == frame,
				"The legacy background, blur, panorama and content must each run once per frame");
			state.reset();
		}
		// ANTE also explicitly calls the new background name from inside legacy render.
		screen.nativeBackground = true;
		screen.extractRenderStateWithTooltipAndSubtitles(graphics, 10, 20, 0.5F);
		require(screen.backgrounds == 4 && screen.blurs == 4 && screen.contents == 4,
			"Explicit addon extractBackground inside render must remain available");
		state.reset();
		screen.nativeBackground = false;
		screen.customBackground = true;
		screen.extractRenderStateWithTooltipAndSubtitles(graphics, 10, 20, 0.5F);
		require(screen.customBackgrounds == 1 && screen.backgrounds == 4 && screen.blurs == 4 && screen.contents == 5,
			"A legacy custom background must remain authoritative, without an automatic vanilla background");
		state.reset();
		screen.noBackground = true;
		screen.extractRenderStateWithTooltipAndSubtitles(graphics, 10, 20, 0.5F);
		require(screen.customBackgrounds == 1 && screen.backgrounds == 4 && screen.blurs == 4 && screen.contents == 6,
			"World-preview screens must keep their deliberate lack of background");
		screen.failRender = true;
		try {
			screen.extractRenderStateWithTooltipAndSubtitles(graphics, 10, 20, 0.5F);
			throw new AssertionError("The test render must fail");
		} catch (ExpectedRenderFailure expected) {
			// A failed frame must not make the next automatic background look explicit.
		}
		screen.extractBackground(graphics, 10, 20, 0.5F);
		require(screen.backgrounds == 4 && screen.blurs == 4, "Render phase leaked after an exception");
		System.out.println("PASS: actual 26.2 Screen lifecycle, real blur guard, three frames, legacy/custom/explicit addon backgrounds, world previews and exception cleanup");
	}

	private static <T> T allocate(Class<T> type) throws InstantiationException {
		return type.cast(UNSAFE.allocateInstance(type));
	}

	private static void set(Class<?> owner, Object instance, String name, Object value) throws ReflectiveOperationException {
		final Field field = owner.getDeclaredField(name);
		field.setAccessible(true);
		field.set(instance, value);
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}

	private static final class Fixture extends ScreenMapper {
		private int backgrounds, blurs, panoramas, contents, customBackgrounds;
		private boolean nativeBackground, customBackground, noBackground, failRender;

		private Fixture() { super(Component.empty()); }

		@Override
		public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
			if (failRender) throw new ExpectedRenderFailure();
			if (!noBackground) {
				if (nativeBackground) extractBackground(graphics, mouseX, mouseY, delta);
				else renderBackground(graphics, mouseX, mouseY, delta);
			}
			contents++;
		}

		@Override
		public void renderBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
			if (customBackground) customBackgrounds++;
			else super.renderBackground(graphics, mouseX, mouseY, delta);
		}

		@Override
		protected void extractBlurredBackground(GuiGraphicsExtractor graphics) {
			graphics.blurBeforeThisStratum();
			blurs++;
		}

		@Override
		protected void extractMenuBackground(GuiGraphicsExtractor graphics) { backgrounds++; }

		@Override
		protected void extractPanorama(GuiGraphicsExtractor graphics, float delta) { panoramas++; }
	}

	private static final class ExpectedRenderFailure extends RuntimeException { }
}
