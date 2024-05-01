package com.vedic.deepinsea.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.vedic.deepinsea.components.indicators.CircularProgressBar
import com.vedic.deepinsea.data.models.UrlsWithName

@Composable
fun PhotoViewPagerLayout(
    photoUrls: UrlsWithName,
    imageOnClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrls.url)
                .crossfade(true)
                .build(),
            contentDescription = photoUrls.name,
            modifier = Modifier
                .clickable(onClick = {
                    imageOnClick(photoUrls.url)
                    Log.d("Abhishek", "click image url $photoUrls")
                })
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressBar()
            }
        )
    }
}

@Preview
@Composable
fun PhotoViewPagerLayoutPreview() {
    PhotoViewPagerLayout(
        UrlsWithName("Original",photoUrls().original!!), imageOnClick = {
            "19620956"
        },
    )
}