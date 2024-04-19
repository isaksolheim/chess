package me.isak.chess.views

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils
import me.isak.chess.Chess
import me.isak.chess.Renderer
import me.isak.chess.model.base.Game
import me.isak.chess.viewmodels.GameViewModel
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.viewmodels.TextureProvider

class GameScreen(private val app: Chess, private val game: Game) : ScreenAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }
    private val stage = Stage(ScreenViewport())
    private val gameViewModel = GameViewModel(game, app)
    private val textureProvider = TextureProvider()
    private val boardView = BoardView(gameViewModel, textureProvider)
//    private lateinit var themeLabel: Label // Declare the label

    init {
        setupUI()

//        // Listener / updater for setting text reflecting current piece theme:
//        updateThemeLabel()
//        textureProvider.onThemeChange = { theme ->
//            updateThemeLabel()
//        }
    }

    private fun setupUI() {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)


        val backButton = TextButton("Exit", app.skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.screen = MainMenuView(app)
            }
        })

        val nextButton = TextButton("Next theme", app.skin)
        nextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                textureProvider.nextPieceTheme()
            }
        })

        val prevButton = TextButton("Prev theme", app.skin)
        prevButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                textureProvider.prevPieceTheme()
            }
        })

        // Piece theme changing label
//        themeLabel = Label("Change themes: ", app.skin, "button")
//        themeLabel.setFontScale(1.3f)

        val table = Table()
        table.setFillParent(true)
        table.add(backButton).expand().top().left().padTop(100f)

        table.row().padTop(70f)

//        table.add(themeLabel).colspan(3).center().padBottom(40f)

        // Add buttons in the next row
        table.row()
        table.add(prevButton).padBottom(150f).padLeft(20f)
        table.add(nextButton).padBottom(150f).padRight(100f)

        stage.addActor(table)
    }

//    private fun updateThemeLabel() {
//        themeLabel.setText("Piece theme: ${textureProvider.currentTheme}")
//    }

    override fun show() {
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)
        inputMultiplexer.addProcessor(boardView)
        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)
        stage.act(delta)
        stage.draw()

        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()

        // Check if the game is over and navigate to a new screen if it is
        val (gameOver, message) = gameViewModel.checkGameOver()
        if (gameOver) {
            app.setScreen(GameOverScreenView(app, game, message))
        }
    }

    override fun dispose() {
        spriteBatch.dispose()
        textureProvider.dispose()
        stage.dispose()
    }
}