package com.example.footyfaces.presentation.ui

import BodyLargeText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.components.PlayerInformation
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.utils.PlayerImage
import com.example.footyfaces.utils.getFullImageUrl

@Composable
fun PlayerDetailsScreen(
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    val uiState by playersViewModel.uiState.collectAsState()

    PlayerDetails(modifier = Modifier.semantics {
        contentDescription = "Player Details Screen"
    }, player = uiState.playerDetails, navController = navController)
}

@Composable
fun PlayerDetails(
    modifier: Modifier,
    player: PlayerEntity?,
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(modifier = modifier
                .height(60.dp)
                .semantics { contentDescription = "AppBar" },
                title = {
                    BodyLargeText(text = player?.displayName ?: "")
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.semantics {
                            role = Role.Button
                            contentDescription = "Go back"
                        }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(
                    vertical = paddingValues.calculateTopPadding(),
                    horizontal = paddingValues.calculateBottomPadding()
                )
                .background(MaterialTheme.colorScheme.secondary)

        ) {
            PlayerImage(
                url = getFullImageUrl(player?.imagePath ?: ""),
                modifier = Modifier
                    .padding(4.dp)
                    .height(400.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentDescription = "${player?.displayName}",
                contentScale = ContentScale.Crop
            )
            PlayerInformation(player = player)
        }
    }
}