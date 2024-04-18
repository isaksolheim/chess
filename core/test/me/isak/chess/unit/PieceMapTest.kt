package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertThrows;
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.PieceMap


class PieceMapTest {

    private val pieceMap = PieceMap(standardPieceMap)

    @Test
    fun getEmptySquare() {
        assertThrows(
            IllegalArgumentException::class.java,
            { pieceMap.getPiece(' ') },
            "Should not be possible to get an emtpy square"
        )
    }

    @Test
    fun getNonExistingPiece() {
        assertThrows(
            Error::class.java,
            { pieceMap.getPiece('O') },
            "Should throw error when a non existing piece is requested."
        )
    }
}