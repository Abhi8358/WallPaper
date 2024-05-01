package com.vedic.deepinsea.utils


import androidx.compose.ui.graphics.vector.ImageVector
import com.vedic.deepinsea.R
import com.vedic.deepinsea.icons.AppIcons


/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class PrimaryDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = AppIcons.home,
        unselectedIcon = AppIcons.homeOutlined,
        iconTextId = R.string.home_icon_text,
        titleTextId = R.string.home_title,
    ),
    VIDEOS(
        selectedIcon = AppIcons.videos,
        unselectedIcon = AppIcons.videoOutlined,
        iconTextId = R.string.interestsr_string_videos,
        titleTextId = R.string.interestsr_string_videos,
    ),
    COLLECTIONS(
        selectedIcon = AppIcons.collection,
        unselectedIcon = AppIcons.collectionOutline,
        iconTextId = R.string.collections_icon_text,
        titleTextId = R.string.collections_icon_text,
    ),

}
