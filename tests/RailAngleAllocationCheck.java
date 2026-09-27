package mtr.data;

import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;

/** Gate enum-array allocations without depending on JIT escape analysis or wall-clock timing. */
public final class RailAngleAllocationCheck {
    private static volatile RailAngle sink;
    public static void main(String[] args) throws Exception {
        if (!ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Xint")) throw new AssertionError("Use -Xint");
        if (!Path.of(RailAngle.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(Path.of(args[0]).toRealPath())) throw new AssertionError("Wrong implementation");
        ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        if (!bean.isThreadAllocatedMemorySupported()) throw new AssertionError("Allocation accounting unavailable");
        bean.setThreadAllocatedMemoryEnabled(true);
        run(4096);
        long thread = Thread.currentThread().threadId(), before = bean.getThreadAllocatedBytes(thread);
        run(32768);
        long allocated = bean.getThreadAllocatedBytes(thread) - before;
        System.out.println("ANGLE_ALLOCATION: " + allocated + " bytes / 32768 calls = " + allocated / 32768.0 + " bytes/call (-Xint)");
        if (args.length == 1 && allocated > 1024) throw new AssertionError("Enum-array copies reintroduced: " + allocated);
        if (args.length > 1 && !args[1].equals("--baseline")) throw new IllegalArgumentException("Unknown option");
    }
    private static void run(int count) { for (int i = 0; i < count; i++) sink = RailAngle.fromAngle(i % 720 - 360F); }
}
