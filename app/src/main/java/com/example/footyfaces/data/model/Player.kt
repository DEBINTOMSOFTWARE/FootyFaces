package com.example.footyfaces.data.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerializedName("display_name")
    val displayName: String? = null,
    val firstname: String? = null,
    val gender: String? = null,
    val height: Int? = null,
    val id: Int? = null,
    @SerializedName("image_path")
    val imagePath: String? = null,
    val lastname: String? = null,
    val name: String? = null,
    val weight: Int? = null
)