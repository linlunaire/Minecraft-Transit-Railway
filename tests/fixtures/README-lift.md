# Lift state, server and client migration

The oracle was recorded before conversion from the Java `Lift`, `LiftServer`
and `LiftClient` classes in MTR `26.2-3.4.0-kotlin.2`, source commit
`33737619dcccdfc6f3296ed5611e946638fd5a63`. Those three Java sources are unchanged
from the frozen `26.2-3.3.2` tag. The newer JAR supplies the currently maintained
dependencies; its SHA-256 is
`600c54aabce022d3896070eae2b7cada64be029e1b9f4485491c381f957c251f`.
Recording refuses any other JAR or Kotlin lift implementation. Ordinary builds
use the checked-in oracle and do not need this local archive.

`checkLiftCompatibility` compares 177 original-Java records and runs 49,491
assertions. `checkKotlinLiftAbi` retains 71 exported contracts, including the
protected mutable fields, static constants, constructors, nullable Java calls,
overridable methods, checked MessagePack exception and render callback.
Both final loader JARs repeat the behavior and ABI checks. The behavior loader
requires the selected classes and nested types rather than falling back to
development output; packaged ABI checks require Kotlin metadata.

Coverage includes:

- All six facing values and both styles; exact packet/MessagePack bytes,
  nearest-floor save position, constructor defaults and transient-state reset.
- Every truncated packet prefix and extra-data update prefix, partial field
  mutation, unknown enums/keys, negative counts, nulls and malformed UUIDs.
- Up/down motion at four tick intervals for 1,600 frames each, exact state/event
  trace hashes, arrival, ding and door bounds. These are deterministic functional
  checks, not frame-time measurements.
- Both door sides on all horizontal orientations, server/client differences,
  nearby-player and loaded-chunk guards, locked doors and arrival IO failures.
- Player range boundaries, already-riding players, retained set identity,
  instruction dirty-flag consumption and passenger count changes.
- Client movement/render ordering, offsets, delegated rider percentages, lazy
  model identity, copy isolation, self-copy and partial-failure behavior.
- Java packet overrides that mutate the floor list or accept null UUID/position
  elements. Explicit Java `Consumer` dispatch preserves `ArrayList.forEach`
  fail-fast behavior. The first Kotlin conversion fails this added regression;
  both the original Java and corrected Kotlin implementations pass it.

World reads, sound output, mount coordination and client/player IO use recording
adapters. Lift algorithms, instruction queues, collections, packet codecs and
model construction are real. The Minecraft registry is bootstrapped but no game,
network connection, GPU renderer or audio device is started. Opening the in-game
floor selector and actual passenger movement still need an in-game check.

Deliberately retained behavior includes the sorted-floor early exit, earlier
floor on an exact distance tie, public-field mutation without automatic model
invalidation, clearing self-copies, stale back-door availability when single-sided,
and syncing passengers by count rather than set equality. Invalid negative/NaN
tick inputs retain Java behavior; conversion does not silently redefine them.

To rerun the Java control, supply `-PjavaLiftBaselineJar=<pinned-jar>` and run
`:common:checkJavaLiftBaseline`. Explicit `-PrecordLiftBaseline` prints the oracle
for manual review; it never replaces the committed fixture itself.
