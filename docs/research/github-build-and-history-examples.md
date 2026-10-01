# GitHub build and historical-input examples

核验日期：2026-10-01。以下结论来自实际读取的构建文件、测试和维护文档；源码链接固定到提交，便于复查。没有一个仓库同时覆盖 YanlingMTR 的双加载器、两条 Minecraft 版本线、Kotlin ABI、ANTE 脚本运行库和旧源码恢复需求。应按具体问题选择例子，并在本项目中验证。

## Architectury：共享代码经过平台转换再打包

官方模板将平台无关代码放在 `common`，Fabric 构建分别使用公共开发配置和 `transformProductionFabric`，然后将转换结果送入 Shadow / remap 任务。它提供了可核对的依赖方向和打包顺序。[common 配置](https://github.com/architectury/architectury-templates/blob/e10504cc3b12e5519aeda056dab1e65bbb5dcf07/templates/architectury/common/build.gradle)、[Fabric 配置](https://github.com/architectury/architectury-templates/blob/e10504cc3b12e5519aeda056dab1e65bbb5dcf07/templates/architectury_fabric/fabric/build.gradle)

YanlingMTR 的 [Fabric](../../fabric/build.gradle) / [NeoForge](../../neoforge/build.gradle) 已沿用公共模块与各平台生产转换的方向。模板的根配置仍使用 Loom `1.3-SNAPSHOT`，并带有旧 Forge 模板，不能直接拿它的插件版本、映射和任务名替换 26.2 配置。[模板根构建](https://github.com/architectury/architectury-templates/blob/e10504cc3b12e5519aeda056dab1e65bbb5dcf07/templates/architectury/build.gradle)

## Fabric Loom：组合构建与最终 JAR 一起验证

Loom 自己的组合构建夹具使用 `includeBuild` 和依赖替换。`CompositeBuildTest` 执行 `remapJar` 后，检查最终 JAR 确实含有外部构建和子项目的嵌套产物；`RemapJarContentsTest` 另行检查成品文件及 Manifest。可复用的是“运行生产任务，再检查生产输出”这一验证方法。[组合构建夹具](https://github.com/FabricMC/fabric-loom/blob/6056fe796865f1d88149e93c62ddf2158aa30782/src/test/resources/projects/compositeBuild/settings.gradle)、[组合产物测试](https://github.com/FabricMC/fabric-loom/blob/6056fe796865f1d88149e93c62ddf2158aa30782/src/test/groovy/net/fabricmc/loom/test/integration/CompositeBuildTest.groovy)、[成品内容测试](https://github.com/FabricMC/fabric-loom/blob/6056fe796865f1d88149e93c62ddf2158aa30782/src/test/groovy/net/fabricmc/loom/test/integration/RemapJarContentsTest.groovy)

本项目的 [统一任务入口](../../gradle/monorepo.gradle) 已连接 MTR 与 ANTE；[ANTE 构建](../../ante/build.gradle) 让附属编译依赖 `buildMtr`，并对成品运行 ABI、Mixin 和运行库检查。ANTE 当前单独输出 JAR；Loom 夹具中的嵌套打包只是另一种消费方式，不能据此改成合并二进制。Loom 的测试属于 Fabric 工具链，NeoForge 的验证仍由本项目独立完成。

## Folia：固定上游提交，补丁准备与构建分开

Folia 的 26.2 分支在 `gradle.properties` 用完整 SHA 固定 `paperRef`，构建直接读取该属性；CI 先执行 `applyAllPatches`，再执行 `build`。这使一次上游升级对应一个可审查的基线和补丁变化。[固定上游](https://github.com/PaperMC/Folia/blob/acf6733ddebcb4b1cd07360d11370c530b8125a2/gradle.properties)、[构建消费方式](https://github.com/PaperMC/Folia/blob/acf6733ddebcb4b1cd07360d11370c530b8125a2/build.gradle.kts)、[CI 顺序](https://github.com/PaperMC/Folia/blob/acf6733ddebcb4b1cd07360d11370c530b8125a2/.github/workflows/build.yml)

YanlingMTR 的 [CI](../../.github/workflows/build.yml) 已固定 Kotlin LunaCore 提交并先构建前置。历史恢复也使用固定存档提交与维护补丁。Folia 是服务端 paperweight 工程；可参考它管理上游输入的方式，无需迁入其构建插件或服务端线程架构。

## Node.js：必要第三方输入与许可证随源码保存

Node.js 将第三方组件保存在 `deps` 并随源码构建。Brotli 更新脚本把选定版本的 C 源码和原许可证一起放回主仓库；普通构建消费这些已保存文件，更新时才重新取上游。[依赖维护说明](https://github.com/nodejs/node/blob/9774069718d9f80578079d252dddf37ae6fd550d/doc/contributing/maintaining/maintaining-dependencies.md)、[Brotli 更新脚本](https://github.com/nodejs/node/blob/9774069718d9f80578079d252dddf37ae6fd550d/tools/dep_updaters/update-brotli.sh)、[保留的许可证](https://github.com/nodejs/node/blob/9774069718d9f80578079d252dddf37ae6fd550d/deps/brotli/LICENSE)

这支持将旧 Minecraft-Mappings 的必要输入、出处、许可证和恢复工具放入受维护仓库。Kotlin LunaCore 已保存固定提交的原始输入和 31 个文件的校验和，当前维护分支通过小型脚本导出并核验；旧标签按恢复说明准备后再构建。[本项目的恢复记录](mappings-retention.md)、[固定存档说明](https://github.com/linlunaire/Kotlin-LunaCore/blob/650800892192395a8755ef41efcfe90b913a3c4a/docs/legacy-mappings.md)

Node.js 的示例脚本调用校验助手时未传入预先保存的摘要，助手此时只输出下载文件的 SHA-256；因此该例不能证明严格的预期摘要验证。本项目仍使用自己的逐文件预期校验和。[校验助手实现](https://github.com/nodejs/node/blob/9774069718d9f80578079d252dddf37ae6fd550d/tools/dep_updaters/utils.sh)

## 本次实际应用：缩小 Maven 仓库的查询范围

本次 26.2 GraalVM CI 的首个依赖解析失败，是 NeoForge 镜像请求 `com.github.jonafanho:Minecraft-Mod-API-Tools` 的 POM 返回 502；随后无关的 `bcutil` 动态版本查询也受该仓库停用影响。修复按 Gradle 官方的仓库内容过滤方式处理：两处 NeoForge Maven 仓库只查询 `net.neoforged`、`net.minecraftforge`、`cpw.mods` 和 `de.oceanlabs.mcp` 范围，settings 的仓库声明同步限制。保留 `de.oceanlabs.mcp` 是为了现有 Loom 依赖的 `mcinjector:3.8.0` 工具，首轮本地解析已确认它仍需要该仓库。JitPack 的 `exclusiveContent` 只接管 API Tools 这个模块，使其他依赖继续从自己的仓库解析。[Gradle 官方说明](https://docs.gradle.org/current/userguide/filtering_repository_content.html)

官方区分了普通 `content` 过滤与 `exclusiveContent`：前者限制该仓库接受哪些请求，后者同时排除其他仓库查询指定依赖。这里采用模块级独占范围，避免把 API Tools 的所有传递依赖也交给 JitPack。实际修复是否完成，以本项目对应提交的 CI 结果为准；外部仓库里的例子不能替代该验证。
