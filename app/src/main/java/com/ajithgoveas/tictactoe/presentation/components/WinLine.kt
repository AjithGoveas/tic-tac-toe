package com.ajithgoveas.tictactoe.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun WinLine(
    start: Offset,
    end: Offset,
    modifier: Modifier = Modifier
) {
    val redColor = MaterialTheme.colorScheme.error
    // This Canvas will be placed over the game board
    Canvas(
        modifier = modifier
            .size(300.dp) // The size of the canvas should match the size of your BoardBase
    ) {
        drawLine(
            color = redColor,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = start,
            end = end
        )
    }
}