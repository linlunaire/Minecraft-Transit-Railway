# Web-map handler Java oracle

The six migrated handler/interface classes come from the frozen MTR Java release
`26.2-3.3.2`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
The 72 checked-in records are produced by that original JAR, never by the Kotlin
implementation. Recording verifies the JAR hash and rejects Kotlin Metadata.
The post-tag `Webserver` lifecycle fixes have their own separate regression.

`ServletHandlersCompatibilityCheck` loads the selected six classes and all their
nested classes child-first. The fixture removes release dependency relocation
only for execution on the development classpath. ASM adapters replace world and
player access, plus the cooldown module's riding-route lookup. The query-module
probe supplies deterministic asynchronous results and admission decisions.
Route, Station, Platform, Depot, DataCache, the JSON library and actual handler
logic remain real. This is not a Minecraft client/server launch.

The fixture performs 10,574 assertions, including individual UTF-8 bytes, and
covers all five endpoints, write readiness/backpressure, malformed surrogate
encoding, IOException/error callbacks, delayed request reads, arrivals sorting,
hidden routes, station accumulation, parameter precedence, queue rejection,
nullable failure timing and empty-data legacy responses. In particular, an
invalid dimension with at most one world can still leave a request unanswered;
this migration deliberately does not redesign that behavior.

Custom collections require virtual Java `forEach(Consumer/BiConsumer)` calls.
The initial Kotlin inline-iteration conversion failed this gate with
`Virtual world-list forEach bypassed`. Production now preserves the original
virtual dispatch. A null delay cache is not dereferenced when no delay entries
exist, and asynchronous response completion keeps its original failure order.

The 18 JVM contracts are stored exactly as exported by the shaded Java release.
`check-development` removes only the two known Shadow namespace prefixes from
the expected snapshot for comparison with development output; packaged checks
remain exact and require Kotlin Metadata. An unshaded output deliberately fails
the exact release-descriptor comparison. Both Fabric and NeoForge final JARs
also execute this same behavior fixture, without replacing the servlet logic.

Verification tasks:

- `:common:checkServletHandlersCompatibility`
- `:common:checkKotlinServletHandlersAbi`
- `:common:checkPackagedServletHandlersFabric`
- `:common:checkPackagedServletHandlersNeoforge`
- `:common:checkJavaServletHandlersBaseline` with `-PjavaBaselineJar=<frozen Java JAR>`
