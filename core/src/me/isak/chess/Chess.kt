package me.isak.chess

import me.isak.chess.game.Game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class Chess : ApplicationAdapter() {
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

    
    fun play() {
        val game = Game("standard")
        
        game.click(51)
        game.click(35)
        
        game.click(1)
        game.click(16)
        
        game.click(60)
        game.click(51)

        println(game.getBoardAsString())
    }
}

fun main() {
    val chess = Chess()
    chess.play()    
}
