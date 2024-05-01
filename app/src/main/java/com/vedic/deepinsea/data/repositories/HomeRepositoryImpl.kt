package com.vedic.deepinsea.data.repositories

import android.util.Log
import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.hilt.PexelsService
import com.vedic.deepinsea.utils.ResourceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRepositoryImpl @Inject constructor(val pexelsService: PexelsService) : HomeRepository {
    override suspend fun getHomePhotos(page:Int, isNetworkConnected: Boolean): Flow<ResourceState<Photos>> {
        Log.d("Abhishek ropo", "Page number $page")
        return flow {
            if (page == 1) {
                emit(ResourceState.Loading())
            } else {
                emit(ResourceState.PaginationLoading())
            }
            if (isNetworkConnected) {
                val response = pexelsService.getHomePhotos(page = page)
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
                emit(ResourceState.Error(
                    "No connection."
                ))
            }
        }.catch {
            emit(ResourceState.Error(it.localizedMessage ?: "Unexpected error in flow."))
        }.flowOn(Dispatchers.IO)
    }
}