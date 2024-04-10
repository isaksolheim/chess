
package me.isak.chess.game

import me.isak.chess.models.FirebaseGameModel
import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move
import kotlin.random.Random

class Game(private val version: String, var firebaseGameModel: FirebaseGameModel? = null, var player: String? = "white") {
    var id: String = Random.nextInt(1000, 10000).toString()
    var isOnline = false

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

    fun getCurrentTurn(): String {
        if (gameState.turn) {
            return "white"
        }
        return "black"
    }

    fun toJSON(): FirebaseGameModel {
        val currentTurn = getCurrentTurn()
        // Assuming the board is stored as an Array<Char> in gameState and you want it as List<Char>
        val board = gameState.getBoardAsString()

        return FirebaseGameModel(
            id = id,
            board = board,
            currentTurn = currentTurn
        )
    }

    /**
     * This function updates the current game with data from Firebase
     * */
    fun updateFromModel(model: FirebaseGameModel) {
        gameState.turn = model.currentTurn == "white"
        firebaseGameModel = model
        isOnline = true
        id = model.id
        gameState.setBoardAsString(model.board)
    }
}
