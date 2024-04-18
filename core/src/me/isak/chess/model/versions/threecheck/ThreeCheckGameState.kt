package me.isak.chess.model.versions.threecheck

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState

class ThreeCheckGameState(simpleMoveCalculator: SimpleMoveCalculator, fen: String)
    : GameState(simpleMoveCalculator, fen) {

    private var whiteHasBeenChecked = 0
    private var blackHasBeenChecked = 0

    /**
     * Defines the way chess state changes when a move 
     * has been executed. Toggles player turn, updates board
     * and checks for pawn promotion.
     * Three check: keep track of how many times a piece has been captured.
     */
    override fun changeState(move: Move) {
        turn = !turn

        val boardArray = move.result.toCharArray().toTypedArray()
    
        promoteIfPossible(boardArray, move)

        val isKingInCheck = simpleMoveCalculator.isKingInCheck(boardArray, turn)

        if (isKingInCheck && turn) {
            whiteHasBeenChecked++
        }
        if (isKingInCheck && !turn) {
            blackHasBeenChecked++
        }

        board = boardArray.joinToString("")
    }

    fun checkCount(color: Boolean): Int {
        if (color) return whiteHasBeenChecked
        else return blackHasBeenChecked
    }

    /**
     * Checks if a move would break a rule based on 
     * the current state of the game. It is not allowed
     * to make a move that would leave your king in check, 
     * or castle should the squares between the rook and king 
     * be covered by the enemy.
     */
    override fun checkState(move: Move): Boolean {

        val board = move.result.toCharArray().toTypedArray()

        if (simpleMoveCalculator.isKingInCheck(board, turn)) return false
    
        if (illegalCastle(move)) return false
    
        return true
    }
}