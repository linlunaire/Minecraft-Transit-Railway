package mtr.servlet;

import mtr.data.*;
import mtr.mappings.Tuple;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sun.misc.Unsafe;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** Real domain objects, deterministic world identities and servlet transport probes. */
public final class ServletHandlersScenario {
    private static final List<String> records = new ArrayList<>();
    private static final List<Runnable> pending = new ArrayList<>();
    private static final Map<Level, RailwayProbe> railways = new IdentityHashMap<>();
    private static final Map<Level, List<Player>> worldPlayers = new IdentityHashMap<>();
    private static final Map<Player, String> playerNames = new IdentityHashMap<>();
    private static final Map<Player, Route> playerRoutes = new IdentityHashMap<>();
    private static final Map<Player, BlockPos> playerPositions = new IdentityHashMap<>();
    private static Unsafe unsafe;
    private static int assertions;
    private static Level firstWorld;

    public static String run() throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true); unsafe = (Unsafe) field.get(null);
        transport();
        var oldCallback = Webserver.callback; var oldWorlds = Webserver.getWorlds; var oldRoutes = Webserver.getRoutes; var oldCache = Webserver.getDataCache;
        try {
            Webserver.callback = pending::add;
            Webserver.getWorlds = ArrayList::new;
            for (HttpServlet handler : handlers()) {
                call(handler, Map.of(), true, "empty:" + handler.getClass().getSimpleName());
                Exchange nullResponse = new Exchange(Map.of()); invoke(handler, nullResponse.request, null);
                require(nullResponse.started == 1 && pending.size() == 1, "Null response failed before callback submission");
                fail(NullPointerException.class, () -> pending.removeFirst().run());
            }
            firstWorld = (Level) unsafe.allocateInstance(ServerLevel.class); Level secondWorld = (Level) unsafe.allocateInstance(ServerLevel.class);
            Webserver.getWorlds = () -> new ArrayList<>(List.of(firstWorld, secondWorld));
            RailwayProbe railway = fixture(); railways.put(firstWorld, railway);
            Webserver.getDataCache = data -> data == null ? null : data.dataCache; Webserver.getRoutes = data -> data.routes;
            Player rider = player("乘客🚉", new BlockPos(10, 64, 10), railway.dataCache.routeIdMap.get(100L));
            Player walker = player("Walker", new BlockPos(150, 64, 150), null); worldPlayers.put(firstWorld, List.of(rider, walker));
            call(new DataServletHandler(), Map.of(), true, "data");
            Station partialStation = railway.dataCache.stationIdMap.get(1L);
            Set<Station> connections = railway.dataCache.stationIdToConnectingStations.remove(partialStation);
            call(new DataServletHandler(), Map.of(), true, "data:missing-connection-set");
            railway.dataCache.stationIdToConnectingStations.put(partialStation, connections);
            call(new InfoServletHandler(), Map.of(), true, "info");
            call(new DelaysServletHandler(), Map.of(), true, "delays");
            for (String station : new String[]{"1", "0", "no", "9223372036854775808"}) for (String world : new String[]{"0", "1", "-1", "bad"})
                call(new ArrivalsServletHandler(), Map.of("stationId", station, "worldIndex", world), true, "arrivals:" + station + ":" + world);
            call(new ArrivalsServletHandler(), Map.of(), true, "arrivals:missing");
            Map<String, String> changing = new HashMap<>(Map.of("stationId", "1", "worldIndex", "0"));
            Exchange earlyParameters = new Exchange(changing); invoke(new ArrivalsServletHandler(), earlyParameters.request, earlyParameters.response);
            changing.put("stationId", "0"); pending.removeFirst().run(); finish(earlyParameters);
            records.add("arrivals:parameter-read-before-callback\t" + earlyParameters.describe());
            virtualForEach(railway, secondWorld);
            routeFinder(railway);
            for (HttpServlet handler : handlers()) {
                String name = handler.getClass().getSimpleName();
                Exchange exchange = new Exchange(Map.of());
                fail(NullPointerException.class, () -> invoke(handler, null, exchange.response));
                require(pending.isEmpty(), "Null request enqueued work for " + name);
            }
            require(pending.isEmpty(), "Leaked pending callbacks");
        } finally { Webserver.callback = oldCallback; Webserver.getWorlds = oldWorlds; Webserver.getRoutes = oldRoutes; Webserver.getDataCache = oldCache; }
        System.out.println("Servlet assertions: " + assertions + ", reference records: " + records.size());
        return String.join("\n", records) + "\n";
    }

    private static void transport() throws Exception {
        Locale before = Locale.getDefault();
        try { Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            for (TransportMode mode : TransportMode.values()) for (RouteType type : RouteType.values()) records.add("key:" + mode + ":" + type + "\t" + IServletHandler.createRouteKey(mode, type));
        } finally { Locale.setDefault(before); }
        fail(NullPointerException.class, () -> IServletHandler.createRouteKey(null, RouteType.values()[0]));
        fail(NullPointerException.class, () -> IServletHandler.createRouteKey(TransportMode.TRAIN, null));
        for (String content : new String[]{"", "plain", "港鐵🚉\n\"é\"", "\uD800"}) {
            Exchange e = new Exchange(Map.of()); IServletHandler.sendResponse(e.response, e.async, content);
            require(e.status == 0 && e.completed == 0 && e.bytes.size() == 0, "Response completed before write callback");
            e.budget = 0; e.listener.onWritePossible(); require(e.completed == 0, "Backpressure ignored");
            for (int count = 0; e.completed == 0 && count < 1000; count++) { e.budget = 1; e.listener.onWritePossible(); }
            require(e.completed == 1 && e.status == 200, "Ready response not completed once");
            require(Arrays.equals(e.bytes.toByteArray(), content.getBytes(StandardCharsets.UTF_8)), "UTF-8 bytes changed");
            require(e.headers.equals(List.of("Access-Control-Allow-Origin=*", "Content-Type=application/json")), "Headers changed");
            records.add("response:" + records.size() + "\t" + HexFormat.of().formatHex(e.bytes.toByteArray()) + ":" + e.completed + ":" + e.headers);
        }
        Exchange e = new Exchange(Map.of()); IServletHandler.sendResponse(e.response, e.async, "pending"); e.listener.onError(null);
        require(e.completed == 1 && e.status == 0, "Write failure must complete without status 200");
        Exchange nullContent = new Exchange(Map.of()); fail(NullPointerException.class, () -> IServletHandler.sendResponse(nullContent.response, nullContent.async, null));
        require(nullContent.headers.isEmpty(), "Null content changed response before failing");
        fail(NullPointerException.class, () -> IServletHandler.sendResponse(null, nullContent.async, "text"));
        Exchange nullAsync = new Exchange(Map.of()); IServletHandler.sendResponse(nullAsync.response, null, ""); nullAsync.budget = 1;
        fail(NullPointerException.class, () -> nullAsync.listener.onWritePossible()); require(nullAsync.status == 200, "Null async failure moved before status");
        Exchange throwing = new Exchange(Map.of()); throwing.throwOutput = true;
        PrintStream previous = System.err; ByteArrayOutputStream errors = new ByteArrayOutputStream();
        try { System.setErr(new PrintStream(errors, true, StandardCharsets.UTF_8)); IServletHandler.sendResponse(throwing.response, throwing.async, "content"); }
        finally { System.setErr(previous); }
        require(errors.toString(StandardCharsets.UTF_8).contains("fixture output unavailable") && throwing.completed == 0, "IOException handling changed");
        Exchange writeFailure = new Exchange(Map.of()); IServletHandler.sendResponse(writeFailure.response, writeFailure.async, "bytes");
        writeFailure.budget = 1; writeFailure.throwWrite = true;
        fail(IOException.class, () -> writeFailure.listener.onWritePossible());
        require(writeFailure.status == 0 && writeFailure.completed == 0, "Write IOException was swallowed or completed prematurely");
        writeFailure.listener.onError(new IOException("fixture callback error")); require(writeFailure.completed == 1, "Container error callback did not complete request");
    }

    private static void routeFinder(RailwayProbe railway) throws Exception {
        String[][] parameters = {
            {}, {"dimension", "wrong"}, {"dimension", "-1"}, {"dimension", "0"},
            {"dimension", "0", "startPlayer", "Missing", "startStation", "1", "startPos", "1,2,3", "endStation", "missing"},
            {"dimension", "0", "startPos", "1,2", "endPos", "no,2,3"},
            {"dimension", "0", "startPos", "-1.1,64.9,4.2,", "endPos", "151,64,151", "maxTickTime", "oops"},
            {"dimension", "OVERWORLD", "startStation", "中央", "endStation", "2", "maxTickTime", "-4"},
            {"dimension", "MINECRAFT:OVERWORLD", "startStation", "CENTRAL|", "endStation", "终点|Terminus"},
            {"dimension", "0", "startPlayer", "乘客🚉", "endPlayer", "wALKER", "maxTickTime", "2147483648"},
            {"dimension", "0", "startPos", "10,64,10", "endPos", "151,64,151", "maxTickTime", "0"},
            {"dimension", "1", "startPos", "1,2,3", "endPos", "4,5,6"}
        };
        for (int i = 0; i < parameters.length; i++) {
            Map<String, String> values = new HashMap<>(); for (int n = 0; n < parameters[i].length; n += 2) values.put(parameters[i][n], parameters[i][n + 1]);
            railway.finder.accept = true; railway.finder.callback = null;
            Exchange e = call(new RouteFinderServletHandler(), values, false, null);
            require(e.timeout == -1, "Route finder timeout changed");
            if (railway.finder.callback != null) {
                require(e.listener == null && e.completed == 0, "Route request responded before finder callback");
                var one = direction(new BlockPos(10, 64, 10), 32, 100, 7); one.stationIds.add(1L);
                var two = direction(new BlockPos(150, 64, 150), 43, 999, 0); two.stationIds.add(2L);
                var three = direction(new BlockPos(400, 64, 400), 55, 100, 3);
                railway.finder.callback.accept(new ArrayList<>(List.of(one, two, three)), 123);
                records.add("finder-input:" + i + "\t" + railway.finder.start + ":" + railway.finder.end + ":" + railway.finder.maxTickTime);
            }
            finish(e); records.add("finder:" + i + "\t" + e.describe());
        }
        railway.finder.accept = false;
        call(new RouteFinderServletHandler(), Map.of("dimension", "0", "startStation", "1", "endStation", "2"), true, "finder:queue-full");
        Webserver.getWorlds = () -> List.of(firstWorld);
        call(new RouteFinderServletHandler(), Map.of("dimension", "invalid"), true, "finder:invalid-single-world");
        Map<String, String> changing = new HashMap<>(); Exchange lateParameters = new Exchange(changing);
        invoke(new RouteFinderServletHandler(), lateParameters.request, lateParameters.response);
        changing.put("dimension", "0"); pending.removeFirst().run(); finish(lateParameters);
        records.add("finder:parameter-read-in-callback\t" + lateParameters.describe());
    }

    private static void virtualForEach(RailwayProbe railway, Level secondWorld) throws Exception {
        // Public Webserver callbacks can return subclasses with virtual forEach.
        // Kotlin inline iteration would silently bypass these overrides.
        OverrideList<Level> worlds = new OverrideList<>(List.of(firstWorld, secondWorld));
        Webserver.getWorlds = () -> worlds;
        for (HttpServlet handler : List.of(new DataServletHandler(), new InfoServletHandler(), new DelaysServletHandler(), new ArrivalsServletHandler()))
            call(handler, Map.of("stationId", "1"), true, "virtual-worlds:" + handler.getClass().getSimpleName());
        require(worlds.calls == 4, "Virtual world-list forEach bypassed");
        Webserver.getWorlds = () -> new ArrayList<>(List.of(firstWorld, secondWorld));

        OverrideSet<Route> routes = new OverrideSet<>(railway.routes); Webserver.getRoutes = data -> routes;
        call(new DataServletHandler(), Map.of(), true, "virtual-routes");
        require(routes.calls == 1, "Virtual route-set forEach bypassed"); Webserver.getRoutes = data -> data.routes;

        Station station = railway.dataCache.stationIdMap.get(1L);
        Set<Station> original = railway.dataCache.stationIdToConnectingStations.get(station);
        OverrideSet<Station> connections = new OverrideSet<>(List.of(railway.dataCache.stationIdMap.get(2L), station));
        railway.dataCache.stationIdToConnectingStations.put(station, connections);
        call(new DataServletHandler(), Map.of(), true, "virtual-connections");
        require(connections.calls == 3, "Virtual connection-set forEach bypassed"); railway.dataCache.stationIdToConnectingStations.put(station, original);

        List<Player> originalPlayers = worldPlayers.get(firstWorld); OverrideList<Player> players = new OverrideList<>(originalPlayers);
        worldPlayers.put(firstWorld, players); call(new InfoServletHandler(), Map.of(), true, "virtual-players");
        require(players.calls == 1, "Virtual player-list forEach bypassed"); worldPlayers.put(firstWorld, originalPlayers);

        Map<Long, Map<BlockPos, TrainDelay>> originalDelays = railway.delays;
        OverrideMap<Long, Map<BlockPos, TrainDelay>> outer = new OverrideMap<>();
        OverrideMap<BlockPos, TrainDelay> inner = new OverrideMap<>();
        inner.put(new BlockPos(2, 3, 4), new DelayProbe()); inner.put(new BlockPos(5, 6, 7), new DelayProbe());
        outer.put(100L, inner); outer.put(999L, Map.of(new BlockPos(8, 9, 10), new DelayProbe())); railway.delays = outer;
        call(new DelaysServletHandler(), Map.of(), true, "virtual-delay-maps");
        require(outer.calls == 1 && inner.calls == 1, "Virtual delay-map forEach bypassed"); railway.delays = originalDelays;

        DataCache originalCache = railway.dataCache;
        try {
            set(railway, "dataCache", null); railway.delays = Map.of();
            call(new DelaysServletHandler(), Map.of(), true, "delays:null-cache-empty-map");
            railway.delays = originalDelays;
            Exchange nonempty = new Exchange(Map.of()); invoke(new DelaysServletHandler(), nonempty.request, nonempty.response);
            fail(NullPointerException.class, () -> pending.removeFirst().run());
            require(nonempty.started == 1 && nonempty.completed == 0 && nonempty.listener == null, "Null cache failure moved past response setup");
        } finally { set(railway, "dataCache", originalCache); railway.delays = originalDelays; }
    }

    private static RailwayProbe fixture() throws Exception {
        RailwayProbe data = (RailwayProbe) unsafe.allocateInstance(RailwayProbe.class);
        Set<Station> stations = new LinkedHashSet<>(); Set<Platform> platforms = new LinkedHashSet<>(); Set<Route> routes = new LinkedHashSet<>();
        set(data, "stations", stations); set(data, "platforms", platforms); set(data, "routes", routes);
        DataCache cache = new DataCache(stations, platforms, new LinkedHashSet<>(), routes, new LinkedHashSet<>(), new LinkedHashSet<>()); set(data, "dataCache", cache);
        Station a = station(1, "中央|Central", 10, 10), b = station(2, "终点|Terminus", 150, 150); stations.add(a); stations.add(b);
        for (Station station : stations) { cache.stationIdMap.put(station.id, station); cache.stationIdToConnectingStations.put(station, new LinkedHashSet<>(List.of(station == a ? b : a))); }
        Platform p1 = platform(11, "A🚉", 10, 10), p2 = platform(12, "2", 150, 150), p3 = platform(13, "3", 12, 14); platforms.addAll(List.of(p1, p2, p3));
        for (Platform p : platforms) { cache.platformIdMap.put(p.id, p); cache.platformIdToStation.put(p.id, p == p2 ? b : a); }
        Route route = new Route(100, TransportMode.TRAIN); route.name = "环线|Loop🚉"; route.color = 0x123ABC; route.isLightRailRoute = true; route.lightRailRouteNumber = "L1"; route.circularState = Route.CircularState.CLOCKWISE;
        for (long id : new long[]{11, 99, 12, 13}) route.platformIds.add(new Route.RoutePlatform(id));
        Route other = new Route(101, TransportMode.BOAT); other.name = "渡轮"; other.color = 0x123ABC; other.circularState = Route.CircularState.ANTICLOCKWISE; other.platformIds.add(new Route.RoutePlatform(11)); other.platformIds.add(new Route.RoutePlatform(12)); other.platformIds.getFirst().customDestination = "自定义🚢";
        Route hidden = new Route(102, TransportMode.TRAIN); hidden.isHidden = true;
        routes.addAll(List.of(route, other, hidden)); for (Route r : routes) cache.routeIdMap.put(r.id, r);
        Depot depot = new Depot(200, TransportMode.TRAIN); depot.platformTimes.put(11L, new HashMap<>(Map.of(99L, 1.5F))); depot.platformTimes.put(99L, new HashMap<>(Map.of(12L, 2.75F))); depot.platformTimes.put(12L, new HashMap<>(Map.of(13L, 4F))); cache.routeIdToOneDepot.put(100L, depot);
        data.finder = new FinderProbe(data); set(data, "railwayDataRouteFinderModule", data.finder);
        data.schedules = new LinkedHashMap<>(); data.schedules.put(11L, List.of(new ScheduleEntry(2000, 1, 100, 0), new ScheduleEntry(1000, 1, 101, 0), new ScheduleEntry(3000, 1, 999, 0), new ScheduleEntry(4000, 1, 100, 3))); data.schedules.put(13L, List.of(new ScheduleEntry(1500, 1, 100, 2)));
        data.delays = new LinkedHashMap<>(); data.delays.put(100L, new LinkedHashMap<>(Map.of(new BlockPos(1, 2, 3), new DelayProbe()))); data.delays.put(999L, new LinkedHashMap<>(Map.of(new BlockPos(-5, 8, -9), new DelayProbe())));
        return data;
    }
    private static Station station(long id, String name, int x, int z) { Station station = new Station(id); station.name = name; station.color = (int) id; station.zone = (int) id + 2; station.corner1 = new Tuple<>(x - 3, z - 3); station.corner2 = new Tuple<>(x + 3, z + 3); return station; }
    private static RailwayDataRouteFinderModule.RouteFinderData direction(BlockPos pos, int duration, long routeId, int wait) throws Exception { var constructor = RailwayDataRouteFinderModule.RouteFinderData.class.getDeclaredConstructor(BlockPos.class, int.class, long.class, int.class); constructor.setAccessible(true); return constructor.newInstance(pos, duration, routeId, wait); }
    private static Platform platform(long id, String name, int x, int z) { Platform p = new Platform(id, TransportMode.TRAIN, new BlockPos(x, 64, z), new BlockPos(x, 64, z + 2)); p.name = name; return p; }
    private static Player player(String name, BlockPos pos, Route route) throws Exception { Player player = (Player) unsafe.allocateInstance(ServerPlayer.class); playerNames.put(player, name); playerPositions.put(player, pos); playerRoutes.put(player, route); return player; }
    public static RailwayData railway(Level world) { return railways.get(world); }
    public static List<? extends Player> players(Level world) { return worldPlayers.getOrDefault(world, List.of()); }
    public static ResourceKey<Level> dimension(Level world) { return world == firstWorld ? Level.OVERWORLD : Level.NETHER; }
    public static Component playerName(Player player) { return Component.literal(playerNames.get(player)); }
    public static BlockPos playerPosition(Player player) { return playerPositions.get(player); }
    public static Route ridingRoute(RailwayDataCoolDownModule ignored, Player player) { return playerRoutes.get(player); }
    private static List<HttpServlet> handlers() { return List.of(new DataServletHandler(), new InfoServletHandler(), new DelaysServletHandler(), new ArrivalsServletHandler(), new RouteFinderServletHandler()); }
    private static Exchange call(HttpServlet handler, Map<String, String> params, boolean finish, String key) throws Exception {
        Exchange exchange = new Exchange(params); invoke(handler, exchange.request, exchange.response);
        require(exchange.started == 1 && pending.size() == 1 && exchange.listener == null, "Handler did not defer work");
        pending.removeFirst().run();
        if (finish) finish(exchange);
        if (key != null) records.add(key + "\t" + exchange.describe());
        return exchange;
    }
    private static void finish(Exchange e) throws Exception { if (e.listener != null) { e.budget = Integer.MAX_VALUE; e.listener.onWritePossible(); require(e.completed == 1 && e.status == 200, "Handler response did not complete"); } }
    private static void invoke(HttpServlet handler, HttpServletRequest request, HttpServletResponse response) throws Exception { Method method = handler.getClass().getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class); method.setAccessible(true); try { method.invoke(handler, request, response); } catch (InvocationTargetException e) { if (e.getCause() instanceof Exception error) throw error; throw e; } }
    private static void set(Object instance, String name, Object value) throws Exception { Class<?> type = instance.getClass(); while (type != null) { try { Field f = type.getDeclaredField(name); f.setAccessible(true); f.set(instance, value); return; } catch (NoSuchFieldException e) { type = type.getSuperclass(); } } throw new NoSuchFieldException(name); }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    private interface Throwing { void run() throws Exception; }
    private static void fail(Class<? extends Throwable> type, Throwing action) throws Exception { try { action.run(); } catch (Throwable error) { require(error.getClass() == type, "Wrong failure " + error); return; } throw new AssertionError("Expected " + type); }

    public static final class RailwayProbe extends RailwayData {
        Map<Long, List<ScheduleEntry>> schedules; Map<Long, Map<BlockPos, TrainDelay>> delays; FinderProbe finder;
        private RailwayProbe() { super(null); }
        @Override public void getSchedulesForStation(Map<Long, List<ScheduleEntry>> target, long stationId) { schedules.forEach((id, list) -> { Station s = dataCache.platformIdToStation.get(id); if (s != null && s.id == stationId) target.put(id, list); }); }
        @Override public Map<Long, Map<BlockPos, TrainDelay>> getTrainDelays() { return delays; }
    }
    public static final class FinderProbe extends RailwayDataRouteFinderModule {
        boolean accept = true; BlockPos start, end; int maxTickTime; BiConsumer<List<RouteFinderData>, Integer> callback;
        FinderProbe(RailwayData data) { super(data, null, null); }
        @Override public boolean findRoute(BlockPos start, BlockPos end, int max, BiConsumer<List<RouteFinderData>, Integer> callback) { this.start = start; this.end = end; maxTickTime = max; this.callback = accept ? callback : null; return accept; }
        @Override public int getConnectionDensity(BlockPos from, BlockPos to) { return from.distManhattan(to); }
    }
    public static final class DelayProbe extends TrainDelay { @Override public int getDelayTicks() { return 42; } @Override public long getLastDelayTime() { return 1800000000123L; } }
    private static final class OverrideList<E> extends ArrayList<E> {
        int calls; OverrideList(Collection<E> values) { super(values); }
        @Override public void forEach(Consumer<? super E> action) { calls++; if (!isEmpty()) action.accept(getFirst()); }
    }
    private static final class OverrideSet<E> extends LinkedHashSet<E> {
        int calls; OverrideSet(Collection<E> values) { super(values); }
        @Override public void forEach(Consumer<? super E> action) { calls++; if (!isEmpty()) action.accept(iterator().next()); }
    }
    private static final class OverrideMap<K, V> extends LinkedHashMap<K, V> {
        int calls;
        @Override public void forEach(BiConsumer<? super K, ? super V> action) { calls++; if (!isEmpty()) { var entry = entrySet().iterator().next(); action.accept(entry.getKey(), entry.getValue()); } }
    }
    private static final class Exchange {
        Map<String, String> params = Map.of(); final ByteArrayOutputStream bytes = new ByteArrayOutputStream(); final List<String> headers = new ArrayList<>();
        int started, completed, status, budget; long timeout; boolean throwOutput, throwWrite; WriteListener listener;
        final ServletOutputStream output = new ServletOutputStream() {
            @Override public boolean isReady() { return budget > 0; }
            @Override public void setWriteListener(WriteListener value) { require(listener == null, "Listener registered twice"); listener = value; }
            @Override public void write(int value) throws IOException { require(budget > 0, "Write while not ready"); if (throwWrite) throw new IOException("fixture write failed"); budget--; bytes.write(value); }
        };
        final AsyncContext async = proxy(AsyncContext.class, (object, method, args) -> { switch (method.getName()) { case "complete" -> completed++; case "setTimeout" -> timeout = (long) args[0]; default -> throw new AssertionError("Unexpected async method " + method); } return null; });
        final HttpServletRequest request = proxy(HttpServletRequest.class, (object, method, args) -> switch (method.getName()) { case "getParameter" -> params.get((String) args[0]); case "startAsync" -> { started++; yield async; } default -> throw new AssertionError("Unexpected request method " + method); });
        final HttpServletResponse response = proxy(HttpServletResponse.class, (object, method, args) -> { switch (method.getName()) { case "addHeader" -> headers.add(args[0] + "=" + args[1]); case "setStatus" -> status = (int) args[0]; case "getOutputStream" -> { if (throwOutput) throw new IOException("fixture output unavailable"); return output; } default -> throw new AssertionError("Unexpected response method " + method); } return null; });
        Exchange(Map<String, String> params) { this.params = params; }
        String describe() { return status + ":" + completed + ":" + timeout + ":" + bytes.toString(StandardCharsets.UTF_8); }
        private static <T> T proxy(Class<T> type, InvocationHandler handler) { return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler)); }
    }
}
