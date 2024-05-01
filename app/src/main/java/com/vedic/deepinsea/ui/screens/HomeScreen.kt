package com.vedic.deepinsea.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.WallpaperCardLayout
import com.vedic.deepinsea.components.error.ErrorScreen
import com.vedic.deepinsea.components.indicators.CustomLinearProgressIndicator
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.viewModels.HomeViewModel
import com.vedic.deepinsea.utils.LoadingAnimation
import com.vedic.deepinsea.utils.ResourceState
import com.vedic.deepinsea.utils.gridItems

@Composable
fun HomeScreenRoute(
    imageOnClick: (AboutPhoto) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val lazyColumnListState = rememberLazyListState()
    val photos = homeViewModel.photo.collectAsState()
    val ctx = LocalContext.current

    val shouldStartPaginate = remember {
        derivedStateOf {
            homeViewModel.canPaginate && homeViewModel.newsList.isNotEmpty() && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            Log.d("Abhishek", "pagination page = ${homeViewModel.page}")
            homeViewModel.getPhotos()
        }
    }

    LazyColumn(
        state = lazyColumnListState,
        modifier = Modifier.fillMaxWidth()
    ) {

        // is ResourceState.Success -> {
        val aboutPhoto = homeViewModel.newsList

        gridItems(
            columnCount = 2,
            data = aboutPhoto.toList(),
            modifier = Modifier
        ) {

            WallpaperCardLayout(
                photoUrls = it.src,
                aboutPhoto = it
            ) {
                if (homeViewModel.isInternetConnected()) {
                    imageOnClick(it)
                } else {
                    Toast.makeText(ctx, "Please check your internet connection", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        when (photos.value) {
            is ResourceState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        LoadingAnimation()
                    }
                }
            }

            is ResourceState.PaginationLoading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CustomLinearProgressIndicator()
                    }
                }
            }

            is ResourceState.Error -> {
                item {
                    ErrorScreen(
                        isInternetConnected = homeViewModel.isInternetConnected(),
                        isEmptyList = homeViewModel.newsList.isEmpty(),
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth(),
                    ) {
                        if (homeViewModel.isInternetConnected()) {
                            homeViewModel.getPhotos()
                        } else {
                            Toast.makeText(
                                ctx,
                                ctx.getString(R.string.connect_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            else -> {}
        }
    }
}