package me.isak.chess.model.versions.makruk

import me.isak.chess.model.base.GameHistory
import me.isak.chess.model.base.Move

/**
 * Makruk does not have anything history wise.
 */
class MakrukGameHistory(fen: String): GameHistory(fen) {

    /**
     * No history checks in makruk.
     */
    override fun checkHistory(move: Move): Boolean {
        return true
    }

    /**
     * No changes.
     */
    override fun changeHistory(move: Move) {}
}