package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker

/**
 * Checks if the game is over.
 * This happens when the current player have no legal moves.
 * When current player has at least one legal move, the game continues.
 * If not, check if the player lost, or the game is stalemate.
 * They lose if the king is in check.
 */
class StandardGameOverChecker(private val moveCalculator: MoveCalculator, private val gameState: GameState) : GameOverChecker(moveCalculator, gameState) {

    override fun checkGameOver(): Boolean { 

        val gameOver = !hasLegalMove()
        if (!gameOver) return false

        val isKingInCheck = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)

        if (isKingInCheck) {
            val loser = if (gameState.turn) "white" else "black"
            println("$loser lost.") 
        } else {
            println("stalemate")
        }
        return true
     }
}
