package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.versions.standard.StandardGameHistory

class GameHistoryTest {
    
    @Test
    fun gameHistoryInit() {
        
        val gameHistory = StandardGameHistory("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq e3 0 1")

        assertEquals(44, gameHistory.enPassant)
        assertEquals(32, gameHistory.numberOfPieces)
        assertEquals(0, gameHistory.halfMove)
        assertEquals(1, gameHistory.fullMove)
    }
}