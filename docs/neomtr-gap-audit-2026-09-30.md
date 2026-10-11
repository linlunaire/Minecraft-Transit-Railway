# NeoMTR 小范围修复差异核对（2026-09-30）

## 范围与证据

本批选择补齐三个已确认的源码缺口：电梯行驶时的出口标记、列车连接处的俯仰角、玩家断线后的乘坐状态清理。电梯模型缓存留作后续。此记录是本批范围的核对，不是对 NeoMTR 全部历史提交的穷尽审计，也不是当前服务器崩溃原因的认定。

- 目标是 `D:/Project/Minecraft-Transit-Railway-1.21.1` 的实际工作树，审计开始时 README/版本配置为 MTR 3.3.7，HEAD 为 `fbe6c8b1ea4ba7a6251981bb0b6d0e85dcabb02e`；已有 APG、网页地图、时间同步、网络通道等未提交改动。本表的“缺失”指实施本批补丁前的工作树，不只看 HEAD，也不代表发行 JAR 已验证。
- 主源核对至 [zbx1425/NeoMTR `8512866`](https://github.com/zbx1425/NeoMTR/commit/851286652df1c3d035aa9ef07747462731f42abc)，默认分支 `teacon2026`，提交时间为 2026-09-26（+08:00）。另核对 [DistrictOfJoban/NeoMTR `d218982`](https://github.com/DistrictOfJoban/NeoMTR/commit/d218982af9b33c8d8daad57a6c831f6ab3a5937a)，默认分支 `lx`，提交日期为 2026-04-14；“分叉”不等于其默认分支更新更晚。
- 阅读了主源最近 300 层、Joban 分叉最近 100 层历史中的相关提交记录，并对下列条目读取实际 diff 和当前源码。只使用作者仓库源码和本地源码，不把提交标题当作本地缺陷已经成立的证据。
- 旁查主 Kotlin 26.2 工作树 `D:/Project/Minecraft-Transit-Railway-3.x.x`，HEAD `8fb3362118f257e6834b489dd2056d5f2aba22f1`、分支 `codex/kotlin-26.2-preview`。下述三项在对应 Kotlin/Java 文件中也仍缺失；本批实施目标仍为 1.21.1。
- 调研没有运行 Gradle、启动游戏、操作世界或修改生产代码。上游只读副本位于 `build/diagnostics/neomtr-gap-source-20260930` 和 `build/diagnostics/neomtr-joban-gap-source-20260930`，没有变更目标仓库远程或分支。

以下本地行号取自补丁实施前快照；后续编辑可能移动行号。

## 本批选定的三个缺口

| 条目 | 1.21.1 初始状态 | 本地入口 | 上游依据 |
| --- | --- | --- | --- |
| 电梯行驶中仍可走出轿厢 | 缺失，已选本批 | `Lift.tick`；`LiftClient.tickClient` | [d16807e](https://github.com/zbx1425/NeoMTR/commit/d16807ed1963226b18c3e70a60fc72219d642603) |
| 列车连接处坡度使用错误长度 | 缺失，已选本批 | `TrainClient.simulateCar` 内连接处角度计算 | [d6a6bec](https://github.com/zbx1425/NeoMTR/commit/d6a6becf30938da0e98b308f676f9ac5d6d2a423) |
| 断线未立即移除乘坐冷却与线路记录 | 缺失，已选本批的小项 | `RailwayDataCoolDownModule.onPlayerDisconnect` | [33c4203](https://github.com/zbx1425/NeoMTR/commit/33c4203c41257419645a160c58fdca3099049d8b) |

### 1. 电梯行驶时清除出口标记

上游在关门后的移动分支结束时将 `frontCanOpen`、`backCanOpen` 置为 `false`。这两个值不仅控制模型开门，还传入乘客在轿厢内的移动边界；继续保留上一层的可开门标记，会让移动中的轿厢沿用出口允许范围。[上游实际 diff](https://github.com/zbx1425/NeoMTR/commit/d16807ed1963226b18c3e70a60fc72219d642603)

本地 [Lift.java](../common/src/main/java/mtr/data/Lift.java) 第 294—320 行在 `!doorOpen && doorValue == 0` 分支移动电梯后没有清除标记；第 330—332 行只在另一分支重新检查门。[LiftClient.java](../common/src/main/java/mtr/data/LiftClient.java) 第 34—36 行把标记传给 `setOffsets` 和 `moveSelf`，后者在 [VehicleRidingClient.java](../common/src/main/java/mtr/data/VehicleRidingClient.java) 中据此放宽横向移动范围，因果路径完整。

置信度高：逻辑和消费入口均确认缺失；未进行真实乘梯验证。回归应覆盖前门/双面门、上下行、闭门移动、到层重新开门，确认停站可正常进出。Kotlin 对应 `common/src/main/kotlin/mtr/data/Lift.kt` 第 228—258 行同样缺少清除。

### 2. 连接处俯仰角按连接点距离计算

上游把俯仰角分母从车厢的 `realSpacing` 改为两个连接端点之间的实际距离。原分母描述整节车的长度，与两个车端之间的连接件长度不一致，坡道上会得到偏小或不正确的连接角度。本批只采用这段几何修复；同一提交还调整了创造模式标签文本，与此无关。[上游实际 diff](https://github.com/zbx1425/NeoMTR/commit/d6a6becf30938da0e98b308f676f9ac5d6d2a423)

本地 [TrainClient.java](../common/src/main/java/mtr/data/TrainClient.java) 第 102—106 行先算 `prevPos0`、`thisPos0`，但仍用 `asin((thisPos0.y - prevPos0.y) / realSpacing)`；第 126、128 行把角度传给连接件和围挡渲染器。

置信度高：错误分母直接存在。移植须保留零长度保护，并处理非有限值/三角函数输入边界；上游原补丁本身没有零长度保护，不能逐行照搬。回归应检查平轨、上坡、下坡、重合端点和非有限输入。主 26.2 的 `common/src/main/java/mtr/data/TrainClient.java` 第 106 行仍有相同表达式。静态角度验证不能代替资源包连接模型的实际画面检查。

### 3. 玩家断线时清理两张乘坐 Map

上游在退出回调中额外移除 `playerRidingCoolDown` 与 `playerRidingRoute`。本地 [RailwayDataCoolDownModule.java](../common/src/main/java/mtr/data/RailwayDataCoolDownModule.java) 第 79—83 行仅移除座椅、座椅冷却和蹲下冷却，所以乘坐数据会在退出后继续存留到后续超时处理。退出事件确实经 [MTR.java](../common/src/main/java/mtr/MTR.java) 的退出回调和 `RailwayData.onPlayerDisconnect` 到达这里。[上游实际 diff](https://github.com/zbx1425/NeoMTR/commit/33c4203c41257419645a160c58fdca3099049d8b)

置信度高：遗漏清理已确认；这是小范围生命周期修补，不能据此宣称存在永久泄漏。现有 `tick()` 会在冷却归零后清理旧记录，故也不能把它描述为当前服务器必现崩溃。

同一上游提交还把超时回调移到遍历之外，并在上车前调用 `stopRiding()`。本地没有上游的 `TeleportDismountMixin`，且已经使用 `Iterator.remove()`；上游所修的重入 CME 没有在本地得到同等证明。本批只取退出时两个 `remove`，不连带更换座椅/Vivecraft 上车机制。Kotlin 对应 `common/src/main/kotlin/mtr/data/RailwayDataCoolDownModule.kt` 第 62—66 行也缺少这两个清理。

回归应覆盖断线即清理、重复退出无异常、其他玩家记录保留以及正常乘坐/冷却不受影响。

## 后续项：电梯自定义界面的模型缓存

状态为“部分已有修复，实时编辑入口待做”，不纳入本批。

上游 [a03731b](https://github.com/zbx1425/NeoMTR/commit/a03731b849b38c4189cab53e7f186d0248b353a2) 试图在宽度、深度、双面配置改变后使模型缓存失效。本地 [LiftClient.java](../common/src/main/java/mtr/data/LiftClient.java) 的 `copyFromLift` 第 94 行已经清空缓存，[ClientData.java](../common/src/main/java/mtr/client/ClientData.java) 第 192 行在收到更新时调用它，所以服务端同步这条入口已有覆盖。

但 [LiftCustomizationScreen.java](../common/src/main/java/mtr/screen/LiftCustomizationScreen.java) 第 51—73、102 行直接改动高度/宽度/深度/双面属性；第 175 行到关闭窗口时才发送设置。[LiftClient.getModel](../common/src/main/java/mtr/data/LiftClient.java) 第 60—64 行只检查缓存是否为空，因此实时编辑时存在继续使用旧模型的入口。主 Kotlin 的 `LiftClient.kt` 第 59—61、88 行保持同样行为。

置信度中高：缓存与直接修改路径已确认，实际窗口中的可见延迟尚未运行验证。上游补丁不可照抄：其 `shouldInvalidate` 在宽度/深度/双面属性“全部相等”时返回真，与调用处失效语义相反，且不检查高度；[当前上游 ModelLift1](https://github.com/zbx1425/NeoMTR/blob/851286652df1c3d035aa9ef07747462731f42abc/common/src/main/java/mtr/model/ModelLift1.java) 仍保留这一判断。后续应按本地模型构造参数核对失效条件，并验证不变参数继续复用模型。

## 已修复、实现不同或不适用的对照

| 上游提交 | 1.21.1 判断与本地证据 | 置信边界 |
| --- | --- | --- |
| [77d641d：静音后重新开始循环音效](https://github.com/zbx1425/NeoMTR/commit/77d641d5aa728bcc9e4d21cd18a7e1d93450b4cc) | 已移植；本地 HEAD 的音效提交及 [train-audio-restart.md](train-audio-restart.md) 已记录。 | 不重复列为缺口；已有检查的范围以该记录为准，本次未重跑。 |
| [6b89bc2：APG 灯光渲染层](https://github.com/zbx1425/NeoMTR/commit/6b89bc26094e9020de5276caa47aac21c5b3d448) | 同等条件已存在：[RenderPSDAPGDoor.java](../common/src/main/java/mtr/render/RenderPSDAPGDoor.java) 第 107 行按 `open > 0` 选择灯光层。 | 源码确认，不是新的游戏内视觉测试。 |
| [47b6086：电梯楼层轨道删除包 ID](https://github.com/zbx1425/NeoMTR/commit/47b6086d2b1eb134f97fcbabc52bc01ee18f53ca) | 已修：[IPacket.java](../common/src/main/java/mtr/packet/IPacket.java) 第 26 行使用独立的 `packet_remove_lift_floor_track`。 | 只核对该 ID 冲突，不替代整套网络测试。 |
| [9a0b252：PIDS tooltip 的空方块状态](https://github.com/zbx1425/NeoMTR/commit/9a0b25258a6bd1447000ed1955eb1f6252bec391) | 本地已避免同一空状态入口：[BlockPIDSBaseHorizontal.java](../common/src/main/java/mtr/block/BlockPIDSBaseHorizontal.java) 第 98 行、[BlockPIDSBaseVertical.java](../common/src/main/java/mtr/block/BlockPIDSBaseVertical.java) 第 53 行传 `defaultBlockState()`；上游当时直接禁用 tooltip 构造。 | 不是本批缺口；不据此保证所有附属 PIDS 类型都通过方块实体校验。 |
| [bbb9eb2：TrainServer MessagePack 字段数](https://github.com/zbx1425/NeoMTR/commit/bbb9eb2facd580fed38be75879eaa1a945213f2e) | 不适用：修复上游新增的 `departureIndex` 导致字段计数少一；本地 [TrainServer.java](../common/src/main/java/mtr/data/TrainServer.java) 没有该字段和附加序列化。 | 不能直接给本地字段总数加一。 |
| [151bd59：重新进世界复用 RailwayData](https://github.com/zbx1425/NeoMTR/commit/151bd590e0e0eb14e85087d05620391d8fa2c0fa) | 对应实现不适用：上游清空自建静态 `RailwayDataManager`；本地 [PersistentStateMapper.java](../common/src/main/java/mtr/mappings/PersistentStateMapper.java) 第 23—30 行通过当前 `ServerLevel.getDataStorage()` 获取 SavedData，没有该静态管理器。 | 仅排除这个上游缓存复用机制，不推导所有跨世界问题均已解决。 |
| [362a3c8：限速前视距离边界](https://github.com/zbx1425/NeoMTR/commit/362a3c88111ba29abf77c387ba9461fa8df9316d) | 不作为小修移植：本地 [Train.java](../common/src/main/java/mtr/data/Train.java) 没有其 `lookAheadDistance`/ATP 前视功能，上游还涉及 VirtualDrive。 | 属于先引入功能再修该功能的情形，超出本批。 |
| [Joban d218982：最新 crash 修复](https://github.com/DistrictOfJoban/NeoMTR/commit/d218982af9b33c8d8daad57a6c831f6ab3a5937a) | 不适用 MTR 核心：实际只修改合并进 NeoMTR 的 `com.lx862.mtrotp` 两个 mixin 注解；本地目标 MTR 没有该集成。 | 提交标题“fix crash”不意味着能修本地任意崩溃。 |

Joban 分叉近期大量提交针对内置 JCM/NTE/OTP 功能；这些在本地生态中是独立项目或未集成模块。没有把它们算成本地 MTR 核心的遗漏。大型渲染迁移、虚拟驾驶和新玩法也不在本批范围。

另一个待验证的专服边界是 [c186563：物品提示避免调用客户端类](https://github.com/zbx1425/NeoMTR/commit/c1865632f19a4571cb6f1455675869c74406a695)。本地 `ItemNodeModifierSelectableBlockBase.appendHoverText` 仍在可保存材料的分支访问 `Minecraft.getInstance().options.keyShift`，没有物理端保护；如果服务端插件/模组请求该 tooltip，可能触发客户端类访问。静态入口已核对，但当前实例是否存在触发者尚未证明，本批未改动或声称复现该问题。

## 实施与验证

三项已实施，版本递增为 **3.3.8**。只改动上述三个生产入口；连接角度额外保留零长度、非有限距离和反三角函数输入边界保护，没有合并同一上游提交的无关修改。

`tests/NeoMtrCompatibilityCheck.java` 使用实际 `Lift.tick`、退出回调和 `TrainClient.simulateCar`，不复制生产几何公式。测试专用 Java agent 只替换客户端获取、透明部件开关与可见性边界；音效/绘制由记录器接收，不启动游戏或 GPU。其余类保留原加载器，避免人工加载器引入不相关的包访问错误。测试、agent 和记录器不属于模组源码。

- 3.3.7 实际 NeoForge JAR 连续失败：移动电梯保留出口标记；退出后保留 `playerRidingCoolDown`；正常示例连接角应为 `0.463647609` 弧度却得到 `0.05002086`。
- 逐项修改后对应失败逐项消失；源码回归通过 **116 个断言**。覆盖上下行/单面与双面、重复退出/其他玩家数据保留、上下坡/平轨/垂直/转弯/重合连接点/非有限输入，并检查连接件与围挡输出一致。没有测试到层后的真实门方块行为。
- `gradlew :common:checkNeoMtrCompatibility --offline` 和导出的 `java @build/diagnostics/neomtr-java.args` 均已通过。
- 干净源码副本的 `gradlew build --offline --console=plain` 成功：43 个任务、1 分 37 秒。源码、Fabric/NeoForge 重映射前的打包代码分别通过 116 个断言；既有门快照、节点防水、存档队列、音效、网络编码及原生 NeoForge 通道检查也通过。
- 最终导出的 NeoForge **3.3.8 JAR** 再次通过 116 个断言和原生通道检查，最终 **3.3.7 JAR** 同时重现三项失败。两个最终 JAR 的元数据均为 `1.21.1-3.3.8`，均不含测试类。Fabric 最终重映射包完成构建与元数据检查，未在实际 Fabric 游戏中启动；不要把重映射前的逻辑回归当作实机运行。
- 产物基于已提交的 3.3.6 源码，加上 3.3.7 通道修复和本批补丁构建。源码副本在 `build/diagnostics/neomtr-clean-1.21.1-20260930/source`；没有把工作树中其他 APG/网页地图/时间同步维护改动混入产物。旧版 JAR 保留。

| 产物 | SHA-256 |
| --- | --- |
| `build/release/MTR-neoforge-1.21.1-3.3.8.jar` | `789E823902034E4927CA99E9C01CA2FAC9C0837C6519EE291ABCD4F5C7EA2AEF` |
| `build/release/MTR-fabric-1.21.1-3.3.8.jar` | `EAE8ABD3B7B61B6365A0768EB3A1C6808A426EF70D1EB2F219509A2E6A1E5B9B` |

这是无界面的逻辑回归，不是 Youer 联机、电梯实际乘坐或资源包/光影画面验收。Kotlin/Java 26.2 分支未改动；现有其他本地维护改动保留。没有推送，也没有更换真实游戏/服务器 JAR 或修改世界。
