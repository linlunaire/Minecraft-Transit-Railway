package mtr.mappings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

/** Follows real item/blockstate roots through model parents, texture slots and actual game resources. */
public final class AssetReferenceCompatibilityCheck {
	private static Path resources;
	private static final Map<String, Model> MODELS = new HashMap<>();
	private static final Set<String> CHECKED_MODELS = new HashSet<>(), CHECKED_TEXTURES = new HashSet<>();

	public static void main(String[] args) throws Exception {
		resources = Path.of(args[1]);
		final Set<String> items = new TreeSet<>(), blocks = new TreeSet<>();
		final var declarations = Pattern.compile("(register\\w+)\\.accept\\(\"([^\"]+)\", (Blocks|Items)\\.").matcher(Files.readString(Path.of(args[0], "common/src/main/java/mtr/MTR.java")));
		while (declarations.find()) {
			if (declarations.group(3).equals("Blocks")) blocks.add(declarations.group(2));
			if (!declarations.group(1).equals("registerBlock")) items.add(declarations.group(2));
		}
		require(items.size() == 270 && blocks.size() == 181, "Review changed registration inventory");
		final Set<String> actualItems = new TreeSet<>();
		try (var paths = Files.walk(resources.resolve("assets/mtr/items"))) {
			for (Path path : paths.filter(Files::isRegularFile).toList()) actualItems.add(path.getFileName().toString().replaceFirst("\\.json$", ""));
		}
		require(actualItems.equals(items), "Item definitions must match registered items; extra=" + difference(actualItems, items) + ", missing=" + difference(items, actualItems));
		for (String item : items) followModelReferences(read("assets/mtr/items/" + item + ".json"));
		for (String block : blocks) followModelReferences(read("assets/mtr/blockstates/" + block + ".json"));
		require(CHECKED_MODELS.contains("mtr:item/rail_connector_20_selected"), "Selected submodel was not followed");
		require(!CHECKED_MODELS.contains("mtr:item/platform_rail"), "Unused platform_rail became a live model root");
		System.out.println("PASS: 270 registered item definitions / 181 blockstates, " + CHECKED_MODELS.size() + " reachable models, " + CHECKED_TEXTURES.size() + " actual texture resources, parent/slot cycles and retained selected submodels (no GPU baking)");
	}

	private static Set<String> difference(Set<String> first, Set<String> second) {
		final Set<String> result = new TreeSet<>(first);
		result.removeAll(second);
		return result;
	}

	private static void followModelReferences(JsonElement json) throws Exception {
		if (json.isJsonArray()) {
			for (JsonElement value : json.getAsJsonArray()) followModelReferences(value);
		} else if (json.isJsonObject()) {
			for (var field : json.getAsJsonObject().entrySet()) {
				if (field.getKey().equals("model") && field.getValue().isJsonPrimitive()) checkModel(id(field.getValue().getAsString()));
				else followModelReferences(field.getValue());
			}
		}
	}

	private static void checkModel(String id) throws Exception {
		if (!CHECKED_MODELS.add(id)) return;
		final Model model = resolveModel(id, new HashSet<>());
		for (String texture : model.textures.values()) checkTexture(texture, model.textures);
		for (String face : model.faces) checkTexture(face, model.textures);
	}

	private static Model resolveModel(String modelId, Set<String> active) throws Exception {
		if (MODELS.containsKey(modelId)) return MODELS.get(modelId);
		require(active.add(modelId), "Model parent cycle: " + modelId);
		if (modelId.startsWith("minecraft:builtin/")) {
			require(Set.of("minecraft:builtin/generated", "minecraft:builtin/entity").contains(modelId), "Unknown built-in model: " + modelId);
			return new Model(Map.of(), List.of());
		}
		final JsonObject json = read(path(modelId, "models", ".json"));
		final Model parent = json.has("parent") ? resolveModel(id(json.get("parent").getAsString()), active) : new Model(Map.of(), List.of());
		final Map<String, String> textures = new HashMap<>(parent.textures);
		if (json.has("textures")) {
			for (var texture : json.getAsJsonObject("textures").entrySet()) {
				final JsonElement value = texture.getValue();
				textures.put(texture.getKey(), value.isJsonPrimitive() ? value.getAsString() : value.getAsJsonObject().get("sprite").getAsString());
			}
		}
		final List<String> faces = new ArrayList<>();
		if (json.has("elements")) {
			for (JsonElement element : json.getAsJsonArray("elements")) {
				for (var face : element.getAsJsonObject().getAsJsonObject("faces").entrySet()) faces.add(face.getValue().getAsJsonObject().get("texture").getAsString());
			}
		} else faces.addAll(parent.faces);
		final Model result = new Model(Map.copyOf(textures), List.copyOf(faces));
		MODELS.put(modelId, result);
		active.remove(modelId);
		return result;
	}

	private static void checkTexture(String value, Map<String, String> slots) {
		final Set<String> active = new HashSet<>();
		while (value.startsWith("#")) {
			require(active.add(value), "Texture slot cycle: " + value);
			final String key = value.substring(1);
			require(slots.containsKey(key), "Missing texture slot: " + value);
			value = slots.get(key);
		}
		final String texture = path(id(value), "textures", ".png");
		require(Files.isRegularFile(resources.resolve(texture)) || AssetReferenceCompatibilityCheck.class.getClassLoader().getResource(texture) != null, "Missing texture resource: " + texture);
		CHECKED_TEXTURES.add(texture);
	}

	private static String id(String value) { return value.contains(":") ? value : "minecraft:" + value; }
	private static String path(String id, String directory, String extension) { return "assets/" + id.replace(":", "/" + directory + "/") + extension; }
	private static JsonObject read(String path) throws Exception {
		if (Files.isRegularFile(resources.resolve(path))) return JsonParser.parseString(Files.readString(resources.resolve(path))).getAsJsonObject();
		try (InputStream stream = AssetReferenceCompatibilityCheck.class.getClassLoader().getResourceAsStream(path)) {
			require(stream != null, "Missing model/root resource: " + path);
			return JsonParser.parseString(new String(stream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
		}
	}
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
	private record Model(Map<String, String> textures, List<String> faces) { }
}
