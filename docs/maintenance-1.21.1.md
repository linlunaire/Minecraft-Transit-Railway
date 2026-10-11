# Minecraft 1.21.1 maintenance

## Scope

The maintenance checkouts are `Minecraft-Transit-Railway-1.21.1` and
`mtr-ante-1.21.1`. Java 21, Gradle 8.14.5, Architectury Plugin 3.4.164,
Loom 1.11.458 and NeoForge 21.1.248 are retained. The Fabric target is
also compiled and packaged. The initial maintenance batch did not modify the
26.2 or Kotlin checkouts. The subsequent [door snapshot fix](door-snapshot-fix.md)
is shared with the Java 26.2 maintenance branch; Kotlin work remains separate.

## Confirmed fixes

- Legacy GUI rendering (3.3.9): the 1.21.1 dashboard called the native
  background twice per frame, with the second blur applied after the map and
  list text. The screen compatibility layer now retains explicit background
  ownership and suppresses the implicit background during widget rendering,
  including cleanup after a widget exception. World/model previews that omit
  a background remain unblurred. Dashboard, PIDS and railway-sign image buttons
  use their existing PNG files and normal/hover UV slices instead of treating
  texture filenames as GUI-atlas sprite identifiers. The equivalent 26.2
  fixes had not been included in the restored 1.21.1 release baseline.
- Addon block entities (3.3.5): FAWE's factory probe reproduced `Invalid block
  entity mtr:apg_glass` for JCM's inherited APG glass. The registered type now
  accepts subclasses that retain the same factory and ticker-type methods;
  unrelated blocks and overridden factories remain invalid. Minecraft method
  discovery uses its signature so Fabric intermediary names are supported.
  Existing block/entity IDs, positions and custom state properties are retained.
- Optional web map (3.3.5): start only with a loaded Dynmap, BlueMap or squaremap
  mod, or the corresponding enabled Bukkit plugin on hybrid servers. API JARs
  alone do not enable the feature. Missing providers produce one Chinese startup
  notice instead of per-dimension missing-integration messages. Port 0 disables
  the listener, malformed files remain unchanged, and bind/start failures release
  sockets and Jetty threads without choosing a different port.
- Real-time sync (3.3.5): remove plugin-overridable commands and the missing
  `/taw` dependency. The overworld uses native day-time and gamerule APIs; normal
  game time/scheduled ticks are untouched. Updates are limited to once per real
  second using the server's local timezone. The previous daylight rule is saved
  with MTR's data so disabling after a restart restores the original value.
  The existing `use_time_and_wind_sync` setting/NBT key is retained for compatibility.

- Dedicated-server networking (3.3.4): register all 41 server-to-client payload
  codecs during common initialization, before loader registration closes. The
  previous client-only registration left Architectury's S2C codec map empty on
  a dedicated server and crashed train synchronization with `codec is null`.
  Physical clients still register each codec with its receiver, avoiding duplicate
  registration in singleplayer. Packet IDs and wire formats remain unchanged.
- Doors: removed recursive world mutations during snapshot/NBT serialization.
  New doors use the entity renderer immediately; legacy doors migrate once on
  the server. Five door variants and all states have repeatable save/update,
  opening, collision and migration checks against native and NeoForge paths.
- Rail nodes: actual Minecraft fluid admission reproduced water replacing
  connected nodes. `forceSolidOn` prevents fluid replacement while retaining
  empty collision, selection and break hardness. The addon direct node inherits
  these properties; the same check can load its actual compiled class.
- Persistence: shorter MessagePack replacements left trailing bytes. Writes now
  encode completely before publishing a temporary file in the same directory;
  atomic replacement is used where the filesystem supports it. Failed encoding
  or publication also removes the previous good record from pending deletion.
- Save scheduling: 100,000 newly queued records grew to 1,100,000 after ten
  overlapping save requests. A save cycle now waits for pending writes as well
  as pending deletions; the next save still starts after completion.
- Build isolation: ANTE resolves the neighboring 1.21.1 checkout (or explicit
  `mtrProjectDir`) and rejects mismatched Minecraft versions/missing dev JARs.
  MTR setup uses local compatibility sources and canonical source resources,
  without network downloads or deleting source resource directories.

## Repeatable checks

Run the Gradle Wrapper with JDK 21 (on Windows use `gradlew.bat`):

```text
./gradlew build
./gradlew :common:checkSaveQueueCompatibility -Pbenchmark
./gradlew :common:checkRailNodeFluidCompatibility -PnodeAddonJar=<ANTE-common-dev.jar>
./gradlew :common:checkNetworkCompatibility
./gradlew :common:checkBlockEntityExtensionCompatibility -PblockEntityAddonJar=<JCM-common-dev.jar>
./gradlew :neoforge:checkBlockEntityExtensionNeoForge -PblockEntityAddonJar=<JCM-common-dev.jar>
./gradlew :common:checkWebserverCompatibility
./gradlew :common:checkRealTimeSyncCompatibility
./gradlew :neoforge:checkRealTimeSyncNeoForge
./gradlew :common:checkGuiCompatibility
./gradlew :common:checkPackagedGuiFabric :common:checkPackagedGuiNeoforge
./gradlew :common:checkGuiCompatibility "-PguiTestJar=<MTR-neoforge-release.jar>"
```

Build MTR first, then build ANTE. In ANTE:

```text
./gradlew build
./gradlew :fabric:checkRailCompatibility :neoforge:checkRailCompatibility
./gradlew :common:checkNetworkCompatibility
```

The save tests call the compiled save module, including failure injection,
FIFO ordering, stale-file deletion and full-save reset. The optional benchmark
measures queue bookkeeping only; it excludes disk access and game load.

The GUI check executes the actual dashboard render entrypoint, native 1.21.1
Screen lifecycle and image-button factories. Drawing boundaries are recorded
instead of requiring a GPU/window. It covers three consecutive frames,
explicit/custom/absent backgrounds, exception recovery, all nine dashboard
icons, PIDS/sign paging icons, actual PNG resources and constructor UV/hover
coordinates. Both shaded platform JARs are checked before publication;
`guiTestJar` can additionally check the exact NeoForge delivery JAR. This
regression is not a screenshot or a real modpack/multiplayer acceptance test.

For the 3.3.9 GUI release, the same headless check failed 59 assertions against
the original 3.3.8 NeoForge delivery JAR and passed all 88 against 3.3.9. An
additional disposable NeoForge client probe opened the real dashboard through
client-tick callbacks, rendered 115 GPU frames, and captured the native render
target without desktop input. Each frame blurred only before content; the
capture showed sharp text and no missing-texture magenta in the panel icons.
This isolated client did not load the Rapid modpack or connect to a server.

The APG regression runs real Minecraft construction, validation and NBT save/load
across all state variants, optionally loading the actual 1.21.1 JCM class. The
web map regression uses real loopback-only Jetty sockets and HTTP, with test-only
loader/plugin inventories; Bukkit absence is checked in an isolated classloader.
Those inventories are not bundled in any Mod JAR. Real-time checks exercise the
production setting/tick entrypoint, native time setter/gamerules, every second
of a 24-hour day, persistence/re-enable/disable, dimension guards and zero command
dispatch. These are headless regressions, not an actual Youer/FAWE game session.

The network checks reproduced Architectury 13.0.11's missing-codec exception
before the fix, including ANTE's own three S2C channels. They execute the actual
early startup statements and MTR payload factory, use real Architectury encoding
and vanilla wire codecs, and verify server/client inventories and byte round trips.
A test-only loader adaptor replaces event registration and packet transport;
this does not establish a real multiplayer connection. A missing-codec negative
control and client-side duplicate-registration checks guard against regression.
ANTE beta.4 requires MTR 3.3.4 or later for the shared registration helper.

The ANTE checks apply the actual Sponge Mixins to Rail/RailAngle/RailType and
execute new, MessagePack, NBT and packet constructors. Unlike the 26.2 failure,
the unmodified 1.21.1 rail initializer passed the vanilla/Fabric checks, so no
constructor patch is copied merely because it was needed on another version.
NeoForge's headless fixture supplies an empty loading-mod list for feature-flag
bootstrap; this is test setup, not a replacement for a real loader launch.

The initial maintenance run passed 563 fluid/collision/selection assertions with
the actual ANTE direct-node class. Both loader classpaths also reconstructed 138
connections from the existing `测试线路1` 1.21.1 save, read-only. No save or installed
game/server JAR was replaced.

Both projects export only checked artifacts to `build/release/`. These checks
do not prove game FPS/TPS, proxy connectivity, dashboard usability or save
compatibility in a running server. Runtime acceptance still needs the same save,
normal and ANTE trains, standing/riding views and a dense railway scene.

## Release directory maintenance

The normal `build` entrypoint publishes each loader only after its existing
source and packaged checks succeed. Publication checks the actual JAR's loader,
Mod ID and version, copies it to a temporary file in `build/release/`, verifies
the copied bytes, and promotes it before moving older releases to
`build/release/archive/previous-*/`. Atomic replacement is used where supported.
If a rebuilt version has different bytes, its previous JAR is backed up too;
identical rebuilds leave the current JAR unchanged.

Archiving matches only the current product variation, loader and Minecraft
1.21.1. NeoForge also archives the obsolete `forge` filename alias. Fabric,
NeoForge, ANTE, other Minecraft versions and other variations do not clean each
other's artifacts. Missing, empty or mismatched candidates preserve old releases.
Run the filesystem regression separately with `./gradlew checkReleaseArchive`.

For an isolated build, first complete its `build`/`check`, then import both
checked loader JARs through the original checkout instead of copying them by hand:

```text
./gradlew publishRelease "-PreleaseSourceDir=<isolated-checkout>/build/release"
```

The original checkout's configured version must match the isolated artifacts.
This entrypoint validates both candidates before publishing either and does not
compile unrelated working-tree changes. Metadata validation is not a substitute
for the isolated build's behavioral checks or a real client/server session.
Archives are recoverable build outputs, not permanent backups: `clean` removes
the build directory, so copy any history you need to retain elsewhere first.

[Youer's CI](https://github.com/MohistMC/Youer/blob/1.21.1/.github/workflows/gradle.yml)
uses an explicit final packaging task and uploads only its server artifact.
This project follows that separation of intermediate outputs and delivery;
the scoped archive implementation and its tests are local additions, not copied
from Youer's server build pipeline.

## Restored checkout note

The supplied MTR checkout already contained untracked platform sources,
templates and data resources before this work. They are preserved. Include the
required restored project files when committing; an incremental local build is
not evidence that the existing HEAD alone contains a complete clean checkout.

## 2026-10-11: rail persistence and YLTE search crash

Minecraft 1.21.1 calls `SavedData.save(File, HolderLookup.Provider)`. The old
`RailwayData.save(File)` overload was never called by `DimensionDataStorage`;
therefore the separate MTR rail save queue was not started by normal world saves.
The fix overrides the actual 1.21.1 entry point, preserves the incremental save
queue while the server is running, flushes it on shutdown, saves the NBT settings,
and keeps subsequent rail saves active even though rail edits do not mark the NBT
dirty flag. The MessagePack save format is unchanged.

The regression calls the real Minecraft data storage, verifies both directed
connections after reload, adds another connection for a second save, and tests
shutdown without another save tick. A direct-writer control passes on the old
implementation while the native storage call loses both connections. NeoForge
checks wait for `IOUtilities.waitUntilIOWorkerComplete()` before checking NBT or
deleting their temporary test directories. This does not change production I/O.

Checks:

```text
gradlew :common:checkRailPersistenceCompatibility :common:checkSaveQueueCompatibility
gradlew :fabric:checkPackagedRailPersistence :neoforge:checkPackagedRailPersistence
gradlew check
gradlew -p tests/rail-persistence-runtime runClient --offline
```

The runtime probe uses a disposable flat world, the actual YLM and YLTE NeoForge
JARs, and the real `SelectScreen` search `EditBox.charTyped` callback. It verifies
two world saves, re-reads rail data with YLTE's Mixins applied, and checks that a
final edit is saved by real integrated-server shutdown. NeoForge 21.1.252 and
Java 21 were used for this runtime check; Fabric has packaged/headless coverage.

The accompanying YLTE fix packages its existing JPinyin dependency and adds an
isolated final-JAR test for Chinese, full pinyin, initials and mixed names. Both
supplied client crash reports name the same absent relocated `PinyinFormat`
class. JPinyin 1.1.7's bundled Maven POM declares GPLv3; the local packaging
candidate is not a new MIT-only public release and its licensing needs to be
addressed before external distribution.

These changes fix a reproduced persistence defect. They do not establish why a
particular Youer multiplayer server appeared to lose connections after one real
day without a confirmed restart. That needs its server logs and affected save.
No live save, installed mod, remote repository or formal release directory was
modified. Existing connections already lost without a backup cannot be inferred
from the remaining node blocks alone.

The subsequent [1.21.1 health review](health-review-2026-10-11.md) records seven
additional legacy problem areas, formal-artifact negative controls, fixed
candidate checks and local-only delivery/compatibility limits.
