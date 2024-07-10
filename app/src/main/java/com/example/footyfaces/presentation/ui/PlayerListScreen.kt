package com.example.footyfaces.presentation.ui

import BodyText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footyfaces.R
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.framework.connectivity.ConnectivityObservable
import com.example.footyfaces.presentation.components.PlayerItem
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.utils.dimenResource
import kotlinx.coroutines.flow.filter

@Composable
fun PlayerListScreen(
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    val appBarTitle = stringResource(id = R.string.app_name)
    val networkUnAvailableLabel = stringResource(id = R.string.network_unavailable_label)
    val uiState by playersViewModel.uiState.collectAsState()
    val networkAvailable =
        playersViewModel.networkAvailable.observe()
            .collectAsState(ConnectivityObservable.Status.Available)

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(dimenResource(id = R.dimen.app_bar_height).dp)
                    .semantics { contentDescription = appBarTitle },
                title = {
                    BodyText(text = appBarTitle)
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = paddingValues.calculateTopPadding(),
                    horizontal = paddingValues.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (networkAvailable.value == ConnectivityObservable.Status.Unavailable) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BodyText(
                        text = networkUnAvailableLabel,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(dimenResource(id = R.dimen.padding_small).dp)
                    )
                }
            }

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
}

@Composable
fun showPlayersGrid(
    players: List<PlayerEntity>,
    hasMore: Boolean,
    isLoading: Boolean,
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    val playersListLabel = stringResource(id = R.string.players_list_label)
    val loadingProgressLabel = stringResource(id = R.string.loading_progress_label)
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        state = gridState,
        modifier = Modifier.semantics { contentDescription = playersListLabel },
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimenResource(id = R.dimen.padding_small).dp),
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
                CircularProgressIndicator(
                    modifier = Modifier.semantics { contentDescription = loadingProgressLabel }
                )
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
