package mtr.screen;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.FormattedCharSequence;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Exercises production widgets against 26.2 extraction signatures and real texture resources, without a GPU. */
public final class GuiWidgetCompatibilityCheck {

    private static final Unsafe UNSAFE;
    private static Path resources;
    private static int assertions;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (ReflectiveOperationException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    public static void main(String[] args) throws Exception {
        resources = Path.of(args[0], "common/src/main/resources");
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Field instance = Minecraft.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, UNSAFE.allocateInstance(Minecraft.class));
        if (args.length < 2 || !args[1].equals("slider")) checkIcons();
        if (args.length < 2 || !args[1].equals("icons")) checkSlider();
        checkCheckbox();
        System.out.println("PASS: " + assertions + " GUI widget checks; actual production extraction, texture resources, hover slices, factories, slider endpoints, odd dimensions and single checkbox labels (no GPU)");
    }

    private static RecordingGraphics graphics() throws Exception {
        RecordingGraphics graphics = (RecordingGraphics) UNSAFE.allocateInstance(RecordingGraphics.class);
        graphics.draws = new ArrayList<>();
        return graphics;
    }

    private static void checkIcons() throws Exception {
        Identifier icon = Identifier.parse("mtr:textures/gui/icon_find.png");
        WidgetSilentImageButton button = new WidgetSilentImageButton(12, 13, 20, 20, 0, 0, 20, icon, 20, 40, ignored -> {}, false);
        for (boolean hovered : new boolean[]{false, true}) {
            setHovered(button, hovered);
            RecordingGraphics graphics = graphics();
            button.extractContents(graphics, 0, 0, 0);
            require(graphics.draws.size() == 1, "Icon must submit one texture slice");
            Draw draw = graphics.draws.getFirst();
            require(!draw.sprite && draw.texture.equals(icon), "Legacy icon must use direct texture extraction, not a GUI atlas ID");
            require(draw.x == 12 && draw.y == 13 && draw.width == 20 && draw.height == 20, "Icon destination changed");
            require(draw.u == 0 && draw.v == (hovered ? 20 : 0) && draw.textureWidth == 20 && draw.textureHeight == 40, "Normal/hovered icon must retain the legacy half-texture UVs");
        }
        // The public constructor also serves addons with non-zero source offsets.
        WidgetSilentImageButton offset = new WidgetSilentImageButton(5, 6, 7, 9, 2, 3, 11, icon, 40, 80, ignored -> {}, true);
        setHovered(offset, true);
        RecordingGraphics offsetGraphics = graphics();
        offset.extractContents(offsetGraphics, 0, 0, 0);
        Draw offsetDraw = offsetGraphics.draws.getFirst();
        require(offsetDraw.u == 2 && offsetDraw.v == 14 && offsetDraw.width == 7 && offsetDraw.height == 9, "Addon texture offsets and odd dimensions changed");
        for (String screen : List.of("DashboardList", "PIDSConfigScreen", "RailwaySignScreen")) {
            var factory = Class.forName("mtr.screen." + screen).getDeclaredMethod("newImageButton", Identifier.class, Button.OnPress.class);
            factory.setAccessible(true);
            ImageButton created = (ImageButton) factory.invoke(null, icon, (Button.OnPress) ignored -> {});
            created.setWidth(20);
            RecordingGraphics graphics = graphics();
            created.extractContents(graphics, 0, 0, 0);
            require(graphics.draws.size() == 1 && !graphics.draws.getFirst().sprite, screen + " still creates atlas buttons from file paths");
        }
    }

    private static void checkSlider() throws Exception {
        WidgetShorterSlider slider = new WidgetShorterSlider(12, 101, 100, 20, 2, Object::toString, null);
        slider.setY(13);
        slider.setHeight(11);
        for (int value : new int[]{0, 50, 100}) {
            slider.setValue(value);
            RecordingGraphics graphics = graphics();
            slider.extractWidgetRenderState(graphics, 0, 0, 0);
            require(graphics.draws.size() == 2 && graphics.draws.stream().allMatch(Draw::sprite), "Slider must use the two current 26.2 GUI sprites, not removed widgets.png");
            Draw track = graphics.draws.get(0), handle = graphics.draws.get(1);
            require(track.texture.equals(Identifier.withDefaultNamespace("widget/slider")), "Unexpected slider track sprite");
            require(track.x == 12 && track.y == 13 && track.width == 101 && track.height == 11, "Odd slider dimensions must not lose a row or column");
            require(handle.width == 8 && handle.height == 11 && handle.x == 12 + (int) (93 * (value / 100D)), "Handle geometry must match vanilla click/drag endpoints");
            require(graphics.ticks == 5 && graphics.labels == 5 && graphics.texts == 1, "Ticks and external value label must remain visible");
        }
        setHovered(slider, true);
        RecordingGraphics hovered = graphics();
        slider.extractWidgetRenderState(hovered, 0, 0, 0);
        require(hovered.draws.get(1).texture.equals(Identifier.withDefaultNamespace("widget/slider_handle_highlighted")), "Hovered slider handle must retain highlighting");
        // Older addon calls use this alias rather than the extraction entry point.
        RecordingGraphics legacy = graphics();
        slider.renderButton(legacy, 0, 0, 0);
        require(legacy.draws.equals(hovered.draws), "Legacy renderButton alias diverged");
    }

    private static void setHovered(AbstractWidget widget, boolean hovered) throws Exception {
        Field field = AbstractWidget.class.getDeclaredField("isHovered");
        field.setAccessible(true);
        field.setBoolean(widget, hovered);
    }

    private static void checkCheckbox() throws Exception {
        List<Boolean> changes = new ArrayList<>();
        Component message = Component.literal("Synchronize time / 将主世界与现实时间同步");
        WidgetBetterCheckbox checkbox = new WidgetBetterCheckbox(20, 20, 380, 20, message, changes::add);
        for (boolean checked : new boolean[]{false, true}) {
            checkbox.setChecked(checked);
            for (boolean active : new boolean[]{false, true}) {
                checkbox.active = active;
                for (boolean hovered : new boolean[]{false, true}) {
                    setHovered(checkbox, hovered);
                    RecordingGraphics graphics = graphics();
                    checkbox.renderWidget(graphics, 0, 0, 0);
                    require(graphics.labels + graphics.texts == 1, "Checkbox draws its label twice: " + (graphics.labels + graphics.texts));
                    require(graphics.lastText.equals((checked ? "[x] " : "[ ] ") + message.getString()), "Checkbox state or label missing");
                    require(graphics.draws.size() == 1, "Checkbox must retain its native background");
                    String sprite = !active ? "widget/button_disabled" : hovered ? "widget/button_highlighted" : "widget/button";
                    require(graphics.draws.getFirst().texture.equals(Identifier.withDefaultNamespace(sprite)), "Checkbox active/hover state changed");
                    require(checkbox.getMessage().getString().equals(message.getString()), "Checkbox narration message must not be cleared to hide duplicate text");
                }
            }
        }
        require(changes.isEmpty(), "Setting or rendering checkbox state must not send an update");
        checkbox.setChecked(false);
        checkbox.onPress();
        checkbox.onPress();
        require(changes.equals(List.of(true, false)), "Each checkbox press must toggle and notify exactly once");
    }

    private static void require(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }

    private record Draw(boolean sprite, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {}

    private static final class RecordingGraphics extends GuiGraphicsExtractor {
        private List<Draw> draws;
        private int ticks, labels, texts;
        private String lastText;
        private RecordingGraphics() { super(null, (GuiRenderState) null, 0, 0); }

        private void record(boolean sprite, Identifier id, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
            String path = "assets/" + id.getNamespace() + "/" + (sprite ? "textures/gui/sprites/" + id.getPath() + ".png" : id.getPath());
            require(Files.isRegularFile(resources.resolve(path)) || getClass().getClassLoader().getResource(path) != null, "Missing " + (sprite ? "GUI sprite" : "texture") + ": " + path);
            draws.add(new Draw(sprite, id, x, y, u, v, width, height, textureWidth, textureHeight));
        }

        @Override public void blit(RenderPipeline pipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
            record(false, texture, x, y, u, v, width, height, textureWidth, textureHeight);
        }
        @Override public void blit(RenderPipeline pipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
            record(false, texture, x, y, u, v, width, height, textureWidth, textureHeight);
        }
        @Override public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
            record(true, sprite, x, y, 0, 0, width, height, 0, 0);
        }
        @Override public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color) {
            record(true, sprite, x, y, 0, 0, width, height, 0, 0);
        }
        @Override public void fill(int x1, int y1, int x2, int y2, int color) { ticks++; }
        @Override public void text(Font font, String text, int x, int y, int color) { texts++; lastText = text; }
        @Override public void centeredText(Font font, String text, int x, int y, int color) { labels++; }
        @Override public ActiveTextCollector textRendererForWidget(AbstractWidget widget, HoveredTextEffects effects) {
            return new ActiveTextCollector() {
                private Parameters parameters;
                @Override public Parameters defaultParameters() { return parameters; }
                @Override public void defaultParameters(Parameters value) { parameters = value; }
                @Override public void accept(TextAlignment alignment, int x, int y, Parameters parameters, FormattedCharSequence text) { labels++; }
                @Override public void acceptScrolling(Component text, int centerX, int left, int right, int top, int bottom, Parameters parameters) { labels++; }
            };
        }
    }
}
