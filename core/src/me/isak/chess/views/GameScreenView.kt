package me.isak.chess.views

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.Gdx
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

class GameScreen(private val app: Chess, private val game: Game) : ScreenAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }
    private val stage = Stage(ScreenViewport())
    private val gameViewModel = GameViewModel(game, app)
    private val boardView = BoardView(gameViewModel)

    init {
        setupUI()
    }

    private fun setupUI() {
        Gdx.input.inputProcessor = stage
        val backButton = TextButton("Back", app.skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.setScreen(MainMenuView(app))
            }
        })

        val table = Table()
        table.setFillParent(true)
        table.add(backButton).expand().top().left().pad(100f)

        stage.addActor(table)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)

        // Render the board view separately
        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()

        // Update and draw the stage for UI
        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        spriteBatch.dispose()
    }

    override fun dispose() {
        spriteBatch.dispose()
        stage.dispose()
    }
}