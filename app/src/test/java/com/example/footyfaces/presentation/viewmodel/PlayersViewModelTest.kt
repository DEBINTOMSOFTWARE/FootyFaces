package com.example.footyfaces.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.footyfaces.TestConstants
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.framework.connectivity.ConnectivityMonitor
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
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

@ExperimentalCoroutinesApi
class PlayersViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getPlayersUseCase: GetPlayers
    private lateinit var connectivityMonitor: ConnectivityMonitor
    private lateinit var viewModel: PlayersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getPlayersUseCase = mockk()
        connectivityMonitor = mockk()
        viewModel = PlayersViewModel(getPlayersUseCase, connectivityMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun givenViewModel_whenLoadPlayers_thenUpdatesUiState() = testScope.runTest {
        val players = listOf(
            PlayerEntity(
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
        )

        val pagination = PaginationEntity(
            count = TestConstants.PAGINATION_COUNT,
            currentPage = TestConstants.PAGINATION_CURRENT_PAGE,
            hasMore = TestConstants.PAGINATION_HAS_MORE,
            nextPage = TestConstants.PAGINATION_NEXT_PAGE,
            perPage = TestConstants.PAGINATION_PER_PAGE
        )

        coEvery { getPlayersUseCase.getPlayers(TestConstants.PAGE) } returns flow {
            emit(Resource.Success(Pair(players, pagination)))
        }

        val states = mutableListOf<PlayerUiState>()
        val job = launch {
            viewModel.uiState.collect { state ->
                states.add(state)
            }
        }

        assertEquals(PlayerUiState(), viewModel.uiState.value)
        viewModel.onIntent(PlayerIntent.LoadPlayers)
        viewModel.loadPlayers()

        advanceUntilIdle()

        assertEquals(
            PlayerUiState(
                players = players,
                isLoading = false,
                currentPage = TestConstants.PAGE,
                hasMore = TestConstants.PAGINATION_HAS_MORE
            ),
            viewModel.uiState.value
        )

        coVerify { getPlayersUseCase.getPlayers(TestConstants.PAGE) }
        job.cancel()
    }
}