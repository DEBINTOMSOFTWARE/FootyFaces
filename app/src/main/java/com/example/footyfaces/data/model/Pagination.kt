package com.example.footyfaces.data.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    val count: Int = 0,
    @SerializedName("current_page")
    val currentPage: Int = 1,
    @SerializedName("has_more")
    val hasMore: Boolean = false,
    @SerializedName("next_page")
    val nextPage: String? = null,
    @SerializedName("per_page")
    val perPage: Int = 0
)