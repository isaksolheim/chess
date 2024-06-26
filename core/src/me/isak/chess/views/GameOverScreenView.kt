package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess
import me.isak.chess.model.base.Game
import me.isak.chess.viewmodels.GameViewModel

class GameOverScreenView(private val app: Chess, private val game: Game, private val message: String) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin
    private val winnerWhiteTexture = Texture(Gdx.files.internal("pieces/standard/wK.png"))
    private val winnerBlackTexture = Texture(Gdx.files.internal("pieces/standard/bK.png"))
    private val gameViewModel = GameViewModel(game, app)

    init {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)

        // Button for opening main menu
        val mainMenuButton = TextButton("Return to main menu", skin)
        mainMenuButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                app.screen = MainMenuView(app)
            }
        })

        // Title
        val gameOverText = "Game Over"
        val gameOverLabel = Label(gameOverText, skin, "button")

        // Winner image(s)
        val winnerImageWhite = Image(winnerWhiteTexture)
        val winnerImageBlack = Image(winnerBlackTexture)
        val winner = "White"

        // Information text
        val informationText = "is the winner"
        val informationLabel = Label(message, skin, "button")

        // Screen layout
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)
        table.add(gameOverLabel)
        table.row()
        table.add(informationLabel)
        table.row()
        // Display king piece image(s) depending on who won
        val imagesTable = Table()
        when (winner) {
            "white" -> imagesTable.add(winnerImageWhite).size(150f, 150f)
            "black" -> imagesTable.add(winnerImageBlack).size(150f, 150f)
            else -> {
                imagesTable.add(winnerImageWhite).size(150f, 150f)
                imagesTable.add(winnerImageBlack).size(150f, 150f)
            }
        }
        table.add(imagesTable)
        table.row().padBottom(20f)
        table.add(mainMenuButton).padTop(20f)
        table.row()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
        winnerWhiteTexture.dispose()
        winnerBlackTexture.dispose()
    }
}
