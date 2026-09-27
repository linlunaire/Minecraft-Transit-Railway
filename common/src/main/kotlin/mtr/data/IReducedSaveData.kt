package mtr.data

import org.msgpack.core.MessagePacker
import java.io.IOException

interface IReducedSaveData {
	@Throws(IOException::class)
	fun toReducedMessagePack(messagePacker: MessagePacker)

	fun reducedMessagePackLength(): Int
}
