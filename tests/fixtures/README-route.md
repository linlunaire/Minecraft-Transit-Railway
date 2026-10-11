# Route migration baseline

`route-java-26.2-3.3.2.tsv` was recorded against the original Java release
`MTR-neoforge-26.2-3.3.2.jar`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.

`KotlinRouteCompatibilityCheck` verifies the code sources of `Route`,
`RoutePlatform` and `CircularState`. Its 779 assertions and 26 records cover
exact MessagePack and packet bytes, all 288 transport/type/circular/flag
combinations, legacy NBT, missing/excess custom destinations, enum fallbacks,
negative counts, duplicate platform IDs, 512 seeded platform entries, destination
reset markers, nullable references, list identity and partial update failures.
The 288-case record is a SHA-256 over the concatenated wire and save encodings;
individual representative cases also retain their complete bytes.

Run `:common:checkKotlinRouteCompatibility` and `:common:checkKotlinRouteAbi`.
With `-PjavaBaselineJar=<original-jar>`, `:common:checkJavaRouteBaseline` runs the
same behavior checks against Java. Record mode is not a Gradle verification
task, and rejects directories or Kotlin implementations. Never regenerate a
Java golden from a new implementation to make a failure pass.

The route's mutable lists/entries remain observable immediately. No destination
or membership cache has been introduced: public direct mutation would require
a separate invalidation design. Two private helpers share the platform packet
format without changing constructor/update entry points used by ANTE.

ANTE separately weaves its migrated `IRoute`/`RouteMixin` into the actual Route,
checks save/packet extensions against an all-Java golden, and repeats the test
using both final loader JAR pairs. This is not a game/world or FPS/TPS test.
