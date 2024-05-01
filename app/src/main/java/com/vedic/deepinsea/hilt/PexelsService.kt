package com.vedic.deepinsea.hilt

import com.vedic.deepinsea.data.models.CollectionDetailModel
import com.vedic.deepinsea.data.models.CollectionsModel
import com.vedic.deepinsea.data.models.Photos
import com.vedic.deepinsea.data.models.VideoDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsService {

    @GET("v1/curated")
    suspend fun getHomePhotos(
        @Query("per_page") contentPerPage: Int = 80,
        @Query("page") page: Int = 1
    ): Response<Photos>

    @GET("v1/search")
    suspend fun getSearchPhotos(
        @Query("per_page") contentPerPage: Int = 10,
        @Query("query") query: String
    ): Response<Photos>

    @GET("videos/popular")
    suspend fun getPopularVideos(
        @Query("per_page") contentPerPage: Int = 80,
        @Query("page") page: Int = 1
    ): Response<VideoDataModel>

    @GET("v1/collections/featured")
    suspend fun getCollectionItems(
        @Query("per_page") contentPerPage: Int = 80,
        @Query("page") page: Int
    ): Response<CollectionsModel>

    @GET("v1/collections/{endpoint}")
    suspend fun getCollectionDetail(
        @Path(value = "endpoint", encoded = true) endpoint: String,
        @Query("per_page") contentPerPage: Int = 80,
        @Query("page") page: Int
    ): Response<CollectionDetailModel>
}