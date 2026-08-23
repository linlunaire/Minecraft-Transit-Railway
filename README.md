# Minecraft Transit Railway 3.x — 1.21.1 Port

这是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的 Minecraft **1.21.1** 社区移植版本。

MTR 是一款以香港 MTR、伦敦地铁和纽约地铁为灵感的 Minecraft 模组，提供自动列车、轨道、车站、PIDS、缆车、船只和飞机等内容，用于建设可实际运行的交通网络。

> 本仓库处于移植与测试阶段，并非上游官方发布渠道。请在存档或服务器使用前自行备份。

## 支持的平台

| 游戏版本 | 加载器 | 状态 |
| --- | --- | --- |
| Minecraft 1.21.1 | Fabric | 支持 |
| Minecraft 1.21.1 | NeoForge | 支持 |

## 安装

请只选择与自己加载器对应的 MTR JAR，不要同时安装 Fabric 与 NeoForge 版本。

### Fabric

前置Mod：

- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Architectury API](https://modrinth.com/mod/architectury-api)

### NeoForge

前置：

- [Architectury API](https://modrinth.com/mod/architectury-api)

## 从源码构建

构建环境：

- JDK 21
- Gradle Wrapper（Gradle 8.14.5）

在仓库根目录运行：

```powershell
.\gradlew.bat build -PbuildVersion=1.21.1 --console=plain --stacktrace
```

构建完成后的发布 JAR 位于：

```text
build/release/
```

## 开发说明

- 映射兼容层来自 [Minecraft-Mappings 1.21.1 分支](https://github.com/Jhesterccj/Minecraft-Mappings/tree/1.21.1)。

## 上游与许可证

本项目基于上游 [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。原作者与贡献者信息保留在源码及模组元数据中。

项目遵循 [MIT License](LICENSE)。随模组分发的 Noto 字体遵循 [SIL Open Font License](https://openfontlicense.org/)。
