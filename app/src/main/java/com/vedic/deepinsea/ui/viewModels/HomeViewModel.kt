package com.vedic.deepinsea.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.data.repositories.HomeRepository
import com.vedic.deepinsea.utils.NetworkUtils
import com.vedic.deepinsea.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {


    val newsList = mutableStateListOf<AboutPhoto>()
    var page by mutableStateOf(1)
    var isLastPage by mutableStateOf(false)
    var canPaginate by mutableStateOf(false)

    private val _photos: MutableStateFlow<ResourceState<Photos>> =
        MutableStateFlow(ResourceState.Loading())

    val photo: MutableStateFlow<ResourceState<Photos>> = _photos

    init {
        Log.d("Abhi", "Photos : ")
        getPhotos()
    }

    fun getPhotos() {
        Log.d("Abhishek", "newsList ${newsList.size} page = $page")
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate)) {
                val photos = homeRepository.getHomePhotos(page, isInternetConnected())
                photos.collectLatest { it ->
                    _photos.value = it
                    canPaginate = true
                    when (it) {
                        is ResourceState.Success -> {
                            val data = it.data
                            isLastPage = data.next_page != null

                            Log.d("Abhishek", "homeViewModelPage ${page}")
                            if (newsList.isEmpty() || page == 1) {
                                newsList.clear()
                            }
                            page += 1
                            data.photos?.filterNotNull()
                                ?.let { newsList.addAll(it) }
                        } else -> {

                        }
                    }
                }
                //Log.d("Abhi", "Photos : ${photos}")
            }
        }
    }

    fun isInternetConnected(): Boolean {
        Log.d("Abhishek", "is network connected ${networkUtils.isNetworkConnected()}")
        return networkUtils.isNetworkConnected()
    }
}