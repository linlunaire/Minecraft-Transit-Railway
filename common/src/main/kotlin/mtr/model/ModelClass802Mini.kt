package mtr.model

import mtr.client.DoorAnimationType
import mtr.mappings.ModelMapper

open class ModelClass802Mini : ModelClass802 {
    constructor() : super()

    private constructor(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) : super(
        doorAnimationType,
        renderDoorOverlay
    )

    public override fun createNew(
        doorAnimationType: DoorAnimationType?,
        renderDoorOverlay: Boolean
    ): ModelClass802Mini {
        return ModelClass802Mini(doorAnimationType, renderDoorOverlay)
    }

    protected override fun getDoorPositions(): IntArray? {
        return intArrayOf(-105, 105)
    }

    protected override fun getEndPositions(): IntArray? {
        return intArrayOf(-105, 105)
    }

    protected override val bogiePositions: IntArray
        get() = intArrayOf(-79, 79)

    protected override fun renderFirstDestination(isEnd1Head: Boolean, isEnd2Head: Boolean): Boolean {
        return !isEnd2Head
    }

    protected override fun renderSecondDestination(isEnd1Head: Boolean, isEnd2Head: Boolean): Boolean {
        return !isEnd1Head
    }

    protected override fun windowParts(): Array<ModelMapper?> {
        return windowPartsMini()
    }

    protected override fun windowEndParts(): Array<ModelMapper?> {
        return windowEndPartsMini()
    }
}
