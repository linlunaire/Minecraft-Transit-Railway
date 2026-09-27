package mtr.sound

import mtr.MTR
import mtr.data.RailwayData
import mtr.mappings.RegistryUtilities
import mtr.mappings.SoundInstanceMapper
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.resources.sounds.TickableSoundInstance
import net.minecraft.client.sounds.SoundManager
import net.minecraft.core.BlockPos
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundSource

open class LoopingSoundInstance(soundId: String?) : SoundInstanceMapper(
    RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, javaReference(soundId))), SoundSource.BLOCKS
), TickableSoundInstance {
    init {
        looping = true
    }

    override fun isStopped(): Boolean = false

    override fun tick() {}

    open fun setPos(pos: BlockPos?, isRemoved: Boolean) {
        if (isRemoved) {
            if (x == pos!!.x.toDouble() && y == pos.y.toDouble() && z == pos.z.toDouble()) {
                x = 0.0
                y = Int.MAX_VALUE.toDouble()
                z = 0.0
            }
        } else {
            val player: LocalPlayer = Minecraft.getInstance().player ?: return
            val playerPos = player.blockPosition()
            val distance = playerPos.distManhattan(javaReference(pos))
            if (distance <= MAX_DISTANCE) {
                val currentDistance = playerPos.distManhattan(RailwayData.newBlockPos(x, y, z))
                if (distance < currentDistance) {
                    x = pos!!.x.toDouble()
                    y = pos.y.toDouble()
                    z = pos.z.toDouble()
                }
                val soundManager: SoundManager? = Minecraft.getInstance().soundManager
                if (soundManager != null && !soundManager.isActive(this)) soundManager.play(this)
            }
        }
    }

    companion object {
        private const val MAX_DISTANCE = 32
        // Leave nullable Java arguments to the original Minecraft call boundary.
        @Suppress("UNCHECKED_CAST")
        private fun <T> javaReference(value: T?): T = value as T
    }
}
