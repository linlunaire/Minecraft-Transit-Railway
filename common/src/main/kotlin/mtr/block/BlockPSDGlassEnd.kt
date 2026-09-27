package mtr.block

import mtr.Items
import net.minecraft.world.item.Item

open class BlockPSDGlassEnd(private val style: Int) : BlockPSDAPGGlassEndBase() {
    override fun asItem(): Item {
        return if (style == 0) Items.PSD_GLASS_END_1.get() else Items.PSD_GLASS_END_2.get()
    }
}
