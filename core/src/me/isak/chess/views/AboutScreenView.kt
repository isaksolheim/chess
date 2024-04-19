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
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import me.isak.chess.Chess
import me.isak.chess.sound.SoundController

class AboutScreenView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private val skin = app.skin
    private val logoTexture = Texture(Gdx.files.internal("glos.jpeg"))

    init {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)

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
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                app.setScreen(MainMenuView(app))
            }
        })
        table.row()

        // Image
        val logoImage = Image(logoTexture)
        logoImage.setScale(2f)
        table.add(logoImage).padBottom(40f).padTop(-100f).center().padRight(logoImage.width)
        table.row()

        // About us text
        val aboutText = "CHESS++ was developed with passion by a group of chess enthusiasts from NTNU. The TDT4240 course guided us toward an architecture that suits its needs."
        val aboutLabel = Label(aboutText, skin)
        aboutLabel.setFontScale(2.3f)
        aboutLabel.wrap = true
        aboutLabel.setAlignment(Align.center)

        table.add(aboutLabel).expandX().fillX().padLeft(70f).padRight(70f)
        table.center()
        table.row()
        table.add(backButton).padTop(40f).width(350f)
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
