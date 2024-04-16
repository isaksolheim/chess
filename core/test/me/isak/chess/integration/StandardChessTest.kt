package me.isak.chess.integration

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.Game

class StandardChessTest {

    @Test
    fun initialSetupTest() {
        val game = Game("standard")

        val actualBoard = game.fen()
        val expectedBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

        assertEquals(expectedBoard, actualBoard, "Initial board setup is incorrect.")
    }

    @Test
    fun StandardChessTest() {
        val game = Game("standard")

        var actualBoard = game.fen()
        var actualLegalMoves = game.getLegalMoves().map{ it.square }

        var expectedLegalMoves: List<Int> = listOf()
        var expectedBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

        assertEquals(actualBoard, expectedBoard)
        assertEquals(actualLegalMoves, expectedLegalMoves)
        
        game.click(53)

        actualBoard = game.fen()
        actualLegalMoves = game.getLegalMoves().map{ it.square }

        expectedLegalMoves = listOf(45, 37)
        expectedBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

        assertEquals(actualBoard, expectedBoard)
        assertEquals(actualLegalMoves, expectedLegalMoves)     
        
        
        game.click(37)

        actualBoard = game.fen()
        actualLegalMoves = game.getLegalMoves().map{ it.square }

        expectedLegalMoves = listOf()
        expectedBoard = "rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq f3 0 1"

        assertEquals(actualBoard, expectedBoard)
        assertEquals(actualLegalMoves, expectedLegalMoves)        
    }



    @Test
    fun pawnMovementTest() {
        val game = Game("standard")
        game.click(53) // e2

        val actualBoard = game.fen()
        val actualLegalMoves = game.getLegalMoves().map { it.square }
        val expectedLegalMoves = listOf(45, 37) // e4, e3
        val expectedBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

        assertEquals(expectedBoard, actualBoard, "Board should be unchanged after selecting pawn.")
        assertEquals(expectedLegalMoves, actualLegalMoves, "Legal moves for pawn are incorrect.")
    }

    @Test
    fun pawnMoveExecutionTest() {
        val game = Game("standard")
        game.click(53) // e2
        game.click(37) // e4

        val actualBoard = game.fen()
        val actualLegalMoves = game.getLegalMoves().map { it.square }
        var expectedLegalMoves: List<Int> = listOf<Int>()
        val expectedBoard = "rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq f3 0 1"

        assertEquals(expectedBoard, actualBoard, "Board should reflect pawn to e4.")
        assertEquals(expectedLegalMoves, actualLegalMoves, "No legal moves should be returned immediately after pawn move.")
    }

    @Test
    fun knightMovementTest() {
        val game = Game("standard")
        game.click(57) // b1

        val actualBoard = game.fen()
        val actualLegalMoves = game.getLegalMoves().map { it.square }
        val expectedLegalMoves = listOf(42, 40) // a3, c3
        val expectedBoard = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

        game.click(40) // a3
        game.click(38) // Sjekker om den hopper rundt brettet

        assertEquals(expectedBoard, actualBoard, "Board should be unchanged after selecting knight.")
        assertEquals(expectedLegalMoves, actualLegalMoves, "Legal moves for knight are incorrect.")
    }




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
}