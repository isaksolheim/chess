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

class MainMenuView(val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    init {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)

        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val titleText = "CHESS++"
        val titleLabel = Label(titleText, app.skin, "button")
        titleLabel.setFontScale(2.4f)

        table.add(titleLabel).center().expandX().padBottom(30f)

        val playButton = TextButton("Play", app.skin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.screen = LobbyView(app)
            }
        })

        val aboutButton = TextButton("About CHESS++", app.skin)
        aboutButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.screen = AboutScreenView(app)
            }
        })
        table.row()
        table.add(playButton).fillX().uniformX().padTop(30f).padLeft(200f).padRight(200f)
        table.row()
        table.add(aboutButton).fillX().uniformX().padTop(30f).padLeft(200f).padRight(200f).padBottom(300f)
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
