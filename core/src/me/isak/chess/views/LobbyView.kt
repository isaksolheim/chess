package me.isak.chess.views

import JoinGameView
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

data class GameVariant(val name: String, val description: String)

class LobbyView(private val app: Chess) : ScreenAdapter() {
    private val stage = Stage(ScreenViewport())

    val gameGeneralInfo = """
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

    private val gameVariants = arrayOf(
        GameVariant("standard", "Standard chess: Classic rules, no modifications. Focus on controlling the center and developing pieces."),
        GameVariant("koth", "King of the Hill: Reach and control the center squares (d4, d5, e4, e5) with your king to win. Prioritize king safety and center control."),
        GameVariant("horde", "Horde: One side has a large number of pawns against typical pieces. Use pawns to overwhelm or break through with standard pieces."),
        GameVariant("fischer", "Fischer random chess: Start with a random setup. Develop strategies on the fly and adapt to unique piece positions."),
        GameVariant("racing", "Racing Kings: Race your king to the eighth rank first without getting into check. Avoid checks and plan a clear path for your king."),
        GameVariant("threecheck", "Three-check chess: Check your opponent three times to win. Aggressively position your pieces to threaten the opponent’s king."),
        GameVariant("makruk", "Makruk (Thai chess): Similar to standard but with different pieces like the Met (bishop) and Khon (knight). Learn the unique movements and strategies."),
        GameVariant("atomic", "Atomic chess: Captures cause an explosion, destroying surrounding pieces except pawns. Sacrifice pieces wisely and control the aftermath.")
    )

    fun getDescriptionByName(gameName: String): String {
        val gameVariant = gameVariants.find { it.name == gameName }
        if (gameVariant?.description != null) {
            return gameVariant.description
        }
        return "Chess!"
    }




    init {
        Gdx.input.inputProcessor = stage

        val backgroundTexture = Texture(Gdx.files.internal("background/chessbackground.png"))
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        val backgroundImage = Image(backgroundTexture)
        backgroundImage.setFillParent(true)
        stage.addActor(backgroundImage)

        val libGDXArray = Array<String>()
        val fontScale = 2.5f

        gameVariants.forEach { variant ->
            libGDXArray.add(variant.name)
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
                app.screen = MainMenuView(app)
            }
        })

        // Help (?) button
        val gameGeneralInfoButton = TextButton("Help", app.skin)
        gameGeneralInfoButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                val infoTitle = "How to get started:"
                app.screen = FaqScreenView(app, infoTitle, gameGeneralInfo)
            }
        })

        val gameModeInfoButton = TextButton("?", app.skin)
        gameModeInfoButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                val infoTitle = selectBox.selected
                val gameDescription = getDescriptionByName(selectBox.selected)
                app.screen = FaqScreenView(app, infoTitle, gameDescription)
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

                app.screen = GameScreen(app, game)
            }
        })

        // Join Game button
        val joinGameButton = TextButton("Join", skin)
        joinGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                app.screen = JoinGameView(app)
            }
        })

        table.top()
        table.add(backButton).left().padTop(170f).padLeft(50f).width(200f).height(100f)
        table.add(gameGeneralInfoButton).right().expandX().padTop(170f).padRight(80f).width(200f).height(100f)
        table.row()
        table.add(titleLabel).colspan(2).expandX().padTop(350f)
        table.row()
        table.add(gameModeLabel).colspan(2).center().padTop(60f)
        table.row()
        table.add(selectBox).expandX().padTop(30f).width(510f).padLeft(195f).padRight(-45f).colspan(1)
        table.add(gameModeInfoButton).padTop(30f).width(200f).padRight(160f).padLeft(-75f).colspan(1)
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
