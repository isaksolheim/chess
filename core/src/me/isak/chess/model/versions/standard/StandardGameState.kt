package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState


open class StandardGameState(simpleMoveCalculator: SimpleMoveCalculator, board: String, turn: Boolean)
    : GameState(simpleMoveCalculator, board, turn) {

    /**
     * Defines the way standard chess state changes when a move 
     * has been executed. Toggles player turn, updates board
     * and checks for pawn promotion.
     */
    override fun changeState(move: Move) {
        turn = !turn

        val boardArray = move.result.toCharArray().toTypedArray()
    
        promoteIfPossible(boardArray, move)
    
        board = boardArray.joinToString("")
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

        if (simpleMoveCalculator.standardIsKingInCheck(board, turn)) return false
    
        if (illegalCastle(move)) return false
    
        return true
    }

    override fun toString(): String {
        return if (turn) "w" else "b"
    }
}