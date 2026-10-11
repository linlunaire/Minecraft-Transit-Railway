package mtr.mappings;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/** Data captured on extraction, with no entity, level, model or mutable text references. */
public final class RenderSnapshot {

	public static final RenderSnapshot EMPTY = new RenderSnapshot(List.of());
	private final List<Submission> submissions;

	RenderSnapshot(List<Submission> submissions) {
		this.submissions = List.copyOf(submissions);
	}

	public void submit(PoseStack matrices, SubmitNodeCollector collector, CameraRenderState cameraState) {
		for (Submission submission : submissions) {
			submission.submit(matrices, collector, cameraState);
		}
	}

	static Submission text(Matrix4f pose, FormattedCharSequence text, float x, float y, int color, boolean shadow, Font.DisplayMode displayMode, int background, int light) {
		final Matrix4f capturedPose = new Matrix4f(pose);
		final List<Glyph> glyphs = new ArrayList<>();
		text.accept((index, style, codePoint) -> {
			glyphs.add(new Glyph(index, style, codePoint));
			return true;
		});
		final List<Glyph> capturedGlyphs = List.copyOf(glyphs);
		final FormattedCharSequence capturedText = sink -> {
			for (Glyph glyph : capturedGlyphs) {
				if (!sink.accept(glyph.index, glyph.style, glyph.codePoint)) {
					return false;
				}
			}
			return true;
		};
		return (matrices, collector, cameraState) -> {
			matrices.pushPose();
			try {
				matrices.mulPose(capturedPose);
				collector.submitText(matrices, x, y, capturedText, shadow, displayMode, light, color, background, 0);
			} finally {
				matrices.popPose();
			}
		};
	}

	@FunctionalInterface
	interface Submission {
		void submit(PoseStack matrices, SubmitNodeCollector collector, CameraRenderState cameraState);
	}

	private record Glyph(int index, Style style, int codePoint) {
	}
}
