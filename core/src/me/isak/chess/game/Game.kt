
package me.isak.chess.game

import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move

class Game(private val version: String) {

    private val moveCalculator: MoveCalculator
    private val gameState: GameState
    private val gameHistory: GameHistory

    private var legalMoves: List<Move> = listOf()

    init {
        val gameFactory = GameFactory()
        val (cal, state, history) = gameFactory.create(version)
        moveCalculator = cal
        gameState = state
        gameHistory = history
    }

    fun click(square: Int) {
        val move = legalMoves.find { it.square == square }

        if (move != null) {
            legalMoves = listOf()
            gameState.executeMove(move)
            gameHistory.changeHistory(move)
            return
        }

        val piece = gameState.board[square]

        if (piece == ' ' || !gameState.isPieceTurn(piece)) {
            legalMoves = listOf()
            return
        }

        legalMoves = moveCalculator.calculate(gameState.getBoard(), square) // Calculate all potentially legal moves
            .filter { gameState.checkGame(it) } // Filter away moves that are illegal for game specific reasons
            .filter { gameHistory.checkHistory(it) } // Filter away moves that are illegal for historic reasons
    }

    fun getBoard(): Array<Char> {
        return gameState.getBoard()
    }

    fun getBoardAsString(): String {
        return gameState.board
    }

     fun getLegalMoves(): List<Move> {
        return legalMoves
    } 
}


