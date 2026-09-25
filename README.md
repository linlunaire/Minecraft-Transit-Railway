# Minecraft Transit Railway

Build transport networks with automated trains, stations, signalling and passenger information displays.

A community-maintained port of **MTR 3** for **Minecraft 26.2**, supporting **Fabric** and **NeoForge**. This fork is independent of the [official MTR project](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway).

## Install

Use Java 25 and the MTR JAR matching your loader. Install the following dependencies for **Minecraft 26.2** alongside MTR in the `mods` directory:

| Loader | Required mods |
| --- | --- |
| Fabric | [Fabric API](https://modrinth.com/mod/fabric-api), [Architectury API](https://modrinth.com/mod/architectury-api) |
| NeoForge | [Architectury API](https://modrinth.com/mod/architectury-api) |

Use matching MTR versions on the server and clients. [ANTE](https://github.com/linlunaire/mtr-ante) is an optional add-on for custom models, scripting and rail tools.

Back up worlds, configuration and resource packs before upgrading. Test an existing world on a copy before replacing a live installation.

## Build from source

Install **JDK 25** and set `JAVA_HOME`, then run from the repository root:

```sh
./gradlew build
```

On Windows, use `./gradlew.bat` in place of `./gradlew`. The wrapper downloads Gradle **9.5.1**; a separate Gradle installation is not needed.

The build runs the compatibility checks and writes both loader JARs to `build/release/`:

- `MTR-fabric-26.2-3.3.2.jar`
- `MTR-neoforge-26.2-3.3.2.jar`

## Development

`common/` contains shared gameplay code and assets; `fabric/` and `neoforge/` contain loader integrations. [Regression and compatibility checks](docs/compatibility.md) live in `tests/`. Dependency versions are defined in [gradle.properties](gradle.properties).

Report fork-specific problems in [this repository's issue tracker](https://github.com/linlunaire/Minecraft-Transit-Railway/issues), including the loader and mod versions, logs and reproduction steps.

## Older versions

`master` targets **26.2 only**. The complete Minecraft 1.21.1 source and build instructions remain available at [tag `1.21.1-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1-3.3.2).

## Credits and license

Based on [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) by Jonathan Ho and its contributors. Original attribution is retained in the source and mod metadata.

Code is licensed under [MIT](LICENSE). Bundled Noto fonts retain their [SIL Open Font License](https://openfontlicense.org/); other third-party content retains its own notices.
