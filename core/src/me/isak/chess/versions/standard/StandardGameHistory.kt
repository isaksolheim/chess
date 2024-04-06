package me.isak.chess.versions.standard

import me.isak.chess.game.GameHistory
import me.isak.chess.move.Move

val rookH1 = Regex("R$")
val rookA1 = Regex("R.{7}$")
val rookH8 = Regex("^.{7}r")
val rookA8 = Regex("^r")

class StandardGameHistory : GameHistory {
    var enPassant: Int = -1
    var castle: String = "KQkq"

    override fun checkHistory(move: Move): Boolean {

        return when (move.id) {
            "enPassant" -> move.square == enPassant
            in "KQkq" -> castle.contains(move.id)
            else -> true
        }
    }

    override fun changeHistory(move: Move) {
        val (square, result, id) = move

        enPassant = -1

        // For pawn double moves: add the square behind to history object
        when (id) {
            "wPawnDoubleForward" -> enPassant = square + 8
            "bPawnDoubleForward" -> enPassant = square - 8
            "bk", "k", "q" -> castle = castle.replace(Regex("[kq]"), "")
            "wk", "K", "Q" -> castle = castle.replace(Regex("[KQ]"), "")
        }

        // Check if rooks/kings have moved, and update castle rights accordingly.
        // This can probably be done better...
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

    override fun toString(): String {
        var enPassantString = if (enPassant == -1) "-" else enPassant
        var castleString = if (castle.length == 0) "-" else castle
        return "$castleString $enPassantString"
    }
}
