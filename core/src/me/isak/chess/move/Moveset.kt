package me.isak.chess.move

import me.isak.chess.move.always
import me.isak.chess.move.friends


class Moveset(
        private val directions: Array<String>, 
        private val stopBefore: Regex = friends,
        private val stopAfter: Regex = always,
        private val tag: String,
        private val boardCondition: Regex?,
        private val replacement: String?
    ) : Iterable<String> {

    fun shouldStopBefore(moveDescription: String): Boolean {
        return stopBefore.containsMatchIn(moveDescription)
    }

    fun shouldStopAfter(moveDescription: String): Boolean {
        return stopAfter.containsMatchIn(moveDescription)
    }

    // return false if the board is not acceptable
    fun boardCheck(board: Array<Char>, startSquare: Int): Boolean {
        if (boardCondition == null) return true

        var boardCopy = board.copyOf()
        boardCopy[startSquare] = 'I'
        val boardString = boardCopy.joinToString("") 

        return boardCondition.containsMatchIn(boardString)
    }

    fun getTag(): String {
        return tag
    }

    fun makeReplacement(board: Array<Char>, startSquare: Int, square: Int): String {
        val boardCopy = board.copyOf()

        if (replacement == null || boardCondition == null) {
            boardCopy[square] = board[startSquare]
            boardCopy[startSquare] = ' '
            return boardCopy.joinToString("")
        }

        boardCopy[startSquare] = 'I'

        return boardCopy.joinToString("").replace(boardCondition, replacement) 
    }

    override fun iterator(): Iterator<String> { return directions.iterator() }

}