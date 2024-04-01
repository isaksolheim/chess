package me.isak.chess.viewmodels

import me.isak.chess.game.Game

class GameViewModel(private val game: Game) {
    var onBoardChanged: ((Array<Char>) -> Unit)? = null

    init {
        // Initialize with the current board state
        onBoardChanged?.invoke(game.getBoard())
    }

    fun getBoard(): Array<Char> {
        return game.getBoard()
    }

    // Call this method when the user tries to make a move in the UI
    fun onUserMove(start: Int, end: Int) {
        // Convert start and end positions to a Move object
        // Execute the move and update the UI accordingly
    }
}
