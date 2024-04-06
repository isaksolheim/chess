package me.isak.chess.versions.standard
import me.isak.chess.game.GameHistory
import me.isak.chess.game.GameOverChecker
import me.isak.chess.game.GameState
import me.isak.chess.move.MoveCalculator


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
