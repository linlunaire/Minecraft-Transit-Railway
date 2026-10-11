@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package mtr.sound.bve

import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceManager
import org.apache.commons.lang3.StringUtils
import java.util.*

open class MotorData5(manager: ResourceManager?, baseName: String?) : MotorDataBase() { // 5 for BVE5 and BVE6
    private val powerVolume = FloatSplines(BveTrainSoundConfig.readResource(manager, Identifier.parse("$baseName/powervol.csv")))
    private val powerFrequency = FloatSplines(BveTrainSoundConfig.readResource(manager, Identifier.parse("$baseName/powerfreq.csv")))
    private val brakeVolume = FloatSplines(BveTrainSoundConfig.readResource(manager, Identifier.parse("$baseName/brakevol.csv")))
    private val brakeFrequency = FloatSplines(BveTrainSoundConfig.readResource(manager, Identifier.parse("$baseName/brakefreq.csv")))
    private val soundCount = Math.max(
        Math.max(powerVolume.data!!.size, powerFrequency.data!!.size),
        Math.max(brakeVolume.data!!.size, brakeFrequency.data!!.size)
    )

    override fun getSoundCount(): Int = soundCount

    override fun getPitch(index: Int, speed: Float, power: Float): Float {
        if (power == 0F) return 0F
        return if (power > 0F) powerFrequency.getValue(index, speed) else brakeFrequency.getValue(index, speed)
    }

    override fun getVolume(index: Int, speed: Float, power: Float): Float {
        if (power == 0F) return 0F
        return (if (power > 0F) powerVolume.getValue(index, speed) else brakeVolume.getValue(index, speed)) * Math.abs(power)
    }

    open class FloatSplines(textContent: String?) {
        @JvmField var data: MutableList<TreeMap<Float, Float?>?>? = ArrayList()

        init {
            val lines = (textContent!! as java.lang.String).split("[\\r\\n]+")
            for (line in lines) {
                val lineTrim = line.trim { it <= ' ' }.lowercase(Locale.ENGLISH)
                if (StringUtils.isEmpty(lineTrim)) continue
                if (lineTrim.startsWith("#") || lineTrim.startsWith("//") || lineTrim.startsWith("bvets")) continue
                val tokens = (lineTrim as java.lang.String).split(",") // Trailing entries automatically removed
                while (data!!.size < tokens.size - 1) data!!.add(TreeMap())
                val key = java.lang.Float.parseFloat(tokens[0].trim { it <= ' ' })
                for (i in 1 until tokens.size) {
                    val tokenTrim = tokens[i].trim { it <= ' ' }
                    if (tokenTrim.isEmpty()) continue
                    data!![i - 1]!![key] = java.lang.Float.parseFloat(tokenTrim)
                }
            }
        }

        open fun getValue(index: Int, key: Float): Float {
            val spline = data!![index]!!
            if (spline.size < 1) return 0F
            val floorEntry = spline.floorEntry(key)
            val ceilingEntry = spline.ceilingEntry(key)
            return if (floorEntry == null) {
                ceilingEntry!!.value!!
            } else if (ceilingEntry == null) {
                floorEntry.value!!
            } else if (Objects.equals(floorEntry.key, ceilingEntry.key)) {
                floorEntry.value!!
            } else {
                floorEntry.value!! + (ceilingEntry.value!! - floorEntry.value!!) *
                    ((key - floorEntry.key) / (ceilingEntry.key - floorEntry.key))
            }
        }
    }
}
