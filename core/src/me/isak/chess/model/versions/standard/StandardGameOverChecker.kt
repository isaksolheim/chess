package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.MoveCalculator
import me.isak.chess.model.base.GameState
import me.isak.chess.model.base.GameOverChecker

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

    private fun hasLegalMove(): Boolean {
        return gameState.board.indices
            .any { moveCalculator.legalMoves(it).isNotEmpty() }            
    }
}
