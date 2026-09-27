# Ticket objective registration gate

`TicketObjectiveRegistrationCheck` executes the real selected
`TicketSystem.addObjectivesIfMissing` against an actual Minecraft `Scoreboard`
subclass. Only `Level.getScoreboard` is redirected to the current fixture board.
Objective lookup, creation, duplicate-name exceptions, removal, metadata and
replacement all use Minecraft's implementation. A constructor-free ServerLevel
provides the world token without starting a server.

The original control is the frozen `MTR-neoforge-26.2-3.3.2.jar`, SHA-256:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.

Every `--java` run checks this hash and rejects Kotlin metadata. Current common
classes and both completed loader JARs must contain a Kotlin `TicketSystem`.
The selected class and its nested classes are loaded together; the measured
scenario makes direct static calls rather than repeated reflection calls.

The hard operation gate is 1,000 calls after both objectives exist:

- Original Java: 2,000 `addObjective` calls and 2,000 duplicate-name exceptions.
- Optimized Kotlin: zero `addObjective` calls and zero duplicate-name exceptions.

Additional behavior checks cover initial criteria/display settings, existing
objective identity and customized metadata, removal/recreation of either
objective, switching the world's scoreboard and back, independent creation
failures and retry, unavailable-scoreboard recovery, null world and preservation
of a preexisting objective with different criteria. There is deliberately no
global/per-world flag that could become stale after deletion or replacement.

With `--allocation`, `-Xint` is mandatory. ThreadMXBean measures 4,096 stable calls
after 1,024 warm-up calls. Both variants retain their operation-count gate during
measurement; current Kotlin permits at most 32 bytes/call to tolerate incidental
VM bookkeeping while rejecting repeated component/exception creation. Original
allocation is reported for comparison, not forced below the optimized threshold.
This is allocation/operation evidence, not a wall-clock benchmark or a TPS claim.

The local interpreter comparison measured original Java at 7,372,800 bytes for
4,096 calls (1,800 bytes/call) and optimized Kotlin at zero bytes for the same
calls. The pre-optimization Kotlin release was also run as a negative control:
its SHA-256 was `3bac70c8f056272503cda7c325e71548ad8b8683aca3441c533f2ca2354a487c`.
It passes the Kotlin-origin requirement but fails the hard gate with 2,000
creation attempts instead of zero. This distinguishes the optimization from
the earlier language-only conversion.

Arguments: `<classes-directory-or-jar> [--java] [--allocation]`.
Main class: `mtr.data.TicketObjectiveRegistrationCheck`.
