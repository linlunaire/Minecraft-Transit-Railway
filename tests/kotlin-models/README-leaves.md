# Leaf model compatibility golden

`KotlinModelLeavesCompatibilityCheck` exercises all 28 second-batch model
leaves. The 153 records come from the original Java JAR identified by SHA-256
in `mtr-model-leaves-java-26.2-3.3.2.tsv`, not from converted Kotlin output.
The independent ABI file contains 168 exported class/member contracts.

The fixture checks real constructors and baked geometry for every model,
including boolean train variants, all three door-overlay constructors, and
four lift dimensions/door-side combinations. Mini/small train checks cover
window/door/end/bogie positions, destination rules, and `createNew` preserving
the concrete class, variant, nullable animation and overlay arguments.
Lift and mini/small stages use actual protected render methods, two door/car
scenarios, non-identity initial transforms, and pose restoration assertions.
Nullable no-op entry points and the overlay's null-switch failure are checked.

Whole quads are sorted to ignore random child names while retaining winding,
duplicate faces, UVs, normals, colors, light and overlay. Position/normal
quantization is `0.0001`, UV quantization is `0.000001`; stage records hash
the ordered stage names and individual quad fingerprints. This is a numeric
regression tolerance, not proof of bit-identical rendering.

The two Christmas MLR variants deliberately exclude `INTERIOR` and
`ALWAYS_ON_LIGHTS` stage snapshots: their existing parent animation reads
wall-clock time. Their construction, layouts, factories and other stages are
still checked. Overlay/bogie/grip construction is checked, but their public
material-backed rendering is not invoked headlessly. Fonts, GPU/shaders,
Mixin application and in-game performance remain outside this check.

Run the main class `mtr.model.KotlinModelLeavesCompatibilityCheck` with this
golden TSV path and optionally an original Java baseline JAR. The default
check requires `kotlin.Metadata` on all 28 current classes and repeats
construction snapshots. `--print-baseline <original-java.jar>` prints candidate
data only after independent Java snapshots agree; never regenerate expected
data from Kotlin output merely to silence a failure.
