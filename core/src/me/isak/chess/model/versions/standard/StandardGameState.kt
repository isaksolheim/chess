package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState

open class StandardGameState(simpleMoveCalculator: SimpleMoveCalculator): GameState(simpleMoveCalculator) {
    override fun changeState(move: Move) {
        turn = !turn

        val boardList = move.result.toCharArray().toMutableList()
    
        // Promote pawn should it reach the final rank
        val square = move.square
        val id = move.id
        if (id == "P" && square / 8 == 0) {
            boardList[square] = 'Q'
        }
    
        if (id == "p" && square / 8 == 7) {
            boardList[square] = 'q'
        }
    
        board = boardList.joinToString("")
    }
    override fun checkState(move: Move): Boolean {

        val board = move.result.toCharArray().toTypedArray()

        if (simpleMoveCalculator.isKingInCheck(board, turn)) return false
    
        if (illegalCastle(move)) return false
    
        return true
    }

    override fun toString(): String {
        return if (turn) "w" else "b"
    }
}