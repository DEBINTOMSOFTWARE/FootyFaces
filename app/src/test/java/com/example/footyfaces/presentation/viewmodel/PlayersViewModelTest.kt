package com.example.footyfaces.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class PlayersViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getPlayersUseCase: GetPlayers
    private lateinit var viewModel: PlayersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getPlayersUseCase = mockk()
        viewModel = PlayersViewModel(getPlayersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_initial_loading_of_players() = testScope.runTest {
        val players = listOf(
            PlayerEntity(
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
        )

        val pagination = PaginationEntity(
            count = 1,
            current_page = 1,
            has_more = true,
            next_page = "",
            per_page = 10
        )

        coEvery { getPlayersUseCase.getPlayers(1) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(Pair(players, pagination)))
        }

        val states = mutableListOf<PlayerUiState>()
        val job = launch {
            viewModel.uiState.collect {state ->
                states.add(state)

            }
        }

        assertEquals(PlayerUiState(), viewModel.uiState.value)
        viewModel.onIntent(PlayerIntent.LoadPlayers)
        viewModel.loadPlayers()

        advanceUntilIdle()

        assertEquals(
            PlayerUiState(players = players, isLoading = false, currentPage = 1, hasMore = true),
            viewModel.uiState.value
        )

        coVerify { getPlayersUseCase.getPlayers(1) }
        job.cancel()
    }

    @Test
    fun test_initial_loading_of_players_error() = testScope.runTest {
        val errorMessage = "Failed to load players"

        coEvery { getPlayersUseCase.getPlayers(1) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }

        val states = mutableListOf<PlayerUiState>()
        val job = launch {
            viewModel.uiState.collect { state ->
                states.add(state)
            }
        }

        assertEquals(PlayerUiState(), viewModel.uiState.value)

        viewModel.onIntent(PlayerIntent.LoadPlayers)

        advanceUntilIdle()

        assertEquals(
            PlayerUiState(isLoading = false, error = errorMessage),
            viewModel.uiState.value
        )

        assertTrue(states.any { it.isLoading })
        assertTrue(states.any { it.error == errorMessage })

        coVerify { getPlayersUseCase.getPlayers(1) }

        job.cancel()
    }

}