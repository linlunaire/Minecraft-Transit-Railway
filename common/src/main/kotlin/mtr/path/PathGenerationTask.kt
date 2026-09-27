package mtr.path

import java.lang.ref.WeakReference
import java.util.WeakHashMap
import java.util.concurrent.CancellationException
import java.util.concurrent.Executor
import java.util.function.BooleanSupplier

/** Request lifetime outlives its worker: queued commits must still be cancellable after thread exit. */
object PathGenerationTask {
    // Values must never retain the key thread (directly or through a world/module).
    private val workers = WeakHashMap<Thread, Request>()

    class Request @JvmOverloads constructor(owner: Any? = null) {
        // The optional depot identity must not create registry -> world -> worker retention.
        private val owner = owner?.let { WeakReference(it) }
        @Volatile var isCurrent: Boolean = true
            private set

        fun cancel() { isCurrent = false }
        internal fun owns(candidate: Any?): Boolean = owner == null || candidate != null && owner.get() === candidate
        fun check() {
            checkInterrupted()
            if (!isCurrent) throw CancellationException("Superseded path generation")
        }
    }

    /** Called synchronously by the existing Consumer<Thread>, before a generator starts its worker. */
    @JvmStatic fun register(worker: Thread?, request: Request) {
        if (worker != null) synchronized(workers) { workers[worker] = request }
    }

    @JvmStatic fun current(): Request? = synchronized(workers) { workers[Thread.currentThread()] }

    /** Hot loops use the interrupt flag, without a registry lookup or lock per iteration. */
    @JvmStatic fun checkInterrupted() {
        if (Thread.currentThread().isInterrupted) throw CancellationException("Interrupted path generation")
    }

    /** Managed requests notify on the owner thread, guarded at execution rather than enqueue time. */
    @JvmStatic fun publish(request: Request?, executor: Executor?, action: Runnable) {
        if (Thread.currentThread().isInterrupted || request?.isCurrent == false) return
        if (request == null) action.run() // Preserve direct add-on calls without the railway module.
        else {
            executor!!.execute { if (request.isCurrent) action.run() }
        }
    }

    /** Owner-thread binding changes invalidate queued work, including detach then reattach to the same depot. */
    class PublicationGate {
        private class Binding(val owner: Any?)
        @Volatile private var binding = Binding(null)

        fun bind(owner: Any?) { binding = Binding(owner) }

        fun capture(): BooleanSupplier {
            val capturedBinding = binding
            val request = current()
            checkInterrupted()
            request?.check()
            if (request != null && !request.owns(capturedBinding.owner)) throw CancellationException("Siding belongs to another depot")
            return BooleanSupplier { binding === capturedBinding && (request == null || request.isCurrent) }
        }
    }
}
