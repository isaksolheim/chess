package me.isak.chess.sound

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import me.isak.chess.sound.soundeffects.StandardSoundEffectsMap
import kotlin.concurrent.Volatile

class SoundController private constructor()
{
    var soundEffects : SoundEffectsMap? = null
        public set
    private var standardSoundEffects : SoundEffectsMap = StandardSoundEffectsMap

    //Singleton pattern
    companion object {
        @Volatile
        private var instance: SoundController? = null // Volatile modifier is necessary
        fun getInstance() = instance ?: synchronized(this) { // synchronized to avoid concurrency problem
            instance ?: SoundController().also { instance = it }
        }
    }

    public enum class MenueSounds{Click}
    public enum class GameSounds{ClickBoard, MovePiece,TakeOver,Win}

    //Standard static menue sounds
    private val pieceImages by lazy {
        hashMapOf<MenueSounds, Sound>().apply {
            put(MenueSounds.Click,Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/click.wav")))
        }
    }


    public fun playMenuSoundEffect(menueSound: MenueSounds)
    {
        val sound : Sound? = pieceImages.get(menueSound)
        if (sound == null) throw IllegalArgumentException("Sound is missing! :" + menueSound.toString())
        sound.play()
    }

    public fun playGameSoundEffect(gameSound: GameSounds)
    {
        if (soundEffects == null)
        {
            standardSoundEffects.GameSoundEffects.get(gameSound)!!.play()
            return
        }
        if (!soundEffects!!.GameSoundEffects.containsKey(gameSound))
        {
            standardSoundEffects.GameSoundEffects.get(gameSound)!!.play()
            return
        }
        soundEffects!!.GameSoundEffects.get(gameSound)!!.play()

    }

    public fun playCustomSoundEffect(soundName : String)
    {

    }
}