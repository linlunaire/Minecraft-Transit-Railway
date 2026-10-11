# Station and saved-rail migration baselines

Both golden files were recorded against the original Java classes in
`MTR-neoforge-26.2-3.3.2.jar`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
The fixtures verify the code source of every migrated class. Record mode also
rejects directories and Kotlin implementations; ordinary CI uses the checked-in
records and never regenerates them from the new implementation.

- `station-java-26.2-3.3.2.tsv`: 46 records and 1,152 assertions for
  `NameColorDataBase`, `AreaBase` and `Station`. Covers exact MessagePack/packet
  bytes, old NBT, default and nullable values, update reader indices, partial
  failure/mutation order, callback exceptions, exit-list identity, English-locale
  comparison, exit-code overflow and 512 seeded area cases.
- `saved-rail-java-26.2-3.3.2.tsv`: 111 records and 2,141 assertions for
  `SavedRailBase` and `Platform`. Covers the same serialization paths, all four
  transport modes, dwell bounds/lazy correction, protected fields and writers,
  256 seeded geometries, HashSet endpoint order, ties, duplicate/null/mutable
  positions, coordinate overflow, rail-type validation and numeric comparison.

The tests intentionally preserve legacy quirks rather than conceal behavior
changes within language conversion. Crossing rectangles can fail the old
corner-containment intersection test. Generating exits when `A` and `A1` coexist
can append to the original `A` list. Duplicate rail endpoints leave the missing
second endpoint at the zero-coordinate fallback. Fixing these requires a
separately scoped behavior change and new expected results.

Run `:common:checkKotlinStationCompatibility`, `:common:checkKotlinStationAbi`,
`:common:checkKotlinSavedRailCompatibility` and `:common:checkKotlinSavedRailAbi`.
All four are included in `build`; both final loader JARs also receive ABI and
Kotlin-metadata checks. For a local original-JAR comparison, use
`-PjavaBaselineJar=<original-jar>` with `:common:checkJavaStationBaseline` and
`:common:checkJavaSavedRailBaseline`.

## Endpoint allocation check

`SavedRailAllocationCheck` calls the actual `getOtherPosition` on 64 platforms
for 32,768 measured queries. The Java implementation copies its endpoint set
into an ArrayList twice per query. Kotlin keeps the same set/order/references
but advances an iterator instead of making those snapshots. On the local JDK 25
with `-Xint`, allocated bytes fall from 4,718,592 (144/query) to 2,097,152
(64/query), a 55.6% reduction for this operation. Iterators still allocate.

`:common:checkSavedRailAllocation` is included in `build` and rejects more than
80 bytes/query in this interpreter-only fixture. The optional
`:common:checkJavaSavedRailAllocationBaseline` reports the old allocation
without applying the new limit. Disabling JIT makes the measurement insensitive
to escape analysis; it deliberately does not claim throughput or in-game FPS/TPS
improvements. The existing MTR/ANTE path golden and actual Mixin weaving checks
remain necessary integration checks, not substitutes for a real client/server
launch or an 80-player capacity test.
