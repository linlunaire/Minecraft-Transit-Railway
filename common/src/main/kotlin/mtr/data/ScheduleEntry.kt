package mtr.data

import net.minecraft.network.FriendlyByteBuf

open class ScheduleEntry(
	@JvmField val arrivalMillis: Long,
	@JvmField val trainCars: Int,
	@JvmField val routeId: Long,
	@JvmField val currentStationIndex: Int
) : Comparable<ScheduleEntry> {
	constructor(packet: FriendlyByteBuf) : this(
		packet.readLong(), packet.readInt(), packet.readLong(), packet.readInt()
	)

	open fun writePacket(packet: FriendlyByteBuf) {
		packet.writeLong(arrivalMillis)
		packet.writeInt(trainCars)
		packet.writeLong(routeId)
		packet.writeInt(currentStationIndex)
	}

	// Keep the legacy ordering, including its -1 result for identical keys.
	override fun compareTo(other: ScheduleEntry): Int =
		if (arrivalMillis == other.arrivalMillis) {
			if (routeId > other.routeId) 1 else -1
		} else {
			if (arrivalMillis > other.arrivalMillis) 1 else -1
		}
}
