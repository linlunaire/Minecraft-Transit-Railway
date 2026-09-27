package mtr.model

import mtr.client.DoorAnimationType

open class ModelATrainSmall : ModelATrain {
    constructor(isAel: Boolean) : super(isAel)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelATrainSmall {
        return ModelATrainSmall(isAel, doorAnimationType, renderDoorOverlay)
    }

    private constructor(
        isAel: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isAel, doorAnimationType, renderDoorOverlay)

    protected override fun getWindowPositions(): IntArray? {
        return intArrayOf(-80, 0, 80)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(-120, -40, 40, 120)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-144, 144)
    }
}
