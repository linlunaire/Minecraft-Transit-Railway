package mtr.mappings;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.tags.TagFile;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.LootTable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

/** Original ID/structure checks plus actual vanilla codecs. This does not bootstrap MTR registries. */
public final class DataResourceCompatibilityCheck {
	private static final Set<String> BLOCK_IDS = new HashSet<>(), ITEM_IDS = new HashSet<>(), VISITED_TAGS = new HashSet<>();
	private static Path output;

	public static void main(String[] args) throws Exception {
		final Path root = Path.of(args[0]);
		output = Path.of(args[1]);
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(VanillaRegistries.createLookup()).forEach(pending -> pending.apply());
		final var registrations = Pattern.compile("(register\\w+)\\.accept\\(\"([^\"]+)\", (Blocks|Items)\\.").matcher(Files.readString(root.resolve("common/src/main/java/mtr/MTR.java")));
		while (registrations.find()) {
			final String name = "mtr:" + registrations.group(2);
			if (registrations.group(3).equals("Blocks")) BLOCK_IDS.add(name);
			if (!registrations.group(1).equals("registerBlock")) ITEM_IDS.add(name);
		}
		int recipes = 0, loot = 0, tags = 0;
		try (var files = Files.walk(output)) {
			for (Path file : files.filter(Files::isRegularFile).toList()) {
				final String path = output.relativize(file).toString().replace('\\', '/');
				require(!path.matches("data/[^/]+/(recipes|loot_tables)/.*") && !path.matches("data/[^/]+/tags/(items|blocks)/.*"), "Old resource path survived: " + path);
				final JsonObject json = read(file);
				if (path.contains("/recipe/")) {
					checkRecipe(json, path);
					recipes++;
				} else if (path.contains("/loot_table/")) {
					final JsonObject schemaFixture = json.deepCopy();
					projectLootReferences(schemaFixture);
					LootTable.DIRECT_CODEC.parse(JsonOps.INSTANCE, schemaFixture).getOrThrow(message -> new AssertionError(path + ": " + message));
					loot++;
				} else if (path.contains("/tags/")) {
					TagFile.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(message -> new AssertionError(path + ": " + message));
					final String[] split = path.split("/", 5);
					checkTag(split[1] + ":" + split[4].replaceFirst("\\.json$", ""), split[3], new HashSet<>());
					tags++;
				}
			}
		}
		checkOriginalTransport(root);
		final JsonObject pack = read(root.resolve("versions/26.2/common/src/main/resources/pack.mcmeta")).getAsJsonObject("pack");
		for (PackType type : PackType.values()) {
			final var metadata = PackMetadataSection.forPackType(type).codec().parse(JsonOps.INSTANCE, pack).getOrThrow();
			require(metadata.supportedFormats().isValueInRange(SharedConstants.getCurrentVersion().packVersion(type)), "Pack metadata excludes " + type);
		}
		require(recipes == 297 && loot == 176 && tags == 32, "Resource inventory changed: " + recipes + "/" + loot + "/" + tags);
		System.out.println("PASS: 297 recipes, 176 loot tables, 32 merged tags; original IDs/counts/patterns/conditions, reference graph, vanilla schema codecs and both pack formats. MTR registry binding/loot execution still requires a game launch.");
	}

	private static void checkRecipe(JsonObject json, String path) throws Exception {
		final JsonObject fixture = json.deepCopy();
		if (fixture.has("key")) {
			for (var key : fixture.getAsJsonObject("key").entrySet()) key.setValue(projectIngredient(key.getValue()));
		} else {
			final JsonArray ingredients = fixture.getAsJsonArray("ingredients");
			for (int i = 0; i < ingredients.size(); i++) ingredients.set(i, projectIngredient(ingredients.get(i)));
		}
		final JsonObject result = fixture.getAsJsonObject("result");
		final String id = result.get("id").getAsString();
		checkId(id, "item");
		if (id.startsWith("mtr:")) result.addProperty("id", "minecraft:stone");
		// Only registry references are projected. Shapes, multiplicities and the actual
		// result schema go through the real game codec without changing production JSON.
		final var ops = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY).createSerializationContext(JsonOps.INSTANCE);
		final Recipe<?> decoded = Recipe.CODEC.parse(ops, fixture).getOrThrow(message -> new AssertionError(path + ": " + message));
		@SuppressWarnings("unchecked") final Recipe<CraftingInput> crafting = (Recipe<CraftingInput>) decoded;
		final int count = json.getAsJsonObject("result").has("count") ? json.getAsJsonObject("result").get("count").getAsInt() : 1;
		require(crafting.assemble(CraftingInput.EMPTY).getCount() == count, "Recipe output count changed: " + path);
	}

	private static JsonElement projectIngredient(JsonElement value) throws Exception {
		if (value.isJsonArray()) {
			final JsonArray projected = new JsonArray();
			for (JsonElement element : value.getAsJsonArray()) projected.add(projectIngredient(element));
			return projected;
		}
		require(value.isJsonPrimitive() && value.getAsJsonPrimitive().isString(), "Old ingredient object survived");
		final String id = value.getAsString();
		if (id.startsWith("#")) {
			checkTag(id.substring(1), "item", new HashSet<>());
			return new JsonPrimitive("minecraft:stone");
		}
		checkId(id, "item");
		return id.startsWith("mtr:") ? new JsonPrimitive("minecraft:stone") : value;
	}

	private static void projectLootReferences(JsonElement element) {
		if (element.isJsonArray()) {
			element.getAsJsonArray().forEach(DataResourceCompatibilityCheck::projectLootReferences);
		} else if (element.isJsonObject()) {
			final JsonObject object = element.getAsJsonObject();
			if (object.has("condition")) {
				final String condition = object.get("condition").getAsString();
				require(!condition.equals("minecraft:alternative"), "Old loot condition survived");
				if (condition.equals("minecraft:block_state_property")) {
					checkId(object.get("block").getAsString(), "block");
					if (object.has("properties")) StatePropertiesPredicate.CODEC.parse(JsonOps.INSTANCE, object.get("properties")).getOrThrow();
					// Properties are schema-checked above, but validating them against MTR's
					// real StateDefinitions is intentionally left to loader integration tests.
					object.addProperty("block", "minecraft:stone");
					object.remove("properties");
				}
			}
			if (object.has("type") && object.get("type").getAsString().equals("minecraft:item")) {
				checkId(object.get("name").getAsString(), "item");
				object.addProperty("name", "minecraft:stone");
			}
			object.entrySet().forEach(entry -> projectLootReferences(entry.getValue()));
		}
	}

	private static void checkId(String id, String kind) {
		final Identifier identifier = Identifier.parse(id);
		final boolean exists = id.startsWith("mtr:") ? (kind.equals("item") ? ITEM_IDS : BLOCK_IDS).contains(id)
			: kind.equals("item") ? BuiltInRegistries.ITEM.containsKey(identifier) : BuiltInRegistries.BLOCK.containsKey(identifier);
		require(exists, "Unregistered " + kind + " reference: " + id);
	}

	private static void checkTag(String id, String kind, Set<String> active) throws Exception {
		final String key = kind + ":" + id;
		if (VISITED_TAGS.contains(key)) return;
		require(active.add(key), "Tag cycle: " + key);
		final Identifier identifier = Identifier.parse(id);
		final String resource = "data/" + identifier.getNamespace() + "/tags/" + kind + "/" + identifier.getPath() + ".json";
		final JsonObject json;
		if (Files.exists(output.resolve(resource))) json = read(output.resolve(resource));
		else {
			try (InputStream stream = DataResourceCompatibilityCheck.class.getClassLoader().getResourceAsStream(resource)) {
				require(stream != null, "Missing tag: " + key);
				json = JsonParser.parseString(new String(stream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
			}
		}
		TagFile.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
		for (JsonElement value : json.getAsJsonArray("values")) {
			if (value.isJsonObject() && value.getAsJsonObject().has("required") && !value.getAsJsonObject().get("required").getAsBoolean()) continue;
			final String name = value.isJsonObject() ? value.getAsJsonObject().get("id").getAsString() : value.getAsString();
			if (name.startsWith("#")) checkTag(name.substring(1), kind, active);
			else checkId(name, kind);
		}
		active.remove(key);
		VISITED_TAGS.add(key);
	}

	private static void checkOriginalTransport(Path root) throws Exception {
		for (String source : List.of("resources/common/lifts/data", "resources/common/normal/data", "common/src/main/resources/data")) {
			try (var files = Files.walk(root.resolve(source))) {
				for (Path file : files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json")).toList()) {
					final String path = "data/" + root.resolve(source).relativize(file).toString().replace('\\', '/');
					final String target = path.replace("/recipes/", "/recipe/").replace("/loot_tables/", "/loot_table/").replace("/tags/items/", "/tags/item/").replace("/tags/blocks/", "/tags/block/");
					final JsonObject original = read(file), actual = read(output.resolve(target));
					if (path.contains("/recipes/")) {
						final JsonObject expected = original.deepCopy();
						if (expected.has("key")) for (var key : expected.getAsJsonObject("key").entrySet()) key.setValue(expectedIngredient(key.getValue()));
						else {
							final JsonArray ingredients = expected.getAsJsonArray("ingredients");
							for (int i = 0; i < ingredients.size(); i++) ingredients.set(i, expectedIngredient(ingredients.get(i)));
						}
						expected.getAsJsonObject("result").add("id", expected.getAsJsonObject("result").remove("item"));
						require(expected.equals(actual), "Recipe changed beyond format conversion: " + path);
					} else if (path.contains("/loot_tables/")) {
						final JsonElement expected = JsonParser.parseString(original.toString().replace("\"minecraft:alternative\"", "\"minecraft:any_of\""));
						require(expected.equals(actual), "Loot changed beyond condition rename: " + path);
					} else {
						for (JsonElement value : original.getAsJsonArray("values")) require(actual.getAsJsonArray("values").contains(value), "Tag merge dropped a member: " + path);
					}
				}
			}
		}
	}

	private static JsonElement expectedIngredient(JsonElement value) {
		if (value.isJsonArray()) {
			final JsonArray result = new JsonArray();
			value.getAsJsonArray().forEach(entry -> result.add(expectedIngredient(entry)));
			return result;
		}
		final JsonObject object = value.getAsJsonObject();
		final String valueId = object.has("item") ? object.get("item").getAsString() : "#" + object.get("tag").getAsString();
		return new JsonPrimitive(valueId.equals("minecraft:chain") ? "minecraft:iron_chain" : valueId);
	}
	private static JsonObject read(Path path) throws Exception { return JsonParser.parseString(Files.readString(path)).getAsJsonObject(); }
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
