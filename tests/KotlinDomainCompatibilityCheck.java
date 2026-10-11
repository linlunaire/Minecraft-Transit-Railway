package mtr.data;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Public domain-model contracts shared by the old Java and migrated Kotlin implementations. */
public final class KotlinDomainCompatibilityCheck {

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			for (Class<?> type : new Class<?>[]{TransportMode.class, TrainType.class, RouteType.class, PIDSType.class,
					TrainDelay.class, ScheduleEntry.class, EnumHelper.class, IReducedSaveData.class, IPIDSRenderChild.class,
					SerializedDataBase.class, MessagePackHelper.class, DataConverter.class, RailwayDataModuleBase.class, LiftInstructions.class}) {
				require(java.nio.file.Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI()).toRealPath()
						.equals(java.nio.file.Path.of(args[0]).toRealPath()), "Unexpected implementation source: " + type);
			}
		}
		checkEnums();
		checkTrainTypes();
		checkMessagePack();
		checkLiftInstructions();
		checkScheduleEntries();
		checkConstructorsAndDelay();
		System.out.println("PASS: domain enums, all legacy train aliases, malformed/null parsing, MessagePack defaults and callbacks, lift ordering/dirty/packet contracts, schedule packet/comparison, nullable constructors");
	}

	private static void checkEnums() {
		require(Arrays.equals(TransportMode.values(), new TransportMode[]{TransportMode.TRAIN, TransportMode.BOAT, TransportMode.CABLE_CAR, TransportMode.AIRPLANE}), "TransportMode order changed");
		final int[] lengths = {Integer.MAX_VALUE, 1, 1, 1};
		final boolean[][] flags = {{false, true, true, true}, {false, true, true, true}, {true, false, false, false}, {false, true, false, false}};
		for (TransportMode mode : TransportMode.values()) {
			final int index = mode.ordinal();
			require(mode.maxLength == lengths[index], "Transport length changed: " + mode);
			require(mode.continuousMovement == flags[index][0] && mode.hasPitchAscending == flags[index][1] && mode.hasPitchDescending == flags[index][2] && mode.hasRouteTypeVariation == flags[index][3], "Transport flags changed: " + mode);
			require(mode.railOffset == (mode == TransportMode.CABLE_CAR ? -6 : 0), "Transport rail offset changed");
		}
		require(RouteType.NORMAL.next() == RouteType.LIGHT_RAIL && RouteType.LIGHT_RAIL.next() == RouteType.HIGH_SPEED && RouteType.HIGH_SPEED.next() == RouteType.NORMAL, "RouteType cycle changed");
		require(Arrays.equals(PIDSType.values(), new PIDSType[]{PIDSType.ARRIVAL_PROJECTOR, PIDSType.PIDS, PIDSType.PIDS_VERTICAL, PIDSType.PIDS_SINGLE_ARRIVAL}), "PIDSType order changed");
		for (PIDSType type : PIDSType.values()) {
			require(type.showTerminatingPlatforms == (type != PIDSType.ARRIVAL_PROJECTOR), "PIDS terminating platforms changed");
			require(type.showPlatformNumber == (type == PIDSType.ARRIVAL_PROJECTOR || type == PIDSType.PIDS_SINGLE_ARRIVAL), "PIDS platform number changed");
			require(type.showCarCount == (type != PIDSType.ARRIVAL_PROJECTOR), "PIDS car count changed");
		}
		require(EnumHelper.valueOf(RouteType.NORMAL, "HIGH_SPEED") == RouteType.HIGH_SPEED, "Enum lookup changed");
		require(EnumHelper.valueOf(RouteType.NORMAL, "high_speed") == RouteType.NORMAL, "Enum lookup must remain case-sensitive");
		require(EnumHelper.valueOf(RouteType.LIGHT_RAIL, null) == RouteType.LIGHT_RAIL, "Null enum name must return the default");
		require(EnumHelper.valueOf(RouteType.HIGH_SPEED, "unknown") == RouteType.HIGH_SPEED, "Unknown enum fallback changed");
		require(EnumHelper.<RouteType>valueOf(null, "NORMAL") == null && EnumHelper.<RouteType>valueOf(null, null) == null, "Null enum default must survive the caught exception");
		require(EnumHelper.valueOf(SpecialEnum.FIRST, "SECOND") == SpecialEnum.SECOND, "Enum lookup used the constant subclass instead of declaring class");
	}

	private static void checkTrainTypes() {
		for (TrainType alias : TrainType.values()) {
			TransportMode expectedMode = null;
			String[] dimensions = null;
			for (TransportMode mode : TransportMode.values()) {
				final String prefix = mode.name().toLowerCase(Locale.ENGLISH) + "_";
				if (alias.baseTrainType.startsWith(prefix)) {
					expectedMode = mode;
					dimensions = alias.baseTrainType.substring(prefix.length()).split("_");
					break;
				}
			}
			require(expectedMode != null && dimensions != null, "Unknown base train type: " + alias.baseTrainType);
			final int spacing = Integer.parseInt(dimensions[0]) + 1;
			final int width = Integer.parseInt(dimensions[1]);
			checkTrainType(alias.name(), expectedMode, spacing, width);
			checkTrainType(alias.name().toLowerCase(Locale.ENGLISH), expectedMode, spacing, width);
			checkTrainType(alias.baseTrainType.toUpperCase(Locale.ENGLISH), expectedMode, spacing, width);
		}
		checkTrainType("unknown-train", TransportMode.TRAIN, 2, 1);
		checkTrainType("", TransportMode.TRAIN, 2, 1);
		checkTrainType("TRAIN_-7_0", TransportMode.TRAIN, 2, 1);
		checkTrainType("cable_car_2_4_extra", TransportMode.CABLE_CAR, 3, 4);
		expect(NullPointerException.class, () -> TrainType.getTransportMode(null));
		expect(NullPointerException.class, () -> TrainType.getSpacing(null));
		expect(NullPointerException.class, () -> TrainType.getWidth(null));

		final PrintStream previousError = System.err;
		final ByteArrayOutputStream errors = new ByteArrayOutputStream();
		try (PrintStream capture = new PrintStream(errors, true, StandardCharsets.UTF_8)) {
			System.setErr(capture);
			checkTrainType("train_12", TransportMode.TRAIN, 13, 1);
			checkTrainType("train_12_bad", TransportMode.TRAIN, 13, 1);
			checkTrainType("train_bad_4", TransportMode.TRAIN, 2, 1);
			checkTrainType("train_", TransportMode.TRAIN, 2, 1);
			checkTrainType("train_2147483648_1", TransportMode.TRAIN, 2, 1);
		} finally {
			System.setErr(previousError);
		}
		final String printed = errors.toString(StandardCharsets.UTF_8);
		require(printed.contains("NumberFormatException") && printed.contains("ArrayIndexOutOfBoundsException"), "Malformed train parse must preserve diagnostics and partial dimensions");
	}

	private static void checkTrainType(String name, TransportMode mode, int spacing, int width) {
		require(TrainType.getTransportMode(name) == mode, "Train mode changed: " + name);
		require(TrainType.getSpacing(name) == spacing, "Train spacing changed: " + name);
		require(TrainType.getWidth(name) == width, "Train width changed: " + name);
	}

	private static void checkMessagePack() {
		final Map<String, Value> values = new HashMap<>();
		final MessagePackHelper helper = new MessagePackHelper(values);
		require(!helper.getBoolean("missing") && helper.getBoolean("missing", true), "Boolean defaults changed");
		require(helper.getInt("missing") == 0 && helper.getInt("missing", 42) == 42, "Int defaults changed");
		require(helper.getLong("missing") == 0 && helper.getLong("missing", Long.MAX_VALUE) == Long.MAX_VALUE, "Long defaults changed");
		require(helper.getFloat("missing") == 0 && helper.getFloat("missing", 1.25F) == 1.25F, "Float defaults changed");
		require(helper.getDouble("missing") == 0 && helper.getDouble("missing", -2.5) == -2.5, "Double defaults changed");
		require(helper.getString("missing").isEmpty() && helper.getString("missing", null) == null, "String/null defaults changed");
		helper.iterateArrayValue("missing", null);
		helper.iterateMapValue("missing", null);

		values.put("boolean", ValueFactory.newBoolean(true));
		values.put("int", ValueFactory.newInteger(Integer.MIN_VALUE));
		values.put("long", ValueFactory.newInteger(Long.MAX_VALUE));
		values.put("float", ValueFactory.newFloat(1.25F));
		values.put("double", ValueFactory.newFloat(-2.5));
		values.put("string", ValueFactory.newString("站名|Station"));
		values.put(null, ValueFactory.newInteger(7));
		require(helper.getBoolean("boolean") && helper.getInt("int") == Integer.MIN_VALUE && helper.getLong("long") == Long.MAX_VALUE, "MessagePack integral values changed");
		require(helper.getFloat("float") == 1.25F && helper.getDouble("double") == -2.5 && helper.getString("string", null).equals("站名|Station"), "MessagePack floating/string values changed");
		require(helper.getInt(null, -1) == 7, "A map with a null key must remain usable");
		values.put("null-value", null);
		expect(NullPointerException.class, () -> helper.getInt("null-value", 9));
		expect(RuntimeException.class, () -> helper.getBoolean("string"));
		final MessagePackHelper nullMap = new MessagePackHelper(null);
		expect(NullPointerException.class, () -> nullMap.getInt("anything"));

		values.put("array", ValueFactory.newArray(ValueFactory.newInteger(3), ValueFactory.newInteger(5)));
		final List<Integer> array = new ArrayList<>();
		helper.iterateArrayValue("array", value -> array.add(value.asIntegerValue().asInt()));
		require(array.equals(List.of(3, 5)), "Array callback ordering changed");
		expect(NullPointerException.class, () -> helper.iterateArrayValue("array", null));
		values.put("map", ValueFactory.newMap(Map.of(ValueFactory.newString("a"), ValueFactory.newInteger(11), ValueFactory.newString("b"), ValueFactory.newInteger(13))));
		final Map<String, Integer> visited = new HashMap<>();
		helper.iterateMapValue("map", entry -> visited.put(entry.getKey().asStringValue().asString(), entry.getValue().asIntegerValue().asInt()));
		require(visited.equals(Map.of("a", 11, "b", 13)), "Map callback entries changed");
		expect(NullPointerException.class, () -> helper.iterateMapValue("map", null));
	}

	private static void checkLiftInstructions() {
		final LiftInstructions original = new LiftInstructions();
		require(!original.hasInstructions() && !original.isDirty(), "New lift queue is not empty/clean");
		original.getTargetFloor(null);
		original.arrived();
		original.addInstruction(0, true, 0);
		require(!original.hasInstructions() && !original.isDirty(), "Current-floor request should be a no-op");
		original.addInstruction(0, true, 10);
		require(original.isDirty() && !original.isDirty(), "Lift dirty flag must be consumed once");
		original.addInstruction(0, true, 5);
		require(original.isDirty(), "Inserted stop must mark the queue dirty");
		original.addInstruction(0, true, 5);
		require(!original.isDirty(), "Duplicate stop must not dirty the queue");
		original.addInstruction(0, true, -4);
		require(original.containsInstruction(5, true) && original.containsInstruction(10) && original.containsInstruction(-4, false) && !original.containsInstruction(100), "Lift stop/direction membership changed");
		checkStops(original, new int[]{5, 10, -4}, new boolean[]{true, true, false});

		final List<Integer> target = new ArrayList<>();
		original.getTargetFloor(target::add);
		require(target.equals(List.of(5)), "Lift target is not the queue head");
		expect(NullPointerException.class, () -> original.getTargetFloor(null));
		final LiftInstructions restored;
		final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
		try {
			original.writePacket(packet);
			restored = new LiftInstructions(packet);
			require(packet.readableBytes() == 0 && !restored.isDirty(), "Lift packet has leftover bytes or dirties restored state");
		} finally {
			packet.release();
		}
		checkStops(restored, new int[]{5, 10, -4}, new boolean[]{true, true, false});
		final LiftInstructions copy = new LiftInstructions();
		copy.copyFrom(restored);
		require(!copy.isDirty(), "Copy must reset dirty state");
		restored.arrived();
		require(restored.isDirty(), "Arrival did not mark the queue dirty");
		checkStops(restored, new int[]{10, -4}, new boolean[]{true, false});
		checkStops(copy, new int[]{5, 10, -4}, new boolean[]{true, true, false});
		copy.copyFrom(copy);
		require(!copy.hasInstructions() && !copy.isDirty(), "Legacy self-copy clearing behavior changed");
		restored.isDirty();
		expect(NullPointerException.class, () -> restored.copyFrom(null));
		require(!restored.hasInstructions() && !restored.isDirty(), "Null copy must preserve the legacy clear-before-failure behavior");

		final FriendlyByteBuf negativeCount = new FriendlyByteBuf(Unpooled.buffer());
		try {
			negativeCount.writeInt(-1);
			require(!new LiftInstructions(negativeCount).hasInstructions(), "Legacy negative instruction count must produce an empty queue");
		} finally {
			negativeCount.release();
		}
	}

	private static void checkStops(LiftInstructions queue, int[] floors, boolean[] directions) {
		final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
		try {
			queue.writePacket(packet);
			require(packet.readableBytes() == 4 + floors.length * 5, "Lift wire layout changed");
			require(packet.readInt() == floors.length, "Lift stop count changed");
			for (int index = 0; index < floors.length; index++) {
				require(packet.readInt() == floors[index] && packet.readBoolean() == directions[index], "Lift stop ordering/direction changed at " + index);
			}
			require(packet.readableBytes() == 0, "Unexpected lift packet fields");
		} finally {
			packet.release();
		}
	}

	private static void checkScheduleEntries() {
		final ScheduleEntry original = new ScheduleEntry(Long.MAX_VALUE, 16, Long.MIN_VALUE, -2);
		final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
		try {
			original.writePacket(packet);
			require(packet.readableBytes() == 24, "Schedule wire layout changed");
			final ScheduleEntry copy = new ScheduleEntry(packet);
			require(copy.arrivalMillis == original.arrivalMillis && copy.trainCars == original.trainCars && copy.routeId == original.routeId && copy.currentStationIndex == original.currentStationIndex && packet.readableBytes() == 0, "Schedule round trip changed");
			require(original.compareTo(copy) == -1 && !original.equals(copy), "Legacy equal-key comparison/identity behavior changed");
			require(original.compareTo(new ScheduleEntry(0, 16, Long.MIN_VALUE, -2)) == 1, "Schedule arrival ordering changed");
			require(original.compareTo(new ScheduleEntry(Long.MAX_VALUE, 1, Long.MAX_VALUE, 0)) == -1, "Schedule route tie-break changed");
		} finally {
			packet.release();
		}
	}

	private static void checkConstructorsAndDelay() {
		HiddenLiftInstructions.addInstruction(null, null, false);
		require(HiddenLiftInstructions.calls == 1, "Java subclass static hiding no longer works");
		final DataConverter converter = new DataConverter(123, null, 0x123456);
		require(converter.id == 123 && converter.name == null && converter.color == 0x123456 && !converter.hasTransportMode(), "DataConverter constructor semantics changed");
		final Map<BlockPos, Map<BlockPos, Rail>> rails = new HashMap<>();
		final ModuleFields module = new ModuleFields(rails);
		require(module.retains(rails), "Protected module field references were copied or narrowed");
		require(new ModuleFields(null).retains(null), "Nullable module constructor changed");
		require(SerializedDataBase.PACKET_STRING_READ_LENGTH == 32767, "Packet string bound changed");
		final IPIDSRenderChild display = () -> 7;
		require(display.getDisplayPage() == 7, "Java SAM use of IPIDSRenderChild changed");
		final TrainDelay delay = new TrainDelay();
		require(delay.getDelayTicks() == 0 && delay.getLastDelayTime() == 0 && delay.isExpired(), "Initial train delay state changed");
		final long before = System.currentTimeMillis();
		delay.delaying();
		require(delay.getDelayTicks() == 1 && delay.getLastDelayTime() >= before && delay.getLastDelayTime() <= System.currentTimeMillis(), "First train delay update changed");
	}

	private static void expect(Class<? extends Throwable> expected, Runnable action) {
		try {
			action.run();
		} catch (Throwable failure) {
			if (expected.isInstance(failure)) return;
			throw new AssertionError("Expected " + expected.getName() + ", got " + failure, failure);
		}
		throw new AssertionError("Expected " + expected.getName());
	}

	private static void require(boolean condition, String message) {
		if (!condition) throw new AssertionError(message);
	}

	private enum SpecialEnum { FIRST {}, SECOND }

	/** Compiling this subclass guards the original non-final static entry point. */
	private static final class HiddenLiftInstructions extends LiftInstructions {
		private static int calls;

		public static void addInstruction(Level world, BlockPos pos, boolean topHalfClicked) {
			calls++;
		}
	}

	private static final class ModuleFields extends RailwayDataModuleBase {
		private ModuleFields(Map<BlockPos, Map<BlockPos, Rail>> rails) {
			super(null, null, rails);
		}

		private boolean retains(Map<BlockPos, Map<BlockPos, Rail>> expected) {
			return railwayData == null && world == null && rails == expected;
		}
	}
}
