package me.isak.chess.model.versions.atomic

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameState


class AtomicGameState(simpleMoveCalculator: SimpleMoveCalculator, fen: String)
    : GameState(simpleMoveCalculator, fen) {

    private var pieceCount: Int
    private val neighbourSquares = arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N") // used to check squares around a square

    init {
        pieceCount = board.count { it.isLetter() }
    }

    /**
     * Defines the way atomic chess state changes when a move 
     * has been executed. Toggles player turn, updates board
     * and checks for pawn promotion.
     * Atomic: explode if a piece capture occurs.
     */
    override fun changeState(move: Move) {
        turn = !turn

        val boardArray = move.result.toCharArray().toTypedArray()
    
        promoteIfPossible(boardArray, move)

        val newPieceCount = move.result.count{ it.isLetter() }

        if (newPieceCount != pieceCount) {
            handleExplosion(boardArray, move)
            pieceCount = newPieceCount
        }
        // must update the piece count after explosion
        board = boardArray.joinToString("")
        pieceCount = board.count { it.isLetter() }
    }

    private fun handleExplosion(board: Array<Char>, move: Move) {
        val captureSquare = move.square

        neighbourSquares // all squares around the piece that captured
          .filter{ offset -> simpleMoveCalculator.isInBounds(captureSquare, offset) } // remove squares off the board
          .map{ offset -> captureSquare + simpleMoveCalculator.parseDirection(offset) } // map to the relevant squares
          .filter{ !"Pp ".contains(board[it]) } // filter away empty squares and pawns (they do not explode)
          .forEach{ board[it] = ' '} // clear the squares containing pieces

        board[captureSquare] = ' ' // always clear the square when capturing
    }

    /**
     * Checks if a move would break a rule based on 
     * the current state of the game. It is not allowed
     * to make a move that would leave your king in check, 
     * or castle should the squares between the rook and king 
     * be covered by the enemy.
     * Atomic: not allowed to blow your own king up.
     */
    override fun checkState(move: Move): Boolean {

        val board = move.result.toCharArray().toTypedArray()

        if (blowsUpOwnKing(board, move)) return false
        if (simpleMoveCalculator.isKingInCheck(board, turn)) return false
    
        if (illegalCastle(move)) return false

    
        return true
    }

    /**
     * This is true when the move reduces the number of pieces (capture),
     * and the king is next to the landing square.
     */
    private fun blowsUpOwnKing(board: Array<Char>, move: Move): Boolean {
        val newPieceCount = move.result.count { it.isLetter() }

        // false if no capture occured
        if (newPieceCount == pieceCount) return false

        // look for the current playing king
        val kingToLookFor = if (turn) 'K' else 'k'
        val captureSquare = move.square

        return neighbourSquares
          .filter{ direction -> simpleMoveCalculator.isInBounds(captureSquare, direction)}  // remove squares off the board
          .map{ direction -> captureSquare + simpleMoveCalculator.parseDirection(direction)} // map to index on the board
          .any{ board[it] == kingToLookFor} // check if any of the indexes include the king
    }
}