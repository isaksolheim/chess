package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class MakrukTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun play() {
        val game = Game("makruk", fen = "rnbqkbnr/8/pppppppp/8/8/PPPPPPPP/8/RNBQKBNR w - - 0 1")

        val expectedStates: List<TestState> = listOf(
            TestState(40, "rnbqkbnr/8/pppppppp/8/8/PPPPPPPP/8/RNBQKBNR w - - 0 1", listOf(32)),
            TestState(32, "rnbqkbnr/8/pppppppp/8/P7/1PPPPPPP/8/RNBQKBNR b - - 0 1", empty),
            TestState(17, "rnbqkbnr/8/pppppppp/8/P7/1PPPPPPP/8/RNBQKBNR b - - 0 1", listOf(25)),
            TestState(25, "rnbqkbnr/8/p1pppppp/1p6/P7/1PPPPPPP/8/RNBQKBNR w - - 0 2", empty),
            TestState(32, "rnbqkbnr/8/p1pppppp/1p6/P7/1PPPPPPP/8/RNBQKBNR w - - 0 2", listOf(24, 25)),
            TestState(25, "rnbqkbnr/8/p1pppppp/1P6/8/1PPPPPPP/8/RNBQKBNR b - - 0 2", empty),
            TestState(16, "rnbqkbnr/8/p1pppppp/1P6/8/1PPPPPPP/8/RNBQKBNR b - - 0 2", listOf(24, 25)),
            TestState(24, "rnbqkbnr/8/2pppppp/pP6/8/1PPPPPPP/8/RNBQKBNR w - - 0 3", empty),
            TestState(25, "rnbqkbnr/8/2pppppp/pP6/8/1PPPPPPP/8/RNBQKBNR w - - 0 3", listOf(17, 18)),
            TestState(18, "rnbqkbnr/8/2Qppppp/p7/8/1PPPPPPP/8/RNBQKBNR b - - 0 3", empty),

            TestState(24, "rnbqkbnr/8/2Qppppp/p7/8/1PPPPPPP/8/RNBQKBNR b - - 0 3", listOf(32)),
            TestState(32, "rnbqkbnr/8/2Qppppp/8/p7/1PPPPPPP/8/RNBQKBNR w - - 0 4", empty),
            TestState(18, "rnbqkbnr/8/2Qppppp/8/p7/1PPPPPPP/8/RNBQKBNR w - - 0 4", listOf(11, 27, 25, 9)),
            TestState(11, "rnbqkbnr/3Q4/3ppppp/8/p7/1PPPPPPP/8/RNBQKBNR b - - 1 4", empty),
            TestState(32, "rnbqkbnr/3Q4/3ppppp/8/p7/1PPPPPPP/8/RNBQKBNR b - - 1 4", empty),
            TestState(4, "rnbqkbnr/3Q4/3ppppp/8/p7/1PPPPPPP/8/RNBQKBNR b - - 1 4", listOf(13, 12, 11)),
            TestState(11, "rnbq1bnr/3k4/3ppppp/8/p7/1PPPPPPP/8/RNBQKBNR w - - 0 5", empty),
            TestState(41, "rnbq1bnr/3k4/3ppppp/8/p7/1PPPPPPP/8/RNBQKBNR w - - 0 5", listOf(33, 32)),
            TestState(33, "rnbq1bnr/3k4/3ppppp/8/pP6/2PPPPPP/8/RNBQKBNR b - - 0 5", empty),
            TestState(32, "rnbq1bnr/3k4/3ppppp/8/pP6/2PPPPPP/8/RNBQKBNR b - - 0 5", listOf(40)),

            TestState(40, "rnbq1bnr/3k4/3ppppp/8/1P6/q1PPPPPP/8/RNBQKBNR w - - 0 6", empty),
        )
        integrationTestRunner.run(expectedStates, game)
    }
}