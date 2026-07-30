param(
    [ValidatePattern('^\d+\.\d+\.\d+$')]
    [string]$Version,

    [ValidateSet("patch", "minor", "major")]
    [string]$Bump = "patch",

    [string]$Remote = "origin",
    [string]$Branch = "master",
    [switch]$SkipPush,
    [switch]$AllowDirty
)

$ErrorActionPreference = "Stop"
Set-StrictMode -Version Latest

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..")
Set-Location $repoRoot

$versionFile = "build.gradle"
$groupId = "com.github.wukuiqing49"
$artifactId = "XPopup"
$githubRepository = "wukuiqing49/XPopup"

function Get-CurrentVersion {
    $content = Get-Content -LiteralPath $versionFile -Raw -Encoding UTF8
    $match = [regex]::Match($content, '(?m)^ext\.xpopup_version\s*=\s*"(\d+\.\d+\.\d+)"\s*$')
    if (-not $match.Success) {
        throw "Cannot read ext.xpopup_version from $versionFile."
    }
    return [version]$match.Groups[1].Value
}

function Get-NextVersion([version]$current, [string]$bump) {
    switch ($bump) {
        "major" { return "$($current.Major + 1).0.0" }
        "minor" { return "$($current.Major).$($current.Minor + 1).0" }
        default { return "$($current.Major).$($current.Minor).$($current.Build + 1)" }
    }
}

function Invoke-CheckedCommand([string]$command, [string[]]$arguments) {
    Write-Host ">> $command $($arguments -join ' ')" -ForegroundColor Cyan
    & $command @arguments
    if ($LASTEXITCODE -ne 0) {
        throw "Command failed: $command $($arguments -join ' ')"
    }
}

function Replace-InFile([string]$path, [scriptblock]$replace) {
    $content = Get-Content -LiteralPath $path -Raw -Encoding UTF8
    $updated = & $replace $content
    if ($updated -ne $content) {
        $utf8NoBom = New-Object System.Text.UTF8Encoding($false)
        [System.IO.File]::WriteAllText((Resolve-Path $path), $updated, $utf8NoBom)
        Write-Host "updated $path"
    }
}

function Assert-FileContains([string]$path, [string]$expected) {
    $content = Get-Content -LiteralPath $path -Raw -Encoding UTF8
    if (-not $content.Contains($expected)) {
        throw "$path was not updated correctly. Missing: $expected"
    }
}

function Assert-FileMatches([string]$path, [string]$pattern, [string]$description) {
    $content = Get-Content -LiteralPath $path -Raw -Encoding UTF8
    if (-not [regex]::IsMatch($content, $pattern)) {
        throw "$path was not updated correctly. Missing: $description"
    }
}

$currentVersion = Get-CurrentVersion
if ([string]::IsNullOrWhiteSpace($Version)) {
    $Version = Get-NextVersion $currentVersion $Bump
    Write-Host "Auto version: $Version ($Bump bump)" -ForegroundColor Green
} else {
    if ([version]$Version -le $currentVersion) {
        throw "Version $Version must be greater than current version $currentVersion."
    }
    Write-Host "Manual version: $Version" -ForegroundColor Green
}

$tag = $Version
$currentBranch = (& git branch --show-current).Trim()
if ($LASTEXITCODE -ne 0 -or $currentBranch -ne $Branch) {
    throw "Release must run on branch $Branch; current branch is $currentBranch."
}

$localTag = & git tag --list $tag
if ($LASTEXITCODE -ne 0) {
    throw "Unable to inspect local tags."
}
if ($localTag) {
    throw "Tag $tag already exists locally. Choose a new version."
}

$remoteTag = & git ls-remote --tags $Remote "refs/tags/$tag"
if ($LASTEXITCODE -ne 0) {
    throw "Unable to inspect tags on $Remote."
}
if ($remoteTag) {
    throw "Tag $tag already exists on $Remote. Choose a new version."
}

$statusBefore = @(& git status --porcelain | Where-Object { $_ -notmatch '^\?\? \.vscode/' })
if ($LASTEXITCODE -ne 0) {
    throw "Unable to inspect the working tree."
}
if ($statusBefore -and -not $AllowDirty) {
    Write-Host "Working tree has uncommitted changes:" -ForegroundColor Yellow
    git status --short
    throw "Re-run with -AllowDirty to include current changes in the release commit."
}

Replace-InFile $versionFile {
    param($content)
    return $content -replace '(?m)^(ext\.xpopup_version\s*=\s*")[^"]+("\s*)$', "`${1}$Version`${2}"
}

Replace-InFile "README.md" {
    param($content)
    $content = $content -replace '(img\.shields\.io/badge/version-).*?(-brightgreen\.svg)', "`${1}$Version`${2}"
    $content = $content -replace "(implementation 'com\.github\.wukuiqing49:XPopup:)[^']+(')", "`${1}$Version`${2}"
    $content = $content -replace '(?m)^(### [^\d\r\n]+)\d+\.\d+\.\d+(\s*)$', "`${1}$Version`${2}"
    $content = $content -replace '(?m)^(- JitPack[^`\r\n]{1,3}`com\.github\.wukuiqing49:XPopup:)[^`]+(`[^A-Za-z0-9\r\n]*\s*)$', "`${1}$Version`${2}"
    return $content
}

Replace-InFile "README-en.md" {
    param($content)
    $content = $content -replace '(img\.shields\.io/badge/version-).*?(-brightgreen\.svg)', "`${1}$Version`${2}"
    $content = $content -replace "(implementation 'com\.github\.wukuiqing49:XPopup:)[^']+(')", "`${1}$Version`${2}"
    $content = $content -replace '(?m)^(### Current release: )\d+\.\d+\.\d+(\s*)$', "`${1}$Version`${2}"
    $content = $content -replace '(?m)^(- JitPack: `com\.github\.wukuiqing49:XPopup:)[^`]+(`\.\s*)$', "`${1}$Version`${2}"
    $content = $content -replace '(?m)^(Version )\d+\.\d+\.\d+( supports `minSdk)', "`${1}$Version`${2}"
    return $content
}

Assert-FileContains $versionFile "ext.xpopup_version = `"$Version`""
foreach ($readme in @("README.md", "README-en.md")) {
    Assert-FileContains $readme "badge/version-$Version-brightgreen.svg"
    Assert-FileContains $readme "implementation 'com.github.wukuiqing49:XPopup:$Version'"
    Assert-FileContains $readme ".\scripts\release-xpopup.ps1 -Bump patch"
    Assert-FileContains $readme ".\scripts\release-xpopup.ps1 -Bump minor -AllowDirty"
    Assert-FileContains $readme "-SkipPush"
}
$escapedVersion = [regex]::Escape($Version)
Assert-FileMatches "README.md" "(?m)^### [^\d\r\n]+$escapedVersion\s*$" "current release heading $Version"
Assert-FileMatches "README.md" "(?m)^- JitPack[^\x60\r\n]{1,3}\x60com\.github\.wukuiqing49:XPopup:$escapedVersion\x60[^A-Za-z0-9\r\n]*\s*$" "current JitPack coordinate $Version"
Assert-FileContains "README-en.md" "### Current release: $Version"
Assert-FileMatches "README-en.md" "(?m)^- JitPack: \x60com\.github\.wukuiqing49:XPopup:$escapedVersion\x60\.\s*$" "current JitPack coordinate $Version"
Assert-FileMatches "README-en.md" "(?m)^Version $escapedVersion supports \x60minSdk 21\x60" "current compatibility version $Version"

$gradleArguments = @(
    "--no-daemon",
    "--max-workers=1",
    ":library:testDebugUnitTest",
    ":library:lintDebug",
    ":app:lintDebug",
    ":app:assembleDebug",
    ":library:assembleRelease",
    ":library:publishReleasePublicationToMavenLocal",
    "--console=plain"
)
Invoke-CheckedCommand ".\gradlew.bat" $gradleArguments

if ($AllowDirty) {
    Invoke-CheckedCommand "git" @("add", "-A", "--", ".", ":(exclude).vscode", ":(exclude).vscode/**")
} else {
    Invoke-CheckedCommand "git" @("add", $versionFile, "README.md", "README-en.md")
}

& git diff --cached --quiet
if ($LASTEXITCODE -eq 0) {
    throw "No changes to release."
}
if ($LASTEXITCODE -ne 1) {
    throw "Unable to inspect staged release changes."
}

Invoke-CheckedCommand "git" @("commit", "-m", "release XPopup $tag")
Invoke-CheckedCommand "git" @("tag", $tag)

if (-not $SkipPush) {
    Invoke-CheckedCommand "git" @("push", "--atomic", $Remote, $Branch, "refs/tags/$tag")
}

Write-Host ""
Write-Host "Release prepared: $tag" -ForegroundColor Green
Write-Host "JitPack: https://jitpack.io/#$githubRepository/$tag"
Write-Host "Dependency: $groupId`:$artifactId`:$tag"
