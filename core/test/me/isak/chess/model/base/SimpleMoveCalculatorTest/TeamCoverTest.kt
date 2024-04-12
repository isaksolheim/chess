package me.isak.chess.model.base.SimpleMoveCalculatorTest

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.PieceMap
import me.isak.chess.model.base.Game

class TeamCoverTest {

    private var mvc = SimpleMoveCalculator(PieceMap(standardPieceMap))
    private var board: Array<Char> = arrayOf()
    private var expected: List<Int> = listOf()
    private var actual: List<Int> = listOf()

    @Test 
    fun startCoverWhite() {
        board = Game("standard").getBoard()
        val teamWhite = true

        expected = listOf(40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62)
        actual = mvc.calculateTeamCover(board, teamWhite).distinct().sorted()
        assertEquals(expected, actual, "Black team should cover most squares from 0->23 (not 7)")
    }
    @Test 
    fun startCoverBlack() {
        board = Game("standard").getBoard()
        val teamWhite = false

        expected = listOf(1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)
        actual = mvc.calculateTeamCover(board, teamWhite).distinct().sorted()
        assertEquals(expected, actual, "Black team should cover most squares from 40->53 (not 56, 63)")
    }
}