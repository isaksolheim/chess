import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import me.isak.chess.Chess
import me.isak.chess.FirebaseCallback
import me.isak.chess.model.base.Game
import me.isak.chess.model.FirebaseGameModel
import me.isak.chess.views.FaqScreenView
import me.isak.chess.views.GameScreen
import me.isak.chess.views.MainMenuView

class LobbyView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    init {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)

        val gameVariants = arrayOf("standard", "koth", "horde", "fischer", "racing", "threecheck", "makruk", "atomic")
        val libGDXArray = Array<String>()
        val fontScale = 2.5f

        gameVariants.forEach { variant ->
            libGDXArray.add(variant)
        }

        val selectBox = SelectBox<String>(app.skin).apply {
            items = libGDXArray
            setSelected("standard")
        }
        selectBox.style.listStyle.font.data.setScale(2.4f)
        selectBox.style.listStyle.font.color = Color.WHITE
        selectBox.style.listStyle.fontColorSelected = Color.WHITE
        selectBox.style.listStyle.fontColorUnselected = Color.WHITE
        selectBox.setAlignment(Align.center)

        val table = Table()
        table.setFillParent(true)

        stage.addActor(table)

        val skin = app.skin

        // Back button
        val backButton = TextButton("Exit", skin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                app.setScreen(MainMenuView(app))
            }
        })

        // Help (?) button
        val questionButton = TextButton("Help", app.skin)
        questionButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                app.setScreen(FaqScreenView(app))
            }
        })

        // Title
        val titleText = "CHESS++"
        val titleLabel = Label(titleText, app.skin, "button")
        titleLabel.setFontScale(2.4f)

        // Game mode selection
        val gameModeLabel = Label("Select your Game Mode:", app.skin)
        gameModeLabel.setFontScale(fontScale)
        gameModeLabel.setAlignment(Align.center)

        val joinGameLabel = Label("Or join an existing game:", app.skin)
        joinGameLabel.setFontScale(fontScale)


        val playLocalButton = TextButton("Start Local Game", app.skin)
        playLocalButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                val selectedVariant = selectBox.selected
                val game = Game(selectedVariant)

                app.screen = GameScreen(app, game)
            }
        })

        // Create Game button
        val createGameButton = TextButton("Start Online Game", skin)
        createGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                val selectedVariant = selectBox.selected
                val game = Game(selectedVariant) // Creating a new game
                app.firebase.pushValue(game.id, game.toJSON()) // Pushing game to Firebase

                // Setting up an event listener to listen for updates to the game
                app.firebase.getData("games/${game.id}", object : FirebaseCallback {
                    override fun onDataReceived(dataModel: FirebaseGameModel) {
                        game.updateFromModel(dataModel)
                    }
                })

                app.setScreen(GameScreen(app, game))
            }
        })

        // Join Game button
        val joinGameButton = TextButton("Join", skin)
        joinGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.setScreen(JoinGameView(app))
            }
        })


        table.top().add(backButton).left().padTop(170f).padLeft(50f).width(200f).height(100f)
        table.add(questionButton).right().expandX().padTop(170f).padRight(80f).width(200f).height(100f)
        table.row()
        table.add(titleLabel).colspan(2).expandX().padTop(350f)
        table.row()
        table.add(gameModeLabel).colspan(2).center().padTop(60f)
        table.row()
        table.add(selectBox).colspan(2).fillX().padLeft(240f).padRight(200f).padTop(30f)
        table.row()
        table.add(playLocalButton).colspan(2).fillX().padLeft(200f).padRight(200f).padTop(30f)
        table.row()
        table.add(createGameButton).colspan(2).fillX().padLeft(200f).padRight(200f).padTop(30f)
        table.row()
        table.add(joinGameLabel).colspan(2).center().padLeft(200f).padRight(200f).padTop(100f)
        table.row()
        table.add(joinGameButton).colspan(2).fillX().padLeft(200f).padRight(200f).padTop(30f)

        stage.addActor(table)
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
