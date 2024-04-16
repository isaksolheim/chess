package me.isak.chess.model.base

import me.isak.chess.model.FirebaseGameModel
import kotlin.random.Random

/**
 * The main interface between client and chess logic.
 *
 * This class acts as the central hub for managing game interactions, state updates, and
 * integrating with Firebase for online gameplay.
 *
 * @param version to specify which version of chess when creating the object.
 * @property firebaseGameModel An optional parameter used in online games to represent the current
 * state of the game as synchronized with Firebase
 * @property player An optional parameter indicating the player's color in online games.
 */
class Game(
    private var version: String,
    var firebaseGameModel: FirebaseGameModel? = null,
    var player: String = "white",
    var fen: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
) {

    private var gameFactory = SimpleGameFactory(version, fen)
    
    private var moveCalculator = gameFactory.moveCalculator()
    private var moveExecutor = gameFactory.moveExecutor()
    private var gameOverChecker = gameFactory.gameOverChecker()
    private var gameState = gameFactory.gameState()
    private var gameHistory = gameFactory.gameHistory()

    private var legalMoves: List<Move> = listOf()

    var id: String = Random.nextInt(1000, 10000).toString()
    var isOnline = false

    /**
     * Main interaction with the game.
     * A player may click on a square, and the state of the game will change as a result.
     * The board will either update, or the legal moves of the current player will update.
     */
    fun click(square: Int): GameResult {
        val newBoard = moveExecutor.execute(legalMoves, square)

        if (newBoard != null) {
            legalMoves = listOf()
            return gameOverChecker.checkGameOver()
        }

        legalMoves = moveCalculator.legalMoves(square)
        return GameResults.active
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
    fun getGameVersion(): String {
        return version
    }

    fun checkGameOver(): GameResult {
        return gameOverChecker.checkGameOver()
    }

    /**
     * Determines the color of the piece at a specified square on the chessboard.
     *
     * This function returns "white" for uppercase piece characters, "black" for lowercase,
     * and null if the square is empty or the input is out of bounds.
     *
     * @param square The index of the square for which to determine the piece color, ranging from 0 to 63.
     * @return The color of the piece ("white" or "black") or null if the square is empty or out of bounds.
     */
    fun getPieceColorAtSquare(square: Int): String? {
        val board = getBoard()
        if (square < 0 || square >= board.size) {
            // Square is out of bounds
            return null
        }

        val piece = board[square]
        return when {
            piece.isUpperCase() -> "white"
            piece.isLowerCase() -> "black"
            else -> null // No piece on the square or invalid input
        }
    }

    fun fen(): String {
        val gameString = (0 until 8).map { i ->
            gameState.getBoard().slice(i * 8 until (i + 1) * 8)
        }
            .map { row -> row.joinToString("") }
            .map { row -> row.replace("\\s+".toRegex()) { match -> match.value.length.toString() } }
            .joinToString("/")

        return "$gameString ${gameState.toString()} ${gameHistory.toString()}"
    }

    /**
     * Retrieves the current turn in the game.
     *
     * @return A string indicating the current turn, either "white" or "black".
     */
    fun getCurrentTurn(): String {
        if (gameState.turn) {
            return "white"
        }
        return "black"
    }

    /**
     * Serializes the current game state into a [FirebaseGameModel].
     *
     * @return A [FirebaseGameModel] instance representing the current game state.
     */
    fun toJSON(): FirebaseGameModel {
        val currentTurn = getCurrentTurn()
        val board = gameState.getBoardAsString()

        return FirebaseGameModel(
            id = id,
            version = version,
            board = board,
            currentTurn = currentTurn
        )
    }

    /**
     * Updates the game state from a [FirebaseGameModel].
     *
     * @param model The [FirebaseGameModel] containing the new state to be applied to the game.
     */
    fun updateFromModel(model: FirebaseGameModel) {
        if (model.version !== version) {
            println("Updating firebase version")
            version = model.version
            gameFactory = SimpleGameFactory(version, fen)
            moveCalculator = gameFactory.moveCalculator()
            moveExecutor = gameFactory.moveExecutor()
            gameOverChecker = gameFactory.gameOverChecker()
            gameState = gameFactory.gameState()
            gameHistory = gameFactory.gameHistory()
        }

        gameState.turn = model.currentTurn == "white"
        firebaseGameModel = model
        isOnline = true
        id = model.id
        gameState.setBoardAsString(model.board)
    }
}


