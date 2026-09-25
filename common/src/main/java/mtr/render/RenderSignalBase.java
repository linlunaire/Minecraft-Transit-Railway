package mtr.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.block.BlockNode;
import mtr.block.BlockSignalLightBase;
import mtr.block.BlockSignalSemaphoreBase;
import mtr.block.IBlock;
import mtr.client.ClientData;
import mtr.data.IGui;
import mtr.data.Rail;
import mtr.mappings.BlockEntityMapper;
import mtr.mappings.BlockEntityRendererMapper;
import mtr.mappings.UtilitiesClient;
import mtr.path.PathData;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public abstract class RenderSignalBase<T extends BlockEntityMapper> extends BlockEntityRendererMapper<T> implements IBlock, IGui {
	private static final ResourceLocation WHITE_TEXTURE = ResourceLocation.parse("mtr:textures/block/white.png");
	private static final int[] CHECK_DISTANCES = {0, 1, -1, 2, -2, 3, -3, 4, -4};

	protected final boolean isSingleSided;
	protected final int aspects;
	private final Map<BlockPos, Float> scanNodesA = new HashMap<>();
	private final Map<BlockPos, Float> scanNodesB = new HashMap<>();

	public RenderSignalBase(BlockEntityRenderDispatcher dispatcher, boolean isSingleSided, int aspects) {
		super(dispatcher);
		this.isSingleSided = isSingleSided;
		this.aspects = aspects;
	}

	// TODO backwards compatibility
	@Deprecated
	public RenderSignalBase(BlockEntityRenderDispatcher dispatcher, boolean isSingleSided) {
		this(dispatcher, isSingleSided, 2);
	}

	@Override
	public final void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		final BlockGetter world = entity.getLevel();
		if (world == null) {
			return;
		}

		final BlockPos pos = entity.getBlockPos();
		final BlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof BlockSignalLightBase || state.getBlock() instanceof BlockSignalSemaphoreBase)) {
			return;
		}
		final Direction facing = IBlock.getStatePropertySafe(state, HorizontalDirectionalBlock.FACING);
		if (RenderTrains.shouldNotRender(pos, RenderTrains.maxTrainRenderDistance, null)) {
			return;
		}

		final BlockPos startPos = getNodePos(world, pos, facing);
		if (startPos == null) {
			return;
		}

		matrices.pushPose();
		matrices.translate(0.5, 0, 0.5);

		for (int i = 0; i < 2; i++) {
			final Direction newFacing = (i == 1 ? facing.getOpposite() : facing);
			final int occupiedAspect = getOccupiedAspect(startPos, newFacing.toYRot() + 90);

			if (occupiedAspect >= 0) {
				matrices.pushPose();
				UtilitiesClient.rotateYDegrees(matrices, -newFacing.toYRot());
				final VertexConsumer vertexConsumer = vertexConsumers.getBuffer(MoreRenderLayers.getLight(WHITE_TEXTURE, false));
				render(matrices, vertexConsumers, vertexConsumer, entity, tickDelta, newFacing, occupiedAspect, i == 1);
				// TODO temporary code
				render(matrices, vertexConsumers, vertexConsumer, entity, tickDelta, newFacing, occupiedAspect == 1, i == 1);
				// TODO temporary code end
				matrices.popPose();
			}

			if (isSingleSided) {
				break;
			}
		}

		matrices.popPose();
	}

	// TODO make abstract later
	protected void render(PoseStack matrices, MultiBufferSource vertexConsumers, VertexConsumer vertexConsumer, T entity, float tickDelta, Direction facing, int occupiedAspect, boolean isBackSide) {
	}

	// TODO temporary code
	protected void render(PoseStack matrices, MultiBufferSource vertexConsumers, VertexConsumer vertexConsumer, T entity, float tickDelta, Direction facing, boolean isOccupied, boolean isBackSide) {
	}
	// TODO temporary code end

	private int getOccupiedAspect(BlockPos startPos, float facing) {
		Map<BlockPos, Float> nodesToScan = scanNodesA;
		Map<BlockPos, Float> newNodesToScan = scanNodesB;
		nodesToScan.clear();
		newNodesToScan.clear();
		nodesToScan.put(startPos, facing);
		int occupiedAspect = -1;

		for (int j = 1; j < aspects; j++) {
			newNodesToScan.clear();

			for (final Map.Entry<BlockPos, Float> checkNode : nodesToScan.entrySet()) {
				final Map<BlockPos, Rail> railMap = ClientData.RAILS.get(checkNode.getKey());

				if (railMap != null) {
					for (final Map.Entry<BlockPos, Rail> railEntry : railMap.entrySet()) {
						final BlockPos endPos = railEntry.getKey();
						final Rail rail = railEntry.getValue();
						if (rail.facingStart.similarFacing(checkNode.getValue())) {
							final java.util.UUID railProduct = PathData.getRailProduct(checkNode.getKey(), endPos);
							if (ClientData.SIGNAL_BLOCKS.isOccupied(railProduct)) {
								return j;
							} else {
								final Boolean isOccupied = ClientData.OCCUPIED_RAILS.get(railProduct);
								if (isOccupied != null && isOccupied) {
									return j;
								}
							}

							newNodesToScan.put(endPos, rail.facingEnd.getOpposite().angleDegrees);
							occupiedAspect = 0;
						}
					}
				}
			}

			final Map<BlockPos, Float> previousNodesToScan = nodesToScan;
			nodesToScan = newNodesToScan;
			newNodesToScan = previousNodesToScan;
		}

		return occupiedAspect;
	}

	private static BlockPos getNodePos(BlockGetter world, BlockPos pos, Direction facing) {
		final Direction perpendicularFacing = facing.getClockWise();
		for (final int z : CHECK_DISTANCES) {
			for (final int x : CHECK_DISTANCES) {
				for (int y = -5; y <= 0; y++) {
					final BlockPos checkPos = pos.offset(perpendicularFacing.getStepX() * x + facing.getStepX() * z, y, perpendicularFacing.getStepZ() * x + facing.getStepZ() * z);
					final BlockState checkState = world.getBlockState(checkPos);
					if (checkState.getBlock() instanceof BlockNode) {
						return checkPos;
					}
				}
			}
		}
		return null;
	}
}
