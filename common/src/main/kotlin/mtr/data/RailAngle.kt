package mtr.data

// Enum entries are constructed before their companion exists.
private fun normalizeRailAngle(angleDegrees: Float): Float {
    var additional = 0
    while (angleDegrees + additional < -180F) additional += 360
    while (angleDegrees + additional >= 180F) additional -= 360
    return angleDegrees + additional
}

@Suppress("NON_FINAL_MEMBER_IN_FINAL_CLASS")
enum class RailAngle(angleDegrees: Float) {
    E(0F), SEE(22.5F), SE(45F), SSE(67.5F), S(90F), SSW(112.5F), SW(135F), SWW(157.5F),
    W(180F), NWW(202.5F), NW(225F), NNW(247.5F), N(270F), NNE(292.5F), NE(315F), NEE(337.5F);

    @JvmField val angleDegrees: Float = normalizeRailAngle(angleDegrees)
    @JvmField val angleRadians: Double = Math.toRadians(this.angleDegrees.toDouble())
    @JvmField val sin: Double = Math.sin(angleRadians)
    @JvmField val cos: Double = Math.cos(angleRadians)
    @JvmField val tan: Double = Math.tan(angleRadians)
    @JvmField val halfTan: Double = Math.tan(angleRadians / 2)

    open fun getOpposite(): RailAngle = when (this) {
        E -> W
        SEE -> NWW
        SE -> NW
        SSE -> NNW
        S -> N
        SSW -> NNE
        SW -> NE
        SWW -> NEE
        W -> E
        NWW -> SEE
        NW -> SE
        NNW -> SSE
        N -> S
        NNE -> SSW
        NE -> SW
        NEE -> SWW
    }

    open fun add(railAngle: RailAngle?): RailAngle = fromAngle(angleDegrees + railAngle!!.angleDegrees)
    open fun sub(railAngle: RailAngle?): RailAngle = fromAngle(angleDegrees - railAngle!!.angleDegrees)
    open fun isParallel(railAngle: RailAngle?): Boolean = this === railAngle || this === railAngle!!.getOpposite()
    open fun similarFacing(newAngleDegrees: Float): Boolean = similarFacing(angleDegrees, newAngleDegrees)

    companion object {
        private const val DEGREES_IN_CIRCLE = 360
        private val QUADRANTS = entries.size
        private val ANGLE_INCREMENT = DEGREES_IN_CIRCLE.toFloat() / QUADRANTS

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun similarFacing(angleDegrees1: Float, angleDegrees2: Float): Boolean =
            Math.abs(normalizeRailAngle(angleDegrees1 - angleDegrees2)) < DEGREES_IN_CIRCLE / 4F

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun getQuadrant(angleDegrees: Float, include225: Boolean): Int {
            val factor = if (include225) 1 else 2
            return Math.round((normalizeRailAngle(angleDegrees) + DEGREES_IN_CIRCLE) / ANGLE_INCREMENT / factor) % (QUADRANTS / factor)
        }

        @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
        @JvmStatic
        open fun fromAngle(angleDegrees: Float): RailAngle = entries[getQuadrant(angleDegrees, true)]
    }
}
