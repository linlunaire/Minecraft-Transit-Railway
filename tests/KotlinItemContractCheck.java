import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.instruction.ConstantInstruction;
import java.lang.classfile.instruction.InvokeInstruction;
import java.lang.reflect.AccessFlag;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarFile;

/** Structural migration checks; this deliberately does not claim to launch Minecraft or apply Mixins. */
public final class KotlinItemContractCheck {
    private static final String ITEM_PREFIX = "mtr/item/";
    private static final String PACKET_OWNER = "mtr/packet/PacketTrainDataGuiServer";
    private static final Map<String, List<String>> SHADOW_FIELDS = Map.of(
            "ItemNodeModifierBase", List.of("isConnector", "forNonContinuousMovementNode", "forContinuousMovementNode", "forAirplaneNode"),
            "ItemRailModifier", List.of("isOneWay", "railType"),
            "ItemWithCreativeTabBase", List.of("creativeModeTab"),
            "ItemWithCreativeTabBase$ItemPlaceOnWater", List.of("creativeModeTab"));

    public static void main(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("snapshot")) {
            describe(Path.of(args[1]), false).forEach((key, value) -> System.out.println(key + "\t" + value));
            return;
        }
        if (args.length != 3 || !args[0].equals("check")) {
            throw new IllegalArgumentException("snapshot baselineJar | check baselineFile classesDirectoryOrJar");
        }
        Map<String, String> expected = new TreeMap<>();
        for (String line : Files.readAllLines(Path.of(args[1]))) {
            if (line.isBlank() || line.startsWith("#")) continue;
            int separator = line.indexOf('\t');
            if (separator < 0 || expected.put(line.substring(0, separator), line.substring(separator + 1)) != null) {
                throw new IllegalArgumentException("Malformed item contract baseline: " + line);
            }
        }
        if (expected.isEmpty()) throw new AssertionError("Empty item contract baseline");
        Map<String, String> actual = describe(Path.of(args[2]), true);
        if (!expected.equals(actual)) throw new AssertionError("Item JVM/packet contracts changed:\nexpected " + expected + "\nactual " + actual);
        System.out.println("PASS: " + expected.size() + " item field and packet-call contracts; nullable player/facing/blacklist paths retained");
    }

    private static Map<String, String> describe(Path artifact, boolean checkNullable) throws IOException {
        Map<String, String> result = new TreeMap<>();
        Map<String, Integer> packets = new TreeMap<>();
        for (ClassModel model : classes(artifact)) {
            String name = model.thisClass().asInternalName().substring(ITEM_PREFIX.length());
            for (String required : SHADOW_FIELDS.getOrDefault(name, List.of())) {
                var field = model.fields().stream().filter(candidate -> candidate.fieldName().equalsString(required)).findFirst()
                        .orElseThrow(() -> new AssertionError("Missing Mixin/Java field " + name + "." + required));
                result.put("F:" + name + "." + required, field.fieldType().stringValue() + " " + field.flags().flags().stream()
                        .filter(flag -> flag == AccessFlag.PUBLIC || flag == AccessFlag.PROTECTED || flag == AccessFlag.PRIVATE
                                || flag == AccessFlag.STATIC || flag == AccessFlag.FINAL).map(Enum::name).sorted().toList());
            }
            String family = name.contains("$") ? name.substring(0, name.indexOf('$')) : name;
            for (var method : model.methods()) {
                if (method.code().isEmpty()) continue;
                String lastString = "";
                for (var element : method.code().get()) {
                    if (element instanceof ConstantInstruction constant && constant.constantValue() instanceof String string) lastString = string;
                    if (!(element instanceof InvokeInstruction call)) continue;
                    if (call.owner().asInternalName().equals(PACKET_OWNER)) {
                        packets.merge("P:" + family + ":" + call.name().stringValue() + call.type().stringValue(), 1, Integer::sum);
                    }
                    if (checkNullable && call.owner().asInternalName().equals("kotlin/jvm/internal/Intrinsics")
                            && call.name().equalsString("checkNotNullParameter")) {
                        boolean worldCallback = (method.methodName().equalsString("onConnect") || method.methodName().equalsString("onRemove"))
                                && method.methodType().stringValue().startsWith("(Lnet/minecraft/world/level/Level;");
                        boolean nullableParameter = lastString.equals("forceFacing") || lastString.equals("blacklistBlock")
                                || lastString.equals("player") && (worldCallback || family.equals("ItemLiftRefresher"));
                        if (nullableParameter) throw new AssertionError("Nullable item parameter now rejected: " + name + "." + method.methodName() + " " + lastString);
                    }
                }
            }
        }
        packets.forEach((key, count) -> result.put(key, count.toString()));
        return result;
    }

    private static List<ClassModel> classes(Path artifact) throws IOException {
        List<ClassModel> result = new ArrayList<>();
        if (Files.isDirectory(artifact)) {
            try (var files = Files.walk(artifact.resolve(ITEM_PREFIX))) {
                for (Path file : files.filter(path -> path.toString().endsWith(".class")).toList()) result.add(ClassFile.of().parse(Files.readAllBytes(file)));
            }
        } else try (JarFile jar = new JarFile(artifact.toFile())) {
            for (var entry : jar.stream().filter(entry -> entry.getName().startsWith(ITEM_PREFIX) && entry.getName().endsWith(".class")).toList()) {
                try (var input = jar.getInputStream(entry)) { result.add(ClassFile.of().parse(input.readAllBytes())); }
            }
        }
        if (result.isEmpty()) throw new AssertionError("No item classes found in " + artifact);
        return result;
    }
}
