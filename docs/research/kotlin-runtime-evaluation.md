# Kotlin runtime evaluation for MTR

## 2026-09-24 补充：26.2、Java 25 与 80 人服务器

本次范围是 Minecraft 26.2 / NeoForge、MTR + ANTE，目标服务器为独占
8 个物理核心、64 GB 内存、Windows Server 2022。当前
[26.2 构建配置](../../build.gradle)使用 Java 25 toolchain 和
`release = 25`；[版本配置](../../gradle.properties)指定 26.2。
这些是源码和构建配置核验，不是 80 人运行测试。

### Kotlin 能提供什么

Kotlin/JVM 生成 Java 兼容字节码。由此推断，把现有循环翻译为 Kotlin，
不会自动减少玩家与轨道的交叉扫描、序列化字节或服务器主线程工作量。
增加 Kotlin 运行库也不会自动改写已有 Java 路径。官方说明支持的语言收益
主要是空值类型、智能类型转换、扩展函数和接收者 lambda，可用于更清楚地表达
配置验证和小型内部接口。[Kotlin FAQ](https://kotlinlang.org/docs/faq.html)

性能相关特性应逐项看待：

- `Sequence` 可以避免多阶段集合操作的中间结果，但惰性处理自身有开销，
  小集合和简单计算可能不合算；它不会把全图扫描变成空间查询。
  [Sequences](https://kotlinlang.org/docs/sequences.html)
- 协程能组织异步任务及其生命周期；具体工作仍由 dispatcher 分配到线程，
  切换 dispatcher 可能增加调度。共享列车、信号占用和乘客状态不能因为加上
  `launch` 就安全并行。要转移工作，仍需先划出不可变快照、后台计算和主线程
  提交的边界，并限制积压。
  [Coroutine context and dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html)
- `inline` 在适用场景可以消除高阶函数对象和调用开销，代价是代码体积增长；
  对外公开的 inline 实现还会进入调用方字节码，带来独立升级时的兼容约束。
  它不是对所有热点函数加修饰符即可加速的通用开关。
  [Inline functions](https://kotlinlang.org/docs/inline-functions.html)

本项目继续使用 Java 的实际优势是保留现有接口、构建流程和依赖关系。若团队
确实需要 Kotlin 的表达能力，可先在纯计算或配置模块内部试用；以相同输入的
耗时、分配量和维护成本决定是否保留，不能用代码行数减少推导 TPS 提升。

### 对附属模组和 Mixin 的边界

Java 可以调用 Kotlin，不等于逐类转换会保留原来的二进制形状。Kotlin 属性
通常生成访问方法与私有 backing field，顶层函数生成文件对应的静态方法；
`@JvmField`、`@JvmStatic` 和 `@JvmName` 可调整部分映射，仍需逐项检查。
[Calling Kotlin from Java](https://kotlinlang.org/docs/java-to-kotlin-interop.html)

这不是本仓库的抽象风险：相邻 ANTE 工程的
`common/src/main/java/cn/zbx1425/mtrsteamloco/mixin/TrainMixin.java` 直接
`@Shadow` 列车字段和方法；`RailwayDataAccessor.java` 访问 `rails` 并调用
`validateData()`；`RailwayDataMixin.java` 声明完整的同名 `simulateTrains()`。
这些源码证明附属模组依赖具体成员。语言迁移前要核对类名、字段名、描述符、
可见性、静态成员及注入目标，并进行 MTR 与 ANTE 的组合启动验证。优先保留
Java 对外边界，避免顺手转换这些目标类。

本次没有验证 26.2 可用的 Kotlin for Forge 或 Fabric Language Kotlin 发行版，
因此不指定安装版本，也不把下方 1.21.1 的历史兼容信息当成 26.2 支持声明。
若决定引入 Kotlin，再单独验证语言插件、运行库、Java 25 目标、NeoForge
加载和附属模组组合；同时维护 Fabric 时还需验证其独立加载路径。

### 历史候选的当前状态与空间索引边界

下方 2026-08-29 清单是历史快照，不能直接作为当前待办：

- [ClientData](../../common/src/main/java/mtr/client/ClientData.java) 已有
  `TRAINS_BY_ID`，`getTrainById()` 使用映射查询。
- [UpdateNearbyMovingObjects](../../common/src/main/java/mtr/data/UpdateNearbyMovingObjects.java)
  已采用顺序组包和同 tick 对象序列化缓存，没有原来的组包 `remove(0)`。
- [RailwayDataRouteFinderModule](../../common/src/main/java/mtr/data/RailwayDataRouteFinderModule.java)
  已通过 `tempDataDuration` 维护累计时长。
- 轨道兴趣查询仍值得优化；客户端渲染候选需另行核验，不能作为服务器
  80 人 TPS 的改善证据。

本批暂不实现跨 tick 轨道空间索引。MTR 的
[RailwayData](../../common/src/main/java/mtr/data/RailwayData.java) 和 ANTE 的
`RailwayDataMixin` 都有附近轨道查询，后者使用 `RailExtraSupplier.isBetween()`。
ANTE 的 `BezierCurve` 通过缓存 `min/max` 判断范围；当前缓存从原点开始，
`mapping()` 累积极值，`setEpsilon()` 会重新调用它。仅按轨道端点或新算的紧边界
建立索引，可能改变当前发送范围。直接将这些可能很大的缓存范围铺满网格，
也可能增加内存和重建开销。

后续最小安全设计是让两个查询调用共同的候选查询入口，保留各自原有精确
判断与数据包格式；索引使用保守边界，超大跨度进入受限的回退集合。失效点
必须覆盖加载、增删替换、校验清理，以及 ANTE 曲线编辑。当前 ANTE 网络编辑
通过 `PacketUpdateRail` 调用 `RailwayData.addRail()` 替换两方向对象，但 MTR
加载和校验也有直接修改 map 的路径；只在一个增轨入口置脏不够。

验证应将新查询结果与旧全扫描逐项比较，覆盖曲线越出端点范围、负坐标、
边界包含关系、长轨道、增删替换、加载、视距变化和编辑后查询。等这些条件
明确后再实现索引，不把少扫到轨道误报为性能收益。

80 人容量最终仍需同存档、同车次、同插件和 JVM 条件下测量服务器
MSPT P95/P99、分配/GC、网络排队及登录突发；客户端帧时间单独记录。
本次没有引入 Kotlin 依赖或进行语言迁移，也没有测得语言切换的性能增益。

## Historical snapshot: MTR 1.21.1

Date: 2026-08-29

The remaining sections preserve the original evaluation. Version claims and
optimization candidates below describe that date and target, not the current
26.2 workspace; use the dated addendum above for the latest source review.

## Question

Would adding Kotlin for Forge and Fabric Language Kotlin, then rewriting parts of
MTR in Kotlin, improve client frame pacing or reduce server tick latency?

## Findings

### What the two loader mods provide

- [Kotlin for Forge](https://modrinth.com/mod/kotlin-for-forge) is a language
  loader and shared library mod. Its own description says it provides Kotlin
  standard-library, reflection, serialization, coroutine and Forge/NeoForge
  integration facilities. It does not claim to optimize Minecraft or other mods.
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) is a
  Fabric language adapter and shared library mod. Its project page explicitly
  describes it as a compatibility dependency that adds no content, while its
  [official README](https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md)
  says it bundles Kotlin stdlib and common kotlinx libraries.
- Installing either mod without executing newly written Kotlin code therefore
  cannot reduce MTR's per-tick or per-frame workload. It adds a dependency and
  library discovery/loading work instead.

### 1.21.1 compatibility at the historical snapshot

- The current NeoForge-compatible 1.21.1 release is
  [Kotlin for Forge 5.12.0](https://modrinth.com/mod/ordsPcFz/version/uhJhCT7X),
  whose artifact is 7,279,264 bytes. The official
  [5.x changelog](https://github.com/thedarkcolour/KotlinForForge/blob/5.x/changelog.md)
  says 5.12.0 bundles Kotlin 2.4.0, coroutines 1.11.0 and serialization 1.11.0.
- The current Fabric-compatible 1.21.1 release is
  [Fabric Language Kotlin 1.13.13+kotlin.2.4.10](https://modrinth.com/mod/Ha28R6CL/version/bdhiINYC),
  whose artifact is 8,076,363 bytes. Its manifest requires Fabric Loader 0.16.9
  or newer, so MTR's configured Loader 0.19.3 satisfies that lower bound.
- The two platforms provide different Kotlin patch versions (2.4.0 and 2.4.10).
  Common Kotlin source must be compiled and tested against a deliberately aligned
  version; platform-specific runtime availability is not proof that Architectury
  Loom's transform/remap pipeline will work unchanged.
- MTR currently uses Java entrypoints. Kotlin-only language entrypoints are not
  required merely to call Kotlin helper classes, but each platform still needs a
  correctly declared runtime dependency or correctly bundled libraries.

### Runtime performance

- Kotlin/JVM code and Java code both become JVM bytecode. Rewriting an unchanged
  algorithm in Kotlin does not change its asymptotic cost.
- Kotlin can express allocation-free loops, but idiomatic collection pipelines
  can also allocate intermediate collections, iterators, lambdas or boxed values.
  The [Kotlin sequence documentation](https://kotlinlang.org/docs/sequences.html)
  explicitly warns that lazy sequences add overhead that can be significant for
  small collections or simple operations.
- Coroutines improve the structure and scalability of suspending work; they do
  not make CPU work cheaper. The
  [official coroutine dispatcher documentation](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html)
  explains that work still runs on a selected thread or thread pool and that
  changing contexts requires additional dispatches.
- Minecraft world mutation, train state, rail occupancy, riders and rendering
  are thread-affine. Moving these operations to a coroutine dispatcher without a
  redesigned immutable snapshot/commit seam would add races and scheduling jitter.
- Kotlin could be useful for off-thread pure computation or blocking I/O, but
  equivalent execution models are already available from Java 21 executors and
  `CompletableFuture`. The performance gain would come from moving safe work off
  the main thread or improving its algorithm, not from Kotlin syntax.

### MTR-specific optimization candidates at the historical snapshot

The current source contains higher-leverage Java optimizations that do not need a
new language runtime:

1. `RailwayData.simulateTrains()` scans all rails separately for each moved
   player. A chunk-based spatial index could reduce this work.
2. `ClientData.getTrainById()` linearly scans the train set for every update. An
   ID map would avoid repeated O(number-of-trains) lookups.
3. `UpdateNearbyMovingObjects` repeatedly calls `ArrayList.remove(0)` while
   building packets, potentially turning packet assembly into quadratic copying.
4. `RailwayDataRouteFinderModule` repeatedly sums the same route prefix instead
   of maintaining an accumulated duration.
5. `RenderTrains` simulates every synchronized client train during the render
   pass; visibility/distance scheduling and simulation/render separation are more
   likely to improve frame pacing.

These are code-review candidates, not confirmed hotspots. A call-tree profile and
controlled before/after benchmark are required before attributing latency to them.

## Recommendation

Do not add Kotlin for the purpose of making MTR faster. Keep the performance work
in Java first and measure server mean/P95/P99 MSPT, allocation rate, client
P95/P99 frame time and long-frame counts in the same dense save.

If Kotlin is desired for maintainability, run a separate experiment in one pure,
non-rendering module behind a small Java-facing interface. Keep the experiment
only if both Fabric and NeoForge compile/remap/start successfully and the measured
runtime and allocation results are no worse. Avoid converting train simulation,
rail synchronization or rendering until a profile demonstrates a specific gain.
