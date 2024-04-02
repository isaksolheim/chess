package me.isak.chess.viewmodels

import me.isak.chess.game.Game
import me.isak.chess.move.Move
import me.isak.chess.sound.SoundController

class GameViewModel(private val game: Game) {
    val soundController = SoundController.getInstance()
    var onBoardChanged: ((Array<Char>) -> Unit)? = null
    var onLegalMovesChanged: ((List<Move>) -> Unit)? = null
    private var selectedSquare: Int? = null

    init {
        onBoardChanged?.invoke(game.getBoard())
        onLegalMovesChanged?.invoke(game.getLegalMoves())
    }

    fun getBoard(): Array<Char> {
        return game.getBoard()
    }

    fun getLegalMoves() : List<Move>
    {
        return game.getLegalMoves()
    }

    fun onUserMove(square: Int) {
        selectedSquare?.let {
            // Attempt to move selected piece to the new square
            println(it)
            println(square)
            game.click(square)
            onBoardChanged?.invoke(game.getBoard())
            selectedSquare = null // Reset selection

            //Play sound
            soundController.playMenuSoundEffect(SoundController.MenueSounds.Click)


        } ?: run {
            // No piece selected yet, so select the current square
            selectedSquare = square
            game.click(square)
            onLegalMovesChanged?.invoke(game.getLegalMoves())

            //Play sound
            soundController.playMenuSoundEffect(SoundController.MenueSounds.Click)
        }

        onBoardChanged?.invoke(game.getBoard())
    }
}
