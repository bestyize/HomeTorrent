package com.home.torrent.setting.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thewind.widget.theme.AppTheme
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.theme.ThemeId
import com.thewind.widget.theme.ThemeManager

class SettingActivity : AppCompatActivity() {
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
                Surface(
                    modifier = Modifier.fillMaxSize(), color = LocalColors.current.Bg1
                ) {
                    MainSettingPage()
                }
            }
        }
    }
}