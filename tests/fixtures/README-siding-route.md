# Siding route planning

`siding-route-java-26.2-3.3.2.tsv` contains 64 records from the actual Java
`Siding.generateRoute` method in the original `MTR-neoforge-26.2-3.3.2.jar`:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`

Both verification and recording of the Java baseline require this exact
SHA-256. Its `Siding`, `PathFinder`, `PathData`, and their nested classes are
loaded from that release, not from the extracted Kotlin module. Stable domain
dependencies such as `Rail` and `Platform` use the existing migration-tested
development runtime when recording the baseline.

## Boundary and coverage

The fixture runs the original planning branches and real search/append
algorithms on deterministic seven-node bidirectional rail graphs. Only three
external phase boundaries in `Siding.generateRoute` are adapted: timetable
integration, construction of the deferred publication closure, and server
queue submission. Their order and captured path/repeat values are recorded.
The test does **not** execute the train/world publication body, substitute the
search algorithm, or claim to validate a running server's thread scheduling.

The 62 computation records cover same/opposite/unrelated main-path boundaries,
empty and nullable main paths, missing first/last platforms, unreachable
approach/return, unused nullable arguments, null elements, both repeat modes,
and signed integer overflow in segment counts and return-path stop indices.
Two additional integration records verify timetable/queue exception identity
and short-circuiting. Path MessagePack hashes include rail/position/dwell/stop
metadata. Separate identity records retain the original main-path element
aliases; result-list independence and borrowed rail identities are asserted.

The current check runs the actual `Siding` adapter against all 64 records and
calls the production `SidingRoutePlan.compute` interface directly against the
62 computation expectations. No reflection into its private state or copied
planner implementation is used. The module, search and path-data classes are
explicitly loaded from the selected output or JAR. Final-artifact tasks put
that JAR first and verify the provenance of the remaining rail/platform domain
dependencies, rejecting development-class fallback.

## Tasks

- `:common:checkSidingRoutePlan` checks the current computation and adapter.
- `:common:checkJavaSidingRouteBaseline -PjavaBaselineJar=<original-jar>` checks
  the pinned baseline. `-PrecordSidingRouteBaseline` deliberately regenerates
  the oracle only from that verified original artifact.
- `:fabric:checkPackagedSidingRoutePlan` and
  `:neoforge:checkPackagedSidingRoutePlan` check the final loader JARs.

These tasks are connected to `check`. The standalone runner's optional
`--negative-control` flag mutates only its isolated in-memory Kotlin result
arithmetic, and must fail the frozen oracle; it never modifies production
files or the Java baseline.

## Request lifetime and real publication

`checkSidingPublication` (runner `--publication-check`) does **not** replace the
publication closure. It executes the selected production `Siding` commit with
only the server queue and timetable-computation call adapted. The live path is
inspected to detect discarded valid updates, obsolete writes and container
replacement. Tests cover unchanged binding, detach, reattach to the same depot,
same-ID/different-object depot replacement, and requests that reach a siding
after it has already moved to another depot.

A controllable generator Adapter lets the real `RailwayDataPathGenerationModule`
register workers and accept replacement requests. It deliberately finishes the
first worker before starting the second, then applies new and old queues in
reverse order. Only the current result/status may publish. A failed replacement
must not revive its predecessor. A constructor-free RailwayData shell supplies
only cache/sidings; delay-reset I/O is adapted, not request registration.

The same runner separately invokes the actual timetable method with a Rail
Adapter that interrupts on its second length read, just before integration.
It must throw cancellation while retaining the interrupt flag. Search also has
pre-interruption and deterministic in-flight interruption tests in the shared
path fixture. The old Java computation goldens do not assert this intentionally
new lifecycle behavior.

Both `:fabric:checkPackagedSidingPublication` and
`:neoforge:checkPackagedSidingPublication` repeat the real-publication test using
the final artifacts and verify the request Module's dependency origin. They
are part of `check`. The original failing command and subsequent fixes are
recorded under ignored `build/diagnostics/`; no save or game process is touched.

Live graph/settings snapshots, world/server-unload cleanup, bounded scheduling
and real dedicated-server capacity are outside these headless checks.
