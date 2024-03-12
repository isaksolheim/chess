import kotlin.collections.forEach
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.toList

class MoveCalculator(private val movesetMap: Map<Char, List<Moveset>>) {
    
    fun calculateMoves(board: Array<Char>, startSquare: Int): List<Move> {
        val moves = mutableListOf<Move>()

        val piece = board[startSquare]
        val movesets = movesetMap[piece] ?: throw IllegalArgumentException("Moveset could not be found for this piece")

        movesets.forEach { moveset ->
            moveset.directions.forEach { direction ->
                var square = startSquare

                while (isInBounds(square, direction)) {
                    square += parseDirection(direction)
                    val moveDescription = "${board[startSquare]}${board[square]}"

                    if (moveset.shouldStopBefore(moveDescription)) break
                    if (!moveset.boardCheck(board, startSquare)) break

                    val result = moveset.makeReplacement(board, startSquare, square)
                    moves.add(Move(square, result, moveset.tag))

                    if (moveset.shouldStopAfter(moveDescription)) break
                }
            }
        }
        return moves
    }
}
