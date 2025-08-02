package com.ajithgoveas.tictactoe.presentation

import BoardBase
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.ajithgoveas.tictactoe.presentation.components.CircleOh
import com.ajithgoveas.tictactoe.presentation.components.Cross

@Composable
fun TicTacToeBoard(modifier: Modifier = Modifier, viewModel: GameViewModel) {
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
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f),
            columns = GridCells.Fixed(3),
        ) {
            // Correct way to iterate over a Map
//            LazyVerticalGrid is designed to work with items(list) or items(count).
            items(viewModel.boardItems.keys.toList()) { cellNo ->
                val boardCellValue = viewModel.boardItems[cellNo]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            viewModel.onAction(UserAction.Boardtapped(cellNo))
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (boardCellValue == BoardCellValue.CIRCLE) {
                        CircleOh()
                    } else if (boardCellValue == BoardCellValue.CROSS) {
                        Cross()
                    }
                }
            }
        }
    }
}