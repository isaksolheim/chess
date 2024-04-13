package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class RacingKingsTest {

    private val integrationTestRunner = IntegrationTestRunner()
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
        integrationTestRunner.run(expectedStates, game)     
    }

    @Test
    fun notPossibleToCheckEnemey() {
        val game = Game("racing")

        val expectedStates: List<TestState> = listOf(
            TestState(55, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", listOf(46, 47)),
            TestState(47, "8/8/8/8/8/7K/krbnNBR1/qrbnNBRQ b - - 1 1", empty),
            TestState(49, "8/8/8/8/8/7K/krbnNBR1/qrbnNBRQ b - - 1 1", listOf(33, 25, 17, 9, 1)),
        )
        integrationTestRunner.run(expectedStates, game)
    }
}