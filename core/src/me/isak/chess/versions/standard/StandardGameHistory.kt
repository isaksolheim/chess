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
        castle = castle.replace(Regex("[q]"), takeIf { !rookA8.containsMatchIn(result) }?.let { "" } ?: "q")
        castle = castle.replace(Regex("[k]"), takeIf { !rookH8.matches(result) }?.let { "" } ?: "k")
        castle = castle.replace(Regex("[Q]"), takeIf { !rookA1.matches(result) }?.let { "" } ?: "Q")
        castle = castle.replace(Regex("[K]"), takeIf { !rookH1.matches(result) }?.let { "" } ?: "K")

        if (!kingF8.matches(result)) {
            castle = castle.replace(Regex("[kq]"), "")
        }

        if (!kingF1.matches(result)) {
            castle = castle.replace(Regex("[KQ]"), "")
        }
    }
}
