package mtr.data;

import mtr.Registry;
import mtr.entity.EntitySeat;
import mtr.mappings.Utilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RailwayDataCoolDownModule extends RailwayDataModuleBase {

	private final Map<Player, Integer> playerRidingCoolDown = new HashMap<>();
	private final Map<Player, Long> playerRidingRoute = new HashMap<>();
	private final Map<Player, EntitySeat> playerSeats = new HashMap<>();
	private final Map<Player, Integer> playerSeatCoolDowns = new HashMap<>();
	private final Map<Player, Integer> playerShiftCoolDowns = new HashMap<>();

	public static final int SHIFT_ACTIVATE_TICKS = 30;

	public RailwayDataCoolDownModule(RailwayData railwayData, Level world, Map<BlockPos, Map<BlockPos, Rail>> rails) {
		super(railwayData, world, rails);
	}

	public void tick() {
		world.players().forEach(player -> {
			final Integer seatCoolDownOld = playerSeatCoolDowns.get(player);
			final EntitySeat seatOld = playerSeats.get(player);
			final EntitySeat seat;
			if (seatCoolDownOld == null || seatCoolDownOld <= 0 || Utilities.entityRemoved(seatOld)) {
				seat = new EntitySeat(world, player.getX(), player.getY(), player.getZ());
				world.addFreshEntity(seat);
				seat.initialize(player);
				playerSeats.put(player, seat);
				playerSeatCoolDowns.put(player, 3);
			} else {
				seat = seatOld;
				playerSeatCoolDowns.put(player, seatCoolDownOld - 1);
			}
			seat.updateSeatByRailwayData(player);

			final int oldShiftCoolDown = playerShiftCoolDowns.getOrDefault(player, 0);
			final int shiftCoolDown;
			if (player.isShiftKeyDown()) {
				shiftCoolDown = Math.min(SHIFT_ACTIVATE_TICKS, oldShiftCoolDown + 1);
			} else {
				shiftCoolDown = 0;
			}
			if (shiftCoolDown != oldShiftCoolDown) {
				playerShiftCoolDowns.put(player, shiftCoolDown);
			}
		});

		final Iterator<Map.Entry<Player, Integer>> playerRidingCoolDownIterator = playerRidingCoolDown.entrySet().iterator();
		while (playerRidingCoolDownIterator.hasNext()) {
			final Map.Entry<Player, Integer> entry = playerRidingCoolDownIterator.next();
			final Player player = entry.getKey();
			final int coolDown = entry.getValue();
			if (coolDown <= 0) {
				updatePlayerRiding(player, 0);
				player.stopRiding();
				playerRidingCoolDownIterator.remove();
				playerRidingRoute.remove(player);
			} else {
				entry.setValue(coolDown - 1);
			}
		}
	}

	public void onPlayerJoin(ServerPlayer serverPlayer) {
		playerRidingCoolDown.put(serverPlayer, 2);
		playerShiftCoolDowns.put(serverPlayer, 0);
	}

	public void onPlayerDisconnect(Player player) {
		playerSeats.remove(player);
		playerSeatCoolDowns.remove(player);
		playerShiftCoolDowns.remove(player);
	}

	public void updatePlayerRiding(Player player, long routeId) {
		final boolean isRiding = routeId != 0;
		player.fallDistance = 0;
		player.setNoGravity(isRiding);
		player.noPhysics = isRiding;
		if (isRiding) {
			Utilities.getAbilities(player).mayfly = true;
			playerRidingCoolDown.put(player, 2);
			playerRidingRoute.put(player, routeId);
		} else {
			((ServerPlayer) player).gameMode.getGameModeForPlayer().updatePlayerAbilities(Utilities.getAbilities(player));
		}
		Registry.setInTeleportationState(player, isRiding);
	}

	public void updatePlayerSeatCoolDown(Player player) {
		playerSeatCoolDowns.put(player, 3);
	}

	public boolean canRide(Player player) {
		return !playerRidingCoolDown.containsKey(player);
	}

	public Route getRidingRoute(Player player) {
		final Long routeId = playerRidingRoute.get(player);
		return routeId == null ? null : railwayData.dataCache.routeIdMap.get(routeId);
	}

	public void moveSeat(Player player, double x, double y, double z) {
		final EntitySeat entitySeat = playerSeats.get(player);
		if (entitySeat != null) {
			player.startRiding(entitySeat);
			entitySeat.setPos(x, y, z);
		}
	}

	public boolean shouldDismount(Player player) {
		return playerShiftCoolDowns.getOrDefault(player, 0) == SHIFT_ACTIVATE_TICKS;
	}
}
