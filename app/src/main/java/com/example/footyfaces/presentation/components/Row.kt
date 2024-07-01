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
import androidx.compose.ui.unit.dp

@Composable
fun CustomRow(
    startText: String,
    endContent: @Composable (() -> Unit)? = null,
    fontSize: Int = 12
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {}
                .padding(16.dp), // Adjust padding as needed
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodySmallText(
                text = startText,
                modifier = Modifier.weight(1f),
                fontSize = fontSize
            )

            endContent?.invoke() ?: Spacer(modifier = Modifier.weight(1f))
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}