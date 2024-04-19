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
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport

import me.isak.chess.Chess
import me.isak.chess.sound.SoundController

class FaqScreenView(private val app: Chess, aboutTitle: String, aboutString: String) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin

    init {
        Gdx.input.inputProcessor = stage

        val aboutTitleLabel = Label(aboutTitle, skin, "button")
        aboutTitleLabel.setFontScale(1.6f)


        val aboutGameLabel = Label(aboutString, skin)
        aboutGameLabel.wrap = true
        aboutGameLabel.setFontScale(2.3f)
        if (aboutTitle != "How to get started:") {
            aboutGameLabel.setAlignment(Align.center)
        }

        // Back button
        val backButton = TextButton("Back", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                // This code will be executed when the back button is clicked
                // Change to the previous screen, e.g., the main menu screen
                app.screen = LobbyView(app)
            }
        })

        // Table layout
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)
        table.add(aboutTitleLabel).center().expandX().padLeft(200f).padRight(200f).padTop(50f)
        table.row()
        table.add(aboutGameLabel).center().fillX().padLeft(150f).padRight(150f).padTop(50f)
        table.row()
        table.add(backButton).padTop(60f).center().fillX().padLeft(200f).padRight(200f)

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
