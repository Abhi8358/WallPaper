package com.vedic.deepinsea.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vedic.deepinsea.data.models.Collection
import com.vedic.deepinsea.data.models.CollectionsModel
import com.vedic.deepinsea.data.repositories.CollectionsRepository
import com.vedic.deepinsea.utils.NetworkUtils
import com.vedic.deepinsea.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    val collectionsList = mutableStateListOf<Collection>()
    var page by mutableStateOf(1)
    var isLastPage by mutableStateOf(false)
    var canPaginate by mutableStateOf(false)

    private val _collectionsModel: MutableStateFlow<ResourceState<CollectionsModel>> =
        MutableStateFlow(ResourceState.Loading())

    val collectionsModel: MutableStateFlow<ResourceState<CollectionsModel>> = _collectionsModel

    init {
        Log.d("Abhi", "Photos : ")
        getCollectionItems()
    }

    fun getCollectionItems() {
        Log.d("Abhishek", "newsList ${collectionsList.size} page = $page")
        viewModelScope.launch {
            if (page == 1 || (page != 1 && canPaginate)) {
                val photos = collectionsRepository.getCollectionItems(page, isInternetConnected())
                photos.collectLatest { it ->
                    _collectionsModel.value = it
                    canPaginate = true
                    when (it) {
                        is ResourceState.Success -> {
                            val data = it.data
                            isLastPage = data.next_page == null
                            canPaginate = !isLastPage
                            Log.d(
                                "Abhishek",
                                "collectionviewmodel ${page} $canPaginate  $isLastPage"
                            )
                            if (collectionsList.isEmpty() || page == 1) {
                                collectionsList.clear()
                            }
                            page += 1
                            data.collections?.filterNotNull()
                                ?.let { collectionsList.addAll(it) }
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