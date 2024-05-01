package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoFileX(
    val file_type: String?,
    val fps: Double?,
    val height: Int?,
    val id: Int?,
    val link: String?,
    val quality: String?,
    val width: Int?
) : Parcelable