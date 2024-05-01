package com.vedic.deepinsea.ui.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.VideoPlayer
import com.vedic.deepinsea.components.error.ErrorScreen
import com.vedic.deepinsea.components.indicators.CircularProgressBar
import com.vedic.deepinsea.ui.viewModels.VideoViewModel
import com.vedic.deepinsea.utils.ResourceState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoScreenRoute(
    videoViewModel: VideoViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
    ) {
        videoViewModel.videoList.size
    }

    val videos = videoViewModel.videos.collectAsState()
//    when (videos.value) {
//        is ResourceState.Success -> {
            val shouldStartPaginate = remember {
                derivedStateOf {
                    videoViewModel.canPaginate && videoViewModel.videoList.isNotEmpty() && ((pagerState.currentPage + 4) > pagerState.pageCount)
                }
            }
            Log.d(
                "Abhishek",
                "page_count = ${pagerState.pageCount}  current_page = ${pagerState.currentPage}"
            )
            LaunchedEffect(key1 = shouldStartPaginate.value) {
                if (shouldStartPaginate.value) {
                    videoViewModel.getVideos()
                }
            }
      //  }

//        else -> {
//
//        }
//    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp
    ) { page: Int ->
        val videoList = videoViewModel.videoList
        val video = videoList[page]

        video.video_files?.let { it ->
            it[0]?.let { videoFile ->
                videoFile.link?.let {
                    Box(modifier = Modifier.fillMaxSize()) {
                        VideoPlayer(uri = Uri.parse(it), video.id)
                    }
                }
            }
        }
    }

    when (videos.value) {
        is ResourceState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Refresh Loading"
                )
                Log.d("Abhishek", "inside Loading")
                CircularProgressBar()
            }
        }

        is ResourceState.PaginationLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    CircularProgressBar()
                }
            }
        }

        is ResourceState.Error -> {

            ErrorScreen(
                isInternetConnected = videoViewModel.isInternetConnected(),
                isEmptyList = videoViewModel.videoList.isEmpty(),
                pagingModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                if (videoViewModel.isInternetConnected()) {
                    videoViewModel.getVideos()
                } else {
                    Toast.makeText(
                        ctx,
                        ctx.getString(R.string.connect_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            /*
                        if (!videoViewModel.isInternetConnected() && videoViewModel.videoList.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                PaginationInternetError(
                                    errorText = "Please connect internet",
                                    onRetryClick = {
                                        if (videoViewModel.isInternetConnected()) {
                                            videoViewModel.getVideos()
                                        } else {
                                            Toast.makeText(
                                                ctx,
                                                context.getString(R.string.connect_internet),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                            }
                        } else if (!videoViewModel.isInternetConnected()) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                InternetError(modifier = Modifier
                                    .wrapContentSize(),
                                    contentText = "Oops, looks like there's no Internet connection",
                                    id = R.drawable.no_wifi,
                                    onRetryClick = {
                                        if (videoViewModel.isInternetConnected()) {
                                            videoViewModel.getVideos()
                                        } else {
                                            Toast.makeText(
                                                ctx,
                                                context.getString(R.string.connect_internet),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                            }

                        } else if (videoViewModel.videoList.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                PaginationInternetError(errorText = "Something went wrong", onRetryClick = {
                                    if (videoViewModel.isInternetConnected()) {
                                        //homeViewModel.getPhotos()
                                    } else {
                                        Toast.makeText(
                                            ctx,
                                            context.getString(R.string.connect_internet),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            }
                        } else {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                InternetError(modifier = Modifier
                                    .wrapContentSize(),
                                    contentText = "Something went wrong, Please try again",
                                    id = R.drawable.error,
                                    onRetryClick = {
                                        if (videoViewModel.isInternetConnected()) {
                                            videoViewModel.getVideos()
                                        } else {
                                            Toast.makeText(
                                                ctx,
                                                context.getString(R.string.connect_internet),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                            }
                        }*/
        }

        else -> {

        }
    }
}