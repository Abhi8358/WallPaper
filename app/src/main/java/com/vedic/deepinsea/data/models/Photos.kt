package com.vedic.deepinsea.data.models

data class Photos(
    val next_page: String?,
    val page: Int = 1,
    val per_page: Int?,
    val photos: List<AboutPhoto?>?,
    val total_results: Int?
)