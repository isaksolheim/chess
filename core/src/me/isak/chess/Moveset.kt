
import kotlin.text.Regex

class Moveset(
    val directions: Array<String>,
    val stopBefore: Regex,
    val stopAfter: Regex,
    val tag: String,
    val boardCondition: Regex? = null,
    val replacement: String? = null
) {
    fun shouldStopBefore(moveDescription: String): Boolean {
        return stopBefore.matches(moveDescription)
    }

    fun shouldStopAfter(moveDescription: String): Boolean {
        return stopAfter.matches(moveDescription)
    }

    /* Return true if board is ok. This has potential for expansion, because it is not using current square effectively */
    fun boardCheck(board: Array<Char>, startSquare: Int): Boolean {
        if (boardCondition == null) return true

        val boardCopy = board.copyOf()
        boardCopy[startSquare]= 'I' // To match with regex
        val boardstring = boardCopy.joinToString("")

        return boardCondition.matches(boardstring)
    }

    fun makeReplacement(board: Array<Char>, startSquare: Int, square: Int): String {
        val boardCopy = board.copyOf()

        if (replacement == null || boardCondition == null) {
            // without specialized replacement, just place the piece on the new square
            boardCopy[square] = board[startSquare]
            boardCopy[startSquare] = ' '
            return boardCopy.joinToString("")
        }

        // Specialized replacement need to use the character I instead of the piece, otherwise matching will not work.
        boardCopy[startSquare] = 'I'

        // return the specialized replacement (like castle or en passant)
        return boardCopy.joinToString("").replace(boardCondition, replacement)
    }
}
