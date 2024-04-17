package me.isak.chess.model.versions.makruk

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState


open class MakrukGameState(simpleMoveCalculator: SimpleMoveCalculator, fen: String)
    : GameState(simpleMoveCalculator, fen) {

    /**
     * Toggles player turn, updates board
     * and checks for pawn promotion.
     */
    override fun changeState(move: Move) {
        turn = !turn

        val boardArray = move.result.toCharArray().toTypedArray()
    
        makrukPromotion(boardArray, move)
    
        board = boardArray.joinToString("")
    }

    private fun makrukPromotion(boardArray: Array<Char>, move: Move) {
        val id = move.id
        val square = move.square

        if (id == "P" && square / 8 == 2) {
            boardArray[square] = 'Q'
        }

        if (id == "p" && square / 8 == 5 ) {
            boardArray[square] = 'q'
        }
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
    
        return true
    }
}