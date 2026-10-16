<div align="center">
  <img src="fabric/src/main/resources/icon.png" alt="YanlingMTR icon" width="128">

  <h1>YanlingMTR</h1>
  <p><strong>Build a railway. Run a network.</strong></p>
  <p>Automated trains, stations and passenger information for Minecraft 1.21.1.</p>

  <p>
    <a href="#whats-new"><img src="https://img.shields.io/badge/YanlingMTR-1.0.4-B52650?style=flat-square" alt="YanlingMTR 1.0.4"></a>
    <img src="https://img.shields.io/badge/Minecraft-1.21.1-62A854?style=flat-square" alt="Minecraft 1.21.1">
    <img src="https://img.shields.io/badge/Loaders-Fabric%20%2B%20NeoForge-546E7A?style=flat-square" alt="Fabric and NeoForge">
    <img src="https://img.shields.io/badge/Java-21-E58A2B?style=flat-square" alt="Java 21">
    <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-327E88?style=flat-square" alt="MIT license"></a>
  </p>
  <p>
    <a href="https://github.com/linlunaire/YanlingMTR/actions/workflows/build.yml?query=branch%3A1.21.1"><img src="https://github.com/linlunaire/YanlingMTR/actions/workflows/build.yml/badge.svg?branch=1.21.1" alt="1.21.1 build status"></a>
  </p>
  <p>
    <a href="#download--install">Download &amp; install</a> &nbsp;·&nbsp;
    <a href="#whats-new">What's new</a> &nbsp;·&nbsp;
    <a href="#build-from-source">Build</a> &nbsp;·&nbsp;
    <a href="https://github.com/linlunaire/YanlingMTR/issues">Report an issue</a> &nbsp;·&nbsp;
    <a href="https://github.com/linlunaire/mtr-ante/tree/1.21.1">YLTE</a>
  </p>
</div>

---

YanlingMTR is a community continuation of [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway). This branch maintains the MTR 3 experience on **Minecraft 1.21.1**, with Fabric and NeoForge builds.

## 🚆 Build your network

| Railways & operations | Stations & passengers |
| :--- | :--- |
| Connect tracks, assign routes and run automated trains. | Build stations with platforms, gates and ticket machines. |
| Set up depots and timetables for your network. | Show live arrivals on passenger information displays. |
| Extend your routes with cable cars, boats and aircraft. | Add custom models, scripts and rail tools with [YLTE](https://github.com/linlunaire/mtr-ante/tree/1.21.1). |

## Download & install

Download **`YanlingMTR-1.21.1`** from the Artifacts section of a successful [1.21.1 build](https://github.com/linlunaire/YanlingMTR/actions/workflows/build.yml?query=branch%3A1.21.1). GitHub requires sign-in to download workflow artifacts.

Use **Java 21** and place the JAR for your loader in `mods/`, together with its Minecraft 1.21.1 dependencies:

| Loader | Required dependencies | Mod file |
| :--- | :--- | :--- |
| Fabric | [Fabric API](https://modrinth.com/mod/fabric-api) · [Architectury API 13.0.11+](https://modrinth.com/mod/architectury-api) | `YanlingMTR-fabric-1.21.1-1.0.4.jar` |
| NeoForge 21.1.248+ | [Architectury API 13.0.11+](https://modrinth.com/mod/architectury-api) | `YanlingMTR-neoforge-1.21.1-1.0.4.jar` |

Replace your existing MTR JAR and use matching versions on clients and servers. For custom models and scripting, install [YLTE 1.1.3](https://github.com/linlunaire/mtr-ante/tree/1.21.1) separately.

> [!IMPORTANT]
> Back up worlds, configuration and resource packs before upgrading. The `mtr` ID, save namespaces, configuration paths and network channels are retained. The separate [26.2 development branch](https://github.com/linlunaire/YanlingMTR/tree/master) uses different builds.

## What's new

**1.0.4 · October 16, 2026**

- Fixed ticket top-ups accepting invalid payments and overflowing balances. Update the server to apply this fix.
- Fixed arrival sorting that could leave PIDS text missing.
- Fixed web-map route requests hanging on small networks, reusing old results or interrupting an earlier request.

<details>
<summary>Previous updates</summary>

- **1.0.3** — Fixed railway data persistence, repeated saves, shutdown saves and negative chunk coordinates.
- **1.0.2** — Moved real-time synchronization to the vanilla API, removing the Time & Wind dependency and command conflicts on hybrid servers.
- **1.0.1** — Fixed missing textures on short depot timetable scrollbars.
- **1.0.0** — Renamed the fork to YanlingMTR, carrying forward the 3.3.9 fixes for repeated dashboard blur and PNG icons.

</details>

## ⚙️ Optional features

**Real-time synchronization** follows the server's local time in the Overworld. Turning it off restores the previous daylight-cycle rule without changing world game time.

**Web maps** start when Dynmap, BlueMap or Squaremap is available, including supported plugin detection on hybrid servers. Set `config/mtr_webserver_port.txt` to `0` to disable the map. A port conflict does not stop railway operation.

## Build from source

Set `JAVA_HOME` to **JDK 21**. The included wrapper uses Gradle 8.14.5.

```powershell
# Windows
.\gradlew.bat build
```

```sh
# Linux / macOS
./gradlew build
```

Both loader JARs are written to **`build/release/`** after the build checks pass. Compatibility sources and resources are included in this repository; a separate Minecraft-Mappings checkout is not required.

Older local JARs are retained in `build/release/archive/`. This is build output and is removed by `clean`; keep permanent backups elsewhere.

## 📚 Documentation & support

See the [maintenance notes](docs/maintenance-1.21.1.md), [save and editing checks](docs/health-review-2026-10-11.md), [real-time synchronization](docs/real-time-sync-1.21.1.md), [GUI fixes](docs/gui-short-slider-1.21.1.md), [NeoMTR comparison](docs/neomtr-gap-audit-2026-09-30.md) and [train audio notes](docs/train-audio-restart.md). These technical notes are currently in Chinese. The old `1.21.1-3.3.2` tag has separate [build recovery instructions](https://github.com/linlunaire/Kotlin-LunaCore/blob/master/docs/legacy-mappings.md).

For bugs in this fork, [open an issue](https://github.com/linlunaire/YanlingMTR/issues) with your loader and mod versions, reproduction steps and logs. Include the shader pack and rendering mods for visual problems.

## Credits & license

Based on [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) by **Jonathan Ho and contributors**. Maintained by **linlunaire** as an independent community fork.

Code is available under the [MIT License](LICENSE). Bundled Noto fonts use the [SIL Open Font License](https://openfontlicense.org/). Original authorship and third-party notices are retained.
