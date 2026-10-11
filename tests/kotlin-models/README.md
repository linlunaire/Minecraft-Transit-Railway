# Large train model golden

The TSV records original Java A320 and Class802 geometry from the exact JAR
identified by SHA-256 in its header. The test constructs each real model,
bakes its Minecraft `ModelPart` tree, then executes the real protected render
stages. No model, Minecraft geometry, or vertex implementation is mocked.

The 52 records cover construction/configuration and four door/car/head/detail
configurations across all five material stages. Each fingerprint includes
position, UV, normal, color, overlay, and packed light. Entire quads are sorted
to ignore `ModelMapper`'s random child names while retaining per-quad vertex
order, winding, and duplicate faces. Position/normal values are quantized to
`0.0001`, and UVs to `0.000001`; integer attributes remain exact. This is a
numeric regression tolerance, not a claim of bit-identical rendering.

The ordinary check uses the committed TSV and requires `kotlin.Metadata` on
both current model classes, preventing a stale Java class directory from
giving a false pass. An optional second argument loads original Java models
from a supplied baseline JAR using an isolated child-first model classloader.
Minecraft and the unchanged geometry adapters are shared.

`KotlinTrainModelsCompatibilityCheck --print-baseline <original-java.jar>`
prints candidate golden records after two independent constructions agree.
Never regenerate the golden from newly converted Kotlin models to silence a
failure. Review a geometry change separately before changing expected data.

This check does not cover fonts, text displays, shader/GPU output, loader
Mixin application, or actual in-game frame rate.
