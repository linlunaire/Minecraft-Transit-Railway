package mtr.data

import io.netty.buffer.Unpooled
import mtr.KeyMappings
import mtr.mappings.Text
import mtr.mappings.UtilitiesClient
import mtr.model.ModelLift1
import mtr.render.RenderTrains
import mtr.screen.LiftSelectionScreen
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import java.util.UUID
import java.util.function.Consumer

@JvmSuppressWildcards
open class LiftClient(packet: FriendlyByteBuf?) : Lift(packet) {
    private val vehicleRidingClient = VehicleRidingClient(ridingEntities, mtr.packet.IPacket.PACKET_UPDATE_LIFT_PASSENGER_POSITION)
    private var liftModel: ModelLift1? = null

    open fun tickClient(world: Level?, renderLift: RenderLift?, ticksElapsed: Float) {
        tick(world, ticksElapsed)
        vehicleRidingClient.begin()
        if (ticksElapsed > 0) {
            vehicleRidingClient.movePlayer { uuid ->
                vehicleRidingClient.setOffsets(uuid,
                    currentPositionX + liftOffsetX / 2F, currentPositionY + liftOffsetY, currentPositionZ + liftOffsetZ / 2F,
                    getYaw(), 0F, (liftWidth - 1).toDouble(), liftDepth - 1, frontCanOpen, backCanOpen, false, false,
                    0F, 0F, speed > 0, doorValue == 0F) {}
                vehicleRidingClient.moveSelf(id, uuid, (liftWidth - 1).toDouble(), liftDepth - 1, getYaw(),
                    0, 1, frontCanOpen, backCanOpen, true, ticksElapsed)
            }
        }
        vehicleRidingClient.end()

        val offset: Vec3 = vehicleRidingClient.renderPlayerAndGetOffset()
        val newX = currentPositionX + liftOffsetX / 2F - offset.x
        val newY = currentPositionY + liftOffsetY - offset.y
        val newZ = currentPositionZ + liftOffsetZ / 2F - offset.z
        renderLift!!.renderLift(newX, newY, newZ,
            if (frontCanOpen) Math.min(doorValue / DOOR_MAX, 1F) else 0F,
            if (backCanOpen) Math.min(doorValue / DOOR_MAX, 1F) else 0F)

        val minecraftClient = Minecraft.getInstance()
        val player: LocalPlayer? = minecraftClient.player
        if (player != null && ridingEntities.contains(player.uuid)) {
            if (KeyMappings.LIFT_MENU.isDown && minecraftClient.gui.screen() !is LiftSelectionScreen) {
                UtilitiesClient.setScreen(minecraftClient, LiftSelectionScreen(this))
            }
            if (RenderTrains.showShiftProgressBar()) {
                mtr.mappings.PlayerUtilities.displayClientMessage(player, Text.translatable("gui.mtr.press_to_select_floor", KeyMappings.LIFT_MENU.translatedKeyMessage), true)
            }
        }
    }

    open fun getModel(): ModelLift1? {
        if (liftModel == null) liftModel = ModelLift1(liftHeight, liftWidth, liftDepth, isDoubleSided)
        return liftModel
    }

    open fun copyFromLift(lift: LiftClient?) {
        liftHeight = lift!!.liftHeight
        liftWidth = lift.liftWidth
        liftDepth = lift.liftDepth
        liftOffsetX = lift.liftOffsetX
        liftOffsetY = lift.liftOffsetY
        liftOffsetZ = lift.liftOffsetZ
        isDoubleSided = lift.isDoubleSided
        liftStyle = lift.liftStyle
        facing = lift.facing
        currentPositionX = lift.currentPositionX
        currentPositionY = lift.currentPositionY
        currentPositionZ = lift.currentPositionZ
        liftDirection = lift.liftDirection
        speed = lift.speed
        doorOpen = lift.doorOpen
        doorValue = lift.doorValue
        frontCanOpen = lift.frontCanOpen
        backCanOpen = lift.backCanOpen
        ridingEntities.clear()
        ridingEntities.addAll(lift.ridingEntities)
        floors.clear()
        floors.addAll(lift.floors)
        liftInstructions.copyFrom(lift.liftInstructions)
        liftModel = null
    }

    open fun setExtraData(sendPacket: Consumer<FriendlyByteBuf>?) {
        val packet = FriendlyByteBuf(Unpooled.buffer())
        packet.writeLong(id)
        packet.writeUtf(transportMode!!.toString())
        packet.writeUtf(KEY_LIFT_UPDATE)
        packet.writeInt(liftHeight)
        packet.writeInt(liftWidth)
        packet.writeInt(liftDepth)
        packet.writeInt(liftOffsetX)
        packet.writeInt(liftOffsetY)
        packet.writeInt(liftOffsetZ)
        packet.writeBoolean(isDoubleSided)
        packet.writeUtf(liftStyle!!.toString())
        packet.writeInt(Math.round(facing!!.toYRot()))
        sendPacket!!.accept(packet)
    }

    open fun startRidingClient(uuid: UUID?, percentageX: Float, percentageZ: Float) {
        vehicleRidingClient.startRiding(uuid, percentageX, percentageZ)
    }

    open fun updateRiderPercentages(uuid: UUID?, percentageX: Float, percentageZ: Float) {
        vehicleRidingClient.updateRiderPercentages(uuid, percentageX, percentageZ)
    }

    open fun iterateFloors(consumer: Consumer<BlockPos>?) {
        floors.forEach(consumer)
    }

    open fun getViewOffset(): Vec3? = vehicleRidingClient.getViewOffset()

    @FunctionalInterface
    fun interface RenderLift {
        fun renderLift(x: Double, y: Double, z: Double, frontDoorValue: Float, backDoorValue: Float)
    }
}
