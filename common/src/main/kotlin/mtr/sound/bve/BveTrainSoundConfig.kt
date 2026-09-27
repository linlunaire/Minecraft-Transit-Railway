package mtr.sound.bve

import mtr.mappings.Utilities
import mtr.mappings.UtilitiesClient
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets

open class BveTrainSoundConfig(manager: ResourceManager?, baseName: String?) {
    @JvmField val baseName: String
    @JvmField val audioBaseName: String
    @JvmField val soundCfg: ConfigFile
    @JvmField val motorData: MotorDataBase

    init {
        val baseLocation = Identifier.parse(if (baseName!!.contains(":")) baseName else "mtr:$baseName")
        this.baseName = baseLocation.toString()
        val configBaseName = baseLocation.namespace + ":sounds/" + baseLocation.path
        audioBaseName = baseLocation.namespace + ":" + baseLocation.path + "_"
        soundCfg = ConfigFile(readResource(manager, Identifier.parse("$configBaseName/sound.cfg")), this)
        motorData = if (soundCfg.motorNoiseDataType == 4) MotorData4(manager, configBaseName) else MotorData5(manager, configBaseName)
    }

    companion object {
        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun readResource(manager: ResourceManager?, location: Identifier?): String {
            return try {
                val resources: MutableList<Resource> = UtilitiesClient.getResources(manager, location)
                if (resources.size < 1) "" else IOUtils.toString(Utilities.getInputStream(resources[0]), StandardCharsets.UTF_8)
            } catch (e: Exception) {
                ""
            }
        }
    }
}
