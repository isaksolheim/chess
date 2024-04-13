package me.isak.chess.integration

import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game


/**
 * Data class only used to make integration tests easier.
 * Collect a list of expected results (TestStates), and compare with actual.
 * @property click is the square clicked by the user.
 * @property fen is the expected fen after the click.
 * @property legalMoves is the list of moves that are available after the click.
 */
data class TestState(val click: Int, val fen: String, val legalMoves: List<Int>)

class IntegrationTestRunner() {

    fun run(expectedStates: List<TestState>, game: Game) {
        for (i in 0..expectedStates.size - 1) {
            val (click, expectedFen, expectedLegalMoves) = expectedStates[i]
            
            game.click(click)

            val actualFen = game.fen()
            val actualLegalMoves = game.getLegalMoves().map{ it.square } // we only care about the squares it can move to in testing. 

            assertEquals(expectedFen, actualFen, "Incorrect fen on step $i")
            assertEquals(expectedLegalMoves, actualLegalMoves, "Incorrect legal moves on step $i")
        }
    }

}