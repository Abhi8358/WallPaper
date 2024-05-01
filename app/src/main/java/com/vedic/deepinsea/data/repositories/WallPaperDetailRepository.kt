package com.vedic.deepinsea.data.repositories

import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface WallPaperDetailRepository {

    suspend fun getSimilarWallpapers(query: String, isNetworkConnected: Boolean): Flow<ResourceState<Photos>>
}