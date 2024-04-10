package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess

class GameOverScreenView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin

    init {
        Gdx.input.inputProcessor = stage

        // Table used for layout
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        // About us text
        val aboutText = "x is the winner!"
        val aboutLabel = Label(aboutText, skin)
        table.add(aboutLabel)

        // Back button
        val backButton = TextButton("Back", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                // This code will be executed when the back button is clicked
                // Change to the previous screen, e.g., the main menu screen
                app.setScreen(MainMenuView(app))
            }
        })
        table.add(backButton).padTop(20f) // Adjust padding as needed
        table.row()

        table.center()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        stage.dispose()
    }
}