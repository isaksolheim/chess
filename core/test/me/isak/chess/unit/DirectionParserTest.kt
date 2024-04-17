package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.PieceMap
import me.isak.chess.model.base.Game

class DirectionParserTest {


    private var mvc = SimpleMoveCalculator(PieceMap(standardPieceMap), "standard")


    @Test 
    fun parseValidDirections() {
        assertEquals(1, mvc.parseDirection("E"))
        assertEquals(8, mvc.parseDirection("S"))
        assertEquals(-1, mvc.parseDirection("W"))
        assertEquals(-8, mvc.parseDirection("N"))
        assertEquals(0, mvc.parseDirection("ESWN"))
        
        assertEquals(17, mvc.parseDirection("SSE"))
    }
    
    @Test
    fun parseInvalidDirections() {

        assertEquals(0, mvc.parseDirection("2345q34tq345"))
        assertEquals(1, mvc.parseDirection("2345q --  E -- 34tq345"))
    }

}