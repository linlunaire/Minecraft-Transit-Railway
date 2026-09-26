# Compatibility checks

The 26.2 build compiles the source in `common/`, `fabric/` and `neoforge/`
directly. It does not download mapping source, rewrite Java source, convert
legacy data packs or select an older Minecraft build.

`build` runs the checks below before copying either loader JAR to
`build/release/`. Individual checks are Gradle tasks in `common/build.gradle`;
their implementations live in `tests/`.

| Area | Contract checked |
| --- | --- |
| Registration | 181 block IDs, 105 item IDs, constructor keys and scoped registration context |
| Persistence and cargo | Legacy NBT/UUID/default values, components, old cargo envelopes, and preservation on failed writes |
| Entity movement | Interpolation targets, cancellation and 20,000 comparisons with the previous movement formula |
| Models and rendering | Actual model geometry, immutable delayed submissions, material state, Unicode text and vehicle animation |
| Client resources | 270 item definitions, 84 selected-item conditions, model/texture references and reload isolation |
| Data resources | 297 recipes, 176 loot tables and 32 tags, reference graphs and vanilla schema codecs |
| Networking | 41 S2C / 26 C2S channels, real encoding/decoding and the missing-codec regression |
| Loader hooks | Configured mixins and callback descriptors checked against actual game bytecode |
| Screens | Screen lifecycle, background ownership and one blur per frame |
| Dashboard terrain | Actual map sampling and 26.2 GUI submission, one terrain element instead of per-cell overlap searches, colors, pan/zoom, clipping and deferred pose ownership |
| GUI widgets | Legacy icon texture slices and hover states, current slider sprites, odd sizes, slider endpoints and exactly one checkbox label |
| Depot editor | Actual widget hit testing, clicks, depot serialization and route-selector initialization across viewport sizes and schedule/transport modes; window, audio and network boundaries are substituted |
| Save queues | FIFO ordering, duplicate and missing IDs, retained files and failure preservation |
| Web map lifecycle | Real loopback HTTP, occupied-port cleanup/recovery, repeated init/start/stop, disabled/invalid configuration and partial-start failures |
| Real-time synchronization | Actual 26.2 clock progression and packets, wall-clock conversion, dimension guards and no plugin-overridable command dispatch |
| Addon block entities | Fixed-factory APG inheritance, original/custom state retention, save/load and rejection of unrelated or overridden factories |
| Node fluid resistance | Actual vanilla fluid admission for all node states, empty player collision, selection and unchanged break hardness; optional real ANTE direct-node coverage |

The resource checks use vanilla registry projections where a running mod registry
would otherwise be required. Networking uses a loader registration adapter but
retains the real packet codecs. These are not full game launches: they do not
establish GPU rendering, complete loader initialization, real multiplayer
connections or server capacity.

Before deploying an upgraded world, test a copy with the intended loader, mods
and resource packs. Check railway data in the dashboard, trains and tracks,
passenger movement, save/reload, and a real client joining a dedicated server.
An 80-player deployment also needs representative train density and player
movement during profiling; a compilation result or collection microbenchmark
does not establish that capacity.
