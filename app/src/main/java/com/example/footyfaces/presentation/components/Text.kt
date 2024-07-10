import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.footyfaces.R
import com.example.footyfaces.utils.dimenResource
import org.w3c.dom.Text

@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.End,
    color: Color = MaterialTheme.colorScheme.surface,
    fontSize: TextUnit = dimenResource(id = R.dimen.font_size_normal).sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        fontWeight = FontWeight.Normal,
        lineHeight = dimenResource(id = R.dimen.line_height).sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
}

@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = dimenResource(id = R.dimen.font_size_small).sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        fontWeight = fontWeight,
        lineHeight = dimenResource(id = R.dimen.line_height).sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
}