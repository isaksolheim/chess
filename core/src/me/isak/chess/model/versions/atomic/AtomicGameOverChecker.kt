package me.isak.chess.model.versions.atomic

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.GameResult
import me.isak.chess.model.base.GameResults

/**
 * Checks if the game is over.
 * This happens when the current player have no legal moves, or the king gets blown up.
 * When current player has at least one legal move, the game continues.
 * If not, check if the player lost, or the game is stalemate.
 * They lose if the king is in check.
 */
class AtomicGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory) 
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    override fun checkGameOver(): GameResult { 

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }

        val kingToFind = if (gameState.turn) 'K' else 'k'

        val king = gameState.getBoard().contains(kingToFind)
        val winner = if (gameState.turn) "Black" else "White"

        if (!king) {
            return GameResult(true, "$winner won by blowing up the king")
        }
        return standardCheck()
     }
}
