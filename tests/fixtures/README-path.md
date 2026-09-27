# Path migration baseline

`path-java-26.2-3.3.2.tsv` was recorded by `KotlinPathCompatibilityCheck`
against the actual pre-migration `MTR-neoforge-26.2-3.3.2.jar`:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`

The fixture verifies class code sources before running. Packet and MessagePack
records contain exact bytes. Search records retain status, segment count and
SHA-256 over the ordered complete MessagePack encoding of every path segment;
positions, metadata, floating-point geometry and ordering are not rounded.

The 170 records cover defaults and legacy NBT, four straight lines (up to 2,000
edges), 128 seeded branching graphs, failed first/later legs and 32 flight cases
across 16 headings, two speeds and two cruise heights. The Java assertions also
check null/deferred failure, mutation and UUID cache identity, coordinate-based
deduplication, retained segment objects and stop/dwell metadata. This uses real
MTR data and search classes, not copied search logic.

Run `:common:checkKotlinPathCompatibility` and `:common:checkKotlinPathAbi` for
the migrated implementation. With `-PjavaBaselineJar=<original-jar>`,
`:common:checkJavaPathBaseline` runs the same behavioral checks against Java.
CI uses the checked-in records and does not need the historical JAR. Record
mode is deliberately not exposed as a Gradle verification task; do not replace
the Java golden with current Kotlin output to make a failing test pass.

ANTE separately applies its real path Mixins and executes Java/Companion entry
dispatch. This headless suite does not establish world loading, GPU correctness,
worst-case search complexity, latency under concurrent route generation, or
80-player server capacity. The language migration preserves the existing DFS
policy, including its legacy tie ordering; it does not claim a new algorithm.
