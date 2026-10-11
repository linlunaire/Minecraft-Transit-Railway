package mtr.model

import mtr.client.DoorAnimationType

open class ModelATrainMini : ModelATrain {
    constructor(isAel: Boolean) : super(isAel)

    private constructor(
        isAel: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isAel, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelATrainMini {
        return ModelATrainMini(isAel, doorAnimationType, renderDoorOverlay)
    }

    protected override fun getWindowPositions(): IntArray? {
        return if (isAel) intArrayOf(-93, -67, -41, 41, 67, 93) else intArrayOf(0)
    }

    protected override fun getDoorPositions(): IntArray? {
        return if (isAel) intArrayOf(0) else intArrayOf(-40, 40)
    }

    protected override fun getEndPositions(): IntArray? {
        return if (isAel) intArrayOf(-104, 104) else intArrayOf(-64, 64)
    }
}
