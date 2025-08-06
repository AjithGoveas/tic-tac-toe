package com.ajithgoveas.tictactoe.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ajithgoveas.tictactoe.presentation.components.CircleOh
import com.ajithgoveas.tictactoe.presentation.components.Cross
import com.ajithgoveas.tictactoe.presentation.components.WinLine

@Composable
fun TicTacToeBoard(modifier: Modifier = Modifier, viewModel: GameViewModel) {
    val state = viewModel.state
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = MaterialTheme.shapes.extraLarge
            )
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .clip(MaterialTheme.shapes.extraLarge),
        contentAlignment = Alignment.Center,
    ) {
        // 1. BoardBase is drawn first, at the bottom layer.
        BoardBase()

        // 2. The interactive grid is drawn on top of the BoardBase.
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            columns = GridCells.Fixed(3),
        ) {
            items(viewModel.boardItems.keys.toList()) { cellNo ->
                val boardCellValue = viewModel.boardItems[cellNo]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            viewModel.onAction(UserAction.Boardtapped(cellNo))
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE,
                        enter = scaleIn(
                            animationSpec = tween(durationMillis = 1000)
                        ),
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

        // 3. DrawWinLine is drawn last, on top of both the board and the symbols.
        // It is passed the victoryType from the state, which correctly triggers the animation.
        DrawWinLine(
            state.victoryType,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun DrawWinLine(
    victoryType: VictoryType,
    modifier: Modifier = Modifier
) {
    // The board size should be passed as a parameter for better reusability.
    // For now, let's calculate it based on the parent size.
    val density = LocalDensity.current
    // Use the parent modifier's size to calculate offsets
    // Assuming the parent box has a fixed size of 300.dp in this context
    val boardSize = 300.dp

    when (victoryType) {
        VictoryType.HORIZONTAL1 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = 15.dp.toPx(),
                    y = (boardSize * 1 / 6).toPx() // Roughly the middle of the first row
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize).toPx(),
                    y = (boardSize * 1 / 6).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 38.dp, horizontal = 30.dp)
            )
        }

        VictoryType.HORIZONTAL2 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = 15.dp.toPx(),
                    y = (boardSize * 3 / 6).toPx() // Roughly the middle of the first row
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize).toPx(),
                    y = (boardSize * 3 / 6).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 40.dp, horizontal = 30.dp)
            )
        }

        VictoryType.HORIZONTAL3 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = 15.dp.toPx(),
                    y = (boardSize * 5 / 6).toPx() // Roughly the middle of the first row
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize).toPx(),
                    y = (boardSize * 5 / 6).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 40.dp, horizontal = 30.dp)
            )
        }

        VictoryType.VERTICAL1 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = (boardSize * 1 / 6).toPx(), // Roughly the middle of the first row
                    y = 15.dp.toPx()
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize * 1 / 6).toPx(),
                    y = (boardSize).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 30.dp, horizontal = 38.dp)
            )
        }

        VictoryType.VERTICAL2 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = (boardSize * 3 / 6).toPx(), // Roughly the middle of the first row
                    y = 15.dp.toPx()
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize * 3 / 6).toPx(),
                    y = (boardSize).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 30.dp, horizontal = 38.dp)
            )
        }

        VictoryType.VERTICAL3 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = (boardSize * 5 / 6).toPx(), // Roughly the middle of the first row
                    y = 15.dp.toPx()
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize * 5 / 6).toPx(),
                    y = (boardSize).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(vertical = 30.dp, horizontal = 40.dp)
            )
        }

        VictoryType.DIAGONAL1 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = 20.dp.toPx(), // Roughly the middle of the first row
                    y = 20.dp.toPx()
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = (boardSize).toPx(),
                    y = (boardSize).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(30.dp)
            )
        }

        VictoryType.DIAGONAL2 -> {
            val (startX, startY) = with(density) {
                // Adjust offsets based on your board's padding/size
                Offset(
                    x = (boardSize - 10.dp).toPx(),
                    y = 10.dp.toPx()
                )
            }
            val (endX, endY) = with(density) {
                Offset(
                    x = 10.dp.toPx(), // Roughly the middle of the first row
                    y = (boardSize - 10.dp).toPx()
                )
            }

            WinLine(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                isVisible = true, // isVisible is always true when the victoryType is not NONE
                modifier = modifier.padding(40.dp)
            )
        }

        else -> Unit
    }
}