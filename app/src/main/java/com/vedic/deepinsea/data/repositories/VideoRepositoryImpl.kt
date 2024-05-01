package com.vedic.deepinsea.data.repositories

import android.util.Log
import com.vedic.deepinsea.data.models.VideoDataModel
import com.vedic.deepinsea.hilt.PexelsService
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(val pexelsService: PexelsService) : VideoRepository{
    override suspend fun getVideos(page: Int, isInternetConnected: Boolean): Flow<ResourceState<VideoDataModel>> {
        Log.d("Abhishek ropo", "Page number $page")
        return flow {
            if (page == 1) {
                emit(ResourceState.Loading())
            } else {
                emit(ResourceState.PaginationLoading())
            }
            if (isInternetConnected) {
                val response = pexelsService.getPopularVideos(page = page)
                if (response.isSuccessful && response.body() != null) {
                    emit(ResourceState.Success(response.body()!!))
                } else {
                    emit(
                        ResourceState.Error(
                            response.errorBody() ?: "Somthing went wrong"
                        )
                    )
                }
            } else {
                emit(
                    ResourceState.Error(
                        "No Internet"
                    )
                )
            }
        }.catch {
            emit(ResourceState.Error(it.localizedMessage ?: "Unexpected error in flow"))
        }.flowOn(Dispatchers.IO)
    }

}