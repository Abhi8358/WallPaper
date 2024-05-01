package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserX(
    val id: Int?,
    val name: String?,
    val url: String?
) : Parcelable