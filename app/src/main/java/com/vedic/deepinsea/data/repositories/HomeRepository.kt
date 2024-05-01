package com.vedic.deepinsea.data.repositories

import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getHomePhotos(page:Int, isNetworkConnected: Boolean) : Flow<ResourceState<Photos>>
}