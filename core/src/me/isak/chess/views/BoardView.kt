package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import me.isak.chess.Renderer
import me.isak.chess.viewmodels.GameViewModel

class BoardView(private val viewModel: GameViewModel) {
    private val lightPixelDrawer by lazy { Renderer.lightPixelDrawer }
    private val darkPixelDrawer by lazy { Renderer.darkPixelDrawer }
    private val spriteBatch by lazy { Renderer.spriteBatch }

    private val pieceImages by lazy {
        hashMapOf<Char, Texture>().apply {
            put('P', Texture(Gdx.files.internal("pieces/wP.png"))) // White pawn
            put('p', Texture(Gdx.files.internal("pieces/bP.png"))) // Black pawn
            put('R', Texture(Gdx.files.internal("pieces/wR.png"))) // White rook
            put('r', Texture(Gdx.files.internal("pieces/bR.png"))) // Black rook
            put('N', Texture(Gdx.files.internal("pieces/wN.png"))) // White knight
            put('n', Texture(Gdx.files.internal("pieces/bN.png"))) // Black knight
            put('B', Texture(Gdx.files.internal("pieces/wB.png"))) // White bishop
            put('b', Texture(Gdx.files.internal("pieces/bB.png"))) // Black bishop
            put('Q', Texture(Gdx.files.internal("pieces/wQ.png"))) // White queen
            put('q', Texture(Gdx.files.internal("pieces/bQ.png"))) // Black queen
            put('K', Texture(Gdx.files.internal("pieces/wK.png"))) // White king
            put('k', Texture(Gdx.files.internal("pieces/bK.png"))) // Black king
        }
    }

    init {
        viewModel.onBoardChanged = { board ->
            renderPieces(board)
        }
    }

    private fun renderPieces(board: Array<Char>) {
        val squareSize = Gdx.graphics.width / 8f
        var yPos = Gdx.graphics.height - (Gdx.graphics.height / 5f) - squareSize

        for ((index, pieceChar) in board.withIndex()) {
            val colNum = index % 8
            val rowNum = index / 8

            val xPos = colNum * squareSize
            val adjustedYPos = yPos - rowNum * squareSize

            val pieceTexture = pieceImages[pieceChar]
            pieceTexture?.let {
                spriteBatch.draw(it, xPos, adjustedYPos, squareSize, squareSize)
            }
        }
    }


    fun render() {
        val squareSize = Gdx.graphics.width / 8f

        var colNum = 0
        var yPos = Gdx.graphics.height - (Gdx.graphics.height / 5f)

        for ((i, _) in viewModel.getBoard().withIndex()) {
            if (i % 8 == 0) {
                yPos -= (squareSize)
                colNum = 0
            }

            var xPos = (squareSize * colNum)
            if ((i / 8) % 2 == 0) {
                if (colNum % 2 == 0) {
                    darkPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                } else {
                    lightPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                }
            } else {
                if (colNum % 2 == 0) {
                    lightPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                } else {
                    darkPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                }
            }

            colNum += 1
        }

        renderPieces(viewModel.getBoard())
    }
}