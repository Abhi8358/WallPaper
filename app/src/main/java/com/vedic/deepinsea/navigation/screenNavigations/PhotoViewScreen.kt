package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.screens.PhotoViewScreenRoute
import com.vedic.deepinsea.utils.convertDataClassToJson
import com.vedic.deepinsea.utils.convertJsonToDataClass

const val photoViewScreenNavigationRoute = "photo_view_screen_route"
const val imageUlrArg = "image_url_arg"

fun NavController.navigateToPhotoView(navOptions: NavOptions? = null, imageUrl: String) {
    val json = convertDataClassToJson<String>(imageUrl)
    this.navigate("$photoViewScreenNavigationRoute/$json", navOptions)
}

fun NavGraphBuilder.photoViewScreen(onBackClick: () -> Unit) {
    composable(
        route = "$photoViewScreenNavigationRoute/{$imageUlrArg}",
        arguments = listOf(navArgument(imageUlrArg) { type = NavType.StringType })
    ) {

        val argument = it.arguments?.getString(imageUlrArg)
        //val nameArgument = Gson().fromJson(argument, AboutPhoto::class.java)
        val nameArgument = argument?.let { it1 -> convertJsonToDataClass<String>(it1) }

        if (nameArgument != null) {
            PhotoViewScreenRoute(onBackClick, nameArgument)
        }
    }
}