package mtr.model

import mtr.client.DoorAnimationType

open class ModelCTrainMini : ModelCTrain {
    constructor() : super()

    private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelCTrainMini {
        return ModelCTrainMini(doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(0)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(-40, 40)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-64, 64)
    }
}
