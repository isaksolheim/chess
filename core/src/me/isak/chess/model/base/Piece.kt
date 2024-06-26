package me.isak.chess.model.base

/**
 * Holds all information related to the behaviour of a piece 
 * in the game of chess. This is done as a list of actions.
 */
class Piece(private val actions: List<Action>) : Iterable<Action> {
    override fun iterator(): Iterator<Action> {
        return actions.iterator()
    }
}