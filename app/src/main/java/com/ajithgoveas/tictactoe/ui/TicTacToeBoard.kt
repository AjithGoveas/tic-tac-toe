package com.ajithgoveas.tictactoe.ui

import BoardBase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeBoard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = MaterialTheme.shapes.extraLarge
            )
            .background(color = MaterialTheme.colorScheme.background)
            .clip(MaterialTheme.shapes.extraLarge),
        contentAlignment = Alignment.Center,
    ) {
        BoardBase()
    }
}