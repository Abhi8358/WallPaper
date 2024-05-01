package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val alt: String?,
    val avg_color: String?,
    val height: Int?,
    val id: Int?,
    val liked: Boolean?,
    val photographer: String?,
    val photographer_id: Int?,
    val photographer_url: String?,
    val src: PhotoUrls?,
    val type: String?,
    val url: String?,
    val width: Int?
) : Parcelable

