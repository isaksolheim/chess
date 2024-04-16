package me.isak.chess.model.base.SimpleMoveCalculatorTest

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.PieceMap
import me.isak.chess.model.base.Game

class PieceCoverTest {


    private var mvc = SimpleMoveCalculator(PieceMap(standardPieceMap), "standard")
    private var board: Array<Char> = arrayOf()
    private var expected: List<Int> = listOf()
    private var actual: List<Int> = listOf()

    @Test 
    fun leaperCover0() {
        board = Game("standard").getBoard()

        expected = listOf(42, 51, 40)
        actual = mvc.calculatePieceCover(board, 57)

        assertEquals(expected, actual, "Knight should cover squares at the start of standard game")
    }

    @Test 
    fun leaperCover1() {
        board = Game("standard", fen = "8/8/2P1N3/5B2/3N4/5b2/4k3/K7 w - - 0 1").getBoard()

        expected = listOf(20, 29, 45, 52, 50, 41, 25, 18)
        actual = mvc.calculatePieceCover(board, 35)

        assertEquals(expected, actual, "Knight should cover squares around it regardless of occupant")
    }

    @Test 
    fun rangerCover0() {
        board = Game("standard").getBoard()

        expected = listOf(62, 55) 
        actual = mvc.calculatePieceCover(board, 63)

        assertEquals(expected, actual, "Rook covers g1, h2 at the start of normal game")
    }

    @Test 
    fun rangerCover1() {
        board = Game("standard", fen = "8/5r2/8/3Q2R1/8/8/6P1/8 w - - 0 1").getBoard()

        expected = listOf(20, 13, 28, 29, 30, 36, 45, 54, 35, 43, 51, 59, 34, 41, 48, 26, 25, 24, 18, 9, 0, 19, 11, 3)
        actual = mvc.calculatePieceCover(board, 27)

        assertEquals(expected, actual, "Queen covers a lot of square, regardless of occupant")
    }
}