package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertThrows;
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.PieceBuilder


class PieceBuilderTest {

    private val pb = PieceBuilder()

    @Test
    fun illegalPieceBuilderArguments() {

        pb.path(Regex("R  r"))

        assertThrows(
            IllegalArgumentException::class.java,
            { pb.pathType("not found") },
            "Should not be possible to use non existing path type"
        )
        assertThrows(
            IllegalArgumentException::class.java,
            { pb.coverType("not found") },
            "Should not be possible to use non existing cover type"
        )
    }
}