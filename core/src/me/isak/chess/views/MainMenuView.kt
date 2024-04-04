package me.isak.chess.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess

class MainMenuView(val game: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    init {
        Gdx.input.inputProcessor = stage // Set the stage to process input events

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val atlas = TextureAtlas(Gdx.files.internal("skin/rainbow-ui.atlas"))
        val skin = Skin(Gdx.files.internal("skin/rainbow-ui.json"), atlas)

        val playLocalButton = TextButton("Play Local", skin)
        playLocalButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.setScreen(GameScreen(game))
            }
        })

        val playMultiplayerButton = TextButton("Play Multiplayer", skin)
        playMultiplayerButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                // TODO: Change to MultiplayerScreen
            }
        })

        val aboutButton = TextButton("About", skin)
        aboutButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                // TODO: Change to About Screen
            }
        })

        table.add(playLocalButton).fillX().uniformX()
        table.row().pad(10f, 0f, 10f, 0f)
        table.add(playMultiplayerButton).fillX().uniformX()
        table.row()
        table.add(aboutButton).fillX().uniformX()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT) // Clear the screen
        stage.act(Gdx.graphics.deltaTime.coerceAtMost(1 / 30f))
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    // TODO: hide(), show(), dispose() ?
}
