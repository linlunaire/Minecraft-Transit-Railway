package mtr.mappings

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier
import net.minecraft.world.level.block.state.BlockState
import java.util.Collections

/** Keeps inherited addon blocks valid without accepting blocks with a different entity factory. */
internal class BlockEntityTypeMapper<T : BlockEntityMapper>(supplier: BlockEntitySupplier<out T>, block: Block) :
	BlockEntityType<T>(supplier, Collections.singleton(block)) {

	private val inheritedFactories: ClassValue<Boolean>

	init {
		val originalClass = block.javaClass
		inheritedFactories = object : ClassValue<Boolean>() {
			override fun computeValue(candidateClass: Class<*>): Boolean {
				if (candidateClass == originalClass || !originalClass.isAssignableFrom(candidateClass) || block !is EntityBlockMapper) {
					return false
				}
				return try {
					originalClass.getMethod("createBlockEntity", BlockPos::class.java, BlockState::class.java) == candidateClass.getMethod("createBlockEntity", BlockPos::class.java, BlockState::class.java) &&
						originalClass.getMethod("newBlockEntity", BlockPos::class.java, BlockState::class.java) == candidateClass.getMethod("newBlockEntity", BlockPos::class.java, BlockState::class.java) &&
						originalClass.getMethod("getType") == candidateClass.getMethod("getType")
				} catch (_: NoSuchMethodException) {
					false
				}
			}
		}
	}

	override fun isValid(state: BlockState): Boolean {
		// 26.2 validates in the entity constructor, during NBT loading and on state
		// changes. Fix the registered type, not just one construction call site.
		return super.isValid(state) || inheritedFactories.get(state.block.javaClass)
	}
}
