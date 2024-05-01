package com.vedic.deepinsea.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.vedic.deepinsea.navigation.AppNavHost
import com.vedic.deepinsea.navigation.AppNavigationBar
import com.vedic.deepinsea.navigation.AppNavigationBarItem
import com.vedic.deepinsea.utils.AppState
import com.vedic.deepinsea.utils.PrimaryDestination
import com.vedic.deepinsea.utils.rememberAppState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun AppStart(
    windowSizeClass: WindowSizeClass,
    appState: AppState = rememberAppState(
        windowSizeClass = windowSizeClass,
    ),
) {

    Scaffold(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            }
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomBar(
                    destinations = PrimaryDestination.entries,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier,
                )
            }

        },
    ) { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                )
                .background(MaterialTheme.colorScheme.primaryContainer),
        ) {
            AppNavHost(appState = appState)
        }
    }
}

@Composable
private fun BottomBar(
    destinations: List<PrimaryDestination>,
    onNavigateToDestination: (PrimaryDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    AppNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
//            val hasUnread = destinationsWithUnreadResources.contains(destination)
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier.height(72.dp),
            )
        }
    }
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: PrimaryDestination): Boolean {
    Log.d(
        "Rawat",
        "route name :- ${this?.route} , route name inside hierarchy -: ${this?.hierarchy?.toList()}"
    )
    return this?.route?.contains(destination.name, true) ?: false
    /*
        return this?.hierarchy?.any {
            it.route?.contains(destination.name, true) ?: false
        } ?: false*/
}

fun NavDestination?.isTopLevelDestinationInHierarchy(): Boolean {
    Log.d(
        "Rawat",
        "route name :- ${this?.route} , route name inside hierarchy -: ${this?.hierarchy?.toList()}"
    )
    //return this?.route?.contains(destination.name, true) ?: false

    PrimaryDestination.entries.forEach {
        if (this?.route?.contains(it.name, true) == true) return true
    }
    return false
    /*
        return this?.hierarchy?.any {
            it.route?.contains(destination.name, true) ?: false
        } ?: false*/
}