package mtr.path

import mtr.data.MessagePackHelper
import mtr.data.Rail
import mtr.data.RailwayData
import mtr.data.SerializedDataBase
import mtr.mappings.CompoundTagMapper
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.UUID

open class PathData : SerializedDataBase {
    @JvmField val rail: Rail?
    @JvmField val savedRailBaseId: Long
    @JvmField val dwellTime: Int
    @JvmField val stopIndex: Int
    @JvmField val startingPos: BlockPos?
    private val endingPos: BlockPos?
    private var railProduct: UUID? = null

    constructor(rail: Rail?, savedRailBaseId: Long, dwellTime: Int, startingPos: BlockPos?, endingPos: BlockPos?, stopIndex: Int) {
        this.rail = rail
        this.savedRailBaseId = savedRailBaseId
        this.dwellTime = dwellTime
        this.startingPos = startingPos
        this.endingPos = endingPos
        this.stopIndex = stopIndex
    }

    constructor(map: MutableMap<String?, Value?>?) {
        val helper = MessagePackHelper(map)
        rail = Rail(RailwayData.castMessagePackValueToSKMap(map!![KEY_RAIL]))
        savedRailBaseId = helper.getLong(KEY_SAVED_RAIL_BASE_ID)
        dwellTime = helper.getInt(KEY_DWELL_TIME)
        stopIndex = helper.getInt(KEY_STOP_INDEX)
        startingPos = BlockPos.of(helper.getLong(KEY_STARTING_POS))
        endingPos = BlockPos.of(helper.getLong(KEY_ENDING_POS))
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) {
        rail = Rail(compoundTag!!.getCompoundOrEmpty(KEY_RAIL))
        savedRailBaseId = CompoundTagMapper.getLong(compoundTag, KEY_SAVED_RAIL_BASE_ID)
        dwellTime = CompoundTagMapper.getInt(compoundTag, KEY_DWELL_TIME)
        stopIndex = CompoundTagMapper.getInt(compoundTag, KEY_STOP_INDEX)
        startingPos = BlockPos.of(CompoundTagMapper.getLong(compoundTag, KEY_STARTING_POS))
        endingPos = BlockPos.of(CompoundTagMapper.getLong(compoundTag, KEY_ENDING_POS))
    }

    constructor(packet: FriendlyByteBuf?) {
        rail = Rail(packet)
        savedRailBaseId = packet!!.readLong()
        dwellTime = packet.readInt()
        stopIndex = packet.readInt()
        startingPos = BlockPos.of(packet.readLong())
        endingPos = BlockPos.of(packet.readLong())
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        messagePacker.packString(KEY_RAIL)
        messagePacker.packMapHeader(rail!!.messagePackLength())
        rail.toMessagePack(messagePacker)
        messagePacker.packString(KEY_SAVED_RAIL_BASE_ID).packLong(savedRailBaseId)
        messagePacker.packString(KEY_DWELL_TIME).packInt(dwellTime)
        messagePacker.packString(KEY_STOP_INDEX).packInt(stopIndex)
        messagePacker.packString(KEY_STARTING_POS).packLong(startingPos!!.asLong())
        messagePacker.packString(KEY_ENDING_POS).packLong(endingPos!!.asLong())
    }

    override fun messagePackLength(): Int = 6

    override fun writePacket(packet: FriendlyByteBuf) {
        rail!!.writePacket(packet)
        packet.writeLong(savedRailBaseId)
        packet.writeInt(dwellTime)
        packet.writeInt(stopIndex)
        packet.writeLong(startingPos!!.asLong())
        packet.writeLong(endingPos!!.asLong())
    }

    open fun isSameRail(pathData: PathData?): Boolean =
        startingPos!!.equals(pathData!!.startingPos) && endingPos!!.equals(pathData.endingPos)

    open fun isOppositeRail(pathData: PathData?): Boolean =
        startingPos!!.equals(pathData!!.endingPos) && endingPos!!.equals(pathData.startingPos)

    open fun getRailProduct(): UUID? {
        if (railProduct == null) railProduct = getRailProduct(startingPos, endingPos)
        return railProduct
    }

    companion object {
        private const val KEY_RAIL = "rail"
        private const val KEY_SAVED_RAIL_BASE_ID = "saved_rail_base_id"
        private const val KEY_DWELL_TIME = "dwell_time"
        private const val KEY_STOP_INDEX = "stop_index"
        private const val KEY_STARTING_POS = "starting_pos"
        private const val KEY_ENDING_POS = "ending_pos"

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getRailProduct(startingPos: BlockPos?, endingPos: BlockPos?): UUID {
            val start = startingPos!!.asLong()
            val end = endingPos!!.asLong()
            return UUID(Math.min(start, end), Math.max(start, end))
        }
    }
}
