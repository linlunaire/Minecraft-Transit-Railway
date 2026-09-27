# Minecraft 1.21.1 maintenance

## Scope

The maintenance checkouts are `Minecraft-Transit-Railway-1.21.1` and
`mtr-ante-1.21.1`. Java 21, Gradle 8.14.5, Architectury Plugin 3.4.164,
Loom 1.11.458 and NeoForge 21.1.248 are retained. The Fabric target is
also compiled and packaged. The initial maintenance batch did not modify the
26.2 or Kotlin checkouts. The subsequent [door snapshot fix](door-snapshot-fix.md)
is shared with the Java 26.2 maintenance branch; Kotlin work remains separate.

## Confirmed fixes

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
```

Build MTR first, then build ANTE. In ANTE:

```text
./gradlew build
./gradlew :fabric:checkRailCompatibility :neoforge:checkRailCompatibility
```

The save tests call the compiled save module, including failure injection,
FIFO ordering, stale-file deletion and full-save reset. The optional benchmark
measures queue bookkeeping only; it excludes disk access and game load.

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

## Restored checkout note

The supplied MTR checkout already contained untracked platform sources,
templates and data resources before this work. They are preserved. Include the
required restored project files when committing; an incremental local build is
not evidence that the existing HEAD alone contains a complete clean checkout.
