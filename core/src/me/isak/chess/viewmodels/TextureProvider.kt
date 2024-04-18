package me.isak.chess.viewmodels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class TextureProvider {

    private var currentThemeIndex = 0
    private val pieceFolders = listOf("standard", "reverse", "makruk")
    private val pieceThemes = mutableListOf<HashMap<Char, Texture>>()

    var onThemeChange: ((String) -> Unit)? = null
    val currentTheme: String
        get() = pieceFolders[currentThemeIndex]

    init {
        pieceFolders.forEach { pieceTheme ->
            pieceThemes.add(initPieceTheme(pieceTheme))
        }
    }

    private fun initPieceTheme(theme: String): HashMap<Char, Texture> {
        val pieces = listOf('P', 'R', 'N', 'B', 'Q', 'K')
        val colors = listOf("w", "b")
        val map = hashMapOf<Char, Texture>()

        pieces.forEach { piece ->
            colors.forEach { color ->
                val key = if (color == "w") piece else piece.lowercaseChar()
                map[key] = Texture(Gdx.files.internal("pieces/$theme/${color}$piece.png"))
            }
        }
        return map
    }


    fun nextPieceTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % pieceThemes.size
        onThemeChange?.invoke(currentTheme)
    }

    fun prevPieceTheme() {
        currentThemeIndex = if (currentThemeIndex - 1 < 0) pieceThemes.size - 1 else currentThemeIndex - 1
        onThemeChange?.invoke(currentTheme)
    }

    fun setPieceTheme(pieceTheme: String) {
        val index = pieceFolders.indexOf(pieceTheme)
        if (index != -1) {
            currentThemeIndex = index
            onThemeChange?.invoke(currentTheme)
        }
    }

    fun getPieceTextures(): HashMap<Char, Texture> = pieceThemes[currentThemeIndex]

    fun dispose() {
        pieceThemes.forEach { theme ->
            theme.values.forEach(Texture::dispose)
        }
    }
}
