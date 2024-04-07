package me.isak.chess.model.versions.horde

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker

class HordeGameOverChecker(private val moveCalculator: MoveCalculator, private val gameState: GameState) 
    : GameOverChecker(moveCalculator, gameState) {

    /**
     * White loses if they lose all pawns.
     * Black loses if they are checkmated like normal.
     * Stalemate works like normal.
     * The logic here can be improved. I will fix that later, 
     * when we decide how game over should be handled (by UI).
     */
    override fun checkGameOver(): Boolean {

        val whitePlaying = gameState.turn

        if (whitePlaying && checkGameOverForWhite()) {
            return true
        } 
        return checkGameOverForBlack()
    }

    private fun checkGameOverForWhite(): Boolean {
        val board = gameState.getBoardAsString()

        val whiteLost = !board.contains('P')
        if (whiteLost) {
            println("White lost")
            return true
        }

        val stalemate = !hasLegalMove()
        if (stalemate) {
            println("stalemate")
            return true
        }
        return false
    }

    private fun checkGameOverForBlack(): Boolean {
        val gameContinues = hasLegalMove()
        if (gameContinues) return false
        
        val isKingInCheck = moveCalculator.isKingInCheck(gameState.getBoard(), gameState.turn)

        // TODO: handle this better
        if (isKingInCheck) {
            println("black lost")
        } else {
            println("stalemate")
        }
        return true
    }
}