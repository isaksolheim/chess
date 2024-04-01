package me.isak.chess

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.ScreenUtils
import me.isak.chess.game.Game
import me.isak.chess.viewmodels.GameViewModel
import me.isak.chess.views.BoardView

class Chess(private val firebase: FirebaseInterface) : ApplicationAdapter() {
    private val spriteBatch by lazy { Renderer.spriteBatch }

    private val game = Game("standard")
    private val gameViewModel = GameViewModel(game)
    private val boardView = BoardView(gameViewModel)

    override fun create() {
        // initialize stuff here
        Gdx.input.inputProcessor = boardView
    }

    override fun render() {
        ScreenUtils.clear(0.95f, 0.95f, 0.95f, 1f)
        spriteBatch.begin()
        boardView.render()
        spriteBatch.end()
    }

    override fun dispose() {
        spriteBatch.dispose()
    }

    // This function highlights how the game object can be used.
    //
    // After all the moves have been executed, the game prints this to console:
    // "rnbqkb1r/pp2p1pp/5n2/1p1p4/8/5N2/PPPP1PPP/RNBQ1RK1 b kq -"
    fun play() {
        //val game = Game("standard")

        game.click(52)        // Click on f2 (pawn)
        game.click(36)        // move pawn two forward

        game.click(11)
        game.click(27)        // move black pawn two forward

        game.click(36)
        game.click(28)        // move white pawn even further

        game.click(13)
        game.click(29)        // move black pawn next to white pawn

        game.click(28)
        game.click(21)        // white en passant

        game.click(6)
        game.click(21)        // black move knight

        game.click(61)
        game.click(25)        // white check black with bishop

        game.click(10)
        game.click(18)        // black block with pawn

        game.click(62)
        game.click(45)        // white move knight

        game.click(18)
        game.click(25)        // black capture bishop

        game.click(60)
        game.click(62)        // white castle kingside

        println(game.fen())
        // Any position can be viewed by going to lichess.org/analysis
        // and put the printed string in the FEN section.
        // Any game state can be printed with println(game.fen()).
        // https://lichess.org/analysis/fromPosition/rnbqkb1r/pp2p1pp/5n2/1p1p4/8/5N2/PPPP1PPP/RNBQ1RK1_w_kq_-_0_1
    }
}

fun main() {
    val coreInterface = CoreInterface()
    val chess = Chess(coreInterface)
    chess.play()
}
