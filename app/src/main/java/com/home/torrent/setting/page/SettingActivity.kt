package com.home.torrent.setting.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.home.torrent.ui.theme.AppTheme
import com.home.torrent.ui.theme.LocalColors
import com.tencent.mmkv.MMKV

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val auto = MMKV.defaultMMKV().decodeBool("theme_mode_auto", true)
            val night = MMKV.defaultMMKV().decodeBool("theme_mode_user_dark", false)
            AppTheme(darkTheme = if (auto) isSystemInDarkTheme() else night) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = LocalColors.current.Bg2
                ) {
                    MainSettingPage()
                }
            }
        }
    }
}