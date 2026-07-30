## XPopup

[![](https://jitpack.io/v/wukuiqing49/XPopup.svg)](https://jitpack.io/#wukuiqing49/XPopup) ![](https://img.shields.io/badge/platform-android-blue.svg) ![](https://img.shields.io/badge/version-3.3.0-brightgreen.svg) ![](https://img.shields.io/badge/compileSdk-36-blue.svg) ![](https://img.shields.io/badge/minSdk-21-blue.svg) ![](https://img.shields.io/hexpm/l/plug.svg)

![](screenshot/logo.png)

### Current release: 3.3.0

- JitPack: `com.github.wukuiqing49:XPopup:3.3.0`.
- The Library uses `compileSdk 36` and supports Android 5.0+ (`minSdk 21`).
- AndroidX dependencies require consuming projects to use `compileSdk 35+`.
- Builds require JDK 17; published artifacts remain compatible with Java 8 bytecode and Kotlin 1.7.20+ consumers.

### English | [中文](README.md)

XPopup is an Android popup framework with Center, Bottom, Attach, Drawer, FullScreen, Position, and ImageViewer popup types. It supports custom layouts and animations, drag gestures, IME interaction, RTL, orientation changes, and multi-window mode.

## Installation

Add the JitPack repository:

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url = uri('https://jitpack.io') }
    }
}
```

Add XPopup:

```groovy
implementation 'com.github.wukuiqing49:XPopup:3.3.0'
```

Version 3.3.0 supports `minSdk 21`; consuming projects need `compileSdk 35+` because of AndroidX dependencies. AndroidX, Material, and RecyclerView dependencies are published transitively. Glide, EasyAdapter, and SubsamplingScaleImageView are not Library dependencies; image loading remains the host application's responsibility.

## Quick Start

```kotlin
XPopup.Builder(context)
    .asConfirm("Confirm", "Continue?") {
        // Confirmed
    }
    .show()
```

Extend the appropriate PopupView type for a custom popup and display it with `asCustom()`:

```kotlin
XPopup.Builder(context)
    .asCustom(MyPopup(context))
    .show()
```

## API 36 And Edge-To-Edge

For a fullscreen or drawer popup whose background extends behind system bars while foreground content stays clear of status bars, cutouts, and navigation bars, enable safe-area insets explicitly:

```kotlin
XPopup.Builder(context)
    .popupInsetMode(PopupInsetMode.SafeArea)
    .asCustom(MyFullScreenPopup(context))
    .show()
```

- `PopupInsetMode.Auto` preserves legacy layout behavior.
- `PopupInsetMode.SafeArea` keeps the background edge-to-edge and applies safe-area insets to foreground content.
- `PopupInsetMode.EdgeToEdge` leaves inset handling to the custom popup.

The Demo targets API 36 and covers Android 15/16 edge-to-edge enforcement, status bars, display cutouts, gesture and three-button navigation, landscape side navigation, and IME scenarios. The Library continues to support `minSdk 21`.

## Release Automation

The version is defined by `ext.xpopup_version` in the root `build.gradle`. Run the following command to bump the patch version and publish automatically:

```powershell
.\scripts\release-xpopup.ps1 -Bump patch
```

The script synchronizes `build.gradle` and both README files, then runs Library unit tests, App/Library Lint, the Debug APK build, the Release AAR build, and Maven Local publication verification. On success, it creates a release commit and matching tag and atomically pushes `master` and the tag to `origin`; JitPack builds the tagged release.

`-Bump` accepts `patch`, `minor`, or `major`. A version can also be supplied explicitly:

```powershell
.\scripts\release-xpopup.ps1 -Version 3.3.1
```

Additional options:

```powershell
# Create the local commit and tag without pushing
.\scripts\release-xpopup.ps1 -Bump patch -SkipPush

# Include current uncommitted changes, excluding .vscode
.\scripts\release-xpopup.ps1 -Bump minor -AllowDirty
```

## Documentation

- [Built-in popups](https://github.com/li-xiaojun/XPopup/wiki/2.-%E5%86%85%E7%BD%AE%E7%9A%84%E5%BC%B9%E7%AA%97%E5%AE%9E%E7%8E%B0)
- [Custom popups](https://github.com/li-xiaojun/XPopup/wiki/3.-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BC%B9%E7%AA%97)
- [Custom animations](https://github.com/li-xiaojun/XPopup/wiki/4.-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8A%A8%E7%94%BB)
- [Common settings](https://github.com/li-xiaojun/XPopup/wiki/5.-%E5%B8%B8%E7%94%A8%E8%AE%BE%E7%BD%AE)
- [FAQ](https://github.com/li-xiaojun/XPopup/wiki/6.-%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98(%E5%BF%85%E7%9C%8B))

## License

[Apache License 2.0](LICENSE)
