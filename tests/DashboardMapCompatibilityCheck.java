package mtr.screen;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix3x2f;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Exercises the actual terrain loop and Minecraft GUI submission, without a window. */
public final class DashboardMapCompatibilityCheck {

	private static final Unsafe UNSAFE;
	private static int samples;
	private static boolean uniform;
	private static boolean negativeHeights;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			UNSAFE = (Unsafe) field.get(null);
		} catch (ReflectiveOperationException e) { throw new ExceptionInInitializerError(e); }
	}

	public static void main(String[] args) throws Exception {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		Path classes = Path.of(args.length == 0 ? "common/build/classes/java/main" : args[0]);
		FixtureLoader loader = new FixtureLoader();
		Class<?> widgetType = loader.define("mtr.screen.WidgetMap", terrainOnly(Files.readAllBytes(classes.resolve("mtr/screen/WidgetMap.class"))));
		ClientLevel world = (ClientLevel) UNSAFE.allocateInstance(loader.define("mtr.screen.DashboardMapWorldFixture", worldFixture()));
		Class<?> graphicsType;
		try {
			Class.forName("mtr.mixin.GuiGraphicsExtractorAccessor");
			graphicsType = loader.define("mtr.screen.DashboardMapGraphicsFixture", graphicsFixture());
		} catch (ClassNotFoundException oldImplementation) { graphicsType = GuiGraphicsExtractor.class; }
		runCase(widgetType, graphicsType, world, 96, 64, 1, 0, 0, false, false);
		runCase(widgetType, graphicsType, world, 79, 53, 0.5, -13.25, 18.5, false, false);
		runCase(widgetType, graphicsType, world, 79, 53, 2.5, 10.125, -17.75, false, false);
		runCase(widgetType, graphicsType, world, 80, 64, 64, 1.75, -2.25, false, false);
		runCase(widgetType, graphicsType, world, 192, 128, 1, 0, 0, true, false);
		runCase(widgetType, graphicsType, world, 45, 31, 0.25, 7.75, -3.125, false, true);
		runCase(widgetType, graphicsType, world, 1280, 720, 1, -61.5, 3.125, true, false);
		negativeHeights = true;
		runCase(widgetType, graphicsType, world, 96, 64, 1, 0, 0, false, false);
		runCase(widgetType, graphicsType, null, 96, 64, 1, 0, 0, false, false);
		runCase(widgetType, graphicsType, world, 0, 64, 1, 0, 0, false, false);
		runCase(widgetType, graphicsType, world, 96, 0, 1, 0, 0, false, false);
		System.out.println("PASS: actual WidgetMap terrain uses one GUI submission with matching terrain colors, pan/zoom, viewport clipping, transformed pose, negative/empty height columns and null/zero-size safety");
	}

	private static void runCase(Class<?> widgetType, Class<?> graphicsType, ClientLevel world, int width, int height, double scale, double centerX, double centerZ, boolean solid, boolean transformed) throws Exception {
		samples = 0;
		uniform = solid;
		Object widget = UNSAFE.allocateInstance(widgetType);
		set(widgetType, widget, "world", world);
		set(widgetType, widget, "x", 11);
		set(widgetType, widget, "y", 17);
		set(widgetType, widget, "width", width);
		set(widgetType, widget, "height", height);
		set(widgetType, widget, "scale", scale);
		set(widgetType, widget, "centerX", centerX);
		set(widgetType, widget, "centerY", centerZ);
		GuiRenderState state = new GuiRenderState();
		GuiGraphicsExtractor graphics = (GuiGraphicsExtractor) UNSAFE.allocateInstance(graphicsType);
		Matrix3x2fStack pose = new Matrix3x2fStack(16);
		if (transformed) pose.translate(23, 41).scale(2, 2);
		Matrix3x2f expectedPose = new Matrix3x2f(pose);
		set(GuiGraphicsExtractor.class, graphics, "pose", pose);
		set(GuiGraphicsExtractor.class, graphics, "guiRenderState", state);
		Class<?> scissorType = Class.forName("net.minecraft.client.gui.GuiGraphicsExtractor$ScissorStack");
		var constructor = scissorType.getDeclaredConstructor(ScreenRectangle.class);
		constructor.setAccessible(true);
		set(GuiGraphicsExtractor.class, graphics, "scissorStack", constructor.newInstance(new ScreenRectangle(0, 0, 4096, 4096)));
		long start = System.nanoTime();
		widgetType.getMethod("render", GuiGraphicsExtractor.class, int.class, int.class, float.class).invoke(widget, graphics, 0, 0, 0F);
		List<GuiElementRenderState> elements = new ArrayList<>();
		state.forEachElement(elements::add, GuiRenderState.TraverseRange.ALL);
		System.out.printf("Actual dashboard terrain %dx%d @ %.3f: %d world samples, %d GUI elements, %.1f ms%n", width, height, scale, samples, elements.size(), (System.nanoTime() - start) / 1_000_000D);
		if (world == null || width <= 0 || height <= 0) {
			if (samples != 0 || !elements.isEmpty()) throw new AssertionError("Null world or empty map must not sample or submit GUI work");
			return;
		}
		int firstX = (int) Math.floor(-width / 2D / scale + centerX), firstZ = (int) Math.floor(-height / 2D / scale + centerZ);
		int lastX = (int) Math.floor(width / 2D / scale + centerX), lastZ = (int) Math.floor(height / 2D / scale + centerZ);
		int increment = scale >= 1 ? 1 : (int) Math.ceil(1 / scale);
		int expectedSamples = ((lastX - firstX) / increment + 1) * ((lastZ - firstZ) / increment + 1);
		if (samples != expectedSamples) throw new AssertionError("Terrain sampling grid changed: " + samples + " vs " + expectedSamples);
		if (elements.size() > 1) throw new AssertionError("Dashboard terrain submitted " + elements.size() + " GUI elements; per-cell submission triggers quadratic GUI intersection checks");
		if (elements.isEmpty()) throw new AssertionError("Terrain disappeared instead of being batched");
		if (!pose.equals(expectedPose)) throw new AssertionError("Terrain submission leaked its transform to other widgets");
		GuiElementRenderState element = elements.getFirst();
		ScreenRectangle viewport = new ScreenRectangle(11, 17, width, height).transformMaxBounds(expectedPose);
		if (!viewport.equals(element.scissorArea())) throw new AssertionError("Wrong map scissor: " + element.scissorArea() + " vs " + viewport);
		// Deferred GUI extraction must own its pose, rather than borrow the mutable stack.
		pose.translate(1000, 2000);
		Vertices vertices = new Vertices();
		element.buildVertices(vertices);
		if (vertices.values.size() % 4 != 0) throw new AssertionError("Incomplete terrain quad");
		if (solid && vertices.values.size() > 4 * (width + 1)) throw new AssertionError("Solid terrain failed to coalesce vertical color runs");
		// The large-window fixture locks down actual submissions, samples and vertices;
		// small fixtures below additionally compare every rasterized pixel.
		int[] pixels = width * height > 250_000 ? null : new int[viewport.width() * viewport.height()];
		for (int index = 0; index < vertices.values.size(); index += 4) {
			float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
			int color = vertices.values.get(index).color;
			for (int offset = 0; offset < 4; offset++) {
				Vertex vertex = vertices.values.get(index + offset);
				if (color != vertex.color) throw new AssertionError("Terrain cell lost its flat map color");
				minX = Math.min(minX, vertex.x); minY = Math.min(minY, vertex.y);
				maxX = Math.max(maxX, vertex.x); maxY = Math.max(maxY, vertex.y);
			}
			if (minX < viewport.left() || minY < viewport.top() || maxX > viewport.right() || maxY > viewport.bottom()) throw new AssertionError("Terrain vertex escaped its viewport");
			if (pixels == null) continue;
			for (int py = Math.max(viewport.top(), (int) Math.ceil(minY - 0.5)); py < Math.min(viewport.bottom(), Math.ceil(maxY - 0.5)); py++) {
				for (int px = Math.max(viewport.left(), (int) Math.ceil(minX - 0.5)); px < Math.min(viewport.right(), Math.ceil(maxX - 0.5)); px++) {
					pixels[(py - viewport.top()) * viewport.width() + px - viewport.left()] = color;
				}
			}
		}
		if (pixels == null) return;
		for (int py = 0; py < viewport.height(); py++) {
			for (int px = 0; px < viewport.width(); px++) {
				double localX = (px + 0.5) / (transformed ? 2 : 1), localY = (py + 0.5) / (transformed ? 2 : 1);
				int sampleX = firstX + (int) Math.floor(((localX - width / 2D) / scale + centerX - firstX) / increment) * increment;
				int sampleZ = firstZ + (int) Math.floor(((localY - height / 2D) / scale + centerZ - firstZ) / increment) * increment;
				BlockPos pos = new BlockPos(sampleX, terrainHeight(sampleX, sampleZ) - 1, sampleZ);
				int mapColor = stateAt(pos).getMapColor(world, pos).col;
				int expected = 0xFF000000 | (((mapColor >> 16) & 255) / 2 << 16) | (((mapColor >> 8) & 255) / 2 << 8) | (mapColor & 255) / 2;
				if (pixels[py * viewport.width() + px] != expected) throw new AssertionError("Terrain color/position mismatch at " + px + "," + py + ": " + Integer.toHexString(pixels[py * viewport.width() + px]) + " vs " + Integer.toHexString(expected));
			}
		}
	}

	public static BlockState blockState(BlockPos pos) {
		samples++;
		if (pos.getY() != terrainHeight(pos.getX(), pos.getZ()) - 1) throw new AssertionError("Map sampled the wrong Y for an empty/negative height column: " + pos);
		return stateAt(pos);
	}
	public static int terrainHeight(int x, int z) {
		if (!negativeHeights) return 64;
		return switch (Math.floorMod(x + z, 5)) { case 0 -> -64; case 1 -> -63; case 2 -> -17; case 3 -> 0; default -> 64; };
	}
	private static BlockState stateAt(BlockPos pos) { return uniform || ((pos.getX() + pos.getZ()) & 1) == 0 ? Blocks.STONE.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState(); }

	public static GuiRenderState guiState(GuiGraphicsExtractor graphics) {
		try { Field field = GuiGraphicsExtractor.class.getDeclaredField("guiRenderState"); field.setAccessible(true); return (GuiRenderState) field.get(graphics); }
		catch (ReflectiveOperationException e) { throw new AssertionError(e); }
	}

	private static byte[] graphicsFixture() {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, "mtr/screen/DashboardMapGraphicsFixture", null, "net/minecraft/client/gui/GuiGraphicsExtractor", new String[]{"mtr/mixin/GuiGraphicsExtractorAccessor"});
		MethodVisitor method = writer.visitMethod(Opcodes.ACC_PUBLIC, "mtr$getGuiRenderState", "()Lnet/minecraft/client/renderer/state/gui/GuiRenderState;", null, null);
		method.visitCode(); method.visitVarInsn(Opcodes.ALOAD, 0);
		method.visitMethodInsn(Opcodes.INVOKESTATIC, "mtr/screen/DashboardMapCompatibilityCheck", "guiState", "(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)Lnet/minecraft/client/renderer/state/gui/GuiRenderState;", false);
		method.visitInsn(Opcodes.ARETURN); method.visitMaxs(0, 0); method.visitEnd(); writer.visitEnd();
		return writer.toByteArray();
	}

	private static final class Vertex {
		final float x, y;
		int color;
		Vertex(float x, float y) { this.x = x; this.y = y; }
	}
	private static final class Vertices implements VertexConsumer {
		final List<Vertex> values = new ArrayList<>();
		public VertexConsumer addVertex(float x, float y, float z) { values.add(new Vertex(x, y)); return this; }
		public VertexConsumer setColor(int r, int g, int b, int a) { return setColor(a << 24 | r << 16 | g << 8 | b); }
		public VertexConsumer setColor(int color) { values.getLast().color = color; return this; }
		public VertexConsumer setUv(float u, float v) { return this; }
		public VertexConsumer setUv1(int u, int v) { return this; }
		public VertexConsumer setUv2(int u, int v) { return this; }
		public VertexConsumer setNormal(float x, float y, float z) { return this; }
		public VertexConsumer setLineWidth(float width) { return this; }
	}

	private static byte[] terrainOnly(byte[] original) {
		ClassNode node = new ClassNode();
		new ClassReader(original).accept(node, 0);
		MethodNode render = node.methods.stream().filter(method -> method.name.equals("render")).findFirst().orElseThrow();
		AbstractInsnNode cut = null;
		for (AbstractInsnNode instruction : render.instructions) {
			if (instruction instanceof MethodInsnNode call && call.name.equals("coordsToWorldPos") && call.desc.startsWith("(DD)")) { cut = instruction; break; }
		}
		if (cut == null) throw new AssertionError("Cannot locate the end of the actual terrain loop");
		while (cut != null) { AbstractInsnNode next = cut.getNext(); render.instructions.remove(cut); cut = next; }
		// Remove the already evaluated mouse-overlay arguments, never terrain work.
		render.instructions.add(new InsnNode(Opcodes.POP2));
		render.instructions.add(new InsnNode(Opcodes.POP2));
		render.instructions.add(new InsnNode(Opcodes.POP));
		render.instructions.add(new InsnNode(Opcodes.RETURN));
		render.tryCatchBlocks.clear();
		render.localVariables = null;
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		node.accept(writer);
		return writer.toByteArray();
	}

	private static byte[] worldFixture() {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		writer.visit(Opcodes.V17, Opcodes.ACC_PUBLIC, "mtr/screen/DashboardMapWorldFixture", null, "net/minecraft/client/multiplayer/ClientLevel", null);
		MethodVisitor height = writer.visitMethod(Opcodes.ACC_PUBLIC, "getHeight", "(Lnet/minecraft/world/level/levelgen/Heightmap$Types;II)I", null, null);
		height.visitCode(); height.visitVarInsn(Opcodes.ILOAD, 2); height.visitVarInsn(Opcodes.ILOAD, 3);
		height.visitMethodInsn(Opcodes.INVOKESTATIC, "mtr/screen/DashboardMapCompatibilityCheck", "terrainHeight", "(II)I", false);
		height.visitInsn(Opcodes.IRETURN); height.visitMaxs(0, 0); height.visitEnd();
		MethodVisitor block = writer.visitMethod(Opcodes.ACC_PUBLIC, "getBlockState", "(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", null, null);
		block.visitCode(); block.visitVarInsn(Opcodes.ALOAD, 1);
		block.visitMethodInsn(Opcodes.INVOKESTATIC, "mtr/screen/DashboardMapCompatibilityCheck", "blockState", "(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", false);
		block.visitInsn(Opcodes.ARETURN); block.visitMaxs(0, 0); block.visitEnd();
		writer.visitEnd(); return writer.toByteArray();
	}

	private static void set(Class<?> owner, Object instance, String name, Object value) throws ReflectiveOperationException {
		Field field = owner.getDeclaredField(name); field.setAccessible(true); field.set(instance, value);
	}

	private static final class FixtureLoader extends ClassLoader {
		private FixtureLoader() { super(DashboardMapCompatibilityCheck.class.getClassLoader()); }
		Class<?> define(String name, byte[] bytes) { return defineClass(name, bytes, 0, bytes.length); }
	}
}
