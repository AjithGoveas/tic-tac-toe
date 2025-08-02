package com.ajithgoveas.tictactoe.presentation

data class GameState(
    val playerCircleCount: Int = 0,
    val playerCrossCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = "Player 'O' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType: VictoryType = VictoryType.NONE,
    val hasWon: Boolean = false
)

enum class BoardCellValue {
    CIRCLE,
    CROSS,
    NONE
}

enum class VictoryType {
    // Horizontal win lines
    HORIZONTAL1, // Top row
    HORIZONTAL2, // Middle row
    HORIZONTAL3, // Bottom row

    // Vertical win lines
    VERTICAL1,   // Left column
    VERTICAL2,   // Middle column
    VERTICAL3,   // Right column

    // Diagonal win lines
    DIAGONAL1,   // Top-left to bottom-right
    DIAGONAL2,   // Top-right to bottom-left

    // Represents no winner yet
    NONE
}
