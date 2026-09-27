package mtr.data

import io.netty.buffer.Unpooled
import mtr.mappings.CompoundTagMapper
import mtr.mappings.Tuple
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.function.Consumer

@JvmSuppressWildcards
abstract class AreaBase : NameColorDataBase {
    @JvmField var corner1: Tuple<Int, Int>? = null
    @JvmField var corner2: Tuple<Int, Int>? = null

    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(transportMode: TransportMode?) : super(transportMode)
    constructor(id: Long, transportMode: TransportMode?) : super(id, transportMode)

    constructor(map: Map<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        setCorners(helper.getInt(KEY_X_MIN), helper.getInt(KEY_Z_MIN), helper.getInt(KEY_X_MAX), helper.getInt(KEY_Z_MAX))
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag) {
        setCorners(CompoundTagMapper.getInt(compoundTag, KEY_X_MIN), CompoundTagMapper.getInt(compoundTag, KEY_Z_MIN),
            CompoundTagMapper.getInt(compoundTag, KEY_X_MAX), CompoundTagMapper.getInt(compoundTag, KEY_Z_MAX))
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        setCorners(packet!!.readInt(), packet.readInt(), packet.readInt(), packet.readInt())
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_X_MIN).packInt(if (corner1 == null) 0 else corner1!!.a)
        messagePacker.packString(KEY_Z_MIN).packInt(if (corner1 == null) 0 else corner1!!.b)
        messagePacker.packString(KEY_X_MAX).packInt(if (corner2 == null) 0 else corner2!!.a)
        messagePacker.packString(KEY_Z_MAX).packInt(if (corner2 == null) 0 else corner2!!.b)
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 4

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        writeCorners(packet)
    }

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        if (key!!.equals(KEY_CORNERS)) {
            setCorners(packet!!.readInt(), packet.readInt(), packet.readInt(), packet.readInt())
        } else {
            super.update(key, packet)
        }
    }

    open fun setCorners(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_CORNERS)
        writeCorners(packet)
        sendPacket!!.accept(packet)
    }

    open fun inArea(x: Int, z: Int): Boolean = nonNullCorners(this) &&
        RailwayData.isBetween(x.toDouble(), corner1!!.a.toDouble(), corner2!!.a.toDouble()) &&
            RailwayData.isBetween(z.toDouble(), corner1!!.b.toDouble(), corner2!!.b.toDouble())

    open fun intersecting(areaBase: AreaBase?): Boolean =
        nonNullCorners(this) && nonNullCorners(areaBase) && (inThis(areaBase!!) || areaBase.inThis(this))

    open fun getCenter(): BlockPos? = if (nonNullCorners(this))
        RailwayData.newBlockPos((corner1!!.a + corner2!!.a) / 2, 0, (corner1!!.b + corner2!!.b) / 2) else null

    private fun setCorners(corner1a: Int, corner1b: Int, corner2a: Int, corner2b: Int) {
        corner1 = if (corner1a == 0 && corner1b == 0) null else Tuple(corner1a, corner1b)
        corner2 = if (corner2a == 0 && corner2b == 0) null else Tuple(corner2a, corner2b)
    }

    private fun writeCorners(packet: FriendlyByteBuf) {
        packet.writeInt(if (corner1 == null) 0 else corner1!!.a)
        packet.writeInt(if (corner1 == null) 0 else corner1!!.b)
        packet.writeInt(if (corner2 == null) 0 else corner2!!.a)
        packet.writeInt(if (corner2 == null) 0 else corner2!!.b)
    }

    private fun inThis(areaBase: AreaBase): Boolean =
        inArea(areaBase.corner1!!.a, areaBase.corner1!!.b) || inArea(areaBase.corner1!!.a, areaBase.corner2!!.b) ||
            inArea(areaBase.corner2!!.a, areaBase.corner1!!.b) || inArea(areaBase.corner2!!.a, areaBase.corner2!!.b)

    companion object {
        private const val KEY_X_MIN = "x_min"
        private const val KEY_Z_MIN = "z_min"
        private const val KEY_X_MAX = "x_max"
        private const val KEY_Z_MAX = "z_max"
        private const val KEY_CORNERS = "corners"

        // Preserve Java subclasses' ability to hide this non-final static entry point.
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun nonNullCorners(station: AreaBase?): Boolean = station != null && station.corner1 != null && station.corner2 != null
    }
}
