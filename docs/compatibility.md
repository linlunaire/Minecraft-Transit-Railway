# Compatibility checks

The 26.2 build compiles Kotlin and the remaining Java source in `common/`, `fabric/` and `neoforge/`
directly and consumes the separately built [Transit Core](https://github.com/linlunaire/Transit-Core) Kotlin library at compile time. At runtime, both loaders require its matching Mod JAR. It does not download mapping source, rewrite Java source, convert
legacy data packs or select an older Minecraft build.

Root `build` now runs YanlingMTR and the in-tree ANTE checks before completing the
four-JAR release set in `build/release/`. `buildMtr` retains YanlingMTR-only verification.
The YanlingMTR checks below are Gradle tasks in `common/build.gradle`; their
implementations live in `tests/`. ANTE's tests and fixtures remain in
`ante/tests/`, with tasks in `ante/common/build.gradle`, `ante/gradle/` and its
loader builds. ANTE compilation depends on the freshly checked YanlingMTR development
and release JARs, including when invoked through `:ante:build` directly.

ANTE coverage includes material factories, model loaders/variants, dynamic
resources, script runtime/module packaging, and real Mixin weaving for camera,
rail, route, depot and riding integrations. Both loaders' finished artifacts
retain their separate Mod IDs and reject embedded copies of MTR, shared core or
Kotlin stdlib where inappropriate. This is a repository/build consolidation,
not an automatic backport of fixes from the separate 1.21.1 maintenance line.

| Area | Contract checked |
| --- | --- |
| External Kotlin core | JVM 25, Java calling conventions, cache warm-path allocation, equality-based frame transitions, resolved required metadata and classes loaded only from the Transit Core Mod JAR |
| Kotlin migration | Frozen Java public/protected JVM contracts, inheritance, generic signatures, declared exceptions, Java static-method hiding and nullable/serialization behavior |
| Path search | 170 original-Java records and 201 assertions for wire/save bytes, UUID caching, append identity, DFS/backtracking, failed legs, flights and a 2,000-edge line |
| Registration | 181 block IDs, 105 item IDs, constructor keys and scoped registration context |
| Persistence and cargo | Legacy NBT/UUID/default values, components, old cargo envelopes, and preservation on failed writes |
| Entity movement | Interpolation targets, cancellation and 20,000 comparisons with the previous movement formula |
| Models and rendering | Actual model geometry, immutable delayed submissions, material state, Unicode text and vehicle animation |
| Client resources | 270 item definitions, 84 selected-item conditions, model/texture references and reload isolation |
| Data resources | 297 recipes, 176 loot tables and 32 tags, reference graphs and vanilla schema codecs |
| Networking | 41 S2C / 26 C2S channels, real encoding/decoding and the missing-codec regression |
| Loader hooks | Configured mixins and callback descriptors checked against actual game bytecode |
| Screens | Screen lifecycle, background ownership and one blur per frame |
| Dashboard terrain | Actual map sampling and Kotlin mesh submission, one terrain element instead of per-cell overlap searches, colors, pan/zoom, clipping and deferred pose ownership |
| GUI widgets | Legacy icon texture slices and hover states, current slider sprites, odd sizes, slider endpoints and exactly one checkbox label |
| Depot editor | Actual widget hit testing, clicks, depot serialization and route-selector initialization across viewport sizes and schedule/transport modes; window, audio and network boundaries are substituted |
| Save queues | FIFO ordering, duplicate and missing IDs, retained files and failure preservation |
| Web map lifecycle | Real loopback HTTP, occupied-port cleanup/recovery, Java/Kotlin shared class-monitor behavior, repeated init/start/stop, disabled/invalid configuration and partial-start failures |
| Real-time synchronization | Actual 26.2 clock progression and packets, wall-clock conversion, dimension guards and no plugin-overridable command dispatch |
| Addon block entities | Fixed-factory APG inheritance, original/custom state retention, save/load and rejection of unrelated or overridden factories |
| Node fluid resistance | Actual vanilla fluid admission for all node states, empty player collision, selection and unchanged break hardness; optional real ANTE direct-node coverage |

The resource checks use vanilla registry projections where a running mod registry
would otherwise be required. Networking uses a loader registration adapter but
retains the real packet codecs. These are not full game launches: they do not
establish GPU rendering, complete loader initialization, real multiplayer
connections or server capacity.

Transit Core's own build checks its Minecraft-independent compile classpath,
frame-membership allocation and bounded-dispatch lifecycle. Build it before MTR;
MTR's `checkFrameGeometryCache` reuses its test fixture against the actual external
library JAR. Release checks reject duplicate shared-core classes and bundled
Kotlin stdlib in MTR or ANTE, then invoke the real Kotlin classes from the
matching Transit Core loader JAR with an isolated shared runtime.

The Kotlin model check constructs and bakes the actual A320 and Class802 models,
then compares 40 material/door/car render combinations with a checked-in golden
captured from the Java `26.2-3.3.2` release. Full-quad fingerprints retain winding,
duplicate faces, UV, normal, color, light and overlay attributes. Position and
normal values use a `0.0001` comparison quantum, and UV uses `0.000001`; this is
not a claim of bit-identical GPU output. The golden's provenance and exclusions
are recorded in [the fixture notes](../tests/kotlin-models/README.md).

The golden and JVM snapshots run without a local baseline JAR. Supplying
`-PjavaBaselineJar=/path/to/MTR-neoforge-26.2-3.3.2.jar` also enables
`checkJavaDomainBaseline`, `checkJavaPathBaseline` and a direct original-Java model comparison. Baseline
loading is isolated and checked so current Kotlin classes cannot silently stand
in for the old implementation. See [the migration plan](kotlin-migration.md) for
the remaining language and runtime acceptance criteria.

Before deploying an upgraded world, test a copy with the intended loader, mods
and resource packs. Check railway data in the dashboard, trains and tracks,
passenger movement, save/reload, and a real client joining a dedicated server.
An 80-player deployment also needs representative train density and player
movement during profiling; a compilation result or collection microbenchmark
does not establish that capacity.
