package com.vedic.deepinsea.data.repositories

import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.hilt.PexelsService
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WallPaperDetailRepositoryImpl @Inject constructor(val pexelsService: PexelsService) :
    WallPaperDetailRepository {

    override suspend fun getSimilarWallpapers(query: String, isNetworkConnected: Boolean): Flow<ResourceState<Photos>> {
        return flow {
            emit(ResourceState.Loading())
            if (isNetworkConnected) {
                val response = pexelsService.getSearchPhotos(contentPerPage = 80, query = query)
                if (response.isSuccessful && response.body() != null) {
                    emit(ResourceState.Success(response.body()!!))
                } else {
                    emit(
                        ResourceState.Error(
                            response.errorBody() ?: "Something went wrong"
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