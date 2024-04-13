package me.isak.chess.integration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move

/**
 * Collection of the expected results. 
 * @property click is the square clicked by the user.
 * @property fen is the expected fen after the click.
 * @property legalMoves is the list of moves that are available after the click.
 */
data class TestState(val click: Int, val fen: String, val legalMoves: List<Int>)

class RacingKingsTest {

    private val empty: List<Int> = listOf()

    @Test
    fun playingRacingKings() {
        val game = Game("racing")

        val expectedStates: List<TestState> = listOf(
            TestState(29, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", empty),
            TestState(40, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", empty),
            TestState(54, "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1", listOf(46, 38, 30, 22, 14, 6)),
            TestState(6, "6R1/8/8/8/8/8/krbnNB1K/qrbnNBRQ b - - 1 1", empty),
        )
        
        for (i in 0..expectedStates.size - 1) {
            val (click, expectedFen, expectedLegalMoves) = expectedStates[i]
            game.click(click)

            val actualFen = game.fen()
            val actualLegalMoves = game.getLegalMoves().map{ it.square }

            assertEquals(expectedFen, actualFen, "Incorrect fen on step $i")
            assertEquals(expectedLegalMoves, actualLegalMoves, "Incorrect legal moves on step $i")
        }
    }
}