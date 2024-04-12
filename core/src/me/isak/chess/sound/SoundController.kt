package me.isak.chess.sound

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import me.isak.chess.sound.soundeffects.StandardSoundEffectsMap
import kotlin.concurrent.Volatile

class SoundController private constructor()
{

    //Singleton pattern
    companion object {
        @Volatile
        private var instance: SoundController? = null // Volatile modifier is necessary
        fun getInstance() = instance ?: synchronized(this) { // synchronized to avoid concurrency problem
            instance ?: SoundController().also {
                instance = it
            }
        }
    }

    public enum class MenueSounds{Click}
    public enum class GameSounds{Click,Move,Capture,Check,Win}

    private var soundEffects : SoundEffectsMap? = null

    private val standardSoundEffects by lazy{
        StandardSoundEffectsMap
    }


    //Standard static menue sounds
    private val menueSounds by lazy {
        hashMapOf<MenueSounds, Sound>().apply {
            put(MenueSounds.Click,Gdx.audio.newSound(Gdx.files.internal("sound/game/standard/click.wav")))
        }
    }

    public fun setSoundEffects(soundEffects : SoundEffectsMap)
    {
        this.soundEffects = soundEffects
    }

    public fun playMenuSoundEffect(menueSound: MenueSounds)
    {
        val sound : Sound? = menueSounds.get(menueSound)
        if (sound == null) throw IllegalArgumentException("Sound is missing! :" + menueSound.toString())
        sound.play()
    }

    public fun playGameSoundEffect(gameSound: GameSounds)
    {
        if (soundEffects == null)
        {
            standardSoundEffects!!.GameSoundEffects.get(gameSound)!!.play()
            return
        }

        if (!soundEffects!!.GameSoundEffects.containsKey(gameSound))
        {
            standardSoundEffects!!.GameSoundEffects.get(gameSound)!!.play()
            return
        }

        soundEffects!!.GameSoundEffects.get(gameSound)!!.play()

    }

    public fun playCustomSoundEffect(soundName : String)
    {
        if (soundEffects == null) return;
        if (soundEffects!!.CustomSoundEffects == null) return

        if (soundEffects!!.CustomSoundEffects!!.containsKey(soundName))
            soundEffects!!.CustomSoundEffects!!.get(soundName)!!.play();
    }


}