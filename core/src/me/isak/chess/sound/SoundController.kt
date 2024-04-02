package me.isak.chess.sound

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import kotlin.concurrent.Volatile

class SoundController private constructor()
{
    companion object {
        @Volatile
        private var instance: SoundController? = null // Volatile modifier is necessary
        fun getInstance() = instance ?: synchronized(this) { // synchronized to avoid concurrency problem
            instance ?: SoundController().also { instance = it }
        }
    }


    public enum class GameSounds{ClickBoard, MovePiece}

    //Standard sounds
    val menueClick : Sound = Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/click.wav"))
    val music : Sound = Gdx.audio.newSound(Gdx.files.internal("sound/music/age-of-war-music.mp3"))
    fun playMusic()
    {

    }
    fun menuClick()
    {

    }

    fun playSoundEffect(gameEffects: GameSounds)
    {

    }

    fun chessClick()
    {
        menueClick.play()
    }
}