package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class ThreeCheckKingsTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun whiteWinningByCheckingThreeTimes() {
        val game = Game("threecheck")
        
        assertEquals(false, game.checkGameOver().gameOver, "Game should not be over")
        
        val expectedStates: List<TestState> = listOf(
            TestState(52, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", listOf(44, 36)),
            TestState(36, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", empty),
            TestState(11, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", listOf(19, 27)),
            TestState(19, "rnbqkbnr/ppp1pppp/3p4/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2", empty),
            TestState(61, "rnbqkbnr/ppp1pppp/3p4/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2", listOf(52, 43, 34, 25, 16)),
            TestState(25, "rnbqkbnr/ppp1pppp/3p4/1B6/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2", empty),
            TestState(2, "rnbqkbnr/ppp1pppp/3p4/1B6/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2", listOf(11)),
            TestState(11, "rn1qkbnr/pppbpppp/3p4/1B6/4P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3", empty),
            TestState(25, "rn1qkbnr/pppbpppp/3p4/1B6/4P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3", listOf(18, 11, 34, 43, 52, 61, 32, 16)),
            TestState(11, "rn1qkbnr/pppBpppp/3p4/8/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 3", empty),
            TestState(4, "rn1qkbnr/pppBpppp/3p4/8/4P3/8/PPPP1PPP/RNBQK1NR b KQkq - 0 3", listOf(11)),
            TestState(11, "rn1q1bnr/pppkpppp/3p4/8/4P3/8/PPPP1PPP/RNBQK1NR w KQ - 0 4", empty),
            TestState(59, "rn1q1bnr/pppkpppp/3p4/8/4P3/8/PPPP1PPP/RNBQK1NR w KQ - 0 4", listOf(52, 45, 38, 31)),
            TestState(38, "rn1q1bnr/pppkpppp/3p4/8/4P1Q1/8/PPPP1PPP/RNB1K1NR b KQ - 1 4", empty),
        )
        integrationTestRunner.run(expectedStates, game)  

        val (gameOver, message) = game.checkGameOver()

        assertEquals("White won by checking three times", message)
        assertEquals(true, gameOver, "Game should be over by three check")
    }
    
    @Test
    fun blackWinningByCheckingThreeTimes() {
        val game = Game("threecheck", fen = "3RRR1K/8/8/8/8/8/8/k1b5 b - - 0 1")
        
        assertEquals(false, game.checkGameOver().gameOver, "Game should not be over")
        
        val expectedStates: List<TestState> = listOf(
            TestState(58, "3RRR1K/8/8/8/8/8/8/k1b5 b - - 0 1", listOf(51, 44, 37, 30, 23, 49, 40)),
            TestState(49, "3RRR1K/8/8/8/8/8/1b6/k7 w - - 1 2", empty),
            TestState(3, "3RRR1K/8/8/8/8/8/1b6/k7 w - - 1 2", listOf(35)),
            TestState(35, "4RR1K/8/8/8/3R4/8/1b6/k7 b - - 2 2", empty),
            TestState(49, "4RR1K/8/8/8/3R4/8/1b6/k7 b - - 2 2", listOf(42, 35, 58, 40)),
            TestState(35, "4RR1K/8/8/8/3b4/8/8/k7 w - - 0 3", empty),
            TestState(4, "4RR1K/8/8/8/3b4/8/8/k7 w - - 0 3", listOf(28)),
            TestState(28, "5R1K/8/8/4R3/3b4/8/8/k7 b - - 1 3", empty),
            TestState(35, "5R1K/8/8/4R3/3b4/8/8/k7 b - - 1 3", listOf(28, 44, 53, 62, 42, 49, 26, 17, 8)),
            TestState(28, "5R1K/8/8/4b3/8/8/8/k7 w - - 0 4", empty),

        )
        integrationTestRunner.run(expectedStates, game)  
        
        assertEquals(true, game.checkGameOver().gameOver, "Black should win by checking three times")
    }
}