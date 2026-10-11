package mtr.mappings;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public interface NetworkUtilities {

	Map<ResourceLocation, PayloadType> PAYLOAD_TYPES = new HashMap<>();

	static void registerReceiverS2C(ResourceLocation id, NetworkManager.NetworkReceiver receiver) {
		final PayloadType payloadType = getPayloadType(getS2CId(id));
		NetworkManager.registerReceiver(NetworkManager.s2c(), payloadType.type, payloadType.codec, (payload, context) -> receiver.receive(payload.createBuffer(context.registryAccess()), context));
	}

	static void registerReceiverC2S(ResourceLocation id, PacketCallback packetCallback) {
		final PayloadType payloadType = getPayloadType(id);
		NetworkManager.registerReceiver(NetworkManager.c2s(), payloadType.type, payloadType.codec, (payload, context) -> {
			final Player player = context.getPlayer();
			if (player != null) {
				packetCallback.packetCallback(player.getServer(), (ServerPlayer) player, payload.createBuffer(context.registryAccess()));
			}
		});
	}

	static void sendToPlayer(ServerPlayer player, ResourceLocation id, FriendlyByteBuf packet) {
		NetworkManager.sendToPlayer(player, createPayload(id, packet));
	}

	static void sendToPlayers(Iterable<? extends Player> players, Player excludedPlayer, ResourceLocation id, FriendlyByteBuf packet) {
		final UUID excludedPlayerId = excludedPlayer == null ? null : excludedPlayer.getUUID();
		final List<ServerPlayer> playersToSend = new ArrayList<>();
		for (final Player player : players) {
			if (excludedPlayerId == null || !player.getUUID().equals(excludedPlayerId)) {
				playersToSend.add((ServerPlayer) player);
			}
		}
		if (!playersToSend.isEmpty()) {
			NetworkManager.sendToPlayers(playersToSend, createPayload(id, packet));
		}
	}

	static void sendToServer(ResourceLocation id, FriendlyByteBuf packet) {
		final PayloadType payloadType = getPayloadType(id);
		NetworkManager.sendToServer(new RawPayload(payloadType.type, getBytes(packet)));
	}

	private static PayloadType getPayloadType(ResourceLocation id) {
		return PAYLOAD_TYPES.computeIfAbsent(id, PayloadType::new);
	}

	private static RawPayload createPayload(ResourceLocation id, FriendlyByteBuf packet) {
		packet.resetReaderIndex();
		final PayloadType payloadType = getPayloadType(getS2CId(id));
		return new RawPayload(payloadType.type, getBytes(packet));
	}

	private static byte[] getBytes(FriendlyByteBuf packet) {
		final byte[] bytes = new byte[packet.readableBytes()];
		packet.getBytes(packet.readerIndex(), bytes);
		return bytes;
	}

	private static ResourceLocation getS2CId(ResourceLocation id) {
		return ResourceLocation.parse(id + "_s2c");
	}

	class PayloadType {

		private final CustomPacketPayload.Type<RawPayload> type;
		private final StreamCodec<RegistryFriendlyByteBuf, RawPayload> codec;

		private PayloadType(ResourceLocation id) {
			type = new CustomPacketPayload.Type<>(id);
			codec = StreamCodec.of((buffer, payload) -> buffer.writeByteArray(payload.bytes), buffer -> new RawPayload(type, buffer.readByteArray()));
		}
	}

	record RawPayload(CustomPacketPayload.Type<RawPayload> type, byte[] bytes) implements CustomPacketPayload {

		private RegistryFriendlyByteBuf createBuffer(RegistryAccess registryAccess) {
			return new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(bytes), registryAccess);
		}
	}

	@FunctionalInterface
	interface PacketCallback {
		void packetCallback(MinecraftServer server, ServerPlayer player, FriendlyByteBuf packet);
	}
}
