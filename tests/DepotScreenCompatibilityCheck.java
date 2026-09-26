package mtr.screen;

import mtr.data.Depot;
import mtr.data.TransportMode;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.Bootstrap;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/** Real depot widgets, hit testing, input dispatch and save serialization; window/audio/network are boundaries. */
public final class DepotScreenCompatibilityCheck {
    private static final Unsafe UNSAFE;
    private static int assertions;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (ReflectiveOperationException e) { throw new ExceptionInInitializerError(e); }
    }

    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Minecraft client = allocate(Minecraft.class);
        Font font = allocate(Font.class);
        set(Font.class, font, "splitter", new StringSplitter((codePoint, style) -> 6));
        set(Minecraft.class, null, "instance", client);
        set(Minecraft.class, client, "font", font);
        RecordingGui gui = allocate(RecordingGui.class);
        set(Minecraft.class, client, "gui", gui);
        set(Minecraft.class, client, "soundManager", allocate(SilentSoundManager.class));
        for (int width : new int[]{427, 640, 1280}) {
            for (TransportMode mode : TransportMode.values()) {
                for (boolean realTime : new boolean[]{false, true}) {
                    SavingDepot depot = new SavingDepot(mode);
                    depot.name = "滨江综合基地A区";
                    depot.useRealTime = realTime;
                    EditDepotScreen screen = new EditDepotScreen(depot, mode, null);
                    screen.width = width;
                    screen.height = 360;
                    screen.init();
                    screen.tick();
                    Button button = (Button) get(EditDepotScreen.class, screen, "buttonEditInstructions");
                    double x = button.getX() + button.getWidth() / 2D, y = button.getY() + button.getHeight() / 2D;
                    require(screen.getChildAt(x, y).orElse(null) == button, "Edit instructions is blocked at " + width + "/" + mode + "/" + realTime);
                    gui.opened = null;
                    require(screen.mouseClicked(new MouseButtonEvent(x, y, new MouseButtonInfo(0, 0)), false), "Edit instructions click was not consumed");
                    require(depot.saves == 1, "Edit instructions must serialize the depot once");
                    require(gui.opened instanceof DashboardListSelectorScreen, "Edit instructions did not open the route selector");
                    gui.opened.width = width;
                    gui.opened.height = 360;
                    ((DashboardListSelectorScreen) gui.opened).init();
                    gui.opened.tick();
                    require(gui.opened.children().stream().filter(child -> child instanceof AbstractWidget).count() > 1, "Selector controls missing");
                }
            }
        }
        System.out.println("PASS: " + assertions + " real depot editor click/save/selector checks across sizes, transport modes and schedule modes (no window/network)");
    }

    private static <T> T allocate(Class<T> type) throws InstantiationException { return type.cast(UNSAFE.allocateInstance(type)); }
    private static Object get(Class<?> owner, Object target, String name) throws ReflectiveOperationException {
        Field field = owner.getDeclaredField(name); field.setAccessible(true); return field.get(target);
    }
    private static void set(Class<?> owner, Object target, String name, Object value) throws ReflectiveOperationException {
        Field field = owner.getDeclaredField(name); field.setAccessible(true); field.set(target, value);
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }

    private static final class SavingDepot extends Depot {
        private int saves;
        private SavingDepot(TransportMode mode) { super(mode); }
        @Override public void setData(Consumer<FriendlyByteBuf> sendPacket) {
            super.setData(packet -> { saves++; packet.release(); });
        }
    }
    private static final class RecordingGui extends Gui {
        private Screen opened;
        private RecordingGui() { super(null, null, (GuiRenderState) null); }
        @Override public void setScreen(Screen screen) { opened = screen; }
    }
    private static final class SilentSoundManager extends SoundManager {
        private SilentSoundManager() { super(null); }
        @Override public SoundEngine.PlayResult play(SoundInstance sound) { return null; }
    }
}
