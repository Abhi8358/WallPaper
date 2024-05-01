package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vedic.deepinsea.ui.screens.VideoScreenRoute

const val videoScreenNavigationRoute = "videos_screen_route"

fun NavController.navigateToVideos(navOptions: NavOptions? = null) {
    this.navigate(videoScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.videosScreen() {
    composable(
        route = videoScreenNavigationRoute,
    ) {
        VideoScreenRoute()
    }
}