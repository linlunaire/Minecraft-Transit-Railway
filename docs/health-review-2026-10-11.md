# 1.21.1 health review — 2026-10-11

## Scope and baseline

These checks compare local fixes with the previous formal releases, using the
Java 21 `Minecraft-Transit-Railway-1.21.1` (YLM) and `mtr-ante-1.21.1` (YLTE)
checkouts. The tests did not change 26.2/Kotlin source, installed mods or live
worlds. The local candidates include the earlier maintenance changes.

Formal NeoForge baselines, whose SHA-256 values were rechecked after the work:

```text
YanlingMTR-neoforge-1.21.1-1.0.2.jar
F583998AF4888FBACA3E6FEC87C29581C03CC4091C51E0F04937715A94B6794A

YLTE-neoforge-1.1.1-1.21.1-beta.8.jar
069B467CDBA5100E76A8AB8E6B45E00E7C247BA674D2DD5A91C93F75CB50C868
```

## Standards

The independent read-only standards review found no new major documented
standard violation in the incoming diff. The Java 21/1.21.1 maintenance
boundary and the earlier rail-save MessagePack layout were retained.
The review also identified legacy behavior defects for reproduction below.

Two possible optimizations remain suggestions, not violations: caching pinyin
per displayed search label and reusing the existing siding-ID index. Their
benefit and invalidation behavior were not measured, so neither was included
in this correctness-focused batch.

## Spec

The independent read-only behavior review found no confirmed new requirement
violation or scope expansion in the incoming diff. Its extended health audit
identified four legacy problem areas: direct-node screen dispatch, held
eye-candy editing, client-supplied item replacement, and negative-coordinate
chunk lookup. Formal-JAR class bytes/bytecode confirmed that these defects
predated the previous save/search fix candidates.

The standards review and main reproduction added train-ID ordering, string-map
encoding, route-tool writeback and node-replacement validation. The combined
implementation below groups the confirmed defects into seven areas; the two
review axes above remain separate assessments of the incoming diff.

## Confirmed fixes

| Area | Reproduced behavior and minimal change |
| --- | --- |
| String-map encoding | Binary `writeUTF` output was decoded as UTF-8, corrupting some length prefixes and text. Keep the binary API byte-for-byte unchanged; keep legacy text when its bytes round-trip exactly; otherwise write a versioned `YLTE-SM1:` Base64 envelope. The reader accepts both formats. |
| Direct-node screen | The `direct_node` switch case fell through into the rail-path editor with a null rail. Add the missing `break`. |
| Train custom configuration | The receiver read `trainId` before `sidingId`, opposite to the sender. Match the existing sender order without changing the wire field order; test distinct IDs and an unrelated train. |
| Route-path tool | Edits to the copied `ANTE-Data` compound were not written back to the item. Persist both the new first-node position and removal of an invalid previous position. |
| Held eye candy | A null virtual block state prevented editor construction; after correcting that alone, edited settings still disappeared because CustomData was a copy. Use the registered eye-candy block state and write the edited `BlockEntityTag` back to the current held item, preserving unrelated data. |
| Client-to-server editing | The server accepted a whole client-supplied item stack or a node replacement with insufficient target checks. Item editing now runs on the server thread, rechecks the held item/count/other components, and merges only the recognized tool's own settings subtree. Node conversion checks permission, dimension, brush, screen name, range, loaded chunk, interaction permission and existing train-node type; orientation comes from server state. |
| Negative chunk coordinates | Integer `/ 16` truncated toward zero. Use Minecraft's `SectionPos.blockToSectionCoord`, verified against native chunk lookup at negative boundaries. |

The item checks deliberately retain `RailwayData.hasNoPermission` policy:
ordinary permitted survival and creative use still works. They do not impose a
new creative-only requirement. No live server was used for malformed requests.

## Verification

All logs below are in each checkout's `build/diagnostics/health-review-2026-10-11`.

| Check | Result |
| --- | --- |
| YLM full `check` | Passed, 54 tasks, 1m 33s (`ylm-check.log`). |
| Final YLTE full `check` | Passed, 45 tasks, 26s (`ylte-check-final.log`), including both loader artifacts and the extended woven-rail checks. |
| String-map final-JAR regression | 5,999 assertions per loader passed: binary golden layout, readable legacy text, lengths 0–512, Chinese, emoji, NUL, varied map counts and deterministic random Unicode. The formal YLTE artifact fails the same targeted check (`string-map-formal-red.log`). |
| Actual woven Rail | Both loader classpaths preserved roll metadata and long ASCII/Chinese/emoji/NUL configuration through packet and MessagePack round trips (`rail-config-check.log`, also rerun by final `check`). |
| Native NeoForge formal baseline | 2 of 14 groups passed; 12 failed as expected (`runtime-formal-baseline.log`). This includes the already-known missing pinyin dependency and the newly confirmed legacy defects. |
| Eye-candy intermediate control | Fixing only the constructor still failed held-item writeback (`runtime-eyecandy-control.log`), independently exposing the second defect. |
| Native NeoForge fixed candidates | All 14 groups passed (`runtime-candidate.log`): search initialization/literal matching/resize; direct-node screen; eye-candy writeback in both hands; distinct train IDs; route first-node persistence; negative chunks; four legal tool types in both hands; unauthorized item/count/component/data changes; permission rejection; invalid and valid node targets. |
| Final search/save regression | Passed (`runtime-save-final.log`): real search EditBox input, two native saves with rail reload and YLTE Mixins, and a final edit persisted by real integrated-server shutdown. |

The native probes launch NeoForge 21.1.252 with Java 21 and the actual YLM/YLTE
NeoForge JARs in disposable local flat worlds. The health probe captures the
client packet transport boundary and invokes the real receivers; it waits for
queued server work before checking outcomes. Production codecs, item components,
Minecraft player/world types and target methods remain real. Test helpers are
not bundled in the candidate mod JARs.

Repeat the main checks using the existing Gradle 8.14.5 wrapper and JDK 21:

```text
# In Minecraft-Transit-Railway-1.21.1:
gradlew check --offline --max-workers=2 --console=plain --no-daemon
gradlew -p tests/health-runtime runClient --offline --max-workers=2 --console=plain --no-daemon -PprobeName=candidate
gradlew -p tests/rail-persistence-runtime runClient --offline --max-workers=2 --console=plain --no-daemon -PprobeName=health-final

# In mtr-ante-1.21.1, after the YLM artifacts are built:
gradlew check --offline --max-workers=2 --console=plain --no-daemon
gradlew :neoforge:checkStringMapSerialization "-PserializationTestJar=<formal-YLTE-jar>"
```

The last command is an intentional failing negative control for the formal
baseline. `tests/health-runtime` also accepts `-PmtrTestJar` and `-PylteTestJar`
to select exact NeoForge artifacts; absolute paths avoid ambiguity.

## Delivery and limits

Local candidates are copied separately to `build/fixes/2026-10-11-health/` in
each project, with `-healthfix.jar` filenames and SHA-256 manifests. Internal mod
versions are unchanged. Use the matching loader and replace the previous JAR;
do not keep two versions of the same mod in one installation. Update YLTE on
both client and server together. Back up the entire world (including MTR data)
and configs before testing; retain the old JARs for rollback.

Old strings that were readable remain readable. Newly enveloped strings need
the new decoder, so mixing an old YLTE client/server or downgrading after writes
is not guaranteed compatible. Previously corrupted configuration or rail
connections already lost without a backup cannot be reconstructed by this fix.

This is not full-modpack, Fabric in-game, dedicated Youer multiplayer, long-run
or overnight acceptance, nor an FPS/TPS benchmark. In particular, the negative
chunk defect does not prove the cause of the reported overnight rail loss.
The local candidates in this record still bundled JPinyin 1.1.7. The later
YLTE 1.1.1 source update replaces it with Apache-2.0 pinyin and Opencc4j
libraries, with notices for their dependencies and dictionary data.

## Source publication

The fixes are included in YanlingMTR 1.0.3 and YLTE 1.1.1 for Minecraft 1.21.1.
YLTE no longer uses a beta version suffix. The native fixture defaults point
to these versions; use the explicit JAR properties above to repeat comparisons
with the original baselines. Older release packages remain under each project's
`build/release/archive/`; `clean` removes that history.
