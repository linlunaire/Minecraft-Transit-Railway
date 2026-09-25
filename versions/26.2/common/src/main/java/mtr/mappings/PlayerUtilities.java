package mtr.mappings;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

/** Keep the legacy action-bar flag while using the two explicit message APIs. */
public final class PlayerUtilities {

	private PlayerUtilities() {
	}

	public static void displayClientMessage(Player player, Component message, boolean actionBar) {
		if (actionBar) {
			player.sendOverlayMessage(message);
		} else {
			player.sendSystemMessage(message);
		}
	}
}
