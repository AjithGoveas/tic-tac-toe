package com.ajithgoveas.tictactoe.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun Cross(modifier: Modifier = Modifier) {
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Canvas(
        modifier = modifier
            .size(60.dp)
            .padding(8.dp)
    ) {
        drawLine(
            color = secondaryColor,
            strokeWidth = 20f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height),
        )
        drawLine(
            color = secondaryColor,
            strokeWidth = 20f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width, y = 0f),
            end = Offset(x = 0f, y = size.height),
        )
    }
}