package com.vedic.deepinsea.data.repositories

import android.util.Log
import com.vedic.deepinsea.data.models.CollectionDetailModel
import com.vedic.deepinsea.data.models.CollectionsModel
import com.vedic.deepinsea.hilt.PexelsService
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(val pexelsService: PexelsService) : CollectionsRepository {
    override suspend fun getCollectionItems(page: Int, isNetworkConnected: Boolean): Flow<ResourceState<CollectionsModel>> {
        Log.d("Abhishek ropo", "Page number $page")
        return flow {
            if (page == 1) {
                emit(ResourceState.Loading())
            } else {
                emit(ResourceState.PaginationLoading())
            }
            if (isNetworkConnected) {
                val response = pexelsService.getCollectionItems(page = page)
                if (response.isSuccessful && response.body() != null) {
                    emit(ResourceState.Success(response.body()!!))
                } else {
                    emit(
                        ResourceState.Error(
                            "Something went wrong."
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

    override suspend fun getCollectionDetail(
        page: Int,
        id: String,
        isNetworkConnected: Boolean
    ): Flow<ResourceState<CollectionDetailModel>> {
        Log.d("Abhishek ropo", "Page number $page")
        return flow {
            if (page == 1) {
                emit(ResourceState.Loading())
            } else {
                emit(ResourceState.PaginationLoading())
            }
            if (isNetworkConnected) {
                val response = pexelsService.getCollectionDetail(page = page, endpoint = id)
                if (response.isSuccessful && response.body() != null) {
                    emit(ResourceState.Success(response.body()!!))
                } else {
                    emit(
                        ResourceState.Error(
                            response.errorBody() ?: "Something went wrong."
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