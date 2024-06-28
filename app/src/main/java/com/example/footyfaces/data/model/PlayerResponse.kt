package com.example.footyfaces.data.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("data")
    val players: List<Player>,
    val pagination: Pagination,
)