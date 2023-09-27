package com.mellowingfactory.wethm.ui.theme

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val lightColorScheme = lightColorScheme()

@Composable
fun WethmTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect { getSideEffect(view) }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = { content() }
    )
}


private fun getSideEffect(view: View){
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)

    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.Transparent.toArgb()
    window.navigationBarColor = Color.Transparent.toArgb()

    with(window) {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
    with(WindowCompat.getInsetsController(window, view)){
        isAppearanceLightStatusBars = true
        isAppearanceLightNavigationBars = true
    }
}