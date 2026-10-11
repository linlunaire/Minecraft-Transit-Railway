package mtr.block

import mtr.Items
import net.minecraft.world.item.Item

open class BlockAPGGlassEnd : BlockPSDAPGGlassEndBase() {
    override fun asItem(): Item {
        return Items.APG_GLASS_END.get()
    }
}
