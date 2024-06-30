package com.example.footyfaces.data.repository

import coil.network.HttpException
import com.example.footyfaces.data.mapper.toDomain
import com.example.footyfaces.data.remote.ApiService
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(private val apiService: ApiService) : PlayerRepository {
    override suspend fun getPlayers(page: Int): Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getPlayers(page)
            val (players, pagination) = response.toDomain()
            emit(Resource.Success(Pair(players, pagination)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}