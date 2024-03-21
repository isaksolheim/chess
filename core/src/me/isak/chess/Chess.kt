package me.isak.chess

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class Chess(private val firebase: FirebaseInterface) : ApplicationAdapter() {
	private lateinit var batch: SpriteBatch
	private lateinit var img: Texture

	override fun create() {
		batch = SpriteBatch()
		img = Texture("badlogic.jpg")
	}

	override fun render() {
		ScreenUtils.clear(1f, 1f, 0f, 1f)
		batch.begin()
		batch.draw(img, 0f, 0f)
		batch.end()
	}

	override fun dispose() {
		batch.dispose()
		img.dispose()
	}
}