package com.example.footyfaces.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.framework.connectivity.ConnectivityMonitor
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.utils.ErrorEntity
import com.example.footyfaces.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayers,
    connectivityMonitor: ConnectivityMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState

    private val _intent = MutableSharedFlow<PlayerIntent>()
    private val intent: SharedFlow<PlayerIntent> = _intent

    private val allPlayers = mutableListOf<PlayerEntity>()
    val networkAvailable = connectivityMonitor


    init {
        processIntents()
        viewModelScope.launch {
            onIntent(PlayerIntent.LoadPlayers)
        }
    }

    fun onExit() {
        println("On Exit Called")
        _uiState.value = _uiState.value.copy(exit = true, error = null)
    }

    fun onIntent(playerIntent: PlayerIntent) {
        viewModelScope.launch {
            _intent.emit(playerIntent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intent.collect { playerIntent ->
                when (playerIntent) {
                    is PlayerIntent.LoadPlayers -> loadPlayers()
                    is PlayerIntent.LoadMorePlayers -> loadMorePlayers()
                    is PlayerIntent.LoadPlayerDetails -> loadPlayerDetails(playerIntent.playerId)
                }
            }
        }
    }

    suspend fun loadPlayers() {
        println("load players")
        withContext(Dispatchers.IO) {
            getPlayersUseCase.getPlayers(1).collect { resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> _uiState.value =
                            _uiState.value.copy(isLoading = true)

                        is Resource.Success -> {
                            val (players, pagination) = resource.data ?: Pair(
                                emptyList(),
                                PaginationEntity()
                            )
                            allPlayers.clear()
                            allPlayers.addAll(players)
                            _uiState.value = _uiState.value.copy(
                                players = players,
                                isLoading = false,
                                error = null,
                                currentPage = pagination.currentPage,
                                hasMore = pagination.hasMore
                            )
                        }

                        is Resource.Error -> _uiState.value =
                            _uiState.value.copy(isLoading = false, error = resource.error)
                    }
                }
            }
        }
    }

    private suspend fun loadMorePlayers() {
        println("load More Players")
        withContext(Dispatchers.IO) {
            getPlayersUseCase.getPlayers(_uiState.value.currentPage + 1).collect { resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> _uiState.value =
                            _uiState.value.copy(isLoading = true)

                        is Resource.Success -> {
                            val (players, pagination) = resource.data ?: Pair(
                                emptyList(),
                                PaginationEntity()
                            )
                            allPlayers.addAll(players)
                            _uiState.value = _uiState.value.copy(
                                players = _uiState.value.players + players,
                                isLoading = false,
                                error = null,
                                currentPage = pagination.currentPage,
                                hasMore = pagination.hasMore
                            )
                        }

                        is Resource.Error -> _uiState.value =
                            _uiState.value.copy(isLoading = false, error = resource.error)

                    }
                }
            }
        }
    }

    private fun loadPlayerDetails(playerId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            val playerDetails = allPlayers.firstOrNull { it.id == playerId }
            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(playerDetails = playerDetails)
            }
        }
    }

}

data class PlayerUiState(
    val players: List<PlayerEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorEntity? = null,
    val currentPage: Int = 1,
    val hasMore: Boolean = true,
    val playerDetails: PlayerEntity? = null,
    val exit: Boolean = false
)