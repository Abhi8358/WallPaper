package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.vedic.deepinsea.data.models.Collection
import com.vedic.deepinsea.ui.screens.CollectionsScreenRoutes

const val collectionScreenNavigationRoute = "collections_screen_route"

fun NavController.navigateToCollections(navOptions: NavOptions? = null) {
    this.navigate(collectionScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.collectionsScreen(
    onClick: (Collection) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    composable(
        route = collectionScreenNavigationRoute,
    ) {
        CollectionsScreenRoutes(onClick = onClick)
    }
    nestedGraphs()
}