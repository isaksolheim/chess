package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class StalemateTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun stalemateTest() {
        // all versions with custom game over checker:
        val versions = listOf("standard", "horde", "koth", "racing", "threecheck")

        for (version in versions) {
            runStalemateTest(version)
        }
    }

    private fun runStalemateTest(version: String) {
        val game = Game(version, fen = "8/8/8/8/8/2q4k/8/7K b - - 0 1")

        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")

        game.click(42)
        game.click(46)
        
        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "should be stalemate in version $version")
    }
}