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


    @Test 
    fun blackWinByWalking() {
        val game = Game("racing", fen = "8/8/8/k6K/8/8/1rbnNBR1/qrbnNBRQ w - - 6 4")
        
        var result = game.checkGameOver()
        assertEquals(false, result.gameOver, "Game should not be over")

        val expectedStates: List<TestState> = listOf(
            TestState(31, "8/8/8/k6K/8/8/1rbnNBR1/qrbnNBRQ w - - 6 4", listOf(39, 38, 30, 23)),
            TestState(23, "8/8/7K/k7/8/8/1rbnNBR1/qrbnNBRQ b - - 7 4", empty),
            TestState(24, "8/8/7K/k7/8/8/1rbnNBR1/qrbnNBRQ b - - 7 4", listOf(25, 33, 32, 16)),
            TestState(16, "8/8/k6K/8/8/8/1rbnNBR1/qrbnNBRQ w - - 8 5", empty),
            TestState(23, "8/8/k6K/8/8/8/1rbnNBR1/qrbnNBRQ w - - 8 5", listOf(31, 30, 14)),
            TestState(14, "8/6K1/k7/8/8/8/1rbnNBR1/qrbnNBRQ b - - 9 5", empty),
            TestState(16, "8/6K1/k7/8/8/8/1rbnNBR1/qrbnNBRQ b - - 9 5", listOf(9, 25, 24)),
            TestState(9, "8/1k4K1/8/8/8/8/1rbnNBR1/qrbnNBRQ w - - 10 6", empty),
            TestState(14, "8/1k4K1/8/8/8/8/1rbnNBR1/qrbnNBRQ w - - 10 6", listOf(7, 23, 21, 13, 5, 6)),
            TestState(21, "8/1k6/5K2/8/8/8/1rbnNBR1/qrbnNBRQ b - - 11 6", empty),
            TestState(9, "8/1k6/5K2/8/8/8/1rbnNBR1/qrbnNBRQ b - - 11 6", listOf(2, 10, 18, 16, 0, 1)),
            TestState(2, "2k5/8/5K2/8/8/8/1rbnNBR1/qrbnNBRQ w - - 12 7", empty),
        )
        integrationTestRunner.run(expectedStates, game)

        result = game.checkGameOver()
        assertEquals(true, result.gameOver, "Game should be over by having the king walk all the way")
    }
}