package mtr.mappings;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class BlockEntityMapper extends BlockEntity {

	// Read and write the existing root keys, including nested tags and arrays, without changing the save format.
	private static final MapCodec<CompoundTag> COMPOUND_CODEC = MapCodec.assumeMapUnsafe(CompoundTag.CODEC);

	public BlockEntityMapper(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected final void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		readCompoundTag(input.read(COMPOUND_CODEC).orElseThrow(() -> new IllegalStateException("Unable to decode MTR block entity data at " + worldPosition)));
	}

	@Override
	protected final void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		final CompoundTag compoundTag = new CompoundTag();
		writeCompoundTag(compoundTag);
		output.store(COMPOUND_CODEC, compoundTag);
	}

	public void readCompoundTag(CompoundTag compoundTag) {
	}

	public void writeCompoundTag(CompoundTag compoundTag) {
	}
}
