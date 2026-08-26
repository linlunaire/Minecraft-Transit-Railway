# Minecraft Transit Railway 3.3.0-beta.1（Minecraft 1.21.1）

这是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的 Minecraft **1.21.1** 社区移植测试版。

MTR 以香港 MTR、伦敦地铁和纽约地铁为灵感，提供自动列车、轨道、车站、PIDS、缆车、船只与飞机等内容，用于建设可实际运行的交通网络。

> 这是测试版，不是上游官方发布渠道。升级现有存档或部署服务器前，请完整备份世界与配置文件。

## 支持版本

| Minecraft | 加载器 | 构建版本 |
| --- | --- | --- |
| 1.21.1 | Fabric | 3.3.0-beta.1 |
| 1.21.1 | NeoForge | 3.3.0-beta.1 |

### Fabric

需要：

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Architectury API](https://modrinth.com/mod/architectury-api)

使用 `MTR-fabric-1.21.1-3.3.0-beta-1.jar`。

### NeoForge

需要：

- [Architectury API](https://modrinth.com/mod/architectury-api)

使用 `MTR-neoforge-1.21.1-3.3.0-beta-1.jar`。

## 从源码构建

环境要求：

- JDK 21
- 项目自带的 Gradle Wrapper（Gradle 8.14.5）

Windows：

```powershell
.\gradlew.bat build
```

成功后，发布 JAR 位于 `build/release/`

## 当前移植状态

- 本地开发客户端已完成启动与资源加载验证。
- 建议在发布前测试既有存档、多人联机、PIDS、列车、轨道和资源包工作流。

## 上游与许可证

本项目基于上游 [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。原作者和贡献者信息保留在源码与模组元数据中。

项目遵循 [MIT License](LICENSE)。随模组分发的 Noto 字体遵循 [SIL Open Font License](https://openfontlicense.org/)。
