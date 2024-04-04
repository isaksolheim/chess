
package me.isak.chess.game

import me.isak.chess.models.FirebaseGameModel
import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move
import kotlin.random.Random

class Game(private val version: String) {
    val id: Int = Random.nextInt(1000, 10000)

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

    fun click(square: Int) : List<Move> {
        val move = legalMoves.find { it.square == square }

        if (move != null) {
            legalMoves = listOf()
            gameState.executeMove(move)
            gameHistory.changeHistory(move)
            return listOf()
        }

        val piece = gameState.board[square]

        if (piece == ' ' || !gameState.isPieceTurn(piece)) {
            legalMoves = listOf()
            return listOf()
        }

        legalMoves = moveCalculator.calculate(gameState.getBoard(), square) // Calculate all potentially legal moves
            .filter { gameState.checkGame(it) } // Filter away moves that are illegal for game specific reasons
            .filter { gameHistory.checkHistory(it) } // Filter away moves that are illegal for historic reasons
        return legalMoves;
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

    fun toJSON(): FirebaseGameModel {
        val currentTurn = if (gameState.turn) "white" else "black"
        // Assuming the board is stored as an Array<Char> in gameState and you want it as List<Char>
        val board = gameState.getBoardAsString()

        return FirebaseGameModel(
            board = board,
            turnId = 1,
            players = mapOf(
                "white" to "1", // player id "1"
                "black" to "2"  // player id "2"
            ),
            currentTurn = currentTurn
        )
    }
}


