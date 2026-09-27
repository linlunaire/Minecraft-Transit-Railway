@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package mtr.sound.bve

import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.Mth
import org.apache.commons.lang3.StringUtils
import java.util.ArrayList
import java.util.Locale

open class MotorData4(manager: ResourceManager?, baseName: String?) : MotorDataBase() { // 4 for BVE4 and OpenBVE
    private val channels = Array(4) { Channel() }
    private val soundCount: Int

    init {
        val textContent = BveTrainSoundConfig.readResource(manager, Identifier.parse("$baseName/train.dat"))
        val lines = (textContent as java.lang.String).split("[\\r\\n]+")
        var section = ""
        for (line in lines) {
            val lineTrim = line.trim { it <= ' ' }.lowercase(Locale.ENGLISH)
            if (StringUtils.isEmpty(lineTrim)) continue
            if (lineTrim.startsWith("#")) {
                section = lineTrim.substring(1).trim { it <= ' ' }.lowercase(Locale.ENGLISH)
                continue
            }
            when (section) {
                "motor_p1", "motor_p2", "motor_b1", "motor_b2" -> {
                    val listIndex = (if (section[6] == 'p') 0 else 2) + (if (section[7] == '1') 0 else 1)
                    val tokens = (lineTrim as java.lang.String).split(",")
                    channels[listIndex].soundIds!!.add(Integer.parseInt(tokens[0]))
                    channels[listIndex].pitches!!.add(java.lang.Float.parseFloat(tokens[1]) / 100F)
                    channels[listIndex].volumes!!.add(java.lang.Float.parseFloat(tokens[2]) / 128F)
                    channels[listIndex].maxSoundId = Math.max(channels[listIndex].maxSoundId, Integer.parseInt(tokens[0]))
                    channels[listIndex].maxEntryId++
                }
            }
        }
        var maxSoundId = -1
        for (channel in channels) maxSoundId = Math.max(maxSoundId, channel.maxSoundId)
        soundCount = maxSoundId + 1
    }

    override fun getSoundCount(): Int = soundCount

    override fun getPitch(index: Int, speed: Float, power: Float): Float {
        if (power == 0F) return 0F
        val offset = if (power > 0F) 0 else 2
        val entryIndex = (speed / 0.2F).toInt()
        if (index == getSafe(channels[offset].soundIds!!, Math.min(channels[offset].maxEntryId, entryIndex))!!) {
            return getSafe(channels[offset].pitches!!, entryIndex)!!
        }
        if (index == getSafe(channels[offset + 1].soundIds!!, Math.min(channels[offset + 1].maxEntryId, entryIndex))!!) {
            return getSafe(channels[offset + 1].pitches!!, entryIndex)!!
        }
        return 0F
    }

    override fun getVolume(index: Int, speed: Float, power: Float): Float {
        if (power == 0F) return 0F
        val offset = if (power > 0F) 0 else 2
        val entryIndex = (speed / 0.2F).toInt()
        if (index == getSafe(channels[offset].soundIds!!, Math.min(channels[offset].maxEntryId, entryIndex))!!) {
            return getSafe(channels[offset].volumes!!, entryIndex)!! * Math.abs(power)
        }
        if (index == getSafe(channels[offset + 1].soundIds!!, Math.min(channels[offset + 1].maxEntryId, entryIndex))!!) {
            return getSafe(channels[offset + 1].volumes!!, entryIndex)!! * Math.abs(power)
        }
        return 0F
    }

    open class Channel {
        @JvmField var soundIds: ArrayList<Int?>? = ArrayList()
        @JvmField var pitches: ArrayList<Float?>? = ArrayList()
        @JvmField var volumes: ArrayList<Float?>? = ArrayList()
        @JvmField var maxEntryId: Int = -1
        @JvmField var maxSoundId: Int = -1
    }

    companion object {
        private fun <T> getSafe(list: ArrayList<T>, index: Int): T = list[Mth.clamp(index, 0, list.size - 1)]
    }
}
