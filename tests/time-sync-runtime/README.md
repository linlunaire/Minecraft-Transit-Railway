# Opt-in native 1.21.1 time-sync regression

This separate fixture loads the actual NeoForge release JAR with Architectury,
creates a disposable flat singleplayer world and drives its real server thread.
It uses game APIs, not mouse/keyboard automation, and hides only its own game
window. A loader window may briefly appear. It never opens the user's saves or
modpack directories. Native startup can still be slower than the headless checks.

From the maintenance checkout, with JDK 21:

```text
gradlew.bat -p tests/time-sync-runtime runClient -PprobeName=final-252
gradlew.bat -p tests/time-sync-runtime runClient -PprobeName=old-control "-PtimeSyncTestJar=<old-1.0.1.jar>"
```

The old control must fail because the real MTR toggle still invokes intercepted
commands. The fixture rejects dispatch only during its assertions, representing
a command map that cannot execute MTR's old commands. This is not an installation
of CMI. Native assertions cover actual world time, ticks continuing while daylight
is paused, deterministic real-time advancement, NBT ownership reload/restoration
and a Nether toggle leaving the shared Overworld state unchanged. The clock is
injected only into MTR's helper, not Minecraft's time/tick implementation. Reload
here recreates the helper from real saved NBT; it is not a whole-process restart.

Logs and private saves are under `build/diagnostics/time-sync-runtime/client-*`.
The task fails unless its log contains `TIME_SYNC_RUNTIME_PASS` without a failure.
The fixture is not shipped in the production mod or run by ordinary `build`.
