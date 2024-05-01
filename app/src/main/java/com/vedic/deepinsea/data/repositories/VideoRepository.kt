package com.vedic.deepinsea.data.repositories

import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.data.models.VideoDataModel
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(page:Int, isInternetConnected: Boolean) : Flow<ResourceState<VideoDataModel>>
}