package mtr.sound

import mtr.data.TrainClient
import mtr.mappings.TickableSoundInstanceMapper
import net.minecraft.client.Minecraft
import net.minecraft.client.sounds.SoundManager
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource

open class TrainLoopingSoundInstance(event: SoundEvent?, private val train: TrainClient?) : TickableSoundInstanceMapper(event, SoundSource.BLOCKS) {
    init {
        looping = true
        delay = 0
        volume = 0F
        pitch = 1F
    }

    open fun setData(volume: Float, pitch: Float, pos: BlockPos?) {
        this.pitch = pitch
        if (this.pitch == 0F) this.pitch = 1F
        this.volume = volume
        x = pos!!.x.toDouble()
        y = pos.y.toDouble()
        z = pos.z.toDouble()
        val soundManager: SoundManager? = Minecraft.getInstance().soundManager
        if (soundManager != null && !train!!.isRemoved && volume > 0F && !soundManager.isActive(this)) {
            looping = true
            soundManager.play(this)
        } else if (soundManager != null && volume <= 0F && soundManager.isActive(this)) {
            // Release the channel, not the reusable instance, so the next playback starts at the beginning.
            soundManager.stop(this)
        }
    }

    override fun tick() {
        if (train!!.isRemoved) stop()
    }

    override fun canStartSilent(): Boolean = true
    override fun canPlaySound(): Boolean = true
}
