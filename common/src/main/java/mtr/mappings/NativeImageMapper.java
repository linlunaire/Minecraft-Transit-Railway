package mtr.mappings;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.util.ARGB;

public final class NativeImageMapper {

	private NativeImageMapper() {
	}

	/** Legacy getPixelRGBA returned the native ABGR integer, not the new ARGB API. */
	public static int getPixelABGR(NativeImage image, int x, int y) {
		return ARGB.toABGR(image.getPixel(x, y));
	}
}
