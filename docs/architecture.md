# Architecture

## Current module layout

```text
YanlingMTR repository
  common / fabric / neoforge      YanlingMTR gameplay and loader integrations
  ante/
    common / fabric / neoforge    ANTE models, scripts, tools and loader integrations

Production dependencies: ANTE -> YanlingMTR -> Transit Core
                        ANTE --------> Transit Core
Transit Core remains a separate prerequisite Mod and repository.
```

The Kotlin 26.2 line uses one repository and one coordinated build, with two
isolated Architectury builds connected through Gradle composite tasks. YanlingMTR owns
trains, stations, ticketing, persistence and network data. ANTE owns custom
models, scripts and extension tools. They still produce separate loader JARs;
YanlingMTR does not acquire a runtime dependency on ANTE. Existing class names, mod IDs
(`mtr`, `mtrsteamloco`), packet channels and saved-data keys are unchanged.
The 1.21.1 and Java maintenance branches are outside this consolidation.

[Transit Core](https://github.com/linlunaire/Transit-Core) is a standalone library Mod for the wider Mod ecosystem; MTR, ANTE and JCM are its first consumers. Its pure JVM library is a compile-time dependency. Its matching Fabric or NeoForge Mod JAR supplies the runtime implementation and nested Kotlin stdlib. Consumers embed neither the core nor another runtime copy.

The core compile classpath is checked to reject Minecraft, loader, rendering, scripting and other third-party implementation dependencies. Minecraft objects and GPU operations stay in consumer code, outside Transit Core. Java functional interfaces cross the seam; no Kotlin function types, coroutines, `Flow` or dependency-injection framework are required.

## First migrated modules

| Transit Core module | Interface and ownership | Invariants |
| --- | --- | --- |
| `FrameGeometryCache` | Render-thread frame/access/retirement operations; injected allocation and disposal | Identity keys, active-frame pinning, inactive LRU eviction, soft budget, idle expiry and complete cleanup |
| `FrameMembership` | Mark visible keys, reconcile add/remove transitions, reset tracking | Equality keys, add-before-remove order, duplicate suppression, no per-key allocation on stable frames |
| `BoundedTaskDispatcher` | Nonblocking admission, one-result publication and stale-result discard; injected executor and callbacks | Bounded in-flight results including pending publication, unchanged rejected work, owner-thread publication and disposal of stale results |

The previous Java cache/scheduler implementations and embedded Kotlin modules are removed, not retained as a second policy or a forwarding layer. Tests call the same Kotlin implementations as production Java callers. ANTE configures the generic dispatcher with two workers and four in-flight results, and still owns Minecraft chunk validity and GPU operations.

Frame membership replaces two copied difference sets and a cleared/repopulated visible set with reusable entries and frame stamps. Stable-scene bookkeeping stays O(N) but no longer allocates O(N) temporary set nodes each frame. This is not a measured in-game FPS/TPS claim.

Transit Core builds a pure JVM library for consumers and loader-specific Mod JARs for installation. Its shared algorithms remain independent of Minecraft lifecycle concerns. TrMenu inspired the separation of contracts from platform assembly; no TrMenu code or Bukkit dynamic loader is copied.

This integration starts with MTR `26.2-3.3.3`, ANTE `1.1.2-26.2` and Transit Core `0.1.0`. Both consumers declare Transit Core as a required dependency on clients and servers. ANTE retains its MTR `26.2-3.3.3` minimum. Local version changes do not indicate a tested game deployment or remote release.

As of Transit Core 0.2.0, Kotlin stdlib 2.4.0 is nested once in its loader artifact using its original Maven/library identity. It is **not** shaded or relocated. Ordinary JVM entrypoints remove the need for FLK/KFF in our mod set without pretending to implement their custom adapters or optional libraries. Both loaders' actual dependency resolvers are tested alone and alongside FLK/KFF; final artifacts execute against the selected stdlib. This is not a full game/mod-pack coexistence claim.

## Incremental architecture evolution

Language conversion and architectural changes are separate, alternating steps.
Conversion freezes existing contracts; a subsequent deep Module extraction
centralizes a real policy and tests it through the same Interface used by callers.
We do not wait for every Java file to disappear before improving the design,
and Kotlin file counts alone do not establish architectural progress.

### Ticket transaction Module

`TicketTransaction` owns gate direction, entry-zone encoding, fares, concessions,
reminders and ordered account updates. Its Interface is one `pass` operation,
a two-integer Account, lazy concession eligibility and synchronous Feedback.
It contains no Minecraft, scoreboard, text, player, network or loader dependency.
`TicketSystem` is the Minecraft Adapter at this Seam and retains its existing
Java-callable entrypoints. In-memory accounts are the direct test Adapter.

Account values remain live; notifications can fail after earlier writes, exactly
as before. Enum outcomes/notices avoid allocating result/event objects on every
pass. This is not an atomic database transaction or a new configurable fare system.
Rules stay in MTR rather than becoming domain-specific baggage in Transit Core.

Scoreboard setup now queries for each missing objective before creating it.
There is no cached initialized flag: removal, replacement and independent
creation failures still recover. In the real-scoreboard fixture, 1,000 stable
calls go from 2,000 duplicate-name exceptions to zero. The `-Xint` allocation
comparison falls from 1,800 to zero bytes per setup call. These figures cover
objective setup only, not a whole gate interaction, server MSPT or player capacity.

All 160 original-Java ticket cases now execute without Minecraft or bytecode
adaptation. A smaller integration oracle retains actual scoreboard, translation,
sound and failure-order checks; final loader JARs execute the selected production
transaction implementation. Compiled dependency checks keep the domain isolated.

MTR's `TicketSystem.registerFareAdjustment(id, handler)` is the supported addon
seam for fare credits. It supplies the actual concession-adjusted fare and live
score before payment, only for a recorded journey. Registration is idempotent by
namespaced ID; dispatch uses an immutable snapshot, so callback-side registration
changes affect the next payment. Failures propagate without MTR clearing the
entry record or debiting fare; addon effects already performed are not rolled back.
JCM registers its fare saver through this API rather than injecting a private
implementation or duplicating MTR's fare formula. This transport-specific seam
stays in MTR and does not expand LunaCore's general API.

### Model variant preparation Module (ANTE)

Decoration and rail definitions share one preparation Interface: borrowed raw
template, variant key, definition and explicit geometry policy produce an owned
variant. The implementation controls deep copying, texture replacement, UV flip,
ordered translation/XYZ rotation/scale/mirror and the final cache identity.
Callers own resource discovery and GPU upload; preparation neither retains nor
mutates the JSON definition or cached template. It is a synchronous CPU operation,
not background work, and rail definitions retain their existing geometry policy.
Mutable mesh/material containers, vectors and face arrays are isolated; the
existing matrix callback reference follows the original copy contract and is
shared rather than attempting to clone a function's captured state.

This Module stays in ANTE because its rules depend on ANTE model definitions.
It does not introduce another cache, parser, dependency-injection framework or
copy-on-write geometry scheme. Input ownership, transform order and failure
isolation are tested at the same Interface used by both registries.

### Route computation before scheduling

`SidingRoutePlan` owns approach/main/return composition, successful-segment
reporting and repeat-index rules. Its Interface returns the computed path and
three integers without accessing a server, integrating a timetable or changing
a siding. `Siding.generateRoute` is the Adapter: it computes time segments and
retains the existing server-executor publication. Both MTR and ANTE depot
generation reach this shared siding implementation. The search still goes
through `PathFinder`, including ANTE's real Companion injection.

The result owns its list, not all objects reachable from it. Existing main-path
elements and graph rails remain borrowed. Empty/failing routes and duplicate
boundary rails retain the original repeat-index and aliasing behavior. This is
a calculation Module, not yet an immutable simulation snapshot.

ANTE's `DepotRoutePlan` separates caller-side route capture from worker-side
assembly. Capture preserves collection order and copies cached `PathData`
metadata before the worker starts; assembly owns connections, same/opposite
joins and stop-index assignment. Assembly mutates request-owned preparation
state, so a new request or retry captures a new plan without a second deep copy
in the normal path. Assembly is neither concurrent nor idempotent; no new guard
changes legacy callbacks that directly run the supplied worker. Metadata is
owned by the captured request, not independently cloned for repeated assembly;
rail and platform objects remain borrowed. `DepotPathGen` retains threads,
callback-before-start, live siding filters/repeat flags and completion packets.

`PathGenerationTask` now owns request cancellation and publication validity.
The railway Module retains the current request separately from its worker, so
restarting invalidates queued results even after that worker has exited. The
existing synchronous `Consumer<Thread>` registers ordinary add-on threads;
no new callback descriptor, thread subclass or coroutine dependency is needed.
A weak-key worker registry bridges that Interface to the shared siding Adapter.
Request values retain neither a thread nor a world; their optional depot
identity is weak, avoiding a value-to-key retention cycle through world data.

Search expansion/materialization and timetable integration check interruption
without clearing the flag. Cancelled work throws `CancellationException`, which
both MTR and ANTE workers handle separately from errors. Managed completion and
failure notifications use the server executor and recheck request validity when
executed. Unregistered direct generator calls retain legacy notification
threading and are not promised latest-request cancellation.

Each siding has a publication gate. It changes epoch only when its world or
depot identity changes, not during routine cache refresh. The gate rejects
queued work after detach, reattach or replacement; a request from the previous
depot is also rejected if it reaches the siding only after rebinding. A valid
commit still updates existing path/timetable containers in place. This is not
a transaction across all sidings: already-applied results are not rolled back.

`PathGenerationLifecycle` registers real world-unload and server-stopping events
unconditionally during MTR initialization. It tracks only instantiated world
Modules; shutdown never loads saved data. Weak world/server keys retain closure
markers for those instances, and weak Module references avoid retaining their
worlds. A new Module cannot reopen an unloaded world instance or a stopped server;
a new world instance on a running server is independent. Replacing a world's
Module closes its predecessor.

Closing a Module invalidates all requests and interrupts every registered worker,
including superseded workers still alive. Late callbacks retain a cancelled
request and cannot restore Module registration or reset train delays. Close is
terminal and never joins workers on the server thread: queued results are
rejected, but a third-party worker that ignores interruption is not guaranteed
to stop computing.

The targeted lifecycle regression passes 33 assertions through real event
invokers, covering unload/stop, repeated events, late registration, replacement
and world/server isolation. Disabling event registration makes the same test
reject an old notification delivered after unload. The full Fabric/NeoForge build
also passes; in-game unload verification remains outstanding.

Remaining scheduling work: immutable graph/settings capture and bounded
admission/running work/publication queues. Borrowed references are still not
snapshots. No worker pool or Transit Core policy was introduced by this local
lifetime Module.

### Next architectural steps

- Path generation: add immutable input capture, then bound workers and pending
  publications together; verify unload/stop behavior in game.
- Sound: extract state advancement from audio output, preserving synchronous
  failure order and avoiding per-frame event lists.
- Resource lifetime: give external-preview templates and GPU variants explicit
  identities and invalidation before changing their cache/reload policy.

## Kotlin migration scope

The target is a Kotlin implementation of all or the overwhelming majority of first-party production code in both MTR and ANTE, not merely a Kotlin utility library. Transit Core extraction is preparation, not completion. Domain objects, models, gameplay, rendering, UI and loader integrations are in scope. See [the migration plan](kotlin-migration.md) for the baseline tags, completion criteria and compatibility gates.

`Train`, `RailwayData`, Mixins, GraalJS-facing classes and network codecs are not permanently exempt. They must retain the class names, fields, constructors and method descriptors used by existing add-ons. Saved-data and packet formats remain unchanged throughout language migration. Any retained Java production file needs a concrete compatibility reason.

Next candidates need their own behavior-preserving seam before migration:

1. Rail-interest queries: conservatively index full curve bounds, preserve exact predicates, and cover every loading/edit/validation invalidation path. Long spans need a bounded fallback.
2. Simulation state versus rendering snapshots: make thread ownership and immutable extraction explicit before moving any work between threads.
3. Save and network work budgets: preserve ordering, disconnect cancellation and shutdown flush behavior before introducing asynchronous stages.

Do not create more modules until a real caller or dependency justifies them. Keep performance-sensitive loops primitive and allocation-measured; Kotlin syntax does not justify hidden collection pipelines.

## Build and verification

Keep Transit Core beside the YanlingMTR repository and build it first. ANTE is now
in-tree under `ante/`; no separate ANTE checkout is required. Root `build`
checks and builds both mods for Fabric and NeoForge. `buildMtr` retains the
YanlingMTR-only workflow. `:ante:build` also builds and verifies its in-tree MTR
prerequisite before any ANTE compilation. ANTE targets `buildMtr`, not the root
aggregate `build`, so the task graph has no recursive build dependency.

Both release versions and shared dependency versions live in root
`gradle.properties`; ANTE's loader metadata requires the matching YanlingMTR version.
ANTE-specific script/configuration dependencies remain in
`ante/gradle.properties`. Both builds write their checked loader JARs into root
`build/release/`. Their existing archive policies distinguish YanlingMTR and ANTE and
preserve earlier releases under `archive/`.

Set `-PtransitCoreProjectDir=<path>` when the prerequisite checkout is elsewhere;
relative paths resolve from the repository root in both builds. Missing library
or loader artifacts fail with an instruction to build Transit Core first.
`-PmtrProjectDir` is intentionally rejected: a monorepo build must not silently
compile ANTE against unrelated sibling sources or stale binaries.

The composite arrangement follows Gradle's [included-build task
dependencies](https://docs.gradle.org/current/userguide/composite_builds.html#depending_on_tasks).
It preserves each mod's tested shading, Mixin and loader configuration rather
than assuming that merging source directories makes those contracts identical.
The root CI builds the pair together on Temurin and GraalVM; local success is
not evidence that a remote CI run has executed.

MTR's `build` retains render/model/Mixin/persistence checks and the 4,096-mesh fixture. Transit Core owns the dependency-isolation, Java interoperability, dispatch lifecycle and warm-path allocation fixtures. MTR also runs the shared cache fixture against the external library. Release checks load real Kotlin classes from the matching prerequisite Mod JAR, require resolved dependency metadata, and reject accidentally embedded core classes or stdlib.

Migrated production classes are checked separately against the frozen Java JVM
contracts. Both final loader JARs must contain Kotlin implementations, not just
matching classes in a temporary compiler output directory. Model golden tests
and domain/math/material fixtures compare behavior independently of the ABI
checks; a preserved method descriptor alone cannot prove equivalent behavior.

Compilation and these fixtures do not prove loader startup, GPU behavior or 80-player capacity. Verify both loaders, dedicated-server/client startup, resource reload, train animations and same-scene profiling before deployment. The installed pre-migration performance-test JARs are a separate baseline.

Primary-source comparisons and runtime evaluation are recorded in [the Kotlin evaluation](research/kotlin-architecture-26.2.md) and [TrMenu research](research/trmenu-library-design.md). Those research documents record earlier design stages; this document defines the current standalone prerequisite layout.
