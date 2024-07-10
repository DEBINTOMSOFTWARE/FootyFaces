package com.example.footyfaces.presentation.components

import BodySmallText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.footyfaces.Destination
import com.example.footyfaces.R
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.presentation.intent.PlayerIntent
import com.example.footyfaces.presentation.viewmodel.PlayersViewModel
import com.example.footyfaces.utils.PlayerImage
import com.example.footyfaces.utils.dimenResource
import com.example.footyfaces.utils.getFullImageUrl

@Composable
fun PlayerItem(
    player: PlayerEntity,
    navController: NavHostController,
    playersViewModel: PlayersViewModel
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.dp, color = Color.LightGray)
            .background(Color.White)
            .padding(dimenResource(id = R.dimen.padding_medium).dp)
            .clickable {
                if (player.id != null) {
                    navController.navigate(Destination.PlayerDetailsScreen.createRoute(player.id))
                    playersViewModel.onIntent(PlayerIntent.LoadPlayerDetails(player.id))
                }
            }
            .semantics {
                role = Role.Button
                contentDescription = "Player ${player.name},"
            }
    ) {
        PlayerImage(
            url = getFullImageUrl(player.imagePath ?: ""),
            modifier = Modifier
                .padding(dimenResource(id = R.dimen.padding_extra_small).dp)
                .height(dimenResource(id = R.dimen.player_item_image_height).dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            contentDescription = "${player.displayName}",
            contentScale = ContentScale.Crop
        )
        BodySmallText(
            text = player.displayName ?: "",
            modifier = Modifier.padding(top = dimenResource(id = R.dimen.padding_medium).dp),
            fontSize = dimenResource(id = R.dimen.font_size_small).sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}