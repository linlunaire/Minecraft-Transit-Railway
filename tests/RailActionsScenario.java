package mtr.data;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.msgpack.value.Value;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.*;

public final class RailActionsScenario {
    private static final RuntimeException FAILURE = new RuntimeException("fixture failure");
    private static final List<String> records = new ArrayList<>(), events = new ArrayList<>();
    private static final IdentityHashMap<Rail.RailActions, Integer> labels = new IdentityHashMap<>();
    private static List<Rail.RailActions> queue;
    private static boolean complete, failBuild, failSend;
    private static int assertions;

    public static String run(boolean woven) throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe"); unsafeField.setAccessible(true); Unsafe unsafe = (Unsafe) unsafeField.get(null);
        RailwayProbe railway = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class);
        PlayerProbe player = (PlayerProbe) unsafe.allocateInstance(PlayerProbe.class);
        Rail rail = (RailProbe) unsafe.allocateInstance(RailProbe.class);
        BlockPos start = new BlockPos(1, 2, 3), end = new BlockPos(4, 5, 6);
        Map<BlockPos, Map<BlockPos, Rail>> rails = new HashMap<>();
        rails.put(start, new HashMap<>()); rails.get(start).put(end, rail);
        RailwayDataRailActionsModule module = new RailwayDataRailActionsModule(railway, null, rails);
        queue = queue(module);
        require(queue != null && queue.isEmpty(), "Constructor did not initialize queue");
        module.tick(); capture("idle"); module.removeRailAction(0); capture("remove-absent");
        require(!module.markRailForBridge(null, null, null, 1, null), "Missing bridge rail accepted");
        require(!module.markRailForTunnel(null, null, null, 1, 2), "Missing tunnel rail accepted");
        require(!module.markRailForTunnelWall(null, null, null, 1, 2, null), "Missing tunnel-wall rail accepted"); capture("missing-rails");
        railway.contains = true;
        require(module.markRailForBridge(player, start, end, -1, Blocks.STONE.defaultBlockState()), "Bridge rejected"); capture("bridge");
        require(module.markRailForTunnel(player, start, end, 3, 4), "Tunnel rejected"); capture("tunnel");
        require(module.markRailForTunnelWall(player, start, end, Integer.MAX_VALUE, Integer.MIN_VALUE, null), "Wall rejected"); capture("wall-overflow");
        fails(NullPointerException.class, () -> module.markRailForBridge(null, start, end, 1, null)); capture("null-player");
        fails(NullPointerException.class, () -> module.markRailForTunnel(player, end, start, 1, 1)); capture("missing-inner-map");
        rails.get(start).put(end, null);
        fails(NullPointerException.class, () -> module.markRailForTunnel(player, start, end, 1, 1)); capture("null-rail");
        rails.get(start).put(end, rail);
        failSend = true; fails(FAILURE.getClass(), () -> module.markRailForBridge(player, start, end, 8, null)); capture("enqueue-send-failure");
        failSend = false; failBuild = true; fails(FAILURE.getClass(), module::tick); capture("build-failure");
        failBuild = false; module.tick(); capture("unfinished-head");
        complete = true; module.tick(); capture("one-completed");
        failSend = true; fails(FAILURE.getClass(), module::tick); capture("completion-send-failure");
        failSend = false; module.tick(); capture("next-after-failure");
        Rail.RailActions duplicate = queue.getFirst(); queue.add(duplicate);
        module.removeRailAction(duplicate.id); require(queue.isEmpty(), "Cancel must remove all duplicate IDs"); capture("duplicate-cancel");
        queue.add(duplicate); queue.add(null);
        fails(NullPointerException.class, () -> module.removeRailAction(duplicate.id));
        require(queue.size() == 2 && queue.getFirst() == duplicate, "removeIf failure partially mutated queue"); capture("cancel-null-tail");
        queue.clear(); queue.add(null); fails(NullPointerException.class, module::tick); capture("null-head"); queue.clear();
        Map<BlockPos, Map<BlockPos, Rail>> nullableRails = new HashMap<>(); nullableRails.put(null, new HashMap<>()); nullableRails.get(null).put(null, rail);
        RailwayDataRailActionsModule nullableModule = new RailwayDataRailActionsModule(railway, null, nullableRails); queue = queue(nullableModule);
        require(nullableModule.markRailForTunnel(player, null, null, -7, -8), "Legacy nullable map keys rejected"); capture("nullable-map-keys");
        nullableModule.tick(); capture("last-completed"); nullableModule.tick(); capture("empty-after-completion");
        if (woven) {
            Class<?> extra = Class.forName("cn.zbx1425.mtrsteamloco.data.RailActionsModuleExtraSupplier");
            require(extra.isInstance(nullableModule), "Woven extension interface missing");
            require(extra.getMethod("getRailActions").invoke(nullableModule) == queue, "Woven getter copied queue");
            require(extra.getMethod("getRails").invoke(nullableModule) == nullableRails, "Woven getter copied map");
            require(extra.getMethod("getWorld").invoke(nullableModule) == null, "Woven world changed");
            extra.getMethod("sendUpdateS2C").invoke(nullableModule); require(events.equals(List.of("send:[]")), "Woven update did not send live queue"); events.clear();
        }
        require(new RailwayDataRailActionsModule(null, null, null) != null, "Nullable construction rejected");
        System.out.println("Rail-action queue assertions: " + assertions + ", records: " + records.size());
        return String.join("\n", records) + "\n";
    }
    public static boolean build(Rail.RailActions action) {
        Objects.requireNonNull(action); events.add("build:" + label(action)); if (failBuild) throw FAILURE; return complete;
    }
    public static void packet(Level world, List<Rail.RailActions> actions) {
        require(world == null && actions == queue, "Send copied queue or changed world"); events.add("send:" + state()); if (failSend) throw FAILURE;
    }
    @SuppressWarnings("unchecked") private static List<Rail.RailActions> queue(RailwayDataRailActionsModule module) throws Exception {
        Field field = RailwayDataRailActionsModule.class.getDeclaredField("railActions"); field.setAccessible(true); return (List<Rail.RailActions>) field.get(module);
    }
    private static String state() {
        List<String> state = new ArrayList<>();
        for (Rail.RailActions action : queue) {
            if (action == null) { state.add("null"); continue; }
            try {
                List<String> fields = new ArrayList<>();
                for (String name : List.of("railActionType", "radius", "height", "length", "distance", "playerName", "uuid", "isSlab")) {
                    Field field = Rail.RailActions.class.getDeclaredField(name); field.setAccessible(true); fields.add(String.valueOf(field.get(action)));
                }
                state.add(label(action) + ":" + String.join(",", fields));
            } catch (ReflectiveOperationException error) { throw new AssertionError(error); }
        }
        return state.toString();
    }
    private static int label(Rail.RailActions action) { return labels.computeIfAbsent(action, ignored -> labels.size()); }
    private static void capture(String name) { records.add(name + "\t" + state() + "\t" + events); events.clear(); }
    private static void fails(Class<? extends Throwable> type, Runnable action) {
        try { action.run(); } catch (Throwable error) { require(error.getClass() == type, "Wrong failure " + error); if (type == FAILURE.getClass()) require(error == FAILURE, "Failure identity changed"); return; }
        throw new AssertionError("Expected " + type);
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    public static final class RailwayProbe extends RailwayData {
        boolean contains;
        private RailwayProbe() { super(null); }
        @Override public boolean containsRail(BlockPos first, BlockPos second) { events.add("contains:" + first + ":" + second); return contains; }
    }
    public static final class PlayerProbe extends ServerPlayer {
        private PlayerProbe() { super(null, null, (GameProfile) null, (ClientInformation) null); }
        @Override public UUID getUUID() { return new UUID(1, 2); }
        @Override public Component getName() { return Component.literal("Fixture"); }
    }
    public static final class RailProbe extends Rail {
        private RailProbe() { super((Map<String, Value>) null); }
        @Override public double getLength() { return 42.125; }
    }
}
