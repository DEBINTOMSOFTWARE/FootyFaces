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
import org.junit.Before
import org.junit.Test
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
                   city_id = 1,
                   common_name = "Player 1",
                   country_id = 1,
                   date_of_birth = "1990-01-01",
                   detailed_position_id = 1,
                   display_name = "Player 1",
                   firstname = "Player",
                   gender = "Male",
                   height = 180,
                   id = 1,
                   image_path = "https://example.com/player1.jpg",
                   lastname = "1",
                   name = "Player 1",
                   nationality_id = 1,
                   position_id = 1,
                   sport_id = 1,
                   type_id = 1,
                   weight = 80
               )
           ),
           pagination = Pagination(
               count = 1,
               current_page = 1,
               has_more = false,
               next_page = "",
               per_page = 10
           )
       )

        coEvery { apiService.getPlayers(page = 1) } returns mockResponse
        val result = playerRepository.getPlayers(page = 1).toList()
        assert(result.size == 1)
        assertTrue {result[0] is Resource.Loading }
    }
}