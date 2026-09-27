package mtr.data

import com.google.gson.JsonParser
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CollectionTag
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NumericTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import org.msgpack.core.MessagePack
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.Date

@JvmSuppressWildcards
open class RailwayDataLoggingModule(
    railwayData: RailwayData?, world: Level?, rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?, savePath: Path?
) : RailwayDataModuleBase(railwayData, world, rails) {
    private val logsPath = savePath!!.resolve("logs")
    private val filePath = logsPath.resolve(SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(Date()) + ".csv")
    private val queuedEvents = ArrayList<String>()

    open fun save() {
        if (queuedEvents.isNotEmpty()) {
            try {
                if (!Files.exists(filePath)) {
                    Files.createDirectories(logsPath)
                    queuedEvents.add(0, "Timestamp,Player Name,Player UUID,Class,ID,Name,Position,Change,Old Data,New Data")
                    Files.write(filePath, queuedEvents)
                } else {
                    Files.write(filePath, queuedEvents, StandardOpenOption.APPEND)
                }
                queuedEvents.clear()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun addEvent(player: ServerPlayer?, dataClass: Class<*>?, oldData: MutableList<String?>?, newData: MutableList<String?>?, vararg positions: BlockPos?) {
        addEvent(player, dataClass, 0, "", oldData, newData, *positions)
    }

    open fun addEvent(player: ServerPlayer?, dataClass: Class<*>?, id: Long, name: String?, oldData: MutableList<String?>?, newData: MutableList<String?>?, vararg positions: BlockPos?) {
        val oldDiff: MutableList<String?>
        val newDiff: MutableList<String?>
        if (oldData!!.size == newData!!.size) {
            oldDiff = ArrayList()
            newDiff = ArrayList()
            for (i in oldData.indices) {
                val old = oldData[i]
                val new = newData[i]
                if (!old!!.equals(new)) {
                    oldDiff.add(old)
                    newDiff.add(new)
                }
            }
        } else {
            oldDiff = oldData
            newDiff = newData
        }
        val positionStrings = ArrayList<String>()
        for (pos in positions) if (pos != null) positionStrings.add(java.lang.String.format("(%s, %s, %s)", pos.x, pos.y, pos.z))
        positionStrings.sort()
        if (oldDiff.isNotEmpty() || newDiff.isNotEmpty()) {
            queuedEvents.add(java.lang.String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                formatString(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(Date())),
                formatString(player!!.name.string), formatString(player.uuid.toString()),
                formatString(dataClass!!.name), formatString(if (id == 0L) "" else java.lang.String.format("[%s]", id)),
                formatString(IGui.formatStationName(name)), formatString(java.lang.String.join("\n", positionStrings)),
                formatString((if (oldDiff.isEmpty()) LoggingEditType.CREATE else if (newDiff.isEmpty()) LoggingEditType.DELETE else LoggingEditType.EDIT).toString()),
                formatString(RailwayData.prettyPrint(JsonParser().parse(java.lang.String.format("{%s}", java.lang.String.join(",", oldDiff))))),
                formatString(RailwayData.prettyPrint(JsonParser().parse(java.lang.String.format("{%s}", java.lang.String.join(",", newDiff)))))))
        }
    }

    private enum class LoggingEditType { CREATE, EDIT, DELETE }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun <T : SerializedDataBase?> getData(data: T): MutableList<String> {
            try {
                MessagePack.newDefaultBufferPacker().use { packer ->
                    if (data is IReducedSaveData) data.toReducedMessagePack(packer) else data!!.toMessagePack(packer)
                    try {
                        MessagePack.newDefaultUnpacker(packer.toByteArray()).use { unpacker ->
                            val result = ArrayList<String>()
                            var previous: String? = null
                            while (unpacker.hasNext()) {
                                val next = unpacker.unpackValue().toJson()
                                if (previous == null) previous = next else {
                                    result.add(java.lang.String.format("%s:%s", previous, next))
                                    previous = null
                                }
                            }
                            return result
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ArrayList()
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getData(compoundTag: CompoundTag?): MutableList<String> {
            val result = ArrayList<String>()
            compoundTag!!.keySet().forEach { key ->
                val value = convertTag(compoundTag.get(key))
                if (value != null) result.add(java.lang.String.format("%s:%s", key, value))
            }
            return result
        }

        private fun convertTag(tag: Tag?): String? {
            if (tag is CollectionTag) {
                val values = ArrayList<String?>()
                tag.forEach { values.add(convertTag(it)) }
                return java.lang.String.format("[%s]", java.lang.String.join(",", values))
            } else if (tag != null) {
                val value = tag.asString().orElseGet(tag::toString)
                return when {
                    value == "0b" -> "false"
                    value == "1b" -> "true"
                    tag is NumericTag -> tag.box().toString()
                    value.isEmpty() -> "\"\""
                    else -> value
                }
            }
            return null
        }

        private fun formatString(text: String): String = java.lang.String.format("\"%s\"", text.replace("\"", "\"\""))
    }
}
