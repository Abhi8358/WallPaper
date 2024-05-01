package com.vedic.deepinsea.navigation.screenNavigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.screens.CollectionDetailRoute
import com.vedic.deepinsea.utils.convertDataClassToJson
import com.vedic.deepinsea.utils.convertJsonToDataClass

const val collectionDetailScreenNavigationRoute = "collection_detail_screen_route"
private const val args_id = "args_id"
private const val args_name = "args_name"
fun NavController.navigateToCollectionsDetail(
    navOptions: NavOptions? = null,
    id: String,
    name: String,
) {
    val json = convertDataClassToJson<String>(id)
    val json2 = convertDataClassToJson<String>(name)
    this.navigate("$collectionDetailScreenNavigationRoute/$json/$json2", navOptions)
}

fun NavGraphBuilder.collectionsDetailScreen(imageOnClick: (AboutPhoto) -> Unit, onBackClick: () -> Unit) {
    composable(
        route = "$collectionDetailScreenNavigationRoute/{$args_id}/{$args_name}",
        arguments = listOf(navArgument(args_id) { type = NavType.StringType }, navArgument(args_name) { type = NavType.StringType })
    ) {
        val argumentId = it.arguments?.getString(args_id)
        val argumentName = it.arguments?.getString(args_name)
        //val nameArgument = Gson().fromJson(argument, AboutPhoto::class.java)
        val idArgument = argumentId?.let { it1 -> convertJsonToDataClass<String>(it1) }
        val nameArgument = argumentName?.let { it1 -> convertJsonToDataClass<String>(it1) }
        idArgument?.let {
            CollectionDetailRoute(id = it, name = nameArgument ?: "", onBackClick = onBackClick, imageOnClick = imageOnClick)
        }
    }
}