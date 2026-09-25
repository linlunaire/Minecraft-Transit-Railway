<div align="center">
  <img src="resources/fabric/normal/icon.png" alt="Minecraft Transit Railway logo" width="180">
  <h1>Minecraft Transit Railway</h1>
  <p>Fabric 与 NeoForge 社区移植版<br>Community port for Fabric and NeoForge</p>

  <p>
    <img src="https://img.shields.io/badge/Minecraft-26.2%20%7C%201.21.1-62B47A?logo=minecraft" alt="Minecraft 26.2 and 1.21.1">
    <img src="https://img.shields.io/badge/26.2-In%20development-E6A23C" alt="26.2 in development">
    <img src="https://img.shields.io/badge/Java-25%20%7C%2021-ED8B00?logo=openjdk" alt="Java 25 and 21">
    <img src="https://img.shields.io/badge/Gradle-9.5.1%20%7C%208.14.5-02303A?logo=gradle" alt="Gradle 9.5.1 and 8.14.5">
    <img src="https://img.shields.io/badge/Loaders-Fabric%20%7C%20NeoForge-DBD0B4" alt="Fabric and NeoForge">
  </p>

  <p><a href="#简体中文">简体中文</a> · <a href="#english">English</a></p>
</div>

---

## 简体中文

### 项目简介

这是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的社区移植版，仅维护 **1.21.1** 和 **26.2** 两个目标。1.21.1 为正式版，26.2 正在移植。

`master` 默认构建 **26.2**；独立的 1.21.1 基线保存在 [tag `1.21.1-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1-3.3.2)，新主线仍保留 1.21.1 构建入口。

MTR 以香港 MTR、伦敦地铁和纽约地铁为灵感，提供自动列车、轨道、车站、PIDS、缆车、船只与飞机等内容，可用于建设完整运行的交通网络。

> 本项目不是上游官方发布渠道。升级既有存档或部署服务器前，请完整备份世界、配置与资源包。

### 支持与依赖

| Minecraft | 加载器 | 状态 | 必需依赖 |
| --- | --- | --- | --- |
| 1.21.1 | Fabric | 正式版 | [Fabric API](https://modrinth.com/mod/fabric-api)、[Architectury API](https://modrinth.com/mod/architectury-api) |
| 1.21.1 | NeoForge | 正式版 | [Architectury API](https://modrinth.com/mod/architectury-api) |
| 26.2 | Fabric / NeoForge | 编译、打包与资源兼容性检查通过；游戏验证未完成 | 使用 26.2 对应的依赖，不能安装 1.21.1 JAR |

安装与加载器匹配的 MTR JAR 及上述依赖，然后将它们放入游戏或服务器的 `mods` 文件夹。

### 从源码构建

环境要求：

- 1.21.1：JDK 21、Gradle 8.14.5
- 26.2：JDK 25、Gradle 9.5.1
- 使用目标目录内自带的 Gradle Wrapper；通过 `JAVA_HOME` 或 `-JavaHome` 选择 JDK。

Windows：

```powershell
.\gradlew.bat build                         # 默认选择 26.2
.\gradlew.bat build -Version="26.2"          # 指定新版本
.\gradlew.bat build -Version="1.21.1"        # 指定 1.21.1
.\gradlew.bat build -Version="1.21.1" -JavaHome "C:\Java\jdk-21"
```

Linux 或 macOS：

```bash
./gradlew build -Version="26.2"
./gradlew build -Version="1.21.1"
```

`gradlew` / `gradlew.bat` 根据版本参数选择独立构建环境，也可使用 `:fabric:build`、`--stacktrace` 等 Gradle 参数。`-ShowTarget` 只查看版本选择；它不执行构建。1.21.1 工程仍位于根目录，26.2 工程位于 `versions/26.2/`。非法版本会报错。

26.2 已能生成两个加载器的测试 JAR，配方、战利品表和标签已转换并检查，另检查注册物品/方块的模型及纹理引用；实际加载器注册、渲染、合成/掉落和联机仍待游戏验证，不应作为已验证的发行版使用。完整进度见 [双版本移植说明](docs/minecraft-targets.md)。

构建成功后，正式 JAR 位于 `build/release/`，不同 Minecraft 版本可以共存：

- `MTR-fabric-<minecraft-version>-<mod-version>.jar`
- `MTR-neoforge-<minecraft-version>-<mod-version>.jar`

### 移植状态与测试建议

- 1.21.1 的 Fabric 与 NeoForge 已完成源码编译和完整打包验证。
- 1.21.1 本地开发客户端已完成启动与资源加载验证。
- 正式部署前仍建议测试既有存档、多人联机、列车与轨道、PIDS、信号系统、自定义列车和资源包。
- 客户端与服务器应使用相同的 MTR 版本。

### 上游与许可证

本项目基于上游 [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。原作者和贡献者信息保留在源码与模组元数据中。

项目遵循 [MIT License](LICENSE)。随模组分发的 Noto 字体遵循 [SIL Open Font License](https://openfontlicense.org/)。

---

## English

### About

This repository contains a community-maintained port of [Minecraft Transit Railway (MTR)](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway), targeting only **1.21.1** and **26.2**. The 1.21.1 port is a release; 26.2 is in development.

`master` builds **26.2** by default. The standalone 1.21.1 baseline is preserved at [tag `1.21.1-3.3.2`](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1-3.3.2); the main branch also retains the 1.21.1 build entry point.

Inspired by the Hong Kong MTR, the London Underground, and the New York Subway, MTR provides automated trains, rails, stations, PIDS displays, cable cars, boats, and airplanes for building fully functional transport networks.

> This is not an official upstream release channel. Back up your worlds, configuration files, and resource packs before upgrading an existing installation or deploying to a server.

### Support and dependencies

| Minecraft | Loader | Status | Required dependencies |
| --- | --- | --- | --- |
| 1.21.1 | Fabric | Release | [Fabric API](https://modrinth.com/mod/fabric-api), [Architectury API](https://modrinth.com/mod/architectury-api) |
| 1.21.1 | NeoForge | Release | [Architectury API](https://modrinth.com/mod/architectury-api) |
| 26.2 | Fabric / NeoForge | Compilation, packaging and resource compatibility checks pass; gameplay checks remain incomplete | Use dependencies for 26.2; the 1.21.1 JAR is incompatible |

Install the MTR JAR for your loader together with the required dependencies, then place them in the `mods` directory of the client or server.

### Building from source

Requirements:

- 1.21.1: JDK 21 and Gradle 8.14.5
- 26.2: JDK 25 and Gradle 9.5.1
- Use the target's included Gradle Wrapper. Select the JDK with `JAVA_HOME` or `-JavaHome`.

Windows:

```powershell
.\gradlew.bat build                         # Defaults to 26.2
.\gradlew.bat build -Version="26.2"
.\gradlew.bat build -Version="1.21.1"
.\gradlew.bat build -Version="1.21.1" -JavaHome "C:\Java\jdk-21"
```

Linux or macOS:

```bash
./gradlew build -Version="26.2"
./gradlew build -Version="1.21.1"
```

`gradlew` / `gradlew.bat` select the build environment and forward Gradle tasks and options, such as `:fabric:build` and `--stacktrace`. `-ShowTarget` only displays the selected target; it does not build. The 1.21.1 project remains in the repository root; the separate 26.2 project lives in `versions/26.2/`. Unsupported versions fail explicitly.

The 26.2 build produces test JARs for both loaders, with converted and checked recipe, loot and tag resources, plus model/texture reference checks for registered items and blocks. Actual loader registration, rendering, crafting/loot behavior and multiplayer still need game testing; these are not verified releases. See the [two-version port notes](docs/minecraft-targets.md).

Release JARs are written to `build/release/`. Outputs for different Minecraft versions coexist:

- `MTR-fabric-<minecraft-version>-<mod-version>.jar`
- `MTR-neoforge-<minecraft-version>-<mod-version>.jar`

### Port status and testing

- Source compilation and complete packaging have been verified for 1.21.1 on both Fabric and NeoForge.
- Local development-client startup and resource loading have been verified for 1.21.1.
- Before production use, test existing worlds, multiplayer, trains and rails, PIDS displays, signalling, custom trains, and resource packs.
- Clients and servers should use the same MTR version.

### Upstream and license

This project is based on [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway). Original author and contributor attribution is retained in the source code and mod metadata.

The project is distributed under the [MIT License](LICENSE). Bundled Noto fonts are licensed under the [SIL Open Font License](https://openfontlicense.org/).
