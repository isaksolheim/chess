package me.isak.chess.move
import me.isak.chess.utils.isInBounds
import me.isak.chess.utils.parseDirection

val piece = "[a-zA-Z]".toRegex()
val black = "[a-z]".toRegex()

class MoveCalculator(private val movesetMap: MovesetMap) {
    
    fun calculate(board: Array<Char>, startSquare: Int): List<Move> {
        val moves: MutableList<Move> = mutableListOf()

        val piece = board[startSquare]
        val movesetArray = movesetMap.getMoveset(piece)

        for (moveset in movesetArray) {
            for (direction in moveset) {
                var square = startSquare

                while (isInBounds(square, direction)) {
                    square += parseDirection(direction)
                    val moveDescription: String = board[startSquare].toString() + board[square].toString()


                    if (moveset.shouldStopBefore(moveDescription)) break
                    if (!moveset.boardCheck(board, startSquare)) break

                    val result = moveset.makeReplacement(board, startSquare, square)
                    moves.add(Move(square, result, moveset.getTag()))

                    if (moveset.shouldStopAfter(moveDescription)) break
                }
            }
        }
        return moves
    }

    public fun calculateEnemyCover(board: Array<Char>, isWhitesTurn: Boolean): List<Move> {
        return board.indices // Get a range of indices of the board
            .filter { square -> piece.matches(board[square].toString()) } // Filter empty squares
            .filter { piece -> black.matches(board[piece].toString()) == isWhitesTurn } // Filter by piece color
            .flatMap { enemy -> calculate(board, enemy) } // Calculate moves for each enemy
    }
}