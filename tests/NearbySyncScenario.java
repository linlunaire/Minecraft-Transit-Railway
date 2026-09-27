package mtr.data;

import net.minecraft.resources.Identifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.world.entity.player.Player;
import com.mojang.authlib.GameProfile;
import mtr.packet.IPacket;
import sun.misc.Unsafe;
import java.util.*;

/** Scenario is defined beside the selected production implementation. */
public final class NearbySyncScenario {
    private static final List<String> records = new ArrayList<>();
    private static boolean ownership;
    private static int assertions;
    public static String run(boolean checkOwnership) throws Exception {
        ownership = checkOwnership;
        var sync = create();
        sync.newDataSetInPlayerRange.put(null, new HashSet<>());
        sync.tick();
        if (ownership && !NearbySyncCompatibilityCheck.buffers.isEmpty()) throw new AssertionError("Idle sync allocated " + NearbySyncCompatibilityCheck.buffers.size() + " packet buffers; live=" + NearbySyncCompatibilityCheck.liveBuffers());
        capture("idle");
        net.minecraft.SharedConstants.tryDetectVersion(); net.minecraft.server.Bootstrap.bootStrap();
        var field = Unsafe.class.getDeclaredField("theUnsafe"); field.setAccessible(true); Unsafe unsafe = (Unsafe) field.get(null);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 80; i++) { PlayerProbe player = (PlayerProbe) unsafe.allocateInstance(PlayerProbe.class); player.number = i; players.add(player); }
        DataProbe first = new DataProbe(1, 37), second = new DataProbe(2, 61);
        sync = create();
        for (Player player : players) sync.newDataSetInPlayerRange.put(player, new LinkedHashSet<>(List.of(first, second)));
        sync.tick(); require(first.writes == 1 && second.writes == 1, "Shared data serialized once per player instead of once per tick"); capture("80-players-cold");
        sync.tick(); require(NearbySyncCompatibilityCheck.packets.isEmpty(), "Unchanged tick sent data");
        if (ownership) require(NearbySyncCompatibilityCheck.buffers.isEmpty(), "Unchanged 80-player tick allocated packet buffers"); capture("80-players-warm");
        sync.startTick();
        for (Player player : players) sync.newDataSetInPlayerRange.put(player, new LinkedHashSet<>(List.of(first, second)));
        sync.tick(); require(first.writes == 1 && second.writes == 1, "Stable next tick serialized unchanged objects"); capture("stable-next-tick");
        first.tag++;
        sync.dataSetToSync.add(first); sync.tick(); require(first.writes == 2 && second.writes == 1, "Dirty-only serialization changed"); capture("dirty");
        sync.startTick();
        for (Player player : players) sync.newDataSetInPlayerRange.put(player, new LinkedHashSet<>(List.of(second)));
        sync.tick(); capture("partial-removal");
        sync.startTick(); sync.tick(); capture("all-removed");

        sync = create(); Set<NameColorDataBase> live = new LinkedHashSet<>(List.of(first));
        sync.newDataSetInPlayerRange.put(null, live); sync.tick(); capture("live-before");
        live.clear(); sync.tick(); require(NearbySyncCompatibilityCheck.packets.isEmpty(), "Published sets unexpectedly copied"); capture("live-mutated");
        for (int[] sizes : List.of(new int[]{0}, new int[]{IPacket.MAX_PACKET_BYTES}, new int[]{IPacket.MAX_PACKET_BYTES + 1},
                new int[]{IPacket.MAX_PACKET_BYTES - 1, 1}, new int[]{IPacket.MAX_PACKET_BYTES / 2, IPacket.MAX_PACKET_BYTES / 2},
                new int[]{0, 13, 0, IPacket.MAX_PACKET_BYTES, 3}, new int[]{IPacket.MAX_PACKET_BYTES + 7, 0})) {
            sync = create(); Set<NameColorDataBase> objects = new LinkedHashSet<>();
            for (int i = 0; i < sizes.length; i++) objects.add(new DataProbe(10 + i, sizes[i]));
            sync.newDataSetInPlayerRange.put(null, objects); sync.tick(); capture("sizes:" + Arrays.toString(sizes));
        }
        sync = create(); DataProbe skipped = new DataProbe(42, 15); skipped.skip = 5;
        sync.newDataSetInPlayerRange.put(null, new LinkedHashSet<>(List.of(skipped))); sync.tick(); capture("reader-index");
        sync = create(); DataProbe failure = new DataProbe(99, 3); failure.fail = true;
        sync.newDataSetInPlayerRange.put(null, new LinkedHashSet<>(List.of(failure)));
        var failingSync = sync; fails(() -> failingSync.tick()); capture("serialization-failure");
        failure.fail = false; sync.tick(); require(failure.writes == 2, "Failed serialization incorrectly cached"); capture("serialization-retry");
        sync = create(); failure = new DataProbe(98, 7);
        sync.newDataSetInPlayerRange.put(null, new LinkedHashSet<>(List.of(failure)));
        NearbySyncCompatibilityCheck.failSend = true; var sendFailure = sync; fails(() -> sendFailure.tick()); capture("send-failure");
        sync.tick(); require(failure.writes == 1, "Send failure discarded serialized data cache"); capture("send-retry");
        sync.startTick(); NearbySyncCompatibilityCheck.failSend = true; fails(() -> sendFailure.tick()); capture("delete-failure");
        sync.tick(); capture("delete-retry");

        sync = create(); DataProbe full = new DataProbe(70, IPacket.MAX_PACKET_BYTES - 1), tail = new DataProbe(71, 1);
        sync.newDataSetInPlayerRange.put(null, new LinkedHashSet<>(List.of(full, tail)));
        NearbySyncCompatibilityCheck.failSend = true; var splitFailure = sync;
        fails(() -> splitFailure.tick()); capture("split-send-failure");
        sync.tick(); require(full.writes == 1 && tail.writes == 1, "Split send retry reserialized cached objects"); capture("split-send-retry");

        // A keep-list larger than the protocol budget is dropped, including its owned buffer.
        sync = create(); Set<NameColorDataBase> old = new LinkedHashSet<>();
        for (int i = 0; i <= IPacket.MAX_PACKET_BYTES / Long.BYTES; i++) old.add(new DataProbe(i + 1000, 0));
        sync.newDataSetInPlayerRange.put(null, old); sync.tick(); capture("large-empty-initial");
        Set<NameColorDataBase> retained = new LinkedHashSet<>(old); retained.remove(old.iterator().next());
        sync.newDataSetInPlayerRange.put(null, retained); sync.tick(); require(NearbySyncCompatibilityCheck.packets.isEmpty(), "Oversized keep-list was sent"); capture("large-keep-list-dropped");
        System.out.println("Nearby sync assertions: " + assertions + ", records: " + records.size() + ", 80-player sharing and packet-boundary scenarios");
        return String.join("\n", records) + "\n";
    }
    private static UpdateNearbyMovingObjects<NameColorDataBase> create() { return new UpdateNearbyMovingObjects<>(Identifier.parse("mtr:delete_test"), Identifier.parse("mtr:update_test")); }
    private static void capture(String name) {
        NearbySyncCompatibilityCheck.verifySnapshots();
        if (name.equals("80-players-warm")) System.out.println("IDLE_SYNC_BUFFERS: " + NearbySyncCompatibilityCheck.buffers.size() + " allocated / " + NearbySyncCompatibilityCheck.liveBuffers() + " live after 80-player unchanged tick");
        if (ownership) require(NearbySyncCompatibilityCheck.liveBuffers() == 0, name + " leaked " + NearbySyncCompatibilityCheck.liveBuffers() + " buffers");
        records.add(name + "\t" + NearbySyncCompatibilityCheck.packets); NearbySyncCompatibilityCheck.clear();
    }
    private static void fails(Runnable action) { try { action.run(); } catch (RuntimeException error) { require(error == NearbySyncCompatibilityCheck.FAILURE, "Exception identity changed"); return; } throw new AssertionError("Expected failure"); }
    private static void require(boolean condition, String message) { assertions++; if (!condition) throw new AssertionError(message); }
    public static final class DataProbe extends NameColorDataBase {
        final int size; int writes, tag, skip; boolean fail;
        DataProbe(long id, int size) { super(id); this.size = size; tag = (int) id; }
        @Override public void writePacket(FriendlyByteBuf buffer) {
            writes++; buffer.writeZero(size); if (size > 0) buffer.setByte(0, tag); buffer.readerIndex(skip);
            if (fail) throw NearbySyncCompatibilityCheck.FAILURE;
        }
        @Override protected boolean hasTransportMode() { return false; }
    }
    public static final class PlayerProbe extends ServerPlayer {
        int number;
        private PlayerProbe() { super(null, null, (GameProfile) null, (ClientInformation) null); }
        @Override public UUID getUUID() { return new UUID(0, number); }
        @Override public int hashCode() { return number; }
        @Override public boolean equals(Object other) { return this == other; }
    }
}
