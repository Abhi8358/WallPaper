package com.vedic.deepinsea.data.models

data class VideoDataModel(
    val nextPage: String?,
    val page: Int?,
    val perPage: Int?,
    val totalResults: Int?,
    val url: String?,
    val videos: List<Video?>?
)