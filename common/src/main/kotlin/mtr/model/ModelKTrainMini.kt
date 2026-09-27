package mtr.model

import mtr.client.DoorAnimationType

open class ModelKTrainMini : ModelKTrain {
    constructor(isTcl: Boolean) : super(isTcl)

    private constructor(
        isTcl: Boolean,
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ) : super(isTcl, doorAnimationType, renderDoorOverlay)

    public override fun createNew(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean): ModelKTrainMini {
        return ModelKTrainMini(isTcl, doorAnimationType, renderDoorOverlay)
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
