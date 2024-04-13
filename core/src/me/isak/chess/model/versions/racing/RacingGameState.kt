package me.isak.chess.model.versions.racing

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState


class RacingGameState(moveCalculator: SimpleMoveCalculator, fen: String) 
    : GameState(moveCalculator, fen) {

    /**
     * Not allowed to put your own king in check, or the opponent's.
     */
    override fun checkState(move: Move): Boolean { 

        val board = move.result.toCharArray().toTypedArray()

        // filter away move if it puts any of the kings in chdck
        if (simpleMoveCalculator.standardIsKingInCheck(board, true)) return false
        if (simpleMoveCalculator.standardIsKingInCheck(board, false)) return false

        
        // no need to check for castle, it is not legal.
        return true
    }

    /**
     * No relevant state exist, because castle is not in the game and
     * en passant is not in the game.
     */
    override fun changeState(move: Move) {  }
    
}