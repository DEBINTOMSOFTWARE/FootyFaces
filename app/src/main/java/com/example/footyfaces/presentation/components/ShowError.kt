package com.example.footyfaces.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.footyfaces.R
import com.example.footyfaces.ui.theme.Shapes
import com.example.footyfaces.utils.dimenResource

@Composable
fun ShowError(
    errorMessage: String,
    userActionMessage: String,
    actionLabel: String,
    onActionClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimenResource(id = R.dimen.padding_small).dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(text = errorMessage, fontSize = dimenResource(id = R.dimen.font_size_small).sp)
        Spacer(modifier = Modifier.height(dimenResource(id = R.dimen.margin_small).dp))
        BodySmallText(
            text = userActionMessage,
            fontSize = dimenResource(id = R.dimen.font_size_extra_small).sp
        )
        Spacer(modifier = Modifier.height(dimenResource(id = R.dimen.margin_small).dp))
        Button(
            onClick = onActionClicked,
            shape = Shapes.large,
            modifier = Modifier
                .padding(dimenResource(id = R.dimen.padding_small).dp)
                .width(dimenResource(id = R.dimen.show_error_action_button_width).dp)
                .height(dimenResource(id = R.dimen.show_error_action_button_height).dp)
        ) {
            BodySmallText(
                text = actionLabel,
                fontSize = dimenResource(id = R.dimen.font_size_small).sp,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}