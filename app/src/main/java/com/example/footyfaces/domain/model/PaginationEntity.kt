package com.example.footyfaces.domain.model

data class PaginationEntity(
    val count: Int? = null,
    val current_page: Int? = null,
    val has_more: Boolean? = null,
    val next_page: String? = null,
    val per_page: Int? = null
)
