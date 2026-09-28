package mtr.data

import mtr.block.BlockLiftTrackFloor
import mtr.block.BlockPSDAPGDoorBase
import mtr.block.IBlock
import mtr.mappings.Utilities
import mtr.packet.IPacket
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import org.msgpack.core.MessagePacker
import org.msgpack.value.Value
import java.io.IOException
import java.util.*

@JvmSuppressWildcards
abstract class Lift : NameColorDataBase, IPacket {
    @JvmField var liftHeight: Int
    @JvmField var liftWidth: Int
    @JvmField var liftDepth: Int
    @JvmField var liftOffsetX: Int
    @JvmField var liftOffsetY: Int
    @JvmField var liftOffsetZ: Int
    @JvmField var isDoubleSided: Boolean
    @JvmField var liftStyle: LiftStyle?
    @JvmField var facing: Direction?

    @JvmField protected var currentPositionX: Double
    @JvmField protected var currentPositionY: Double
    @JvmField protected var currentPositionZ: Double
    @JvmField protected var liftDirection: LiftDirection? = LiftDirection.NONE
    @JvmField protected var speed = 0.0
    @JvmField protected var doorOpen = true
    @JvmField protected var doorValue = 0F
    @JvmField protected var frontCanOpen = false
    @JvmField protected var backCanOpen = false

    @JvmField val liftInstructions: LiftInstructions
    @JvmField protected val floors: MutableList<BlockPos> = ArrayList()
    @JvmField protected val ridingEntities: MutableSet<UUID?> = HashSet()

    constructor(pos: BlockPos?, facing: Direction?) {
        liftHeight = 4
        liftWidth = 2
        liftDepth = 2
        liftOffsetX = 0
        liftOffsetY = 0
        liftOffsetZ = 0
        isDoubleSided = false
        liftStyle = LiftStyle.TRANSPARENT
        this.facing = facing
        currentPositionX = pos!!.x.toDouble()
        currentPositionY = pos.y.toDouble()
        currentPositionZ = pos.z.toDouble()
        liftInstructions = LiftInstructions()
    }

    constructor(map: MutableMap<String?, Value?>?) : super(map) {
        val helper = MessagePackHelper(map)
        liftHeight = helper.getInt(KEY_LIFT_HEIGHT)
        liftWidth = helper.getInt(KEY_LIFT_WIDTH)
        liftDepth = helper.getInt(KEY_LIFT_DEPTH)
        liftOffsetX = helper.getInt(KEY_LIFT_OFFSET_X)
        liftOffsetY = helper.getInt(KEY_LIFT_OFFSET_Y)
        liftOffsetZ = helper.getInt(KEY_LIFT_OFFSET_Z)
        isDoubleSided = helper.getBoolean(KEY_IS_DOUBLE_SIDED)
        liftStyle = EnumHelper.valueOf(LiftStyle.TRANSPARENT, helper.getString(KEY_LIFT_STYLE))
        facing = Direction.fromYRot(helper.getInt(KEY_FACING).toDouble())
        currentPositionX = helper.getDouble(KEY_CURRENT_POSITION_X)
        currentPositionY = helper.getDouble(KEY_CURRENT_POSITION_Y)
        currentPositionZ = helper.getDouble(KEY_CURRENT_POSITION_Z)
        helper.iterateArrayValue(KEY_RIDING_ENTITIES) { ridingEntities.add(UUID.fromString(it.asStringValue().asString())) }
        helper.iterateArrayValue(KEY_FLOORS) { floors.add(BlockPos.of(it.asIntegerValue().toLong())) }
        liftInstructions = LiftInstructions()
        doorOpen = true
        doorValue = 0F
    }

    constructor(packet: FriendlyByteBuf?) : super(packet) {
        liftHeight = packet!!.readInt()
        liftWidth = packet.readInt()
        liftDepth = packet.readInt()
        liftOffsetX = packet.readInt()
        liftOffsetY = packet.readInt()
        liftOffsetZ = packet.readInt()
        isDoubleSided = packet.readBoolean()
        liftStyle = EnumHelper.valueOf(LiftStyle.TRANSPARENT, packet.readUtf(PACKET_STRING_READ_LENGTH))
        facing = Direction.fromYRot(packet.readInt().toDouble())
        currentPositionX = packet.readDouble()
        currentPositionY = packet.readDouble()
        currentPositionZ = packet.readDouble()
        liftDirection = EnumHelper.valueOf(LiftDirection.NONE, packet.readUtf(PACKET_STRING_READ_LENGTH))
        speed = packet.readDouble()
        doorOpen = packet.readBoolean()
        doorValue = packet.readFloat()
        val ridingEntitiesCount = packet.readInt()
        for (index in 0 until ridingEntitiesCount) ridingEntities.add(packet.readUUID())
        val floorCount = packet.readInt()
        for (index in 0 until floorCount) floors.add(packet.readBlockPos())
        liftInstructions = LiftInstructions(packet)
    }

    @Throws(IOException::class)
    override fun toMessagePack(messagePacker: MessagePacker) {
        super.toMessagePack(messagePacker)
        messagePacker.packString(KEY_LIFT_HEIGHT).packInt(liftHeight)
        messagePacker.packString(KEY_LIFT_WIDTH).packInt(liftWidth)
        messagePacker.packString(KEY_LIFT_DEPTH).packInt(liftDepth)
        messagePacker.packString(KEY_LIFT_OFFSET_X).packInt(liftOffsetX)
        messagePacker.packString(KEY_LIFT_OFFSET_Y).packInt(liftOffsetY)
        messagePacker.packString(KEY_LIFT_OFFSET_Z).packInt(liftOffsetZ)
        messagePacker.packString(KEY_IS_DOUBLE_SIDED).packBoolean(isDoubleSided)
        messagePacker.packString(KEY_LIFT_STYLE).packString(liftStyle!!.toString())
        messagePacker.packString(KEY_FACING).packInt(Math.round(facing!!.toYRot()))
        val closestFloor = getCurrentFloorBlockPos()
        messagePacker.packString(KEY_CURRENT_POSITION_X).packDouble(closestFloor!!.x.toDouble())
        messagePacker.packString(KEY_CURRENT_POSITION_Y).packDouble(closestFloor.y.toDouble())
        messagePacker.packString(KEY_CURRENT_POSITION_Z).packDouble(closestFloor.z.toDouble())
        messagePacker.packString(KEY_RIDING_ENTITIES).packArrayHeader(ridingEntities.size)
        for (uuid in ridingEntities) messagePacker.packString(uuid!!.toString())
        messagePacker.packString(KEY_FLOORS).packArrayHeader(floors.size)
        for (floor in floors) messagePacker.packLong(floor.asLong())
    }

    override fun messagePackLength(): Int = super.messagePackLength() + 14

    override fun writePacket(packet: FriendlyByteBuf) {
        super.writePacket(packet)
        packet.writeInt(liftHeight)
        packet.writeInt(liftWidth)
        packet.writeInt(liftDepth)
        packet.writeInt(liftOffsetX)
        packet.writeInt(liftOffsetY)
        packet.writeInt(liftOffsetZ)
        packet.writeBoolean(isDoubleSided)
        packet.writeUtf(liftStyle!!.toString())
        packet.writeInt(Math.round(facing!!.toYRot()))
        packet.writeDouble(currentPositionX)
        packet.writeDouble(currentPositionY)
        packet.writeDouble(currentPositionZ)
        packet.writeUtf(liftDirection!!.toString())
        packet.writeDouble(speed)
        packet.writeBoolean(doorOpen)
        packet.writeFloat(doorValue)
        packet.writeInt(ridingEntities.size)
        ridingEntities.forEach(java.util.function.Consumer<UUID?> { packet.writeUUID(javaReference(it)) })
        packet.writeInt(floors.size)
        floors.forEach(java.util.function.Consumer<BlockPos?> { packet.writeBlockPos(javaReference(it)) })
        liftInstructions.writePacket(packet)
    }

    override fun update(key: String?, packet: FriendlyByteBuf?) {
        if (KEY_LIFT_UPDATE == key) {
            liftHeight = packet!!.readInt()
            liftWidth = packet.readInt()
            liftDepth = packet.readInt()
            liftOffsetX = packet.readInt()
            liftOffsetY = packet.readInt()
            liftOffsetZ = packet.readInt()
            isDoubleSided = packet.readBoolean()
            liftStyle = EnumHelper.valueOf(LiftStyle.TRANSPARENT, packet.readUtf(PACKET_STRING_READ_LENGTH))
            facing = Direction.fromYRot(packet.readInt().toDouble())
        } else {
            super.update(key, packet)
        }
    }

    override fun hasTransportMode(): Boolean = false

    open fun setFloors(floors: MutableList<BlockPos>?) {
        this.floors.clear()
        this.floors.addAll(floors!!)
    }

    open fun hasFloor(pos: BlockPos?): Boolean = floors.contains(pos)
    open fun getPositionX(): Double = currentPositionX
    open fun getPositionY(): Double = currentPositionY
    open fun getPositionZ(): Double = currentPositionZ
    open fun getLiftDirection(): LiftDirection? = liftDirection

    open fun hasUpDownButtonForFloor(checkFloor: Int, hasButton: BooleanArray?) {
        floors.forEach(java.util.function.Consumer<BlockPos?> { floor ->
            if (floor!!.y > checkFloor) hasButton!![0] = true
            if (floor.y < checkFloor) hasButton!![1] = true
        })
    }

    open fun pressButton(floor: Int) {
        val movingUp = liftDirection == LiftDirection.UP
        liftInstructions.addInstruction((if (movingUp) Math.floor(currentPositionY) else Math.ceil(currentPositionY)).toInt(), movingUp, floor)
    }

    open fun getCurrentFloorBlockPos(): BlockPos? {
        var distance = Double.MAX_VALUE
        var closestFloor: BlockPos? = null
        for (floor in floors) {
            val difference = Math.abs(currentPositionY - floor.y)
            if (difference < distance) {
                distance = difference
                closestFloor = floor
            } else {
                return closestFloor
            }
        }
        return closestFloor
    }

    open fun isInvalidLift(world: Level?): Boolean {
        if (floors.isEmpty()) return true
        for (checkFloor in floors) {
            if (RailwayData.chunkLoaded(world, checkFloor) && world!!.getBlockState(checkFloor).block !is BlockLiftTrackFloor) return true
        }
        return false
    }

    protected open fun tick(world: Level?, ticksElapsed: Float) {
        if (liftInstructions.hasInstructions() && doorValue == (DOOR_MAX * 2).toFloat()) {
            doorOpen = false
            liftInstructions.getTargetFloor { targetFloor -> liftDirection = if (targetFloor > currentPositionY) LiftDirection.UP else LiftDirection.DOWN }
        } else if (!liftInstructions.hasInstructions()) {
            liftDirection = LiftDirection.NONE
        }

        if (!doorOpen && doorValue == 0F) {
            liftInstructions.getTargetFloor { targetFloor ->
                val stoppingDistance = Math.abs(targetFloor - currentPositionY)
                liftDirection = if (stoppingDistance < Train.ACCELERATION_DEFAULT) LiftDirection.NONE else if (targetFloor > currentPositionY) LiftDirection.UP else LiftDirection.DOWN
                if (liftDirection == LiftDirection.NONE) {
                    speed = 0.0
                    doorOpen = true
                    currentPositionY = targetFloor.toDouble()
                    liftInstructions.arrived()
                    if (!world!!.isClientSide()) {
                        val blockEntity: BlockEntity? = world.getBlockEntity(getBlockPos())
                        if (blockEntity is BlockLiftTrackFloor.TileEntityLiftTrackFloor && blockEntity.shouldDing) {
                            world.playSound(null, getBlockPos(), Utilities.unwrapSoundEvent(SoundEvents.NOTE_BLOCK_PLING), SoundSource.BLOCKS, 16F, 2F)
                        }
                    }
                } else {
                    speed = if (stoppingDistance < 0.5 * speed * speed / Train.ACCELERATION_DEFAULT) {
                        Math.max(speed - 0.5 * speed * speed / stoppingDistance * ticksElapsed, Train.ACCELERATION_DEFAULT.toDouble())
                    } else {
                        Math.min(speed + Train.ACCELERATION_DEFAULT * ticksElapsed, 1.0)
                    }
                    currentPositionY += speed * liftDirection!!.speedMultiplier * ticksElapsed
                }
            }
        } else {
            if (!doorOpen && doorValue > 0 || doorOpen && doorValue < DOOR_MAX * 2) {
                doorValue = if (doorOpen) Math.min(doorValue + ticksElapsed, (DOOR_MAX * 2).toFloat()) else Math.max(doorValue - ticksElapsed, 0F)
            }
            frontCanOpen = checkDoor(world, true)
            if (isDoubleSided) backCanOpen = checkDoor(world, false)
        }
    }

    protected open fun getYaw(): Float = Math.toRadians(-facing!!.clockWise.toYRot().toDouble()).toFloat()

    private fun getBlockPos(): BlockPos = RailwayData.newBlockPos(currentPositionX, currentPositionY, currentPositionZ)

    // Java callers can insert null into exposed collections. Preserve dispatch to
    // overridable packet methods instead of rejecting their input at the Kotlin call site.
    @Suppress("UNCHECKED_CAST")
    private fun <T> javaReference(value: T?): T = value as T

    private fun checkDoor(world: Level?, front: Boolean): Boolean {
        val directionClockwise = facing!!.clockWise
        val sign = if (front) 1 else -1
        var hasDoor = false
        for (index in -1..1) {
            val checkPos = RailwayData.newBlockPos(
                currentPositionX + liftOffsetX / 2F - facing!!.stepX * sign * (liftDepth / 2F + 0.5) + directionClockwise.stepX * index,
                currentPositionY + liftOffsetY,
                currentPositionZ + liftOffsetZ / 2F - facing!!.stepZ * sign * (liftDepth / 2F + 0.5) + directionClockwise.stepZ * index
            )
            if (world!!.getNearestPlayer(currentPositionX, currentPositionY, currentPositionZ, Train.MAX_CHECK_DISTANCE.toDouble()) { true } != null && RailwayData.chunkLoaded(world, checkPos) && RailwayData.chunkLoaded(world, checkPos.above())) {
                val entity1 = world.getBlockEntity(checkPos)
                val entity2 = world.getBlockEntity(checkPos.above())
                if (entity1 is BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase && entity2 is BlockPSDAPGDoorBase.TileEntityPSDAPGDoorBase && IBlock.getStatePropertySafe(world, checkPos, BlockPSDAPGDoorBase.UNLOCKED) && IBlock.getStatePropertySafe(world, checkPos.above(), BlockPSDAPGDoorBase.UNLOCKED)) {
                    if (!world.isClientSide()) {
                        entity1.setOpen(Math.min(Math.round(doorValue), DOOR_MAX))
                        entity2.setOpen(Math.min(Math.round(doorValue), DOOR_MAX))
                    }
                    hasDoor = true
                }
            }
        }
        return hasDoor
    }

    enum class LiftDirection(internal val speedMultiplier: Int) { NONE(0), UP(1), DOWN(-1) }
    enum class LiftStyle { TRANSPARENT, OPAQUE }

    companion object {
        const val DOOR_MAX = 24
        protected const val KEY_LIFT_UPDATE = "lift_update"
        private const val KEY_LIFT_HEIGHT = "lift_height"
        private const val KEY_LIFT_WIDTH = "lift_width"
        private const val KEY_LIFT_DEPTH = "lift_depth"
        private const val KEY_LIFT_OFFSET_X = "lift_offset_x"
        private const val KEY_LIFT_OFFSET_Y = "lift_offset_y"
        private const val KEY_LIFT_OFFSET_Z = "lift_offset_z"
        private const val KEY_IS_DOUBLE_SIDED = "is_double_sided"
        private const val KEY_LIFT_STYLE = "lift_style"
        private const val KEY_FACING = "facing"
        private const val KEY_CURRENT_POSITION_X = "current_position_x"
        private const val KEY_CURRENT_POSITION_Y = "current_position_y"
        private const val KEY_CURRENT_POSITION_Z = "current_position_z"
        private const val KEY_RIDING_ENTITIES = "riding_entities"
        private const val KEY_FLOORS = "floors"
    }
}
