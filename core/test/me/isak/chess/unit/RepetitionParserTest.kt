package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.PieceMap
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.PieceBuilder

class RepetitionParserTest {


    
    
    /**
     * Normal moves are tested well in the integration tests, but since none of them uses composite moves, 
     * they are tested here instead.
     */
    @Test 
    fun specialMoveTest() {

        // step 1. create custom piece
        val pb = PieceBuilder() 
        val specialPiece = pb.directions("EEE1SSS1").type("ranger").buildAction().buildPiece()

        val customPieceMap = standardPieceMap.toMutableMap()
        customPieceMap.put('s', specialPiece)

        // step 2. create the calculator with special piece included, and the custom board
        val mvc = SimpleMoveCalculator(PieceMap(customPieceMap), "custom")

        val board = "s                                                               ".toCharArray().toTypedArray()

        // step 3. find the cover of the special piece
        val cover = mvc.calculatePieceCover(board, 0)

        assertEquals(listOf(3, 27), cover)
    }
    @Test 
    fun specialMoveTestWithIllegalCharacters() {

        // step 1. create custom piece
        val pb = PieceBuilder() 
        val specialPiece = pb.directions("EEESSS!").buildAction().buildPiece()

        val customPieceMap = standardPieceMap.toMutableMap()
        customPieceMap.put('s', specialPiece)

        // step 2. create the calculator with special piece included, and the custom board
        val mvc = SimpleMoveCalculator(PieceMap(customPieceMap), "custom")

        val board = "s                                                               ".toCharArray().toTypedArray()

        // step 3. find the cover of the special piece
        val cover = mvc.calculatePieceCover(board, 0)

        assertEquals(listOf(27), cover)
    }
}