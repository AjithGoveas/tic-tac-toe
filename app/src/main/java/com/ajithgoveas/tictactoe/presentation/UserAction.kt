package com.ajithgoveas.tictactoe.presentation

sealed class UserAction {
    object PlayAgainButtonClicked: UserAction()
    data class Boardtapped(val cellNo: Int) : UserAction()
}