<div align="center">
  <img src="fabric/src/main/resources/icon.png" alt="YanlingMTR" width="128">

  <h1>YanlingMTR</h1>

  <p>Automated trains, stations, signalling and passenger information displays.</p>
  <p><strong>YanlingMTR</strong> — an independent MTR 3 fork for Fabric and NeoForge.</p>

  <p>
    <img src="https://img.shields.io/badge/Minecraft-26.2-62B47A?style=flat-square" alt="Minecraft 26.2">
    <img src="https://img.shields.io/badge/Java-25-ED8B00?style=flat-square" alt="Java 25">
    <img src="https://img.shields.io/badge/Loaders-Fabric%20%7C%20NeoForge-5C6BC0?style=flat-square" alt="Fabric and NeoForge">
    <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-4C9CA6?style=flat-square" alt="MIT License"></a>
  </p>

  <p>
    <a href="#installation">Installation</a> &nbsp;·&nbsp;
    <a href="#build">Build</a> &nbsp;·&nbsp;
    <a href="https://github.com/linlunaire/Minecraft-Transit-Railway/issues">Issues</a> &nbsp;·&nbsp;
    <a href="https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway">Upstream</a>
  </p>
</div>

---

> [!NOTE]
> This **Kotlin preview** targets Minecraft **26.2**. Use branch `codex/kotlin-26.2-preview` for this migration, `26.2` for Java maintenance and `1.21.1` for the older game. It is not yet a fully migrated or multiplayer-validated release.

To rebuild that historical tag without the old Minecraft-Mappings repository, first apply [Kotlin LunaCore's legacy preparation script](https://github.com/linlunaire/Kotlin-LunaCore/blob/650800892192395a8755ef41efcfe90b913a3c4a/docs/legacy-mappings.md). Old game releases do not require the Kotlin LunaCore mod.

The Java-based 26.2 implementation, including its rendering optimizations, is preserved at [`26.2-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/26.2-3.3.2). The [production Kotlin migration](docs/kotlin-migration.md) starts after that tag and is still in progress.

The [`26.2` maintenance branch](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/26.2) contains subsequent Java server fixes. Those fixes are also carried forward into the Kotlin migration; the frozen tag is unchanged.

## Installation

Use **Java 25** and the YanlingMTR JAR for your loader. Place YanlingMTR, the matching [Kotlin LunaCore 0.2.1+](https://github.com/linlunaire/Kotlin-LunaCore) loader JAR, and these dependencies for **Minecraft 26.2** in `mods/`:

| Loader | Required dependencies |
| :--- | :--- |
| **Fabric** | [Fabric API](https://modrinth.com/mod/fabric-api) · [Architectury API](https://modrinth.com/mod/architectury-api) |
| **NeoForge** | [Architectury API](https://modrinth.com/mod/architectury-api) |

Use matching YanlingMTR versions on the server and clients. YanlingMTR retains the `mtr` mod ID, registry and resource namespaces, configuration paths and network channels. Replace the previous MTR fork JAR; do not install both together. Repository URLs and historical tags retain their original names.

YanlingMTR, YanlingMTR-ANTE and JCM share Kotlin LunaCore's bundled, loader-managed Kotlin standard library. They do not need a separate FLK/KFF installation; other mods may still require one. Install the **Fabric or NeoForge Mod JAR**, not Kotlin LunaCore's pure JVM library or sources JAR.

### Server configuration

The optional web map starts only when Dynmap, BlueMap or Squaremap is loaded
as a mod or enabled as a plugin on a hybrid server. Without one, YanlingMTR leaves
the map disabled and does not open a port. Bundled API libraries do not count
as installed providers. Map availability/startup messages are in Chinese.

When enabled, the map uses `config/mtr_webserver_port.txt` (default
`8888`). Use a free port from `1025` to `65535`, or `0` to disable the web map,
then restart. A port conflict does not stop Minecraft, but the map remains
unavailable; YanlingMTR does not take over another process or choose an alternate port.

The Overworld time-sync option uses Minecraft 26.2's native clock API; Time &
Wind is not required. The saved option key is retained for existing worlds.
A 24-hour cycle assumes 20 TPS and default day/night multipliers. On Youer,
custom Purpur `gameplay-mechanics.daylight-cycle-ticks.daytime` / `nighttime`
values also affect progression; leave both at `12000` for the standard cycle.
Disabling live sync restores its previous clock rate/pause unless another
owner changed them. After a restart, the earlier in-memory settings are
unavailable, so disabling an unchanged saved real-time rate falls back to the
normal rate.

**ANTE integration:** The new **26.2 ANTE** line has been merged into YanlingMTR's source repository and unified build. [YanlingMTR-ANTE](ante/README.md) adds custom models, scripting and rail tools. The current packaging still uses a core JAR and a separate optional ANTE JAR; the repository merger is not a single-JAR merger. The old **1.21.1** standalone addon is now **YLTE — Yanling Transit Expansion**, maintained in the [original addon repository](https://github.com/linlunaire/mtr-ante).

> [!IMPORTANT]
> Back up worlds, configuration and resource packs before upgrading. Test existing worlds on a copy first.

## Build

Set `JAVA_HOME` to **JDK 25**. Check out [Kotlin LunaCore](https://github.com/linlunaire/Kotlin-LunaCore) beside this repository:

```text
workspace/
├── Kotlin-LunaCore/
└── Minecraft-Transit-Railway-3.x.x/
    ├── common/                      YanlingMTR gameplay and assets
    ├── fabric/                      YanlingMTR Fabric integration
    ├── neoforge/                    YanlingMTR NeoForge integration
    └── ante/                        ANTE sources, tests and loader integrations
```

Build **Kotlin LunaCore first**, then run this command once from the YanlingMTR repository root. The unified build produces and verifies YanlingMTR before compiling ANTE against those exact artifacts:

```sh
./gradlew build
```

On Windows use `./gradlew.bat`. For a different Kotlin LunaCore checkout location, pass `-PtransitCoreProjectDir=<path>`; relative paths resolve from the repository root for both builds. The build consumes its versioned artifacts without embedding the prerequisite. All four checked JARs are written to the same directory:

```text
build/release/
├── YanlingMTR-fabric-26.2-1.0.0-beta.1.jar
├── YanlingMTR-neoforge-26.2-1.0.0-beta.1.jar
├── YanlingMTR-ANTE-fabric-1.2.0-26.2-kotlin.5.jar
└── YanlingMTR-ANTE-neoforge-1.2.0-26.2-kotlin.5.jar
```

Install only the JARs for **one** loader. `./gradlew buildMtr` builds and verifies YanlingMTR alone. `./gradlew :ante:build` builds ANTE and its matching in-tree YanlingMTR prerequisite automatically. A separate `mtr-ante` checkout and `-PmtrProjectDir` are no longer used. Game, loader, Kotlin LunaCore and both release versions are coordinated in the root [gradle.properties](gradle.properties).

## Development

YanlingMTR starts its own public version series: **1.0.0** for Minecraft 1.21.1
and **1.0.0-beta.1** for this 26.2 Kotlin preview. The public version controls
release filenames, in-game version checks and Modrinth version numbers. The
loader dependency version retains the previous MTR compatibility floor and
appends `+yanlingmtr.<public-version>` so existing ANTE/JCM minimum-version
checks still work. Keep `mtr_compat_version` monotonic when changing addon APIs.
This compatibility number is not another release series or a second mod.

On Modrinth, use one **YanlingMTR** project and separate version entries for each
Minecraft version/loader pair. 26.2 additionally requires **Kotlin LunaCore
0.2.1+**; list it as a dependency once its Modrinth project exists. Explain the
26.2 ANTE source/build integration and its current separate-JAR installation.
The old 1.21.1 addon is published independently as **YLTE — Yanling Transit Expansion**.
Project metadata and version upload drafts
are maintained in `modrinth/`; build artifacts are not uploaded by Gradle.

After both game-version builds pass, run `pwsh -File tools/Prepare-ModrinthRelease.ps1`.
It validates the delivery JARs and prepares `build/modrinth/YanlingMTR-YLTE-release/`
with the upload text, version/dependency fields and checksums. The 1.21.1 build
defaults to the sibling `Minecraft-Transit-Railway-1.21.1` checkout; override it
with `-MaintenanceProject <path>` if needed. The integrated 26.2 ANTE module is
kept in `ante-26.2/`; the separately maintained 1.21.1 YLTE release and its upload
drafts are in `ylte-1.21.1/`. YLTE defaults to the sibling `mtr-ante-1.21.1`
checkout, overridable with `-YlteProject <path>`. Both retain their addon version series.

Declare the upstream MTR fork in Modrinth's derivative-content disclosure and
retain MIT and bundled font notices. The publishing work and portions of the
code use AI assistance; complete the platform's AI disclosure accurately.
See [Modrinth content rules](https://modrinth.com/legal/rules) and
[multi-loader guidance](https://support.modrinth.com/en/articles/8824810-multiple-loaders-on-one-project).

Minecraft-independent Kotlin policies live in the separate [Kotlin LunaCore](https://github.com/linlunaire/Kotlin-LunaCore) project. YanlingMTR and YanlingMTR-ANTE are its first consumers. YanlingMTR game code and assets stay in `common/`; loader integrations live in `fabric/` and `neoforge/`. ANTE retains the same internal layout under `ante/`. Its production dependency remains one-way, ANTE to YanlingMTR; the repository merger does not change mod IDs, script interfaces or saved-data formats. See [architecture](docs/architecture.md) for ownership and compatibility constraints.

See [`gradle.properties`](gradle.properties) for dependency versions and [compatibility checks](docs/compatibility.md) for the tests in `tests/`.

Report fork-specific problems in [Issues](https://github.com/linlunaire/Minecraft-Transit-Railway/issues), including loader and mod versions, logs and reproduction steps.

## Credits & license

Based on [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) by **Jonathan Ho** and its contributors. This is an independent community fork, not an official MTR release.

Code: [MIT](LICENSE). ANTE retains its [original license](ante/LICENSE), [credits](ante/README.md#credits-and-license) and [import provenance](ante/IMPORT.md). Bundled Noto fonts: [SIL Open Font License](https://openfontlicense.org/). Original attribution and third-party notices are retained.
