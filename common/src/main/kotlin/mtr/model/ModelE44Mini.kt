package mtr.model

import mtr.client.DoorAnimationType

open class ModelE44Mini : ModelE44 {
    constructor() : super()

    private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelE44Mini {
        return ModelE44Mini(doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(-34, 34)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(0)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-62, 62)
    }
}
