package com.vedic.deepinsea.components

import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.indicators.DownloadingButton
import com.vedic.deepinsea.utils.downloadImageNew

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(uri: Uri, id: Int?, useControls: Boolean = false) {

    val context = LocalContext.current
    val startPlay = remember {
        mutableStateOf(true)
    }

    val isLoading = remember {
        mutableStateOf(true)
    }

    val videoFormate = stringResource(R.string.video_formate)
    val isDownloaded = remember {
        mutableStateOf(false)
    }
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))

                setMediaSource(source)
                prepare()
            }
    }
    if (startPlay.value) {
        exoPlayer.playWhenReady = true
    }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (video, loader, pause, download) = createRefs()
        DisposableEffect(
            key1 = AndroidView(
                modifier = Modifier
                    .background(color = Color.Blue)
                    .constrainAs(video) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                factory = {
                    val container = FrameLayout(context)

                    // Create the PlayerView
                    val playerView = PlayerView(context).apply {
                        useController = useControls
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    // Create the loader (ProgressBar for example)
                    val progressBar = ProgressBar(context).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply {
                            gravity = Gravity.CENTER
                        }
                        // You can customize the loader here
                    }

                    // Add the loader to the container
                    container.addView(playerView)
                    container.addView(progressBar)

                    // Listen to player state changes to show/hide the loader
                    exoPlayer.addListener(object : Player.Listener {
                        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                            progressBar.visibility = if (playbackState == Player.STATE_BUFFERING && playWhenReady) {
                                View.VISIBLE
                            } else {
                                View.GONE
                            }
                        }
                    })

                    // Return the container
                    container
                })
        ) {
            Log.d("Abhishek", "dispose")
            onDispose { exoPlayer.release() }
        }
      /*  if (exoPlayer.isPlaying) {
            isLoading.value = false
        } else if (exoPlayer.isLoading) {
            isLoading.value = true
        } else {
            isLoading.value = false
        }

        if (isLoading.value) {
            Log.d("Abhishek", "ProgressBar")
            CircularProgressBar(modifier = Modifier
                .constrainAs(loader) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        }*/
        if (!useControls) {
            Box(modifier = Modifier
                .fillMaxSize()
                .constrainAs(pause) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    if (startPlay.value) {
                        startPlay.value = false
                        exoPlayer.pause()
                    } else {
                        startPlay.value = true
                        exoPlayer.play()
                    }
                })
        }
        Box(
            modifier = Modifier
                .constrainAs(download) {
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }
                .clip(RoundedCornerShape(40.dp))
                .background(color = Color.DarkGray)
                .size(37.dp)) {
            DownloadingButton(
                checked = isDownloaded.value,
                onClick = {
                    if (!isDownloaded.value) {
                        isDownloaded.value = true
                        context.downloadImageNew("WallPaper_", uri, id, videoFormate)
                    }
                })
        }
    }
}