package com.example.footyfaces.presentation.intent

sealed class PlayerIntent {
    data object LoadPlayers : PlayerIntent()
    data object LoadMorePlayers : PlayerIntent()
    data class LoadPlayerDetails(val playerId: Int?) : PlayerIntent()
}