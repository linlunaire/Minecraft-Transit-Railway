package mtr.data

import io.netty.buffer.Unpooled
import mtr.mappings.CompoundTagMapper
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.Locale
import java.util.Random
import java.util.function.Consumer

@JvmSuppressWildcards
abstract class NameColorDataBase : SerializedDataBase, Comparable<NameColorDataBase?> {
    @JvmField val id: Long
    @JvmField val transportMode: TransportMode?
    @JvmField var name: String?
    @JvmField var color: Int = 0

    constructor() : this(0)
    constructor(id: Long) : this(id, TransportMode.TRAIN)
    constructor(transportMode: TransportMode?) : this(0, transportMode)

    constructor(id: Long, transportMode: TransportMode?) {
        this.id = if (id == 0L) Random().nextLong() else id
        this.transportMode = transportMode
        name = ""
    }

    constructor(map: Map<String?, Value?>?) {
        val helper = MessagePackHelper(map)
        id = helper.getLong(KEY_ID)
        transportMode = EnumHelper.valueOf(TransportMode.TRAIN, helper.getString(KEY_TRANSPORT_MODE))
        name = helper.getString(KEY_NAME)
        color = helper.getInt(KEY_COLOR)
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) {
        id = CompoundTagMapper.getLong(compoundTag, KEY_ID)
        transportMode = EnumHelper.valueOf(TransportMode.TRAIN, CompoundTagMapper.getString(compoundTag, KEY_TRANSPORT_MODE))
        name = CompoundTagMapper.getString(compoundTag, KEY_NAME)
        color = CompoundTagMapper.getInt(compoundTag, KEY_COLOR)
    }

    constructor(packet: FriendlyByteBuf?) {
        id = packet!!.readLong()
        transportMode = EnumHelper.valueOf(TransportMode.TRAIN, packet.readUtf(PACKET_STRING_READ_LENGTH))
        name = packet.readUtf(PACKET_STRING_READ_LENGTH).replace(" |", "|").replace("| ", "|")
        color = packet.readInt()
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        messagePacker.packString(KEY_ID).packLong(id)
        messagePacker.packString(KEY_TRANSPORT_MODE).packString(transportMode!!.toString())
        messagePacker.packString(KEY_NAME).packString(name)
        messagePacker.packString(KEY_COLOR).packInt(color)
    }

    override fun messagePackLength(): Int = 4

    override fun writePacket(packet: FriendlyByteBuf) {
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(name!!)
        packet.writeInt(color)
    }

    open fun update(key: String?, packet: FriendlyByteBuf?) {
        if (key!!.equals(KEY_NAME)) {
            name = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
            color = packet.readInt()
        }
    }

    open fun setNameColor(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_NAME)
        packet.writeUtf(name!!)
        packet.writeInt(color)
        sendPacket?.accept(packet)
    }

    fun isTransportMode(transportMode: TransportMode?): Boolean = !hasTransportMode() || this.transportMode == transportMode

    protected abstract fun hasTransportMode(): Boolean

    override fun compareTo(other: NameColorDataBase?): Int =
        (name!!.lowercase(Locale.ENGLISH) + color).compareTo((other!!.name + other.color).lowercase(Locale.ENGLISH))

    private companion object {
        const val KEY_ID = "id"
        const val KEY_TRANSPORT_MODE = "transport_mode"
        const val KEY_NAME = "name"
        const val KEY_COLOR = "color"
    }
}
