package com.vedic.deepinsea.utils

import android.net.Uri
import androidx.compose.material3.MaterialTheme
import com.google.gson.Gson
import com.vedic.deepinsea.data.models.PhotoUrls
import com.vedic.deepinsea.data.models.UrlsWithName
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

inline fun <reified T> convertJsonToDataClass(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

inline fun <reified T> convertDataClassToJson(data: T): String {
    return Uri.encode(Gson().toJson(data))
}

fun createPhotoUrlList(dataClass: PhotoUrls?): ArrayList<UrlsWithName> {
    val photoUrlsList = ArrayList<UrlsWithName>()
    dataClass?.let {
        dataClass.large2x?.let { photoUrlsList.add(UrlsWithName("Large2x", it)) }
        dataClass.large?.let { photoUrlsList.add(UrlsWithName("Large", it)) }
        dataClass.medium?.let { photoUrlsList.add(UrlsWithName("Medium", it)) }
        dataClass.original?.let { photoUrlsList.add(UrlsWithName("Original", it)) }
        dataClass.landscape?.let { photoUrlsList.add(UrlsWithName("Landscape", it)) }
        dataClass.portrait?.let { photoUrlsList.add(UrlsWithName("Portrait", it)) }
        dataClass.small?.let { photoUrlsList.add(UrlsWithName("Small", it)) }
        dataClass.tiny?.let { photoUrlsList.add(UrlsWithName("Tiny", it)) }
    }
    return photoUrlsList
}

enum class Type {
    Video,
    Photo
}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 22.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 200L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )
        }
    }

}