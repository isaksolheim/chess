package me.isak.chess.move

/**
 * Move is a description of where a piece can go.
 * It is calculated using the starting position and an action of the piece.
 * @property square is where the piece will end up
 * @property result is the string representation of the board, should the move be played.
 * @property id is passed on from the piece action that it was created from. This is useful for certain
 * filter operations.
 */
data class Move(val square: Int, var result: String, val id: String)