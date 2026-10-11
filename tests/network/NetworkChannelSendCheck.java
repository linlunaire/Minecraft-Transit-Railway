package mtr.mappings;

import dev.architectury.impl.NetworkAggregator;
import dev.architectury.utils.Env;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/** Exercises real MTR/Architectury sends and NeoForge's channel rejection, without a server or sockets. */
public final class NetworkChannelSendCheck {
    private static final ResourceLocation RAILS = ResourceLocation.fromNamespaceAndPath("mtr", "write_rails");
    private static final ResourceLocation RAILS_WIRE = ResourceLocation.fromNamespaceAndPath("mtr", "write_rails_s2c");
    private static final byte[] DATA = new byte[]{3, 1, 4, 1, 5, 9};

    public static void main(String[] args) throws Exception {
        var loadingMods = Class.forName("net.neoforged.fml.loading.LoadingModList");
        loadingMods.getMethod("of", List.class, List.class, List.class, List.class, Map.class)
                .invoke(null, List.of(), List.of(), List.of(), List.of(), Map.of());
        net.minecraft.SharedConstants.tryDetectVersion();
        net.minecraft.server.Bootstrap.bootStrap();
        NetworkTestAdaptor.install();
        NetworkUtilities.registerServerS2CTypes(Env.SERVER, RAILS);
        var unsupported = player(1, Set.of());
        var supported = player(2, Set.of(RAILS_WIRE));
        var excluded = player(3, Set.of(RAILS_WIRE));
        var wrongId = player(4, Set.of(RAILS));
        try {
            try (var buffer = borrowedBuffer()) {
                NetworkUtilities.sendToPlayer(unsupported, RAILS, buffer.value);
                require(unsupported.sink().packets.isEmpty(), "Sent an unnegotiated payload");
                buffer.checkBorrowed();
            }
            try (var buffer = borrowedBuffer()) {
                NetworkUtilities.sendToPlayer(supported, RAILS, buffer.value);
                require(supported.sink().packets.size() == 1, "Dropped a negotiated unicast");
                checkPacket(supported.sink().packets.getFirst());
                buffer.checkBorrowed();
            }
            try (var buffer = borrowedBuffer()) {
                NetworkUtilities.sendToPlayers(List.of(unsupported, supported, excluded, wrongId), excluded, RAILS, buffer.value);
                require(supported.sink().packets.size() == 2, "Dropped a negotiated broadcast recipient");
                require(unsupported.sink().packets.isEmpty() && excluded.sink().packets.isEmpty()
                        && wrongId.sink().packets.isEmpty(), "Broadcast sent to an unsupported/excluded recipient");
                checkPacket(supported.sink().packets.getLast());
                buffer.checkBorrowed();
            }
            try (var buffer = borrowedBuffer()) {
                NetworkUtilities.sendToPlayers(List.of(unsupported, wrongId), null, RAILS, buffer.value);
                require(unsupported.sink().packets.isEmpty() && wrongId.sink().packets.isEmpty(),
                        "All-unsupported broadcast was sent");
                buffer.checkBorrowed();
            }
            // The native guard must still reject a deliberately unnegotiated packet.
            try {
                NetworkRegistry.checkPacket(supported.sink().packets.getFirst(), unsupported.sink());
                throw new AssertionError("NeoForge rejection negative control passed unexpectedly");
            } catch (UnsupportedOperationException expected) {
                require(expected.getMessage().equals("Payload mtr:write_rails_s2c may not be sent to the client!"),
                        "Unexpected native rejection: " + expected.getMessage());
            }
            System.out.println("PASS: unsupported unicast and mixed/all-unsupported broadcasts are skipped; negotiated packets keep ID/bytes, exclusions and borrowed buffers; actual NeoForge rejection remains active");
        } finally {
            for (var player : List.of(unsupported, supported, excluded, wrongId)) {
                player.sink().channel.finishAndReleaseAll();
            }
        }
    }

    private static FakePlayer player(long id, Set<ResourceLocation> channels) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        var unsafe = (Unsafe) unsafeField.get(null);
        // Only allocation/transport are fixtures. Payload construction, codecs and native guards are real.
        var player = (FakePlayer) unsafe.allocateInstance(FakePlayer.class);
        var sink = (RecordingListener) unsafe.allocateInstance(RecordingListener.class);
        sink.channel = new EmbeddedChannel();
        sink.connectionFixture = new Connection(PacketFlow.SERVERBOUND);
        var connectionChannel = Connection.class.getDeclaredField("channel");
        connectionChannel.setAccessible(true);
        connectionChannel.set(sink.connectionFixture, sink.channel);
        sink.packets = new ArrayList<>();
        NetworkRegistry.onMinecraftRegister(sink.connectionFixture, channels);
        player.id = new UUID(0, id);
        player.connection = sink;
        return player;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void checkPacket(Packet<?> packet) {
        require(packet instanceof ClientboundCustomPayloadPacket, "Wrong packet direction");
        var payload = ((ClientboundCustomPayloadPacket) packet).payload();
        require(payload.type().id().equals(RAILS_WIRE), "Changed the wire ID");
        require(payload instanceof NetworkAggregator.BufCustomPacketPayload, "Missing Architectury wire payload");
        var wire = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(
                ((NetworkAggregator.BufCustomPacketPayload) payload).payload()), RegistryAccess.EMPTY);
        try {
            StreamCodec codec = NetworkAggregator.S2C_CODECS.get(RAILS_WIRE);
            var decoded = (NetworkUtilities.RawPayload) codec.decode(wire);
            require(Arrays.equals(decoded.bytes(), DATA) && !wire.isReadable(), "Changed payload bytes");
        } finally {
            wire.release();
        }
    }

    private static BorrowedBuffer borrowedBuffer() {
        return new BorrowedBuffer(new FriendlyByteBuf(Unpooled.wrappedBuffer(DATA.clone())));
    }

    private record BorrowedBuffer(FriendlyByteBuf value) implements AutoCloseable {
        void checkBorrowed() { require(value.refCnt() == 1, "Sending released the caller's buffer"); }
        @Override public void close() { value.release(); }
    }

    public static final class FakePlayer extends ServerPlayer {
        private UUID id;
        private FakePlayer() { super(null, null, null, null); }
        @Override public UUID getUUID() { return id; }
        @Override public RegistryAccess registryAccess() { return RegistryAccess.EMPTY; }
        private RecordingListener sink() { return (RecordingListener) connection; }
    }

    public static final class RecordingListener extends ServerGamePacketListenerImpl {
        private Connection connectionFixture;
        private EmbeddedChannel channel;
        private List<Packet<?>> packets;
        private RecordingListener() { super(null, null, null, null); }
        @Override public Connection getConnection() { return connectionFixture; }
        @Override public void send(Packet<?> packet) {
            NetworkRegistry.checkPacket(packet, this);
            packets.add(packet);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
