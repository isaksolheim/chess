package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.Move

val rookH1 = Regex("R$")
val rookA1 = Regex("R.{7}$")
val rookH8 = Regex("^.{7}r")
val rookA8 = Regex("^r")

/**
 * Keeps track of relevant history information for standard chess.
 * This is enPassant possibilities and castling rights for each side.

class StandardGameHistory(fen: String): GameHistory(fen) {


    /**
     * Use the move id to determine if it requires a history check.
     * For en passant move, return true if the landing square of the pawn
     * is stored. For castling, return true if the id still remains.
     */
    override fun checkHistory(move: Move): Boolean {

        return when (move.id) {
            "enPassant" -> move.square == enPassant
            in "KQkq" -> castle.contains(move.id)
            else -> true
        }
    }

    /**
     * Only need to change state when a pawn moves two forward, 
     * a king or a rook moves. 
     */
    override fun changeHistory(move: Move) {
        val (square, result, id) = move

        enPassant = -1

        // If a pawn moves two forward, store the square behind it
        // for one move. If a king moves, remove castling rights (both ways)
        when (id) {
            "wPawnDoubleForward" -> enPassant = square + 8
            "bPawnDoubleForward" -> enPassant = square - 8
            "bk", "k", "q" -> castle = castle.replace(Regex("[kq]"), "")
            "wk", "K", "Q" -> castle = castle.replace(Regex("[KQ]"), "")
        }

        // Check if the rooks are not in the starting position.
        // if they are not, remove castling rights towards that rook.
        // This can probably be done more efficiently...
        if (!rookA8.containsMatchIn(result)) {
            castle = castle.replace("q", "")
        }
        if (!rookH8.containsMatchIn((result))) {
            castle = castle.replace("k", "")
        }
        if (!rookA1.containsMatchIn((result))) {
            castle = castle.replace("Q", "")
        }
        if (!rookH1.containsMatchIn((result))) {
            castle = castle.replace("K", "")
        }
    }


}
