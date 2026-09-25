package mtr.mappings;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

/**
 * Render-thread-owned buffer lifetime policy, independent of GPU allocation.
 * The budget is soft: current-frame geometry is pinned through execution, even
 * when the active working set exceeds it. After a frame, retained bytes are at
 * most max(budget, active working set); invisible entries never expand that bound.
 */
final class FrameGeometryCache<K, V> {

	private final Map<K, Entry> entries = new IdentityHashMap<>();
	private final long budgetBytes;
	private final int maxIdleFrames;
	private final ToLongFunction<V> size;
	private final Consumer<V> dispose;
	private long frame;
	private long bytes;
	private Entry oldest;
	private Entry newest;

	FrameGeometryCache(long budgetBytes, int maxIdleFrames, ToLongFunction<V> size, Consumer<V> dispose) {
		this.budgetBytes = budgetBytes;
		this.maxIdleFrames = maxIdleFrames;
		this.size = size;
		this.dispose = dispose;
	}

	void beginFrame() {
		frame++;
		while (oldest != null && frame - oldest.lastFrame > maxIdleFrames) {
			retireOldest();
		}
	}

	V get(K key, Function<K, V> create) {
		Entry entry = entries.get(key);
		if (entry == null) {
			final V value = create.apply(key);
			entry = new Entry(key, value, size.applyAsLong(value));
			entries.put(key, entry);
			bytes += entry.bytes;
		} else {
			unlink(entry);
		}
		entry.lastFrame = frame;
		entry.previous = newest;
		if (newest == null) oldest = entry;
		else newest.next = entry;
		newest = entry;
		return entry.value;
	}

	void finishFrame() {
		// Access order puts all inactive entries before all pinned current-frame entries.
		// Never throw away an active mesh merely to upload it again on the next frame.
		while (bytes > budgetBytes && oldest != null && oldest.lastFrame != frame) {
			retireOldest();
		}
	}

	void close() {
		RuntimeException failure = null;
		while (oldest != null) {
			try {
				retireOldest();
			} catch (RuntimeException exception) {
				if (failure == null) failure = exception;
				else failure.addSuppressed(exception);
			}
		}
		if (failure != null) throw failure;
	}

	private void retireOldest() {
		final Entry entry = oldest;
		unlink(entry);
		entries.remove(entry.key);
		bytes -= entry.bytes;
		dispose.accept(entry.value);
	}

	private void unlink(Entry entry) {
		if (entry.previous == null) oldest = entry.next;
		else entry.previous.next = entry.next;
		if (entry.next == null) newest = entry.previous;
		else entry.next.previous = entry.previous;
		entry.previous = null;
		entry.next = null;
	}

	private final class Entry {
		private final K key;
		private final V value;
		private final long bytes;
		private long lastFrame;
		private Entry previous;
		private Entry next;
		private Entry(K key, V value, long bytes) {
			this.key = key;
			this.value = value;
			this.bytes = bytes;
		}
	}
}
