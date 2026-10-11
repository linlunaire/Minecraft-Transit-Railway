package mtr.data

import io.netty.buffer.Unpooled
import mtr.mappings.CompoundTagMapper
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.function.Consumer

// The original final Java class exported non-final instance methods.
@Suppress("NON_FINAL_MEMBER_IN_FINAL_CLASS")
@JvmSuppressWildcards
class Route : NameColorDataBase, IGui {
    @JvmField var routeType: RouteType? = RouteType.NORMAL
    @JvmField var isLightRailRoute: Boolean = false
    @JvmField var isHidden: Boolean = false
    @JvmField var disableNextStationAnnouncements: Boolean = false
    @JvmField var circularState: CircularState? = CircularState.NONE
    @JvmField var lightRailRouteNumber: String? = ""
    @JvmField val platformIds: MutableList<RoutePlatform?> = ArrayList()

    constructor(transportMode: TransportMode?) : this(0, transportMode)
    constructor(id: Long, transportMode: TransportMode?) : super(id, transportMode)

    constructor(map: Map<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        helper.iterateArrayValue(KEY_PLATFORM_IDS) { platformIds.add(RoutePlatform(it.asIntegerValue().asLong())) }
        val destinations = ArrayList<String>()
        helper.iterateArrayValue(KEY_CUSTOM_DESTINATIONS) { destinations.add(it.asStringValue().asString()) }
        for (i in 0 until minOf(platformIds.size, destinations.size)) platformIds[i]!!.customDestination = destinations[i]
        routeType = EnumHelper.valueOf(RouteType.NORMAL, helper.getString(KEY_ROUTE_TYPE))
        isLightRailRoute = helper.getBoolean(KEY_IS_LIGHT_RAIL_ROUTE)
        isHidden = helper.getBoolean(KEY_IS_ROUTE_HIDDEN)
        disableNextStationAnnouncements = helper.getBoolean(KEY_DISABLE_NEXT_STATION_ANNOUNCEMENTS)
        lightRailRouteNumber = helper.getString(KEY_LIGHT_RAIL_ROUTE_NUMBER)
        circularState = EnumHelper.valueOf(CircularState.NONE, helper.getString(KEY_CIRCULAR_STATE))
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag) {
        for (id in CompoundTagMapper.getLongArray(compoundTag, KEY_PLATFORM_IDS)) platformIds.add(RoutePlatform(id))
        routeType = EnumHelper.valueOf(RouteType.NORMAL, CompoundTagMapper.getString(compoundTag, KEY_ROUTE_TYPE))
        isLightRailRoute = CompoundTagMapper.getBoolean(compoundTag, KEY_IS_LIGHT_RAIL_ROUTE)
        isHidden = CompoundTagMapper.getBoolean(compoundTag, KEY_IS_ROUTE_HIDDEN)
        disableNextStationAnnouncements = CompoundTagMapper.getBoolean(compoundTag, KEY_DISABLE_NEXT_STATION_ANNOUNCEMENTS)
        lightRailRouteNumber = CompoundTagMapper.getString(compoundTag, KEY_LIGHT_RAIL_ROUTE_NUMBER)
        circularState = EnumHelper.valueOf(CircularState.NONE, CompoundTagMapper.getString(compoundTag, KEY_CIRCULAR_STATE))
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        readPlatforms(packet)
        routeType = EnumHelper.valueOf(RouteType.NORMAL, packet!!.readUtf(PACKET_STRING_READ_LENGTH))
        isLightRailRoute = packet.readBoolean()
        isHidden = packet.readBoolean()
        disableNextStationAnnouncements = packet.readBoolean()
        lightRailRouteNumber = packet.readUtf(PACKET_STRING_READ_LENGTH)
        circularState = EnumHelper.valueOf(CircularState.NONE, packet.readUtf(PACKET_STRING_READ_LENGTH))
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_PLATFORM_IDS).packArrayHeader(platformIds.size)
        for (platform in platformIds) messagePacker.packLong(platform!!.platformId)
        messagePacker.packString(KEY_CUSTOM_DESTINATIONS).packArrayHeader(platformIds.size)
        for (platform in platformIds) messagePacker.packString(platform!!.customDestination)
        messagePacker.packString(KEY_ROUTE_TYPE).packString(routeType!!.toString())
        messagePacker.packString(KEY_IS_LIGHT_RAIL_ROUTE).packBoolean(isLightRailRoute)
        messagePacker.packString(KEY_IS_ROUTE_HIDDEN).packBoolean(isHidden)
        messagePacker.packString(KEY_DISABLE_NEXT_STATION_ANNOUNCEMENTS).packBoolean(disableNextStationAnnouncements)
        messagePacker.packString(KEY_LIGHT_RAIL_ROUTE_NUMBER).packString(lightRailRouteNumber)
        messagePacker.packString(KEY_CIRCULAR_STATE).packString(circularState!!.toString())
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 8

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        writePlatforms(packet)
        packet.writeUtf(routeType!!.toString())
        packet.writeBoolean(isLightRailRoute)
        packet.writeBoolean(isHidden)
        packet.writeBoolean(disableNextStationAnnouncements)
        packet.writeUtf(lightRailRouteNumber!!)
        packet.writeUtf(circularState!!.toString())
    }

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        when (key!!) {
            KEY_PLATFORM_IDS -> {
                platformIds.clear()
                readPlatforms(packet)
            }
            KEY_IS_LIGHT_RAIL_ROUTE -> {
                name = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
                color = packet.readInt()
                routeType = EnumHelper.valueOf(RouteType.NORMAL, packet.readUtf(PACKET_STRING_READ_LENGTH))
                isLightRailRoute = packet.readBoolean()
                lightRailRouteNumber = packet.readUtf(PACKET_STRING_READ_LENGTH)
                isHidden = packet.readBoolean()
                disableNextStationAnnouncements = packet.readBoolean()
                circularState = EnumHelper.valueOf(CircularState.NONE, packet.readUtf(PACKET_STRING_READ_LENGTH))
            }
            else -> super.update(key, packet)
        }
    }

    override fun hasTransportMode(): Boolean = true

    open fun setPlatformIds(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_PLATFORM_IDS)
        writePlatforms(packet)
        sendPacket!!.accept(packet)
    }

    open fun setExtraData(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_IS_LIGHT_RAIL_ROUTE)
        packet.writeUtf(name!!)
        packet.writeInt(color)
        packet.writeUtf(routeType!!.toString())
        packet.writeBoolean(isLightRailRoute)
        packet.writeUtf(lightRailRouteNumber!!)
        packet.writeBoolean(isHidden)
        packet.writeBoolean(disableNextStationAnnouncements)
        packet.writeUtf(circularState!!.toString())
        sendPacket!!.accept(packet)
    }

    open fun getPlatformIdIndex(platformId: Long): Int {
        for (i in platformIds.indices) if (platformIds[i]!!.platformId == platformId) return i
        return -1
    }

    open fun containsPlatformId(platformId: Long): Boolean = getPlatformIdIndex(platformId) >= 0
    open fun getFirstPlatformId(): Long = if (platformIds.isEmpty()) 0 else platformIds[0]!!.platformId
    open fun getLastPlatformId(): Long = if (platformIds.isEmpty()) 0 else platformIds[platformIds.size - 1]!!.platformId

    open fun getDestination(index: Int): String? {
        for (i in minOf(platformIds.size - 1, index) downTo 0) {
            val destination = platformIds[i]!!.customDestination
            if (destinationIsReset(destination)) return null
            if (!destination!!.isEmpty()) return destination
        }
        return null
    }

    private fun readPlatforms(packet: FriendlyByteBuf?) {
        val count = packet!!.readInt()
        for (i in 0 until count) {
            val platform = RoutePlatform(packet.readLong())
            platform.customDestination = packet.readUtf(PACKET_STRING_READ_LENGTH)
            platformIds.add(platform)
        }
    }

    private fun writePlatforms(packet: FriendlyByteBuf) {
        packet.writeInt(platformIds.size)
        platformIds.forEach { platform ->
            packet.writeLong(platform!!.platformId)
            packet.writeUtf(platform.customDestination!!)
        }
    }

    open class RoutePlatform(@JvmField val platformId: Long) {
        @JvmField var customDestination: String? = ""
    }

    enum class CircularState { NONE, CLOCKWISE, ANTICLOCKWISE }

    companion object {
        private const val KEY_PLATFORM_IDS = "platform_ids"
        private const val KEY_CUSTOM_DESTINATIONS = "custom_destinations"
        private const val KEY_ROUTE_TYPE = "route_type"
        private const val KEY_IS_LIGHT_RAIL_ROUTE = "is_light_rail_route"
        private const val KEY_LIGHT_RAIL_ROUTE_NUMBER = "light_rail_route_number"
        private const val KEY_IS_ROUTE_HIDDEN = "is_route_hidden"
        private const val KEY_DISABLE_NEXT_STATION_ANNOUNCEMENTS = "disable_next_station_announcements"
        private const val KEY_CIRCULAR_STATE = "circular_state"

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun destinationIsReset(destination: String?): Boolean = destination!!.equals("\\r") || destination.equals("\\reset")
    }
}
