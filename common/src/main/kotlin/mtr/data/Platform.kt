package mtr.data

import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.value.Value
import java.util.function.Consumer

@JvmSuppressWildcards
class Platform : SavedRailBase {
    constructor(id: Long, transportMode: TransportMode?, pos1: BlockPos?, pos2: BlockPos?) : super(id, transportMode, pos1, pos2)
    constructor(transportMode: TransportMode?, pos1: BlockPos?, pos2: BlockPos?) : super(transportMode, pos1, pos2)
    constructor(map: Map<String?, Value?>?) : super(map)

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag)

    constructor(packet: FriendlyByteBuf?) : super(packet)

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        if (KEY_DWELL_TIME == key) {
            name = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
            color = packet.readInt()
            dwellTime = packet.readInt()
            if (transportMode!!.continuousMovement) dwellTime = 1
        } else {
            super.update(key, packet)
        }
    }

    // Preserve the non-final JVM method in the original final Java class.
    @Suppress("NON_FINAL_MEMBER_IN_FINAL_CLASS")
    open fun setDwellTime(newDwellTime: Int, sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_DWELL_TIME)
        packet.writeUtf(name!!)
        packet.writeInt(color)
        writeDwellTimePacket(packet, newDwellTime)
        sendPacket!!.accept(packet)
    }

    private companion object {
        const val KEY_DWELL_TIME = "dwell_time"
    }
}
