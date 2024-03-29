package me.isak.chess.versions.standard

import me.isak.chess.game.GameHistory
import me.isak.chess.move.Move

import me.isak.chess.utils.parseDirection

val rookH1 = Regex("R$")
val kingF1 = Regex("K...$")
val rookA1 = Regex("R.{7}$")

val rookH8 = Regex("^.{7}r")
val kingF8 = Regex("^.{4}k")
val rookA8 = Regex("^r")

class StandardGameHistory : GameHistory {
    var enPassant: Int = -1
    var castle: String = "KQkq"

    override fun checkHistory(move: Move): Boolean {
        val tag = move.tag ?: return true // no tag means no check - move is acceptable

        return when (tag) {
            "enPassant" -> move.square == enPassant
            in "KQkq" -> castle.contains(tag)
            else -> true
        }
    }

    override fun changeHistory(move: Move) {
        val (square, result, tag) = move

        enPassant = -1

        // For pawn double moves: add the square behind to history object
        when (tag) {
            "whitePawnDoubleForward" -> enPassant = square + parseDirection("S")
            "blackPawnDoubleForward" -> enPassant = square + parseDirection("N")
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
        if (!kingF8.containsMatchIn(result)) {
            castle = castle.replace(Regex("[kq]"), "")
        }

        if (!kingF1.containsMatchIn(result)) {
            castle = castle.replace(Regex("[KQ]"), "")
        }
    }

    override fun toString(): String {
        var enPassantString = if (enPassant == -1) "-" else enPassant
        var castleString = if (castle.length == 0) "-" else castle
        return "$castleString $enPassantString"
    }
}
