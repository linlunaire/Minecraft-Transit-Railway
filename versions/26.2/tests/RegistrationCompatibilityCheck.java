package mtr.mappings;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import mtr.RegistryObject;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Run with Java 25, the official 26.2 game libraries and Gradle's Groovy library. */
public final class RegistrationCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		final Path root = Path.of(args.length == 0 ? "." : args[0]);
		final Map<String, String> blockIds = new LinkedHashMap<>();
		final Map<String, String> itemIds = new LinkedHashMap<>();
		final Matcher registrations = Pattern.compile("register\\w+\\.accept\\(\"([^\"]+)\", (Blocks|Items)\\.([A-Z0-9_]+)").matcher(read(root, "common/src/main/java/mtr/MTR.java"));
		while (registrations.find()) {
			final Map<String, String> ids = registrations.group(2).equals("Blocks") ? blockIds : itemIds;
			final String previous = ids.put(registrations.group(3), registrations.group(1));
			require(previous == null || previous.equals(registrations.group(1)), "Conflicting legacy registration names");
		}
		final Map<String, Object> extensions = new LinkedHashMap<>();
		final Binding binding = new Binding();
		binding.setVariable("rootProject", Map.of("sharedRoot", root.toFile(), "ext", extensions));
		new GroovyShell(binding).evaluate("class GradleException extends RuntimeException { GradleException(String message) { super(message) } }\n" + read(root, "versions/26.2/registration-port.gradle"));
		final Closure<?> transform = (Closure<?>) extensions.get("transformMc26RegistrationSource");
		final String blocks = transform.call(read(root, "common/src/main/java/mtr/Blocks.java")).toString();
		final String items = transform.call(read(root, "common/src/main/java/mtr/Items.java")).toString();
		verifyIds(blocks, blockIds);
		verifyIds(items, itemIds);
		require(blockIds.get("PSD_DOOR_1").equals("psd_door"), "PSD door legacy fixture changed");
		require(itemIds.get("RAILWAY_DASHBOARD").equals("dashboard"), "Dashboard legacy fixture changed");
		require(!Pattern.compile("new (?:Block|SlabBlock)\\(BlockBehaviour\\.Properties").matcher(blocks).find(), "Direct vanilla block constructors bypass registration properties");
		for (String mapper : new String[]{"BlockMapper", "BlockDirectionalMapper"}) {
			final String transformed = transform.call(read(root, "common/src/main/java/mtr/mappings/" + mapper + ".java")).toString();
			require(transformed.contains("super(RegistrationContext.blockProperties(properties));"), mapper + " constructor bypasses registration properties");
		}
		for (String platform : new String[]{"fabric/src/main/java/mtr/MTRFabric.java", "forge/src/main/java/mtr/MTRForge.java"}) {
			final String transformed = transform.call(read(root, platform)).toString();
			require(!transformed.contains("RegistryUtilities.createItemProperties("), platform + " block items bypass their explicit registration ids");
			require(transformed.contains("createBlockItemProperties(path, block.get())"), platform + " block items do not preserve custom block names");
			require(!transformed.contains("new BlockItem("), platform + " block items bypass the tooltip bridge");
		}
		for (String stationColor : new String[]{"BlockStationColor", "BlockStationColorSlab"}) {
			final String transformed = transform.call(read(root, "common/src/main/java/mtr/block/" + stationColor + ".java")).toString();
			require(transformed.contains("super(mtr.mappings.RegistrationContext.stationColorProperties(settings));"), stationColor + " lost its vanilla description key");
		}
		final String railwaySign = transform.call(read(root, "common/src/main/java/mtr/block/BlockRailwaySign.java")).toString();
		require(railwaySign.contains("super(Properties.of().overrideDescription(\"block.mtr.railway_sign\")"), "Railway sign lost its shared description key");
		final String waterItem = transform.call(read(root, "common/src/main/java/mtr/mappings/PlaceOnWaterBlockItem.java")).toString();
		require(waterItem.contains("super(block, properties.overrideDescription(block.getDescriptionId()));"), "Place-on-water item lost its block description key");

		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// Vanilla registries are frozen after bootstrap. Check the real properties
		// consumed by constructors without unfreezing registries or mocking blocks.
		for (String path : blockIds.values()) {
			final BlockBehaviour.Properties properties = new RegistryObject<>(path, () -> RegistrationContext.blockProperties(BlockBehaviour.Properties.of())).get();
			require(description(properties).equals("block.mtr." + path), "Block properties changed their registration id: " + path);
		}
		for (String path : itemIds.values()) {
			final Item.Properties properties = new RegistryObject<>(path, RegistrationContext::itemProperties).get();
			require(properties.effectiveModel().equals(id(path)), "Item model changed its registration id: " + path);
			require(description(properties).equals("item.mtr." + path), "Item properties changed their registration id: " + path);
		}
		final AtomicInteger calls = new AtomicInteger();
		final RegistryObject<BlockBehaviour.Properties> cached = new RegistryObject<>("cached", () -> {
			calls.incrementAndGet();
			return RegistrationContext.blockProperties(BlockBehaviour.Properties.of());
		});
		require(cached.get() == cached.get() && calls.get() == 1, "Registry objects must construct once");
		RegistrationContext.construct(id("outer"), () -> {
			final Item.Properties inner = new RegistryObject<>("inner", RegistrationContext::itemProperties).get();
			require(inner.effectiveModel().equals(id("inner")), "Nested registration did not use its own id");
			require(RegistrationContext.itemProperties().effectiveModel().equals(id("outer")), "Nested registration did not restore its caller id");
			try {
				RegistrationContext.construct(id("throwing"), () -> { throw new IllegalStateException("expected"); });
				throw new AssertionError("Supplier exception was swallowed");
			} catch (IllegalStateException expected) {
				require(expected.getMessage().equals("expected"), "Unexpected supplier exception");
			}
			require(RegistrationContext.itemProperties().effectiveModel().equals(id("outer")), "Throwing nested registration did not restore its caller id");
			final AtomicReference<Throwable> threadFailure = new AtomicReference<>();
			final Thread independent = Thread.ofPlatform().start(() -> {
				try {
					expectNoContext();
				} catch (Throwable exception) {
					threadFailure.set(exception);
				}
			});
			try {
				independent.join();
			} catch (InterruptedException exception) {
				throw new AssertionError(exception);
			}
			require(threadFailure.get() == null, "Registration context leaked into another thread: " + threadFailure.get());
			return null;
		});
		expectNoContext();
		try {
			RegistrationContext.construct(id("failure"), () -> { throw new IllegalStateException("expected"); });
		} catch (IllegalStateException expected) {
			require(expected.getMessage().equals("expected"), "Unexpected supplier exception");
		}
		expectNoContext();
		final BlockBehaviour.Properties stationColor = RegistrationContext.construct(id("station_color_andesite"), () -> RegistrationContext.stationColorProperties(BlockBehaviour.Properties.of()));
		require(description(stationColor).equals("block.minecraft.andesite"), "Station-color block lost its vanilla name");
		final BlockBehaviour.Properties stationSlab = RegistrationContext.construct(id("station_color_andesite_slab"), () -> RegistrationContext.stationColorProperties(BlockBehaviour.Properties.of()));
		require(description(stationSlab).equals("block.minecraft.andesite_slab"), "Station-color slab lost its vanilla name");
		final Item.Properties stationItem = RegistrationContext.blockItemProperties(id("station_color_andesite"), Blocks.ANDESITE);
		require(description(stationItem).equals("block.minecraft.andesite"), "Block item lost its block name");
		require(stationItem.effectiveModel().equals(id("station_color_andesite")), "Custom block name changed item model id");
		System.out.println("PASS: " + blockIds.size() + " block ids, " + itemIds.size() + " item ids, generated constructor hooks, property keys, lazy cache, nested/throwing/thread-local context and block-item names");
	}

	private static String description(Object properties) throws Exception {
		final var method = properties.getClass().getDeclaredMethod("effectiveDescriptionId");
		method.setAccessible(true);
		return (String) method.invoke(properties);
	}

	private static void verifyIds(String source, Map<String, String> expected) {
		final Map<String, String> actual = new LinkedHashMap<>();
		final Matcher fields = Pattern.compile("RegistryObject<\\w+> ([A-Z0-9_]+) = new RegistryObject<>\\(\"([^\"]+)\", ").matcher(source);
		while (fields.find()) {
			actual.put(fields.group(1), fields.group(2));
		}
		require(actual.equals(expected), "Generated registration ids do not exactly match MTR.init");
	}

	private static void expectNoContext() {
		try {
			RegistrationContext.itemProperties();
			throw new AssertionError("Registration context leaked outside construction");
		} catch (NullPointerException expected) {
			require(expected.getMessage().contains("without its registration identifier"), "Unexpected missing-context error");
		}
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath("mtr", path);
	}

	private static String read(Path root, String path) throws Exception {
		return Files.readString(root.resolve(path));
	}

	private static void require(boolean condition, String message) {
		if (!condition) {
			throw new AssertionError(message);
		}
	}
}
