# MTR / ANTE 26.2 Kotlin architecture evaluation

Date: 2026-09-25

Historical first-stage evaluation: the user subsequently requested a standalone
ecosystem prerequisite and an all-or-overwhelmingly-Kotlin production rewrite.
The scope and embedded `:core` proposal below are superseded by
[the current architecture](../architecture.md) and
[migration plan](../kotlin-migration.md); they are not a reason to retain
Minecraft-facing code in Java permanently.

## Decision

Kotlin can be introduced safely as an implementation language for a small,
Minecraft-independent `:core` module. It is not, by itself, a performance
optimization. The first migration should keep Java entrypoints, Minecraft-facing
adapters, render code and Mixins in Java, and move only bounded policies or pure
algorithms behind small Java-callable interfaces.

The minimum-risk runtime arrangement for the current repository is:

- compile Kotlin with Kotlin Gradle Plugin (KGP) `2.4.20`, JVM target 25;
- compile `:core` against Kotlin stdlib `2.4.0`, the lowest runtime supplied by
  the two target loaders;
- use Fabric Language Kotlin (FLK) `1.13.14+kotlin.2.4.20` while the project
  remains on Fabric Loader `0.19.3`;
- use Kotlin for Forge (KFF) `6.3.0` on NeoForge 26.2;
- do not Shadow, relocate or JarJar another copy of `kotlin.*` into MTR or ANTE;
- do not switch MTR's Java entrypoint to a Kotlin language entrypoint merely
  because some internal classes are Kotlin.

Any lower server latency must be attributed to the migrated algorithm -- for
example a bounded scheduler, a cache with a lower miss/rebuild rate, or less work
per tick -- and demonstrated by before/after profiles. Shorter Kotlin source is
not evidence of lower MSPT.

## Verified toolchain and runtime matrix

| Concern | Verified result | Consequence |
| --- | --- | --- |
| KGP and Gradle | Kotlin's [KGP compatibility table](https://kotlinlang.org/docs/gradle-configure-project.html) lists KGP 2.4.20 as fully compatible with Gradle 7.6.3 through 9.7.0. | Gradle 9.5.1 is inside the supported range. Use KGP 2.4.20 rather than 2.4.0, whose fully supported range ends at Gradle 9.5.0. |
| Java/JVM target | The [Kotlin JVM target API](https://kotlinlang.org/api/kotlin-gradle-plugin/kotlin-gradle-plugin-api/org.jetbrains.kotlin.gradle.dsl/-kotlin-jvm-compiler-options/jvm-target.html) lists targets 1.8 and 9 through 26. Kotlin 2.4.0 also added [Java 26 bytecode support](https://kotlinlang.org/docs/whatsnew24.html#support-for-java-26). | JDK 25 and `jvmTarget=25` are supported. Kotlin and Java compile tasks must both target 25; Gradle 8+ treats a mismatch as an error by default. |
| KFF for 26.2 | Modrinth's exact [KFF 6.3.0 release](https://modrinth.com/mod/ordsPcFz/version/WSKgpZoB) declares NeoForge and Minecraft 26.2. The official source publishes the supported list including 26.2 in [`build.gradle.kts`](https://github.com/thedarkcolour/KotlinForForge/blob/6.x/build.gradle.kts). | Use `thedarkcolour:kotlinforforge-neoforge:6.3.0` from `https://thedarkcolour.github.io/KotlinForForge/`. |
| KFF's real Kotlin runtime | The official [6.3.0 POM](https://thedarkcolour.github.io/KotlinForForge/thedarkcolour/kotlinforforge-neoforge/6.3.0/kotlinforforge-neoforge-6.3.0.pom) depends on Kotlin stdlib/reflection 2.4.0. Inspection of the Modrinth all-JAR (SHA-256 `263D24B212A067C3D0916124BC262DE41A8013751F623645B196C69E12A6F6B1`) found the same 2.4.0 libraries under `META-INF/jarjar`. | Do not assume that KFF 6.3.0 contains Kotlin 2.4.10 or 2.4.20. Compile the shared core against stdlib 2.4.0 even when KGP itself is 2.4.20. |
| FLK latest release | The official [FLK README](https://github.com/FabricMC/fabric-language-kotlin) currently names `1.14.1+kotlin.2.4.20` and lists its bundled libraries. Its actual `fabric.mod.json` requires Fabric Loader `>=0.19.5`. | It is not directly usable with this repository's Loader 0.19.3 unless that loader is also upgraded and retested. |
| FLK without a loader upgrade | Modrinth's exact [FLK 1.13.14 release](https://modrinth.com/mod/Ha28R6CL/version/KcCe0rZz) supports 26.2 and bundles Kotlin 2.4.20. Its actual `fabric.mod.json` requires Loader `>=0.16.9`. | `1.13.14+kotlin.2.4.20` is the smallest compatible choice for Loader 0.19.3. It aligns the Fabric runtime with KGP 2.4.20 while remaining newer than the shared core's 2.4.0 API floor. |

The repository's own configuration was also checked: it currently uses Gradle
9.5.1, JDK/release 25, Fabric Loader 0.19.3, NeoForge 26.2.0.75 and Minecraft
26.2. Those local values are not inferred from the external project pages.

### Why KGP 2.4.20 plus stdlib 2.4.0 is deliberate

KGP and stdlib do not have to use the same patch artifact. Kotlin's Gradle
documentation says that KGP normally adds a same-version stdlib, but an explicit
stdlib dependency or `coreLibrariesVersion` overrides that default. The compiler
documentation also explains that the API version sets the minimum required
stdlib version, and recommends an older API surface when consumers supply an
older runtime. See [stdlib dependency configuration](https://kotlinlang.org/docs/gradle-configure-project.html#dependency-on-the-standard-library)
and [`-api-version`](https://kotlinlang.org/docs/compiler-reference.html#api-version-version).

For this project, use the 2.4.20 compiler because it supports Gradle 9.5.1, but
put stdlib 2.4.0 on `:core`'s compile classpath. This catches accidental calls to
stdlib functions absent from KFF's runtime. `apiVersion=2.4` alone is not a
patch-level check, so the actual compile classpath is the important guard.

The intended invariants are:

```text
KGP:                    2.4.20
Java toolchain/target:  25
Kotlin JVM target:      25
:core compile stdlib:   2.4.0
NeoForge runtime:       KFF 6.3.0 -> stdlib 2.4.0
Fabric runtime:         FLK 1.13.14 -> stdlib 2.4.20
```

This still needs a clean Gradle compile/remap and both loader startup tests;
version metadata cannot prove that Architectury Loom's transformations and the
project's custom Shadow pipeline are correct.

## External runtime mod versus embedding stdlib

### Recommended: loader-specific runtime mods

FLK describes itself as a Fabric Kotlin language module that bundles stdlib and
common kotlinx libraries. KFF likewise provides stdlib, reflection, serialization,
coroutines and its NeoForge language integration. KFF 6.x's own build uses
NeoForge JarJar for those libraries; its source sets label the language component
`LIBRARY` and the Kotlin utility component `GAMELIBRARY`. See the
[FLK README](https://github.com/FabricMC/fabric-language-kotlin/blob/master/README.md)
and [KFF build](https://github.com/thedarkcolour/KotlinForForge/blob/6.x/build.gradle.kts).

Let those projects own runtime placement and loader integration:

- Fabric development/runtime dependency:
  `net.fabricmc:fabric-language-kotlin:1.13.14+kotlin.2.4.20`;
- NeoForge Maven repository:
  `https://thedarkcolour.github.io/KotlinForForge/`;
- NeoForge development/runtime dependency:
  `thedarkcolour:kotlinforforge-neoforge:6.3.0`.

MTR and ANTE should both declare their actual required mod explicitly so a
missing runtime produces an early loader error rather than a later
`NoClassDefFoundError`.

For Fabric metadata while Loader remains 0.19.3:

```json
"fabric-language-kotlin": ">=1.13.14+kotlin.2.4.20"
```

For NeoForge metadata:

```toml
[[dependencies.mtr]]
modId = "kotlinforforge"
type = "required"
versionRange = "[6.3.0,7)"
ordering = "NONE"
side = "BOTH"
```

ANTE should use the same runtime range under its own dependency table.

### Keep the Java entrypoint

KFF's README tells a fully Kotlin `@Mod object` project to set
`modLoader="kotlinforforge"`, and FLK documents its `kotlin` entrypoint adapter.
Neither step is required to call ordinary Kotlin/JVM classes from an existing
Java entrypoint once the runtime libraries are present. MTR should keep its
current Java loader boundary and add a required dependency on the library mod.

This avoids changing construction order and binary entrypoint shape while the
experiment is still small. It also keeps Mixins and their target descriptors in
Java, where MTR and ANTE already depend on exact fields, methods and class names.

### Why not embed another stdlib copy

Embedding the runtime in both MTR and ANTE would create two ownership problems:

1. both mods could contribute classes under the same `kotlin.*` and
   `kotlinx.*` packages, in addition to FLK/KFF;
2. NeoForge loads libraries through module/language layers, so equal binary names
   do not imply equal class identity when different class loaders define them.

This is not merely theoretical. An issue in KFF's official repository records a
[`LinkageError` involving `kotlin.jvm.functions.Function0` loaded from a different
layer](https://github.com/thedarkcolour/KotlinForForge/issues/94). It does not
prove every duplicate will fail, but it is sufficient reason not to create the
same arrangement deliberately.

Relocating the whole Kotlin runtime is also not the minimum safe solution: it
expands the artifact, couples generated bytecode and metadata rewriting to the
Shadow configuration, and still has to handle reflection, serialization and
service metadata. There is no benefit for the first pure-core experiment.

The `:core` output should therefore contribute only MTR/ANTE classes to the
platform artifact. Its Gradle stdlib dependency is a compile dependency, not a
request to Shadow stdlib bytecode into the release JAR.

## Recommended module seam

```text
Minecraft / loader / Mixin code (Java)
                |
                v
small Java-facing input and result types
                |
                v
:core (Kotlin/JVM 25, pure deterministic policies and algorithms)
                |
                v
Java applies decisions to Minecraft state on the owning thread
```

` :core` should not import Minecraft, NeoForge, Fabric, Architectury, GraalJS,
LWJGL, Mixin or loader event classes. This gives it fast unit tests and prevents
the language experiment from becoming a second platform abstraction layer.

Good first candidates are the two already selected by the implementation work:

- an MTR cache policy whose inputs are stable keys/capacity/access events and
  whose outputs are hit/miss/eviction decisions;
- an ANTE bounded scheduler that owns admission and the lifecycle of background
  builds, pending results and owner-thread uploads. Worker completion order is
  asynchronous; it is not a deterministic per-tick execution order.

The language contribution here is safer expression of immutable state, null
handling and closed result types. The performance contribution, if measured, is
the cache/scheduling policy. Preserve this distinction in commits and profiling.

Do not initially migrate:

- Java entrypoints or loader registration;
- Mixins, accessors or classes shadowed by ANTE;
- render callbacks and GL/LWJGL resource ownership;
- world mutation, rail occupancy or train state ownership;
- network codecs or serialized persistent schemas;
- API classes consumed directly by third-party Java mods.

For Java-callable Kotlin APIs, prefer ordinary public classes/interfaces and
explicit methods. Avoid exposing `Sequence`, `Flow`, `suspend` functions,
Kotlin function types, default parameters or unsigned/value-class representations
across the boundary. Those types either leak a Kotlin-specific calling convention
or make allocation and binary shape less obvious to Java callers.

## Hot-path rules

Kotlin's own documentation warns that lazy
[`Sequence`](https://kotlinlang.org/docs/sequences.html) processing adds overhead
that can be significant for small collections or simple computations. The
coroutine documentation shows that work still executes on a selected thread or
pool and that context changes dispatch it to another thread; see
[coroutine dispatchers](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html).

For the first core migration:

- use explicit indexed loops and primitive arrays in measured hot paths;
- avoid chained `map` / `filter` pipelines and `Sequence` in per-frame/per-tick
  code until allocation profiles justify them;
- do not add coroutines, `Flow`, channels or dependency injection;
- keep thread ownership explicit; a scheduler decision can be pure, but applying
  it to Minecraft state remains on the owning game thread;
- bound all queues and per-tick work, and define overflow/backpressure behavior;
- avoid temporary `Pair`, `Triple` and data-class objects inside tight loops;
- use `inline` only for a demonstrated high-order call allocation, not as a
  blanket annotation. Kotlin notes that inlining can remove function-object and
  virtual-call overhead but grows generated code; see
  [inline functions](https://kotlinlang.org/docs/inline-functions.html).

Java 25 can execute both MTR's target-25 classes and KFF's older target-21
classes. That compatibility does not make target-21 KFF code a performance
bottleneck; it is a runtime provider, not the train simulation.

## Primary-source architecture references

Only three external projects are used here. Their build versions should not be
copied; the useful part is the boundary each project chooses.

### 1. YetAnotherConfigLib: Java API, optional Kotlin DSL

YACL's current settings generate separate Fabric and NeoForge targets for 26.2
from one central build script. Its main implementation is predominantly Java,
while a small Kotlin DSL delegates to the Java API:

- [26.2 Fabric/NeoForge target matrix](https://github.com/isXander/YetAnotherConfigLib/blob/main/settings.gradle.kts);
- [Java-facing `YetAnotherConfigLib` builder API](https://github.com/isXander/YetAnotherConfigLib/blob/main/src/main/java/dev/isxander/yacl3/api/YetAnotherConfigLib.java);
- [Kotlin DSL layered on the Java API](https://github.com/isXander/YetAnotherConfigLib/blob/main/src/main/kotlin/dev/isxander/yacl3/dsl/API.kt).

Lesson for MTR: Kotlin does not have to replace the stable Java surface. A narrow
Kotlin layer can add a clearer internal expression while Java remains the ABI and
loader boundary.

Do not copy YACL's runtime section blindly: its current NeoForge KFF dependency
line is commented out in
[`build.gradle.kts`](https://github.com/isXander/YetAnotherConfigLib/blob/main/build.gradle.kts).
It is evidence for a hybrid source layout, not proof of MTR's required runtime
packaging.

### 2. Lithium: performance comes from data and work selection

Lithium is implemented in Java, yet its optimization modules reduce work by
changing data access and algorithms. For example, its item-merging mixin uses a
specialized item list after a threshold and iterates only accessible non-empty
sections, with a vanilla fallback:

- [item-merging implementation](https://github.com/CaffeineMC/lithium/blob/develop/common/src/main/java/net/caffeinemc/mods/lithium/mixin/experimental/entity/item_entity_merging/ItemEntityMixin.java);
- [documented optimization modules](https://github.com/CaffeineMC/lithium/blob/develop/lithium-fabric-mixin-config.md).

Lesson for MTR/ANTE: a cache index, bounded candidate set, threshold and fallback
can lower work; changing the syntax from Java to Kotlin cannot. Retain a fallback
or equivalence test whenever a new cache changes which trains/rails/jobs are
considered.

### 3. Valkyrien Skies 2: separate engine core from loader adapters

The VS2 1.20.1 branch is not a 26.2 version reference. Architecturally, its
settings separate `common`, `fabric` and `forge`, and optionally substitute
independent `vs-core` API/util/implementation modules through an included build.
Its build applies both Java and Kotlin compilation and explicitly aligns their
JVM target:

- [module and included-core layout](https://github.com/ValkyrienSkies/Valkyrien-Skies-2/blob/1.20.1/main/settings.gradle);
- [mixed Java/Kotlin build](https://github.com/ValkyrienSkies/Valkyrien-Skies-2/blob/1.20.1/main/build.gradle).

Lesson for MTR: the reusable algorithm core should be independent of loader
adapters. Unlike VS2, the first MTR/ANTE core should remain much smaller and avoid
turning into an independent engine before the two policies prove useful.

## Validation gates

The migration is acceptable only when all gates below pass.

### Build and packaging

1. Clean Gradle 9.5.1 build under JDK 25.
2. `compileKotlin` reports JVM target 25 and matches `compileJava` target/release
   25; do not suppress target validation.
3. Fabric and NeoForge remap/release tasks succeed.
4. Release JAR inspection finds MTR/ANTE `:core` classes but no embedded
   `kotlin/`, `kotlinx/` or second FLK/KFF runtime.
5. Dependency reports show the core compiling against stdlib 2.4.0 rather than a
   resolution-upgraded 2.4.20.

### Loader startup

1. Fabric Loader 0.19.3 starts with FLK 1.13.14 and the platform artifact.
2. NeoForge 26.2.0.75 starts with KFF 6.3.0 and the platform artifact.
3. Dedicated server and client both start.
4. Removing the Kotlin runtime mod produces an immediate, clear required-mod
   error on both loaders.
5. MTR + ANTE start together; no duplicate-package, module-layer,
   `NoClassDefFoundError`, `NoSuchMethodError` or Mixin descriptor error appears.

### Correctness

1. Pure-core tests compare the new cache/scheduler results with the old Java
   behavior on fixed fixtures and randomized operation sequences.
2. Cache invalidation covers load, add, replace, remove, validation cleanup and
   ANTE edit paths relevant to the selected policy.
3. Scheduler tests cover empty, below-budget, exact-budget, over-budget,
   cancellation and starvation/fairness cases.
4. Java-facing signatures are checked with `javap` where Mixins or external mods
   depend on exact descriptors.

### Performance

Use the same save, trains, schedules, players/bots, JVM flags and observation
window before and after. Record at minimum:

- server MSPT mean, P95 and P99;
- worst-tick and long-tick counts;
- allocation rate and GC pause/time;
- cache hit/miss/eviction and rebuild counts;
- scheduler queue depth, admitted/deferred/dropped work and time spent;
- client P95/P99 frame time separately from server results.

A build success demonstrates compatibility, not latency improvement. A
microbenchmark demonstrates the isolated policy, not whole-server capacity. Keep
the Kotlin migration only when the combined runtime measurement is no worse and
the algorithmic change has an explainable benefit.

## Final recommendation

Proceed with the small pure `:core` modules and external loader runtimes. For the
current MTR checkout, choose FLK 1.13.14 on Fabric Loader 0.19.3 and KFF 6.3.0 on
NeoForge; use KGP 2.4.20 but compile the shared core against stdlib 2.4.0. Preserve
Java entrypoints/Mixins and do not introduce coroutine/Flow/DI infrastructure.

This arrangement makes the Kotlin experiment reversible and testable. It can
improve server latency only through the cache and bounded-scheduling algorithms;
the language and loader mods are enabling infrastructure, not optimizers.
