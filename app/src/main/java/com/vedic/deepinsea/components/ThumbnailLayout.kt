package com.vedic.deepinsea.components

import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.indicators.CircularProgressBar
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.data.models.PhotoUrls
import com.vedic.deepinsea.icons.AppIcons
import com.vedic.deepinsea.utils.shimmerLoadingAnimation

@Composable
fun WallpaperCardLayout(photoUrls: PhotoUrls?, aboutPhoto: AboutPhoto, imageOnClick: (AboutPhoto) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)


    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrls?.original ?: aboutPhoto.image)
                .crossfade(true)
                .build(),
            contentDescription = aboutPhoto.alt,
            modifier = Modifier
                .clickable(onClick = {
                    imageOnClick(aboutPhoto)
                    Log.d("Abhishek", "click image url ${photoUrls?.original}")
                })
                .fillMaxWidth()
                .height(210.dp)
                .clip(RoundedCornerShape(8))
                .padding(2.dp)
                .shimmerLoadingAnimation(),
            contentScale = ContentScale.Crop,
        )
    }
    aboutPhoto.image?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = AppIcons.playButton,
                contentDescription = stringResource(R.string.video_icon),
                modifier = Modifier.height(48.dp).width(48.dp).clip(
                    RoundedCornerShape(50)
                ).background(Color.White)
            )
        }
    }
}


fun photoUrls(): PhotoUrls {
    return PhotoUrls(
        original = "https://images.pexels.com/photos/15272613/pexels-photo-15272613.jpeg",
        landscape = null,
        portrait = null,
        large = null,
        large2x = null,
        medium = null,
        small = null,
        tiny = null
    )
}

fun aboutPhoto(): AboutPhoto {
    return AboutPhoto(
        alt = "Abhishek Rawat dsfgds ggdfg sdgdsg sdg sd gdsg sdg sgsd gds gsg dfg fgdfg fgdf dfgd dfgdfg dfgdf dfg fdg ",
        avgColor = null,
        height = null,
        id = null,
        liked = null,
        photographer = "Abhishek",
        photographerId = null,
        photographerUrl = null, src = photoUrls(),
        url = null,
        width = null
    )
}

object AppIconButtonDefaults {
    // TODO: File bug
    // IconToggleButton disabled container alpha not exposed by IconButtonDefaults
    const val DisabledIconButtonContainerAlpha = 0.12f
}


@Composable
@Preview(uiMode = 32)
fun WallpaperCardLayoutPreview() {
    WallpaperCardLayout(photoUrls(), aboutPhoto(), imageOnClick = {
        "19620956"
    })
}
