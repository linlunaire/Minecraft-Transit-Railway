param(
    [string]$MaintenanceProject = (Join-Path $PSScriptRoot '../../Minecraft-Transit-Railway-1.21.1'),
    [string]$YlteProject = (Join-Path $PSScriptRoot '../../mtr-ante-1.21.1'),
    [string]$OutputDirectory = (Join-Path $PSScriptRoot '../build/modrinth/YanlingMTR-YLTE-release')
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem
$projectRoot = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot '..'))
$maintenanceRoot = [System.IO.Path]::GetFullPath($MaintenanceProject)
$ylteRoot = [System.IO.Path]::GetFullPath($YlteProject)
$destination = [System.IO.Path]::GetFullPath($OutputDirectory)
$versions = Get-Content -LiteralPath (Join-Path $projectRoot 'modrinth/versions.json') -Raw | ConvertFrom-Json
$projectDraft = Get-Content -LiteralPath (Join-Path $projectRoot 'modrinth/project.json') -Raw | ConvertFrom-Json

function Read-ZipText($archive, [string]$entryName) {
    $entry = $archive.GetEntry($entryName)
    if ($null -eq $entry) { throw "Missing packaged file: $entryName" }
    $reader = [System.IO.StreamReader]::new($entry.Open())
    try { return $reader.ReadToEnd() } finally { $reader.Dispose() }
}

# Preflight all four primary files before preparing a handoff directory.
$sources = foreach ($release in $versions) {
    $game = $release.upload.game_versions[0]
    $loader = $release.upload.loaders[0]
    $root = if ($game -eq '1.21.1') { $maintenanceRoot } else { $projectRoot }
    $source = Join-Path $root "build/release/$($release.file)"
    $archive = [System.IO.Compression.ZipFile]::OpenRead($source)
    try {
        if ($loader -eq 'fabric') {
            $metadata = Read-ZipText $archive 'fabric.mod.json' | ConvertFrom-Json
            if ($metadata.id -ne 'mtr' -or $metadata.name -ne 'YanlingMTR' -or $metadata.version -ne $release.loader_compatibility_version) {
                throw "Wrong Fabric identity/version in $source"
            }
        } else {
            $metadata = Read-ZipText $archive 'META-INF/neoforge.mods.toml'
            if ($metadata -notmatch '(?m)^modId = "mtr"\s*$' -or $metadata -notmatch '(?m)^displayName = "YanlingMTR"\s*$' -or
                $metadata -notmatch ('(?m)^version = "' + [regex]::Escape($release.loader_compatibility_version) + '"\s*$')) {
                throw "Wrong NeoForge identity/version in $source"
            }
            if ($null -ne $archive.GetEntry('META-INF/mods.toml')) { throw "Obsolete Forge metadata in $source" }
        }
        $license = Read-ZipText $archive 'LICENSE-YanlingMTR'
        if ($license -notmatch 'Copyright \(c\) 2022 Jonathan Ho') { throw "Missing upstream MIT attribution: $source" }
        $fontLicense = Read-ZipText $archive 'licenses/Noto-OFL-1.1.txt'
        if ($fontLicense -notmatch 'SIL OPEN FONT LICENSE Version 1.1') { throw "Missing font license: $source" }
        $webVersion = Read-ZipText $archive 'assets/mtr/website/version.js'
        if (-not $webVersion.Contains("$game-$($release.public_version)")) { throw "Stale web-map version: $source" }
        foreach ($locale in @('en_us', 'zh_cn')) {
            $language = Read-ZipText $archive "assets/mtr/lang/$locale.json" | ConvertFrom-Json
            if ($language.'gui.mtr.mtr_options' -notmatch 'YanlingMTR') { throw "Stale in-game brand: $source / $locale" }
        }
    } finally { $archive.Dispose() }
    [pscustomobject]@{ Release = $release; Source = $source; Hash = (Get-FileHash -LiteralPath $source -Algorithm SHA256).Hash }
}

$anteVersion = (Get-Content -LiteralPath (Join-Path $projectRoot 'gradle.properties') | Where-Object { $_ -match '^ante_version=' }).Split('=', 2)[1].Trim()
$ylteVersion = (Get-Content -LiteralPath (Join-Path $ylteRoot 'gradle.properties') | Where-Object { $_ -match '^mod_version=' }).Split('=', 2)[1].Trim()
$ylteVersions = Get-Content -LiteralPath (Join-Path $ylteRoot 'modrinth/versions.json') -Raw | ConvertFrom-Json
$ylteDraft = Get-Content -LiteralPath (Join-Path $ylteRoot 'modrinth/project.json') -Raw | ConvertFrom-Json
if ($ylteVersions.Count -ne 2) { throw 'Expected two YLTE upload drafts' }
$addons = @(
    [pscustomobject]@{ Root = $projectRoot; Folder = 'ante-26.2'; Prefix = 'YanlingMTR-ANTE'; Name = 'YanlingMTR-ANTE'; Version = $anteVersion; License = (Join-Path $projectRoot 'ante/LICENSE') },
    [pscustomobject]@{ Root = $ylteRoot; Folder = 'ylte-1.21.1'; Prefix = 'YLTE'; Name = 'YLTE — Yanling Transit Expansion'; Version = $ylteVersion; License = (Join-Path $ylteRoot 'LICENSE') }
)
# Both addon pairs are required for this handoff. Validate every candidate before copying any file.
$addonSources = foreach ($addon in $addons) {
    foreach ($loader in @('fabric', 'neoforge')) {
        $fileName = "$($addon.Prefix)-$loader-$($addon.Version).jar"
        if ($addon.Prefix -eq 'YLTE') {
            $draft = @($ylteVersions | Where-Object { $_.file -eq $fileName -and $_.loader_version -eq $ylteVersion -and $_.upload.game_versions[0] -eq '1.21.1' -and $_.upload.loaders[0] -eq $loader })
            if ($draft.Count -ne 1) { throw "Stale YLTE upload draft for $fileName" }
        }
        $source = Join-Path $addon.Root "build/release/$fileName"
        $archive = [System.IO.Compression.ZipFile]::OpenRead($source)
        try {
            if ($loader -eq 'fabric') {
                $metadata = Read-ZipText $archive 'fabric.mod.json' | ConvertFrom-Json
                if ($metadata.id -ne 'mtrsteamloco' -or $metadata.name -ne $addon.Name -or $metadata.version -ne $addon.Version) {
                    throw "Wrong addon Fabric identity/version: $source"
                }
            } else {
                $metadata = Read-ZipText $archive 'META-INF/neoforge.mods.toml'
                if ($metadata -notmatch '(?m)^\s*modId\s*=\s*"mtrsteamloco"\s*$' -or
                    $metadata -notmatch ('(?m)^\s*displayName\s*=\s*"' + [regex]::Escape($addon.Name) + '"\s*$') -or
                    $metadata -notmatch ('(?m)^\s*version\s*=\s*"' + [regex]::Escape($addon.Version) + '"\s*$')) {
                    throw "Wrong addon NeoForge identity/version: $source"
                }
            }
            if ($addon.Prefix -eq 'YLTE') {
                if ((Read-ZipText $archive 'LICENSE-YLTE') -notmatch '2022-present Zbx1425') { throw "Missing YLTE upstream attribution: $source" }
                if ((Read-ZipText $archive 'licenses/Noto-OFL-1.1.txt') -notmatch 'SIL OPEN FONT LICENSE Version 1.1') { throw "Missing YLTE font license: $source" }
                foreach ($locale in @('en_us', 'zh_cn')) {
                    $language = Read-ZipText $archive "assets/mtrsteamloco/lang/$locale.json" | ConvertFrom-Json
                    if ($language.'gui.mtrsteamloco.config.client.title' -notmatch 'YLTE') { throw "Stale YLTE UI in $source" }
                }
            }
        } finally { $archive.Dispose() }
        [pscustomobject]@{ Addon = $addon; File = $fileName; Source = $source; Hash = (Get-FileHash -LiteralPath $source -Algorithm SHA256).Hash }
    }
}

New-Item -ItemType Directory -Path $destination -Force | Out-Null
$hashes = foreach ($item in $sources) {
    $target = Join-Path $destination $item.Release.file
    if ((Test-Path -LiteralPath $target) -and (Get-FileHash -LiteralPath $target -Algorithm SHA256).Hash -ne $item.Hash) {
        throw "A different existing handoff file is present: $target. Select a fresh OutputDirectory."
    }
    Copy-Item -LiteralPath $item.Source -Destination $target
    if ((Get-FileHash -LiteralPath $target -Algorithm SHA256).Hash -ne $item.Hash) { throw "Handoff checksum mismatch: $target" }
    "$($item.Hash.ToLowerInvariant())  $($item.Release.file)"
}
$utf8 = [System.Text.UTF8Encoding]::new($false)
[System.IO.File]::WriteAllLines((Join-Path $destination 'SHA256SUMS.txt'), [string[]]$hashes, $utf8)
[System.IO.File]::WriteAllText((Join-Path $destination 'project-description.txt'), $projectDraft.project.body, $utf8)
Copy-Item -LiteralPath (Join-Path $projectRoot 'modrinth/project.json'), (Join-Path $projectRoot 'modrinth/versions.json'), (Join-Path $projectRoot 'LICENSE') -Destination $destination

foreach ($addon in $addons) {
    $addonDestination = Join-Path $destination $addon.Folder
    New-Item -ItemType Directory -Path $addonDestination -Force | Out-Null
    $addonHashes = foreach ($item in @($addonSources | Where-Object { $_.Addon.Folder -eq $addon.Folder })) {
        $target = Join-Path $addonDestination $item.File
        if ((Test-Path -LiteralPath $target) -and (Get-FileHash -LiteralPath $target -Algorithm SHA256).Hash -ne $item.Hash) {
            throw "A different addon file is present: $target. Select a fresh OutputDirectory."
        }
        Copy-Item -LiteralPath $item.Source -Destination $target
        if ((Get-FileHash -LiteralPath $target -Algorithm SHA256).Hash -ne $item.Hash) { throw "Addon checksum mismatch: $target" }
        "$($item.Hash.ToLowerInvariant())  $($item.File)"
    }
    Copy-Item -LiteralPath $addon.License -Destination $addonDestination
    [System.IO.File]::WriteAllLines((Join-Path $addonDestination 'SHA256SUMS.txt'), [string[]]$addonHashes, $utf8)
}
$ylteDestination = Join-Path $destination 'ylte-1.21.1'
Copy-Item -LiteralPath (Join-Path $ylteRoot 'modrinth/project.json'), (Join-Path $ylteRoot 'modrinth/versions.json') -Destination $ylteDestination
[System.IO.File]::WriteAllText((Join-Path $ylteDestination 'project-description.txt'), $ylteDraft.project.body, $utf8)

$instructions = @'
YanlingMTR 与 YLTE 发布包

在一个名为 YanlingMTR 的 Modrinth 项目中创建四个版本，每个版本只上传对应的一个主 JAR。
1.21.1 为 1.0.0；26.2 为 1.0.0-beta.1。两个加载器分别创建版本，不放进 Additional files。
project-description.txt 是可粘贴的英文项目介绍，project.json 记录项目字段和署名披露。
versions.json 包含每个版本的名称、版本号、加载器、游戏版本、更新日志和依赖。
SHA256SUMS.txt 记录经过检查的四个主 JAR 的校验和。

Fabric：添加 Fabric API、Architectury API 为必需依赖。
NeoForge：添加 Architectury API 为必需依赖。
26.2：还必须安装匹配加载器的 Kotlin LunaCore 0.2.1；项目介绍已提供其下载项目链接。
Kotlin LunaCore 尚未在 Modrinth 搜索中找到；该项可用时应填写其真实项目依赖，不能虚构项目 ID。
ante-26.2：新版 ANTE 已合并进 YanlingMTR 的源码仓库和统一构建，版本保持 1.2.0-26.2-kotlin.5。
这里的“合并”不是单 JAR 合并：目前本体和 ANTE 模块仍为两个文件，使用 ANTE 功能时要安装匹配加载器的两个 JAR。
不要把 ANTE JAR 当作能替代本体的 YanlingMTR 主文件；当前文件布局仍是可选附属模块。

ylte-1.21.1：旧版独立 ANTE 更名为 YLTE — Yanling Transit Expansion，本次版本 1.1.1-1.21.1-beta.6。
为 YLTE 创建独立的 Modrinth 项目，按该目录 versions.json 分别上传 Fabric 和 NeoForge 主文件。
该目录的 project-description.txt 可直接粘贴；添加实际 YanlingMTR 项目、Architectury API 为必需依赖，Fabric 还需 Fabric API。
YLTE 与旧 ANTE 使用相同的 mtrsteamloco ID、脚本接口、存档键及协议，替换旧 ANTE JAR，不能并装；也不能用于 26.2。

保留上游 MTR、ANTE/NTE 作者署名及 MIT、字体 OFL 许可证。填写 derivative-content 和适用的 AI 内容披露。
加载器里看到旧 3.x 兼容编号加 yanlingmtr 后缀是有意保留的附属模组版本兼容层。
本包没有自动上传。26.2 是 Kotlin 预览版，本次验证不代表完成整合包或多人游戏验收。
'@
[System.IO.File]::WriteAllText((Join-Path $destination '上传说明.txt'), $instructions, $utf8)
Write-Output "PASS: four YanlingMTR, two integrated-line ANTE and two YLTE artifacts; identities, versions, branding, licenses and SHA-256 verified"
Write-Output "Prepared: $destination"
