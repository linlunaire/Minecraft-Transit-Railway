package mtr.data

enum class RouteType {
	NORMAL, LIGHT_RAIL, HIGH_SPEED;

	open fun next(): RouteType = entries[(ordinal + 1) % entries.size]
}
