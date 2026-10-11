package mtr.data;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DimensionDataStorage;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/** Drives Minecraft's real SavedData dispatch, not a direct call to the MTR writer. */
public final class RailPersistenceCompatibilityCheck {
    private static final Unsafe UNSAFE = unsafe();
    private static final BlockPos START = new BlockPos(-32, 64, 16);
    private static final BlockPos END = new BlockPos(32, 64, 16);
    private static boolean directControl;

    public static void main(String[] args) throws Exception {
        directControl = Arrays.asList(args).contains("--direct-control");
        if (args.length > 0 && !args[0].startsWith("--")) {
            require(Files.isSameFile(Path.of(args[0]), Path.of(RailwayData.class.getProtectionDomain().getCodeSource().getLocation().toURI())), "Wrong production RailwayData source");
        }
        try {
            Class.forName("net.neoforged.fml.loading.LoadingModList")
                    .getMethod("of", List.class, List.class, List.class, List.class, Map.class)
                    .invoke(null, List.of(), List.of(), List.of(), List.of(), Map.of());
        } catch (ClassNotFoundException ignored) {
            // The common/Fabric classpath does not include NeoForge.
        }
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Path directory = Files.createTempDirectory("mtr-rail-persistence-");
        try {
            checkStorageDispatch(directory.resolve("autosave"), true);
            checkStorageDispatch(directory.resolve("shutdown"), false);
            System.out.println(directControl ? "PASS: direct writer control preserves both rail connections" : "PASS: native DimensionDataStorage save, rail save/reload, repeated autosaves, synchronous shutdown and NBT settings");
        } finally {
            awaitNeoForgeWrites();
            net.minecraft.Util.ioPool().shutdown();
            try (var paths = Files.walk(directory)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.delete(path);
            }
        }
    }

    private static void checkStorageDispatch(Path root, boolean running) throws Exception {
        Files.createDirectories(root.resolve("data"));
        TestServer server = allocate(TestServer.class);
        server.running = running;
        TestLevel level = allocate(TestLevel.class);
        level.server = server;
        Map<BlockPos, Map<BlockPos, Rail>> rails = new HashMap<>();
        RailwayData data = newData(level, rails);
        RailwayDataFileSaveModule module = new RailwayDataFileSaveModule(data, level, rails, root.resolve("mtr"), new SignalBlocks());
        write(data, "railwayDataFileSaveModule", module);
        write(data, "railwayDataLoggingModule", new RailwayDataLoggingModule(data, level, rails, root.resolve("mtr")));
        write(data, "useTimeAndWindSync", true);
        connect(rails, START, END);
        DimensionDataStorage storage = new DimensionDataStorage(root.resolve("data").toFile(), null, null);
        storage.set("mtr_train_data", data);
        if (directControl) {
            module.fullSave();
            assertReload(level, root.resolve("mtr"), START, END, "direct writer control");
            return;
        }
        storage.save();
        awaitNeoForgeWrites();
        if (running) drain(module);
        assertReload(level, root.resolve("mtr"), START, END, running ? "autosave" : "shutdown (without another tick)");
        Path settings = root.resolve("data/mtr_train_data.dat");
        require(Files.exists(settings), "Rail saving lost the Minecraft NBT settings file");
        require(NbtIo.readCompressed(settings, NbtAccounter.unlimitedHeap()).getCompound("data").getBoolean("use_time_and_wind_sync"), "Rail saving lost the time-sync setting");
        require(data.isDirty(), "Minecraft cleared the flag needed for subsequent rail saves");
        if (running) {
            BlockPos extension = new BlockPos(96, 64, 16);
            connect(rails, END, extension);
            // Rail edits do not set SavedData.dirty; the save hook must remain active.
            storage.save();
            awaitNeoForgeWrites();
            drain(module);
            assertReload(level, root.resolve("mtr"), END, extension, "second autosave");
            BlockPos finalNode = new BlockPos(160, 64, 16);
            connect(rails, extension, finalNode);
            server.running = false;
            storage.save();
            awaitNeoForgeWrites();
            assertReload(level, root.resolve("mtr"), extension, finalNode, "shutdown after autosave (without another tick)");
        }
    }

    private static void assertReload(TestLevel level, Path root, BlockPos start, BlockPos end, String phase) throws Exception {
        Map<BlockPos, Map<BlockPos, Rail>> reloaded = new HashMap<>();
        RailwayData restored = newData(level, reloaded);
        new RailwayDataFileSaveModule(restored, level, reloaded, root, new SignalBlocks()).load();
        require(reloaded.containsKey(start) && reloaded.get(start).containsKey(end)
                && reloaded.containsKey(end) && reloaded.get(end).containsKey(start),
                phase + " lost rail connections after reload; in-memory nodes=2, persisted connections="
                        + reloaded.values().stream().mapToInt(Map::size).sum());
    }

    private static void drain(RailwayDataFileSaveModule module) {
        for (int tick = 0; tick < 1000; tick++) if (module.autoSaveTick()) return;
        throw new AssertionError("Save did not complete in 1000 ticks");
    }

    private static void awaitNeoForgeWrites() throws Exception {
        try {
            Class.forName("net.neoforged.neoforge.common.IOUtilities").getMethod("waitUntilIOWorkerComplete").invoke(null);
        } catch (ClassNotFoundException ignored) {
            // Vanilla/Fabric's SavedData writer is synchronous.
        }
    }

    private static void connect(Map<BlockPos, Map<BlockPos, Rail>> rails, BlockPos start, BlockPos end) {
        rails.computeIfAbsent(start, ignored -> new HashMap<>()).put(end, new Rail(start, RailAngle.E, end, RailAngle.E, RailType.WOODEN, TransportMode.TRAIN));
        rails.computeIfAbsent(end, ignored -> new HashMap<>()).put(start, new Rail(end, RailAngle.E, start, RailAngle.E, RailType.WOODEN, TransportMode.TRAIN));
    }

    private static RailwayData newData(TestLevel level, Map<BlockPos, Map<BlockPos, Rail>> rails) throws Exception {
        RailwayData data = allocate(RailwayData.class);
        for (String name : List.of("stations", "platforms", "sidings", "routes", "depots", "lifts")) write(data, name, new HashSet<>());
        write(data, "dataCache", new DataCache(data.stations, data.platforms, data.sidings, data.routes, data.depots, data.lifts));
        write(data, "world", level);
        write(data, "rails", rails);
        write(data, "trainDelays", new HashMap<>());
        write(data, "realTimeSync", new RealTimeSync());
        return data;
    }

    private static final class TestServer extends DedicatedServer {
        private boolean running;
        private TestServer() { super(null, null, null, null, null, null, null, null); }
        @Override public boolean isRunning() { return running; }
        @Override public boolean isStopped() { return !running; }
    }

    private static final class TestLevel extends ServerLevel {
        private MinecraftServer server;
        private TestLevel() { super(null, null, null, null, null, null, null, false, 0, null, false, null); }
        @Override public MinecraftServer getServer() { return server; }
        @Override public ResourceKey<Level> dimension() { return Level.OVERWORLD; }
    }

    private static <T> T allocate(Class<T> type) throws InstantiationException { return type.cast(UNSAFE.allocateInstance(type)); }
    private static void write(RailwayData data, String name, Object value) throws Exception {
        Field field = RailwayData.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(data, value);
    }
    private static Unsafe unsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (ReflectiveOperationException exception) { throw new ExceptionInInitializerError(exception); }
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
