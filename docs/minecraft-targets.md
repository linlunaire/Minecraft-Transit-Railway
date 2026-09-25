# Minecraft 1.21.1 / 26.2

## 构建入口

仅维护两个目标，版本目录见 `gradle/minecraft-targets.json`。`defaultVersion` 当前为 `26.2`。

```powershell
.\gradlew.bat build -Version="1.21.1"
.\gradlew.bat build -Version="26.2"
.\gradlew.bat build
```

Linux/macOS 使用相同的 `./gradlew build -Version="..."` 语法。`-Version` 是仓库 Wrapper 处理的参数，不是 Gradle 内建选项。它会在启动 Gradle 前选择工程和工具链。省略参数选择 26.2；非法版本直接失败。`-ShowTarget` 仅查看选择结果。

| Minecraft | 工程目录 | Java | Gradle | 当前状态 |
| --- | --- | --- | --- | --- |
| 1.21.1 | 根目录 | 21 | 8.14.5 | Fabric / NeoForge 完整构建通过 |
| 26.2 | `versions/26.2` | 25 | 9.5.1 | 两个加载器完整构建及资源检查通过；游戏验证未完成的测试包 |

通过 `JAVA_HOME` 或 `-JavaHome` 指定对应 JDK。每个工程有自己的 Wrapper、生成目录和依赖，不把 26.2 的工具链应用到 1.21.1。

模组版本仍只修改根目录 `gradle.properties` 的 `mod_version`。两个目标的成功产物汇总到 `build/release/MTR-<loader>-<minecraft>-<mod_version>.jar`。26.2 发布任务显式依赖公共模块和对应加载器的检查；已通过 `--continue` 故障注入确认检查失败时两个旧发布包的哈希和写入时间不变。清理旧模组版本仅匹配当前 Minecraft 目标，保留另一个目标的产物。

## 26.2 预支持边界

26.2 工程复用当前源码，并在各模块的 `build/generated/sources/mtr` 内生成副本。`source-port.gradle` 编排注册、NBT、方块、实体、物品、GUI、渲染、客户端模型和加载器接口的定向转换；涉及行为变化的桥接位于同路径 `versions/26.2/<module>/src/main/java` 覆盖层。构建过程不修改 1.21.1 源码。

已有 `SavedData` / `BlockEntity` 持久化覆盖层。它们保留 MTR 的 NBT 键和读写接口；首次升级时读取旧维度目录下的 MTR 数据文件，原文件保留。新格式文件存在但解析失败时必须报错，不能用空数据覆盖。

已接入构造前注册资源键（沿用 181 个方块、105 个物品的旧 ID）、NBT getter 缺省值、GUI extraction/鼠标事件、方块更新与 tooltip 桥，以及保留 MTR 分阶段绘制契约的模型适配层。货物改用服务器注册表解析组件，同时兼容根 `Items` 与嵌套 `cargo` 两种旧结构。文件写入先完成编码，再通过临时文件替换；编码或写入失败的记录不进入旧文件删除流程。

实体移动已接入 26.2 的 `InterpolationHandler`，保持旧逐 tick 插值公式和本地乘客的列车驱动位置分支；座位玩家标识使用内建字符串同步器传递 UUID。物品使用/提示文本、GUI 当前屏幕入口、光照打包、权限检查、字体描述和像素读写也已调整。线路图读取像素时显式恢复旧 ABGR 顺序，保留 alpha；模型仍按原有部件和材质阶段绘制。

新增实体/方块实体提取桥：旧绘制阶段捕获顶点和文本，随后只提交快照；顶点通过基础类型数组存储，文字保存 Unicode、样式、字体和矩阵副本，独立的即时批次不会提前封存主模型缓冲。原版船和矿车改用真实新模型与动画状态，不再创建无世界的假实体。乘客也在提取阶段转换成原版渲染状态；其游戏内路径仍待验证。车体材质保留旧背面剔除、主输出目标与深度写入。

地形层注册改为烘焙模型的逐 quad 材质覆盖，保留旧 cutout/translucent 意图，不依赖新版本的自动透明度判定。仅为实际注册的 270 个物品生成新版 `assets/mtr/items` 定义，其中 84 个使用 `mtr:selected` 条件，仍以自定义 NBT 中是否存在 `pos` 判断选中。84 个选中态子模型保留，但不再与未注册的旧 `platform_rail` 模型一起生成冗余顶层入口。三处车站颜色石英柱模型使用原版的新 `quartz_pillar_side` 侧面纹理，转换仅作用于 26.2 资源输出。世界列车与资源包创建器预览在 `LevelExtractor` 中提取，快照随 `LevelRenderState` 重置，再由 `LevelRenderer.submitFeatures` 提交；乘客偏移也在提取时捕获。两个加载器使用同一组客户端钩子。

数据资源由 `DataResourcePort.groovy` 转换到 26.2 自有生成目录：297 个配方改用新版目录和 ingredient/result 结构，176 个战利品表改用新版目录及 `any_of` 条件，32 个标签改用单数目录。保留现有 MTR/`c:` ID、数量、图案和条件；同路径的非替换标签合并成员，配方/战利品的冲突副本及未支持的格式直接报错。司机钥匙配方中的原版 `chain` 更新为 `iron_chain`，对应[原版 1.21.9 的 ID 变更](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-9)。原始 1.21.1 资源不被改写。

2026-09-14 检查结果：公共模块、Fabric、NeoForge 编译全部通过；十组独立兼容性检查和一组加载器钩子静态检查通过。专服网络注册修复后的离线完整构建用时 55 秒，重新执行了 Architectury 两种生产转换和打包，并在检查成功后更新发布包。实际 JAR 中的 `Registry` 已分别调用 Fabric/NeoForge 实现，且包含新版物品定义、客户端钩子和纹理；两个 JAR 均核实包含新增的启动登记调用和物理服务端判断，未打包网络测试适配器。产物保留 297 个配方、176 个战利品表、32 个标签，没有旧的 `recipes` / `loot_tables` / `tags/items` / `tags/blocks` 路径。测试产物为：

- `build/release/MTR-fabric-26.2-3.3.2.jar`
- `build/release/MTR-neoforge-26.2-3.3.2.jar`

**这仍不是经过游戏验证的发行版。** 还未启动实际 Fabric/NeoForge 游戏，未验证完整模组注册、运行时 Mixin 应用、GPU 绘制、合成/掉落行为或联机。资源结构检查也不能覆盖全部客户端资源加载与显示。CI 继续将 26.2 标为移植中，不能把零编译错误或打包成功当成剩余工作比例。

构建复查曾出现 Loom 缓存 `invalid header field` 配置错误，09-14 再次在 NeoForge 配置阶段出现（line 34238）；下一次执行由 Loom 自动重建缓存后通过。该问题不发生在源码编译阶段，但根因尚未证明，也不能据重试通过认定已修复；没有为此添加源码绕过逻辑。

可在完整移植完成前单独运行兼容回归：

```powershell
.\gradlew.bat :common:checkCompatibility -Version="26.2" -JavaHome "<JDK 25 路径>"
.\gradlew.bat :common:checkDataResourceCompatibility -Version="26.2" -JavaHome "<JDK 25 路径>"
.\gradlew.bat :common:checkAssetReferenceCompatibility -Version="26.2" -JavaHome "<JDK 25 路径>"
.\gradlew.bat :common:checkNetworkCompatibility -Version="26.2" -JavaHome "<JDK 25 路径>"
.\gradlew.bat :common:checkLoaderHookCompatibility -Version="26.2" -JavaHome "<JDK 25 路径>"
```

检查覆盖注册 ID/属性、嵌套与异常情况下的构造上下文、NBT 根键/数组/旧 UUID、旧 `.dat`、货物槽位/数量/组件/旧 byte Slot、未知物品拒绝、失败时不改写目标货箱，以及文件替换/失败保留。

实体检查包括目标中途更新、单 tick 移动、取消、非正步数和 20,000 次旧公式对比。模型检查使用实际烘焙的 `ModelPart`，检查父子/单独部件顶点、镜像、膨胀与旋转、矩阵恢复、UV/颜色/光照/overlay；另检查全部光照等级组合及 ABGR 转换的 alpha 保留。像素颜色检查不包含完整线路图或 GPU 上传。

渲染检查使用实际 `SubmitNodeStorage`，在提取上下文关闭后重放几何与文本提交节点；检查 268 个顶点、数组扩容、多材质批次、即时批次独立刷新、位置/法线变换、矩阵恢复、文本 Unicode/字体/透明度以及上下文嵌套、异常清理和线程隔离。船/矿车实际输出 216/120 个顶点，并检查双桨更新顺序、列车间动画隔离、停止与删除/清缓存重置。材质检查验证实际管线的剔除、输出目标、混合和深度写入。

客户端模型检查覆盖逐面几何/材质传递、重载缓存、未注册方块不受影响、`pos` 条件，以及全部 270 个物品定义通过实际游戏 `ClientItem.CODEC` 解析。资源引用检查从 270 个注册物品和 181 个方块状态出发，检查 858 个模型引用和 365 个实际纹理，沿父模型和纹理槽解析，拒绝缺失资源与引用循环；仍不等于 GPU 烘焙/显示验证。资源处理任务将过滤规则所在构建脚本声明为输入，避免修改转换规则后增量构建继续复用旧输出。

加载器钩子检查读取两个加载器配置的 16 个类引用，核对原版 26.2 字节码中的目标方法、字段、回调签名及相机提取顺序；它不是运行时 Mixin 应用测试。该检查需要两个加载器编译完成，十组兼容检查仍可独立运行。完整 `build` 会执行两类检查。

数据资源检查逐文件对比转换前后的结构，检查原版物品存在、MTR 注册声明、递归标签引用/循环/成员保留和最终数量。配方、战利品及方块状态谓词的结构使用实际 26.2 codec 检查；其中 MTR 物品引用和配方标签在测试副本中投影为原版物品，战利品的 MTR 方块引用也使用原版方块，原谓词另经独立 codec 检查。这仅验证结构，不证明这些谓词能绑定实际 MTR 方块状态，也不执行掉落。生产 JSON 不做这些测试投影。组合资源包的格式范围覆盖资源格式 88.0 与数据格式 107.1，并分别通过客户端/服务端元数据 codec 的范围检查；两种当前格式见[26.2 官方说明](https://www.minecraft.net/en-us/article/minecraft-java-edition-26-2)。

注册测试检查真实属性和生成的构造入口，不是完整加载器注册；实体检查不包含实际世界中的网络回调和乘客同步；模型和提交节点检查不包含 GPU 绘制。自定义几何的发光轮廓和破坏覆盖层尚需对接验证，密集列车帧耗时也未测量。旧世界转换、实际 GUI、列车渲染和联机仍需游戏测试。

09-14 已修复 26.2 继承的专用服 S2C 注册缺失：新增 `NetworkUtilities` 覆盖层，在生成的 `MTR.init` 首句传入物理环境并预注册 41 个 S2C 类型；列表从 `MTRClient` 的接收器声明生成，不在专服加载客户端类。仅 `Env.SERVER` 调用 payload-only API，客户端仍由原接收器入口登记，避免双注册。原通道 ID、`_s2c` 后缀和字节格式保留；登记早于 NeoForge 网络注册事件锁定，不在首次发送时惰性补齐。原始 1.21.1 网络代码没有被修改，因此本次修复不覆盖其同类问题。

网络回归先两次在真实 Architectury 21.0.7 `NetworkAggregator.collectPackets` 中复现 `codec is null`，修复后通过。测试执行生成的公共初始化前段，仅替换物理环境输入；测试专用 `NetworkTestAdaptor` 接管加载器登记边界，但保留实际 Aggregator、MTR payload codec、wire codec 和原版 packet 类。覆盖 41 个 S2C/26 个 C2S ID、服务端无客户端接收器、物理客户端不预登记、跨方向 ID 唯一、每个 S2C 通道的编码/线格式/接收器字节往返（含 Unicode 与 16 KiB 以上数据），并用清空 codec 的负对照确保测试仍能捕获原故障。它不执行完整 MTR 初始化、真实 Fabric/NeoForge 注册事件或网络连接，专服启动及真实加入游戏仍待验证。

完成条件是两个加载器完整编译、打包与启动，以及旧存档加载/保存、客户端 GUI、列车/铁轨/信号渲染和联机验证。仅 Wrapper 路由、独立类编译或元数据检查通过不等于完整移植完成。

工具链与渲染接口参考：[Fabric 26.2 开发说明](https://www.fabricmc.net/2026/06/15/262.html)、[NeoForge 26.2 迁移说明](https://docs.neoforged.net/primer/docs/26.2/)。配方 ingredient 转换依据[1.21.2 官方说明](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-2)。

## English

Only Minecraft **1.21.1** and **26.2** are targets. The repository wrappers accept `build -Version="1.21.1"` or `build -Version="26.2"`; omitting the version selects **26.2**. Select JDK 21 or JDK 25 using `JAVA_HOME` or `-JavaHome`. `-ShowTarget` displays routing without building.

The 1.21.1 Fabric and NeoForge builds pass. As of September 14, 2026, the separate 26.2 project compiles and packages both loaders, passes ten standalone compatibility checks plus a static hook check, and produces test JARs. Both artifacts contain 270 registered item definitions, 297 converted recipes, 176 loot tables and 32 merged tags, with no legacy data directories. Asset checks resolve 858 model references and 365 textures; all 84 selected-item conditions remain intact. Release copying requires successful checks, with failure preservation tested under `--continue`. Resource schema checks use vanilla-registry projections; they do not verify actual MTR registry binding or loot execution. Runtime validation remains unfinished, so these are not verified releases. Generated sources and version-specific overrides keep the existing 1.21.1 sources intact. CI reports 26.2 as an experimental port.

Change `mod_version` in the root `gradle.properties` for both targets. Successful release artifacts retain their Minecraft version in the file name and coexist in `build/release/`.

The inherited dedicated-server S2C registration defect is now fixed in the 26.2 overlay: startup registers all 41 client-declared S2C types on physical servers only. The existing client receiver path, IDs and wire format are preserved. The regression reproduced the actual Architectury codec-null failure twice before the fix; it now checks all 41 S2C / 26 C2S IDs, no duplicate client registration and S2C byte round-trips through actual codecs and receiver wrappers. A test adaptor replaces loader registration; no real server, loader event or connection is exercised. The corresponding 1.21.1 networking code remains unchanged.
