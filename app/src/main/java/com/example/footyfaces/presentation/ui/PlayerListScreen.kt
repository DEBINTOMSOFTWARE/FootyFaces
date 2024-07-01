package com.example.footyfaces.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel

@Composable
fun PlayerListScreen(playersViewModel: PlayersViewModel) {
    val uiState by playersViewModel.uiState.collectAsState()

    LazyColumn {
        items(uiState.players) { player ->
             PlayerItem(player = player)
        }
        if (uiState.hasMore) {
            item {
                LaunchedEffect(Unit) {
                  playersViewModel.onIntent(PlayerIntent.LoadMorePlayers)
                }
            }
        }
        if (uiState.isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
        if (uiState.error != null) {
            item {
                Text(text = uiState.error!!)
            }
        }
    }

}

@Composable
fun PlayerItem(player: PlayerEntity) {
    Text(text = player.display_name!!)
}