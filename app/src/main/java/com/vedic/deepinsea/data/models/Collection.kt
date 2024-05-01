package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    val description: String?,
    val id: String?,
    val media_count: Int?,
    val photos_count: Int?,
    val `private`: Boolean?,
    val title: String?,
    val videos_count: Int?
) : Parcelable