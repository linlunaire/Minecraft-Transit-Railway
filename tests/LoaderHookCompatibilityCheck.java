package mtr.mappings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/** Static target/descriptor checks, deliberately not a substitute for runtime Mixin weaving. */
public final class LoaderHookCompatibilityCheck {

	private static final String MIXIN = "Lorg/spongepowered/asm/mixin/";
	private static final Set<String> CLIENT_MIXINS = Set.of("LevelExtractionMixin", "LevelSubmissionMixin", "LevelRenderStateMixin",
		"FeatureRenderDispatcherMixin", "PlayerRendererOffsetMixin", "PassengerRenderStateMixin", "TerrainModelsMixin", "ItemModelPropertiesMixin");

	public static void main(String[] args) throws Exception {
		final Path root = Path.of(args[0]);
		int checked = 0;
		for (String loader : List.of("fabric", "neoforge")) {
			final JsonObject config = JsonParser.parseString(Files.readString(root.resolve(loader + "/src/main/resources/mtr.mixins.json"))).getAsJsonObject();
			final List<String> names = new ArrayList<>();
			config.getAsJsonArray("client").forEach(name -> names.add(name.getAsString()));
			require(Set.copyOf(names).equals(CLIENT_MIXINS) && names.size() == CLIENT_MIXINS.size(), loader + ": missing/duplicate/legacy client hooks");
			config.getAsJsonArray("mixins").forEach(name -> names.add(name.getAsString()));
			require(names.size() == CLIENT_MIXINS.size() + 1 && names.getLast().equals("PlayerTeleportationStateAccessor"), loader + ": unexpected common hooks");
			for (String name : names) {
				Path file = root.resolve("common/build/classes/java/main/mtr/mixin/" + name + ".class");
				if (!Files.exists(file)) file = root.resolve(loader + "/build/classes/java/main/mtr/mixin/" + name + ".class");
				checkMixin(read(Files.readAllBytes(file)));
				checked++;
			}
		}
		// Camera extraction precedes our LevelExtractor tail hook; its reset preserves that camera.
		final ClassNode renderer = gameClass("net/minecraft/client/renderer/GameRenderer");
		final MethodNode extraction = method(renderer, "extract", "(Lnet/minecraft/client/DeltaTracker;Z)V");
		int camera = -1, world = -1;
		for (int i = 0; i < extraction.instructions.size(); i++) {
			if (extraction.instructions.get(i) instanceof MethodInsnNode call) {
				if (call.owner.equals(renderer.name) && call.name.equals("extractCamera")) camera = i;
				if (call.owner.equals("net/minecraft/client/renderer/extract/LevelExtractor") && call.name.equals("extract")) world = i;
			}
		}
		require(camera >= 0 && world > camera, "Camera/world extraction order changed");
		final MethodNode reset = method(gameClass("net/minecraft/client/renderer/state/level/LevelRenderState"), "reset", "()V");
		for (var instruction : reset.instructions) {
			if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode field) require(!field.name.equals("cameraRenderState"), "Level reset now touches the camera state");
		}
		System.out.println("PASS: " + checked + " loader-configured Mixin classes, actual target methods/fields, callback descriptors and camera extraction order (static; no runtime weaving)");
	}

	private static void checkMixin(ClassNode mixin) throws Exception {
		final AnnotationNode annotation = annotation(mixin.invisibleAnnotations, MIXIN + "Mixin;");
		require(annotation != null, "Missing @Mixin on " + mixin.name);
		final List<?> targets = (List<?>) value(annotation, "value");
		require(targets.size() == 1, "Review multi-target mixin: " + mixin.name);
		final ClassNode target = gameClass(((Type) targets.getFirst()).getInternalName());
		for (FieldNode field : mixin.fields) {
			if (annotation(field.visibleAnnotations, MIXIN + "Shadow;") != null || annotation(field.invisibleAnnotations, MIXIN + "Shadow;") != null) {
				final FieldNode actual = target.fields.stream().filter(item -> item.name.equals(field.name) && item.desc.equals(field.desc)).findFirst().orElseThrow(() -> new AssertionError("Missing shadow " + target.name + "." + field.name));
				require(isStatic(actual.access) == isStatic(field.access), "Shadow static mismatch");
			}
		}
		for (MethodNode handler : mixin.methods) {
			final List<AnnotationNode> annotations = new ArrayList<>();
			if (handler.visibleAnnotations != null) annotations.addAll(handler.visibleAnnotations);
			if (handler.invisibleAnnotations != null) annotations.addAll(handler.invisibleAnnotations);
			for (AnnotationNode hook : annotations) {
				if (hook.desc.equals(MIXIN + "gen/Accessor;")) {
					final String name = (String) value(hook, "value");
					final Type[] arguments = Type.getArgumentTypes(handler.desc);
					require(arguments.length == 1 && Type.getReturnType(handler.desc).equals(Type.VOID_TYPE), "Review non-setter accessor");
					require(target.fields.stream().anyMatch(field -> field.name.equals(name) && field.desc.equals(arguments[0].getDescriptor()) && isStatic(field.access) == isStatic(handler.access)), "Accessor field missing: " + target.name + "." + name);
				} else if (hook.desc.equals(MIXIN + "injection/Inject;") || hook.desc.equals(MIXIN + "injection/ModifyVariable;")) {
					for (Object selector : (List<?>) value(hook, "method")) {
						final String selected = selector.toString();
						final List<MethodNode> matches = target.methods.stream().filter(candidate -> selected.equals(candidate.name) || selected.equals(candidate.name + candidate.desc)).toList();
						require(matches.size() == 1, "Missing/ambiguous target: " + target.name + "." + selected);
						final MethodNode actual = matches.getFirst();
						if (hook.desc.endsWith("/Inject;")) {
							require(isStatic(actual.access) == isStatic(handler.access), "Injector static mismatch: " + selected);
							final Type[] captured = Type.getArgumentTypes(handler.desc), parameters = Type.getArgumentTypes(actual.desc);
							require(captured.length == parameters.length + 1 && Arrays.equals(Arrays.copyOf(captured, parameters.length), parameters), "Injector arguments differ: " + selected);
							final String callback = Type.getReturnType(actual.desc).equals(Type.VOID_TYPE) ? "CallbackInfo" : "CallbackInfoReturnable";
							require(captured[captured.length - 1].getInternalName().equals("org/spongepowered/asm/mixin/injection/callback/" + callback), "Wrong callback type: " + selected);
						} else {
							require(actual.name.equals("<init>") && isStatic(handler.access), "Review non-constructor variable injection");
							final Type replacement = Type.getReturnType(handler.desc);
							require(Arrays.equals(Type.getArgumentTypes(handler.desc), new Type[]{replacement}) && Arrays.stream(Type.getArgumentTypes(actual.desc)).filter(replacement::equals).count() == 1, "Constructor argument replacement mismatch");
						}
					}
				}
			}
		}
	}

	private static boolean isStatic(int access) { return (access & Opcodes.ACC_STATIC) != 0; }
	private static AnnotationNode annotation(List<AnnotationNode> annotations, String descriptor) {
		return annotations == null ? null : annotations.stream().filter(item -> item.desc.equals(descriptor)).findFirst().orElse(null);
	}
	private static Object value(AnnotationNode annotation, String name) {
		for (int i = 0; i < annotation.values.size(); i += 2) if (annotation.values.get(i).equals(name)) return annotation.values.get(i + 1);
		throw new AssertionError("Missing annotation value: " + name);
	}
	private static MethodNode method(ClassNode owner, String name, String descriptor) {
		return owner.methods.stream().filter(item -> item.name.equals(name) && item.desc.equals(descriptor)).findFirst().orElseThrow(() -> new AssertionError("Missing method: " + owner.name + "." + name + descriptor));
	}
	private static ClassNode gameClass(String name) throws Exception {
		try (InputStream stream = LoaderHookCompatibilityCheck.class.getClassLoader().getResourceAsStream(name + ".class")) {
			require(stream != null, "Missing actual game class: " + name);
			return read(stream.readAllBytes());
		}
	}
	private static ClassNode read(byte[] bytes) {
		final ClassNode node = new ClassNode();
		new ClassReader(bytes).accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
		return node;
	}
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
