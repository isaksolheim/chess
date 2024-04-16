package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import me.isak.chess.model.base.Game
import org.junit.jupiter.api.Assertions.assertEquals;

class FiftyMoveTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun FiftyMoveRuleTest() {
        // All versions where the fifty move rule apply
        val versions = listOf("standard", "fischer", "koth", "racing", "threecheck")

        for (version in versions) {
            runFiftyMoveTest(version)
        }
    }

    private fun runFiftyMoveTest(version: String) {
        
        val game = Game(version, fen = "rnbqpbnr/pppppppk/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 49 100")
        
        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over $version")

        if (version == "fischer" || version == "racing") {
            // in fischer or racing, we need to find the knight before clicking on it...
            val board = game.getBoard()
            val knightIndex = board.indices.find { board[it] == 'N' } ?: throw Error("Knight could not be found")
            val knightJump = knightIndex - 15

            game.click(knightIndex)
            game.click(knightJump)
        } else {
            // In all versions we already know where the knight is
            game.click(62)
            game.click(45)
        }

        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "Game should be over by 50 move rule in version $version")
    }
}