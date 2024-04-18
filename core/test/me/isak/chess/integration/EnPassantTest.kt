package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class EnPassantTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun whiteEnPassantTest() {
        val game = Game("standard")

        val expectedStates: List<TestState> = listOf(
            TestState(51, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", listOf(43, 35)),
            TestState(35, "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1", empty),
            TestState(10, "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1", listOf(18, 26)),
            TestState(26, "rnbqkbnr/pp1ppppp/8/2p5/3P4/8/PPP1PPPP/RNBQKBNR w KQkq c6 0 2", empty),
            TestState(35, "rnbqkbnr/pp1ppppp/8/2p5/3P4/8/PPP1PPPP/RNBQKBNR w KQkq c6 0 2", listOf(26, 27)),
            TestState(27, "rnbqkbnr/pp1ppppp/8/2pP4/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2", empty),
            TestState(12, "rnbqkbnr/pp1ppppp/8/2pP4/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2", listOf(20, 28)),
            TestState(28, "rnbqkbnr/pp1p1ppp/8/2pPp3/8/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 3", empty),
            TestState(27, "rnbqkbnr/pp1p1ppp/8/2pPp3/8/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 3", listOf(19, 20)),
            TestState(20, "rnbqkbnr/pp1p1ppp/4P3/2p5/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3", empty),
        )
        integrationTestRunner.run(expectedStates, game)    
    }
}