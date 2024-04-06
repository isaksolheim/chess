
package me.isak.chess.game

import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move
import me.isak.chess.move.MoveExecutor

class Game(private val version: String) {

    private val moveCalculator: MoveCalculator
    private val moveExecutor: MoveExecutor
    private val gameOverChecker: GameOverChecker
    private val gameState: GameState
    private val gameHistory: GameHistory

    private var legalMoves: List<Move> = listOf()

    init {
        val gameFactory = GameFactory()
        val (cal, exec, checker, state, history) = gameFactory.create(version)
        moveCalculator = cal
        moveExecutor = exec
        gameOverChecker = checker
        gameState = state
        gameHistory = history
    }

    fun click(square: Int) : List<Move> {

        val newBoard = moveExecutor.execute(legalMoves, square)

        if (newBoard != null) {
            gameOverChecker.checkGameOver()
            /* TODO: figure out how game over should be handled. */

            legalMoves = listOf()
            return legalMoves
        } 

        legalMoves = moveCalculator.legalMoves(square)
        return legalMoves
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

    fun fen(): String {
        val gameString = (0 until 8).map { i ->
            gameState.getBoard().slice(i * 8 until (i + 1) * 8) }
            .map { row -> row.joinToString("") }
            .map { row -> row.replace("\\s+".toRegex()) { match -> match.value.length.toString() }}
            .joinToString("/")

        return "$gameString ${gameState.toString()} ${gameHistory.toString()}"
    }
}


