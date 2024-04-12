package me.isak.chess.model.base


class MoveCalculator(private val simpleMoveCalculator: SimpleMoveCalculator, private val gameState: GameState, private val gameHistory: GameHistory) {

    /**
     * Generate all moves that are legal for a piece.
     * @param square where the piece is standing.
     * @return List<Move> which are all the legal moves.
     */
    fun legalMoves(square: Int): List<Move> {
        val board = gameState.getBoard()

        val piece = board[square]

        if (piece == ' ') return listOf()

        if (!gameState.isPieceTurn(piece)) return listOf()

        return simpleMoveCalculator
            .calculateSimpleMoves(board, square)
            .filter{ gameState.checkState(it) }
            .filter{ gameHistory.checkHistory(it) }
    }

  /**
   * Find out if the king is in check.
   * @param board to analyse
   * @param whiteKing true to check for the white king, false for black king.
   * @return true if the selected king is in check
   */
    fun isKingInCheck(board: Array<Char>, whiteKing: Boolean): Boolean {
        return simpleMoveCalculator.isKingInCheck(board, whiteKing)
    }
}