package me.isak.chess.model.base


class MoveCalculator(private val simpleMoveCalculator: SimpleMoveCalculator, private val gameState: GameState, private val gameHistory: GameHistory) {

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

    fun isKingInCheck(board: Array<Char>, whiteKing: Boolean): Boolean {
        return simpleMoveCalculator.isKingInCheck(board, whiteKing)
    }
}