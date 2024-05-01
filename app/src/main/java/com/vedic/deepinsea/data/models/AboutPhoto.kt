package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AboutPhoto(
    val alt: String?,
    val avgColor: String?,
    val height: Int?,
    val id: Int?,
    val liked: Boolean?,
    val photographer: String?,
    val photographerId: Int?,
    val photographerUrl: String?,
    val src: PhotoUrls?,
    val url: String?,
    val width: Int?,
    val type: String? = null,
    val image: String? = null,
    val user: UserX? = null,
    val video_files: List<VideoFileX>? = null,
    val video_pictures: List<VideoPictureX>? = null,
) : Parcelable
