# Door snapshot recursion

## Failure and fix

A 26.2 client crash reported on 2026-09-26 contains a repeated
`BlockSnapshot.create -> saveWithFullMetadata -> writeCompoundTag ->
setBlockAndUpdate -> BlockSnapshot.create` chain, ending in
`StackOverflowError`. The door serializer was migrating its block state while
NeoForge was taking a pre-change snapshot. The guard was cleared only after the
nested mutation returned.

The same serializer existed in the preserved 1.21.1 baseline. Both maintenance
versions reproduce the recursion with their actual door classes and NeoForge
snapshot implementations. This does not mean every old installation triggers it:
the world-mutation path and whether the door has already been migrated matter.

The fix covers both platform screen door styles, automatic platform gates and
both lift door widths through their shared base class:

- Saving and update-packet serialization only write NBT; they never change blocks.
- New doors start with `temp=false`, using the existing block-entity renderer.
- Legacy `temp=true` block states are converted on the server's normal block-entity
  tick, with a client block update and no neighbour-update cascade. The ticker is
  removed after conversion. The obsolete NBT `temp` flag is no longer used.
- Door IDs, block-state properties and the persisted `open` value are preserved.
  Opening no longer forces another block-state write.

No ANTE door implementation duplicates this method; this fix belongs in MTR,
not in a second addon workaround.

## Regression

With the checkout's required JDK, run the wrapper (Windows: `gradlew.bat`):

```text
./gradlew :common:checkDoorSnapshotCommon :neoforge:checkDoorSnapshotNeoForge
./gradlew build
```

The standalone fixture uses real registered MTR doors, native NBT save/load and
update packets, and NeoForge's actual `BlockSnapshot.create`. The in-memory world
boundary captures before every mutation and fails at bounded recursion depth
instead of exhausting the test JVM's stack. It deliberately has no network or
neighbour simulation: neither is required to reproduce this failure.

Checks cover every door state on client/server sides, pure/repeated snapshots,
legacy data, one-time migration and ticker removal, property retention, opening
values that skip 1, collisions, save/load and external block updates. The full
build includes these checks before publishing release artifacts.

This is not an in-game multiplayer test. Before deployment, update MTR on both
client and server and test new placement, old doors, brush locking/unlocking,
train-triggered opening, breaking and chunk reload in a copy of the affected
world. Keep the old JAR outside the mods directory for rollback.
