package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.screens.HomeScreenRoute
const val homeScreenRoutePattern = "top_screen_detail_graph"
const val homeScreenNavigationRoute = "home_screen_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeScreenRoutePattern, navOptions)
}

fun NavGraphBuilder.homeScreen(imageOnClick: (AboutPhoto) -> Unit,nestedGraphs: NavGraphBuilder.() -> Unit) {
    navigation(
        route = homeScreenRoutePattern,
        startDestination = homeScreenNavigationRoute
    ) {
        composable(
            route = homeScreenNavigationRoute,
        ) {
            HomeScreenRoute(imageOnClick)
        }
        nestedGraphs()
    }
}