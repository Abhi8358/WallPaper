package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoPictureX(
    val id: Int?,
    val nr: Int?,
    val picture: String?
) : Parcelable