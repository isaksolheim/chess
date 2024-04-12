package me.isak.chess.model.base.SimpleMoveCalculatorTest

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.PieceMap
import me.isak.chess.model.base.Game

class GeneralInCheckTest {

    private var mvc = SimpleMoveCalculator(PieceMap(standardPieceMap))
    private var board: Array<Char> = arrayOf()
    private var white = true
    private var black = false

    @Test 
    fun notCheck0() {
        board = Game("standard").getBoard()

        val isWhiteInCheck = mvc.isKingInCheck(board, white)
        val isBlackInCheck = mvc.isKingInCheck(board, black)

        assertEquals(false, isWhiteInCheck, "white is not in check")
        assertEquals(false, isBlackInCheck, "black is not in check")
    }

    @Test 
    fun notCheck1() {
        board = Game("standard", fen = "2rk4/4q3/4R1PP/5pp1/4P3/1K1pn1B1/7n/5Br1 w - - 0 1").getBoard()

        val isWhiteInCheck = mvc.isKingInCheck(board, white)
        val isBlackInCheck = mvc.isKingInCheck(board, black)

        assertEquals(false, isWhiteInCheck, "white is not in check")
        assertEquals(false, isBlackInCheck, "black is not in check")
    }

    @Test 
    fun notCheck2() {
        board = Game("standard", fen = "8/1Nk1pn2/Pp6/P1b5/p1K1Pn2/4q3/2P1P3/7r w - - 0 1").getBoard()

        val isWhiteInCheck = mvc.isKingInCheck(board, white)
        val isBlackInCheck = mvc.isKingInCheck(board, black)

        assertEquals(false, isWhiteInCheck, "white is not in check")
        assertEquals(false, isBlackInCheck, "black is not in check")
    }

    @Test 
    fun notCheck3() {
        board = Game("standard", fen = "8/2B5/1P5p/pB3P2/Q6P/5N1p/P4p2/4Rb2 w - - 0 1").getBoard()

        val isWhiteInCheck = mvc.isKingInCheck(board, white)
        val isBlackInCheck = mvc.isKingInCheck(board, black)

        assertEquals(false, isWhiteInCheck, "No white king on the board -> not in check")
        assertEquals(false, isBlackInCheck, "No black king on the board -> not in check")
    }

    @Test
    fun inCheck0() {
        board = Game("standard", fen = "8/k6R/8/8/8/8/K6r/8 w - - 0 1").getBoard()

        val isWhiteInCheck = mvc.isKingInCheck(board, white)
        val isBlackInCheck = mvc.isKingInCheck(board, black)

        assertEquals(true, isWhiteInCheck, "White king is in check")
        assertEquals(true, isBlackInCheck, "Black king is in check")
    }
}