package com.thewind.widget.nav

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val MainNavigation = staticCompositionLocalOf<NavHostController> { error("not provider") }