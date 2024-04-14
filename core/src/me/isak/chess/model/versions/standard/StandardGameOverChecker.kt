package me.isak.chess.model.versions.standard

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
class StandardGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory) 
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    override fun checkGameOver(): GameResult { 

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }

        if (hasLegalMove()) {
            return GameResults.active
        }

        val winner = if (gameState.turn) "Black" else "White"
        val checkmate = moveCalculator.standardIsKingInCheck(gameState.getBoard(), gameState.turn)

        return when (checkmate) {
            true -> GameResult(true, "$winner won by checkmate")
            false -> GameResults.stalemate
        }
     }
}
