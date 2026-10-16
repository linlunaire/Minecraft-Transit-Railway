package mtr.data;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import mtr.client.ClientData;
import mtr.mappings.BlockEntityMapper;
import mtr.packet.PacketTrainDataGuiServer;
import mtr.render.RenderPIDS;
import mtr.screen.TicketMachineScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/** Actual packet receiver, native inventory/scoreboard, PIDS collection and route-finder regressions. */
public final class GameplayCompatibilityCheck {

	private static final String SCHEDULE_FIXTURE = "0:3,0:3,3:2,3:2,3:2,3:3,0:3,0:3,1:3,3:3,3:3,3:2,1:1,3:4,0:2,3:3,1:3,0:2,2:1,2:3,0:3,3:1,2:3,3:4,3:4,1:4,3:4,2:4,3:4,3:4,0:2,2:4";
	private static final List<String> failures = new ArrayList<>();
	private static int assertions;

	public static void main(String[] args) throws Exception {
		final var output = System.out;
		final Path expected = Path.of(args[0]).toRealPath();
		for (Class<?> type : List.of(PacketTrainDataGuiServer.class, TicketSystem.class, TicketMachineScreen.class,
				ScheduleEntry.class, RailwayDataRouteFinderModule.class)) {
			require(Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(expected),
					"Wrong production source for " + type.getName());
		}
		output.println("PRODUCTION_SOURCE=" + expected);
		net.minecraft.SharedConstants.tryDetectVersion();
		net.minecraft.server.Bootstrap.bootStrap();
		for (String check : List.of("ticket-topup", "schedule-sort", "route-finder")) {
			if (args.length > 1 && !args[1].equals(check)) continue;
			try {
				switch (check) {
					case "ticket-topup" -> checkTopUp();
					case "schedule-sort" -> checkSchedules();
					case "route-finder" -> checkRouteFinder();
				}
				output.println("PASS: " + check);
			} catch (AssertionError | Exception error) {
				failures.add(check + ": " + error);
				output.println("FAIL: " + failures.getLast());
			}
		}
		if (!failures.isEmpty()) throw new AssertionError(String.join("; ", failures));
		output.println("PASS: " + assertions + " gameplay assertions; real production methods, no game or live server");
	}

	private static void checkTopUp() throws Exception {
		final TestServer server = allocate(TestServer.class);
		server.pending = new ArrayList<>();
		final TestLevel level = allocate(TestLevel.class);
		level.scoreboard = new ServerScoreboard(server);
		final ServerPlayer player = allocate(ServerPlayer.class);
		write(Entity.class, player, "level", level);
		write(Entity.class, player, "blockPosition", BlockPos.ZERO);
		write(Player.class, player, "gameProfile", new GameProfile(UUID.fromString("ef2b22ec-9a19-4a50-b864-f43f4b78791d"), "LocalRegression"));
		final Inventory inventory = new Inventory(player);
		write(Player.class, player, "inventory", inventory);
		final Abilities abilities = new Abilities();
		write(Player.class, player, "abilities", abilities);
		TicketSystem.addObjectivesIfMissing(level);
		final var balance = TicketSystem.getPlayerScore(level, player, TicketSystem.BALANCE_OBJECTIVE);

		final int[] prices = {10, 22, 48, 104, 224, 480, 1024, 2176, 4608, 9728};
		final var guiPrice = TicketMachineScreen.class.getDeclaredMethod("getAddAmount", int.class);
		guiPrice.setAccessible(true);
		require((int) field(TicketMachineScreen.class, "BUTTON_COUNT").get(null) == prices.length, "Ticket-machine option count changed");
		final List<TopUpCase> cases = new ArrayList<>();
		for (int index = 0; index < prices.length; index++) {
			require((int) guiPrice.invoke(null, index) == prices[index], "Client price changed at option " + index);
			cases.add(new TopUpCase("valid-" + index, 0, 1 << index, prices[index], 1 << index, 1, prices[index], 0, false));
		}
		cases.addAll(List.of(
				new TopUpCase("empty-inventory", 0, 0, 10, 1, 1, 0, 0, false),
				new TopUpCase("partial-inventory", 0, 1, 22, 2, 1, 0, 1, false),
				new TopUpCase("invalid-price", 0, 1, 100, 1, 1, 0, 1, false),
				new TopUpCase("zero-cost", 0, 0, 10, 0, 1, 0, 0, false),
				new TopUpCase("negative-cost", 0, 1, 10, -1, 1, 0, 1, false),
				new TopUpCase("non-option-cost", 0, 3, 30, 3, 1, 0, 3, false),
				new TopUpCase("out-of-range-cost", 0, 1, 20480, 1024, 1, 0, 1, false),
				new TopUpCase("zero-amount", 50, 1, 0, 1, 1, 50, 1, false),
				new TopUpCase("negative-amount", 50, 1, -10, 1, 1, 50, 1, false),
				new TopUpCase("minimum-amount", 50, 1, Integer.MIN_VALUE, 1, 1, 50, 1, false),
				new TopUpCase("maximum-amount", 50, 1, Integer.MAX_VALUE, 1, 1, 50, 1, false),
				new TopUpCase("overflow", Integer.MAX_VALUE - 9, 1, 10, 1, 1, Integer.MAX_VALUE - 9, 1, false),
				new TopUpCase("exact-maximum", Integer.MAX_VALUE - 10, 1, 10, 1, 1, Integer.MAX_VALUE, 0, false),
				new TopUpCase("negative-balance", -500, 1, 10, 1, 1, -490, 0, false),
				new TopUpCase("replayed-request", 0, 1, 10, 1, 2, 10, 0, false),
				new TopUpCase("creative-payment", 0, 1, 10, 1, 1, 10, 0, true),
				new TopUpCase("creative-empty", 0, 0, 10, 1, 1, 0, 0, true)));
		final List<String> errors = new ArrayList<>();
		for (TopUpCase sample : cases) {
			balance.set(sample.initialBalance);
			inventory.clearContent();
			inventory.setItem(35, new ItemStack(Items.DIAMOND, 2));
			for (int left = sample.available, slot = 0; left > 0; slot++) {
				final int count = Math.min(64, left);
				inventory.setItem(slot, new ItemStack(Items.EMERALD, count));
				left -= count;
			}
			abilities.instabuild = sample.creative;
			for (int request = 0; request < sample.requests; request++) receiveTopUp(server, player, sample.amount, sample.cost);
			drain(server);
			assertions++;
			if (balance.get() != sample.expectedBalance || inventory.countItem(Items.EMERALD) != sample.expectedEmeralds
					|| inventory.countItem(Items.DIAMOND) != 2) {
				errors.add(sample.name + " expectedBalance=" + sample.expectedBalance + " actualBalance=" + balance.get()
						+ " expectedEmeralds=" + sample.expectedEmeralds + " actualEmeralds=" + inventory.countItem(Items.EMERALD));
			}
		}
		// The inventory must be rechecked in the queued server work, not when the packet arrives.
		balance.set(0);
		inventory.clearContent();
		inventory.setItem(0, new ItemStack(Items.EMERALD));
		receiveTopUp(server, player, 10, 1);
		inventory.clearContent();
		drain(server);
		if (balance.get() != 0) errors.add("Inventory changed before server execution but balance became " + balance.get());
		require(errors.isEmpty(), String.join("; ", errors));
	}

	private static void receiveTopUp(TestServer server, ServerPlayer player, int amount, int emeralds) {
		final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
		try {
			packet.writeInt(amount).writeInt(emeralds);
			PacketTrainDataGuiServer.receiveAddBalanceC2S(server, player, packet);
		} finally {
			packet.release();
		}
	}

	private static void drain(TestServer server) {
		for (Runnable work : List.copyOf(server.pending)) work.run();
		server.pending.clear();
	}

	private static void checkSchedules() {
		final List<ScheduleEntry> entries = new ArrayList<>();
		for (String entry : SCHEDULE_FIXTURE.split(",")) {
			final String[] pair = entry.split(":");
			entries.add(new ScheduleEntry(1_000_000L + Long.parseLong(pair[0]) * 1000L, 8, Long.parseLong(pair[1]), 0));
		}
		// Pin the unspecified HashSet order by selecting native identity hashes. No comparator,
		// collection implementation or ScheduleEntry method is replaced by this fixture.
		final List<ScheduleEntry> ordered = new ArrayList<>();
		for (int index = 0; index < entries.size(); index++) {
			final ScheduleEntry value = entries.get(index);
			for (int attempt = 0; ; attempt++) {
				if (attempt >= 1_000_000) throw new AssertionError("Could not pin the schedule encounter order");
				final ScheduleEntry candidate = new ScheduleEntry(value.arrivalMillis, value.trainCars, value.routeId, value.currentStationIndex);
				final int hash = candidate.hashCode();
				if (((hash ^ (hash >>> 16)) & 63) == index) {
					ordered.add(candidate);
					break;
				}
			}
		}
		require(new ArrayList<>(new HashSet<>(ordered)).equals(ordered), "Schedule encounter order changed");
		final Station station = new Station(101);
		station.corner1 = new Tuple<>(-10, -10);
		station.corner2 = new Tuple<>(10, 10);
		ClientData.STATIONS.add(station);
		final Platform platform = new Platform(102, TransportMode.TRAIN, new BlockPos(-4, 64, 0), new BlockPos(4, 64, 0));
		ClientData.PLATFORMS.add(platform);
		ClientData.PLATFORMS.add(new Platform(103, TransportMode.TRAIN, new BlockPos(-4, 64, 5), new BlockPos(4, 64, 5)));
		for (int id = 1; id <= 4; id++) {
			final Route route = new Route(id, TransportMode.TRAIN);
			route.platformIds.add(new Route.RoutePlatform(102));
			route.platformIds.add(new Route.RoutePlatform(103));
			ClientData.ROUTES.add(route);
		}
		ClientData.DATA_CACHE.sync();
		ClientData.SCHEDULES_FOR_PLATFORM.put(platform.id, new HashSet<>(ordered));
		final RenderPIDS<BlockEntityMapper> renderer = new RenderPIDS<>(null, 2, 2, 0, 0, 0, 8, 16, false, false,
				PIDSType.ARRIVAL_PROJECTOR, 0xFFFFFF, 0xFFFF00);
		final List<ScheduleEntry> actual = renderer.getSchedules(null, new BlockPos(0, 64, 0), new HashMap<>());
		final List<ScheduleEntry> expected = new ArrayList<>(ordered);
		final Comparator<ScheduleEntry> comparator = Comparator.comparingLong((ScheduleEntry entry) -> entry.arrivalMillis).thenComparingLong(entry -> entry.routeId);
		expected.sort(comparator);
		require(actual.equals(expected), "Actual PIDS collection/sort lost or reordered equal-key arrivals");
		for (long arrival : new long[]{Long.MIN_VALUE, 0, Long.MAX_VALUE}) {
			for (long route : new long[]{Long.MIN_VALUE, 0, Long.MAX_VALUE}) entries.add(new ScheduleEntry(arrival, 8, route, 0));
		}
		for (ScheduleEntry first : entries) {
			require(first.compareTo(first) == 0, "Schedule comparator is not reflexive");
			for (ScheduleEntry second : entries) require(Integer.signum(first.compareTo(second)) == Integer.signum(comparator.compare(first, second)), "Schedule comparator changed arrival/route ordering");
		}
		Collections.sort(entries);
	}

	private static void checkRouteFinder() throws Exception {
		final List<String> errors = new ArrayList<>();
		for (int platforms = 0; platforms <= 4; platforms++) {
			final RailwayDataRouteFinderModule module = routeModule(platforms);
			final List<List<RailwayDataRouteFinderModule.RouteFinderData>> replies = new ArrayList<>();
			module.tick(); // Empty small worlds must not run the background random selection.
			require(module.findRoute(new BlockPos(100, 64, 0), new BlockPos(110, 64, 0), 2, (data, duration) -> replies.add(data)), "Route request was not accepted");
			runUntilReplies(module, replies, 1);
			if (replies.size() != 1 || replies.getFirst().size() != 1 || replies.getFirst().getFirst().duration != 50
					|| !replies.getFirst().getLast().pos.equals(new BlockPos(110, 64, 0))) {
				errors.add("platformCount=" + platforms + " callbacks=" + replies.size() + " (expected one 50-tick walking result)");
			}
		}
		for (boolean previousRequest : new boolean[]{false, true}) {
			final RailwayDataRouteFinderModule module = routeModule(4);
			final List<List<RailwayDataRouteFinderModule.RouteFinderData>> replies = new ArrayList<>();
			if (previousRequest) {
				module.findRoute(new BlockPos(100, 64, 0), new BlockPos(110, 64, 0), 2, (data, duration) -> replies.add(data));
				runUntilReplies(module, replies, 1);
				require(replies.size() == 1, "Initial route control failed");
				replies.clear();
			}
			module.findRoute(new BlockPos(200, 64, 0), new BlockPos(200, 64, 0), 2, (data, duration) -> replies.add(data));
			runUntilReplies(module, replies, 1);
			if (replies.size() != 1 || !replies.getFirst().isEmpty()) errors.add("same endpoint afterPriorRoute=" + previousRequest + " returned a stale route or no callback");
		}
		final RailwayDataRouteFinderModule module = routeModule(2);
		final List<List<RailwayDataRouteFinderModule.RouteFinderData>> replies = new ArrayList<>();
		for (int request = 0; request < 10; request++) require(module.findRoute(new BlockPos(100, 64, 0), new BlockPos(110 + request, 64, 0), 2, (data, duration) -> replies.add(data)), "Queue rejected an allowed request");
		require(!module.findRoute(BlockPos.ZERO, BlockPos.ZERO, 2, (data, duration) -> { }), "Queue limit changed");
		runUntilReplies(module, replies, 10);
		if (replies.size() != 10) errors.add("small-network queued requests callbacks=" + replies.size() + " (expected 10)");
		else for (int index = 0; index < 10; index++) require(replies.get(index).getLast().pos.equals(new BlockPos(110 + index, 64, 0)), "Queued results crossed request boundaries");
		checkActiveRouteRequests();
		require(errors.isEmpty(), String.join("; ", errors));
	}

	private static void checkActiveRouteRequests() throws Exception {
		for (boolean secondRequest : new boolean[]{false, true}) {
			final DelayedRailwayData data = allocate(DelayedRailwayData.class);
			final RailwayDataRouteFinderModule module = routeModule(4, data);
			final List<String> replies = new ArrayList<>();
			require(module.findRoute(new BlockPos(0, 64, 0), new BlockPos(60, 64, 0), 2, (route, duration) -> {
				require(!route.isEmpty() && route.getLast().pos.equals(new BlockPos(60, 64, 0)), "Active first request returned the wrong endpoint");
				replies.add("first");
			}), "First active request was not accepted");
			for (int tick = 0; tick < 100 && !data.delayed; tick++) module.tick();
			require(data.delayed && replies.isEmpty(), "First request did not cross the tick boundary");
			require(field(RailwayDataRouteFinderModule.class, "currentRouteFinderRequest").get(module) != null
					&& !field(RailwayDataRouteFinderModule.class, "tickStage").get(module).toString().equals("GET_POS"), "First request was not actively computing");
			if (secondRequest) require(module.findRoute(new BlockPos(100, 64, 0), new BlockPos(110, 64, 0), 2, (route, duration) -> {
				require(!route.isEmpty() && route.getLast().pos.equals(new BlockPos(110, 64, 0)), "Active second request returned the wrong endpoint");
				replies.add("second");
			}), "Second active request was not accepted");
			// Continue ticking after callbacks, so duplicate replies also fail the exact-order check.
			for (int tick = 0; tick < 100; tick++) module.tick();
			require(replies.equals(secondRequest ? List.of("first", "second") : List.of("first")),
					"Active request callback loss, duplication or reordering: secondRequest=" + secondRequest + " replies=" + replies);
		}
		final DelayedRailwayData backgroundData = allocate(DelayedRailwayData.class);
		final RailwayDataRouteFinderModule background = routeModule(4, backgroundData);
		for (int tick = 0; tick < 100 && !backgroundData.delayed; tick++) background.tick();
		require(backgroundData.delayed && field(RailwayDataRouteFinderModule.class, "currentRouteFinderRequest").get(background) == null
				&& !field(RailwayDataRouteFinderModule.class, "tickStage").get(background).toString().equals("GET_POS"), "Background control did not cross the tick boundary");
		final List<List<RailwayDataRouteFinderModule.RouteFinderData>> replies = new ArrayList<>();
		require(background.findRoute(new BlockPos(100, 64, 0), new BlockPos(110, 64, 0), 2, (route, duration) -> replies.add(route)), "User request during background work was not accepted");
		require(field(RailwayDataRouteFinderModule.class, "tickStage").get(background).toString().equals("GET_POS"), "User request no longer interrupts background work");
		for (int tick = 0; tick < 100; tick++) background.tick();
		require(replies.size() == 1 && !replies.getFirst().isEmpty() && replies.getFirst().getLast().pos.equals(new BlockPos(110, 64, 0)), "User request after background work did not complete exactly once");
	}

	private static void runUntilReplies(RailwayDataRouteFinderModule module, List<?> replies, int expected) {
		for (int tick = 0; tick < 100 && replies.size() < expected; tick++) module.tick();
	}

	private static RailwayDataRouteFinderModule routeModule(int count) throws Exception {
		return routeModule(count, allocate(RailwayData.class));
	}

	private static RailwayDataRouteFinderModule routeModule(int count, RailwayData data) throws Exception {
		final Set<Platform> platforms = new HashSet<>();
		final Route route = new Route(101, TransportMode.TRAIN);
		final Depot depot = new Depot(201, TransportMode.TRAIN);
		depot.routeIds.add(route.id);
		for (int index = 0; index < count; index++) {
			final Platform platform = new Platform(301L + index, TransportMode.TRAIN, new BlockPos(index * 20 - 2, 64, 0), new BlockPos(index * 20 + 2, 64, 0));
			platforms.add(platform);
			route.platformIds.add(new Route.RoutePlatform(platform.id));
			if (index > 0) depot.platformTimes.computeIfAbsent(300L + index, ignored -> new HashMap<>()).put(platform.id, 10F);
		}
		final DataCache cache = new DataCache(Set.of(), platforms, Set.of(), Set.of(route), Set.of(depot), Set.of());
		cache.sync();
		require(cache.platformConnections.size() == (count < 2 ? 0 : count), "Real DataCache did not construct the route graph");
		write(RailwayData.class, data, "dataCache", cache);
		write(RailwayData.class, data, "stations", Set.of());
		write(RailwayData.class, data, "schedulesForPlatform", new HashMap<>());
		return new RailwayDataRouteFinderModule(data, null, new HashMap<>());
	}

	private static final class DelayedRailwayData extends RailwayData {
		private boolean delayed;
		private DelayedRailwayData() { super(null); }
		@Override public List<ScheduleEntry> getSchedulesAtPlatform(long platformId) {
			final List<ScheduleEntry> result = super.getSchedulesAtPlatform(platformId);
			if (!delayed) {
				delayed = true;
				// Preserve the native lookup result; only force real route work across its 2 ms budget.
				final long until = System.nanoTime() + 10_000_000L;
				while (System.nanoTime() < until) Thread.onSpinWait();
			}
			return result;
		}
	}

	private record TopUpCase(String name, int initialBalance, int available, int amount, int cost, int requests, int expectedBalance, int expectedEmeralds, boolean creative) { }

	private static final class TestServer extends DedicatedServer {
		private List<Runnable> pending;
		private TestServer() { super(null, null, null, null, null, null, null, null); }
		@Override public void execute(Runnable command) { pending.add(command); }
	}

	private static final class TestLevel extends ServerLevel {
		private ServerScoreboard scoreboard;
		private TestLevel() { super(null, null, null, null, null, null, null, false, 0, null, false, null); }
		@Override public ServerScoreboard getScoreboard() { return scoreboard; }
		@Override public void playSound(Player excluded, BlockPos pos, SoundEvent sound, SoundSource source, float volume, float pitch) { }
	}

	private static <T> T allocate(Class<T> type) throws Exception {
		return type.cast(((Unsafe) field(Unsafe.class, "theUnsafe").get(null)).allocateInstance(type));
	}

	private static Field field(Class<?> type, String name) throws Exception {
		final Field field = type.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	private static void write(Class<?> type, Object target, String name, Object value) throws Exception {
		field(type, name).set(target, value);
	}

	private static void require(boolean condition, String message) {
		assertions++;
		if (!condition) throw new AssertionError(message);
	}
}
