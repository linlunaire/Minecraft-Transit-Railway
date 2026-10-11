# YanlingMTR

YanlingMTR 是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的社区分支，提供自动列车、轨道、车站、信号和到站信息屏。支持 Fabric 与 NeoForge。

| 分支 | 版本 |
| --- | --- |
| [master](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/master) | Minecraft 26.2，Kotlin 重构版 |
| [26.2](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/26.2) | Minecraft 26.2，旧 Java 版 |
| [1.21.1](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1) | Minecraft 1.21.1，Java 维护版 |

当前主分支版本为 `1.0.0-beta.1`，仍在按模块重构。已有存档、资源包和附属接口的兼容性是重构约束；完整的客户端、专服和多人联机验证尚未完成。升级前请备份，并先用存档副本测试。

## 安装

Minecraft 26.2 使用 Java 25。下载与你的加载器对应的 YanlingMTR 和 [Kotlin LunaCore](https://github.com/linlunaire/Kotlin-LunaCore)，放入 `mods/`：

| 加载器 | 其他依赖 |
| --- | --- |
| Fabric | [Fabric API](https://modrinth.com/mod/fabric-api)、[Architectury API](https://modrinth.com/mod/architectury-api) |
| NeoForge | [Architectury API](https://modrinth.com/mod/architectury-api) |

本分支使用 Kotlin LunaCore 0.2.1 作为构建依赖。它提供共享的 Kotlin 运行库，不需要为这组模组单独安装 FLK/KFF；其他模组若依赖它们，仍需保留。

YanlingMTR 保留 `mtr` 标识、注册名、配置路径和网络通道。升级时替换原 MTR JAR，不要同时安装两份。客户端和服务端应使用同一版本，1.21.1、Java 26.2 与 Kotlin 26.2 的文件不能混用。

[ANTE](ante/README.md) 的 26.2 源码已合并到本仓库，提供自定义模型、脚本和轨道工具。目前仍需单独安装对应加载器的 ANTE JAR。1.21.1 的附属继续在 [YLTE 仓库](https://github.com/linlunaire/mtr-ante/tree/1.21.1) 维护。

## 构建

使用 JDK 25，将 Kotlin LunaCore 与本仓库放在同一级目录。先构建 Kotlin LunaCore，再在本仓库根目录运行：

```powershell
.\gradlew.bat build
```

Linux/macOS 使用 `./gradlew build`。Kotlin LunaCore 的确切提交见 [构建工作流](.github/workflows/build.yml)；自定义路径可传入 `-PtransitCoreProjectDir=<路径>`。

构建会依次检查 YanlingMTR 和 ANTE，并将 Fabric、NeoForge 的四个 JAR 写入 `build/release/`。只安装其中一个加载器的文件。`buildMtr` 只构建本体；`:ante:build` 会先构建它依赖的本体。无需另行检出 ANTE。

## 可选功能

网页地图只在检测到 Dynmap、BlueMap 或 Squaremap 时启动。端口由 `config/mtr_webserver_port.txt` 设置，默认 `8888`，可用范围为 `1025` 至 `65535`；设为 `0` 可停用。端口冲突不会中断铁路功能。

现实时间同步使用 Minecraft 26.2 的原版时钟 API，无需 Time & Wind。20 TPS 下的标准周期要求昼夜倍率保持默认；Youer 的 Purpur 昼夜长度也应保持 `12000`。关闭同步时恢复之前的时钟设置；重启后若旧设置已不可用，则恢复普通速率。

## 开发与许可

共享代码在 `common/`，加载器集成在 `fabric/`、`neoforge/`，ANTE 在 `ante/`。重构方案见 [架构](docs/architecture.md) 和 [模块重构记录](docs/kotlin-migration.md)，测试说明见 [兼容性检查](docs/compatibility.md)。问题请附上模组版本、加载器、日志和复现步骤。

本项目基于 Jonathan Ho 及贡献者开发的 Minecraft Transit Railway。代码采用 [MIT License](LICENSE)；ANTE 保留自己的 [许可证](ante/LICENSE)、[署名](ante/README.md#credits-and-license) 和 [来源记录](ante/IMPORT.md)。Noto 字体采用 [SIL Open Font License](https://openfontlicense.org/)。
