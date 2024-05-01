package com.vedic.deepinsea.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.AppBar
import com.vedic.deepinsea.components.WallpaperCardLayout
import com.vedic.deepinsea.components.error.ErrorScreen
import com.vedic.deepinsea.components.indicators.CustomLinearProgressIndicator
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.ui.viewModels.CollectionsDetailViewModel
import com.vedic.deepinsea.utils.LoadingAnimation
import com.vedic.deepinsea.utils.ResourceState
import com.vedic.deepinsea.utils.gridItems

@Composable
fun CollectionDetailRoute(
    id: String,
    onBackClick: () -> Unit,
    name: String,
    imageOnClick: (AboutPhoto) -> Unit,
    collectionDetailViewModel: CollectionsDetailViewModel = hiltViewModel()
) {
    Log.d("Abhishek", "id = $id")
    val isFirstTime = remember {
        mutableStateOf(true)
    }
    if (isFirstTime.value && collectionDetailViewModel.photosCollectionsDetailList.isEmpty()) {
        collectionDetailViewModel.getCollectionItems(id)
        isFirstTime.value = false
    }
    val ctx = LocalContext.current

    val lazyColumnListState = rememberLazyListState()
    val collectionsModel = collectionDetailViewModel.collectionsDetailModel.collectAsState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            collectionDetailViewModel.canPaginate && collectionDetailViewModel.photosCollectionsDetailList.isNotEmpty() && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        Log.d("Abhishek", "start pagination : ${collectionDetailViewModel.canPaginate}")
        if (shouldStartPaginate.value) {
            Log.d("Abhishek", "start pagination in collection screen")
            collectionDetailViewModel.getCollectionItems(id)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .width(48.dp)
        ) {
            AppBar(
                header = name, modifier = Modifier, onBackClick = onBackClick
            )
        }

        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier.fillMaxWidth()
        ) {

            // is ResourceState.Success -> {
            val mediaPhoto = collectionDetailViewModel.photosCollectionsDetailList.toList()

            gridItems(
                columnCount = 2,
                data = mediaPhoto.toList(),
                modifier = Modifier
            ) {

                WallpaperCardLayout(
                    photoUrls = it.src,
                    aboutPhoto = it,
                    imageOnClick = imageOnClick
                )
            }

            when (collectionsModel.value) {

                is ResourceState.Loading -> {
                    item(

                    ) {
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
                             isInternetConnected = collectionDetailViewModel.isInternetConnected(),
                             isEmptyList = collectionDetailViewModel.photosCollectionsDetailList.isEmpty(),
                             modifier = Modifier
                                 .fillParentMaxHeight()
                                 .fillParentMaxWidth(),
                         ) {
                             if (collectionDetailViewModel.isInternetConnected()) {
                                 collectionDetailViewModel.getCollectionItems(id)
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

                else -> {

                }
            }


        }
    }
}