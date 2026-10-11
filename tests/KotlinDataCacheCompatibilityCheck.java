package mtr.data;

import it.unimi.dsi.fastutil.longs.*;
import mtr.mappings.Tuple;
import net.minecraft.core.BlockPos;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

/** Exercises actual cache refresh, its exported collections, subclass hook and static map helpers. */
public final class KotlinDataCacheCompatibilityCheck {
    private static final List<String> records = new ArrayList<>();
    private static int assertions;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("golden implementationSource [--record]");
        Path source = Path.of(args[1]).toRealPath();
        require(Path.of(DataCache.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(source), "Wrong cache implementation");
        if (args.length == 3) require(args[2].equals("--record") && Files.isRegularFile(source) && Arrays.stream(DataCache.class.getDeclaredAnnotations())
                .noneMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata")), "Record only original Java JAR");
        helpers(); refresh(); failures(); graphs();
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "Cache behavior differs from Java golden");
        System.out.println("PASS: " + assertions + " cache assertions, " + records.size() + " Java golden records; map helpers, duplicate IDs, reference pruning, area ordering, graph durations, failure publication and 128 seeded networks");
    }

    private static void helpers() {
        Map<String, Map<String, String>> map = new HashMap<>();
        require(DataCache.tryGet(map, "a", "b") == null && DataCache.tryGet(map, "a", "b", "fallback").equals("fallback"), "Missing outer key changed");
        map.put(null, new HashMap<>()); map.get(null).put(null, "value");
        require(DataCache.tryGet(map, null, null).equals("value"), "Nullable map keys changed");
        map.get(null).put(null, null);
        require(DataCache.tryGet(map, null, null, "fallback").equals("fallback") && DataCache.tryGet(map, null, null, null) == null, "Null value/default changed");
        expect("null-map", NullPointerException.class, () -> DataCache.tryGet((Map<String, Map<String, String>>) null, null, null));
        Long2ObjectOpenHashMap<Long2ObjectOpenHashMap<String>> objects = new Long2ObjectOpenHashMap<>();
        require(DataCache.tryGet(objects, 1, 2) == null, "Missing primitive outer key changed");
        DataCache.put(objects, 1, 2, old -> { require(old == null && objects.containsKey(1), "Inner map must exist before callback"); return "one"; });
        var inner = objects.get(1);
        DataCache.put(objects, 1, 2, old -> old + "-two");
        require(objects.get(1) == inner && DataCache.tryGet(objects, 1, 2).equals("one-two"), "Object inner-map identity changed");
        DataCache.put(objects, 1, 3, ignored -> null); require(inner.containsKey(3) && inner.get(3) == null, "Null object insertion changed");
        inner.defaultReturnValue("missing"); require(DataCache.tryGet(objects, 1, 99).equals("missing"), "Primitive object default ignored");
        expect("object-callback", IllegalStateException.class, () -> DataCache.put(objects, 9, 8, old -> { throw new IllegalStateException(); }));
        require(objects.containsKey(9) && objects.get(9).isEmpty(), "Throwing callback rolled back newly published map");
        expect("null-object-callback", NullPointerException.class, () -> DataCache.put(objects, 10, 1, null));
        require(objects.containsKey(10), "Null callback changed publication order");
        Long2ObjectOpenHashMap<Long2IntOpenHashMap> integers = new Long2ObjectOpenHashMap<>();
        require(DataCache.tryGet2(integers, 1, 2, -3) == -3, "Missing integer map changed");
        DataCache.put2(integers, 1, 2, old -> { require(old == 0 && integers.containsKey(1), "Integer callback old value/order changed"); return 4; });
        integers.get(1).defaultReturnValue(19);
        require(DataCache.tryGet2(integers, 1, 999, -3) == -3, "Explicit integer fallback ignored");
        DataCache.put2(integers, 1, 3, old -> old + 1); require(integers.get(1).get(3) == 20, "Integer map default changed");
        expect("null-int-value", NullPointerException.class, () -> DataCache.put2(integers, 8, 9, old -> null));
        require(integers.containsKey(8) && integers.get(8).isEmpty(), "Null integer callback publication changed");
        Map<Long, Station> ids = new HashMap<>(); ids.put(999L, station(999, 0, 0, 1, 1));
        Station first = station(1, 1, 1, 2, 2), last = station(1, 3, 3, 4, 4);
        Probe.map(ids, new LinkedHashSet<>(List.of(first, last)));
        require(ids.size() == 1 && ids.get(1L) == last, "Duplicate ID last-wins changed");
        expect("null-source", NullPointerException.class, () -> Probe.map(ids, null)); require(ids.isEmpty(), "Map must clear before null source failure");
        LinkedHashSet<Station> partial = new LinkedHashSet<>(); partial.add(first); partial.add(null); partial.add(last);
        expect("null-source-entry", NullPointerException.class, () -> Probe.map(ids, partial)); require(ids.size() == 1 && ids.get(1L) == first, "Partial map retention changed");
    }

    private static void refresh() throws Exception {
        Station a = station(1, -10, -10, 10, 10), b = station(2, 0, 0, 20, 20), c = station(3, 100, -10, 110, 10);
        Set<Station> stations = new LinkedHashSet<>(List.of(a, b, c));
        Platform p1 = platform(10, 1), p2 = platform(20, 15), p3 = platform(30, 105);
        Set<Platform> platforms = new LinkedHashSet<>(List.of(p1, p2, p3));
        Route route = route(7, 10, 999, 20, 20, 30);
        Set<Route> routes = new LinkedHashSet<>(List.of(route));
        Depot first = depot(100, -20, 20), last = depot(200, -20, 20);
        first.routeIds.addAll(Arrays.asList(7L, 777L, null)); last.routeIds.add(7L);
        last.platformTimes.put(10L, new HashMap<>(Map.of(20L, 12.5F)));
        last.platformTimes.put(20L, new HashMap<>(Map.of(20L, 0F, 30L, 19.4F)));
        Set<Depot> depots = new LinkedHashSet<>(List.of(first, last));
        Siding siding = new Siding(5, TransportMode.TRAIN, new BlockPos(1, 64, 1), new BlockPos(2, 64, 1), 2);
        Set<Siding> sidings = new LinkedHashSet<>(List.of(siding));
        Probe cache = new Probe(stations, platforms, sidings, routes, depots, new LinkedHashSet<>());
        require(cache.aliases(stations, platforms, sidings, routes, depots), "Constructor copied input sets");
        require(!cache.needsRefresh(0) && cache.needsRefresh(-1), "Initial refresh timestamp changed");
        Map<Long, Station> mapIdentity = cache.stationIdMap; var connectionsIdentity = cache.platformConnections;
        List<Route.RoutePlatform> entriesIdentity = route.platformIds; List<Long> idsIdentity = first.routeIds;
        cache.blockPosToStation.put(BlockPos.ZERO, a); cache.blockPosToPlatformId.put(0, 99);
        long before = System.currentTimeMillis(); cache.sync();
        require(cache.calls == 1 && cache.needsRefresh(before - 1) && !cache.needsRefresh(System.currentTimeMillis()), "Refresh hook/timestamp changed");
        require(mapIdentity == cache.stationIdMap && connectionsIdentity == cache.platformConnections, "Published map references changed");
        require(entriesIdentity == route.platformIds && idsIdentity == first.routeIds && first.routeIds.equals(List.of(7L)), "Pruning replaced list references");
        require(route.platformIds.stream().map(entry -> entry.platformId).toList().equals(List.of(10L, 20L, 20L, 30L)), "Pruning order/duplicates changed");
        require(cache.routeIdToOneDepot.get(7L) == last && cache.sidingIdToDepot.get(5L) == first, "Last depot route owner / first siding area changed");
        require(cache.platformIdToStation.get(10L) == a && cache.platformIdToStation.get(20L) == b && cache.platformIdToStation.get(30L) == c, "Platform area matching changed");
        require(cache.stationIdToConnectingStations.get(a).equals(Set.of(b)) && cache.stationIdToConnectingStations.get(b).equals(Set.of(a))
                && cache.stationIdToConnectingStations.get(c).isEmpty(), "Station adjacency changed");
        require(cache.blockPosToStation.isEmpty() && cache.blockPosToPlatformId.isEmpty(), "Lazy position caches not invalidated before hook");
        records.add("initial-graph\t" + describe(cache));
        platforms.remove(p2); stations.remove(b); depots.remove(last); cache.sync();
        require(cache.platformIdMap.get(20L) == null && route.platformIds.size() == 2 && cache.platformConnections.isEmpty(), "Input alias edits not seen on resync");
        records.add("pruned-graph\t" + describe(cache));
    }

    private static void failures() {
        Probe empty = new Probe(null, null, null, null, null, null);
        empty.stationIdMap.put(1L, station(1, 1, 1, 2, 2)); empty.routeIdToOneDepot.put(9L, depot(9, 1, 2));
        captureFailure(empty::sync);
        require(empty.stationIdMap.isEmpty() && empty.routeIdToOneDepot.containsKey(9L) && empty.calls == 0 && empty.needsRefresh(0), "Partial failed refresh changed");
        Probe hook = new Probe(new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>());
        hook.failHook = true; captureFailure(hook::sync);
        require(hook.calls == 1 && hook.needsRefresh(0), "Failed subclass hook no longer publishes refresh timestamp");
    }

    private static void graphs() throws Exception {
        Random random = new Random(55183); MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (int scenario = 0; scenario < 128; scenario++) {
            Set<Station> stations = new LinkedHashSet<>(); Set<Platform> platforms = new LinkedHashSet<>();
            Set<Route> routes = new LinkedHashSet<>(); Set<Depot> depots = new LinkedHashSet<>();
            for (int i = 0; i < 6; i++) stations.add(station(i + 1, i * 10 - 10, -10, i * 10 + 15, 10));
            for (int i = 0; i < 24; i++) platforms.add(platform(i + 1, random.nextInt(80)));
            for (int i = 0; i < 12; i++) {
                long[] ids = new long[2 + random.nextInt(10)]; for (int j = 0; j < ids.length; j++) ids[j] = random.nextInt(30) + 1;
                Route route = route(i + 1, ids); routes.add(route);
                Depot depot = depot(i + 1, -20, 100); depot.routeIds.add(route.id); depots.add(depot);
                for (int j = 1; j < ids.length; j++) {
                    float duration = switch (random.nextInt(6)) { case 0 -> 0; case 1 -> -1; case 2 -> Float.NaN; case 3 -> Float.POSITIVE_INFINITY; default -> random.nextFloat() * 100; };
                    depot.platformTimes.computeIfAbsent(ids[j - 1], ignored -> new HashMap<>()).put(ids[j], duration);
                }
            }
            Probe cache = new Probe(stations, platforms, new LinkedHashSet<>(), routes, depots, new LinkedHashSet<>());
            cache.sync(); require(cache.calls == 1 && cache.routeIdMap.size() == 12 && cache.platformIdMap.size() == 24, "Seeded graph failed refresh");
            for (Route route : routes) for (Route.RoutePlatform entry : route.platformIds) require(cache.platformIdMap.containsKey(entry.platformId), "Stale route reference survived");
            digest.update(describe(cache).getBytes(StandardCharsets.UTF_8));
        }
        records.add("seeded-graphs\t" + HexFormat.of().formatHex(digest.digest()));
    }

    private static String describe(DataCache cache) throws Exception {
        List<String> values = new ArrayList<>();
        cache.platformIdToStation.forEach((id, station) -> values.add("p" + id + "=" + station.id));
        cache.sidingIdToDepot.forEach((id, depot) -> values.add("s" + id + "=" + depot.id));
        cache.stationIdToConnectingStations.forEach((station, connected) -> values.add("a" + station.id + "=" + connected.stream().map(value -> value.id).sorted().toList()));
        var type = RailwayDataRouteFinderModule.ConnectionDetails.class;
        var start = type.getDeclaredField("platformStart"); start.setAccessible(true);
        var duration = type.getDeclaredField("shortestDuration"); duration.setAccessible(true);
        var info = type.getDeclaredField("durationInfo"); info.setAccessible(true);
        for (long key : cache.platformConnections.keySet()) {
            var inner = cache.platformConnections.get(key); values.add("n" + key + "=" + inner.size());
            for (long other : inner.keySet()) {
                var details = inner.get(other);
                @SuppressWarnings("unchecked") Map<Long, Integer> durations = (Map<Long, Integer>) info.get(details);
                values.add("e" + key + ">" + other + "=" + ((Platform) start.get(details)).id + ":" + duration.getInt(details) + ":" + new TreeMap<>(durations));
            }
        }
        Collections.sort(values); return String.join(";", values);
    }
    private static Station station(long id, int x1, int z1, int x2, int z2) { Station station = new Station(id); station.corner1 = new Tuple<>(x1, z1); station.corner2 = new Tuple<>(x2, z2); return station; }
    private static Platform platform(long id, int x) { return new Platform(id, TransportMode.TRAIN, new BlockPos(x, 64, 1), new BlockPos(x + 1, 64, 1)); }
    private static Route route(long id, long... platformIds) { Route route = new Route(id, TransportMode.TRAIN); for (long platformId : platformIds) route.platformIds.add(new Route.RoutePlatform(platformId)); return route; }
    private static Depot depot(long id, int min, int max) { Depot depot = new Depot(id, TransportMode.TRAIN); depot.corner1 = new Tuple<>(min, min); depot.corner2 = new Tuple<>(max, max); return depot; }
    private static void captureFailure(Runnable action) {
        PrintStream old = System.err; ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream stream = new PrintStream(output)) { System.setErr(stream); action.run(); require(output.size() > 0, "Expected caught failure diagnostics"); }
        finally { System.setErr(old); }
    }
    private static void expect(String label, Class<? extends Throwable> type, Runnable action) {
        try { action.run(); } catch (Throwable failure) { require(type.isInstance(failure), label + " wrong exception " + failure); records.add(label + "\t" + failure.getClass().getName()); return; }
        throw new AssertionError(label + " did not fail");
    }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    private static final class Probe extends DataCache {
        int calls; boolean failHook;
        Probe(Set<Station> stations, Set<Platform> platforms, Set<Siding> sidings, Set<Route> routes, Set<Depot> depots, Set<LiftServer> lifts) { super(stations, platforms, sidings, routes, depots, lifts); }
        @Override protected void syncAdditional() { calls++; if (failHook) throw new IllegalStateException("hook"); }
        boolean aliases(Set<Station> stations, Set<Platform> platforms, Set<Siding> sidings, Set<Route> routes, Set<Depot> depots) {
            return this.stations == stations && this.platforms == platforms && this.sidings == sidings && this.routes == routes && this.depots == depots;
        }
        static void map(Map<Long, Station> map, Set<Station> source) { mapIds(map, source); }
    }
}
