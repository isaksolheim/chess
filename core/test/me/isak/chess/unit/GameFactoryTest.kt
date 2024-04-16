package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertThrows;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.SimpleGameFactory

class GameFactoryTest {

    @Test
    fun makeCorrectObjects() {
        val game = Game("horde")
        assertEquals("rnbqkbnr/pppppppp/8/1PP2PP1/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w kq - 0 1", game.fen())
    }

    @Test
    fun makeIncorrectVersion() {
        assertThrows(
            IllegalArgumentException::class.java,
            { SimpleGameFactory("illegal version", "rnbqkbnr/pppppppp/8/1PP2PP1/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w kq - 0 1") },
            "Illegal version should throw error"
        )
    }
}