package mtr.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.objectweb.asm.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;

/** Executes selected production sync logic; counts owned buffers and snapshots real payloads. */
public final class NearbySyncCompatibilityCheck {
    public static final List<ByteBuf> buffers = new ArrayList<>();
    public static final List<String> packets = new ArrayList<>();
    private static final List<byte[]> snapshots = new ArrayList<>();
    private static final List<String> snapshotHashes = new ArrayList<>();
    public static boolean failSend;
    public static final RuntimeException FAILURE = new RuntimeException("sync fixture failure");
    private static final String TYPE = "mtr.data.UpdateNearbyMovingObjects";
    private static final String SCENARIO = "mtr.data.NearbySyncScenario";

    public static ByteBuf buffer() { ByteBuf buffer = Unpooled.buffer(); buffers.add(buffer); return buffer; }
    public static void sendToPlayer(ServerPlayer player, Identifier id, FriendlyByteBuf buffer) {
        if (failSend) throw FAILURE;
        try {
            // Both loader RegistryImpl entrypoints use this exact snapshot factory.
            Class<?> utilities = Class.forName("mtr.mappings.NetworkUtilities");
            var factory = utilities.getDeclaredMethod("createPayload", Identifier.class, FriendlyByteBuf.class); factory.setAccessible(true);
            Object payload = factory.invoke(null, id, buffer);
            var bytes = payload.getClass().getDeclaredMethod("bytes"); bytes.setAccessible(true);
            byte[] snapshot = (byte[]) bytes.invoke(payload);
            String hash = HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(snapshot));
            snapshots.add(snapshot); snapshotHashes.add(hash);
            packets.add((player == null ? "null" : player.getUUID()) + ":" + id + ":" + hash + ":" + snapshot.length);
        } catch (ReflectiveOperationException | java.security.NoSuchAlgorithmException error) { throw new AssertionError(error); }
    }
    public static int liveBuffers() { return (int) buffers.stream().filter(buffer -> buffer.refCnt() != 0).count(); }
    public static void verifySnapshots() {
        try {
            var digest = java.security.MessageDigest.getInstance("SHA-256");
            for (int i = 0; i < snapshots.size(); i++) if (!HexFormat.of().formatHex(digest.digest(snapshots.get(i))).equals(snapshotHashes.get(i))) throw new AssertionError("Reusing/releasing a sent buffer corrupted its retained payload snapshot");
        } catch (java.security.NoSuchAlgorithmException error) { throw new AssertionError(error); }
    }
    public static void clear() {
        for (ByteBuf buffer : buffers) if (buffer.refCnt() > 0) buffer.release(buffer.refCnt());
        buffers.clear(); packets.clear(); snapshots.clear(); snapshotHashes.clear(); failSend = false;
    }

    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[1]).toRealPath();
        boolean kotlin = Files.isDirectory(source), record = Arrays.asList(args).contains("--record");
        boolean ownership = Arrays.asList(args).contains("--ownership");
        if (record && kotlin) throw new AssertionError("Only record frozen Java releases");
        byte[] original;
        String entry = TYPE.replace('.', '/') + ".class";
        if (kotlin) original = Files.readAllBytes(source.resolve(entry));
        else try (JarFile jar = new JarFile(source.toFile()); var input = jar.getInputStream(jar.getJarEntry(entry))) { original = input.readAllBytes(); }
        ClassWriter writer = new ClassWriter(0); boolean[] metadata = {false};
        new ClassReader(original).accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                if (descriptor.equals("Lkotlin/Metadata;")) metadata[0] = true;
                return super.visitAnnotation(descriptor, visible);
            }
            @Override public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                    @Override public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (owner.equals("io/netty/buffer/Unpooled") && name.equals("buffer") && descriptor.equals("()Lio/netty/buffer/ByteBuf;") || owner.equals("mtr/Registry") && name.equals("sendToPlayer")) owner = NearbySyncCompatibilityCheck.class.getName().replace('.', '/');
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            }
        }, 0);
        if (metadata[0] != kotlin) throw new AssertionError("Wrong source language");
        byte[] selected = writer.toByteArray();
        ClassLoader loader = new ClassLoader(NearbySyncCompatibilityCheck.class.getClassLoader()) {
            @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (!name.equals(TYPE) && !name.equals(SCENARIO) && !name.startsWith(SCENARIO + "$")) return super.loadClass(name, resolve);
                synchronized (getClassLoadingLock(name)) {
                    Class<?> type = findLoadedClass(name);
                    if (type == null) {
                        byte[] bytes = selected;
                        if (!name.equals(TYPE)) try (InputStream input = getParent().getResourceAsStream(name.replace('.', '/') + ".class")) {
                            if (input == null) throw new ClassNotFoundException(name); bytes = input.readAllBytes();
                        } catch (IOException error) { throw new ClassNotFoundException(name, error); }
                        type = defineClass(name, bytes, 0, bytes.length);
                    }
                    if (resolve) resolveClass(type); return type;
                }
            }
        };
        String actual;
        try { actual = (String) Class.forName(SCENARIO, true, loader).getMethod("run", boolean.class).invoke(null, ownership); }
        catch (InvocationTargetException error) { throw new AssertionError("Selected nearby sync failed", error.getCause()); }
        finally { clear(); }
        if (!ownership) {
            if (record) Files.writeString(Path.of(args[0]), actual);
            else if (!Files.readString(Path.of(args[0])).replace("\r\n", "\n").equals(actual)) throw new AssertionError("Nearby sync differs from Java baseline:\n" + actual);
        }
        System.out.println("PASS: nearby sync " + (ownership ? "buffer ownership/allocation gates" : "frozen Java payload/order/failure behavior"));
    }
}
