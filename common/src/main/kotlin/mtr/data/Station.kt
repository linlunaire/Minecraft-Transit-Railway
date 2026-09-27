package mtr.data

import io.netty.buffer.Unpooled
import mtr.mappings.CompoundTagMapper
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.function.Consumer

// Java's final class still exported non-final instance methods; retain their JVM flags.
@Suppress("NON_FINAL_MEMBER_IN_FINAL_CLASS")
@JvmSuppressWildcards
class Station : AreaBase {
    @JvmField var zone: Int = 0
    @JvmField val exits: MutableMap<String?, MutableList<String?>?> = HashMap()

    constructor() : super()
    constructor(id: Long) : super(id)

    constructor(map: Map<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        zone = helper.getInt(KEY_ZONE)
        helper.iterateMapValue(KEY_EXITS) { entry ->
            val destinations = ArrayList<String?>(entry.value.asArrayValue().size())
            for (destination in entry.value.asArrayValue()) destinations.add(destination.asStringValue().asString())
            exits[entry.key.asStringValue().asString()] = destinations
        }
    }

    @Deprecated("Legacy NBT save format")
    constructor(compoundTag: CompoundTag?) : super(compoundTag) {
        zone = CompoundTagMapper.getInt(compoundTag, KEY_ZONE)
        val tagExits = compoundTag!!.getCompoundOrEmpty(KEY_EXITS)
        for (parent in tagExits.keySet()) {
            val destinations = ArrayList<String?>()
            val tagDestinations = tagExits.getCompoundOrEmpty(parent)
            for (destination in tagDestinations.keySet()) destinations.add(CompoundTagMapper.getString(tagDestinations, destination))
            exits[parent] = destinations
        }
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        zone = packet!!.readInt()
        val exitCount = packet.readInt()
        for (i in 0 until exitCount) {
            val parent = packet.readUtf(PACKET_STRING_READ_LENGTH)
            val destinations = ArrayList<String?>()
            val destinationCount = packet.readInt()
            for (j in 0 until destinationCount) destinations.add(packet.readUtf(PACKET_STRING_READ_LENGTH))
            exits[parent] = destinations
        }
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_ZONE).packInt(zone)
        messagePacker.packString(KEY_EXITS)
        messagePacker.packMapHeader(exits.size)
        for ((parent, destinations) in exits) {
            messagePacker.packString(parent)
            messagePacker.packArrayHeader(destinations!!.size)
            for (destination in destinations) messagePacker.packString(destination)
        }
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 2

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        packet.writeInt(zone)
        packet.writeInt(exits.size)
        exits.forEach { (parent, destinations) ->
            packet.writeUtf(parent!!)
            packet.writeInt(destinations!!.size)
            destinations.forEach { packet.writeUtf(it!!) }
        }
    }

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        when (key!!) {
            KEY_EXIT_EDIT_PARENT -> {
                val oldParent = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
                val newParent = packet.readUtf(PACKET_STRING_READ_LENGTH)
                setExitParent(oldParent, newParent)
            }
            KEY_EXIT_DELETE_PARENT -> exits.remove(packet!!.readUtf(PACKET_STRING_READ_LENGTH))
            KEY_EXIT_DESTINATIONS -> {
                val parent = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
                if (parentExists(parent)) {
                    exits[parent]!!.clear()
                    val destinationCount = packet.readInt()
                    for (i in 0 until destinationCount) exits[parent]!!.add(packet.readUtf(PACKET_STRING_READ_LENGTH))
                }
            }
            KEY_ZONE -> {
                name = packet!!.readUtf(PACKET_STRING_READ_LENGTH)
                color = packet.readInt()
                zone = packet.readInt()
            }
            else -> super.update(key, packet)
        }
    }

    override fun hasTransportMode(): Boolean = false

    open fun setZone(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_ZONE)
        packet.writeUtf(name!!)
        packet.writeInt(color)
        packet.writeInt(zone)
        sendPacket!!.accept(packet)
    }

    open fun setExitParent(oldParent: String?, newParent: String?, sendPacket: Consumer<FriendlyByteBuf>?) {
        setExitParent(oldParent, newParent)
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_EXIT_EDIT_PARENT)
        packet.writeUtf(oldParent!!)
        packet.writeUtf(newParent!!)
        sendPacket!!.accept(packet)
    }

    open fun deleteExitParent(parent: String?, sendPacket: Consumer<FriendlyByteBuf>?) {
        exits.remove(parent)
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_EXIT_DELETE_PARENT)
        packet.writeUtf(parent!!)
        sendPacket!!.accept(packet)
    }

    open fun setExitDestinations(parent: String?, sendPacket: Consumer<FriendlyByteBuf>?) {
        if (parentExists(parent)) {
            val packet = FriendlyByteBuf(Unpooled.buffer())
            packet.writeLong(id)
            packet.writeUtf(transportMode!!.toString())
            packet.writeUtf(KEY_EXIT_DESTINATIONS)
            packet.writeUtf(parent!!)
            packet.writeInt(exits[parent]!!.size)
            exits[parent]!!.forEach { packet.writeUtf(it!!) }
            sendPacket!!.accept(packet)
        }
    }

    open fun getGeneratedExits(): MutableMap<String?, MutableList<String?>?> {
        val parents = ArrayList(exits.keys)
        parents.sortWith { first, second -> first!!.compareTo(second!!) }
        val generatedExits = HashMap<String?, MutableList<String?>?>()
        for (parent in parents) {
            val letter = parent!!.substring(0, 1)
            if (!generatedExits.containsKey(letter)) generatedExits[letter] = ArrayList()
            // Retain the legacy alias when a literal prefix ("A") coexists with numbered exits ("A1").
            generatedExits[letter]!!.addAll(exits[parent]!!)
            generatedExits[parent] = exits[parent]
        }
        return generatedExits
    }

    private fun setExitParent(oldParent: String?, newParent: String?) {
        if (parentExists(oldParent)) {
            val existing = exits[oldParent]
            exits.remove(oldParent)
            exits[newParent] = existing ?: ArrayList()
        } else {
            exits[newParent] = ArrayList()
        }
    }

    private fun parentExists(parent: String?): Boolean = parent != null && exits.containsKey(parent)

    companion object {
        private const val KEY_ZONE = "zone"
        private const val KEY_EXITS = "exits"
        private const val KEY_EXIT_EDIT_PARENT = "exit_edit_parent"
        private const val KEY_EXIT_DELETE_PARENT = "exit_delete_parent"
        private const val KEY_EXIT_DESTINATIONS = "exit_destinations"

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun serializeExit(exit: String?): Long {
            var code = 0L
            for (character in exit!!.toCharArray()) {
                code = code shl 8
                code += character.code
            }
            return code
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun deserializeExit(code: Long): String {
            val exit = StringBuilder()
            var charCodes = code
            while (charCodes > 0) {
                exit.insert(0, (charCodes and 0xFF).toInt().toChar())
                charCodes = charCodes shr 8
            }
            return exit.toString()
        }
    }
}
