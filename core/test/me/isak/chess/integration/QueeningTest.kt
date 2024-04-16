package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class QueeningTest {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun queeningTest() {
        val game = Game("standard", fen = "8/P6K/8/8/8/8/p6k/8 w - - 0 1")

        val expectedStates: List<TestState> = listOf(
            TestState(8, "8/P6K/8/8/8/8/p6k/8 w - - 0 1", listOf(0)),
            TestState(0, "Q7/7K/8/8/8/8/p6k/8 b - - 0 1", empty),
            TestState(48, "Q7/7K/8/8/8/8/p6k/8 b - - 0 1", listOf(56)),
            TestState(56, "Q7/7K/8/8/8/8/7k/q7 w - - 0 2", empty),
        )
        integrationTestRunner.run(expectedStates, game)
    }
}