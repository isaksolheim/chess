import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import me.isak.chess.Chess
import me.isak.chess.views.GameScreen
import me.isak.chess.views.MainMenuView

class LobbyView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    init {
        Gdx.input.inputProcessor = stage
        val table = Table()
        table.setFillParent(true)
        stage.addActor(table)

        val skin = app.skin

        val backButton = TextButton("Back", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                // This code will be executed when the back button is clicked
                // Change to the previous screen, e.g., the main menu screen
                // val firebaseGameModel = app.toJSON()
                // game.firebase.pushValue("1", firebaseGameModel)
                app.setScreen(MainMenuView(app))
            }
        })

        // Create Game button
        val createGameButton = TextButton("Create Game", skin)
        createGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                // TODO: Create Firebase Game
                app.setScreen(GameScreen(app))
            }
        })

        // Join Game button
        val joinGameButton = TextButton("Join Game", skin)
        joinGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.setScreen(JoinGameView(app))
            }
        })

        // Adding buttons to the table
        table.add(backButton).padTop(10f)
        table.row()
        table.add(createGameButton).pad(10f)
        table.row()
        table.add(joinGameButton).pad(10f)
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
