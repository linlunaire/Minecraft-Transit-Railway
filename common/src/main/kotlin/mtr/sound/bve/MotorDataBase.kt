package mtr.sound.bve

abstract class MotorDataBase {
    abstract fun getSoundCount(): Int
    abstract fun getPitch(index: Int, speed: Float, accel: Float): Float
    abstract fun getVolume(index: Int, speed: Float, accel: Float): Float
}
