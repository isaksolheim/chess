package me.isak.chess.model.base


/**
 * @property gameOver to indicate if the game is over
 * @property message to explain how the game ended
 */
data class GameResult(val gameOver: Boolean, val message: String)

object GameResults {
    val active = GameResult(false, "")
    val stalemate = GameResult(true, "Draw by stalemate")
    val wCheckmate = GameResult(true, "White won by checkmate")
    val fiftyMove = GameResult(true, "Draw by the fifty move rule")
}

/**
 * Each version of chess might have their own definition of when the game is over. 
 * Therefore, a new version might require its own implementation of the GameOverChecker class.
 * Standard chess ends when the current player can make no legal moves.
 * King of the Hill ends when the king reaches one of the center squares.
 * 
 * @param moveCalculator might be needed to analyse the game.
 * @gameState is needed to access the board. 
 */
abstract class GameOverChecker(protected val moveCalculator: MoveCalculator, protected val gameState: GameState, protected val gameHistory: GameHistory) {

    abstract fun checkGameOver(): GameResult

    /**
     * Check if the current player has any legal moves available
     * @return true if they do
     */
    fun hasLegalMove(): Boolean {
        return gameState.board.indices
            .any { moveCalculator.legalMoves(it).isNotEmpty() }            
    }

    /**
     * Check if the game is over by 50 move rule
     * Happens when 50 half moves have been completed
     * without a capture or pawn move.
     */
    fun checkFiftyMoveRule(): Boolean {
        return gameHistory.halfMove > 49
    }

    /**
     * The default checkmate functionality, used in many versions of chess.
     */
    fun standardCheck(): GameResult {

        if (hasLegalMove()) {
            return GameResults.active
        }

        val winner = if (gameState.turn) "Black" else "White"
        val checkmate = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)

        if (checkmate) {
            return GameResult(true, "$winner won by checkmate")
        } else {
            return GameResults.stalemate
        }
    }
}


