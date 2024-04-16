package me.isak.chess.views

import LobbyView
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess
import me.isak.chess.sound.SoundController

class MainMenuView(val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    init {
        Gdx.input.inputProcessor = stage // Set the stage to process input events

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val playButton = TextButton("Play", app.skin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                app.setScreen(LobbyView(app))
            }
        })

        val aboutButton = TextButton("About", app.skin)
        aboutButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                app.setScreen(AboutScreenView(app))
            }
        })

        table.add(playButton).fillX().uniformX()
        table.row().pad(10f, 0f, 10f, 0f)
        table.add(aboutButton).fillX().uniformX()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1f) // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    // TODO: hide(), show(), dispose() ?
}
