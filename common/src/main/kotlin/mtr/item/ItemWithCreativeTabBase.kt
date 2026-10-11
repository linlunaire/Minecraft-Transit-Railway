package mtr.item

import mtr.CreativeModeTabs
import mtr.mappings.PlaceOnWaterBlockItem
import mtr.mappings.RegistryUtilities
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.function.Function

open class ItemWithCreativeTabBase : Item {
    @JvmField
    val creativeModeTab: CreativeModeTabs.Wrapper

    constructor(creativeModeTab: CreativeModeTabs.Wrapper) : super(
        RegistryUtilities.createItemProperties(creativeModeTab::get)
    ) {
        this.creativeModeTab = creativeModeTab
    }

    constructor(
        creativeModeTab: CreativeModeTabs.Wrapper,
        propertiesConsumer: Function<Properties, Properties>
    ) : super(propertiesConsumer.apply(RegistryUtilities.createItemProperties(creativeModeTab::get))) {
        this.creativeModeTab = creativeModeTab
    }

    open class ItemPlaceOnWater(
        @JvmField val creativeModeTab: CreativeModeTabs.Wrapper,
        block: Block
    ) : PlaceOnWaterBlockItem(block, RegistryUtilities.createItemProperties(creativeModeTab::get))
}
