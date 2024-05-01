package com.vedic.deepinsea.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.io.File

fun <T> LazyListScope.gridItems(
    data: List<T>?,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data?.count()?:0
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        data?.get(itemIndex)?.let { itemContent(it) }
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

fun Context.downloadImageNew(filename: String, downloadUrlOfImage: Uri, id: Int?, formate: String) {
    try {
        val dm: DownloadManager? = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        // val downloadUri = Uri.parse(downloadUrlOfImage)
        val request: DownloadManager.Request = DownloadManager.Request(downloadUrlOfImage)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            //.setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File.separator + filename + id + formate
            )
        dm?.enqueue(request)
        Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show()
    }
}

fun Modifier.shimmerLoadingAnimation(): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 0.3f),
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = 100f, y = 0.0f),
                end = Offset(x = 400f, y = 270f),
            ),
        )
    }
}