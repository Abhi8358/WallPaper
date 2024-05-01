package com.vedic.deepinsea.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.indicators.CircularProgressBar
import com.vedic.deepinsea.components.photo.PhotoBox
import com.vedic.deepinsea.icons.AppIcons

@Composable
fun PhotoViewScreenRoute(onBack: () -> Unit, imageUrl: String) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        val (backButton, photoImage) = createRefs()

        PhotoBox(
            modifier = Modifier
                .constrainAs(photoImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                }) {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "image in photo view",
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressBar()
                })
        }

        Icon(
            modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top, margin = 9.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
                .clickable { onBack() },
            imageVector = AppIcons.backArrow,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(id = R.string.back_button),
        )
    }
}