package me.isak.chess

import me.isak.chess.views.MainMenuView

class Chess(private val firebase: FirebaseInterface) : com.badlogic.gdx.Game() {

    override fun create() {
        this.setScreen(MainMenuView(this))
    }

}

fun main() {
    val coreInterface = CoreInterface()
    val chess = Chess(coreInterface)
}
