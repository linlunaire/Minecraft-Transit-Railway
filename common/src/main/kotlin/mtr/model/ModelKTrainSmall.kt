package mtr.model

import mtr.client.DoorAnimationType

open class ModelKTrainSmall : ModelKTrain {
    constructor(isTcl: Boolean) : super(isTcl)

    private constructor(
        isTcl: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isTcl, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelKTrainSmall {
        return ModelKTrainSmall(isTcl, doorAnimationType, renderDoorOverlay)
    }

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
