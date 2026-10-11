# Nearby synchronization and rail types

The original Java reference is MTR `26.2-3.3.2`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
The 27 synchronization records and 21 rail-type records were recorded only
from that JAR. Runners select bytecode from the supplied source and verify
Java versus Kotlin metadata. Recording Kotlin output as the reference is
rejected. The two classes and nested slope enum retain 38 exported contracts.

## Packet behavior and ownership

`checkNearbySyncCompatibility` compares payload bytes and ordering, including
80 mock players sharing two objects, unchanged/dirty ticks, partial/full
removal, retained mutable sets, zero-length objects, exact/over-limit packets,
split boundaries, reader indices, serialization/final-send/mid-split failure
and retry, and a
131,073-entry keep-list that must be dropped for exceeding the protocol limit.
Each shared object is still serialized at most once per tick after success.

ASM redirects allocation to a counting factory and outgoing sends to an
adapter that invokes the real `NetworkUtilities.createPayload`. Both loader
implementations synchronously copy bytes into that payload before returning.
The fixture retains those payload arrays and rechecks their hashes after
buffer reuse and release; it does not substitute a serialization algorithm.

`checkNearbySyncOwnership` additionally requires zero owned live buffers after
every scenario. For an unchanged tick with 80 mock players, the original Java
implementation allocates and leaves 80 buffers live; Kotlin allocates zero.
Previously allocated serialization, deletion and update buffers are now
released in `finally`, including failure and oversized-drop paths. A player's
split-packet buffer is cleared and reused only after the synchronous snapshot.
The old implementation fails the ownership gate, rather than merely reporting
a different elapsed time.

These are deliberate resource-lifetime changes. Packet limits, retry-state
publication and caller-owned mutable sets are unchanged. The snapshot-before-
return contract must be revisited if either loader changes its send API to
retain the caller's buffer asynchronously.

## Enum compatibility

`checkKotlinRailTypeCompatibility` verifies original enum order, fields, raw
float results, slope styles, default-speed dispatch and nullable failures.
ANTE extends the enum through Mixin, so its real weaving tests also require
both `values()` and Kotlin `entries` to contain the same 637 extended entries.
Updating only `$VALUES` left Kotlin's `$ENTRIES` stale and failed that test;
ANTE now refreshes both in the same class-initialization injection.

The behavior, ownership and ABI gates run in `build`. Historical comparisons
are available via `checkJavaNearbySyncBaseline` and `checkJavaRailTypeBaseline`
with `-PjavaBaselineJar=<original-jar>`.

No network connection, game world or GPU is started. Mock-player counts are
not multiplayer-capacity evidence, and allocation results are not FPS/TPS.
