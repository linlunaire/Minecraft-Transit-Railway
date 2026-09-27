package mtr.data;

import java.nio.file.*;
import java.util.*;

public final class KotlinRailTypeCompatibilityCheck {
    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        if (!Path.of(RailType.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(source)) throw new AssertionError("Wrong RailType implementation");
        List<String> records = new ArrayList<>();
        for (RailType type : RailType.values()) {
            if (RailType.valueOf(type.name()) != type) throw new AssertionError("Enum identity changed");
            records.add(type + "\t" + type.ordinal() + "," + type.speedLimit + "," + Float.floatToRawIntBits(type.maxBlocksPerTick) + "," + type.color + "," + type.hasSavedRail + "," + type.canAccelerate + "," + type.hasSignal + "," + type.railSlopeStyle);
        }
        for (TransportMode mode : TransportMode.values()) records.add("default:" + mode + "\t" + Float.floatToRawIntBits(RailType.getDefaultMaxBlocksPerTick(mode)));
        records.add("slopes\t" + Arrays.toString(RailType.RailSlopeStyle.values()));
        RailType[] values = RailType.values(); values[0] = null;
        if (RailType.values()[0] != RailType.WOODEN) throw new AssertionError("values leaked enum storage");
        try { RailType.getDefaultMaxBlocksPerTick(null); throw new AssertionError("Null mode accepted"); } catch (NullPointerException expected) { }
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3 && args[2].equals("--record")) {
            if (Files.isDirectory(source)) throw new AssertionError("Only record original Java JARs");
            Files.writeString(Path.of(args[0]), actual);
        } else if (!Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual)) throw new AssertionError("RailType differs from Java golden");
        System.out.println("PASS: " + records.size() + " RailType exact-float/enum/color/flag/default records and null/values isolation");
    }
}
