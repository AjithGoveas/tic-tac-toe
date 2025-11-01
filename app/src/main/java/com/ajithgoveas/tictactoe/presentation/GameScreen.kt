package com.ajithgoveas.tictactoe.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A reusable Composable to display a piece of score information.
 */
@Composable
fun ScoreText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge, // Relies on titleMedium being appropriately styled
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground
    )
}

/**
 * Composable that displays the scores for Player O, Player X, and Draws.
 */
@Composable
fun ScoreDisplay(
    oScore: Int,
    xScore: Int,
    drawScore: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Added some vertical padding for the section
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between the Row and the Draw text
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Keeps original evenly spaced for two items
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreText(text = "Player 'O' : $oScore")
            ScoreText(text = "Player 'X' : $xScore")
        }
        ScoreText(text = "Draw : $drawScore")
    }
}

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        ScoreDisplay(
            oScore = state.playerCircleCount,
            xScore = state.playerCrossCount,
            drawScore = state.drawCount
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tic Tac Toe",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
                letterSpacing = 4.sp, // Changed from dp to sp
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Text(
                text = state.hintText,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        TicTacToeBoard(viewModel = viewModel)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.onAction(UserAction.PlayAgainButtonClicked)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Play Again")
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Surrender")
            }
        }

    }
}