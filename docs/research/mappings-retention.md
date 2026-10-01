# Minecraft-Mappings retention decision

Historical finding, verified 2026-09-25 before the standalone Transit Core migration: the unchanged 1.21.1 tags require `linlunaire/Minecraft-Mappings`. Archiving would preserve their original download URLs. No repository was archived or deleted during this investigation.

**Migration implemented and recovery verified on 2026-10-01:** [Kotlin LunaCore preserves the exact historical inputs and a preparation script](https://github.com/linlunaire/Kotlin-LunaCore/blob/master/docs/legacy-mappings.md). The `transit-core-0.1.0` archive tag is published at commit `650800892192395a8755ef41efcfe90b913a3c4a`. A fresh shallow clone retrieved that tag and verified all 31 original payload hashes. Current 1.21.1 and 26.2 maintenance builds keep their required mappings in the MTR source tree; YLTE compiles against those MTR artifacts. They do not download the separate Minecraft-Mappings repository. The original historical tags still require the preparation step below. The remaining audit records their old dependencies.

## Current 26.2

The 26.2 maintenance and Kotlin branches track their mapping/compatibility sources directly. Their build, settings and CI do not download `Minecraft-Mappings`. ANTE's current build depends on MTR artifacts, not this repository. Already-built game JARs do not need GitHub mapping downloads at runtime.

## Historical source builds still need it

MTR tag `1.21.1-3.3.2` resolves to `52095c771f8ab36527a723bb922fc6d8650bd4b5`:

- [build.gradle line 21](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L21) pins mapping commit `59c5ee19517f61f1b00a54c013322dc32592941f`.
- [Line 106](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L106) downloads its source ZIP from the old `linborealis/Minecraft-Mappings` URL.
- [Line 146](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L146) downloads `fabric.min.js` from the same pinned commit.
- [Line 278](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L278) makes every subproject task depend on `setupFiles`. Download failure has no source-vendoring fallback.

ANTE tag `1.1.1-1.21.1-beta.2` resolves to `da16869dee6c14a1b50dc04150081c3a211d06eb`; its [CI checks out and builds that MTR tag](https://github.com/linlunaire/mtr-ante/blob/da16869dee6c14a1b50dc04150081c3a211d06eb/.github/workflows/build.yml#L25), so it is indirectly affected too.

Both historical download URLs returned HTTP 200 at the original audit. The old username redirects to `linlunaire`; it is not a separate backup. Deleting that repository removes these historical download endpoints.

## Rebuild after repository deletion

Use the current YanlingMTR 1.21.1 maintenance branch or the current 26.2 branch for new builds. To rebuild the frozen MTR `1.21.1-3.3.2` / ANTE `1.1.1-1.21.1-beta.2` pair, clone Kotlin LunaCore and fetch the preserved archive if the checkout is shallow:

```sh
git fetch --depth=1 origin tag transit-core-0.1.0
```

Run the preparation script from that Kotlin LunaCore checkout against the historical MTR checkout:

```powershell
pwsh -File tools/prepare-legacy-1.21.1.ps1 -MtrPath ../Minecraft-Transit-Railway-3.x.x
```

Then build MTR first and ANTE second with Java 21 and their Gradle wrappers. The preparation script preserves the historical Java compatibility fixes, vendors the archived dashboard input and removes the old repository downloads. The original tags remain unchanged; checking them out alone does not perform this migration. The original licenses, byte-level checksums and maintenance patch are kept together in the preserved archive.

The owner can delete the separate repository after this recovery path is available. This migration does not delete the GitHub repository or local checkouts.
