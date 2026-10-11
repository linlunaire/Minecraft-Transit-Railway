package mtr.data;

import com.sun.management.ThreadMXBean;
import net.minecraft.core.BlockPos;

import java.lang.management.ManagementFactory;
import java.nio.file.Path;

/** Interpreter-only allocation gate: no timing or JIT escape-analysis claim. */
public final class SavedRailAllocationCheck {
    private static volatile BlockPos sink;

    public static void main(String[] args) throws Exception {
        if (!ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Xint")) throw new AssertionError("Run with -Xint for reproducible allocation accounting");
        if (!Path.of(SavedRailBase.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(Path.of(args[0]).toRealPath())) throw new AssertionError("Wrong implementation");
        ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        if (!bean.isThreadAllocatedMemorySupported()) throw new AssertionError("Thread allocation accounting is unavailable");
        bean.setThreadAllocatedMemoryEnabled(true);
        Platform[] platforms = new Platform[64];
        BlockPos[] starts = new BlockPos[64];
        for (int i = 0; i < platforms.length; i++) {
            starts[i] = new BlockPos(i * 31, 64, -i);
            platforms[i] = new Platform(i + 1, TransportMode.TRAIN, starts[i], new BlockPos(i * 31 + 19, 65, i));
        }
        run(platforms, starts, 64);
        long thread = Thread.currentThread().threadId(), before = bean.getThreadAllocatedBytes(thread);
        run(platforms, starts, 512);
        long allocated = bean.getThreadAllocatedBytes(thread) - before;
        int calls = 512 * platforms.length;
        System.out.println("ENDPOINT_ALLOCATION: " + allocated + " bytes / " + calls + " calls = " + (allocated / (double) calls) + " bytes/call (-Xint)");
        if (args.length == 1 && allocated > calls * 80L) throw new AssertionError("Endpoint lookup reintroduced full-set snapshots: " + allocated);
    }

    private static void run(Platform[] platforms, BlockPos[] starts, int rounds) {
        for (int round = 0; round < rounds; round++) for (int i = 0; i < platforms.length; i++) sink = platforms[i].getOtherPosition(starts[i]);
    }
}
