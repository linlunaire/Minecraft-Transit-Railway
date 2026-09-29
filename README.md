# Minecraft Transit Railway 3.3.6（Minecraft 1.21.1）

这是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的 Minecraft **1.21.1** 社区移植版。本分支继续维护 Java 21 / Gradle 8.14.5 的 Fabric 与 NeoForge 版本，与 26.2、Kotlin 重构目录分开开发。

MTR 以香港 MTR、伦敦地铁和纽约地铁为灵感，提供自动列车、轨道、车站、PIDS、缆车、船只与飞机等内容，用于建设可实际运行的交通网络。

> 这是测试版，不是上游官方发布渠道。升级现有存档或部署服务器前，请完整备份世界与配置文件。

## 支持版本

| Minecraft | 加载器 | 构建版本   |
| --- | --- |------------|
| 1.21.1 | Fabric | beta |
| 1.21.1 | NeoForge | beta |

### Fabric

需要：

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Architectury API](https://modrinth.com/mod/architectury-api)

### NeoForge

需要：

- [Architectury API](https://modrinth.com/mod/architectury-api)

## 从源码构建

环境要求：

- JDK 21
- 项目自带的 Gradle Wrapper（Gradle 8.14.5）

Windows：

```powershell
.\gradlew.bat build
```

成功后，JAR 位于 `build/release/`

每次分发修复或优化版本时，递增 `gradle.properties` 中的 `mod_version`，并重新构建；不要只重命名旧 JAR。构建会同步文件名、模组元数据、游戏内版本和网页地图版本。

构建直接使用本仓库的兼容层和资源，不再下载 Minecraft-Mappings 或删除源码资源。`build` 会运行节点防水与存档队列回归，通过后才更新发行 JAR。详细验证范围见 [1.21.1 维护记录](docs/maintenance-1.21.1.md)。

## 当前移植状态

- 3.3.6 修复列车循环音效静音后继续旧进度的问题；恢复播放会从头开始，详见[验证说明](docs/train-audio-restart.md)。
- 本地开发客户端已完成启动与资源加载验证。
- 建议在使用前测试既有存档、多人联机、PIDS、列车、轨道和资源包工作流。

## 上游与许可证

本项目基于上游 [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。原作者和贡献者信息保留在源码与模组元数据中。

项目遵循 [MIT License](LICENSE)。随模组分发的 Noto 字体遵循 [SIL Open Font License](https://openfontlicense.org/)。
