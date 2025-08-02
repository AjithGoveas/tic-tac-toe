package com.ajithgoveas.tictactoe.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CircleOh(modifier: Modifier = Modifier) {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    Canvas(
        modifier = modifier
            .size(60.dp)
            .padding(8.dp)
    ) {
        drawCircle(
            color = tertiaryColor,
            style = Stroke(width = 20f)
        )
    }
}