package com.example.footyfaces.data.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    val count: Int? = null,
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("has_more")
    val hasMore: Boolean? = null,
    @SerializedName("next_page")
    val nextPage: String? = null,
    @SerializedName("per_page")
    val perPage: Int? = null
)