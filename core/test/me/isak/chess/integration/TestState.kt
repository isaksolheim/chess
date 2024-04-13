package me.isak.chess.integration

/**
 * Data class only used to make integration tests easier.
 * Collect a list of expected results (TestStates), and compare with actual.
 * @property click is the square clicked by the user.
 * @property fen is the expected fen after the click.
 * @property legalMoves is the list of moves that are available after the click.
 */
data class TestState(val click: Int, val fen: String, val legalMoves: List<Int>)