package com.example.footyfaces.data.repository

import com.example.footyfaces.TestConstants
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
    fun givenApiServiceReturnsPlayers_whenGetPlayers_thenReturnsPlayersAndPagination() = runTest {
        val mockResponse = PlayerResponse(
            players = listOf(
                Player(
                    dateOfBirth = TestConstants.PLAYER_DOB,
                    displayName = TestConstants.PLAYER_NAME,
                    firstname = TestConstants.PLAYER_FIRSTNAME,
                    gender = TestConstants.PLAYER_GENDER,
                    height = TestConstants.PLAYER_HEIGHT,
                    id = TestConstants.PLAYER_ID,
                    imagePath = TestConstants.PLAYER_IMAGE_PATH,
                    lastname = TestConstants.PLAYER_LASTNAME,
                    name = TestConstants.PLAYER_NAME,
                    weight = TestConstants.PLAYER_WEIGHT
                )
            ),
            pagination = Pagination(
                count = TestConstants.PAGINATION_COUNT,
                currentPage = TestConstants.PAGINATION_CURRENT_PAGE,
                hasMore = TestConstants.PAGINATION_HAS_MORE,
                nextPage = TestConstants.PAGINATION_NEXT_PAGE,
                perPage = TestConstants.PAGINATION_PER_PAGE
            )
        )

        coEvery { apiService.getPlayers(page = TestConstants.PAGE) } returns mockResponse
        val result = playerRepository.getPlayers(page = TestConstants.PAGE).toList()
        assert(result.size == 2)
        assertTrue { result[0] is Resource.Loading }
        assertTrue { result[1] is Resource.Success }
        val players = (result[1] as Resource.Success).data
        assertEquals(TestConstants.PLAYER_NAME, players!!.first[0].displayName)
        assertEquals(TestConstants.PAGINATION_HAS_MORE, players.second.hasMore)
    }

    @Test
    fun givenApiServiceThrowsIOException_whenGetPlayers_thenReturnsError() = runTest {
        coEvery { apiService.getPlayers(page = TestConstants.PAGE) } throws IOException()
        val players = playerRepository.getPlayers(page = TestConstants.PAGE).toList()
        assertTrue { players[0] is Resource.Loading }
        assertTrue { players[1] is Resource.Error }
    }

}