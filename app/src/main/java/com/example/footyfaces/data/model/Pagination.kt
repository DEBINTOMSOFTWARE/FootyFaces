package com.example.footyfaces.data.model

data class Pagination(
    val count: Int,
    val current_page: Int,
    val has_more: Boolean,
    val next_page: String,
    val per_page: Int
)