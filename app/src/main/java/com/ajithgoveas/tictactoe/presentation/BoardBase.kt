import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun BoardBase(modifier: Modifier = Modifier) {
    // This Box will contain both the grid lines and the game pieces
    // This Canvas draws the grid lines. It's the background layer.
    Box(
        modifier = modifier
            .size(300.dp)
            .padding(8.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Vertical Lines
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = size.width * 1 / 3, y = 0f),
                end = Offset(x = size.width * 1 / 3, y = size.height),
            )
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = size.width * 2 / 3, y = 0f),
                end = Offset(x = size.width * 2 / 3, y = size.height),
            )

            // Horizontal Lines
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = size.height * 1 / 3),
                end = Offset(x = size.width, y = size.height * 1 / 3)
            )
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = size.height * 2 / 3),
                end = Offset(x = size.width, y = size.height * 2 / 3)
            )
        }
    }
}