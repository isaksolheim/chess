package me.isak.chess.sound
import com.badlogic.gdx.audio.Sound

data class SoundEffectsMap(
    val CustomSoundEffects: HashMap<String,Sound>,
    val GameSoundEffects: HashMap<SoundController.GameSounds,Sound>
)