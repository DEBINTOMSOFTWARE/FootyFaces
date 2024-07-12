package com.example.footyfaces.data.repository

import com.example.footyfaces.data.mapper.toDomain
import com.example.footyfaces.data.remote.ApiService
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.utils.ErrorEntity
import com.example.footyfaces.utils.Resource
import com.example.footyfaces.utils.mapHttpExceptionToDomainError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    PlayerRepository {

    override fun getPlayers(page: Int): Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> =
        flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getPlayers(page)
                val (players, pagination) = response.toDomain()
                emit(Resource.Success(Pair(players, pagination)))
            } catch (e: HttpException) {
                emit(Resource.Error(mapHttpExceptionToDomainError(e)))
            } catch (e: IOException) {
                emit(Resource.Error(ErrorEntity.Network))
            } catch (e: Exception) {
                emit(Resource.Error(ErrorEntity.Unknown(e.localizedMessage ?: "Unknown error")))
            }
        }
}