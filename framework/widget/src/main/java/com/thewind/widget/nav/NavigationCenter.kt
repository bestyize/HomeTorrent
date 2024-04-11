package com.thewind.widget.nav

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalMainNavigation = staticCompositionLocalOf<NavHostController> { error("not provider") }