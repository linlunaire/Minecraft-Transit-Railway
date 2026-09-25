package mtr.mappings;

import java.util.ArrayList;
import java.util.List;

/** Exercises the real renderer cache policy without allocating native/GPU buffers. */
public final class FrameGeometryCacheCheck {

	public static void main(String[] args) {
		activeSceneDoesNotThrash();
		largeWorkingSetDoesNotThrash();
		inactiveEntriesRetireInAccessOrder();
		idleEntriesExpire();
		geometryUsesIdentity();
		failedUploadDoesNotPoisonCache();
		failedDisposalDoesNotLeakRemainingBuffers();
		System.out.println("PASS: oversized active-scene reuse, 4096 meshes over 200 frames, inactive LRU retirement, idle expiry, identity keys, upload failure, complete/idempotent disposal (no GPU/runtime claim)");
	}

	private static void activeSceneDoesNotThrash() {
		final List<Buffer> allocated = new ArrayList<>();
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(256, 120, buffer -> buffer.bytes, Buffer::close);
		final Object[] geometry = {new Object(), new Object(), new Object(), new Object()};
		for (int frame = 0; frame < 10; frame++) {
			cache.beginFrame();
			for (Object key : geometry) {
				final Buffer buffer = cache.get(key, ignored -> allocate(allocated, 80));
				require(!buffer.closed, "Prepared draw references a closed buffer");
			}
			cache.finishFrame();
		}
		require(allocated.size() == geometry.length,
			"Active scene over budget repeatedly uploads meshes: expected " + geometry.length + " allocations, got " + allocated.size());
		cache.close();
		cache.close();
		allocated.forEach(buffer -> require(buffer.closed, "Buffer leaked on resource reload"));
	}

	private static void largeWorkingSetDoesNotThrash() {
		final List<Buffer> allocated = new ArrayList<>();
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(1024, 120, buffer -> buffer.bytes, Buffer::close);
		final Object[] geometry = new Object[4096];
		for (int index = 0; index < geometry.length; index++) geometry[index] = new Object();
		for (int frame = 0; frame < 200; frame++) {
			cache.beginFrame();
			for (Object key : geometry) require(!cache.get(key, ignored -> allocate(allocated, 80)).closed, "Active mesh retired");
			cache.finishFrame();
		}
		require(allocated.size() == geometry.length, "Large stable scene re-uploaded geometry");
		cache.beginFrame();
		final Buffer replacement = cache.get(new Object(), ignored -> allocate(allocated, 2048));
		allocated.forEach(buffer -> require(!buffer.closed, "Preparation prematurely disposed a buffer"));
		cache.finishFrame();
		for (int index = 0; index < geometry.length; index++) require(allocated.get(index).closed, "Invisible large scene leaked beyond the soft budget");
		require(!replacement.closed, "Replacement active mesh was evicted");
		cache.close();
	}

	private static void inactiveEntriesRetireInAccessOrder() {
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(20, 120, buffer -> buffer.bytes, Buffer::close);
		final Object a = new Object(), b = new Object(), c = new Object();
		cache.beginFrame();
		final Buffer first = cache.get(a, ignored -> new Buffer(10));
		final Buffer second = cache.get(b, ignored -> new Buffer(10));
		final Buffer third = cache.get(c, ignored -> new Buffer(10));
		cache.finishFrame();
		cache.beginFrame();
		require(cache.get(a, ignored -> { throw new AssertionError("Warm buffer missed"); }) == first, "Identity cache miss");
		cache.finishFrame();
		require(second.closed && !third.closed && !first.closed, "Least-recently-used inactive entry was not retired");
		cache.beginFrame();
		final Buffer fourth = cache.get(new Object(), ignored -> new Buffer(10));
		cache.get(a, ignored -> { throw new AssertionError("Warm buffer missed"); });
		require(!third.closed, "Retired an inactive buffer before prepared draws completed");
		cache.finishFrame();
		require(third.closed && !fourth.closed && !first.closed, "Soft budget did not retire remaining inactive entry");
		cache.close();
	}

	private static void idleEntriesExpire() {
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(256, 120, buffer -> buffer.bytes, Buffer::close);
		cache.beginFrame();
		final Buffer buffer = cache.get(new Object(), ignored -> new Buffer(80));
		cache.finishFrame();
		for (int frame = 0; frame < 120; frame++) {
			cache.beginFrame();
			cache.finishFrame();
		}
		require(!buffer.closed, "Idle mesh expired before its grace period");
		cache.beginFrame();
		require(buffer.closed, "Idle mesh never expired");
		cache.finishFrame();
		cache.close();
	}

	private static void geometryUsesIdentity() {
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(256, 120, buffer -> buffer.bytes, Buffer::close);
		cache.beginFrame();
		final Buffer first = cache.get(new EqualGeometry(1), ignored -> new Buffer(80));
		final Buffer second = cache.get(new EqualGeometry(1), ignored -> new Buffer(80));
		require(first != second, "Rebuilt/equal geometry reused the old GPU buffer");
		cache.finishFrame();
		cache.close();
	}

	private static void failedUploadDoesNotPoisonCache() {
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(256, 120, buffer -> buffer.bytes, Buffer::close);
		final Object key = new Object();
		cache.beginFrame();
		try {
			cache.get(key, ignored -> { throw new IllegalStateException("simulated upload failure"); });
			throw new AssertionError("Upload failure swallowed");
		} catch (IllegalStateException expected) { }
		final Buffer retry = cache.get(key, ignored -> new Buffer(80));
		require(!retry.closed, "Failed upload prevented retry");
		cache.close();
		require(retry.closed, "Retried buffer leaked on close");
	}

	private static void failedDisposalDoesNotLeakRemainingBuffers() {
		final FrameGeometryCache<Object, Buffer> cache = new FrameGeometryCache<>(256, 120, buffer -> buffer.bytes, buffer -> {
			buffer.close();
			throw new IllegalStateException("simulated disposal failure");
		});
		cache.beginFrame();
		final Buffer first = cache.get(new Object(), ignored -> new Buffer(80));
		final Buffer second = cache.get(new Object(), ignored -> new Buffer(80));
		try {
			cache.close();
			throw new AssertionError("Disposal failure swallowed");
		} catch (IllegalStateException expected) {
			require(expected.getSuppressed().length == 1, "Secondary disposal failure lost");
		}
		require(first.closed && second.closed, "One disposal failure leaked remaining buffers");
		cache.close();
	}

	private record EqualGeometry(int value) { }

	private static Buffer allocate(List<Buffer> allocated, long bytes) {
		final Buffer buffer = new Buffer(bytes);
		allocated.add(buffer);
		return buffer;
	}

	private static final class Buffer {
		private final long bytes;
		private boolean closed;
		private Buffer(long bytes) { this.bytes = bytes; }
		private void close() {
			require(!closed, "Buffer was disposed twice");
			closed = true;
		}
	}

	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
