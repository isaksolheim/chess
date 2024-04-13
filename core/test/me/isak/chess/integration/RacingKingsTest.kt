package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move
import me.isak.chess.integration.TestState

class RacingKingsTest {

    private val empty: List<Int> = listOf()

    @Test
    fun makingOneMove() {
        val game = Game("racing")

        val expectedStates: List<TestState> = listOf(
            TestState(29, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", empty),
            TestState(40, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", empty),
            TestState(54, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", listOf(46, 38, 30, 22, 14, 6)),
            TestState(6, "6R1/8/8/8/8/8/krbnNB1K/qrbnNBRQ b - - 1 1", empty),
        )
        runIntegrationTest(expectedStates, game)     

    }

    @Test
    fun notPossibleToCheckEnemey() {
        val game = Game("racing")

        val expectedStates: List<TestState> = listOf(
            TestState(55, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", listOf(46, 47)),
            TestState(47, "8/8/8/8/8/7K/krbnNBR1/qrbnNBRQ b - - 1 1", empty),
            TestState(49, "8/8/8/8/8/7K/krbnNBR1/qrbnNBRQ b - - 1 1", listOf(33, 25, 17, 9, 1)),
        )
        runIntegrationTest(expectedStates, game)

    }

    private fun runIntegrationTest(expectedStates: List<TestState>, game: Game) {
        for (i in 0..expectedStates.size - 1) {
            val (click, expectedFen, expectedLegalMoves) = expectedStates[i]
            
            game.click(click)

            val actualFen = game.fen()
            val actualLegalMoves = game.getLegalMoves().map{ it.square } // we only care about the squares it can move to in testing. 

            assertEquals(expectedFen, actualFen, "Incorrect fen on step $i")
            assertEquals(expectedLegalMoves, actualLegalMoves, "Incorrect legal moves on step $i")
        }
    }
}