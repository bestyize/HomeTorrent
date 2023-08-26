package com.home.torrent.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.thewind.widget.utils.transportStatusBar

/**
 * @author: read
 * @date: 2023/8/25 上午12:09
 * @description:
 */


val LocalColors = staticCompositionLocalOf { dayThemeColors }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {

    val customColorsPalette = if (darkTheme) nightThemeColors
    else dayThemeColors
    (LocalContext.current as Activity).transportStatusBar(!darkTheme)
    CompositionLocalProvider(
        LocalColors provides customColorsPalette
    ) {
        MaterialTheme(
            content = content
        )
    }
}
