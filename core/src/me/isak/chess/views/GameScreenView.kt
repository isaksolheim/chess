package me.isak.chess.views

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import me.isak.chess.Chess
import me.isak.chess.Renderer
import me.isak.chess.game.Game
import me.isak.chess.viewmodels.GameViewModel

class GameScreen(private val app: Chess, private val game: Game) : ScreenAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }

    private val gameViewModel = GameViewModel(game)
    private val boardView = BoardView(gameViewModel)

    override fun show() {
        Gdx.input.inputProcessor = boardView

        // TODO: create firebase game here?
        // val firebaseGameModel = game.toJSON()
        // firebase.pushValue("1", firebaseGameModel)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)

        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()
    }

    override fun hide() {
        // TODO: Clean up resources when hiding screen?
    }

    override fun dispose() {
        spriteBatch.dispose()
    }
}
