package mtr.model

import mtr.client.DoorAnimationType

open class ModelMLRSmall : ModelMLR {
    constructor(isChristmas: Boolean) : super(isChristmas)

    private constructor(
        isChristmas: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isChristmas, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelMLRSmall {
        return ModelMLRSmall(isChristmas, doorAnimationType, renderDoorOverlay)
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
