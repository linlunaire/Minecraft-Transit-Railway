package mtr.client;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import mtr.KeyMappings;
import mtr.MTRClient;
import mtr.data.*;
import mtr.mappings.Text;
import mtr.packet.PacketTrainDataGuiClient;
import mtr.path.PathData;
import mtr.render.JonModelTrainRenderer;
import mtr.render.RenderTrains;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ClientData {

	public static String DASHBOARD_SEARCH = "";
	public static String ROUTES_PLATFORMS_SEARCH = "";
	public static String ROUTES_PLATFORMS_SELECTED_SEARCH = "";
	public static String TRAINS_SEARCH = "";
	public static String EXIT_PARENTS_SEARCH = "";
	public static String EXIT_DESTINATIONS_SEARCH = "";

	private static boolean pressingAccelerate = false;
	private static boolean pressingBrake = false;
	private static boolean pressingDoors = false;
	private static float shiftHoldingTicks = 0;

	public static final Set<Station> STATIONS = new HashSet<>();
	public static final Set<Platform> PLATFORMS = new HashSet<>();
	public static final Set<Siding> SIDINGS = new HashSet<>();
	public static final Set<Route> ROUTES = new HashSet<>();
	public static final Set<Depot> DEPOTS = new HashSet<>();
	public static final Set<LiftClient> LIFTS = new HashSet<>();
	public static final SignalBlocks SIGNAL_BLOCKS = new SignalBlocks();
	public static final Map<UUID, Boolean> OCCUPIED_RAILS = new HashMap<>();
	public static final Map<BlockPos, Map<BlockPos, Rail>> RAILS = new HashMap<>();
	public static final Set<TrainClient> TRAINS = new HashSet<>();
	public static final List<DataConverter> RAIL_ACTIONS = new ArrayList<>();
	public static final Map<Long, Set<ScheduleEntry>> SCHEDULES_FOR_PLATFORM = new HashMap<>();

	public static final ClientCache DATA_CACHE = new ClientCache(STATIONS, PLATFORMS, SIDINGS, ROUTES, DEPOTS, LIFTS);

	private static final Long2ObjectOpenHashMap<TrainClient> TRAINS_BY_ID = new Long2ObjectOpenHashMap<>();
	private static final Map<UUID, Integer> PLAYER_RIDING_COOL_DOWN = new HashMap<>();
	private static final ExecutorService INITIAL_DATA_EXECUTOR = createDataExecutor("MTR Client Data Loader");
	private static final ExecutorService RAIL_DATA_EXECUTOR = createDataExecutor("MTR Rail Data Loader");
	private static final boolean EXTERNAL_RAIL_RENDERER_PRESENT = isClassPresent("cn.zbx1425.mtrsteamloco.data.RailExtraSupplier");
	private static volatile InitialSyncState initialSyncState = new InitialSyncState(Long.MIN_VALUE, true);

	public static void tick() {
		final Iterator<Map.Entry<UUID, Integer>> playerRidingCoolDownIterator = PLAYER_RIDING_COOL_DOWN.entrySet().iterator();
		while (playerRidingCoolDownIterator.hasNext()) {
			final Map.Entry<UUID, Integer> entry = playerRidingCoolDownIterator.next();
			if (entry.getValue() <= 0) {
				playerRidingCoolDownIterator.remove();
			} else {
				entry.setValue(entry.getValue() - 1);
			}
		}


		final boolean tempPressingAccelerate = KeyMappings.TRAIN_ACCELERATE.isDown();
		final boolean tempPressingBrake = KeyMappings.TRAIN_BRAKE.isDown();
		final boolean tempPressingDoors = KeyMappings.TRAIN_TOGGLE_DOORS.isDown();
		PacketTrainDataGuiClient.sendDriveTrainC2S(
				tempPressingAccelerate && !pressingAccelerate,
				tempPressingBrake && !pressingBrake,
				tempPressingDoors && !pressingDoors
		);
		pressingAccelerate = tempPressingAccelerate;
		pressingBrake = tempPressingBrake;
		pressingDoors = tempPressingDoors;

		final Minecraft minecraftClient = Minecraft.getInstance();
		final Player player = minecraftClient.player;
		if (player != null) {
			if (player.isShiftKeyDown()) {
				shiftHoldingTicks += MTRClient.getLastFrameDuration();
			} else {
				shiftHoldingTicks = 0;
			}
		}
	}

	public static void writeRails(Minecraft client, FriendlyByteBuf packet) {
		submitPacketDecode(RAIL_DATA_EXECUTOR, packet, packetCopy -> decodeRails(client, packetCopy));
	}

	private static void decodeRails(Minecraft client, FriendlyByteBuf packet) {
		final Map<BlockPos, Map<BlockPos, Rail>> railsTemp = new HashMap<>();

		final int railsCount = packet.readInt();
		for (int i = 0; i < railsCount; i++) {
			final BlockPos startPos = packet.readBlockPos();
			final Map<BlockPos, Rail> railMap = new HashMap<>();
			final int railCount = packet.readInt();
			for (int j = 0; j < railCount; j++) {
				railMap.put(packet.readBlockPos(), new Rail(packet));
			}
			railsTemp.put(startPos, railMap);
		}

		if (!EXTERNAL_RAIL_RENDERER_PRESENT) {
			final Map<UUID, RailType> prewarmedRails = new HashMap<>();
			for (final Map.Entry<BlockPos, Map<BlockPos, Rail>> startEntry : railsTemp.entrySet()) {
				for (final Map.Entry<BlockPos, Rail> endEntry : startEntry.getValue().entrySet()) {
					final Rail rail = endEntry.getValue();
					final UUID railProduct = PathData.getRailProduct(startEntry.getKey(), endEntry.getKey());
					final RailType prewarmedRailType = prewarmedRails.get(railProduct);
					if (prewarmedRailType == null) {
						prewarmedRails.put(railProduct, rail.railType);
					}
					if (prewarmedRailType == null || prewarmedRailType != rail.railType) {
						rail.prewarmRender();
					}
				}
			}
		}

		executeAfterInitialSync(client, () -> clearAndAddAll(RAILS, railsTemp));
	}

	public static void updateTrains(Minecraft client, FriendlyByteBuf packet) {
		final Set<TrainClient> trainsToUpdate = new HashSet<>();

		while (packet.isReadable()) {
			trainsToUpdate.add(new TrainClient(packet));
		}

		executeAfterInitialSync(client, () -> trainsToUpdate.forEach(newTrain -> {
			final TrainClient existingTrain = getTrainById(newTrain.id);
			if (existingTrain == null) {
				TRAINS.add(newTrain);
				TRAINS_BY_ID.put(newTrain.id, newTrain);
			} else {
				existingTrain.copyFromTrain(newTrain);
			}
		}));
	}

	public static void deleteTrains(Minecraft client, FriendlyByteBuf packet) {
		final Set<Long> trainIdsToKeep = new HashSet<>();

		final int trainsCount = packet.readInt();
		for (int i = 0; i < trainsCount; i++) {
			trainIdsToKeep.add(packet.readLong());
		}

		executeAfterInitialSync(client, () -> {
			TRAINS.forEach(trainClient -> {
				if (!trainIdsToKeep.contains(trainClient.id)) {
					trainClient.isRemoved = true;
				}
			});
			TRAINS.removeIf(trainClient -> {
				if (trainClient.isRemoved) {
					TRAINS_BY_ID.remove(trainClient.id);
					JonModelTrainRenderer.removeTrainFromCache(trainClient.id);
					return true;
				}
				return false;
			});
		});
	}

	public static void updateLifts(Minecraft client, FriendlyByteBuf packet) {
		final Set<LiftClient> liftsToUpdate = new HashSet<>();

		while (packet.isReadable()) {
			liftsToUpdate.add(new LiftClient(packet));
		}

		executeAfterInitialSync(client, () -> liftsToUpdate.forEach(newLift -> {
			final LiftClient existingLift = DATA_CACHE.liftsClientIdMap.get(newLift.id);
			if (existingLift == null) {
				LIFTS.add(newLift);
				ClientData.DATA_CACHE.syncLiftIds();
			} else {
				existingLift.copyFromLift(newLift);
			}
		}));
	}

	public static void deleteLifts(Minecraft client, FriendlyByteBuf packet) {
		final Set<Long> liftIdsToKeep = new HashSet<>();

		final int liftsCount = packet.readInt();
		for (int i = 0; i < liftsCount; i++) {
			liftIdsToKeep.add(packet.readLong());
		}

		executeAfterInitialSync(client, () -> {
			final Set<LiftClient> liftsToRemove = new HashSet<>();
			LIFTS.forEach(lift -> {
				if (!liftIdsToKeep.contains(lift.id)) {
					liftsToRemove.add(lift);
				}
			});
			liftsToRemove.forEach(LIFTS::remove);
			ClientData.DATA_CACHE.syncLiftIds();
		});
	}

	public static void updateTrainPassengers(Minecraft client, FriendlyByteBuf packet) {
		final long trainId = packet.readLong();
		final float percentageX = packet.readFloat();
		final float percentageZ = packet.readFloat();
		final UUID uuid = packet.readUUID();
		executeAfterInitialSync(client, () -> {
			final TrainClient train = getTrainById(trainId);
			if (train != null) {
				train.startRidingClient(uuid, percentageX, percentageZ);
			}
		});
	}

	public static void updateTrainPassengerPosition(Minecraft client, FriendlyByteBuf packet) {
		final long trainId = packet.readLong();
		final float percentageX = packet.readFloat();
		final float percentageZ = packet.readFloat();
		final UUID uuid = packet.readUUID();
		executeAfterInitialSync(client, () -> {
			final TrainClient train = getTrainById(trainId);
			if (train != null) {
				train.updateRiderPercentages(uuid, percentageX, percentageZ);
			}
		});
	}

	public static void updateLiftPassengers(Minecraft client, FriendlyByteBuf packet) {
		final long liftId = packet.readLong();
		final float percentageX = packet.readFloat();
		final float percentageZ = packet.readFloat();
		final UUID uuid = packet.readUUID();
		executeAfterInitialSync(client, () -> {
			final LiftClient lift = DATA_CACHE.liftsClientIdMap.get(liftId);
			if (lift != null) {
				lift.startRidingClient(uuid, percentageX, percentageZ);
			}
		});
	}

	public static void updateLiftPassengerPosition(Minecraft client, FriendlyByteBuf packet) {
		final long liftId = packet.readLong();
		final float percentageX = packet.readFloat();
		final float percentageZ = packet.readFloat();
		final UUID uuid = packet.readUUID();
		executeAfterInitialSync(client, () -> {
			final LiftClient lift = DATA_CACHE.liftsClientIdMap.get(liftId);
			if (lift != null) {
				lift.updateRiderPercentages(uuid, percentageX, percentageZ);
			}
		});
	}

	public static void updateRailActions(Minecraft client, FriendlyByteBuf packet) {
		final List<DataConverter> railActions = new ArrayList<>();
		final int actionCount = packet.readInt();
		for (int i = 0; i < actionCount; i++) {
			final long id = packet.readLong();
			final String player = packet.readUtf();
			final float length = packet.readFloat();
			final String block = Text.translatable(packet.readUtf()).getString();
			final String name = Text.translatable("gui.mtr." + packet.readUtf(), player, length, block).getString();
			final int color = packet.readInt();
			railActions.add(new DataConverter(id, name, color));
		}
		executeAfterInitialSync(client, () -> {
			RAIL_ACTIONS.clear();
			RAIL_ACTIONS.addAll(railActions);
		});
	}

	public static void updateSchedule(Minecraft client, FriendlyByteBuf packet) {
		final Map<Long, Set<ScheduleEntry>> tempSchedulesForPlatform = new HashMap<>();
		final int platformCount = packet.readInt();
		for (int i = 0; i < platformCount; i++) {
			final long platformId = packet.readLong();
			final int scheduleCount = packet.readInt();
			for (int j = 0; j < scheduleCount; j++) {
				if (!tempSchedulesForPlatform.containsKey(platformId)) {
					tempSchedulesForPlatform.put(platformId, new HashSet<>());
				}
				tempSchedulesForPlatform.get(platformId).add(new ScheduleEntry(packet));
			}
		}

		final Map<Long, Boolean> signalBlockStatus = new HashMap<>();
		final int signalBlockCount = packet.readInt();
		for (int i = 0; i < signalBlockCount; i++) {
			signalBlockStatus.put(packet.readLong(), packet.readBoolean());
		}

		final Map<UUID, Boolean> occupiedRails = new HashMap<>();
		final int occupiedRailsCount = packet.readInt();
		for (int i = 0; i < occupiedRailsCount; i++) {
			occupiedRails.put(packet.readUUID(), packet.readBoolean());
		}

		executeAfterInitialSync(client, () -> {
			clearAndAddAll(SCHEDULES_FOR_PLATFORM, tempSchedulesForPlatform);
			SIGNAL_BLOCKS.writeSignalBlockStatus(signalBlockStatus);
			OCCUPIED_RAILS.clear();
			OCCUPIED_RAILS.putAll(occupiedRails);
		});
	}

	public static synchronized void beginInitialSync(long syncId) {
		if (initialSyncState.id != syncId) {
			initialSyncState = new InitialSyncState(syncId, false);
		}
	}

	public static void receivePacketAsync(Minecraft client, long syncId, byte[] data) {
		beginInitialSync(syncId);
		final InitialSyncState syncState = initialSyncState;
		INITIAL_DATA_EXECUTOR.execute(() -> {
			InitialDataSnapshot snapshot = null;
			try {
				final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
				try {
					snapshot = decodeInitialData(packet);
				} finally {
					packet.release();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			final InitialDataSnapshot decodedSnapshot = snapshot;
			client.execute(() -> finishInitialSync(syncState, decodedSnapshot));
		});
	}

	public static void receivePacket(FriendlyByteBuf packet) {
		publishInitialData(decodeInitialData(packet));
	}

	public static void executeAfterInitialSync(Minecraft client, Runnable action) {
		final InitialSyncState syncState = initialSyncState;
		synchronized (syncState) {
			if (!syncState.complete) {
				syncState.pendingActions.add(action);
				return;
			}
		}
		client.execute(() -> {
			if (initialSyncState == syncState) {
				action.run();
			}
		});
	}

	public static void executeInRailPacketOrder(Minecraft client, Runnable action) {
		RAIL_DATA_EXECUTOR.execute(() -> executeAfterInitialSync(client, action));
	}

	private static void submitPacketDecode(ExecutorService executor, FriendlyByteBuf packet, Consumer<FriendlyByteBuf> decoder) {
		final byte[] data = new byte[packet.readableBytes()];
		packet.readBytes(data);
		executor.execute(() -> {
			final FriendlyByteBuf packetCopy = new FriendlyByteBuf(Unpooled.wrappedBuffer(data));
			try {
				decoder.accept(packetCopy);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				packetCopy.release();
			}
		});
	}

	private static ExecutorService createDataExecutor(String name) {
		return Executors.newSingleThreadExecutor(runnable -> {
			final Thread thread = new Thread(runnable, name);
			thread.setDaemon(true);
			return thread;
		});
	}

	private static boolean isClassPresent(String className) {
		try {
			Class.forName(className, false, ClientData.class.getClassLoader());
			return true;
		} catch (ClassNotFoundException | LinkageError ignored) {
			return false;
		}
	}

	private static InitialDataSnapshot decodeInitialData(FriendlyByteBuf packet) {
		return new InitialDataSnapshot(
				deserializeData(packet, Station::new),
				deserializeData(packet, Platform::new),
				deserializeData(packet, Siding::new),
				deserializeData(packet, Route::new),
				deserializeData(packet, Depot::new),
				deserializeData(packet, LiftClient::new),
				deserializeData(packet, SignalBlocks.SignalBlock::new)
		);
	}

	private static void finishInitialSync(InitialSyncState syncState, InitialDataSnapshot snapshot) {
		if (initialSyncState != syncState) {
			return;
		}
		if (snapshot != null) {
			publishInitialData(snapshot);
		}

		final List<Runnable> pendingActions;
		synchronized (syncState) {
			syncState.complete = true;
			pendingActions = new ArrayList<>(syncState.pendingActions);
			syncState.pendingActions.clear();
		}
		pendingActions.forEach(Runnable::run);
	}

	private static void publishInitialData(InitialDataSnapshot snapshot) {
		clearAndAddAll(STATIONS, snapshot.stations);
		clearAndAddAll(PLATFORMS, snapshot.platforms);
		clearAndAddAll(SIDINGS, snapshot.sidings);
		clearAndAddAll(ROUTES, snapshot.routes);
		clearAndAddAll(DEPOTS, snapshot.depots);
		clearAndAddAll(LIFTS, snapshot.lifts);
		clearAndAddAll(SIGNAL_BLOCKS.signalBlocks, snapshot.signalBlocks);

		TRAINS.clear();
		TRAINS_BY_ID.clear();
		JonModelTrainRenderer.clearTrainCache();
		RenderTrains.clearRenderQueue();
		ClientData.DATA_CACHE.sync();
		ClientData.DATA_CACHE.clearDynamicResources();
		SIGNAL_BLOCKS.writeCache();
	}

	private static class InitialSyncState {

		private final long id;
		private final List<Runnable> pendingActions = new ArrayList<>();
		private boolean complete;

		private InitialSyncState(long id, boolean complete) {
			this.id = id;
			this.complete = complete;
		}
	}

	private static class InitialDataSnapshot {

		private final Set<Station> stations;
		private final Set<Platform> platforms;
		private final Set<Siding> sidings;
		private final Set<Route> routes;
		private final Set<Depot> depots;
		private final Set<LiftClient> lifts;
		private final Set<SignalBlocks.SignalBlock> signalBlocks;

		private InitialDataSnapshot(Set<Station> stations, Set<Platform> platforms, Set<Siding> sidings, Set<Route> routes, Set<Depot> depots, Set<LiftClient> lifts, Set<SignalBlocks.SignalBlock> signalBlocks) {
			this.stations = stations;
			this.platforms = platforms;
			this.sidings = sidings;
			this.routes = routes;
			this.depots = depots;
			this.lifts = lifts;
			this.signalBlocks = signalBlocks;
		}
	}

	public static <T extends NameColorDataBase> Set<T> getFilteredDataSet(TransportMode transportMode, Set<T> dataSet) {
		final Set<T> returnData = new HashSet<>();
		dataSet.forEach(data -> {
			if (data.isTransportMode(transportMode)) {
				returnData.add(data);
			}
		});
		return returnData;
	}

	public static void updatePlayerRidingOffset(UUID uuid) {
		PLAYER_RIDING_COOL_DOWN.put(uuid, 2);
	}

	public static boolean isRiding(UUID uuid) {
		return PLAYER_RIDING_COOL_DOWN.containsKey(uuid);
	}

	public static boolean hasPermission() {
		final LocalPlayer player = Minecraft.getInstance().player;
		if (player == null) {
			return false;
		}
		final ClientPacketListener clientPacketListener = Minecraft.getInstance().getConnection();
		if (clientPacketListener == null) {
			return false;
		}
		final PlayerInfo playerInfo = clientPacketListener.getPlayerInfo(player.getUUID());
		if (playerInfo == null) {
			return false;
		}
		return RailwayData.hasPermission(playerInfo.getGameMode());
	}

	public static float getShiftHoldingTicks() {
		return shiftHoldingTicks;
	}

	private static <T extends SerializedDataBase> Set<T> deserializeData(FriendlyByteBuf packet, Function<FriendlyByteBuf, T> supplier) {
		final Set<T> objects = new HashSet<>();
		final int dataCount = packet.readInt();
		for (int i = 0; i < dataCount; i++) {
			objects.add(supplier.apply(packet));
		}
		return objects;
	}

	private static <U> void clearAndAddAll(Collection<U> target, Collection<U> source) {
		target.clear();
		target.addAll(source);
	}

	private static <U, V> void clearAndAddAll(Map<U, V> target, Map<U, V> source) {
		target.clear();
		target.putAll(source);
	}

	private static TrainClient getTrainById(long id) {
		return TRAINS_BY_ID.get(id);
	}
}
