package me.isak.chess.model.versions.koth

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory

class KothGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory)
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    private val centerSquares = arrayOf(27, 28, 35, 36) // d5, e5, d4, e4

    override fun checkGameOver(): Boolean {

        // Game can end by getting the king to the center
        if (isKingInCenter()) {
            val winner = if (gameState.turn) "black" else "white"
            println("Well done to $winner, you got the king to the center")
            return true
        }
        
        if (!hasLegalMove()) {
            val stalemate = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)
            
            if (stalemate) {
                println("stalemate")
            } else {
                val winner = if (gameState.turn) "white" else "black"
                println("Well done to $winner, you won by checkmate")
            }
            return true
        }
        return false
     }

     /**
      * Checks if the king was moved to the center.
      * Important detail: gameState.turn has flipped, meaning we have to check
      * if the non-active king is in the center.
      */
     private fun isKingInCenter(): Boolean {
        val kingToLookFor = if (gameState.turn) 'k' else 'K'
        println("king is on $kingToLookFor")

        val kingIndex = gameState.board.indices
          .first{ gameState.board[it] == kingToLookFor }

        return centerSquares.contains(kingIndex)
     }
}