# YanlingMTR 1.0.0（Minecraft 1.21.1）

YanlingMTR 是 [Minecraft Transit Railway（MTR）](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) 的独立社区分支。本分支维护 Minecraft **1.21.1**、Java 21 / Gradle 8.14.5 的 Fabric 与 NeoForge 版本，与 26.2 Kotlin 预览版分开构建。

MTR 以香港 MTR、伦敦地铁和纽约地铁为灵感，提供自动列车、轨道、车站、PIDS、缆车、船只与飞机等内容，用于建设可实际运行的交通网络。

公开版本从 **1.0.0** 重新开始，继承此前 3.3.9 的修复。游戏内名称和发布包统一为 YanlingMTR；Mod ID、存档命名空间、配置路径和网络通道仍为 `mtr`。替换旧 MTR JAR 使用，不能同时安装两份。

现有 ANTE/JCM 通过旧的 3.x 版本下限检查依赖，因此加载器元数据使用
`1.21.1-3.3.9+yanlingmtr.1.0.0` 作为兼容编号。JAR 文件、游戏内版本检查
和 Modrinth 公开版本使用新的 1.0.0 系列；后续修改附属 API 时同步维护
`gradle.properties` 中的 `mtr_compat_version`。

1.21.1 的独立 ANTE 附属现更名为 **YLTE — Yanling Transit Expansion**，当前为 `1.1.1-1.21.1-beta.6`。它保留 `mtrsteamloco` 标识及旧资源包、脚本和存档接口，安装时替换原 ANTE JAR。26.2 的新版 ANTE 则已合并进 YanlingMTR 的源码仓库和统一构建，目前仍输出单独的可选模块 JAR；两条版本线不能混用。

## 支持版本

| Minecraft | 加载器 | 构建版本   |
| --- | --- |------------|
| 1.21.1 | Fabric | 1.0.0 |
| 1.21.1 | NeoForge | 1.0.0 |

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

- 1.0.0 更名为 YanlingMTR 并重置公开版本；包含原 3.3.9 的控制面板重复模糊和 PNG 图标修复，GUI 回归已接入构建。

- 3.3.8 补齐三项 NeoMTR 修复：电梯移动时清除可走出的出口标记、按连接端点距离计算车厢连接件俯仰角、玩家断线时立即清理乘坐冷却及线路记录。`gradlew :common:checkNeoMtrCompatibility` 运行真实逻辑回归，`build` 也检查两种加载器的重映射前打包代码；可用 `-PneomtrTestJar=JAR路径` 检查命名空间兼容的成品。来源、适用边界及未移植项见 [NeoMTR 差异核对](docs/neomtr-gap-audit-2026-09-30.md)。
- 3.3.7 在单人发送及群发 S2C 数据前检查客户端实际协商的 `_s2c` 通道，跳过不支持该包的连接，避免 `Payload mtr:write_rails_s2c may not be sent to the client!` 导致服务器 tick 崩溃。`gradlew :neoforge:checkNetworkChannels` 覆盖真实 MTR/Architectury 发送与 NeoForge 通道检查、正常客户端数据保留、群发排除和失败对照；可用 `-PnetworkTestJar=JAR路径` 验证发布包，不替代混合服联机实测。
- 3.3.6 修复列车循环音效静音后继续旧进度的问题；恢复播放会从头开始，详见[验证说明](docs/train-audio-restart.md)。
- 3.3.5 修复 JCM 继承 APG 玻璃时的方块实体校验错误，以及由此触发的 FAWE 初始化失败；保持原有方块和方块实体存档 ID。
- 现实时间同步由 MTR 直接调用原版 API，不再执行 `/taw`、`/time`、`/gamerule`。仅作用于主世界，按服务端本地时间运行 24 小时周期；关闭时恢复原先的昼夜循环规则。
- 网页地图仅在检测到 Dynmap、BlueMap、Squaremap 中任一模组或已启用 Bukkit 插件时启动；未安装时中文提示停用，不监听端口。`config/mtr_webserver_port.txt` 填 `0` 可明确停用；端口冲突不会影响铁路功能。

- 本地开发客户端已完成启动与资源加载验证。
- 建议在使用前测试既有存档、多人联机、PIDS、列车、轨道和资源包工作流。

## 上游与许可证

本项目基于上游 [Minecraft-Transit-Railway/Minecraft-Transit-Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。原作者和贡献者信息保留在源码与模组元数据中。

项目遵循 [MIT License](LICENSE)。随模组分发的 Noto 字体遵循 [SIL Open Font License](https://openfontlicense.org/)。

构建使用仓库内保存的兼容源码，不再下载独立 Minecraft-Mappings 仓库。旧 `1.21.1-3.3.2` 标签需先按 [Kotlin LunaCore 的历史构建恢复说明](https://github.com/linlunaire/Kotlin-LunaCore/blob/master/docs/legacy-mappings.md) 准备源码；归档标签 `transit-core-0.1.0` 保留旧输入与校验和。
