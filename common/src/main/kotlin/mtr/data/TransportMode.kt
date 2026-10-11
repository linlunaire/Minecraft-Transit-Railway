package mtr.data

enum class TransportMode(
	@JvmField val maxLength: Int,
	@JvmField val continuousMovement: Boolean,
	@JvmField val hasPitchAscending: Boolean,
	@JvmField val hasPitchDescending: Boolean,
	@JvmField val hasRouteTypeVariation: Boolean,
	@JvmField val railOffset: Int
) {
	TRAIN(Int.MAX_VALUE, false, true, true, true, 0),
	BOAT(1, false, true, true, true, 0),
	CABLE_CAR(1, true, false, false, false, -6),
	AIRPLANE(1, false, true, false, false, 0)
}
