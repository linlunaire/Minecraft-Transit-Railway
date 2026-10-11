@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package mtr.sound.bve

import mtr.mappings.RegistryUtilities
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent
import org.apache.commons.lang3.StringUtils
import java.util.Locale

open class ConfigFile(textContent: String?, config: BveTrainSoundConfig?) {
    @JvmField val run: Array<SoundEvent?> = arrayOfNulls(1)
    @JvmField val flange: Array<SoundEvent?> = arrayOfNulls(1)
    @JvmField val motor: Array<SoundEvent?> = arrayOfNulls(40)
    @JvmField val joint: Array<SoundEvent?> = arrayOfNulls(1)

    @JvmField val air: SoundEvent?
    @JvmField val airZero: SoundEvent?
    @JvmField val airHigh: SoundEvent?
    @JvmField val brakeEmergency: SoundEvent?
    @JvmField val doorOpen: SoundEvent?
    @JvmField val doorClose: SoundEvent?
    @JvmField val brakeHandleApply: SoundEvent?
    @JvmField val brakeHandleRelease: SoundEvent?
    @JvmField val compressorAttack: SoundEvent?
    @JvmField val compressorLoop: SoundEvent?
    @JvmField val compressorRelease: SoundEvent?
    @JvmField val noise: SoundEvent?
    @JvmField val shoe: SoundEvent?

    @JvmField val motorNoiseDataType: Int
    @JvmField val motorVolumeMultiply: Float
    @JvmField val breakerDelay: Float
    @JvmField val regenerationLimit: Float
    @JvmField val motorOutputAtCoast: Float

    @JvmField val mrPressMin: Int = 700 // kPa
    @JvmField val mrPressMax: Int = 800 // kPa
    @JvmField val mrCompressorSpeed: Float = 5F // kPa/s
    @JvmField val mrServiceBrakeReduce: Float = 5F // kPa each time
    @JvmField val doorCloseSoundLength: Float

    init {
        val lines = (textContent!! as java.lang.String).split("[\\r\\n]+")
        var section = ""
        var air: SoundEvent? = null
        var airZero: SoundEvent? = null
        var airHigh: SoundEvent? = null
        var brakeEmergency: SoundEvent? = null
        var doorOpen: SoundEvent? = null
        var doorClose: SoundEvent? = null
        var brakeHandleApply: SoundEvent? = null
        var brakeHandleRelease: SoundEvent? = null
        var compressorAttack: SoundEvent? = null
        var compressorLoop: SoundEvent? = null
        var compressorRelease: SoundEvent? = null
        var noise: SoundEvent? = null
        var shoe: SoundEvent? = null
        var motorNoiseDataType = 5 // 4 or 5
        var motorVolumeMultiply = 1F
        var breakerDelay = 0F
        var regenerationLimit = 0F // m/s
        var motorOutputAtCoast = 0.4F
        var doorCloseSoundLength = 1F

        for (line in lines) {
            val trimLine = (line.trim { it <= ' ' } as java.lang.String).replaceAll("\\s*(;|#|//).+", "")
            if (StringUtils.isEmpty(trimLine)) continue
            if (trimLine.contains("=")) {
                val tokens = (trimLine as java.lang.String).split("=")
                if (tokens.size != 2) continue
                val key = (tokens[0].trim { it <= ' ' }.lowercase(Locale.ENGLISH) as java.lang.String).replaceAll("\\s", "")
                val value = (tokens[1].trim { it <= ' ' }.lowercase(Locale.ENGLISH).replace("\\", "/") as java.lang.String)
                    .replaceAll("\\.wav|\\s|.+/", "")
                if (StringUtils.isEmpty(value)) continue
                val valueAsSoundEvent = RegistryUtilities.createSoundEvent(Identifier.parse(config!!.audioBaseName + value))
                when (section) {
                    "mtr" -> when (key) {
                        "motornoisedatatype" -> motorNoiseDataType = Integer.parseInt(value)
                        "motorvolumemultiply" -> motorVolumeMultiply = java.lang.Float.parseFloat(value)
                        "doorclosesoundlength" -> doorCloseSoundLength = java.lang.Float.parseFloat(value)
                        "breakerdelay" -> breakerDelay = java.lang.Float.parseFloat(value)
                        "regenerationlimit" -> regenerationLimit = java.lang.Float.parseFloat(value) / 3.6F
                        "motoroutputatcoast" -> motorOutputAtCoast = java.lang.Float.parseFloat(value)
                    }
                    "run", "rolling" -> {
                        if (Integer.parseInt(key) < run.size) run[Integer.parseInt(key)] = valueAsSoundEvent
                    }
                    "flange" -> {
                        if (Integer.parseInt(key) < flange.size) flange[Integer.parseInt(key)] = valueAsSoundEvent
                    }
                    "motor" -> {
                        if (Integer.parseInt(key) < motor.size) motor[Integer.parseInt(key)] = valueAsSoundEvent
                    }
                    "joint", "switch" -> {
                        if (Integer.parseInt(key) < joint.size) joint[Integer.parseInt(key)] = valueAsSoundEvent
                    }
                    "brake" -> when (key) {
                        "bcrelease" -> air = valueAsSoundEvent
                        "bcreleasefull" -> airZero = valueAsSoundEvent
                        "bcreleasehigh" -> airHigh = valueAsSoundEvent
                        "emergency" -> brakeEmergency = valueAsSoundEvent
                    }
                    "door" -> {
                        when (key) {
                            "open", "openleft", "openright" -> doorOpen = valueAsSoundEvent
                            "close", "closeleft", "closeright" -> doorClose = valueAsSoundEvent
                        }
                        // Preserve the Java switch's fall-through into brakehandle.
                        when (key) {
                            "apply" -> brakeHandleApply = valueAsSoundEvent
                            "release" -> brakeHandleRelease = valueAsSoundEvent
                        }
                    }
                    "brakehandle" -> when (key) {
                        "apply" -> brakeHandleApply = valueAsSoundEvent
                        "release" -> brakeHandleRelease = valueAsSoundEvent
                    }
                    "compressor" -> {
                        when (key) {
                            "attack" -> compressorAttack = valueAsSoundEvent
                            "loop" -> compressorLoop = valueAsSoundEvent
                            "release" -> compressorRelease = valueAsSoundEvent
                        }
                        // Preserve the Java switch's fall-through into others.
                        when (key) {
                            "noise" -> noise = valueAsSoundEvent
                            "shoe" -> shoe = valueAsSoundEvent
                        }
                    }
                    "others" -> when (key) {
                        "noise" -> noise = valueAsSoundEvent
                        "shoe" -> shoe = valueAsSoundEvent
                    }
                }
            } else if (trimLine.startsWith("[") && trimLine.endsWith("]")) {
                section = trimLine.substring(1, trimLine.length - 1).trim { it <= ' ' }.replace(" ", "").lowercase(Locale.ENGLISH)
            }
        }

        if (airZero == null) airZero = air
        if (airHigh == null) airHigh = air
        this.air = air
        this.airZero = airZero
        this.airHigh = airHigh
        this.brakeEmergency = brakeEmergency
        this.doorOpen = doorOpen
        this.doorClose = doorClose
        this.brakeHandleApply = brakeHandleApply
        this.brakeHandleRelease = brakeHandleRelease
        this.compressorAttack = compressorAttack
        this.compressorLoop = compressorLoop
        this.compressorRelease = compressorRelease
        this.noise = noise
        this.shoe = shoe
        this.motorNoiseDataType = motorNoiseDataType
        this.motorVolumeMultiply = motorVolumeMultiply
        this.breakerDelay = breakerDelay
        this.regenerationLimit = regenerationLimit
        this.motorOutputAtCoast = motorOutputAtCoast
        this.doorCloseSoundLength = doorCloseSoundLength
    }
}
