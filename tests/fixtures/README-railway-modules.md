# Railway module migration fixtures

`railway-modules-java-26.2-3.3.2.tsv` was recorded from the frozen original
MTR Java release (SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`).
Recording refuses Kotlin output. The independent ABI snapshot retains 15
exported class/method contracts, including overloads and generic signatures.

`checkKotlinRailwayModulesCompatibility` runs 204 assertions and compares 15
golden records. `checkKotlinRailwayModulesAbi` and both final-loader artifact
checks verify compiled contracts. These gates are dependencies of `build`.
`checkJavaRailwayModulesBaseline -PjavaBaselineJar=<original-jar>` reruns the
behavior fixture against that Java release without recording new goldens.

## Boundaries exercised

- Path generation: missing depot notification, unstarted/dead-worker cleanup,
  restart interruption, different-depot isolation, synchronous callback
  publication, failures before/after publication, reset ordering and nullable
  worker/name behavior. Real latch-controlled threads are always released and
  joined; this is not a server load or route-search benchmark.
- Driving: all 64 input/result combinations, multiple riders, per-tick input
  union, acceleration/braking priority, independent door changes, repeated
  application, tick clearing and exception retention. The actual modules run
  against constructor-free player/train probes; control outcomes are adapters.
- Logging: real CSV creation/append/retry, quoting and newline handling, sorted
  positions, unchanged-data no-op, CREATE/EDIT/DELETE, immutable queued strings,
  malformed data rejection, reduced/full MessagePack extraction, dangling
  field handling and numeric/collection/nested NBT conversion.

Only the two production `new Date()` sites are adapted to
`2027-01-15T08:00:00.123Z`; locale/timezone are restored after the fixture.
Packet delivery is replaced by a recording adapter. Minecraft registries are
bootstrapped, but no world/server/network connection is started. Each logging
run creates and cleans up only its own canonical temporary directory.

The old Java driver reads a protected field through package access. Its
selected `Train` and `TrainServer` classes share the fixture loader with the
driver so Java's runtime-package check is real. Kotlin uses the additive
`Train.getRidingEntities()` live-iteration method: no set copy, synchronization
policy change, or unsupported compiler visibility suppression.

## Deliberately unchanged behavior and interop limits

One platform thread per path request, interrupt-without-join restarts, and the
existing failure publication semantics are unchanged. The fixture does not
establish bounded concurrent route generation or 80-player server capacity.
Logging retains the original retry queue and direct append/write policy; it
does not add transactional writes or log rotation.

Kotlin varargs retain the public JVM varargs descriptors and nullable position
elements, but not a nullable whole array. A Java caller passing a null whole
array now fails at entry instead of during iteration. The short overload still
virtually dispatches to the full overload with the same position elements;
Kotlin's spread call copies the array, unlike Java's same-array forwarding.
External subclasses must not rely on array identity or interception of that
invalid null-array call. This is a documented interop difference, not claimed
as exact behavioral equivalence for every possible external subclass.

CSV formatting calls `java.lang.String.format` directly to avoid introducing
Kotlin's extra vararg spread copies around Java formatting. No FPS/TPS claim
is made from these compatibility checks.
