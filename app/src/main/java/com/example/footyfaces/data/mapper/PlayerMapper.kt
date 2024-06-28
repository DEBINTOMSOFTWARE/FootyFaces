package com.example.footyfaces.data.mapper

import com.example.footyfaces.data.model.PlayerResponse
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity

fun PlayerResponse.toDomain(): Pair<List<PlayerEntity>, PaginationEntity> {
    val players = players.map {
        PlayerEntity(
             city_id = it.city_id,
             common_name = it.common_name,
             country_id = it.country_id,
             date_of_birth = it.date_of_birth,
             detailed_position_id = it.detailed_position_id,
             display_name = it.display_name,
             firstname = it.firstname,
             gender = it.gender,
             height = it.height,
             id = it.id,
             image_path = it.image_path,
             lastname = it.lastname,
             name = it.name,
             nationality_id = it.nationality_id,
             position_id = it.position_id,
             sport_id = it.sport_id,
             type_id = it.type_id,
             weight = it.weight
         )
    }

    val pagination = PaginationEntity(
        count = pagination.count,
        current_page = pagination.current_page,
        has_more = pagination.has_more,
        next_page = pagination.next_page,
        per_page = pagination.per_page
    )

    return Pair(players, pagination)
}