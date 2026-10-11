import java.io.IOException;
import java.lang.classfile.AccessFlags;
import java.lang.classfile.AttributedElement;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.reflect.AccessFlag;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarFile;

/** Checks exported JVM contracts without loading Minecraft or trusting source syntax. */
public final class KotlinMigrationAbiCheck {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) throw new IllegalArgumentException("snapshot baselineJar class... | check baselineFile classesDirOrJar | check-development shadedBaselineFile classesDir | check-packaged snapshotsDir releaseJar");
        if (args[0].equals("snapshot")) {
            for (int index = 2; index < args.length; index++) {
                String name = args[index].replace('.', '/');
                describe(read(Path.of(args[1]), name)).forEach((key, value) -> System.out.println(key + "\t" + value));
            }
        } else if (args[0].equals("check")) {
            check(Path.of(args[1]), Path.of(args[2]), false);
        } else if (args[0].equals("check-development")) {
            check(Path.of(args[1]), Path.of(args[2]), false, true);
        } else if (args[0].equals("check-packaged")) {
            try (var paths = Files.list(Path.of(args[1]))) {
                var snapshots = paths.filter(path -> path.toString().endsWith(".tsv")).sorted().toList();
                if (snapshots.isEmpty()) throw new AssertionError("No packaged Kotlin migration baselines");
                for (Path snapshot : snapshots) check(snapshot, Path.of(args[2]), true);
            }
        } else throw new IllegalArgumentException("Unknown mode " + args[0]);
    }

    private static void check(Path snapshot, Path artifact, boolean requireKotlin) throws IOException {
        check(snapshot, artifact, requireKotlin, false);
    }

    private static void check(Path snapshot, Path artifact, boolean requireKotlin, boolean development) throws IOException {
        Map<String, String> expected = new TreeMap<>(), actual = new TreeMap<>();
        for (String line : Files.readAllLines(snapshot)) {
            if (line.isBlank() || line.startsWith("#")) continue;
            // Release snapshots remain exact. Only an explicitly selected dev-output
            // check removes the two namespaces relocated by the production Shadow task.
            if (development) line = line.replace("mtr/libraries/javax/servlet/", "javax/servlet/")
                    .replace("mtr/libraries/org/eclipse/", "org/eclipse/");
            int tab = line.indexOf('\t');
            if (tab < 0 || expected.put(line.substring(0, tab), line.substring(tab + 1)) != null) {
                throw new IllegalArgumentException("Invalid or duplicate ABI snapshot line: " + line);
            }
        }
        if (expected.isEmpty()) throw new AssertionError("Empty ABI baseline");
        for (String key : expected.keySet()) {
            if (key.startsWith("C:")) {
                ClassModel model = read(artifact, key.substring(2));
                if (requireKotlin && model.findAttribute(Attributes.runtimeVisibleAnnotations())
                        .map(attribute -> attribute.annotations().stream().noneMatch(annotation -> annotation.className().equalsString("Lkotlin/Metadata;"))).orElse(true)) {
                    throw new AssertionError("Release still contains a Java implementation for migrated " + key.substring(2));
                }
                actual.putAll(describe(model));
            }
        }
        List<String> failures = new ArrayList<>();
        expected.forEach((key, value) -> {
            if (!value.equals(actual.get(key))) failures.add(key + " expected " + value + " but was " + actual.get(key));
        });
        if (!failures.isEmpty()) throw new AssertionError("Kotlin migration changed exported JVM contracts:\n" + String.join("\n", failures));
        System.out.println("PASS: " + expected.size() + " baseline class/field/method contracts retained (descriptors, inheritance, access, generics and declared exceptions)"
                + (requireKotlin ? " in packaged Kotlin: " + artifact.getFileName() : ""));
    }

    private static ClassModel read(Path artifact, String name) throws IOException {
        byte[] bytes;
        if (Files.isDirectory(artifact)) bytes = Files.readAllBytes(artifact.resolve(name + ".class"));
        else try (JarFile jar = new JarFile(artifact.toFile())) {
            var entry = jar.getJarEntry(name + ".class");
            if (entry == null) throw new AssertionError("Missing baseline class " + name + " in " + artifact);
            try (var input = jar.getInputStream(entry)) { bytes = input.readAllBytes(); }
        }
        return ClassFile.of().parse(bytes);
    }

    private static Map<String, String> describe(ClassModel model) {
        String owner = model.thisClass().asInternalName();
        Map<String, String> result = new TreeMap<>();
        result.put("C:" + owner, flags(model.flags()) + " "
                + model.superclass().map(entry -> entry.asInternalName()).orElse("") + " "
                + model.interfaces().stream().map(entry -> entry.asInternalName()).sorted().toList() + " " + signature(model));
        for (var field : model.fields()) {
            if (!exported(field.flags())) continue;
            result.put("F:" + owner + "." + field.fieldName().stringValue() + ":" + field.fieldType().stringValue(),
                    flags(field.flags()) + " " + signature(field));
        }
        for (var method : model.methods()) {
            if (!exported(method.flags()) || method.flags().has(AccessFlag.SYNTHETIC)) continue;
            result.put("M:" + owner + "." + method.methodName().stringValue() + method.methodType().stringValue(),
                    flags(method.flags()) + " " + signature(method) + " "
                            + method.findAttribute(Attributes.exceptions()).map(attribute -> attribute.exceptions().stream()
                            .map(entry -> entry.asInternalName()).sorted().toList().toString()).orElse("[]"));
        }
        result.replaceAll((key, value) -> value.stripTrailing());
        return result;
    }

    private static boolean exported(AccessFlags flags) {
        return flags.has(AccessFlag.PUBLIC) || flags.has(AccessFlag.PROTECTED);
    }

    private static String flags(AccessFlags flags) {
        return flags.flags().stream().filter(flag -> Arrays.asList(AccessFlag.PUBLIC, AccessFlag.PROTECTED,
                        AccessFlag.STATIC, AccessFlag.FINAL, AccessFlag.ABSTRACT, AccessFlag.INTERFACE, AccessFlag.ENUM,
                        AccessFlag.VOLATILE, AccessFlag.SYNCHRONIZED, AccessFlag.TRANSIENT, AccessFlag.NATIVE).contains(flag))
                .map(Enum::name).sorted().toList().toString();
    }

    private static String signature(AttributedElement element) {
        return element.findAttribute(Attributes.signature()).map(attribute -> attribute.signature().stringValue()).orElse("");
    }
}
