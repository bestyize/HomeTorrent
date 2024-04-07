package com.thewind.widget.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

val slideInFromRight: (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = {
    slideInHorizontally(animationSpec = tween(durationMillis = 200), initialOffsetX = {
        it
    })
}

val slideOutToRight: (@JvmSuppressWildcards
AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = {
    slideOutHorizontally(
        animationSpec = tween(durationMillis = 200),
        targetOffsetX = { it })
}