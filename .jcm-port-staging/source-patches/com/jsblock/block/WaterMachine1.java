package com.jsblock.block;

import mtr.block.BlockDirectionalDoubleBlockBase;
import mtr.block.IBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WaterMachine1 extends BlockDirectionalDoubleBlockBase {
   public WaterMachine1(BlockBehaviour.Properties settings) {
      super(settings);
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      Direction facing = (Direction)IBlock.getStatePropertySafe(state, f_54117_);
      return IBlock.getVoxelShapeByDirection(2.5, 0.0, 0.0, 13.5, 16.0, 11.0, facing);
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (player.m_21055_(Items.f_42590_)) {
         ItemStack bottleItem = player.m_21120_(hand);
         ItemStack waterBottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);
         bottleItem.m_41774_(1);
         if (bottleItem.m_41619_()) {
            player.m_21008_(hand, waterBottle);
         } else if (!player.m_36356_(waterBottle)) {
            player.m_36176_(waterBottle, false);
         }

         world.m_5594_((Player)null, pos, SoundEvents.f_11781_, SoundSource.BLOCKS, 1.0F, 1.0F);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{f_54117_, HALF});
   }
}

