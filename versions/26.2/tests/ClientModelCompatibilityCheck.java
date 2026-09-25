package mtr.mappings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** CPU-only checks of material transport, conditional model codecs and generated resources. */
public final class ClientModelCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		final Path root = Path.of(args[0]);
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(VanillaRegistries.createLookup()).forEach(pending -> pending.apply());
		checkTerrain();
		checkSelection();
		checkDefinitions(root, Path.of(args[1]));
		checkSourceTransform(root);
		System.out.println("PASS: terrain layer/flags transport without changing quad geometry, per-reload model/part caching, unchanged unregistered blocks, selected-pos semantics, 270 real client-item codecs / 84 conditions and bounded source conversion");
	}

	private static void checkTerrain() {
		final BakedQuad.MaterialInfo material = new BakedQuad.MaterialInfo(null, ChunkSectionLayer.TRANSLUCENT, RenderTypes.translucentMovingBlock(), 2, true, 7);
		// These are actual game data records. No atlas/GPU or fake world is needed to test transport.
		final BakedQuad quad = new BakedQuad(new Vector3f(1, 2, 3), new Vector3f(4, 5, 6), new Vector3f(7, 8, 9), new Vector3f(10, 11, 12),
			0x123456789L, 0xABCDEF012L, 3, 4, Direction.EAST, material);
		final Material.Baked particle = new Material.Baked(null, true);
		final BlockStateModelPart part = new BlockStateModelPart() {
			@Override public List<BakedQuad> getQuads(Direction direction) { return List.of(quad); }
			@Override public boolean useAmbientOcclusion() { return true; }
			@Override public Material.Baked particleMaterial() { return particle; }
			@Override public int materialFlags() { return BakedQuad.FLAG_TRANSLUCENT | BakedQuad.FLAG_ANIMATED | 8; }
		};
		final BlockStateModel original = new BlockStateModel() {
			@Override public void collectParts(RandomSource random, List<BlockStateModelPart> output) { output.add(part); }
			@Override public Material.Baked particleMaterial() { return particle; }
			@Override public int materialFlags() { return part.materialFlags(); }
		};
		TerrainRenderLayers.register(ChunkSectionLayer.CUTOUT, Blocks.STONE);
		TerrainRenderLayers.register(ChunkSectionLayer.CUTOUT, Blocks.STONE);
		TerrainRenderLayers.register(ChunkSectionLayer.TRANSLUCENT, Blocks.GLASS);
		final Map<BlockState, BlockStateModel> old = Map.of(Blocks.STONE.defaultBlockState(), original, Blocks.GLASS.defaultBlockState(), original, Blocks.DIRT.defaultBlockState(), original);
		final Map<BlockState, BlockStateModel> baked = TerrainRenderLayers.apply(old);
		require(old.get(Blocks.STONE.defaultBlockState()) == original && baked.get(Blocks.DIRT.defaultBlockState()) == original, "Source map or unregistered block was changed");
		final BlockStateModel cutout = baked.get(Blocks.STONE.defaultBlockState());
		require(!cutout.hasMaterialFlag(BakedQuad.FLAG_TRANSLUCENT) && cutout.hasMaterialFlag(BakedQuad.FLAG_ANIMATED) && cutout.hasMaterialFlag(8), "Model flags lost animation/unknown bits or retained translucency");
		require(cutout.particleMaterial() == particle, "Particle material changed");
		final List<BlockStateModelPart> output = new ArrayList<>();
		output.add(part);
		cutout.collectParts(RandomSource.create(123), output);
		require(output.size() == 2 && output.getFirst() == part, "collectParts changed the caller's existing entries");
		final BlockStateModelPart replaced = output.get(1);
		cutout.collectParts(RandomSource.create(123), output);
		require(output.get(2) == replaced, "Material conversion allocates parts on repeated collection");
		require(replaced.useAmbientOcclusion() && replaced.particleMaterial() == particle && replaced.materialFlags() == (BakedQuad.FLAG_ANIMATED | 8), "Part metadata changed");
		final List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
		directions.add(null);
		for (Direction direction : directions) {
			final BakedQuad converted = replaced.getQuads(direction).getFirst();
			require(converted.position0() == quad.position0() && converted.position1() == quad.position1() && converted.position2() == quad.position2() && converted.position3() == quad.position3(), "Quad geometry changed");
			require(converted.packedUV0() == quad.packedUV0() && converted.packedUV1() == quad.packedUV1() && converted.packedUV2() == quad.packedUV2() && converted.packedUV3() == quad.packedUV3() && converted.direction() == quad.direction(), "Quad UVs/facing changed");
			final BakedQuad.MaterialInfo convertedMaterial = converted.materialInfo();
			require(convertedMaterial.layer() == ChunkSectionLayer.CUTOUT && convertedMaterial.sprite() == material.sprite() && convertedMaterial.itemRenderType() == material.itemRenderType() && convertedMaterial.tintIndex() == 2 && convertedMaterial.shade() && convertedMaterial.lightEmission() == 7, "Material conversion changed more than the terrain layer");
		}
		final List<BlockStateModelPart> glass = new ArrayList<>();
		baked.get(Blocks.GLASS.defaultBlockState()).collectParts(RandomSource.create(123), glass);
		require(glass.getFirst().getQuads(null).getFirst() == quad, "Already matching quads should retain identity");
		require(TerrainRenderLayers.apply(old).get(Blocks.STONE.defaultBlockState()) != cutout, "Reload reused an old texture/model cache");
		try {
			TerrainRenderLayers.register(ChunkSectionLayer.TRANSLUCENT, Blocks.STONE);
			throw new AssertionError("Conflicting layer registration accepted");
		} catch (IllegalStateException expected) {
		}
	}

	private static void checkSelection() {
		final ItemStack stack = new ItemStack(Items.STICK);
		final SelectedItemModelProperty property = SelectedItemModelProperty.INSTANCE;
		require(!property.get(stack, null, null, 0, ItemDisplayContext.GUI), "Empty custom data is selected");
		final CompoundTag tag = new CompoundTag();
		tag.putString("other", "pos");
		stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		require(!property.get(stack, null, null, 0, ItemDisplayContext.GUI), "Unrelated data is selected");
		for (int i = 0; i < 3; i++) {
			if (i == 0) tag.putLong("pos", 0); else if (i == 1) tag.putLong("pos", Long.MIN_VALUE); else tag.putString("pos", "legacy wrong type");
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
			require(property.get(stack, null, null, 0, ItemDisplayContext.GUI), "Legacy presence test was changed to a value/type check");
		}
	}

	@SuppressWarnings("unchecked")
	private static void checkDefinitions(Path root, Path generated) throws Exception {
		ItemModels.bootstrap();
		ConditionalItemModelProperties.bootstrap();
		// A standalone JVM does not apply game mixins. Perform the identical registration here
		// to exercise the real dispatcher codec; this does not claim loader weaving is tested.
		final Field mapperField = ConditionalItemModelProperties.class.getDeclaredField("ID_MAPPER");
		mapperField.setAccessible(true);
		final var mapper = (ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ConditionalItemModelProperty>>) mapperField.get(null);
		mapper.put(SelectedItemModelProperty.ID, SelectedItemModelProperty.CODEC);
		int count = 0, selected = 0;
		try (var paths = Files.walk(generated.resolve("assets/mtr/items"))) {
			for (Path path : paths.filter(p -> p.toString().endsWith(".json")).toList()) {
				final JsonObject json = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
				final ClientItem item = ClientItem.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
				final Path relative = generated.resolve("assets/mtr/items").relativize(path);
				final JsonObject legacy = JsonParser.parseString(Files.readString(root.resolve("common/src/main/resources/assets/mtr/models/item").resolve(relative))).getAsJsonObject();
				if (legacy.has("overrides")) {
					require(item.model() instanceof ConditionalItemModel.Unbaked condition && condition.property() instanceof SelectedItemModelProperty, "Selected model codec did not resolve the custom property");
					require(json.getAsJsonObject("model").getAsJsonObject("on_true").get("model").equals(legacy.getAsJsonArray("overrides").get(0).getAsJsonObject().get("model")), "Selected model target changed");
					selected++;
				}
				count++;
			}
		}
		require(count == 270 && selected == 84, "Registered model coverage changed: " + count + "/" + selected);
	}

	private static void checkSourceTransform(Path root) throws Exception {
		final Map<String, Object> extensions = new HashMap<>();
		final Binding binding = new Binding(Map.of("rootProject", Map.of("ext", extensions)));
		new GroovyShell(binding).evaluate("class GradleException extends RuntimeException { GradleException(String message) { super(message) } }\n" + Files.readString(root.resolve("versions/26.2/client-model-port.gradle")));
		final Closure<?> transform = (Closure<?>) extensions.get("transformMc26ClientModelSource");
		final String source = Files.readString(root.resolve("common/src/main/java/mtr/MTRClient.java")).replace("\r\n", "\n");
		final String result = transform.call(source).toString();
		require(result.substring(result.indexOf("\tpublic static boolean isReplayMod()")).equals(source.substring(source.indexOf("\tpublic static boolean isReplayMod()"))), "Predicate conversion removed or changed the following render/timing methods");
		require(result.split("TerrainRenderLayers.register", -1).length - 1 == 57 && !result.contains("RegistryClient.registerBlockRenderType"), "Terrain registrations were lost");
		require(!result.contains("initItemModelPredicate"), "Old item callback initializer remains");
		try {
			transform.call(source.replace("MTR.MOD_ID + \":selected\"", "MTR.MOD_ID + \":unsupported\""));
			throw new AssertionError("Unknown predicates must not be silently removed");
		} catch (RuntimeException expected) {
			require(expected.getMessage().contains("unsupported item predicate"), "Unexpected transform failure");
		}
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}
}
