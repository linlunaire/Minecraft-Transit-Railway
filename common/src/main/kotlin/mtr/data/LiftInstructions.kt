package mtr.data

import mtr.block.BlockLiftButtons
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import java.util.function.Consumer

open class LiftInstructions {
	private var dirty = false
	private val instructions = ArrayList<LiftInstruction>()

	constructor()

	constructor(packet: FriendlyByteBuf) {
		val instructionsCount = packet.readInt()
		for (index in 0 until instructionsCount) {
			instructions.add(LiftInstruction(packet))
		}
	}

	open fun writePacket(packet: FriendlyByteBuf) {
		packet.writeInt(instructions.size)
		instructions.forEach { it.writePacket(packet) }
	}

	open fun copyFrom(liftInstructions: LiftInstructions?) {
		dirty = false
		instructions.clear()
		instructions.addAll(liftInstructions!!.instructions)
	}

	open fun getTargetFloor(callback: Consumer<Int>?) {
		if (hasInstructions()) {
			callback!!.accept(instructions[0].floor)
		}
	}

	open fun arrived() {
		if (hasInstructions()) {
			instructions.removeAt(0)
			dirty = true
		}
	}

	open fun hasInstructions(): Boolean = instructions.isNotEmpty()

	open fun addInstruction(currentFloor: Int, currentMovingUp: Boolean, floor: Int) {
		addInstruction(currentFloor, currentMovingUp, floor, false, true, true)
	}

	open fun isDirty(): Boolean {
		val result = dirty
		dirty = false
		return result
	}

	private fun addInstruction(currentFloor: Int, currentMovingUp: Boolean, newFloor: Int, newMovingUp: Boolean, noDirection: Boolean, shouldAdd: Boolean): Int {
		if (currentFloor == newFloor) {
			return 0
		}

		val tempInstructions = ArrayList(instructions)
		tempInstructions.add(0, LiftInstruction(currentFloor, currentMovingUp))
		var distance = 0
		for (index in 0 until tempInstructions.size - 1) {
			val previousInstruction = tempInstructions[index]
			val nextInstruction = tempInstructions[index + 1]
			val movingUp = if (noDirection) nextInstruction.movingUp else newMovingUp
			if (instructions.contains(LiftInstruction(newFloor, movingUp))) {
				return -1
			}
			if (nextInstruction.canInsert(previousInstruction, newFloor, movingUp)) {
				if (shouldAdd) {
					instructions.add(index, LiftInstruction(newFloor, movingUp))
					dirty = true
				}
				return distance + Math.abs(newFloor - previousInstruction.floor)
			}
			distance += Math.abs(nextInstruction.floor - previousInstruction.floor)
		}

		val lastFloor = if (hasInstructions()) instructions[instructions.size - 1].floor else currentFloor
		if (shouldAdd) {
			instructions.add(LiftInstruction(newFloor, if (noDirection) newFloor > lastFloor else newMovingUp))
			dirty = true
		}
		return distance + Math.abs(newFloor - lastFloor)
	}

	open fun containsInstruction(floor: Int, movingUp: Boolean): Boolean =
		instructions.contains(LiftInstruction(floor, movingUp))

	open fun containsInstruction(floor: Int): Boolean =
		containsInstruction(floor, true) || containsInstruction(floor, false)

	companion object {
		// Java subclasses could hide this static entry point before migration.
		@Suppress("NON_FINAL_MEMBER_IN_OBJECT")
		@JvmStatic
		open fun addInstruction(world: Level, pos: BlockPos, topHalfClicked: Boolean) {
			val blockEntity = world.getBlockEntity(pos)
			if (blockEntity !is BlockLiftButtons.TileEntityLiftButtons) {
				return
			}
			val railwayData = RailwayData.getInstance(world) ?: return
			var currentWeight = Int.MAX_VALUE
			var instructionsToUse: LiftInstructions? = null
			var floorToUse = 0
			var movingUpToUse = false
			var newFloorToUse = 0
			val hasButtonOverall = booleanArrayOf(false, false)

			blockEntity.forEachTrackPosition(world) { trackPosition, _ ->
				railwayData.lifts.stream().filter { it.hasFloor(trackPosition) }.findFirst().ifPresent { lift ->
					val liftFloor = Math.round(lift.positionY).toInt()
					val newLiftFloor = trackPosition.y
					val hasButton = booleanArrayOf(false, false)
					lift.hasUpDownButtonForFloor(newLiftFloor, hasButton)
					val newMovingUp = if (topHalfClicked) hasButton[0] else !hasButton[1]
					val liftMovingUp = lift.liftDirection == Lift.LiftDirection.UP
					val weight = lift.liftInstructions.addInstruction(liftFloor, liftMovingUp, newLiftFloor, newMovingUp, false, false)
					if (weight >= 0 && (topHalfClicked == newMovingUp && weight < currentWeight || newMovingUp && !hasButtonOverall[0] || !newMovingUp && !hasButtonOverall[1])) {
						currentWeight = weight
						instructionsToUse = lift.liftInstructions
						floorToUse = liftFloor
						movingUpToUse = liftMovingUp
						newFloorToUse = newLiftFloor
					}
					if (hasButton[0]) hasButtonOverall[0] = true
					if (hasButton[1]) hasButtonOverall[1] = true
				}
			}
			instructionsToUse?.addInstruction(floorToUse, movingUpToUse, newFloorToUse, if (topHalfClicked) hasButtonOverall[0] else !hasButtonOverall[1], false, true)
		}
	}

	private class LiftInstruction(val floor: Int, val movingUp: Boolean) {
		constructor(packet: FriendlyByteBuf) : this(packet.readInt(), packet.readBoolean())

		fun canInsert(previous: LiftInstruction, newFloor: Int, newMovingUp: Boolean): Boolean =
			if (RailwayData.isBetween(newFloor.toDouble(), previous.floor.toDouble(), floor.toDouble()) && newMovingUp == movingUp) {
				true
			} else {
				previous.movingUp != movingUp && previous.movingUp == (newFloor > previous.floor)
			}

		fun writePacket(packet: FriendlyByteBuf) {
			packet.writeInt(floor)
			packet.writeBoolean(movingUp)
		}

		override fun equals(other: Any?): Boolean =
			other is LiftInstruction && floor == other.floor && movingUp == other.movingUp
	}
}
