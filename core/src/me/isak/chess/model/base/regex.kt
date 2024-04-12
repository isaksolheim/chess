package me.isak.chess.model.base

/**
 * Common patterns to verify the path taken by a piece.
 * A path is a string starting with the piece itself, followed 
 * by the squares it visits.
 * Examples:
 * 1. "R   r" - a white rook crossing 3 empty squares and capturing a black rook.
 * 2. "R   R" - a white rook crossing 3 emtpy squares and capturing another white rook.
 * 3. "Rr   " - a white rook capturing a black rook and then going over 3 empty squares.
 * 
 * These paths are used to terminate illegal moves. Path 1. above is legal,
 * while 2. and 3. would not be (in regular chess). 
 */
val PathPatterns = mapOf(

    /**
     * A leaper is a chess piece that moves directly to a square a fixed distance away.
     * Cannot be blocked. 
     */
    "leaper" to Regex("^([A-Z][a-z ]|[a-z][A-Z ])$"),
    
    /**
     * A ranger is a piece that moves an unlimited distance in one direction,
     * provided that there are no obstacles. If the obstacle is a friendly piece,
     * it blocks further movement; if the obstacle is an enemy piece, it may be captured.
     */
    "ranger" to Regex("^([A-Z]\\W*[a-z]?|[a-z]\\W*[A-Z]?)$"),
    
    /**
     * Block move if square is occupied.
     */
    "unoccupied" to Regex("^.\\W$"),
    
    /**
     * Only allow a move that captures an enemy
     */
    "enemy" to Regex("^([A-Z][a-z]|[a-z][A-Z])$"),
    ) 

/**
 * Common patterns to verify the cover by a piece.
 * Cover is similar to paths, but differ slightly. Pieces can cover friendly pieces, but not 
 * move there.
 * Pawns covers the squares diagonally infront, but they may not be allowed to move there.
 * The distinction is important, because some moves require a check of what the enemy covers.
 * An example of this is castle.
 */
val CoverPatterns = mapOf(

    /** 
     * Leapers cover squares regardless of what is there
     */
    "leaper" to Regex("^\\w.$"),
    
    /**
     * Rangers cover a piece after any number of empty squares
     */
    "ranger" to Regex("^\\w\\s*\\w?$"),
    
    /**
     * Some moves do not count as cover. Examples are pawn forward moves
     * and other special moves.
     */
    "never" to Regex("\\$"),
)