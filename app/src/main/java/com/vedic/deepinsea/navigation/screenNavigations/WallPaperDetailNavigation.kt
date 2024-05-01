package com.vedic.deepinsea.navigation.screenNavigations

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.screens.WallPaperDetailRoute
import com.vedic.deepinsea.utils.convertDataClassToJson
import com.vedic.deepinsea.utils.convertJsonToDataClass

const val wallpaperScreenNavigationRoute = "wallpaper_screen_route"
const val photoViewGraphRoutePattern = "photo_view_graph"
const val aboutPhotoParams = "aboutPhotos"

fun NavController.navigateToWallPaperDetail(
    navOptions: NavOptions? = null,
    aboutPhoto: AboutPhoto
) {
    val json = convertDataClassToJson<AboutPhoto>(aboutPhoto)
    Log.d("Abhishek", "josn $json")
    this.navigate("$wallpaperScreenNavigationRoute/$json", navOptions)
}

fun NavGraphBuilder.wallPaperScreen(
    onClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onSimilarItemClick: (AboutPhoto) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = photoViewGraphRoutePattern,
        startDestination = wallpaperScreenNavigationRoute
    ) {
        composable(
            route = "$wallpaperScreenNavigationRoute/{$aboutPhotoParams}",
            arguments = listOf(navArgument(aboutPhotoParams) { type = NavType.StringType })
        ) {
            val argument = it.arguments?.getString(aboutPhotoParams)
            val nameArgument = argument?.let { it1 -> convertJsonToDataClass<AboutPhoto>(it1) }
            val context: Context = LocalContext.current
            WallPaperDetailRoute(
                onClick,
                nameArgument = nameArgument,
                onBackClick = onBackClick,
                context = context,
                onSimilarItemClick = onSimilarItemClick
            )
        }
        nestedGraphs()
    }
}

