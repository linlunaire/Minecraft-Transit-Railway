# YanlingMTR 1.0.3（Minecraft 1.21.1）

YanlingMTR 是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的社区分支，提供自动列车、轨道、车站、到站信息屏（PIDS）、缆车、船只和飞机。本分支维护 Minecraft 1.21.1 的 Fabric 与 NeoForge 版本。

## 安装

使用 Java 21，下载与你的加载器对应的 YanlingMTR JAR，并安装以下依赖：

| 加载器 | 依赖 |
| --- | --- |
| Fabric | [Fabric API](https://modrinth.com/mod/fabric-api)、[Architectury API](https://modrinth.com/mod/architectury-api) |
| NeoForge | [Architectury API](https://modrinth.com/mod/architectury-api) |

升级时替换原有 MTR JAR，不要同时安装两份。模组继续使用 `mtr` 标识、存档命名空间、配置路径和网络通道。

原 ANTE 附属在 1.21.1 上更名为 [YLTE（Yanling Transit Expansion）](https://github.com/linlunaire/mtr-ante)，需要单独安装。它保留 `mtrsteamloco` 标识以及原有资源包、脚本和存档接口，安装时替换原 ANTE JAR。1.21.1 与 26.2 的模组文件不能混用。

升级前请备份存档和资源包。本地开发客户端已完成启动与资源加载验证；已有存档、多人联机和自定义资源包仍建议先在副本中测试。

## 近期更新

- 1.0.3：修复铁路数据保存流程，补齐连续保存和关服保存，并修正负坐标区块判断。
- 1.0.2：现实时间同步改用原版 API，不再依赖 Time & Wind，也不会被混合服的同名命令接管。
- 1.0.1：修复车厂时刻表界面短滑动条的紫黑缺图。
- 1.0.0：更名为 YanlingMTR，继承此前 3.3.9 的修复，包括控制面板重复模糊和 PNG 图标显示问题。

详细修复与测试记录：[维护记录](docs/maintenance-1.21.1.md)、[存档与编辑检查](docs/health-review-2026-10-11.md)、[时间同步](docs/real-time-sync-1.21.1.md)、[滑动条](docs/gui-short-slider-1.21.1.md)、[NeoMTR 修复核对](docs/neomtr-gap-audit-2026-09-30.md)、[列车音效](docs/train-audio-restart.md)。

## 可选功能

现实时间同步按服务端本地时间运行，只影响主世界。关闭后恢复原先的昼夜循环规则，不修改世界运行时间。

网页地图在检测到 Dynmap、BlueMap 或 Squaremap 模组（或混合服上对应的已启用插件）时启动。未安装时不会监听端口；将 `config/mtr_webserver_port.txt` 设为 `0` 可停用地图。端口冲突不影响铁路功能。

## 从源码构建

使用 JDK 21 和项目自带的 Gradle Wrapper（Gradle 8.14.5）。Windows 下运行：

```powershell
.\gradlew.bat build
```

构建通过后，JAR 位于 `build/release/`。兼容源码和资源已保存在仓库中，不需要另行下载 Minecraft-Mappings。

重建旧 `1.21.1-3.3.2` 标签时，请先阅读 [历史构建恢复说明](https://github.com/linlunaire/Kotlin-LunaCore/blob/master/docs/legacy-mappings.md)。

## 上游与许可证

本项目基于 [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。上游作者和贡献者信息保留在源码与模组元数据中。

代码采用 [MIT License](LICENSE)，随模组分发的 Noto 字体采用 [SIL Open Font License](https://openfontlicense.org/)。
