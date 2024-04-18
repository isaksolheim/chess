package me.isak.chess.views

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
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
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.viewmodels.TextureProvider

class GameScreen(private val app: Chess, private val game: Game) : ScreenAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }
    private val stage = Stage(ScreenViewport())
    private val gameViewModel = GameViewModel(game, app)
    private val textureProvider = TextureProvider()
    private val boardView = BoardView(gameViewModel, textureProvider)

    init {
        setupUI()
    }

    private fun setupUI() {
        Gdx.input.inputProcessor = stage
        val backButton = TextButton("Exit", app.skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.setScreen(MainMenuView(app))
            }
        })

        val nextButton = TextButton("Next pieces", app.skin)
        nextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                textureProvider.nextPieceTheme()
            }
        })

        val prevButton = TextButton("Prev pieces", app.skin)
        prevButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                textureProvider.prevPieceTheme()
            }
        })

        val table = Table()
        table.setFillParent(true)
        table.add(backButton).expand().top().left().padTop(100f)
        table.row() // Create a new row for theme buttons
        table.add(prevButton).expandX().top().padBottom(300f).padLeft(20f)  // Higher placement with specific top padding
        table.add(nextButton).expandX().top().padBottom(300f).padRight(70f)

        stage.addActor(table)
    }

    override fun show() {
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)
        inputMultiplexer.addProcessor(boardView)
        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)

        // Render the board view
        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()

        stage.act(delta)
        stage.draw()

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