package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class HordeTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun whiteWinByCheckmate() {
        val game = Game("horde", fen = "7k/PQ6/8/8/8/8/8/8 w - - 0 1")

        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")

        game.click(8)
        game.click(0)
        
        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "White should have won by checkmate")
    }

    @Test
    fun witeStalemate() {
        val game = Game("horde", fen = "6bk/PQ6/8/8/8/8/8/8 w - - 0 1")

        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")

        game.click(8)
        game.click(0)
        
        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "Game should be over by stalemate")
    }
    @Test
    fun blackStalemate() {
        val game = Game("horde", fen = "6bk/8/8/4P3/4P3/8/8/8 b - - 0 1")

        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")

        game.click(6)
        game.click(20)
        
        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "Game should be over by stalemate")
    }
}