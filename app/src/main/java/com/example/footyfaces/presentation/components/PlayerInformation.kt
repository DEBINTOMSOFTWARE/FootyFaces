package com.example.footyfaces.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import com.example.footyfaces.R
import com.example.footyfaces.domain.model.PlayerEntity
import com.example.footyfaces.utils.dimenResource

@Composable
fun PlayerInformation(
    player: PlayerEntity?,
) {
    val playerInformationLabel = stringResource(id = R.string.player_information_label)
    val nameLabel = stringResource(id = R.string.name_label)
    val dateOfBirthLabel = stringResource(id = R.string.dob_label)
    val genderLabel = stringResource(id = R.string.gender_label)
    val heightLabel = stringResource(id = R.string.height_label)
    val weightLabel = stringResource(id = R.string.weight_label)
    val cmLabel = stringResource(id = R.string.cm_label)
    val kgLabel = stringResource(id = R.string.kg_label)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .semantics {
                contentDescription = playerInformationLabel
            }
    ) {
        CustomRow(
            startText = playerInformationLabel,
            fontSize = dimenResource(id = R.dimen.font_size_normal).sp
        )
        CustomRow(startText = nameLabel, endContent = {
            BodySmallText(
                text = player?.name ?: "",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp
            )
        })
        CustomRow(startText = dateOfBirthLabel, endContent = {
            BodySmallText(
                text = player?.dateOfBirth ?: "",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp
            )
        })
        CustomRow(startText = genderLabel, endContent = {
            BodySmallText(
                text = player?.gender ?: "",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp
            )
        })
        CustomRow(startText = heightLabel, endContent = {
            BodySmallText(
                text = "${player?.height ?: "0"} $cmLabel",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp
            )
        })
        CustomRow(startText = weightLabel, endContent = {
            BodySmallText(
                text = "${player?.weight ?: "0"} $kgLabel",
                fontSize = dimenResource(id = R.dimen.font_size_small).sp
            )
        })
    }
}