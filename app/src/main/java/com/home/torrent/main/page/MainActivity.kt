package com.home.torrent.main.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.home.torrent.main.page.splash.page.SplashPage
import com.home.torrent.ui.theme.AppTheme
import com.home.torrent.ui.theme.LocalColors


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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

