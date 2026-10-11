package mtr.mixin;

/**
 * The pre-1.20.2 raw custom-payload interception hook.  Networking is now
 * handled through Architectury's typed payload API, so it is intentionally
 * not registered on 1.21.1.
 */
public final class UnknownPacketMixin {
	private UnknownPacketMixin() {
	}
}
