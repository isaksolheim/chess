package me.isak.chess.game

import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move
import me.isak.chess.move.SimpleMoveCalculator

val tagToCastleSquares = mapOf(
    "q" to listOf(2, 3, 4),
    "k" to listOf(4, 5, 6),
    "Q" to listOf(58, 59, 60),
    "K" to listOf(60, 61, 62)
)

abstract class GameState(val simpleMoveCalculator: SimpleMoveCalculator) {
    public var turn = true;
    public var board = "rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR";
    public var gameActive = true;

    abstract fun changeState(move: Move)
    abstract fun checkState(move: Move): Boolean

    fun getBoard(): Array<Char> {
        return board.toCharArray().toTypedArray()
    }

    fun getBoardAsString(): String {
        return board
    }

    fun isPieceTurn(piece: Char): Boolean {
        return piece.isUpperCase() == turn
    } 


    /* Return TRUE only if the move is attempting illegal castle */
    protected fun illegalCastle(move: Move): Boolean {
        val id = move.id

        val squaresToClear = tagToCastleSquares[id] ?: return false

        val board = move.result.toCharArray().toTypedArray()
        
        return simpleMoveCalculator
        .calculateTeamCover(board, !turn)
        .any{ squaresToClear.contains(it) }
    }

}