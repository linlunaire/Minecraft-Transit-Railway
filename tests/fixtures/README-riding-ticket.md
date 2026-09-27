# Ticketing, server riding and cooldown migration oracle

`riding-ticket-java-26.2-3.3.2.tsv` contains 264 records generated from the
original Java `MTR-neoforge-26.2-3.3.2.jar`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
`RidingTicketCompatibilityCheck --record` requires that exact JAR hash and
rejects Kotlin metadata in the selected baseline classes. The checked-in ABI
snapshot retains 29 exported class, field and method contracts, including
the nested ticket-barrier enum, Java static hiding and generic descriptors.

The child-first loader selects all three implementations and their nested
types from the supplied artifact, plus the new ticket domain implementation
when checking Kotlin. It does not use current Kotlin results as Java expectations.

After the architectural extraction, `TicketTransactionCheck` runs all 160
ticket-rule records through the production transaction Interface with ordinary
integer accounts and synchronous feedback. Minecraft is deliberately absent
from that test's runtime classpath; a compiled dependency check rejects new
platform dependencies. Additional checks cover lazy eligibility, ordered writes
and partial state after failing feedback/account updates. No ASM, Unsafe or
private-state reflection is needed for these business rules.

The integration oracle now runs 116 records and 95 additional assertions:
12 representative ticket combinations (all five notices, four success sounds
and both automatic-gate directions), the original ticket failure/early-return
cases, and all vehicle/cooldown cases. The other 148 business combinations are
not duplicated at this layer. The frozen 264-record fixture is unchanged;
`--record` still runs its complete corpus against the pinned Java artifact.

## Covered behavior

- **TicketSystem:** real vanilla `Scoreboard` and `ScoreAccess`, duplicate
  objective registration, serialized barrier states, 160 seeded combinations
  of entrance/exit flags, reminder mode, creative concession, signed zones and
  balances (including both integer extremes). Records preserve fines, encoded
  entry zones, float rounding of half fares, overflow, messages and sounds.
  The 160 combinations now primarily belong to the pure transaction check,
  while integration checks retain actual scoreboard/text/sound wiring.
  Failure cases check balances/entry records already changed before a message,
  sound or nullable-name error, missing worlds/stations/objectives and nullable
  fail sounds.
- **VehicleRidingServer:** real `Vec3` rotations, `AABB`, sets and
  `FriendlyByteBuf` encoding, nine geometric faces and 40 seeded rotations,
  zero length/width, spectator/cooldown/shift filtering, function false/null,
  virtual list `forEach`, unknown/null UUIDs, packet hex, rider callback order,
  open/closed doors, out-of-length dismount behavior and exceptions after rider
  insertion or ability changes. A callback error must not advance iterator
  removal; a packet error must leave the newly inserted rider intact.
- **RailwayDataCoolDownModule:** actual private maps and tick loop, seat refresh,
  removed-seat replacement, 30-tick shift activation and release, shift integer
  overflow, nullable keys/values, disconnect retention followed by expiry,
  move-seat ordering even when `startRiding` returns false, virtual player-list
  `forEach`, missing cache with/without a recorded route, and partial state
  after seat initialization, teleport and stop-riding errors. A null player in
  the world list can update an existing seat's cooldown before failing at the
  later player dereference; the fixture records this ordering explicitly.

## Isolation boundaries

The Kotlin preview adds 47 integration assertions through the actual
`TicketSystem.passThrough` entrypoint without changing the 116 retained golden
records. Common and both finished loader JARs now run 142 assertions. The new
cases cover fare/context delivery, concession credit, stable registration IDs,
replacement order, unregistering, entry/evasion exclusions, pre-debit exceptions
and registration changes affecting only the next snapshot. Original Java checks
explicitly skip this new API; the Java 3.3.3 maintenance JAR was rechecked against
the unchanged frozen records (95 assertions).

Minecraft world/player access, sounds/messages, game-mode lookup, network
delivery and seat entity construction/lifecycle are deterministic adapters.
Seat allocation skips registration, while production cooldown decisions and
map mutations still execute unchanged. Player abilities are real `Abilities`;
survival restoration runs real `GameType.updatePlayerAbilities`. Players and
worlds are constructor-free Minecraft instances with stable, distinct entity
IDs, not replacements for domain or scoreboard algorithms.

Only the `RailwayData.railwayDataCoolDownModule` field read is redirected to
the selected real cooldown instance, avoiding a parent/child class-loader type
conflict. The complete set of external adapter sites is checked. Published
dependency relocation is normalized only in the behavior-test loader.

These checks are deterministic compatibility tests, not a running Minecraft
server, end-to-end mod registration test or performance benchmark. Existing
Java quirks are characterized rather than silently repaired during migration.
