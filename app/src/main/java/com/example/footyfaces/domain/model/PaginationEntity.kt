package com.example.footyfaces.domain.model

data class PaginationEntity(
    val count: Int?,
    val current_page: Int?,
    val has_more: Boolean?,
    val next_page: String?,
    val per_page: Int?
)
