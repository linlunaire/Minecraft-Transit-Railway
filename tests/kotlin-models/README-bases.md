# Model base migration checks

`KotlinModelBasesCompatibilityCheck` uses a real Java subclass of the migrated
generic model hierarchy, not a reimplementation of its dispatcher. Its 188
golden records were generated with the original Java classes from the JAR
identified by SHA-256 in `mtr-model-bases-java-26.2-3.3.2.tsv`.

The checks cover head/middle/tail and single-car callback order, forwarded
arguments (including nullable no-op paths), live position getter calls,
overlay lookup/render ordering, disabled overlays, translucent/no-details
pose restoration, nullable constructors, static method hiding, destination
spacing/case/CJK/empty/trailing-delimiter behavior, alternating text across
tick boundaries and numeric edge cases, and positive/negative array bounds.
The alternating-text probe restores the original process-local game tick in
`finally`; it does not open a world, touch saves, or connect to the game.

The additional ABI golden checks 65 declarations across three base classes
and their two nested enums, including original protected/static/non-final
flags, generic signatures and final render entry points. Existing large-model
and leaf-model geometry goldens must also pass unchanged.

Java consumers retain the original protected static helpers. Kotlin has no
Java-style package-protected access, so six narrow `internal @JvmSynthetic`
companion bridge overloads allow existing same-package, non-subclass models
to call those helpers without broadening their old access flags or suppressing
visibility errors. They allocate no wrapper objects or collections.

Run `mtr.model.KotlinModelBasesCompatibilityCheck <golden.tsv>`; an optional
second argument compares the original Java JAR in an isolated classloader.
`--print-baseline <original-java.jar>` prints candidate golden data only after
two independent original-Java runs agree. Default checks require Kotlin
metadata on all three current base classes. Do not regenerate expectations
from migrated implementations to silence regressions.

This does not claim shader/GPU or font output equivalence. Real Mixin weaving
is covered separately by the ANTE model-accessor gate, not by ABI inspection.
