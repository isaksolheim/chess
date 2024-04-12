package me.isak.chess.model.base

/**
 * Use the version specific mapping between characters and pieces to 
 * determine piece behaviour.
 */
class PieceMap(private val pieceMap: Map<Char, Piece>) {
    fun getPiece(piece: Char): Piece { 
        return pieceMap.get(piece) ?: throw Error("Piece does not exist for this piece :$piece")
    }   
}