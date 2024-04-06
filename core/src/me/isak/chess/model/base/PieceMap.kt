package me.isak.chess.model.base

/**
 * Implement a mapping between characters and piece for each version of chess.
 * This allows for the use of non-standard pieces.
 */
interface PieceMap {

    fun getPiece(piece: Char): Piece

}