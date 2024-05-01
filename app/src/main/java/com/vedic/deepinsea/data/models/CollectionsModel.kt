package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionsModel(
    val collections: List<Collection?>?,
    val next_page: String?,
    val page: Int?,
    val per_page: Int?,
    val total_results: Int?
) : Parcelable