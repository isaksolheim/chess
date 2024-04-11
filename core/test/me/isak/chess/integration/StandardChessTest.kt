package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move

class StandardChessTest {

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
}