package mtr.block

import mtr.Items
import net.minecraft.world.item.Item

open class BlockPSDGlass(private val style: Int) : BlockPSDAPGGlassBase() {
    override fun asItem(): Item {
        return if (style == 0) Items.PSD_GLASS_1.get() else Items.PSD_GLASS_2.get()
    }
}
