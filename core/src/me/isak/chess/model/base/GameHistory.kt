package me.isak.chess.model.base

/**
 * Interface defining what each version of chess requires to handle game history.
 * We separate between state and history for a game of chess.
 * State is what the board currently looks like. It has no memory of 
 * previous moves or occurances. This is the job of GameHistory.
 * 
 * An example from standard chess is the right to castle. After a king
 * has moved, he loses the right to castle forever. En passant can only 
 * be done if the pawn moved forward in the previous move. 
 */
abstract class GameHistory(fen: String) {

    var castle: String
    var enPassant: Int
    var halfMove: Int
    var fullMove: Int

    // used to check if a capture occured
    var numberOfPieces: Int

    // Extract game history relevant information from the fen string
    init {
        val gameInfo = fen.split(" ")
        castle = gameInfo[2]
        enPassant = processEnPassant(gameInfo[3])
        halfMove = gameInfo[4].toInt()
        fullMove = gameInfo[5].toInt()

        numberOfPieces = gameInfo[0].count { it.isLetter() }
    }

    /**
     * A method for filtering down generated moves
     * to only allow those that do not break rules based on 
     * history.
     * @param move to check for legality.
     * @return True if no historic rule is broken.
     */
    abstract fun checkHistory(move: Move): Boolean

    /**
     * Change the history values after a move
     * has been executed.
     * @param move that has been executed.
     */
    abstract fun changeHistory(move: Move): Unit

    /**
     * The half move counter is incremented every move unless:
     * 1. a pawn moves.
     * 2. a piece is captured.
     */
    fun incrementHalfMove(move: Move) {
        halfMove++
        
        val newNumberOfPieces = move.result.count { it != ' ' }
        if (numberOfPieces != newNumberOfPieces) {
            halfMove = 0
            numberOfPieces = newNumberOfPieces 
        }
        
        val movedPiece = move.result[move.square] 
        
        if (movedPiece == 'p' || movedPiece == 'P') {
            halfMove = 0
        }
    }
    
    /**
     * Fullmove number: The number of the full moves. 
     * It starts at 1 and is incremented after Black's move.
     * 
     */
    fun incrementFullMove(whitesTurn: Boolean) {
        if (whitesTurn) {
            fullMove++
        }
    }

    
    override fun toString(): String {
        var enPassantString = indexToStandardNotation(enPassant)
        var castleString = if (castle.length == 0) "-" else castle
        return "$castleString $enPassantString $halfMove $fullMove"
    }

    /**
     * Turn chess coordinate into square index
     * 
     * a8 -> 0,
     * h1 -> 63 
     */
    private fun processEnPassant(enPassant: String): Int {
        if (!enPassant.matches(Regex("[a-h][1-8]"))) return -1
        val x = enPassant[0].lowercase().toInt(16) % 10
        val y = 8 - enPassant[1].digitToInt()

        return y * 8 + x
    }

    /**
     * Inverse of the function above. Turn square index into chess coordinate
     */
    private fun indexToStandardNotation(enPassant: Int): String {
        if (enPassant == -1) return "-"
        val y = enPassant / 8
        val x = enPassant % 8
        
        return arrayOf("a", "b", "c", "d", "e", "f", "g", "h")[x] + (8 - y)
    } 
}