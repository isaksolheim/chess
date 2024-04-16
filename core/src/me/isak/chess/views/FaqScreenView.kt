package me.isak.chess.views

import LobbyView
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
import me.isak.chess.sound.SoundController

class FaqScreenView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin

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
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                // This code will be executed when the back button is clicked
                // Change to the previous screen, e.g., the main menu screen
                app.setScreen(LobbyView(app))
            }
        })
        table.add(backButton).padTop(20f) // Adjust padding as needed
        table.row()

        // FAQ text
        val faqText = """
            To start playing, first use the game mode picker to select your desired game mode.
            We offer several modes including standard chess, Horde, King of the Hill,
            and Fischer random chess.

            To play a local game:
            1. Select your game mode from the picker.
            2. Click the "Start Local Game" button to begin playing a friend on the same device.

            To start an online game:
            1. Select your game mode from the picker.
            2. Click the "Start Online Game" button. This will create a new game and provide you
            with a 4-digit game ID.
            3. Share this game ID with a friend so they can join your game.

            To join an existing game:
            1. Click the "Join" button.
            2. Enter the 4-digit game ID provided by your opponent.
            3. Once entered, you'll be connected to the game already in progress.
        """.trimIndent()
        val faqLabel = Label(faqText, skin)
        table.add(faqLabel)

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
