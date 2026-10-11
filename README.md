# MTR 26.2（Java 版）

本分支保留 Minecraft Transit Railway 3 的 Minecraft 26.2 Java 版本，当前版本为 `3.3.4`，支持 Fabric 与 NeoForge。

新的 Kotlin 架构在 [master](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/master)，Minecraft 1.21.1 的 Java 版在 [1.21.1](https://github.com/linlunaire/Minecraft-Transit-Railway/tree/1.21.1)。不同分支的模组文件不能混用。

## 安装

使用 Java 25，安装对应加载器的 MTR 和 Architectury API；Fabric 还需要 Fabric API。本分支不需要 Kotlin LunaCore。客户端和服务端应使用同一版本。

[ANTE 的 Java 26.2 分支](https://github.com/linlunaire/mtr-ante/tree/26.2) 提供自定义模型、脚本和轨道工具。不要搭配它的 Kotlin 版本。

升级前备份存档、配置和资源包，并先在存档副本中检查线路与自定义列车。

## 构建

将 `JAVA_HOME` 设为 JDK 25，在仓库根目录运行：

```powershell
.\gradlew.bat build
```

Linux/macOS 使用 `./gradlew build`。构建产物位于 `build/release/`：

- `MTR-fabric-26.2-3.3.4.jar`
- `MTR-neoforge-26.2-3.3.4.jar`

## 服务端设置

网页地图端口由 `config/mtr_webserver_port.txt` 设置，默认为 `8888`。可使用 `1025` 至 `65535`，或设为 `0` 停用。修改后重启；端口冲突时地图不启动，不会占用其他进程的端口。

现实时间同步使用原版时钟 API，不依赖 Time & Wind。标准周期要求 20 TPS 和默认昼夜倍率；Youer 的 Purpur 昼夜长度也应保持 `12000`。关闭同步会恢复此前的速率和暂停状态，除非它们已被其他功能修改。重启后此前的设置不可用时，关闭未被更改的同步设置会恢复普通速率。

## 开发与许可

依赖版本见 [gradle.properties](gradle.properties)，回归测试见 [兼容性检查](docs/compatibility.md)。每次分发维护版本时递增 `mod_version`，重新构建两个加载器。

基于 Jonathan Ho 及贡献者开发的 [Minecraft Transit Railway](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway)。代码采用 [MIT License](LICENSE)，Noto 字体采用 [SIL Open Font License](https://openfontlicense.org/)；原作者署名和第三方声明保留。
