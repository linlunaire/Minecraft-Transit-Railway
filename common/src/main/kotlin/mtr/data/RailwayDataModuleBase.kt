package mtr.data

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

abstract class RailwayDataModuleBase(
	@JvmField protected val railwayData: RailwayData?,
	@JvmField protected val world: Level?,
	@JvmField protected val rails: MutableMap<BlockPos, MutableMap<BlockPos, Rail>>?
)
