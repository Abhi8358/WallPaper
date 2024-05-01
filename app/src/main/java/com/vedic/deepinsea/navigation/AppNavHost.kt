package com.vedic.deepinsea.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.vedic.deepinsea.navigation.screenNavigations.collectionsDetailScreen
import com.vedic.deepinsea.navigation.screenNavigations.collectionsScreen
import com.vedic.deepinsea.navigation.screenNavigations.homeScreen
import com.vedic.deepinsea.navigation.screenNavigations.homeScreenRoutePattern
import com.vedic.deepinsea.navigation.screenNavigations.navigateToCollectionsDetail
import com.vedic.deepinsea.navigation.screenNavigations.navigateToPhotoView
import com.vedic.deepinsea.navigation.screenNavigations.navigateToVideoDetail
import com.vedic.deepinsea.navigation.screenNavigations.navigateToWallPaperDetail
import com.vedic.deepinsea.navigation.screenNavigations.photoViewScreen
import com.vedic.deepinsea.navigation.screenNavigations.videosDetailScreen
import com.vedic.deepinsea.navigation.screenNavigations.videosScreen
import com.vedic.deepinsea.navigation.screenNavigations.wallPaperScreen
import com.vedic.deepinsea.utils.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeScreenRoutePattern
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier,
    ) {
        // TODO: handle topic clicks from each top level destination
        homeScreen(
            imageOnClick = {
                navController.navigateToWallPaperDetail(aboutPhoto = it)
            },
            nestedGraphs = {
                wallPaperScreen(
                    onClick = {
                        navController.navigateToPhotoView(imageUrl = it)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSimilarItemClick = { aboutPhoto ->
                        navController.navigateToWallPaperDetail(aboutPhoto = aboutPhoto)
                    },
                    nestedGraphs = {
                        photoViewScreen(onBackClick = {
                            navController.popBackStack()
                        })
                    }
                )
            }
        )
        collectionsScreen(
            onClick = {
                it.id?.let { id ->
                    navController.navigateToCollectionsDetail(id = id, name = it.title ?: "")
                }
            },
            nestedGraphs = {
                collectionsDetailScreen(imageOnClick = { aboutPhoto ->
                    if (aboutPhoto.src != null) {
                        navController.navigateToWallPaperDetail(aboutPhoto = aboutPhoto)
                    } else {
                        aboutPhoto.image?.let {
                            navController.navigateToVideoDetail(aboutPhoto = aboutPhoto)
                        }
                    }
                },
                    onBackClick = {
                        navController.popBackStack()
                    })
            })
        videosDetailScreen {
            navController.popBackStack()
        }
        videosScreen()
    }

}