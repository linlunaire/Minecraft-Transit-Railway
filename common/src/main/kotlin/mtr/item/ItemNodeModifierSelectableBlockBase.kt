package mtr.item

import mtr.block.BlockNode
import mtr.data.RailAngle
import mtr.data.RailwayData
import mtr.data.TransportMode
import mtr.mappings.CompoundTagMapper
import mtr.mappings.ItemStackUtilities
import mtr.mappings.PlayerUtilities
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

abstract class ItemNodeModifierSelectableBlockBase(
    private val canSaveBlock: Boolean,
    private val height: Int,
    private val width: Int
) : ItemNodeModifierBase(true, false, false, true) {
    private val radius: Int

    init {
        radius = width / 2
    }

    public override fun useOn(context: UseOnContext): InteractionResult {
        if (canSaveBlock) {
            val world = context.getLevel()
            if (!world.isClientSide()) {
                val player = context.getPlayer()
                if (player != null && player.isShiftKeyDown()) {
                    val state = world.getBlockState(context.getClickedPos())
                    var newState: BlockState
                    if (state.getBlock() is BlockNode) {
                        newState = Blocks.AIR.defaultBlockState()
                    } else {
                        newState = state
                    }
                    PlayerUtilities.displayClientMessage(
                        player,
                        Text.translatable(
                            "tooltip.mtr.selected_material",
                            Text.translatable(newState.getBlock().getDescriptionId())
                        ),
                        true
                    )
                    val itemStack = context.getItemInHand()
                    val compoundTag = ItemStackUtilities.getCustomData(itemStack)
                    compoundTag.putInt(TAG_BLOCK_ID, Block.getId(newState))
                    ItemStackUtilities.setCustomData(itemStack, compoundTag)
                    return InteractionResult.SUCCESS
                }
            }
        }

        return super.useOn(context)
    }

    public override fun appendHoverText(
        stack: ItemStack,
        tooltipContext: TooltipContext,
        tooltipDisplay: TooltipDisplay,
        tooltip: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        if (height > 0) {
            tooltip.accept(
                Text.translatable("tooltip.mtr.rail_action_height", height)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            )
        }
        tooltip.accept(
            Text.translatable("tooltip.mtr.rail_action_width", width)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
        )

        if (canSaveBlock) {
            val state = getSavedState(stack)!!
            // Preserve Java regex splitting, including removal of trailing empty parts.
            val textSplit = (Text.translatable(
                if (state.isAir()) "tooltip.mtr.shift_right_click_to_select_material" else "tooltip.mtr.shift_right_click_to_clear",
                Minecraft.getInstance().options.keyShift.getTranslatedKeyMessage(),
                Text.translatable(mtr.Blocks.RAIL_NODE.get().getDescriptionId())
            ).getString() as java.lang.String).split("\\|")
            for (text in textSplit) {
                tooltip.accept(
                    Text.literal(text)
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).applyFormat(ChatFormatting.ITALIC))
                )
            }
            tooltip.accept(
                Text.translatable(
                    "tooltip.mtr.selected_material",
                    Text.translatable(state.getBlock().getDescriptionId())
                ).setStyle(
                    Style.EMPTY.withColor(ChatFormatting.GREEN)
                )
            )
        }

        super.appendHoverText(stack, tooltipContext, tooltipDisplay, tooltip, tooltipFlag)
    }

    final protected override fun onConnect(
        world: Level,
        stack: ItemStack,
        transportMode: TransportMode,
        stateStart: BlockState,
        stateEnd: BlockState,
        posStart: BlockPos,
        posEnd: BlockPos,
        facingStart: RailAngle,
        facingEnd: RailAngle,
        player: Player?,
        railwayData: RailwayData
    ) {
        if (player != null && !onConnect(player, stack, railwayData, posStart, posEnd, radius, height)) {
            PlayerUtilities.displayClientMessage(player, Text.translatable("gui.mtr.rail_not_found_action"), true)
        }
    }

    final protected override fun onRemove(
        world: Level,
        posStart: BlockPos,
        posEnd: BlockPos,
        player: Player?,
        railwayData: RailwayData
    ) {
    }

    protected open fun getSavedState(stack: ItemStack): BlockState? {
        val tag = ItemStackUtilities.getCustomData(stack)
        if (tag.contains(TAG_BLOCK_ID)) {
            return Block.stateById(CompoundTagMapper.getInt(tag, TAG_BLOCK_ID))
        } else {
            return Blocks.AIR.defaultBlockState()
        }
    }

    protected abstract fun onConnect(
        player: Player,
        itemStack: ItemStack,
        railwayData: RailwayData,
        posStart: BlockPos,
        posEnd: BlockPos,
        radius: Int,
        height: Int
    ): Boolean

    companion object {
        private const val TAG_BLOCK_ID = "block_id"
    }
}
