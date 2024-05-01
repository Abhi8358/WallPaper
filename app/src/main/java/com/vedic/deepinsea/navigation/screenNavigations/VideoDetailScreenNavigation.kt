package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.screens.VideoDetailScreenRoute
import com.vedic.deepinsea.ui.screens.VideoScreenRoute
import com.vedic.deepinsea.utils.convertDataClassToJson
import com.vedic.deepinsea.utils.convertJsonToDataClass

const val videoDetailScreenNavigationRoute = "video_screen_route"
private const val args_about_photo = "args_about_photo"

fun NavController.navigateToVideoDetail(navOptions: NavOptions? = null, aboutPhoto: AboutPhoto) {
    val json = convertDataClassToJson<AboutPhoto>(aboutPhoto)
    this.navigate("$videoDetailScreenNavigationRoute/$json", navOptions)
}

fun NavGraphBuilder.videosDetailScreen(onBackClick: () -> Unit,) {
    composable(
        route = "$videoDetailScreenNavigationRoute/{$args_about_photo}",
        arguments = listOf(navArgument(args_about_photo) { type = NavType.StringType })
    ) {
        val argument = it.arguments?.getString(args_about_photo)
        //val nameArgument = Gson().fromJson(argument, AboutPhoto::class.java)
        val nameArgument = argument?.let { it1 -> convertJsonToDataClass<AboutPhoto>(it1) }

        nameArgument?.let {
            VideoDetailScreenRoute(aboutPhoto = it, onBackClick = onBackClick)
        }

    }
}