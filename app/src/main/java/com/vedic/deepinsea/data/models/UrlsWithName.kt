package com.vedic.deepinsea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlsWithName(val name: String, val url: String) : Parcelable
