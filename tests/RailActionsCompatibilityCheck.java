package mtr.data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import org.objectweb.asm.*;

/** Executes the real queue; only block-building and outgoing packet I/O are adapted. */
public final class RailActionsCompatibilityCheck {
    public static final String TARGET = "mtr.data.RailwayDataRailActionsModule";
    private static final String SCENARIO = "mtr.data.RailActionsScenario";
    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        boolean kotlin = Files.isDirectory(source), record = Arrays.asList(args).contains("--record");
        byte[] selected;
        if (kotlin) selected = Files.readAllBytes(source.resolve(TARGET.replace('.', '/') + ".class"));
        else try (JarFile jar = new JarFile(source.toFile()); var stream = jar.getInputStream(jar.getJarEntry(TARGET.replace('.', '/') + ".class"))) { selected = stream.readAllBytes(); }
        boolean[] metadata = {false};
        new ClassReader(selected).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) { if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true; return null; }
        }, ClassReader.SKIP_CODE);
        require(metadata[0] == kotlin, "Wrong selected queue implementation");
        String actual = run(selected, false);
        if (record) { require(!kotlin, "Only record original Java"); Files.writeString(Path.of(args[0]), actual); }
        else compare(args[0], actual);
        System.out.println("PASS: " + (kotlin ? "Kotlin" : "original Java") + " rail-action queue and real action construction (headless building/packet adapters)");
    }
    public static void compare(String golden, String actual) throws IOException {
        require(Files.readString(Path.of(golden)).replace("\r\n", "\n").equals(actual), "Rail-action queue differs from original Java:\n" + actual);
    }
    public static String run(byte[] selected, boolean woven) throws Exception {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        int[] packets = {0}, builds = {0};
        new ClassReader(selected).accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (owner.equals("mtr/packet/PacketTrainDataGuiServer") && name.equals("updateRailActionsS2C")) { packets[0]++; owner = SCENARIO.replace('.', '/'); name = "packet"; }
                        if (owner.equals("mtr/data/Rail$RailActions") && name.equals("build") && descriptor.equals("()Z")) {
                            builds[0]++; opcode = Opcodes.INVOKESTATIC; owner = SCENARIO.replace('.', '/'); name = "build"; descriptor = "(Lmtr/data/Rail$RailActions;)Z"; isInterface = false;
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            }
        }, 0);
        require(builds[0] == 1 && packets[0] == (woven ? 6 : 5), "Queue I/O adapter sites changed");
        ClassLoader loader = new ClassLoader(RailActionsCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (!name.equals(TARGET) && !name.equals(SCENARIO) && !name.startsWith(SCENARIO + "$")) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name);
                    if (type == null) {
                        byte[] bytes = name.equals(TARGET) ? writer.toByteArray() : null;
                        if (bytes == null) try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                            if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                        } catch (IOException error) { throw new ClassNotFoundException(name, error); }
                        type = defineClass(name, bytes, 0, bytes.length);
                    }
                    if (resolve) resolveClass(type); return type;
                }
            }
        };
        try { return (String) Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class).invoke(null, woven); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected queue failed", error.getCause()); }
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
