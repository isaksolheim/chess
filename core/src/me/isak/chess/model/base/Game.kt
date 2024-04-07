package me.isak.chess.model.base

class Game(private val version: String) {
    private val gameFactory = SimpleGameFactory(version)
    
    private val moveCalculator = gameFactory.moveCalculator()
    private val moveExecutor = gameFactory.moveExecutor()
    private val gameOverChecker = gameFactory.gameOverChecker()
    private val gameState = gameFactory.gameState()
    private val gameHistory = gameFactory.gameHistory()
    
    private var legalMoves: List<Move> = listOf()
    
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


