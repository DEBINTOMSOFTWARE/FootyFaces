package com.example.footyfaces.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.components.PlayerItem
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun PlayerListScreen(playersViewModel: PlayersViewModel, navController: NavHostController) {
    val uiState by playersViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when {
            uiState.error != null -> {
                Text(text = uiState.error!!)
            }

            uiState.players.isNotEmpty() && uiState.hasMore -> {
                showPlayersGrid(
                    players = uiState.players,
                    hasMore = uiState.hasMore,
                    isLoading = uiState.isLoading,
                    playersViewModel = playersViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun showPlayersGrid(
    players: List<PlayerEntity>,
    hasMore: Boolean,
    isLoading: Boolean,
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        modifier = Modifier.semantics { contentDescription = "Players List" },
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(players.size) { index ->
            val player = players[index]
            PlayerItem(
                player = player,
                navController = navController,
                playersViewModel = playersViewModel
            )
        }
        if (isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }.filter { visibleItems ->
            val totalItems = gridState.layoutInfo.totalItemsCount
            val lastVisibleItem = visibleItems.lastOrNull()?.index ?: 0
            lastVisibleItem >= totalItems - 1
        }.collect {
            if (hasMore) {
                playersViewModel.onIntent(PlayerIntent.LoadMorePlayers)
            }
        }
    }
}
