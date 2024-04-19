package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class AtomicTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun whiteBlowsUpKing() {
        val game = Game("atomic")

        val expectedStates: List<TestState> = listOf(
            TestState(62, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", listOf(47, 45)),
            TestState(45, "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1", empty),
            TestState(8, "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1", listOf(16, 24)),
            TestState(16, "rnbqkbnr/1ppppppp/p7/8/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 0 2", empty),
            TestState(45, "rnbqkbnr/1ppppppp/p7/8/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 0 2", listOf(30, 39, 62, 35, 28)),
            TestState(28, "rnbqkbnr/1ppppppp/p7/4N3/8/8/PPPPPPPP/RNBQKB1R b KQkq - 1 2", empty),
            TestState(16, "rnbqkbnr/1ppppppp/p7/4N3/8/8/PPPPPPPP/RNBQKB1R b KQkq - 1 2", listOf(24)),
            TestState(24, "rnbqkbnr/1ppppppp/8/p3N3/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 3", empty),
            TestState(28, "rnbqkbnr/1ppppppp/8/p3N3/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 3", listOf(13, 22, 38, 45, 43, 34, 18, 11)),
            TestState(13, "rnbq3r/1pppp1pp/8/p7/8/8/PPPPPPPP/RNBQKB1R b KQkq - 0 3", empty),
        )
        integrationTestRunner.run(expectedStates, game)

        val (gameOver, _) = game.checkGameOver()
        assertEquals(true, gameOver, "White should have won by blowing up the king")
    }

    @Test
    fun whiteNotAllowedToBlowUpOwnKing() {
        val game = Game("atomic", fen = "3K4/4n3/8/3N4/8/8/8/k7 w - - 0 1")

        val expectedStates: List<TestState> = listOf(
            TestState(27, "3K4/4n3/8/3N4/8/8/8/k7 w - - 0 1", listOf(21, 37, 44, 42, 33, 17, 10)),
        )
        integrationTestRunner.run(expectedStates, game)
    }

    @Test
    fun blackNotAllowedToBlowUpOwnKing() {
        val game = Game("atomic", fen = "3b4/k7/1N6/Kb6/8/8/8/8 b - - 0 1")

        val expectedStates: List<TestState> = listOf(
            TestState(3, "3b4/k7/1N6/Kb6/8/8/8/8 b - - 0 1", listOf(12, 21, 30, 39, 10)),
            TestState(10, "8/k1b5/1N6/Kb6/8/8/8/8 w - - 1 2", empty),
            TestState(17, "8/k1b5/1N6/Kb6/8/8/8/8 w - - 1 2", empty),
            TestState(24, "8/k1b5/1N6/Kb6/8/8/8/8 w - - 1 2", listOf(25, 33)),
        )
        integrationTestRunner.run(expectedStates, game)
    }
}