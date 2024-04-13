package me.isak.chess.model.versions.racing

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker

/**
 * Only way to win is by reaching the 8th rank with the king. Not possible 
 * to checkmate or stalemate.
 */
class RacingGameOverChecker(private val moveCalculator: MoveCalculator, private val gameState: GameState)
    : GameOverChecker(moveCalculator, gameState) {

    override fun checkGameOver(): Boolean {
        val board = gameState.getBoard()

        // check if the king that just moved reached the 8th rank (index [0, 7])
        val kingToFind = if (gameState.turn) 'k' else 'K'

        val kingIndex = board.indices
         .find{ board[it] == kingToFind }

        if (kingIndex in 0..7) {
            val winner = if (gameState.turn) "black" else "white"
            println("Winner is $winner")
            return true
        }
        return false
     }

}