# YanlingMTR-ANTE

Custom train and rail models, JavaScript-driven rendering, decorative objects and rail editing tools for **YanlingMTR**.

The **Minecraft 26.2** continuation of **Aphrodite's Nemo's Transit Expansion** is now merged into **YanlingMTR's source repository and unified build**, for **Fabric** and **NeoForge**. It extends [YanlingMTR](https://github.com/linlunaire/Minecraft-Transit-Railway); it is not a standalone mod or an MTR 4 add-on. This is a source/build integration: the current release still contains a core JAR and a separate optional ANTE JAR, not a single combined mod file.

## Install

This is an incomplete **Kotlin preview**, not a production-validated release. Use Java 25 and install YanlingMTR-ANTE together with the **26.2 YanlingMTR Kotlin preview** and [Kotlin LunaCore 0.2.1+](https://github.com/linlunaire/Kotlin-LunaCore), using the same loader for all JARs.

| Loader | Required mods |
| --- | --- |
| Fabric | YanlingMTR, [Fabric API](https://modrinth.com/mod/fabric-api), [Architectury API](https://modrinth.com/mod/architectury-api) |
| NeoForge | YanlingMTR, [Architectury API](https://modrinth.com/mod/architectury-api) |

Place the JARs in `mods` on the server and clients. YanlingMTR-ANTE's scripting runtime and configuration library are bundled; no separate installation is needed. Players using custom models also need the corresponding resource packs.

The Kotlin standard library is provided by Kotlin LunaCore through loader-managed nesting, not bundled again in YanlingMTR-ANTE. No separate FLK/KFF installation is needed for our mods; keep those dependencies if other mods require them.

This preview requires **YanlingMTR 1.0.0-beta.1 for Minecraft 26.2**, including the train audio restart fix; its Kotlin Mixin targets are not compatible with the Java maintenance build. The loader retains the `26.2-3.4.0-kotlin.5` compatibility floor. **Kotlin LunaCore** supplies frame membership tracking and bounded background task scheduling; neither YanlingMTR nor YanlingMTR-ANTE bundles another copy. The unified build verifies matching sources in dependency order.

The original `mtrsteamloco` mod ID, resource namespaces, script APIs and saved data keys are retained. Replace the previous ANTE JAR rather than installing both. YanlingMTR's dependency ID remains `mtr`; repository URLs and historical tags are unchanged.

Back up worlds, configuration and resource packs before upgrading. Test your existing routes, custom trains and scripts on a copy of the world first.

## Build from source

Install **JDK 25** and set `JAVA_HOME`. ANTE's Kotlin line is maintained under `ante/` in the [YanlingMTR repository](../README.md); a separate ANTE checkout is not needed. Keep [Kotlin LunaCore](https://github.com/linlunaire/Kotlin-LunaCore) beside YanlingMTR:

```text
workspace/
├── Kotlin-LunaCore/
└── Minecraft-Transit-Railway-3.x.x/
    └── ante/
```

Build Kotlin LunaCore first. Then run once from the **YanlingMTR repository root**:

```sh
./gradlew build
```

On Windows, use `./gradlew.bat` in place of `./gradlew`. The shared wrapper downloads Gradle **9.5.1**. `./gradlew :ante:build` builds ANTE and its current YanlingMTR prerequisite; `./gradlew buildMtr` omits ANTE. For another Kotlin LunaCore location, use `-PtransitCoreProjectDir=/path/to/Transit-Core`. `-PmtrProjectDir` is no longer accepted because this build must use its in-tree YanlingMTR sources.

The build runs both mods' compatibility checks and writes all four loader JARs to the **YanlingMTR root** `build/release/`. The ANTE artifacts are:

- `YanlingMTR-ANTE-fabric-1.2.0-26.2-kotlin.5.jar`
- `YanlingMTR-ANTE-neoforge-1.2.0-26.2-kotlin.5.jar`

## Development

Minecraft-independent Kotlin utilities live in the separate Kotlin LunaCore project. `common/` contains gameplay, rendering, scripts and assets; its production code is being migrated to Kotlin, including the Sowcer package and path generation. `fabric/` and `neoforge/` contain loader integrations. Regression and compatibility checks live in `tests/`. Shared dependency and release versions are defined in [the root gradle.properties](../gradle.properties); ANTE-specific dependencies remain in [gradle.properties](gradle.properties).

The [migration target](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/master/docs/kotlin-migration.md) is all or the overwhelming majority of production code, not just a Kotlin utility layer. Frozen Java JVM contracts and behavioral checks guard Java/Mixin/script compatibility. See the [current checkpoint and compatibility choices](docs/kotlin-migration.md). The rewrite is in progress; language conversion alone is not evidence of higher FPS or TPS.

Report fork-specific problems in [YanlingMTR's issue tracker](https://github.com/linlunaire/Minecraft-Transit-Railway/issues), including the YanlingMTR and YanlingMTR-ANTE versions, loader, logs and a minimal reproduction or resource pack.

## Older versions

This migration lives on YanlingMTR's `codex/kotlin-26.2-preview` branch. The independent **Minecraft 1.21.1** maintenance line is now **YLTE — Yanling Transit Expansion**, kept in the [original repository](https://github.com/linlunaire/mtr-ante). YLTE was not merged into this 26.2 build and is not interchangeable with it. The repository URL and historical tags keep their original history and build instructions. See [import provenance](IMPORT.md).

The last Java-based 26.2 pair is preserved as [ANTE `1.1.1-26.2`](https://github.com/linlunaire/mtr-ante/tree/1.1.1-26.2) and [MTR `26.2-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/26.2-3.3.2), before the production Kotlin migration and external Kotlin LunaCore requirement.

To rebuild them without the old Minecraft-Mappings repository, first apply [Kotlin LunaCore's legacy preparation script](https://github.com/linlunaire/Kotlin-LunaCore/blob/650800892192395a8755ef41efcfe90b913a3c4a/docs/legacy-mappings.md) to the old MTR checkout. Old game releases do not require the Kotlin LunaCore mod.

## Credits and license

Based on [ANTE](https://github.com/aphrodite281/mtr-ante) by Aphrodite281 and [Nemo's Transit Expansion](https://github.com/zbx1425/mtr-nte) by Zbx1425, with contributions from their communities.

Code is licensed under [MIT](LICENSE). Bundled models, textures, sounds and other third-party content retain their respective licenses and [credits](docs/feature.md).
