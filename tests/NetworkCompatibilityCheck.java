package mtr.mappings;

import dev.architectury.impl.NetworkAggregator;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Exercises the real MTR payload factory and Architectury encoding without a game launch. */
public final class NetworkCompatibilityCheck {
	public static void main(String[] args) throws Exception {
		final Path root = Path.of(args[0]);
		final NetworkTestAdaptor adaptor = NetworkTestAdaptor.install();
		final Map<String, Identifier> ids = packetIds(root);
		final Set<Identifier> s2c = declaredIds(root.resolve("common/src/main/java/mtr/MTRClient.java"), "RegistryClient", ids);
		final Set<Identifier> c2s = declaredIds(root.resolve("common/src/main/java/mtr/MTR.java"), "Registry", ids);
		require(s2c.size() == 41 && c2s.size() == 26, "Review changed packet inventory");
		initializeFromEntryPoint(root, Env.SERVER, ids);
		// Exercise the original red path before asserting inventory, so removing the hook reproduces codec-null.
		encode(Identifier.parse("mtr:packet_version_check"), new byte[]{5, '3', '.', '3', '.', '2'});
		registerC2S(c2s);
		require(adaptor.registrations(NetworkManager.Side.S2C).keySet().equals(s2c.stream().map(NetworkCompatibilityCheck::s2cId).collect(Collectors.toSet())), "Dedicated-server S2C types differ from client declarations");
		require(NetworkAggregator.S2C_RECEIVER.isEmpty(), "Dedicated-server initialization registered client handlers");
		final Map<Identifier, byte[]> original = new LinkedHashMap<>();
		final Map<Identifier, CustomPacketPayload> packets = new LinkedHashMap<>();
		for (Identifier id : s2c) {
			final byte[] bytes = sampleBytes(id);
			original.put(id, bytes);
			final CustomPacketPayload packet = encode(id, bytes);
			packets.put(id, wireRoundTrip(adaptor.registration(NetworkManager.Side.S2C, s2cId(id)), packet));
		}
		final Map<Identifier, byte[]> delivered = new LinkedHashMap<>();
		adaptor.reset();
		NetworkUtilities.PAYLOAD_TYPES.clear();
		initializeFromEntryPoint(root, Env.CLIENT, ids);
		require(NetworkAggregator.S2C_CODECS.isEmpty() && adaptor.registrations(NetworkManager.Side.S2C).isEmpty(), "Physical client must not pre-register S2C types");
		registerC2S(c2s);
		for (Identifier id : s2c) {
			NetworkUtilities.registerReceiverS2C(id, (buffer, context) -> {
				final FriendlyByteBuf received = (FriendlyByteBuf) buffer;
				try {
					final byte[] bytes = new byte[received.readableBytes()];
					received.readBytes(bytes);
					delivered.put(id, bytes);
				} finally { received.release(); }
			});
		}
		require(adaptor.registrations(NetworkManager.Side.C2S).keySet().equals(c2s), "C2S IDs changed");
		require(adaptor.registrations(NetworkManager.Side.S2C).size() == 41, "Missing/duplicate client S2C registration");
		for (var packet : packets.entrySet()) {
			deliver(adaptor.registration(NetworkManager.Side.S2C, s2cId(packet.getKey())), packet.getValue());
			require(Arrays.equals(original.get(packet.getKey()), delivered.get(packet.getKey())), "Payload bytes changed for " + packet.getKey());
		}
		// A negative control keeps this harness sensitive to the real missing-codec bug.
		adaptor.reset();
		try {
			encode(Identifier.parse("mtr:packet_version_check"), new byte[0]);
			throw new AssertionError("Missing S2C codec unexpectedly encoded");
		} catch (NullPointerException expected) {
			require(expected.getMessage().contains("codec"), "Unexpected failure in missing-codec control");
		}
		System.out.println("PASS: server/client startup hook, 41 S2C / 26 C2S IDs, no duplicate client registration, actual Architectury encoding/wire codecs/receiver byte round-trips and missing-codec negative control (test loader adaptor; no game connection)");
	}

	private static CustomPacketPayload encode(Identifier id, byte[] bytes) throws Exception {
		final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes.clone()));
		try {
			if (buffer.isReadable()) buffer.readByte(); // The existing S2C factory resets the read position.
			final var factory = NetworkUtilities.class.getDeclaredMethod("createPayload", Identifier.class, FriendlyByteBuf.class);
			factory.setAccessible(true);
			final NetworkUtilities.RawPayload payload = (NetworkUtilities.RawPayload) factory.invoke(null, id, buffer);
			require(payload.type().id().equals(s2cId(id)) && Arrays.equals(payload.bytes(), bytes), "MTR payload ID/bytes changed");
			final var packet = NetworkManager.toPacket(NetworkManager.Side.S2C, payload, RegistryAccess.EMPTY);
			require(packet instanceof ClientboundCustomPayloadPacket, "Wrong vanilla packet direction");
			return ((ClientboundCustomPayloadPacket) packet).payload();
		} finally { buffer.release(); }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static CustomPacketPayload wireRoundTrip(NetworkTestAdaptor.Registration<?> registration, CustomPacketPayload payload) {
		final RegistryFriendlyByteBuf wire = new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY);
		try {
			final StreamCodec codec = registration.codec();
			codec.encode(wire, payload);
			final CustomPacketPayload decoded = (CustomPacketPayload) codec.decode(wire);
			require(!wire.isReadable() && decoded.type().id().equals(payload.type().id()), "Wire codec changed ID or left bytes");
			return decoded;
		} finally { wire.release(); }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static void deliver(NetworkTestAdaptor.Registration<?> registration, CustomPacketPayload payload) {
		final NetworkManager.NetworkReceiver receiver = registration.receiver();
		receiver.receive(payload, new NetworkManager.PacketContext() {
			@Override public Player getPlayer() { return null; }
			@Override public void queue(Runnable action) { action.run(); }
			@Override public Env getEnvironment() { return Env.CLIENT; }
			@Override public RegistryAccess registryAccess() { return RegistryAccess.EMPTY; }
		});
	}

	private static void registerC2S(Set<Identifier> ids) {
		for (Identifier id : ids) NetworkUtilities.registerReceiverC2S(id, (server, player, buffer) -> { });
	}

	private static byte[] sampleBytes(Identifier id) {
		final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
		try {
			buffer.writeUtf(id + " / 列车 🚆");
			final byte[] body = new byte[16385];
			for (int i = 0; i < body.length; i++) body[i] = (byte) (i * 31);
			buffer.writeByteArray(body);
			final byte[] bytes = new byte[buffer.readableBytes()];
			buffer.readBytes(bytes);
			return bytes;
		} finally { buffer.release(); }
	}

	private static Set<Identifier> declaredIds(Path source, String registry, Map<String, Identifier> ids) throws Exception {
		return Pattern.compile(registry + "\\.registerNetworkReceiver\\((PACKET_\\w+),").matcher(Files.readString(source)).results().map(match -> ids.get(match.group(1))).collect(Collectors.toSet());
	}

	private static Map<String, Identifier> packetIds(Path root) throws Exception {
		return Pattern.compile("Identifier (PACKET_\\w+) = Identifier.fromNamespaceAndPath\\(MTR.MOD_ID, \"([^\"]+)\"\\)").matcher(Files.readString(root.resolve("common/src/main/java/mtr/packet/IPacket.java"))).results().collect(Collectors.toMap(match -> match.group(1), match -> Identifier.fromNamespaceAndPath("mtr", match.group(2))));
	}

	private static void initializeFromEntryPoint(Path root, Env environment, Map<String, Identifier> ids) throws Exception {
		final String entryPoint = Files.readString(root.resolve("common/src/main/java/mtr/MTR.java"));
		final var init = Pattern.compile("public static void init\\([\\s\\S]+?\\) \\{([\\s\\S]*?)registerItem\\.accept\\(").matcher(entryPoint);
		if (!init.find()) throw new AssertionError("Review changed MTR initialization boundary");
		final Binding binding = new Binding();
		binding.setVariable("environment", environment);
		ids.forEach(binding::setVariable);
		// Execute the actual pre-registration statements, substituting only the physical environment.
		// This does not run block/entity registration, loader events, or a Minecraft server.
		new GroovyShell(binding).evaluate(init.group(1).replace("dev.architectury.platform.Platform.getEnvironment()", "environment"));
	}

	private static Identifier s2cId(Identifier id) { return Identifier.parse(id + "_s2c"); }
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
