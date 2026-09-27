# BVE sound-data migration baseline

`bve-sound-java-26.2-3.3.2.tsv` contains 85 records from the original Java
`MTR-neoforge-26.2-3.3.2.jar`, SHA-256:

`15f96a804948735ab69ef717a3f09bafaa9c58a842f25aaf91f0706a4901e8ec`.

Recording checks that hash and rejects Kotlin implementations. The exported JVM
snapshot retains 62 contracts for `MotorDataBase`, `MotorData4`, `MotorData5`,
`BveTrainSoundConfig`, `ConfigFile`, `Channel` and `FloatSplines`. Normal CI only
needs the checked-in records; the old JAR is needed for optional baseline runs.

`BveSoundCompatibilityCheck` loads these production classes and their nested
classes child-first from the selected class directory or JAR. ASM changes exactly
three external calls: resource lookup, resource stream opening and sound-event
creation. These adapters provide deterministic in-memory input and capture call
order; actual Minecraft `Identifier` parsing, `SoundEvent` construction and
`Mth.clamp`, Apache stream decoding, Java collections and all production BVE
parsing/interpolation remain real. No client singleton, audio device or game is
started. Both Java and Kotlin execute the same scenario bytecode.

The 9,108 comparison probes include config defaults and field values, section
aliases, CR/LF and Java ASCII trim/split behavior, trailing CSV/equal-sign
elements, comments, invalid identifiers/numbers, resource fallback and exception
boundaries. Table grids exercise all power directions, signed zero, subnormals,
NaN, infinities, adjacent BVE4 sample boundaries and 1,024 seeded float inputs per
motor version. Values are compared by raw float bits; grid digests keep fixtures
small. Additional checks cover sparse/duplicate spline keys, mutated lists/maps,
nullable entries, sound-array identity, all instance-method overrides and Java
static-method hiding. Unicode resources are recorded as UTF-8 byte hex to avoid
platform-dependent console encoding.

Deliberately preserved behavior includes the Java `door` to `brakehandle` and
`compressor` to `others` switch fall-through, sound-event creation even for numeric
MTR settings, failure on negative sound indexes and missing active motor channels,
Java float-parser rejection of lowercased `nan`/`infinity`, and read-resource streams
not being closed by this helper. Unequal BVE5 table widths still report the maximum
width but can fail at lookup in a narrower table. Correcting any of these policies
is a separate behavior change, not part of the language migration.

`checkBveSoundCompatibility` and `checkKotlinBveSoundAbi` run with the normal build;
`checkJavaBveSoundBaseline` is available when `-PjavaBaselineJar=<frozen-jar>` is set.
The tests establish headless behavior and JVM compatibility, not audible playback,
resource-reload correctness in a running client or a frame-rate improvement.
