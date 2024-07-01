package com.example.footyfaces.data.repository

import com.example.footyfaces.data.model.Pagination
import com.example.footyfaces.data.model.Player
import com.example.footyfaces.data.model.PlayerResponse
import com.example.footyfaces.data.remote.ApiService
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class PlayerRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var playerRepository: PlayerRepository

    @Before
    fun setup() {
        apiService = mockk()
        playerRepository = PlayerRepositoryImpl(apiService)
    }

    @Test
    fun getPlayers_returnsPlayersAndPagination() = runTest {
        val mockResponse = PlayerResponse(
            players = listOf(
                Player(
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
            ),
            pagination = Pagination(
                count = 1,
                currentPage = 1,
                hasMore = false,
                nextPage = "",
                perPage = 10
            )
        )

        coEvery { apiService.getPlayers(page = 1) } returns mockResponse
        val result = playerRepository.getPlayers(page = 1).toList()
        assert(result.size == 2)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Success }
        val players = (result[1] as Resource.Success).data
        assertEquals("Player 1", players!!.first[0].displayName)
        assertEquals(false, players.second.hasMore)
    }

    @Test
    fun getPlayers_returnsError() = runTest {
        coEvery { apiService.getPlayers(page = 1) } throws IOException()
        val players = playerRepository.getPlayers(page = 1).toList()
        assertTrue { players[0] is Resource.Loading }
        assertTrue { players[1] is Resource.Error }
    }

}