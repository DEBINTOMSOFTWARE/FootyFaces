package com.example.footyfaces.domain.model

data class PlayerEntity(
    val dateOfBirth: String,
    val displayName: String,
    val firstname: String,
    val gender: String,
    val height: Int,
    val id: Int,
    val imagePath: String,
    val lastname: String,
    val name: String,
    val weight: Int
)