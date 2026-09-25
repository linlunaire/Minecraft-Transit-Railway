<div align="center">
  <img src="fabric/src/main/resources/icon.png" alt="Minecraft Transit Railway" width="128">

  <h1>Minecraft Transit Railway</h1>

  <p>Automated trains, stations, signalling and passenger information displays.</p>
  <p>A community-maintained <strong>MTR 3</strong> port for Fabric and NeoForge.</p>

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
> `master` targets **Minecraft 26.2 only**. For Minecraft **1.21.1**, use the source and build instructions at [`1.21.1-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1-3.3.2).

## Installation

Use **Java 25** and the MTR JAR for your loader. Place MTR and the dependencies for **Minecraft 26.2** in `mods/`:

| Loader | Required dependencies |
| :--- | :--- |
| **Fabric** | [Fabric API](https://modrinth.com/mod/fabric-api) · [Architectury API](https://modrinth.com/mod/architectury-api) |
| **NeoForge** | [Architectury API](https://modrinth.com/mod/architectury-api) |

Use matching MTR versions on the server and clients.

### Server configuration

The optional web map uses the port in `config/mtr_webserver_port.txt` (default
`8888`). Use a free port from `1025` to `65535`, or `0` to disable the web map,
then restart. A port conflict does not stop Minecraft, but the map remains
unavailable; MTR does not take over another process or choose an alternate port.

The Overworld time-sync option uses Minecraft 26.2's native clock API; Time &
Wind is not required. The saved option key is retained for existing worlds.
A 24-hour cycle assumes 20 TPS and default day/night multipliers. On Youer,
custom Purpur `gameplay-mechanics.daylight-cycle-ticks.daytime` / `nighttime`
values also affect progression; leave both at `12000` for this option's standard cycle. Disabling live sync
restores its previous clock rate/pause unless another owner changed them.
After a restart, the earlier in-memory settings are unavailable, so disabling
an unchanged saved real-time rate falls back to the normal rate.

**Optional:** [ANTE](https://github.com/linlunaire/mtr-ante) adds custom models, scripting and rail tools. Choose a compatible build.

> [!IMPORTANT]
> Back up worlds, configuration and resource packs before upgrading. Test existing worlds on a copy first.

## Build

Set `JAVA_HOME` to **JDK 25**, then run from the repository root:

```sh
.\gradlew.bat build
```

The build runs compatibility checks and produces both loader JARs:

```text
build/release/
├── MTR-fabric-26.2-3.3.2.jar
└── MTR-neoforge-26.2-3.3.2.jar
```

## Development

Shared code and assets live in `common/`; loader integrations live in `fabric/` and `neoforge/`.

See [`gradle.properties`](gradle.properties) for dependency versions and [compatibility checks](docs/compatibility.md) for the tests in `tests/`.

Report fork-specific problems in [Issues](https://github.com/linlunaire/Minecraft-Transit-Railway/issues), including loader and mod versions, logs and reproduction steps.

## Credits & license

Based on [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) by **Jonathan Ho** and its contributors. This is an independent community fork, not an official MTR release.

Code: [MIT](LICENSE). Bundled Noto fonts: [SIL Open Font License](https://openfontlicense.org/). Original attribution and third-party notices are retained.
