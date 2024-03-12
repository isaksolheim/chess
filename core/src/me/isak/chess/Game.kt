

import kotlin.arrayOf

class Game(version: String) {

    // Use version as input into GameFactory, and get the stuff we need.
    // TODO: make the factory

    private val moveCalculator = MoveCalculator(movesetMap) // TODO:  moveset map should depend on version

    private var legalMoves: Array<Move> = arrayOf()
    
    // This is temporary. When the rest of the core exist, we get the board from GameState, one of the objects created by GameFactory
    private var board: Array<Char> = "rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR".toCharArray().toTypedArray()
    
    fun handleClick(square: Int) {

        val moveToExecute = legalMoves.find{ move -> move.square == square }

        if (moveToExecute != null) {
            board = moveToExecute.result.toCharArray().toTypedArray()
            // TODO : executor here
            // TODO : changeHistory here
            return
        }

        val piece = board[square]

        if (piece == ' ') {
            legalMoves = arrayOf()
            return
        }

        if (!isPieceTurn(piece)) {
            legalMoves = arrayOf()
            return
        }  
        legalMoves = moveCalculator.calculateMoves(board, square)
                    .filter { move -> checkGame(move) }
                    .filter { move -> checkHistory(move) }
                    .toTypedArray()

        // At this point the UI should receive some updates. 
        // We should figure out how we want that to work.

    }

    // TODO : implement
    fun checkGame(move: Move): Boolean {
        return true
    }

    // TODO : implement
    fun checkHistory(move: Move): Boolean {
        return true
    }


    // TODO : implement proper turn check
    fun isPieceTurn(piece: Char): Boolean {
        return true
    }
    
}