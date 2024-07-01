package com.example.footyfaces.presentation.ui

import BodySmallText
import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.footyfaces.domain.model.PaginationEntity
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.presentation.viewmodel.PlayerUiState
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.utils.PlayerImage
import com.example.footyfaces.utils.Resource
import com.example.footyfaces.utils.getFullImageUrl

@Composable
fun PlayerListScreen(playersViewModel: PlayersViewModel) {
    val uiState by playersViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when {
           uiState.isLoading -> {
              CircularProgressIndicator()
           }
            uiState.error != null -> {
                Text(text = uiState.error!!)
            }
            uiState.players.isNotEmpty() && uiState.hasMore -> {
                showPlayersGrid(
                    players = uiState.players,
                    hasMore = uiState.hasMore,
                    playersViewModel = playersViewModel
                )

//                LazyColumn {
//                    items(uiState.players) { player ->
//                        PlayerItem(player = player)
//                    }
//                    if (uiState.hasMore) {
//                        item {
//                            LaunchedEffect(Unit) {
//                                playersViewModel.onIntent(PlayerIntent.LoadMorePlayers)
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun showPlayersGrid(players: List<PlayerEntity>, hasMore: Boolean, playersViewModel: PlayersViewModel) {
    LazyVerticalGrid(
        modifier = Modifier.semantics { contentDescription = "Players List" },
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
       items(players) { player ->
           PlayerItem(player = player)
       }
        if( hasMore) {
            item {
                LaunchedEffect(Unit) {
                    playersViewModel.onIntent(PlayerIntent.LoadMorePlayers)
                }
            }

        }
    }
}

@Composable
fun PlayerItem(player: PlayerEntity) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray)
            .background(Color.White)
            .padding(16.dp)
            .wrapContentHeight()
            .clickable {

            }
            .semantics {
                role = Role.Button
                contentDescription = "Player ${player.name},"
            }
    ) {
        PlayerImage(
            url = getFullImageUrl(player.image_path ?: ""),
            modifier = Modifier
                .padding(4.dp)
                .height(120.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            contentDescription = "${player.display_name}",
            contentScale = ContentScale.Crop
        )
        BodySmallText(
            text = player.display_name ?: "",
            fontSize = 12,
            modifier = Modifier.padding(top = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

}