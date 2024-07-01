package com.example.footyfaces.domain.model

data class PaginationEntity(
    val count: Int? = null,
    val currentPage: Int? = null,
    val hasMore: Boolean? = null,
    val nextPage: String? = null,
    val perPage: Int? = null
)
