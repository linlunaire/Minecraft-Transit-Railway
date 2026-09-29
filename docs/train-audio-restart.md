# Train audio restart after silence

`TrainLoopingSoundInstance.setData` previously set volume to zero without stopping
its audio channel. `SoundManager.isActive` therefore stayed true and the next
positive volume resumed the previous playback position instead of starting again.

Release the channel with `SoundManager.stop(instance)` when volume is nonpositive.
Do not call the instance's inherited `stop()` for temporary silence: that marks a
tickable sound permanently stopped. Train removal still uses that terminal path.
Keep the positive-volume activity guard: the engine may release a stopped channel
asynchronously, and later frame updates restart playback once cleanup completes.
No extra channel, per-frame lifecycle object, packet or save-format change is needed.

This follows [NeoMTR's channel-stop fix](https://github.com/zbx1425/NeoMTR/commit/77d641d5aa728bcc9e4d21cd18a7e1d93450b4cc).
Its unrelated BVE acceleration changes are not included. ANTE's BVE wrapper uses
this MTR instance, so it needs no separate script-sound patch.

## Regression checks

Run `:common:checkSoundRestartCompatibility` with the checkout's JDK. The same
test runs on the Java 1.21.1, Java 26.2 and Kotlin 26.2 branches. Both loader
`check` tasks also exercise their shaded production JAR.

The test loads the real production class and intercepts only Minecraft/client
and audio-device calls. It covers zero/negative volume, repeated stop/restart,
delayed channel cleanup, continuous volume/pitch/position updates, external
channel cleanup (resource reload) and terminal train removal. It fails against
the pre-fix implementation. It does not open an audio device or prove audible
in-game playback; resource-pack-specific listening remains a runtime check.

On the Kotlin branch the original 368-record Java lifecycle fixture remains
immutable. `sound-lifecycle-restart-overrides.tsv` records only the intentional
new activity probes, channel stops and fresh plays; train/BVE state and one-shot
sound dispatch are unchanged. The JVM ABI check remains unchanged as well.
