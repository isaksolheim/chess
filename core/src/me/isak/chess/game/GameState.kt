package me.isak.chess.game

import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move

var white = "[A-Z]".toRegex()

val tagToCastleSquares = mapOf(
    "q" to listOf(2, 3, 4),
    "k" to listOf(4, 5, 6),
    "Q" to listOf(58, 59, 60),
    "K" to listOf(60, 61, 62)
)

abstract class GameState(val moveCalculator: MoveCalculator, val gameOverChecker: GameOverChecker) {
    public var turn = true;
    public open var board = "rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR";
    public var gameActive = true;

    abstract fun executeMove(move: Move)
    abstract fun checkGame(move: Move): Boolean

    fun getBoard(): Array<Char> {
        return board.toCharArray().toTypedArray()
    }

    fun getBoardAsString(): String {
        return board
    }

    fun isPieceTurn(piece: Char): Boolean {
        return white.matches(piece.toString()) == turn
    } 

    protected fun isKingInCheck(move: Move): Boolean {
        val board = move.result.toCharArray()
    
        // This is where our king would end up
        val kingIndex = board.indexOfFirst { square ->
            if (turn) {
                'K' == square
            } else {
                'k' == square
            }
        }
    
        // Find all squares the enemy cover, and check if the king is in it.
        return moveCalculator.calculateEnemyCover(move.result.toCharArray().toTypedArray(), turn)
                .any { m -> m.square == kingIndex }
    }

    /* Return TRUE only if the move is attempting illegal castle */
    protected fun illegalCastle(move: Move): Boolean {
        val tag = move.tag ?: return false

        if (!tag.matches(Regex("^[KQkq]$"))) return false // no filter if tag is unrelated

        val squaresToClear = tagToCastleSquares[tag]

        val board = move.result.toCharArray().toTypedArray()
        val enemyCover = moveCalculator.calculateEnemyCover(board, turn)

        // if enemy covers relevant square, do not castle
        return enemyCover.any { m -> squaresToClear?.contains(m.square) ?: false }
    }

}