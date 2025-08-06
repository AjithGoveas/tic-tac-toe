package com.ajithgoveas.tictactoe.presentation

sealed class UserAction {
    object PlayAgainButtonClicked: UserAction()

    object SurrenderButtonClicked: UserAction()
    data class Boardtapped(val cellNo: Int) : UserAction()
}