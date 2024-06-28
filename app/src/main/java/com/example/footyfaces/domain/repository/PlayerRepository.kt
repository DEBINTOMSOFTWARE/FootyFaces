package com.example.footyfaces.domain.repository

import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun getPlayers(page: Int): Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>>
}