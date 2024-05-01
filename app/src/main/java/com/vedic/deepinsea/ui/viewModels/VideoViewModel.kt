package com.vedic.deepinsea.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.vedic.deepinsea.data.models.Video
import com.vedic.deepinsea.data.models.VideoDataModel
import com.vedic.deepinsea.data.repositories.VideoRepository
import com.vedic.deepinsea.utils.Exoplayer
import com.vedic.deepinsea.utils.NetworkUtils
import com.vedic.deepinsea.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val exoplayer: Exoplayer,
    private val networkUtils: NetworkUtils
) : ViewModel() {


    val videoList = mutableStateListOf<Video>()
    var page by mutableStateOf(1)
    var isLastPage by mutableStateOf(false)
    var canPaginate by mutableStateOf(false)

    private val _videos: MutableStateFlow<ResourceState<VideoDataModel>> =
        MutableStateFlow(ResourceState.Loading())

    val videos: MutableStateFlow<ResourceState<VideoDataModel>> = _videos

    init {
        Log.d("Abhi", "Photos : ")
        getVideos()
        Log.d("Abhi", exoplayer.javaClass.name)
    }

    fun getVideos() {
        Log.d("Abhishek", "newsList ${videoList.size} page = $page")
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate)) {
                val fetchedVideos = videoRepository.getVideos(page, isInternetConnected())
                fetchedVideos.collectLatest { it ->
                    _videos.value = it
                    canPaginate = true
                    when (it) {
                        is ResourceState.Success -> {
                            val data = it.data
                            isLastPage = data.nextPage != null

                            Log.d("Abhishek", "homeViewModelPage ${page}")
                            if (videoList.isEmpty() || page == 1) {
                                videoList.clear()
                            }
                            page += 1
                            data.videos?.filterNotNull()
                                ?.let {
                                    videoList.addAll(it)
                                }
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun getPlayer(): ExoPlayer {
        return exoplayer.player
    }

    fun isInternetConnected(): Boolean {
        Log.d("Abhishek", "is network connected ${networkUtils.isNetworkConnected()}")
        return networkUtils.isNetworkConnected()
    }
}