package mtr.item

import java.util.function.Function

import mtr.CreativeModeTabs
import mtr.block.BlockNode
import mtr.data.RailAngle
import mtr.data.RailwayData
import mtr.data.TransportMode
import mtr.mappings.CompoundTagMapper
import mtr.mappings.ItemStackUtilities
import mtr.mappings.Text
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

abstract class ItemNodeModifierBase(
    @JvmField val forNonContinuousMovementNode: Boolean,
    @JvmField val forContinuousMovementNode: Boolean,
    @JvmField val forAirplaneNode: Boolean,
    @JvmField protected val isConnector: Boolean
) : ItemBlockClickingBase(
    CreativeModeTabs.CORE, Function { properties -> properties.stacksTo(1) }) {
    public override fun appendHoverText(
        stack: ItemStack,
        tooltipContext: TooltipContext,
        tooltipDisplay: TooltipDisplay,
        tooltip: java.util.function.Consumer<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val compoundTag = ItemStackUtilities.getCustomData(stack)
        val posLong = CompoundTagMapper.getLong(compoundTag, TAG_POS)
        if (posLong != 0L) {
            tooltip.accept(
                Text.translatable("tooltip.mtr.selected_block", BlockPos.of(posLong).toShortString()).setStyle(
                    Style.EMPTY.withColor(ChatFormatting.GOLD)
                )
            )
        }
    }

    protected override fun onStartClick(context: UseOnContext, compoundTag: CompoundTag) {
        compoundTag.putString(
            TAG_TRANSPORT_MODE,
            (context.getLevel().getBlockState(context.getClickedPos()).getBlock() as BlockNode).transportMode.toString()
        )
    }

    protected override fun onEndClick(context: UseOnContext, posEnd: BlockPos, compoundTag: CompoundTag) {
        val world = context.getLevel()
        val railwayData = RailwayData.getInstance(world)
        val posStart = context.getClickedPos()
        val stateStart = world.getBlockState(posStart)
        val blockStart = stateStart.getBlock()
        val stateEnd = world.getBlockState(posEnd)

        if (railwayData != null && stateEnd.getBlock() is BlockNode && (blockStart as BlockNode).transportMode.toString()
                .equals(
                    CompoundTagMapper.getString(compoundTag, TAG_TRANSPORT_MODE)
                )
        ) {
            val player = context.getPlayer()

            if (isConnector) {
                if (!posStart.equals(posEnd)) {
                    val angle1 = BlockNode.getAngle(stateStart)
                    val angle2 = BlockNode.getAngle(stateEnd)

                    val angleDifference = Math.toDegrees(
                        Math.atan2(
                            (posEnd.getZ() - posStart.getZ()).toDouble(),
                            (posEnd.getX() - posStart.getX()).toDouble()
                        )
                    ).toFloat()
                    val railAngleStart =
                        RailAngle.fromAngle(angle1 + (if (RailAngle.similarFacing(angleDifference, angle1)) 0 else 180))
                    val railAngleEnd =
                        RailAngle.fromAngle(angle2 + (if (RailAngle.similarFacing(angleDifference, angle2)) 180 else 0))

                    onConnect(
                        world,
                        context.getItemInHand(),
                        blockStart.transportMode,
                        stateStart,
                        stateEnd,
                        posStart,
                        posEnd,
                        railAngleStart,
                        railAngleEnd,
                        player,
                        railwayData
                    )
                }
            } else {
                onRemove(world, posStart, posEnd, player, railwayData)
            }
        }

        compoundTag.remove(TAG_TRANSPORT_MODE)
    }

    protected override fun clickCondition(context: UseOnContext): Boolean {
        val world = context.getLevel()
        val blockStart = world.getBlockState(context.getClickedPos()).getBlock()
        if (blockStart is BlockNode) {
            val blockNode = blockStart
            if (blockNode.transportMode == TransportMode.AIRPLANE) {
                return forAirplaneNode
            } else {
                return if (blockNode.transportMode.continuousMovement) forContinuousMovementNode else forNonContinuousMovementNode
            }
        } else {
            return false
        }
    }

    protected abstract fun onConnect(
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
    )

    protected abstract fun onRemove(
        world: Level,
        posStart: BlockPos,
        posEnd: BlockPos,
        player: Player?,
        railwayData: RailwayData
    )

    companion object {
        const val TAG_POS: String = "pos"
        private const val TAG_TRANSPORT_MODE = "transport_mode"
    }
}
