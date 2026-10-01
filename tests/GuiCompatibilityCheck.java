package mtr.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import mtr.mappings.ScreenMapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import sun.misc.Unsafe;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/** Runs the real MTR dashboard, native Screen lifecycle and image buttons; only GPU drawing is recorded. */
public final class GuiCompatibilityCheck {

	private static final Unsafe UNSAFE = unsafe();
	private static final List<String> EVENTS = new ArrayList<>();
	private static final List<Draw> DRAWS = new ArrayList<>();
	private static final List<String> FAILURES = new ArrayList<>();
	private static int assertions;

	public static void main(String[] args) throws Exception {
		var out = System.out;
		net.minecraft.SharedConstants.tryDetectVersion();
		net.minecraft.server.Bootstrap.bootStrap();
		RecordingGraphics graphics = allocate(RecordingGraphics.class);
		graphics.matrices = new PoseStack();
		var scissorConstructor = Class.forName("net.minecraft.client.gui.GuiGraphics$ScissorStack").getDeclaredConstructor();
		scissorConstructor.setAccessible(true);
		set(GuiGraphics.class, graphics, "scissorStack", scissorConstructor.newInstance());
		checkDashboard(graphics);
		checkBackgroundOwnership(graphics);
		checkBackgroundExceptionCleanup(graphics);
		checkIcons(graphics, Path.of(args[0]));
		checkPagingIcons(graphics);
		checkTextureCoordinates(graphics);
		out.println("GUI production classes: " + DashboardScreen.class.getProtectionDomain().getCodeSource().getLocation());
		if (!FAILURES.isEmpty()) {
			FAILURES.forEach(failure -> out.println("FAIL: " + failure));
			throw new AssertionError("GUI compatibility: " + FAILURES.size() + " failed checks");
		}
		out.println("PASS: GUI compatibility, " + assertions + " assertions; actual dashboard/Screen and image buttons, three frames, background ownership, exception cleanup, PNG resources and hover/focus UVs (no GPU/window)");
	}

	private static void checkDashboard(RecordingGraphics graphics) throws Exception {
		ObservedDashboard dashboard = allocate(ObservedDashboard.class);
		initializeScreen(dashboard);
		set(DashboardScreen.class, dashboard, "widgetMap", allocate(RecordedMap.class));
		set(DashboardScreen.class, dashboard, "dashboardList", allocate(RecordedList.class));
		for (int frame = 1; frame <= 3; frame++) {
			EVENTS.clear();
			dashboard.renderWithTooltip(graphics, 0, 0, 0);
			require(EVENTS.equals(List.of("PANORAMA", "BLUR", "BACKGROUND", "MAP", "PANEL", "LIST", "WIDGETS")),
					"Dashboard frame " + frame + " must not blur or redraw a background after its content: " + EVENTS);
		}
	}

	private static void checkBackgroundOwnership(RecordingGraphics graphics) throws Exception {
		ObservedScreen screen = allocate(ObservedScreen.class);
		initializeScreen(screen);
		for (int frame = 1; frame <= 3; frame++) {
			EVENTS.clear();
			screen.mode = 0;
			screen.renderWithTooltip(graphics, 0, 0, 0);
			require(EVENTS.equals(List.of("CONTENT", "WIDGETS")), "World preview must not gain a background: " + EVENTS);
			EVENTS.clear();
			screen.mode = 1;
			screen.renderWithTooltip(graphics, 0, 0, 0);
			require(EVENTS.equals(List.of("PANEL", "CONTENT", "WIDGETS")), "Custom background must remain authoritative: " + EVENTS);
			EVENTS.clear();
			screen.mode = 2;
			screen.renderWithTooltip(graphics, 0, 0, 0);
			require(EVENTS.equals(List.of("PANORAMA", "BLUR", "BACKGROUND", "CONTENT", "WIDGETS")),
					"Explicit addon background must run exactly once: " + EVENTS);
		}
	}

	private static void checkBackgroundExceptionCleanup(RecordingGraphics graphics) throws Exception {
		ObservedScreen screen = allocate(ObservedScreen.class);
		initializeScreen(screen);
		set(Screen.class, screen, "renderables", List.<Renderable>of((g, x, y, d) -> { throw new ExpectedFailure(); }));
		boolean thrown = false;
		try {
			screen.renderWithTooltip(graphics, 0, 0, 0);
		} catch (ExpectedFailure expected) {
			thrown = true;
		}
		require(thrown, "Widget failure must escape the compatibility layer");
		EVENTS.clear();
		screen.renderBackground(graphics, 0, 0, 0);
		require(EVENTS.equals(List.of("PANORAMA", "BLUR", "BACKGROUND")), "Background suppression leaked after an exception: " + EVENTS);
		initializeScreen(screen);
		screen.mode = 2;
		EVENTS.clear();
		screen.renderWithTooltip(graphics, 0, 0, 0);
		require(EVENTS.equals(List.of("PANORAMA", "BLUR", "BACKGROUND", "CONTENT", "WIDGETS")), "Next frame did not recover: " + EVENTS);
	}

	private static void checkIcons(RecordingGraphics graphics, Path resources) throws Exception {
		Method factory = DashboardList.class.getDeclaredMethod("newImageButton", ResourceLocation.class, ImageButton.OnPress.class);
		factory.setAccessible(true);
		for (String name : List.of("left", "right", "find", "draw_area", "edit", "up", "down", "add", "delete")) {
			ResourceLocation texture = ResourceLocation.parse("mtr:textures/gui/icon_" + name + ".png");
			ImageButton button = name.equals("find")
					? new WidgetSilentImageButton(7, 8, 20, 20, 0, 0, 20, texture, 20, 40, b -> {}, false)
					: (ImageButton) factory.invoke(null, texture, (ImageButton.OnPress) b -> {});
			button.setX(7);
			button.setY(8);
			button.setWidth(20);
			checkPng(resources, texture);
			for (int state = 0; state < 4; state++) {
				button.active = state != 3;
				button.setFocused(state == 2);
				DRAWS.clear();
				button.render(graphics, state == 1 ? 8 : -1, state == 1 ? 9 : -1, 0);
				Draw expected = new Draw(false, texture, 7, 8, 0, state == 1 || state == 2 ? 20 : 0, 20, 20, 20, 40);
				require(DRAWS.equals(List.of(expected)), name + " state " + state + " must draw the legacy PNG slice, not a GUI atlas sprite: " + DRAWS);
			}
			button.visible = false;
			DRAWS.clear();
			button.render(graphics, 8, 9, 0);
			require(DRAWS.isEmpty(), name + " hidden button still rendered");
		}
	}

	private static void checkTextureCoordinates(RecordingGraphics graphics) {
		ResourceLocation texture = ResourceLocation.parse("mtr:textures/gui/custom.png");
		WidgetSilentImageButton button = new WidgetSilentImageButton(12, 13, 11, 9, 3, 4, 16, texture, 64, 96, b -> {}, false);
		for (boolean hover : List.of(false, true)) {
			DRAWS.clear();
			button.render(graphics, hover ? 13 : -1, hover ? 14 : -1, 0);
			require(DRAWS.equals(List.of(new Draw(false, texture, 12, 13, 3, hover ? 20 : 4, 11, 9, 64, 96))),
					"Constructor UVs, hover offset or odd widget dimensions were discarded: " + DRAWS);
		}
	}

	private static void checkPagingIcons(RecordingGraphics graphics) throws Exception {
		for (Class<?> owner : List.of(PIDSConfigScreen.class, RailwaySignScreen.class)) {
			Method factory = owner.getDeclaredMethod("newImageButton", ResourceLocation.class, ImageButton.OnPress.class);
			factory.setAccessible(true);
			for (String name : List.of("left", "right")) {
				ResourceLocation texture = ResourceLocation.parse("mtr:textures/gui/icon_" + name + ".png");
				ImageButton button = (ImageButton) factory.invoke(null, texture, (ImageButton.OnPress) b -> {});
				button.setWidth(20);
				for (boolean hover : List.of(false, true)) {
					DRAWS.clear();
					button.render(graphics, hover ? 1 : -1, hover ? 1 : -1, 0);
					require(DRAWS.equals(List.of(new Draw(false, texture, 0, 0, 0, hover ? 20 : 0, 20, 20, 20, 40))),
							owner.getSimpleName() + " paging icon still uses a GUI sprite: " + DRAWS);
				}
			}
		}
	}

	private static void checkPng(Path resources, ResourceLocation texture) throws Exception {
		String entry = "assets/" + texture.getNamespace() + "/" + texture.getPath();
		if (Files.isRegularFile(resources)) {
			try (ZipFile jar = new ZipFile(resources.toFile())) {
				require(jar.getEntry(entry) != null, "Shipped PNG is missing: " + entry);
				if (jar.getEntry(entry) != null) try (InputStream stream = jar.getInputStream(jar.getEntry(entry))) { checkPngDimensions(stream, entry); }
			}
		} else {
			require(Files.isRegularFile(resources.resolve(entry)), "PNG is missing: " + entry);
			if (Files.isRegularFile(resources.resolve(entry))) try (InputStream stream = Files.newInputStream(resources.resolve(entry))) { checkPngDimensions(stream, entry); }
		}
	}

	private static void checkPngDimensions(InputStream stream, String entry) throws Exception {
		var image = ImageIO.read(stream);
		require(image != null && image.getWidth() > 0 && image.getHeight() == image.getWidth() * 2,
				"Legacy normal/hover slices must occupy two square texture halves: " + entry);
	}

	private static void initializeScreen(Screen screen) throws Exception {
		set(Screen.class, screen, "minecraft", allocate(Minecraft.class));
		set(Screen.class, screen, "renderables", List.<Renderable>of((g, x, y, d) -> EVENTS.add("WIDGETS")));
	}

	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) FAILURES.add(message);
	}

	private static void set(Class<?> owner, Object object, String name, Object value) throws Exception {
		Field field = owner.getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, value);
	}

	private static <T> T allocate(Class<T> type) throws InstantiationException { return type.cast(UNSAFE.allocateInstance(type)); }

	private static Unsafe unsafe() {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		} catch (ReflectiveOperationException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private record Draw(boolean sprite, ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {}
	private static final class ExpectedFailure extends RuntimeException {}

	private static final class ObservedDashboard extends DashboardScreen {
		private ObservedDashboard() { super(null, false); }
		@Override protected void renderBlurredBackground(float delta) { EVENTS.add("BLUR"); }
		@Override protected void renderPanorama(GuiGraphics graphics, float delta) { EVENTS.add("PANORAMA"); }
		@Override protected void renderMenuBackground(GuiGraphics graphics) { EVENTS.add("BACKGROUND"); }
	}

	private static final class ObservedScreen extends ScreenMapper {
		private int mode;
		private ObservedScreen() { super(Component.empty()); }
		@Override public void render(GuiGraphics graphics, int x, int y, float delta) {
			if (mode == 1) graphics.fill(0, 0, 20, 20, 0xFF000000);
			if (mode == 2) ((Screen) this).renderBackground(graphics, x, y, delta);
			EVENTS.add("CONTENT");
			super.render(graphics, x, y, delta);
		}
		@Override protected void renderBlurredBackground(float delta) { EVENTS.add("BLUR"); }
		@Override protected void renderPanorama(GuiGraphics graphics, float delta) { EVENTS.add("PANORAMA"); }
		@Override protected void renderMenuBackground(GuiGraphics graphics) { EVENTS.add("BACKGROUND"); }
	}

	private static final class RecordedMap extends WidgetMap {
		private RecordedMap() { super(null, null, null, null, null, null); }
		@Override public void render(GuiGraphics graphics, int x, int y, float delta) { EVENTS.add("MAP"); }
	}

	private static final class RecordedList extends DashboardList {
		private RecordedList() { super(null, null, null, null, null, null, null, null, null); }
		@Override public void render(GuiGraphics graphics, Font font) { EVENTS.add("LIST"); }
	}

	private static final class RecordingGraphics extends GuiGraphics {
		private PoseStack matrices;
		private RecordingGraphics() { super(null, null); }
		@Override public PoseStack pose() { return matrices; }
		@Override public void fill(int x1, int y1, int x2, int y2, int color) { EVENTS.add("PANEL"); }
		@Override public void blitSprite(ResourceLocation sprite, int x, int y, int width, int height) {
			DRAWS.add(new Draw(true, sprite, x, y, 0, 0, width, height, 0, 0));
		}
		@Override public void blit(ResourceLocation texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
			DRAWS.add(new Draw(false, texture, x, y, u, v, width, height, textureWidth, textureHeight));
		}
	}
}
