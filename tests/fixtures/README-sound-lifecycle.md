# Sound lifecycle migration baseline

`sound-lifecycle-java-26.2-3.3.2.tsv` contains 368 records captured from the
original Java `MTR-neoforge-26.2-3.3.2.jar`, SHA-256:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.

Recording rejects Kotlin and verifies the original JAR hash. The matching ABI
snapshot retains 40 exported contracts for `TrainSoundBase`,
`LoopingSoundInstance`, `TrainLoopingSoundInstance`, `JonTrainSound`,
`JonTrainSoundConfig` and `BveTrainSound`, including constructors, inheritance,
public fields and overridable methods. Checked-in records are sufficient for CI.

## Execution boundary

`SoundLifecycleCompatibilityCheck` loads only this production batch child-first
from the selected class directory or JAR. It verifies exactly 19 call sites at
nine external boundaries: the client singleton, client sound-manager access,
player position, active-sound lookup, sound playback, local-world sound output,
frame duration, sound-playback eligibility and BVE constructor random integers.
The same adapters are applied to original Java and current Kotlin. Jon's actual
`Random` is seeded; 2,048 frames exercise its intermittent random-sound branch.

Actual Minecraft sound superclasses, `Identifier`, `SoundEvent`, `BlockPos`,
train speed/door/route getters and binary path-index search execute normally.
Constructor-free Minecraft, player, world and train objects avoid starting a
window, connection or audio device. Their fixture fields provide input only;
sound constructors, public methods and inherited tickable stopping logic are
not replaced. The raw inherited volume/pitch fields are inspected before the
external audio engine resolves or mixes a `Sound`.

The previously migrated BVE configuration/table classes are real shared
dependencies for both implementations. `ConfigFile` parses the fixture settings,
and `MotorData5.FloatSplines` parses actual CSV data used by motor lookups. Only
the outer resource-loading object construction is bypassed; the prior
[BVE data baseline](README-bve-sound.md) covers those constructors and resource
parsing separately. This isolates the new lifecycle migration rather than
substituting a new implementation as its expected result.

## Covered behavior and retained policies

The 915 explicit probes and 368 records cover nearest-loop selection/removal and
the 32-block boundary; active, silent, removed and stopped sound instances;
null-forwarding and failed-play mutation order; Jon speed groups, acceleration,
coasting, fixed playback speed, random sounds and door precedence; BVE constructor
ownership, breaker timing, reservoir/compressor transitions, motor parameters,
Jacobs/non-Jacobs bogies, door dispatch and all exported method overrides.
Float values are captured by raw bits, including signed zero, subnormals,
NaN/infinity and the adjacent speed/volume/pitch boundaries.

The motor loop deliberately rereads the virtual `getSoundCount()` on every
condition check; a changing-count subclass records this dispatch. Integer
reservoir updates preserve Java compound-assignment float-to-int truncation,
and shoe gain preserves its float multiplication followed by double arithmetic
and final float narrowing. Nullable positions are forwarded through private
erased generic bridges where Minecraft's new annotations would otherwise force
an earlier Kotlin check.

Preserved legacy policies include non-stopping generic loops, not resetting the
inherited stopped flag when a train sound is replayed, no automatic retry/reset
after an audio failure, failure for motor counts above the available array size,
and the existing world-type assumptions in BVE one-shot playback. The migration
does not fix paused/NaN frame behavior or change compressor precision.

## Running

`checkSoundLifecycleCompatibility` and `checkKotlinSoundLifecycleAbi` validate the
common output. Both completed loader JARs also run the same lifecycle scenarios.
With `-PjavaBaselineJar=<frozen-jar>`, `checkJavaSoundLifecycleBaseline` verifies
the old implementation against the records. The checker accepts the golden path
and the selected classes directory/JAR; optional `--record` prints a baseline
only after rejecting Kotlin and checking the frozen hash.

These are headless behavior/JVM-contract checks, not audible playback,
in-game resource-reload verification or measured FPS/TPS gains.
