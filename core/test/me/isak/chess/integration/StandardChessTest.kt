package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move

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

}