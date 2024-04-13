package me.isak.chess.model.versions.threecheck

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory

/**
 * Checks if the game is over.
 * This happens when the current player have no legal moves.
 * When current player has at least one legal move, the game continues.
 * If not, check if the player lost, or the game is stalemate.
 * They lose if the king is in check.
 */
class ThreeCheckGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory) 
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    private var whiteHasBeenChecked = 0
    private var blackHasBeenChecked = 0

    override fun checkGameOver(): Boolean {
        
        val whitesTurn = gameState.turn
        val isKingInCheck = moveCalculator.standardIsKingInCheck(gameState.getBoard(), whitesTurn)

        if (whitesTurn && isKingInCheck) whiteHasBeenChecked++ 
        if (!whitesTurn && isKingInCheck) blackHasBeenChecked++ 

        
        if (whiteHasBeenChecked > 2) {
            println("black won by checking three times")
            return true
        }

        if (blackHasBeenChecked > 2) {
            println("white won by checking three times")
            return true
        }
       
        if (checkFiftyMoveRule()) {
            println("draw by fifty move rule")
            return true
        }
        
        val gameOver = !hasLegalMove()
        if (!gameOver) return false
        
        
        if (isKingInCheck) {
            val loser = if (gameState.turn) "white" else "black"
            println("$loser lost.") 
        } else {
            println("stalemate")
        }
        
        return true
     }      
}

  
