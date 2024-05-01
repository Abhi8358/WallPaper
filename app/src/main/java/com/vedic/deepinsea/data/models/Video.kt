package com.vedic.deepinsea.data.models

data class Video(
    val avgColor: Any?,
    val duration: Int?,
    val fullRes: Any?,
    val height: Int?,
    val id: Int?,
    val image: String?,
    val tags: List<Any?>?,
    val url: String?,
    val user: User?,
    val video_files: List<VideoFile?>?,
    val videoPictures: List<VideoPicture?>?,
    val width: Int?
)