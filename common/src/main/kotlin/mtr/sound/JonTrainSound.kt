package mtr.sound

import mtr.MTR
import mtr.MTRClient
import mtr.data.Train
import mtr.data.TrainClient
import mtr.mappings.RegistryUtilities
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import java.util.Random

open class JonTrainSound private constructor(
    @JvmField val soundId: String?,
    @JvmField val config: JonTrainSoundConfig?,
    private val train: TrainClient?
) : TrainSoundBase() {
    private val random = Random()

    constructor(soundId: String?, config: JonTrainSoundConfig?) : this(soundId, config, null)

    override fun createTrainInstance(train: TrainClient?): TrainSoundBase = JonTrainSound(soundId, config, train)

    override fun playNearestCar(world: Level?, pos: BlockPos?, carIndex: Int) {
        if (!(world is ClientLevel && MTRClient.canPlaySound())) return
        if (config!!.speedSoundCount > 0 && soundId != null) {
            // TODO: Better sound system to adapt to different acceleration
            val referenceAcceleration = if (config.constantPlaybackSpeed) train!!.accelerationConstant else Train.ACCELERATION_DEFAULT
            val floorSpeed = Math.floor((train!!.getSpeed() / referenceAcceleration / MTRClient.TICKS_PER_SPEED_SOUND).toDouble()).toInt()
            if (floorSpeed > 0) {
                if (floorSpeed >= 30 && random.nextInt(RANDOM_SOUND_CHANCE) == 0) {
                    world.playLocalSound(javaReference(pos), RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, soundId + SOUND_RANDOM)), SoundSource.BLOCKS, 10F, 1F, false)
                }
                val index = Math.min(floorSpeed, config.speedSoundCount) - 1
                val isAccelerating = if (train.speedChange() == 0F) config.useAccelerationSoundsWhenCoasting || random.nextBoolean() else train.speedChange() > 0F
                val speedSoundId = soundId + (if (isAccelerating) SOUND_ACCELERATION else SOUND_DECELERATION) + index / SOUND_GROUP_SIZE + SOUND_GROUP_LETTERS[index % SOUND_GROUP_SIZE]
                world.playLocalSound(javaReference(pos), RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, speedSoundId)), SoundSource.BLOCKS, 1F, 1F, false)
            }
        }
    }

    override fun playAllCars(world: Level?, pos: BlockPos?, carIndex: Int) {}

    override fun playAllCarsDoorOpening(world: Level?, pos: BlockPos?, carIndex: Int) {
        if (world is ClientLevel && config!!.doorSoundBaseId != null) {
            val soundId = if (train!!.justOpening()) config.doorSoundBaseId + SOUND_DOOR_OPEN
            else if (train.justClosing(config.doorCloseSoundTime)) config.doorSoundBaseId + SOUND_DOOR_CLOSE
            else null
            if (soundId != null) {
                world.playLocalSound(javaReference(pos), RegistryUtilities.createSoundEvent(Identifier.fromNamespaceAndPath(MTR.MOD_ID, soundId)), SoundSource.BLOCKS, 1F, 1F, false)
            }
        }
    }

    open class JonTrainSoundConfig(
        @JvmField val doorSoundBaseId: String?,
        @JvmField val speedSoundCount: Int,
        @JvmField val doorCloseSoundTime: Float,
        @JvmField val useAccelerationSoundsWhenCoasting: Boolean,
        @JvmField val constantPlaybackSpeed: Boolean
    ) {
        constructor(doorSoundBaseId: String?, speedSoundCount: Int, doorCloseSoundTime: Float, useAccelerationSoundsWhenCoasting: Boolean) :
            this(doorSoundBaseId, speedSoundCount, doorCloseSoundTime, useAccelerationSoundsWhenCoasting, false)
    }

    companion object {
        private val SOUND_GROUP_LETTERS = charArrayOf('a', 'b', 'c')
        private val SOUND_GROUP_SIZE = SOUND_GROUP_LETTERS.size
        private const val SOUND_ACCELERATION = "_acceleration_"
        private const val SOUND_DECELERATION = "_deceleration_"
        private const val SOUND_DOOR_OPEN = "_door_open"
        private const val SOUND_DOOR_CLOSE = "_door_close"
        private const val SOUND_RANDOM = "_random"
        private const val RANDOM_SOUND_CHANCE = 300
        // Preserve argument evaluation and nullable Java forwarding to Minecraft.
        @Suppress("UNCHECKED_CAST")
        private fun <T> javaReference(value: T?): T = value as T
    }
}
