package mtr.data

import io.netty.buffer.Unpooled
import mtr.Registry
import mtr.packet.IPacket
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.Identifier
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

@JvmSuppressWildcards
open class UpdateNearbyMovingObjects<T : NameColorDataBase?>(
    private val deletePacketId: Identifier?, private val updatePacketId: Identifier?
) : IPacket {
    @JvmField val newDataSetInPlayerRange: MutableMap<Player?, MutableSet<T>?> = HashMap()
    @JvmField val dataSetToSync: MutableSet<T> = HashSet()
    private val dataSetInPlayerRange = HashMap<Player?, MutableSet<T>?>()
    private val serializedData = HashMap<T, ByteArray>()

    open fun startTick() {
        newDataSetInPlayerRange.clear()
        dataSetToSync.clear()
        serializedData.clear()
    }

    open fun tick() {
        dataSetInPlayerRange.forEach { (player, dataSet) ->
            val newDataSet = newDataSetInPlayerRange[player]
            for (data in dataSet!!) {
                if (newDataSet == null || !newDataSet.contains(data)) {
                    val packet = FriendlyByteBuf(Unpooled.buffer())
                    try {
                        if (newDataSet == null) packet.writeInt(0) else {
                            packet.writeInt(newDataSet.size)
                            newDataSet.forEach { packet.writeLong(it!!.id) }
                        }
                        if (packet.readableBytes() <= IPacket.MAX_PACKET_BYTES) {
                            Registry.sendToPlayer(player as ServerPlayer?, deletePacketId, packet)
                        }
                    } finally { packet.release() }
                    break
                }
            }
        }

        newDataSetInPlayerRange.forEach { (player, dataSet) ->
            val oldDataSet = dataSetInPlayerRange[player]
            var packet: FriendlyByteBuf? = null
            try {
                for (data in dataSet!!) {
                    if (dataSetToSync.contains(data) || oldDataSet == null || !oldDataSet.contains(data)) {
                        val bytes = getSerializedData(data)
                        if (bytes.isNotEmpty() && bytes.size < IPacket.MAX_PACKET_BYTES) {
                            val current = packet ?: FriendlyByteBuf(Unpooled.buffer()).also { packet = it }
                            if (current.readableBytes() + bytes.size >= IPacket.MAX_PACKET_BYTES) {
                                Registry.sendToPlayer(player as ServerPlayer?, updatePacketId, current)
                                // Both loaders synchronously snapshot bytes in NetworkUtilities.
                                // Reuse only after that send has completed; no payload retains this buffer.
                                current.clear()
                            }
                            current.writeBytes(bytes)
                        }
                    }
                }
                val remaining = packet
                if (remaining != null && remaining.readableBytes() > 0) {
                    Registry.sendToPlayer(player as ServerPlayer?, updatePacketId, remaining)
                }
            } finally { packet?.release() }
        }

        dataSetInPlayerRange.clear()
        dataSetInPlayerRange.putAll(newDataSetInPlayerRange)
    }

    private fun getSerializedData(data: T): ByteArray {
        serializedData[data]?.let { return it }
        val packet = FriendlyByteBuf(Unpooled.buffer())
        try {
            data!!.writePacket(packet)
            val bytes = ByteArray(packet.readableBytes())
            packet.getBytes(packet.readerIndex(), bytes)
            serializedData[data] = bytes
            return bytes
        } finally { packet.release() }
    }
}
