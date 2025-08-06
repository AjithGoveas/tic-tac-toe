package com.ajithgoveas.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajithgoveas.tictactoe.presentation.GameScreen
import com.ajithgoveas.tictactoe.presentation.GameViewModel
import com.ajithgoveas.tictactoe.presentation.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                val viewModel = viewModel<GameViewModel>()
                HomeScreen(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background),
                    gameViewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel
) {
    GameScreen(modifier = modifier, viewModel = gameViewModel)
}

@Preview(showBackground = false)
@Composable
private fun HomeScreenPreview() {
    TicTacToeTheme {
        HomeScreen(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background),
            viewModel<GameViewModel>()
        )
    }
}