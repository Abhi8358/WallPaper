package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionDetailModel(
    val id: String?,
    val media: List<AboutPhoto?>?,
    val next_page: String?,
    val page: Int?,
    val per_page: Int?,
    val total_results: Int?
) : Parcelable