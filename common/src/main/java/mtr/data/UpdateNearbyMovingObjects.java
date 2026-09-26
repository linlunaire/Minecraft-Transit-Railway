package mtr.data;

import io.netty.buffer.Unpooled;
import mtr.Registry;
import mtr.packet.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class UpdateNearbyMovingObjects<T extends NameColorDataBase> implements IPacket {

	public final Map<Player, Set<T>> newDataSetInPlayerRange = new HashMap<>();
	public final Set<T> dataSetToSync = new HashSet<>();
	private final Map<Player, Set<T>> dataSetInPlayerRange = new HashMap<>();
	private final Map<T, byte[]> serializedData = new HashMap<>();
	private final ResourceLocation deletePacketId;
	private final ResourceLocation updatePacketId;

	public UpdateNearbyMovingObjects(ResourceLocation deletePacketId, ResourceLocation updatePacketId) {
		this.deletePacketId = deletePacketId;
		this.updatePacketId = updatePacketId;
	}

	public void startTick() {
		newDataSetInPlayerRange.clear();
		dataSetToSync.clear();
		serializedData.clear();
	}

	public void tick() {
		dataSetInPlayerRange.forEach((player, dataSet) -> {
			final Set<T> newDataSet = newDataSetInPlayerRange.get(player);
			for (final T data : dataSet) {
				if (newDataSet == null || !newDataSet.contains(data)) {
					final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());

					if (newDataSet == null) {
						packet.writeInt(0);
					} else {
						packet.writeInt(newDataSet.size());
						newDataSet.forEach(dataToKeep -> packet.writeLong(dataToKeep.id));
					}

					if (packet.readableBytes() <= MAX_PACKET_BYTES) {
						Registry.sendToPlayer((ServerPlayer) player, deletePacketId, packet);
					}

					break;
				}
			}
		});

		newDataSetInPlayerRange.forEach((player, dataSet) -> {
			final Set<T> oldDataSet = dataSetInPlayerRange.get(player);
			FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
			for (final T data : dataSet) {
				if (dataSetToSync.contains(data) || oldDataSet == null || !oldDataSet.contains(data)) {
					final byte[] dataPacket = getSerializedData(data);
					if (dataPacket.length < MAX_PACKET_BYTES) {
						if (packet.readableBytes() + dataPacket.length >= MAX_PACKET_BYTES) {
							Registry.sendToPlayer((ServerPlayer) player, updatePacketId, packet);
							packet = new FriendlyByteBuf(Unpooled.buffer());
						}
						packet.writeBytes(dataPacket);
					}
				}
			}

			if (packet.readableBytes() > 0) {
				Registry.sendToPlayer((ServerPlayer) player, updatePacketId, packet);
			}
		});

		dataSetInPlayerRange.clear();
		dataSetInPlayerRange.putAll(newDataSetInPlayerRange);
	}

	private byte[] getSerializedData(T data) {
		byte[] dataPacket = serializedData.get(data);
		if (dataPacket == null) {
			final FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());
			data.writePacket(packet);
			dataPacket = new byte[packet.readableBytes()];
			packet.getBytes(packet.readerIndex(), dataPacket);
			serializedData.put(data, dataPacket);
		}
		return dataPacket;
	}
}
