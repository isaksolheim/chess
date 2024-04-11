package me.isak.chess.model.base

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap


class SimpleMoveCalculatorTest {

    private lateinit var mvc: SimpleMoveCalculator
    private var board: Array<Char> = arrayOf()

    @BeforeEach
    fun setUp() {
        mvc = SimpleMoveCalculator(PieceMap(standardPieceMap))
    }

    @Test
    fun parseDirectionTest() {
        assertEquals(mvc.parseDirection(""), 0)
        assertEquals(mvc.parseDirection("a"), 0)
        assertEquals(mvc.parseDirection("E"), 1)
        assertEquals(mvc.parseDirection("EE"), 2)
        assertEquals(mvc.parseDirection("ESWN"), 0)
    }

    @Test 
    fun isKingInCheckTest() {
        // Black is in check here
        board = Game("standard", fen = "7k/8/8/8/3B4/8/8/K7 w - - 0 1").getBoard()

        assertEquals(mvc.isKingInCheck(board, true), false)
        assertEquals(mvc.isKingInCheck(board, false), true)

        // No kings on the board -> not in check
        board = Game("standard", fen = "3r4/8/8/8/8/3R4/8/8 w - - 0 1").getBoard()
        
        assertEquals(mvc.isKingInCheck(board, true), false)
        assertEquals(mvc.isKingInCheck(board, false), false)
    }
    
    @Test
    fun calculatePieceCoverTest() {
        board = Game("standard", fen = "R7/8/8/8/8/8/8/8 w - - 0 1").getBoard()

        // Cover of rook on a8
        var cover = mvc.calculatePieceCover(board, 0)
        var actual = listOf(1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56)
        assertEquals(actual, cover)
    }
}