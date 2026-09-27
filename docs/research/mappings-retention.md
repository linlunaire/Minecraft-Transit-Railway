# Minecraft-Mappings retention decision

Historical finding, verified 2026-09-25 before the standalone Transit Core migration: the unchanged 1.21.1 tags require `linlunaire/Minecraft-Mappings`. Archiving would preserve their original download URLs. No repository was archived or deleted during this investigation.

**Migration now implemented:** [Transit Core preserves the exact historical inputs and a preparation script](https://github.com/linlunaire/Transit-Core/blob/650800892192395a8755ef41efcfe90b913a3c4a/docs/legacy-mappings.md). Prepared 1.21.1 MTR and ANTE checkouts both build without the old repository. The original tags remain unchanged and still need that preparation step if their dependency repository is removed. The sections below record the original dependency audit, not an outstanding requirement to create another archive.

## Current 26.2

MTR master tracks its mapping/compatibility Java sources directly. Its build, settings and CI no longer download `Minecraft-Mappings` or run `setupFiles`. ANTE's current build depends on MTR artifacts, not this repository. Already-built game JARs do not need GitHub mapping downloads at runtime.

## Historical source builds still need it

MTR tag `1.21.1-3.3.2` resolves to `52095c771f8ab36527a723bb922fc6d8650bd4b5`:

- [build.gradle line 21](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L21) pins mapping commit `59c5ee19517f61f1b00a54c013322dc32592941f`.
- [Line 106](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L106) downloads its source ZIP from the old `linborealis/Minecraft-Mappings` URL.
- [Line 146](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L146) downloads `fabric.min.js` from the same pinned commit.
- [Line 278](https://github.com/linlunaire/Minecraft-Transit-Railway/blob/52095c771f8ab36527a723bb922fc6d8650bd4b5/build.gradle#L278) makes every subproject task depend on `setupFiles`. Download failure has no source-vendoring fallback.

ANTE tag `1.1.1-1.21.1-beta.2` resolves to `da16869dee6c14a1b50dc04150081c3a211d06eb`; its [CI checks out and builds that MTR tag](https://github.com/linlunaire/mtr-ante/blob/da16869dee6c14a1b50dc04150081c3a211d06eb/.github/workflows/build.yml#L25), so it is indirectly affected too.

Both historical download URLs were checked live and returned HTTP 200. The old username redirects to `linlunaire`; it is not a separate backup. The mapping repository's remote `1.21.1` branch is still the pinned commit.

## If deletion is required later

First create a new 1.21.1 maintenance commit/tag with the required mapping sources and `fabric.min.js` vendored, remove compulsory downloads, update the corresponding ANTE build reference, and test a clean source build without a local mapping cache. Do not overwrite existing release tags. Even then, deleting the dependency makes the original tags non-reproducible, so retaining an archive remains preferable.

[GitHub's archiving documentation](https://docs.github.com/en/repositories/archiving-a-github-repository/archiving-repositories) describes the read-only, reversible archival behavior.
