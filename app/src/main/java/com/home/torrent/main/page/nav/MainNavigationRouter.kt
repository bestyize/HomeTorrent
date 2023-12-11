package com.home.torrent.main.page.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.home.torrent.main.page.MainPage
import com.home.torrent.main.page.splash.page.SplashPage

@Composable
fun MainNavigationRouter() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "/page/splash") {
        composable(route = "/page/splash") {
            SplashPage {
                navController.popBackStack()
                navController.navigate("/page/main") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = "/page/main") {
            MainPage()
        }
    }
}