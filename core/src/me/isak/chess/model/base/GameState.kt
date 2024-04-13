package me.isak.chess.model.base

val tagToCastleSquares = mapOf(
    "q" to listOf(2, 3, 4),
    "k" to listOf(4, 5, 6),
    "Q" to listOf(58, 59, 60),
    "K" to listOf(60, 61, 62)
)

/**
 * Each version of chess might have different qualities that it needs to keep track of.
 * However, most versions closely follow the standard chess state.
 * 
 * GameState keeps track only of immediate values. Who's turn it is, what the board looks
 * like, etc. For long term values (like castling rights) are tracked by the GameHistory class.
 */
abstract class GameState(val simpleMoveCalculator: SimpleMoveCalculator, public var board: String, public var turn: Boolean) {

    /**
     * A method for filtering down legal moves to
     * only allow those not breaking state specific rules.
     * @param move to check for legality.
     * @return True if no historic rule is broken.
     */
    abstract fun checkState(move: Move): Boolean
    
    /**
     * Change state after a move has been executed.
     * @param move that has been executed.
     */    
    abstract fun changeState(move: Move)

    fun setBoardAsString(newBoard: String) {
        board = newBoard
    }

    fun getBoard(): Array<Char> {
        return board.toCharArray().toTypedArray()
    }

    fun getBoardAsString(): String {
        return board
    }

    fun isPieceTurn(piece: Char): Boolean {
        return piece.isUpperCase() == turn
    } 


    /**
     * Check if a move is attempting to castle illegally.
     * @return true in that case.
     */
    protected fun illegalCastle(move: Move): Boolean {
        val id = move.id

        val squaresToClear = tagToCastleSquares[id] ?: return false

        val board = move.result.toCharArray().toTypedArray()
        
        return simpleMoveCalculator
        .calculateTeamCover(board, !turn)
        .any{ squaresToClear.contains(it) }
    }

    protected fun promoteIfPossible(board: Array<Char>, move: Move) {
        // Promote pawn should it reach the final rank
        val square = move.square
        val id = move.id
        if (id == "P" && square / 8 == 0) {
            board[square] = 'Q'
        }
    
        if (id == "p" && square / 8 == 7) {
            board[square] = 'q'
        }
    }
}