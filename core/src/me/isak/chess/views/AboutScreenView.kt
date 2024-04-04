package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess

class AboutScreenView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin
    private val logoTexture = Texture(Gdx.files.internal("glos.jpeg"))

    init {
        Gdx.input.inputProcessor = stage

        // Table used for layout
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

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

        // Image
        val logoImage = Image(logoTexture)
        table.add(logoImage).padBottom(20f).row()
        table.row()

        // About us text
        val aboutText = "This app is developed by students at NTNU\ntaking the TDT4240 course."
        val aboutLabel = Label(aboutText, skin)
        table.add(aboutLabel)

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
        logoTexture.dispose()
    }
}
