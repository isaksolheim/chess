package me.isak.chess.model.base

import me.isak.chess.model.FirebaseGameModel
import kotlin.random.Random

/**
 * The main interface between client and chess logic.
 * @param version to specify which version of chess when creating the object.
 */
class Game(private val version: String, var firebaseGameModel: FirebaseGameModel? = null, var player: String? = "white") {
    private val gameFactory = SimpleGameFactory(version)
    
    private val moveCalculator = gameFactory.moveCalculator()
    private val moveExecutor = gameFactory.moveExecutor()
    private val gameOverChecker = gameFactory.gameOverChecker()
    private val gameState = gameFactory.gameState()
    private val gameHistory = gameFactory.gameHistory()
    
    private var legalMoves: List<Move> = listOf()

    var id: String = Random.nextInt(1000, 10000).toString()
    var isOnline = false
    
    /**
     * Main interaction with the game. 
     * A player may click on a square, and the state of the game will change as a result.
     * The board will either update, or the legal moves of the current player will update.
     */
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


    fun getCurrentTurn(): String {
        if (gameState.turn) {
            return "white"
        }
        return "black"
    }

    fun toJSON(): FirebaseGameModel {
        val currentTurn = getCurrentTurn()
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


