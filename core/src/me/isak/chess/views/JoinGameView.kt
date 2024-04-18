import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import me.isak.chess.Chess
import me.isak.chess.FirebaseCallback
import me.isak.chess.model.base.Game
import me.isak.chess.model.FirebaseGameModel
import me.isak.chess.views.GameScreen
import me.isak.chess.views.LobbyView

class JoinGameView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())
    private var gameIdInput: TextField

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

        val skin = app.skin

        val joinGameLabel = Label("Join with a 4-digit GameID:", app.skin)
        joinGameLabel.setFontScale(2.5f)

        gameIdInput = TextField("", skin)
        gameIdInput.setScale(450f)
        gameIdInput.height = 200f
        gameIdInput.maxLength = 4
        gameIdInput.alignment = Align.center
        val joinGameButton = TextButton("Join Game", skin)
        joinGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                val game = Game("standard", player = "black") // Creating a new game

                // Setting up an event listener to listen for updates to the game
                app.firebase.getData("games/${gameIdInput.text}", object : FirebaseCallback {
                    override fun onDataReceived(dataModel: FirebaseGameModel) {
                        game.updateFromModel(dataModel)
                    }
                })

                app.screen = GameScreen(app, game)
            }
        })
        val backButton = TextButton("Back", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                app.screen = LobbyView(app)
            }
        })

        // Layout
        table.add(joinGameLabel).center().padBottom(60f)
        table.row()
        table.add(gameIdInput).expandX().fillX().center().padLeft(300f).padRight(300f).padBottom(50f)
        table.row()
        table.add(joinGameButton).expandX().fillX().center().padLeft(300f).padRight(300f).padBottom(50f)
        table.row()
        table.add(backButton).expandX().fillX().center().padLeft(300f).padRight(300f).padBottom(50f)
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
