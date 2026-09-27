package mtr.data

import mtr.mappings.CompoundTagMapper
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.apache.commons.lang3.math.NumberUtils
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException

@JvmSuppressWildcards
abstract class SavedRailBase : NameColorDataBase {
    @JvmField protected var dwellTime: Int
    private val positions: MutableSet<BlockPos?> = HashSet(2)

    constructor(id: Long, transportMode: TransportMode?, pos1: BlockPos?, pos2: BlockPos?) : super(id, transportMode) {
        name = "1"
        positions.add(pos1)
        positions.add(pos2)
        dwellTime = if (transportMode!!.continuousMovement) 1 else DEFAULT_DWELL_TIME
    }

    constructor(transportMode: TransportMode?, pos1: BlockPos?, pos2: BlockPos?) : this(0, transportMode, pos1, pos2)

    constructor(map: Map<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        positions.add(BlockPos.of(helper.getLong(KEY_POS_1)))
        positions.add(BlockPos.of(helper.getLong(KEY_POS_2)))
        dwellTime = if (transportMode!!.continuousMovement) 1 else helper.getInt(KEY_DWELL_TIME)
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag) {
        positions.add(BlockPos.of(CompoundTagMapper.getLong(compoundTag, KEY_POS_1)))
        positions.add(BlockPos.of(CompoundTagMapper.getLong(compoundTag, KEY_POS_2)))
        dwellTime = if (transportMode!!.continuousMovement) 1 else CompoundTagMapper.getInt(compoundTag, KEY_DWELL_TIME)
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        positions.add(packet!!.readBlockPos())
        positions.add(packet.readBlockPos())
        dwellTime = packet.readInt()
        if (transportMode!!.continuousMovement) dwellTime = 1
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_POS_1).packLong(getPosition(0)!!.asLong())
        messagePacker.packString(KEY_POS_2).packLong(getPosition(1)!!.asLong())
        messagePacker.packString(KEY_DWELL_TIME).packInt(dwellTime)
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 3

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        packet.writeBlockPos(getPosition(0)!!)
        packet.writeBlockPos(getPosition(1)!!)
        packet.writeInt(dwellTime)
    }

    final override fun hasTransportMode(): Boolean = true

    open fun containsPos(pos: BlockPos?): Boolean = positions.contains(pos)

    open fun getMidPos(): BlockPos = getMidPos(false)

    open fun getMidPos(zeroY: Boolean): BlockPos {
        val pos = getPosition(0)!!.offset(getPosition(1)!!)
        return RailwayData.newBlockPos(pos.x / 2, if (zeroY) 0 else pos.y / 2, pos.z / 2)
    }

    open fun getAxis(): Direction.Axis {
        val difference = getPosition(0)!!.subtract(getPosition(1)!!)
        return if (Math.abs(difference.x) > Math.abs(difference.z)) Direction.Axis.X else Direction.Axis.Z
    }

    open fun isInvalidSavedRail(rails: Map<BlockPos, Map<BlockPos, Rail>>?): Boolean {
        val pos1 = getPosition(0)
        val pos2 = getPosition(1)
        return isInvalidSavedRail(rails, pos1, pos2) || isInvalidSavedRail(rails, pos2, pos1)
    }

    open fun isCloseToSavedRail(pos: BlockPos?, radius: Int, lower: Int, upper: Int): Boolean {
        val pos1 = getPosition(0)!!
        val pos2 = getPosition(1)!!
        val x1 = Math.min(pos1.x, pos2.x)
        val y1 = Math.min(pos1.y, pos2.y)
        val z1 = Math.min(pos1.z, pos2.z)
        val x2 = Math.max(pos1.x, pos2.x)
        val y2 = Math.max(pos1.y, pos2.y)
        val z2 = Math.max(pos1.z, pos2.z)
        return pos!!.x >= x1 - radius && pos.x < x2 + radius + 1 &&
            pos.y >= y1 - lower && pos.y < y2 + upper + 1 &&
            pos.z >= z1 - radius && pos.z < z2 + radius + 1
    }

    open fun getOrderedPositions(pos: BlockPos?, reverse: Boolean): MutableList<BlockPos> {
        val pos1 = getPosition(0)!!
        val pos2 = getPosition(1)!!
        val d1 = pos1.distSqr(pos!!)
        val d2 = pos2.distSqr(pos)
        val ordered = ArrayList<BlockPos>()
        if ((d2 > d1) == reverse) {
            ordered.add(pos2)
            ordered.add(pos1)
        } else {
            ordered.add(pos1)
            ordered.add(pos2)
        }
        return ordered
    }

    open fun getOtherPosition(pos: BlockPos?): BlockPos? {
        val pos1 = getPosition(0)
        val pos2 = getPosition(1)
        return if (pos!!.equals(pos1)) pos2 else pos1
    }

    open fun getDwellTime(): Int {
        if (dwellTime <= 0 || dwellTime > MAX_DWELL_TIME) dwellTime = DEFAULT_DWELL_TIME
        return if (transportMode!!.continuousMovement) 1 else dwellTime
    }

    protected open fun writeDwellTimePacket(packet: FriendlyByteBuf?, newDwellTime: Int) {
        dwellTime = when {
            transportMode!!.continuousMovement -> 1
            newDwellTime <= 0 || newDwellTime > MAX_DWELL_TIME -> DEFAULT_DWELL_TIME
            else -> newDwellTime
        }
        packet!!.writeInt(dwellTime)
    }

    private fun getPosition(index: Int): BlockPos? {
        if (positions.size <= index) return RailwayData.newBlockPos(0, 0, 0)
        // Keep HashSet order and original position references without copying the whole set per lookup.
        val iterator = positions.iterator()
        repeat(index) { iterator.next() }
        return iterator.next()
    }

    override fun compareTo(other: NameColorDataBase?): Int {
        val thisIsNumber = NumberUtils.isParsable(name)
        val otherIsNumber = NumberUtils.isParsable(other!!.name)
        return if (thisIsNumber && otherIsNumber) {
            val comparison = java.lang.Float.compare(java.lang.Float.parseFloat(name), java.lang.Float.parseFloat(other.name))
            if (comparison == 0) super.compareTo(other) else comparison
        } else if (thisIsNumber) -1 else if (otherIsNumber) 1 else super.compareTo(other)
    }

    companion object {
        const val MAX_DWELL_TIME = 1200
        private const val DEFAULT_DWELL_TIME = 20
        private const val KEY_POS_1 = "pos_1"
        private const val KEY_POS_2 = "pos_2"
        private const val KEY_DWELL_TIME = "dwell_time"

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun isInvalidSavedRail(rails: Map<BlockPos, Map<BlockPos, Rail>>?, pos1: BlockPos?, pos2: BlockPos?): Boolean =
            !RailwayData.containsRail(rails, pos1, pos2) || !rails!![pos1]!![pos2]!!.railType.hasSavedRail
    }
}
