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
class ThreeCheckGameOverChecker(moveCalculator: MoveCalculator, private val threeCheckGameState: ThreeCheckGameState, gameHistory: GameHistory) 
    : GameOverChecker(moveCalculator, threeCheckGameState, gameHistory) {



    override fun checkGameOver(): GameResult {

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }
        
        if (threeCheckGameState.checkCount(gameState.turn) > 2) {
            val winner = if (gameState.turn) "Black" else "White"
            return GameResult(true, "$winner won by checking three times")
        }

        return standardCheck()
     }      
}

  
