package mtr.data;

import mtr.TrigCache;
import mtr.block.*;
import mtr.mappings.Utilities;
import mtr.path.PathData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.msgpack.value.Value;

import java.util.*;
import java.util.function.Consumer;

public class TrainServer extends Train {
	private static final Direction[] DIRECTIONS = Direction.values();

	private boolean canDeploy;
	private List<Map<UUID, Long>> trainPositions;
	private Map<Player, Set<TrainServer>> trainsInPlayerRange = new HashMap<>();
	private Map<Long, Map<BlockPos, TrainDelay>> trainDelays = new HashMap<>();
	private long routeId;
	private int updateRailProgressCounter;
	private int manualCoolDown;

	private final List<Siding.TimeSegment> timeSegments;

	private static final int TRAIN_UPDATE_DISTANCE = 128;
	private static final int TICKS_TO_SEND_RAIL_PROGRESS = 40;

	public TrainServer(long id, long sidingId, float railLength, String trainId, String baseTrainType, int trainCars, List<PathData> path, List<Double> distances, int repeatIndex1, int repeatIndex2, float accelerationConstant, List<Siding.TimeSegment> timeSegments, boolean isManual, int maxManualSpeed, int manualToAutomaticTime) {
		super(id, sidingId, railLength, trainId, baseTrainType, trainCars, path, distances, repeatIndex1, repeatIndex2, accelerationConstant, isManual, maxManualSpeed, manualToAutomaticTime);
		this.timeSegments = timeSegments;
	}

	public TrainServer(
			long sidingId, float railLength, List<Siding.TimeSegment> timeSegments,
			List<PathData> path, List<Double> distances, int repeatIndex1, int repeatIndex2,
			float accelerationConstant, boolean isManual, int maxManualSpeed, int manualToAutomaticTime,
			Map<String, Value> map
	) {
		super(sidingId, railLength, path, distances, repeatIndex1, repeatIndex2, accelerationConstant, isManual, maxManualSpeed, manualToAutomaticTime, map);
		this.timeSegments = timeSegments;
	}

	@Deprecated
	public TrainServer(
			long sidingId, float railLength, List<Siding.TimeSegment> timeSegments,
			List<PathData> path, List<Double> distances, int repeatIndex1, int repeatIndex2,
			float accelerationConstant, boolean isManual, int maxManualSpeed, int manualToAutomaticTime,
			CompoundTag compoundTag
	) {
		super(sidingId, railLength, path, distances, repeatIndex1, repeatIndex2, accelerationConstant, isManual, maxManualSpeed, manualToAutomaticTime, compoundTag);
		this.timeSegments = timeSegments;
	}

	@Override
	protected void startUp(Level world, int trainCars, int trainSpacing, boolean isOppositeRail) {
		canDeploy = false;
		isOnRoute = true;
		elapsedDwellTicks = 0;
		speed = Train.ACCELERATION_DEFAULT;
		if (isOppositeRail) {
			railProgress += trainCars * trainSpacing;
			reversed = !reversed;
		}
		nextStoppingIndex = getNextStoppingIndex();
		super.startUp(world, trainCars, trainSpacing, isOppositeRail);
	}

	@Override
	protected boolean openDoors() {
		if (isCurrentlyManual) {
			return doorTarget;
		} else {
			if (transportMode.continuousMovement) {
				final int index = getIndex(railProgress, false);
				if (path.get(index).dwellTime > 0 && index > 0) {
					final double doorValue1 = (railProgress - distances.get(index - 1)) * 0.5;
					final double doorValue2 = (distances.get(index) - railProgress) * 0.5;
					return doorValue1 > 0 && (doorValue2 > doorValue1 || doorValue2 > 1);
				} else {
					return false;
				}
			} else {
				final int dwellTicks = path.get(nextStoppingIndex).dwellTime * 10;
				final float maxDoorMoveTime = Math.min(DOOR_MOVE_TIME, dwellTicks / 2 - DOOR_DELAY);
				return elapsedDwellTicks >= DOOR_DELAY && elapsedDwellTicks < dwellTicks - DOOR_DELAY - maxDoorMoveTime;
			}
		}
	}

	@Override
	protected void simulateCar(
			Level world, int ridingCar, float ticksElapsed,
			double carX, double carY, double carZ, float carYaw, float carPitch,
			double prevCarX, double prevCarY, double prevCarZ, float prevCarYaw, float prevCarPitch,
			boolean doorLeftOpen, boolean doorRightOpen, double realSpacing
	) {
		VehicleRidingServer.mountRider(world, ridingEntities, id, routeId, carX, carY, carZ, realSpacing, width, carYaw, carPitch, doorLeftOpen || doorRightOpen, isManualAllowed || doorLeftOpen || doorRightOpen, ridingCar, PACKET_UPDATE_TRAIN_PASSENGERS, player -> !isManualAllowed || doorLeftOpen || doorRightOpen || Train.isHoldingKey(player), player -> {
			if (isHoldingKey(player)) {
				manualCoolDown = 0;
			}
		});
	}

	@Override
	protected boolean handlePositions(Level world, Vec3[] positions, float ticksElapsed) {
		final Vec3 firstPosition = positions[0];
		final Vec3 lastPosition = positions[positions.length - 1];
		final double minX = Math.min(firstPosition.x, lastPosition.x) - TRAIN_UPDATE_DISTANCE;
		final double minY = Math.min(firstPosition.y, lastPosition.y) - TRAIN_UPDATE_DISTANCE;
		final double minZ = Math.min(firstPosition.z, lastPosition.z) - TRAIN_UPDATE_DISTANCE;
		final double maxX = Math.max(firstPosition.x, lastPosition.x) + TRAIN_UPDATE_DISTANCE;
		final double maxY = Math.max(firstPosition.y, lastPosition.y) + TRAIN_UPDATE_DISTANCE;
		final double maxZ = Math.max(firstPosition.z, lastPosition.z) + TRAIN_UPDATE_DISTANCE;
		boolean playerNearby = false;
		for (final Player player : world.players()) {
			if (isPlayerRiding(player) || player.getX() >= minX && player.getX() < maxX && player.getY() >= minY && player.getY() < maxY && player.getZ() >= minZ && player.getZ() < maxZ) {
				trainsInPlayerRange.computeIfAbsent(player, ignored -> new HashSet<>()).add(this);
				playerNearby = true;
			}
		}

		final BlockPos frontPos = RailwayData.newBlockPos(positions[reversed ? positions.length - 1 : 0]);
		if (RailwayData.chunkLoaded(world, frontPos)) {
			checkBlock(frontPos, checkPos -> {
				if (RailwayData.chunkLoaded(world, checkPos)) {
					final BlockState state = world.getBlockState(checkPos);
					final Block block = state.getBlock();

					if (block instanceof BlockTrainRedstoneSensor && BlockTrainSensorBase.matchesFilter(world, checkPos, routeId, speed)) {
						((BlockTrainRedstoneSensor) block).power(world, state, checkPos);
					}

					if ((block instanceof BlockTrainCargoLoader || block instanceof BlockTrainCargoUnloader) && BlockTrainSensorBase.matchesFilter(world, checkPos, routeId, speed)) {
						for (final Direction direction : DIRECTIONS) {
							final Container nearbyInventory = HopperBlockEntity.getContainerAt(world, checkPos.relative(direction));
							if (nearbyInventory != null) {
								if (block instanceof BlockTrainCargoLoader) {
									transferItems(nearbyInventory, inventory);
								} else {
									transferItems(inventory, nearbyInventory);
								}
							}
						}
					}
				}
			});
		}

		if (!ridingEntities.isEmpty() && RailwayData.chunkLoaded(world, frontPos)) {
			checkBlock(frontPos, checkPos -> {
				if (RailwayData.chunkLoaded(world, checkPos) && world.getBlockState(checkPos).getBlock() instanceof BlockTrainAnnouncer) {
					final BlockEntity entity = world.getBlockEntity(checkPos);
					if (entity instanceof BlockTrainAnnouncer.TileEntityTrainAnnouncer && ((BlockTrainAnnouncer.TileEntityTrainAnnouncer) entity).matchesFilter(routeId, speed)) {
						ridingEntities.forEach(uuid -> ((BlockTrainAnnouncer.TileEntityTrainAnnouncer) entity).announce(world.getPlayerByUUID(uuid)));
					}
				}
			});
		}

		return playerNearby;
	}

	@Override
	protected boolean canDeploy(Depot depot) {
		if (path.size() > 1 && depot != null) {
			depot.requestDeploy(sidingId, this);
		}
		return canDeploy;
	}

	@Override
	protected boolean isRailBlocked(int checkIndex) {
		if (!transportMode.continuousMovement && trainPositions != null && checkIndex < path.size()) {
			final PathData pathData = path.get(checkIndex);
			final UUID railProduct = pathData.getRailProduct();
			for (final Map<UUID, Long> trainPositionsMap : trainPositions) {
				final Long occupyingTrain = trainPositionsMap.get(railProduct);
				if (occupyingTrain != null && occupyingTrain != id) {
					if (routeId != 0) {
						trainDelays.computeIfAbsent(routeId, ignored -> new HashMap<>()).computeIfAbsent(pathData.startingPos, ignored -> new TrainDelay()).delaying();
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean skipScanBlocks(Level world, double trainX, double trainY, double trainZ) {
		return world.getNearestPlayer(trainX, trainY, trainZ, MAX_CHECK_DISTANCE, entity -> true) == null;
	}

	@Override
	protected boolean openDoors(Level world, Block block, BlockPos checkPos, int dwellTicks) {
		if (block instanceof BlockPSDAPGDoorBase) {
			for (int i = -1; i <= 1; i++) {
				final BlockPos doorPos = checkPos.above(i);
				final BlockState state = world.getBlockState(doorPos);
				final Block doorBlock = state.getBlock();
				final BlockEntity entity = world.getBlockEntity(doorPos);

				if (doorBlock instanceof BlockPSDAPGDoorBase && entity instanceof BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase && IBlock.getStatePropertySafe(state, BlockPSDAPGDoorBase.UNLOCKED)) {
					final int doorStateValue = (int) Mth.clamp(doorValue * DOOR_MOVE_TIME, 0, BlockPSDAPGDoorBase.MAX_OPEN_VALUE);
					((BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase) entity).setOpen(doorStateValue);

					if (doorStateValue > 0 && !world.getBlockTicks().hasScheduledTick(doorPos, doorBlock)) {
						/* This schedules the block tick to the door (Ensures the door will be closed when the train passes by) */
						Utilities.scheduleBlockTick(world, doorPos, doorBlock, dwellTicks);
					}
				}
			}
		}

		return false;
	}

	@Override
	protected double asin(double value) {
		return TrigCache.asin(value);
	}

	public boolean simulateTrain(Level world, float ticksElapsed, Depot depot, DataCache dataCache, List<Map<UUID, Long>> trainPositions, Map<Player, Set<TrainServer>> trainsInPlayerRange, Map<Long, List<ScheduleEntry>> schedulesForPlatform, Map<Long, Map<BlockPos, TrainDelay>> trainDelays) {
		this.trainPositions = trainPositions;
		this.trainsInPlayerRange = trainsInPlayerRange;
		this.trainDelays = trainDelays;
		final int oldStoppingIndex = nextStoppingIndex;
		final int oldPassengerCount = ridingEntities.size();
		final boolean oldIsCurrentlyManual = isCurrentlyManual;
		final boolean oldStopped = speed == 0;
		final boolean oldDoorOpen = doorTarget;

		simulateTrain(world, ticksElapsed, depot);

		final int nextDepartureTicks = isOnRoute ? 0 : depot.getNextDepartureMillis();
		final long currentMillis = System.currentTimeMillis() - (long) (elapsedDwellTicks * Depot.MILLIS_PER_TICK) + (long) Math.max(0, nextDepartureTicks);

		double currentTime = -1;
		int startingIndex = 0;
		for (final Siding.TimeSegment timeSegment : timeSegments) {
			if (RailwayData.isBetween(railProgress, timeSegment.startRailProgress, timeSegment.endRailProgress)) {
				currentTime = timeSegment.getTime(railProgress);
				break;
			}
			startingIndex++;
		}

		if (currentTime >= 0) {
			float offsetTime = 0;
			float offsetTimeTemp = 0;
			boolean secondRound = false;
			long pendingPlatformId = 0;
			ScheduleEntry pendingSchedule = null;
			routeId = 0;
			for (int i = startingIndex; i < timeSegments.size() + (isRepeat() ? timeSegments.size() : 0); i++) {
				final Siding.TimeSegment timeSegment = timeSegments.get(i % timeSegments.size());

				if (timeSegment.savedRailBaseId != 0) {
					if (timeSegment.routeId == 0) {
						RailwayData.useRoutesAndStationsFromIndex(path.get(getIndex(timeSegment.endRailProgress, true)).stopIndex - 1, depot.routeIds, dataCache, (currentStationIndex, thisRoute, nextRoute, thisStation, nextStation, lastStation) -> {
							timeSegment.routeId = thisRoute == null ? 0 : thisRoute.id;
							timeSegment.currentStationIndex = currentStationIndex;
						});
					}

					final long platformId = timeSegment.savedRailBaseId;
					final List<ScheduleEntry> platformSchedules = schedulesForPlatform.computeIfAbsent(platformId, ignored -> new ArrayList<>());

					if (secondRound) {
						offsetTime = offsetTimeTemp - timeSegment.endTime;
						secondRound = false;
					} else if (pendingSchedule != null) {
						schedulesForPlatform.get(pendingPlatformId).add(pendingSchedule);
					}

					if (isOnRoute || nextDepartureTicks >= 0) {
						final long arrivalMillis = currentMillis + (long) ((timeSegment.endTime + offsetTime - currentTime) * Depot.MILLIS_PER_TICK);
						pendingPlatformId = platformId;
						pendingSchedule = new ScheduleEntry(arrivalMillis, trainCars, timeSegment.routeId, timeSegment.currentStationIndex);
						if (!isRepeat()) {
							platformSchedules.add(pendingSchedule);
							pendingSchedule = null;
						}
					}

					offsetTimeTemp = timeSegment.endTime;
				}

				if (routeId == 0) {
					routeId = timeSegment.routeId;
				}

				if (i == timeSegments.size() - 1) {
					secondRound = true;
				}
			}
		}

		updateRailProgressCounter++;
		if (updateRailProgressCounter == TICKS_TO_SEND_RAIL_PROGRESS) {
			updateRailProgressCounter = 0;
		}

		if (isManualAllowed) {
			if (isOnRoute) {
				if (manualCoolDown >= manualToAutomaticTime * 10) {
					if (isCurrentlyManual) {
						final int dwellTicks = nextStoppingIndex >= path.size() ? 0 : path.get(nextStoppingIndex).dwellTime * 10;
						elapsedDwellTicks = doorTarget ? dwellTicks / 2F : dwellTicks;
					}
					isCurrentlyManual = false;
				} else {
					manualCoolDown++;
					isCurrentlyManual = true;
				}
			} else {
				manualCoolDown = 0;
				isCurrentlyManual = true;
			}
		} else {
			isCurrentlyManual = false;
		}

		return oldPassengerCount > ridingEntities.size() || oldStoppingIndex != nextStoppingIndex || oldIsCurrentlyManual != isCurrentlyManual || oldStopped && speed != 0 || oldDoorOpen != doorTarget;
	}

	public void writeTrainPositions(List<Map<UUID, Long>> trainPositions, SignalBlocks signalBlocks) {
		if (!path.isEmpty()) {
			final int headIndex = getIndex(0, spacing, true);
			final int tailIndex = getIndex(trainCars, spacing, false);
			for (int i = tailIndex; i <= headIndex; i++) {
				final PathData pathData = path.get(i);
				if (i > 0 && pathData.savedRailBaseId != sidingId && pathData.rail.railType.hasSignal) {
					signalBlocks.occupy(pathData.getRailProduct(), trainPositions, id);
				}
			}
		}
	}

	public void deployTrain() {
		canDeploy = true;
	}

	private int getNextStoppingIndex() {
		final int headIndex = getIndex(0, 0, false);
		for (int i = headIndex; i < path.size(); i++) {
			if (path.get(i).dwellTime > 0) {
				return i;
			}
		}
		return path.size() - 1;
	}

	private void checkBlock(BlockPos pos, Consumer<BlockPos> callback) {
		final int checkRadius = (int) Math.floor(speed);
		for (int x = -checkRadius; x <= checkRadius; x++) {
			for (int z = -checkRadius; z <= checkRadius; z++) {
				for (int y = 0; y <= 3; y++) {
					callback.accept(pos.offset(x, -y, z));
				}
			}
		}
	}

	private static void transferItems(Container inventoryFrom, Container inventoryTo) {
		for (int i = 0; i < inventoryFrom.getContainerSize(); i++) {
			if (!inventoryFrom.getItem(i).isEmpty()) {
				final ItemStack insertItem = inventoryFrom.getItem(i).copyWithCount(1);

				final ItemStack remainingStack = HopperBlockEntity.addItem(null, inventoryTo, insertItem, null);
				if (remainingStack.isEmpty()) {
					inventoryFrom.removeItem(i, 1);
					return;
				}
			}
		}
	}
}
