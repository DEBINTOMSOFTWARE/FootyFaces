package com.example.footyfaces.data.repository

import com.example.footyfaces.data.remote.ApiService
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(private val apiService: ApiService) : PlayerRepository {
    override suspend fun getPlayers(page: Int): Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> = flow {
        emit(Resource.Loading)
    }
}