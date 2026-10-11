package mtr.data

import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import java.io.IOException

abstract class SerializedDataBase {
	@Throws(IOException::class)
	abstract fun toMessagePack(messagePacker: MessagePacker)

	abstract fun messagePackLength(): Int

	abstract fun writePacket(packet: FriendlyByteBuf)

	companion object {
		const val PACKET_STRING_READ_LENGTH = 32767
	}
}
