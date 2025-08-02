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
import com.ajithgoveas.tictactoe.ui.GameScreen
import com.ajithgoveas.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                HomeScreen(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    GameScreen(modifier = modifier)
}

@Preview(showBackground = false)
@Composable
private fun GameScreenPreview() {
    TicTacToeTheme {
        HomeScreen(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}