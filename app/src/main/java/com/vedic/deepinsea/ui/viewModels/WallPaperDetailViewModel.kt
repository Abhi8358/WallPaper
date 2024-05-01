package com.vedic.deepinsea.ui.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.data.models.UrlsWithName
import com.vedic.deepinsea.data.repositories.WallPaperDetailRepository
import com.vedic.deepinsea.utils.NetworkUtils
import com.vedic.deepinsea.utils.ResourceState
import com.vedic.deepinsea.utils.createPhotoUrlList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WallPaperDetailViewModel @Inject constructor(
    private val wallPaperDetailRepository: WallPaperDetailRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {
    var aboutPhoto = mutableStateOf<AboutPhoto?>(null)
    var photoUrlsList = mutableStateListOf<UrlsWithName>()
    val imageDescription = mutableStateOf<String?>("")

    private val _photos: MutableStateFlow<ResourceState<Photos>> =
        MutableStateFlow(ResourceState.Loading())

    val photo: MutableStateFlow<ResourceState<Photos>> = _photos

    fun setPhotoUrlList(aboutPhoto: AboutPhoto) {
        this.aboutPhoto.value = aboutPhoto
        this.photoUrlsList.addAll(createPhotoUrlList(aboutPhoto?.src))
        searchSimilarImages(
            query = if (aboutPhoto.alt.isNullOrEmpty()) aboutPhoto.url ?: "Shiva" else aboutPhoto.alt
        )
    }

     fun searchSimilarImages(query: String) {
        viewModelScope.launch {
            val similarPhotos = wallPaperDetailRepository.getSimilarWallpapers(query, isInternetConnected())
            similarPhotos.collectLatest {
                _photos.value = it
            }
        }
    }

    fun isInternetConnected(): Boolean {
        Log.d("Abhishek", "is network connected ${networkUtils.isNetworkConnected()}")
        return networkUtils.isNetworkConnected()
    }
}