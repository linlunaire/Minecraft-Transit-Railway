package mtr.data

import io.netty.buffer.Unpooled
import mtr.mappings.CompoundTagMapper
import mtr.packet.PacketTrainDataGuiServer
import mtr.path.PathData
import mtr.path.PathFinder
import mtr.path.PathGenerationTask
import java.util.concurrent.CancellationException
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.Level
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.function.Consumer
import java.util.stream.Collectors

@JvmSuppressWildcards
open class Depot : AreaBase, IReducedSaveData {
    @JvmField var clientPathGenerationSuccessfulSegments: Int = 0
    @JvmField var lastDeployedMillis: Long = 0
    @JvmField var useRealTime: Boolean = false
    @JvmField var repeatInfinitely: Boolean = false
    @JvmField var cruisingAltitude: Int = DEFAULT_CRUISING_ALTITUDE
    private var deployIndex = 0
    private var departureOffset = 0
    private var isDirty = true

    @JvmField val routeIds: MutableList<Long?> = ArrayList()
    @JvmField val platformTimes: MutableMap<Long?, MutableMap<Long?, Float?>?> = HashMap()
    @JvmField val departures: MutableList<Int?> = ArrayList()
    @JvmField val tempDepartures: MutableList<Int?> = ArrayList()

    private val frequencies = IntArray(HOURS_IN_DAY)
    private val deployableSidings = HashMap<Long, TrainServer?>()

    constructor(transportMode: TransportMode?) : super(transportMode)
    constructor(id: Long, transportMode: TransportMode?) : super(id, transportMode)

    constructor(map: Map<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        helper.iterateArrayValue(KEY_ROUTE_IDS) { routeIds.add(it.asIntegerValue().asLong()) }
        useRealTime = helper.getBoolean(KEY_USE_REAL_TIME)
        try {
            val array = map!![KEY_FREQUENCIES]!!.asArrayValue()
            for (i in 0 until HOURS_IN_DAY) frequencies[i] = array[i].asIntegerValue().asInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        helper.iterateArrayValue(KEY_DEPARTURES) { departures.add(it.asIntegerValue().asInt()) }
        deployIndex = helper.getInt(KEY_DEPLOY_INDEX)
        repeatInfinitely = helper.getBoolean(KEY_REPEAT_INFINITELY)
        cruisingAltitude = helper.getInt(KEY_CRUISING_ALTITUDE)
        lastDeployedMillis = System.currentTimeMillis() - helper.getLong(KEY_LAST_DEPLOYED)
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag) {
        for (routeId in CompoundTagMapper.getLongArray(compoundTag, KEY_ROUTE_IDS)) routeIds.add(routeId)
        for (i in 0 until HOURS_IN_DAY) frequencies[i] = CompoundTagMapper.getInt(compoundTag, KEY_FREQUENCIES + i)
        lastDeployedMillis = System.currentTimeMillis() - CompoundTagMapper.getLong(compoundTag, KEY_LAST_DEPLOYED)
        deployIndex = CompoundTagMapper.getInt(compoundTag, KEY_DEPLOY_INDEX)
        repeatInfinitely = CompoundTagMapper.getBoolean(compoundTag, KEY_REPEAT_INFINITELY)
        cruisingAltitude = CompoundTagMapper.getInt(compoundTag, KEY_CRUISING_ALTITUDE)
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        for (i in 0 until packet!!.readInt()) routeIds.add(packet.readLong())
        useRealTime = packet.readBoolean()
        for (i in 0 until HOURS_IN_DAY) frequencies[i] = packet.readInt()
        for (i in 0 until packet.readInt()) departures.add(packet.readInt())
        lastDeployedMillis = packet.readLong()
        deployIndex = packet.readInt()
        repeatInfinitely = packet.readBoolean()
        cruisingAltitude = packet.readInt()
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        toReducedMessagePack(messagePacker)
        messagePacker.packString(KEY_DEPLOY_INDEX).packInt(deployIndex)
        messagePacker.packString(KEY_LAST_DEPLOYED).packLong(System.currentTimeMillis() - lastDeployedMillis)
    }

    @Throws(IOException::class)
    override fun toReducedMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_ROUTE_IDS).packArrayHeader(routeIds.size)
        for (routeId in routeIds) messagePacker.packLong(routeId!!)
        messagePacker.packString(KEY_USE_REAL_TIME).packBoolean(useRealTime)
        messagePacker.packString(KEY_REPEAT_INFINITELY).packBoolean(repeatInfinitely)
        messagePacker.packString(KEY_CRUISING_ALTITUDE).packInt(cruisingAltitude)
        messagePacker.packString(KEY_FREQUENCIES).packArrayHeader(HOURS_IN_DAY)
        for (frequency in frequencies) messagePacker.packInt(frequency)
        messagePacker.packString(KEY_DEPARTURES).packArrayHeader(departures.size)
        for (departure in departures) messagePacker.packInt(departure!!)
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 7
    override fun reducedMessagePackLength(): Int = messagePackLength() - 2

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        packet.writeInt(routeIds.size)
        routeIds.forEach { packet.writeLong(it!!) }
        packet.writeBoolean(useRealTime)
        for (frequency in frequencies) packet.writeInt(frequency)
        packet.writeInt(departures.size)
        departures.forEach { packet.writeInt(it!!) }
        packet.writeLong(lastDeployedMillis)
        packet.writeInt(deployIndex)
        packet.writeBoolean(repeatInfinitely)
        packet.writeInt(cruisingAltitude)
    }

    override fun hasTransportMode(): Boolean = true

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        if (KEY_FREQUENCIES == key) {
            name = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
            color = packet.readInt()
            useRealTime = packet.readBoolean()
            for (i in 0 until HOURS_IN_DAY) frequencies[i] = packet.readInt()
            departures.clear()
            for (i in 0 until packet.readInt()) departures.add(packet.readInt())
            routeIds.clear()
            for (i in 0 until packet.readInt()) routeIds.add(packet.readLong())
            repeatInfinitely = packet.readBoolean()
            cruisingAltitude = packet.readInt()
        } else {
            super.update(key, packet)
        }
        isDirty = true
    }

    open fun getFrequency(index: Int): Int = if (index >= 0 && index < frequencies.size) frequencies[index] else 0

    open fun setFrequency(newFrequency: Int, index: Int) {
        if (index >= 0 && index < frequencies.size) frequencies[index] = newFrequency
        isDirty = true
    }

    open fun setData(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_FREQUENCIES)
        packet.writeUtf(name!!)
        packet.writeInt(color)
        packet.writeBoolean(useRealTime)
        for (frequency in frequencies) packet.writeInt(frequency)
        departures.replaceAll { it!! % MILLISECONDS_PER_DAY }
        departures.removeIf { it!! % 1000 != 0 }
        departures.sortWith { a, b -> a!!.compareTo(b!!) }
        packet.writeInt(departures.size)
        departures.forEach { packet.writeInt(it!!) }
        packet.writeInt(routeIds.size)
        routeIds.forEach { packet.writeLong(it!!) }
        packet.writeBoolean(repeatInfinitely)
        packet.writeInt(cruisingAltitude)
        sendPacket!!.accept(packet)
    }

    open fun generateMainRoute(
        minecraftServer: MinecraftServer?, world: Level?, dataCache: DataCache?,
        rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?, sidings: MutableSet<Siding>?, callback: Consumer<Thread>?
    ) {
        val platformsInRoute = ArrayList<SavedRailBase>()
        routeIds.forEach { routeId ->
            val route = dataCache!!.routeIdMap[routeId]
            if (route != null) {
                route.platformIds.forEach { entry ->
                    val platform = dataCache.platformIdMap[entry!!.platformId]
                    if (platform != null && (platformsInRoute.isEmpty() || platform.id != platformsInRoute[platformsInRoute.size - 1].id)) {
                        platformsInRoute.add(platform)
                    }
                }
            }
        }
        val useFastSpeed = cruisingAltitude >= (world!!.maxY + 1) + THRESHOLD_ABOVE_MAX_BUILD_HEIGHT
        val thread = Thread {
            val request = PathGenerationTask.current()
            try {
                request?.check()
                PathGenerationTask.checkInterrupted()
                val tempPath = ArrayList<PathData?>()
                val successfulSegmentsMain = PathFinder.findPath(tempPath, rails, platformsInRoute, 1, cruisingAltitude, useFastSpeed)
                var successfulSegments = Int.MAX_VALUE
                sidings!!.forEach { siding ->
                    PathGenerationTask.checkInterrupted()
                    val mid = siding.getMidPos()
                    if (siding.isTransportMode(transportMode) && inArea(mid.x, mid.z)) {
                        val first = if (platformsInRoute.isEmpty()) null else platformsInRoute[0]
                        val last = if (platformsInRoute.isEmpty()) null else platformsInRoute[platformsInRoute.size - 1]
                        val result = siding.generateRoute(minecraftServer, tempPath, successfulSegmentsMain, rails, first, last, repeatInfinitely, cruisingAltitude, useFastSpeed)
                        if (result < successfulSegments) successfulSegments = result
                    }
                }
                PathGenerationTask.publish(request, minecraftServer) {
                    PacketTrainDataGuiServer.generatePathS2C(world, id, successfulSegments)
                    System.out.println("Finished path generation" + if (name!!.isEmpty()) "" else " for " + name)
                }
            } catch (_: CancellationException) {
                // A replacement request owns the status; cancellation is not a failed route.
            } catch (e: Exception) {
                if (!Thread.currentThread().isInterrupted && (request == null || request.isCurrent)) {
                    e.printStackTrace()
                    PathGenerationTask.publish(request, minecraftServer) {
                        PacketTrainDataGuiServer.generatePathS2C(world, id, 0)
                        System.out.println("Failed to generate path" + if (name!!.isEmpty()) "" else " for " + name)
                    }
                }
            }
        }
        callback!!.accept(thread)
        thread.start()
    }

    open fun requestDeploy(sidingId: Long, train: TrainServer?) { deployableSidings[sidingId] = train }

    open fun deployTrain(railwayData: RailwayData?, world: Level?) {
        if (isDirty) generateTempDepartures(world)
        if (deployableSidings.isNotEmpty() && getMillisUntilDeploy(1) == 0) {
            val sidingsInDepot = railwayData!!.sidings.stream().filter { siding ->
                val pos = siding.getMidPos()
                siding.isTransportMode(transportMode) && inArea(pos.x, pos.z)
            }.sorted().collect(Collectors.toList())
            val size = sidingsInDepot.size
            var i = deployIndex
            while (i < deployIndex + size) {
                val train = deployableSidings[sidingsInDepot[i % size].id]
                if (train != null) {
                    lastDeployedMillis = System.currentTimeMillis()
                    deployIndex++
                    if (deployIndex >= size) deployIndex = 0
                    train.deployTrain()
                    break
                }
                i++
            }
        }
        departureOffset = 0
        deployableSidings.clear()
    }

    open fun getNextDepartureMillis(): Int {
        departureOffset++
        val millis = getMillisUntilDeploy(departureOffset)
        return if (millis >= 0) millis else -1
    }

    open fun getMillisUntilDeploy(offset: Int): Int = getMillisUntilDeploy(offset, 0)

    open fun getMillisUntilDeploy(offset: Int, currentTimeOffset: Int): Int {
        val millis = (System.currentTimeMillis() + currentTimeOffset) % MILLISECONDS_PER_DAY
        for (i in tempDepartures.indices) {
            val thisDeparture = tempDepartures[i]!!.toLong()
            val nextDeparture = wrapTime(tempDepartures[(i + 1) % tempDepartures.size]!!.toLong(), thisDeparture)
            val newMillis = wrapTime(millis, thisDeparture)
            if (newMillis > thisDeparture && newMillis <= nextDeparture) {
                if (offset > 1) {
                    if (offset <= tempDepartures.size) return (wrapTime(tempDepartures[(i + offset) % tempDepartures.size]!!.toLong(), millis) - millis).toInt()
                } else {
                    return if (wrapTime(lastDeployedMillis + currentTimeOffset, newMillis) - MILLISECONDS_PER_DAY >= thisDeparture) (nextDeparture - newMillis).toInt() else 0
                }
            }
        }
        return -1
    }

    open fun generateTempDepartures(world: Level?) {
        tempDepartures.clear()
        if (useRealTime && !transportMode!!.continuousMovement) {
            tempDepartures.addAll(departures)
        } else if (world != null) {
            var millisOffset = 0
            while (millisOffset < MILLISECONDS_PER_DAY) {
                val frequency = getFrequency(getHour(world, millisOffset))
                if (frequency == 0 && !transportMode!!.continuousMovement) {
                    millisOffset = (Math.floor((millisOffset.toFloat() / MILLIS_PER_TICK / TICKS_PER_HOUR).toDouble()) + 1).toInt() * TICKS_PER_HOUR * MILLIS_PER_TICK
                } else {
                    tempDepartures.add(((lastDeployedMillis + millisOffset) % MILLISECONDS_PER_DAY).toInt())
                    millisOffset += if (transportMode!!.continuousMovement) CONTINUOUS_MOVEMENT_FREQUENCY else TICKS_PER_HOUR * MILLIS_PER_TICK * TRAIN_FREQUENCY_MULTIPLIER / frequency
                }
            }
            tempDepartures.sortWith { a, b -> a!!.compareTo(b!!) }
        }
        isDirty = false
    }

    companion object {
        const val HOURS_IN_DAY = 24
        const val TRAIN_FREQUENCY_MULTIPLIER = 4
        const val TICKS_PER_HOUR = 1000
        const val MILLIS_PER_TICK = 50
        const val MILLISECONDS_PER_DAY = HOURS_IN_DAY * 60 * 60 * 1000
        const val DEFAULT_CRUISING_ALTITUDE = 256
        private const val TICKS_PER_DAY = HOURS_IN_DAY * TICKS_PER_HOUR
        private const val CONTINUOUS_MOVEMENT_FREQUENCY = 8000
        private const val THRESHOLD_ABOVE_MAX_BUILD_HEIGHT = 64
        private const val KEY_ROUTE_IDS = "route_ids"
        private const val KEY_USE_REAL_TIME = "use_real_time"
        private const val KEY_FREQUENCIES = "frequencies"
        private const val KEY_DEPARTURES = "departures"
        private const val KEY_LAST_DEPLOYED = "last_deployed"
        private const val KEY_DEPLOY_INDEX = "deploy_index"
        private const val KEY_REPEAT_INFINITELY = "repeat_infinitely"
        private const val KEY_CRUISING_ALTITUDE = "cruising_altitude"

        private fun getHour(world: Level, offsetMillis: Int): Int =
            wrapTime(world.overworldClockTime.toFloat() + offsetMillis.toFloat() / MILLIS_PER_TICK).toInt() / TICKS_PER_HOUR

        private fun wrapTime(time: Float): Float = (time + 6000 + TICKS_PER_DAY) % TICKS_PER_DAY

        private fun wrapTime(time: Long, mustBeGreaterThan: Long): Long {
            var newTime = time % MILLISECONDS_PER_DAY
            while (newTime <= mustBeGreaterThan) newTime += MILLISECONDS_PER_DAY
            return newTime
        }
    }
}
