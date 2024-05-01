package com.vedic.deepinsea.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vedic.deepinsea.data.models.UrlsWithName
import com.vedic.deepinsea.icons.AppIcons
import com.vedic.deepinsea.icons.AppIcons.downArrow
import com.vedic.deepinsea.icons.AppIcons.upArrow


@Composable
fun DownloadingSection(urlsList: List<UrlsWithName>, onDownloadClick: (url: UrlsWithName) -> Unit) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    var iconState by remember {
        mutableStateOf(AppIcons.upArrow)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .animateContentSize()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                val (downloadText, arrowIcon) = createRefs()
                Text(
                    modifier = Modifier.constrainAs(downloadText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = 6.dp)
                        bottom.linkTo(parent.bottom)
                    },
                    text = "Download WallPapers",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.Bold
                )

/*                Box(
                    modifier = Modifier
                        .constrainAs(arrowIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.value(56.dp)
                        }
                        .clickable {
                            isVisible = !isVisible
                            if (isVisible) {
                                iconState = Icons.Rounded.KeyboardArrowDown
                            } else {
                                iconState = Icons.Rounded.KeyboardArrowUp
                            }
                        },

                    ) {*/
                Icon(
                    modifier = Modifier
                        .constrainAs(arrowIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.value(56.dp)
                            height = Dimension.matchParent
                        }
                        .clickable {
                            isVisible = !isVisible
                            if (isVisible) {
                                iconState = downArrow
                            } else {
                                iconState = upArrow
                            }
                        }
                        .padding(top = 8.dp, bottom = 8.dp),
                    imageVector = iconState,
                    contentDescription = "DownLoading",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                //}
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            )

            if (isVisible) {
                urlsList.forEach { urlsWithName ->
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                                DownloadingUrlSection(urlsWithName, onDownloadClick)
                    }
                }
                /*BoxWithConstraints(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        items(urlsList.size) {
                            DownloadingUrlSection(urlsList[it], onDownloadClick)
                        }
                    }
                }*/
            }
        }
    }
}


@Composable
fun DownloadingUrlSection(urlsWithName: UrlsWithName, onDownloadClick: (url: UrlsWithName) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clickable {
                onDownloadClick(urlsWithName)
            }
    ) {
        val (text, icon) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(top = 2.dp, bottom = 2.dp),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight.SemiBold,
            text = urlsWithName.name
        )
        IconButton(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.value(64.dp)
                }
                .padding(2.dp),
            onClick = { onDownloadClick(urlsWithName) },

            ) {
            Icon(
                imageVector = AppIcons.download,
                contentDescription = urlsWithName.name
            )
        }

    }
}

@Preview
@Composable
fun DownloadingSectionPreview() {
    DownloadingSection(
        urlsList = listOf(
            UrlsWithName("Abhi", "gdfg"),
            UrlsWithName("Abhi", "gdfg"),
            UrlsWithName("Abhi", "gdfg")
        ), onDownloadClick = {})
}

@Composable
@Preview
fun DownloadingUrlSectionPreview() {
    DownloadingUrlSection(UrlsWithName("Abhi", "gdfg"), onDownloadClick = {})
}