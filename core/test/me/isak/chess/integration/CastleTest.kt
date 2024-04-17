package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game

class IllegalCastle {

    private val integrationTestRunner = IntegrationTestRunner()
    private val empty: List<Int> = listOf()

    @Test
    fun bothCastle() {
        val game = Game("standard", fen = "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")

        val expectedStates: List<TestState> = listOf(
            TestState(60, "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1", listOf(53, 61, 59, 51, 52, 62, 58)),
            TestState(62, "r3k2r/8/8/8/8/8/8/R4RK1 b kq - 1 1", empty),
            TestState(4, "r3k2r/8/8/8/8/8/8/R4RK1 b kq - 1 1", listOf(12, 11, 3, 2)),
            TestState(2, "2kr3r/8/8/8/8/8/8/R4RK1 w - - 2 2", empty),
        )
        integrationTestRunner.run(expectedStates, game)    
    }
    
    @Test
    fun illegalByFen() {
        val game = Game("standard", fen = "r3k2r/8/8/8/8/8/8/R3K2R w - - 0 1")  // castle rights are missing from fen

        val expectedStates: List<TestState> = listOf(
            TestState(60, "r3k2r/8/8/8/8/8/8/R3K2R w - - 0 1", listOf(53, 61, 59, 51, 52)),
            TestState(61, "r3k2r/8/8/8/8/8/8/R4K1R b - - 1 1", empty),
            TestState(4, "r3k2r/8/8/8/8/8/8/R4K1R b - - 1 1", listOf(5, 13, 12, 11, 3)),
            TestState(13, "r6r/5k2/8/8/8/8/8/R4K1R w - - 2 2", empty),
        )
        integrationTestRunner.run(expectedStates, game)    
    }
    
    @Test
    fun illegalByCover() {
        // castle is blocked by enemy
        val game = Game("standard", fen = "r3k2r/8/8/3R1r2/8/8/8/R3K2R w KQkq - 0 1")  
    
        val expectedStates: List<TestState> = listOf(
            TestState(60, "r3k2r/8/8/3R1r2/8/8/8/R3K2R w KQkq - 0 1", listOf(59, 51, 52, 58)),
            TestState(58, "r3k2r/8/8/3R1r2/8/8/8/2KR3R b kq - 1 1", empty),
            TestState(4, "r3k2r/8/8/3R1r2/8/8/8/2KR3R b kq - 1 1", listOf(5, 13, 12, 6)),
            TestState(6, "r4rk1/8/8/3R1r2/8/8/8/2KR3R w - - 2 2", empty),
        )
        integrationTestRunner.run(expectedStates, game)    
    }
}