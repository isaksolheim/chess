package me.isak.chess.model.versions.koth

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.GameResult
import me.isak.chess.model.base.GameResults

class KothGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory)
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    private val centerSquares = arrayOf(27, 28, 35, 36) // d5, e5, d4, e4

    override fun checkGameOver(): GameResult {

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }

        // winner, if the game has one
        val winner = if (gameState.turn) "Black" else "White"

        // Game can end by getting the king to the center
        if (isKingInCenter()) {
            return GameResult(true, "$winner won by bringing the king to the center")
        }

        return standardCheck()
     }

     /**
      * Checks if the king was moved to the center.
      * Important detail: gameState.turn has flipped, meaning we have to check
      * if the non-active king is in the center.
      */
     private fun isKingInCenter(): Boolean {
        val kingToLookFor = if (gameState.turn) 'k' else 'K'

        val kingIndex = gameState.board.indices
          .first{ gameState.board[it] == kingToLookFor }

        return centerSquares.contains(kingIndex)
     }
}