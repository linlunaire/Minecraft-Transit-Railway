# Native real-time sync — YanlingMTR 1.21.1 / 1.0.2

The 1.0.1 delivery JAR still called `/gamerule doDaylightCycle true`,
`/taw set-cycle-length`, `/taw reload` and `/time set`. Its isolated release
source had not incorporated the earlier native-sync change in the maintenance
checkout. The old release reproduces this through the actual setting method,
without starting plugins or requiring the optional Time & Wind mod.

1.0.2 adds that scoped change to the same source snapshot used for 1.0.1.
It does not include unrelated, unpublished maintenance changes. It directly
uses `ServerLevel.setDayTime` and the vanilla daylight gamerule, bypassing
the command dispatcher and any plugin aliases. Neither command permissions,
console-world naming nor a `/taw` registration are required.

The existing `use_time_and_wind_sync` packet/NBT setting is retained. Only the
Overworld is affected; its time follows the server's local timezone at most once
per second. Vanilla's automatic 20-minute day cycle is paused while syncing.
The previous daylight rule is stored in MTR's saved data and restored on disable,
including after restart. World game time and scheduled ticks are not changed.
If an administrator resumes the daylight gamerule while sync is enabled, MTR
stops updating time and does not reverse that change on disable. A plugin that
actively changes world time through its own scheduler is still a separate owner;
this repair removes command collisions, not all possible time-controller conflicts.

## Regression loop

Use the wrapper with JDK 21. The first command is an intentional failing control
against the archived 1.0.1 NeoForge JAR; change the path to its actual location.

```text
gradlew.bat :neoforge:checkRealTimeSyncNeoForge "-PrealTimeSyncTestJar=<old-1.0.1.jar>" -PrealTimeSyncBaseline
gradlew.bat :common:checkRealTimeSyncCompatibility :neoforge:checkRealTimeSyncNeoForge
gradlew.bat :common:checkPackagedRealTimeSyncFabric :common:checkPackagedRealTimeSyncNeoforge
gradlew.bat :neoforge:checkRealTimeSyncNeoForge "-PrealTimeSyncTestJar=<new-1.0.2.jar>"
```

The checks invoke the real production setting and tick paths. Actual Minecraft
day-time setters, game rules and NBT operate on an isolated level-data fixture;
the command dispatcher records calls without executing them. Assertions cover
zero dispatch, every second of a 24-hour cycle, throttling, save/reload/disable,
originally paused worlds, administrator changes and dimension guards. Selected
JAR checks verify the classes' actual origin so source output cannot mask missing
packaged code. Both shaded loader artifacts are checked during `build` before
publication. These headless tests are not a real CMI/Youer server certification.

## Verified on 2026-10-03

- The exact 1.0.1 NeoForge delivery JAR fails with all four intercepted commands.
- The scoped 1.0.2 snapshot passes the complete 53-task build, including existing
  GUI, door, sound, networking and persistence checks. The native time checks
  pass 86,420 assertions per source path and 86,422 per shaded/selected-JAR path.
- The final remapped 1.0.2 NeoForge JAR passes the selected-JAR regression.
- The opt-in [native fixture](../tests/time-sync-runtime/README.md) passes with
  NeoForge 21.1.252, Minecraft 1.21.1 and the actual new release. Its real server
  continues ticking with daylight paused, applies deterministic wall-clock time,
  restores saved daylight ownership and leaves the Overworld unchanged when
  toggling the Nether setting. The same native fixture fails against 1.0.1 with
  the intercepted commands. Both game processes shut down normally.
- Both published JARs match the tested snapshot by SHA-256 and contain the new
  production helper without the test mod. Fabric's checks cover the shaded
  artifact, not a running Fabric server. No CMI/Youer server was launched.

The NeoForge release SHA-256 is
`F583998AF4888FBACA3E6FEC87C29581C03CC4091C51E0F04937715A94B6794A`;
Fabric is `7DCF4E5084931CE556902A78C0761C50CCBD5828F93B617B5DBE52BD2752F64A`.

## Deployment

Replace the corresponding YanlingMTR/MTR JAR rather than installing both. Keep
the existing config and railway saves. Stop the server before replacing its mod;
update clients to the matching release. YLTE beta.7 remains a separate 1.21.1
addon and its own version is unchanged. This release does not change the 26.2 line.
Older packages are retained by `publishRelease` in `build/release/archive/`;
`clean` removes that build history, so it is not a permanent backup.
