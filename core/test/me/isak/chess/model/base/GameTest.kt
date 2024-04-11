package me.isak.chess.model.base

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    fun InitializeGameTest() {
        val game = Game("standard")
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", game.fen())
    }
}