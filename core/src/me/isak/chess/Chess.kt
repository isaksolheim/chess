package me.isak.chess;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils
import me.isak.chess.views.BoardView

class Chess : ApplicationAdapter() {
	private lateinit var img: Texture
	private val spriteBatch by lazy { Renderer.spriteBatch }
	private val boardView = BoardView()

	override fun create() {
		// Setup...
	}

	override fun render() {
		ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)

		spriteBatch.begin()
		boardView.render()
		spriteBatch.end()
	}

	override fun dispose() {
		spriteBatch.dispose()
		img.dispose()
	}
}