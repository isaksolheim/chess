package me.isak.chess

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import me.isak.chess.sound.SoundController
import me.isak.chess.views.MainMenuView

class Chess(val firebase: FirebaseInterface) : com.badlogic.gdx.Game() {
    lateinit var skin: Skin


    override fun create() {
        var atlas = TextureAtlas(Gdx.files.internal("skin/rainbow-ui.atlas"))
        skin = Skin(Gdx.files.internal("skin/rainbow-ui.json"), atlas)

        this.setScreen(MainMenuView(this))
    }

}

fun main() {
    val coreInterface = CoreInterface()
    val chess = Chess(coreInterface)
}
