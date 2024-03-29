package me.isak.chess.versions.standard

import me.isak.chess.game.GameState
import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move


class StandardGameState(moveCalculator: MoveCalculator): GameState(moveCalculator) {
    override fun executeMove(move: Move): Array<Char> {
        turn = !turn
        board = move.result
    
        val boardList = move.result.toCharArray().toMutableList()
    
        // Promote pawn should it reach the final rank
        val square = move.square
        val tag = move.tag
        if (tag == "P" && square / 8 == 0) {
            boardList[square] = 'Q'
        }
    
        if (tag == "p" && square / 8 == 7) {
            boardList[square] = 'q'
        }
    
        /* NEED TO ALSO CHECK FOR GAME OVER AFTER EXECUTING A MOVE !!! */
    
        return boardList.toTypedArray()
    }
    
    override fun checkGame(move: Move): Boolean {
        if (isKingInCheck(move)) return false
    
        if (illegalCastle(move)) return false
    
        return true
    }

    override fun toString(): String {
        return if (turn) "w" else "b"
    }

}