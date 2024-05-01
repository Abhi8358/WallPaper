package com.vedic.deepinsea.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedic.deepinsea.data.models.AboutPhoto
import com.vedic.deepinsea.data.models.CollectionDetailModel
import com.vedic.deepinsea.data.models.Media
import com.vedic.deepinsea.data.repositories.CollectionsRepository
import com.vedic.deepinsea.utils.NetworkUtils
import com.vedic.deepinsea.utils.ResourceState
import com.vedic.deepinsea.utils.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsDetailViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    val photosCollectionsDetailList = mutableStateListOf<AboutPhoto>()
    val videoCollectionsDetailList = mutableStateListOf<AboutPhoto>()

    var page by mutableStateOf(1)
    var isLastPage by mutableStateOf(false)
    var canPaginate by mutableStateOf(false)

    private val _collectionsDetailModel: MutableStateFlow<ResourceState<CollectionDetailModel>> =
        MutableStateFlow(ResourceState.Loading())

    val collectionsDetailModel: MutableStateFlow<ResourceState<CollectionDetailModel>> = _collectionsDetailModel

    fun getCollectionItems(id: String) {
        Log.d("Abhishek", "collection detail page ${photosCollectionsDetailList.size} page = $page")
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate)) {
                val photos = collectionsRepository.getCollectionDetail(page,id,isInternetConnected())
                photos.collectLatest { it ->
                    _collectionsDetailModel.value = it
                    canPaginate = true
                    when (it) {
                        is ResourceState.Success -> {
                            val data = it.data
                            isLastPage = data.next_page == null
                            canPaginate = !isLastPage
                            Log.d("Abhishek", "collectiondetailviewmodel ${page} $canPaginate  $isLastPage")
                            if (photosCollectionsDetailList.isEmpty() || page == 1) {
                                photosCollectionsDetailList.clear()
                            }
                            page += 1
                            // added videos
                            data.media
                                ?.filterNotNull()
                                ?.filter {
                                    it.type == Type.Video.toString()
                                }?.let {
                                    videoCollectionsDetailList.addAll(it)
                                }
                            // added photos
                            data
                                .media
                                ?.filterNotNull()
                                ?.let {
                                    photosCollectionsDetailList.addAll(it)
                                }
                        }

                        else -> {

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