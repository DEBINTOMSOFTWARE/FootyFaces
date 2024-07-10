package com.example.footyfaces.domain.model

data class PaginationEntity(
    val count: Int = 0,
    val currentPage: Int = 1,
    val hasMore: Boolean = false,
    val nextPage: String? = null,
    val perPage: Int = 0
)
