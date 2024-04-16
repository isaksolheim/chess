package me.isak.chess.model.versions.racing

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.GameResult
import me.isak.chess.model.base.GameResults

/**
 * Only way to win is by reaching the 8th rank with the king. Not possible 
 * to checkmate or stalemate.
 */
class RacingGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory)
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    override fun checkGameOver(): GameResult {
        val board = gameState.getBoard()

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }

        // winner, if there is one
        val winner = if (gameState.turn) "Black" else "White"

        // check if the king that just moved reached the 8th rank (index [0, 7])
        val kingToFind = if (gameState.turn) 'k' else 'K'

        val kingIndex = board.indices
         .find{ board[it] == kingToFind }!!

         if (kingIndex < 8) return GameResult(true, "$winner won by bringing the king to the finish line")
         return standardCheck()
     }
}