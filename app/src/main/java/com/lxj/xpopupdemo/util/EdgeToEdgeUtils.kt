package com.lxj.xpopupdemo.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

internal fun AppCompatActivity.applyEdgeToEdgeInsets() {
    val content = findViewById<View>(android.R.id.content)
    val initialLeft = content.paddingLeft
    val initialTop = content.paddingTop
    val initialRight = content.paddingRight
    val initialBottom = content.paddingBottom
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, content).apply {
        isAppearanceLightStatusBars = true
        isAppearanceLightNavigationBars = true
    }

    ViewCompat.setOnApplyWindowInsetsListener(content) { view, windowInsets ->
        val safeInsets = windowInsets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.setPadding(
            initialLeft + safeInsets.left,
            initialTop + safeInsets.top,
            initialRight + safeInsets.right,
            initialBottom + safeInsets.bottom
        )
        windowInsets
    }
    ViewCompat.requestApplyInsets(content)
}
