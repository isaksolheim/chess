package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class ThreeCheckKingsTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun winningByCheckingThreeTimes() {
        val game = Game("threecheck")

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
    }
}