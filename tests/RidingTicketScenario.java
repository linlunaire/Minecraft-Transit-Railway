package mtr.data;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBufUtil;
import mtr.entity.EntitySeat;
import mtr.mappings.Tuple;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.scores.*;
import sun.misc.Unsafe;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

/** Production algorithms with real scoreboard, geometry, collections and packet codecs. */
public final class RidingTicketScenario {
    private static final List<String> records = new ArrayList<>(), events = new ArrayList<>();
    private static final Map<Player, State> state = new IdentityHashMap<>();
    private static final Map<EntitySeat, Integer> seats = new IdentityHashMap<>();
    private static final Set<Entity> removedSeats = Collections.newSetFromMap(new IdentityHashMap<>());
    private static final RuntimeException FAILURE = new IllegalStateException("fixture boundary failure");
    private static Unsafe unsafe;
    private static Level world;
    private static RailwayData data;
    private static RailwayDataCoolDownModule module;
    private static Scoreboard board;
    private static List<Player> online = new ArrayList<>();
    private static boolean selectiveEntities;
    private static String failAt;
    private static int assertions, nextPlayer, nextSeat;
    private static final Identifier PACKET = Identifier.fromNamespaceAndPath("mtr", "fixture_riding");
    private static final SoundEvent ENTER = sound("enter"), ENTER_CONCESSION = sound("enter_concession"), EXIT = sound("exit"), EXIT_CONCESSION = sound("exit_concession"), FAIL = sound("fail");

    public static String run(boolean fullTicketCorpus, boolean fareExtensions) throws Exception {
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        Field singleton = Unsafe.class.getDeclaredField("theUnsafe"); singleton.setAccessible(true); unsafe = (Unsafe) singleton.get(null);
        world = (Level) unsafe.allocateInstance(ServerLevel.class);
        fixture(); ticketCases(fullTicketCorpus); vehicleCases(); cooldownCases();
        // New contracts are assertions, not additions to the frozen Java oracle. Run
        // last so their fixture players cannot change identities in the old corpus.
        if (fareExtensions) fareAdjustmentCases();
        System.out.println("Riding/ticket assertions: " + assertions + ", records: " + records.size());
        return String.join("\n", records) + "\n";
    }

    private static void fixture() throws Exception {
        data = (RailwayData) unsafe.allocateInstance(RailwayData.class);
        Set<Station> stations = new LinkedHashSet<>(); Station station = new Station(1); station.name = "中央|Central🚉"; station.zone = 3; station.corner1 = new Tuple<>(-10, -10); station.corner2 = new Tuple<>(10, 10); stations.add(station);
        set(data, "stations", stations); set(data, "dataCache", new DataCache(stations, new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>(), new LinkedHashSet<>()));
        Route route = new Route(77, TransportMode.TRAIN); route.name = "Fixture route"; data.dataCache.routeIdMap.put(77L, route);
        board = new Scoreboard(); module = new RailwayDataCoolDownModule(data, world, null);
        online = new ArrayList<>(); events.clear(); failAt = null; selectiveEntities = false;
    }
    private static void ticketCases(boolean fullTicketCorpus) throws Exception {
        Player player = player(Vec3.ZERO); Station station = data.stations.iterator().next();
        for (TicketSystem.EnumTicketBarrierOpen value : TicketSystem.EnumTicketBarrierOpen.values()) record("ticket-enum:" + value, value.getSerializedName() + ":" + value.isOpen());
        TicketSystem.addObjectivesIfMissing(null);
        require(board.getObjectives().isEmpty(), "Null world unexpectedly created objectives");
        TicketSystem.addObjectivesIfMissing(world); TicketSystem.addObjectivesIfMissing(world);
        require(board.getObjectives().size() == 2 && board.getObjective("mtr_balance").getDisplayName().getString().equals("Balance"), "Scoreboard objective creation changed");
        int[] numbers = {Integer.MIN_VALUE, -501, -1, 0, 1, 499, 500, Integer.MAX_VALUE};
        int[] zones = {Integer.MIN_VALUE, -5, -1, 0, 1, 5, Integer.MAX_VALUE};
        Random random = new Random(2622600);
        for (int i = 0; i < 160; i++) {
            int balance = numbers[random.nextInt(numbers.length)], entry = zones[random.nextInt(zones.length)], zone = zones[random.nextInt(zones.length)];
            boolean entrance = random.nextBoolean(), exit = random.nextBoolean(), remind = random.nextBoolean(), creative = random.nextBoolean();
            // The full 160-case business corpus now runs through the pure transaction interface.
            // Keep representative real-scoreboard/text/sound wiring cases here; recording still
            // executes all original cases so the frozen Java fixture remains reproducible.
            if (!fullTicketCorpus && !RidingTicketCompatibilityCheck.includeTicketCase(i)) continue;
            ticketSetup(player, balance, entry, zone, creative); events.clear();
            String result = attempt(() -> pass(player, entrance, exit, remind));
            record("ticket:" + i, balance + ":" + entry + ":" + zone + ":" + entrance + ":" + exit + ":" + remind + ":" + creative + "->" + result + ":" + scores(player) + ":" + events);
        }
        for (String failure : new String[]{"message", "sound", "creative"}) {
            ticketSetup(player, 1000, 4, 7, false); failAt = failure; events.clear();
            record("ticket-failure:" + failure, attempt(() -> pass(player, false, true, false)) + ":" + scores(player) + ":" + events); failAt = null;
        }
        ticketSetup(player, 1000, 0, 3, false); station.name = null; events.clear();
        record("ticket-null-name", attempt(() -> pass(player, true, false, false)) + ":" + scores(player) + ":" + events); station.name = "中央|Central🚉";
        ticketSetup(player, 1000, 0, 3, false); events.clear();
        record("ticket-null-player", attempt(() -> pass(null, true, false, false)) + ":" + events);
        record("ticket-missing-objective", attempt(() -> TicketSystem.getPlayerScore(world, player, "missing")));
        RailwayData previous = data; data = null; events.clear();
        require(pass(null, true, false, false) == TicketSystem.EnumTicketBarrierOpen.CLOSED && events.isEmpty(), "Missing railway did not return early"); data = previous;
        require(TicketSystem.passThrough(world, new BlockPos(99, 0, 99), null, true, false, null, null, null, null, null, false) == TicketSystem.EnumTicketBarrierOpen.CLOSED, "Missing station must ignore null player/sounds");
        ticketSetup(player, -1, 0, 0, false); events.clear();
        record("ticket-null-fail-sound", TicketSystem.passThrough(world, BlockPos.ZERO, player, true, false, null, null, null, null, null, true) + ":" + scores(player) + ":" + events);
    }
    private static void ticketSetup(Player player, int balance, int entry, int zone, boolean creative) {
        board = new Scoreboard(); TicketSystem.addObjectivesIfMissing(world); TicketSystem.getPlayerScore(world, player, "mtr_balance").set(balance); TicketSystem.getPlayerScore(world, player, "mtr_entry_zone").set(entry);
        data.stations.iterator().next().zone = zone; state.get(player).creative = creative;
    }
    private static TicketSystem.EnumTicketBarrierOpen pass(Player player, boolean entrance, boolean exit, boolean remind) { return TicketSystem.passThrough(world, BlockPos.ZERO, player, entrance, exit, ENTER, ENTER_CONCESSION, EXIT, EXIT_CONCESSION, FAIL, remind); }
    private static String scores(Player player) { return TicketSystem.getPlayerScore(world, player, "mtr_balance").get() + ":" + TicketSystem.getPlayerScore(world, player, "mtr_entry_zone").get(); }

    private static void fareAdjustmentCases() throws Exception {
        fixture(); Player player = player(Vec3.ZERO); Station station = data.stations.iterator().next();
        String first = "fixture:fare-first", second = "fixture:fare-second", third = "fixture:fare-third";
        List<String> calls = new ArrayList<>(); int before = assertions;
        try {
            for (boolean concession : List.of(false, true)) {
                ticketSetup(player, 1000, 4, 8, concession); events.clear(); calls.clear();
                TicketSystem.registerFareAdjustment(first, (actualStation, actualPlayer, balance, fare) -> {
                    calls.add("credit");
                    require(actualStation == station && actualPlayer == player, "Fare callback lost live station/player context");
                    require(balance.get() == 1000 && scores(player).equals("1000:4"), "Fare callback ran after debit or cleared entry");
                    require(fare == (concession ? 4 : 7), "Fare callback did not receive the rounded concession fare");
                    require(events.isEmpty(), "Fare callback must run before exit feedback");
                    balance.set(balance.get() + 2);
                    require(scores(player).equals("1002:4"), "Fare callback score did not update the actual scoreboard");
                });
                require(pass(player, false, true, false) == (concession ? TicketSystem.EnumTicketBarrierOpen.OPEN_CONCESSIONARY : TicketSystem.EnumTicketBarrierOpen.OPEN), "Adjusted ticket returned wrong gate state");
                int fare = concession ? 4 : 7, balance = 1002 - fare;
                require(calls.equals(List.of("credit")) && scores(player).equals(balance + ":0"), "Fare adjustment was not charged exactly once");
                require(events.size() == 2 && events.getFirst().contains(", " + fare + ", " + balance + "]:true") && events.getLast().startsWith("sound:mtr:exit"), "Exit feedback ignored adjusted balance/fare");
            }

            TicketSystem.registerFareAdjustment(first, (s, p, b, f) -> calls.add("obsolete"));
            TicketSystem.registerFareAdjustment(second, (s, p, b, f) -> calls.add("second"));
            TicketSystem.registerFareAdjustment(first, (s, p, b, f) -> calls.add("replacement"));
            ticketSetup(player, 1000, 4, 8, false); calls.clear(); pass(player, false, true, false);
            require(calls.equals(List.of("replacement", "second")), "Stable ID replacement duplicated or reordered a callback");
            require(TicketSystem.unregisterFareAdjustment(second), "Existing adjustment was not removed");
            require(!TicketSystem.unregisterFareAdjustment(second), "Removing absent adjustment should return false");
            ticketSetup(player, 1000, 4, 8, false); calls.clear(); pass(player, false, true, false);
            require(calls.equals(List.of("replacement")), "Removed adjustment was still invoked");
            for (String invalid : List.of("", "unqualified", "Upper:case", "fixture:", ":empty", "fixture:two:colons")) {
                require(attempt(() -> { TicketSystem.registerFareAdjustment(invalid, (s, p, b, f) -> { throw new AssertionError("Invalid ID was registered"); }); return null; }).equals("error:IllegalArgumentException"), "Malformed adjustment ID accepted: " + invalid);
            }

            // Both denied and penalized unrecorded trips skip add-on fare credits.
            int[][] trips = {{0, 1, 0, 1}, {4, 1, 0, 1}, {4, 1, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 1, 1}};
            for (int[] trip : trips) {
                ticketSetup(player, 1000, trip[0], 8, false); calls.clear();
                pass(player, trip[1] != 0, trip[2] != 0, trip[3] != 0);
                require(calls.isEmpty(), "Entrance or fare evasion invoked a recorded-journey adjustment: " + Arrays.toString(trip));
            }
            ticketSetup(player, 1000, 4, 8, false); calls.clear();
            pass(player, true, true, false);
            require(calls.equals(List.of("replacement")), "Automatic gate exit bypassed fare adjustment");

            TicketSystem.registerFareAdjustment(first, (s, p, b, f) -> { calls.add("failure"); throw FAILURE; });
            TicketSystem.registerFareAdjustment(second, (s, p, b, f) -> calls.add("after-failure"));
            ticketSetup(player, 1000, 4, 8, false); calls.clear(); events.clear();
            try { pass(player, false, true, false); throw new AssertionError("Fare callback exception was swallowed"); }
            catch (RuntimeException error) { require(error == FAILURE, "Fare callback exception identity changed"); }
            require(scores(player).equals("1000:4") && calls.equals(List.of("failure")) && events.isEmpty(), "Failed callback cleared entry, charged fare, continued callbacks or emitted feedback");

            // The extension owns its side effects: MTR guarantees no debit/entry
            // clearing on failure, not rollback of an add-on's previous credit.
            TicketSystem.registerFareAdjustment(first, (s, p, b, f) -> { b.set(b.get() + 2); throw FAILURE; });
            try { pass(player, false, true, false); throw new AssertionError("Crediting callback exception was swallowed"); }
            catch (RuntimeException error) { require(error == FAILURE, "Crediting callback exception identity changed"); }
            require(scores(player).equals("1002:4") && events.isEmpty(), "Failed adjustment unexpectedly rolled back add-on credit or charged the journey");

            boolean[] mutate = {true};
            TicketSystem.registerFareAdjustment(first, (s, p, b, f) -> {
                calls.add("first");
                if (mutate[0]) {
                    mutate[0] = false;
                    require(TicketSystem.unregisterFareAdjustment(second), "Snapshot fixture could not remove second callback");
                    TicketSystem.registerFareAdjustment(third, (s2, p2, b2, f2) -> calls.add("third"));
                }
            });
            TicketSystem.registerFareAdjustment(second, (s, p, b, f) -> calls.add("second"));
            ticketSetup(player, 1000, 4, 8, false); calls.clear(); pass(player, false, true, false);
            require(calls.equals(List.of("first", "second")), "Mutation changed the in-flight fare callback snapshot");
            ticketSetup(player, 1000, 4, 8, false); calls.clear(); pass(player, false, true, false);
            require(calls.equals(List.of("first", "third")), "Next payment did not observe the updated fare callback snapshot");
        } finally {
            TicketSystem.unregisterFareAdjustment(first); TicketSystem.unregisterFareAdjustment(second); TicketSystem.unregisterFareAdjustment(third);
        }
        ticketSetup(player, 1000, 4, 8, false); calls.clear(); pass(player, false, true, false);
        require(calls.isEmpty() && scores(player).equals("993:0"), "Removing all adjustments failed to restore the ordinary fare");
        System.out.println("Fare extension integration assertions: " + (assertions - before));
    }

    private static void vehicleCases() throws Exception {
        // Four strict/inclusive geometric faces, disabled mounting and rotation.
        double[][] coordinates = {{0,0,0},{1.999,0,0},{2,0,0},{0,2.499,0},{0,2.5,0},{0,0,4},{0,0,4.001},{-1,0,-4},{0,10.001,0}};
        for (int i = 0; i < coordinates.length; i++) {
            fixture(); Player p = player(new Vec3(coordinates[i][0], coordinates[i][1], coordinates[i][2])); online.add(p); events.clear(); Set<UUID> riders = new LinkedHashSet<>();
            mount(riders, true, true, 8, 3, 0, 0, 2, candidate -> { events.add("canRide:" + id(candidate)); return true; }, candidate -> events.add("callback:" + id(candidate)));
            record("mount-face:" + i, riders + ":" + describe(p) + ":" + events);
        }
        Random random = new Random(2622601);
        for (int i = 0; i < 40; i++) {
            fixture(); double length = i % 7 == 0 ? 0 : 8, width = i % 9 == 0 ? 0 : 3; float yaw = (float) (random.nextDouble() * 3), pitch = (float) (random.nextDouble() * 0.8);
            Player p = player(new Vec3(random.nextDouble() * 4 - 2, random.nextDouble() * 2, random.nextDouble() * 8 - 4)); online.add(p); Set<UUID> riders = new LinkedHashSet<>(); events.clear();
            mount(riders, true, true, length, width, yaw, pitch, i - 20, candidate -> true, candidate -> events.add("callback:" + id(candidate)));
            record("mount-rotation:" + i, riders + ":" + describe(p) + ":" + events);
        }
        for (int mode = 0; mode < 7; mode++) {
            fixture(); Player p = player(Vec3.ZERO), other = player(new Vec3(1, 0, 0)); online.addAll(List.of(p, other)); Set<UUID> riders = new LinkedHashSet<>();
            if (mode == 0) state.get(p).spectator = true;
            if (mode == 1) module.onPlayerJoin((ServerPlayer) p);
            if (mode == 2) map(module, "playerShiftCoolDowns").put(p, 30);
            if (mode == 3) riders.add(uuid(p));
            if (mode == 4) selectiveEntities = true;
            events.clear(); final int selected = mode;
            record("mount-filter:" + mode, attempt(() -> { mount(riders, true, true, 8, 3, 0, 0, 0, candidate -> { events.add("canRide:" + id(candidate)); return selected == 5 ? false : selected == 6 ? null : true; }, candidate -> events.add("callback:" + id(candidate))); return riders; }) + ":" + events);
        }
        for (int mode = 0; mode < 8; mode++) {
            fixture(); Player p = player(new Vec3(mode == 0 ? 0 : 3, mode == 2 ? 11 : 0, mode == 3 ? 5 : 0)); online.add(p); Set<UUID> riders = new LinkedHashSet<>(List.of(uuid(p), new UUID(0, 9999)));
            if (mode == 4) state.get(p).spectator = true; if (mode == 5) map(module, "playerShiftCoolDowns").put(p, 30);
            final int selected = mode; events.clear();
            record("dismount:" + mode, attempt(() -> { mount(riders, selected != 6, false, 8, 3, 0, 0, 0, null, candidate -> { events.add("callback:" + id(candidate)); if (selected == 7) throw FAILURE; }); return riders; }) + ":" + riders + ":" + describe(p) + ":" + events);
        }
        fixture(); Player p = player(Vec3.ZERO); online.add(p); Set<UUID> riders = new LinkedHashSet<>(); failAt = "packet"; events.clear();
        record("mount-packet-failure", attempt(() -> { mount(riders, false, true, 8, 3, 0, 0, 0, candidate -> true, candidate -> events.add("callback")); return null; }) + ":" + riders + ":" + describe(p) + ":" + events); failAt = null;
        fixture(); events.clear(); record("mount-null-set", attempt(() -> { mount(null, false, false, 8, 3, 0, 0, 0, null, null); return null; }));
        data = null; mount(null, false, false, 8, 3, 0, 0, 0, null, null); require(events.isEmpty(), "Missing railway did not return before nullable vehicle arguments");
        for (String edge : List.of("null-uuid", "null-filter", "null-callback", "teleport-failure")) {
            fixture(); Player candidate = player(Vec3.ZERO); online.add(candidate); Set<UUID> selected = new LinkedHashSet<>();
            if (edge.equals("null-uuid")) state.get(candidate).uuid = null;
            if (edge.equals("null-callback")) selected.add(uuid(candidate));
            if (edge.equals("teleport-failure")) failAt = "teleport";
            events.clear();
            record("mount-edge:" + edge, attempt(() -> { mount(selected, true, !edge.equals("null-callback"), 8, 3, 0, 0, 0, edge.equals("null-filter") ? null : value -> true, edge.equals("null-callback") ? null : value -> events.add("callback:" + id(value))); return null; }) + ":" + selected + ":" + describe(candidate) + ":" + map(module, "playerRidingCoolDown").get(candidate) + ":" + events);
            failAt = null;
        }
        fixture(); Set<UUID> nullRider = new LinkedHashSet<>(); nullRider.add(null); online.add(player(Vec3.ZERO));
        mount(nullRider, true, false, 8, 3, 0, 0, 0, null, null); require(nullRider.contains(null), "Missing/null UUID rider was unexpectedly removed");
    }

    private static void cooldownCases() throws Exception {
        fixture(); Player p = player(new Vec3(1, 2, 3)); online.add(p); state.get(p).shift = true; module.onPlayerJoin((ServerPlayer) p); module.updatePlayerRiding(p, 77);
        for (int tick = 1; tick <= 34; tick++) {
            events.clear(); module.tick();
            if (Set.of(1,2,3,4,29,30,31,34).contains(tick)) record("cooldown-tick:" + tick, moduleState(p) + ":" + events);
        }
        state.get(p).shift = false; events.clear(); module.tick(); record("cooldown-unshift", moduleState(p) + ":" + events);
        events.clear(); module.moveSeat(p, -2, 5, 8); record("cooldown-move", events.toString());
        EntitySeat seat = (EntitySeat) map(module, "playerSeats").get(p); removedSeats.add(seat); events.clear(); module.tick(); record("cooldown-removed-seat", moduleState(p) + ":" + events);
        module.updatePlayerRiding(p, 77); module.onPlayerDisconnect(p); online.clear(); events.clear(); record("cooldown-disconnect", moduleState(p));
        for (int tick = 0; tick < 3; tick++) module.tick(); record("cooldown-disconnect-expiry", moduleState(p) + ":" + events);
        module.updatePlayerSeatCoolDown(null); module.onPlayerJoin(null); require(!module.canRide(null) && !module.shouldDismount(null) && module.getRidingRoute(null) == null, "Nullable map keys changed"); module.onPlayerDisconnect(null);
        require(!module.canRide(null), "Disconnect unexpectedly clears riding cooldown"); events.clear();
        record("cooldown-null-expiry", attempt(() -> { for (int tick = 0; tick < 3; tick++) module.tick(); return null; }) + ":" + map(module, "playerRidingCoolDown"));
        fixture(); p = player(Vec3.ZERO); online.add(p); state.get(p).shift = true; map(module, "playerShiftCoolDowns").put(p, Integer.MAX_VALUE); module.tick(); record("cooldown-shift-overflow", moduleState(p));
        map(module, "playerShiftCoolDowns").put(p, 31); require(!module.shouldDismount(p), "Dismount is equality, not >= threshold");
        map(module, "playerShiftCoolDowns").put(p, null); record("cooldown-null-map-value", attempt(() -> { module.tick(); return null; }));
        fixture(); Player first = player(Vec3.ZERO), second = player(Vec3.ZERO); online = new FirstOnlyList<>(List.of(first, second)); events.clear(); module.tick(); require(((FirstOnlyList<?>) online).calls == 1, "World player forEach override bypassed");
        record("cooldown-virtual-foreach", moduleState(first) + ":" + moduleState(second) + ":" + events);
        RailwayDataCoolDownModule nullable = new RailwayDataCoolDownModule(null, null, null);
        require(nullable.canRide(null) && nullable.getRidingRoute(null) == null && !nullable.shouldDismount(null), "Constructor added eager null checks");
        nullable.moveSeat(null, 1, 2, 3); nullable.onPlayerDisconnect(null);
        record("cooldown-null-world-tick", attempt(() -> { nullable.tick(); return null; }));
        for (String failure : List.of("initializeSeat", "teleport", "stopRiding")) {
            fixture(); Player candidate = player(Vec3.ZERO); online.add(candidate);
            if (!failure.equals("initializeSeat")) { module.updatePlayerRiding(candidate, 77); map(module, "playerRidingCoolDown").put(candidate, 0); }
            failAt = failure; events.clear();
            record("cooldown-failure:" + failure, attempt(() -> { module.tick(); return null; }) + ":" + moduleState(candidate) + ":" + events); failAt = null;
        }
        fixture(); Player candidate = player(Vec3.ZERO); DataCache previous = data.dataCache; set(data, "dataCache", null);
        require(module.getRidingRoute(candidate) == null, "Absent route eagerly dereferenced null cache"); module.updatePlayerRiding(candidate, 77);
        record("cooldown-null-cache-recorded-route", attempt(() -> module.getRidingRoute(candidate))); set(data, "dataCache", previous);
        fixture(); online.add(null); events.clear();
        record("cooldown-null-online-player", attempt(() -> { module.tick(); return null; }) + ":" + events);
        EntitySeat nullablePlayerSeat = newSeat(world, 1, 2, 3); map(module, "playerSeats").put(null, nullablePlayerSeat); map(module, "playerSeatCoolDowns").put(null, 3); events.clear();
        record("cooldown-null-online-existing-seat", attempt(() -> { module.tick(); return null; }) + ":" + map(module, "playerSeatCoolDowns").get(null) + ":" + events);
    }
    private static void mount(Set<UUID> riders, boolean door, boolean canMount, double length, double width, float yaw, float pitch, int offset, Function<Player, Boolean> canRide, Consumer<Player> callback) { VehicleRidingServer.mountRider(world, riders, 0x123456789ABCDEFL, 77, 0, 0, 0, length, width, yaw, pitch, door, canMount, offset, PACKET, canRide, callback); }
    private static String moduleState(Player p) { List<String> values = new ArrayList<>(); for (String name : List.of("playerRidingCoolDown", "playerRidingRoute", "playerSeatCoolDowns", "playerShiftCoolDowns")) values.add(name + "=" + map(module, name).get(p)); EntitySeat seat = (EntitySeat) map(module, "playerSeats").get(p); return values + ":seat=" + (seat == null ? "null" : seats.get(seat)) + ":can=" + module.canRide(p) + ":dismount=" + module.shouldDismount(p) + ":route=" + (module.getRidingRoute(p) == null ? "null" : module.getRidingRoute(p).id) + ":" + describe(p); }
    private static String describe(Player p) { State s = state.get(p); return id(p) + ":" + p.fallDistance + ":" + p.noPhysics + ":" + s.abilities.mayfly + ":" + s.abilities.flying; }
    private static Player player(Vec3 pos) throws Exception { ServerPlayer player = (ServerPlayer) unsafe.allocateInstance(ServerPlayer.class); player.setId(++nextPlayer); set(player, "gameMode", unsafe.allocateInstance(ServerPlayerGameMode.class)); player.fallDistance = 19; State s = new State(new UUID(0, nextPlayer), pos); state.put(player, s); return player; }
    private static SoundEvent sound(String name) { return SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("mtr", name)); }
    private static String id(Player p) { return p == null ? "null" : Integer.toString(p.getId()); }
    private static void record(String key, Object value) { records.add(key + "\t" + value); }
    private static void require(boolean value, String message) { assertions++; if (!value) throw new AssertionError(message); }
    private interface Attempt { Object run() throws Exception; }
    private static String attempt(Attempt action) { try { Object value = action.run(); return "ok:" + value; } catch (Throwable error) { if (error instanceof AssertionError) throw (AssertionError) error; assertions++; return "error:" + error.getClass().getSimpleName() + (error == FAILURE ? ":same" : ""); } }
    private static void boundary(String point) { if (point.equals(failAt)) throw FAILURE; }
    @SuppressWarnings("unchecked") private static Map<Player, Object> map(Object object, String name) { try { return (Map<Player, Object>) field(object.getClass(), name).get(object); } catch (ReflectiveOperationException e) { throw new AssertionError(e); } }
    private static Field field(Class<?> type, String name) throws NoSuchFieldException { while (type != null) { try { Field field = type.getDeclaredField(name); field.setAccessible(true); return field; } catch (NoSuchFieldException e) { type = type.getSuperclass(); } } throw new NoSuchFieldException(name); }
    private static void set(Object object, String name, Object value) throws Exception { field(object.getClass(), name).set(object, value); }

    public static RailwayData railway(Level level) { return data; }
    public static RailwayDataCoolDownModule cooldown(RailwayData railway) { Objects.requireNonNull(railway); return module; }
    public static Scoreboard scoreboard(Level level) { Objects.requireNonNull(level); return board; }
    public static GameProfile profile(Player player) { return new GameProfile(uuid(player), "fixture-" + id(player)); }
    public static boolean creative(Player player) { Objects.requireNonNull(player); boundary("creative"); return state.get(player).creative; }
    public static boolean spectator(Player player) { return state.get(Objects.requireNonNull(player)).spectator; }
    public static boolean shift(Player player) { return state.get(Objects.requireNonNull(player)).shift; }
    public static UUID uuid(Player player) { return state.get(Objects.requireNonNull(player)).uuid; }
    public static Vec3 position(Player player) { return state.get(Objects.requireNonNull(player)).pos; }
    public static double x(Player player) { return position(player).x; } public static double y(Player player) { return position(player).y; } public static double z(Player player) { return position(player).z; }
    public static void message(Player player, Component message, boolean actionbar) { Objects.requireNonNull(player); TranslatableContents text = (TranslatableContents) message.getContents(); events.add("message:" + text.getKey() + ":" + Arrays.deepToString(text.getArgs()) + ":" + actionbar); boundary("message"); }
    public static void playSound(Level level, Entity source, BlockPos pos, SoundEvent sound, SoundSource category, float volume, float pitch) { Objects.requireNonNull(level); events.add("sound:" + (sound == null ? "null" : sound.location()) + ":" + category + ":" + volume + ":" + pitch); boundary("sound"); }
    public static List<Player> entities(Level level, Class<Player> type, AABB bounds, Predicate<? super Player> predicate) { Objects.requireNonNull(level); require(type == Player.class, "Unexpected query type"); events.add("query:" + bounds); List<Player> result = new ArrayList<>(); for (Player p : online) if (bounds.contains(position(p)) && predicate.test(p)) result.add(p); return selectiveEntities ? new FirstOnlyList<>(result) : result; }
    public static Player byUuid(Level level, UUID uuid) { Objects.requireNonNull(level); for (Player p : online) if (uuid(p).equals(uuid)) return p; return null; }
    public static List<? extends Player> players(Level level) { Objects.requireNonNull(level); return online; }
    public static void packet(Level level, Identifier id, FriendlyByteBuf bytes) { Objects.requireNonNull(level); events.add("packet:" + id + ":" + ByteBufUtil.hexDump(bytes)); bytes.release(); boundary("packet"); }
    public static Abilities abilities(Player player) { return state.get(Objects.requireNonNull(player)).abilities; }
    public static GameType gameMode(ServerPlayerGameMode mode) { Objects.requireNonNull(mode); return GameType.SURVIVAL; }
    public static void gravity(Player player, boolean enabled) { Objects.requireNonNull(player); events.add("gravity:" + id(player) + ":" + enabled); }
    public static void teleport(Player player, boolean enabled) { Objects.requireNonNull(player); events.add("teleport:" + id(player) + ":" + enabled); boundary("teleport"); }
    public static boolean startRiding(Player player, Entity seat) { Objects.requireNonNull(player); events.add("startRiding:" + id(player) + ":" + seats.get(seat)); return false; }
    public static void stopRiding(Player player) { Objects.requireNonNull(player); events.add("stopRiding:" + id(player)); boundary("stopRiding"); }
    public static EntitySeat newSeat(Level level, double x, double y, double z) { Objects.requireNonNull(level); try { EntitySeat seat = (EntitySeat) unsafe.allocateInstance(EntitySeat.class); seats.put(seat, ++nextSeat); events.add("newSeat:" + nextSeat + ":" + x + ":" + y + ":" + z); return seat; } catch (InstantiationException e) { throw new AssertionError(e); } }
    public static boolean addEntity(Level level, Entity seat) { Objects.requireNonNull(level); events.add("addSeat:" + seats.get(seat)); return false; }
    public static void initializeSeat(EntitySeat seat, Player player) { Objects.requireNonNull(seat); events.add("initializeSeat:" + seats.get(seat) + ":" + id(player)); boundary("initializeSeat"); }
    public static void updateSeat(EntitySeat seat, Player player) { Objects.requireNonNull(seat); events.add("updateSeat:" + seats.get(seat) + ":" + id(player)); }
    public static void seatPos(EntitySeat seat, double x, double y, double z) { Objects.requireNonNull(seat); events.add("seatPos:" + seats.get(seat) + ":" + x + ":" + y + ":" + z); }
    public static boolean removed(Entity seat) { return seat == null || removedSeats.contains(seat); }
    private static final class State { UUID uuid; final Abilities abilities = new Abilities(); Vec3 pos; boolean creative, spectator, shift; State(UUID uuid, Vec3 pos) { this.uuid = uuid; this.pos = pos; } }
    private static final class FirstOnlyList<E> extends ArrayList<E> { int calls; FirstOnlyList(Collection<E> values) { super(values); } @Override public void forEach(Consumer<? super E> consumer) { calls++; if (!isEmpty()) consumer.accept(getFirst()); } }
}
