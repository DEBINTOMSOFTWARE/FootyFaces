package com.example.footyfaces.domain.usecases

import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.domain.usecase.GetPlayersImpl
import com.example.footyfaces.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class GetPlayersImplTest {

    private lateinit var repository: PlayerRepository
    private lateinit var getPlayersImpl: GetPlayersImpl

    @Before
    fun setup() {
        repository = mockk()
        getPlayersImpl = GetPlayersImpl(repository)
    }

    @Test
    fun getPlayers_usecase_fetches_data_from_repository() = runTest {
        val mockResponse: Flow<Resource<Pair<List<PlayerEntity>, PaginationEntity>>> = flow {
            val players = listOf(
                PlayerEntity(
                    displayName = "Player 1",
                    firstname = "Player",
                    gender = "Male",
                    height = 180,
                    id = 1,
                    imagePath = "https://example.com/player1.jpg",
                    lastname = "1",
                    name = "Player 1",
                    weight = 80
                )
            )

            val pagination = PaginationEntity(
                count = 1,
                currentPage = 1,
                hasMore = false,
                nextPage = "",
                perPage = 10
            )
            emit(Resource.Success(Pair(players, pagination)))
        }

        coEvery { repository.getPlayers(page = 1) } returns mockResponse
        val result = getPlayersImpl.getPlayers(page = 1)
        result.collect {
            assert(it is Resource.Success)
            assertTrue { (it as Resource.Success).data!!.first[0].displayName == "Player 1" }
            assertTrue { (it as Resource.Success).data!!.second.hasMore == false }
        }
    }

}