package mtr.model

import mtr.client.DoorAnimationType

open class ModelSP1900Mini : ModelSP1900 {
    constructor(isC1141A: Boolean) : super(isC1141A)

    private constructor(isC1141A: Boolean, doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        isC1141A,
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelSP1900 {
        return ModelSP1900Mini(isC1141A, doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(0)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(-32, 32)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-64, 64)
    }
}
