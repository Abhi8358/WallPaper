package com.vedic.deepinsea.utils

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.vedic.deepinsea.navigation.screenNavigations.collectionScreenNavigationRoute
import com.vedic.deepinsea.navigation.screenNavigations.homeScreenNavigationRoute
import com.vedic.deepinsea.navigation.screenNavigations.navigateToCollections
import com.vedic.deepinsea.navigation.screenNavigations.navigateToHome
import com.vedic.deepinsea.navigation.screenNavigations.navigateToVideos
import com.vedic.deepinsea.navigation.screenNavigations.videoScreenNavigationRoute
import com.vedic.deepinsea.ui.screens.isTopLevelDestinationInHierarchy
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    //NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        AppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}

@Preview
@Composable
fun getRememberNiaAppState() {
    //rememberNiaAppState()
}

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: PrimaryDestination?
        @Composable get() = when (currentDestination?.route) {
            homeScreenNavigationRoute -> PrimaryDestination.HOME
            collectionScreenNavigationRoute -> PrimaryDestination.COLLECTIONS
            videoScreenNavigationRoute -> PrimaryDestination.VIDEOS
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable
        get() {
            return currentDestination.isTopLevelDestinationInHierarchy()
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<PrimaryDestination> = PrimaryDestination.entries

    /**
     * The top level destinations that have unread news resources.
     */
    /*val topLevelDestinationsWithUnreadResources: StateFlow<Set<PrimaryDestination>> =
        userNewsResourceRepository.observeAllForFollowedTopics()
            .combine(userNewsResourceRepository.observeAllBookmarked()) { forYouNewsResources, bookmarkedNewsResources ->
                setOfNotNull(
                    FOR_YOU.takeIf { forYouNewsResources.any { !it.hasBeenViewed } },
                    BOOKMARKS.takeIf { bookmarkedNewsResources.any { !it.hasBeenViewed } },
                )
            }.stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet(),
            )
*/
    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: PrimaryDestination) {

        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            PrimaryDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            PrimaryDestination.COLLECTIONS -> navController.navigateToCollections(topLevelNavOptions)
            PrimaryDestination.VIDEOS -> navController.navigateToVideos(topLevelNavOptions)
        }

    }

    /*fun navigateToSearch() {
        navController.navigateToSearch()
    }*/
}
