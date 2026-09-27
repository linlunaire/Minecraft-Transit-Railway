# Kotlin production-code migration

The requested end state is **all or the overwhelming majority of first-party production code in Kotlin**, across MTR and ANTE. Moving a few algorithms into Transit Core does not meet that target. Conversion is staged so every completed batch remains buildable and testable.

## Frozen Java baselines

These annotated tags are published and will not move:

- MTR [`26.2-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/26.2-3.3.2): `e1d52b9b1d7fe7be6322c890f29a749ae8b184aa`.
- ANTE [`1.1.1-26.2`](https://github.com/linlunaire/mtr-ante/tree/1.1.1-26.2): `9eb6303e1e7c1f18d15312b15ffe50ca991c7b9a`.

They contain the Java implementation and the prior geometry/allocation fixes, not an intermediate unverified Kotlin migration. The older 1.21.1 tags are unchanged.

The Java maintenance branch `26.2` subsequently received the server fixes in
[`b6ab4d36`](https://github.com/linlunaire/Minecraft-Transit-Railway/commit/b6ab4d366af3e10fda408b062a804805f39adedf).
This is a separate post-tag baseline, not a replacement for the frozen tag.
The Kotlin work carries those fixes forward in `Webserver`, `RealTimeSync`
and `BlockEntityTypeMapper`; their production implementations are Kotlin.
Java registration and `RailwayData` callers retain their existing interfaces.
The full `RailwayData` and registration-facade conversions remain migration
work; moving the new helpers does not count those Java callers as converted.

Server regression checks preserve occupied-port recovery, real loopback HTTP,
the original class-monitor contract and Java static-method hiding; 86,425
clock assertions cover native progression, timezone-local wall-clock conversion
and disabling ownership; 1,192 addon-state assertions cover inherited factories
and actual NBT round trips. Both release JARs must contain all three Kotlin
implementations, and `Webserver` retains its post-fix Java descriptors, mutable
fields, generics, constructor and static-method hiding. Its three lifecycle
methods deliberately use explicit `synchronized(Webserver.class)` blocks rather
than the JVM `SYNCHRONIZED` method flag: Kotlin's `@Synchronized` would lock the
Companion for Kotlin callers. This flag-only exception is documented in the
snapshot and replaced by real Java/Kotlin shared-monitor behavior checks.
The Java build's isolated Youer startup is not runtime verification of this
Kotlin build. The independent Youer/FAWE material-cache failure is not fixed
by these MTR changes.

## Completion criteria

- Target at least 95% of hand-maintained first-party production files in Kotlin, with meaningful coverage across every business package. Report source-file and line counts together; converting a large generated model alone is not architectural completion.
- Track third-party vendored sources, generated build templates and tests separately. Java interoperability tests are intentionally written in Java. Transit Core files do not inflate MTR or ANTE's Kotlin percentage.
- Each remaining first-party Java production file must have a documented concrete compatibility constraint. Mixins and public APIs are migration work, not a blanket permanent exemption.
- Preserve exported class names, fields, constructors, static entrypoints, inheritance, generic signatures, exception contracts and existing serialization formats. Do not retain Java wrappers simply to label a class migrated.
- Require full dual-loader builds, exported-JVM-contract checks, behavioral regression checks and real client/server/resource-reload validation before treating a migration release as deployable. Source-language changes alone are not evidence of higher FPS or TPS.

## Sequence

1. Independent Transit Core and shared Kotlin runtime; no duplicated policies or stdlib.
2. Complete domain and math types, then complete train models. Use the installed official J2K converter in an isolated environment for repetitive model code; review and compile its output rather than treating conversion as proof of correctness.
3. Gameplay blocks/items/entities, screens, sound, paths, scripts and loader integration.
4. Rendering and simulation ownership, networking and saved-data adapters, then Mixin-sensitive classes with actual weaving checks.
5. Review the remaining Java list, remove obsolete source-parsing tests in favor of compiled behavior checks, and profile representative client/server scenes.

`tests/KotlinMigrationAbiCheck.java` reads class files without loading Minecraft. Checked-in baseline snapshots are derived from the pre-migration release JARs, identified by SHA-256; CI does not need those local JARs. New Kotlin implementations are compared with those contracts. Behavioral fixtures separately cover null handling, mutation/aliasing, serialization, disposal and numeric evaluation order.

The migration is in progress. Build success for an individual batch does not mean the overall language target or in-game verification has been reached.

Language conversion and architectural improvement alternate: first preserve a
module's behavior, then extract a real policy behind the Interface its callers
use. This avoids both a mechanical syntax-only rewrite and a speculative new
framework. The [architecture notes](architecture.md#incremental-architecture-evolution)
document the ticket transaction and shared model-variant preparation boundaries.

## Source checkpoint (2026-09-27)

| Repository | Kotlin files | Remaining Java files | Kotlin physical lines | Java physical lines |
| --- | ---: | ---: | ---: | ---: |
| MTR | 148 | 269 | 45,718 | 32,734 |
| ANTE | 64 | 223 | 4,644 | 34,566 |

The first combined preview uses MTR `26.2-3.4.0-kotlin.1`, ANTE
`1.2.0-26.2-kotlin.1`, JCM `1.2.2-26.2-kotlin.1` and Kotlin LunaCore `0.2.0`
(formerly Transit Core). MTR and ANTE publish on `codex/kotlin-26.2-preview`,
without replacing the Java maintenance branches. JCM remains an MTR addon;
its seven Kotlin implementations are only the beginning of that migration.
Its generated 26.2 source tree is not counted as migrated Kotlin.

The `kotlin.2` follow-up aligns all three consumers with LunaCore `0.2.1`.
LunaCore production sources are now entirely Kotlin; its historical Java
mappings live in a recoverable fixed Git archive. MTR reuses LunaCore's versioned
contract-check JAR instead of compiling a file from its old Java test directory.
This dependency/build cleanup does not change the MTR/ANTE conversion counts above.

LunaCore now supplies stdlib by canonical loader-managed nesting. Consumers
do not require FLK/KFF and do not bundle their own core/runtime copies. Internal
mod IDs and JVM namespaces remain stable across the public rename.

The door snapshot recursion fix from Java maintenance is now included in the
Kotlin `BlockPSDAPGDoorBase`, with real NeoForge `BlockSnapshot.create` regression
and a 21-contract ABI baseline. JCM no longer injects into TicketSystem's private
`onExit`: the explicit fare-adjustment API passes the already-calculated fare
before payment, retaining credit/message/removal ordering and failure behavior.
The ticket domain still has no Minecraft dependency; callbacks are wired by its
game adapter. The shared API belongs to MTR, not the general-purpose foundation.

This inventory counts `.kt` and `.java` under the three modules' `src/main/java`
and `src/main/kotlin` directories. It excludes tests, generated build outputs,
templates (including ANTE's generated `BuildConfig.java`) and Transit Core;
physical lines include comments and formatting.
These are conservative whole-source counts, before any vendored-code exclusion.
MTR's large model files must not disguise the substantial remaining migration.

Completed MTR batches: all 55 model classes (including the three bases), all 16
item classes, both path classes, 17 block classes and 33 data classes, plus the
entire seven-class web-map package, all ten sound classes and two new
server-fix helpers. Completed ANTE batches: the
entire 32-class Sowcer package, both path-generation classes, `IRoute`, `RouteMixin`, `DepotMixin`,
`Tree`, `RelativePosition`, `ShapeSerializer`, `Rolling`, five rail/train/vehicle
extension interfaces, `RailwayDataRailActionsModuleMixin`, `DynamicResource`,
`EyeCandyItemResources`, `EyeCandyProperties`, `RailModelProperties`, and the
`Vertex`/`Face`/`RawMesh`/`RawModel` geometry chain with `FaceList` and
`BufferSourceProxy`, `RawMeshBuilder`, `AtlasSprite`, `NmbModelLoader` and
`ObjModelLoader`. The previous Java implementations are recoverable
from the frozen tags; no parallel Java production implementation is retained.
The new `TicketTransaction`, `SidingRoutePlan`, ANTE `ModelVariantPreparation`
and `DepotRoutePlan` are architectural extractions, not additional converted
Java classes; their remaining Java callers are not counted as migrated.

Model tests compare 52 large-model, 153 leaf-model and 188 base-behavior records
against Java goldens. ANTE additionally applies its actual item Mixins and all
10 model Invokers with Sponge. Path search and serialization retain 170 frozen
Java records and 201 assertions, including 128 branching graphs, 32 flight cases
and a 2,000-edge line. ANTE's path interception targets the actual Kotlin
Companion implementation so both Java static bridges and Kotlin calls are
intercepted exactly once. ANTE's distinct search policy passes its own 170-record
golden; depot stitching and worker lifecycle pass 12 original-Java scenarios.
Its repeated path-membership scan is now cached for one candidate pass, reducing
hub comparisons from 12,800 to 400 in the 400-edge/32-branch regression. This is
an operation-count result, not an FPS/TPS or total-search-complexity claim.
These are headless checks, not a game launch.

The station/area and saved-rail/platform hierarchies add 157 original-Java
behavior records, 3,293 assertions and 91 exported JVM contracts. They cover
save/packet bytes, legacy NBT, mutable references, partial update failures,
callback ordering, dwell correction and numeric/locale comparison. Endpoint
lookup keeps HashSet ordering while avoiding full-set ArrayList snapshots:
the interpreter-only allocation fixture falls from 144 to 64 bytes per query
(55.6%), not an in-game FPS/TPS claim. See the
[baseline notes](../tests/fixtures/README-station-and-saved-rail.md) for scope
and deliberately preserved legacy behaviors.

`Route` (including its nested entry/enum types) adds 779 assertions, 26 Java
golden records and 36 JVM contracts, covering all 288 mode/type/circular/flag
combinations. ANTE's migrated `IRoute` and `RouteMixin` retain seven exported
contracts; actual Sponge weaving preserves their path-list ownership and
save/packet extensions against an all-Java five-record golden. The same test
runs on both finished loader JAR pairs, and missing-method/field negative
controls fail real injection. Public mutable route data remains uncached, so
this batch does not introduce stale destination or platform lookups.

`Depot` retains daily scheduling, real-time/continuous modes, mutable departure
lists, packet/save order and virtual dispatch. Its 10,382 assertions and 24 Java
goldens use an adapted clock, not wall-clock timing; train selection covers
sorting, round-robin, filtering, unavailable sidings and failure retention.
`DataCache` adds 8,041 assertions, nine records and 128 seeded networks covering
map identity, pruning, duplicate IDs, first-area/last-depot ordering, primitive
map callbacks and refresh failure publication. ANTE's real Kotlin `DepotMixin`
now runs the existing 12 generation scenarios through the intercepted public
entry point in common and both final loader artifacts. These tests retain
legacy scheduling/concurrency behavior rather than proving server-load safety.

`RailAngle` preserves exact floating-point constants, all 256 angle pairs and
20,392 boundary/seeded inputs. `fromAngle` uses shared enum entries instead of
cloning `values()` on every call; both path finders also avoid the loop-bound
clone. The interpreter-only allocation check falls from 80 to zero bytes per
query (2,621,440 to zero bytes for 32,768 calls), with the old implementation
rejected by the new gate. This is allocation evidence, not an FPS/TPS claim.
The three MTR classes and ANTE's depot mixin retain 103 exported JVM contracts.
See [the depot/cache/angle fixture notes](../tests/fixtures/README-depot-cache-angle.md)
for test adapters and deliberately unchanged legacy edge cases.

The path-generation, driving and logging modules add 204 assertions, 15 Java
golden records and 15 JVM contracts. Real controlled worker lifecycles, input
priority/retention, CSV file creation/append/retry and reduced serialization
are checked. Kotlin passenger iteration uses a live public iterable without
copying the protected Java set. The null-whole-array and forwarding-array
identity differences of logging varargs are documented in the
[module fixture notes](../tests/fixtures/README-railway-modules.md).

ANTE's resource tree and relative positions add 42,102 assertions, 164 Java
goldens and 53 JVM contracts. Per-level duplicate-name scans are replaced by a
local frequency map: 4,096 siblings require 12,288 hash probes instead of
16,777,216 list visits. The original mutable-component and copy-parent behavior
remains unchanged. Position hashing avoids the varargs array allocation,
falling from 32 to zero bytes per call in the interpreter-only allocation gate.
These are operation/allocation checks with failing Java negative controls,
not resource-reload latency or FPS/TPS measurements.

Nearby synchronization and rail types add 48 Java golden records and 38 JVM
contracts. Buffer ownership checks retain actual network payload snapshots
across reuse/release and require no live owned buffers after failure/retry.
An unchanged tick with 80 mock players drops from 80 buffer allocations to
zero, not a measured 80-player server result. ANTE now updates Kotlin enum
entries alongside Java values; actual weaving on both final JAR pairs verifies
all 637 rail types. See [the sync notes](../tests/fixtures/README-nearby-sync.md).

The rail-action queue and ANTE Mixin retain 21 Java behavior records and 13
exported contracts. Real action construction, single-head completion,
cancellation, live references, integer overflow and failed-send state are
checked before and after actual Sponge weaving in both final artifacts.
Block-editing and outgoing packets use headless adapters; the legacy queue
policy is not redesigned. See [the queue notes](../tests/fixtures/README-rail-actions.md).

ANTE shapes/rolling and five extension interfaces add 650 geometry/rotation
and 838 helper records plus 82 exported contracts. A mismatched shape-cache
lookup key is corrected: 1,024 warm validation calls now perform zero parses
instead of 1,024, while retaining the cached shape object. Quaternion float
bits, nullable behavior, integer overflow and public map ownership are checked.
This is operation-count evidence, not a GPU/FPS/TPS measurement.

The incremental passenger route finder retains 304 assertions, 167 Java
records and 14 exported contracts. Fixed-clock/seeded tests cover schedules,
queue failure/retry, live result aliases and density publication. Primitive
position snapshots and iteration reduce allocations at 4,096 positions from
140,872 to 42,528 bytes for sampling and 392,984 to 294,680 bytes for one
candidate pass under `-Xint`. The legacy heuristic, scheduling and known edge
cases remain unchanged; see [the route-finder notes](../tests/fixtures/README-route-finder.md).

ANTE's dynamic resource/item-preparation/property chain retains 39 JVM contracts.
Actual resource-manager, client-item loader and atlas-directory checks preserve
priority, reload isolation and lazy suppliers. Property ownership adds 10 Java
records and 50 assertions. Private game-interface overrides enforce Minecraft's
non-null parameter contracts; the outer nullable supplier path is preserved.
Rail-model properties add 86 original-Java records and 550 assertions for
in-place geometry, packed height bits, manager cache identity and upload/failure
ordering. Only the client singleton and GPU upload are adapted in that fixture.

ANTE raw vertices, faces and model aggregation add 58 JVM contracts and 396
Java records, including binary geometry, floating-point/hash semantics,
per-class assertions, upload synchronization/retry and mutable callbacks.
Vertex hashing retains Java's captured-input ordering without the temporary
array and boxes: 104 to zero bytes per call in the interpreter allocation test.
This does not establish end-to-end rendering or server throughput gains.

ANTE's `RawMesh` adds 29 JVM contracts, 221 Java records and 551 assertions in
each of three assertion modes, including all 128 attribute layouts and actual
CPU upload snapshots. Shared enum entries remove a clone per packed vertex:
48 bytes/vertex under `-Xint`, or 144 KiB per 3,072-vertex pack. The isolated
comparison keeps current geometry dependencies for both versions, excluding
the earlier vertex-hash savings. This is not GPU or in-game validation.
The face-list/material-batch adapters add eight JVM contracts and pass original
Java/current Kotlin checks for actual 26.2 submission, sorting policy, mutable
inputs, delayed snapshot isolation, queue clearing and failure retention.

The remaining six web-map classes add 72 original-Java behavior records,
10,574 assertions and 18 exported contracts. Tests cover all five endpoints,
UTF-8/backpressure, deferred parameter reads, queue admission, response order,
nullable failure timing and overridden Java collection `forEach` dispatch.
The pre-correction Kotlin implementation fails the virtual-dispatch regression.
The same oracle runs against both finished loader JARs. Release ABI checks
retain exact shaded servlet descriptors; only the explicitly selected
development-output check removes the expected relocation prefixes.
World/player access and query results use test adapters, not a running server;
see [the servlet fixture notes](../tests/fixtures/README-servlet-handlers.md).

Five BVE sound-data classes retain 85 Java records, 9,108 probes and 62 JVM
contracts, including floating-point bits, parsing, public mutable arrays,
subclass dispatch and existing resource-stream ownership. Both loader artifacts
run the same behavior oracle. These data checks do not verify audible output
or resource reload in a game. See
[the sound fixture notes](../tests/fixtures/README-bve-sound.md).

ANTE's mesh builder and atlas sprite add 228 original-Java records, 441
assertions and 28 JVM contracts. Initial/reset vertex defaults, fluent aliases,
partial failure side effects, exact UV bits and warning order are preserved.
The existing atlas V-coordinate denominator and unused rotation flag are not
silently changed by conversion. This batch is CPU-only behavior preservation,
not a GPU performance improvement.

Ticketing, server-side vehicle boarding and riding cooldowns retain the original
264-record fixture and 29 JVM contracts. After the architectural extraction,
all 160 ticket-rule records and 1,270 assertions run directly against
`TicketTransaction` without Minecraft on the runtime classpath. The integration
layer keeps 116 records and 95 assertions, including 12 overlapping ticket cases
for all five notices, four success sounds and both automatic-gate directions.
It preserves real scoreboard updates, exact boarding packet bytes, rotated
geometry, callback order, seat refresh, shift activation and failure state.
World/player access, seat entities and delivery are headless boundaries, not a
running server. See [the riding/ticket notes](../tests/fixtures/README-riding-ticket.md).

Objective setup now checks for absence instead of relying on duplicate-name
exceptions. In the real-scoreboard gate, 1,000 stable calls drop from 2,000
duplicate exceptions to zero, and the interpreter allocation comparison drops
from 1,800 to zero bytes per setup call. The previous Kotlin build fails this
gate too, distinguishing the logic improvement from language conversion.
Deletion/recreation, independent creation failures and scoreboard replacement
remain covered. This is not a whole-interaction allocation or TPS measurement;
see [the registration gate](../tests/fixtures/README-ticket-objective-registration.md).

The five remaining sound classes add 368 Java records, 915 probes and 40 JVM
contracts. Real sound-instance and train state checks cover looping lifecycle,
nearest-source selection, acceleration/coasting, doors, compressor transitions,
rail joints and exact floating-point timing. Client/audio/clock/random boundaries
are adapted; this is not audible playback or resource-reload validation. Both
finished loader JARs run the same oracle as compiler output. See
[the lifecycle notes](../tests/fixtures/README-sound-lifecycle.md).

ANTE's NMB and OBJ loaders retain 81 Java records, 172 assertions and 12 JVM
contracts. Real AES round trips, decrypted model bytes, OBJ/MTL parsing, geometry,
material options, exports and failure/stream-ownership behavior are compared.
Both final loader JARs repeat the corpus with 180 assertions, including the
actual artifact origin of model dependencies and the relocated OBJ parser.
Neither the legacy NMB format nor the bundled third-party OBJ parser is redesigned.
CSV import remains migration work; these CPU checks do not establish in-game
resource-reload correctness or performance gains.

ANTE's `ModelVariantPreparation` centralizes the existing decoration/rail
preparation policy: one owned model copy, texture/UV changes, ordered geometry
and the original source/key identity. Resource loading and GPU upload stay with
the two registries, which remain Java for now. A separate 166-record original-Java
oracle checks both actual registry entrypoints; direct Interface checks compare
128 combinations with those Java captures and test ownership and failure
isolation. Both finished loader JARs repeat the checks with dependency-origin
assertions. This is a CPU ownership boundary, not a new GPU cache or FPS claim.

Remaining work includes the rest of gameplay/data, rendering/simulation,
networking, UI, scripts, and loader/Mixin code. The 95% file target is **not yet
met**, and these local changes are not a deployed or published Kotlin release.

## Route-planning architecture checkpoint

`SidingRoutePlan` moves approach/main/return path composition and repeat-index
rules out of `Siding.generateRoute`. The remaining Java Adapter still computes
the timetable and queues guarded server-thread publication; the
whole `Siding` class is not counted as migrated. The computed list is new, but
main-route elements and rail objects keep their existing aliases. Actual search
continues through `PathFinder`, including ANTE's Companion injection.

The actual original Java method supplies 64 frozen records: 62 calculation
cases and two external-stage failure cases. Current computation/Adapter checks
add 890 assertions, including direct Interface comparison, retained path-element
aliases and exact time-stage/publication/queue ordering. Deliberately corrupting
the computed segment count in isolated test bytecode is rejected by the same
oracle. Both loader artifact checks select the real planner/search classes and
verify rail/platform dependency provenance. Timetable integration and execution
of the queued train-state update are outside this fixture; see
[the siding route notes](../tests/fixtures/README-siding-route.md).

ANTE's `DepotRoutePlan` captures route lists and copies cached path metadata
before the callback, then assembles joins and stop indices from request-owned
mutable state on the worker. It is not an idempotent/concurrent Interface; new
requests and retries capture fresh plans. `DepotPathGen` retains callback/thread/packet handling
and live siding/depot-setting reads. Captured metadata is owned; platforms and
rails are still borrowed, not an immutable graph snapshot.

ANTE retains its twelve original-Java depot scenarios through the helper and
real `DepotMixin`, then tests the calculation Interface directly against their
path-result projection. Additional contracts cover ownership between captures,
late graph reads, copy order, capture-versus-worker failures and caller-side
`Thread.run()` followed by the generator's `Thread.start()`. Final artifacts
must provide all plan classes; a missing class cannot fall back to development
output. The original twelve-record fixture is unchanged.

This decomposition permits direct calculation tests and creates a place to
introduce request-lifetime control without mixing it into every path branch.
The Kotlin `PathGenerationTask` now supplies per-request cancellation that
survives worker completion, managed owner-thread notifications and siding-binding
publication gates. A separate real-publication fixture reproduces the previous
detach/resurrection bug and covers finished-worker restart, failed replacement,
same-ID depot replacement and rebinding before an old worker reaches a siding.
Both finders and the actual timetable loop have deterministic interruption
checks; actual MTR/ANTE workers distinguish cancellation from route failure.
The calculation goldens remain unchanged.

`PathGenerationLifecycle` now registers actual world-unload and server-stopping
events during MTR initialization. Weak world/server keys preserve closed-instance
markers without retaining worlds; weak Module references track only data already
instantiated. Closure invalidates pending requests, interrupts current and
superseded workers, keeps late callbacks cancelled and prevents the same unloaded
world or stopped server from admitting a new Module. A replacement Module closes
its predecessor, while a new world instance on a running server remains usable.

Close never joins workers on the server thread. It prevents stale publication but
does not guarantee that third-party workers ignoring interruption finish. The
33-assertion targeted lifecycle regression exercises real event invokers and
world/server isolation; disabling registration reproduces the old queued
notification after unload. The railway-Module compatibility and real siding
publication checks also pass. The full Fabric/NeoForge build now passes with this
lifecycle increment; in-game unload/stop verification remains outstanding.

Input snapshots and bounded admission remain open. This is not a measured FPS/TPS
improvement or a full concurrency guarantee.
See [the architecture requirements](architecture.md#route-computation-before-scheduling).

## Dashboard maintenance fix carried into Kotlin

Maintenance commit `750992f7` fixes dashboard terrain stalls and the 26.2 GUI
widget resources. The Kotlin work carries the terrain module as
`TerrainMapGeometry.kt`, with the same static `capture` and instance `submit`
Java entrypoints. Its primitive arrays and copied pose own one immutable GUI
submission; consecutive equal-color cells are merged without changing sampling.
The screen and icon/slider adapters retain their current Java APIs; synchronizing
these fixes does not count those screens as Kotlin-converted.

The same 11 map scenarios and 45 widget assertions run here, including 1280x720
terrain, pixel colors, pan/zoom, viewport clipping, deferred pose ownership and
legacy icon UVs. Both loaders register the GUI-state accessor. These headless
checks do not establish real in-game FPS or prove all UI layout issues resolved.
