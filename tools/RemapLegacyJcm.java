import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

import net.neoforged.art.relocated.org.objectweb.asm.ClassReader;
import net.neoforged.art.relocated.org.objectweb.asm.ClassWriter;
import net.neoforged.art.relocated.org.objectweb.asm.commons.ClassRemapper;
import net.neoforged.art.relocated.org.objectweb.asm.commons.Remapper;

/** Remaps exact SRG Minecraft members in legacy JCM without hierarchy analysis. */
public final class RemapLegacyJcm {
    private static final Map<String, String> METHODS = new HashMap<>();
    private static final Map<String, String> FIELDS = new HashMap<>();
    private static final Map<String, String> FALLBACK_METHODS = new HashMap<>();
    private static final Map<String, String> FALLBACK_FIELDS = new HashMap<>();
    private static final Set<String> AMBIGUOUS_METHODS = new HashSet<>();
    private static final Set<String> AMBIGUOUS_FIELDS = new HashSet<>();
    private static final String SEP = "\u0000";

    private static String key(String owner, String name) {
        return owner + SEP + name;
    }

    private static void addFallback(Map<String, String> values, Set<String> ambiguous, String key, String mapped) {
        if (ambiguous.contains(key)) return;
        String previous = values.putIfAbsent(key, mapped);
        if (previous != null && !previous.equals(mapped)) {
            values.remove(key);
            ambiguous.add(key);
        }
    }

    private static void load(Path mappings) throws IOException {
        String owner = null;
        for (String line : Files.readAllLines(mappings, StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("tsrg2") || line.startsWith("#")) continue;
            if (!line.startsWith("\t")) {
                String[] parts = line.split(" ");
                owner = parts[0];
                continue;
            }
            if (owner == null || line.startsWith("\t\t")) continue;
            String[] parts = line.strip().split(" ");
            if (parts.length == 2) {
                FIELDS.put(key(owner, parts[0]), parts[1]);
                addFallback(FALLBACK_FIELDS, AMBIGUOUS_FIELDS, parts[0], parts[1]);
            } else if (parts.length >= 3) {
                METHODS.put(key(owner, parts[0]) + SEP + parts[1], parts[2]);
                addFallback(FALLBACK_METHODS, AMBIGUOUS_METHODS, parts[0] + SEP + parts[1], parts[2]);
            }
        }
    }

    private static byte[] remap(byte[] input) {
        ClassReader reader = new ClassReader(input);
        ClassWriter writer = new ClassWriter(reader, 0);
        Remapper remapper = new Remapper() {
            @Override public String mapMethodName(String owner, String name, String descriptor) {
                return METHODS.getOrDefault(key(owner, name) + SEP + descriptor,
                        FALLBACK_METHODS.getOrDefault(name + SEP + descriptor, name));
            }
            @Override public String mapFieldName(String owner, String name, String descriptor) {
                return FIELDS.getOrDefault(key(owner, name), FALLBACK_FIELDS.getOrDefault(name, name));
            }
        };
        reader.accept(new ClassRemapper(writer, remapper), 0);
        return writer.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("<input.jar> <output.jar> <map.tsrg>");
        load(Path.of(args[2]));
        try (ZipFile input = new ZipFile(args[0]);
             ZipOutputStream output = new ZipOutputStream(Files.newOutputStream(Path.of(args[1])))) {
            Enumeration<? extends ZipEntry> entries = input.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                ZipEntry next = new ZipEntry(entry.getName());
                next.setTime(entry.getTime());
                output.putNextEntry(next);
                byte[] bytes = input.getInputStream(entry).readAllBytes();
                output.write(entry.getName().endsWith(".class") ? remap(bytes) : bytes);
                output.closeEntry();
            }
        }
        System.out.printf("Remapped %d methods and %d fields.%n", METHODS.size(), FIELDS.size());
    }
}
