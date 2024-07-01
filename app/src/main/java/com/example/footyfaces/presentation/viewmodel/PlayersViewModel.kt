package com.example.footyfaces.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val getPlayersUseCase: GetPlayers): ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState

    private val _intent = MutableSharedFlow<PlayerIntent>()
    val intent: SharedFlow<PlayerIntent> = _intent

    init {
        processIntents()
        viewModelScope.launch {
            onIntent(PlayerIntent.LoadPlayers)
        }
    }

    fun onIntent(playerIntent: PlayerIntent) {
        viewModelScope.launch {
            _intent.emit(playerIntent)
        }
    }

    fun processIntents() {
        viewModelScope.launch(Dispatchers.IO) {
            intent.collect{ playerIntent->
                when(playerIntent) {
                    is PlayerIntent.LoadPlayers -> loadPlayers()
                    is PlayerIntent.LoadMorePlayers ->loadMorePlayers()
                }
            }
        }
    }

    suspend fun loadPlayers() {
        println("load players")
        getPlayersUseCase.getPlayers(1).collect { resource ->
            when(resource) {
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                is Resource.Success -> {
                    val (players, pagination) = resource.data!!
                    _uiState.value = _uiState.value.copy(
                        players = players,
                        isLoading = false,
                        error = null,
                        currentPage = pagination.current_page!!,
                        hasMore = pagination.has_more!!
                    )
                }
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = resource.message)
                else -> Unit
            }
        }
    }

    private suspend fun loadMorePlayers() {
        println("load More Players")
        getPlayersUseCase.getPlayers(_uiState.value.currentPage + 1).collect { resource ->
            when(resource) {
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                is Resource.Success -> {
                    val (players, pagination) = resource.data!!
                    _uiState.value = _uiState.value.copy(
                        players = _uiState.value.players + players,
                        isLoading = false,
                        error = null,
                        currentPage = pagination.current_page!!,
                        hasMore = pagination.has_more!!
                    )
                }
                is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = resource.message)
                else -> Unit
            }
        }
    }

}

data class PlayerUiState(
    val players: List<PlayerEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMore: Boolean = true
)