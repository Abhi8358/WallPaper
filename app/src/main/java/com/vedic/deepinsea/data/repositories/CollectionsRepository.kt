package com.vedic.deepinsea.data.repositories

import com.vedic.deepinsea.data.models.CollectionDetailModel
import com.vedic.deepinsea.data.models.CollectionsModel
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    suspend fun getCollectionItems(page:Int, isNetworkConnected: Boolean) : Flow<ResourceState<CollectionsModel>>

    suspend fun getCollectionDetail(page: Int, id: String, isNetworkConnected: Boolean) : Flow<ResourceState<CollectionDetailModel>>
}