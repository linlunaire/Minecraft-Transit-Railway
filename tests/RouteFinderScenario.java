package mtr.data;

import com.sun.management.ThreadMXBean;
import it.unimi.dsi.fastutil.longs.*;
import mtr.data.RailwayDataRouteFinderModule.*;
import net.minecraft.core.BlockPos;
import sun.misc.Unsafe;
import java.lang.management.ManagementFactory;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.BiConsumer;

public final class RouteFinderScenario {
    private static final RuntimeException FAILURE = new RuntimeException("callback failure");
    private static final List<String> records = new ArrayList<>();
    private static Unsafe unsafe;
    private static int assertions;
    public static String run(boolean allocation, boolean baseline) throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true); unsafe = (Unsafe) field.get(null);
        if (allocation) { allocations(baseline); return ""; }
        Random random = new Random(2622026L);
        for (int seed = 0; seed < 128; seed++) {
            RouteFinderCompatibilityCheck.now = RouteFinderCompatibilityCheck.EPOCH;
            RailwayProbe railway = graph(4 + random.nextInt(10));
            int size = railway.positions.size();
            for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) if (i != j && random.nextInt(4) == 0) {
                long route = 10 + random.nextInt(3);
                connect(railway, i, j, route, 1 + random.nextInt(300), -150 + random.nextInt(900));
            }
            RailwayDataRouteFinderModule finder = new RailwayDataRouteFinderModule(railway, null, null);
            List<String> results = new ArrayList<>();
            require(finder.findRoute(railway.positions.getFirst().offset(-3, 0, 1), railway.positions.getLast().offset(2, 0, -1), seed % 7 - 3,
                    (data, duration) -> results.add(duration + ":" + describe(data))), "Request rejected");
            int ticks = untilResult(finder, results);
            records.add("graph:" + seed + "\t" + ticks + ":" + results + ":" + densities(finder, railway));
        }
        schedules(); queueAndFailures(); densityAndAppend();
        ConnectionDetails details = new ConnectionDetails(null);
        details.addDurationInfo(7, 10); details.addDurationInfo(7, 20); details.addDurationInfo(Long.MIN_VALUE, Integer.MIN_VALUE);
        records.add("connection-overwrite\t" + get(details, "shortestDuration") + ":" + new TreeMap<>((Map<?, ?>) get(details, "durationInfo")));
        require(new RailwayDataRouteFinderModule(null, null, null) != null, "Nullable construction rejected");
        System.out.println("Route finder assertions: " + assertions + ", records: " + records.size() + ", 128 seeded graphs");
        return String.join("\n", records) + "\n";
    }
    private static void schedules() throws Exception {
        for (int delay : new int[]{-200, -101, -100, -99, -1, 0, 1, 100, 10000}) for (int deploy : new int[]{-50, 0, 5050}) {
            RouteFinderCompatibilityCheck.now = RouteFinderCompatibilityCheck.EPOCH;
            RailwayProbe railway = graph(4);
            connect(railway, 0, 1, 10, 20, delay); connect(railway, 1, 2, 10, 20, delay + 20); connect(railway, 2, 3, 11, 20, delay + 40);
            DepotProbe depot = new DepotProbe(); depot.deploy = deploy;
            railway.dataCache.routeIdToOneDepot.put(10L, depot); railway.dataCache.routeIdToOneDepot.put(11L, depot);
            RailwayDataRouteFinderModule finder = new RailwayDataRouteFinderModule(railway, null, null); List<String> results = new ArrayList<>();
            finder.findRoute(railway.positions.getFirst(), railway.positions.getLast(), 2, (data, duration) -> results.add(duration + ":" + describe(data)));
            int ticks = untilResult(finder, results);
            records.add("schedule:" + delay + ":" + deploy + "\t" + ticks + ":" + results + ":" + depot.calls);
        }
    }
    private static void queueAndFailures() throws Exception {
        RouteFinderCompatibilityCheck.now = RouteFinderCompatibilityCheck.EPOCH;
        RailwayProbe small = graph(3); RailwayDataRouteFinderModule finder = new RailwayDataRouteFinderModule(small, null, null);
        for (int i = 0; i < 10; i++) require(finder.findRoute(null, null, Integer.MIN_VALUE, null), "Queue rejected before ten pending requests");
        require(!finder.findRoute(null, null, 0, null), "Queue accepted eleventh pending request");
        for (int i = 0; i < 20; i++) finder.tick();
        records.add("under-four\t" + ((List<?>) get(finder, "routeFinderQueue")).size() + ":" + get(finder, "tickStage"));
        RailwayProbe railway = graph(4); finder = new RailwayDataRouteFinderModule(railway, null, null);
        List<String> results = new ArrayList<>();
        finder.findRoute(railway.positions.getFirst(), railway.positions.getLast(), 2, (data, duration) -> results.add("abandoned"));
        finder.tick(); finder.tick();
        finder.findRoute(railway.positions.get(1), railway.positions.get(2), Integer.MIN_VALUE, (data, duration) -> results.add("second:" + describe(data)));
        untilResult(finder, results);
        require(results.size() == 1 && results.getFirst().startsWith("second:"), "New request no longer resets active search");
        records.add("active-reset\t" + results);
        finder = new RailwayDataRouteFinderModule(railway, null, null); final var nullableCallback = finder;
        finder.findRoute(railway.positions.getFirst(), railway.positions.getLast(), 2, null);
        failsEventually(NullPointerException.class, nullableCallback); records.add("null-callback\t" + get(finder, "tickStage"));
        finder = new RailwayDataRouteFinderModule(railway, null, null); final var nullableEnd = finder;
        finder.findRoute(railway.positions.getFirst(), null, 2, (data, duration) -> { throw new AssertionError("Unexpected callback"); });
        finder.tick(); fails(NullPointerException.class, nullableEnd::tick); records.add("null-end\t" + get(finder, "tickStage"));
        finder = new RailwayDataRouteFinderModule(railway, null, null);
        finder.findRoute(null, railway.positions.getLast(), 2, (data, duration) -> { throw new AssertionError("Unexpected callback"); });
        finder.tick(); finder.tick(); finder.tick(); records.add("null-start\t" + get(finder, "tickStage"));
    }
    @SuppressWarnings("unchecked")
    private static void densityAndAppend() throws Exception {
        RouteFinderCompatibilityCheck.now = RouteFinderCompatibilityCheck.EPOCH;
        RailwayProbe railway = graph(4); RailwayDataRouteFinderModule finder = new RailwayDataRouteFinderModule(railway, null, null);
        List<List<RouteFinderData>> callbacks = new ArrayList<>(); boolean[] fail = {true};
        finder.findRoute(railway.positions.getFirst(), railway.positions.getLast(), 2, (data, duration) -> { callbacks.add(data); if (fail[0]) throw FAILURE; });
        failsEventually(FAILURE.getClass(), finder);
        List<RouteFinderData> internal = (List<RouteFinderData>) get(finder, "data");
        require(callbacks.getFirst().getFirst() == internal.getFirst(), "Single-segment callback no longer aliases search data");
        callbacks.getFirst().getFirst().stationIds.add(999L); fail[0] = false; finder.tick();
        records.add("callback-retry-alias\t" + describe(callbacks.get(0)) + ":" + describe(callbacks.get(1)) + ":" + get(finder, "count"));
        List<String> result = new ArrayList<>();
        finder.findRoute(railway.positions.getLast(), railway.positions.getLast(), 2, (data, duration) -> result.add(describe(data)));
        untilResult(finder, result); records.add("same-position-after-query\t" + result);
        Long2ObjectOpenHashMap<Long2IntOpenHashMap> current = (Long2ObjectOpenHashMap<Long2IntOpenHashMap>) get(finder, "connectionDensity");
        Long2ObjectOpenHashMap<Long2IntOpenHashMap> old = (Long2ObjectOpenHashMap<Long2IntOpenHashMap>) get(finder, "connectionDensityOld");
        BlockPos first = railway.positions.getFirst(), last = railway.positions.getLast();
        Long2IntOpenHashMap counts = new Long2IntOpenHashMap(); counts.put(last.asLong(), Integer.MAX_VALUE); current.put(first.asLong(), counts);
        set(finder, "count", 3); records.add("density-overflow\t" + finder.getConnectionDensity(first, last));
        Long2IntOpenHashMap previous = new Long2IntOpenHashMap(); previous.put(last.asLong(), 17); old.put(first.asLong(), previous);
        records.add("density-old-precedence\t" + finder.getConnectionDensity(first, last));
        set(finder, "count", 399); set(finder, "tickStage", Enum.valueOf((Class) get(finder, "tickStage").getClass(), "END_FIND_ROUTE"));
        ((List<?>) get(finder, "tempData")).clear(); finder.tick();
        require(get(finder, "connectionDensityOld") == current && get(finder, "connectionDensity") != current, "Density publication did not rotate references");
        records.add("density-rotation\t" + finder.getConnectionDensity(first, last) + ":" + get(finder, "count"));
        // Background sampling is deterministic under the random adapter.
        finder = new RailwayDataRouteFinderModule(railway, null, null); for (int i = 0; i < 200; i++) finder.tick();
        records.add("background-density\t" + get(finder, "count") + ":" + densities(finder, railway));
    }
    private static void allocations(boolean baseline) throws Exception {
        if (!ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Xint")) throw new AssertionError("Use -Xint for allocation checks");
        ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        if (!bean.isThreadAllocatedMemorySupported()) throw new AssertionError("Allocation accounting unavailable"); bean.setThreadAllocatedMemoryEnabled(true);
        long thread = Thread.currentThread().threadId();
        for (int size : new int[]{128, 1024, 4096}) {
            RailwayProbe railway = graph(size); long snapshots = 0, candidates = 0;
            for (int i = 0; i < 20; i++) {
                RouteFinderCompatibilityCheck.now = RouteFinderCompatibilityCheck.EPOCH;
                RailwayDataRouteFinderModule finder = new RailwayDataRouteFinderModule(railway, null, null);
                long before = bean.getThreadAllocatedBytes(thread); finder.tick(); long snapshot = bean.getThreadAllocatedBytes(thread) - before;
                finder.findRoute(railway.positions.getFirst(), railway.positions.getLast(), 2, (data, duration) -> { });
                finder.tick(); finder.tick();
                before = bean.getThreadAllocatedBytes(thread); finder.tick(); long candidate = bean.getThreadAllocatedBytes(thread) - before;
                if (i >= 4) { snapshots += snapshot; candidates += candidate; }
            }
            System.out.println("ROUTE_FINDER_ALLOCATION: " + size + " positions; snapshot=" + snapshots / 16 + ", candidate=" + candidates / 16 + " bytes/query (-Xint)");
            if (!baseline) {
                require(snapshots <= 16L * (size * 8L + 24000), "Boxed platform snapshot reintroduced");
                // Includes ~72 bytes/position for BlockPos and primitive-map growth.
                // The removed iterator boxing adds exactly 24 bytes/position on this JVM.
                require(candidates <= 16L * (size * 76L + 3000), "Boxed candidate iteration reintroduced");
            }
        }
    }
    private static RailwayProbe graph(int size) throws Exception {
        RailwayProbe railway = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class); railway.positions = new ArrayList<>(); railway.schedules = new HashMap<>();
        set(railway, "stations", new LinkedHashSet<Station>());
        DataCache cache = new DataCache(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>());
        set(railway, "dataCache", cache);
        for (int i = 0; i < size; i++) {
            BlockPos pos = new BlockPos(i * 40 + 1000, 64 + i % 3, i % 5 * 3); railway.positions.add(pos);
            cache.platformConnections.put(pos.asLong(), new Long2ObjectOpenHashMap<>()); cache.blockPosToStation.put(pos, new Station(i + 1));
        }
        return railway;
    }
    private static void connect(RailwayProbe railway, int from, int to, long route, int duration, int departure) {
        BlockPos pos = railway.positions.get(from); Platform platform = new Platform(100 + from, TransportMode.TRAIN, pos, pos.offset(1, 0, 0));
        var targets = railway.dataCache.platformConnections.get(pos.asLong()); long destination = railway.positions.get(to).asLong();
        ConnectionDetails details = targets.get(destination); if (details == null) { details = new ConnectionDetails(platform); targets.put(destination, details); }
        details.addDurationInfo(route, duration);
        railway.schedules.computeIfAbsent(platform.id, ignored -> new ArrayList<>()).add(new ScheduleEntry(RouteFinderCompatibilityCheck.EPOCH + departure * 50L, 4, route, 0));
    }
    private static int untilResult(RailwayDataRouteFinderModule finder, List<?> results) { int ticks = 0; while (results.isEmpty() && ticks < 10000) { finder.tick(); ticks++; } require(!results.isEmpty(), "Search did not finish"); return ticks; }
    private static String densities(RailwayDataRouteFinderModule finder, RailwayProbe railway) { List<Integer> values = new ArrayList<>(); for (int i = 1; i < railway.positions.size(); i++) values.add(finder.getConnectionDensity(railway.positions.get(i - 1), railway.positions.get(i))); return values.toString(); }
    private static String describe(List<RouteFinderData> data) { List<String> values = new ArrayList<>(); for (RouteFinderData part : data) values.add(part.pos.asLong() + ":" + part.duration + ":" + part.routeId + ":" + part.waitingTime + ":" + part.stationIds); return values.toString(); }
    private static Object get(Object value, String name) throws Exception { Field field = field(value.getClass(), name); return field.get(value); }
    private static void set(Object value, String name, Object replacement) throws Exception { field(value.getClass(), name).set(value, replacement); }
    private static Field field(Class<?> type, String name) throws Exception { while (type != null) { try { Field field = type.getDeclaredField(name); field.setAccessible(true); return field; } catch (NoSuchFieldException ignored) { type = type.getSuperclass(); } } throw new NoSuchFieldException(name); }
    private static void failsEventually(Class<? extends Throwable> type, RailwayDataRouteFinderModule finder) { for (int i = 0; i < 10000; i++) { try { finder.tick(); } catch (Throwable error) { verifyFailure(type, error); return; } } throw new AssertionError("Expected failure " + type); }
    private static void fails(Class<? extends Throwable> type, Runnable action) { try { action.run(); } catch (Throwable error) { verifyFailure(type, error); return; } throw new AssertionError("Expected failure " + type); }
    private static void verifyFailure(Class<? extends Throwable> type, Throwable error) { require(error.getClass() == type, "Wrong failure " + error); if (type == FAILURE.getClass()) require(error == FAILURE, "Failure identity changed"); }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    public static final class RailwayProbe extends RailwayData {
        List<BlockPos> positions; Map<Long, List<ScheduleEntry>> schedules;
        private RailwayProbe() { super(null); }
        @Override public List<ScheduleEntry> getSchedulesAtPlatform(long platformId) { return schedules.get(platformId); }
    }
    public static final class DepotProbe extends Depot {
        int deploy; final List<String> calls = new ArrayList<>();
        DepotProbe() { super(1, TransportMode.TRAIN); }
        @Override public int getMillisUntilDeploy(int count, int delay) { calls.add(count + ":" + delay); return deploy; }
    }
}
