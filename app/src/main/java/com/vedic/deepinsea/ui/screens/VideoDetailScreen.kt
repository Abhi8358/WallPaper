package com.vedic.deepinsea.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vedic.deepinsea.components.AppBar
import com.vedic.deepinsea.components.VideoPlayer
import com.vedic.deepinsea.data.models.AboutPhoto

@Composable
fun VideoDetailScreenRoute(aboutPhoto: AboutPhoto, onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(48.dp)
        ) {
            AppBar(
                header = aboutPhoto.user?.name ?: "", modifier = Modifier, onBackClick = onBackClick
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            aboutPhoto.video_files?.let {
                it[0].link?.let {
                    VideoPlayer(uri = Uri.parse(it), id = aboutPhoto.id, useControls = true)
                }
            }
        }
    }

}