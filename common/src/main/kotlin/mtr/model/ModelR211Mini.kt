package mtr.model

import mtr.client.DoorAnimationType

open class ModelR211Mini : ModelR211 {
    constructor(openGangway: Boolean) : super(openGangway)

    private constructor(
        openGangway: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(openGangway, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelR211Mini {
        return ModelR211Mini(openGangway, doorAnimationType, renderDoorOverlay)
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
