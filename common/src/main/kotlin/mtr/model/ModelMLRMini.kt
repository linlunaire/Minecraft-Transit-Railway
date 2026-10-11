package mtr.model

import mtr.client.DoorAnimationType

open class ModelMLRMini : ModelMLR {
    constructor(isChristmas: Boolean) : super(isChristmas)

    private constructor(
        isChristmas: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isChristmas, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelMLRMini {
        return ModelMLRMini(isChristmas, doorAnimationType, renderDoorOverlay)
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
