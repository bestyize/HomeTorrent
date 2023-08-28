package com.thewind.community.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thewind.widget.theme.AppTheme
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.theme.ThemeId
import com.thewind.widget.theme.ThemeManager

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme =
                ThemeManager.themeFlow.collectAsStateWithLifecycle(initialValue = ThemeId.AUTO)
            val isNight = when (theme.value) {
                ThemeId.AUTO -> isSystemInDarkTheme()
                ThemeId.NIGHT -> true
                ThemeId.DAY -> false
            }
            AppTheme(darkTheme = isNight) {
                DetailPage(modifier = Modifier
                    .fillMaxSize()
                    .background(LocalColors.current.Bg2))
            }

        }
    }
}