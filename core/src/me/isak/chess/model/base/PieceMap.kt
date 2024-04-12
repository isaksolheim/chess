package me.isak.chess.model.base

/**
 * Use the version specific mapping between characters and pieces to 
 * determine piece behaviour.
 */
class PieceMap(private val pieceMap: Map<Char, Piece>) {
    fun getPiece(piece: Char): Piece {
        if (piece == ' ') throw IllegalArgumentException("Cannot use empty char in piece map") 
        return pieceMap.get(piece) ?: throw Error("Piece map could not find mapping from this symbol: $piece")
    }   
}