package me.isak.chess.model.versions.horde

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.GameResult
import me.isak.chess.model.base.GameResults

class HordeGameOverChecker(moveCalculator: MoveCalculator, gameState: GameState, gameHistory: GameHistory) 
    : GameOverChecker(moveCalculator, gameState, gameHistory) {

    /**
     * White loses if they have less than two pieces
     * Black loses if they are checkmated like normal.
     * Stalemate works like normal.
     * The logic here can be improved. I will fix that later, 
     * when we decide how game over should be handled (by UI).
     */
    override fun checkGameOver(): GameResult {

        if (checkFiftyMoveRule()) {
            return GameResults.fiftyMove
        }

        return when (gameState.turn) {
            true -> checkGameOverForWhite()
            false -> checkGameOverForBlack()
        }
    }

    private fun checkGameOverForWhite(): GameResult {
        val board = gameState.getBoardAsString()
    
        val whiteLost = board.count { it.isUpperCase() } < 2
        if (whiteLost) {
            return GameResult(true, "Black won by capturing enough pawns")
        }
    
        return when (hasLegalMove()) {
            true -> GameResults.active
            false -> GameResults.stalemate
        }
    }

    private fun checkGameOverForBlack(): GameResult {
        val gameContinues = hasLegalMove()
        if (gameContinues) return GameResults.active
        
        val isKingInCheck = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)

        return when (isKingInCheck) {
            true -> GameResults.wCheckmate
            false -> GameResults.stalemate
        }
    }
}