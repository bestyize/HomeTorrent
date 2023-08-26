package com.home.torrent.main.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.home.torrent.main.page.splash.page.SplashPage
import com.home.torrent.setting.theme.ThemeId
import com.home.torrent.setting.theme.ThemeManager
import com.home.torrent.ui.theme.AppTheme
import com.home.torrent.ui.theme.LocalColors


class MainActivity : AppCompatActivity() {

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
                val showSplashPage = remember {
                    mutableStateOf(true)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(), color = LocalColors.current.Bg1
                ) {
                    if (showSplashPage.value) {
                        SplashPage {
                            showSplashPage.value = false
                        }
                    } else {
                        MainPage()
                    }

                }
            }

        }


    }

}

