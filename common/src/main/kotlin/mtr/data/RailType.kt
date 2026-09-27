package mtr.data

import net.minecraft.world.level.material.MapColor

enum class RailType(
    @JvmField val speedLimit: Int, mapColor: MapColor,
    @JvmField val hasSavedRail: Boolean, @JvmField val canAccelerate: Boolean,
    @JvmField val hasSignal: Boolean, @JvmField val railSlopeStyle: RailSlopeStyle
) : IGui {
    WOODEN(20, MapColor.WOOD, false, true, true, RailSlopeStyle.CURVE),
    STONE(40, MapColor.STONE, false, true, true, RailSlopeStyle.CURVE),
    EMERALD(60, MapColor.EMERALD, false, true, true, RailSlopeStyle.CURVE),
    IRON(80, MapColor.METAL, false, true, true, RailSlopeStyle.CURVE),
    OBSIDIAN(120, MapColor.COLOR_PURPLE, false, true, true, RailSlopeStyle.CURVE),
    BLAZE(160, MapColor.COLOR_ORANGE, false, true, true, RailSlopeStyle.CURVE),
    QUARTZ(200, MapColor.QUARTZ, false, true, true, RailSlopeStyle.CURVE),
    DIAMOND(300, MapColor.DIAMOND, false, true, true, RailSlopeStyle.CURVE),
    PLATFORM(80, MapColor.COLOR_RED, true, false, true, RailSlopeStyle.CURVE),
    SIDING(40, MapColor.COLOR_YELLOW, true, false, true, RailSlopeStyle.CURVE),
    TURN_BACK(80, MapColor.COLOR_BLUE, false, false, true, RailSlopeStyle.CURVE),
    CABLE_CAR(30, MapColor.SNOW, false, true, true, RailSlopeStyle.CABLE),
    CABLE_CAR_STATION(2, MapColor.SNOW, false, true, true, RailSlopeStyle.CURVE),
    RUNWAY(300, MapColor.ICE, false, true, false, RailSlopeStyle.CURVE),
    AIRPLANE_DUMMY(900, MapColor.COLOR_BLACK, false, true, false, RailSlopeStyle.CURVE),
    NONE(20, MapColor.COLOR_BLACK, false, false, true, RailSlopeStyle.CURVE);

    @JvmField val maxBlocksPerTick: Float = speedLimit / 3.6F / 20
    @JvmField val color: Int = mapColor.col or IGui.ARGB_BLACK

    enum class RailSlopeStyle { CURVE, CABLE }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getDefaultMaxBlocksPerTick(transportMode: TransportMode?): Float =
            (if (transportMode!!.continuousMovement) CABLE_CAR_STATION else WOODEN).maxBlocksPerTick
    }
}
