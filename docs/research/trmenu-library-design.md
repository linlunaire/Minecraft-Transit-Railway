# TrMenu 与 Kotlin 基础库设计核查

核查日期：2026-09-25。范围：源码架构、运行库装载和许可；未运行 TrMenu，不能据其 README 推导 MTR 的 FPS 或 80 人服务器容量。

后续决定：用户已明确要求独立、公开且面向未来 Mod 生态的 Transit Core，并将 MTR / ANTE 全部或绝大部分生产源码迁入 Kotlin。[当前架构](../architecture.md)与[迁移计划](../kotlin-migration.md)优先于本文的阶段性范围建议；下文外部项目与运行库核查仍作为设计依据保留。

## 结论

可以借鉴 TrMenu 的**业务 API、公共实现与平台适配分层**，自己实现 MTR / ANTE 的基础库；不建议另造一个与 Fabric Language Kotlin（FLK）或 Kotlin for Forge（KFF）竞争的 Kotlin 运行库。两件事应分开：

- **业务基础库**拥有缓存、任务生命周期、资源释放、观测和通用算法，Kotlin 只是实现语言。
- **语言前置 mod**拥有标准库、语言入口和加载器整合，继续使用 FLK / KFF。

以上是设计建议，不是现有依赖必须新增的结论。只有真实共享契约值得抽取；目前 MTR 的帧缓存与 ANTE 的轨道调度器各有不同消费者，把两个类搬进同一个新 mod 本身不会减少计算。先形成可测试 JVM 库；出现两端确实共享的生命周期或服务后，再决定是否需要独立安装的前置 mod。

## TrMenu 实际做了什么

核查用户指定 fork 的 `stable/v3`，固定提交为 [`8102d8befba6b8ba629577d1125c6b3eaeba9425`](https://github.com/CoderKuo/TrMenu/tree/8102d8befba6b8ba629577d1125c6b3eaeba9425)。

1. `settings.gradle.kts` 声明 `common`、`plugin`、`api:action`、`api:receptacle`，还列有 `module:database` / `module:migrate`；核查的源码树没有后两者的实现，不能仅凭声明说它们已形成完整模块。[设置文件](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/settings.gradle.kts)
2. `plugin` 通过 `taboo(project(...))` 打包公共模块和两个 API 模块。它还依赖 Bukkit/NMS、PlaceholderAPI 等插件 API，不是可直接运行于 Fabric / NeoForge 的通用引擎。[插件构建](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/plugin/build.gradle.kts)
3. 根构建使用 Kotlin `2.1.0`、JVM `1.8`、TabooLib 插件 `2.0.30` 和 TabooLib `6.3.0-4bf7820`，以 `compileOnly(kotlin("stdlib"))` 编译，并明确关闭 TabooLib 的协程版本配置。这些是它服务旧 Bukkit 版本的选择，不应覆盖 MTR 的 JDK 25 / MC 26.2 工具链。[根构建](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/build.gradle.kts)
4. `ActionHandle` 从外部接收条件与占位符解析器，`TrMenu.onLoad` 负责装配具体实现；这是值得借鉴的依赖边界。但该 API 仍引用 TabooLib 的 `ProxyPlayer` 和调度接口，不是纯 JVM 领域核心。[ActionHandle](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/api/action/src/main/kotlin/trplugins/menu/api/action/ActionHandle.kt)、[入口装配](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/plugin/src/main/kotlin/trplugins/menu/TrMenu.kt)
5. 不能把这个项目每一处实现都当作性能模板。例如 `TaskConcurrent` 创建 fixed thread pool，为全部任务提交 future，再逐个 `get()`；这个类没有展示容量上限或 executor 的关闭方法。MTR / ANTE 已有明确背压、失效结果丢弃和资源关闭的需求，不能退回这类批量提交模式。[任务工具源码](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/common/src/main/kotlin/trplugins/menu/util/concurrent/TaskConcurrent.kt)

## TabooLib 的装载方式不能照搬

TrMenu 使用的 TabooLib 版本对应 [`4bf78200a6ad081d19403e5091cbbd2fed790121`](https://github.com/TabooLib/taboolib/tree/4bf78200a6ad081d19403e5091cbbd2fed790121)。其 `RuntimeEnv` 会根据配置检查运行环境、动态装载 Kotlin，并在非隔离模式下选择重定位；还包含针对 Bukkit / Paper 插件类加载与协程首次初始化的专门处理。[RuntimeEnv](https://github.com/TabooLib/taboolib/blob/4bf78200a6ad081d19403e5091cbbd2fed790121/common-env/src/main/java/taboolib/common/env/RuntimeEnv.java)

因此 TrMenu 的“公共库”不等于简单打包 stdlib。照搬将同时引入依赖下载器、重定位规则、额外类加载边界与生命周期管理。对 MTR 的推论是：这些机制增加维护面，却没有直接减少列车仿真或轨道绘制工作。不要引入游戏启动时联网下载依赖、任意包扫描或另一个 Kotlin 类加载器。

## 26.2 应采用的语言前置

### Fabric

FLK 的工作是提供 Kotlin 入口适配器并打包 stdlib / kotlinx 库；构建中的 `includeAndExpose` 同时向消费者暴露并嵌套这些库。[固定源码构建](https://github.com/FabricMC/fabric-language-kotlin/blob/858a0d35937406a19e01c6bab3537550b4f972d9/build.gradle)、[入口与加载器元数据](https://github.com/FabricMC/fabric-language-kotlin/blob/858a0d35937406a19e01c6bab3537550b4f972d9/src/main/resources/fabric.mod.json)

本地 `gradle.properties` 当前保留 Loader `0.19.3`。继续使用 FLK `1.13.14+kotlin.2.4.20`：其发布 tag 固定到 [`15f7862f4114082e3c569cc03835f6d7ea1e09ac`](https://github.com/FabricMC/fabric-language-kotlin/tree/15f7862f4114082e3c569cc03835f6d7ea1e09ac)，元数据要求 Loader `>=0.16.9`；当前 `1.14.1` 源码要求 `>=0.19.5`，不能无验证替换。[1.13.14 元数据](https://github.com/FabricMC/fabric-language-kotlin/blob/15f7862f4114082e3c569cc03835f6d7ea1e09ac/src/main/resources/fabric.mod.json)、[当前版本配置](https://github.com/FabricMC/fabric-language-kotlin/blob/858a0d35937406a19e01c6bab3537550b4f972d9/gradle.properties)

### NeoForge

KFF `6.3.0` 固定源码为 [`9cd48346a4a6e30c0c75df049d4f6fa1b66cb975`](https://github.com/thedarkcolour/KotlinForForge/tree/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975)。它将语言加载器、游戏侧工具与 mod 元数据分开，使用 JarJar 组织 Kotlin 库；版本目录中的 Kotlin 为 `2.4.0`，MC 范围由属性与模板生成为 `[1.21.9,26.3)`，发布矩阵含 `26.2`。[构建](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/build.gradle.kts)、[依赖版本](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/gradle/libs.versions.toml)、[版本属性](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/gradle.properties)、[MC 依赖模板](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/src/kffmod/templates/META-INF/neoforge.mods.toml)

当前 6.x README 明示普通 Forge 支持尚未实现，发布配置也只启用 NeoForge；不能因为项目叫 Kotlin *for Forge* 就把普通 Forge 与 NeoForge 混同。[KFF README](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/README.md)

对本项目的建议：保持 Java mod 入口和 Mixin，只把业务类用 Kotlin 实现，显式依赖 KFF / FLK。自建库不内嵌第二份 `kotlin.*` / `kotlinx.*`，仍以两端较低的 stdlib `2.4.0` 为编译 API 下限；KGP 与运行时版本不是同一个概念。

## 自建基础库的最小边界

建议的依赖方向，不代表本报告已经实现：

```text
MTR 仿真 / ANTE 脚本与轨道业务
                 ↓
稳定 Java 可调用 API → Kotlin 实现的基础库
                 ↓
        显式平台适配 / 线程所有者

Fabric 产物 → FLK           NeoForge 产物 → KFF
```

- **先提取契约，再提取模块**：候选包括有界任务接纳、完成队列、资源关闭与失效代次；不把 Minecraft world、GPU 或 GraalJS 对象放入基础库。
- **业务策略留在业务侧**：轨道每帧上传预算、列车可见性、缓存权重与世界切换规则不能因为“通用”就被隐藏成全局静态配置。
- **没有独立生命周期就无需新 mod**：普通 JVM 模块即可测试复用；若确需跨 MTR / ANTE 的唯一服务实例，再发布基础 mod，并明确双加载器元数据及版本约束。
- **不做新的万能框架**：暂不增加注解扫描、自动 DI、反射注册、热路径 DSL、默认协程调度或启动下载器。现有 Java API / 脚本 ABI / 存档格式保持稳定。
- **性能靠减少工作**：验证任务容量与取消/关闭、缓存重建率、分配率以及真正场景的 P95/P99 帧时/MSPT。模块变少、Kotlin 占比提高或编译通过，不等于性能提高。

这些是基于本项目约束作出的设计判断，而非声称 TrMenu 已验证了 MTR 的具体方案。

## 许可边界

TrMenu 使用自定义限制许可，文本限制完整、部分及修改版本的再分发，并限制无授权修改用途。对计划公开的 MTR / ANTE，本次仅研究模块思想，不复制或翻译移植 TrMenu 代码；若要复用实现，应先取得明确授权。[TrMenu LICENSE](https://github.com/CoderKuo/TrMenu/blob/8102d8befba6b8ba629577d1125c6b3eaeba9425/LICENSE.md)

TabooLib 是 MIT、FLK 是 Apache-2.0、KFF 是 LGPL-2.1；它们不是同一套许可。引用源码时需分别履行对应许可，不能因“参考 Kotlin mod”而统一改成新库自己的许可。推荐依赖原版语言 mod，并独立实现自己的业务基础库，避免无必要的运行库 fork。[TabooLib LICENSE](https://github.com/TabooLib/taboolib/blob/4bf78200a6ad081d19403e5091cbbd2fed790121/LICENSE)、[FLK LICENSE](https://github.com/FabricMC/fabric-language-kotlin/blob/858a0d35937406a19e01c6bab3537550b4f972d9/LICENSE)、[KFF LICENSE](https://github.com/thedarkcolour/KotlinForForge/blob/9cd48346a4a6e30c0c75df049d4f6fa1b66cb975/LICENSE)

本节是工程复用风险提示，不替代法律意见。本报告未新增生产依赖、创建远端仓库或发布任何新前置 mod。
