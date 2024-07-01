package com.example.footyfaces.presentation.components

import BodySmallText
import BodyText
import CustomRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.footyfaces.domain.model.PlayerEntity

@Composable
fun PlayerInformation(
    player: PlayerEntity?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .semantics {
                contentDescription = "Player Information"
            }
    ) {
        CustomRow(
            startText = "Player Information",
            fontSize = 16
        )
        CustomRow(startText = "Name", endContent = {
            BodyText(text = player?.name ?: "")
        })
        CustomRow(startText = "Date of Birth", endContent = {
            BodySmallText(
                text = player?.dateOfBirth ?: "",
                fontSize = 14
            )
        })
        CustomRow(startText = "Gender", endContent = {
            BodySmallText(
                text = player?.gender ?: "",
                fontSize = 14
            )
        })
        CustomRow(startText = "Height", endContent = {
            BodySmallText(
                text = "${player?.height ?: "0"} cm",
                fontSize = 14
            )
        })
        CustomRow(startText = "Weight", endContent = {
            BodySmallText(
                text = "${player?.weight ?: "0"} kg",
                fontSize = 14
            )
        })
    }
}