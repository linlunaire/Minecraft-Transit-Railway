# Short slider rendering — Minecraft 1.21.1

## Failure and scope

The depot editor's 24 hourly frequency sliders still sampled the legacy vanilla
`minecraft:textures/gui/widgets.png` sheet. This asset is absent from the actual
Minecraft 1.21.1 client JAR. Both the 1.0.0 release source and the installed Rapid
`MTR-neoforge-1.21.1-3.3.9.jar` fail the new compatibility check at this reference.
The earlier dashboard regressions covered background ownership and PNG image
buttons, not `WidgetShorterSlider`.

YanlingMTR 1.0.1 uses vanilla `widget/slider`, `widget/slider_handle` and
`widget/slider_handle_highlighted` through `GuiGraphics.blitSprite`, as the native
1.21.1 slider does. Their packaged nine-slice metadata supports the short track
and 6-pixel handle. Tick marks use solid rectangles instead of sampling the
removed sheet. Whole-width/height drawing also avoids the old half-size division
discarding the final pixel of odd dimensions.

The fix is shared by depot scheduling, dwell-time, siding, configuration and
resource-pack editor sliders. Value quantization, label placement, dragging,
Shift-click behavior, the 380-pixel width cap and save formats are unchanged.
It does not change 26.2 or the ANTE/YLTE modules.

## Repeatable checks

Run the wrapper with JDK 21:

```text
./gradlew :common:checkGuiCompatibility
./gradlew :common:checkPackagedGuiFabric :common:checkPackagedGuiNeoforge
./gradlew build
```

The GUI fixture executes the real production slider and native widget lifecycle,
recording only drawing instead of creating a window. Its 321 assertions include
the previous dashboard/button coverage, vanilla sprite existence/scaling metadata,
24 timetable rows at 8/9/20-pixel heights, endpoint/intermediate values, odd sizes,
hover/focus/disabled/hidden states, legacy render entry point and eleven dwell-time
ticks with the original displayed ratio. Packaged checks load the production
class from each shaded platform JAR rather than silently using source output.

To detect an old namespace-compatible JAR, pass it as the first production
classpath entry:

```text
./gradlew :common:checkGuiCompatibility -PguiTestJar=<old-MTR-JAR>
```

The old JAR is an expected failing control, reporting the removed texture path
before trying to draw it. This isolates the missing texture without an unrelated
headless OpenGL failure. No resource pack, installed mod or world is modified.
These source/package checks do not establish a live in-game visual test.
