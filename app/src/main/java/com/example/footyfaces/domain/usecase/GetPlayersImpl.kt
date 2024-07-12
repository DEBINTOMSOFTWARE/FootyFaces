package com.example.footyfaces.domain.usecase

import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayersImpl @Inject constructor(
    private val playerRepository: PlayerRepository
) : GetPlayers {
    override fun getPlayers(page: Int): Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> {
        return playerRepository.getPlayers(page)
    }
}