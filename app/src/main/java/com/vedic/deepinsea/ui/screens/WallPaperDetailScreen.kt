package com.vedic.deepinsea.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.vedic.deepinsea.BuildConfig
import com.vedic.deepinsea.R
import com.vedic.deepinsea.components.AboutSection
import com.vedic.deepinsea.components.AdmobBanner
import com.vedic.deepinsea.components.AppBar
import com.vedic.deepinsea.components.DownloadingSection
import com.vedic.deepinsea.components.PhotoViewPagerLayout
import com.vedic.deepinsea.components.WallpaperCardLayout
import com.vedic.deepinsea.components.error.ErrorScreen
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.ui.viewModels.WallPaperDetailViewModel
import com.vedic.deepinsea.utils.ResourceState
import com.vedic.deepinsea.utils.downloadImageNew
import com.vedic.deepinsea.utils.gridItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random

@Composable
fun WallPaperDetailRoute(
    onClick: (String) -> Unit,
    wallPaperDetailViewModel: WallPaperDetailViewModel = hiltViewModel(),
    nameArgument: AboutPhoto?,
    onBackClick: () -> Unit,
    onSimilarItemClick: (AboutPhoto) -> Unit,
    context: Context
) {
    if (nameArgument != null && wallPaperDetailViewModel.aboutPhoto.value == null) {
        wallPaperDetailViewModel.setPhotoUrlList(nameArgument)
    }

    var descriptionLoaderKey = remember {
        mutableStateOf(1)
    }
    val lazyColumnListState = rememberLazyListState()
    val photos = wallPaperDetailViewModel.photo.collectAsState()
    val photoFormate = stringResource(R.string.photo_formate)
    Log.d("Abhishek", "outside launcheffect key1 = ${descriptionLoaderKey.value}")
    LaunchedEffect(
        key1 = descriptionLoaderKey.value
    ) {
        Log.d("Abhishek", "inside launcheffect key1 = ${descriptionLoaderKey.value}")
        withContext(Dispatchers.IO) {
            val bitmap = getBitmap(context, wallPaperDetailViewModel.photoUrlsList[0].url)
            bitmap?.let {
                val generativeModel2 = GenerativeModel(
                    // For text-and-images input (multimodal), use the gemini-pro-vision model
                    modelName = "gemini-pro-vision",
                    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                    apiKey = BuildConfig.GEMINI_API_KEY
                )
                val inputContent = content {
                    image(bitmap)
                    text("Write some thing about this image least 7 lines")
                }
                try {
                    wallPaperDetailViewModel.imageDescription.value =
                        generativeModel2.generateContent(inputContent).text.toString()
                    // aboutText = generativeModel2.generateContent(inputContent).text.toString()
                    Log.d(
                        "Abhishek",
                        "response in detail inside ${wallPaperDetailViewModel.imageDescription.value}"
                    )
                } catch (e: Exception) {
                    wallPaperDetailViewModel.imageDescription.value =
                        "Something went wrong....."
                    Log.d("Abhishek", "response in detail error $e")
                }
//                wallPaperDetailViewModel.imageDescription.value =
//                    generativeModel2.generateContent(inputContent).text.toString()
                // aboutText = generativeModel2.generateContent(inputContent).text.toString()
                Log.d(
                    "Abhishek",
                    "response in detail outside ${wallPaperDetailViewModel.imageDescription.value}"
                )
            } ?: run {
                wallPaperDetailViewModel.imageDescription.value =
                    "Something went wrong....."
            }

        }
    }

    Column {

        wallPaperDetailViewModel.aboutPhoto.value?.let { aboutPhoto ->
            aboutPhoto.photographer?.let {
                AppBar(
                    modifier = Modifier,
                    header = it,
                    onBackClick = onBackClick
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = lazyColumnListState
        ) {

            item {
                if (wallPaperDetailViewModel.photoUrlsList.isNotEmpty()) {
                    PhotoViewPagerLayout(
                        photoUrls = wallPaperDetailViewModel.photoUrlsList[0]
                    ) {
                        if (wallPaperDetailViewModel.isInternetConnected()) {
                            onClick(it)
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.connect_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

          /*  item {
                AdmobBanner(modifier = Modifier.fillMaxWidth().wrapContentHeight())
            }*/

            item {
                DownloadingSection(
                    wallPaperDetailViewModel.photoUrlsList.toList(),
                    onDownloadClick = {
                        if (wallPaperDetailViewModel.isInternetConnected()) {
                            context.downloadImageNew(
                                filename = it.name,
                                downloadUrlOfImage = Uri.parse(it.url),
                                id = Random(345325345L).nextInt(),
                                formate = photoFormate
                            )
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.connect_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

            item {
                AboutSection(
                    wallPaperDetailViewModel.imageDescription.value,
                    wallPaperDetailViewModel.aboutPhoto.value?.alt ?: ""
                )
            }

            when (photos.value) {
                is ResourceState.Success -> {
                    gridItems(
                        data = (photos.value as ResourceState.Success<Photos>).data.photos,
                        columnCount = 3,
                        modifier = Modifier
                    ) {
                        it?.let {
                            WallpaperCardLayout(
                                aboutPhoto = it,
                                photoUrls = it.src,
                                imageOnClick = {
                                    onSimilarItemClick(it)
                                })
                        }

                    }
                }

                is ResourceState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is ResourceState.Error -> {
                    item {
                        ErrorScreen(
                            isInternetConnected = wallPaperDetailViewModel.isInternetConnected(),
                            isEmptyList = true,
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .fillParentMaxWidth(),
                        ) {
                            if (wallPaperDetailViewModel.isInternetConnected()) {
                                descriptionLoaderKey.value += 1
                                wallPaperDetailViewModel.imageDescription.value = ""
                                wallPaperDetailViewModel.searchSimilarImages(
                                    query = if (wallPaperDetailViewModel.aboutPhoto.value?.alt.isNullOrEmpty()) wallPaperDetailViewModel.aboutPhoto.value?.url
                                        ?: "Shiva" else wallPaperDetailViewModel.aboutPhoto.value?.alt
                                        ?: ""
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.connect_internet),
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

private suspend fun getBitmap(context: Context, imageUrl: String): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .build()
    if (loader.execute(request = request) is SuccessResult) {
        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
    return null
}