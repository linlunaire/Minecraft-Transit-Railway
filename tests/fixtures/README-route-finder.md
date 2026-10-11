# Incremental passenger route finder

The 167 records in `route-finder-java-26.2-3.3.2.tsv` were recorded from Java
MTR `26.2-3.3.2`, SHA-256
`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.
The runner selects the entire module/nested-class family from the supplied
source and verifies Java/Kotlin metadata. Kotlin cannot record the reference.
The module and its two public nested types retain 14 exported JVM contracts.

`checkRouteFinderCompatibility` runs 304 assertions against that reference:

- 128 seeded networks with walking and scheduled transit, plus 27 explicit
  departure/depot combinations around the late-arrival threshold;
- queue capacity, minimum tick budget, active-query replacement, null callback
  and position failures, and the fewer-than-four-platform early return;
- same-route segment merging, station IDs, live result aliases, callback
  failure/retry and retained prior results;
- density overflow, old-map precedence, reference rotation and background
  sampling, including overwritten connection durations.

Only four wall-clock calls and the one random-constructor site are adapted.
Graph traversal, schedules, blacklists, state transitions, station lookup and
result construction execute production code. The clock advances by one per
read and each background sampler uses a fixed seed. A constructor-free railway
fixture supplies real data caches and schedules without a server world.

## Allocation change

Background sampling now snapshots packed positions into a primitive `long[]`,
preserving the original map iteration order. Candidate traversal explicitly
uses the primitive tree-set iterator. No persistent position cache is added:
the public connection graph may be mutated between requests.

`checkRouteFinderAllocation` uses thread allocation accounting under `-Xint`
to keep JIT escape analysis out of the comparison. Four rounds warm up; 16
rounds are measured per size. Manager/graph construction and the first two
request ticks are outside the candidate measurement.

| Positions | Java snapshot bytes | Kotlin snapshot bytes | Java candidate bytes | Kotlin candidate bytes |
| ---: | ---: | ---: | ---: | ---: |
| 128 | 4,344 | 1,232 | 11,816 | 8,744 |
| 1,024 | 37,960 | 13,344 | 97,976 | 73,400 |
| 4,096 | 140,872 | 42,528 | 392,984 | 294,680 |

Candidate measurements include `BlockPos` construction and primitive-map
growth; they are not a zero-allocation claim. The removed iterator boxes cost
24 bytes per candidate on this JVM. Budgets include the retained work, and
the original Java implementation fails the new snapshot gate. These numbers
describe individual synthetic search stages, not total query latency, tick
time, throughput or production TPS. `-Xint` is a test setting, not server advice.

The two historical tasks are `checkJavaRouteFinderBaseline` and
`checkJavaRouteFinderAllocationBaseline`, with `-PjavaBaselineJar=<original-jar>`.
Current behavior, allocation and packaged-ABI checks participate in `build`.

## Preserved limitations and Kotlin interface choices

The heuristic is not replaced with Dijkstra or A*. A new request still resets
an active search, fewer than four platforms still stall processing, and the
previous result list is not cleared at every request. A failed callback can
observe repeated mutation of shared station-ID lists. Re-adding a slower
duration retains the previous minimum, and integer arithmetic still wraps.
These are characterized legacy behaviors, not newly fixed algorithm defects.

The module reuses the inherited railway reference instead of keeping a second
private field. Private nested state stays private in JVM fields; synthetic
internal getters/factories serve the outer Kotlin implementation without
creating a new public Java extension surface. Public result fields remain
fields, including the caller-mutable station-ID list.
