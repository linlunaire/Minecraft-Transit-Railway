package mtr.sound.bve

import mtr.MTRClient
import mtr.client.TrainProperties
import mtr.data.TrainClient
import mtr.sound.TrainLoopingSoundInstance
import mtr.sound.TrainSoundBase
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import java.util.concurrent.ThreadLocalRandom

open class BveTrainSound private constructor(@JvmField val config: BveTrainSoundConfig?, private val train: TrainClient?) : TrainSoundBase() {
    private var accelLastElapsed = 0F
    private var onRouteLastElapsed = false
    private var motorCurrentOutput = 0F
    private var motorBreakerTimer = -1F
    private var mrPress = 0
    private var isCompressorActive = false
    private var isCompressorActiveLastElapsed = false
    private val soundLoopMotor: Array<TrainLoopingSoundInstance?>
    private val soundLoopRun: TrainLoopingSoundInstance?
    private val soundLoopFlange: TrainLoopingSoundInstance?
    private val soundLoopNoise: TrainLoopingSoundInstance?
    private val soundLoopShoe: TrainLoopingSoundInstance?
    private val soundLoopCompressor: TrainLoopingSoundInstance?
    private val bogieRailId: Array<IntArray>

    init {
        if (train == null) {
            soundLoopMotor = arrayOfNulls(0)
            soundLoopRun = null
            soundLoopFlange = null
            soundLoopNoise = null
            soundLoopShoe = null
            soundLoopCompressor = null
            bogieRailId = emptyArray()
        } else {
            bogieRailId = Array(train.trainCars) { IntArray(2) }
            mrPress = ThreadLocalRandom.current().nextInt(config!!.soundCfg.mrPressMin, config.soundCfg.mrPressMax + 1)
            isCompressorActive = ThreadLocalRandom.current().nextInt(0, 20) == 0 // Currently, set to 1/20 at client-side load
            isCompressorActiveLastElapsed = isCompressorActive
            soundLoopRun = if (config.soundCfg.run[0] == null) null else TrainLoopingSoundInstance(config.soundCfg.run[0], train)
            soundLoopFlange = if (config.soundCfg.flange[0] == null) null else TrainLoopingSoundInstance(config.soundCfg.flange[0], train)
            soundLoopNoise = if (config.soundCfg.noise == null) null else TrainLoopingSoundInstance(config.soundCfg.noise, train)
            soundLoopShoe = if (config.soundCfg.shoe == null) null else TrainLoopingSoundInstance(config.soundCfg.shoe, train)
            soundLoopCompressor = if (config.soundCfg.compressorLoop == null) null else TrainLoopingSoundInstance(config.soundCfg.compressorLoop, train)
            soundLoopMotor = arrayOfNulls(config.soundCfg.motor.size)
            var i = 0
            while (i < Math.min(config.soundCfg.motor.size, config.motorData.getSoundCount())) {
                if (config.soundCfg.motor[i] != null) soundLoopMotor[i] = TrainLoopingSoundInstance(config.soundCfg.motor[i], train)
                i++
            }
        }
    }

    constructor(config: BveTrainSoundConfig?) : this(config, null)

    override fun createTrainInstance(train: TrainClient?): TrainSoundBase = BveTrainSound(config, train)

    override fun playNearestCar(world: Level?, pos: BlockPos?, carIndex: Int) {
        if (train == null) return
        val deltaT = MTRClient.getLastFrameDuration() / 20F
        val speed = train.getSpeed() * 20F
        val accel = train.speedChange() / deltaT // TODO sounds weird when coasting or braking
        val speedKph = speed * 3.6F

        // Rolling noise
        if (soundLoopRun != null) soundLoopRun.setData(Math.min(1F, speed * 0.04F), speed * 0.04F, pos)

        // Simulation of circuit breaker in traction controller
        var motorTarget = Math.signum(accel)
        if (motorTarget == 0F && speed != 0F) motorTarget = config!!.soundCfg.motorOutputAtCoast
        if (motorTarget < 0F && speed < config!!.soundCfg.regenerationLimit) {
            motorCurrentOutput = 0F // Regeneration brake cut off below limit speed
            motorBreakerTimer = -1F
        } else if (motorTarget > 0F && speed < 1F) {
            motorCurrentOutput = 1F // Disable delay at startup
            motorBreakerTimer = -1F
        } else if (motorTarget != motorCurrentOutput && motorBreakerTimer < 0F) {
            motorBreakerTimer = 0F
            if (motorTarget != 0F && motorCurrentOutput != 0F) motorCurrentOutput = 0F // Loose behavior but sounds OK
        }
        if (motorBreakerTimer >= 0F) {
            motorBreakerTimer += deltaT
            if (motorBreakerTimer > config!!.soundCfg.breakerDelay) {
                motorBreakerTimer = -1F
                motorCurrentOutput = motorTarget
            }
        }

        // Simulation of main reservoir air compressor
        if (mrPress <= config!!.soundCfg.mrPressMin) {
            isCompressorActive = true
            mrPress = config.soundCfg.mrPressMin
        } else if (mrPress >= config.soundCfg.mrPressMax) {
            isCompressorActive = false
            mrPress = config.soundCfg.mrPressMax
        }
        if (isCompressorActive) mrPress = (mrPress + deltaT * config.soundCfg.mrCompressorSpeed).toInt()
        if (soundLoopCompressor != null) {
            // NOTE: Attack sound playback is not to BVE specification.
            soundLoopCompressor.setData(if (isCompressorActive) 1F else 0F, 1F, pos)
        }
        if (isCompressorActive && !isCompressorActiveLastElapsed) playLocalSound(world, config.soundCfg.compressorAttack, pos)
        else if (!isCompressorActive && isCompressorActiveLastElapsed) playLocalSound(world, config.soundCfg.compressorRelease, pos)

        // Motor noise
        var i = 0
        while (i < config.motorData.getSoundCount()) {
            if (soundLoopMotor[i] != null) {
                soundLoopMotor[i]!!.setData(config.motorData.getVolume(i, speedKph, motorCurrentOutput) * config.soundCfg.motorVolumeMultiply, config.motorData.getPitch(i, speedKph, motorCurrentOutput), pos)
            }
            i++
        }

        // TODO Play flange sounds
        // Flange noise
        if (soundLoopFlange != null) soundLoopFlange.setData(0F, 1F, pos)

        // Brake shoe rubbing noise (below regeneration brake cutoff limit)
        if (soundLoopShoe != null) {
            val shoePitch = 1F / (speed + 1F) + 1F
            var shoeGain = if (speed < config.soundCfg.regenerationLimit && accel < 0F) 1F else 0F
            if (speed.toDouble() < 1.39) {
                val t = (speed * speed).toDouble()
                shoeGain = (shoeGain * (1.5552 * t - 0.746496 * speed * t)).toFloat()
            } else if (speed.toDouble() > 12.5) {
                val t = speed - 12.5
                shoeGain = (shoeGain * (1 / (0.1 * t * t + 1))).toFloat()
            }
            soundLoopShoe.setData(shoeGain, shoePitch, pos)
        }

        // Constant loop noise
        if (soundLoopNoise != null) soundLoopNoise.setData(if (train.getIsOnRoute()) 1F else 0F, 1F, pos)

        // Air brake application and release noise
        if (accelLastElapsed < 0F && accel >= 0F) {
            playLocalSound(world, config.soundCfg.brakeHandleRelease, pos)
            if (speed < config.soundCfg.regenerationLimit) playLocalSound(world, config.soundCfg.airZero, pos)
        } else if (accelLastElapsed <= 0F && accel > 0F && speed.toDouble() < 0.3) {
            playLocalSound(world, config.soundCfg.airHigh, pos)
        } else if (accelLastElapsed >= 0F && accel < 0F) {
            mrPress = (mrPress - config.soundCfg.mrServiceBrakeReduce).toInt()
            playLocalSound(world, config.soundCfg.brakeHandleApply, pos)
        }

        // Emergency brake application after returning to depot
        if (onRouteLastElapsed && !train.getIsOnRoute()) playLocalSound(world, config.soundCfg.brakeEmergency, pos)
        accelLastElapsed = accel
        onRouteLastElapsed = train.getIsOnRoute()
        isCompressorActiveLastElapsed = isCompressorActive
    }

    override fun playAllCars(world: Level?, pos: BlockPos?, carIndex: Int) {
        if (train == null) return
        val trainProperties: TrainProperties? = train.getTrainProperties()
        if (config!!.soundCfg.joint[0] == null || trainProperties!!.bogiePosition == 0F) return
        val bogieOffsetFront: Float
        val bogieOffsetRear: Float
        if (trainProperties.isJacobsBogie) {
            if (carIndex == 0) {
                bogieOffsetFront = train.spacing / 2F - trainProperties.bogiePosition
                bogieOffsetRear = -1F
            } else if (carIndex == train.trainCars - 1) {
                bogieOffsetFront = 0F
                bogieOffsetRear = train.spacing / 2F + trainProperties.bogiePosition
            } else {
                bogieOffsetFront = 0F
                bogieOffsetRear = -1F
            }
        } else {
            bogieOffsetFront = train.spacing / 2F - trainProperties.bogiePosition
            bogieOffsetRear = train.spacing / 2F + trainProperties.bogiePosition
        }
        val pitch = train.getSpeed() * 20F / 12.5F
        val gain = if (pitch < 0.5F) 2F * pitch else 1F
        if (bogieOffsetFront >= 0F) {
            val indexFront = train.getIndex(train.getRailProgress() - train.spacing * carIndex - bogieOffsetFront, false)
            if (indexFront != bogieRailId[carIndex][0]) {
                bogieRailId[carIndex][0] = indexFront
                playLocalSound(world, config.soundCfg.joint[0], pos, gain, pitch)
            }
        }
        if (bogieOffsetRear >= 0F) {
            val indexRear = train.getIndex(train.getRailProgress() - train.spacing * carIndex - bogieOffsetRear, false)
            if (indexRear != bogieRailId[carIndex][1]) {
                bogieRailId[carIndex][1] = indexRear
                playLocalSound(world, config.soundCfg.joint[0], pos, gain, pitch)
            }
        }
    }

    override fun playAllCarsDoorOpening(world: Level?, pos: BlockPos?, carIndex: Int) {
        if (world !is ClientLevel || train == null) return
        val soundEvent = if (train.justOpening() && config!!.soundCfg.doorOpen != null) config.soundCfg.doorOpen
        else if (train.justClosing(config!!.soundCfg.doorCloseSoundLength) && config.soundCfg.doorClose != null) config.soundCfg.doorClose
        else null
        playLocalSound(world, soundEvent, pos)
    }

    companion object {
        private fun playLocalSound(world: Level?, event: SoundEvent?, pos: BlockPos?, gain: Float, pitch: Float) {
            if (event == null) return
            (world as ClientLevel).playLocalSound(javaReference(pos), event, SoundSource.BLOCKS, Math.min(1F, gain), pitch, false)
        }

        private fun playLocalSound(world: Level?, event: SoundEvent?, pos: BlockPos?) {
            if (event == null) return
            (world as ClientLevel).playLocalSound(javaReference(pos), event, SoundSource.BLOCKS, 1F, 1F, false)
        }

        // Preserve argument evaluation and nullable Java forwarding to Minecraft.
        @Suppress("UNCHECKED_CAST")
        private fun <T> javaReference(value: T?): T = value as T
    }
}
