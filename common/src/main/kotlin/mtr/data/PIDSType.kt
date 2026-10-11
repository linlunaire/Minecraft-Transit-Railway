package mtr.data

enum class PIDSType(
	@JvmField val showTerminatingPlatforms: Boolean,
	@JvmField val showPlatformNumber: Boolean,
	@JvmField val showCarCount: Boolean
) {
	ARRIVAL_PROJECTOR(false, true, false),
	PIDS(true, false, true),
	PIDS_VERTICAL(true, false, true),
	PIDS_SINGLE_ARRIVAL(true, true, true)
}
