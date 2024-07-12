package com.example.footyfaces.data.mapper

import com.example.footyfaces.data.model.PlayerResponse
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity

fun PlayerResponse.toDomain(): Pair<List<PlayerEntity>, PaginationEntity> {
    val players = players.map {
        PlayerEntity(
            dateOfBirth = it.dateOfBirth,
            displayName = it.displayName,
            firstname = it.firstname,
            gender = it.gender,
            height = it.height,
            id = it.id,
            imagePath = it.imagePath,
            lastname = it.lastname,
            name = it.name,
            weight = it.weight
        )
    }

    val pagination = PaginationEntity(
        count = pagination.count,
        currentPage = pagination.currentPage,
        hasMore = pagination.hasMore,
        nextPage = pagination.nextPage,
        perPage = pagination.perPage
    )

    return Pair(players, pagination)
}