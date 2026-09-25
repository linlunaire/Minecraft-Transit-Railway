package mtr.mappings;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Keeps legacy per-block cutout/translucent intent in 26.2's per-quad materials. */
public final class TerrainRenderLayers {

	private static final Map<Block, ChunkSectionLayer> LAYERS = new IdentityHashMap<>();

	private TerrainRenderLayers() {
	}

	public static synchronized void register(ChunkSectionLayer layer, Block block) {
		Objects.requireNonNull(block);
		Objects.requireNonNull(layer);
		final ChunkSectionLayer previous = LAYERS.putIfAbsent(block, layer);
		if (previous != null && previous != layer) {
			throw new IllegalStateException("Conflicting MTR terrain layers for " + block);
		}
	}

	/** Called once for each new baked model set; never mutates the loader's source map. */
	public static Map<BlockState, BlockStateModel> apply(Map<BlockState, BlockStateModel> models) {
		final Map<Block, ChunkSectionLayer> layers;
		synchronized (TerrainRenderLayers.class) {
			layers = new IdentityHashMap<>(LAYERS);
		}
		final Map<BlockState, BlockStateModel> result = new IdentityHashMap<>(models);
		final Map<BlockStateModel, Map<ChunkSectionLayer, BlockStateModel>> wrapped = new IdentityHashMap<>();
		result.replaceAll((state, model) -> {
			final ChunkSectionLayer layer = layers.get(state.getBlock());
			return layer == null ? model : wrapped.computeIfAbsent(model, ignored -> new EnumMap<>(ChunkSectionLayer.class))
				.computeIfAbsent(layer, ignored -> new LayeredModel(model, layer));
		});
		return Collections.unmodifiableMap(result);
	}

	private static int flags(int original, ChunkSectionLayer layer) {
		return (original & ~BakedQuad.FLAG_TRANSLUCENT) | (layer.translucent() ? BakedQuad.FLAG_TRANSLUCENT : 0);
	}

	private static final class LayeredModel implements BlockStateModel {
		private final BlockStateModel delegate;
		private final ChunkSectionLayer layer;
		// The cache belongs to this reload's model, not to a static cache of old textures.
		private final Map<BlockStateModelPart, BlockStateModelPart> parts = Collections.synchronizedMap(new IdentityHashMap<>());

		private LayeredModel(BlockStateModel delegate, ChunkSectionLayer layer) {
			this.delegate = delegate;
			this.layer = layer;
		}

		@Override
		public void collectParts(RandomSource random, List<BlockStateModelPart> output) {
			final int first = output.size();
			delegate.collectParts(random, output);
			for (int i = first; i < output.size(); i++) {
				output.set(i, parts.computeIfAbsent(output.get(i), part -> new LayeredPart(part, layer)));
			}
		}

		@Override
		public Material.Baked particleMaterial() { return delegate.particleMaterial(); }

		@Override
		public int materialFlags() { return flags(delegate.materialFlags(), layer); }
	}

	private static final class LayeredPart implements BlockStateModelPart {
		private final BlockStateModelPart delegate;
		private final int materialFlags;
		private final List<List<BakedQuad>> faces = new ArrayList<>(7);

		private LayeredPart(BlockStateModelPart delegate, ChunkSectionLayer layer) {
			this.delegate = delegate;
			materialFlags = flags(delegate.materialFlags(), layer);
			for (Direction direction : Direction.values()) {
				faces.add(convert(delegate.getQuads(direction), layer));
			}
			faces.add(convert(delegate.getQuads(null), layer));
		}

		@Override
		public List<BakedQuad> getQuads(Direction direction) { return faces.get(direction == null ? 6 : direction.ordinal()); }

		@Override
		public boolean useAmbientOcclusion() { return delegate.useAmbientOcclusion(); }

		@Override
		public Material.Baked particleMaterial() { return delegate.particleMaterial(); }

		@Override
		public int materialFlags() { return materialFlags; }

		private static List<BakedQuad> convert(List<BakedQuad> quads, ChunkSectionLayer layer) {
			return quads.stream().map(quad -> {
				final BakedQuad.MaterialInfo old = quad.materialInfo();
				if (old.layer() == layer) {
					return quad;
				}
				final BakedQuad.MaterialInfo material = new BakedQuad.MaterialInfo(old.sprite(), layer, old.itemRenderType(), old.tintIndex(), old.shade(), old.lightEmission());
				return new BakedQuad(quad.position0(), quad.position1(), quad.position2(), quad.position3(),
					quad.packedUV0(), quad.packedUV1(), quad.packedUV2(), quad.packedUV3(), quad.direction(), material);
			}).toList();
		}
	}
}
