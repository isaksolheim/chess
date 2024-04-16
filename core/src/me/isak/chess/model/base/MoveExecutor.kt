package me.isak.chess.model.base

/**
 * Updates game state and history should a move be executed.
 * Returns the new board if move was executed, null otherwise.
 */
class MoveExecutor(private val gameState: GameState, private val gameHistory: GameHistory) {

    /**
     * Execute a move if possible.
     * @param list of squares the user may click to trigger a move execution.
     * @return null if no move execution is required. 
     * @return the board if a move was executed.
     */
    fun execute(legalMoves: List<Move>, square: Int): Array<Char>? {
        val move = legalMoves.find{ it.square == square } ?: return null
        gameState.changeState(move)

        gameHistory.incrementHalfMove(move)
        gameHistory.incrementFullMove(gameState.turn)
        gameHistory.changeHistory(move)

        return gameState.board.toCharArray().toTypedArray()
    }
}