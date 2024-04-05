package me.isak.chess.viewmodels

import me.isak.chess.game.Game
import me.isak.chess.move.Move
import java.util.UUID

class GameViewModel(private val game: Game) {
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

    fun getGameId(): Int {
        return game.id
    }

    fun getLegalMoves() : List<Move>
    {
        return game.getLegalMoves()
    }

    // TODO: Retrieve actual winner (white/black/none) from the chess model / firebase (?)
    fun getWinner() : String {
//        return "white"
        return "black"
//        return "none"
    }

    // TODO: Retrieve actual game over condition (checkmate/draw/resign) from the chess model / firebase (?)
    fun getGameOverCondition() : String {
//        return "checkmate"
//        return "draw"
        return "resign"
    }

    // TODO: Rename to getGameStatus? Logical view names the function "GameStatus", but if it returns a string rather than a boolean, then maybe including "message" at the end makes it a bit more intuitive?
    fun getGameStatusMessage() : String {
        //TODO: Build these strings based on values retrieved from the chessModel (would need winner(s) and the end condition (checkmate/resignation/draw))
        val winner = getWinner()
        val gameOverCondition = getGameOverCondition()
        var loser = "none"
        if (winner == "white") {
            loser = "black"
        } else if (winner == "black") {
            loser = "white"
        }

        return when (gameOverCondition) {
            "draw" -> "The players agreed to draw."
            "resign" -> "${loser.replaceFirstChar { it.titlecase() }} ${gameOverCondition}s. ${winner.replaceFirstChar { it.titlecase() }} wins!"
            else -> "${winner.replaceFirstChar { it.titlecase() }} ${gameOverCondition}s. ${winner.replaceFirstChar { it.titlecase() }} wins!"
        }
    }

    // TODO: implement resign, need to work for both local and online (firebase/chessModel?)
    // Handled through firebase or the chessModel?
        fun resign() {
    }

    // TODO: implement draw, need to work for both local and online (firebase/chessModel?)
    // Handled through firebase or the chessModel?
    fun offerDraw() {

    }

    fun onUserMove(square: Int) {
        selectedSquare?.let {
            // Attempt to move selected piece to the new square
            println(it)
            println(square)
            game.click(square)
            onBoardChanged?.invoke(game.getBoard())
            selectedSquare = null // Reset selection


        } ?: run {
            // No piece selected yet, so select the current square
            selectedSquare = square
            game.click(square)
            onLegalMovesChanged?.invoke(game.getLegalMoves())
        }

        onBoardChanged?.invoke(game.getBoard())
    }
}
