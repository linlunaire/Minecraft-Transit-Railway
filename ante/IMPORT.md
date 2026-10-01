# ANTE import provenance

The Kotlin 26.2 source tree was imported on 2026-09-30 from
`https://github.com/linlunaire/mtr-ante`, branch `codex/kotlin-26.2-preview`,
commit `bd5716bc8f232bf0006ef934ae0ca2922b47b2b6`
(YLM-ANTE `1.2.0-26.2-kotlin.4`).

The import retained 756 tracked files: production sources, resources, tests,
fixtures, build configuration, documentation and license notices. The original
checkout and its Git history were left unchanged. No generated build outputs,
local caches, worlds or nested `.git` directory were imported. The old Gradle
wrapper and standalone GitHub workflows were omitted because the YanlingMTR root now
owns the wrapper, joint verification and CI.

Preview 5 changes the repository/build organization; it does not merge the mod
IDs or JAR contents. Shared game/loader/core and release versions now come from
`../gradle.properties`. ANTE compilation depends on a checked, current in-tree
YanlingMTR build, and both mods publish their local artifacts to `../build/release/`.
Gameplay sources and compatibility fixtures are unchanged by the import.

Earlier Java and 1.21.1 maintenance histories remain in the original repository;
the independently maintained 1.21.1 line is now YLTE (Yanling Transit Expansion).
The original [MIT license](LICENSE), source notices and [credits](README.md#credits-and-license)
continue to apply.
