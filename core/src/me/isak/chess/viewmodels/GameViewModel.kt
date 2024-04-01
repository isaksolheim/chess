package me.isak.chess.viewmodels

import me.isak.chess.game.Game

class GameViewModel(private val game: Game) {
    var onBoardChanged: ((Array<Char>) -> Unit)? = null
    private var selectedSquare: Int? = null

    init {
        onBoardChanged?.invoke(game.getBoard())
    }

    fun getBoard(): Array<Char> {
        return game.getBoard()
    }

    fun onUserMove(square: Int) {
        selectedSquare?.let {
            // Attempt to move selected piece to the new square
            println(it)
            println(square)
            game.click(it)
            game.click(square)
            onBoardChanged?.invoke(game.getBoard())
            selectedSquare = null // Reset selection

        } ?: run {
            // No piece selected yet, so select the current square
            selectedSquare = square
        }

        onBoardChanged?.invoke(game.getBoard())
    }
}
