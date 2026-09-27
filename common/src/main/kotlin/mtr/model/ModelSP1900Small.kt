package mtr.model

import mtr.client.DoorAnimationType

open class ModelSP1900Small : ModelSP1900 {
    constructor(isC1141A: Boolean) : super(isC1141A)

    private constructor(isC1141A: Boolean, doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        isC1141A,
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelSP1900 {
        return ModelSP1900Small(isC1141A, doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(-64, 0, 64)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(-96, -32, 32, 96)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-128, 128)
    }
}
