package com.example.footyfaces.data.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("display_name")
    val displayName: String,
    val firstname: String,
    val gender: String,
    val height: Int,
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String,
    val lastname: String,
    val name: String,
    val weight: Int
)