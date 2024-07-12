package com.example.footyfaces.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.footyfaces.R
import com.example.footyfaces.utils.dimenResource

@Composable
fun CustomRow(
    startText: String,
    endContent: @Composable (() -> Unit)? = null,
    fontSize: TextUnit = dimenResource(id = R.dimen.font_size_small).sp
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {}
                .padding(dimenResource(id = R.dimen.padding_small).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodySmallText(
                text = startText,
                modifier = Modifier.weight(1f),
                fontSize = fontSize
            )

            endContent?.invoke() ?: Spacer(modifier = Modifier.weight(1f))
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = dimenResource(id = R.dimen.padding_small).dp))
    }
}