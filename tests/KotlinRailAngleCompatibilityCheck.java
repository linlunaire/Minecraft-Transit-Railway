package mtr.data;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

public final class KotlinRailAngleCompatibilityCheck {
    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        require(Path.of(RailAngle.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath().equals(source), "Wrong angle implementation");
        if (args.length == 3) require(args[2].equals("--record") && Files.isRegularFile(source) && Arrays.stream(RailAngle.class.getDeclaredAnnotations())
                .noneMatch(annotation -> annotation.annotationType().getName().equals("kotlin.Metadata")), "Record only original Java JAR");
        List<String> records = new ArrayList<>();
        RailAngle[] values = RailAngle.values();
        require(values.length == 16 && Arrays.stream(values).map(Enum::name).toList().equals(List.of("E", "SEE", "SE", "SSE", "S", "SSW", "SW", "SWW", "W", "NWW", "NW", "NNW", "N", "NNE", "NE", "NEE")), "Enum order changed");
        for (RailAngle angle : values) {
            records.add(angle + "\t" + Float.floatToRawIntBits(angle.angleDegrees) + ":" + bits(angle.angleRadians) + ":" + bits(angle.sin) + ":" + bits(angle.cos) + ":" + bits(angle.tan) + ":" + bits(angle.halfTan));
            require(angle.getOpposite().getOpposite() == angle && RailAngle.fromAngle(angle.angleDegrees) == angle, "Opposite/angle roundtrip changed");
            for (RailAngle other : values) records.add(angle + "/" + other + "\t" + angle.add(other) + ":" + angle.sub(other) + ":" + angle.isParallel(other) + ":" + angle.similarFacing(other.angleDegrees));
            fails(() -> angle.add(null)); fails(() -> angle.sub(null)); fails(() -> angle.isParallel(null));
        }
        List<Float> inputs = new ArrayList<>(List.of(0F, -0F, Float.MIN_VALUE, -Float.MIN_VALUE, Float.NaN));
        for (int i = -64; i <= 64; i++) {
            float boundary = i * 11.25F;
            inputs.add(Math.nextDown(boundary)); inputs.add(boundary); inputs.add(Math.nextUp(boundary));
        }
        Random random = new Random(801391); for (int i = 0; i < 20000; i++) inputs.add((random.nextFloat() - 0.5F) * 100000);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (float input : inputs) {
            StringBuilder row = new StringBuilder().append(Float.floatToRawIntBits(input)).append(':').append(RailAngle.fromAngle(input))
                    .append(':').append(RailAngle.getQuadrant(input, false)).append(':').append(RailAngle.getQuadrant(input, true));
            for (RailAngle angle : values) row.append(':').append(RailAngle.similarFacing(input, angle.angleDegrees));
            digest.update(row.toString().getBytes(StandardCharsets.UTF_8));
        }
        records.add("boundary-and-seeded-inputs\t" + inputs.size() + ":" + HexFormat.of().formatHex(digest.digest()));
        values[0] = null; require(RailAngle.fromAngle(0) == RailAngle.E && RailAngle.values()[0] == RailAngle.E, "Caller mutation corrupted cached enum entries");
        String actual = String.join("\n", records) + "\n";
        if (args.length == 3) Files.writeString(Path.of(args[0]), actual);
        else require(Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual), "RailAngle changed from Java golden");
        System.out.println("PASS: 16 exact-bit angle constants, 256 enum pairs and " + inputs.size() + " boundary/seeded inputs, nullable failures and values-array isolation (" + records.size() + " records)");
    }
    private static String bits(double value) { return Long.toHexString(Double.doubleToRawLongBits(value)); }
    private static void fails(Runnable action) { try { action.run(); } catch (NullPointerException expected) { return; } throw new AssertionError("Null must fail"); }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
