package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import me.isak.chess.model.base.Game
import org.junit.jupiter.api.Assertions.assertEquals;

class StandardChessTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun ScholarsMate() {
        val game = Game("standard")

        val expectedStates: List<TestState> = listOf(
            TestState(52, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", listOf(44, 36)),
            TestState(36, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", empty),
            TestState(8, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", listOf(16, 24)),
            TestState(24, "rnbqkbnr/1ppppppp/8/p7/4P3/8/PPPP1PPP/RNBQKBNR w KQkq a6 0 2", empty),
            TestState(61, "rnbqkbnr/1ppppppp/8/p7/4P3/8/PPPP1PPP/RNBQKBNR w KQkq a6 0 2", listOf(52, 43, 34, 25, 16)),
            TestState(34, "rnbqkbnr/1ppppppp/8/p7/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2", empty),
            TestState(1, "rnbqkbnr/1ppppppp/8/p7/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2", listOf(18, 16)),
            TestState(16, "r1bqkbnr/1ppppppp/n7/p7/2B1P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3", empty),
            TestState(59, "r1bqkbnr/1ppppppp/n7/p7/2B1P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3", listOf(52, 45, 38, 31)),
            TestState(45, "r1bqkbnr/1ppppppp/n7/p7/2B1P3/5Q2/PPPP1PPP/RNB1K1NR b KQkq - 3 3", empty),
            TestState(16, "r1bqkbnr/1ppppppp/n7/p7/2B1P3/5Q2/PPPP1PPP/RNB1K1NR b KQkq - 3 3", listOf(1, 26, 33)),
            TestState(33, "r1bqkbnr/1ppppppp/8/p7/1nB1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq - 4 4", empty),
            TestState(45, "r1bqkbnr/1ppppppp/8/p7/1nB1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq - 4 4", listOf(38, 31, 46, 47, 52, 59, 44, 43, 42, 41, 40, 37, 29, 21, 13)),
            TestState(13, "r1bqkbnr/1ppppQpp/8/p7/1nB1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4", empty),
        )
        integrationTestRunner.run(expectedStates, game)     
    }
    
    @Test
    fun drawByFiftyMoveRule() {
        val game = Game("standard", fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 49 100")

        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")
        
        val expectedStates: List<TestState> = listOf(
            TestState(62, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 49 100", listOf(47, 45)),
            TestState(45, "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 50 100", empty),
        )
        integrationTestRunner.run(expectedStates, game)     
        
        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "Game should be over by 50 move rule")
    }
}