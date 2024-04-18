package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Rectangle
import me.isak.chess.Renderer
import me.isak.chess.model.base.Move
import me.isak.chess.viewmodels.GameViewModel
import me.isak.chess.viewmodels.TextureProvider

class BoardView( private val viewModel: GameViewModel, private val textureProvider: TextureProvider) : InputAdapter() {
    private val lightPixelDrawer by lazy { Renderer.lightPixelDrawer }
    private val darkPixelDrawer by lazy { Renderer.darkPixelDrawer }
    private val dotPixelDrawer by lazy { Renderer.dotPixelDrawer }
    private val spriteBatch by lazy { Renderer.spriteBatch }

    private lateinit var turnWhiteTexture: Texture
    private lateinit var turnBlackTexture: Texture

    private val font = BitmapFont().apply {
        color = Color.BLACK
        data.setScale(3f)
    }

    init {
        textureProvider.onThemeChange = { theme ->
            updateTextures(theme)
        }

        // Set an initial theme based on the game version
        val initialTheme = when (viewModel.getGameVersion()) {
            "makruk" -> "makruk"
            else -> "standard"
        }
        textureProvider.setPieceTheme(initialTheme)
        setupListeners()
    }

    private fun updateTextures(theme: String) {
        disposeOldTextures()
        turnWhiteTexture = Texture(Gdx.files.internal("pieces/$theme/wK.png"))
        turnBlackTexture = Texture(Gdx.files.internal("pieces/$theme/bK.png"))
    }

    private fun disposeOldTextures() {
        if (::turnWhiteTexture.isInitialized) {
            turnWhiteTexture.dispose()
        }
        if (::turnBlackTexture.isInitialized) {
            turnBlackTexture.dispose()
        }
    }

    private fun setupListeners() {
        viewModel.onLegalMovesChanged = { moves ->
            Gdx.app.postRunnable { renderLegalMoves(moves) }
        }
        viewModel.onBoardChanged = { board ->
            Gdx.app.postRunnable { renderPieces(board) }
        }
    }

    init {
        // Set the theme based on the game version
        val theme = when (viewModel.getGameVersion()) {
            "makruk" -> "makruk"
            else -> "standard"
        }
        textureProvider.setPieceTheme(theme)

        viewModel.onLegalMovesChanged = { moves ->
            Gdx.app.postRunnable { renderLegalMoves(moves) }
        }
        viewModel.onBoardChanged = { board ->
            Gdx.app.postRunnable { renderPieces(board) }
        }
        font.data.setScale(3f)

        setupListeners()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val squareSize = Gdx.graphics.width / 8f
        val yOffset = Gdx.graphics.height - (Gdx.graphics.height / 5f) - (8 * squareSize)

        val boardX = if (viewModel.getPlayerColor() == "white") {
            (screenX / squareSize).toInt()
        } else {
            7 - (screenX / squareSize).toInt()
        }

        val boardY = ((Gdx.graphics.height - screenY - yOffset) / squareSize).toInt()

        val adjustedBoardY = if (viewModel.getPlayerColor() == "white") {
            7 - boardY
        } else {
            boardY
        }

        if (boardX in 0..7 && adjustedBoardY in 0..7) {
            val square = adjustedBoardY * 8 + boardX
            viewModel.onUserMove(square)
        }
        return true
    }

    private fun renderPieces(board: Array<Char>) {
        val squareSize = Gdx.graphics.width / 8f
        val maxYPos = Gdx.graphics.height - (Gdx.graphics.height / 5f) - squareSize
        val pieceImages = textureProvider.getPieceTextures()

        for ((index, pieceChar) in board.withIndex()) {
            var colNum = index % 8
            val rowNum = index / 8
            colNum = (7 - colNum) % 8

            // Check if player is white and rotate the board
            val xPos = if (viewModel.getPlayerColor() == "white") {
                (7 - colNum) * squareSize
            } else {
                colNum * squareSize
            }
            val yPos = if (viewModel.getPlayerColor() == "white") {
                maxYPos - rowNum * squareSize
            } else {
                maxYPos - (7 - rowNum) * squareSize
            }

            val pieceTexture = pieceImages[pieceChar]
            pieceTexture?.let {
                spriteBatch.draw(it, xPos, yPos, squareSize, squareSize)
            }
        }
    }

    private fun renderLegalMoves(moveSet: List<Move>) {
        val squareSize = Gdx.graphics.width / 8f
        val yPos = Gdx.graphics.height - (Gdx.graphics.height / 5f) - squareSize
        val circleSize = squareSize / 8f

        for (move: Move in moveSet) {
            val index = move.square
            var colNum = index % 8
            colNum = (7 - colNum) % 8
            val rowNum = index / 8

            val xPos = if (viewModel.getPlayerColor() == "white") {
                (7 - colNum) * squareSize + squareSize / 2
            } else {
                colNum * squareSize + squareSize / 2
            }

            val adjustedYPos = if (viewModel.getPlayerColor() == "white") {
                yPos - rowNum * squareSize + squareSize / 2
            } else {
                yPos - (7 - rowNum) * squareSize + squareSize / 2
            }

            dotPixelDrawer.filledCircle(xPos, adjustedYPos, circleSize)
        }
    }
    private fun renderPlayerTurn(currentTurn: String) {
        val layout = GlyphLayout() // To measure text width(s)

        // Current player text (centered)
        val currentTurnText = "Current turn: $currentTurn"
        layout.setText(font, currentTurnText)
        val currentTurnTextWidth = layout.width
        font.draw(spriteBatch, currentTurnText, ((Gdx.graphics.width - currentTurnTextWidth) / 2), (Gdx.graphics.height - 170f))

        // Current player image (centered)
        val texture = if (viewModel.getCurrentPlayer() == "white") turnWhiteTexture else turnBlackTexture
        val imageY = (Gdx.graphics.height - 170f) - 250f
        val imageSize = 200f
        spriteBatch.draw(texture, (Gdx.graphics.width - imageSize) / 2, imageY, imageSize, imageSize)

    }

    fun render() {
        val squareSize = Gdx.graphics.width / 8f
        val layout = GlyphLayout() // To measure text width

        // GameID text (upper left corner)
        if (viewModel.getOnlineStatus()) {
            val gameIdText = "Game ID: ${viewModel.getGameId()}"
            font.draw(spriteBatch, gameIdText, 10f, Gdx.graphics.height - 10f)
        } else {
            val localGameText = "Local Game"
            font.draw(spriteBatch, localGameText, 10f, Gdx.graphics.height - 10f)
        }

        // Game mode text (upper right corner)
        val gameModeText = "Game mode: ${viewModel.getGameVersion()}"
        layout.setText(font, gameModeText)
        val gameModeTextWidth = layout.width
        font.draw(spriteBatch, gameModeText, Gdx.graphics.width - gameModeTextWidth - 20f, Gdx.graphics.height - 10f)

        // Playing as color text (centered below board)
        val playerColor = viewModel.getPlayerColor()
        val playerColorText = "You are playing as: $playerColor"
        layout.setText(font, playerColorText)
        val currentTurnTextWidth = layout.width
        font.draw(spriteBatch, playerColorText, ((Gdx.graphics.width - currentTurnTextWidth) / 2), (790f))

        var colNum = 0
        var yPos = Gdx.graphics.height - (Gdx.graphics.height / 5f)

        for ((i, _) in viewModel.getBoard().withIndex()) {
            if (i % 8 == 0) {
                yPos -= (squareSize)
                colNum = 0
            }

            val xPos = (squareSize * colNum)
            if ((i / 8) % 2 == 0) {
                if (colNum % 2 == 0) {
                    lightPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                } else {
                    darkPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                }
            } else {
                if (colNum % 2 == 0) {
                    darkPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                } else {
                    lightPixelDrawer.filledRectangle(Rectangle(xPos, yPos, squareSize, squareSize))
                }
            }

            colNum += 1
        }

        renderPieces(viewModel.getBoard())
        renderPlayerTurn(viewModel.getCurrentPlayer())
        renderLegalMoves(viewModel.getLegalMoves())
    }
}