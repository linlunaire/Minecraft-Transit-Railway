package mtr.sound

import mtr.data.TrainClient
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

abstract class TrainSoundBase {
    abstract fun createTrainInstance(train: TrainClient?): TrainSoundBase
    abstract fun playNearestCar(world: Level?, pos: BlockPos?, carIndex: Int)
    abstract fun playAllCars(world: Level?, pos: BlockPos?, carIndex: Int)
    abstract fun playAllCarsDoorOpening(world: Level?, pos: BlockPos?, carIndex: Int)
}
