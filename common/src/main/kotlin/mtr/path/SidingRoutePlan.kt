package mtr.path

import mtr.data.Rail
import mtr.data.SavedRailBase
import net.minecraft.core.BlockPos

/**
 * Computes a siding's approach, main route, return path and repeat indices.
 *
 * This Module does not update a siding, integrate its timetable or schedule publication.
 * The result owns its list, but retains main-path elements and rail/platform references
 * exactly as the search does. It is not an immutable world snapshot or a cancellation token.
 */
@JvmSuppressWildcards
internal object SidingRoutePlan {
    class Result internal constructor(
        @JvmField val path: MutableList<PathData?>,
        @JvmField val successfulSegments: Int,
        @JvmField val repeatIndex1: Int,
        @JvmField val repeatIndex2: Int
    )

    @JvmStatic
    fun compute(
        siding: SavedRailBase?, mainPath: MutableList<PathData?>?, successfulSegmentsMain: Int,
        rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?, firstPlatform: SavedRailBase?,
        lastPlatform: SavedRailBase?, repeatInfinitely: Boolean, cruisingAltitude: Int, useFastSpeed: Boolean
    ): Result {
        val path = ArrayList<PathData?>()
        if (firstPlatform == null || lastPlatform == null) return Result(path, 0, 0, 0)

        PathFinder.findPath(path, rails, endpoints(siding, firstPlatform), 0, cruisingAltitude, useFastSpeed)
        if (path.isEmpty()) return Result(path, 1, 0, 0)
        if (mainPath!!.isEmpty()) {
            path.clear()
            return Result(path, successfulSegmentsMain + 1, 0, 0)
        }

        val repeatIndex1 = if (repeatInfinitely) {
            path.size - if (path[path.size - 1]!!.isOppositeRail(mainPath[0])) 0 else 1
        } else 0
        PathFinder.appendPath(path, mainPath)

        val returnPath = ArrayList<PathData?>()
        PathFinder.findPath(returnPath, rails, endpoints(lastPlatform, siding), successfulSegmentsMain, cruisingAltitude, useFastSpeed)
        if (returnPath.isEmpty()) {
            path.clear()
            // The legacy result retains its first repeat index even if the return route fails.
            return Result(path, successfulSegmentsMain + 1, repeatIndex1, 0)
        }
        val repeatIndex2 = if (repeatInfinitely) path.size - 1 else 0
        PathFinder.appendPath(path, returnPath)
        return Result(path, successfulSegmentsMain + 2, repeatIndex1, repeatIndex2)
    }

    // Preserve the old Java list's nullable entries: the search, not capture, rejects them.
    @Suppress("UNCHECKED_CAST")
    private fun endpoints(first: SavedRailBase?, last: SavedRailBase?): MutableList<SavedRailBase> {
        val result = ArrayList<SavedRailBase?>(2)
        result.add(first)
        result.add(last)
        return result as MutableList<SavedRailBase>
    }
}
