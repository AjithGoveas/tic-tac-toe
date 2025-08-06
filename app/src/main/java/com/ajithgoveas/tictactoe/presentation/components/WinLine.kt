package com.ajithgoveas.tictactoe.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

private const val ANIMATION_DURATION_MILLIS = 600

@Composable
fun WinLine(
    start: Offset,
    end: Offset,
    isVisible: Boolean, // This boolean state is the key to the animation
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.error,
    strokeWidth: Float = 10f,
    strokeCap: StrokeCap = StrokeCap.Round,
    animationDurationMillis: Int = ANIMATION_DURATION_MILLIS
) {

    var animateLine by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        animateLine = isVisible
    }

    // This function animates the value of animatedFraction from 0f to 1f
    val animatedFraction by animateFloatAsState(
        targetValue = if (animateLine) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDurationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "winLineLength"
    )

    // This Canvas will be placed over the game board
    Canvas(
        modifier = modifier
    ) {
        // The line is only drawn if the animated fraction is greater than 0,
        // which means the animation has started.
        if (animatedFraction > 0f) {
            // This is the core of the animation:
            // It calculates a new endpoint for the line based on the current
            // animatedFraction. As animatedFraction goes from 0 to 1,
            // currentEnd moves from 'start' to 'end'.
            val currentEnd = start + (end - start) * animatedFraction

            drawLine(
                color = lineColor,
                strokeWidth = strokeWidth,
                cap = strokeCap,
                start = start,
                end = currentEnd
            )
        }
    }
}