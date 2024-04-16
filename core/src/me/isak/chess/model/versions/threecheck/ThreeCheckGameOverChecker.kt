package me.isak.chess.model.versions.threecheck

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.GameResult
import me.isak.chess.model.base.GameResults

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

    override fun checkGameOver(): GameResult {

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }
        
        val whitesTurn = gameState.turn
        val isKingInCheck = moveCalculator.isKingInCheck(gameState.getBoard(), whitesTurn)

        if (whitesTurn && isKingInCheck) whiteHasBeenChecked++ 
        if (!whitesTurn && isKingInCheck) blackHasBeenChecked++ 

        
        if (whiteHasBeenChecked > 2) {
            return GameResult(true, "Black won by checking three times")
        }
        if (blackHasBeenChecked > 2) {
            return GameResult(true, "White won by checking three times")
        }

        if (hasLegalMove()) {
            return GameResults.active
        }

        val winner = if (gameState.turn) "Black" else "White"
        val checkmate = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)

        return when (checkmate) {
            true -> GameResult(true, "$winner won by checkmate")
            false -> GameResults.stalemate
        }
     }      
}

  
