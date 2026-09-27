# Depot, cache and angle migration baselines

The `depot-java-26.2-3.3.2.tsv`, `cache-java-26.2-3.3.2.tsv` and
`angle-java-26.2-3.3.2.tsv` goldens were recorded from the original Java
`MTR-neoforge-26.2-3.3.2.jar`, SHA-256:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.

Recording rejects Kotlin implementations. Normal CI uses committed records;
the historical JAR is only needed for optional baseline runs. The corresponding
ABI snapshots retain 38 Depot, 30 DataCache and 33 RailAngle exported contracts.

## Depot

`KotlinDepotCompatibilityCheck` loads the selected Depot and nested classes
directly from the supplied artifact. ASM replaces only the five external
`System.currentTimeMillis` calls with a controllable fixture clock. A
constructor-free Level adapter supplies overworld time; a real TrainServer
subclass captures deployment calls, and a constructor-free RailwayData holds
the test siding set. No world or network connection is opened.

The 10,382 assertions and 24 records cover exact save/reduced-save/packet bytes,
all modes, legacy NBT, malformed frequency recovery, negative wire counts,
partial updates, departure normalization, list identity, subclass dispatch,
60 world timetables, 256 seeded departure lists with 40 offset combinations
each, dirty-state clearing, round-robin selection, unavailable/filtered sidings
and failed train deployment. Original production scheduling code is exercised;
the external train action itself is not simulated.

Deliberately preserved Java behavior includes a save-header count mismatch:
Depot reports 15 full / 13 reduced fields but emits 16 / 14. Fixtures compare
all emitted fields without a header and separately assert the old reported
lengths. Repairing this requires a separate saved-data compatibility change.
Likewise, negative or excessive frequencies can prevent forward progress in
the legacy timetable loop; fixtures use finite positive frequencies or zero,
not pathological nonterminating inputs. This migration does not claim to have
fixed those issues or the per-request path-generation thread lifecycle.

ANTE separately tests generation through the actual Kotlin DepotMixin with
real Sponge weaving, bounded worker joins and its existing 12 Java goldens.

## DataCache

The 8,041 assertions, nine records and 128 seeded networks cover retained input
and published-map references, duplicate ID ordering, invalid-reference pruning,
overlapping station adjacency, first matching area, last route-owning depot,
positive/zero/negative/NaN/infinite durations, primitive-map defaults, callback
publication order and partial refresh failures. Graph payloads are inspected
read-only; private ConnectionDetails fields are not modified. Timestamps use
before/after bounds instead of a sleep or an assumed millisecond advance.

Public mutable collections remain mutable and uncached. This batch does not
change the quadratic station-overlap scan or introduce invalidation assumptions.

## RailAngle

273 records retain 16 exact-bit trigonometric constants, 256 enum pairs and a
digest of 20,392 boundary/seeded inputs. Checks include half-step rounding,
positive/negative wraps, signed zero, subnormals, NaN, null failures and isolation
from caller mutations of the array returned by `values()`.

The old normalization loop is retained. Infinite or extremely large finite
inputs can fail to terminate in that loop and are not fed into these fixtures.
`fromAngle` now indexes Kotlin's shared enum entries; both path finders use
the shared entries for their turn-loop bounds as well.

`RailAngleAllocationCheck` requires `-Xint` and thread allocation accounting.
After warm-up, 32,768 queries allocate 2,621,440 bytes in Java (80/query) and
zero in Kotlin. The gate allows at most 1,024 total bytes; the original Java
implementation fails it unless explicitly run with `--baseline`. This does
not measure JIT-optimized application timing, GPU frames or server capacity.

## Running

`build` includes `checkKotlinDepotCompatibility`, `checkKotlinCacheCompatibility`,
`checkKotlinAngleCompatibility`, their three ABI gates and `checkRailAngleAllocation`.
With `-PjavaBaselineJar=<original-jar>`, optional `checkJavaDepotBaseline`,
`checkJavaCacheBaseline`, `checkJavaAngleBaseline` and
`checkJavaAngleAllocationBaseline` execute the original Java implementation.
