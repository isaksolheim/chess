package me.isak.chess.viewmodels

import me.isak.chess.Chess
import me.isak.chess.game.Game
import me.isak.chess.move.Move
import java.util.UUID

class GameViewModel(private val game: Game, val app: Chess) {
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

    fun getGameId(): String {
        if (game.firebaseGameModel != null) {
            return game.firebaseGameModel.id
        }
        return game.id
    }

    fun getLegalMoves() : List<Move>
    {
        return game.getLegalMoves()
    }

    fun onUserMove(square: Int) {
        println(game.isOnline)
        println(game.getCurrentTurn())
        println(game.player)
        if (!game.isOnline || (game.isOnline && game.getCurrentTurn() == game.player)) {
            selectedSquare?.let {
                // Attempt to move selected piece to the new square
                println(it)
                println(square)
                game.click(square)
                onBoardChanged?.invoke(game.getBoard())
                selectedSquare = null // Reset selection

                // TODO: Only call this if move was actually done
                if (game.isOnline) {
                    var boardAsString = game.getBoardAsString()
                    game.firebaseGameModel?.board = boardAsString
                    game.firebaseGameModel?.currentTurn = game.getCurrentTurn()
                    println("THIS IS CALLED!!")
                    app.firebase.pushValue(game.id, game.firebaseGameModel!!)
                }
            } ?: run {
                // No piece selected yet, so select the current square
                selectedSquare = square
                game.click(square)
                onLegalMovesChanged?.invoke(game.getLegalMoves())
            }

            onBoardChanged?.invoke(game.getBoard())
        }
    }
}
