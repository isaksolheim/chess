package me.isak.chess.viewmodels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class TextureProvider {
    private var currentPieceThemeIndex = 0
    private var currentBoardThemeIndex = 0
    private val pieceThemes = listOf(
        hashMapOf(
            'P' to Texture(Gdx.files.internal("pieces/theme1/wP.png")),
            'p' to Texture(Gdx.files.internal("pieces/theme1/bP.png")),
            'R' to Texture(Gdx.files.internal("pieces/theme1/wR.png")),
            'r' to Texture(Gdx.files.internal("pieces/theme1/bR.png")),
            'N' to Texture(Gdx.files.internal("pieces/theme1/wN.png")),
            'n' to Texture(Gdx.files.internal("pieces/theme1/bN.png")),
            'B' to Texture(Gdx.files.internal("pieces/theme1/wB.png")),
            'b' to Texture(Gdx.files.internal("pieces/theme1/bB.png")),
            'Q' to Texture(Gdx.files.internal("pieces/theme1/wQ.png")),
            'q' to Texture(Gdx.files.internal("pieces/theme1/bQ.png")),
            'K' to Texture(Gdx.files.internal("pieces/theme1/wK.png")),
            'k' to Texture(Gdx.files.internal("pieces/theme1/bK.png")),
            ),
        hashMapOf(
            'P' to Texture(Gdx.files.internal("pieces/theme2/wP.png")),
            'p' to Texture(Gdx.files.internal("pieces/theme2/bP.png")),
            'R' to Texture(Gdx.files.internal("pieces/theme2/wR.png")),
            'r' to Texture(Gdx.files.internal("pieces/theme2/bR.png")),
            'N' to Texture(Gdx.files.internal("pieces/theme2/wN.png")),
            'n' to Texture(Gdx.files.internal("pieces/theme2/bN.png")),
            'B' to Texture(Gdx.files.internal("pieces/theme2/wB.png")),
            'b' to Texture(Gdx.files.internal("pieces/theme2/bB.png")),
            'Q' to Texture(Gdx.files.internal("pieces/theme2/wQ.png")),
            'q' to Texture(Gdx.files.internal("pieces/theme2/bQ.png")),
            'K' to Texture(Gdx.files.internal("pieces/theme2/wK.png")),
            'k' to Texture(Gdx.files.internal("pieces/theme2/bK.png"))
        )
    )

    fun nextPieceTheme() {
        currentPieceThemeIndex = (currentPieceThemeIndex + 1) % pieceThemes.size
    }

    fun prevPieceTheme() {
        currentPieceThemeIndex = if (currentPieceThemeIndex - 1 < 0) pieceThemes.size - 1 else currentBoardThemeIndex - 1
    }
//    fun nextBoardTheme() {
//        currentBoardThemeIndex = (currentBoardThemeIndex + 1) % pieceThemes.size
//    }

//    fun prevBoardTheme() {
//        currentBoardThemeIndex = if (currentBoardThemeIndex - 1 < 0) boardThemes.size - 1 else currentBoardThemeIndex - 1
//    }

    fun getPieceTextures(): HashMap<Char, Texture> = pieceThemes[currentPieceThemeIndex]
//    fun getBoardTextures(): HashMap<Char, Texture> = boardThemes[currentBoardThemeIndex]

    fun dispose() {
        pieceThemes.forEach { theme ->
            theme.values.forEach(Texture::dispose)
        }
//        boardThemes.forEach { theme ->
//            theme.values.forEach(Texture::dispose)
//        }
    }
}
