package com.example.footyfaces.presentation.ui

import BodyText
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.footyfaces.R
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.components.PlayerInformation
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.utils.PlayerImage
import com.example.footyfaces.utils.dimenResource
import com.example.footyfaces.utils.getFullImageUrl

@Composable
fun PlayerDetailsScreen(
    playersViewModel: PlayersViewModel,
    navController: NavHostController
) {
    val playerDetailsScreenLabel = stringResource(id = R.string.player_details_screen_label)
    val uiState by playersViewModel.uiState.collectAsState()

    PlayerDetails(modifier = Modifier.semantics {
        contentDescription = playerDetailsScreenLabel
    }, player = uiState.playerDetails, navController = navController)
}

@Composable
fun PlayerDetails(
    modifier: Modifier,
    player: PlayerEntity?,
    navController: NavHostController
) {
    val appbarTitle = stringResource(id = R.string.app_name)
    val goBackLabel = stringResource(id = R.string.go_back_label)
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(modifier = modifier
                .height(dimenResource(id = R.dimen.app_bar_height).dp)
                .semantics { contentDescription = appbarTitle },
                title = {
                    BodyText(text = player?.displayName ?: "")
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.semantics {
                            role = Role.Button
                            contentDescription = goBackLabel
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
                    .height(dimenResource(id = R.dimen.player_image_height).dp)
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
                contentDescription = "${player?.displayName}",
                contentScale = ContentScale.Crop
            )
            PlayerInformation(player = player)
        }
    }
}