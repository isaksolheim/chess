package me.isak.chess.model.base

/**
 * Updates game state and history should a move be executed.
 * Returns the new board if move was executed, null otherwise.
 */
class MoveExecutor(private val gameState: GameState, private val gameHistory: GameHistory) {

    fun execute(legalMoves: List<Move>, square: Int): Array<Char>? {
        val move = legalMoves.find{ it.square == square } ?: return null

        gameState.changeState(move)
        gameHistory.changeHistory(move)

        return gameState.board.toCharArray().toTypedArray()
    }



}