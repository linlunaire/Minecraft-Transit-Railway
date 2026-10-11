package mtr.item

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

open class ItemBlockEnchanted(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun isFoil(itemStack: ItemStack): Boolean {
        return true
    }
}
