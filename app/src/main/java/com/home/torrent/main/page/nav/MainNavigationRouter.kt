package com.home.torrent.main.page.nav

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination.Companion.createRoute
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.home.torrent.main.page.MainPage
import com.home.torrent.main.page.splash.page.SplashPage
import com.home.torrent.setting.page.MainSettingPage
import com.thewind.community.detail.PosterDetailPage
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.widget.animation.slideInFromRight
import com.thewind.widget.animation.slideOutToRight

@Composable
fun MainNavigationRouter() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Router.PAGE_SPLASH) {
        composable(route = Router.PAGE_SPLASH) {
            SplashPage {
                navController.popBackStack()
                navController.navigate(Router.PAGE_MAIN) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = Router.PAGE_MAIN) {
            MainPage { url, bundle ->
                navController.navigate(url)
            }
        }
        composable(
            route = Router.PAGE_SETTING,
            deepLinks = listOf(navDeepLink { uriPattern = Router.PAGE_SETTING }),
            enterTransition = slideInFromRight,
            exitTransition = slideOutToRight
        ) {
            MainSettingPage()
        }

        composable(route = Router.PAGE_RECOMMEND_DETAIL, enterTransition = slideInFromRight, exitTransition = slideOutToRight) {
            val poster: RecommendPoster? = it.arguments?.getParcelable("poster_content", RecommendPoster::class.java)
            val posterId = it.arguments?.getLong("poster_id") ?: 0L
            PosterDetailPage(posterId = posterId, poster = poster)
        }

    }
}