package me.isak.chess.move

import me.isak.chess.move.Action
import me.isak.chess.move.MutableAction
import kotlin.collections.mutableListOf
import CoverPatterns
import PathPatterns

/**
 * This is a mutable version of the Action data class only used
 * during design time.
 * Default values are given based on what is common. Most actions follow the
 * rules of a leaper (going directly to a square, but no further).
 */
data class MutableAction (
    var id: String = "",
    var directions: List<String> = listOf(),
    var path: Regex = PathPatterns.get("leaper") ?: throw IllegalStateException("Regex is null"),
    var cover: Regex = CoverPatterns.get("leaper") ?: throw IllegalStateException("Regex is null"),
    var boardCondition: Regex? = null,
    var replacement: String? = null,
)

/**
 * Designing the behaviour of a piece is a complex process. This class
 * uses the builder design pattern to make it easier.
 * Building a piece is done by first building all the actions for the 
 * piece, and then building the piece itself.
 */
class PieceBuilder {
    // Completed actions
    private var actions: MutableList<Action> = mutableListOf()

    // Next action under construction
    private var action = MutableAction()

    /**
     * Creates an Action by copying the properties of the mutable
     * action into an immutable version. Reset the mutable action,
     * to allow the creation of the next action.
     * @return this to allow chaining operations.
     */
    fun buildAction(): PieceBuilder {
        actions.add(Action(
            action.id,
            action.directions,
            action.path,
            action.cover,
            action.boardCondition,
            action.replacement,
        ))
        action = MutableAction()
        return this
    }

    /**
     * Build a piece. Reset properties to allow 
     * building of the next piece.
     */
    fun buildPiece(): Piece {
        val piece = Piece(actions)
        actions = mutableListOf()
        action = MutableAction()
        return piece
    }

    /**
     * This function, and all remaining functions are builder pattern
     * for an action.
     */
    fun id(id: String): PieceBuilder {
        action.id = id
        return this
    }

    fun directions(vararg directions: String): PieceBuilder {
        action.directions = directions.toList()
        return this
    }

    fun type(type: String): PieceBuilder {
        pathType(type)
        coverType(type)
        return this
    }

    fun path(path: Regex): PieceBuilder {
        action.path = path
        return this
    }

    fun pathType(type: String): PieceBuilder {
        action.path = PathPatterns.get(type) ?: throw IllegalArgumentException("$type does not exist in PathPatterns")
        return this
    }

    fun cover(cover: Regex): PieceBuilder {
        action.cover = cover
        return this
    }

    fun coverType(type: String): PieceBuilder {
        action.cover = CoverPatterns.get(type) ?: throw IllegalArgumentException("$type does not exist in CoverPatterns")
        return this
    }

    fun boardCondition(boardCondition: Regex): PieceBuilder {
        action.boardCondition = boardCondition
        return this
    }

    fun replacement(replacement: String): PieceBuilder {
        action.replacement = replacement
        return this
    }
}