package mtr.model

import mtr.client.DoorAnimationType

open class ModelLondonUndergroundD78Mini : ModelLondonUndergroundD78 {
    constructor() : super()

    private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ): ModelLondonUndergroundD78Mini {
        return ModelLondonUndergroundD78Mini(doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(0)
    }

    protected override fun getDoorPositions(): IntArray {
        return intArrayOf(-32, 32)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-64, 64)
    }
}
