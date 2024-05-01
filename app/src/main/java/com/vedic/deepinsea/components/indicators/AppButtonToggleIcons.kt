package com.vedic.deepinsea.components.indicators

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vedic.deepinsea.icons.AppIcons

@Composable
fun DownloadingButton(
    checked: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppIconToggleButton(
        checked = checked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = AppIcons.download,
                contentDescription = "download",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = AppIcons.downloading,
                contentDescription = "downloading",
            )
        },
    )
}

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = AppIcons.bookMarkBorder,
                contentDescription = "bookmark",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = AppIcons.bookMark,
                contentDescription = "unbookmark",
            )
        },
    )
}



@Composable
fun ShareButton(
    isBookmarked: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = AppIcons.shareIcon,
                contentDescription = "bookmark",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = AppIcons.shareIcon,
                contentDescription = "unbookmark",
            )
        },
    )
}

@Composable
@Preview
fun BookmarkIconPrev() {
    BookmarkButton(isBookmarked = true, onClick = { /*TODO*/ })
}

@Composable
@Preview
fun ShareIconPrev() {
    ShareButton(isBookmarked = true, onClick = { /*TODO*/ })
}

/*
@Composable
@Preview
fun DownloadingButtonPreview() {
    DownloadingButton(buttonText = "Download", onClick = {})
}*/
