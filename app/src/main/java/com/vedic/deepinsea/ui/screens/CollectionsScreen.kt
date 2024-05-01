package com.vedic.deepinsea.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.vedic.deepinsea.components.CollectionItemView
import com.vedic.deepinsea.components.error.ErrorScreen
import com.vedic.deepinsea.components.indicators.CustomLinearProgressIndicator
import com.vedic.deepinsea.data.models.Collection
import com.vedic.deepinsea.ui.viewModels.CollectionsViewModel
import com.vedic.deepinsea.utils.LoadingAnimation
import com.vedic.deepinsea.utils.ResourceState

@Composable
fun CollectionsScreenRoutes(
    collectionsViewModel: CollectionsViewModel = hiltViewModel(),
    onClick: (Collection) -> Unit
) {

    val lazyColumnListState = rememberLazyListState()
    val collectionsModel = collectionsViewModel.collectionsModel.collectAsState()
    val ctx = LocalContext.current

    val shouldStartPaginate = remember {
        derivedStateOf {
            collectionsViewModel.canPaginate && collectionsViewModel.collectionsList.isNotEmpty() && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        Log.d("Abhishek", "start pagination : ${collectionsViewModel.canPaginate}")
        if (shouldStartPaginate.value) {
            Log.d("Abhishek", "start pagination in collection screen")
            collectionsViewModel.getCollectionItems()
        }
    }

    //when (collectionsModel.value) {
    //  is ResourceState.Success -> {
    LazyColumn(
        state = lazyColumnListState,
        modifier = Modifier.padding(top = 4.dp)
    ) {
        items(count = collectionsViewModel.collectionsList.size) {
            CollectionItemView(collectionsViewModel.collectionsList[it]) {
                Log.d("Abhishek", "clicked : ${it}")
                onClick(it)
            }
        }

        when (collectionsModel.value) {
            is ResourceState.Loading -> {
                item {

                    Column(
                        modifier = Modifier.fillParentMaxHeight().fillParentMaxWidth(),
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
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        CustomLinearProgressIndicator()
                    }
                }
            }

            is ResourceState.Error -> {
                item {
                    ErrorScreen(
                        isInternetConnected = collectionsViewModel.isInternetConnected(),
                        isEmptyList = collectionsViewModel.collectionsList.isEmpty(),
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth(),
                    ) {
                        if (collectionsViewModel.isInternetConnected()) {
                            collectionsViewModel.getCollectionItems()
                        } else {
                            Toast.makeText(
                                ctx,
                                ctx.getString(R.string.connect_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else -> {}
        }
    }

    // }

    /*  is ResourceState.Loading -> {
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center,
          ) {
              //LoadingAnimation()
              Column(
                  modifier = Modifier
                      .fillMaxWidth()
                      .fillMaxHeight(),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center,
              ) {
//                        Text(
//                            modifier = Modifier
//                                .padding(8.dp),
//                            text = "Refresh Loading"
//                        )
                  //CircularProgressBar()
                  LoadingAnimation()
              }
          }
      }

      is ResourceState.PaginationLoading -> {
          *//*Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(text = "Pagination Loading")
                CustomLinearProgressIndicator()
            }*//*
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Something went wrong Please try again")
            }
        }*/
    //}
}