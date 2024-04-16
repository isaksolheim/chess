import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Actor
import me.isak.chess.Chess
import me.isak.chess.FirebaseCallback
import me.isak.chess.model.base.Game
import me.isak.chess.model.FirebaseGameModel
import me.isak.chess.sound.SoundController
import me.isak.chess.views.GameScreen

class JoinGameView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private var gameIdInput: TextField

    init {
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val skin = app.skin

        gameIdInput = TextField("", skin)
        val joinGameButton = TextButton("Join Game", skin)
        joinGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                val game = Game("standard", player = "black") // Creating a new game

                // Setting up an event listener to listen for updates to the game
                app.firebase.getData("games/${gameIdInput.text}", object : FirebaseCallback {
                    override fun onDataReceived(dataModel: FirebaseGameModel) {
                        game.updateFromModel(dataModel)
                    }
                })

                app.setScreen(GameScreen(app, game))
            }
        })
        val backButton = TextButton("Back", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                SoundController.getInstance().playMenuSoundEffect(SoundController.MenueSounds.Click)
                app.setScreen(LobbyView(app))
            }
        })

        // Layout
        table.add(gameIdInput).pad(10f)
        table.row()
        table.add(joinGameButton).pad(10f)
        table.row()
        table.add(backButton)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }
}
