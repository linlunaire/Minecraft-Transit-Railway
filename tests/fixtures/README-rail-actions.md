# Rail-action queue migration

The 21 records in `rail-actions-java-26.2-3.3.2.tsv` come from the original
Java MTR release, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
The runner selects the real queue class, verifies language metadata and
refuses to record Kotlin as the baseline. Seven exported JVM contracts are
also checked in compiler output and both finished loader JARs.

`checkRailActionsCompatibility` covers actual `RailActions` construction for
bridges, tunnels and walls, absent rails, nullable keys/arguments, negative
dimensions and integer overflow. It records queue state and callback order for
unfinished/completed heads, enqueue/send failures, build failures, duplicate
ID cancellation, null entries and subsequent ticks. Its 33 assertions include
the original `ArrayList.removeIf` all-or-nothing behavior when a later null
entry throws. Queue and map references remain live, not copied.

Only outgoing packet I/O and the actual block-editing `build()` call are
adapted. The production queue algorithm, constructors, field reads, list
mutation and failure propagation execute unchanged. Player and railway test
objects avoid world construction; the real action constructor still reads
their UUID, name and rail length. Random action IDs are normalized only in
the golden output, not replaced in production.

ANTE's `checkRailActionsWeaving` applies the actual Kotlin Mixin before running
the same 21 records. Six further assertions check the extension interface,
live queue/map/world getters and direct update dispatch. Both final loader
JAR pairs repeat the test with raw-byte source checks. The mixin retains six
exported contracts and its private shadow field name/type.

`checkJavaRailActionsBaseline -PjavaBaselineJar=<original-jar>` reruns the Java
reference. All current implementation checks are attached to `build`. This
batch does not change the legacy single-head queue policy, block-editing time
budget or cancellation complexity, and does not measure server TPS.
