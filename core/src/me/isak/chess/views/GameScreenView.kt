package me.isak.chess.views

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import me.isak.chess.Chess
import me.isak.chess.Renderer
import me.isak.chess.model.base.Game
import me.isak.chess.viewmodels.GameViewModel

class GameScreen(app: Chess, game: Game) : ScreenAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }

    private val gameViewModel = GameViewModel(game, app)
    private val boardView = BoardView(gameViewModel)

    override fun show() {
        Gdx.input.inputProcessor = boardView
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)

        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()
    }

    override fun hide() {
        spriteBatch.dispose()
    }

    override fun dispose() {
        spriteBatch.dispose()
    }
}
