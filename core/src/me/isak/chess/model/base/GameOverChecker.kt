package me.isak.chess.model.base

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

    public enum class Winner{White,Black,Draw}

    abstract fun checkGameOver(): Boolean

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
}


