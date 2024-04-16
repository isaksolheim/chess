package me.isak.chess.unit

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.assertEquals;
import me.isak.chess.model.base.Game
import me.isak.chess.model.FirebaseGameModel

class GameTest {

    private val game = Game("standard")

    @Test
    fun InitializeGameTest() {
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", game.fen())
        assertEquals("standard", game.getGameVersion())
        assertEquals("rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR", game.getBoardAsString())
        assertEquals(false, game.isOnline)
        assertEquals("white", game.getCurrentTurn())
        assertEquals("white", game.getPieceColorAtSquare(56))
        assertEquals("black", game.getPieceColorAtSquare(0))
        assertEquals(null, game.getPieceColorAtSquare(-1))
        assertEquals(null, game.getPieceColorAtSquare(32))
    }

    @Test
    fun json() {
        val jsonRepresentation = game.toJSON()
        assertEquals("white", jsonRepresentation.currentTurn)
        assertEquals("rnbqkbnrpppppppp                                PPPPPPPPRNBQKBNR", jsonRepresentation.board)
    }

    @Test
    fun updateModel() {
        val model = game.toJSON()
        val turn = "black"
        val newBoard = "rnbqkbnrpppppppp                               PPPPPPPP RNBQKBNR"
        model.currentTurn = turn
        model.board = newBoard

        game.updateFromModel(model)

        assertEquals(turn, game.getCurrentTurn())
        assertEquals(newBoard, game.getBoardAsString())

        val newModel = FirebaseGameModel("1", "koth", "rnbqkbnr pppppppp                              PPPPPPPP RNBQKBNR", "white")

        game.updateFromModel(newModel)

        assertEquals("white", game.getCurrentTurn())
    }
}