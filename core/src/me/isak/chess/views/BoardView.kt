package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import me.isak.chess.Renderer

class BoardView {
    private val lightPixelDrawer by lazy { Renderer.lightPixelDrawer }
    private val darkPixelDrawer by lazy { Renderer.darkPixelDrawer }

    val squares = arrayOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

    fun render() {
        val squareSize = Gdx.graphics.width / 8f

        var colNum = 0
        var yPos = Gdx.graphics.height - (Gdx.graphics.height/5f)

        for (i in squares.indices) {
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
    }
}