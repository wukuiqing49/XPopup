## XPopup

[![](https://jitpack.io/v/wukuiqing49/XPopup.svg)](https://jitpack.io/#wukuiqing49/XPopup) ![](https://img.shields.io/badge/platform-android-blue.svg) ![](https://img.shields.io/badge/version-3.3.0-brightgreen.svg) ![](https://img.shields.io/badge/compileSdk-36-blue.svg) ![](https://img.shields.io/badge/minSdk-21-blue.svg) ![](https://img.shields.io/hexpm/l/plug.svg)

![](screenshot/logo.png)

### 当前版本：3.3.0

- JitPack：`com.github.wukuiqing49:XPopup:3.3.0`。
- Library 使用 `compileSdk 36`，支持 Android 5.0 及以上系统（`minSdk 21`）。
- AndroidX 依赖要求接入项目使用 `compileSdk 35+`。
- 构建需要 JDK 17；发布产物保持 Java 8 字节码和 Kotlin 1.7.20+ 兼容性。

### 中文 | [English](README-en.md)

XPopup 是一个 Android 弹窗框架，提供 Center、Bottom、Attach、Drawer、FullScreen、Position、ImageViewer 等弹窗类型，支持自定义布局、动画、拖拽、输入法交互、RTL、横竖屏和小窗模式。

## 引入依赖

在项目中加入 JitPack 仓库：

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url = uri('https://jitpack.io') }
    }
}
```

加入 XPopup：

```groovy
implementation 'com.github.wukuiqing49:XPopup:3.3.0'
```

Library 的 AndroidX、Material 和 RecyclerView 依赖会由 Maven 自动传递。Glide、EasyAdapter 和 `subsampling-scale-image-view` 不属于 Library 依赖，图片加载由宿主应用自行实现。

## 快速使用

```kotlin
XPopup.Builder(context)
    .asConfirm("提示", "确定继续吗？") {
        // Confirmed
    }
    .show()
```

自定义弹窗继承对应的 PopupView，并通过 `asCustom()` 显示：

```kotlin
XPopup.Builder(context)
    .asCustom(MyPopup(context))
    .show()
```

## API 36 与沉浸式

全屏或 Drawer 弹窗需要背景延伸到系统栏、前景内容避开状态栏、刘海和导航栏时，显式启用安全区：

```kotlin
XPopup.Builder(context)
    .popupInsetMode(PopupInsetMode.SafeArea)
    .asCustom(MyFullScreenPopup(context))
    .show()
```

- `PopupInsetMode.Auto`：保持旧版本布局行为。
- `PopupInsetMode.SafeArea`：背景保持 edge-to-edge，前景内容应用系统安全区。
- `PopupInsetMode.EdgeToEdge`：由自定义弹窗自行处理 Insets。

Demo 使用 `targetSdk 36`，覆盖 Android 15/16 强制 edge-to-edge、状态栏、显示刘海、手势/三键导航、横屏侧边导航和输入法场景。Library 继续保持 `minSdk 21`。

## 发布

版本号定义在根目录 `build.gradle` 的 `ext.xpopup_version`。执行以下命令自动升级补丁版本并发布：

```powershell
.\scripts\release-xpopup.ps1 -Bump patch
```

脚本会同步更新 `build.gradle` 和中英文 README，随后执行 Library 单元测试、App/Library Lint、Debug APK、Release AAR 和 Maven Local 发布验证。验证通过后创建 release commit 和同名 tag，并将 `master` 与 tag 原子推送到 `origin`；JitPack 根据 tag 构建发布产物。

`-Bump` 支持 `patch`、`minor` 和 `major`，也可以指定版本：

```powershell
.\scripts\release-xpopup.ps1 -Version 3.3.1
```

辅助选项：

```powershell
# 只创建本地 commit 和 tag
.\scripts\release-xpopup.ps1 -Bump patch -SkipPush

# 将当前未提交改动纳入发版提交，但排除 .vscode
.\scripts\release-xpopup.ps1 -Bump minor -AllowDirty
```

## 文档

- [内置弹窗](https://github.com/li-xiaojun/XPopup/wiki/2.-%E5%86%85%E7%BD%AE%E7%9A%84%E5%BC%B9%E7%AA%97%E5%AE%9E%E7%8E%B0)
- [自定义弹窗](https://github.com/li-xiaojun/XPopup/wiki/3.-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%BC%B9%E7%AA%97)
- [自定义动画](https://github.com/li-xiaojun/XPopup/wiki/4.-%E5%A6%82%E4%BD%95%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8A%A8%E7%94%BB)
- [常用设置](https://github.com/li-xiaojun/XPopup/wiki/5.-%E5%B8%B8%E7%94%A8%E8%AE%BE%E7%BD%AE)
- [常见问题](https://github.com/li-xiaojun/XPopup/wiki/6.-%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98(%E5%BF%85%E7%9C%8B))

## License

[Apache License 2.0](LICENSE)
